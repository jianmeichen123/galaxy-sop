package com.galaxyinternet.chart.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.chart.SopProjectAnalysisDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.model.chart.SopCharts;

@Repository(value="analysisDao")
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

	@Override
	public Page<SopCharts> selectCountGroupAll(SopCharts query,Pageable pageable) {
		// TODO Auto-generated method stub
		List<SopCharts> contentList = sqlSessionTemplate.selectList(getSqlName("selectCountGroupAll"), getParams(query, pageable));
		Long total = selectCountBySqlName("selectCountGroupAllCount", query);
		return new Page<SopCharts>(contentList, pageable, total);
	}

	@Override
	public List<SopCharts> selectInvestmentGroupDate(SopCharts query) {
		// TODO Auto-generated method stub
		return selectListBySqlName("selectInvestmentGroupDate", query);
	}
	
	@Override
	public List<SopCharts> searchPostAnalysis(SopCharts query){
		return selectListBySqlName("searchPostAnalysis", query);
	}
	
	public List<SopCharts> searchPostAnalysisByHhr(SopCharts query){
		return selectListBySqlName("searchPostAnalysisByHhr", query); 
	}
	
	
	
}
