package com.galaxyinternet.hologram.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationData;
import com.galaxyinternet.service.hologram.InformationDataService;


@Controller
@RequestMapping("/galaxy/infoProject")
public class InfoProjectController  extends BaseControllerImpl<InformationData, InformationData> {

	final Logger logger = LoggerFactory.getLogger(InfoProjectController.class);
	
	
	@Autowired
	private InformationDataService infoDataService;

	@Override
	protected BaseService<InformationData> getBaseService() {
		return this.infoDataService;
	}
	
	@ResponseBody
	@RequestMapping("/save")
	public ResponseData<InformationData> save(@RequestBody InformationData data)
	{
		ResponseData<InformationData> rtn = new ResponseData<>();
		
		try
		{
			infoDataService.save(data);
		} catch (Exception e)
		{
			logger.error("保存失败，信息:"+data,e);
			rtn.getResult().addError("保存失败");
		}
		
		return rtn;
	}
	
}



