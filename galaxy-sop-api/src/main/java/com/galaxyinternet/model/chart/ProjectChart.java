package com.galaxyinternet.model.chart;

/**
 * 项目
 * @author wangkun
 *
 */
public class ProjectChart extends Chart{
	
	private static final long serialVersionUID = 1L;
	
	private Long id; // 项目id
	private String projectName; // 项目名称
	private Long partner; // 合伙人
	private String userName;// 投资经理
	private String projectLine; // 投资事业线
	private String projectType;// 项目类型
	private String projectStatus;// 项目阶段状态
	private String creatTime;// 项目创建时间
	private String lxhCreatedTime; // 立项会申请时间
	private Long durationMillisecond;// 总历时，毫秒
	private Integer durationDay;// 总历时，天
	private Long ftDurationMillisecond;// 访谈历时,毫秒
	private Integer ftDurationDay;// 访谈历时，天
	private Long npDurationMillisecond;// 内评历时,毫秒
	private Integer npDurationDay;// 内评历时，天
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Long getPartner() {
		return partner;
	}
	public void setPartner(Long partner) {
		this.partner = partner;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getProjectLine() {
		return projectLine;
	}
	public void setProjectLine(String projectLine) {
		this.projectLine = projectLine;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public String getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}
	public String getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}
	public String getLxhCreatedTime() {
		return lxhCreatedTime;
	}
	public void setLxhCreatedTime(String lxhCreatedTime) {
		this.lxhCreatedTime = lxhCreatedTime;
	}
	public Long getDurationMillisecond() {
		return durationMillisecond;
	}
	public void setDurationMillisecond(Long durationMillisecond) {
		this.durationMillisecond = durationMillisecond;
	}
	public Integer getDurationDay() {
		return durationDay;
	}
	public void setDurationDay(Integer durationDay) {
		this.durationDay = durationDay;
	}
	public Long getFtDurationMillisecond() {
		return ftDurationMillisecond;
	}
	public void setFtDurationMillisecond(Long ftDurationMillisecond) {
		this.ftDurationMillisecond = ftDurationMillisecond;
	}
	public Integer getFtDurationDay() {
		return ftDurationDay;
	}
	public void setFtDurationDay(Integer ftDurationDay) {
		this.ftDurationDay = ftDurationDay;
	}
	public Long getNpDurationMillisecond() {
		return npDurationMillisecond;
	}
	public void setNpDurationMillisecond(Long npDurationMillisecond) {
		this.npDurationMillisecond = npDurationMillisecond;
	}
	public Integer getNpDurationDay() {
		return npDurationDay;
	}
	public void setNpDurationDay(Integer npDurationDay) {
		this.npDurationDay = npDurationDay;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
