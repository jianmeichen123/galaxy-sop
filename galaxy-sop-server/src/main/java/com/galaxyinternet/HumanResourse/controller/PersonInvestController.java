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

import com.galaxyinternet.bo.project.personInvestBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.HR.personInvest;
import com.galaxyinternet.service.PersonInvestService;

@Controller
@RequestMapping("/galaxy/hrInvest")
public class PersonInvestController extends BaseControllerImpl<personInvest, personInvestBo> {
	
	final Logger logger = LoggerFactory.getLogger(PersonInvestController.class);

	@Autowired
	private PersonInvestService personInvestService;

	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	@Override
	protected BaseService<personInvest> getBaseService() {
		return this.personInvestService;
	}
	
	/**
	 * 个人简历之添加外部项目
	 * @author gxc
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addPersonInvest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<personInvest> addPersonInvest(@RequestBody personInvest personInvest, HttpServletRequest request) {
		ResponseData<personInvest> responseBody = new ResponseData<personInvest>();
		if(personInvest.getCompanyName()==null||personInvest.getShareRation()==null || personInvest.getInverstmentAmount()==null ||personInvest.getProjectDirector()==null 
		|| personInvest.getEmceedPosition()==null || personInvest.getTelephone()==null)
		{
			responseBody.setResult(new Result(Status.ERROR, "学历信息不全请补全!"));
			return responseBody;
		}
		try {
			personInvest.setCreatedTime(new Date().getTime());
			Long id =personInvestService.insert(personInvest);
			if(id > 0)
				responseBody.setResult(new Result(Status.OK,"保存成功!"));
				responseBody.setEntity(personInvest);
			}
		catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "invest hrxl faild"));	
		}		
		return responseBody;
	}
	
}
