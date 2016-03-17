package com.galaxyinternet.project.service.handler;

import com.galaxyinternet.common.SopResult;
import com.galaxyinternet.common.ViewQuery;
import com.galaxyinternet.model.project.Project;

public interface Handler {
	
	/**
	 * 处理业务
	 */
	public SopResult handler(ViewQuery query, Project project) throws Exception;

}
