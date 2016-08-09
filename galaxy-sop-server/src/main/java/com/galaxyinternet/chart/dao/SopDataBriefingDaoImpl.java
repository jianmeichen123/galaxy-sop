package com.galaxyinternet.chart.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.chart.SopDataBriefingDao;
import com.galaxyinternet.model.chart.SopCharts;

@Repository(value="dataBriefingDao")
public class SopDataBriefingDaoImpl extends BaseChartDaoImpl<SopCharts> implements SopDataBriefingDao {
	
	@Override
	public Long selectCompleteCount(SopCharts query) {
		// TODO Auto-generated method stub
		return selectCountBySqlName("selectCompleteCount",query);
	}

	@Override
	public List<SopCharts> selectCountGroupDate(SopCharts query) {
		// TODO Auto-generated method stub
		return selectListBySqlName("selectCountGroupDate", query);
	}
	
	
	
	
	
	
}
