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
}
