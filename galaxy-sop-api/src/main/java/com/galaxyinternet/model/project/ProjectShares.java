package com.galaxyinternet.model.project;

import com.galaxyinternet.framework.core.model.PagableEntity;
import com.galaxyinternet.model.hr.PersonLearn;

public class ProjectShares extends PagableEntity {
	private static final long serialVersionUID = 1L;
	
	private String uuid;
    private Long projectId;

    private String sharesType;

    private String sharesOwner;

    private Double sharesRatio;

    private String gainMode;

    private String remark;
    
    private Integer financeUnit;
    
    private Double financeAmount;

    public String getSharesType() {
        return sharesType;
    }

    public void setSharesType(String sharesType) {
        this.sharesType = sharesType == null ? null : sharesType.trim();
    }

    public String getSharesOwner() {
        return sharesOwner;
    }

    public void setSharesOwner(String sharesOwner) {
        this.sharesOwner = sharesOwner == null ? null : sharesOwner.trim();
    }

    public Double getSharesRatio() {
		return sharesRatio;
	}

	public void setSharesRatio(Double sharesRatio) {
		this.sharesRatio = sharesRatio;
	}

	public String getGainMode() {
        return gainMode;
    }

    public void setGainMode(String gainMode) {
        this.gainMode = gainMode == null ? null : gainMode.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Integer getFinanceUnit() {
		return financeUnit;
	}

	public void setFinanceUnit(Integer financeUnit) {
		this.financeUnit = financeUnit;
	}

	public Double getFinanceAmount() {
		return financeAmount;
	}

	public void setFinanceAmount(Double financeAmount) {
		this.financeAmount = financeAmount;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(uuid != null && obj != null && obj instanceof ProjectShares && ((ProjectShares) obj).getUuid() != null){
			return this.getUuid().equals(((ProjectShares) obj).getUuid());
		}
		return super.equals(obj);
	}
}