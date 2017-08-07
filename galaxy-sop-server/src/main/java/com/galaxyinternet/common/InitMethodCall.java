package com.galaxyinternet.common;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.galaxyinternet.common.utils.DictCacheUtils;
import com.galaxyinternet.common.utils.SpringContextManager;
import com.galaxyinternet.scheduling.MeetingPassStageTask;
import com.galaxyinternet.scheduling.PullMessageTask;
import com.galaxyinternet.scheduling.PushMessageOperation;
import com.galaxyinternet.scheduling.PushMessageTask;
import com.galaxyinternet.scheduling.UserInfoCache;


public class InitMethodCall extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void init(ServletConfig config) throws ServletException {
		MeetingPassStageTask meetingPassStageTask = SpringContextManager.getBean("meetingPassStageTask",MeetingPassStageTask.class);
		meetingPassStageTask.execute();
		
		//初始化内存-角色
		UserInfoCache userInfoTask = SpringContextManager.getBean("userInfoTask",UserInfoCache.class);
		userInfoTask.execute();
		
		//拉取排期时间信息
		PullMessageTask pullMessageTask = SpringContextManager.getBean("pullMessageTask",PullMessageTask.class);
		pullMessageTask.execute();
		
		//推送排期时间
		PushMessageTask pushMessageTask = SpringContextManager.getBean("pushMessageTask",PushMessageTask.class);
		pushMessageTask.execute();
		
		
		//推送排期时间
		DictCacheUtils dictCacheUtils = SpringContextManager.getBean("dictCacheUtils",DictCacheUtils.class);
		dictCacheUtils.execute();
		
		//每月1号给已经有注资信息的项目推送添加运营数据的消息
	//	PushMessageOperation pushMessageOperation = SpringContextManager.getBean("pushMessageOperation",PushMessageOperation.class);
	//	pushMessageOperation.execute();
		
		
		
	}

}
