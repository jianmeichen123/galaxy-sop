package com.galaxyinternet.chart.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.chart.BaseChartDaoImpl;
import com.galaxyinternet.dao.chart.SopProjectAnalysisDao;
import com.galaxyinternet.model.chart.SopProjectAnalysis;

@Repository(value="overViewDao")
public class SopProjectAnalysisDaoImpl extends BaseChartDaoImpl<SopProjectAnalysis> implements SopProjectAnalysisDao {

	@Override
	public List<SopProjectAnalysis> selectOverView(SopProjectAnalysis query) {
		// TODO Auto-generated method stub
		return selectListBySqlName("selectOverView", query);
	}

	@Override
	public Long selectCount(SopProjectAnalysis query) {
		// TODO Auto-generated method stub
		return selectCountBySqlName("selectCount",query);
	}
}
