package com.galaxyinternet.hologram.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationOperationTime;
import com.galaxyinternet.service.hologram.InformationOperationTimeService;

@Controller
@RequestMapping("/galaxy/InformationOperationTime")
public class InformationOperationTimeController extends BaseControllerImpl<InformationOperationTime,InformationOperationTime> {

	final Logger logger = LoggerFactory.getLogger(InformationOperationTimeController.class);
	 
	@Autowired
	InformationOperationTimeService informationOperationTimeService;
	
	@Override
	protected BaseService<InformationOperationTime> getBaseService() {
		// TODO Auto-generated method stub
		return this.informationOperationTimeService;
	}
	
	
	
	/**
	 * 更新全息图项目走势时间
	 * @param informationFile
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateOperateTime", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)  
    public Result updateOperateTime(@RequestBody InformationOperationTime informationOperationTime,HttpServletRequest request){
		if(informationOperationTime == null || informationOperationTime.getProjectId() == null || StringUtils.isEmpty(informationOperationTime.getReflect())){
			return new Result(Status.ERROR, "参数丢失!");
		}
		try {
			Map<String,Object> map = reflectData(informationOperationTime.getReflect());
		    if(map.get("reflect") != null && map.get("reportType") != null){
		    	informationOperationTime.setReflect((String) map.get("reflect"));
		    	informationOperationTime.setReportType((Byte) map.get("reportType"));
		    }
			informationOperationTimeService.updateInformationTime(informationOperationTime);
			return new Result(Status.OK, "");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("更新操作时间失败!");
			// TODO: handle exception
			return new Result(Status.ERROR, "系统出现异常");
		}
		
	}
	
	
	/**
	 * 获取全息图项目走势时间
	 * @param informationFile
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getOperateTime", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)  
    public ResponseData<InformationOperationTime> getOperateTime(@RequestBody InformationOperationTime informationOperationTime,HttpServletRequest request){
		ResponseData<InformationOperationTime> responseBody = new ResponseData<InformationOperationTime>();
		if(informationOperationTime == null || informationOperationTime.getProjectId() == null || StringUtils.isEmpty(informationOperationTime.getReflect())){
			responseBody.setResult(new Result(Status.ERROR, null,"参数丢失"));
			return responseBody;
		}
		try {
			Map<String,Object> map = reflectData(informationOperationTime.getReflect());
		    if(map.get("reflect") != null && map.get("reportType") != null){
		    	informationOperationTime.setReflect((String) map.get("reflect"));
		    	informationOperationTime.setReportType((Byte) map.get("reportType"));
		    }
			InformationOperationTime in = informationOperationTimeService.getInformationTime(informationOperationTime);
			responseBody.setEntity(in);
			responseBody.setResult(new Result(Status.OK, null,"success"));
			return responseBody;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取操作时间操作时间失败!");
			// TODO: handle exception
			responseBody.setResult(new Result(Status.ERROR, "系统出现异常"));
		}
		return responseBody;
		
	}
	
	/**
	 * 参数判断
	 * @param reflect
	 * @return
	 */
	private Map<String,Object> reflectData(String reflect){
		Map<String,Object> map = new HashMap<String,Object>();
		Byte reportType = null;
		switch(reflect){
		    case "NO1": 
			    	reflect = "informationTime";
			    	reportType = 0;
			    	break;
		    case "NO2" : reportType = 0;reflect = "projectTime";break;
		    case "ENO1": reportType = 1;reflect = "projectTime";break;
		    case "DNO2": reportType = 2;reflect = "projectTime";break;	
		    case "GNO2": reportType = 5;reflect = "projectTime";break;
		    case "CNO1": reportType = 6;reflect = "projectTime";break;
		    case "NO3" : reportType = 0;reflect = "teamTime";break;
		    case "ENO2": reportType = 1;reflect = "teamTime";break;
		    case "DNO3": reportType = 2;reflect = "teamTime";break;
		    case "GNO3": reportType = 5;reflect = "teamTime";break;
		    case "CNO2": reportType = 6;reflect = "teamTime";break;
		    case "ONO2": reportType = 7;reflect = "teamTime";break;
		    case "NO4" : reportType = 0;reflect = "operationDataTime";break;
		    case "ENO3": reportType = 1;reflect = "operationDataTime";break;
		    case "DNO4": reportType = 2;reflect = "operationDataTime";break;
		    case "GNO4": reportType = 5;reflect = "operationDataTime";break;
		    case "CNO3": reportType = 6;reflect = "operationDataTime";break;
		    case "ONO3": reportType = 7;reflect = "operationDataTime";break;
		    case "NO5" : reportType = 0;reflect = "competeDataTime";break;
		    case "ENO4": reportType = 1;reflect = "competeDataTime";break;
		    case "DNO5": reportType = 2;reflect = "competeDataTime";break;
		    case "GNO5": reportType = 5;reflect = "competeDataTime";break;
		    case "CNO4": reportType = 6;reflect = "competeDataTime";break;
		    case "ONO5": reportType = 7;reflect = "competeDataTime";break;
		    case "NO6" : reportType = 0;reflect = "stratagemDataTime";break;
		    case "DNO6": reportType = 2;reflect = "stratagemDataTime";break;
		    case "GNO6": reportType = 5;reflect = "stratagemDataTime";break;
		    case "ONO6": reportType = 7;reflect = "stratagemDataTime";break;
		    case "NO7" : reportType = 0;reflect = "financeDataTime";break;
		    case "DNO7": reportType = 2;reflect = "financeDataTime";break;
		    case "GNO7": reportType = 5;reflect = "financeDataTime";break;
		    case "ONO7": reportType = 7;reflect = "financeDataTime";break;
		    case "NO8" : reportType = 0;reflect = "lawDataTime";break;
		    case "DNO8": reportType = 2;reflect = "lawDataTime";break;
		    case "GNO8": reportType = 5;reflect = "lawDataTime";break;
		    case "ONO8": reportType = 7;reflect = "lawDataTime";break;
		    case "NO9" : reportType = 0;reflect = "financingTime";break;
		    case "ENO5": reportType = 1;reflect = "financingTime";break;
		    case "DNO9": reportType = 2;reflect = "financingTime";break;
		    case "GNO9": reportType = 5;reflect = "financingTime";break;
		    case "CNO5": reportType = 6;reflect = "financingTime";break;
		    case "ONO9": reportType = 7;reflect = "financingTime";break;
		    case "ENO6": reportType = 1;reflect = "exitEvaluationTime";break;
		    case "CNO6": reportType = 6;reflect = "exitEvaluationTime";break;
		    case "PNO1": reportType = 3;reflect = "investmentProgramTime";break;
		    case "ONO1": reportType = 7;reflect = "investmentProgramTime";break;
		    case "PNO2": reportType = 3;reflect = "otherBusinessTime";break;
		    default:
			    	reflect = null;reportType = null;
			    	break;
		
		}
		map.put("reflect", reflect);
		map.put("reportType", reportType);
		return map;
	}
	
		
	    

}
