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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/galaxy/infoDanao")
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
	@RequestMapping(value = "/searchProject", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
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



	/**
	 * 查询大脑项目 推荐信息
	 * 项目id，题code，大脑sourceCode = compCode
     * compCode == sourceCode
     *
     * 项目id ：       projId
     * 报告题code ：   titleCode
     * 大脑项目code ： projCode
     * 大脑项目公司code ： compCode
     *
	 */
	@RequestMapping(value = "/searchProjectInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseData<DnProject> searchProjectInfo(@RequestBody DnProject dnProject,
                                                                   HttpServletRequest request,HttpServletResponse response)
	{
		ResponseData<DnProject> responseBody = new ResponseData<DnProject>();

		Map<String,Object>  result = null;
		try {

			if(dnProject.getCompCode() != null && dnProject.getTitleCode() == null)
			{
				//仅有大脑项目code，
				//创建项目时，待引用所有的大脑信息，
				//保存星河投项目 和 大脑项目code\sourceCode的关联关系


				//法人信息、股权结构
                result = infoFromDanaoService.queryDnaoBusinessInfo(dnProject.getCompCode(),null);

                //项目团队
                Map<String,Object> result2 = infoFromDanaoService.queryDnaoProjTeam(dnProject.getProjCode());

                //项目团队
                Map<String,Object> result3 = infoFromDanaoService.queryDnaoProjFinance(dnProject.getProjCode());



			} else if(dnProject.getCompCode() != null && dnProject.getTitleCode() != null)
			{
				//有大脑项目code，标题code
				//在报告中，待引用特定的大脑信息，


			}else if(dnProject.getCompCode() == null && dnProject.getTitleCode() != null)
			{
				//无大脑项目code，有标题code
				//在报告中，待引用特定的大脑信息，但是创建项目时未引用大脑数据
				//此时调用项目查询接口，查找类似项目

			}


			responseBody.setUserData(result);
			responseBody.setResult(new Result(Result.Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Result.Status.ERROR,null, "失败"));
			logger.error("失败",e);
		}

		return responseBody;
	}


}
