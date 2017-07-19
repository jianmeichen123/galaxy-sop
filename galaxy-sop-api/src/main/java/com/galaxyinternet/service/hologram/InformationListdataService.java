package com.galaxyinternet.service.hologram;

import java.util.List;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationListdata;

public interface InformationListdataService extends BaseService<InformationListdata>{

     InformationListdata queryMemberById(Long id);

     void deleteOneMember(Long id);
     
     public void save(InformationListdata entity);
     public void saveBatch(List<InformationListdata> list);
}
