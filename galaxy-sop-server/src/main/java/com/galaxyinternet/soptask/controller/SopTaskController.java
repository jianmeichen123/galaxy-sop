package com.galaxyinternet.soptask.controller;

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

import com.galaxyinternet.bo.SopTaskBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.exception.PlatformException;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.StringEx;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.service.SopTaskService;

@Controller
@RequestMapping("/galaxy/soptask")
public class SopTaskController extends BaseControllerImpl<SopTask, SopTaskBo> {
	final Logger logger = LoggerFactory.getLogger(SopTaskController.class);
	@Autowired
	private SopTaskService sopTaskService;
	// @Autowiredo
	// UserRepository userRepository;

	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;

	@Override
	protected BaseService<SopTask> getBaseService() {
		return this.sopTaskService;
	}

	/**
	 * @category 根据角色获取当前登录人所属角色的所有任务
	 * @author chenjianmei
	 * @serialData 2016-02-26
	 * @param pageable
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/taskListByRole", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopTask> taskListByRole(PageRequest pageable, @RequestBody SopTask sopTask) {
		ResponseData<SopTask> responseBody = new ResponseData<SopTask>();
		// 获取当前登录人，并且查询该登录人所属角色
		sopTask.setTaskDestination("1");
		Result result = new Result();
		try {
			Page<SopTaskBo> list = sopTaskService.tasklist(pageable, sopTask);
			responseBody.setPageVoList((Page<SopTaskBo>) list);
			result.setStatus(Status.OK);
		} catch (PlatformException e) {
			result.addError(e.getMessage());
		} catch (Exception e) {
			result.addError("任务列表查询失败");
			logger.error("任务列表查询失败", e);
		}
		responseBody.setResult(result);
		return responseBody;
	}

	/**
	 * @category 根据任务id修改任务状态 1，待认领 2，待处理 3，已完成
	 * @author chenjianmei
	 * @serialData 2016-02-26
	 * @param pageable
	 * @return @PathVariable("taskId") String taskId
	 */
	@ResponseBody
	@RequestMapping(value = "/insertTask", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopTask> insertTask(@RequestBody SopTask entity, HttpServletRequest request) {
		ResponseData<SopTask> responseBody = new ResponseData<SopTask>();
		Result result = new Result();
		Object ob = request.getSession().getAttribute("sessionUser");
		if (ob == null) {
			responseBody.setResult(new Result(Status.ERROR, "no login status."));
			return responseBody;
		}
		try {
			Long id = sopTaskService.insertsopTask(entity);
			responseBody.setId(id);
			result.setStatus(Status.OK);
		} catch (PlatformException e) {
			result.addError(e.getMessage());
		} catch (Exception e) {
			result.addError("生成任务失败");
			logger.error("生成任务失败", e);
		}
		responseBody.setResult(result);
		return responseBody;

	}

	/**
	 * @category 根据任务id修改任务状态 1，待认领 2，待处理 3，已完成
	 * @author chenjianmei
	 * @serialData 2016-02-26
	 * @param pageable
	 * @return
	 *@PathVariable("taskId") String taskId
	 */
	@ResponseBody
	@RequestMapping(value = "/updateTaskStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopTask> updateTaskStatus( @RequestBody SopTask entity) {
		ResponseData<SopTask> responseBody = new ResponseData<SopTask>();
		Result result = new Result();
		try {
		    sopTaskService.updateById(entity);
		     result.setStatus(Status.OK);
		} catch (PlatformException e) {
			result.addError(e.getMessage());
		} catch (Exception e) {
			result.addError("修改任务状态失败");
			logger.error("修改任务状态失败", e);
		}
		responseBody.setResult(result);
		return responseBody;
	}
}
