package com.galaxyinternet.bo;

import com.galaxyinternet.model.operationLog.OperationLogs;

public class OperationLogsBo extends OperationLogs {
	private static final long serialVersionUID = 1L;
	
	private Integer pageNum;// 页码，默认是第一页
	private Integer pageSize;// 每页显示的记录数，默认是10
	
	private Integer isMine;
	private Integer isMyDepart;
	
	public Integer getPageNum() {
		if(pageNum == null){
			pageNum = 0;
		}
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	
	public Integer getPageSize() {
		if(pageSize == null){
			pageSize = 10;
		}
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getIsMine() {
		return isMine;
	}
	public void setIsMine(Integer isMine) {
		this.isMine = isMine;
	}
	public Integer getIsMyDepart() {
		return isMyDepart;
	}
	public void setIsMyDepart(Integer isMyDepart) {
		this.isMyDepart = isMyDepart;
	}
	
	

}