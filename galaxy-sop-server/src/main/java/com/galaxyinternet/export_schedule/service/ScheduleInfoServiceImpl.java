package com.galaxyinternet.export_schedule.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.export_schedule.dao.ScheduleInfoDao;
import com.galaxyinternet.export_schedule.model.ScheduleInfo;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;

@Service("scheduleInfoService")
public class ScheduleInfoServiceImpl extends BaseServiceImpl<ScheduleInfo> implements ScheduleInfoService{

	@Autowired
	private ScheduleInfoDao scheduleInfoDao;
	
	@Override
	protected BaseDao<ScheduleInfo, Long> getBaseDao() {
		// TODO Auto-generated method stub
		return this.scheduleInfoDao;
	}

	/**
	 * 拜访统计
	 */
	@Override
	public Map<String, Object> getVisitStatistics(ScheduleInfo info) {
		// TODO Auto-generated method stub
		double visitRate = 0;
		//计划拜访量
		long visitCount = scheduleInfoDao.getVisitCount(info);
		//已完成拜访量
		long completedVisitCount = scheduleInfoDao.getCompletedVisit(info);
		//访谈完成率
		if(visitCount != 0 && completedVisitCount != 0 ){
			visitRate = Math.round(completedVisitCount/visitCount)/100.0;
		}
		Map<String,Object> visitMap = new HashMap<String,Object>();
		visitMap.put("visitCount", visitCount);
		visitMap.put("completedVisitCount", completedVisitCount);
		visitMap.put("visitRate", visitRate);
		return visitMap;
	}

	/**
	 * 融资轮次占比
	 */
	@Override
	public List<ScheduleInfo> getVisitFanceStatus(ScheduleInfo info) {
		//计划拜访量
		long visitCount = scheduleInfoDao.getVisitCount(info);
		// TODO Auto-generated method stub
		List<ScheduleInfo> list = scheduleInfoDao.getVisitFanceStatus(info);
		if(list != null && list.size() > 0){
			for(ScheduleInfo sf : list){
				double count = sf.getCountVisit();
				if(visitCount != 0){
					sf.setFanceRate(Math.round(count/visitCount)/100.0);
				}
			}
		}
		return list;
	}
    /**
     * 项目占比图
     */
	@Override
	public Map<String, Object> getProjectVisit(ScheduleInfo info) {
		// TODO Auto-generated method stub
		//项目拜访
		long isAllProVisit = scheduleInfoDao.getVisitCount(info);
		//项目拜访
	    info.setIsProject(1);
	    long isProVisit = scheduleInfoDao.getVisitCount(info);
	    double isProRate = 0;
	    double isNoProRate = 0;
	    if(isAllProVisit != 0){
	    	isProRate = Math.round(isProVisit/isAllProVisit)/100.0;
	    }
	    //非项目拜访
	    long isNoVisit = isAllProVisit - isProVisit;
	    if(isAllProVisit != 0){
	    	isNoProRate = Math.round(isNoVisit/isAllProVisit)/100.0;
	    }
	    Map<String,Object> map = new HashMap<String,Object>();
	    map.put("isAllProVisit", isAllProVisit);
	    map.put("isProVisit", isProVisit);
	    map.put("isProVisitRate", isProRate);
	    map.put("isNoVisit", isNoVisit);
	    map.put("isNoProRate", isNoProRate);
		return map;
	}

	
}
