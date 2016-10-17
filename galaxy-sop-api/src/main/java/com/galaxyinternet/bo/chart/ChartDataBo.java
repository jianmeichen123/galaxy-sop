package com.galaxyinternet.bo.chart;


import com.galaxyinternet.framework.core.model.PagableEntity;

/**
 * 图表返回数据
 *
 */
public class ChartDataBo extends PagableEntity{
	
	private static final long serialVersionUID = 1L;
	
	
	private Long userId;
	private Long departmentId;
	private String realName;
	private String departmentName;
	
	private Integer target;        //目标数
	private Integer zjCompleted;   //自建项目数
	private Integer wbCompleted;   //外部投资项目数
	private Integer completed;     //项目数,	                项目id数， 创建项目数
	private Integer completedAll;  //累计已完成数     累计已完成的项目数。 仅不受查询时间影响
	private Integer notCompleted;  //未完成数 =目标数-完成项目数

	private Double wbRate;   //外部投资项目数占比
	private Double zjRate;   //自建项目数比率
	
	private Integer companyRank;    //公司排名		个人项目数在公司的排名
	private Integer deptRank;       //部门排名                  个人项目数在本投资事业线的排名
	
	private Double  rate;           //完成率     or  过会率
	private Double  totalRate;      //公司完成数占比             项目数/平台所有项目总和
	private Double  deptRate;       //部门完成数占比		项目数/所在投资事业线的项目总和

	private Integer  lxhPnumber;    //立项会通过数             立项会结果为“通过”的项目id数
	private Integer  tjhPnumber;    //投决会通过数             投资决策会结果为“通过”的项目id数。
	
	private Double   ghlRate;      //过会率           立项会通过数/立项会已过会的项目id数。
	private Double   tjlRate;      //投决率	        投资决策会通过数/投资决策会已过会的项目id数。
	
	private Integer proMeetNum;  //过会项目数   已开会的项目数
	private Integer  vetoMeetProNumber;    //会 失败数             结果为“否决”
	private Integer  waitMeetProNumber;    //会  待定数          结果为“待定”
	private Integer  passMeetProNumber;    //会  通过数          结果为“通过”
	
	
	private String progressCode;
	private String progressName;
	private Long dayLine;        //目标数
	
	
	private String name;
	private Long score1; //分数/生成项目
	private Long score2; //分数/通过CEO评审
	private Long score3; // 分数/通过立项会
	private Long sumScore; //总分数
	
	private Double ceoRate; //CEO评审过会率
	private Double lxhRate; //立项会过会率
	
	
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	/**
	 * 目标数
	 */	
	public Integer getTarget() {
		return target;
	}
	/**
	 * 目标数
	 */	
	public void setTarget(Integer target) {
		this.target = target;
	}
	
	/**
	 * 项目数   ， 项目id数， 创建项目数
	 */	
	public Integer getCompleted() {
		return completed;
	}
	/**
	 * 项目数   ， 项目id数， 创建项目数
	 */	
	public void setCompleted(Integer completed) {
		this.completed = completed;
	}
	
	
	/**
	 * 累计已完成数     累计已完成的项目数
	 */	
	public Integer getCompletedAll() {
		return completedAll;
	}
	/**
	 * 累计已完成数     累计已完成的项目数
	 */	
	public void setCompletedAll(Integer completedAll) {
		this.completedAll = completedAll;
	}
	
	/**
	 * 公司排名; 个人项目数在公司的排名
	 */	
	public Integer getCompanyRank() {
		return companyRank;
	}
	/**
	 * 公司排名; 个人项目数在公司的排名
	 */	
	public void setCompanyRank(Integer companyRank) {
		this.companyRank = companyRank;
	}
	
	/**
	 * 部门排名   ：个人项目数在本投资事业线的排名
	 */
	public Integer getDeptRank() {
		return deptRank;
	}
	/**
	 * 部门排名   ：个人项目数在本投资事业线的排名
	 */	
	public void setDeptRank(Integer deptRank) {
		this.deptRank = deptRank;
	}
	
	/**
	 * 公司完成数占比 ：项目数/平台所有项目总和
	 */	
	public Double getTotalRate() {
		return totalRate;
	}
	/**
	 * 公司完成数占比 ：项目数/平台所有项目总和
	 */	
	public void setTotalRate(Double totalRate) {
		this.totalRate = totalRate;
	}
	
	/**
	 * 部门完成数占比：项目数/所在投资事业线的项目总和
	 */	
	public Double getDeptRate() {
		return deptRate;
	}
	/**
	 * 部门完成数占比：项目数/所在投资事业线的项目总和
	 */	
	public void setDeptRate(Double deptRate) {
		this.deptRate = deptRate;
	}
	
	/**
	 * 立项会通过数  ：立项会结果为“通过”的项目id数
	 */	
	public Integer getLxhPnumber() {
		return lxhPnumber;
	}
	/**
	 * 立项会通过数  ：立项会结果为“通过”的项目id数
	 */	
	public void setLxhPnumber(Integer lxhPnumber) {
		this.lxhPnumber = lxhPnumber;
	}
	
