package com.galaxyinternet.service.chart;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.galaxyinternet.framework.core.model.Page;
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
	
	/**
	 * 项目日期，事业部，类型，投资经理分组环比增长率
	 * @param query
	 * @return
	 */
	public Page<SopCharts> queryRiseRateGroup(SopCharts query,Pageable pageable);
	
	
	public List<SopCharts> queryInvestmentGroupDate(SopCharts query);
	
	
	public List<SopCharts> queryPostAnalysis(SopCharts query);
}
