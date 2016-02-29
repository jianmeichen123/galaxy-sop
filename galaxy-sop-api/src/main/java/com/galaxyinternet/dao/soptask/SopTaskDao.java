package com.galaxyinternet.dao.soptask;

import org.springframework.data.domain.Pageable;

import com.galaxyinternet.bo.SopTaskBo;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.model.soptask.SopTask;

public interface SopTaskDao extends BaseDao<SopTask, Long> {
	/**
	    * 根据查询任务
	    * @return
	    */
	public Page<SopTask> selectTaskInPids(SopTaskBo query,Pageable pageable);
}