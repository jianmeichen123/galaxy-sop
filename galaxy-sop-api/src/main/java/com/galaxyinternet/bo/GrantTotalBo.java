package com.galaxyinternet.bo;

import java.util.List;

import com.galaxyinternet.model.GrantPart;
import com.galaxyinternet.model.GrantTotal;

public class GrantTotalBo extends GrantTotal{

	private static final long serialVersionUID = 1L;
	
	private List<GrantPart> partList;

	public List<GrantPart> getPartList() {
		return partList;
	}
	public void setPartList(List<GrantPart> partList) {
		this.partList = partList;
	}
}
