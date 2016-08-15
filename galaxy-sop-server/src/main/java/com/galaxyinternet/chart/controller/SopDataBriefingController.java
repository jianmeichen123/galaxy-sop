package com.galaxyinternet.chart.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.model.chart.SopCharts;
import com.galaxyinternet.model.common.Config;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.model.user.UserRole;
import com.galaxyinternet.service.ConfigService;
import com.galaxyinternet.service.UserRoleService;
import com.galaxyinternet.service.UserService;
import com.galaxyinternet.service.chart.SopDataBriefingService;
import com.galaxyinternet.utils.MathUtils;

@Controller
@RequestMapping("/galaxy/charts/briefing") 
public class SopDataBriefingController extends BaseControllerImpl<SopCharts, SopCharts> {
	
	@Autowired
	private SopDataBriefingService dataBriefingService;
	
	@Autowired 
	private UserRoleService userRoleService;
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private UserService userService;
	

	@Override
	protected BaseService<SopCharts> getBaseService() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@RequestMapping("/toDataBriefing")
	public String toDataBriefing(){
		return "charts/dataBriefing";
	}
	
	/**
	 * 项目目标追踪根据年份
	 * @param request
	 * @param sopCharts
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/searchTargetTracking", method=RequestMethod.POST , produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopCharts> searchTargetTracking(HttpServletRequest request,@RequestBody(required=false) SopCharts sopCharts){
		ResponseData<SopCharts> responseBody = new ResponseData<SopCharts>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if (user == null) {
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}
		if(sopCharts == null){
			sopCharts = new SopCharts();
		}
		try {
			//设置当前年份 （如果有指定年份的需求 需要加入endTime）
			Date currentYear = DateUtil.getCurrYearFirst();
			sopCharts.setStartTime(currentYear.getTime());
			
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user
				.getId());
			
			if (roleIdList.contains(UserConstant.CEO)
					|| roleIdList.contains(UserConstant.DSZ)) {
				
			} else if (roleIdList.contains(UserConstant.HHR)) {
				sopCharts.setDepartmentId(user.getDepartmentId());
			}
			
			//获取项目目标数
			Long targetCount = queryTargetCount(null, null);
			sopCharts = dataBriefingService.queryTargetTracking(sopCharts,targetCount);
			responseBody.setEntity(sopCharts);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (DaoException e) {
			// TODO: handle exception
			responseBody.setResult(new Result(Status.ERROR, "系统出现不可预知的错误"));
		}
		return responseBody;
	}
	
	
	
	/**
	 * 数据简报 项目完成率
	 * @param sopCharts
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/searchProjectCompletion", method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopCharts> searchCountGroupDate(@RequestBody(required=false) SopCharts sopCharts,HttpServletRequest request){	
		ResponseData<SopCharts> responseBody = new ResponseData<SopCharts>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if (user == null) {
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}
		if(sopCharts == null){
			sopCharts = new SopCharts();
		}
		List<SopCharts> chartsList = null;
		sopCharts.setDateType("%Y-%m");
		try {
			//设置当前年份 （如果有指定年份的需求 需要加入endTime）
			Date currentYear = DateUtil.getCurrYearFirst();
			sopCharts.setStartTime(currentYear.getTime());
			
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user
					.getId());
			if (roleIdList.contains(UserConstant.CEO) || roleIdList.contains(UserConstant.DSZ)) {
				
			}else if(roleIdList.contains(UserConstant.HHR)){
				sopCharts.setDepartmentId(user.getDepartmentId());
			}
			chartsList = dataBriefingService.queryCountGroupDate(sopCharts);
			for(SopCharts chart : chartsList){
				//获取每一个时间段的所有项目树
				Long projectCount = chart.getProjectCount();
				String projectDate = chart.getProjectDate();
				String dateType = sopCharts.getDateType() == null ? "%Y-%m-%d" : sopCharts.getDateType();
				int days = 31;
				switch (dateType) {
				//日配置
				case "%Y-%m-%d":
					days = 1;
					break;
				//月配置
				case "%Y-%m" :
					if(!StringUtils.isBlank(projectDate)){
						String[] timeStr = projectDate.split("-");
						days = DateUtil.getDaysByYearMonth(Integer.parseInt(timeStr[0]), Integer.parseInt(timeStr[1]));
					}
					break;
				default:
					break;
				}
				
				Long targetCount = queryTargetCount(sopCharts.getDepartmentId(), new Long(days));
				
				String completedRate = MathUtils.calculate(projectCount, targetCount, "/", 4);
				completedRate = MathUtils.calculate(Float.parseFloat(completedRate), 100L, "*", 4);
				//计算完成率
				chart.setCompletedRate(completedRate);
				
			}
			responseBody.setEntityList(chartsList);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (DaoException e) {
			// TODO: handle exception
			responseBody.setResult(new Result(Status.ERROR, "系统出现不可预知的错误"));
		}
		return responseBody;
	}
	
	/**
	 * 获取项目目标数
	 * @param departmentId  部门 为空则获取所有
	 * @param times			时间段 ‘天’单位 为空则获取整年
	 * @return
	 */
	private Long queryTargetCount(Long departmentId, Long times){
		//获取个人目标数
		Config config = new Config();
		config.setKey("target_count_code");
		config = configService.queryOne(config);
		Float personTargetCount = Float.parseFloat(config.getValue());
		int userCount = 0;
		
		//部门为空时获取所有投资经理人数
		if(departmentId!=null){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("roleId", UserConstant.TZJL);
			List<Long> departmentIds = new ArrayList<Long>();
			departmentIds.add(departmentId);
			params.put("status", "0");
			params.put("departmentIds", departmentIds);
			userCount = userService.querytTzjlSum(params).get(0).getUserTzjlSum();
		}else{
			UserRole query = new UserRole();
			query.setRoleId(UserConstant.TZJL);
			userCount = userRoleService.queryCount(query).intValue();
		}
		//时长为空时为一年,时长以天为单位
		if(times != null){
			personTargetCount = Float.parseFloat(MathUtils.calculate(personTargetCount, new Long(365), "/", 2));
			personTargetCount = Float.parseFloat(MathUtils.calculate(personTargetCount, times, "*", 2));
		}
		
		Float targetCount = Float.parseFloat(MathUtils.calculate(personTargetCount, new Long(userCount), "*", 2));
		return new Long((int) (targetCount.floatValue() + 0.5));
	}
	
	
	
	
	
}
