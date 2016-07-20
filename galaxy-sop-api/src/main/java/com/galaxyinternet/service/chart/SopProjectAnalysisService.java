package com.galaxyinternet.service.chart;

import java.util.List;

import com.galaxyinternet.model.chart.SopProjectAnalysis;

public interface SopProjectAnalysisService {
	
	/**
	 * 项目总览图表查询
	 * @param query
	 * @return
	 */
	public List<SopProjectAnalysis> queryProjectOverView(SopProjectAnalysis query);
}
