package com.galaxyinternet.soptask.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.galaxyinternet.bo.SopUserScheduleBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.soptask.SopUserSchedule;
import com.galaxyinternet.service.SopUserScheduleService;

@Controller
@RequestMapping("/galaxy/userSchedule")
public class SopUserScheduleController extends
		BaseControllerImpl<SopUserSchedule, SopUserScheduleBo> {
	final Logger logger = LoggerFactory
			.getLogger(SopUserScheduleController.class);

	@Autowired
	private SopUserScheduleService sopUserScheduleService;

	@Override
	protected BaseService<SopUserSchedule> getBaseService() {
		// TODO Auto-generated method stub
		return this.sopUserScheduleService;
	}

	/***
	 * 添加|修改我的日程 status 1:添加,2:修改
	 * 
	 * @param sopUserSchedule
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateSopUserSchedule/{status}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopUserSchedule> addUserSchedule(
			@RequestBody SopUserSchedule sopUserSchedule,
			@PathVariable String status) {

		ResponseData<SopUserSchedule> responseBody = new ResponseData<SopUserSchedule>();
		Result result = new Result();

		if ("1".equals(status)) {
			// 获取用户id
			sopUserSchedule.setUserId(0);
			sopUserScheduleService.insert(sopUserSchedule);
			result.setMessage("添加日程成功!");
		} else {
			sopUserScheduleService.updateById(sopUserSchedule);
			result.setMessage("修改日程成功!");
		}

		responseBody.setResult(result);
		return responseBody;
	}

	/***
	 * 获取我的日程前三条信息|
	 * type: 1:前三条数据?更多
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectSopUserSchedule/{type}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public ResponseData<SopUserSchedule> selectUserScheduleByTime(@PathVariable Integer type) {

		ResponseData<SopUserSchedule> responseBody = new ResponseData<SopUserSchedule>();
		Result result = new Result();
		Long currentTime = System.currentTimeMillis();
		List<SopUserScheduleBo> list = sopUserScheduleService
				.selectSopUserScheduleByTime(currentTime,type);
		result.setMessage(list);
		responseBody.setResult(result);

		return responseBody;
	}

}
