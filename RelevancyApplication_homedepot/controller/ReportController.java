package ca.homedepot.relevancy.controller;

import ca.homedepot.relevancy.config.properties.ReportProperties;
import ca.homedepot.relevancy.model.RelevancyReportParameters;
import ca.homedepot.relevancy.model.ReportFile;
import ca.homedepot.relevancy.model.SolrReportParameters;
import ca.homedepot.relevancy.model.entries.IEntry;
import ca.homedepot.relevancy.processors.RelevancyProcessor;
import ca.homedepot.relevancy.processors.ReportProcessor;
import ca.homedepot.relevancy.processors.SolrReportProcessor;
import ca.homedepot.relevancy.processors.CategoryReportProcessor;
import ca.homedepot.relevancy.services.report.ReportService;
import ca.homedepot.relevancy.tasks.TopResultMatchReportTask;
import ca.homedepot.relevancy.util.DataInputUtil;
import ca.homedepot.relevancy.util.SearchTermUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


@RestController
public class ReportController
{
	private static final Logger LOG = LoggerFactory.getLogger(ReportController.class);
	private final ExecutorService executorService = Executors.newFixedThreadPool(10);
	private static final Map<String, ReportProcessor> processors = new ConcurrentHashMap<>();
	private static final Map<String, TopResultMatchReportTask> topResultMatchReportTasks = new ConcurrentHashMap<>();
	private static final Map<String, Future<?>> reportStatuses = new ConcurrentHashMap<>();

	private final ReportService reportService;
	private final ApplicationContext context;

	public ReportController(final ReportService reportService, final ApplicationContext context)
	{
		this.reportService = reportService;
		this.context = context;
	}

	@GetMapping({ "/" })
	public ModelAndView getHome(final Model model)
	{
		final ModelAndView index = new ModelAndView("index");
		index.addObject("report", new RelevancyReportParameters(context.getBean(ReportProperties.class)));
		return index;
	}

	@GetMapping({ "/report" })
	public ModelAndView getReports(final Model model)
	{
		final List<ReportFile> reportFiles = reportService.getReportFiles();
		final ModelAndView mav = new ModelAndView("report-list");
		mav.addObject("reportFiles", reportFiles);
		return mav;
	}

	@GetMapping({ "/topResult" })
	public ModelAndView getTopResultHome()
	{
		final ModelAndView mav = new ModelAndView("top-result");
		mav.addObject("topReport", new RelevancyReportParameters(context.getBean(ReportProperties.class)));
		return mav;
	}

	@GetMapping({ "/solrReport" })
	public ModelAndView getSolrReport()
	{
		final ModelAndView mav = new ModelAndView("solr-report");
		mav.addObject("report", new SolrReportParameters(context.getBean(ReportProperties.class)));
		return mav;
	}
	
	@GetMapping({ "/categoryReport" })
	public ModelAndView getCategoryReport()
	{
		final ModelAndView mav = new ModelAndView("category-count-report");
		mav.addObject("report", new SolrReportParameters(context.getBean(ReportProperties.class)));
		return mav;
	}


	/**
	 * Downloads the selected file from the browser
	 *
	 * @param response
	 * @param fileName
	 */
	@GetMapping({ "/report/{fileName:.+}" })
	public void downloadReport(final HttpServletResponse response, @PathVariable final String fileName)
	{
		final File reportFile = reportService.getReportFileByFileName(fileName);

		// Proceed to download if it is a csv file
		if (reportFile != null && fileName.endsWith("csv"))
		{
			response.setHeader("Content-Type", "application/csv");
			response.setHeader("Content-Disposition", String.format("attachment; fileName=\"%s\"", fileName));
			response.setContentLengthLong(reportFile.length());
			try (final InputStream is = new BufferedInputStream(new FileInputStream(reportFile)))
			{
				FileCopyUtils.copy(is, response.getOutputStream());
			}
			catch (final IOException ex)
			{
				LOG.error("Failed to open stream and download file", ex);
			}
		}
	}

	@GetMapping({ "/status/report/{filename:.+}" })
	public int getReportStatus(@PathVariable final String filename)
	{
		final ReportProcessor processor = processors.get(filename);
		if (processor == null)
		{
			return 0;
		}

		return processor.getStatus();
	}

	@GetMapping({ "/status/topReport/{filename:.+}" })
	public int getTopReportStatus(@PathVariable final String filename)
	{
		final TopResultMatchReportTask topResultMatchReportTask = topResultMatchReportTasks.get(filename);
		if (topResultMatchReportTask == null)
		{
			return 0;
		}

		return topResultMatchReportTask.getStatus();
	}

