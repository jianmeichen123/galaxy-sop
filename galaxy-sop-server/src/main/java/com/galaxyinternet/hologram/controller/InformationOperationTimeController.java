package com.galaxyinternet.hologram.controller;

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

import com.galaxyinternet.common.controller.BaseControllerImpl;
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
		if(informationOperationTime == null || informationOperationTime.getProjectId() == null){
			return new Result(Status.ERROR, "参数丢失!");
		}
		try {
			informationOperationTimeService.updateInformationTime(informationOperationTime);
			return new Result(Status.OK, "");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("更新操作时间失败!");
			// TODO: handle exception
			return new Result(Status.ERROR, "系统出现异常");
		}
		
	}
	
	
	
	

}
