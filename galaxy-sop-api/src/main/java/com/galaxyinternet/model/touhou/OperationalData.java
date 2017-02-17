package com.galaxyinternet.model.touhou;


import java.util.List;

import com.galaxyinternet.framework.core.model.PagableEntity;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.framework.core.utils.NumberUtils;

public class OperationalData extends PagableEntity{

	private static final long serialVersionUID = 1L;
	

    private Integer employNum;

    private Integer branchNum;

    private Double productProcess;
    private String formatProductProcess;

    private Double salary;
    private String formatSalary;

    private Double otherCoat;
    private String formatOtherCoat;

    private String payType;

    private Double manageCost;
    private String formatManageCost;

    private Double marketCost;
    private String formatMarketCost;

    private Double operatingCost;
    private String formatOperatingCost;

    private Double productionCost;
    private String formatProductionCost;

    private Double tradeCost;
    private String formatTradeCost;

    private Double incomeCost;
    private String formatIncomeCost;

    private Double profitCost;
    private String formatProfitCost;

    private Double accountBalance;
    private String formatAccountBalance;

    private Integer userNum;

    private Integer userNew;

    private Integer userActiveMonth;

    private Integer userActiveDay;

    private Integer userBuy;

    private Integer userOnline;

    private Integer businessNew;

    private Integer businessBuy;

    private Integer tradeOrders;

    private Double tradeOrderBlance;
    private String formatTradeOrderBlance;

    private Double tradeUserBlance;
    private String formatTradeUserBlance;

    private Long projectId;

    private String isDelete;

    private Long createUid;

    private Long createTime;

    private Long updatedUid;

    private Long updatedTime;
    
    /**
     * 辅助字段
     */
    private Integer operationIntervalDate;
    private Integer dataType;
    private String dataTypeValue;
    
    private String operateDate;
    private String updateDate;
    private String updateUserName;
    
    private String operateDateStart;
    private String operateDateEnd;
    private String operationalDataId;
    private String dataTypeMonth;
    private String dataTypeQuarter;
    /**辅助in查询**/
    private List<Long> dataTypeList;
    


    public Integer getEmployNum() {
		return employNum;
	}

