package com.galaxyinternet.model.chart;

public class ProgressChart extends Chart{
	
	private static final long serialVersionUID = 1L;
	
	private Integer c;	// 阶段项目数
	private Integer total; // 项目总数
	private float rate;	// 阶段占比 
	private String name; // 阶段名
	
	public Integer getC() {
		return c;
	}
	public void setC(Integer c) {
		this.c = c;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
