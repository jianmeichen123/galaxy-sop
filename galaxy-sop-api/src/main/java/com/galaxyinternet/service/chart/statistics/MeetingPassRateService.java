package com.galaxyinternet.service.chart.statistics;

public interface MeetingPassRateService {
	
	
	/**
	 *  分数/通过CEO评审
	 */
	int scorePassCEOMeeting(String startDate, String endDate, long departmentId) throws Exception;
	/**
	 *  分数/生成项目
	 */
	int scoreCreateProject(String startDate, String endDate, long departmentId) throws Exception;

}
