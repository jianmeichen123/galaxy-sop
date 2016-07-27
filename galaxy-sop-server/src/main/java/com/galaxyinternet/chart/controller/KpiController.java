package com.galaxyinternet.chart.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.chart.ChartDataBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.query.ChartKpiQuery;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.model.chart.Chart;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.chart.ChartService;
import com.galaxyinternet.service.chart.KpiService;

@Controller
@RequestMapping("/galaxy/kpireport")
public class KpiController extends BaseControllerImpl<Chart, Chart>{
	final Logger logger = LoggerFactory.getLogger(KpiController.class);
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	@Autowired
	private ChartService chartService;
	
	@Autowired
	private KpiService kpiService;
	
	@Override
	protected ChartService getBaseService() {
		return this.chartService;
	}
	
	
	
	
	/**
	 *  获取合伙人管理事业线id
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
	
	
	
	
	//    //TODO   绩效考核
	
	/**
	 * 绩效考核
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/touserkpi", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String touserkpi(HttpServletRequest request) {
		return "report/jxkh/tab_perkpi";
	}
	
	/**
	 * 绩效考核
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toteamkpi", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toteamkpi(HttpServletRequest request) {
		return "report/jxkh/tab_teamkpi";
	}
	
	
	
	/**
	 * 获取投资经理KPI
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/userkpi", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ChartDataBo> userKpi(HttpServletRequest request,@RequestBody ChartKpiQuery query) {
		
		//返回对象
		ResponseData<ChartDataBo> responseBody = new ResponseData<ChartDataBo>();
		
		try {
			if(StringUtils.isBlank(query.getSdate())){
				query.setSdate(DateUtil.getDefaultSdate(1));
			}
			if(StringUtils.isBlank(query.getEdate())){
				query.setEdate(DateUtil.getDefaultEdate(1));
			}
			
			query.setSdate(query.getSdate().trim() + " 00:00:00");
			query.setEdate(query.getEdate().trim() + " 23:59:59");
			Long startTime = DateUtil.stringToLong(query.getSdate(), "yyyy-MM-dd HH:mm:ss");
			Long endTime = DateUtil.stringToLong(query.getEdate(), "yyyy-MM-dd HH:mm:ss");
			if(startTime > endTime){
				responseBody.setResult(new Result(Status.ERROR,null, "开始时间不能大于结束时间"));
				return responseBody;
			}
			query.setStartTime(startTime);
			query.setEndTime(endTime);
			
			//强制 HHR 只能看个人部门下的数据
			int hhrDeptId = getDepId(request);
			if(hhrDeptId!=-1){
				query.setDeptid((long) hhrDeptId);
			}
			com.galaxyinternet.framework.core.model.Page<ChartDataBo> pageList = kpiService.userKpi(query);
			//pageList.setHHR(isHHR(request));
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setPageList(pageList);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "统计失败"));
			logger.error("userKpi report 统计失败",e);
		}
		
		return responseBody;
	}
	
	
	/**
	 * 获取部门KPI
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deptkpi", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ChartDataBo> deptkpi(HttpServletRequest request,@RequestBody ChartKpiQuery query) {
		
		//返回对象
		ResponseData<ChartDataBo> responseBody = new ResponseData<ChartDataBo>();
		
		try {
			if(StringUtils.isBlank(query.getSdate())){
				query.setSdate(DateUtil.getDefaultSdate(1));
			}
			if(StringUtils.isBlank(query.getEdate())){
				query.setEdate(DateUtil.getDefaultEdate(1));
			}
			
			query.setSdate(query.getSdate().trim() + " 00:00:00");
			query.setEdate(query.getEdate().trim() + " 23:59:59");
			Long startTime = DateUtil.stringToLong(query.getSdate(), "yyyy-MM-dd HH:mm:ss");
			Long endTime = DateUtil.stringToLong(query.getEdate(), "yyyy-MM-dd HH:mm:ss");
			if(startTime > endTime){
				responseBody.setResult(new Result(Status.ERROR,null, "开始时间不能大于结束时间"));
				return responseBody;
			}
			query.setStartTime(startTime);
			query.setEndTime(endTime);

			Page<ChartDataBo> pageList = kpiService.deptkpi(query);
			//pageList.setHHR(isHHR(request));
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setPageList(pageList);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "统计失败"));
			logger.error("userKpi report 统计失败",e);
		}
		
		return responseBody;
	}
	
	
	
	
	
	
	/**
	 * 绩效考核－团队绩效－项目列表页面，只提供路由功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deptkpiprojectlist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deptkpiprojectlist(HttpServletRequest request) {
	
		return "common/deptkpiprojectlist";
	}
	
	
	
	
	
	
	
	 
	//==========================  项目数 统计      //TODO   项目数 统计  
	
	
	/**
	 * 项目分析－项目数统计－项目列表页面，只提供路由功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/paprojectlist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String paprojectlist(HttpServletRequest request) {
	
		return "report/projectAnalysis/tab_proNum";
	}
	
	
	
	/**
	 * 项目分析
	 * 项目数统计，高管调用    根据事业部分组统计  
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/gglinechart", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ChartDataBo> gglinechart(HttpServletRequest request,@RequestBody ChartKpiQuery query) {
		
		//返回对象
		ResponseData<ChartDataBo> responseBody = new ResponseData<ChartDataBo>();
		
		try {
			if(StringUtils.isBlank(query.getSdate())){
				query.setSdate(DateUtil.getDefaultSdate(1));
			}
			if(StringUtils.isBlank(query.getEdate())){
				query.setEdate(DateUtil.getDefaultEdate(1));
			}
			
			query.setSdate(query.getSdate().trim() + " 00:00:00");
			query.setEdate(query.getEdate().trim() + " 23:59:59");
			Long startTime = DateUtil.stringToLong(query.getSdate(), "yyyy-MM-dd HH:mm:ss");
			Long endTime = DateUtil.stringToLong(query.getEdate(), "yyyy-MM-dd HH:mm:ss");
			if(startTime > endTime){
				responseBody.setResult(new Result(Status.ERROR,null, "开始时间不能大于结束时间"));
				return responseBody;
			}
			query.setStartTime(startTime);
			query.setEndTime(endTime);

			Page<ChartDataBo> pageList = kpiService.ggLineChart(query);
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setPageList(pageList);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "高管项目数统计失败"));
			logger.error("gglinechart 高管项目数统计失败",e);
		}
		
		return responseBody;
	}
	
	


	/**
	 * 项目分析
	 * 项目数统计. 合伙人专用
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/hhrLineChart", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ChartDataBo> hhrLineChart(HttpServletRequest request,@RequestBody ChartKpiQuery query) {
		//返回对象
		ResponseData<ChartDataBo> responseBody = new ResponseData<ChartDataBo>();
		
		try {
			//强制 HHR 能看
			int hhrDeptId = getDepId(request);
			if(hhrDeptId!=-1){
				query.setDeptid((long) hhrDeptId);
			}else{
				responseBody.setResult(new Result(Status.ERROR,null, "权限错误"));
				return responseBody;
			}
			
			if(StringUtils.isBlank(query.getSdate())){
				query.setSdate(DateUtil.getDefaultSdate(1));
			}
			if(StringUtils.isBlank(query.getEdate())){
				query.setEdate(DateUtil.getDefaultEdate(1));
			}
			
			query.setSdate(query.getSdate().trim() + " 00:00:00");
			query.setEdate(query.getEdate().trim() + " 23:59:59");
			Long startTime = DateUtil.stringToLong(query.getSdate(), "yyyy-MM-dd HH:mm:ss");
			Long endTime = DateUtil.stringToLong(query.getEdate(), "yyyy-MM-dd HH:mm:ss");
			if(startTime > endTime){
				responseBody.setResult(new Result(Status.ERROR,null, "开始时间不能大于结束时间"));
				return responseBody;
			}
			query.setStartTime(startTime);
			query.setEndTime(endTime);
			
			Page<ChartDataBo> pageList = kpiService.hhrLineChart(query);
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setPageList(pageList);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "项目数统计失败"));
			logger.error("hhrLineChart 项目数统计失败",e);
		}
		
		return responseBody;
	}
	
	
	
	
	
	
	
	
	/**
	 * 项目分析－过会率统计－项目列表页面，只提供路由功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ghlprojectlist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String ghlprojectlist(HttpServletRequest request) {
	
		return "common/ghlprojectlist";
	}
	
	/**
	 * 项目分析－投决率统计－项目列表页面，只提供路由功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/tjlprojectlist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String tjlprojectlist(HttpServletRequest request) {
	
		return "common/tjlprojectlist";
	}
	
	
	
	
	
	
	
	
	//  //TODO   过会率统计，投决率统计
	
	
	/**
	 * 项目分析－过会率统计页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toGhlSum", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toGhlSum(HttpServletRequest request) {
	
		return "report/projectAnalysis/tab_ghl";
	}
	
	
	
	/**
	 * 项目分析－投决率统计页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toTjlSum", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toTjlSum(HttpServletRequest request) {
	
		return "report/projectAnalysis/tab_tjl";
	}
	
	
	/**
	 * 项目分析
	 * 过会率统计，投决率统计
	 * 高管调用    根据事业部分组统计  
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deptMeetPassRate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ChartDataBo> deptMeetPassRate(HttpServletRequest request,@RequestBody ChartKpiQuery query) {
		
		//返回对象
		ResponseData<ChartDataBo> responseBody = new ResponseData<ChartDataBo>();
		
		try {
			if(query.getMeetingType()==null){
				responseBody.setResult(new Result(Status.ERROR,null, "统计条件缺失"));
				return responseBody;
			}
			if(StringUtils.isBlank(query.getSdate())){
				query.setSdate(DateUtil.getDefaultSdate(1));
			}
			if(StringUtils.isBlank(query.getEdate())){
				query.setEdate(DateUtil.getDefaultEdate(1));
			}
			
			query.setSdate(query.getSdate().trim() + " 00:00:00");
			query.setEdate(query.getEdate().trim() + " 23:59:59");
			Long startTime = DateUtil.stringToLong(query.getSdate(), "yyyy-MM-dd HH:mm:ss");
			Long endTime = DateUtil.stringToLong(query.getEdate(), "yyyy-MM-dd HH:mm:ss");
			if(startTime > endTime){
				responseBody.setResult(new Result(Status.ERROR,null, "开始时间不能大于结束时间"));
				return responseBody;
			}
			query.setStartTime(startTime);
			query.setEndTime(endTime);

			Page<ChartDataBo> pageList = kpiService.deptMeetPassRate(query);
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setPageList(pageList);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "过会率统计失败"));
			logger.error("deptMeetPassRate 过会率统计失败",e);
		}
		
		return responseBody;
	}
	
	


	/**
	 * 项目分析
	 * 过会率统计，投决率统计
	 * 合伙人专用，统计投资经理数据
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/tzjlMeetPassRate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ChartDataBo> tzjlMeetPassRate(HttpServletRequest request,@RequestBody ChartKpiQuery query) {
		//返回对象
		ResponseData<ChartDataBo> responseBody = new ResponseData<ChartDataBo>();
		
		try {
			if(query.getMeetingType()==null){
				responseBody.setResult(new Result(Status.ERROR,null, "统计条件缺失"));
				return responseBody;
			}
			
			//强制 HHR 能看
			int hhrDeptId = getDepId(request);
			if(hhrDeptId!=-1){
				query.setDeptid((long) hhrDeptId);
			}else{
				responseBody.setResult(new Result(Status.ERROR,null, "权限错误"));
				return responseBody;
			}
			
			if(StringUtils.isBlank(query.getSdate())){
				query.setSdate(DateUtil.getDefaultSdate(1));
			}
			if(StringUtils.isBlank(query.getEdate())){
				query.setEdate(DateUtil.getDefaultEdate(1));
			}
			
			query.setSdate(query.getSdate().trim() + " 00:00:00");
			query.setEdate(query.getEdate().trim() + " 23:59:59");
			Long startTime = DateUtil.stringToLong(query.getSdate(), "yyyy-MM-dd HH:mm:ss");
			Long endTime = DateUtil.stringToLong(query.getEdate(), "yyyy-MM-dd HH:mm:ss");
			if(startTime > endTime){
				responseBody.setResult(new Result(Status.ERROR,null, "开始时间不能大于结束时间"));
				return responseBody;
			}
			query.setStartTime(startTime);
			query.setEndTime(endTime);
			
			Page<ChartDataBo> pageList = kpiService.tzjlMeetPassRate(query);
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setPageList(pageList);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "项目数统计失败"));
			logger.error("hhrLineChart 项目数统计失败",e);
		}
		
		return responseBody;
	}
	
	
	
	
	
	
	
	
	
	
	
	//=======================  数据简报      //  //TODO   目标完成对比
	
	
	/**
	 * 项目分析－过会率统计页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toDataBriefing", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String toDataBriefing(HttpServletRequest request) {
	
		return "report/dataSimpleRep/div_dataBriefing";
	}
	
	
	/**
	 * 数据简报
	 * 投资事业线目标完成对比
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deptProTarget", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ChartDataBo> deptProTarget(HttpServletRequest request,@RequestBody(required=false) ChartKpiQuery query) {
		
		//返回对象
		ResponseData<ChartDataBo> responseBody = new ResponseData<ChartDataBo>();
		
		try {
			if(query==null){
				query = new ChartKpiQuery();
			}
			if(StringUtils.isBlank(query.getSdate())){
				query.setSdate(DateUtil.getDefaultSdate(1));
			}
			if(StringUtils.isBlank(query.getEdate())){
				query.setEdate(DateUtil.getDefaultEdate(1));
			}
			
			query.setSdate(query.getSdate().trim() + " 00:00:00");
			query.setEdate(query.getEdate().trim() + " 23:59:59");
			Long startTime = DateUtil.stringToLong(query.getSdate(), "yyyy-MM-dd HH:mm:ss");
			Long endTime = DateUtil.stringToLong(query.getEdate(), "yyyy-MM-dd HH:mm:ss");
			if(startTime > endTime){
				responseBody.setResult(new Result(Status.ERROR,null, "开始时间不能大于结束时间"));
				return responseBody;
			}
			query.setStartTime(startTime);
			query.setEndTime(endTime);

			Page<ChartDataBo> pageList = kpiService.deptProTarget(query);
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setPageList(pageList);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "过会率统计失败"));
			logger.error("deptMeetPassRate 过会率统计失败",e);
		}
		
		return responseBody;
	}
	
	
	
	
	
	
	
	
	/**
	 * 数据简报
	 * 投资事业线 的   投资经理  目标完成对比
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/tzjlProTarget", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ChartDataBo> tzjlProTarget(HttpServletRequest request,@RequestBody(required=false) ChartKpiQuery query) {
		//返回对象
		ResponseData<ChartDataBo> responseBody = new ResponseData<ChartDataBo>();
		
		try {
			if(query==null){
				query = new ChartKpiQuery();
			}
			
			//强制 HHR 能看
			int hhrDeptId = getDepId(request);
			if(hhrDeptId!=-1){
				query.setDeptid((long) hhrDeptId);
			}else{
				responseBody.setResult(new Result(Status.ERROR,null, "权限错误"));
				return responseBody;
			}
			
			if(StringUtils.isBlank(query.getSdate())){
				query.setSdate(DateUtil.getDefaultSdate(1));
			}
			if(StringUtils.isBlank(query.getEdate())){
				query.setEdate(DateUtil.getDefaultEdate(1));
			}
			
			query.setSdate(query.getSdate().trim() + " 00:00:00");
			query.setEdate(query.getEdate().trim() + " 23:59:59");
			Long startTime = DateUtil.stringToLong(query.getSdate(), "yyyy-MM-dd HH:mm:ss");
			Long endTime = DateUtil.stringToLong(query.getEdate(), "yyyy-MM-dd HH:mm:ss");
			if(startTime > endTime){
				responseBody.setResult(new Result(Status.ERROR,null, "开始时间不能大于结束时间"));
				return responseBody;
			}
			query.setStartTime(startTime);
			query.setEndTime(endTime);
			
			Page<ChartDataBo> pageList = kpiService.tzjlProTarget(query);
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setPageList(pageList);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "项目数统计失败"));
			logger.error("hhrLineChart 项目数统计失败",e);
		}
		
		return responseBody;
	}
	
	
	
	
	
	
}
