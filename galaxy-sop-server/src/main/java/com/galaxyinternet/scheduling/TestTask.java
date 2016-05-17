package com.galaxyinternet.scheduling;

import org.apache.log4j.Logger;

import com.galaxyinternet.framework.core.exception.BusinessException;

public class TestTask extends BaseGalaxyTask {
	private Logger logger = Logger.getLogger(TestTask.class);
	@Override
	protected void executeInteral() throws BusinessException {
		logger.debug("TestTask executed.");
	}

}
