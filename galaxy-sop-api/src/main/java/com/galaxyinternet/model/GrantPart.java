package com.galaxyinternet.model;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.galaxyinternet.framework.core.model.PagableEntity;
import com.galaxyinternet.model.sopfile.SopFile;

public class GrantPart extends PagableEntity{
	
	private static final long serialVersionUID = 1L;
	
	private Long totalGrantId;
	private String grantName;
	private String grantDetail;
	private Double grantMoney;
	private Long createUid;
	private String createUname;
	private GrantTotal grantTotal;
	/**
	 * 默认0
	 */
	private Integer partStatus = 0;
	private String fileReidsKey;
	private List<SopFile> files; 
	private byte fileNum;
    private List<Long> fileIds;	
	
	
	
	public List<Long> getFileIds() {
		return fileIds;
	}
	public void setFileIds(List<Long> fileIds) {
		this.fileIds = fileIds;
	}
	public Long getTotalGrantId() {
		return totalGrantId;
	}
	public void setTotalGrantId(Long totalGrantId) {
		this.totalGrantId = totalGrantId;
	}
	public String getGrantName() {
		return grantName;
	}
	public void setGrantName(String grantName) {
		this.grantName = grantName;
	}
	public String getGrantDetail() {
		return grantDetail;
	}
	public void setGrantDetail(String grantDetail) {
		this.grantDetail = grantDetail;
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
	public Integer getPartStatus() {
		return partStatus;
	}
	public void setPartStatus(Integer partStatus) {
		this.partStatus = partStatus;
	}

	//---bo---
	private double actualMoney;
	public double getActualMoney() {
		return actualMoney;
	}
	public void setActualMoney(double actualMoney) {
		this.actualMoney = actualMoney;
	}
	
	
	public String getFileReidsKey() {
		return fileReidsKey;
	}
	public void setFileReidsKey(String fileReidsKey) {
		this.fileReidsKey = fileReidsKey;
	}
	public List<SopFile> getFiles() {
		return files;
	}
	public void setFiles(List<SopFile> files) {
		this.files = files;
	}
    public Byte getFileNum() {
        return fileNum;
    }

    public void setFileNum(Byte fileNum) {
        this.fileNum = fileNum;
    }
	
	public GrantTotal getGrantTotal() {
		return grantTotal;
	}
	public void setGrantTotal(GrantTotal grantTotal) {
		this.grantTotal = grantTotal;
	}
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
