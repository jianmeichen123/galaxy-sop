package com.galaxyinternet.scheduling;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.galaxyinternet.framework.core.cluster.LeaderSelector;
import com.galaxyinternet.framework.core.exception.BusinessException;

public abstract class BaseGalaxyTask implements GalaxyTask {

	private static final Logger logger = Logger.getLogger(BaseGalaxyTask.class);
	protected boolean disabled = false;
	@Autowired
	private LeaderSelector leaderSelector;

	@Override
	public void execute() throws BusinessException {
		if(isDisabled())
		{
			return;
		}
		if(leaderSelector != null && !leaderSelector.isLeader())
		{
			logger.debug(String.format("当前节点[ID=%s]不是Leader节点[LeaderId=%s]", leaderSelector.getId(),leaderSelector.getLeaderId()));
			return;
		}
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

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}
