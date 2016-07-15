package com.galaxyinternet.model.touhou;

import java.util.List;

import com.galaxyinternet.framework.core.model.PagableEntity;
import com.galaxyinternet.model.sopfile.SopFile;

public class Delivery extends PagableEntity {
	private static final long serialVersionUID = 1L;

    private Long projectId;

    private String delDescribe;

    private String details;

    private Byte delStatus;  //0:未完成  1:已完成
    private String statusFormat;

    private Byte fileNum;

    private Long createdUid;

    private Long updatedUid;
    
    private List<SopFile> files; 
    private List<Long> fileIds;

    private String endByUname;
    
    private String fileReidsKey;
    

    public String getFileReidsKey() {
		return fileReidsKey;
	}

	public void setFileReidsKey(String fileReidsKey) {
		this.fileReidsKey = fileReidsKey;
	}

	public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getDelDescribe() {
        return delDescribe;
    }

    public void setDelDescribe(String delDescribe) {
        this.delDescribe = delDescribe == null ? null : delDescribe.trim();
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details == null ? null : details.trim();
    }

    public Byte getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Byte delStatus) {
    	if(delStatus!=null){
			if(delStatus == 0){
				statusFormat = "未完成";
			}else if(delStatus == 1){
				statusFormat = "已完成";
			}
		}
        this.delStatus = delStatus;
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

	public List<SopFile> getFiles() {
		return files;
	}

	public void setFiles(List<SopFile> files) {
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

	public List<Long> getFileIds() {
		return fileIds;
	}

	public void setFileIds(List<Long> fileIds) {
		this.fileIds = fileIds;
	}

    
}