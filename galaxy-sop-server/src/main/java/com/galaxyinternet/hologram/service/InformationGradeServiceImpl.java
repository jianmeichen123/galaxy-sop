package com.galaxyinternet.hologram.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.hologram.InformationGradeDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.hologram.InformationGrade;
import com.galaxyinternet.service.hologram.InformationGradeService;

@Service("com.galaxyinternet.service.hologram.InformationGradeService")
public class InformationGradeServiceImpl extends BaseServiceImpl<InformationGrade> implements InformationGradeService{

	
	@Autowired
	private InformationGradeDao informationGradeDao;
	
	@Override
	protected BaseDao<InformationGrade, Long> getBaseDao() {
		return this.informationGradeDao;
	}


	
}
