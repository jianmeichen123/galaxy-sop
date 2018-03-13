package com.galaxyinternet.common.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.galaxyinternet.common.utils.WebUtils;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.systemMessage.SystemMessage;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.platform.constant.PlatformConst;
import com.galaxyinternet.service.SystemMessageService;


@Controller
@RequestMapping("/galaxy/systemMessage")
public class SystemMessageController extends BaseControllerImpl<SystemMessage, SystemMessageBo>{
	final Logger logger = LoggerFactory.getLogger(SystemMessageController.class);

	@Autowired
	private SystemMessageService systemMessageService;

	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;

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
		Direction direction = Direction.DESC;
		String property = "update_time";
		try {
			systemMessage.setIsDel(0);
			Page<SystemMessage> queryPageList = systemMessageService.queryPageList(systemMessage, new PageRequest(systemMessage.getPageNum(),
					systemMessage.getPageSize(), direction, property));
			List<SystemMessage> list=new ArrayList<SystemMessage>();
			list=queryPageList.getContent();
			for(int i=0;i<list.size();i++){
				SystemMessage m=list.get(i);
				Object realname = cache.hget(PlatformConst.CACHE_PREFIX_USER+m.getCreateId(), "realName");
				if(null!=realname){
					  m.setUserStr((String)realname);	
				}
			}
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
	@RequestMapping(value = "/am", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SystemMessage> addMessage(@RequestBody SystemMessage systemMessage, HttpServletRequest request)
	{
		ResponseData<SystemMessage> responseBody = new ResponseData<SystemMessage>();
		if (systemMessage == null || systemMessage.getMessageContent() == null || "".equals(systemMessage.getMessageContent().trim())
				|| systemMessage.getOsType()== null ||"".equals(systemMessage.getOsType().trim()))
		{
			responseBody.setResult(new Result(Status.ERROR, "csds", "必要的参数丢失!"));
			return responseBody;
		}
		try
		{
			//systemMessage.setSendStatus("messageStatus:1");
			User user = WebUtils.getUserFromSession();
			Long userId = user != null ? user.getId() : null;
			Long now = new Date().getTime();
			systemMessage.setCreatedTime(now);
			systemMessage.setCreateId(userId);
			 systemMessageService.insert(systemMessage);
			responseBody.setResult(new Result(Status.OK,"新增成功"));
		} catch (Exception e)
		{
			e.printStackTrace();
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
	@RequestMapping(value = "/updateMessage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SystemMessage> updateMessage(@RequestBody SystemMessage param, HttpServletRequest request)
	{
		ResponseData<SystemMessage> responseBody = new ResponseData<SystemMessage>();

		try
		{
			SystemMessage sysMessage = new SystemMessage();
			sysMessage = systemMessageService.queryById(param.getId());
			if (sysMessage == null)
			{
				responseBody.setResult(new Result(Status.ERROR, null, "编辑的消息不存在"));
				return responseBody;
			}
			int id = systemMessageService.updateById(param);
			if (id != 1)
			{
				responseBody.setResult(new Result(Status.ERROR, null, "编辑消息失败"));
				return responseBody;
			}
			responseBody.setResult(new Result(Status.OK, ""));
			} catch (Exception e)
		{
				e.printStackTrace();
			responseBody.setResult(new Result(Status.ERROR, null, "edit message faild"));
			logger.error("edit message faild ", e);
		}
		return responseBody;
	}
	/**
	 * 根绝id查询接口
	 * 
	 * @version 2018-03-12
	 * @author chenjianmei
	 */
	@ResponseBody
	@RequestMapping(value = "/sml", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SystemMessage> sml(@RequestBody SystemMessage systemMessage, HttpServletRequest request)
	{
		ResponseData<SystemMessage> responseBody = new ResponseData<SystemMessage>();
		/*if (systemMessage == null || systemMessage.getId() == null || "".equals(systemMessage.getId()))
		{
			responseBody.setResult(new Result(Status.ERROR, "csds", "必要的参数丢失!"));
			return responseBody;
		}*/
		try
		{
			SystemMessage query=new SystemMessage();
			query.setId(systemMessage.getId());
			query.setIsDel(0);
			query.setOsType(systemMessage.getOsType());
			query.setSendStatus(systemMessage.getSendStatus());
			query.setEndTime(systemMessage.getEndTime());
			 SystemMessage queryOne = systemMessageService.queryOne(query);
			 responseBody.setEntity(queryOne);
			responseBody.setResult(new Result(Status.OK,"查询成功"));
		} catch (Exception e)
		{
			e.printStackTrace();
			logger.error("异常信息:", e.getMessage());
		}
		return responseBody;
	}
}
