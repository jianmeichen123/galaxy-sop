package com.galaxyinternet.scheduling;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public class GalaxyTaskScheduler extends ThreadPoolTaskScheduler 
{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(GalaxyTaskScheduler.class);
	
	@PostConstruct
	public void initPoolsize()
	{
		super.setPoolSize(Runtime.getRuntime().availableProcessors());
		logger.debug("PoolSize = "+getPoolSize());
	}
	
}
