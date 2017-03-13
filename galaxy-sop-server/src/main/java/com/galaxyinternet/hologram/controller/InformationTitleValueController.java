package com.galaxyinternet.hologram.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.galaxyinternet.bo.hologram.InformationTitleBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.project.controller.ProjectProgressController;
import com.galaxyinternet.service.hologram.InformationDictionaryService;
import com.galaxyinternet.service.hologram.InformationTitleService;


@Controller
@RequestMapping("/galaxy/tvalue")
public class InformationTitleValueController  extends BaseControllerImpl<InformationTitle, InformationTitleBo> {

	final Logger logger = LoggerFactory.getLogger(ProjectProgressController.class);
	
	
	@Autowired
	private InformationTitleService informationTitleService;
	
	
	@Autowired
	private InformationDictionaryService informationDictionaryService;
	
	
	@Override
	protected BaseService<InformationTitle> getBaseService() {
		return this.informationTitleService;
	}
	
	
	
	
	
	
	
	
	
	
}



