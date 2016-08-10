package com.galaxyinternet.service.chart;

import java.util.List;

import com.galaxyinternet.model.chart.SopCharts;

public interface SopDataBriefingService {
	
	/**
	 * 项目目标追踪
	 * @param query
	 * @return
	 */
	public SopCharts queryTargetTracking(SopCharts query,Long targetCount);
	
	/**
	 * 项目完成率
	 * @param query
	 * @return
	 */
	public List<SopCharts> queryCountGroupDate(SopCharts query);
	
	
	
}
