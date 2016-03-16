package com.galaxyinternet.resource.controller;

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
	 * 跳转到完善简历页面
	 * 
	 */
	@RequestMapping(value="/wanshan")
	public String wanshan(){
		return "/resumetc/wanshanjianli";
	}
	
	@RequestMapping(value="/resumetcc/", method = RequestMethod.GET)
	public String resumetcc(){
//		PersonPool person = personPoolService.queryById(id);
//		if(person == null ){
//			return "未查找到指定信息!";
//		}	
//		request.setAttribute("person", person);
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
	public ResponseData<PersonPool> toaddPersonHr(HttpServletRequest request,
			@PathVariable Long pid){
		
			ResponseData<PersonPool> responseBody = new ResponseData<PersonPool>();	
			
			PersonPool personPool1 = personPoolService.queryById(pid);
			if(personPool1 == null){
				responseBody.setResult(new Result(Status.ERROR, "未找到相关人员信息"));
				
			}else{
				responseBody.setEntity(personPool1);
			}			
			return responseBody;	
	}

	
	/**
	 * 个人简历
	 * @author gxc
	 * @return ||personPool.getPersonDuties()==null
	 */
	@ResponseBody
	@RequestMapping(value = "/addPersonHr", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonPool> addPersonHr(@RequestBody PersonPool personPool, HttpServletRequest request,
			@PathVariable Long pid
			) {
		ResponseData<PersonPool> responseBody = new ResponseData<PersonPool>();
		/*if(personPool.getPersonName() == null || "".equals(personPool.getPersonName().trim())||personPool.getPersonBirthday()==null||personPool.getPersonAge()==null  || personPool.getPersonSex()==null 
				||personPool.getPersonCharacter()==null ||personPool.getPersonIdcard()==null || personPool.getPersonTelephone()==null
				||personPool.getPersonEmail()==null || personPool.getPersonCharacter()==null||personPool.getPersonGoodness()==null || personPool.getPersonDisparity()==null
				||personPool.getTalkAbility()==null || personPool.getTeamAbility()==null||personPool.getBusinessStrength()==null || personPool.getFree()==null || personPool.getTeamRole()==null
				||personPool.getMemberRelation()==null || personPool.getLaborDispute()==null || personPool.getAbilityStar()==null || personPool.getLevelStar()==null || personPool.getEndComment()==null)
		{
			responseBody.setResult(new Result(Status.ERROR, "个人简历不全请补全!"));
			return responseBody;
		}*/
/*		Object obj = request.getSession().getAttribute(Constants.SESSION_ID_KEY);
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}*/		
		try {			
//			personPool.setId(pid);
			Long id = (long) personPoolService.updateById(personPool);
			System.out.println("ok");
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
