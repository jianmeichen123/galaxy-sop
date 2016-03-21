package com.galaxyinternet.bo;

import java.util.List;

import com.galaxyinternet.model.operationLog.OperationLogs;

public class OperationLogsBo extends OperationLogs {
	private static final long serialVersionUID = 1L;
	
	private Integer pageNum;// 页码，默认是第一页
	private Integer pageSize;// 每页显示的记录数，默认是10
	
	private Integer isAll; //1:queryAll
	private List<Long> projectIdList;
	
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
	
	public Integer getIsAll() {
		return isAll;
	}
	public void setIsAll(Integer isAll) {
		this.isAll = isAll;
	}
	public List<Long> getProjectIdList() {
		return projectIdList;
	}
	public void setProjectIdList(List<Long> projectIdList) {
		this.projectIdList = projectIdList;
	}
	
	

}