	public void setEmployNum(Integer employNum) {
		this.employNum = employNum;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public Double getProductProcess() {
		if(this.formatProductProcess != null && !"".equals(this.formatProductProcess.trim())){
			this.productProcess = NumberUtils.toFormatNoSplitFour(this.formatProductProcess.trim());
		}
		return productProcess;
	}

	public void setProductProcess(Double productProcess) {
		this.productProcess = productProcess;
	}

	public Double getSalary() {
		if(this.formatSalary != null && !"".equals(this.formatSalary.trim())){
			this.salary = NumberUtils.toFormatNoSplitFour(this.formatSalary.trim());
		}
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Double getOtherCoat() {
		if(this.formatOtherCoat != null && !"".equals(this.formatOtherCoat.trim())){
			this.otherCoat = NumberUtils.toFormatNoSplitFour(this.formatOtherCoat.trim());
		}
		return otherCoat;
	}

	public void setOtherCoat(Double otherCoat) {
		this.otherCoat = otherCoat;
	}


	public Double getManageCost() {
		if(this.formatManageCost != null && !"".equals(this.formatManageCost.trim())){
			this.manageCost = NumberUtils.toFormatNoSplitFour(this.formatManageCost.trim());
		}
		return manageCost;
	}

	public void setManageCost(Double manageCost) {
		this.manageCost = manageCost;
	}

	public Double getMarketCost() {
		if(this.formatMarketCost != null && !"".equals(this.formatMarketCost.trim())){
			this.marketCost = NumberUtils.toFormatNoSplitFour(this.formatMarketCost.trim());
		}
		return marketCost;
	}

	public void setMarketCost(Double marketCost) {
		this.marketCost = marketCost;
	}

	public Double getOperatingCost() {
		if(this.formatOperatingCost != null && !"".equals(this.formatOperatingCost.trim())){
			this.operatingCost = NumberUtils.toFormatNoSplitFour(this.formatOperatingCost.trim());
		}
		return operatingCost;
	}

	public void setOperatingCost(Double operatingCost) {
		this.operatingCost = operatingCost;
	}

	public Double getProductionCost() {
		if(this.formatProductionCost != null && !"".equals(this.formatProductionCost.trim())){
			this.productionCost = NumberUtils.toFormatNoSplitFour(this.formatProductionCost.trim());
		}
		return productionCost;
	}

	public void setProductionCost(Double productionCost) {
		this.productionCost = productionCost;
	}

	public Double getTradeCost() {
		if(this.formatTradeCost != null && !"".equals(this.formatTradeCost.trim())){
			this.tradeCost = NumberUtils.toFormatNoSplitFour(this.formatTradeCost.trim());
		}
		return tradeCost;
	}

	public void setTradeCost(Double tradeCost) {
		this.tradeCost = tradeCost;
	}

	public Double getIncomeCost() {
		if(this.formatIncomeCost != null && !"".equals(this.formatIncomeCost.trim())){
			this.incomeCost = NumberUtils.toFormatNoSplitFour(this.formatIncomeCost.trim());
		}
		return incomeCost;
	}

	public void setIncomeCost(Double incomeCost) {
		this.incomeCost = incomeCost;
	}

	public Double getProfitCost() {
		if(this.formatProfitCost != null && !"".equals(this.formatProfitCost.trim())){
			this.profitCost = NumberUtils.toFormatNoSplitFour(this.formatProfitCost.trim());
		}
		return profitCost;
	}

	public void setProfitCost(Double profitCost) {
		this.profitCost = profitCost;
	}

	public Double getAccountBalance() {
		if(this.formatAccountBalance != null && !"".equals(this.formatAccountBalance.trim())){
			this.accountBalance = NumberUtils.toFormatNoSplitFour(this.formatAccountBalance.trim());
		}
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public Integer getUserNum() {
		return userNum;
	}

	public void setUserNum(Integer userNum) {
		this.userNum = userNum;
	}

	public Integer getUserNew() {
		return userNew;
	}

	public void setUserNew(Integer userNew) {
		this.userNew = userNew;
	}

	public Integer getUserActiveMonth() {
		return userActiveMonth;
	}

	public void setUserActiveMonth(Integer userActiveMonth) {
		this.userActiveMonth = userActiveMonth;
	}

	public Integer getUserActiveDay() {
		return userActiveDay;
	}

	public void setUserActiveDay(Integer userActiveDay) {
		this.userActiveDay = userActiveDay;
	}

	public Integer getUserBuy() {
		return userBuy;
	}

	public void setUserBuy(Integer userBuy) {
		this.userBuy = userBuy;
	}

	public Integer getUserOnline() {
		return userOnline;
	}

	public void setUserOnline(Integer userOnline) {
		this.userOnline = userOnline;
	}

	public Integer getBusinessNew() {
		return businessNew;
	}

	public void setBusinessNew(Integer businessNew) {
		this.businessNew = businessNew;
	}

	public Integer getBusinessBuy() {
		return businessBuy;
	}

	public void setBusinessBuy(Integer businessBuy) {
		this.businessBuy = businessBuy;
	}

	public Integer getTradeOrders() {
		return tradeOrders;
	}

	public void setTradeOrders(Integer tradeOrders) {
		this.tradeOrders = tradeOrders;
	}

	public Double getTradeOrderBlance() {
		if(this.formatTradeOrderBlance != null && !"".equals(this.formatTradeOrderBlance.trim())){
			this.tradeOrderBlance = NumberUtils.toFormatNoSplitFour(this.formatTradeOrderBlance.trim());
		}
		return tradeOrderBlance;
	}

	public void setTradeOrderBlance(Double tradeOrderBlance) {
		this.tradeOrderBlance = tradeOrderBlance;
	}

	public Double getTradeUserBlance() {
		if(this.formatTradeUserBlance != null && !"".equals(this.formatTradeUserBlance.trim())){
			this.tradeUserBlance = NumberUtils.toFormatNoSplitFour(this.formatTradeUserBlance.trim());
		}
		return tradeUserBlance;
	}

	public void setTradeUserBlance(Double tradeUserBlance) {
		this.tradeUserBlance = tradeUserBlance;
	}

	

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public Long getCreateUid() {
		return createUid;
	}

	public void setCreateUid(Long createUid) {
		this.createUid = createUid;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdatedUid() {
		return updatedUid;
	}

	public void setUpdatedUid(Long updatedUid) {
		this.updatedUid = updatedUid;
	}

	public Long getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Long updatedTime) {
		this.updatedTime = updatedTime;
    	if(updatedTime != null){
    		this.updateDate = DateUtil.longToString(updatedTime);
    	}
	
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFormatProductProcess() {
		return formatProductProcess;
	}

	public void setFormatProductProcess(String formatProductProcess) {
		this.formatProductProcess = formatProductProcess;
	}

	public String getFormatSalary() {
		return formatSalary;
	}

	public void setFormatSalary(String formatSalary) {
		this.formatSalary = formatSalary;
	}

	public String getFormatOtherCoat() {
		return formatOtherCoat;
	}

	public void setFormatOtherCoat(String formatOtherCoat) {
		this.formatOtherCoat = formatOtherCoat;
	}

	public String getFormatManageCost() {
		return formatManageCost;
	}

	public void setFormatManageCost(String formatManageCost) {
		this.formatManageCost = formatManageCost;
	}

	public String getFormatMarketCost() {
		return formatMarketCost;
	}

	public void setFormatMarketCost(String formatMarketCost) {
		this.formatMarketCost = formatMarketCost;
	}

	public String getFormatOperatingCost() {
		return formatOperatingCost;
	}

	public void setFormatOperatingCost(String formatOperatingCost) {
		this.formatOperatingCost = formatOperatingCost;
	}

	public String getFormatProductionCost() {
		return formatProductionCost;
	}

	public void setFormatProductionCost(String formatProductionCost) {
		this.formatProductionCost = formatProductionCost;
	}

	public String getFormatTradeCost() {
		return formatTradeCost;
	}

	public void setFormatTradeCost(String formatTradeCost) {
		this.formatTradeCost = formatTradeCost;
	}

	public String getFormatIncomeCost() {
		return formatIncomeCost;
	}

	public void setFormatIncomeCost(String formatIncomeCost) {
		this.formatIncomeCost = formatIncomeCost;
	}

	public String getFormatProfitCost() {
		return formatProfitCost;
	}

	public void setFormatProfitCost(String formatProfitCost) {
		this.formatProfitCost = formatProfitCost;
	}

	public String getFormatAccountBalance() {
		return formatAccountBalance;
	}

	public void setFormatAccountBalance(String formatAccountBalance) {
		this.formatAccountBalance = formatAccountBalance;
	}

	public String getFormatTradeOrderBlance() {
		return formatTradeOrderBlance;
	}

	public void setFormatTradeOrderBlance(String formatTradeOrderBlance) {
		this.formatTradeOrderBlance = formatTradeOrderBlance;
	}

	public String getFormatTradeUserBlance() {
		return formatTradeUserBlance;
	}

	public void setFormatTradeUserBlance(String formatTradeUserBlance) {
		this.formatTradeUserBlance = formatTradeUserBlance;
	}


	public String getDataTypeValue() {
		return dataTypeValue;
	}

	public void setDataTypeValue(String dataTypeValue) {
		this.dataTypeValue = dataTypeValue;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public String getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getOperateDateStart() {
		return operateDateStart;
	}

	public void setOperateDateStart(String operateDateStart) {
		this.operateDateStart = operateDateStart;
	}

	public String getOperateDateEnd() {
		return operateDateEnd;
	}

	public void setOperateDateEnd(String operateDateEnd) {
		this.operateDateEnd = operateDateEnd;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getOperationalDataId() {
		return operationalDataId;
	}

	public void setOperationalDataId(String operationalDataId) {
		this.operationalDataId = operationalDataId;
	}

	public String getDataTypeMonth() {
		return dataTypeMonth;
	}

	public void setDataTypeMonth(String dataTypeMonth) {
		this.dataTypeMonth = dataTypeMonth;
	}

	public String getDataTypeQuarter() {
		return dataTypeQuarter;
	}

	public void setDataTypeQuarter(String dataTypeQuarter) {
		this.dataTypeQuarter = dataTypeQuarter;
	}

	public List<Long> getDataTypeList() {
		return dataTypeList;
	}

	public void setDataTypeList(List<Long> dataTypeList) {
		this.dataTypeList = dataTypeList;
	}

	public Integer getOperationIntervalDate() {
		return operationIntervalDate;
	}

	public void setOperationIntervalDate(Integer operationIntervalDate) {
		this.operationIntervalDate = operationIntervalDate;
	}

	
	
	
	
	
}
