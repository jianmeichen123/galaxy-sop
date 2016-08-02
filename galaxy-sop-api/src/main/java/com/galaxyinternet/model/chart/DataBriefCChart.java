package com.galaxyinternet.model.chart;

/**
 * 项目简报 - 投资事业线项目跟踪 + 项目完成跟踪
 * @author wangkun
 *
 */
public class DataBriefCChart extends Chart{
	
private static final long serialVersionUID = 1L;
	
	private Long id;	// 部门id
	private String name; // 部门名称
	private Integer completed; // 完成数
	private Integer target; // 目标数
	private float rate; // 完成占比
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCompleted() {
		return completed;
	}
	public void setCompleted(Integer completed) {
		this.completed = completed;
	}
	public Integer getTarget() {
		return target;
	}
	public void setTarget(Integer target) {
		this.target = target;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
