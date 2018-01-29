package com.galaxyinternet.project.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.bo.project.ProjectTransferBo;
import com.galaxyinternet.common.annotation.GalaxyResource;
import com.galaxyinternet.common.annotation.LogType;
import com.galaxyinternet.common.annotation.RecordType;
import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.project.ProjectTransfer;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.platform.constant.PlatformConst;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.ProjectTransferService;
import com.galaxyinternet.utils.SopConstatnts;

@Controller
@RequestMapping("/galaxy/projectTransfer")
public class ProjectTransferController extends BaseControllerImpl<ProjectTransfer, ProjectTransferBo>
{
	private final static Logger _common_logger_ = LoggerFactory.getLogger(ProjectTransferController.class);

	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;

	@Autowired
	private ProjectTransferService projectTransferService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private DepartmentService departmentService;

	@Override
	protected BaseService<ProjectTransfer> getBaseService()
	{
		return this.projectTransferService;
	}

	/**
	 * 页面跳转至移交项目
	 */
	@RequestMapping(value = "/toProjectTransfer", method = RequestMethod.GET)
	public String toProjectTransfer()
	{
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
		/*
		 * if (projectTransfer.getProjectId() == null ||
		 * projectTransfer.getAfterDepartmentId() == null ||
		 * projectTransfer.getAfterUid() == null) { data.setResult(new
		 * Result(Status.ERROR, "csds", "必要的参数丢失!")); return data; }
		 */

		if (projectTransfer.getProjectIds() == null || projectTransfer.getAfterDepartmentId() == null || projectTransfer.getAfterUid() == null)
		{
			data.setResult(new Result(Status.ERROR, "csds", "必要的参数丢失!"));
			return data;
		}
		String[] arrprojectId = projectTransfer.getProjectIds().split(",");

		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if (user.getId().longValue() == projectTransfer.getAfterUid().longValue())
		{
			data.setResult(new Result(Status.ERROR, "err", "不能将项目移交给本人！"));
			return data;
		}
	//	Project queryById = projectService.queryById(projectTransfer.getProjectId());
		User u = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		try
		{
			Project project = new Project();
			List<String> projectList = java.util.Arrays.asList(arrprojectId);
			project.setProejctIdList(projectList);
			List<Project> queryList = projectService.queryList(project);
			if (null == queryList || queryList.size() != projectList.size())
			{
				if(projectList.size()>=1){
					data.setResult(new Result(Status.ERROR, "csds", "部分项目已被删除，请刷新列表重新选择!"));
				}else{
					data.setResult(new Result(Status.ERROR, "csds", "项目已被删除，请刷新列表重新选择!"));
				}
				
				return data;
			}
			if(projectTransfer.getOperateType().equals("assign")){
				for(Project p:queryList){
					if(p.getCreateUid().longValue()==projectTransfer.getAfterUid().longValue()){
						data.setResult(new Result(Status.ERROR, "err", "不能将项目指派给项目负责人！"));
						return data;
					}
				}
			}
		
			UrlNumber urlNumber = null;
			List<Long> projectIds = new ArrayList<>(queryList.size());
			for (Project p : queryList)
			{
				projectIds.add(p.getId());
				ProjectTransfer projectTransferNew = new ProjectTransfer();
				String realName = (String) cache.hget(PlatformConst.CACHE_PREFIX_USER + projectTransfer.getAfterUid(), "realName");
				projectTransferNew.setBeforeUid(p.getCreateUid());
				projectTransferNew.setBeforeDepartmentId(p.getProjectDepartid());
				projectTransferNew.setRecordStatus(SopConstatnts.TransferStatus._receive_status_);
				projectTransferNew.setAfterUid(projectTransfer.getAfterUid());
				projectTransferNew.setRefuseReason(null != projectTransfer.getRefuseReason() ? projectTransfer.getRefuseReason() : "");
				projectTransferNew.setAfterDepartmentId(projectTransfer.getAfterDepartmentId());
				projectTransferNew.setProjectId(p.getId());
				projectTransferNew.setOperateId(u.getId());
				// 0位移交，1位指派
				if (projectTransfer.getOperateType().equals("transfer"))
				{
					projectTransferNew.setOperateType("0");
					urlNumber = UrlNumber.one;
				} else if (projectTransfer.getOperateType().equals("assign"))
				{
					projectTransferNew.setOperateType("1");
					urlNumber = UrlNumber.two;
				}
				projectTransferService.insert(projectTransferNew);
				projectTransferService.receiveProjectTransfer(projectTransferNew, projectTransfer.getAfterUid(), realName, projectTransfer.getAfterDepartmentId());

				_common_logger_.info(user.getRealName() + "移交项目成功[json]-" + projectTransfer);

			}
			Map<String, Object> params = new HashMap<>();
			params.put(PlatformConst.REQUEST_SCOPE_RECORD_IDS, projectIds);
			params.put(PlatformConst.REQUEST_SCOPE_URL_NUMBER, urlNumber.name());
			params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_REASON, projectTransfer.getTransferReason());
			params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_RECORD_TYPE, RecordType.PROJECT.getType());
			params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_RECORD_IDS, projectIds);
			ControllerUtils.setRequestParamsForMessageTip(request, params);
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

	/**
	 * 项目列表查询
	 * 
	 * @version 2016-06-21
	 * @author yangshuhua
	 */
	@GalaxyResource(name="project_batch_transfer")
	@ResponseBody
	@RequestMapping(value = "/searchProjectTansfer", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> searchProjectAssign(HttpServletRequest request, @RequestBody ProjectBo project)
	{
		ResponseData<Project> responseBody = new ResponseData<Project>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		project.setProperty("updated_time");
		project.setDirection("desc");
		// 有搜索条件则不启动默认筛选
		if (project.getCreateUid() == null && project.getProjectDepartid() == null)
		{
			List<Long> roleIdList = user.getRoleIds();
			if (roleIdList.contains(UserConstant.TZJL))
			{
				project.setCreateUid(user.getId());
			} else if (roleIdList.contains(UserConstant.HHR))
			{
				project.setProjectDepartid(user.getDepartmentId());
			} else
			{

			}
		}
		// 搜索全部时,传过来参数值为0,此时要转换为查询全部
		project.setCreateUid((project.getCreateUid() != null && project.getCreateUid().longValue() == 0L) ? null : project.getCreateUid());
		project.setProjectDepartid((project.getProjectDepartid() != null && project.getProjectDepartid().longValue() == 0L) ? null : project.getProjectDepartid());
		if (null != project.getProperty() && project.getProperty().equals("project_progress"))
		{
			project.setProperty(" CAST(REPLACE(project_progress,'projectProgress---','')  AS SIGNED) ");

		}
		if (project.getProjectPerson() != null)
			project.setProjectPerson(project.getProjectPerson().toUpperCase());
		List<Department> departmentList = departmentService.queryAll();
		Page<Project> pageProject = projectService.queryPageList(project,
				new PageRequest(project.getPageNum(), project.getPageSize(), Direction.fromString(project.getDirection()), project.getProperty()));
		// 封装事业线数据
		List<Project> projectList = new ArrayList<Project>();
		for (Project p : pageProject.getContent())
		{
			projectList.add(p);
			for (Department d : departmentList)
			{
				if (p.getProjectDepartid().longValue() == d.getId().longValue())
				{
					p.setProjectCareerline(d.getName());
					break;
				}
			}
			// 项目来源
			if (p.getFaFlag() != null)
			{
				if (NumberUtils.isNumber(p.getFaFlag()))
				{
					Object dictVal = cache.hget(SopConstant.TITLE_DICT_KEY_PREFIX + p.getFaFlag(), "name");
					if (dictVal != null)
					{
						p.setFaFlagStr((String) dictVal);
					}
				} else
				{
					p.setFaFlagStr(p.getFaFlag());
				}
			}
		}
		pageProject.setContent(projectList);
		responseBody.setPageList(pageProject);
		responseBody.putAttachmentItem("user", user);
		responseBody.setResult(new Result(Status.OK, ""));
		return responseBody;
	}

	/**
	 * 项目列表查询
	 * 
	 * @version 2016-06-21
	 * @author yangshuhua
	 */
	@GalaxyResource(name="project_batch_assign")
	@ResponseBody
	@RequestMapping(value = "/searchProjectAssigin", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> searchProjectAssigin(HttpServletRequest request, @RequestBody ProjectBo project)
	{
		ResponseData<Project> responseBody = new ResponseData<Project>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		project.setProperty("updated_time");
		project.setDirection("desc");
		// 有搜索条件则不启动默认筛选
		if (project.getCreateUid() == null && project.getProjectDepartid() == null)
		{
			List<Long> roleIdList = user.getRoleIds();
			if (roleIdList.contains(UserConstant.TZJL))
			{
				project.setCreateUid(user.getId());
			} else if (roleIdList.contains(UserConstant.HHR))
			{
				project.setProjectDepartid(user.getDepartmentId());
			} else
			{

			}
		}
		// 搜索全部时,传过来参数值为0,此时要转换为查询全部
		project.setCreateUid((project.getCreateUid() != null && project.getCreateUid().longValue() == 0L) ? null : project.getCreateUid());
		project.setProjectDepartid((project.getProjectDepartid() != null && project.getProjectDepartid().longValue() == 0L) ? null : project.getProjectDepartid());
		if (null != project.getProperty() && project.getProperty().equals("project_progress"))
		{
			project.setProperty(" CAST(REPLACE(project_progress,'projectProgress---','')  AS SIGNED) ");

		}
		if (project.getProjectPerson() != null)
			project.setProjectPerson(project.getProjectPerson().toUpperCase());
		List<Department> departmentList = departmentService.queryAll();
		Page<Project> pageProject = projectService.queryPageList(project,
				new PageRequest(project.getPageNum(), project.getPageSize(), Direction.fromString(project.getDirection()), project.getProperty()));
		// 封装事业线数据
		List<Project> projectList = new ArrayList<Project>();
		for (Project p : pageProject.getContent())
		{
			projectList.add(p);
			for (Department d : departmentList)
			{
				if (p.getProjectDepartid().longValue() == d.getId().longValue())
				{
					p.setProjectCareerline(d.getName());
					break;
				}
			}
			// 项目来源
			if (p.getFaFlag() != null)
			{
				if (NumberUtils.isNumber(p.getFaFlag()))
				{
					Object dictVal = cache.hget(SopConstant.TITLE_DICT_KEY_PREFIX + p.getFaFlag(), "name");
					if (dictVal != null)
					{
						p.setFaFlagStr((String) dictVal);
					}
				} else
				{
					p.setFaFlagStr(p.getFaFlag());
				}
			}
		}
		pageProject.setContent(projectList);
		responseBody.setPageList(pageProject);
		responseBody.putAttachmentItem("user", user);
		responseBody.setResult(new Result(Status.OK, ""));
		return responseBody;
	}
}
