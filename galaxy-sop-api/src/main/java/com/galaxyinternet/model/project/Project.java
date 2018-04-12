package com.galaxyinternet.model.project;


import java.util.List;

import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.query.ProjectQuery;
import com.galaxyinternet.framework.core.model.PagableEntity;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.framework.core.utils.NumberUtils;
import com.galaxyinternet.model.hologram.InformationData;
import com.galaxyinternet.model.hologram.InformationListdata;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.model.sopfile.SopFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(value="项目对象",description="项目对象")
public class Project extends PagableEntity {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="id数组",hidden=true)
	private int completed; 
	@ApiModelProperty(value="id数组",hidden=true)
	private int type_completed; 
	@ApiModelProperty(value="id数组",hidden=true)
	private int depNumOrder;  //部门排名
	@ApiModelProperty(value="id数组",hidden=true)
	private Long durationDay;   //历时
	@ApiModelProperty(value="id数组",hidden=true)
	private String departmentName;
	
	@ApiModelProperty(value="项目名称",
			name="projectName",example="星河互联集团有限公司")
	private String projectName;
    private String projectCode;
	@ApiModelProperty(value="id数组",hidden=true)
    private Long ideaId;
	
	@ApiModelProperty(value="项目类型",
	name="projectType",example="projectType:1")
    private String projectType;
	@ApiModelProperty(value="id数组",hidden=true)
    private Integer stockTransfer;
    private String projectCareerline;
    private Long projectDepartid;
    private Double projectValuations;
    private Double projectContribution;
    private Integer currencyUnit;
    private Double projectShareRatio;
	@ApiModelProperty(value="id数组",hidden=true)
    private String projectCompany;
	@ApiModelProperty(value="id数组",hidden=true)
    private String projectCompanyCode;
	@ApiModelProperty(value="id数组",hidden=true)
    private Long createUid;
	@ApiModelProperty(value="id数组",hidden=true)
    private String createUname;
	@ApiModelProperty(value="id数组",hidden=true)
    private String createUposition;
	@ApiModelProperty(value="id数组",hidden=true)
    private String projectProgress;
	@ApiModelProperty(value="id数组",hidden=true)
    private String projectStatus;
	@ApiModelProperty(value="id数组",hidden=true)
    private String projectDescribe;
	@ApiModelProperty(value="id数组",hidden=true)
    private String projectDescribeFinancing;
	@ApiModelProperty(value="id数组",hidden=true)
    private String projectBusinessModel;
	@ApiModelProperty(value="id数组",hidden=true)
    private String companyLocation;
	@ApiModelProperty(value="id数组",hidden=true)
    private String userPortrait;
	@ApiModelProperty(value="id数组",hidden=true)
    private String prospectAnalysis;
	@ApiModelProperty(value="id数组",hidden=true)
    private String nextFinancingSource;
	@ApiModelProperty(value="id数组",hidden=true)
    private String industryAnalysis;
	@ApiModelProperty(value="id数组",hidden=true)
    private String operationalData;
    //06-21版本更新字段
	
	@ApiModelProperty(value="行业归属",example="5")
  	private Long industryOwn;//行业归属
	@ApiModelProperty(value="id数组",hidden=true)
  	private String financeStatus;//融资状态
	@ApiModelProperty(value="id数组",hidden=true)
  	private Double finalValuations;//实际估值
	@ApiModelProperty(value="id数组",hidden=true)
  	private Double finalContribution;//实际投资
	@ApiModelProperty(value="id数组",hidden=true)
  	private Double finalShareRatio;//实际所占股份百分比
	@ApiModelProperty(value="id数组",hidden=true)
	private Double serviceCharge;
	@ApiModelProperty(value="id数组",hidden=true)
  	private String companyLegal;//公司法人
	@ApiModelProperty(value="id数组",hidden=true)
  	private Long formationDate;//公司成立时间
  	//数据转换
	@ApiModelProperty(value="id数组",hidden=true)
    private String formatContribution;
	@ApiModelProperty(value="id数组",hidden=true)
    private String formatValuations;
	@ApiModelProperty(value="id数组",hidden=true)
    private String formatUnit;
	@ApiModelProperty(value="id数组",hidden=true)
    private String formatShareRatio;
	@ApiModelProperty(value="id数组",hidden=true)
    private String nameCodeLike;
    //详情数据转换
	@ApiModelProperty(value="创建时间",example="2018-04-11")
  	private String createDate;
	@ApiModelProperty(value="id数组",hidden=true)
  	private String updateDate;
	@ApiModelProperty(value="id数组",hidden=true)
  	private String type;
	@ApiModelProperty(value="id数组",hidden=true)
  	private String progress;
	@ApiModelProperty(value="id数组",hidden=true)
    private  String  hhrName;
    //行业归属数据转换
	@ApiModelProperty(value="id数组",hidden=true)
    private String industryOwnDs;
    //项目进度状态数据转换
	@ApiModelProperty(value="id数组",hidden=true)
    private String projectStatusDs;
    //融资状态的数据转换
	@ApiModelProperty(value="id数组",hidden=true)
    private String financeStatusDs;

    //in查询
	@ApiModelProperty(value="id数组",hidden=true)
    private List<Long> deptIdList;
	@ApiModelProperty(value="id数组",hidden=true)
    private List<String> proejctIdList;
	@ApiModelProperty(value="id数组",hidden=true)
	private Long startTime; 
	@ApiModelProperty(value="id数组",hidden=true)
	private Long endTime;
	@ApiModelProperty(value="id数组",hidden=true)
	private String  faFlag;//项目来源
	@ApiModelProperty(value="id数组",hidden=true)
	private String faFlagStr;
	@ApiModelProperty(value="id数组",hidden=true)
	private String faName;//中介名称
	
	/**
	 * 项目成员
	 */
	@ApiModelProperty(value="id数组",hidden=true)
	private String projectPerson;

	/**
	 * 项目绿色通道标识
	 */
	@ApiModelProperty(value="id数组",hidden=true)
	private String greanChannel;
	
	/**
	 *项目备注
	 */
	@ApiModelProperty(value="id数组",hidden=true)
    private String remark;
    
	@ApiModelProperty(value="id数组",hidden=true)
    private List<FinanceHistory> financeList;
	@ApiModelProperty(value="id数组",hidden=true)
    private InformationTitle listInfoTitle;
    
    /**
     * 投后运营新增字段
     */
	@ApiModelProperty(value="id数组",hidden=true)
   private String isNullTime;
   
	@ApiModelProperty(value="id数组",hidden=true)
   private String healthState;
   
	@ApiModelProperty(value="id数组",hidden=true)
   private String ctime;
	@ApiModelProperty(value="id数组",hidden=true)
   private Double grantMoney;
	@ApiModelProperty(value="id数组",hidden=true)
   private String deptId;
	@ApiModelProperty(value="id数组",hidden=true)
   private String sDate;
	@ApiModelProperty(value="id数组",hidden=true)
   private String eDate;
	@ApiModelProperty(value="id数组",hidden=true)
   private Long projectTime;
	@ApiModelProperty(value="id数组",hidden=true)
   private String progressHistory;
	@ApiModelProperty(value="id数组",hidden=true)
   private String businessTypeCode;
	@ApiModelProperty(value="id数组",hidden=true)
   private String financeMode;//投资形式
	@ApiModelProperty(value="id数组",hidden=true)
   private String fModeRemark;//投资形式备注
	@ApiModelProperty(value="id数组",hidden=true)
   private List<JointDelivery> jointDeliveryList;
	@ApiModelProperty(value="id数组",hidden=true)
   private List<Long> isDelete;
	@ApiModelProperty(value="id数组",hidden=true)
   private Long updateUid;
	@ApiModelProperty(value="id数组",hidden=true)
   private String resultId;
   /**
    * 该字段为项目删除标识
    */
	@ApiModelProperty(value="id数组",hidden=true)
   private int isdelete;
	@ApiModelProperty(value="id数组",hidden=true)
   private String deleteReason;


