package com.galaxyinternet.model;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.galaxyinternet.framework.core.model.PagableEntity;
import com.galaxyinternet.framework.core.utils.DateUtil;

public class GrantTotal extends PagableEntity {

	private static final long serialVersionUID = 1L;
	
	private Long projectId;
	private String grantName;
	private Double grantMoney;
	private Long createUid;
	private String createUname;
	private boolean is_edit;
	private String updatedUname;
	
	private String investors;//投资方
	private Double finalShareRatio;//实际股权占比
	private Double serviceCharge; //实际服务费占比
	private Double finalValuations; //实际项目估值
	private String finalValuationsStr;
	private Double finalContribution; //	计划总注资金额：基本信息—实际投资中的投资金额
	private String finalContributionStr;
	
	/**扩展字段**/
	private String projectCompany;//目标公司名称
    
	
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getGrantName() {
		return grantName;
	}
	public void setGrantName(String grantName) {
		this.grantName = grantName;
	}
	public Double getGrantMoney() {
		return grantMoney;
	}
	public void setGrantMoney(Double grantMoney) {
		this.grantMoney = grantMoney;
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
		this.createUname = createUname;
	}
	
	public String getUpdatedUname()
	{
		return updatedUname;
	}
	public void setUpdatedUname(String updatedUname)
	{
		this.updatedUname = updatedUname;
	}


	//----bo----
	private List<GrantPart> partList;
	private String formatCreatedTime;
	private String formatUpdatedTime;
	private Double searchPartMoney;
	public List<GrantPart> getPartList() {
		return partList;
	}
	public void setPartList(List<GrantPart> partList) {
		this.partList = partList;
	}
	public String getFormatCreatedTime() {
		if(createdTime != null){
			return DateUtil.longToString(createdTime);
		}
		return formatCreatedTime;
	}
	public String getFormatUpdatedTime() {
		if(updatedTime != null){
			return DateUtil.longToString(updatedTime);
		}
		return formatUpdatedTime;
	}
	public Double getSearchPartMoney() {
		return searchPartMoney;
	}
	public void setSearchPartMoney(Double searchPartMoney) {
		this.searchPartMoney = searchPartMoney;
	}
	
	
	public boolean getIs_edit() {
		return is_edit;
	}
	public void setIs_edit(boolean is_edit) {
		this.is_edit = is_edit;
	}
	public void setFormatCreatedTime(String formatCreatedTime) {
		this.formatCreatedTime = formatCreatedTime;
	}
	public void setFormatUpdatedTime(String formatUpdatedTime) {
		this.formatUpdatedTime = formatUpdatedTime;
	}
	
	
	
	public String getInvestors() {
		return investors;
	}
	public void setInvestors(String investors) {
		this.investors = investors;
	}
	public Double getFinalShareRatio() {
		return finalShareRatio;
	}
	public void setFinalShareRatio(Double finalShareRatio) {
		this.finalShareRatio = finalShareRatio;
	}
	public Double getServiceCharge() {
		return serviceCharge;
	}
	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	public Double getFinalValuations() {
		return finalValuations;
	}
	public void setFinalValuations(Double finalValuations) {
		this.finalValuations = finalValuations;
	}
	public String getProjectCompany() {
		return projectCompany;
	}
	public void setProjectCompany(String projectCompany) {
		this.projectCompany = projectCompany;
	}
	public Double getFinalContribution() {
		return finalContribution;
	}
	public void setFinalContribution(Double finalContribution) {
		this.finalContribution = finalContribution;
	}
	public String getFinalValuationsStr() {
		return finalValuationsStr;
	}
	public void setFinalValuationsStr(String finalValuationsStr) {
		this.finalValuationsStr = finalValuationsStr;
	}
	public String getFinalContributionStr() {
		return finalContributionStr;
	}
	public void setFinalContributionStr(String finalContributionStr) {
		this.finalContributionStr = finalContributionStr;
	}
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
	
	
}
