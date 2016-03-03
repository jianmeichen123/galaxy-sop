package com.galaxyinternet.HumanResourse.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.project.PersonPoolBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.PersonPool;
import com.galaxyinternet.service.PersonPoolService;

@Controller
@RequestMapping("/galaxy/hrjl")
public class HumanResourseController extends BaseControllerImpl<PersonPool, PersonPoolBo> {
	
	final Logger logger = LoggerFactory.getLogger(HumanResourseController.class);

	@Autowired
	private PersonPoolService personPoolService;
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;	
	@Override
	protected BaseService<PersonPool> getBaseService() {
		return this.personPoolService;
	}
	
	/**
	 * 个人简历
	 * @author gxc
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addPersonHr", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonPool> addPersonHr(@RequestBody PersonPool personPool, HttpServletRequest request) {
		ResponseData<PersonPool> responseBody = new ResponseData<PersonPool>();
		if(personPool.getPersonName() == null || "".equals(personPool.getPersonName().trim())||personPool.getPersonBirthday()==null||personPool.getPersonAge()==null  || personPool.getPersonSex()==null 
				||personPool.getPersonCharacter()==null ||personPool.getPersonDuties()==null||personPool.getPersonIdcard()==null || personPool.getPersonTelephone()==null
				||personPool.getPersonEmail()==null || personPool.getPersonCharacter()==null||personPool.getPersonGoodness()==null || personPool.getPersonDisparity()==null
				||personPool.getTalkAbility()==null || personPool.getTeamAbility()==null||personPool.getBusinessStrength()==null || personPool.getFree()==null || personPool.getTeamRole()==null
				||personPool.getMemberRelation()==null || personPool.getLaborDispute()==null || personPool.getAbilityStar()==null || personPool.getLevelStar()==null || personPool.getEndComment()==null)
		{
			responseBody.setResult(new Result(Status.ERROR, "个人简历不全请补全!"));
			return responseBody;
		}
		try {
			personPool.setCreatedTime(new Date().getTime());
			Long id = personPoolService.insert(personPool);
			if(id > 0)
				responseBody.setResult(new Result(Status.OK,"保存成功!"));
				responseBody.setEntity(personPool);
			}
		catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "insert hrjl faild"));	
		}
		
		return responseBody;
	}
	
}
