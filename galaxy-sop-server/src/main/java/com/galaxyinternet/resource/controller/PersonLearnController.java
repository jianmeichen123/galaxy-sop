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

import com.galaxyinternet.bo.project.personLearnBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hr.PersonLearn;
import com.galaxyinternet.service.PersonLearnService;

@Controller
@RequestMapping("/galaxy/hrlearn")
public class PersonLearnController extends BaseControllerImpl<PersonLearn, personLearnBo> {
	
	final Logger logger = LoggerFactory.getLogger(PersonLearnController.class);

	@Autowired
	private PersonLearnService personLearnService;

	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	@Override
	protected BaseService<PersonLearn> getBaseService() {
		return this.personLearnService;
	}
	
	/**
	 * 批量增加学历
	 * @author gxc
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value = "/addPersonLearn", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<PersonLearn> addPersonLearn(@RequestBody List<PersonLearn> personLearnList, HttpServletRequest request,
			@PathVariable Long pid
			
			) {
		ResponseData<PersonLearn> responseBody = new ResponseData<PersonLearn>();
		/*if(personLearn.getPersonId()==null || personLearn.getDegree()==null || personLearn.getSchool()==null || personLearn.getMajor()==null
			||personLearn.getEducationType()==null || personLearn.getBeginDate()==null || personLearn.getOverDate()==null || personLearn.getCertificateNumber()==null
			||personLearn.getTeacherName()==null || personLearn.getTeacherPhone()==null ||personLearn.getTeacherPosition()==null || personLearn.getClassmateName()==null
			||personLearn.getClassmatePhone()==null)
		{
			responseBody.setResult(new Result(Status.ERROR, "学历信息不全请补全!"));
			return responseBody;
		}*/
		try {
			personLearnService.insertInBatch(personLearnList);
			responseBody.setResult(new Result(Status.OK,"保存成功!"));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "insert hrxl faild"));	
		}
		
		return responseBody;
	}
	
}
