package com.galaxyinternet.model.iosMessage;

import java.util.List;

import com.galaxyinternet.framework.core.model.BaseEntity;
import com.galaxyinternet.framework.core.utils.GSONUtil;

public class IosMessage extends BaseEntity{

	
	private static final long serialVersionUID = 1L;
	
	
	private String uid;
	private List<String> uidList;
	
	private String title;
	
	private String content;
	
	private Long projectId;
	private String projectName;
	
	private String messageType;
	
	private String operator;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public List<String> getUidList() {
		return uidList;
	}
	public void setUidList(List<String> uidList) {
		this.uidList = uidList;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	
	@Override
	public String toString() {
		return GSONUtil.toJson(this);
	}

	
	
	
	
		
	
	
}
