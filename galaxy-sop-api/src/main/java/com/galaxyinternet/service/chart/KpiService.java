package com.galaxyinternet.service.chart;


import com.galaxyinternet.bo.chart.ChartDataBo;
import com.galaxyinternet.common.query.ChartKpiQuery;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.Project;


public interface KpiService extends BaseService<ChartDataBo> {

	Page<ChartDataBo> userKpi(ChartKpiQuery query);

	Page<ChartDataBo> deptkpi(ChartKpiQuery query);
	
	Page<ChartDataBo> parterkpi(ChartKpiQuery query) throws Exception;

	Page<ChartDataBo> ggLineChart(ChartKpiQuery query);

	Page<ChartDataBo> hhrLineChart(ChartKpiQuery query);

	Page<ChartDataBo> deptMeetPassRate(ChartKpiQuery query) throws Exception;

	Page<ChartDataBo> tzjlMeetPassRate(ChartKpiQuery query) throws Exception;

	Page<ChartDataBo> deptProTarget(ChartKpiQuery query);

	Page<ChartDataBo> tzjlProTarget(ChartKpiQuery query);

	Page<Project> proNum_ProjectList(ChartKpiQuery query);

	Page<Project> meetRate_ProjectList(ChartKpiQuery query);

	Page<ChartDataBo> proTimeLine(ChartKpiQuery query);

	
	
	
	
}

