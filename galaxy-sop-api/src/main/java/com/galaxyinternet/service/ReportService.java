package com.galaxyinternet.service;

import java.util.List;

import com.galaxyinternet.model.report.DataReport;

public interface ReportService<T extends DataReport> {
	public String createReport(List<T> dataSource) throws Exception;
}