	@DeleteMapping({ "/cancel/{filename:.+}" })
	public void cancel(@PathVariable final String filename)
	{
		final Future<?> status = reportStatuses.get(filename);
		if (status == null)
		{
			return;
		}

		status.cancel(true);
		processors.remove(filename);
		topResultMatchReportTasks.remove(filename);
		reportStatuses.remove(filename);
	}

	@PostMapping("/report")
	public void submitReport(@RequestParam("inputEntries") final MultipartFile file,
			@ModelAttribute final RelevancyReportParameters reportParameters, final HttpServletResponse response)
	{
		final IEntry[] inputEntries = DataInputUtil.parseInputEntries(file);
		if (inputEntries == null)
		{
			return;
		}

		// Retrieve a new instance of report processors
		final RelevancyProcessor processor = context.getBean(RelevancyProcessor.class);
		processor.setReportParameters(reportParameters);
		processor.setInputEntries(DataInputUtil.parseInputEntries(inputEntries, reportParameters.getTimePeriod().getEvaluator()));
		processor.setFilename(ReportService.getFilename(reportParameters.getName()));

		final String filename = processor.getFilename();
		final Future<?> status = executorService.submit(processor);

		processors.put(filename, processor);
		reportStatuses.put(filename, status);

		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		response.setHeader("Location", filename);
	}

	@PostMapping("/topResultReport")
	public void submitTopResultReport(@RequestParam("searchTerms") final MultipartFile file,
			@ModelAttribute final RelevancyReportParameters userReport, final HttpServletResponse response)
	{
		final String[] searchTerms = SearchTermUtil.parseSearchTerms(file);

		userReport.setAnalyticsEnable(true);
		userReport.setBoostBuryEnable(true);
		userReport.setNlpEnable(true);

		// Retrieve a new instance of report task
		final TopResultMatchReportTask topResultMatchReportTask = context.getBean(TopResultMatchReportTask.class);
		topResultMatchReportTask.setUserReport(userReport);
		topResultMatchReportTask.setSearchTerms(SearchTermUtil.parseSearchTerms(searchTerms));
		topResultMatchReportTask.setFilename(ReportService.getFilename(userReport.getName()));

		final String filename = topResultMatchReportTask.getFilename();
		final Future<?> status = executorService.submit(topResultMatchReportTask);

		topResultMatchReportTasks.put(filename, topResultMatchReportTask);
		reportStatuses.put(filename, status);

		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		response.setHeader("Location", filename);
	}

	@PostMapping("/solrReport")
	public void submitSolrReport(@RequestParam("searchTerms") final MultipartFile file,
			@ModelAttribute final SolrReportParameters reportParameters, final HttpServletResponse response)
	{
		final IEntry[] inputEntries = DataInputUtil.parseInputEntries(file);

		if (inputEntries == null)
		{
			return;
		}

		// Retrieve a new instance of report processors
		final SolrReportProcessor processor = context.getBean(SolrReportProcessor.class);
		processor.setReportParameters(reportParameters);
		processor.setInputEntries(DataInputUtil.parseInputEntries(inputEntries, reportParameters.getTimePeriod().getEvaluator()));
		processor.setFilename(ReportService.getFilename(reportParameters.getName()));

		final String filename = processor.getFilename();
		final Future<?> status = executorService.submit(processor);

		processors.put(filename, processor);
		reportStatuses.put(filename, status);

		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		response.setHeader("Location", filename);
	}
	
	/** This method parses the CSV file containing searchTerms, categoryLeaf, score, and language.
	 *  It then posts the data to a new CSV file with the parameters searchTerms, categoryLeaf, 
	 *  score, #SKU Before Changes, #SKU After Changes, and Language.
	 */
	@PostMapping("/categoryReport")
	public void submitCategoryReport(@RequestParam("searchTerms") final MultipartFile file,
			@ModelAttribute final SolrReportParameters reportParameters, final HttpServletResponse response, final HttpServletRequest request) throws IOException
	{
				
		final List<IEntry> inputEntries = DataInputUtil.parseCSV(file, request);

		// Retrieve a new instance of report processors
		final CategoryReportProcessor processor = context.getBean(CategoryReportProcessor.class);
		processor.setReportParameters(reportParameters);
		processor.setInputEntries(inputEntries);
		processor.setFilename(ReportService.getFilename(reportParameters.getName()));

		final String filename = processor.getFilename();
		final Future<?> status = executorService.submit(processor);

		processors.put(filename, processor);
		reportStatuses.put(filename, status);

		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		response.setHeader("Location", filename);
	}
}
