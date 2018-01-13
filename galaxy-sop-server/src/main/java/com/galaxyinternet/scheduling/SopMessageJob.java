package com.galaxyinternet.scheduling;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.dao.Message.SopMessageDao;
import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.model.message.SopMessage;
import com.galaxyinternet.operationMessage.component.MessageRequest;

@Component(value="sopMessageJob")
public class SopMessageJob extends BaseGalaxyTask
{
	private static final Logger logger = LoggerFactory.getLogger(SopMessageJob.class);
	@Autowired
	private SopMessageDao messageDao;
	@Autowired
	private MessageRequest rest;
	@Override
	@Transactional
	public void executeInteral() throws BusinessException
	{
		SopMessage query = new SopMessage();
		List<SopMessage> list = messageDao.selectList(query);
		if(list == null || list.size() == 0)
		{
			return;
		}
		for(SopMessage message : list)
		{
			process(message);
			updateRetryTimes(message);
		}
	}
	/**
	 * 更新重试次数
	 * @param message
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW )
	public void updateRetryTimes(SopMessage message)
	{
		SopMessage entity = new SopMessage();
		entity.setId(message.getId());
		entity.setIsPublished(null); //不更新此字段
		entity.setRetryTimes(message.getRetryTimes()+1);
		messageDao.updateById(entity);
	}
	
	/**
	 * 发送消息
	 * @param message
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW )
	public void process(SopMessage message)
	{
		try
		{
			boolean result = rest.send(message.getMessageType(), message.getRecordId(), message.getCreatedId());
			if(!result)
			{
				return;
			}
			SopMessage entity = new SopMessage();
			entity.setId(message.getId());
			entity.setIsPublished(1);
			entity.setPublishedTime(System.currentTimeMillis());
			messageDao.updateById(entity);
		} catch (Exception e)
		{
			logger.error(String.format("ID:%s, Message: %s",message.getId(), e.getMessage()));
		}
	}

}
