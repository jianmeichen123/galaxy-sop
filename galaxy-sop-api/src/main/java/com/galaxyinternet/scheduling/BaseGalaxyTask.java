package com.galaxyinternet.scheduling;

import org.apache.log4j.Logger;

import com.galaxyinternet.framework.core.exception.BusinessException;

public abstract class BaseGalaxyTask implements GalaxyTask {

	private static final Logger logger = Logger.getLogger(BaseGalaxyTask.class);
	
	@Override
	public void execute() throws BusinessException {
		String jobName = this.getClass().getName();
		try {
			logger.debug("======================"+jobName+" Start========================");
			executeInteral();
			logger.debug("======================"+jobName+" Success========================");
		} catch (Exception e) {
			logger.debug("======================"+jobName+" Error========================");
			throw e;
		}

	}
	
	protected abstract void executeInteral() throws BusinessException;

}
