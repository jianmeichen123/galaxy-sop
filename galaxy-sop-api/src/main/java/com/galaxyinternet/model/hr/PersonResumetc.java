package com.galaxyinternet.model.hr;

import java.util.Date;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class PersonResumetc extends BaseEntity {

	/**
	 * gxc大表单的model
	 */
	private static final long serialVersionUID = 1L;

		//完善简历的外部项目信息		
		private Long personId;//关联人力资源id
		private String icompanyName;//公司名称
		private Double investmentAmount;//投资金额 investment_amount
		private Double shareRatio;//股权占比
		private String projectDirector;//项目负责人;
		private String emceedPosition;//担任职位
		private String telephone;//联系方式
		private String acompanyName;//a轮投资公司名称
		private Double ainvestmentAmount;//a轮投资金额
		private Double ashareRatio;//a轮股权占比
		private String atelephone;//a轮联系方式
		
		//下面是personpool的字段
		private String personName;
		private Integer personSex;
	    private Integer personAge;
	    private String highestDegree;
	    private Integer workTime;
	    private String personDuties;
	    private Date personBirthday;
	    private String personIdcard;
	    private String personTelephone;
	    private String personEmail;
	    private String personCharacter;
	    private String personGoodness;
	    private String personDisparity;
	    private Integer talkAbility;
	    private Integer teamAbility;
	    private String businessStrength;
	    private Integer free;
	    private String teamRole;
	    private String memberRelation;
	    private Integer laborDispute;
	    private Integer abilityStar;
	    private Integer levelStar;
	    private String endComment;
	    private Long createId;
		
	    //下面是完善简历的工作经历
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
		
		
		
		//下面是完善简历的学习经历的 字段
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
		
		public String getPersonName() {
			return personName;
		}
		public void setPersonName(String personName) {
			this.personName = personName;
		}
		public Integer getPersonSex() {
			return personSex;
		}
		public void setPersonSex(Integer personSex) {
			this.personSex = personSex;
		}
		public Integer getPersonAge() {
			return personAge;
		}
		public void setPersonAge(Integer personAge) {
			this.personAge = personAge;
		}
		public String getHighestDegree() {
			return highestDegree;
		}
		public void setHighestDegree(String highestDegree) {
			this.highestDegree = highestDegree;
		}
		public Integer getWorkTime() {
			return workTime;
		}
		public void setWorkTime(Integer workTime) {
			this.workTime = workTime;
		}
		public String getPersonDuties() {
			return personDuties;
		}
		public void setPersonDuties(String personDuties) {
			this.personDuties = personDuties;
		}
		public Date getPersonBirthday() {
			return personBirthday;
		}
		public void setPersonBirthday(Date personBirthday) {
			this.personBirthday = personBirthday;
		}
		public String getPersonIdcard() {
			return personIdcard;
		}
		public void setPersonIdcard(String personIdcard) {
			this.personIdcard = personIdcard;
		}
		public String getPersonTelephone() {
			return personTelephone;
		}
		public void setPersonTelephone(String personTelephone) {
			this.personTelephone = personTelephone;
		}
		public String getPersonEmail() {
			return personEmail;
		}
		public void setPersonEmail(String personEmail) {
			this.personEmail = personEmail;
		}
		public String getPersonCharacter() {
			return personCharacter;
		}
		public void setPersonCharacter(String personCharacter) {
			this.personCharacter = personCharacter;
		}
		public String getPersonGoodness() {
			return personGoodness;
		}
		public void setPersonGoodness(String personGoodness) {
			this.personGoodness = personGoodness;
		}
		public String getPersonDisparity() {
			return personDisparity;
		}
		public void setPersonDisparity(String personDisparity) {
			this.personDisparity = personDisparity;
		}
		public Integer getTalkAbility() {
			return talkAbility;
		}
		public void setTalkAbility(Integer talkAbility) {
			this.talkAbility = talkAbility;
		}
		public Integer getTeamAbility() {
			return teamAbility;
		}
		public void setTeamAbility(Integer teamAbility) {
			this.teamAbility = teamAbility;
		}
		public String getBusinessStrength() {
			return businessStrength;
		}
		public void setBusinessStrength(String businessStrength) {
			this.businessStrength = businessStrength;
		}
		public Integer getFree() {
			return free;
		}
		public void setFree(Integer free) {
			this.free = free;
		}
		public String getTeamRole() {
			return teamRole;
		}
		public void setTeamRole(String teamRole) {
			this.teamRole = teamRole;
		}
		public String getMemberRelation() {
			return memberRelation;
		}
		public void setMemberRelation(String memberRelation) {
			this.memberRelation = memberRelation;
		}
		public Integer getLaborDispute() {
			return laborDispute;
		}
		public void setLaborDispute(Integer laborDispute) {
			this.laborDispute = laborDispute;
		}
		public Integer getAbilityStar() {
			return abilityStar;
		}
		public void setAbilityStar(Integer abilityStar) {
			this.abilityStar = abilityStar;
		}
		public Integer getLevelStar() {
			return levelStar;
		}
		public void setLevelStar(Integer levelStar) {
			this.levelStar = levelStar;
		}
		public String getEndComment() {
			return endComment;
		}
		public void setEndComment(String endComment) {
			this.endComment = endComment;
		}
		public Long getCreateId() {
			return createId;
		}
		public void setCreateId(Long createId) {
			this.createId = createId;
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
		public String getLeaderName() {
			return leaderName;
		}
		public void setLeaderName(String leaderName) {
			this.leaderName = leaderName;
		}
		public String getLeaderPosition() {
			return leaderPosition;
		}
		public void setLeaderPosition(String leaderPosition) {
			this.leaderPosition = leaderPosition;
		}
		public String getLeaderRelationship() {
			return leaderRelationship;
		}
		public void setLeaderRelationship(String leaderRelationship) {
			this.leaderRelationship = leaderRelationship;
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
		public String getColleagueRelationship() {
			return colleagueRelationship;
		}
		public void setColleagueRelationship(String colleagueRelationship) {
			this.colleagueRelationship = colleagueRelationship;
		}
		public String getColleaguePhone() {
			return colleaguePhone;
		}
		public void setColleaguePhone(String colleaguePhone) {
			this.colleaguePhone = colleaguePhone;
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
		public Long getPersonId() {
			return personId;
		}
		public void setPersonId(Long personId) {
			this.personId = personId;
		}

		public String getIcompanyName() {
			return icompanyName;
		}
		public void setIcompanyName(String icompanyName) {
			this.icompanyName = icompanyName;
		}
		public Double getInvestmentAmount() {
			return investmentAmount;
		}
		public void setInvestmentAmount(Double investmentAmount) {
			this.investmentAmount = investmentAmount;
		}
		
		public Double getShareRatio() {
			return shareRatio;
		}
		public void setShareRatio(Double shareRatio) {
			this.shareRatio = shareRatio;
		}
		public String getProjectDirector() {
			return projectDirector;
		}
		public void setProjectDirector(String projectDirector) {
			this.projectDirector = projectDirector;
		}
		public String getEmceedPosition() {
			return emceedPosition;
		}
		public void setEmceedPosition(String emceedPosition) {
			this.emceedPosition = emceedPosition;
		}
		public String getTelephone() {
			return telephone;
		}
		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}
		public String getAcompanyName() {
			return acompanyName;
		}
		public void setAcompanyName(String acompanyName) {
			this.acompanyName = acompanyName;
		}
		public Double getAinvestmentAmount() {
			return ainvestmentAmount;
		}
		public void setAinvestmentAmount(Double ainvestmentAmount) {
			this.ainvestmentAmount = ainvestmentAmount;
		}
		public Double getAshareRatio() {
			return ashareRatio;
		}
		public void setAshareRatio(Double ashareRatio) {
			this.ashareRatio = ashareRatio;
		}
		public String getAtelephone() {
			return atelephone;
		}
		public void setAtelephone(String atelephone) {
			this.atelephone = atelephone;
		}
		public String getCompanyName() {
			return companyName;
		}
		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}
	
}
