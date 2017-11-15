package com.galaxyinternet.scheduling;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxyinternet.framework.core.exception.BusinessException;

public class TestTask extends BaseGalaxyTask {
	private Logger logger = LoggerFactory.getLogger(TestTask.class);
	@Override
	protected void executeInteral() throws BusinessException {
		logger.debug("TestTask executed.");
	}

}
