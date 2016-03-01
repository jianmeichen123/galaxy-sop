package com.galaxyinternet.service;

import java.util.List;

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
	public Page<SopTaskBo> tasklist(PageRequest pageable,SopTask sopTask);
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
	
}

