package com.galaxyinternet.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.systemMessage.SystemMessageBo;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.systemMessage.SystemMessage;
import com.galaxyinternet.service.SystemMessageService;


@Controller
@RequestMapping("/galaxy/systemMessage")
public class SystemMessageController extends BaseControllerImpl<SystemMessage, SystemMessageBo>{
	final Logger logger = LoggerFactory.getLogger(SystemMessageController.class);

	@Autowired
	private SystemMessageService systemMessageService;
	

	@Override
	protected BaseService<SystemMessage> getBaseService() {
		return this.systemMessageService;
	}
	
	/**
	 * 跳转到系统消息配置页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/tabSystemMessage", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String tabSystemMessage(HttpServletRequest request) {
		return "systemNotice/notice";
	}
	/**
	 * @date 2018-3-1
	 * @author chenjianmei
	 * @param request
	 * @param systemMessage
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchSystemMessage", method = RequestMethod.POST)
	public ResponseData<SystemMessage> searchSystemMessage(HttpServletRequest request, 
			@RequestBody SystemMessage systemMessage)
	   {
		ResponseData<SystemMessage> data = new ResponseData<SystemMessage>();
		SystemMessage query=new SystemMessage();
		query.setSendTime(systemMessage.getSendTime());
		query.setSendStatus(systemMessage.getSendStatus());
		Direction direction = Direction.DESC;
		String property = "updated_time";
		try {
			Page<SystemMessage> queryPageList = systemMessageService.queryPageList(systemMessage, new PageRequest(systemMessage.getPageNum(),
					systemMessage.getPageSize(), direction, property));
			data.setPageList(queryPageList);
			data.setResult(new Result(Status.OK, ""));
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询系统消息失败", e);
			data.getResult().addError("查询系统消息失败");
		}
		return data;
	}
	
	/**
	 * 新建系统消息接口
	 * 
	 * @version 2018-03-02
	 * @author chenjianmei
	 */
	@ResponseBody
	@RequestMapping(value = "/ap", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SystemMessage> addProject(@RequestBody SystemMessage systemMessage, HttpServletRequest request)
	{
		ResponseData<SystemMessage> responseBody = new ResponseData<SystemMessage>();
		if (systemMessage == null || systemMessage.getMessageContent() == null || "".equals(systemMessage.getMessageContent().trim())
				|| systemMessage.getOsType()== null || "".equals(systemMessage.getOsType().trim())
				|| systemMessage.getSendTime() == null )
		{
			responseBody.setResult(new Result(Status.ERROR, "csds", "必要的参数丢失!"));
			return responseBody;
		}
		try
		{
			Long insert = systemMessageService.insert(systemMessage);
			if(insert>0){
				responseBody.setResult(new Result(Status.OK,"新增成功"));
			}
		} catch (Exception e)
		{
			logger.error("异常信息:", e.getMessage());
		}
		return responseBody;
	}
	/**
	 * 删除消息
	 * 
	 * @param id
	 * @version 2018-03-02
	 * @author chenjianmei
	 *           消息id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteMessage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SystemMessage> deleteMessage(@RequestBody SystemMessage param, HttpServletRequest request)
	{
		ResponseData<SystemMessage> responseBody = new ResponseData<SystemMessage>();

		try
		{
			SystemMessage sysMessage = new SystemMessage();
			sysMessage = systemMessageService.queryById(param.getId());
			if (sysMessage == null)
			{
				responseBody.setResult(new Result(Status.ERROR, null, "删除的消息不存在"));
				return responseBody;
			}
			sysMessage.setIsDel((byte)1);
			int id = systemMessageService.updateById(sysMessage);
			if (id != 1)
			{
				responseBody.setResult(new Result(Status.ERROR, null, "删除消息失败"));
				return responseBody;
			}
			responseBody.setResult(new Result(Status.OK, ""));
			} catch (Exception e)
		{
			responseBody.setResult(new Result(Status.ERROR, null, "delete message faild"));
			logger.error("delete message faild ", e);
		}
		return responseBody;
	}
}
