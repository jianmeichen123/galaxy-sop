package com.galaxyinternet.service;

import com.galaxyinternet.bo.IdeaZixunBo;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.idea.IdeaZixun;

public interface IdeaZixunService extends BaseService<IdeaZixun> {

	Page<IdeaZixunBo> queryZixunPage(IdeaZixunBo query, PageRequest pageable);

	void insertZixun(IdeaZixun zixun, IdeaZixunBo zixunbo);

	void editZixun(IdeaZixun zixun, IdeaZixunBo zixunbo);

	void deleteZixun(IdeaZixun zx);
	
	
}
