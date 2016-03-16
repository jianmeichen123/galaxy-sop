package com.galaxyinternet.OperationLogs.controller;


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

import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.bo.OperationLogsBo;
import com.galaxyinternet.model.operationLog.OperationLogs;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.OperationLogsService;

@Controller
@RequestMapping("/galaxy/sop/operatlog")
public class OperationLogsController extends BaseControllerImpl<OperationLogs, OperationLogsBo> {
	
	final Logger logger = LoggerFactory.getLogger(OperationLogsController.class);
	
	@Autowired
	private OperationLogsService operationLogsService;
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	@Override
	protected BaseService<OperationLogs> getBaseService() {
		return this.operationLogsService;
	}
	
	
	/**
	 * 内部评审、 CEO评审 、 立项会、投决会  阶段
	 * 			查询个人项目下的会议记录
	 * @param    
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/query", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<OperationLogs> queryoperatelog(HttpServletRequest request,@RequestBody OperationLogsBo query ) {
		
		ResponseData<OperationLogs> responseBody = new ResponseData<OperationLogs>();
		
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
		try {
			if(query.getIsMine()!=null && query.getIsMine()==4) {
				query.setUid(user.getId());
			}
			if(query.getIsMyDepart()!=null && query.getIsMyDepart()==4) {
				query.setUserDepartid(user.getDepartmentId());
			}
			Page<OperationLogs> pageList = operationLogsService.queryPageList(query,new PageRequest(query.getPageNum(),query.getPageSize()));
			responseBody.setPageList(pageList);
			responseBody.setResult(new Result(Status.OK, ""));
			
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"queryoperatelog faild"));
			
			if(logger.isErrorEnabled()){
				logger.error("queryInterviewPageList ",e);
			}
		}
		
		return responseBody;
	}
	
	
	
	
	
}
