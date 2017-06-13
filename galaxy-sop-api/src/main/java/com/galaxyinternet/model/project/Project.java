package com.galaxyinternet.model.project;


import java.util.List;

import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.framework.core.model.PagableEntity;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.framework.core.utils.NumberUtils;

public class Project extends PagableEntity {
	private static final long serialVersionUID = 1L;

	private int completed; 
	private int type_completed; 
	private int depNumOrder;  //部门排名
	private Long durationDay;   //历时
	private String departmentName;
	

	private String projectName;
    private String projectCode;
    private Long ideaId;
    private String projectType;
    private Integer stockTransfer;
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
    private String projectDescribe;
    private String projectDescribeFinancing;
    private String projectBusinessModel;
    private String companyLocation;
    private String userPortrait;
    private String prospectAnalysis;
    private String nextFinancingSource;
    private String industryAnalysis;
    private String operationalData;
    //06-21版本更新字段
  	private Long industryOwn;//行业归属
  	private String financeStatus;//融资状态
  	private Double finalValuations;//实际估值
  	private Double finalContribution;//实际投资
  	private Double finalShareRatio;//实际所占股份百分比
	private Double serviceCharge;
  	private String companyLegal;//公司法人
  	private Long formationDate;//公司成立时间
  	//数据转换
    private String formatContribution;
    private String formatValuations;
    private String formatUnit;
    private String formatShareRatio;
    private String nameCodeLike;
    //详情数据转换
  	private String createDate;
  	private String updateDate;
  	private String type;
  	private String progress;
    private  String  hhrName;
    //行业归属数据转换
    private String industryOwnDs;
    //项目进度状态数据转换
    private String projectStatusDs;
    //融资状态的数据转换
    private String financeStatusDs;

    //in查询
    private List<Long> deptIdList;
	private Long startTime; 
	private Long endTime;
	
	private Integer faFlag;//项目是否来自中介
	
	private String faName;//中介名称
	
	/**
	 * 项目成员
	 */
	private String projectPerson;

	/**
	 * 项目绿色通道标识
	 */
	private String greanChannel;
	
	/**
	 *项目备注
	 */
    private String remark;
    
     
    private List<FinanceHistory> financeList;
    
    /**
     * 投后运营新增字段
     */
    
   private String isNullTime;
   
   
   private String healthState;
   
   
   private String ctime;
   
   private Double grantMoney;
    
   private String deptId;
   
   private String sDate;
   
   private String eDate;
   
   private Long projectTime;
   
   private String financeMode;//投资形式
   
   private String fModeRemark;//投资形式备注
   
   private List<JointDelivery> jointDeliveryList;
   
