package com.galaxyinternet.service;

import java.util.List;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.GrantPart;

public interface GrantPartService extends BaseService<GrantPart>{
	
	double calculateBelongToPartMoney(Long totalId);
	
	List<GrantPart> selectHasActualMoney(GrantPart part);
	
	public void insertGrantPart(GrantPart grantPart);
	public void upateGrantPart(GrantPart grantPart);
	public void deleteGrantPart(Long grantPartId);
	
	public GrantPart selectGrantPart(Long partId);
	
	 public List<Long> grantPartFileList(Long partId);
	
}
