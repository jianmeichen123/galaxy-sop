package com.galaxyinternet.model.project;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class FinanceHistory extends BaseEntity {
	private static final long serialVersionUID = 1L;

    private Long projectId;//项目id
    
    private String financeDate;//融资日期
    
    private String financeFrom;//融资来源
    
    private double financeAmount;//融资金额
    
    private Integer financeUnit;//融资货币
    
    private double financeProportion;//融资占比
    
    private String financeStatus;//融资轮次
    
    private Long createUid;//创建人
    

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

	public String getFinanceDate() {
		return financeDate;
	}

	public void setFinanceDate(String financeDate) {
		this.financeDate = financeDate;
	}

	public String getFinanceFrom() {
		return financeFrom;
	}

	public void setFinanceFrom(String financeFrom) {
		this.financeFrom = financeFrom;
	}

	public double getFinanceAmount() {
		return financeAmount;
	}

	public void setFinanceAmount(double financeAmount) {
		this.financeAmount = financeAmount;
	}
	
	public Integer getFinanceUnit() {
		return financeUnit;
	}

	public void setFinanceUnit(Integer financeUnit) {
		this.financeUnit = financeUnit;
	}

	public double getFinanceProportion() {
		return financeProportion;
	}

	public void setFinanceProportion(double financeProportion) {
		this.financeProportion = financeProportion;
	}

	public String getFinanceStatus() {
		return financeStatus;
	}

	public void setFinanceStatus(String financeStatus) {
		this.financeStatus = financeStatus;
	}

	public Long getCreateUid() {
		return createUid;
	}

	public void setCreateUid(Long createUid) {
		this.createUid = createUid;
	}

}