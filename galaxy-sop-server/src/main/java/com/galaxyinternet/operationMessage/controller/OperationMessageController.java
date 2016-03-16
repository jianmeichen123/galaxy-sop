package com.galaxyinternet.operationMessage.controller;


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

import com.galaxyinternet.bo.OperationMessageBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.exception.PlatformException;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.model.operationMessage.OperationMessage;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.platform.constant.PlatformConst;
import com.galaxyinternet.service.OperationMessageService;

@Controller
@RequestMapping("/galaxy/operationMessage")
public class OperationMessageController extends BaseControllerImpl<OperationMessage, OperationMessageBo> {
	
	final Logger logger = LoggerFactory.getLogger(OperationMessageController.class);
	
	@Autowired
	private OperationMessageService operationMessageService;
	
	
	@Override
	protected BaseService<OperationMessage> getBaseService() {
		return this.operationMessageService;
	}
	
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "operationMessage/index";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/queryList", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<OperationMessage> queryUserList(HttpServletRequest request,@RequestBody OperationMessageBo operationMessageBo) {
		ResponseData<OperationMessage> responseBody = new ResponseData<OperationMessage>();
		try {
			if(operationMessageBo.getModule()!=null&&operationMessageBo.getModule() != PlatformConst.MODULE_BROADCAST_MESSAGE.intValue()){
				User user = (User) getUserFromSession(request);
				operationMessageBo.setOperatorId(user.getId());
			}
			Page<OperationMessage> operationMessage = operationMessageService.queryPageList(operationMessageBo,new PageRequest(operationMessageBo.getPageNum(), operationMessageBo.getPageSize()));
			responseBody.setPageList(operationMessage);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;	
		} catch (PlatformException e) {
			responseBody.setResult(new Result(Status.ERROR, "queryUserList faild"));
			logger.error("queryUserList ", e);
		}
		return responseBody;
	}
	
	@ResponseBody
	@RequestMapping(value = "/remind", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<OperationMessageBo> remind(HttpServletRequest request) {
		ResponseData<OperationMessageBo> responseBody = new ResponseData<OperationMessageBo>();
		try {
			OperationMessageBo operationMessageBo = new OperationMessageBo();
			operationMessageBo.setCreatedTimeStart(DateUtil.getCurrentDate().getTime());
			User user = (User) getUserFromSession(request);
			operationMessageBo.setOperatorId(user.getId());
			Long count = operationMessageService.selectCount(operationMessageBo);
			operationMessageBo.setCount(count);
			operationMessageBo.setOperatorId(null);
			responseBody.setEntity(operationMessageBo);
			return responseBody;	
		} catch (PlatformException e) {
			responseBody.setResult(new Result(Status.ERROR, "queryUserList faild"));
			logger.error("queryUserList ", e);
		}
		return responseBody;
	}
	
	
}