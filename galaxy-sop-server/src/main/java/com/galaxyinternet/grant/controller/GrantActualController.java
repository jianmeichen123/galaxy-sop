package com.galaxyinternet.grant.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.galaxyinternet.bo.GrantActualBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.GrantActual;
import com.galaxyinternet.service.GrantActualService;


@Controller
@RequestMapping("/galaxy/grant/actual")
public class GrantActualController extends BaseControllerImpl<GrantActual, GrantActualBo> {
	
	private GrantActualService grantActualService;
	
	@Override
	protected BaseService<GrantActual> getBaseService() {
		return this.grantActualService;
	}
	/**
	 * sop tab页面  日志 详情    /galaxy/project/proview/
	 */
	@RequestMapping(value = "/toApprActualPage", method = RequestMethod.GET)
	public String toApprActualPage(HttpServletRequest request) {
				return "project/tanchuan/appr_actual";
	}
	/**
	 * sop tab页面  日志 详情    /galaxy/project/proview/
	 */
	@RequestMapping(value = "/toApprActualLook", method = RequestMethod.GET)
	public String toApprActualLock(HttpServletRequest request) {
				return "project/tanchuan/appr_actual_look";
	}
	/**
	 * sop tab页面  日志 详情    /galaxy/project/proview/
	 */
	@RequestMapping(value = "/toApprActualAdd", method = RequestMethod.GET)
	public String toApprActualAdd(HttpServletRequest request) {
				return "project/tanchuan/appr_edit_actual";
	}
	
}
