package com.galaxyinternet.service.chart;

import java.util.List;
import java.util.Map;

import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.chart.Chart;
import com.galaxyinternet.model.project.Project;
/**
 * @author wangkun
 */
public interface ChartService extends BaseService<Chart> {
	
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
	public List<Map<String, Object>> ggLineChart(Map<String, Object> params) throws Exception;
	public List<Map<String, Object>> lineHhrChart1(Project ps,PageRequest pr);
}

