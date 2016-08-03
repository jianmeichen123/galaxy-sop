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
	
	
	void applyProjectTransfer(ProjectTransfer projectTransfer);
	
	
	void setTransferProjectInRedis(Cache cache,Project project);
	
}

