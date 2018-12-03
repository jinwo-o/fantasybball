package ca.homedepot.relevancy.services.report;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ca.homedepot.relevancy.model.ReportFile;

public interface ReportService {

    List<ReportFile> getReportFiles();
    File getReportFileByFileName(String filename);
    
    static String getFilename(final String prefix) {
	    final Date date = new Date();
	    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	    final String filename = prefix + "_" + dateFormat.format(date);
	    
	    return filename;
    }
}
