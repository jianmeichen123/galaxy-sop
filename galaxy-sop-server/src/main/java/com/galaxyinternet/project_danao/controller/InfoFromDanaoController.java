package com.galaxyinternet.project_danao.controller;


import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.utils.BeanUtils;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.model.DaNao.DnProject;
import com.galaxyinternet.model.DaNao.DnZixun;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.platform.constant.PlatformConst;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Controller
@RequestMapping("/galaxy/infoDanao")
public class InfoFromDanaoController{

	final Logger logger = LoggerFactory.getLogger(InfoFromDanaoController.class);

	@Autowired
	private com.galaxyinternet.service.InfoFromDanaoService infoFromDanaoService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;


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
     * xxx大脑项目公司code ： compCode
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
			//upd.setDanaoCompCode(dnProject.getCompCode());
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
     * 大脑项目code ： projCode
     * xxxxx大脑项目公司code ： compCode
	 * 引用标识：danaoInfo
     *return
	 *  法人信息   	legalInfo
	 *  股权结构	    equityInfo
	 *  团队成员    teamInfo
	 *  融资历史	    financeInfo
	 */
	@RequestMapping(value = "/searchProjectInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseData<DnProject> searchProjectInfo(@RequestBody DnProject dnProject,
																	HttpServletRequest request, HttpServletResponse response)
	{
		ResponseData<DnProject> responseBody = new ResponseData<DnProject>();

		Map<String,Object>  result = new HashMap<>();
		try {

			/*if(dnProject.getCompCode() == null || dnProject.getProjCode() == null){
				if(dnProject.getCompCode() != null || dnProject.getProjCode() != null){
					throw new Exception("缺少参数");
				}
			}*/

			String compCode = null;
			if(dnProject.getDanaoInfo() == null || dnProject.getDanaoInfo().contains("legalInfo")|| dnProject.getDanaoInfo().contains("equityInfo")){
				compCode = infoFromDanaoService.queryDanaoProjCompCode(dnProject.getProjCode());
			}
			dnProject.setCompCode(compCode);

			if(dnProject.getDanaoInfo() != null ){
				String[] marks = dnProject.getDanaoInfo().split(",");
				List<String> markList = Arrays.asList(marks);

				if(markList.contains("legalInfo") && dnProject.getCompCode() != null){
					Map<String,Object> result1 = infoFromDanaoService.queryDnaoBusinessInfo(dnProject.getCompCode(),"legalInfo");
					result.put("legalInfo",result1.get("legalInfo"));
				}

				if(markList.contains("equityInfo") && dnProject.getCompCode() != null){
					Map<String,Object> result1 = infoFromDanaoService.queryDnaoBusinessInfo(dnProject.getCompCode(),"equityInfo");
					result.put("equityInfo",result1.get("equityInfo"));
				}

				if(markList.contains("teamInfo") && dnProject.getProjCode() != null){
					Map<String,Object> result1 = infoFromDanaoService.queryDnaoProjTeam(dnProject.getProjCode());
					result.putAll(result1);
				}

				if(markList.contains("financeInfo") && dnProject.getProjCode() != null){
					Map<String,Object> result1 = infoFromDanaoService.queryDnaoProjFinance(dnProject.getProjCode());
					result.putAll(result1);
				}

			}else if(dnProject.getProjCode() != null ){
				/*//仅有大脑项目code，
				//创建项目时，待引用所有的大脑信息，*/

				//2.法人信息 legalInfo、 股权结构 equityInfo
				if( dnProject.getCompCode() != null){
					Map<String,Object> result1 = infoFromDanaoService.queryDnaoBusinessInfo(dnProject.getCompCode(),null);
					result.putAll(result1);
				}

                //2.项目团队成员 teamInfo
                Map<String,Object> result2 = infoFromDanaoService.queryDnaoProjTeam(dnProject.getProjCode());
				result.putAll(result2);

                //2.融资历史 financeInfo
                Map<String,Object> result3 = infoFromDanaoService.queryDnaoProjFinance(dnProject.getProjCode());
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


// todo global

	/**
	 * 查询
	 */
	@RequestMapping(value = "/searchGlobalInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseData searchGlobalInfo(@RequestBody DnProject dnProject,
														HttpServletRequest request, HttpServletResponse response)
	{
		ResponseData responseBody = new ResponseData();

		Map<String,Long> tReslut = new HashMap<>();
		try {

			String pageSearchInfo = dnProject.getPageSearchInfo()==null?"xhtProject":dnProject.getPageSearchInfo();

			Integer pageNum = dnProject.getPageNo() != null ? dnProject.getPageNo() : 0;
			Integer pageSize = dnProject.getPageSize() != null ? dnProject.getPageSize() : 10;
			String direction = dnProject.getOrder() != null ? dnProject.getOrder() : "desc";
			String keyword = dnProject.getKeyword() != null ? dnProject.getKeyword() : null;

			dnProject.setOrder(direction);
			dnProject.setPageNo(pageNum);
			dnProject.setPageSize(pageSize);

			// 封装查询信息
			Project project = new Project();
			//默认筛选
			/*User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
			if(roleIdList.contains(UserConstant.TZJL)){
				project.setCreateUid(user.getId());
			}else if (roleIdList.contains(UserConstant.HHR)){
				project.setProjectDepartid(user.getDepartmentId());
			}*/
			project.setKeyword(keyword);

			/**
			 * 全局搜索 参数
			 *  pageSearchInfo     total             说明
			 *   xhtProject      xhtProjectTotal    创投项目
			 *   dnProject       dnProjectTotal     创投大脑的项目
			 *   dnZixun         dnZixunTotal       创投大脑投融快讯
			 *   xhtAppZixun     xhtAppZixunTotal   星河资讯-app资讯
			 */
			tReslut =  infoFromDanaoService.globalSearchTypesTotal(project);


			//创投项目 pageList xhtProject
			switch (pageSearchInfo)
			{
				case "xhtProject":
					if(tReslut.get("xhtProjectTotal").intValue() != 0)
					{
						Page<Project> pageProject = projectService.queryPageList(
								project,
								new PageRequest(
										pageNum+1,
										pageSize,
										Sort.Direction.fromString(direction),
										dnProject.getOrderBy()==null?"updated_time":dnProject.getOrderBy())
						);

						//封装数据
						for(Project p : pageProject.getContent()){
							p.setProjectCareerline((String)cache.hget(PlatformConst.CACHE_PREFIX_DEP+p.getProjectDepartid(), "name"));
							//p.setCreateUname((String)cache.hget(PlatformConst.CACHE_PREFIX_USER+p.getCreateUid(), "realName"));
						}

						responseBody.setPageList(pageProject);
					}
				case "dnProject":
					if(tReslut.get("dnProjectTotal").intValue() != 0){
						dnProject.setOrderBy(dnProject.getOrderBy()==null?"setupDT":dnProject.getOrderBy());
						Page<DnProject> projectPage = infoFromDanaoService.queryDnaoProjectPage(BeanUtils.toMap(dnProject));
						responseBody.setPageList(projectPage);
					}
				case "dnZixun":
					if(tReslut.get("dnZixunTotal").intValue() != 0){
						dnProject.setOrderBy(dnProject.getOrderBy()==null?"ctime":dnProject.getOrderBy());
						Page<DnZixun> projectPage = infoFromDanaoService.queryDnaoZixunPage(BeanUtils.toMap(dnProject));
						responseBody.setPageList(projectPage);
					}
				case "xhtAppZixun":
					if(tReslut.get("xhtAppZixunTotal").intValue() != 0){
						dnProject.setOrderBy(dnProject.getOrderBy()==null?"create_time":dnProject.getOrderBy());
						Page<DnZixun> projectPage = infoFromDanaoService.queryXhtAppZixunPage(BeanUtils.toMap(dnProject));
						responseBody.setPageList(projectPage);

					}
				default:
					break;
			}

			if(responseBody.getPageList()==null){
				responseBody.setPageList(new Page<DnZixun>(new ArrayList<DnZixun>() , 0l));
			}
			responseBody.setUserData(tReslut);
			responseBody.setResult(new Result(Result.Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Result.Status.ERROR,null, "失败"));
			logger.error("失败",e);
		}

		return responseBody;
	}



}
