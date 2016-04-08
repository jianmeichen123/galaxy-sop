package com.galaxyinternet.model.template;

import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.framework.core.model.BaseEntity;

public class SopTemplate extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private String worktype;
	private String workTypeDesc;
	private Long departmentId;
	private String departmentDesc;
	private String docType;
	private String docTypeDesc;
	private String fileUri;
	private Long fileLength;
    private String fileKey;
    private String bucketName;
	private Long createUid;
    private Long updateUid;
    private String updateUname;
    private String remark;
    private String fileName;
    
    
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getWorktype() {
		return worktype;
	}
	public void setWorktype(String worktype) {
		this.worktype = worktype;
		if(worktype != null)
		{
			workTypeDesc = DictEnum.TemplateType.getNameByCode(worktype);
		}
	}
	public String getWorkTypeDesc() {
		return workTypeDesc;
	}
	public void setWorkTypeDesc(String workTypeDesc) {
		this.workTypeDesc = workTypeDesc;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentDesc() {
		return departmentDesc;
	}
	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
		if(docType != null)
		{
			docTypeDesc = DictEnum.fileType.getNameByCode(docType);
		}
	}
	public String getDocTypeDesc() {
		return docTypeDesc;
	}
	public void setDocTypeDesc(String docTypeDesc) {
		this.docTypeDesc = docTypeDesc;
	}
	public String getFileUri() {
		return fileUri;
	}
	public void setFileUri(String fileUri) {
		this.fileUri = fileUri;
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
	public Long getCreateUid() {
		return createUid;
	}
	public void setCreateUid(Long createUid) {
		this.createUid = createUid;
	}
	public Long getUpdateUid() {
		return updateUid;
	}
	public void setUpdateUid(Long updateUid) {
		this.updateUid = updateUid;
	}
	public String getUpdateUname() {
		return updateUname;
	}
	public void setUpdateUname(String updateUname) {
		this.updateUname = updateUname;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
    
    
	
}
