package com.galaxyinternet.chart.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.constants.MSqlId;
import com.galaxyinternet.dao.chart.ChartDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.chart.Chart;

@Repository("ChartDao")
public class ChartDaoImpl extends BaseDaoImpl<Chart, Long>implements ChartDao {

	/*@Override
	public List<CareerlineChart> careerlineChart(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(getSqlName(MSqlId.SQL_CHART_CAREERLINE), params);
	}*/

	@Override
	public List<Map<String,Object>> lineChart(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(getSqlName(MSqlId.SQL_CHART_LINE), params);
	}

	@Override
	public List<Map<String,Object>> dataBriefChart(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(getSqlName(MSqlId.SQL_CHART_DATA_BRIEF), params);
	}

	@Override
	public List<Map<String, Object>> projectList(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(getSqlName(MSqlId.SQL_PROJECT_LIST), params);
	}

	@Override
	public List<Map<String, Object>> userKpi(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(getSqlName(MSqlId.SQL_USER_KPI), params);
	}

	@Override
	public List<Map<String, Object>> deptKpi(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(getSqlName(MSqlId.SQL_DEPT_KPI), params);
	}

	@Override
	public List<Map<String, Object>> projectProgressChart(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(getSqlName(MSqlId.SQL_PROJECT_PROGRESS_CHART), params);
	}

	@Override
	public List<Map<String, Object>> meetingRate(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(getSqlName(MSqlId.SQL_MEETING_RATE), params);
	}

	@Override
	public List<Map<String, Object>> rateRiseDChart(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(getSqlName(MSqlId.SQL_Rate_Rise_D_Chart), params);
	}

	@Override
	public List<Map<String, Object>> rateRiseD(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(getSqlName(MSqlId.SQL_Rate_Rise_D), params);
	}

	@Override
	public List<Map<String, Object>> rateRiseMChart(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(getSqlName(MSqlId.SQL_Rate_Rise_M_Chart), params);
	}

	@Override
	public List<Map<String, Object>> rateRiseM(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(getSqlName(MSqlId.SQL_Rate_Rise_M), params);
	}

	@Override
	public List<Map<String, Object>> rateRiseMonthChart(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(getSqlName(MSqlId.SQL_RATE_RISE_MONTH_CHART), params);
	}

	@Override
	public List<Map<String, Object>> platformMeetingScheduling(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(getSqlName(MSqlId.SQL_PLATFORM_MEETING_SCHEDULING), params);
	}

	@Override
	public List<Map<String, Object>> meetingSchedList(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(getSqlName(MSqlId.SQL_MEETINGSCHEDLIST), params);
	}

	@Override
	public List<Map<String, Object>> progressDurationList(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(getSqlName(MSqlId.SQL_PROGRESS_DURATION_LIST), params);
	}

	@Override
	public List<Map<String, Object>> departmentList(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(getSqlName(MSqlId.SQL_DEPARTMENT_LIST), params);
	}

	@Override
	public List<Map<String, Object>> meetingRateUser(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(getSqlName(MSqlId.SQL_MEETING_RATE_USER), params);
	}

	@Override
	public List<Map<String, Object>> lineHhrChart(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(getSqlName(MSqlId.SQL_CHART_LINE_HHR), params);
	}

	@Override
	public List<Map<String, Object>> appBrief(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(getSqlName(MSqlId.SQL_APP_BRIEF), params);
	}

	@Override
	public long scoreCEOMeeting(Map<String, Object> params) {
		return sqlSessionTemplate.selectOne(getSqlName("scoreCEOMeeting"), params);
	}

	@Override
	public long scoreCreateProject(Map<String, Object> params) {
		return sqlSessionTemplate.selectOne(getSqlName("scoreCreateProject"), params);
	}
	
	@Override
	public long countMeetingRate(Map<String, Object> params) {
		return sqlSessionTemplate.selectOne(getSqlName("countMeetingRate"), params);
	}
	
}
