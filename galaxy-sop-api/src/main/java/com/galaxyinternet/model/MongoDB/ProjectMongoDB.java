package com.galaxyinternet.model.MongoDB;


import java.util.List;

import com.galaxyinternet.framework.core.model.PagableEntity;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.ProjectShares;

public class ProjectMongoDB extends PagableEntity {
	private static final long serialVersionUID = 1L;
    private String projectCode;
    private String projectType;//1，项目类型
    private String projectName;//2,项目名称
    private String createDate;//3,项目创建时间
    private Long industryOwn;//4，行业归属
	private String financeStatus;//5，融资状态
    private Integer faFlag;//6，项目是否来自中介
	private String faName;//7，中介名称
	private  String remark;//8，备注
    private Double projectValuations;//9,初始估值
    private Double projectContribution;//10,初始投资额
    private Double projectShareRatio;//11,初始百分比
    private List<FinancingMongoDB2> Financing;//12，融资历时
    private List<MongoDBSopFile> businessPlan;//13，商业计划书
    private String projectDescribe;//项目描述
    private String projectBusinessModel;//产品服务
    private String companyLocation;//公司定位
    private String userPortrait;//用户画像
    private String prospectAnalysis;//竞争分析
    private String nextFinancingSource;//下一轮融资路径
    private String industryAnalysis;//行业分析
    private String operationalData;//运营数据
    private List<ProjectPersonMongoDB> projectTeam;
	private String companyLegal;//公司法人
  	private Long formationDate;//公司成立时间
    private String projectCompany;
    private String projectCompanyCode;
    private List<ProjectShares> projectshare;
    private List<InterviewRecord> interview;
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Long getIndustryOwn() {
		return industryOwn;
	}

	public void setIndustryOwn(Long industryOwn) {
		this.industryOwn = industryOwn;
	}

	public String getFinanceStatus() {
		return financeStatus;
	}

	public void setFinanceStatus(String financeStatus) {
		this.financeStatus = financeStatus;
	}

	public Integer getFaFlag() {
		return faFlag;
	}

	public void setFaFlag(Integer faFlag) {
		this.faFlag = faFlag;
	}

	public String getFaName() {
		return faName;
	}

	public void setFaName(String faName) {
		this.faName = faName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getProjectValuations() {
		return projectValuations;
	}

	public void setProjectValuations(Double projectValuations) {
		this.projectValuations = projectValuations;
	}

	public Double getProjectContribution() {
		return projectContribution;
	}

	public void setProjectContribution(Double projectContribution) {
		this.projectContribution = projectContribution;
	}

	public Double getProjectShareRatio() {
		return projectShareRatio;
	}

	public void setProjectShareRatio(Double projectShareRatio) {
		this.projectShareRatio = projectShareRatio;
	}

	public List<FinancingMongoDB2> getFinancing() {
		return Financing;
	}

	public void setFinancing(List<FinancingMongoDB2> financing) {
		Financing = financing;
	}

	public List<MongoDBSopFile> getBusinessPlan() {
		return businessPlan;
	}

	public void setBusinessPlan(List<MongoDBSopFile> businessPlan) {
		this.businessPlan = businessPlan;
	}

	public String getProjectDescribe() {
		return projectDescribe;
	}

	public void setProjectDescribe(String projectDescribe) {
		this.projectDescribe = projectDescribe;
	}

	public String getProjectBusinessModel() {
		return projectBusinessModel;
	}

	public void setProjectBusinessModel(String projectBusinessModel) {
		this.projectBusinessModel = projectBusinessModel;
	}

	public String getCompanyLocation() {
		return companyLocation;
	}

	public void setCompanyLocation(String companyLocation) {
		this.companyLocation = companyLocation;
	}

	public String getUserPortrait() {
		return userPortrait;
	}

	public void setUserPortrait(String userPortrait) {
		this.userPortrait = userPortrait;
	}

	public String getProspectAnalysis() {
		return prospectAnalysis;
	}

	public void setProspectAnalysis(String prospectAnalysis) {
		this.prospectAnalysis = prospectAnalysis;
	}

	public String getNextFinancingSource() {
		return nextFinancingSource;
	}

	public void setNextFinancingSource(String nextFinancingSource) {
		this.nextFinancingSource = nextFinancingSource;
	}

	public String getIndustryAnalysis() {
		return industryAnalysis;
	}

	public void setIndustryAnalysis(String industryAnalysis) {
		this.industryAnalysis = industryAnalysis;
	}

	public String getOperationalData() {
		return operationalData;
	}

	public void setOperationalData(String operationalData) {
		this.operationalData = operationalData;
	}

	public List<ProjectPersonMongoDB> getProjectTeam() {
		return projectTeam;
	}

	public void setProjectTeam(List<ProjectPersonMongoDB> projectTeam) {
		this.projectTeam = projectTeam;
	}

	public String getCompanyLegal() {
		return companyLegal;
	}

	public void setCompanyLegal(String companyLegal) {
		this.companyLegal = companyLegal;
	}

	public Long getFormationDate() {
		return formationDate;
	}

	public void setFormationDate(Long formationDate) {
		this.formationDate = formationDate;
	}

	public String getProjectCompany() {
		return projectCompany;
	}

	public void setProjectCompany(String projectCompany) {
		this.projectCompany = projectCompany;
	}

	public String getProjectCompanyCode() {
		return projectCompanyCode;
	}

	public void setProjectCompanyCode(String projectCompanyCode) {
		this.projectCompanyCode = projectCompanyCode;
	}

	public List<ProjectShares> getProjectshare() {
		return projectshare;
	}

	public void setProjectshare(List<ProjectShares> projectshare) {
		this.projectshare = projectshare;
	}


}