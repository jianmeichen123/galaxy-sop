package com.galaxyinternet.chart.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.DepartmentBo;
import com.galaxyinternet.bo.chart.ChartBo;
import com.galaxyinternet.bo.chart.ChartDataBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.model.chart.Page;
import com.galaxyinternet.framework.core.model.chart.ResponseBodyData;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.model.chart.Chart;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.UserRoleService;
import com.galaxyinternet.service.chart.ChartService;

@Controller
@RequestMapping("/galaxy/report")
public class ChartsController extends BaseControllerImpl<Chart, Chart> {
	final Logger logger = LoggerFactory.getLogger(ChartsController.class);
	@Autowired
	private ChartService chartService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	@Override
	protected ChartService getBaseService() {
		return this.chartService;
	}
	
	/**
	 * 工作桌面－－事项预览
	 * @param request
	 * {
	 * 	deptid 部门编号 
	 * }
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/platformMeetingScheduling", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBodyData platformMeetingScheduling(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		
		//返回对象
		ResponseBodyData responseBody = new ResponseBodyData();
		
		//获取参数
		Integer deptid = chartBo.getDeptid();

		//参数map
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deptid", deptid!=-1 ? deptid :getDepId(request));
		
		//获取数据
		List<Map<String,Object>> list = chartService.platformMeetingScheduling(params);
		responseBody.setMapList(list);
		return responseBody;
	}
	
	
	/**
	 * 会议排期列表通用
	 * @param request
	 * {
	     sdate 创建时间－开始
		 edate 创建时间－结束
		 deptid 部门id
		 meetingType 会议类型
		 model - 1.分页模式 2.全部数据
		 datatype -1总数 
		 pageNum 页码，从0开始
		 pageSize 每页数据量 
		}
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/meetingSchedList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBodyData meetingSchedList(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		
		//返回对象
		ResponseBodyData responseBody = new ResponseBodyData();
		
		//获取参数
		String  sdate = chartBo.getDefaultSdate(1);
		String  edate = chartBo.getDefaultEdate(1);
		Integer deptid = chartBo.getDeptid();
		String  meetingType = chartBo.getMeetingType();
		Integer model = chartBo.getModel();
		Integer datatype = chartBo.getDatatype();
		Integer pageNum = chartBo.getPageNum();
		Integer pageSize = chartBo.getPageSize();
		
		//参数map
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sdate", sdate);
		params.put("edate", edate);
		params.put("deptid",deptid!=-1 ? deptid :getDepId(request));
		params.put("meetingType", meetingType);
		params.put("model", model);
		params.put("datatype", datatype);
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
				
		//判断模式
		if(model==1){
			params.put("datatype" ,-1);
			List<Map<String,Object>> listTotal = chartService.meetingSchedList(params);
			Long total = (Long) listTotal.get(0).get("c");
			
			params.put("datatype" ,1);
			List<Map<String,Object>> list = chartService.meetingSchedList(params);
			
			Page pageList = new Page(total, list);
			responseBody.setPageList(pageList);
			pageList.setHHR(isHHR(request));
		}else{
			List<Map<String,Object>> mapList = chartService.meetingSchedList(params);
			responseBody.setMapList(mapList);
		}
				
		return responseBody;
	}
	
	/**
	 * 项目数统计，高管调用    根据事业部分组统计  
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/gglinechart", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBodyData gglinechart(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		
		//返回对象
		ResponseBodyData responseBody = new ResponseBodyData();
		
		try {
			//获取参数
			String  sdate = chartBo.getDefaultSdate(1);  	//2016-01-01
			String  edate = chartBo.getDefaultEdate(1);  	//2016-05-18
			Integer year = chartBo.getYear();            	//2016
			Integer deptid = chartBo.getDeptid();       	 //-1
			String projectType = chartBo.getProjectType();  //-1
			Integer model = chartBo.getModel();				//1    1分页模式  2返回全部
			Integer datatype = chartBo.getDatatype();       // -1      当model＝1时有效   -1  返回总数  <>-1 返回数据行
			Integer pageNum = chartBo.getPageNum();			//0
			Integer pageSize = chartBo.getPageSize();		//10
			
			//参数map
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("sdate", sdate);
			params.put("edate", edate);
			params.put("year", year);
			params.put("deptid", deptid!=-1 ? deptid :getDepId(request));  //-1
			params.put("projectType", projectType);  //-1
			params.put("model", model);  
			params.put("datatype", datatype);  
			params.put("pageNum", pageNum);
			params.put("pageSize", pageSize);
			
			//判断模式
			if(model==1){
				// -1  返回总数  <>-1 返回数据行
				params.put("datatype" ,-1);
				List<Map<String,Object>> listTotal = chartService.ggLineChart(params);
				Long total = (Long) listTotal.get(0).get("c");
				
				params.put("datatype" ,1);
				List<Map<String,Object>> list = chartService.ggLineChart(params);
				
				Page pageList = new Page(total, list);
				pageList.setHHR(isHHR(request));
				responseBody.setPageList(pageList);
			}else{
				List<Map<String,Object>> mapList = chartService.ggLineChart(params);
				responseBody.setMapList(mapList);
			}
					
		} catch (Exception e) {
			logger.error("gglinechart 高管项目数查看统计失败", e);
		}
		
		return responseBody;
	}
	
	/**
	 * 项目数统计，根据事业部分组统计
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/linechart", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBodyData linechart(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		
		//返回对象
		ResponseBodyData responseBody = new ResponseBodyData();
		
		//获取参数
		String  sdate = chartBo.getDefaultSdate(1);
		String  edate = chartBo.getDefaultEdate(1);
		Integer year = chartBo.getYear();
		Integer deptid = chartBo.getDeptid();
		String projectType = chartBo.getProjectType();
		Integer model = chartBo.getModel();
		Integer datatype = chartBo.getDatatype();
		Integer pageNum = chartBo.getPageNum();
		Integer pageSize = chartBo.getPageSize();
		
		//参数map
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sdate", sdate);
		params.put("edate", edate);
		params.put("year", year);
		params.put("deptid", deptid!=-1 ? deptid :getDepId(request));
		params.put("projectType", projectType);
		params.put("model", model);
		params.put("datatype", datatype);
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		
		//判断模式
		if(model==1){
			params.put("datatype" ,-1);
			List<Map<String,Object>> listTotal = chartService.lineChart(params);
			Long total = (Long) listTotal.get(0).get("c");
			
			params.put("datatype" ,1);
			List<Map<String,Object>> list = chartService.lineChart(params);
			
			Page pageList = new Page(total, list);
			pageList.setHHR(isHHR(request));
			responseBody.setPageList(pageList);
		}else{
			List<Map<String,Object>> mapList = chartService.lineChart(params);
			responseBody.setMapList(mapList);
		}
				
		return responseBody;
	}
	


	/**
	 * 项目数统计. 合伙人专用
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/lineHhrChart", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBodyData lineHhrChart(HttpServletRequest request,@RequestBody ChartBo chartBo) {	
		//返回对象
		ResponseBodyData responseBody = new ResponseBodyData();
		//获取参数
		String  sdate = chartBo.getDefaultSdate(1)+" 00:00:00";
		String  edate = chartBo.getDefaultEdate(1)+" 23:59:59";
	//	Integer year = chartBo.getYear();
		Integer deptid = chartBo.getDeptid();
		String  projectType = chartBo.getProjectType();
		Integer model = chartBo.getModel();
	//	Integer datatype = chartBo.getDatatype();
		Integer pageNum = chartBo.getPageNum();
		Integer pageSize = chartBo.getPageSize();
		User user =  (User) getUserFromSession(request);
		Project project=new Project();
		project.setCreateUid(user.getId());
		PageRequest pageable = new PageRequest();
		pageable.setSize(pageSize);
		pageable.setPage(pageNum);
		
			try {
				project.setStartTime(DateUtil.stringToLong(sdate,"yyyy-MM-dd HH:mm:ss"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				project.setEndTime(DateUtil.stringToLong(edate,"yyyy-MM-dd HH:mm:ss"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		project.setProjectType(!projectType.equals("-1")?projectType:null);
		project.setProjectDepartid(deptid!=-1 ? deptid:(long)getDepId(request));
		//判断模式
		if(model==1){
			List<Map<String,Object>> list = chartService.lineHhrChart1(project,pageable);
			Map<String, Object> map = list.get(list.size()-1);
			int total =(int) map.get("total");
			list.remove(list.size()-1);
			Page pageList = new Page((long)total, list);
			pageList.setHHR(isHHR(request));
			responseBody.setPageList(pageList);
		}else{
			List<Map<String,Object>> mapList = chartService.lineHhrChart1(project,pageable);
			responseBody.setMapList(mapList);
		}
				
		return responseBody;
	}
	
	/**
	 * 数据简报－项目目标追踪、投资事业线目标完成对比
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/databriefchart", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBodyData databriefcchart(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		
		//返回对象
		ResponseBodyData responseBody = new ResponseBodyData();
		
		if(isHHR(request) && chartBo.getType()==3){
			//获取参数
			String  sdate = chartBo.getDefaultSdate(1);
			String  edate = chartBo.getDefaultEdate(1);
			Integer year = chartBo.getYear();
			String projectType = chartBo.getProjectType();
			String projectProgress = chartBo.getProjectProgress();
			Integer deptid = chartBo.getDeptid();
			Integer datatype = chartBo.getDatatype();
			Integer pageNum = chartBo.getPageNum();
			Integer pageSize = chartBo.getPageSize();
			
			//参数map
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("sdate", sdate);
			params.put("edate", edate);
			params.put("year", year);
			params.put("projectType", projectType);
			params.put("projectProgress", projectProgress);
			params.put("deptid", deptid!=-1 ? deptid :getDepId(request));
			params.put("datatype", datatype);
			params.put("pageNum", pageNum);
			params.put("pageSize", pageSize);

			//获取总数
			params.put("datatype" ,-1);
			List<Map<String,Object>> listTotal = chartService.userKpi(params);
			Long total = (Long) listTotal.get(0).get("c");
			//获取数据
			params.put("datatype" ,1);
			List<Map<String,Object>> list = chartService.userKpi(params);
			//封装，返回
			Page pageList = new Page(total, list);
			responseBody.setPageList(pageList);
			boolean flag = isHHR(request);
			pageList.setHHR(flag);
		}else{
			//获取参数
			Integer year = chartBo.getYear();
			Integer type = chartBo.getType();
			Integer deptid = chartBo.getDeptid();
			
			//参数map
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("year", year);
			params.put("type", type);
			params.put("deptid", deptid!=-1 ? deptid :getDepId(request));
			
			//获取数据
			List<Map<String,Object>> list = chartService.dataBriefChart(params);
			responseBody.setMapList(list);
		}
		return responseBody;
	}
	
	/**
	 * 获取项目进度分布
	 * 1.工作桌面－项目进度
	 * 2.项目总览－项目进度分布图
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/projectprogress", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBodyData projectprogress(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		
		//返回对象
		ResponseBodyData responseBody = new ResponseBodyData();
		
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
		if(!(roleIdList.contains(UserConstant.CEO) || roleIdList.contains(UserConstant.DSZ) || roleIdList.contains(UserConstant.HHR))){ 
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
		}
		
		//获取参数
		String sdate = chartBo.getDefaultSdate(1); // 开始时间(项目创建时间)
		String edate = chartBo.getDefaultEdate(1); // 结束时间(项目创建时间)
		String projectType = chartBo.getProjectType(); // 项目类型：自建、外部
		String projectProgress = chartBo.getProjectProgress(); // 项目进度
		Integer deptid = chartBo.getDeptid(); // 部门id（投资线）
		long userid = chartBo.getUserid();
		
		//获取数据
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sdate", sdate);
		params.put("edate", edate);
		params.put("projectType", projectType);
		params.put("projectProgress", projectProgress);
		params.put("deptid", deptid!=-1 ? deptid :getDepId(request));
		params.put("userid", userid);
		List<Map<String,Object>> list = chartService.projectProgressChart(params);
		responseBody.setMapList(list);
		return responseBody;
	}
	
	/**
	 * 获取项目列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/projectlist", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBodyData projectList(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		
		//返回对象
		ResponseBodyData responseBody = new ResponseBodyData();
				
		//获取参数
		String sdate = chartBo.getDefaultSdate(1); // 开始时间(项目创建时间)
		String edate = chartBo.getDefaultEdate(1); // 结束时间(项目创建时间)
		String projectType = chartBo.getProjectType(); // 项目类型：自建、外部
		String projectProgress = chartBo.getProjectProgress(); // 项目进度
		Integer deptid = chartBo.getDeptid(); // 部门id（投资线）
		long userid = chartBo.getUserid();
		Integer secLxh = chartBo.getSecLxh();
		Integer secTjh = chartBo.getSecTjh();
		//Integer type = request.getParameter("type")==null ? -1 : Integer.parseInt(request.getParameter("type")); // -1总数 else 详细列表
		Integer pageNum = chartBo.getPageNum(); // 页码
		Integer pageSize = chartBo.getPageSize(); // 每页数据量
		
		//验证参数
		if(sdate==null || edate==null || projectType==null || projectProgress==null || deptid==null || pageNum==null || pageSize==null){
			responseBody.setResult(new Result(Status.ERROR, "参数类型错误或缺失!"));
			return responseBody;
		}
		
		//获取表格数据总数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sdate", sdate);
		params.put("edate", edate);
		params.put("projectType", projectType);
		params.put("projectProgress", projectProgress);
		params.put("deptid", deptid!=-1 ? deptid :getDepId(request));
		params.put("userid", userid);
		params.put("secLxh", secLxh);
		params.put("secTjh", secTjh);
		params.put("type", -1);
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		List<Map<String,Object>> list = chartService.projectList(params);
		Long total = (Long) list.get(0).get("c");
		
		//获取表格数据
		params.put("type", 1);
		List<Map<String,Object>> tableList = chartService.projectList(params);
		
		Page page = new Page(total, tableList, null);
		responseBody.setPageList(page);
		return responseBody;
	}
	
	/**
	 * 项目历时统计
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/progressDurationList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBodyData progressDurationList(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		
		//返回对象
		ResponseBodyData responseBody = new ResponseBodyData();
		
		//获取参数
		String  sdate = chartBo.getDefaultSdate(1);
		String  edate = chartBo.getDefaultEdate(1);
		String  projectType = chartBo.getProjectType();
		String  projectProgress = chartBo.getProjectProgress();
		String  projectStatus = chartBo.getProjectStatus();
		Integer deptid = chartBo.getDeptid();
		Integer model = chartBo.getModel();
		Integer datatype = chartBo.getDatatype();
		Integer pageNum = chartBo.getPageNum();
		Integer pageSize = chartBo.getPageSize();
		
		//参数map
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sdate", sdate);
		params.put("edate", edate);
		params.put("projectType", projectType);
		params.put("projectProgress", projectProgress);
		params.put("projectStatus", projectStatus);
		params.put("deptid", deptid!=-1 ? deptid :getDepId(request));
		params.put("model", model);
		params.put("datatype", datatype);
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		
		//判断模式
		if(model==1){
			params.put("datatype" ,-1);
			List<Map<String,Object>> listTotal = chartService.progressDurationList(params);
			Long total = (Long) listTotal.get(0).get("c");
			
			params.put("datatype" ,1);
			List<Map<String,Object>> list = chartService.progressDurationList(params);
			
			Page pageList = new Page(total, list);
			responseBody.setPageList(pageList);
			pageList.setHHR(isHHR(request));
		}else{
			List<Map<String,Object>> mapList = chartService.progressDurationList(params);
			responseBody.setMapList(mapList);
		}
				
		return responseBody;
	}
	
	/**
	 * 项目完成增长率统计－－日报图表
	 * 维度 ：事业线、项目类型、日期范围
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/rateRiseDChart", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBodyData rateRiseDChart(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		
		//返回对象
		ResponseBodyData responseBody = new ResponseBodyData();
		
		//获取参数
		String  sdate = chartBo.getDefaultSdate(1);
		String  edate = chartBo.getDefaultEdate(1);
		Integer deptid = chartBo.getDeptid();
		String projectType = chartBo.getProjectType();
		
		//参数map
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sdate", sdate);
		params.put("edate", edate);
		params.put("deptid", deptid!=-1 ? deptid :getDepId(request));
		params.put("projectType", projectType);
		
		//取数据，返回
		List<Map<String,Object>> mapList = chartService.rateRiseDChart(params);
		responseBody.setMapList(mapList);
				
		return responseBody;
	}
	
	/**
	 * 项目完成增长率统计－－日报表格数据
	 * 维度 ：事业线、项目类型、日期范围
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/rateRiseD", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBodyData rateRiseD(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		
		//返回对象
		ResponseBodyData responseBody = new ResponseBodyData();
		
		//获取参数
		String  sdate = chartBo.getDefaultSdate(1);
		String  edate = chartBo.getDefaultEdate(1);
		Integer deptid = chartBo.getDeptid();
		String projectType = chartBo.getProjectType();
		Integer datatype = chartBo.getDatatype();
		Integer pageNum = chartBo.getPageNum();
		Integer pageSize = chartBo.getPageSize();
		
		//参数map
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sdate", sdate);
		params.put("edate", edate);
		params.put("deptid", deptid!=-1 ? deptid :getDepId(request));
		params.put("projectType", projectType);
		params.put("datatype", datatype);
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		
		//取数据，返回
		params.put("datatype" ,-1);
		List<Map<String,Object>> listTotal = chartService.rateRiseD(params);
		Long total = (Long) listTotal.get(0).get("c");
		
		params.put("datatype" ,1);
		List<Map<String,Object>> list = chartService.rateRiseD(params);
		
		Page pageList = new Page(total, list);
		responseBody.setPageList(pageList);
		pageList.setHHR(isHHR(request));		
		return responseBody;
	}
	
	/**
	 * 项目完成增长率统计－－月报图表
	 * 维度 ：事业线、项目类型、月范围
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/rateRiseMChart", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBodyData rateRiseMChart(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		
		//返回对象
		ResponseBodyData responseBody = new ResponseBodyData();
		
		//获取参数
		String  sym = chartBo.getSym();
		String  eym = chartBo.getEym();
		Integer deptid = chartBo.getDeptid();
		String projectType = chartBo.getProjectType();
		
		//参数map
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sym", sym);
		params.put("eym", eym);
		params.put("deptid", deptid!=-1 ? deptid :getDepId(request));
		params.put("projectType", projectType);
		
		//取数据，返回
		List<Map<String,Object>> mapList = chartService.rateRiseMChart(params);
		responseBody.setMapList(mapList);
				
		return responseBody;
	}
	
	/**
	 * 数据简报－－项目完成增长率统计
	 * 维度 ：月范围
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/rateRiseMonthChart", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBodyData rateRiseMonthChart(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		
		//返回对象
		ResponseBodyData responseBody = new ResponseBodyData();
		
		//获取参数
		String  sym = chartBo.getSym();
		String  eym = chartBo.getEym();
		Integer deptid = chartBo.getDeptid();
		String projectType = chartBo.getProjectType();
		String projectProgress = chartBo.getProjectProgress();
		
		//参数map
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sym", sym);
		params.put("eym", eym);
		params.put("deptid", deptid!=-1 ? deptid :getDepId(request));
		params.put("projectType", projectType);
		params.put("projectProgress", projectProgress);
		
		//取数据，返回
		List<Map<String,Object>> mapList = chartService.rateRiseMonthChart(params);
		responseBody.setMapList(mapList);
				
		return responseBody;
	}

	/**
	 * 项目完成增长率统计－－月报表格数据
	 * 维度 ：事业线、项目类型、日期范围
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/rateRiseM", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBodyData rateRiseM(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		
		//返回对象
		ResponseBodyData responseBody = new ResponseBodyData();
		
		//获取参数
		String  sym = chartBo.getSym();
		String  eym = chartBo.getDefaultEdate(1);
		Integer deptid = chartBo.getDeptid();
		String projectType = chartBo.getProjectType();
		Integer datatype = chartBo.getDatatype();
		Integer pageNum = chartBo.getPageNum();
		Integer pageSize = chartBo.getPageSize();
		
		//参数map
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sym", sym);
		params.put("eym", eym);
		params.put("deptid", deptid!=-1 ? deptid :getDepId(request));
		params.put("projectType", projectType);
		params.put("datatype", datatype);
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		
		//取数据，返回
		params.put("datatype" ,-1);
		List<Map<String,Object>> listTotal = chartService.rateRiseM(params);
		Long total = (Long) listTotal.get(0).get("c");
		
		params.put("datatype" ,1);
		List<Map<String,Object>> list = chartService.rateRiseM(params);
		
		Page pageList = new Page(total, list);
		responseBody.setPageList(pageList);
		pageList.setHHR(isHHR(request));		
		return responseBody;
	}
	
	/**
	 * 过会率
	 * 维度 ：事业线
	 * 数据范围 ： 会议创建时间
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/meetingrate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBodyData meetingRate(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		
		//返回对象
		ResponseBodyData responseBody = new ResponseBodyData();
		
		//获取参数
		String  sdate = chartBo.getDefaultSdate(1);
		String  edate = chartBo.getDefaultEdate(1);
		Integer deptid = chartBo.getDeptid();
		String meetingType = chartBo.getMeetingType();
		String projectType = chartBo.getProjectType();
		Integer model = chartBo.getModel();
		Integer datatype = chartBo.getDatatype();
		Integer pageNum = chartBo.getPageNum();
		Integer pageSize = chartBo.getPageSize();
		
		//参数map
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sdate", sdate);
		params.put("edate", edate);
		params.put("deptid", deptid!=-1 ? deptid :getDepId(request));
		params.put("meetingType", meetingType);
		params.put("projectType", projectType);
		params.put("model", model);
		params.put("datatype", datatype);
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		
		//判断模式
		if(model==1){
			params.put("datatype" ,-1);
			List<Map<String,Object>> listTotal = chartService.meetingRate(params);
			Long total = (Long) listTotal.get(0).get("c");
			
			params.put("datatype" ,1);
			List<Map<String,Object>> list = chartService.meetingRate(params);
			
			Page pageList = new Page(total, list);
			responseBody.setPageList(pageList);
			pageList.setHHR(isHHR(request));
		}else{
			List<Map<String,Object>> mapList = chartService.meetingRate(params);
			responseBody.setMapList(mapList);
		}
				
		return responseBody;
	}
	
	/**
	 * 过会率
	 * 维度 ：投资经理
	 * 数据范围 ： 会议创建时间
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/meetingRateUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBodyData meetingRateUser(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		
		//返回对象
		ResponseBodyData responseBody = new ResponseBodyData();
		
		//获取参数
		String  sdate = chartBo.getDefaultSdate(1);
		String  edate = chartBo.getDefaultEdate(1);
		Integer deptid = chartBo.getDeptid();
		String meetingType = chartBo.getMeetingType();
		String projectType = chartBo.getProjectType();
		Integer model = chartBo.getModel();
		Integer datatype = chartBo.getDatatype();
		Integer pageNum = chartBo.getPageNum();
		Integer pageSize = chartBo.getPageSize();
		
		//参数map
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sdate", sdate);
		params.put("edate", edate);
		params.put("deptid", deptid!=-1 ? deptid :getDepId(request));
		params.put("meetingType", meetingType);
		params.put("projectType", projectType);
		params.put("model", model);
		params.put("datatype", datatype);
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		
		//判断模式
		if(model==1){
			params.put("datatype" ,-1);
			List<Map<String,Object>> listTotal = chartService.meetingRateUser(params);
			Long total = (Long) listTotal.get(0).get("c");
			
			params.put("datatype" ,1);
			List<Map<String,Object>> list = chartService.meetingRateUser(params);
			
			Page pageList = new Page(total, list);
			responseBody.setPageList(pageList);
			pageList.setHHR(isHHR(request));
		}else{
			List<Map<String,Object>> mapList = chartService.meetingRateUser(params);
			responseBody.setMapList(mapList);
		}
				
		return responseBody;
	}
	
	/**
	 * 获取投资经理KPI
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/userkpi", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBodyData userKpi(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		
		//返回对象
		ResponseBodyData responseBody = new ResponseBodyData();
		
		//获取参数
		String  sdate = chartBo.getDefaultSdate(1);
		String  edate = chartBo.getDefaultEdate(1);
		Integer deptid = chartBo.getDeptid();
		Integer year = chartBo.getYear();
		String projectType = chartBo.getProjectType();
		String projectProgress = chartBo.getProjectProgress();
		Integer datatype = chartBo.getDatatype();
		Integer pageNum = chartBo.getPageNum();
		Integer pageSize = chartBo.getPageSize();
		
		//参数map
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sdate", sdate);
		params.put("edate", edate);
		params.put("year", year);
		params.put("projectType", projectType);
		params.put("projectProgress", projectProgress);
		params.put("deptid", deptid!=-1 ? deptid :getDepId(request));
		params.put("datatype", datatype);
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);

		//获取总数
		params.put("datatype" ,-1);
		List<Map<String,Object>> listTotal = chartService.userKpi(params);
		Long total = (Long) listTotal.get(0).get("c");
		//获取数据
		params.put("datatype" ,1);
		List<Map<String,Object>> list = chartService.userKpi(params);
		//封装，返回
		Page pageList = new Page(total, list);
		responseBody.setPageList(pageList);
		pageList.setHHR(isHHR(request));
		return responseBody;
	}
	
	/**
	 * 获取部门KPI
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deptkpi", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBodyData deptkpi(HttpServletRequest request,@RequestBody ChartBo chartBo) {
		
		//返回对象
		ResponseBodyData responseBody = new ResponseBodyData();
				
		//获取参数
		String  sdate = chartBo.getDefaultSdate(1);
		String  edate = chartBo.getDefaultEdate(1);
		Integer year = chartBo.getYear();
		Integer deptid = chartBo.getDeptid();
		String projectType = chartBo.getProjectType();
		Integer datatype = chartBo.getDatatype();
		Integer pageNum = chartBo.getPageNum();
		Integer pageSize = chartBo.getPageSize();
		
		//参数map
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sdate", sdate);
		params.put("edate", edate);
		params.put("year", year);
		params.put("deptid", deptid!=-1 ? deptid :getDepId(request));
		params.put("projectType", projectType);
		params.put("datatype", datatype);
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);

		//获取总数
		params.put("datatype" ,-1);
		Long total = null;
		List<Map<String,Object>> list = null;
		List<Map<String,Object>> listTotal  = null;
		boolean flag = isHHR(request);
		if(flag){
			listTotal = chartService.userKpi(params);
		}else{
			listTotal = chartService.deptKpi(params);
		}
		total = (Long) listTotal.get(0).get("c");
		//获取数据
		params.put("datatype" ,1);
		if(flag){
			list = chartService.userKpi(params);
		}else{
			list = chartService.deptKpi(params);
		}

		//封装，返回
		Page pageList = new Page(total, list);
		responseBody.setPageList(pageList);
		pageList.setHHR(isHHR(request));
		return responseBody;
	}
	
	/**
	 * 获取部门列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/departmentList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBodyData departmentList(HttpServletRequest request,@RequestBody DepartmentBo departmenttBo) {
		
		//返回对象
		ResponseBodyData responseBody = new ResponseBodyData();
		
		//获取参数
		long id = departmenttBo.getId();
		String  name = departmenttBo.getName();
		long parmentId = departmenttBo.getParentId();
		Integer type = departmenttBo.getType();
		long managerId = departmenttBo.getManagerId();
		
		//参数map
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id!=-1 ? id :getDepId(request));
		params.put("name", name);
		params.put("parmentId", parmentId);
		params.put("type", type);
		params.put("managerId", managerId);
		
		//获取数据
		List<Map<String,Object>> mapList = chartService.departmentList(params);
		responseBody.setMapList(mapList);
				
		return responseBody;
	}
	
	/**
	 * 数据简报－－项目完成增长率统计
	 * 维度 ：月范围
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/appBrief", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseBodyData appBrief(HttpServletRequest request/*,@RequestBody ChartBo chartBo*/) {
		
		//返回对象
		ResponseBodyData responseBody = new ResponseBodyData();
		
		//参数map
		Map<String, Object> params = new HashMap<String, Object>();
		
		//判断角色
		User user =  (User) getUserFromSession(request);
		long roleid = user.getRoleId();
		if(roleid==UserConstant.DSZ || roleid==UserConstant.CEO){
			params.put("deptid", -1);
			params.put("userid", -1);
		}else if(roleid==UserConstant.HHR){
			params.put("deptid", getDepId(request));
			params.put("userid", -1);
		}else if(roleid==UserConstant.TZJL){
			params.put("deptid", getDepId(request));
			params.put("userid", user.getId());
		}else{
			params.put("deptid", "-100");
			params.put("userid", "-100");
		}
		
		//取数据，返回
		List<Map<String,Object>> mapList = chartService.appBrief(params);
		responseBody.setMapList(mapList);
				
		return responseBody;
	}

	/**
	 *  获取合伙人管理事业心线id
	    * @Title: getDepId
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param request
	    * @param @return    参数
	    * @return int    返回类型
	    * @throws
	 */
	public int getDepId(HttpServletRequest request){
		
		//返回对象
		User user =  (User) getUserFromSession(request);
		if(user != null){
			if(UserConstant.HHR == user.getRoleId()){
				Long depId = user.getDepartmentId();
				if(depId != null ){
					return depId.intValue();
				}
			}
		}
		return -1;
	}
	
	/**
	 * 	判断一个用户是否是合伙人 
	    * @Title: isHHR
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param request
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	 */
	public boolean isHHR(HttpServletRequest request){
		User user =  (User) getUserFromSession(request);
		if(user != null){
			return UserConstant.HHR == user.getRoleId(); 
		} 
		return false;
	}
	
	
	
	
	
	
	

}
