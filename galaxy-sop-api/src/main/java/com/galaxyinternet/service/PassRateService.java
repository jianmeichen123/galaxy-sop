package com.galaxyinternet.service;

import java.util.List;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.timer.PassRate;

public interface PassRateService extends BaseService<PassRate>{
	public List<PassRate> queryListById(List<String> idList);
}
