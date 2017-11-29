package com.galaxyinternet.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.galaxyinternet.bo.SopTaskBo;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.PersonPool;
import com.galaxyinternet.model.soptask.SopTask;

/**
 * @author keifer
 */
public interface SopTaskService extends BaseService<SopTask> {
	
	/**
	 * 提交完成文件上传任务
	 */
	public int submitTask(SopTask task) throws Exception;
	
	/**
	 * 完善简历待办任务的生成
	 */
	public void toSureMsgForPerson(Long pid, List<PersonPool> list) throws Exception;
	
	
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
	
	/**
	 * 根据项目id查询任务
	 * @param query
	 * @return
	 */
	public List<SopTask> getSopTaskByProjectId(SopTaskBo query);
	
}

