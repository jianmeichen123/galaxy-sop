package com.galaxyinternet.dao.chart;

import java.util.List;

import com.galaxyinternet.model.chart.SopCharts;


public interface SopDataBriefingDao {	
	
	/**
	 * 查询已完成目标数 
	 * @param query
	 * @return
	 */
	public Long selectCompleteCount(SopCharts query);
	
	/**
	 * 查询目标数量
	 * @param query
	 * @return
	 */
	public List<SopCharts> selectCountGroupDate(SopCharts query);
}
