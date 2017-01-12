package com.galaxyinternet.model;

import java.util.List;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class GrantFile extends PagableEntity{
	
	private static final long serialVersionUID = 1L;
	
	private Long grantId;
	private Long fileId;
	private List<Long> fileIds;
	
	private Long tid;
	private String target;
	private List<Long> tids;
	
	public List<Long> getFileIds() {
		return fileIds;
	}
	public void setFileIds(List<Long> fileIds) {
		this.fileIds = fileIds;
	}
	
	public List<Long> getTids() {
		return tids;
	}
	public void setTids(List<Long> tids) {
		this.tids = tids;
	}
	public Long getGrantId() {
		return grantId;
	}
	public void setGrantId(Long grantId) {
		this.grantId = grantId;
	}
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	public Long getTid() {
		return tid;
	}
	public void setTid(Long tid) {
		this.tid = tid;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	
	
	
	
}
