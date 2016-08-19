package com.galaxyinternet.model;

import java.util.List;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class GrantFile extends PagableEntity{
	
	private static final long serialVersionUID = 1L;
	
	private long grantId;
	private long fileId;
	private List<Long> fileIds;
	
	public List<Long> getFileIds() {
		return fileIds;
	}
	public void setFileIds(List<Long> fileIds) {
		this.fileIds = fileIds;
	}
	public long getGrantId() {
		return grantId;
	}
	public void setGrantId(long grantId) {
		this.grantId = grantId;
	}
	public long getFileId() {
		return fileId;
	}
	public void setFileId(long fileId) {
		this.fileId = fileId;
	}
	
	
	
	
}
