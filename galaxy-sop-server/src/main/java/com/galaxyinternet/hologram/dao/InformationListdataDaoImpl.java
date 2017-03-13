package com.galaxyinternet.hologram.dao;

import com.galaxyinternet.dao.hologram.InformationListdataRemarkDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.hologram.InformationListdataRemark;
import org.springframework.stereotype.Repository;

@Repository("informationListdataDao")
public class InformationListdataDaoImpl extends BaseDaoImpl<InformationListdataRemark, Long> implements InformationListdataRemarkDao{

}
