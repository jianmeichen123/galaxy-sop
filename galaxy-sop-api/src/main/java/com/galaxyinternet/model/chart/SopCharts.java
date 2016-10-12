package com.galaxyinternet.model.chart;

import java.io.Serializable;

import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.framework.core.model.BaseEntity;
import com.galaxyinternet.framework.core.utils.DateUtil;

public class SopCharts extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 项目阶段
	 */
	private String projectProgress;

	/**
	 * 阶段项目比率 (程序中计算)
	 */
	private String projectRate;
	
	/**
	 * 项目环比(增长率)
	 */
	private String riseRate;
	
	/**
	 * 项目阶段名称
	 */
	private String projectProgressName;
	
	/**
	 * 项目所属事业线
	 */
	private Long departmentId;
	
	/**
	 * 排序
	 */
	private int sort;
	

	/**
	 * 以下为查询用
	 * 开始时间
	 */
	private Long startTime;
	
	/**
	 * 结束时间
	 */
	private Long endTime;
	/**
	 *  项目类型
	 */
	private String projectType;
	
	private Long createUid;
	

	
	
	
	/**
	 * 下为数据简报
	 * 年份
	 */
	private String projectDate;
	
	/**
	 * 年份
	 */
	private Long projectTime;
	
	/**
	 * 项目完成数量 	
	 */
	private Long projectCount;
	/**
	 * 项目总数量
	 */
	private Long totalCount;
	
	/**
	 * 内部数量
	 */
	private Long nbCount;
	
	/**
	 * 外部数量
	 */
	private Long wbCount;
	
	/**
	 * 目标数
	 */
	private Long targetCount;	
	/**
	 * 未完成数
	 */
	private Long notCompleteCount;
	
	/**
	 * 超额数
	 */
	private Long aboveCount;
	
	/**
	 * 项目完成率
	 */
	private String completedRate ;
	
	/**
	 * 分析日期格式化串 
	 * %Y-%m-%d		2016-06-08
	 * %Y-%m		2016-06
	 * %Y			2016
	 */
	private String dateType;
	
	/**
	 * 估值
	 */
	private Long appraisement;
	
	/**
	 * 投资
	 */
	private Long invest;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private String createUname;
	private String departmentName;
	private String projectTypeName;
	
	/**
	 * 业务部门大分类(1:联合创业 2：融快 3：创宝联)
	 */
	private Long belongType;

	
	
	protected Integer pageSize;
	protected Integer pageNum;
	
	
	private String projectStatus;
	
	
	
	public String getProjectProgress() {
		return projectProgress;
	}
	public void setProjectProgress(String projectProgress) {
		this.projectProgress = projectProgress;
	}
	public Long getProjectCount() {
		return projectCount;
	}
	public void setProjectCount(Long projectCount) {
		this.projectCount = projectCount;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	
	public String getProjectRate() {
		return projectRate;
	}
	public void setProjectRate(String projectRate) {
		this.projectRate = projectRate;
	}
	public String getProjectProgressName() {
		return projectProgressName;
	}
	public void setProjectProgressName(String projectProgressName) {
		this.projectProgressName = projectProgressName;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType == null ? null : projectType.trim();
		if (projectType != null) {
			this.projectTypeName = DictEnum.projectType
					.getNameByCode(projectType);
		}
	}

	
	


	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public String getProjectDate() {
		return projectDate;
	}
	public void setProjectDate(String projectDate) {
		this.projectDate = projectDate;
	}
	public Long getProjectTime() {
		return projectTime;
	}
	public void setProjectTime(Long projectTime) {
		this.projectTime = projectTime;
		if (projectTime != null) {
			this.projectDate = DateUtil.longToString(projectTime);
		}
	}
	
	public String getCompletedRate() {
		return completedRate;
	}
	public void setCompletedRate(String completedRate) {
		this.completedRate = completedRate;
	}
	
	public Long getTargetCount() {
		return targetCount;
	}

	public void setTargetCount(Long targetCount) {
		this.targetCount = targetCount;
	}

	public Long getNotCompleteCount() {
		return notCompleteCount;
	}

	public void setNotCompleteCount(Long notCompleteCount) {
		this.notCompleteCount = notCompleteCount;
	}

	public Long getAboveCount() {
		return aboveCount;
	}

	public void setAboveCount(Long aboveCount) {
		this.aboveCount = aboveCount;
	}
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	public String getRiseRate() {
		return riseRate;
	}
	public void setRiseRate(String riseRate) {
		this.riseRate = riseRate;
	}
	public Long getCreateUid() {
		return createUid;
	}
	public void setCreateUid(Long createUid) {
		this.createUid = createUid;
	}
	public String getCreateUname() {
		return createUname;
	}
	public void setCreateUname(String createUname) {
		this.createUname = createUname;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getProjectTypeName() {
		return projectTypeName;
	}
	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Long getAppraisement() {
		return appraisement;
	}
	public void setAppraisement(Long appraisement) {
		this.appraisement = appraisement;
	}
	public Long getInvest() {
		return invest;
	}
	public void setInvest(Long invest) {
		this.invest = invest;
	}
	public String getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}
	public Long getNbCount() {
		return nbCount;
	}
	public void setNbCount(Long nbCount) {
		this.nbCount = nbCount;
	}
	public Long getWbCount() {
		return wbCount;
	}
	public void setWbCount(Long wbCount) {
		this.wbCount = wbCount;
	}
	public Long getBelongType() {
		return belongType;
	}
	public void setBelongType(Long belongType) {
		this.belongType = belongType;
	}
	
	
	
	
}
