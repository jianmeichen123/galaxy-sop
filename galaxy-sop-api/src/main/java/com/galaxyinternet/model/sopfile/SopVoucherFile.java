package com.galaxyinternet.model.sopfile;




public class SopVoucherFile extends SopParentFile {
	
	private static final long serialVersionUID = 1L;
	

    private String projectProgress;
    private Long careerLine;
    private String fileSource;
    private String fileType;
    private String remark;
    private String fileStatus;
    private Long fileUid;
    private String filUri;


    public String getProjectProgress() {
        return projectProgress;
    }
    public void setProjectProgress(String projectProgress) {
        this.projectProgress = projectProgress == null ? null : projectProgress.trim();
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

	
}