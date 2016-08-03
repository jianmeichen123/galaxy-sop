package com.galaxyinternet.project.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.galaxyinternet.bo.project.ProjectTransferBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.ProjectTransfer;
import com.galaxyinternet.service.ProjectTransferService;

@Controller
@RequestMapping("/galaxy/projectTransfer")
public class ProjectTransferController extends BaseControllerImpl<ProjectTransfer, ProjectTransferBo> {

	final Logger logger = LoggerFactory.getLogger(ProjectTransferController.class);
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;

	@Autowired
	private ProjectTransferService projectTransferService;
	
	@Override
	protected BaseService<ProjectTransfer> getBaseService() {
		// TODO Auto-generated method stub
		return this.projectTransferService;
	}
	
	/**
	 * 添加页面
	 */
	@RequestMapping(value = "/toProjectTransfer", method = RequestMethod.GET)
	public String interViewAdd() {
		return "project/projectTransfer/project_transfer";
	}
	
	
	
}
