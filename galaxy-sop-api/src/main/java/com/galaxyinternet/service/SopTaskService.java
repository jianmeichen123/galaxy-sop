package com.galaxyinternet.service;

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
}

