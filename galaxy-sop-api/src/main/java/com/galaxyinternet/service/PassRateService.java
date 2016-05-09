package com.galaxyinternet.service;

import java.util.List;

import com.galaxyinternet.bo.PassRateBo;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.timer.PassRate;

public interface PassRateService extends BaseService<PassRate>{
	public List<PassRate> queryListById(PassRateBo bo);
}
