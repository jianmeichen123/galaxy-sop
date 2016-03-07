package com.galaxyinternet.resource.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.project.personWorkBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hr.PersonWork;
import com.galaxyinternet.service.PersonWorkService;

@Controller
@RequestMapping("/galaxy/hrwork")
public class PersonWorkController extends BaseControllerImpl<PersonWork, personWorkBo> {
	
	final Logger logger = LoggerFactory.getLogger(PersonWorkController.class);
	

	@Autowired
	private PersonWorkService personWorkService;

	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	@Override
	protected BaseService<PersonWork> getBaseService() {
		return this.personWorkService;
	}
	
	/**
	 * 个人简历之工作简历
	 * @author gxc
	 * @return/{pid}
	 */
	@ResponseBody
	@RequestMapping(value = "/addPersonWork/{pid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonWork> addPersonWork(@RequestBody PersonWork personWork, HttpServletRequest request,
			@PathVariable Long pid
			
			) {
		ResponseData<PersonWork> responseBody = new ResponseData<PersonWork>();
		/*if(personWork.getCompanyName()==null || personWork.getWorkDepart()==null || personWork.getWorkPosition()==null 
			||personWork.getWorkContent()==null || personWork.getWorkEffect()==null || personWork.getWorkEmolument()==null
			||personWork.getBeginWork()==null || personWork.getOverWork()==null || personWork.getLeaveReason()==null
			||personWork.getLeaderPosition()==null||personWork.getLeaderPhone()==null || personWork.getColleagueName()==null
			||personWork.getColleaguePhone()==null||personWork.getColleaguePosition()==null	)
		{
			responseBody.setResult(new Result(Status.ERROR, "工作履历信息不全请补全!"));
			return responseBody;
		}*/
		try {
			personWork.setCreatedTime(new Date().getTime());
			personWork.setPersonId(pid);
			
			Long id =personWorkService.insert(personWork);
			if(id > 0)
				responseBody.setResult(new Result(Status.OK,"保存成功!"));
				responseBody.setEntity(personWork);
			}
		catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "insert hrwork faild"));	
		}		
		return responseBody;
	}
	
}
