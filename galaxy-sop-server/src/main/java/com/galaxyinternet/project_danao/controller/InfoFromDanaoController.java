package com.galaxyinternet.project_danao.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.model.DongNao.DnProject;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



public class InfoFromDanaoController{
	
	final Logger logger = LoggerFactory.getLogger(InfoFromDanaoController.class);
	
	@Autowired
	private com.galaxyinternet.service.InfoFromDanaoService infoFromDanaoService;


	@RequestMapping(value = "/interView", method = RequestMethod.GET)
	public String interView() {
		return "interview/view";
	}

	
	
	/**
	 * 查询大脑项目列表
	 */
	@RequestMapping(value = "/searchProject", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseData<DnProject> addFileInterview(@RequestBody DnProject dnProject,
			HttpServletRequest request,HttpServletResponse response )
	{
		ResponseData<DnProject> responseBody = new ResponseData<DnProject>();

		try {
			if(dnProject.getOrder() == null) dnProject.setOrder("desc");
			if(dnProject.getOrderBy() == null) dnProject.setOrderBy("setupDT");
			if(dnProject.getPageNo() == null) dnProject.setPageNo(0);
			if(dnProject.getPageSize() == null) dnProject.setPageSize(5);

			Map<String, Object> map = new HashMap<>();
			map.put("uid", request.getSession().getId());
			map.put("query", com.galaxyinternet.framework.core.utils.BeanUtils.toMap(dnProject));
			Page<DnProject> projectPage = infoFromDanaoService.queryDnaoProjectPage(map);

			responseBody.setPageList(projectPage);
			responseBody.setResult(new Result(Result.Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Result.Status.ERROR,null, "失败"));
			logger.error("失败",e);
		}

		return responseBody;
	}






}
