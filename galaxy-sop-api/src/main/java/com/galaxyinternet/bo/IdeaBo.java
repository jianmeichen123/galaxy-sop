package com.galaxyinternet.bo;

import com.galaxyinternet.model.idea.Idea;

public class IdeaBo extends Idea {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 
	private String abReason;
	
	private Long giveUpId;

	public String getAbReason() {
		return abReason;
	}

	public void setAbReason(String abReason) {
		this.abReason = abReason;
	}

	public Long getGiveUpId() {
		return giveUpId;
	}

	public void setGiveUpId(Long giveUpId) {
		this.giveUpId = giveUpId;
	}

	
	
	


}
