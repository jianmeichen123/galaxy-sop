package com.galaxyinternet.chart.service.statistics;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.chart.ChartDao;
import com.galaxyinternet.project.controller.ProjectController;
import com.galaxyinternet.service.chart.statistics.MeetingPassRateService;

@Service("com.galaxyinternet.service.chart.statistics.MeetingPassRateService")
public class MeetingPassRateServiceImpl implements MeetingPassRateService {
	
	private final static Logger logger = LoggerFactory.getLogger(ProjectController.class);
	
	@Autowired
	private ChartDao chartDao;
	
	@Override
	public long scoreCreateProject(long startDate, long endDate, long departmentId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("departmentId", departmentId);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		
		long a = chartDao.scoreCreateProject(params);
		logger.info("score by  create.project:[" + a + "]");
		return a;
	}
	
	@Override
	public long scorePassCEOMeeting(long startDate, long endDate, long departmentId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("meetingType", "meetingType:2");
		params.put("departmentId", departmentId);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		//投资
		params.put("projectType", "projectType:1");
		long a = chartDao.scoreCEOMeeting(params);
		//创建
		params.put("projectType", "projectType:2");
		long b = chartDao.scoreCEOMeeting(params);
		logger.info("score by  ceomeeting:[" + a + "*1" + b + "*5=" + a*1 + b*5 + "]");
		return a*1 + b*5;
	}

	@Override
	public long scorePassLXHMeeting(long startDate, long endDate, long departmentId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("meetingType", "meetingType:3");
		params.put("departmentId", departmentId);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		//投资
		params.put("projectType", "projectType:1");
		long a = chartDao.scoreCEOMeeting(params);
		//创建
		params.put("projectType", "projectType:2");
		long b = chartDao.scoreCEOMeeting(params);
		logger.info("score by lxhmeeting:[" + a + "*10" + b + "*20=" + a*10 + b*20 + "]");
		return a*10 + b*20;
	}

	@Override
	public double passCEOMeetingRate(long startDate, long endDate, long departmentId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("meetingType", "meetingType:2");
		params.put("departmentId", departmentId);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		long a = chartDao.countMeetingRate(params);
		params.put("meetingResult", "meetingResult:1");
		long b = chartDao.countMeetingRate(params);
		logger.info("score  ceo.meetiong.pass.rate:[" + b + "/" + a + "]");
		if(a > 0){
			return (double) b/a;
		}else{
			return 0;
		}
	}

	@Override
	public double passLXHMeetingRate(long startDate, long endDate, long departmentId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("meetingType", "meetingType:3");
		params.put("departmentId", departmentId);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		long a = chartDao.countMeetingRate(params);
		params.put("meetingResult", "meetingResult:1");
		long b = chartDao.countMeetingRate(params);
		logger.info("score  lxh.meeting.pass.rate:[" + b + "/" + a + "]");
		if(a > 0){
			return (double) b/a;
		}else{
			return 0;
		}
	}
	
}
