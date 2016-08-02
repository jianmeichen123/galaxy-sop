package com.galaxyinternet.model.project;


import com.galaxyinternet.framework.core.model.PagableEntity;
import com.galaxyinternet.framework.core.utils.DateUtil;

public class ProjectTransfer extends PagableEntity {
	private static final long serialVersionUID = 1L;

	private Long projectId; //项目id
	private Long beforeUid; //项目移交人
	private Long afterUid;  //项目接收人
	private Long afterDepartmentId;  //项目接受部门 
	private String transferReason;//项目移交原因
	private String recordStatus; //移交状态
    private String undoReason; //
    private Long refuseReason;//决绝原因
   	private String createDate;//创建时间
   	private String updateDate;//修改时间
   	
   	
   	
    public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getBeforeUid() {
		return beforeUid;
	}

	public void setBeforeUid(Long beforeUid) {
		this.beforeUid = beforeUid;
	}

	public Long getAfterUid() {
		return afterUid;
	}

	public void setAfterUid(Long afterUid) {
		this.afterUid = afterUid;
	}

	public Long getAfterDepartmentId() {
		return afterDepartmentId;
	}

	public void setAfterDepartmentId(Long afterDepartmentId) {
		this.afterDepartmentId = afterDepartmentId;
	}

	public String getTransferReason() {
		return transferReason;
	}

	public void setTransferReason(String transferReason) {
		this.transferReason = transferReason;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public String getUndoReason() {
		return undoReason;
	}

	public void setUndoReason(String undoReason) {
		this.undoReason = undoReason;
	}

	public Long getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(Long refuseReason) {
		this.refuseReason = refuseReason;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateDate() {
		return createDate;
	}
    
    @Override
    public void setCreatedTime(Long createdTime) {
    	this.createdTime = createdTime;
    	if(createdTime != null){
    		this.createDate = DateUtil.longToString(createdTime);
    	}
    }
    
    public String getUpdateDate() {
		return updateDate;
	}
    public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public void setUpdatedTime(Long updatedTime) {
    	this.updatedTime = updatedTime;
    	if(updatedTime != null){
    		this.updateDate = DateUtil.longToString(updatedTime);
    	}
    }
   	
  
}