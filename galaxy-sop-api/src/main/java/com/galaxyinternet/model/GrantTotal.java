package com.galaxyinternet.model;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.galaxyinternet.framework.core.model.PagableEntity;
import com.galaxyinternet.framework.core.utils.DateUtil;

public class GrantTotal extends PagableEntity {

	private static final long serialVersionUID = 1L;
	
	private Long projectId;
	private String grantName;
	private Double grantMoney;
	private Long createUid;
	private String createUname;
	
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getGrantName() {
		return grantName;
	}
	public void setGrantName(String grantName) {
		this.grantName = grantName;
	}
	public Double getGrantMoney() {
		return grantMoney;
	}
	public void setGrantMoney(Double grantMoney) {
		this.grantMoney = grantMoney;
	}
	public Long getCreateUid() {
		return createUid;
	}
	public void setCreateUid(Long createUid) {
		this.createUid = createUid;
	}
	public String getCreateUname() {
		return createUname;
	}
	public void setCreateUname(String createUname) {
		this.createUname = createUname;
	}
	
	
	//----bo----
	private List<GrantPart> partList;
	private String formatCreatedTime;
	private String formatUpdatedTime;
	public List<GrantPart> getPartList() {
		return partList;
	}
	public void setPartList(List<GrantPart> partList) {
		this.partList = partList;
	}
	public String getFormatCreatedTime() {
		if(createdTime != null){
			return DateUtil.longToString(createdTime);
		}
		return formatCreatedTime;
	}
	public String getFormatUpdatedTime() {
		return formatUpdatedTime;
	}
	
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
