package com.galaxyinternet.dao.chart;

import java.util.List;

import com.galaxyinternet.model.chart.SopProjectAnalysis;

public interface SopProjectAnalysisDao {
	
	public List<SopProjectAnalysis> selectOverView(SopProjectAnalysis query);
	
	public Long selectCount(SopProjectAnalysis query);
}
