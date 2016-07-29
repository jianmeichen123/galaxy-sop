package com.galaxyinternet.service.chart;

import java.util.List;

import com.galaxyinternet.model.chart.SopCharts;

public interface SopProjectAnalysisService {
	
	/**
	 * 项目总览图表查询
	 * @param query
	 * @return
	 */
	public List<SopCharts> queryProjectOverView(SopCharts query);
	
	/**
	 * 项目日期环比增长率
	 * @param query
	 * @return
	 */
	public List<SopCharts> queryRiseRate(SopCharts query);
}
