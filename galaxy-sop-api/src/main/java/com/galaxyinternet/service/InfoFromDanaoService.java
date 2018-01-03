package com.galaxyinternet.service;

import java.util.List;
import java.util.Map;

import com.galaxyinternet.model.DongNao.DnProject;
import org.springframework.data.domain.Pageable;

import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.PersonPool;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;

public interface InfoFromDanaoService {

	/**
	 * @author zf
	 * @param query
	 * @param pageable
	 * @return
	 */
	public Page<DnProject> queryDnaoProjectPage(Map<String,Object> map) throws Exception;
	



}