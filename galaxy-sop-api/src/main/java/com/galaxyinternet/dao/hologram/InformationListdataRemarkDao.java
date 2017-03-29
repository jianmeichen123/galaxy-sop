package com.galaxyinternet.dao.hologram;

import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.model.hologram.InformationListdataRemark;

public interface InformationListdataRemarkDao extends BaseDao<InformationListdataRemark, Long>{

    InformationListdataRemark selectByTitleId(Long titleId);
}
