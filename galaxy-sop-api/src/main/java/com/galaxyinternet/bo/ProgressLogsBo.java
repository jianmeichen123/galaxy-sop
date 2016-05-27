package com.galaxyinternet.bo;

import java.util.List;

import com.galaxyinternet.model.common.ProgressLog;
 //app端需要创意动态的类
public class ProgressLogsBo extends ProgressLog {
	
	private static final long serialVersionUID = 1L;
	
	private Integer pageNum;// 页码，默认是第一页
	private Integer pageSize;// 每页显示的记录数，默认是10	
//	private Integer isAll; //1:queryAll
	private List<Long> relatedIdList;	//创意ID列表
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

	public List<Long> getRelatedIdList() {
		return relatedIdList;
	}
	public void setRelatedIdList(List<Long> relatedIdList) {
		this.relatedIdList = relatedIdList;
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