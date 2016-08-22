package com.galaxyinternet.grant.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Sort.Direction;

import com.galaxyinternet.bo.GrantActualBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.GrantActual;
import com.galaxyinternet.service.GrantActualService;


@Controller
@RequestMapping("/galaxy/grant/actual")
public class GrantActualController extends BaseControllerImpl<GrantActual, GrantActualBo> {
	
	private static final Logger _common_logger_ = LoggerFactory.getLogger(GrantActualController.class);
	
	@Autowired
	private GrantActualService grantActualService;
	
	@Override
	protected BaseService<GrantActual> getBaseService() {
		return this.grantActualService;
	}
	/**
	 * 查看实际拨款列表弹出层
	 */
	@RequestMapping(value = "/toApprActualPage/{partId}", method = RequestMethod.GET)
	public ModelAndView toApprActualPage(@PathVariable("partId") Long partId, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("project/tanchuan/appr_actual");
		mv.addObject("partId", partId);
		return mv;
	}
	/**
	 * 查看实际拨款详细信息弹出层
	 */
	@RequestMapping(value = "/lookActual/{actualId}", method = RequestMethod.GET)
	public ModelAndView lookActual(@PathVariable("actualId") Long actualId, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("project/tanchuan/appr_actual_look");
		Map<String, Object> actualInfo = grantActualService.lookActualDetail(actualId);
		mv.addObject("actualInfo", actualInfo);
		return mv;
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
	
	
	/**
	 * 实际拨款记录列表查询
	 */
	@ResponseBody
	@RequestMapping(value = "/searchActualList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<GrantActual> searchActualList(@RequestBody GrantActual actual,
			HttpServletRequest request) {
		ResponseData<GrantActual> responseBody = new ResponseData<GrantActual>();
		if(actual.getPartGrantId() == null){
			responseBody.setResult(new Result(Status.ERROR,"error" , "必要的参数丢失!"));
			return responseBody;
		}
		try {
			Page<GrantActual> actualPage = grantActualService.queryPageList(actual,
					new PageRequest(actual.getPageNum(), 
							actual.getPageSize(), 
							Direction.fromString(actual.getDirection()), 
							actual.getProperty()));
			responseBody.setPageList(actualPage);
		} catch (Exception e) {
			_common_logger_.error("查询实际拨款列表失败！查询条件：" + actual, e);
		}
		return responseBody;
	}
	
}
