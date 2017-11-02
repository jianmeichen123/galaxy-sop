package com.galaxyinternet.common.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.model.resource.PlatformResource;
import com.galaxyinternet.model.user.User;
/**
 * 数据权限，获取页面资源标识（pageId）
 * @author wangsong
 *
 */
public class PageInfoInterceptor extends HandlerInterceptorAdapter 
{
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		String pageId= request.getHeader("pageId");
		HttpSession session = request.getSession();
		if(pageId != null && session != null)
		{
			AuthContext.get().setPageId(pageId);
			User user = (User)session.getAttribute(Constants.SESSION_USER_KEY);
			if(user != null)
			{
				List<PlatformResource> list = user.getAllResourceToUser();
				if(list != null)
				{
					for(PlatformResource item : list)
					{
						if(pageId.equals(item.getResourceMark()) && item.getUserIds() != null && item.getUserIds().size() >0)
						{
							AuthContext.get().setUserIds(item.getUserIds());
						}
					}
				}
			}
		}
		return super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
	{
		AuthContext.remove();
		super.afterCompletion(request, response, handler, ex);
	}

	
}
