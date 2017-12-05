package com.galaxyinternet.mongodb.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.galaxyinternet.framework.core.dao.utils.QueryField;
@Document(collection="galaxy.sop.InformationCreateTimeMG")
public class InformationCreateTimeMG {

	/*唯一编码*/
	@QueryField(attribute="uuid")
	private String uuid;
	@QueryField(attribute="id")
	private String id;
	
	@QueryField(attribute="tiltleId")
	private String tiltleId;
	@QueryField(attribute="projectId")
	private String projectId;
	private String createTime;
	private String createId;
	@QueryField(attribute="parentId")
	private String parentId;
	public String getTiltleId() {
		return tiltleId;
	}

	public void setTiltleId(String tiltleId) {
		this.tiltleId = tiltleId;
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	
	
	
    
}