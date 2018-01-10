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

import com.galaxyinternet.OperationLogs.component.OperationLogGenerator;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.thread.GalaxyThreadPool;
import com.galaxyinternet.iosMessage.IosMessageGenerator;
import com.galaxyinternet.iosMessage.operType.IosMeaageOperation;
import com.galaxyinternet.model.common.ProgressLog;
import com.galaxyinternet.model.iosMessage.IosMessage;
import com.galaxyinternet.model.operationLog.OperationLogType;
import com.galaxyinternet.model.operationMessage.OperationType;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.platform.constant.PlatformConst;
import com.galaxyinternet.service.OperationMessageService;
import com.galaxyinternet.service.ProgressLogService;
import com.tencent.xinge.XGPush;

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
	OperationLogGenerator logGenerator;
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
										
									} else if (ltype == LogType.LOG) 
									{
										logGenerator.generate(operLogType, user, map, recordType);
									} else if (ltype == LogType.IDEANEWS) {
										insertIdeaNews(populateProgressLog(operLogType, user, map, recordType));
									} else if (ltype == LogType.IOSPUSHMESS) {
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
	
	

	// 添加创意动态
	private void insertIdeaNews(ProgressLog message) {
		try {
			ideaNewsService.insert(message);
		} catch (Exception e1) {
			loger.error("产生提醒消息异常，请求数据：" + message, e1);
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
