package com.galaxyinternet.project.controller;
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
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.ProjectShares;
import com.galaxyinternet.service.ProjectSharesService;

@Controller
@RequestMapping("/galaxy/projectShares")
public class ProjectSharesController extends BaseControllerImpl<ProjectShares, ProjectSharesBo> {

	final Logger logger = LoggerFactory.getLogger(ProjectSharesController.class);
	
	@Autowired
	private ProjectSharesService projectSharesService;
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
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
	@RequestMapping(value = "/addProjectShares", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData<ProjectShares> addProjectShares(@RequestBody ProjectShares entity) {
		ResponseData<ProjectShares> responseBody = new ResponseData<ProjectShares>();
		
		//判断项目ID空判断
		if(StringUtils.isEmpty(String.valueOf(entity.getProjectId()))){
			responseBody.setResult(new Result(Status.ERROR, "项目ID不能为空!"));
			return responseBody;
		}
		//类型空判断
		if(StringUtils.isEmpty(entity.getSharesType())){
			responseBody.setResult(new Result(Status.ERROR, "类型不能为空!"));
			return responseBody;
		}
		//所有权人空判断
	    if(StringUtils.isEmpty(entity.getSharesOwner())){
	    	responseBody.setResult(new Result(Status.ERROR, "所有权人不能为空!"));
			return responseBody;
	    }
	    //股份占比空判断
	    if(StringUtils.isEmpty(entity.getSharesRatio().toString())){
	    	responseBody.setResult(new Result(Status.ERROR, "股份占比不能为空!"));
			return responseBody;
	    }
	    try{
	    	Long id = getBaseService().insert(entity);
			responseBody.setId(id);
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
	@RequestMapping(value = "/selectProjectShares", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public ResponseData<ProjectShares> selectProjectShares(ProjectShares query, PageRequest pageable) {
		
		ResponseData<ProjectShares> responseBody = new ResponseData<ProjectShares>();
		Page<ProjectShares> pageList = projectSharesService.queryPageList(query, pageable);
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
	public ResponseData<ProjectShares> updateProjectShares(ProjectShares shares){
		
		//获取角色权限，有权限才会进行修改
		ResponseData<ProjectShares> responseBody = new ResponseData<ProjectShares>();
		if(shares.getId() == null){
			responseBody.setResult(new Result(Status.OK, "ID不能为空!"));
			return responseBody;
		}
		try{
			projectSharesService.updateById(shares);
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
	@RequestMapping(value = "/deleteProjectShares/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectShares> deleteProjectShares(@PathVariable Long id){
		
		//角色删除判断-待写
		ResponseData<ProjectShares> responseBody = new ResponseData<ProjectShares>();
		Result result = null;
		if (id == null) {
			logger.error("要删除的ID号为null或空字符串！对象：{}", path.getEntityName());
			result = new Result(Status.ERROR, "没有传入要删除的ID号！");
			responseBody.setResult(result);
			return responseBody;
		}
		int count = projectSharesService.deleteById(id);
		if (count == 0) {
			result = new Result(Status.ERROR, "要删除的记录不存在！");
			responseBody.setResult(result);
			return responseBody;
		}
		responseBody.setResult(new Result(Status.OK, count));
		return responseBody;
	}
	
	
	

}
