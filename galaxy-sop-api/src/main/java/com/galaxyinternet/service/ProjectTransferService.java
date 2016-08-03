package com.galaxyinternet.service;

import java.util.List;

import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.project.ProjectTransfer;

/**
 * @author chenjianmei
 */
public interface ProjectTransferService extends BaseService<ProjectTransfer> {
	
	List<ProjectTransfer> applyTransferData(Long pid);
	
	/**
	 * 项目移交申请
	 * 1.生成移交记录
	 * 2.生成待办任务
	 * @param projectTransfer
	 */
	void applyProjectTransfer(ProjectTransfer projectTransfer);
	
	/**
	 * 撤销项目移交
	 * 1.修改项目移交记录
	 * 2.修改待办任务
	 */
	void undoProjectTransfer(ProjectTransfer projectTransfer);
	
	
	void setTransferProjectInRedis(Cache cache, Long pid);
	
	void removeTransferProjectFromRedis(Cache cache, Long pid);
	
	
	
}

