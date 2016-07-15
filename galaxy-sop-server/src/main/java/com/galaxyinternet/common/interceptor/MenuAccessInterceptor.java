package com.galaxyinternet.common.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.galaxyinternet.common.constants.SopConstant;

public class MenuAccessInterceptor extends HandlerInterceptorAdapter implements InitializingBean
{
	private final Logger logger = Logger.getLogger(MenuAccessInterceptor.class);
	private Map<String,String> mappings = new HashMap<>();
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception
	{
		String isMenu = request.getParameter(SopConstant.REQUEST_SCOPE_ATTR_IS_MENU);
		if("true".equals(isMenu))
		{
			HttpSession session = request.getSession();
			String URI = request.getRequestURI();
			if(URI != null)
			{
				for(Map.Entry<String, String> mapping : mappings.entrySet())
				{
					if(URI.contains(mapping.getKey()))
					{
						session.setAttribute(SopConstant.SESSION_SCOPE_ATTR_CUR_MENU, mapping.getValue());
						break;
					}
				}
			}
		}
		super.postHandle(request, response, handler, modelAndView);
	}
	@Override
	public void afterPropertiesSet() throws Exception
	{
		if(mappings == null || mappings.size() == 0)
		{
			logger.error("No mappings.");
			throw new Exception("No mappings.");
		}
		
	}
	public Map<String, String> getMappings()
	{
		return mappings;
	}
	public void setMappings(Map<String, String> mappings)
	{
		this.mappings = mappings;
	}
}
