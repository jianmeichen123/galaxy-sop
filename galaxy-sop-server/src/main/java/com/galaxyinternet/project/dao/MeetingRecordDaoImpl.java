package com.galaxyinternet.project.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.dao.project.MeetingRecordDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.utils.BeanUtils;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.model.project.MeetingRecord;


@Repository("meetingRecordDao")
public class MeetingRecordDaoImpl extends BaseDaoImpl<MeetingRecord, Long> implements MeetingRecordDao {


	@Override
	public Page<MeetingRecordBo> selectMeetPageList(MeetingRecordBo query, Pageable pageable) {
		try {
			List<MeetingRecordBo> contentList = sqlSessionTemplate.selectList(getSqlName("selectMeetPage") ,getParams(query, pageable));
			
			System.err.println("contentList==>>"+GSONUtil.toJson(contentList));
			
			return new Page<MeetingRecordBo>(contentList, pageable, selectMeetCount(query));
		} catch (Exception e) {
			throw new DaoException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName("selectMeetPage")), e);
		}
	}
	
	
	public Long selectMeetCount(MeetingRecordBo query) {
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return sqlSessionTemplate.selectOne(getSqlName("selectMeetCount"), params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象总数出错！语句：%s", getSqlName("selectMeetCount")), e);
		}
	}

}