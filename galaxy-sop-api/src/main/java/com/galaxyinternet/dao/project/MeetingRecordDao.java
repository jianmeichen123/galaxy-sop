package com.galaxyinternet.dao.project;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.model.project.MeetingRecord;

public interface MeetingRecordDao extends BaseDao<MeetingRecord, Long> {
	
	public Page<MeetingRecordBo> selectMeetPageList(MeetingRecordBo query, Pageable pageable);
	
	/**
	 * 根据项目及类型查询会议名称的序号
	 * @param query
	 * @return
	 */
	public Long selectMeetNumberByType(MeetingRecord query);

	public Long selectMeetCountByGHL(MeetingRecordBo mquery1);

	public List<MeetingRecord> selectUserPassMeetNum(MeetingRecordBo mquery1);

	public List<MeetingRecord> selectDeptPassMeetNum(MeetingRecordBo mquery1);


}