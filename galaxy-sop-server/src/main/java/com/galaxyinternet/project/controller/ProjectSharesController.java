package com.galaxyinternet.project.controller;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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

import com.galaxyinternet.bo.project.ProjectSharesBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.project.ProjectShares;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.ProjectSharesService;

@Controller
@RequestMapping("/galaxy/projectShares")
public class ProjectSharesController extends BaseControllerImpl<ProjectShares, ProjectSharesBo> {

	final Logger logger = LoggerFactory.getLogger(ProjectSharesController.class);
	
	@Autowired
	private ProjectSharesService projectSharesService;
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;

	@Autowired
	private ProjectService projectService;
	
	@Override
	protected BaseService<ProjectShares> getBaseService() {
		// TODO Auto-generated method stub
		return this.projectSharesService;
	}
	
	/***
	 * 添加项目股权结构
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/addProjectShares",  method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData<ProjectShares> addProjectShares(@RequestBody ProjectShares entity,HttpServletRequest request) {
		ResponseData<ProjectShares> responseBody = new ResponseData<ProjectShares>();
		
		if(StringUtils.isEmpty(String.valueOf(entity.getProjectId()))){
			responseBody.setResult(new Result(Status.ERROR, "必要的参数丢失!"));
			return responseBody;
		}
		Object obj = request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}
		User user = (User) obj;
		Project p = projectService.queryById(entity.getProjectId());
		//项目创建者用户ID与当前登录人ID是否一样
		if(p != null && user.getId().doubleValue() != p.getCreateUid().doubleValue()){
			responseBody.setResult(new Result(Status.ERROR, "没有权限为股权结构!"));
			return responseBody;
		}
	    try{
	    	projectSharesService.insert(entity);
			responseBody.setResult(new Result(Status.OK,"添加股权结构成功!"));
	    }catch(Exception e){
	    	logger.error("添加股权结构失败!", e);
	    	responseBody.setResult(new Result(Status.ERROR, "添加股权结构失败!"));
	    }
		return responseBody;
	}
	
	/***
	 * 股权结构列表
	 * @param query
	 * @param pageable
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectProjectShares", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public ResponseData<ProjectShares> selectProjectShares(HttpServletRequest request,@RequestBody ProjectShares query) {
		
		ResponseData<ProjectShares> responseBody = new ResponseData<ProjectShares>();
		Page<ProjectShares> pageList = projectSharesService.queryPageList(query, new PageRequest(query.getPageNum(), query.getPageSize()));
		responseBody.setPageList(pageList);
		return responseBody;
		
	}
	
	/***
	 * 修改股权结构-有权限的角色可修改
	 * @param shares
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateProjectShares", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectShares> updateProjectShares(@RequestBody ProjectShares shares,HttpServletRequest request){
		
		ResponseData<ProjectShares> responseBody = new ResponseData<ProjectShares>();
		if(shares == null || shares.getId() == null || shares.getProjectId() == null){
			responseBody.setResult(new Result(Status.ERROR, "必要的参数丢失!"));
			return responseBody;
		}
		Object obj = request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}
		User user = (User) obj;
		Project p = projectService.queryById(shares.getProjectId());
		//项目创建者用户ID与当前登录人ID是否一样
		if(p != null && user.getId().doubleValue() != p.getCreateUid().doubleValue()){
			responseBody.setResult(new Result(Status.ERROR, "没有权限修改该项目的团队成员信息!"));
			return responseBody;
		}
		try{
			projectSharesService.updateById(shares);
			responseBody.setResult(new Result(Status.OK,"修改股权结构成功!"));
		}catch(Exception e){
			logger.error("修改股权结构失败!ID="+shares.getId(), e);
			responseBody.setResult(new Result(Status.ERROR,"修改股权结构失败!"));
		}
		return responseBody;
	}
	
	/***
	 * 删除股权结构
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteProjectShares/{id}/{projectId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectShares> deleteProjectShares(@PathVariable("id") Long id,@PathVariable("projectId") Long projectId,HttpServletRequest request){
		
		//角色删除判断-待写
		ResponseData<ProjectShares> responseBody = new ResponseData<ProjectShares>();
		if(projectId == null){
			responseBody.setResult(new Result(Status.ERROR, "必要的参数丢失!"));
			return responseBody;
		}
		Object obj = request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}
		User user = (User) obj;
		Project p = projectService.queryById(projectId);
		//项目创建者用户ID与当前登录人ID是否一样
		if(p != null && user.getId().doubleValue() != p.getCreateUid().doubleValue()){
			responseBody.setResult(new Result(Status.ERROR, "没有权限删除该项目的团队成员!"));
			return responseBody;
		}
		int count = projectSharesService.deleteById(id);
		if (count == 0) {
			responseBody.setResult(new Result(Status.ERROR,"要删除的记录不存在！"));
			return responseBody;
		}
		responseBody.setResult(new Result(Status.OK,"团队成员删除成功!"));
		return responseBody;
	}
	
	
	/**
	 * 添加股权结构
	 * @return
	 */
	@RequestMapping(value = "/addShares", method = RequestMethod.GET)
	public String addSharesProject() {
		return "project/addSharesProject";
	}
	
	/**
	 * 跳转到修改股权页面
	 * @return
	 */
	@RequestMapping(value = "/updateShare/{id}", method = RequestMethod.GET)
	public String updateProject(@PathVariable("id") Long id,HttpServletRequest request) {
		
		return "project/updateSharesProject";
	}
	/***
	 * 股权结构列表
	 * @param query
	 * @param pageable
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectEntityShare/{id}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public ResponseData<ProjectShares> selectEntityShare(
			HttpServletRequest request,@PathVariable("id") Long id) {
		
		ResponseData<ProjectShares> responseBody = new ResponseData<ProjectShares>();
		ProjectShares share = projectSharesService.queryById(id);
		responseBody.setEntity(share);
		return responseBody;
		
	}

}
