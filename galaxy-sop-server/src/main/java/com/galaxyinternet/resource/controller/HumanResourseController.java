package com.galaxyinternet.resource.controller;

import java.util.List;

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

import com.galaxyinternet.bo.project.PersonPoolBo;
import com.galaxyinternet.bo.project.personInvestBo;
import com.galaxyinternet.bo.project.personLearnBo;
import com.galaxyinternet.bo.project.personWorkBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hr.PersonInvest;
import com.galaxyinternet.model.hr.PersonLearn;
import com.galaxyinternet.model.hr.PersonResumetc;
import com.galaxyinternet.model.hr.PersonWork;
import com.galaxyinternet.model.project.PersonPool;
import com.galaxyinternet.service.PersonInvestService;
import com.galaxyinternet.service.PersonLearnService;
import com.galaxyinternet.service.PersonPoolService;
import com.galaxyinternet.service.PersonWorkService;

@Controller
@RequestMapping("/galaxy/hrjl")
public class HumanResourseController extends BaseControllerImpl<PersonPool, PersonPoolBo> {
	
	final Logger logger = LoggerFactory.getLogger(HumanResourseController.class);

	@Autowired
	private PersonPoolService personPoolService;
	
	
	@Autowired
	private PersonLearnService personLearnService;
	
	@Autowired
	private PersonInvestService personInvestService;
	
	@Autowired
	private PersonWorkService personWorkService; 
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;	
	@Override
	protected BaseService<PersonPool> getBaseService() {
		return this.personPoolService;
	}

	/**
	 * 跳转到完善简历页面
	 * 
	 */
	@RequestMapping(value="/wanshan")
	public String wanshan(){
		return "/resumetc/wanshanjianli";
	}
	
	@RequestMapping(value="/resumetcc", method = RequestMethod.GET)
	public String resumetcc(HttpServletRequest request){

		request.setAttribute("personId", request.getParameter("personId"));
		
		return "/resumetc/resumetc";
	}

	/**
	 * 回显数据
	 * @param personPool
	 * @param request
	 * @param pid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/toaddPersonHr/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonResumetc> toaddPersonHr(HttpServletRequest request,
			@PathVariable Long pid){
		
			ResponseData<PersonResumetc> responseBody = new ResponseData<PersonResumetc>();	
			PersonPool personPool = personPoolService.queryById(pid);
			
			PersonLearn personLearnQuery = new PersonLearn();
			personLearnQuery.setPersonId(pid);
			List<PersonLearn> personLearns =  personLearnService.queryList(personLearnQuery);
	
			
			PersonWork personWorkQuery  = new PersonWork();
			personWorkQuery.setPersonId(pid);
			List<PersonWork> personWorks =personWorkService.queryList(personWorkQuery);
			
			PersonInvest personInvest =new PersonInvest();
			personInvest.setPersonId(pid);
			personInvest = personInvestService.queryOne(personInvest);
			PersonResumetc personResumetc = new PersonResumetc();
			personResumetc.setPersonInvest(personInvest);
			personResumetc.setPersonLearn(personLearns);
			personResumetc.setPersonPool(personPool);
			personResumetc.setPersonWork(personWorks);
			responseBody.setEntity(personResumetc);
			return responseBody;	
	}

	
	/**
	 * 个人简历
	 * @author gxc
	 * @return ||personPool.getPersonDuties()==null
	 */
	@ResponseBody
	@RequestMapping(value = "/addPersonHr", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonPool> addPersonHr(@RequestBody PersonResumetc personResumetc, HttpServletRequest request){
		personInvestService.WanShan(personResumetc);
		
		return null;
		
	}

}
