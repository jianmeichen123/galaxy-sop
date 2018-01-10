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
import com.galaxyinternet.common.annotation.LogType;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.dictEnum.DictEnum;
import com.galaxyinternet.common.dictEnum.DictEnum.MessageType;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.project.ProjectTransfer;
import com.galaxyinternet.model.user.User;
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
	/*	if (projectTransfer.getProjectId() == null || projectTransfer.getAfterDepartmentId() == null || projectTransfer.getAfterUid() == null)
		{
			data.setResult(new Result(Status.ERROR, "csds", "必要的参数丢失!"));
			return data;
		}*/
		
		if (projectTransfer.getProjectIds()== null || projectTransfer.getAfterDepartmentId() == null || projectTransfer.getAfterUid() == null)
		{
			data.setResult(new Result(Status.ERROR, "csds", "必要的参数丢失!"));
			return data;
		}
		String[] arrprojectId=projectTransfer.getProjectIds().split(",");
		
		User user = (User) getUserFromSession(request);
		if (user.getId().longValue() == projectTransfer.getAfterUid().longValue())
		{
			data.setResult(new Result(Status.ERROR, "err", "不能将项目移交给本人！"));
			return data;
		}
		User u = (User) getUserFromSession(request);
		try
		{
			Project project =new Project();
			List<String> projectList = java.util.Arrays.asList(arrprojectId);
			project.setProejctIdList(projectList);
			List<Project> queryList = projectService.queryList(project);
			if(null==queryList||queryList.size()!=projectList.size())
			{
				data.setResult(new Result(Status.ERROR, "csds", "部分项目不存在!"));
				return data;
			}
			for(Project p:queryList){
				ProjectTransfer  projectTransferNew=new ProjectTransfer();
				String realName = (String)cache.hget(PlatformConst.CACHE_PREFIX_USER+projectTransfer.getAfterUid(), "realName");
				projectTransferNew.setBeforeUid(p.getCreateUid());
				projectTransferNew.setBeforeDepartmentId(p.getProjectDepartid());
				projectTransferNew.setRecordStatus(SopConstatnts.TransferStatus._receive_status_);
				projectTransferNew.setAfterUid(projectTransfer.getAfterUid());
				projectTransferNew.setRefuseReason(null!=projectTransfer.getRefuseReason()?projectTransfer.getRefuseReason():"");
				projectTransferNew.setAfterDepartmentId(projectTransfer.getAfterDepartmentId());
				projectTransferNew.setProjectId(p.getId());
				projectTransferNew.setOperateId(u.getId());
				//0位移交，1位指派
				if(projectTransfer.getOperateType().equals("transfer")){
					projectTransferNew.setOperateType("0");
				}else if(projectTransfer.getOperateType().equals("assign")){
					projectTransferNew.setOperateType("1");
				}
				projectTransferService.insert(projectTransferNew);
				projectTransferService.receiveProjectTransfer(projectTransferNew, projectTransfer.getAfterUid(), realName, projectTransfer.getAfterDepartmentId());
				_common_logger_.info(user.getRealName() + "移交项目成功[json]-" + projectTransfer);
				ControllerUtils.setRequestParamsForMessageTip(request, p.getProjectName(), p.getId(), MessageType.TRANSFER_PROJECT.getCode(), true, u,
						projectTransfer.getTransferReason(), DictEnum.projectProgress.getNameByCode(p.getProjectProgress()));
		
			     }
			    data.setResult(new Result(Status.OK, "200", "项目移交成功!"));
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
