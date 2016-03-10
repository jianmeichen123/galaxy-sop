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

import com.galaxyinternet.bo.project.personInvestBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hr.PersonInvest;
import com.galaxyinternet.service.PersonInvestService;

@Controller
@RequestMapping("/galaxy/hrInvest")
public class PersonInvestController extends BaseControllerImpl<PersonInvest, personInvestBo> {
	
	final Logger logger = LoggerFactory.getLogger(PersonInvestController.class);

	@Autowired
	private PersonInvestService personInvestService;

	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	@Override
	protected BaseService<PersonInvest> getBaseService() {
		return this.personInvestService;
	}
	
	/**
	 * 个人简历之学历
	 * @author gxc
	 * @return  //			@PathVariable Long pid
	 */
	@ResponseBody
	@RequestMapping(value = "/addPersonLearn", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonInvest> addPersonLearn(@RequestBody PersonInvest personInvest, HttpServletRequest request,
			@PathVariable Long pid
			) {
		ResponseData<PersonInvest> responseBody = new ResponseData<PersonInvest>();
		/*if(personLearn.getPersonId()==null || personLearn.getDegree()==null || personLearn.getSchool()==null || personLearn.getMajor()==null
			||personLearn.getEducationType()==null || personLearn.getBeginDate()==null || personLearn.getOverDate()==null || personLearn.getCertificateNumber()==null
			||personLearn.getTeacherName()==null || personLearn.getTeacherPhone()==null ||personLearn.getTeacherPosition()==null || personLearn.getClassmateName()==null
			||personLearn.getClassmatePhone()==null)
		{
			responseBody.setResult(new Result(Status.ERROR, "学历信息不全请补全!"));
			return responseBody;
		}*/
		try {
			personInvest.setCreatedTime(new Date().getTime());
//			personInvest.setPersonId(pid);
			Long id =personInvestService.insert(personInvest);
			if(id > 0)
				responseBody.setResult(new Result(Status.OK,"保存成功!"));
				responseBody.setEntity(personInvest);
			}
		catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "insert hrxl faild"));	
		}
		
		return responseBody;
	}
	
}
