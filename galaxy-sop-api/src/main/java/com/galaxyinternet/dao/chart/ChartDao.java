package com.galaxyinternet.dao.chart;

import java.util.List;
import java.util.Map;

import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.model.chart.Chart;

public interface ChartDao extends BaseDao<Chart, Long> {
	
	public List<Map<String,Object>> lineChart(Map<String,Object> params);
	public List<Map<String,Object>> lineHhrChart(Map<String,Object> params);	
	public List<Map<String,Object>> dataBriefChart(Map<String,Object> params);
	public List<Map<String,Object>> projectList(Map<String,Object> params); 
	public List<Map<String,Object>> userKpi(Map<String,Object> params); 
	public List<Map<String,Object>> deptKpi(Map<String,Object> params); 
	public List<Map<String,Object>> projectProgressChart(Map<String,Object> params); 
	public List<Map<String,Object>> meetingRate(Map<String,Object> params); 
	public List<Map<String,Object>> meetingRateUser(Map<String,Object> params); 
	public List<Map<String,Object>> rateRiseDChart(Map<String,Object> params); 
	public List<Map<String,Object>> rateRiseD(Map<String,Object> params); 
	public List<Map<String,Object>> rateRiseMChart(Map<String,Object> params); 
	public List<Map<String,Object>> rateRiseM(Map<String,Object> params); 
	public List<Map<String,Object>> rateRiseMonthChart(Map<String,Object> params); 
	public List<Map<String,Object>> platformMeetingScheduling(Map<String,Object> params); 
	public List<Map<String,Object>> meetingSchedList(Map<String,Object> params); 
	public List<Map<String,Object>> progressDurationList(Map<String,Object> params); 
	public List<Map<String,Object>> departmentList(Map<String,Object> params); 
	public List<Map<String,Object>> appBrief(Map<String,Object> params);
	public long scoreCEOMeeting(Map<String,Object> params);
	public long scoreCreateProject(Map<String,Object> params);
	public long countMeetingRate(Map<String, Object> params);
}
