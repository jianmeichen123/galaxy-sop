package com.galaxyinternet.model.hr;

import java.util.Date;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class PersonWork extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	private String uuid;
	private Long personId;//关联人力资源ID
	private String companyName;//公司名称
	private String workDepart;//所在部门
	private String workPosition;//所处职位
	private String workContent;//工作内容
	private String workEffect;//工作业绩
	private Double workEmolument;//薪酬
	private Date beginWork;//入职时间 
	private Date overWork;//离职时间
	private String leaveReason;//离职原因
	private String leaderName;//上级姓名
	private String leaderPosition;//上级职位
	private String leaderRelationship;//上级关系
	private String leaderPhone;//上级电话
	private String colleagueName;//同事姓名
	private String colleaguePosition;//同事职位
	private String colleagueRelationship; //同事工作关系  新加入的字段
	private String colleaguePhone;//同事电话
	
	private String beginWorkStr;//入职时间 
	private String overWorkStr;//离职时间 
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getWorkDepart() {
		return workDepart;
	}
	public void setWorkDepart(String workDepart) {
		this.workDepart = workDepart;
	}
	public String getWorkPosition() {
		return workPosition;
	}
	public void setWorkPosition(String workPosition) {
		this.workPosition = workPosition;
	}
	public String getWorkContent() {
		return workContent;
	}
	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}
	public String getWorkEffect() {
		return workEffect;
	}
	public void setWorkEffect(String workEffect) {
		this.workEffect = workEffect;
	}
	
	public Double getWorkEmolument() {
		return workEmolument;
	}
	public void setWorkEmolument(Double workEmolument) {
		this.workEmolument = workEmolument;
	}
	public Date getBeginWork() {
		return beginWork;
	}
	public void setBeginWork(Date beginWork) {
		this.beginWork = beginWork;
	}
	public Date getOverWork() {
		return overWork;
	}
	public void setOverWork(Date overWork) {
		this.overWork = overWork;
	}
	public String getLeaveReason() {
		return leaveReason;
	}
	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}
	public String getLeaderPosition() {
		return leaderPosition;
	}
	public void setLeaderPosition(String leaderPosition) {
		this.leaderPosition = leaderPosition;
	}
	public String getLeaderPhone() {
		return leaderPhone;
	}
	public void setLeaderPhone(String leaderPhone) {
		this.leaderPhone = leaderPhone;
	}
	public String getColleagueName() {
		return colleagueName;
	}
	public void setColleagueName(String colleagueName) {
		this.colleagueName = colleagueName;
	}
	public String getColleaguePosition() {
		return colleaguePosition;
	}
	public void setColleaguePosition(String colleaguePosition) {
		this.colleaguePosition = colleaguePosition;
	}
	public String getColleaguePhone() {
		return colleaguePhone;
	}
	public void setColleaguePhone(String colleaguePhone) {
		this.colleaguePhone = colleaguePhone;
	}
	public String getLeaderName() {
		return leaderName;
	}
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	public String getLeaderRelationship() {
		return leaderRelationship;
	}
	public void setLeaderRelationship(String leaderRelationship) {
		this.leaderRelationship = leaderRelationship;
	}
	public String getColleagueRelationship() {
		return colleagueRelationship;
	}
	public void setColleagueRelationship(String colleagueRelationship) {
		this.colleagueRelationship = colleagueRelationship;
	}
	public String getBeginWorkStr() {
		return beginWorkStr;
	}
	public void setBeginWorkStr(String beginWorkStr) {
		this.beginWorkStr = beginWorkStr;
	}
	public String getOverWorkStr() {
		return overWorkStr;
	}
	public void setOverWorkStr(String overWorkStr) {
		this.overWorkStr = overWorkStr;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(uuid != null && obj != null && obj instanceof PersonWork && ((PersonWork) obj).getUuid() != null){
			return this.getUuid().equals(((PersonWork) obj).getUuid());
		}
		return super.equals(obj);
	}
}
