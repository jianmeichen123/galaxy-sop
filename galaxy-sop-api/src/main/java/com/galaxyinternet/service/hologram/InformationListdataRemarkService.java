package com.galaxyinternet.service.hologram;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationListdataRemark;

public interface InformationListdataRemarkService extends BaseService<InformationListdataRemark>{

    InformationListdataRemark queryByTitleId(Long titleId);

}
