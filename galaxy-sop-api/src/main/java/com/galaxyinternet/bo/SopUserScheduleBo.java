package com.galaxyinternet.bo;

import com.galaxyinternet.model.soptask.SopUserSchedule;

public class SopUserScheduleBo extends SopUserSchedule{

	private static final long serialVersionUID = 1L;
	private String timeTask;// 业务对象中扩展的字段

	public String getTimeTask() {
		return timeTask;
	}
	public void setTimeTask(String timeTask) {
		this.timeTask = timeTask;
	}

}
