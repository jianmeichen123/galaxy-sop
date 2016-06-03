package com.galaxyinternet.dao.project;

import java.util.List;

import com.galaxyinternet.bo.project.MeetingSchedulingBo;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.model.project.MeetingScheduling;

public interface MeetingSchedulingDao extends BaseDao<MeetingScheduling, Long> {
	
	public int updateCountBySelective(MeetingScheduling meetingScheduling);
	
	/**
	 * 查询top5，按次数时间降序
	 * @author zhaoying
	 * @return
	 */
	List<MeetingSchedulingBo> selectTop5ProjectMeetingByType(String type);
	
	List<MeetingSchedulingBo> selectProjectMeetingByType(String type);
	
	int updateBySelective(MeetingScheduling ms);
	/**
	 * 批量更新
	 * @param entityList
	 */
	void updateBatch(List<MeetingScheduling> entityList);
	
	public Page<MeetingScheduling> getMeetingList(MeetingScheduling bo,PageRequest page);
	
	 /***
     * 根据id查询排期集合
     * @param ids
     * @return
     */
    public List<MeetingScheduling> getMeetingListByIds(MeetingScheduling bo);

}