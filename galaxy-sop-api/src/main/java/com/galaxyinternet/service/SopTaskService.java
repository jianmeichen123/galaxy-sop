package com.galaxyinternet.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.galaxyinternet.bo.SopTaskBo;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.soptask.SopTask;

/**
 * @author keifer
 */
public interface SopTaskService extends BaseService<SopTask> {
	/**
	 * @author chenjianmei
	 * @category 
	 * @return
	 */
	public Page<SopTaskBo> tasklist(PageRequest pageable,SopTaskBo sopTaskBo,HttpServletRequest request);
	/**
	 * @author chenjianmei
	 * @category 生成任务
	 * @return
	 */
	public Long insertsopTask(SopTask entity) ;
	/**
	 * @author chenjianmei
	 * @category 修改任务状态
	 * @return
	 */
	public int updateById(SopTask entity) ;
	
	/**
	 * @author zf
	 * @category 由任务状态查询任务
	 * @return
	 */
	public List<SopTask> selectForTaskOverList(SopTaskBo query);
	
	/**
	 * 待办任务总数
	 * @author zhaoying
	 * @param query
	 * @return
	 */
    Long selectTotalMission(SopTaskBo query);
	
    /**
     * 紧急任务总数
     * @author zhaoying
     * @param query
     * @return
     */
	Long selectTotalUrgent(SopTaskBo query);
	
	public int updateTask(SopTask task);
	/**
	 * 根据文件信息查询任务
	 * @param id 传入sop_file.id；
	 * @return
	 */
	public SopTask getByFileInfo(Long id);
	
	
	
}

