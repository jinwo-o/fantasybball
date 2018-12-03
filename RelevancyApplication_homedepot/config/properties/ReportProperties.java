package ca.homedepot.relevancy.config.properties;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import ca.homedepot.relevancy.model.ServerEnvironment;

@Component
@ConfigurationProperties("report")
public class ReportProperties {

	private String directory;
	private int httpPoolSize;
	private int relevancyProcessorThreadCount;
	private boolean relativePath;
	private ServerEnvironment[] environments;

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(final String directory) {
		this.directory = directory;
	}

	public String getDirectoryFullPath() {
		// Platform compatibility
		if (directory.contains("/")) {
			directory.replace("/", File.separator);
		}
		
		if (!relativePath) {
			return directory;
		}
		
		return System.getProperty("user.dir") + File.separator + directory;
	}

	public int getHttpPoolSize() {
		return httpPoolSize;
	}

	public void setHttpPoolSize(final int httpPoolSize) {
		this.httpPoolSize = httpPoolSize;
	}

	public int getRelevancyProcessorThreadCount() {
		return relevancyProcessorThreadCount;
	}

	public void setRelevancyProcessorThreadCount(final int relevancyProcessorThreadCount) {
		this.relevancyProcessorThreadCount = relevancyProcessorThreadCount;
	}

	public boolean isRelativePath() {
		return relativePath;
	}

	public void setRelativePath(final boolean relativePath) {
		this.relativePath = relativePath;
	}

	public ServerEnvironment[] getEnvironments() {
		return environments;
	}

	public void setEnvironments(final ServerEnvironment[] environments) {
		this.environments = environments;
	}
}
