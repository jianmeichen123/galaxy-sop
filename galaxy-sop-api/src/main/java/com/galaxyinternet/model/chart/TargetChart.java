package com.galaxyinternet.model.chart;

/**
 * 项目简报 - 项目数目标追踪
 * @author wangkun
 *
 */
public class TargetChart extends Chart{
	
	private static final long serialVersionUID = 1L;
	
	private Integer year;
	private Integer target;
	private Integer completed;
	private Integer notcompleted;
	private Integer above;
	
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