   private Long updateUid;
	
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
        if(projectType != null){
			this.type = DictEnum.projectType.getNameByCode(projectType);
		}
    }

    public Integer getStockTransfer() {
		return stockTransfer;
	}

	public void setStockTransfer(Integer stockTransfer) {
		this.stockTransfer = stockTransfer;
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
    	if(this.formatValuations != null && !"".equals(this.formatValuations.trim())){
			this.projectValuations = NumberUtils.toFormatNoSplitFour(this.formatValuations.trim());
		}
        return projectValuations;
    }

    public void setProjectValuations(Double projectValuations) {
        this.projectValuations = projectValuations;
    }

    public Double getProjectContribution() {
    	if(this.formatContribution != null && !"".equals(this.formatContribution.trim())){
			this.projectContribution = NumberUtils.toFormatNoSplitFour(this.formatContribution.trim());
		}
        return projectContribution;
    }

    public void setProjectContribution(Double projectContribution) {
        this.projectContribution = projectContribution;
    }

    public Integer getCurrencyUnit() {
    	if(this.formatUnit != null && !"".equals(this.formatUnit.trim())){
			this.currencyUnit = Integer.parseInt(this.formatUnit.trim());
		}
        return currencyUnit;
    }

    public void setCurrencyUnit(Integer currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public Double getProjectShareRatio() {
    	if(this.formatShareRatio != null && !"".equals(this.formatShareRatio.trim())){
			this.projectShareRatio = NumberUtils.toFormatNoSplitFour(this.formatShareRatio.trim());
		}
        return projectShareRatio;
    }

    public void setProjectShareRatio(Double projectShareRatio) {
        this.projectShareRatio = projectShareRatio;
    }
    
    public String getFormatContribution() {
		return formatContribution;
	}

	public void setFormatContribution(String formatContribution) {
		this.formatContribution = formatContribution;
	}

	public String getFormatValuations() {
		return formatValuations;
	}

	public void setFormatValuations(String formatValuations) {
		this.formatValuations = formatValuations;
	}

	public String getFormatUnit() {
		return formatUnit;
	}

	public void setFormatUnit(String formatUnit) {
		this.formatUnit = formatUnit;
	}

	public String getFormatShareRatio() {
		return formatShareRatio;
	}

	public void setFormatShareRatio(String formatShareRatio) {
		this.formatShareRatio = formatShareRatio;
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
        if(projectProgress != null){
			this.progress = DictEnum.projectProgress.getNameByCode(projectProgress);
		}
    }

    public String getProjectStatus() {
    	
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {

        this.projectStatus = projectStatus == null ? null : projectStatus.trim();
        if(projectStatus != null){
			this.projectStatusDs = DictEnum.projectStatus.getNameByCode(projectStatus);
		}

    }

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
		return createDate;
	}
    
    @Override
    public void setCreatedTime(Long createdTime) {
    	this.createdTime = createdTime;
    	if(createdTime != null){
    		this.createDate = DateUtil.longToString(createdTime);
    	}
    }
    
    public String getUpdateDate() {
		return updateDate;
	}
    public void setUpdatedTime(Long updatedTime) {
    	this.updatedTime = updatedTime;
    	if(updatedTime != null){
    		this.updateDate = DateUtil.longToString(updatedTime);
    	}
    }
	public String getProgress() {
		return progress;
	}

	public String getType() {
		return type;
	}

	public String getNameCodeLike() {
		return  nameCodeLike == null ? null : nameCodeLike.trim();
	}

	public void setNameCodeLike(String nameCodeLike) {
		this.nameCodeLike = nameCodeLike == null ? null : nameCodeLike.trim();
	}
	
	private Long count;


	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getHhrName() {
		return hhrName;
	}

	public void setHhrName(String hhrName) {
		this.hhrName = hhrName;
	}

	public List<Long> getDeptIdList() {
		return deptIdList;
	}

	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}

	public Long getIdeaId() {
		return ideaId;
	}

	public void setIdeaId(Long ideaId) {
		this.ideaId = ideaId;
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
		this.financeStatus = financeStatus == null ? null: financeStatus.trim();
        if(financeStatus != null){
			this.financeStatusDs = DictEnum.financeStatus.getNameByCode(financeStatus);
		}else{
			this.financeStatusDs ="不明确";
		}
	}

	public Double getFinalValuations() {
		return finalValuations;
	}

	public void setFinalValuations(Double finalValuations) {
		this.finalValuations = finalValuations;
	}

	public Double getFinalContribution() {
		return finalContribution;
	}

	public void setFinalContribution(Double finalContribution) {
		this.finalContribution = finalContribution;
	}

	public Double getFinalShareRatio() {
		return finalShareRatio;
	}

	public void setFinalShareRatio(Double finalShareRatio) {
		this.finalShareRatio = finalShareRatio;
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

	public String getIndustryOwnDs() {
		return industryOwnDs;
	}

	public void setIndustryOwnDs(String industryOwnDs) {
		this.industryOwnDs = industryOwnDs;
	}

	public String getProjectStatusDs() {
		return projectStatusDs;
	}
	public String getFinanceStatusDs() {
		return financeStatusDs;
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

	public Double getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	
	public int getCompleted() {
		return completed;
	}

	public void setCompleted(int completed) {
		this.completed = completed;
	}

	public int getDepNumOrder() {
		return depNumOrder;
	}

	public void setDepNumOrder(int depNumOrder) {
		this.depNumOrder = depNumOrder;
	}

	public int getType_completed() {
		return type_completed;
	}

	public void setType_completed(int type_completed) {
		this.type_completed = type_completed;
	}


	public Long getDurationDay() {
		return durationDay;
	}

	public void setDurationDay(Long durationDay) {
		this.durationDay = durationDay;
	}


	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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

	public String getProjectPerson() {
		return projectPerson;
	}

	public void setProjectPerson(String projectPerson) {
		this.projectPerson = projectPerson;
	}

	public String getGreanChannel() {
		return greanChannel;
	}

	public void setGreanChannel(String greanChannel) {
		this.greanChannel = greanChannel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProjectDescribeFinancing() {
		return projectDescribeFinancing;
	}

	public void setProjectDescribeFinancing(String projectDescribeFinancing) {
		this.projectDescribeFinancing = projectDescribeFinancing;
	}

	public List<FinanceHistory> getFinanceList() {
		return financeList;
	}

	public void setFinanceList(List<FinanceHistory> financeList) {
		this.financeList = financeList;
	}

	public String getIsNullTime() {
		return isNullTime;
	}

	public void setIsNullTime(String isNullTime) {
		this.isNullTime = isNullTime;
	}

	public String getHealthState() {
		return healthState;
	}

	public void setHealthState(String healthState) {
		this.healthState = healthState;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public Double getGrantMoney() {
		return grantMoney;
	}

	public void setGrantMoney(Double grantMoney) {
		this.grantMoney = grantMoney;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getsDate() {
		return sDate;
	}

	public void setsDate(String sDate) {
		this.sDate = sDate;
	}

	public String geteDate() {
		return eDate;
	}

	public void seteDate(String eDate) {
		this.eDate = eDate;
	}

	public Long getProjectTime() {
		return projectTime;
	}

	public void setProjectTime(Long projectTime) {
		this.projectTime = projectTime;
	}

	public String getFinanceMode() {
		return financeMode;
	}

	public void setFinanceMode(String financeMode) {
		this.financeMode = financeMode;
	}

	public String getfModeRemark() {
		return fModeRemark;
	}

	public void setfModeRemark(String fModeRemark) {
		this.fModeRemark = fModeRemark;
	}

	public List<JointDelivery> getJointDeliveryList() {
		return jointDeliveryList;
	}

	public void setJointDeliveryList(List<JointDelivery> jointDeliveryList) {
		this.jointDeliveryList = jointDeliveryList;
	}

	public Long getUpdateUid() {
		return updateUid;
	}

	public void setUpdateUid(Long updateUid) {
		this.updateUid = updateUid;
	}
   
    



}