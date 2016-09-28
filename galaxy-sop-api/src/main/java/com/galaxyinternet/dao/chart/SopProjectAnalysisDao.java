package com.galaxyinternet.dao.chart;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.model.chart.SopCharts;

public interface SopProjectAnalysisDao {
	
	public List<SopCharts> selectOverView(SopCharts query);
	
	public Long selectCount(SopCharts query);
	
	/**
	 * 根据所有查询字段分组查询数量
	 * @param query
	 * @return
	 */
	public Page<SopCharts> selectCountGroupAll(SopCharts query,Pageable pageable);
	
	
	public List<SopCharts> selectInvestmentGroupDate(SopCharts query);
	
	public List<SopCharts> searchPostAnalysis(SopCharts query);
	public List<SopCharts> searchPostAnalysisByHhr(SopCharts query);
	
}
