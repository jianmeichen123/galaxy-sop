package com.galaxyinternet.model.project;

import com.galaxyinternet.framework.core.model.BaseEntity;

public class FinanceHistory extends BaseEntity {
	private static final long serialVersionUID = 1L;

    private Long projectId;//项目id
    
    private long financeDate;//融资日期
    
    private String financeFrom;//融资来源
    
    private double financeAmount;//融资金额
    
    private Integer finance_unit;//融资货币
    
    private double finance_proportion;//融资占比
    
    private Integer finance_round;//融资轮次
    
    private Long createUid;//创建人
    

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

	public long getFinanceDate() {
		return financeDate;
	}

	public void setFinanceDate(long financeDate) {
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

	public Integer getFinance_unit() {
		return finance_unit;
	}

	public void setFinance_unit(Integer finance_unit) {
		this.finance_unit = finance_unit;
	}

	public double getFinance_proportion() {
		return finance_proportion;
	}

	public void setFinance_proportion(double finance_proportion) {
		this.finance_proportion = finance_proportion;
	}

	public Integer getFinance_round() {
		return finance_round;
	}

	public void setFinance_round(Integer finance_round) {
		this.finance_round = finance_round;
	}

	public Long getCreateUid() {
		return createUid;
	}

	public void setCreateUid(Long createUid) {
		this.createUid = createUid;
	}

}