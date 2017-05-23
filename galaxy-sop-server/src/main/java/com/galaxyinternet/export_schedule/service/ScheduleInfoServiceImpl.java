package com.galaxyinternet.export_schedule.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.export_schedule.dao.ScheduleInfoDao;
import com.galaxyinternet.export_schedule.model.ScheduleInfo;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.framework.core.utils.DateUtil;

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
		String visitRate = "0.00%";
		//计划拜访量
		long visitCount = scheduleInfoDao.getVisitCount(info);
		//已完成拜访量
		long completedVisitCount = scheduleInfoDao.getCompletedVisit(info);
		//访谈完成率
		if(visitCount != 0 && completedVisitCount != 0 ){
			double k = (double)completedVisitCount/visitCount*100;
			java.math.BigDecimal   big   =   new   java.math.BigDecimal(k);  
			visitRate = big.setScale(2,java.math.BigDecimal.ROUND_HALF_UP).doubleValue() +"%";
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
		// TODO Auto-generated method stub
		List<ScheduleInfo> list = scheduleInfoDao.getVisitFanceStatus(info);
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
	    //非项目拜访
	    long isNoVisit = isAllProVisit - isProVisit;
	    Map<String,Object> map = new HashMap<String,Object>();
		    map.put("isProVisit", isProVisit);
		    map.put("isNoVisit", isNoVisit);
		return map;
	}

	@Override
	public Map<String, Object> getRecordVisit(ScheduleInfo info) {
		// TODO Auto-generated method stub
		//拜访记录
		long all = scheduleInfoDao.getAllRecordVisitCount(info);
		//未缺失的拜访记录
	    long part = scheduleInfoDao.getRecordVisitCount(info);
	    //已缺失的拜访
	    long nopart = all - part;
	    Map<String,Object> map = new HashMap<String,Object>();
		    map.put("part", part);
		    map.put("nopart", nopart);
		return map;
	}

	@Override
	public List<Map<String,Object>> selectTendency(ScheduleInfo info) {
		List<Map<String,Object>> result = getEmptyResult(info.getPeriodType(), info.getStartTimeFrom(), info.getStartTimeThrough());
		
		List<Map<String,Object>> actualList = scheduleInfoDao.selectTendency(info);
		for(Map<String,Object> actItem : actualList)
		{
			Object actKey = actItem.get("period");
			for(Map<String,Object> item : result)
			{
				Object key = item.get("period");
				if(key.equals(actKey))
				{
					item.put("count", actItem.get("count"));
				}
			}
		}
		return result;
	}
	/**
	 * 生成日期区间
	 * @param periodType
	 * @param from
	 * @param through
	 * @return
	 */
	private List<Map<String,Object>> getEmptyResult(Byte periodType, Date from, Date through)
	{
		if(3 == periodType.intValue())
		{
			return getWeeklyResult(from, through);
		}
		else if(2==periodType.intValue())
		{
			return getMonthlyResult(from, through);
		}
		return getQuarterlyResult(from, through);
	}
	private List<Map<String,Object>> getQuarterlyResult(Date from, Date through)
	{
		List<Map<String,Object>> list = new ArrayList<>();
		
		Calendar fromCal = Calendar.getInstance();
		fromCal.setTime(from);
		Calendar throughCal = Calendar.getInstance();
		throughCal.setTime(through);
		
		int quarter = DateUtil.getQuarterly(from);
		int preQuarter = 0;
		
		while(fromCal.before(throughCal))
		{
			if(quarter != preQuarter)
			{
				String period = fromCal.get(Calendar.YEAR)+"年Q"+quarter;
				Map<String,Object> item = new HashMap<>();
				item.put("period", period);
				item.put("count", 0);
				list.add(item);
			}
			preQuarter = quarter;
			fromCal.add(Calendar.MONTH, 1);
			quarter = DateUtil.getQuarterly(fromCal.getTime());
		}
		
		return list;
	}
	
	private List<Map<String,Object>> getMonthlyResult(Date from, Date through)
	{
		List<Map<String,Object>> list = new ArrayList<>();
		
		Calendar fromCal = Calendar.getInstance();
		fromCal.setTime(from);
		Calendar throughCal = Calendar.getInstance();
		throughCal.setTime(through);
		
		
		while(fromCal.before(throughCal))
		{
			String period = fromCal.get(Calendar.YEAR)+"-"+(fromCal.get(Calendar.MONTH)+1);
			Map<String,Object> item = new HashMap<>();
			item.put("period", period);
			item.put("count", 0);
			list.add(item);
			fromCal.add(Calendar.MONTH, 1);
		}
		
		return list;
	}
	
	private List<Map<String,Object>> getWeeklyResult(Date from, Date through)
	{
		List<Map<String,Object>> list = new ArrayList<>();
		
		Calendar fromCal = Calendar.getInstance();
		fromCal.setTime(from);
		Calendar throughCal = Calendar.getInstance();
		throughCal.setTime(through);
		
		while(fromCal.before(throughCal))
		{
			String period = fromCal.get(Calendar.YEAR)+"/"+(fromCal.get(Calendar.MONTH)+1)+"/"+fromCal.get(Calendar.DAY_OF_MONTH);
			fromCal.add(Calendar.DAY_OF_YEAR, 6);
			period += "-"+fromCal.get(Calendar.YEAR)+"/"+(fromCal.get(Calendar.MONTH)+1)+"/"+fromCal.get(Calendar.DAY_OF_MONTH);
			Map<String,Object> item = new HashMap<>();
			item.put("period", period);
			item.put("count", 0);
			list.add(item);
			fromCal.add(Calendar.DAY_OF_YEAR, 1);
		}
		
		return list;
	}
	

	
}
