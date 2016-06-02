package com.galaxyinternet.soptask.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.galaxyinternet.bo.SopTaskBo;
import com.galaxyinternet.dao.soptask.SopTaskDao;
import com.galaxyinternet.framework.core.constants.SqlId;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.model.soptask.SopTask;

@Repository("sopTaskDao")
public class SopTastDaoImpl extends BaseDaoImpl<SopTask, Long>implements SopTaskDao {
	@Override
	public Page<SopTask> selectTaskInPids(SopTaskBo query,Pageable pageable) {
		Assert.notNull(query);
		try {
			List<SopTask> contentList= sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_SELECT), getParams(query, pageable));
			return new  Page<SopTask>(contentList, pageable, this.selectCount(query));
		} catch (Exception e) {
			throw new DaoException(String.format("根据pids查询：%s", getSqlName(SqlId.SQL_SELECT)), e);
		}
	}
	
	@Override
	public List<SopTask> selectForTaskOverList(SopTaskBo query) {
		try {
			//Map<String, Object> params = BeanUtils.toMap(query);
			return sqlSessionTemplate.selectList(getSqlName("selectForTaskOver"), query);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象列表出错！语句：%s", getSqlName("selectForTaskOver")), e);
		}
	}

	@Override
	public Long selectTotalMission(SopTaskBo query) {
		try {
			return sqlSessionTemplate.selectOne("selectTotalMission", query);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象总数！语句：%s",
					getSqlName("selectTotalMission")), e);
		}

	}

	@Override
	public Long selectTotalUrgent(SopTaskBo query) {
		try {
			return sqlSessionTemplate.selectOne("selectTotalUrgent", query);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象总数！语句：%s",
					getSqlName("selectTotalUrgent")), e);
		}
		
	}

	/**
	 * 根据任务FLAG进行查询
	 * @param query
	 * @return
	 */
	public List<SopTask> selectForTaskByFlag(SopTaskBo query) {
		this.sqlSessionTemplate.selectList(getSqlName("selectForTaskByFlag"), query);
		return null;
	}

	@Override
	public int updateTask(SopTask task) {
		return this.sqlSessionTemplate.update(getSqlName("updateTask"), task);
	}
	
	@Override
	public List<SopTask> getSopTaskByProjectId(SopTaskBo query) {
		try {
			return sqlSessionTemplate.selectList(getSqlName("selectTaskByProjectId"), query);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象列表出错！语句：%s", getSqlName("selectTaskByProjectId")), e);
		}
	}
}