	/**
	 * 投决会通过数 ：投资决策会结果为“通过”的项目id数
	 */	
	public Integer getTjhPnumber() {
		return tjhPnumber;
	}
	/**
	 * 投决会通过数 ：投资决策会结果为“通过”的项目id数
	 */	
	public void setTjhPnumber(Integer tjhPnumber) {
		this.tjhPnumber = tjhPnumber;
	}
	
	/**
	 * 过会率  ：立项会通过数/立项会已过会的项目id数
	 */
	public Double getGhlRate() {
		return ghlRate;
	}
	/**
	 * 过会率  ：立项会通过数/立项会已过会的项目id数
	 */	
	public void setGhlRate(Double ghlRate) {
		this.ghlRate = ghlRate;
	}
	
	/**
	 * 投决率 ：投资决策会通过数/投资决策会已过会的项目id数
	 */	
	public Double getTjlRate() {
		return tjlRate;
	}
	/**
	 * 投决率 ：投资决策会通过数/投资决策会已过会的项目id数
	 */	
	public void setTjlRate(Double tjlRate) {
		this.tjlRate = tjlRate;
	}
	
	
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	/**
	 * 自建项目数
	 */	
	public Integer getZjCompleted() {
		return zjCompleted;
	}
	/**
	 * 自建项目数
	 */	
	public void setZjCompleted(Integer zjCompleted) {
		this.zjCompleted = zjCompleted;
	}
	
	/**
	 * 外部投资项目数
	 */	
	public Integer getWbCompleted() {
		return wbCompleted;
	}
	/**
	 * 外部投资项目数
	 */	
	public void setWbCompleted(Integer wbCompleted) {
		this.wbCompleted = wbCompleted;
	}
	
	/**
	 * 未完成数 =目标数-完成项目数
	 */	
	public Integer getNotCompleted() {
		return notCompleted;
	}
	/**
	 * 未完成数 =目标数-完成项目数
	 */	
	public void setNotCompleted(Integer notCompleted) {
		this.notCompleted = notCompleted;
	}
	
	/**
	 * 内部创建与外部投资项目数占比
	 */	
	public Double getWbRate() {
		return wbRate;
	}
	/**
	 * 内部创建与外部投资项目数占比
	 */	
	public void setWbRate(Double wbRate) {
		this.wbRate = wbRate;
	}
	
	/**
	 * 自建项目数比率
	 */	
	public Double getZjRate() {
		return zjRate;
	}
	/**
	 * 自建项目数比率
	 */	
	public void setZjRate(Double zjRate) {
		this.zjRate = zjRate;
	}
	
	/**
	 * 完成率
	 */	
	public Double getRate() {
		return rate;
	}
	/**
	 * 完成率
	 */	
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Integer getProMeetNum() {
		return proMeetNum;
	}
	public void setProMeetNum(Integer proMeetNum) {
		this.proMeetNum = proMeetNum;
	}
	public Integer getVetoMeetProNumber() {
		return vetoMeetProNumber;
	}
	public void setVetoMeetProNumber(Integer vetoMeetProNumber) {
		this.vetoMeetProNumber = vetoMeetProNumber;
	}
	public Integer getWaitMeetProNumber() {
		return waitMeetProNumber;
	}
	public void setWaitMeetProNumber(Integer waitMeetProNumber) {
		this.waitMeetProNumber = waitMeetProNumber;
	}
	public Integer getPassMeetProNumber() {
		return passMeetProNumber;
	}
	public void setPassMeetProNumber(Integer passMeetProNumber) {
		this.passMeetProNumber = passMeetProNumber;
	}
	public String getProgressCode() {
		return progressCode;
	}
	public void setProgressCode(String progressCode) {
		this.progressCode = progressCode;
	}
	public String getProgressName() {
		return progressName;
	}
	public void setProgressName(String progressName) {
		this.progressName = progressName;
	}
	public Long getDayLine() {
		return dayLine;
	}
	public void setDayLine(Long dayLine) {
		this.dayLine = dayLine;
	}
	public Long getScore1() {
		return score1;
	}
	public void setScore1(Long score1) {
		this.score1 = score1;
	}
	public Long getScore2() {
		return score2;
	}
	public void setScore2(Long score2) {
		this.score2 = score2;
	}
	public Long getScore3() {
		return score3;
	}
	public void setScore3(Long score3) {
		this.score3 = score3;
	}
	public Long getSumScore() {
		return sumScore;
	}
	public void setSumScore(Long sumScore) {
		this.sumScore = sumScore;
	}
	public Double getCeoRate() {
		return ceoRate;
	}
	public void setCeoRate(Double ceoRate) {
		this.ceoRate = ceoRate;
	}
	public Double getLxhRate() {
		return lxhRate;
	}
	public void setLxhRate(Double lxhRate) {
		this.lxhRate = lxhRate;
	}
	
	
	
	
	
	
	
	
}

