package com.galaxyinternet.project.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.galaxyinternet.bo.project.MeetingSchedulingBo;
import com.galaxyinternet.dao.project.MeetingSchedulingDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
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
	public List<MeetingSchedulingBo> selectTop5ProjectMeetingByType(
			String type) {
		try {
			return sqlSessionTemplate.selectList(getSqlName("selectTop5ProjectMeetingByType"),type);
		} catch (Exception e) {
			throw new DaoException(String.format("查询top5排期出错！语句：%s", getSqlName("selectTop5ProjectMeetingByType")),
					e);
		}
	}

	@Override
	public List<MeetingSchedulingBo> selectProjectMeetingByType(String type) {
		try {
			return sqlSessionTemplate.selectList(getSqlName("selectProjectMeetingByType"),type);
		} catch (Exception e) {
			throw new DaoException(String.format("查询排期出错！语句：%s", getSqlName("selectProjectMeetingByType")),
					e);
		}
	}


	@Override
	public int updateBySelective(MeetingScheduling ms) {
		return sqlSessionTemplate.update(getSqlName("updateBySelective"), ms);
	}

	/**
	 * 批量更新
	 */
	@Override
	public void updateBatch(List<MeetingScheduling> entityList) {
		
		if (entityList == null || entityList.isEmpty())
			return;
		for (MeetingScheduling entity : entityList) {
			this.updateBySelective(entity);
		}
	}
	@Override
	public Page<MeetingScheduling> getMeetingList(MeetingScheduling bo,PageRequest page) {
		// TODO Auto-generated method stub
		try {
			List<MeetingScheduling> list=sqlSessionTemplate.selectList(getSqlName("select"), getParams(bo, page));
			return new  Page<MeetingScheduling>(list, page, this.selectCount(bo));
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象出错！语句：%s", getSqlName("selectTotal")), e);
		}
	}

	@Override
	public List<MeetingScheduling> getMeetingListByIds(MeetingScheduling bo) {
		try {
			return sqlSessionTemplate.selectList(getSqlName("selectMeetingSchedulingId"),bo);
		} catch (Exception e) {
			throw new DaoException(String.format("查询排期出错！语句：%s", getSqlName("selectMeetingSchedulingId")),
					e);
		}
	}

	@Override
	public List<MeetingSchedulingBo> meetingListByCondition(MeetingScheduling ms) {
		// TODO Auto-generated method stub
		try {
			List<MeetingSchedulingBo> list=sqlSessionTemplate.selectList(getSqlName("selectShedule"),ms);
		    return list;
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象出错！语句：%s", getSqlName("selectShedule")), e);
		}
	}

	
	
	
	
	//====report
	
	@Override
	public Long getMeetingScheduling(MeetingSchedulingBo bo) {
		// TODO Auto-generated method stub
		try {
			return sqlSessionTemplate.selectOne(getSqlName("selectTotal"), bo);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象出错！语句：%s", getSqlName("selectTotal")), e);
		}
	}

	@Override
	public Page<MeetingScheduling> getMeetingListForReport(MeetingSchedulingBo bo,PageRequest page) {
		// TODO Auto-generated method stub
		try {
			List<MeetingScheduling> list=sqlSessionTemplate.selectList(getSqlName("selectListByCondition"), getParams(bo, page));
			return new  Page<MeetingScheduling>(list, page, this.getMeetingScheduling(bo));
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象出错！语句：%s", getSqlName("selectListByCondition")), e);
		}
	}

	
}