package com.galaxyinternet.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.galaxyinternet.framework.core.exception.BusinessException;
@Component("testAnnotaionTask")
public class TestAnnotaionTask extends BaseGalaxyTask {

	private static final Logger logger = LoggerFactory.getLogger(TestAnnotaionTask.class);
	@Override
	protected void executeInteral() throws BusinessException {
		logger.debug("TestAnnotaionTask executed.");

	}
	@Override
	public boolean isDisabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
