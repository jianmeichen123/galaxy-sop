package com.galaxyinternet.service;

import java.text.ParseException;
import java.util.List;

import com.galaxyinternet.bo.SopUserScheduleBo;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.soptask.SopUserSchedule;

public interface SopUserScheduleService extends BaseService<SopUserSchedule>{

	public List<SopUserScheduleBo> selectSopUserScheduleByTime(Long userId,Long currentTime,Integer type) throws ParseException;
	
}