// 大脑数据
	@ApiModelProperty(value="id数组",hidden=true)
	private String danaoProjCode;
   //2018-04-11新增项目重构功能添加辅助属性
	@ApiModelProperty(value="跟报告同步的其他字段")
	private InformationData informationData;//项目信息保存报告的其他字段
	@ApiModelProperty(value="商业计划书",hidden=true)
	private SopFile businessPlanFile;//移动端上传商业计划书对象
	@ApiModelProperty(value="访谈信息",
			name="projectQuery")
	private ProjectQuery projectQuery;


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



	public String getFaFlag() {
		return faFlag;
	}

	public void setFaFlag(String faFlag) {
		this.faFlag = faFlag == null ? null: faFlag.trim();
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


	public String getProgressHistory()
	{
		return progressHistory;
	}

	public void setProgressHistory(String progressHistory)
	{
		this.progressHistory = progressHistory;
	}

	public String getBusinessTypeCode()
	{
		return businessTypeCode;
	}

	public void setBusinessTypeCode(String businessTypeCode)
	{
		this.businessTypeCode = businessTypeCode;
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

	public List<Long> getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(List<Long> isDelete) {
		this.isDelete = isDelete;
	}
	
	public String getFaFlagStr() {
		return faFlagStr;
	}

	public void setFaFlagStr(String faFlagStr)
	{
		this.faFlagStr = faFlagStr;
	}


	public InformationTitle getListInfoTitle() {
		return listInfoTitle;
	}

	public void setListInfoTitle(InformationTitle listInfoTitle) {
		this.listInfoTitle = listInfoTitle;
	}

	public String getResultId() {
		return resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
	}

	public int getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(int isdelete) {
		this.isdelete = isdelete;
	}

	public String getDeleteReason() {
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}


	public String getDanaoProjCode() {
		return danaoProjCode;
	}

	public void setDanaoProjCode(String danaoProjCode) {
		this.danaoProjCode = danaoProjCode;
	}

	public List<String> getProejctIdList() {
		return proejctIdList;
	}

	public void setProejctIdList(List<String> proejctIdList) {
		this.proejctIdList = proejctIdList;
	}


	public InformationData getInformationData() {
		return informationData;
	}

	public void setInformationData(InformationData informationData) {
		this.informationData = informationData;
	}


	public SopFile getBusinessPlanFile() {
		return businessPlanFile;
	}

	public void setBusinessPlanFile(SopFile businessPlanFile) {
		this.businessPlanFile = businessPlanFile;
	}

	public ProjectQuery getProjectQuery() {
		return projectQuery;
	}

	public void setProjectQuery(ProjectQuery projectQuery) {
		this.projectQuery = projectQuery;
	}

	

}