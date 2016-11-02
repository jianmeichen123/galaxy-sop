package com.galaxyinternet.mongodb.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.galaxyinternet.model.hr.PersonLearn;
import com.galaxyinternet.model.project.ProjectShares;

@Document(collection="galaxy.sop.project")
public class Project {
	
	@Id
	private String id;
	/*唯一编码*/
	private String uuid;
	/*项目的创建人ID*/
	private Long uid;
	/*项目的状态*/
	private int status;
	/*项目名称*/
	private String pn;
	/*项目关联的股权结构*/
	private List<ProjectShares> psc;
	/*团队成员的学习经历*/
	private List<PersonLearn> plc;
	
	public String getId() {
		return id;
	}
	public String getUuid() {
		return uuid;
	}
	public Long getUid() {
		return uid;
	}
	public int getStatus() {
		return status;
	}
	public String getPn() {
		return pn;
	}
	public List<ProjectShares> getPsc() {
		return psc;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setPn(String pn) {
		this.pn = pn;
	}
	public void setPsc(List<ProjectShares> psc) {
		this.psc = psc;
	}
	public List<PersonLearn> getPlc() {
		return plc;
	}
	public void setPlc(List<PersonLearn> plc) {
		this.plc = plc;
	}
}
