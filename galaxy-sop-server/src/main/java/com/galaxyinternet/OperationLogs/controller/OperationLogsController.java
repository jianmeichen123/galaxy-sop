package com.galaxyinternet.OperationLogs.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.bo.OperationLogsBo;
import com.galaxyinternet.model.common.ProgressLog;
import com.galaxyinternet.model.operationLog.OperationLogs;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.OperationLogsService;
import com.galaxyinternet.service.ProgressLogService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.UserRoleService;

@Controller
@RequestMapping("/galaxy/operatlog")
public class OperationLogsController extends BaseControllerImpl<OperationLogs, OperationLogsBo> {
	
	final Logger logger = LoggerFactory.getLogger(OperationLogsController.class);
	
	@Autowired
	private OperationLogsService operationLogsService;
	@Autowired
	private ProgressLogService progressLogService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Override
	protected BaseService<OperationLogs> getBaseService() {
		return this.operationLogsService;
	}
	
	
	/**
	 * 根据项目id查询该项目下操作日志
	 * @param    
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/query", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<OperationLogs> querylogByProId(HttpServletRequest request,@RequestBody OperationLogsBo query ) {
		
		ResponseData<OperationLogs> responseBody = new ResponseData<OperationLogs>();
		
		try {
			if(query.getProjectId() == null){   //传入project，仅查询该项目日志
				responseBody.setResult(new Result(Status.ERROR, null,"项目id为空"));
				logger.error("项目id为空 ");
				return responseBody;
			}
			
			Page<OperationLogs> pageList = operationLogsService.queryPageList(query,new PageRequest(query.getPageNum(),query.getPageSize()));
			responseBody.setPageList(pageList);
			responseBody.setResult(new Result(Status.OK, ""));
			
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"查询日志失败"));
			if(logger.isErrorEnabled()){
				logger.error("查询日志失败  ",e);
			}
			return responseBody;
		}
		
		return responseBody;
	}
	
	/**
	 * 根据项目id查询该项目下操作日志
	 * @param    
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryIdeaProgressLog", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProgressLog> queryIdeaProgressLog(HttpServletRequest request,@RequestBody ProgressLog query ) {
		
		ResponseData<ProgressLog> responseBody = new ResponseData<ProgressLog>();
		
		try {
			if(query.getRelatedId() == null){   //传入project，仅查询该项目日志
				responseBody.setResult(new Result(Status.ERROR, null,"创意id为空"));
				logger.error("创意id为空 ");
				return responseBody;
			}
			Page<ProgressLog> pageList = progressLogService.queryPageList(query,new PageRequest(query.getPageNum(),query.getPageSize()));
			responseBody.setPageList(pageList);
			responseBody.setResult(new Result(Status.OK, ""));
			
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"查询日志失败"));
			if(logger.isErrorEnabled()){
				logger.error("查询日志失败  ",e);
			}
			return responseBody;
		}
		
		return responseBody;
	}
	
	
	/**
	 * 高管：看到的是所有的操作日志（排序：时间倒序）
		投资经理: 看到的是自己(自己负责项目下得所有操作日志)
		投资合伙人：本部门下面所有项目的操作日志
	 * @param    
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/querybyrole", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<OperationLogs> querybyrole(HttpServletRequest request,@RequestBody OperationLogsBo query) {
		
		ResponseData<OperationLogs> responseBody = new ResponseData<OperationLogs>();
		
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
		try {
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());  //所有角色
			
			if(roleIdList!=null && !roleIdList.isEmpty()){
				if(roleIdList.contains(UserConstant.DSZ) || roleIdList.contains(UserConstant.CEO)){ //高管:董事长、CEO UserConstant.DSZ = 1L UserConstant.CEO = 2;
					query.setIsAll(1);
				}else if(roleIdList.contains(UserConstant.HHR)){  //投资线合伙人    UserConstant.HHR = 3
					query.setUserDepartid(user.getDepartmentId()); 
				}else if(roleIdList.contains(UserConstant.TZJL)){ //投资经理   UserConstant.TZJL = 4
					Project pro = new Project();
					pro.setCreateUid(user.getId());
					List<Project> perProList = projectService.queryList(pro); //登陆人的所有项目
					List<Long> proidList = new ArrayList<Long>();
					if(perProList!=null && !perProList.isEmpty()){
						for(Project ap : perProList){
							proidList.add(ap.getId());
						}
						query.setProjectIdList(proidList);
					}else{
						responseBody.setPageList(null);
						responseBody.setResult(new Result(Status.OK, ""));
						return responseBody;
					}
				}else{
					responseBody.setResult(new Result(Status.ERROR, null,"无权限查看日志"));
					logger.error(GSONUtil.toJson(user)+ " 无权限查看日志 ");
					return responseBody;
				}
			}else{
				responseBody.setResult(new Result(Status.ERROR, null,"角色表数据为空"));
				logger.error("角色表数据为空 ");
				return responseBody;
			}
		
			Page<OperationLogs> pageList = operationLogsService.queryPageList(query,new PageRequest(query.getPageNum(),query.getPageSize()));
			responseBody.setPageList(pageList);
			responseBody.setResult(new Result(Status.OK, ""));
			
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"查询日志失败"));
			if(logger.isErrorEnabled()){
				logger.error("查询日志失败  ",e);
			} 
		}
		return responseBody;
	}
	
	
	
	
	
	
	
	
	
}
