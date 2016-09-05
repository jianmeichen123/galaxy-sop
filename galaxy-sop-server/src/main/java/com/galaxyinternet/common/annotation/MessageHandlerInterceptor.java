package com.galaxyinternet.common.annotation;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.thread.GalaxyThreadPool;
import com.galaxyinternet.iosMessage.IosMessageGenerator;
import com.galaxyinternet.iosMessage.operType.IosMeaageOperation;
import com.galaxyinternet.model.common.ProgressLog;
import com.galaxyinternet.model.iosMessage.IosMessage;
import com.galaxyinternet.model.operationLog.OperationLogType;
import com.galaxyinternet.model.operationLog.OperationLogs;
import com.galaxyinternet.model.operationMessage.OperationMessage;
import com.galaxyinternet.model.operationMessage.OperationType;
import com.galaxyinternet.model.sopfile.SopParentFile;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.operationMessage.MessageGenerator;
import com.galaxyinternet.operationMessage.handler.MeetMessageHandler;
import com.galaxyinternet.operationMessage.handler.SopFileMessageHandler;
import com.galaxyinternet.operationMessage.handler.StageChangeHandler;
import com.galaxyinternet.platform.constant.PlatformConst;
import com.galaxyinternet.service.OperationLogsService;
import com.galaxyinternet.service.OperationMessageService;
import com.galaxyinternet.service.ProgressLogService;
import com.tencent.xinge.XGPush;

import net.sf.json.JSONObject;

/**
 * @description 消息提醒拦截器
 * @author keifer
 * @used 1.在你的controller中的方法中加入注解,如<br/>
 *       @Logger(operationScope=LogType.MESSAGE)，表示记录"消息提醒"功能产生的日志,默认值。
 *       如果是此操作该属性可以不写<br/>
 *       <br>
 *       @Logger(operationScope=LogType.LOG)，表示记录sop中"日志操作"功能产生的日志<br/>
 *       <br>
 *       @Logger(operationScope={LogType.LOG,LogType.MESSAGE})，表示记录sop中"日志操作"和"消息提醒"功能产生的日志<br/>
 *       <br>
 *       @Logger(operationScope=LogType.LOG,unique="request_unique_key",
 *       recordType=RecordType.IDEAS)，表示记录创意中操作日志；unique指定请求的唯一标识值，只要能唯一区分请求即可，
 *       是自定义的值。默认为空，表示根据请求地址作为唯一性处理。recordType指定记录的类型是项目还是创意，默认是项目，可以省略。
 *       <br>
 *       2.在方法处理前或后，在reqeust中设置操作的项目名称。例如：ControllerUtils.
 *       setRequestParamsForMessageTip(request,"星河互联创业项目",68) <br/>
 *       注意：如果url里处理多个业务逻辑，分别需
 *       要记录，在枚举类的url后面加上UrlNumber中的数字,UrlNumber.one.name()如。ControllerUtils.
 *       setRequestParamsForMessageTip(request,"星河互联创业项目",68,UrlNumber.one)即可。
 *       <br/>
 *       <br>
 *       3.需要在springmvc配置文件中添加如下配置 <br/>
 *       {@code
 * <mvc:interceptors>
		<mvc:interceptor>
			<mvc:mapping path="/**" />
			<bean class="com.galaxyinternet.common.annotation.MessageHandlerInterceptor" />
		</mvc:interceptor>
	</mvc:interceptors> 
}
 */
public class MessageHandlerInterceptor extends HandlerInterceptorAdapter {

	final org.slf4j.Logger loger = LoggerFactory.getLogger(MessageHandlerInterceptor.class);
	
	//添加创投项目
	public static final String add_project_type = "1";
	public static final String add_interview_type = "3";
	
	public static final String ceo_apply_type = "10.1";
	public static final String lxh_apply_type = "10.2";
	public static final String tjh_apply_type = "10.3";
	
	
	@Autowired
	OperationMessageService operationMessageService;
	
	@Autowired
	OperationLogsService operationLogsService;
	@Autowired
	MessageGenerator messageGenerator;
	@Autowired
	IosMessageGenerator iosMessageGenerator;

	@Autowired
	ProgressLogService ideaNewsService;

