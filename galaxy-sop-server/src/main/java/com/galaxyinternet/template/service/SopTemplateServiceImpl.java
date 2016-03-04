package com.galaxyinternet.template.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.template.SopTemplateDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.template.SopTemplate;
import com.galaxyinternet.service.SopTemplateService;
@Service
public class SopTemplateServiceImpl extends BaseServiceImpl<SopTemplate>implements SopTemplateService {

	@Autowired
	private SopTemplateDao tempalteDao;
	@Override
	protected BaseDao<SopTemplate, Long> getBaseDao() {
		// TODO Auto-generated method stub
		return this.tempalteDao;
	}


}
