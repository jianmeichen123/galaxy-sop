package com.galaxyinternet.common.query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.galaxyinternet.common.ViewQuery;
import com.galaxyinternet.framework.core.model.PagableEntity;
import com.galaxyinternet.framework.core.utils.DateUtil;


public class ChartKpiQuery extends PagableEntity {
	private static final long serialVersionUID = 1L;
	
	private String sdate;   //  开始时间
	private Long startTime;   //  开始时间
	private String edate;   //  结束时间
	private Long endTime;
	private Long deptid;    //  部门编号
	private String projectType;
	
	
	
	
	
	
	
	
	
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
	}
	public Long getDeptid() {
		return deptid;
	}
	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
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
	
	
	
	
	
}
