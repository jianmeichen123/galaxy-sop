package com.galaxyinternet.project.service;

import java.util.Map;
import com.galaxyinternet.project.service.handler.Handler;

public class HandlerManager {
	
	//项目阶段中的文档记录处理器集合
	private Map<String,Handler> stageHandlers;

	public Map<String, Handler> getStageHandlers() {
		return stageHandlers;
	}

	public void setStageHandlers(Map<String, Handler> stageHandlers) {
		this.stageHandlers = stageHandlers;
	}
}
