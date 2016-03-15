package com.galaxyinternet.model.sopfile;


import com.galaxyinternet.framework.core.model.BaseEntity;

public class SopVoucherFile extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
    private Long projectId;
    private String projectProgress;
    private String fileWorktype;
    private Long careerLine;
    private String fileSource;
    private String fileType;
    private String remark;
    private String fileStatus;
    private Long fileUid;
    private String filUri;
    private Long fileLength;
    private String fileKey;
    private String bucketName;
    private String fileName;
    private String fileSuffix;

    public Long getProjectId() {
        return projectId;
    }
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
    public String getProjectProgress() {
        return projectProgress;
    }
    public void setProjectProgress(String projectProgress) {
        this.projectProgress = projectProgress == null ? null : projectProgress.trim();
    }
    public String getFileWorktype() {
        return fileWorktype;
    }
    public void setFileWorktype(String fileWorktype) {
    	 this.fileWorktype = fileWorktype == null ? null : fileWorktype.trim();
    }
    public Long getCareerLine() {
        return careerLine;
    }
    public void setCareerLine(Long careerLine) {
        this.careerLine = careerLine;
    }
    public String getFileSource() {
		return fileSource;
	}
	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		 this.fileType = fileType == null ? null : fileType.trim();
	}
	public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
	public String getFileStatus() {
        return fileStatus;
    }
    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }
    public Long getFileUid() {
        return fileUid;
    }
    public void setFileUid(Long fileUid) {
        this.fileUid = fileUid;
    }
    public String getFilUri() {
        return filUri;
    }
    public void setFilUri(String filUri) {
        this.filUri = filUri;
    }
	public Long getFileLength() {
		return fileLength;
	}
	public void setFileLength(Long fileLength) {
		this.fileLength = fileLength;
	}
	public String getFileKey() {
		return fileKey;
	}
	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileSuffix() {
		return fileSuffix;
	}
	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}
	
}