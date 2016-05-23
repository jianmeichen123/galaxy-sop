package com.galaxyinternet.model.idea;

import java.util.Date;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class Abandoned extends PagableEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 /**
	  *  主键id
	  */
	private Long id;
	 /**
	  *  放弃人姓名
	  */
	private String abUsername;
	 /**
	  *  放弃人id
	  */
	private Long abUserid;
	 /**
	  *  创意id
	  */
	private Long ideaId;
	 /**
	  *  放弃时间
	  */
	private Date abDatetime;
	
	private String abReason;
	
	private String abDatetimeToString;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAbUsername() {
		return abUsername;
	}
	public void setAbUsername(String abUsername) {
		this.abUsername = abUsername;
	}
	public Long getIdeaId() {
		return ideaId;
	}
	public void setIdeaId(Long ideaId) {
		this.ideaId = ideaId;
	}
	
	public Long getAbUserid() {
		return abUserid;
	}
	public void setAbUserid(Long abUserid) {
		this.abUserid = abUserid;
	}
	
	public Date getAbDatetime() {
		return abDatetime;
	}
	public void setAbDatetime(Date abDatetime) {
		this.abDatetime = abDatetime;
	}
	public String getAbReason() {
		return abReason;
	}
	public void setAbReason(String abReason) {
		this.abReason = abReason;
	}
	public String getAbDatetimeToString() {
		return abDatetimeToString;
	}
	public void setAbDatetimeToString(String abDatetimeToString) {
		this.abDatetimeToString = abDatetimeToString;
	}
	
	

}
