package com.galaxyinternet.dao.project;

import org.springframework.data.domain.Pageable;

import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.model.project.MeetingRecord;

public interface MeetingRecordDao extends BaseDao<MeetingRecord, Long> {
	
	public Page<MeetingRecordBo> selectMeetPageList(MeetingRecordBo query, Pageable pageable);

}