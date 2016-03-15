package com.galaxyinternet.bo;

import java.util.List;

import com.galaxyinternet.model.soptask.SopUserSchedule;

public class SheduleCommon  extends SopUserSchedule{

	private static final long serialVersionUID = 1L;
	private String months;
	private Integer count;
	private List<SopUserSchedule> list;
	
	
	public List<SopUserSchedule> getList() {
		return list;
	}
	public void setList(List<SopUserSchedule> list) {
		this.list = list;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMonths() {
		return months;
	}
	public void setMonths(String months) {
		this.months = months;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}
