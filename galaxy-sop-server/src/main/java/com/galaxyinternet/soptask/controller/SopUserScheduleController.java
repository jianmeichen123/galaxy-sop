package com.galaxyinternet.soptask.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.galaxyinternet.bo.SheduleCommon;
import com.galaxyinternet.bo.SopUserScheduleBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.exception.PlatformException;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.soptask.SopUserSchedule;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.SopUserScheduleService;

@Controller
@RequestMapping("/galaxy/sopUserSchedule")
public class SopUserScheduleController extends
		BaseControllerImpl<SopUserSchedule, SopUserScheduleBo> {
	final Logger logger = LoggerFactory
			.getLogger(SopUserScheduleController.class);

	@Autowired
	private SopUserScheduleService sopUserScheduleService;

	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;

	@Override
	protected BaseService<SopUserSchedule> getBaseService() {
		// TODO Auto-generated method stub
		return this.sopUserScheduleService;
	}
	/***
	 * 添加|修改我的日程 status 1:添加,2:修改
	 * 
	 * @param sopUserSchedule
	 * @return*/
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateSopUserSchedule", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopUserScheduleBo> addProjectPerson(@RequestBody SopUserScheduleBo sopUserSchedule, HttpServletRequest request) {
		ResponseData<SopUserScheduleBo> responseBody = new ResponseData<SopUserScheduleBo>();
		User user = (User) getUserFromSession(request);
		sopUserSchedule.setUserId(user.getId());
		String date=sopUserSchedule.getItemDateStr()+" 00:00:00";
		try {
			if(sopUserSchedule.getId() == null){
				sopUserSchedule.setItemDate(Timestamp.valueOf(date));
				sopUserScheduleService.insert(sopUserSchedule);
				responseBody.setResult(new Result(Status.OK,"添加日程成功!"));
			}else{
				sopUserSchedule.setItemDate(Timestamp.valueOf(date));
				sopUserScheduleService.updateById(sopUserSchedule);
				responseBody.setResult(new Result(Status.OK,"修改日程成功!"));
			}
		} catch (Exception e) {
			logger.error("操作日程失败!",e);
			responseBody.setResult(new Result(Status.ERROR,"操作日程失败!"));
		}
		return responseBody;
	}
	/***
	 * 获取我的日程前三条信息| type: 1:前三条数据?更多
	 * 
	 * @return
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequestMapping(value = "/selectSopUserSchedule/{type}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public ResponseData<SopUserScheduleBo> selectUserScheduleByTime(
			@PathVariable Integer type, HttpServletRequest request) throws ParseException {

		ResponseData<SopUserScheduleBo> responseBody = new ResponseData<SopUserScheduleBo>();
		User user = (User) getUserFromSession(request);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		Long currentTime = sdf.parse(date).getTime();
		List<SopUserScheduleBo> list = sopUserScheduleService
				.selectSopUserScheduleByTime(user.getId(), currentTime, type);
		Page<SopUserScheduleBo> page = new Page<SopUserScheduleBo>(list, null,
				null);
		responseBody.setPageList(page);
		
		return responseBody;
	}
	
	
	/***
	 * 获取日程列表
	 */
	@RequestMapping(value = "/scheduleList",method = RequestMethod.GET)
	public String scheduleList(HttpServletRequest request) {
		User user = (User) getUserFromSession(request);
		List<SheduleCommon> sop=sopUserScheduleService.scheduleListByDate(user.getId());
		request.setAttribute("sheduleList", sop);
		return "shedule/sheduleList";
	}
	
	
	/***
	 * 获取日程信息
	 */
	@ResponseBody
	@RequestMapping(value = "/getSchedule/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public ResponseData<SopUserScheduleBo> getSchedule(
			@PathVariable Long id, HttpServletRequest request) throws ParseException {

		ResponseData<SopUserScheduleBo> responseBody = new ResponseData<SopUserScheduleBo>();
		try{
			SopUserScheduleBo sop= (SopUserScheduleBo) sopUserScheduleService.queryById(id);
			String str[] = sop.getItemDate().toString().split(" ");
			sop.setTimeTask(str[0]);
			responseBody.setEntity(sop);
			responseBody.setResult(new Result(Status.OK,"成功!"));
		}catch(Exception e){
			responseBody.setResult(new Result(Status.ERROR,"失败!"));
		}
		return responseBody;
	}

	/**
	 * 供app端的接口
	 * 获取日程代分页模糊查询
	 * 
	 * 当前用户的 
	 */
	@ResponseBody
	@RequestMapping(value = "/queryscheduleList",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopUserSchedule> queryscheduleList(HttpServletRequest request,@RequestBody SopUserScheduleBo sopUserScheduleBo) {
		ResponseData<SopUserSchedule> responseBody = new ResponseData<SopUserSchedule>();
		User user = (User) getUserFromSession(request);		
		sopUserScheduleBo.setUserId(user.getId());
		try {
			Page<SopUserSchedule> pageList = sopUserScheduleService.scheduleListByName(sopUserScheduleBo, new PageRequest(sopUserScheduleBo.getPageNum(), sopUserScheduleBo.getPageSize()));
			responseBody.setPageList(pageList);
			return responseBody;
		} catch (PlatformException e) {
			responseBody.setResult(new Result(Status.ERROR, "queryscheduleList faild"));
			if (logger.isErrorEnabled()) {
				logger.error("queryscheduleList ", e);
			}
		}
		return responseBody;
		
		
	}
	
	

}
