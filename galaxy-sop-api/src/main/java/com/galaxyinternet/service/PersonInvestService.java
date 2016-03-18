package com.galaxyinternet.service;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hr.PersonInvest;
import com.galaxyinternet.model.hr.PersonResumetc;

public interface PersonInvestService extends BaseService<PersonInvest> {

	
	public int WanShan(PersonResumetc personResumetc);
}
