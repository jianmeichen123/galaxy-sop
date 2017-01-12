package com.galaxyinternet.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.galaxyinternet.framework.core.model.PagableEntity;
import com.galaxyinternet.model.chart.DataFormat;
import com.galaxyinternet.model.report.SopReportModal;

public interface ReportService<T extends PagableEntity> {
	public SopReportModal createReport(DataFormat<T> dataSource,String templatePath,String tempFilePath,String suffix) throws Exception;
	public void download(HttpServletRequest request,HttpServletResponse response,SopReportModal modal) throws Exception;
}
