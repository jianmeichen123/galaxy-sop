package com.galaxyinternet.model.chart;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class ProjectAnalysis extends PagableEntity{
	
	private static final long serialVersionUID = 1L;
	//完成率
	private double rate;
	//创建与投资项目数占比
	private double wbRate;
	//未完成数=目标数-完成项目数
	private int notCompleted;
	//自建项目数
	private int zjCompleted;
	//自建项目数比率
	private double zjRate;
	//投资项目数
	private int wbCompleted;
	//完成项目数
	private double completed;
	//部门Id
	private long departmentId;
	//部门名称
	private String departmentName;
	//目标数
	private int target;
	
	
	
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getWbRate() {
		return wbRate;
	}
	public void setWbRate(double wbRate) {
		this.wbRate = wbRate;
	}
	public int getNotCompleted() {
		return notCompleted;
	}
	public void setNotCompleted(int notCompleted) {
		this.notCompleted = notCompleted;
	}
	public int getZjCompleted() {
		return zjCompleted;
	}
	public void setZjCompleted(int zjCompleted) {
		this.zjCompleted = zjCompleted;
	}
	public double getZjRate() {
		return zjRate;
	}
	public void setZjRate(double zjRate) {
		this.zjRate = zjRate;
	}
	public int getWbCompleted() {
		return wbCompleted;
	}
	public void setWbCompleted(int wbCompleted) {
		this.wbCompleted = wbCompleted;
	}
	public double getCompleted() {
		return completed;
	}
	public void setCompleted(double completed) {
		this.completed = completed;
	}
	public long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public int getTarget() {
		return target;
	}
	public void setTarget(int target) {
		this.target = target;
	}
}