	@SuppressWarnings("unchecked")
	@Override
	public void afterCompletion(final HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			final Method method = handlerMethod.getMethod();
			final Logger logger = method.getAnnotation(Logger.class);
			if (logger != null) {
				final Map<String, Object> map = (Map<String, Object>) request.getAttribute(PlatformConst.REQUEST_SCOPE_MESSAGE_TIP);
				if (null != map && !map.isEmpty()) {
					String uniqueKey = getUniqueKey(request, map, logger);
					final OperationType type = OperationType.getObject(uniqueKey);   //message
					final OperationLogType operLogType = OperationLogType.getObject(uniqueKey); //log
					final IosMeaageOperation iosMessageOper = IosMeaageOperation.getObject(uniqueKey); //ios
					
					if (null != type || null != operLogType || null != iosMessageOper) {
						final User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
						final RecordType recordType = logger.recordType();
						final LogType[] logTypes = logger.operationScope();  //log message 
						GalaxyThreadPool.getExecutorService().execute(new Runnable() {
							@Override
							public void run() {
								for (final LogType ltype : logTypes) {
									if (ltype == LogType.MESSAGE) {
										if(map.get(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE) != null){
											    insertMessageTip(populateOperationMessage(type, user, map));
									     }
									} else if (ltype == LogType.LOG) {
										insertOperationLog(populateOperationLog(operLogType, user, map, recordType));
									} else if (ltype == LogType.IDEANEWS) {
										insertIdeaNews(populateProgressLog(operLogType, user, map, recordType));
									}else if (ltype == LogType.IOSPUSHMESS) {
										if(method.getName().contains("updateReserveTime")){
											if(map.get(PlatformConst.REQUEST_SCOPE_MESSAGE_BATCH) != null){
												List<Map<String,Object>> mapList = (List<Map<String, Object>>) map.get(PlatformConst.REQUEST_SCOPE_MESSAGE_BATCH);
												if(mapList != null && mapList.size() > 0){
													for(Map<String,Object> map:mapList){
														toPushIosMessage(iosMessageOper, user, map);
													}
												}
											}else
												toPushIosMessage(iosMessageOper,user,map);
										}else
											toPushIosMessage(iosMessageOper,user,map);
										
									}else if(ltype == LogType.BATCHMESSAGE){
										if(map.get(PlatformConst.REQUEST_SCOPE_MESSAGE_BATCH) != null){
											List<Map<String,Object>> mapList = (List<Map<String, Object>>) map.get(PlatformConst.REQUEST_SCOPE_MESSAGE_BATCH);
											if(mapList != null && mapList.size() > 0){
												for(Map<String,Object> map:mapList){
													insertMessageTip(populateOperationMessage(type, user, map));
												}
											}
										}
									}else if (ltype == LogType.PROJECTNEWS) {
										// 这里用于扩展
									}
								}
							}
						});
					}
				}
			}
		}
		super.afterCompletion(request, response, handler, ex);
	}
	
	/**
	 * 
	 * @Description:产生消息提醒的方法
	 */
	
	private void insertMessageTip(OperationMessage message) {
		try {
			if(message == null){
				return;
			}
			Long c=operationMessageService.insert(message);
			StringBuffer content = new StringBuffer();
			
			if(message.getMessageType().equals(StageChangeHandler._6_1_)){
				message.setMessageType(add_project_type);
				content.append(message.getOperator()).append("添加了项目");
				content.append(ControllerUtils.getProjectNameLink(message));
				message.setContent(content.toString());
				operationMessageService.insert(message);
				
				List<String> messageList = message.getMessageList();
				if(messageList != null){
					for(String temp : messageList){
						message.setContent(temp);
						operationMessageService.insert(message);
					}
				}
				
				
				
			} else if(message.getMessageType().equals(StageChangeHandler._6_3_)){
				message.setMessageType(MeetMessageHandler.lph_message_type);
				content.append(message.getOperator())
				.append("为项目")
				.append(ControllerUtils.getProjectNameLink(message))
				.append("添加了内评会会议纪要");
				message.setContent(content.toString());
				operationMessageService.insert(message);
				content.setLength(0);
				message.setMessageType(ceo_apply_type);
				content.append(message.getOperator())
				.append("为项目")
				.append(ControllerUtils.getProjectNameLink(message))
				.append("申请CEO评审会会议排期");
				message.setContent(content.toString());
				operationMessageService.insert(message);
			} else if(message.getMessageType().equals(StageChangeHandler._6_4_)){
				message.setMessageType(lxh_apply_type);
				content.append(message.getOperator())
				.append("为项目")
				.append(ControllerUtils.getProjectNameLink(message))
				.append("申请立项会会议排期");
				message.setContent(content.toString());
				operationMessageService.insert(message);
			} else if(message.getMessageType().equals(StageChangeHandler._6_5_)){
				message.setMessageType(MeetMessageHandler.lxh_message_type);
				content.append(message.getOperator())
				.append("为项目")
				.append(ControllerUtils.getProjectNameLink(message))
				.append("添加了立项会会议纪要");
				message.setContent(content.toString());
				operationMessageService.insert(message);
			} else if(message.getMessageType().equals(StageChangeHandler._6_6_)){
				message.setMessageType(SopFileMessageHandler._5_3_);
				SopParentFile sopFile = (SopParentFile) message.getUserData();
				content.append(message.getOperator());
				content.append("为项目");
				content.append(ControllerUtils.getProjectNameLink(message));
				content.append("上传了投资意向书签署凭证");
				content.append("《");
				content.append(sopFile.getFileName() + "." + sopFile.getFileSuffix());
				content.append("》");	
				message.setContent(content.toString());
				operationMessageService.insert(message);
			} else if(message.getMessageType().equals(StageChangeHandler._6_7_)){
				message.setMessageType(tjh_apply_type);
				content.append(message.getOperator())
				.append("为项目")
				.append(ControllerUtils.getProjectNameLink(message))
				.append("申请投决会会议排期");
				message.setContent(content.toString());
				operationMessageService.insert(message);
			}else if(message.getMessageType().equals(StageChangeHandler._6_8_)){
				message.setMessageType(MeetMessageHandler.tjh_message_type);
				content.append(message.getOperator())
				.append("为项目")
				.append(ControllerUtils.getProjectNameLink(message))
				.append("添加了投决会会议纪要");
				message.setContent(content.toString());
				operationMessageService.insert(message);
			} else if(message.getMessageType().equals(StageChangeHandler._6_9_)){
				SopParentFile sopFile = (SopParentFile) message.getUserData();
				if(sopFile.getFileWorktype().equals(DictEnum.fileWorktype.投资协议.getCode())){
					message.setMessageType(SopFileMessageHandler._5_9_);
					content.append(message.getOperator());
					content.append("为项目");
					content.append(ControllerUtils.getProjectNameLink(message));
					content.append("上传了投资协议签署凭证");
					content.append("《");
					content.append(sopFile.getFileName() + "." + sopFile.getFileSuffix());
					content.append("》");	
					message.setContent(content.toString());
					operationMessageService.insert(message);
				}else{
					message.setMessageType(SopFileMessageHandler._5_13_);
					content.append(message.getOperator());
					content.append("为项目");
					content.append(ControllerUtils.getProjectNameLink(message));
					content.append("上传了股权转让签署凭证");
					content.append("《");
					content.append(sopFile.getFileName() + "." + sopFile.getFileSuffix());
					content.append("》");	
					message.setContent(content.toString());
					operationMessageService.insert(message);
				}
				content.setLength(0);
				message.setMessageType(tjh_apply_type);
				content.append(message.getOperator())
				.append("为项目")
				.append(ControllerUtils.getProjectNameLink(message))
				.append("申请投决会会议排期");
				message.setContent(content.toString());
				operationMessageService.insert(message);
			} else if(message.getMessageType().equals(StageChangeHandler._6_10_)){
				String messageType = (String) message.getUserData();
				message.setMessageType(messageType);
				content.append(message.getOperator())
				.append("完成了项目")
				.append(ControllerUtils.getProjectNameLink(message))
				.append("的");
				if(messageType.equals("9.5")){
					content.append(SopConstant.TASK_NAME_ZJBF);
				}else{
					content.append(SopConstant.TASK_NAME_GSBG);
				}
				content.append("任务");
				message.setContent(content.toString());
				operationMessageService.insert(message);
			}else if(message.getFlag()==true)
			{
                User user = (User) message.getUserData();
				message.setBelongUid(user.getId());
				message.setBelongDepartmentId(user.getDepartmentId());
				message.setSingleMark((byte) 1);
			    operationMessageService.insert(message);
			}
		} catch (Exception e1) {
			loger.error("产生提醒消息异常，请求数据：" + message, e1);
		}
	}
	

	// 添加创意动态
	private void insertIdeaNews(ProgressLog message) {
		try {
			ideaNewsService.insert(message);
		} catch (Exception e1) {
			loger.error("产生提醒消息异常，请求数据：" + message, e1);
		}
	}

	/**
	 * 
	 * @Description:产生操作日志的方法
	 */
	private void insertOperationLog(OperationLogs operationLog) {
		try {
			operationLogsService.insert(operationLog);
		} catch (Exception e3) {
			loger.error("产生SOP操作日志异常，请求数据：" + operationLog, e3);
		}
	}

	private String getUniqueKey(HttpServletRequest request, Map<String, Object> map, Logger logger) {
		String uniqueKey = logger.unique();
		if (StringUtils.isBlank(uniqueKey)) {
			uniqueKey = request.getRequestURL().toString();
			if (null != map && !map.isEmpty()) {
				if (map.containsKey(PlatformConst.REQUEST_SCOPE_URL_NUMBER)) {
					uniqueKey = uniqueKey + "/" + map.get(PlatformConst.REQUEST_SCOPE_URL_NUMBER);
				}
			}
		}
		return uniqueKey;
	}

	private OperationLogs populateOperationLog(OperationLogType type, User user, Map<String, Object> map, RecordType recordType) {
		OperationLogs entity = new OperationLogs();
		
		entity.setOperationContent(type.getContent());
		if(type.getUniqueKey().contains("project/breakpro")){
			entity.setOperationContent(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_PROJECT_NAME)));
		}
		entity.setOperationType(type.getType());
		entity.setUid(user.getId());
		entity.setUname(user.getRealName());
		entity.setDepartName(user.getDepartmentName());
		entity.setUserDepartid(user.getDepartmentId());
		
		entity.setProjectName(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_PROJECT_NAME)));
		entity.setProjectId(Long.valueOf(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_PROJECT_ID))));
		
		if(map.containsKey(PlatformConst.REQUEST_SCOPE_PROJECT_PROGRESS)&& map.get(PlatformConst.REQUEST_SCOPE_PROJECT_PROGRESS)!=null){
			entity.setSopstage(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_PROJECT_PROGRESS)));
		}else{
			Object flag=map.get(PlatformConst.REQUEST_SCOPE_MESSAGE_NUM);
			Object obj=map.get(PlatformConst.REQUEST_SCOPE_MESSAGE_STAGE);
			if(null!=flag&&null!=obj){
				boolean f=(boolean) flag;
				String stage=(String)obj;
				if(f==true&&!"".equals(stage)){
					entity.setSopstage(stage);
				 }
			}else{
				entity.setSopstage(type.getSopstage());
			}
		}
		entity.setReason(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_MESSAGE_REASON)));
		entity.setRecordType(recordType.getType());
		
		return entity;
	}

	// 创意动态
	private ProgressLog populateProgressLog(OperationLogType type, User user, Map<String, Object> map, RecordType recordType) {
		ProgressLog entity = new ProgressLog();
		entity.setOperationContent(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_IDEA_CONTENT)));
		entity.setOperationType(type.getType());
		entity.setUid(user.getId());
		entity.setUname(user.getRealName());
		entity.setDepartName(user.getDepartmentName());
		entity.setUserDepartid(user.getDepartmentId());
		entity.setSopstage(type.getSopstage());
		entity.setRelatedName(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_IDEA_NAME)));
		entity.setRelatedId(Long.valueOf(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_IDEA_ID))));
		entity.setRecordType(recordType.getType());
		return entity;
	}

	
	private OperationMessage populateOperationMessage(OperationType type, User user, Map<String, Object> map) {
		return messageGenerator.generate(type, user, map);
	}
	
	
	
	// ios message push
	private void toPushIosMessage(IosMeaageOperation type, User user, Map<String, Object> map) {
		try{
			if(map.get(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE) != null){
				String messageType = String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE));
				if(messageType.equals("8.4") || messageType.equals("8.5") ||
						messageType.equals("9.4") || messageType.equals("9.5")){
					return;
				}
			}else throw new Exception("requset 封装的  map 中  messageType 信息缺失");
			
			IosMessage entity = iosMessageGenerator.generate(type, user,map);
		
			if(entity==null || entity.getUidList()==null || entity.getUidList().isEmpty() || StringUtils.isBlank(entity.getContent())){
				return;
			}

			XGPush xinge = XGPush.getInstance();
			org.json.JSONObject result = xinge.pushAccountList(entity.getUidList(),entity.getTitle(), entity.getContent());
			
			if(result!=null){
				String backStr = result.toString();
				String iosmarkV = backStr.substring(backStr.indexOf("ret_code\":")+10, backStr.indexOf("ret_code\":")+11);
				String andriodmarkV = backStr.substring(backStr.lastIndexOf("ret_code\":")+10, backStr.lastIndexOf("ret_code\":")+11);
				if(!iosmarkV.equals("0") || !andriodmarkV.equals("0")){
					throw new Exception("xingge 推送失败 "+backStr);
				}
			}
			
		} catch (Exception e) {
			loger.error("xingge 消息推送失败" +e);
		}
	}
	
	
		
	
	
	
}
