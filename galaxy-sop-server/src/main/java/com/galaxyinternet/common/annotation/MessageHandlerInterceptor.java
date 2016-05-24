package com.galaxyinternet.common.annotation;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.thread.GalaxyThreadPool;
import com.galaxyinternet.model.common.ProgressLog;
import com.galaxyinternet.model.operationLog.OperationLogType;
import com.galaxyinternet.model.operationLog.OperationLogs;
import com.galaxyinternet.model.operationMessage.OperationMessage;
import com.galaxyinternet.model.operationMessage.OperationType;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.platform.constant.PlatformConst;
import com.galaxyinternet.service.OperationLogsService;
import com.galaxyinternet.service.OperationMessageService;
import com.galaxyinternet.service.ProgressLogService;

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

	@Autowired
	OperationMessageService operationMessageService;
	@Autowired
	OperationLogsService operationLogsService;

	@Autowired
	ProgressLogService ideaNewsService;

	@SuppressWarnings("unchecked")
	@Override
	public void afterCompletion(final HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			final Logger logger = method.getAnnotation(Logger.class);
			if (logger != null) {
				final Map<String, Object> map = (Map<String, Object>) request.getAttribute(PlatformConst.REQUEST_SCOPE_MESSAGE_TIP);
				if (null != map && !map.isEmpty()) {
					String uniqueKey = getUniqueKey(request, map, logger);
					final OperationType type = OperationType.getObject(uniqueKey);
					final OperationLogType operLogType = OperationLogType.getObject(uniqueKey);
					if (null != type || null != operLogType) {
						final User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
						final RecordType recordType = logger.recordType();
						final LogType[] logTypes = logger.operationScope();
						GalaxyThreadPool.getExecutorService().execute(new Runnable() {
							@Override
							public void run() {
								for (final LogType ltype : logTypes) {
									if (ltype == LogType.MESSAGE) {
										insertMessageTip(populateOperationMessage(type, user, map));
									} else if (ltype == LogType.LOG) {
										insertOperationLog(populateOperationLog(operLogType, user, map, recordType));
									} else if (ltype == LogType.IDEANEWS) {
										insertIdeaNews(populateProgressLog(operLogType, user, map, recordType));
									} else if (ltype == LogType.PROJECTNEWS) {
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
			operationMessageService.insert(message);
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
		entity.setOperationType(type.getType());
		entity.setUid(user.getId());
		entity.setUname(user.getRealName());
		entity.setDepartName(user.getDepartmentName());
		entity.setUserDepartid(user.getDepartmentId());
		entity.setSopstage(type.getSopstage());
		entity.setProjectName(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_PROJECT_NAME)));
		entity.setProjectId(Long.valueOf(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_PROJECT_ID))));
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
		OperationMessage entity = new OperationMessage();
		entity.setContent(type.getContent());
		if("lijunyang".equals(user.getEmail())){
			entity.setDepartment("产品研发部");
			entity.setRole("SVP");
		}else{
			entity.setDepartment(user.getDepartmentName());
			entity.setRole(user.getRole());
		}
		entity.setOperatorId(user.getId());
		entity.setOperator(user.getRealName());
		Object o = map.get(PlatformConst.REQUEST_SCOPE_USER);
		User u = null;
		if (o != null) {
			u = (User) o;
		} else {
			u = user;
		}
		entity.setBelongUid(u.getId());
		entity.setBelongUname(u.getRealName());
		entity.setType(type.getType());
		entity.setProjectName(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_PROJECT_NAME)));
		entity.setProjectId(Long.valueOf(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_PROJECT_ID))));
		Integer module = type.getModule();
		entity.setModule(module == null ? OperationType.getModule(user.getRoleId()) : module);
		return entity;
	}

}
