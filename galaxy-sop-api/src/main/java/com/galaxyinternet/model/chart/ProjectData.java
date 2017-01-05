package com.galaxyinternet.model.chart;


import com.galaxyinternet.model.report.DataReport;

public class ProjectData extends DataReport {
	private static final long serialVersionUID = 1L;
	private String projectName;
	private String projectCompany;
  	private String type;
  	private String departmentName;
 	private String financeStatus;//融资状态
    private String ctime;
	private Double finalContribution;//实际投资
	private String radioStr;
	private String financeHistory;
	private String healthState;
	private String projectDescribe;
	private String projectDescribeFinancing;
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


	public String getProjectCompany() {
		return projectCompany;
	}

	public void setProjectCompany(String projectCompany) {
		this.projectCompany = projectCompany;
	}
    public String getCreateUname() {
        return createUname;
    }

    public void setCreateUname(String createUname) {
        this.createUname = createUname == null ? null : createUname.trim();
    }
    public String getProjectDescribe() {
        return projectDescribe;
    }

    public void setProjectDescribe(String projectDescribe) {
        this.projectDescribe = projectDescribe == null ? null : projectDescribe.trim();
    }

	public Double getFinalContribution() {
		return finalContribution;
	}
	public void setFinalContribution(Double finalContribution) {
		this.finalContribution = finalContribution;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}


	public String getProjectDescribeFinancing() {
		return projectDescribeFinancing;
	}

	public void setProjectDescribeFinancing(String projectDescribeFinancing) {
		this.projectDescribeFinancing = projectDescribeFinancing;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRadioStr() {
		return radioStr;
	}

	public void setRadioStr(String radioStr) {
		this.radioStr = radioStr;
	}

	public String getFinanceHistory() {
		return financeHistory;
	}

	public void setFinanceHistory(String financeHistory) {
		this.financeHistory = financeHistory;
	}

	public String getFinanceStatus() {
		return financeStatus;
	}

	public void setFinanceStatus(String financeStatus) {
		this.financeStatus = financeStatus;
	}
	
	
}