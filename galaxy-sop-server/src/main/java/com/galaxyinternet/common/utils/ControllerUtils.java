package com.galaxyinternet.common.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.operationMessage.OperationMessage;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.platform.constant.PlatformConst;

public class ControllerUtils {
	
	
	public static String getProjectNameLink(OperationMessage message) {
		return "projectname";
	}

	/**
	 * 
	 * @Description:消息提醒临时传参方法
	 * @param request
	 * @param projectName
	 *            项目名称
	 * @param projectId
	 *            项目id
	 *
	 */
	public static void setRequestParamsForMessageTip(HttpServletRequest request, String projectName, Long projectId) 
	{
		String messageType = null;
		setRequestParamsForMessageTip(request,projectName,projectId,messageType);
	}
	public static void setRequestParamsForMessageTip(HttpServletRequest request, String projectName, Long projectId, String messageType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_NAME, projectName);
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_ID, projectId);
		params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE, messageType);
		request.setAttribute(PlatformConst.REQUEST_SCOPE_MESSAGE_TIP, params);
	}
	public static void setRequestParamsForMessageTip(HttpServletRequest request, String projectName, Long projectId, String messageType,Object userData) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_NAME, projectName);
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_ID, projectId);
		params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE, messageType);
		params.put(PlatformConst.REQUEST_SCOPE_USER_DATA, userData);
		request.setAttribute(PlatformConst.REQUEST_SCOPE_MESSAGE_TIP, params);
	}
	public static void setRequestParamsForMessageTip(HttpServletRequest request, String projectName, Long projectId,UrlNumber number) 
	{
		setRequestParamsForMessageTip(request,projectName,projectId,null,number);
	}
	public static void setRequestParamsForMessageTip(HttpServletRequest request, String projectName, Long projectId, String messageType,UrlNumber number) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_NAME, projectName);
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_ID, projectId);
		params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE, messageType);
		if(number != null){
			params.put(PlatformConst.REQUEST_SCOPE_URL_NUMBER, number.name());
		}
		request.setAttribute(PlatformConst.REQUEST_SCOPE_MESSAGE_TIP, params);
	}
	public static void setRequestParamsForMessageTip(HttpServletRequest request, String projectName, Long projectId, String messageType,UrlNumber number,Object userData) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_NAME, projectName);
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_ID, projectId);
		params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE, messageType);
		if(number!=null){
			params.put(PlatformConst.REQUEST_SCOPE_URL_NUMBER, number.name());	
		}
		params.put(PlatformConst.REQUEST_SCOPE_USER_DATA, userData);
		request.setAttribute(PlatformConst.REQUEST_SCOPE_MESSAGE_TIP, params);
	}
	public static void setRequestParamsForMessageTip(HttpServletRequest request, User user, String projectName, Long projectId,UrlNumber number) 
	{
		setRequestParamsForMessageTip(request,user,projectName,projectId,null,number);
	}
	public static void setRequestParamsForMessageTip(HttpServletRequest request, User user, String projectName, Long projectId, String messageType, UrlNumber number) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_NAME, projectName);
		params.put(PlatformConst.REQUEST_SCOPE_USER, user);
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_ID, projectId);
		params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE, messageType);
		if(number != null){
			params.put(PlatformConst.REQUEST_SCOPE_URL_NUMBER, number.name());
		}
		request.setAttribute(PlatformConst.REQUEST_SCOPE_MESSAGE_TIP, params);
	}
	public static void setRequestParamsForMessageTip(HttpServletRequest request, User user, String projectName, Long projectId, String messageType, UrlNumber number,Object userData) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_NAME, projectName);
		params.put(PlatformConst.REQUEST_SCOPE_USER, user);
		params.put(PlatformConst.REQUEST_SCOPE_PROJECT_ID, projectId);
		params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE, messageType);
		if(number != null){
			params.put(PlatformConst.REQUEST_SCOPE_URL_NUMBER, number.name());
		}
		params.put(PlatformConst.REQUEST_SCOPE_USER_DATA, userData);
		request.setAttribute(PlatformConst.REQUEST_SCOPE_MESSAGE_TIP, params);
	}
	
	public static void setRequestIdeaParamsForMessageTip(HttpServletRequest request, User user, String ideaName, Long ideaId,String content,UrlNumber number) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PlatformConst.REQUEST_SCOPE_IDEA_NAME, ideaName);
		params.put(PlatformConst.REQUEST_SCOPE_USER, user);
		params.put(PlatformConst.REQUEST_SCOPE_IDEA_ID, ideaId);
		params.put(PlatformConst.REQUEST_SCOPE_URL_NUMBER, number.name());
		params.put(PlatformConst.REQUEST_SCOPE_IDEA_CONTENT, content);
		request.setAttribute(PlatformConst.REQUEST_SCOPE_MESSAGE_TIP, params);
	}
}
