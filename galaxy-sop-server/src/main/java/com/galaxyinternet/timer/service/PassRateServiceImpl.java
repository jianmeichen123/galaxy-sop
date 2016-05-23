package com.galaxyinternet.timer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.bo.PassRateBo;
import com.galaxyinternet.dao.timer.PassRateDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.timer.PassRate;
import com.galaxyinternet.service.PassRateService;


@Service("com.galaxyinternet.service.PassRateService")
public class PassRateServiceImpl extends BaseServiceImpl<PassRate> implements PassRateService {
	
	@Autowired
	private PassRateDao passRateDao;
	
	@Override
	protected BaseDao<PassRate, Long> getBaseDao() {
		return this.passRateDao;
	}

	@Override
	public List<PassRate> queryListById(PassRateBo bo) {
		// TODO Auto-generated method stub
		return passRateDao.selectListById(bo);
	}
}
