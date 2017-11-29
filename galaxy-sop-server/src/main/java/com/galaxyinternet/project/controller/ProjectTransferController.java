package com.galaxyinternet.project.controller;
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
import com.galaxyinternet.common.annotation.LogType;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.dictEnum.DictEnum;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.project.ProjectTransfer;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.operationMessage.handler.ProjectTransferMessageHandler;
import com.galaxyinternet.platform.constant.PlatformConst;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.ProjectTransferService;
import com.galaxyinternet.service.UserService;
import com.galaxyinternet.utils.SopConstatnts;

@Controller
@RequestMapping("/galaxy/projectTransfer")
public class ProjectTransferController extends BaseControllerImpl<ProjectTransfer, ProjectTransferBo> {
	private final static Logger _common_logger_ = LoggerFactory.getLogger(ProjectTransferController.class);
	
    @Autowired
	com.galaxyinternet.framework.cache.Cache cache;

	@Autowired
	private ProjectTransferService projectTransferService;
	
	@Autowired
	private ProjectService projectService;
	
	
	@Autowired
	private UserService userService;
	
	@Override
	protected BaseService<ProjectTransfer> getBaseService() {
		return this.projectTransferService;
	}
	
	
	/**
	 * 页面跳转至移交项目
	 */
	@RequestMapping(value = "/toProjectTransfer", method = RequestMethod.GET)
	public String toProjectTransfer() {
		return "project/projectTransfer/project_transfer";
	}
	
	/**
	 * 项目移交
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG, LogType.MESSAGE })
	@ResponseBody
	@RequestMapping(value = "/applyTransfer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectTransfer> applyTransfer(@RequestBody ProjectTransfer projectTransfer, HttpServletRequest request)
	{
		ResponseData<ProjectTransfer> data = new ResponseData<ProjectTransfer>();
		if (projectTransfer.getProjectId() == null || projectTransfer.getAfterDepartmentId() == null || projectTransfer.getAfterUid() == null)
		{
			data.setResult(new Result(Status.ERROR, "csds", "必要的参数丢失!"));
			return data;
		}
		User user = (User) getUserFromSession(request);
		if (user.getId().longValue() == projectTransfer.getAfterUid().longValue())
		{
			data.setResult(new Result(Status.ERROR, "err", "不能将项目移交给本人！"));
			return data;
		}
		try
		{
			Project project = projectService.queryById(projectTransfer.getProjectId());
			if (project == null)
			{
				data.setResult(new Result(Status.ERROR, "csds", "未找到被移交的项目!"));
				return data;
			}
			String realName = (String)cache.hget(PlatformConst.CACHE_PREFIX_USER+projectTransfer.getAfterUid(), "realName");
			projectTransfer.setBeforeUid(project.getCreateUid());
			projectTransfer.setBeforeDepartmentId(project.getProjectDepartid());
			projectTransfer.setRecordStatus(SopConstatnts.TransferStatus._receive_status_);
			projectTransferService.insert(projectTransfer);
			projectTransferService.receiveProjectTransfer(projectTransfer, projectTransfer.getAfterUid(), realName, projectTransfer.getAfterDepartmentId());
			
			data.setResult(new Result(Status.OK, "200", "项目移交成功!"));
			_common_logger_.info(user.getRealName() + "移交项目成功[json]-" + projectTransfer);
			Long userid = projectTransfer.getAfterUid();
			User u = userService.queryById(userid);
			ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId(), ProjectTransferMessageHandler.MESSAGE_TYPE_APPLY, true, u,
					projectTransfer.getTransferReason(), DictEnum.projectProgress.getNameByCode(project.getProjectProgress()));
		} catch (Exception e)
		{
			data.setResult(new Result(Status.ERROR, "err", "项目移交失败!"));
			if (_common_logger_.isErrorEnabled())
			{
				_common_logger_.error("移交项目失败[josn]-" + projectTransfer);
			}
		}
		return data;
	}
}
