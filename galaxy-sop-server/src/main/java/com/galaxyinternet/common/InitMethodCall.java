package com.galaxyinternet.common;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


import com.galaxyinternet.scheduling.MeetingPassStageTask;
import com.galaxyinternet.timer.beans.SpringContextManager;


public class InitMethodCall extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		MeetingPassStageTask meetingPassStageTask = SpringContextManager.getBean("meetingPassStageTask",MeetingPassStageTask.class);
		meetingPassStageTask.execute();
	}

}
