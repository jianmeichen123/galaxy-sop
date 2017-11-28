package com.galaxyinternet.mongodb.service;

import java.util.List;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.mongodb.model.InformationDataMG;

public interface InformationMGService extends BaseService<InformationDataMG> {
	public void save(InformationDataMG data) throws CloneNotSupportedException;
	public List<InformationTitle> searchWithData(String parentId,String projectId);
	public List<InformationTitle> selectChildsByPid(Long pid);
	public InformationTitle selectTitleByPinfo(String pinfoKey);
	public InformationTitle selectAreaTitleResutl(String pid, String pinfoKey) ;
	public InformationTitle selectTChildsByPinfo(String pinfoKey);

}
