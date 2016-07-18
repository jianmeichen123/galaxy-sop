package com.galaxyinternet.model.chart;

/**
 * 项目简报 - 项目总目标完成跟踪
 * @author wangkun
 *
 */
public class DataBriefAChart extends Chart{
	
	private static final long serialVersionUID = 1L;
	
	private Integer year; // 年份
	private Integer target;// 项目目标数
	private Integer completed;// 完成数
	private Integer notcompleted;// 未完成数 
	private Integer above;// 超额数
	
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getTarget() {
		return target;
	}
	public void setTarget(Integer target) {
		this.target = target;
	}
	public Integer getCompleted() {
		return completed;
	}
	public void setCompleted(Integer completed) {
		this.completed = completed;
	}
	public Integer getNotcompleted() {
		return notcompleted;
	}
	public void setNotcompleted(Integer notcompleted) {
		this.notcompleted = notcompleted;
	}
	public Integer getAbove() {
		return above;
	}
	public void setAbove(Integer above) {
		this.above = above;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
