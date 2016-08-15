package com.galaxyinternet.chart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.chart.SopDataBriefingDao;
import com.galaxyinternet.model.chart.SopCharts;
import com.galaxyinternet.service.ConfigService;
import com.galaxyinternet.service.DictService;
import com.galaxyinternet.service.UserRoleService;
import com.galaxyinternet.service.UserService;
import com.galaxyinternet.service.chart.SopDataBriefingService;

@Service("dataBriefingService")
public class SopDataBriefingServiceImpl implements SopDataBriefingService {

	@Autowired
	private DictService dictService;
	@Autowired
	private SopDataBriefingDao dataBriefingDao;
	@Autowired
	private ConfigService configService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRoleService userRoleService;
	/**
	 * 项目目标追踪
	 * @param query
	 * @return
	 */
	@Override
	public SopCharts queryTargetTracking(SopCharts query,Long targetCount) {
		// TODO Auto-generated method stub
		//已完成目标数
		Long completedCount = dataBriefingDao.selectCompleteCount(query);
		
		if(completedCount.intValue() > targetCount.intValue()){
			query.setAboveCount(completedCount - targetCount);
		}else if(completedCount.intValue() == targetCount.intValue()){
			
		}else{
			query.setNotCompleteCount(targetCount - completedCount);
		}
		if(query.getStartTime()!=null){
			query.setProjectTime(new Long(query.getStartTime()));
		}
		query.setProjectCount(completedCount);
		query.setTargetCount(targetCount);
		return query;
	}
	
	

	/**
	 * 项目完成率
	 * @param query
	 * @return
	 */
	@Override
	public List<SopCharts> queryCountGroupDate(SopCharts query) {
		// TODO Auto-generated method stub
		List<SopCharts> chartsList = dataBriefingDao.selectCountGroupDate(query);
		return chartsList;
	}


	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
