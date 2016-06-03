package com.galaxyinternet.bo;

import java.util.List;

import com.galaxyinternet.model.operationLog.OperationLogs;
/**
 * App端项目动态（操作日志）的业务信息类
 * @author LZJ
 * @ClassName  : AppOperationLogsBo  
 * @Version  版本   
 * @ModifiedBy修改人  
 * @Copyright  Galaxyinternet  
 * @date  2016年5月17日 上午11:33:47
 */
public class AppOperationLogsBo extends OperationLogs{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6193061402852733227L;
	
	private Integer pageNum;// 页码，默认是第一页
	private Integer pageSize;// 每页显示的记录数，默认是10	
//	private Integer isAll; //1:queryAll
	private List<Long> projectIdList;	//项目ID列表
	private Integer offset=0; //记录起始记录索引，默认值0
	private Integer limit;//获取记录的数量

	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public List<Long> getProjectIdList() {
		return projectIdList;
	}
	public void setProjectIdList(List<Long> projectIdList) {
		this.projectIdList = projectIdList;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

}
