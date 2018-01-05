package com.galaxyinternet.project_danao.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.model.DongNao.DnProject;

import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.service.ProjectService;
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

	@Autowired
	private ProjectService projectService;




	@RequestMapping(value = "/list/{projectId}", method = RequestMethod.GET)
	public String toRight(@PathVariable("projectId") Long projectId, HttpServletRequest request) {
		Project project = new Project();
		project = projectService.selectColumnById(projectId);
		request.setAttribute("projectInfo", GSONUtil.toJson(project));
		return "project/infoenter/infoList";
	}

	@RequestMapping(value = "/info/{projectId}")
	public String platformTest2(@PathVariable("projectId") Long projectId, HttpServletRequest request) {
		Project project = new Project();
		project = projectService.selectColumnById(projectId);
		request.setAttribute("projectInfo", GSONUtil.toJson(project));
		return "project/infoenter/info";
	}
	@RequestMapping(value = "/infoJsp")
	public String platformTest5(HttpServletRequest request) {
		return "project/infoenter/infoJsp";
	}

	@RequestMapping(value = "/infoDJsp")
	public String platformTestD(HttpServletRequest request) {
		return "project/infoenter/infoDJsp";
	}



	
	/**
	 * 保存项目和大脑的关联关系
	 * 项目id ：       projId
     * 大脑项目code ： projCode
     * 大脑项目公司code ： compCode
	 */
	@RequestMapping(value = "/saveConstat", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseData<DnProject> saveConstat(@RequestBody DnProject dnProject,
															 HttpServletRequest request,HttpServletResponse response )
	{
		ResponseData<DnProject> responseBody = new ResponseData<DnProject>();

		try {
			//1.保存星河投项目 和 大脑项目code\sourceCode的关联关系
			Project upd = new Project();
			upd.setId(dnProject.getProjId());
			upd.setDanaoProjCode(dnProject.getProjCode());
			upd.setDanaoCompCode(dnProject.getCompCode());
			int i = projectService.updateById(upd);
			if(i!=1){
				throw new Exception("项目更新失败");
			}

			responseBody.setResult(new Result(Result.Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Result.Status.ERROR,null, "失败"));
			logger.error("失败",e);
		}

		return responseBody;
	}


	/**
	 * 查询大脑项目列表
	 * { "keyword":"项目名称", "pageNo":0, "pageSize":10 }
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
     * <xxx报告题code ：   titleCode> 引用标识：danaoInfo
     * 大脑项目code ： projCode
     * 大脑项目公司code ： compCode
     *return
	 *  法人信息   	legalInfo
	 *  股权结构	    equityInfo
	 *  团队成员    teamInfo
	 *  融资历史	    financeInfo
	 */
	@RequestMapping(value = "/searchProjectInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseData<DnProject> searchProjectInfo(@RequestBody DnProject dnProject,
                                                                   HttpServletRequest request,HttpServletResponse response)
	{
		ResponseData<DnProject> responseBody = new ResponseData<DnProject>();

		Map<String,Object>  result = new HashMap<>();
		try {

			/*if(dnProject.getCompCode() == null || dnProject.getProjCode() == null){
				if(dnProject.getCompCode() != null || dnProject.getProjCode() != null){
					throw new Exception("缺少参数");
				}
			}*/

			if(dnProject.getDanaoInfo() != null){
				String[] marks = dnProject.getDanaoInfo().split(",");
				List<String> markList = Arrays.asList(marks);

				if(markList.contains("legalInfo")){
					Map<String,Object> result1 = infoFromDanaoService.queryDnaoBusinessInfo(dnProject.getCompCode(),"legalInfo");
					result.putAll(result1);
				}

				if(markList.contains("equityInfo")){
					Map<String,Object> result1 = infoFromDanaoService.queryDnaoBusinessInfo(dnProject.getCompCode(),"equityInfo");
					result.putAll(result1);
				}

				if(markList.contains("teamInfo")){
					Map<String,Object> result1 = infoFromDanaoService.queryDnaoProjTeam(dnProject.getProjCode());
					result.putAll(result1);
				}

				if(markList.contains("financeInfo")){
					Map<String,Object> result1 = infoFromDanaoService.queryDnaoProjFinance(dnProject.getProjCode());
					result.putAll(result1);
				}

			}else{
				/*//仅有大脑项目code，
				//创建项目时，待引用所有的大脑信息，*/

				//2.法人信息 legalInfo、 股权结构 equityInfo
				Map<String,Object> result1 = infoFromDanaoService.queryDnaoBusinessInfo(dnProject.getCompCode(),null);

                //2.项目团队成员 teamInfo
                Map<String,Object> result2 = infoFromDanaoService.queryDnaoProjTeam(dnProject.getProjCode());

                //2.融资历史 financeInfo
                Map<String,Object> result3 = infoFromDanaoService.queryDnaoProjFinance(dnProject.getProjCode());

				result.putAll(result1);
				result.putAll(result2);
				result.putAll(result3);

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
