package com.galaxyinternet.service.chart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.galaxyinternet.bo.chart.ChartDataBo;
import com.galaxyinternet.model.report.SopReportModal;
import com.galaxyinternet.service.ReportService;

public interface KpiGradeService extends ReportService<ChartDataBo> {
	public void download(HttpServletRequest request,
			HttpServletResponse response,SopReportModal modal) throws Exception;
}
