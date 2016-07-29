package com.galaxyinternet.dao.chart;

import java.util.List;

import com.galaxyinternet.model.chart.SopCharts;

public interface SopProjectAnalysisDao {
	
	public List<SopCharts> selectOverView(SopCharts query);
	
	public Long selectCount(SopCharts query);
}
