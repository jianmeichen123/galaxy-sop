package com.galaxyinternet.operationMessage.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.galaxyinternet.bo.chart.ChartDataBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.utils.StaticParamService;
import com.galaxyinternet.exception.PlatformException;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.constants.UserConstant;
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
import com.galaxyinternet.service.UserRoleService;

@Controller
@RequestMapping("/galaxy/operationMessage")
public class OperationMessageController extends BaseControllerImpl<OperationMessage, OperationMessageBo> {
	
	final Logger logger = LoggerFactory.getLogger(OperationMessageController.class);
	
	@Autowired
	private OperationMessageService operationMessageService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private StaticParamService staticParamService;
	

	@Autowired
	Cache cache;
	
	@Override
	protected BaseService<OperationMessage> getBaseService() {
		return this.operationMessageService;
	}
	
	//点击消息链接时，刷新缓存时间
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
		User user = (User) getUserFromSession(request);
		if(user != null){
			cache.set(PlatformConst.OPERATIO_NMESSAGE_TIME+user.getId(),System.currentTimeMillis());
		}
		return "operationMessage/index";
	}
	
	@ResponseBody
	@RequestMapping(value = "/clear", method = RequestMethod.POST)
	public String clear(HttpServletRequest request) {
		String userId = getUserId(request);
		if(userId != null){
			cache.set(PlatformConst.OPERATIO_NMESSAGE_TIME+userId,System.currentTimeMillis());
			return "ok";
		}
		return "error";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/queryList", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<OperationMessage> queryUserList(HttpServletRequest request,@RequestBody OperationMessageBo operationMessageBo) {
		ResponseData<OperationMessage> responseBody = new ResponseData<OperationMessage>();
		Result result = new Result();
		try {
			User user = (User) getUserFromSession(request);
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
			
			/*if(operationMessageBo.getModule()!=null&&operationMessageBo.getModule() != PlatformConst.MODULE_BROADCAST_MESSAGE.intValue()){
				operationMessageBo.setBelongUid(user.getId());
			}*/
			initquery(operationMessageBo,user,roleIdList);
			Page<OperationMessage> operationMessage = new Page<OperationMessage>(new ArrayList<OperationMessage>(),0l);;
			if(operationMessageBo.getFlag()){
				 operationMessage = operationMessageService.queryPageList(operationMessageBo,new PageRequest(operationMessageBo.getPageNum(), operationMessageBo.getPageSize()));
			}
			responseBody.setPageList(operationMessage);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;	
		} catch (PlatformException e) {
			result.addError(e.getMessage(), e.getCode()+"");
			logger.error("queryUserList ", e);
		}
		return responseBody;
	}
	
	
	//实时统计消息数
	@ResponseBody
	@RequestMapping(value = "/remind", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<OperationMessageBo> remind(HttpServletRequest request) {
		ResponseData<OperationMessageBo> responseBody = new ResponseData<OperationMessageBo>();
		Result result = new Result();
		try {
			User user = (User) getUserFromSession(request);
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
			
 			OperationMessageBo operationMessageBo = new OperationMessageBo();
			Long lastTime = (Long) cache.get(PlatformConst.OPERATIO_NMESSAGE_TIME+getUserId(request));
			if(lastTime == null){
				lastTime= DateUtil.getCurrentDate().getTime();
			}
			operationMessageBo.setCreatedTimeStart(lastTime);
			initquery(operationMessageBo,user,roleIdList);
			
			/*operationMessageBo.setOperatorId(user.getId());
			operationMessageBo.setModule(PlatformConst.MODULE_BROADCAST_MESSAGE);//1
			 */			
			Long count = 0l;
			if(operationMessageBo.getFlag()){
				 count = operationMessageService.selectCount(operationMessageBo);
			}
			operationMessageBo.setCount(count);
			operationMessageBo.setOperatorId(null);
			responseBody.setEntity(operationMessageBo);
			return responseBody;	
		} catch (PlatformException e) {
			result.addError(e.getMessage(), e.getCode()+"");
			logger.error("queryUserList ", e);
		}
		return responseBody;
	}
	
	
	public void initquery(OperationMessageBo operationMessageBo,User user,List<Long> roleIdList){
		
		boolean flat = true;
		
		//查看消息资格判断
		if(roleIdList.contains(UserConstant.DSZ) || roleIdList.contains(UserConstant.CEO)
				|| roleIdList.contains(UserConstant.DMS) || roleIdList.contains(UserConstant.CEOMS)
				|| roleIdList.contains(UserConstant.HHR)
				|| roleIdList.contains(UserConstant.TZJL)
				|| roleIdList.contains(UserConstant.HRZJ) || roleIdList.contains(UserConstant.FWZJ) || roleIdList.contains(UserConstant.CWZJ)
				|| roleIdList.contains(UserConstant.HRJL) || roleIdList.contains(UserConstant.FWJL) || roleIdList.contains(UserConstant.CWJL)
				|| roleIdList.contains(UserConstant.YYFZR) || roleIdList.contains(UserConstant.THYY)
				){     
			operationMessageBo.setBelongDepartmentId(user.getDepartmentId());
			operationMessageBo.setBelongUid(user.getId());
		}else{
			 flat = false;
		}
		 
		//查看消息范围
		if(flat){
			Map<String,List<String>> typelist = StaticParamService.getRoleTypeList(roleIdList, staticParamService);
			
			if(typelist==null || typelist.isEmpty()){
				flat = false;
			}else{
				if(typelist.get("inAll")!= null && !typelist.get("inAll").isEmpty()){
					operationMessageBo.setInAll(typelist.get("inAll"));
				}
				if(typelist.get("inPer")!= null && !typelist.get("inPer").isEmpty()){
					operationMessageBo.setInPer(typelist.get("inPer"));
				}
				if(typelist.get("inPat")!= null && !typelist.get("inPat").isEmpty()){
					operationMessageBo.setInPat(typelist.get("inPat"));
				}
			}
		}
		operationMessageBo.setFlag(flat);
	}
	
	
	
}