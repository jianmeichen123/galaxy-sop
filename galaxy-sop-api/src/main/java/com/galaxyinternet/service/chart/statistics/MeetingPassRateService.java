package com.galaxyinternet.service.chart.statistics;

public interface MeetingPassRateService {
	
	
	/**
	 *  分数/通过CEO评审
	 */
	long scorePassCEOMeeting(long startDate, long endDate, long departmentId) throws Exception;
	/**
	 *  分数/生成项目
	 */
	long scoreCreateProject(long startDate, long endDate, long departmentId) throws Exception;

}
