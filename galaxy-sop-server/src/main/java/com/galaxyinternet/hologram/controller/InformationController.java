package com.galaxyinternet.hologram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.model.hologram.InformationData;
import com.galaxyinternet.service.hologram.InformationDataService;

@Controller
@RequestMapping("/galaxy/info")
public class InformationController
{
	private final Logger logger = LoggerFactory.getLogger(InformationController.class);
	@Autowired
	InformationDataService service;
	
	@ResponseBody
	@RequestMapping("/save")
	public ResponseData<InformationData> save(@RequestBody InformationData data)
	{
		ResponseData<InformationData> rtn = new ResponseData<>();
		
		try
		{
			service.save(data);
		} catch (Exception e)
		{
			logger.error("保存失败，信息:"+data,e);
			rtn.getResult().addError("保存失败");
		}
		
		return rtn;
	}

}
