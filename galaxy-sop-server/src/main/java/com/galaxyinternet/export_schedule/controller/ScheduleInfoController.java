package com.galaxyinternet.export_schedule.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.export_schedule.model.ScheduleInfo;
import com.galaxyinternet.export_schedule.service.ScheduleInfoService;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;

@Controller
@RequestMapping("/galaxy/visit")
public class ScheduleInfoController extends BaseControllerImpl<ScheduleInfo, ScheduleInfo> {
	private final static Logger logger = LoggerFactory.getLogger(ScheduleInfoController.class);

	@Autowired
	private ScheduleInfoService scheduleInfoService;
	
	@Override
	protected BaseService<ScheduleInfo> getBaseService() {
		// TODO Auto-generated method stub
		return this.scheduleInfoService;
	}
	
    /**
     * 拜访量统计
     * @param sheduleInfo
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "/getVisitStatistics", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ScheduleInfo> getVisitStatistics(@RequestBody ScheduleInfo sheduleInfo){
		ResponseData<ScheduleInfo> responseBody = new ResponseData<ScheduleInfo>();
		if(sheduleInfo == null){
			responseBody.setResult(new Result(Status.ERROR, null, "必要的参数丢失!"));
			return responseBody;
		}
		try{
			Map<String,Object> map = scheduleInfoService.getVisitStatistics(sheduleInfo);
			responseBody.setUserData(map);
		}catch(Exception e){
			responseBody.setResult(new Result(Status.ERROR, null, "拜访量统计失败!"));
			logger.error("拜访量统计失败!", e);
		}
		return responseBody;
	}
	
	 /**
     * 项目拜访
     * @param sheduleInfo
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "/getProjectVisit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ScheduleInfo> getProjectVisit(@RequestBody ScheduleInfo sheduleInfo){
		ResponseData<ScheduleInfo> responseBody = new ResponseData<ScheduleInfo>();
		if(sheduleInfo == null){
			responseBody.setResult(new Result(Status.ERROR, null, "必要的参数丢失!"));
			return responseBody;
		}
		try{
			Map<String,Object> map = scheduleInfoService.getProjectVisit(sheduleInfo);
			responseBody.setUserData(map);
		}catch(Exception e){
			responseBody.setResult(new Result(Status.ERROR, null, "项目拜访统计失败!"));
			logger.error("项目拜访统计失败!", e);
		}
		return responseBody;
	}
	
	
	 /**
     * 融资轮次占比
     * @param sheduleInfo
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "/getVisitFanceStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ScheduleInfo> getVisitFanceStatus(@RequestBody ScheduleInfo sheduleInfo){
		ResponseData<ScheduleInfo> responseBody = new ResponseData<ScheduleInfo>();
		if(sheduleInfo == null){
			responseBody.setResult(new Result(Status.ERROR, null, "必要的参数丢失!"));
			return responseBody;
		}
		try{
			List<ScheduleInfo> list= scheduleInfoService.getVisitFanceStatus(sheduleInfo);
			responseBody.setEntityList(list);
		}catch(Exception e){
			responseBody.setResult(new Result(Status.ERROR, null, "融资轮次占比统计失败!"));
			logger.error("融资轮次占比统计失败!", e);
		}
		return responseBody;
	}
	
	
	 /**
     * 访谈缺失
     * @param sheduleInfo
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "/getRecordVisit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ScheduleInfo> getRecordVisit(@RequestBody ScheduleInfo sheduleInfo){
		ResponseData<ScheduleInfo> responseBody = new ResponseData<ScheduleInfo>();
		if(sheduleInfo == null){
			responseBody.setResult(new Result(Status.ERROR, null, "必要的参数丢失!"));
			return responseBody;
		}
		try{
			Map<String,Object> map = scheduleInfoService.getRecordVisit(sheduleInfo);
			responseBody.setUserData(map);
		}catch(Exception e){
			responseBody.setResult(new Result(Status.ERROR, null, "访谈缺失统计失败!"));
			logger.error("访谈缺失统计失败!", e);
		}
		return responseBody;
	}
}
