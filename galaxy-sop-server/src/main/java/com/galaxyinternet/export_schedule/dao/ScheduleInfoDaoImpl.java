package com.galaxyinternet.export_schedule.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.export_schedule.model.ScheduleInfo;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.query.Query;
import com.galaxyinternet.framework.core.utils.BeanUtils;

@Repository("scheduleInfoDao")
public class ScheduleInfoDaoImpl extends BaseDaoImpl<ScheduleInfo, Long> implements ScheduleInfoDao{

	/**
	 * 计划拜访量
	 */
	@Override
	public Long getVisitCount(ScheduleInfo info) {
		try {
			Map<String, Object> params = BeanUtils.toMap(info);
			return sqlSessionTemplate.selectOne(getSqlName("selectVisitCount"), params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象总数出错！语句：%s", getSqlName("selectVisitCount")), e);
		}
	}

    /**
     * 已完成拜访量
     */
	@Override
	public Long getCompletedVisit(ScheduleInfo info) {
		try {
			Map<String, Object> params = BeanUtils.toMap(info);
			return sqlSessionTemplate.selectOne(getSqlName("selectVisitCompletedCount"), params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象总数出错！语句：%s", getSqlName("selectVisitCompletedCount")), e);
		}
	}


	/**
	 * 融资轮次占比
	 */
	@Override
	public List<ScheduleInfo> getVisitFanceStatus(ScheduleInfo info) {
		// TODO Auto-generated method stub
		try {
			Map<String, Object> params = BeanUtils.toMap(info);
			List<ScheduleInfo> list = sqlSessionTemplate.selectList(getSqlName("selectVisitFanceStatusCount"), params);
		    return list;
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象总数出错！语句：%s", getSqlName("selectVisitFanceStatusCount")), e);
		}
	}

	@Override
	public Long getAllRecordVisitCount(ScheduleInfo info) {
		try {
			Map<String, Object> params = BeanUtils.toMap(info);
			return sqlSessionTemplate.selectOne(getSqlName("selectAllVisitCount"), params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象总数出错！语句：%s", getSqlName("selectAllVisitCount")), e);
		}
	}

	@Override
	public Long getRecordVisitCount(ScheduleInfo info) {
		try {
			Map<String, Object> params = BeanUtils.toMap(info);
			return sqlSessionTemplate.selectOne(getSqlName("selectInterviewRecordCount"), params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象总数出错！语句：%s", getSqlName("selectInterviewRecordCount")), e);
		}
	}

	
}
