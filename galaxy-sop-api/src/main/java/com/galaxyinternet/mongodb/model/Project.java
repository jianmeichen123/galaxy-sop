package com.galaxyinternet.mongodb.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.galaxyinternet.framework.core.dao.utils.QueryField;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.model.project.FinanceHistory;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.PersonPool;
import com.galaxyinternet.model.project.ProjectShares;
import com.galaxyinternet.model.sopfile.SopFile;

@Document(collection="galaxy.sop.project")
public class Project {
	
	@Id
	private String id;
	/*唯一编码*/
	@QueryField(attribute="uuid")
	private String uuid;
	/*项目的创建人ID*/
	private Long uid;
	/*项目的状态*/
	private int status;
	/*项目名称*/
	private String pn;
	/*团队成员列表*/
	private List<PersonPool> pc;
	/*项目关联的股权结构*/
	private List<ProjectShares> psc;
	/*项目融资历史*/
	private List<FinanceHistory> fh=new ArrayList<FinanceHistory>();
	/*判断是修改或者新增的标示*/
	private String flagId;
	/*项目类型*/
	private String projectType;
	/*项目名称*/
	private String projectName;
	/*项目创建时间*/
	private String createDate;
	/*项目更新时间*/
	private String updateDate;
	/*行业归属*/
	private Long industryOwn;
	/*融资状态（融资轮次）*/
	private String financeStatus;
	/*是否来自中介*/
	private Integer faFlag;
	/*中介名称*/
	private String faName;
	/*备注*/
	private String remark;
	/*计划投资额*/
	private String formatContribution;
	/*估值*/
	private String formatValuations;
	/*股权占比*/
	private String formatShareRatio;
	
	/**step2**/
	private String projectDescribe;//项目描述
	private String projectDescribeFinancing ;//项目描述要点
    private String projectBusinessModel;//商业模式
    private String companyLocation;//公司定位
    private String userPortrait;//用户画像
    private String prospectAnalysis;//尽情分析
    private String nextFinancingSource;//下一轮融资计划
    private String industryAnalysis;//行业分析
    private String operationalData;//运营数据
    private SopFile sopFile;
    private String sopfiletype; //上传文件标识
    
    /**step3**/
	private String projectCompany;//公司名称
	private String projectCompanyCode ;//组织代码
    private String companyLegal;//法人
    private String formationDate;//成立日期
    
    
    /**step4  访谈记录 **/
    private List<InterviewRecord> view;
    
	/**
	 * 创建时间
	 */
	protected Long createdTime;
	/**
	 * 更新时间
	 */
	protected Long updatedTime;
	
	
	public String getId() {
		return id;
	}
	public String getUuid() {
		return uuid;
	}
	public Long getUid() {
		return uid;
	}
	public int getStatus() {
		return status;
	}
	public String getPn() {
		return pn;
	}
	public List<ProjectShares> getPsc() {
		return psc;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setPn(String pn) {
		this.pn = pn;
	}
	public List<PersonPool> getPc() {
		return pc;
	}
	public void setPc(List<PersonPool> pc) {
		this.pc = pc;
	}
	public void setPsc(List<ProjectShares> psc) {
		this.psc = psc;
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
	public String getFormatShareRatio() {
		return formatShareRatio;
	}
	public void setFormatShareRatio(String formatShareRatio) {
		this.formatShareRatio = formatShareRatio;
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
	public String getFlagId() {
		return flagId;
	}
	public void setFlagId(String flagId) {
		this.flagId = flagId;
	}
	public String getCreateDate() {
		return createDate;
	}
    
	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
		if (createdTime != null) {
			this.createDate = DateUtil.longToString(createdTime);
		}
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdatedTime(Long updatedTime) {
		this.updatedTime = updatedTime;
		if (updatedTime != null) {
			this.updateDate = DateUtil.longToString(updatedTime);
		}
	}
	public List<FinanceHistory> getFh() {
		return fh;
	}
	public void setFh(List<FinanceHistory> fh) {
		this.fh = fh;
	}
	public String getProjectDescribeFinancing() {
		return projectDescribeFinancing;
	}
	public void setProjectDescribeFinancing(String projectDescribeFinancing) {
		this.projectDescribeFinancing = projectDescribeFinancing;
	}
	public SopFile getSopFile() {
		return sopFile;
	}
	public void setSopFile(SopFile sopFile) {
		this.sopFile = sopFile;
	}
	public String getSopfiletype() {
		return sopfiletype;
	}
	public void setSopfiletype(String sopfiletype) {
		this.sopfiletype = sopfiletype;
	}
	public List<InterviewRecord> getView() {
		return view;
	}
	public void setView(List<InterviewRecord> view) {
		this.view = view;
	}
	public String getProjectCompany() {
		return projectCompany;
	}
	public String getProjectCompanyCode() {
		return projectCompanyCode;
	}
	public String getCompanyLegal() {
		return companyLegal;
	}
	public String getFormationDate() {
		return formationDate;
	}
	public void setProjectCompany(String projectCompany) {
		this.projectCompany = projectCompany;
	}
	public void setProjectCompanyCode(String projectCompanyCode) {
		this.projectCompanyCode = projectCompanyCode;
	}
	public void setCompanyLegal(String companyLegal) {
		this.companyLegal = companyLegal;
	}
	public void setFormationDate(String formationDate) {
		this.formationDate = formationDate;
	}
	
	
}
