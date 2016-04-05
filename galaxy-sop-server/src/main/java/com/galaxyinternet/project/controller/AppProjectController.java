package com.galaxyinternet.project.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.exception.PlatformException;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.UserRoleService;
/**
 * ios对接接口
 * @author gxc
 *
 */
@Controller
@RequestMapping("/galaxy/aproject")
public class AppProjectController extends BaseControllerImpl<Project, ProjectBo> {
	
	final Logger logger = LoggerFactory.getLogger(AppProjectController.class);
	
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	@Override
	protected BaseService<Project> getBaseService() {
		return this.projectService;
	}	
	 /**
	  * 
	  * 供app端根据角色不同查看不同的项目列表
	  * 
	  * 
	  */
	   	@ResponseBody
		@RequestMapping(value = "/splapp", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseData<Project> searchAppProjectList(HttpServletRequest request, @RequestBody ProjectBo project) {
			ResponseData<Project> responseBody = new ResponseData<Project>();
			User user = (User) getUserFromSession(request);
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
			if(project.getProjectProgress()!=null&&project.getProjectProgress().equals("guanbi")){
				project.setProjectStatus("meetingResult:3");
				project.setProjectProgress(null);
			}
			if (roleIdList.contains(UserConstant.HHR)){
				project.setProjectDepartid(user.getDepartmentId());
			}
			if(!roleIdList.contains(UserConstant.DSZ) && !roleIdList.contains(UserConstant.CEO)&&!roleIdList.contains(UserConstant.HHR)){
				project.setCreateUid(user.getId());
			}
				
			try {						
				Page<Project>  pageProject = null;
				if(project.getAscOrDes()!=null&&project.getCascOrDes()!=null){	
					if(project.getAscOrDes().equals("desc")){
						Sort sort = new Sort(Direction.DESC,project.getCascOrDes());
						 pageProject = projectService.queryPageList(project,new PageRequest(project.getPageNum(), project.getPageSize(),sort));						
					}else if(project.getAscOrDes().equals("asc")){
						Sort sort = new Sort(Direction.ASC,project.getCascOrDes());
						pageProject= projectService.queryPageList(project,new PageRequest(project.getPageNum(), project.getPageSize(),sort));	
					}													
				}else{
					pageProject= projectService.queryPageList(project,new PageRequest(project.getPageNum(), project.getPageSize()));				
				}	
				if(pageProject!=null){
				    if(pageProject.getContent().isEmpty())	{
				    	List<Project> p=new ArrayList<Project>();
				    	pageProject.setContent(p);
				    	pageProject.setTotal((long)0);
				   }else{
					   for(int i=0;i<pageProject.getContent().size();i++){
			    			Project p=pageProject.getContent().get(i);
							Department Department=new Department();
							Department.setId(p.getProjectDepartid());
							Department queryOne = departmentService.queryOne(Department);
							if(queryOne!=null){
								p.setProjectCareerline(queryOne.getName());
							}else{
								p.setProjectCareerline("");
							}
					   }
				  }
				}
				responseBody.setPageList(pageProject);
				responseBody.setResult(new Result(Status.OK, ""));
				return responseBody;
			} catch (PlatformException e) {
				responseBody.setResult(new Result(Status.ERROR, "queryUserList faild"));
				if (logger.isErrorEnabled()) {
					logger.error("queryUserList ", e);
				}
			}
			return responseBody;
		}
	 

	  
	  
}
