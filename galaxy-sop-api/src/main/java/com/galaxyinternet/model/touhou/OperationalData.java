package com.galaxyinternet.model.touhou;

import java.math.BigDecimal;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class OperationalData extends PagableEntity{

	private static final long serialVersionUID = 1L;
	
	private Long id;

    private Integer employNum;

    private Integer branchNum;

    private BigDecimal productProcess;

    private BigDecimal salary;

    private BigDecimal otherCoat;

    private Integer payType;

    private BigDecimal manageCost;

    private BigDecimal marketCost;

    private BigDecimal operatingCost;

    private BigDecimal productionCost;

    private BigDecimal tradeCost;

    private BigDecimal incomeCost;

    private BigDecimal profitCost;

    private BigDecimal accountBalance;

    private Integer userNum;

    private Integer userNew;

    private Integer userActiveMonth;

    private Integer userActiveDay;

    private Integer userBuy;

    private Integer userOnline;

    private Integer businessNew;

    private Integer businessBuy;

    private Integer tradeOrders;

    private BigDecimal tradeOrderBlance;

    private BigDecimal tradeUserBlance;

    private Integer projectId;

    private String isDelete;

    private Long createUid;

    private Long createTime;

    private Long updatedUid;

    private Long updatedTime;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public BigDecimal getProductProcess() {
		return productProcess;
	}

	public void setProductProcess(BigDecimal productProcess) {
		this.productProcess = productProcess;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public BigDecimal getOtherCoat() {
		return otherCoat;
	}

	public void setOtherCoat(BigDecimal otherCoat) {
		this.otherCoat = otherCoat;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public BigDecimal getManageCost() {
		return manageCost;
	}

	public void setManageCost(BigDecimal manageCost) {
		this.manageCost = manageCost;
	}

	public BigDecimal getMarketCost() {
		return marketCost;
	}

	public void setMarketCost(BigDecimal marketCost) {
		this.marketCost = marketCost;
	}

	public BigDecimal getOperatingCost() {
		return operatingCost;
	}

	public void setOperatingCost(BigDecimal operatingCost) {
		this.operatingCost = operatingCost;
	}

	public BigDecimal getProductionCost() {
		return productionCost;
	}

	public void setProductionCost(BigDecimal productionCost) {
		this.productionCost = productionCost;
	}

	public BigDecimal getTradeCost() {
		return tradeCost;
	}

	public void setTradeCost(BigDecimal tradeCost) {
		this.tradeCost = tradeCost;
	}

	public BigDecimal getIncomeCost() {
		return incomeCost;
	}

	public void setIncomeCost(BigDecimal incomeCost) {
		this.incomeCost = incomeCost;
	}

	public BigDecimal getProfitCost() {
		return profitCost;
	}

	public void setProfitCost(BigDecimal profitCost) {
		this.profitCost = profitCost;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
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

	public BigDecimal getTradeOrderBlance() {
		return tradeOrderBlance;
	}

	public void setTradeOrderBlance(BigDecimal tradeOrderBlance) {
		this.tradeOrderBlance = tradeOrderBlance;
	}

	public BigDecimal getTradeUserBlance() {
		return tradeUserBlance;
	}

	public void setTradeUserBlance(BigDecimal tradeUserBlance) {
		this.tradeUserBlance = tradeUserBlance;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
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
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
}
