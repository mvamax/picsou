package io.picsou.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="picsou.report")
public class ReportConfiguration {

	public String folderReportOut;
	
	public String folderReportResources;

	public String getFolderReportOut() {
		return folderReportOut;
	}

	public void setFolderReportOut(String folderReport) {
		this.folderReportOut = folderReport;
	}

	public String getFolderReportResources() {
		return folderReportResources;
	}

	public void setFolderReportResources(String folderReportResources) {
		this.folderReportResources = folderReportResources;
	}
	
	
}
