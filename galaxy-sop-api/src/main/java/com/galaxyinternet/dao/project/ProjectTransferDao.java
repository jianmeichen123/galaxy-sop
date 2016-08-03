package com.galaxyinternet.dao.project;

import java.util.List;

import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.model.project.ProjectTransfer;

public interface ProjectTransferDao extends BaseDao<ProjectTransfer, Long> {
	
	
	/**
	 * 根据项目ID查询出移交记录
	 * @param pid
	 * @return
	 */
	List<ProjectTransfer> selectApplyRecord(Long pid);
	
}