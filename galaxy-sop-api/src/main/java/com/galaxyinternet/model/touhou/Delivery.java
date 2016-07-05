package com.galaxyinternet.model.touhou;

import java.io.File;
import java.util.List;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class Delivery extends PagableEntity {
	private static final long serialVersionUID = 1L;

    private Long projectId;

    private String describe;

    private String details;

    private Byte status;  //0:未完成  1:已完成
    private String statusFormat;

    private Byte fileNum;

    private Long createdUid;

    private Long updatedUid;
    
    private List<File> files; 

    private String endByUname;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details == null ? null : details.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
    	if(status!=null){
			if(status == 0){
				statusFormat = "未完成";
			}else if(status == 1){
				statusFormat = "已完成";
			}
		}
        this.status = status;
    }

    public Byte getFileNum() {
        return fileNum;
    }

    public void setFileNum(Byte fileNum) {
        this.fileNum = fileNum;
    }

    public Long getCreatedUid() {
        return createdUid;
    }

    public void setCreatedUid(Long createdUid) {
        this.createdUid = createdUid;
    }


    public Long getUpdatedUid() {
        return updatedUid;
    }

    public void setUpdatedUid(Long updatedUid) {
        this.updatedUid = updatedUid;
    }

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public String getEndByUname() {
		return endByUname;
	}

	public void setEndByUname(String endByUname) {
		this.endByUname = endByUname;
	}

	public String getStatusFormat() {
		return statusFormat;
	}

	public void setStatusFormat(String statusFormat) {
		this.statusFormat = statusFormat;
	}

    
}