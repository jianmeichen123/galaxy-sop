package com.galaxyinternet.project.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.galaxyinternet.dao.project.MeetingSchedulingDao;
import com.galaxyinternet.framework.core.constants.SqlId;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.model.project.MeetingScheduling;


@Repository("meetingSchedulingDao")
public class MeetingSchedulingDaoImpl extends BaseDaoImpl<MeetingScheduling, Long> implements MeetingSchedulingDao {


	@Override
	public int updateCountBySelective(MeetingScheduling meetingScheduling){
		Assert.notNull(meetingScheduling);
		meetingScheduling.setUpdatedTime(new Date().getTime());
		try {
			return sqlSessionTemplate.update(getSqlName("updateCountBySelective"), meetingScheduling);
		} catch (Exception e) {
			throw new DaoException(String.format("根据ID更新对象某些属性出错！语句：%s", getSqlName("updateCountBySelective")),
					e);
		}
	}
	
	
	
}