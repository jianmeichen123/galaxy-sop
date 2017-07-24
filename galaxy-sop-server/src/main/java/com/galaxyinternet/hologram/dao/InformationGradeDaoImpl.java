package com.galaxyinternet.hologram.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.hologram.InformationGradeDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.hologram.InformationGrade;

@Repository("informationGradeDao")
public class InformationGradeDaoImpl extends BaseDaoImpl<InformationGrade, Long> implements InformationGradeDao{

	
}
