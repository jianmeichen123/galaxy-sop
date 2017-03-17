package com.galaxyinternet.service.hologram;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationListdata;
import com.galaxyinternet.model.hologram.InformationListdataRemark;

import java.util.List;

public interface InformationListdataService extends BaseService<InformationListdata>{

     InformationListdata queryMemberById(Long id);

     void deleteOneMember(Long id);
}
