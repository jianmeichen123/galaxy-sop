package com.galaxyinternet.utils;

public class WorktypeTask {
	/**
	 * 任务类别
	 */
	private int taskFlag;
	/**
	 * 业务分类
	 */
	private int worktype;
	/**
	 * 该业务分类是否存在签署凭证
	 */
	private boolean hasProve;
	public int getTaskFlag() {
		return taskFlag;
	}
	public void setTaskFlag(int taskFlag) {
		this.taskFlag = taskFlag;
	}
	public int getWorktype() {
		return worktype;
	}
	public void setWorktype(int worktype) {
		this.worktype = worktype;
	}
	
	public boolean isHasProve() {
		return hasProve;
	}
	public void setHasProve(boolean hasProve) {
		this.hasProve = hasProve;
	}
	public WorktypeTask(int taskFlag,int worktype,boolean hasProve){
		this.taskFlag = taskFlag;
		this.worktype = worktype;
		this.hasProve = hasProve;
	}
	
}
