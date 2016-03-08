package com.galaxyinternet.project.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.galaxyinternet.bo.project.MeetingSchedulingBo;
import com.galaxyinternet.dao.project.MeetingSchedulingDao;
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
			throw new DaoException(String.format("更新排期池对象某些属性出错！语句：%s", getSqlName("updateCountBySelective")),
					e);
		}
	}

	@Override
	public List<MeetingSchedulingBo> selectTop5ProjectMeeting() {
		try {
			return sqlSessionTemplate.selectList(getSqlName("selectTop5ProjectMeeting"));
		} catch (Exception e) {
			throw new DaoException(String.format("查询top5立项会排期出错！语句：%s", getSqlName("selectTop5ProjectMeeting")),
					e);
		}
	}
	
	
	
}