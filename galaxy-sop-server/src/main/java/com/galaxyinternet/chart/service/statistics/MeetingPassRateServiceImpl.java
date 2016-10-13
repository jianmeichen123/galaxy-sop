package com.galaxyinternet.chart.service.statistics;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.chart.ChartDao;
import com.galaxyinternet.service.chart.statistics.MeetingPassRateService;

@Service("com.galaxyinternet.service.chart.statistics.MeetingPassRateService")
public class MeetingPassRateServiceImpl implements MeetingPassRateService {
	
	@Autowired
	private ChartDao chartDao;
	
	@Override
	public long scorePassCEOMeeting(long startDate, long endDate, long departmentId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("meetingType", "meetingType:2");
		params.put("departmentId", departmentId);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		//外部
		params.put("projectType", "projectType:1");
		long a = chartDao.scoreCEOMeeting(params);
		//内部
		params.put("projectType", "projectType:2");
		long b = chartDao.scoreCEOMeeting(params);
		
		return a*1 + b*5;
	}

	@Override
	public long scoreCreateProject(long startDate, long endDate, long departmentId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("departmentId", departmentId);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		
		long a = chartDao.scoreCEOMeeting(params);
		
		return a;
	}


}
