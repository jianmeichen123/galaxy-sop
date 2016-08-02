package com.galaxyinternet.bo.project;

import com.galaxyinternet.model.project.MeetingScheduling;

public class MeetingSchedulingBo extends MeetingScheduling {
	private static final long serialVersionUID = 1L;
	
	private String extendFiled;// 业务对象中扩展的字段
	
	private String start;
	private String end;
	private String title;
	
	private String nameLike;//模糊查询条件匹配
	
	private Long meetingTotal;
	private Long lxhTotal;
	private Long tjhTotal;
	private Long pshTotal;
	
	private Long lxhWait;
	private Long tjhWait;
	private Long pshWait;
	
	
	
	
	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	public String getNameLike() {
		return nameLike;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public String getExtendFiled() {
		return extendFiled;
	}

	public void setExtendFiled(String extendFiled) {
		this.extendFiled = extendFiled;
	}

	public Long getMeetingTotal() {
		return meetingTotal;
	}

	public void setMeetingTotal(Long meetingTotal) {
		this.meetingTotal = meetingTotal;
	}

	public Long getLxhTotal() {
		return lxhTotal;
	}

	public void setLxhTotal(Long lxhTotal) {
		this.lxhTotal = lxhTotal;
	}

	public Long getTjhTotal() {
		return tjhTotal;
	}

	public void setTjhTotal(Long tjhTotal) {
		this.tjhTotal = tjhTotal;
	}

	public Long getPshTotal() {
		return pshTotal;
	}

	public void setPshTotal(Long pshTotal) {
		this.pshTotal = pshTotal;
	}

	public Long getLxhWait() {
		return lxhWait;
	}

	public void setLxhWait(Long lxhWait) {
		this.lxhWait = lxhWait;
	}

	public Long getTjhWait() {
		return tjhWait;
	}

	public void setTjhWait(Long tjhWait) {
		this.tjhWait = tjhWait;
	}

	public Long getPshWait() {
		return pshWait;
	}

	public void setPshWait(Long pshWait) {
		this.pshWait = pshWait;
	}


	
	
}