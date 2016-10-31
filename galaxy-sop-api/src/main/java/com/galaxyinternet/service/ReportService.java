package com.galaxyinternet.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.galaxyinternet.model.report.DataReport;
import com.galaxyinternet.model.report.SopReportModal;

public interface ReportService<T extends DataReport> {
	public SopReportModal createReport(List<T> dataSource,String templatePath,String tempFilePath,String suffix) throws Exception;
	public void download(HttpServletRequest request,HttpServletResponse response,SopReportModal modal) throws Exception;
}
