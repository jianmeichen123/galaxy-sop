package com.galaxyinternet.project.controller;
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

import com.galaxyinternet.bo.project.ProjectTransferBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.project.ProjectTransfer;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.ProjectTransferService;

@Controller
@RequestMapping("/galaxy/projectTransfer/applyTransfer")
public class ProjectTransferController extends BaseControllerImpl<ProjectTransfer, ProjectTransferBo> {

	private final static Logger _common_logger_ = LoggerFactory.getLogger(ProjectController.class);
    @Autowired
	com.galaxyinternet.framework.cache.Cache cache;

	@Autowired
	private ProjectTransferService projectTransferService;
	
	@Autowired
	private ProjectService projectService;
	
	@Override
	protected BaseService<ProjectTransfer> getBaseService() {
		// TODO Auto-generated method stub
		return this.projectTransferService;
	}

	
	
	/**
	 * 添加页面
	 */
	@RequestMapping(value = "/toProjectTransfer", method = RequestMethod.GET)
	public String interViewAdd() {
		return "project/projectTransfer/project_transfer";
	}
	
	
	/**
	 * 项目移交
	 */
	@ResponseBody
	@RequestMapping(value = "/applyTransfer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectTransfer> applyTransfer(@RequestBody ProjectTransfer projectTransfer,
			HttpServletRequest request) {
		ResponseData<ProjectTransfer> responseBody = new ResponseData<ProjectTransfer>();
		if(projectTransfer.getProjectId() == null 
				|| projectTransfer.getAfterDepartmentId() == null 
				|| projectTransfer.getAfterUid() == null){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		try {
			Project project = projectService.queryById(projectTransfer.getProjectId());
			if(project == null){
				responseBody.setResult(new Result(Status.ERROR,"csds" , "未找到被移交的项目!"));
				return responseBody;
			}
			User user = (User) getUserFromSession(request);
			projectTransfer.setBeforeUid(user.getId());
			projectTransfer.setBeforeDepartmentId(user.getDepartmentId());
			projectTransferService.applyProjectTransfer(projectTransfer);
			projectTransferService.setTransferProjectInRedis(cache, project);
			responseBody.setResult(new Result(Status.OK,"200" , "项目移交成功!"));
			_common_logger_.info(user.getRealName() + "移交项目成功[json]-" + projectTransfer);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,"err" , "项目移交失败!"));
			if(_common_logger_.isErrorEnabled()){
				_common_logger_.error("移交项目失败[josn]-" + projectTransfer);
			}
		}
		return responseBody;
	}
	
	
	/**
	 * 撤销项目移交
	 */
	@ResponseBody
	@RequestMapping(value = "/undoTransfer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectTransfer> undoTransfer(@RequestBody ProjectTransfer projectTransfer,
			HttpServletRequest request) {
		ResponseData<ProjectTransfer> responseBody = new ResponseData<ProjectTransfer>();
		if(projectTransfer.getProjectId() == null || projectTransfer.getUndoReason() == null){
			responseBody.setResult(new Result(Status.ERROR,"err" , "缺少必需参数!"));
			return responseBody;
		}
		try {
			User user = (User) getUserFromSession(request);
			List<ProjectTransfer> datas = projectTransferService.applyTransferData(projectTransfer.getProjectId());
			if(datas == null || datas.isEmpty()){
				responseBody.setResult(new Result(Status.ERROR,"err" , "没有找到相关的移交申请记录!"));
				return responseBody;
			}
			ProjectTransfer transfer = datas.get(0);
			projectTransferService.undoProjectTransfer(transfer);
			
			
			responseBody.setResult(new Result(Status.ERROR,"200" , "移交申请撤销成功!"));
			_common_logger_.info(user.getRealName() + "撤销移交申请成功[json]-" + transfer);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,"err" , "撤销移交申请失败!"));
			if(_common_logger_.isErrorEnabled()){
				_common_logger_.error("撤销移交申请失败[josn]-" + projectTransfer);
			}
		}
		return responseBody;
	}
	
	
	
	
}
