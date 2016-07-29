package com.galaxyinternet.chart.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.chart.SopProjectAnalysisDao;
import com.galaxyinternet.model.chart.SopCharts;

@Repository(value="overViewDao")
public class SopProjectAnalysisDaoImpl extends BaseChartDaoImpl<SopCharts> implements SopProjectAnalysisDao {

	@Override
	public List<SopCharts> selectOverView(SopCharts query) {
		// TODO Auto-generated method stub
		return selectListBySqlName("selectOverView", query);
	}

	@Override
	public Long selectCount(SopCharts query) {
		// TODO Auto-generated method stub
		return selectCountBySqlName("selectCount",query);
	}
}
