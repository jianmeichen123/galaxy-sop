package com.galaxyinternet.model.mongodb;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.galaxyinternet.model.project.ProjectShares;

@Document(collection="galaxy.sop.project")
public class Project {
	
	@Id
	private String id;
	private String pn;
	private List<ProjectShares> psc;
	
	public String getId() {
		return id;
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
	public void setPn(String pn) {
		this.pn = pn;
	}
	public void setPsc(List<ProjectShares> psc) {
		this.psc = psc;
	}
}
