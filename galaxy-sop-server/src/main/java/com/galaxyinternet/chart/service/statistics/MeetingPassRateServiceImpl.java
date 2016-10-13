package com.galaxyinternet.chart.service.statistics;

import org.springframework.stereotype.Service;

import com.galaxyinternet.service.chart.statistics.MeetingPassRateService;

@Service("com.galaxyinternet.service.chart.statistics.MeetingPassRateService")
public class MeetingPassRateServiceImpl implements MeetingPassRateService {

	@Override
	public int scorePassCEOMeeting(String startDate, String endDate, long departmentId) throws Exception {
		return 0;
	}

	@Override
	public int scoreCreateProject(String startDate, String endDate, long departmentId) throws Exception {
		return 0;
	}


}
