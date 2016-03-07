package com.galaxyinternet.model.project;


import com.galaxyinternet.framework.core.model.PagableEntity;
import com.galaxyinternet.framework.core.utils.DateUtil;

public class Project extends PagableEntity {
	private static final long serialVersionUID = 1L;

	 private String projectName;

    private String projectCode;

    private String projectType;

    private String projectCareerline;

    private Long projectDepartid;

    private Double projectValuations;

    private Double projectContribution;

    private Integer currencyUnit;

    private Double projectShareRatio;
    
    private String projectCompany;
    
    private String projectCompanyCode;

    private Long createUid;

    private String createUname;

    private String createUposition;

    private String projectProgress;

    private String projectStatus;
    
    private String nameCodeLike;
    
    //详情数据转换
  	private String createDate;
  	private String type;


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
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode == null ? null : projectCode.trim();
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType == null ? null : projectType.trim();
        if(this.projectType != null && "projectType:2".equals(this.projectType)){
			this.type = "内部创建";
		}else{
			this.type = "外部投资";
		}
    }

    public String getProjectCareerline() {
        return projectCareerline;
    }

    public void setProjectCareerline(String projectCareerline) {
        this.projectCareerline = projectCareerline == null ? null : projectCareerline.trim();
    }

    public Long getProjectDepartid() {
        return projectDepartid;
    }

    public void setProjectDepartid(Long projectDepartid) {
        this.projectDepartid = projectDepartid;
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

    public Integer getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(Integer currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public Double getProjectShareRatio() {
        return projectShareRatio;
    }

    public void setProjectShareRatio(Double projectShareRatio) {
        this.projectShareRatio = projectShareRatio;
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
        this.createUname = createUname == null ? null : createUname.trim();
    }

    public String getCreateUposition() {
        return createUposition;
    }

    public void setCreateUposition(String createUposition) {
        this.createUposition = createUposition == null ? null : createUposition.trim();
    }

    public String getProjectProgress() {
        return projectProgress;
    }

    public void setProjectProgress(String projectProgress) {
        this.projectProgress = projectProgress == null ? null : projectProgress.trim();
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus == null ? null : projectStatus.trim();
    }

    
    
    private String projectDescribe;

    private String projectBusinessModel;

    private String companyLocation;

    private String userPortrait;

    private String prospectAnalysis;

    public String getProjectDescribe() {
        return projectDescribe;
    }

    public void setProjectDescribe(String projectDescribe) {
        this.projectDescribe = projectDescribe == null ? null : projectDescribe.trim();
    }

    public String getProjectBusinessModel() {
        return projectBusinessModel;
    }

    public void setProjectBusinessModel(String projectBusinessModel) {
        this.projectBusinessModel = projectBusinessModel == null ? null : projectBusinessModel.trim();
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation == null ? null : companyLocation.trim();
    }

    public String getUserPortrait() {
        return userPortrait;
    }

    public void setUserPortrait(String userPortrait) {
        this.userPortrait = userPortrait == null ? null : userPortrait.trim();
    }

    public String getProspectAnalysis() {
        return prospectAnalysis;
    }

    public void setProspectAnalysis(String prospectAnalysis) {
        this.prospectAnalysis = prospectAnalysis == null ? null : prospectAnalysis.trim();
    }
    
    public String getCreateDate() {
		this.createDate = DateUtil.longToString(this.createdTime);
		return createDate;
	}

	public String getType() {
		return type;
	}

	public String getNameCodeLike() {
		return nameCodeLike;
	}

	public void setNameCodeLike(String nameCodeLike) {
		this.nameCodeLike = nameCodeLike;
	}

}