package com.galaxyinternet.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.galaxyinternet.bo.project.FinanceHistoryBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.FinanceHistory;
import com.galaxyinternet.service.FinanceHistoryService;

@Controller
@RequestMapping("/galaxy/project/financeHistory")
public class FinanceHistoryController extends BaseControllerImpl<FinanceHistory, FinanceHistoryBo> {

	private final static Logger logger = LoggerFactory.getLogger(FinanceHistoryController.class);

	@Autowired
	private FinanceHistoryService financeHistoryService;

	@Override
	protected BaseService<FinanceHistory> getBaseService() {
		
		return this.financeHistoryService;
	}
	
}
