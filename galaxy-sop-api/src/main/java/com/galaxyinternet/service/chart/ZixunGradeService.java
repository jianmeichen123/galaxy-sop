package com.galaxyinternet.service.chart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.galaxyinternet.model.chart.ZixunData;
import com.galaxyinternet.model.report.SopReportModal;
import com.galaxyinternet.service.ReportService;

public interface ZixunGradeService extends ReportService<ZixunData> {
	public void download(HttpServletRequest request,
			HttpServletResponse response,SopReportModal modal) throws Exception;
}
