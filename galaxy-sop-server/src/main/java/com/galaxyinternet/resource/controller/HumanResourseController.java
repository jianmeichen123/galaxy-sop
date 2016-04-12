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
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.exception.PlatformException;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.DateUtil;
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

	@RequestMapping(value="/genresumetcc", method = RequestMethod.GET)
	public String resumetccr(HttpServletRequest request){
		
		request.setAttribute("personId", request.getParameter("personId"));
		
		return "/resumetc/resumetcc";
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
	public ResponseData<PersonResumetc> toaddPersonHr(HttpServletRequest request,@PathVariable Long pid){
			
			ResponseData<PersonResumetc> responseData = new ResponseData<PersonResumetc>();
			Result result = new Result();
			try {
				PersonPool personPool = personPoolService.queryById(pid);
				if(personPool.getPersonIdcard()!=null &&!"".equals(personPool.getPersonIdcard().trim())&& personPool.getPersonBirthday()==null){
					String str = personPool.getPersonIdcard();
					String Str = str.substring(6, 14);
					Str = Str.substring(0, 4) + "-" + Str.substring(4, 6) + "-" + Str.substring(6);
					personPool.setPersonBirthdayStr(Str);
				}
				if(personPool.getPersonBirthday()!=null){				
					personPool.setPersonBirthdayStr(DateUtil.convertDateToString(personPool.getPersonBirthday()));					
				}
				PersonLearn personLearnQuery = new PersonLearn();
				personLearnQuery.setPersonId(pid);
				List<PersonLearn> personLearns =  personLearnService.queryList(personLearnQuery);
				for (PersonLearn personLearn : personLearns) {
					if(personLearn.getOverDate()!=null){
						personLearn.setOverDateStr(DateUtil.convertDateToString(personLearn.getOverDate()));
					}

				}
				
				PersonWork personWorkQuery  = new PersonWork();
				personWorkQuery.setPersonId(pid);
				List<PersonWork> personWorks =personWorkService.queryList(personWorkQuery);
				for (PersonWork personWork : personWorks) {
					if(personWork.getBeginWork()!=null){
						personWork.setBeginWorkStr(DateUtil.convertDateToString(personWork.getBeginWork()));
					}
				}
				PersonInvest personInvest =new PersonInvest();
				personInvest.setPersonId(pid);
				personInvest = personInvestService.queryOne(personInvest);
				PersonResumetc personResumetc = new PersonResumetc();
				personResumetc.setPersonInvest(personInvest);
				personResumetc.setPersonLearn(personLearns);
				personResumetc.setPersonPool(personPool);
				personResumetc.setPersonWork(personWorks);
				responseData.setEntity(personResumetc);
				result.setStatus(Status.OK);
			} catch (PlatformException e) {
				result.addError(e.getMessage(), e.getCode()+"");
				logger.error("queryUserList ", e);
			} catch (Exception e) {
				result.addError("系统繁忙，请稍后");
				logger.error("toaddPersonHr",e);
			}
			responseData.setResult(result);
			return responseData;	
	}

	
	/**
	 * 个人简历
	 * @author gxc
	 * @return ||personPool.getPersonDuties()==null
	 */
	@ResponseBody
	@RequestMapping(value = "/addPersonHr", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonResumetc> addPersonHr(@RequestBody PersonResumetc personResumetc, HttpServletRequest request){
		ResponseData<PersonResumetc> responseData = new ResponseData<>();
		Result result = new Result();
		try {
			personInvestService.WanShan(personResumetc);
			result.setStatus(Status.OK);
		}  catch (PlatformException e) {
			result.addError(e.getMessage(), e.getCode()+"");
			logger.error("addPersonHr ", e);
		} catch (Exception e) {
			result.addError("系统繁忙，请稍后");
			logger.error("addPersonHr",e);
		}
		responseData.setResult(result);
		responseData.setEntity(personResumetc);
		return responseData;
		
	}

}