package com.galaxyinternet.touhou.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.touhou.OperationalDataDao;
import com.galaxyinternet.framework.core.constants.SqlId;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.model.touhou.OperationalData;

@Repository("operationalDataDao")
public class OperationalDataDaoImpl extends BaseDaoImpl<OperationalData, Long> implements OperationalDataDao{


	@Override
	public Page<OperationalData> selectOperationalDataPageList(
			OperationalData query, PageRequest pageRequest) {
		try {
			
			List<OperationalData> contentList = sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_SELECT),getParams(query, pageRequest));
			return new Page<OperationalData>(contentList, pageRequest, this.selectCount(query));
		} catch (Exception e) {
			throw new DaoException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(SqlId.SQL_SELECT)), e);
		}
	}

}
