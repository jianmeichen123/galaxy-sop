package com.galaxyinternet.model.hr;

import java.util.Date;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class PersonLearn extends BaseEntity{

	private static final long serialVersionUID = 1L;
	private String uuid;
	private Long personId; //关联人力资源的ID 
	private String degree; //字典 学历,关联数据字典数据项ID
	private String school; //学校
	private String major;   //专业 education_type             
	private String educationType; //字典  学制  全日制、自考...educationType
	private Date   beginDate; //入学时间
	private Date   overDate; // 毕业时间
	private String certificateNumber; //证书编号      
	private String teacherName;//老师姓名
	private String teacherPosition;//老师部门
	private String teacherPhone; //老师电话
	private String classmateName;//同学姓名
	private String classmatePhone;//同学电话
	private String   beginDateStr; //入学时间
	private String   overDateStr; // 毕业时间
	
	
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
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getEducationType() {
		return educationType;
	}
	public void setEducationType(String educationType) {
		this.educationType = educationType;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getOverDate() {
		return overDate;
	}
	public void setOverDate(Date overDate) {
		this.overDate = overDate;
	}
	public String getCertificateNumber() {
		return certificateNumber;
	}
	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getTeacherPosition() {
		return teacherPosition;
	}
	public void setTeacherPosition(String teacherPosition) {
		this.teacherPosition = teacherPosition;
	}
	public String getTeacherPhone() {
		return teacherPhone;
	}
	public void setTeacherPhone(String teacherPhone) {
		this.teacherPhone = teacherPhone;
	}
	public String getClassmateName() {
		return classmateName;
	}
	public void setClassmateName(String classmateName) {
		this.classmateName = classmateName;
	}
	public String getClassmatePhone() {
		return classmatePhone;
	}
	public void setClassmatePhone(String classmatePhone) {
		this.classmatePhone = classmatePhone;
	}
	public String getBeginDateStr() {
		return beginDateStr;
	}
	public void setBeginDateStr(String beginDateStr) {
		this.beginDateStr = beginDateStr;
	}
	public String getOverDateStr() {
		return overDateStr;
	}
	public void setOverDateStr(String overDateStr) {
		this.overDateStr = overDateStr;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(uuid != null && obj != null && obj instanceof PersonLearn && ((PersonLearn) obj).getUuid() != null){
			return this.getUuid().equals(((PersonLearn) obj).getUuid());
		}
		return super.equals(obj);
	}
}
