package com.galaxyinternet.project_process.event.handlers;

import com.galaxyinternet.project_process.event.ProgressChangeEvent;

public interface ProgressChangeHandler
{
	boolean support(ProgressChangeEvent event);
	void handler(ProgressChangeEvent event);
}
