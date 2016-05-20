package com.galaxyinternet.OperationLogs.controller;

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

import com.galaxyinternet.bo.AppOperationLogsBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.operationLog.OperationLogs;
import com.galaxyinternet.service.OperationLogsService;

@Controller
@RequestMapping("/galaxy/appOperatlog")
public class AppOperationLogsController extends BaseControllerImpl<OperationLogs, AppOperationLogsBo> {
	
	final Logger logger = LoggerFactory.getLogger(AppOperationLogsController.class);
	
	@Autowired
	private OperationLogsService operationLogsService;

	@Override
	protected BaseService<OperationLogs> getBaseService() {
		// TODO Auto-generated method stub
		return this.operationLogsService;
	}
	
	/**
	 * 获取项目动态信息，记录数可定制获取（无分页）
	 * @param    
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/multQuery", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<OperationLogs> querylogByMultiCondition(HttpServletRequest request,@RequestBody AppOperationLogsBo appQuery ) {	
		ResponseData<OperationLogs> responseBody = new ResponseData<OperationLogs>();	
		try {
			if(appQuery.getProjectIdList()==null || appQuery.getProjectIdList().size()<=0){
				responseBody.setResult(new Result(Status.ERROR, null,"项目id为空"));
				logger.error("[异常]接口调用，传参项目id缺失...");
				return responseBody;
			}
			List<OperationLogs> list =   operationLogsService.queryList(appQuery);
			responseBody.setEntityList(list);
			responseBody.setResult(new Result(Status.OK, ""));
			
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"查询项目动态失败"));
			logger.error("[异常]项目动态查询异常", e);
			return responseBody;
		}		
		return responseBody;
	}
	
	
	/**
	 * 获取项目动态信息，记录数可定制获取（分页）
	 * @param    
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pageQuery", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<OperationLogs> queryPagelogByMultiCondition(HttpServletRequest request,@RequestBody AppOperationLogsBo appQuery ) {	
		ResponseData<OperationLogs> responseBody = new ResponseData<OperationLogs>();	
		try {
			if(appQuery.getProjectIdList()==null || appQuery.getProjectIdList().size()<=0){
				responseBody.setResult(new Result(Status.ERROR, null,"项目id为空"));
				logger.error("[异常]接口调用，传参项目id缺失...");
				return responseBody;
			}
//			OperationLogs query = new OperationLogs();
//			List<Long> list = appQuery.getProjectIdList();
//			query.setProjectId(list.get(0).);

			Page<OperationLogs> pageList = operationLogsService.queryPageList(appQuery,new PageRequest(appQuery.getPageNum(),appQuery.getPageSize()));
			responseBody.setPageList(pageList);
			responseBody.setResult(new Result(Status.OK, ""));
			
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"查询项目动态失败"));
			logger.error("[异常]项目动态查询异常", e);
			return responseBody;
		}		
		return responseBody;
	}

}
