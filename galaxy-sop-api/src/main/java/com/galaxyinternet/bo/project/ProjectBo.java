package com.galaxyinternet.bo.project;

import java.util.List;

import com.galaxyinternet.model.project.Project;

public class ProjectBo extends Project {
	
	private static final long serialVersionUID = 1L;

	private String extendFiled;// 业务对象中扩展的字段
	
	private String nameLike;//模糊查询条件匹配
	private String resultCloseFilter;//过滤已关闭项目
	
	private List<String>  ids;//业务扩展字段---项目ids
	
	private String partnerName;//合伙人姓名
	
	private String meetingType;
	
	private String nameOnlyLike;//指针对projectName进行模糊查询
	
	private Long createDateFrom;
	private Long createDateThrough;
	
	
	public Long getCreateDateFrom() {
		return createDateFrom;
	}

	public void setCreateDateFrom(Long createDateFrom) {
		this.createDateFrom = createDateFrom;
	}

	public Long getCreateDateThrough() {
		return createDateThrough;
	}

	public void setCreateDateThrough(Long createDateThrough) {
		this.createDateThrough = createDateThrough;
	}

	public String getExtendFiled() {
		return extendFiled;
	}

	public void setExtendFiled(String extendFiled) {
		this.extendFiled = extendFiled;
	}

	public String getNameLike() {
		return nameLike;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getMeetingType() {
		return meetingType;
	}

	public void setMeetingType(String meetingType) {
		this.meetingType = meetingType;
	}

	public String getNameOnlyLike() {
		return nameOnlyLike;
	}

	public void setNameOnlyLike(String nameOnlyLike) {
		this.nameOnlyLike = nameOnlyLike;
	}
	
	public String getResultCloseFilter() {
		return resultCloseFilter;
	}

	public void setResultCloseFilter(String resultCloseFilter) {
		this.resultCloseFilter = resultCloseFilter;
	}
	
	
  
}