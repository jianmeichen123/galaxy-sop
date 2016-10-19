package com.galaxyinternet.service;

import java.util.List;

import com.galaxyinternet.model.report.DataReport;
import com.galaxyinternet.model.report.SopReportModal;

public interface ReportService<T extends DataReport> {
	public SopReportModal createReport(List<T> dataSource,String tempFilePath) throws Exception;
}
