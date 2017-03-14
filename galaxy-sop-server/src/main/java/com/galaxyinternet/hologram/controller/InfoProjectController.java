package com.galaxyinternet.hologram.controller;


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

import com.galaxyinternet.common.annotation.LogType;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.form.Token;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationData;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.hologram.InformationDataService;


@Controller
@RequestMapping("/galaxy/infoProject")
public class InfoProjectController  extends BaseControllerImpl<InformationData, InformationData> {

	final Logger logger = LoggerFactory.getLogger(InfoProjectController.class);
	
	
	@Autowired
	private InformationDataService infoDataService;

	@Override
	protected BaseService<InformationData> getBaseService() {
		return this.infoDataService;
	}
	@Autowired
	private ProjectService projectService;
	
	
	/**
	 * 全息图-项目部分数据处理页面
	 * @version 
	 * @return
	 */
	@RequestMapping(value = "/toInfoProjectPage", method = RequestMethod.GET)
	public String toInfoProjectPage(HttpServletRequest request) {
		return "project/tanchuan/v_person_learning";
	}
	
	/**
	 * 全息图-项目部分-字段编辑
	 * @version 2017-03-13
	 * @author jianmeichen
	 */
	@Token
	@com.galaxyinternet.common.annotation.Logger(operationScope = LogType.LOG)
	@ResponseBody
	@RequestMapping(value = "/saveOrUpdateInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> saveOrUpdateInfo(@RequestBody InformationData informationData,
			HttpServletRequest request) {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		if(null==informationData.getProjectId()||"".equals(informationData.getProjectId())){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
	    Long projectId=Long.parseLong(informationData.getProjectId());
	    Project project = projectService.queryById(projectId);
		User user = (User) getUserFromSession(request);
		try{
			infoDataService.save(informationData);
		    logger.info("全息图编辑项目相关信息["+"项目名称:"+project.getProjectName()+" 创建人:"+project.getCreateUname()+" 部门："+user.getDepartmentName()+"]");
		    responseBody.setResult(new Result(Status.OK, null,"编辑项目部分成功"));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"编辑项目部分数据失败"));
			logger.error("异常信息:",e.getMessage());
		}
		return responseBody;
	}
	
	
	@ResponseBody
	@RequestMapping("/save")
	public ResponseData<InformationData> save(@RequestBody InformationData data)
	{
		ResponseData<InformationData> rtn = new ResponseData<>();
		
		try
		{
			infoDataService.save(data);
		} catch (Exception e)
		{
			logger.error("保存失败，信息:"+data,e);
			rtn.getResult().addError("保存失败");
		}
		
		return rtn;
	}
	
}



