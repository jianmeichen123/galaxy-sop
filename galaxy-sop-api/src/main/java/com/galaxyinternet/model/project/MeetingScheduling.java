package com.galaxyinternet.model.project;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.galaxyinternet.framework.core.model.PagableEntity;
import com.galaxyinternet.framework.core.utils.DateUtil;

public class MeetingScheduling extends PagableEntity {
	private static final long serialVersionUID = 1L;

	
	private String type;//标志 0：今日会议 1:待开的会议

	
	private Long projectId;

	private String meetingType;

	private Integer meetingCount;

	private Date meetingDate;

	private String status;

	private String remark;
	private Integer scheduleStatus;
	private Timestamp applyTime;
	private Timestamp lastTime;

	private Timestamp reserveTimeStart;
	private Timestamp reserveTimeEnd;

	// 排序字段名称
	private String sortName;
	// 排序方式
	private String sortDirection;
	// 过滤字段
	private String filterName;

	private List<Long> deptIdList;
	private List<Long> projectIdList;
	// 项目名称
	private String projectName;
	// 投资事业线
	private String projectCareerline;
	// 投资经理
	private String createUname;
	private String meetingDateStr;
	private String applyTimeStr;
	private String createId;

	private String reserveTimeStartStr;
	private String reserveTimeEndStr;
	// 投资事业线部门id
	private Integer careline;

	// 申请时间(申请立项会时间)
	private Date meetTime;

	// 项目代码
	private String projectCode;

	// 权限部分
	private byte isEdit;
	private byte isTransfor;

	private Double meetingRate;
	
	private List<Long> ids;//排期id集合

	//report
	private Long startTime; 
	private Long endTime;
	
	
	public byte getIsTransfor() {
		return isTransfor;
	}

	public void setIsTransfor(byte isTransfor) {
		this.isTransfor = isTransfor;
	}

	public Timestamp getReserveTimeEnd() {
		if (StringUtils.isNotBlank(this.reserveTimeEndStr)) {
			Date tmp = null;
			Timestamp timestmp = null;
			try {
				tmp = DateUtil
						.convertStringToDateTimeForChina(this.reserveTimeEndStr);
				timestmp = new Timestamp(tmp.getTime());
				this.setReserveTimeEnd(timestmp);
				return timestmp;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return reserveTimeEnd;
	}

	public void setReserveTimeEnd(Timestamp reserveTimeEnd) {
		this.reserveTimeEnd = reserveTimeEnd;
		if (reserveTimeEnd != null) {
			reserveTimeEndStr = DateUtil
					.convertDateToStringForChina(reserveTimeEnd);
		}
	}

	public String getReserveTimeStartStr() throws ParseException {
		return reserveTimeStartStr;
	}

	public void setReserveTimeStartStr(String reserveTimeStartStr) {
		this.reserveTimeStartStr = reserveTimeStartStr;
	}

	public String getReserveTimeEndStr() {
		return reserveTimeEndStr;
	}

	public void setReserveTimeEndStr(String reserveTimeEndStr) {
		this.reserveTimeEndStr = reserveTimeEndStr;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getMeetingDateStr() {
		return meetingDateStr;
	}

	public void setMeetingDateStr(String meetingDateStr) {
		this.meetingDateStr = meetingDateStr;
	}

	public String getApplyTimeStr() {
		return applyTimeStr;
	}

	public void setApplyTimeStr(String applyTimeStr) {
		this.applyTimeStr = applyTimeStr;
	}

	public String getProjectCareerline() {
		return projectCareerline;
	}

	public void setProjectCareerline(String projectCareerline) {
		this.projectCareerline = projectCareerline;
	}

	public String getCreateUname() {
		return createUname;
	}

	public void setCreateUname(String createUname) {
		this.createUname = createUname;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getMeetingType() {
		return meetingType;
	}

	public void setMeetingType(String meetingType) {
		this.meetingType = meetingType == null ? null : meetingType.trim();
    	
		
	}

	public Integer getMeetingCount() {
		return meetingCount;
	}

	public void setMeetingCount(Integer meetingCount) {
		this.meetingCount = meetingCount;
	}

	public Date getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(Date meetingDate) {
		this.meetingDate = meetingDate;
		if (meetingDate != null) {
			meetingDateStr = DateUtil.convertDateToStringForChina(meetingDate);
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Integer getScheduleStatus() {
		return scheduleStatus;
	}

	public void setScheduleStatus(Integer scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}

	public Timestamp getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Timestamp applyTime) {
		this.applyTime = applyTime;
		if (applyTime != null) {
			applyTimeStr = DateUtil.convertDateToStringForChina(applyTime);
		}
	}

	public Timestamp getReserveTimeStart() {
		if (StringUtils.isNotBlank(this.reserveTimeStartStr)) {
			Date tmp = null;
			Timestamp timestmp = null;
			try {
				tmp = DateUtil
						.convertStringToDateTimeForChina(this.reserveTimeStartStr);
				timestmp = new Timestamp(tmp.getTime());
				this.setReserveTimeStart(timestmp);
				return timestmp;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return reserveTimeStart;
	}

	public void setReserveTimeStart(Timestamp reserveStartTime) {
		this.reserveTimeStart = reserveStartTime;
		if (reserveStartTime != null) {
			reserveTimeStartStr = DateUtil
					.convertDateToStringForChina(reserveTimeStart);
		}
	}

	public Timestamp getLastTime() {
		return lastTime;
	}

	public void setLastTime(Timestamp lastTime) {
		this.lastTime = lastTime;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public List<Long> getDeptIdList() {
		return deptIdList;
	}

	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}

	public List<Long> getProjectIdList() {
		return projectIdList;
	}

	public void setProjectIdList(List<Long> projectIdList) {
		this.projectIdList = projectIdList;
	}

	public Date getMeetTime() {
		return meetTime;
	}

	public void setMeetTime(Date meetTime) {
		this.meetTime = meetTime;
	}

	public byte getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(byte isEdit) {
		this.isEdit = isEdit;
	}

	public Integer getCareline() {
		return careline;
	}

	public void setCareline(Integer careline) {
		this.careline = careline;
	}

	public Double getMeetingRate() {
		return meetingRate;
	}

	public void setMeetingRate(Double meetingRate) {
		this.meetingRate = meetingRate;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}
    
}