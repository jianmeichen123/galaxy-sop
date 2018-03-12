package com.galaxyinternet.common.controller;

import java.util.Date;

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

import com.galaxyinternet.bo.systemMessage.SystemMessageUserBo;
import com.galaxyinternet.common.utils.WebUtils;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.systemMessage.SystemMessageUser;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.SystemMessageUserService;


@Controller
@RequestMapping("/galaxy/systemMessageUser")
public class SystemMessageUserController extends BaseControllerImpl<SystemMessageUser, SystemMessageUserBo>{
	final Logger logger = LoggerFactory.getLogger(SystemMessageUserController.class);

	@Autowired
	private SystemMessageUserService systemMessageUserService;

	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;

	@Override
	protected BaseService<SystemMessageUser> getBaseService() {
		return this.systemMessageUserService;
	}
	
	/**
	 * 新建系统消息接口
	 * 
	 * @version 2018-03-02
	 * @author chenjianmei
	 */
	@ResponseBody
	@RequestMapping(value = "/amu", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SystemMessageUser> addMessage(@RequestBody SystemMessageUser systemMessageUser, HttpServletRequest request)
	{
		ResponseData<SystemMessageUser> responseBody = new ResponseData<SystemMessageUser>();
		if (systemMessageUser == null || systemMessageUser.getMessageId() == null || "".equals(systemMessageUser.getMessageId())
				|| systemMessageUser.getMessageOs() == null || "".equals(systemMessageUser.getMessageOs()))
		{
			responseBody.setResult(new Result(Status.ERROR, "csds", "必要的参数丢失!"));
			return responseBody;
		}
		try
		{
			User user = WebUtils.getUserFromSession();
			Long userId = user != null ? user.getId() : null;
			Long now = new Date().getTime();
			systemMessageUser.setCreatedTime(now);
			systemMessageUser.setCreateId(userId);
			systemMessageUser.setUserId(userId);
			systemMessageUserService.insert(systemMessageUser);
			responseBody.setResult(new Result(Status.OK,"新增成功"));
		} catch (Exception e)
		{
			e.printStackTrace();
			logger.error("异常信息:", e.getMessage());
		}
		return responseBody;
	}
}
