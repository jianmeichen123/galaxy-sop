package com.galaxyinternet.soptask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.galaxyinternet.bo.SopUserScheduleBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.soptask.SopUserSchedule;
import com.galaxyinternet.service.SopUserScheduleService;

@Controller
@RequestMapping("/galaxy/userSchedule")
public class SopUserScheduleController extends BaseControllerImpl<SopUserSchedule, SopUserScheduleBo> {

	@Autowired
	private SopUserScheduleService sopUserScheduleService;
	
	@Override
	protected BaseService<SopUserSchedule> getBaseService() {
		// TODO Auto-generated method stub
		return this.sopUserScheduleService;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/addUserSchedule", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopUserSchedule> addUserSchedule() {
		
		ResponseData<SopUserSchedule> responseBody = new ResponseData<SopUserSchedule>();
		SopUserSchedule sop=new SopUserSchedule();
		sop.setContent("test88");
		sop.setUserId(6);
		sopUserScheduleService.insert(sop);
		
		return responseBody;
	}

}
