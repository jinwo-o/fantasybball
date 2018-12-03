package ca.homedepot.relevancy.tasks;

import ca.homedepot.relevancy.config.properties.ReportProperties;
import ca.homedepot.relevancy.model.SolrTopSearchResult;
import ca.homedepot.relevancy.model.RelevancyReportParameters;
import ca.homedepot.relevancy.processors.TopResultMatchProcessor;
import ca.homedepot.relevancy.services.report.ReportService;
import ca.homedepot.relevancy.services.writer.PrintReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope("prototype")
public class TopResultMatchReportTask implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(TopResultMatchReportTask.class);

    private final ReportProperties reportProperties;
    private final ApplicationContext context;

    private PrintReportService printReportService;

    private RelevancyReportParameters userReport;
    private String filename;
    private List<String> searchTerms;

    final Map<String, SolrTopSearchResult> matchResults = new ConcurrentHashMap<>();

    @Autowired
    public TopResultMatchReportTask(final ReportProperties reportProperties, final ApplicationContext context, final PrintReportService printReportService) {
        this.reportProperties = reportProperties;
        this.context = context;
        this.printReportService = printReportService;
    }

    private Map<String, SolrTopSearchResult> getMatchResults() {
        final TopResultMatchProcessor topResultMatchProcessor = context.getBean(TopResultMatchProcessor.class);
        topResultMatchProcessor.executeTopResultMatchTask(searchTerms, userReport, matchResults);

        while (!Thread.currentThread().isInterrupted() && matchResults.size() < searchTerms.size()) {
        }

        topResultMatchProcessor.deactivateTopResultMatchTasks();

        LOG.info("Number of search terms: " + searchTerms.size());

        return topResultMatchProcessor.getMatchResults();
    }


    @Override
    public void run() {
        LOG.info("Begin generating report");
        this.filename = ReportService.getFilename(userReport.getName());
        final Map<String, SolrTopSearchResult> matchResults = getMatchResults();
        printReportService.writeTopResult(reportProperties, userReport, matchResults, searchTerms, filename);
    }

    public int getStatus() {
        if (searchTerms.size() > 0) {
            return matchResults.size() * 100 / searchTerms.size();
        }
        return 100;
    }

    public RelevancyReportParameters getUserReport() {
        return userReport;
    }

    public void setUserReport(RelevancyReportParameters userReport) {
        this.userReport = userReport;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<String> getSearchTerms() {
        return searchTerms;
    }

    public void setSearchTerms(List<String> searchTerms) {
        this.searchTerms = searchTerms;
    }
    
}
