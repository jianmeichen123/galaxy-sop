/*package com.galaxyinternet.resource.controller;

import java.util.List;

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

import com.galaxyinternet.bo.PersonResumetcBo;
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
@RequestMapping("/galaxy/personResumetc")
public class PersonResumetcController extends BaseControllerImpl<PersonResumetc, PersonResumetcBo> {
	
	final Logger logger = LoggerFactory.getLogger(PersonResumetcController.class);

	
	
	@Autowired
	private PersonWorkService personWorkService;	
	
	@Autowired
	private PersonLearnService personLearnService;

	@Autowired
	private PersonPoolService personPoolService;
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;

	@Autowired
	private PersonInvestService personInvestService;
	
	@Override
	protected BaseService<PersonResumetc> getBaseService() {
		//
		return null;
	}	
	@ResponseBody
	@RequestMapping(value = "/addpersonResumetc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonResumetc> personResumetc(@RequestBody PersonResumetcBo personResumetc, HttpServletRequest request) {
	
		ResponseData<PersonResumetc> responseBody = new ResponseData<PersonResumetc>();

		List<PersonLearn> personLearnList = personResumetc.getPersonLearnList();
		List<PersonInvest> personInvestList = personResumetc.getPersonInvestList();
		List<PersonWork> personWorkList = personResumetc.getPersonWorkList();
		
		PersonPool personPool = personResumetc.getPersonPool();
		
		try {

			personPoolService.insert(personPool);
			personInvestService.insertInBatch(personInvestList);
			personLearnService.insertInBatch(personLearnList);
			personWorkService.insertInBatch(personWorkList);
			responseBody.setResult(new Result(Status.OK,"更改成功!"));
			responseBody.setEntity(personResumetc);
		}
		catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "update hrxl faild"));
			System.out.println("失败失败失败失败失败失败失败失败失败失败");
		}		
		return responseBody;
		

	}
	*//**
	 * 更改保存的数据
	 *//*
	@ResponseBody
	@RequestMapping(value = "/uppersonResumetc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonResumetc> uppersonResumetc(@RequestBody PersonResumetc personResumetc, HttpServletRequest request) {
	
		ResponseData<PersonResumetc> responseBody = new ResponseData<PersonResumetc>();

		*//**
		 * 学习经历
		 *//*
		PersonLearn personLearn = new PersonLearn();
		
		personLearn.setPersonId(personResumetc.getPersonId());
		personLearn.setDegree(personResumetc.getAcompanyName());
		personLearn.setSchool(personResumetc.getSchool());
		personLearn.setMajor(personResumetc.getMajor());
		personLearn.setEducationType(personResumetc.getEducationType());
		personLearn.setBeginDate(personResumetc.getBeginDate());
		personLearn.setOverDate(personResumetc.getOverDate());
		personLearn.setCertificateNumber(personResumetc.getCertificateNumber());
		personLearn.setTeacherName(personResumetc.getTeacherName());
		personLearn.setTeacherPosition(personResumetc.getTeacherPosition());
		personLearn.setTeacherPhone(personResumetc.getTeacherPhone());
		personLearn.setClassmateName(personResumetc.getClassmateName());
		personLearn.setClassmatePhone(personLearn.getClassmatePhone());
		
		*//**
		 * 外部项目信息
		 *//*
		PersonInvest personInvest = new PersonInvest();
		
		personInvest.setPersonId(personResumetc.getPersonId());
		personInvest.setIcompanyName(personResumetc.getIcompanyName());
		personInvest.setInvestmentAmount(personResumetc.getInvestmentAmount());
		personInvest.setShareRatio(personResumetc.getShareRatio());
		personInvest.setProjectDirector(personResumetc.getProjectDirector());
		personInvest.setEmceedPosition(personResumetc.getEmceedPosition());
		personInvest.setTelephone(personResumetc.getTelephone());
		personInvest.setAcompanyName(personResumetc.getAcompanyName());
		personInvest.setAinvestmentAmount(personResumetc.getAinvestmentAmount());
		personInvest.setAshareRatio(personResumetc.getAshareRatio());
		personInvest.setAtelephone(personResumetc.getAcompanyName());
		
		
		
		*//**
		 * 工作经历
		 *//*
		PersonWork personWork = new PersonWork();
		
		personWork.setPersonId(personResumetc.getPersonId());
		personWork.setCompanyName(personResumetc.getCompanyName());
		personWork.setWorkDepart(personResumetc.getWorkDepart());
		personWork.setWorkPosition(personResumetc.getWorkPosition());
		personWork.setWorkContent(personResumetc.getWorkContent());
		personWork.setWorkEffect(personResumetc.getWorkEffect());
		personWork.setWorkEmolument(personResumetc.getWorkEmolument());
		personWork.setBeginWork(personResumetc.getBeginWork());
		personWork.setOverWork(personResumetc.getOverWork());
		personWork.setLeaveReason(personResumetc.getLeaveReason());
		personWork.setLeaderName(personResumetc.getLeaderName());
		personWork.setLeaderPosition(personResumetc.getLeaderPosition());
		personWork.setLeaderRelationship(personResumetc.getLeaderRelationship());
		personWork.setLeaderPhone(personResumetc.getLeaderPhone());
		personWork.setColleagueName(personResumetc.getColleagueName());
		personWork.setColleaguePosition(personResumetc.getColleaguePosition());
		personWork.setColleagueRelationship(personResumetc.getColleagueRelationship());
		personWork.setColleaguePhone(personResumetc.getColleaguePhone());
		
		
		*//**
		 * 个人简历
		 *//*
		PersonPool personPool = new PersonPool();
		
		personPool.setPersonName(personResumetc.getPersonName());
		personPool.setPersonSex(personResumetc.getPersonSex());
		personPool.setPersonAge(personResumetc.getPersonAge());
		personPool.setHighestDegree(personResumetc.getHighestDegree());
		personPool.setWorkTime(personResumetc.getWorkTime());
		personPool.setPersonDuties(personResumetc.getPersonDuties());
		personPool.setPersonBirthday(personResumetc.getPersonBirthday());
		personPool.setPersonIdcard(personResumetc.getPersonIdcard());
		personPool.setPersonTelephone(personResumetc.getPersonTelephone());
		personPool.setPersonEmail(personResumetc.getPersonEmail());
		personPool.setPersonCharacter(personResumetc.getPersonCharacter());
		personPool.setPersonGoodness(personResumetc.getPersonGoodness());
		personPool.setPersonDisparity(personResumetc.getPersonDisparity());
		personPool.setTalkAbility(personResumetc.getTalkAbility());
		personPool.setTeamAbility(personResumetc.getTeamAbility());
		personPool.setBusinessStrength(personResumetc.getBusinessStrength());
		personPool.setFree(personResumetc.getFree());
		personPool.setTeamRole(personResumetc.getTeamRole());
		personPool.setMemberRelation(personResumetc.getMemberRelation());
		personPool.setLaborDispute(personResumetc.getLaborDispute());
		personPool.setAbilityStar(personResumetc.getAbilityStar());
		personPool.setLevelStar(personResumetc.getLevelStar());
		personPool.setEndComment(personResumetc.getEndComment());
		
				Object obj = request.getSession().getAttribute(Constants.SESSION_ID_KEY);
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}	
		try {

			int uinvestid =personInvestService.updateById(personInvest);
			int poolid = personPoolService.updateById(personPool);
			int learnid = personLearnService.updateById(personLearn);
			int workid= personWorkService.updateById(personWork);
			
			if(uinvestid > 0||poolid>0||learnid>0||workid>0){
				responseBody.setResult(new Result(Status.OK,"更改成功!"));
				responseBody.setEntity(personResumetc);
				System.out.println("成功成功成功成功成功成功成功成功");
			}
		}
		catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "update hrxl faild"));
			System.out.println("失败失败失败失败失败失败失败失败失败失败");
		}		
		return responseBody;

	}

}
*/