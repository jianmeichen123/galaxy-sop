package com.galaxyinternet.timer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public abstract class AutoThread implements Runnable{
	private List<Calendar> startTimes = new ArrayList<Calendar>();
	/**
	 * 定时线程是否在运行中
	 */
	private boolean isRunning = false;
	/**
	 * 定时线程的名称
	 */
	private String id;
	/**
	 * 定时线程的描述
	 */
	private String description;
	/**
	 * 定时程序的上次运行时间
	 */
	private Calendar proviousStartTime;

	@Override
	public void run() {
		this.setRunning(true);
		this.setProviousStartTime(Calendar.getInstance());
		executeTask();
	}
	protected abstract  void executeTask();
	
	
	public List<Calendar> getStartTimes() {
		return startTimes;
	}
	public void setStartTimes(List<Calendar> startTimes) {
		this.startTimes = startTimes;
	}
	public boolean isRunning() {
		return isRunning;
	}
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	public Calendar getProviousStartTime() {
		return proviousStartTime;
	}
	public void setProviousStartTime(Calendar proviousStartTime) {
		this.proviousStartTime = proviousStartTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	
	
}
