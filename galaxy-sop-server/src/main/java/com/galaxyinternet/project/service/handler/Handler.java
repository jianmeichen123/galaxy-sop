package com.galaxyinternet.project.service.handler;

import com.galaxyinternet.common.ViewQuery;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.model.project.Project;

public interface Handler {
	
	/**
	 * 处理业务
	 */
	public Result handler(ViewQuery query, Project project) throws Exception;

}
