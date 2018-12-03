package ca.homedepot.relevancy.services.report;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.homedepot.relevancy.config.properties.ReportProperties;
import ca.homedepot.relevancy.model.ReportFile;

@Component
public class DefaultReportService implements ReportService {

    private final ReportProperties reportProperties;
            
    @Autowired
    public DefaultReportService(final ReportProperties reportProperties) {
    	this.reportProperties = reportProperties;
    }
                    
	@Override
	public List<ReportFile> getReportFiles() {
		final File folder = new File(reportProperties.getDirectoryFullPath());

		if (!(folder.exists() && folder.isDirectory())) {
			return Collections.emptyList();
		}
		
		final File[] files = folder.listFiles();
		
		final List<ReportFile> reportFileList = new ArrayList<>();
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		
		for (final File file : files) {
			final ReportFile reportFile = new ReportFile(file.getName(), FileUtils.byteCountToDisplaySize(file.length()), sdf.format(file.lastModified()));
			reportFileList.add(reportFile);
		}
		
		return reportFileList;
	}
	
	@Override
	public File getReportFileByFileName(final String fileName) {
		final File file = new File(reportProperties.getDirectoryFullPath() + fileName);
		return (file.exists() && file.isFile()) ? file : null;
	}
}
