package com.galaxyinternet.common.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.utils.BeanContextUtils;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.UserRoleService;

public class WebUtils
{
	public static HttpServletRequest getRequest()
	{
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attrs != null)
		{
			return attrs.getRequest();
		}
		return null;
	}
	public static HttpSession getSession()
	{
		HttpServletRequest request = getRequest();
		if(request != null)
		{
			return request.getSession();
		}
		return null;
	}
	public static User getUserFromSession()
	{
		HttpSession session = getSession();
		if(session != null)
		{
			Object userObj = session.getAttribute(Constants.SESSION_USER_KEY);
			if (userObj != null) 
			{
				return (User) userObj;
			}
		}
		return null;
	}
	
	public static List<Long> getRoleIds()
	{
		List<Long> roleIds = new ArrayList<Long>();
		User user = getUserFromSession();
		if(user != null && user.getId() != null)
		{
			UserRoleService userRoleService = BeanContextUtils.getBean(UserRoleService.class, getRequest());
			if(userRoleService != null)
			{
				roleIds = userRoleService.selectRoleIdByUserId(user.getId());
			}
		}
		return roleIds;
	}
	
	public static boolean hasRole(Long roleId)
	{
		if(roleId != null)
		{
			List<Long> roleIds = getRoleIds();
			if(roleIds != null && roleIds.size() > 0)
			{
				return roleIds.contains(roleId);
			}
		}
		return false;
	}
	
	public static <T> T getBean(Class<T> clazz)
	{
		HttpServletRequest request = getRequest();
		if(request != null)
		{
			return BeanContextUtils.getBean(clazz, getRequest());
		}
		return null;
	}
}
