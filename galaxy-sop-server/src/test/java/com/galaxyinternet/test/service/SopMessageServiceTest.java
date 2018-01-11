package com.galaxyinternet.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.galaxyinternet.common.annotation.RecordType;
import com.galaxyinternet.common.enums.DictEnum.MessageType;
import com.galaxyinternet.model.message.SopMessage;
import com.galaxyinternet.service.SopMessageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/server_ctx.xml")
public class SopMessageServiceTest
{
	@Autowired
	private SopMessageService service;
	
	@Test
	public void testInsert()
	{
		SopMessage entity = new SopMessage();
		entity.setMessageType(MessageType.TRANSFER_PROJECT.getCode());
		entity.setRecordType(RecordType.PROJECT.getType());
		entity.setRecordId(111L);
		entity.setPublishedTime(System.currentTimeMillis());
		entity.setCreatedId(3L);
		entity.setCreatedTime(System.currentTimeMillis());
		entity.setUpdatedId(4L);
		entity.setUpdatedTime(System.currentTimeMillis());
		service.insert(entity);
	}
	@Test
	public void testUpdateById()
	{
		SopMessage entity = new SopMessage();
		entity.setMessageType(MessageType.TRANSFER_PROJECT.getCode());
		entity.setRecordType(RecordType.TASK.getType());
		entity.setRecordId(222L);
		entity.setIsPublished(1);
		entity.setRetryTimes(1);
		entity.setPublishedTime(System.currentTimeMillis());
		entity.setCreatedId(3L);
		entity.setCreatedTime(System.currentTimeMillis());
		entity.setUpdatedId(4L);
		entity.setUpdatedTime(System.currentTimeMillis());
		entity.setId(1l);
		service.updateById(entity);
	}
}
