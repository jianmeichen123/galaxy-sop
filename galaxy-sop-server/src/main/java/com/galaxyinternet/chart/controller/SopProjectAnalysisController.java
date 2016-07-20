package com.galaxyinternet.chart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.chart.SopProjectAnalysis;
import com.galaxyinternet.service.chart.SopProjectAnalysisService;


@Controller
@RequestMapping("/galaxy/charts")
public class SopProjectAnalysisController extends BaseControllerImpl<SopProjectAnalysis, SopProjectAnalysis>{

	@Autowired
	private SopProjectAnalysisService analysisService;
	
	
	@Override
	protected BaseService<SopProjectAnalysis> getBaseService() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	@RequestMapping(value="/toProjectAnalysis",method = RequestMethod.GET)
	public String toProjectAnalysis(){
		return "charts/projectAnalysis";
	}
	/**
	 * 
	 * 项目总览柱状图表
	 * @param query
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryOverView")
	public ResponseData<SopProjectAnalysis> queryOverView(SopProjectAnalysis query){
		ResponseData<SopProjectAnalysis> responseBody = new ResponseData<SopProjectAnalysis>();
		try {
			List<SopProjectAnalysis> overViewList = analysisService.queryProjectOverView(query);
			responseBody.setEntityList(overViewList);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (DaoException e) {
			// TODO: handle exception
			responseBody.setResult(new Result(Status.ERROR, "系统出现误不可预知错"));
		}
		return responseBody;
	}
	
	
	
	
	
	

}
