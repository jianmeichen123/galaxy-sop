package com.galaxyinternet.export_schedule.model;


import com.galaxyinternet.framework.core.model.PagableEntity;

public class BaiFanTj extends PagableEntity{
	
	private static final long serialVersionUID = 1L;


	private Long uid;
	private Long did;
	
	private String name;
	private String uname;
	private String dname;
	
	private Integer usum;
	private Integer dsum;
	
	private Integer completeSum;
	private Integer allSum;
	
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getDid() {
		return did;
	}
	public void setDid(Long did) {
		this.did = did;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public Integer getUsum() {
		return usum;
	}
	public void setUsum(Integer usum) {
		this.usum = usum;
	}
	public Integer getDsum() {
		return dsum;
	}
	public void setDsum(Integer dsum) {
		this.dsum = dsum;
	}
	public Integer getCompleteSum() {
		return completeSum;
	}
	public void setCompleteSum(Integer completeSum) {
		this.completeSum = completeSum;
	}
	public Integer getAllSum() {
		return allSum;
	}
	public void setAllSum(Integer allSum) {
		this.allSum = allSum;
	}
	
	
    
}