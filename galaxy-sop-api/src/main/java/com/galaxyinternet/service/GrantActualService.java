package com.galaxyinternet.service;

import java.util.List;
import java.util.Map;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.GrantActual;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopDownLoad;

public interface GrantActualService extends BaseService<GrantActual>{
	
	/**
	 * 查看实际注资信息
	 */
	Map<String, Object> lookActualDetail(Long actualId);
	
	
	/**
	 * 计算实际注资金额的总和
	 */
	double calculateBelongToActualMoney(Long partId);


	void insertGrantActual(GrantActual grantActual, Project project);


	void upateGrantActual(GrantActual grantActual, Project project);


	void deleteGrantActual(Long grantActualId);
	
	List<GrantActual> selectSumActualByPid(Long projctId);


	GrantActual selectGrantActual(Long ActualId);


	List<SopDownLoad> queryActualDownFiles(Long id);
}
