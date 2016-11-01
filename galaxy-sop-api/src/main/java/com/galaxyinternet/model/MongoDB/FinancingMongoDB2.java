package com.galaxyinternet.model.MongoDB;


import com.galaxyinternet.framework.core.model.PagableEntity;

public class FinancingMongoDB2 extends PagableEntity {
	private static final long serialVersionUID = 1L;
	 private String fdate;//融资时间
	 private String ffrom;//投资方
     private Double fContribution;//融资金额；
     private String fcurrencyUnit;//比重；
     private String fstatus;
	public String getFdate() {
		return fdate;
	}
	public void setFdate(String fdate) {
		this.fdate = fdate;
	}
	public String getFfrom() {
		return ffrom;
	}
	public void setFfrom(String ffrom) {
		this.ffrom = ffrom;
	}
	public Double getfContribution() {
		return fContribution;
	}
	public void setfContribution(Double fContribution) {
		this.fContribution = fContribution;
	}
	public String getFcurrencyUnit() {
		return fcurrencyUnit;
	}
	public void setFcurrencyUnit(String fcurrencyUnit) {
		this.fcurrencyUnit = fcurrencyUnit;
	}
	public String getFstatus() {
		return fstatus;
	}
	public void setFstatus(String fstatus) {
		this.fstatus = fstatus;
	}
     


}