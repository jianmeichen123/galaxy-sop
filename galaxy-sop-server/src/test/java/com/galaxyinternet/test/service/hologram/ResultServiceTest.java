package com.galaxyinternet.test.service.hologram;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.galaxyinternet.model.hologram.InformationResult;
import com.galaxyinternet.service.hologram.InformationResultService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/server_ctx.xml")
public class ResultServiceTest
{
	@Autowired
	InformationResultService service;
	@Test
	public void  insertBatchTest()
	{
		List<InformationResult> list = new ArrayList<>();
		InformationResult entity = null;
		for(int i=1;i<=10;i++)
		{
			entity = new InformationResult();
			entity.setProjectId("1");
			entity.setTitleId("2");
			entity.setContentChoose("3");
			entity.setContentDescribe("33333");
			list.add(entity);
		}
		service.insertInBatch(list);
	}
	@Test
	public void delete()
	{
		Set<String> titleIds = new HashSet<>();
		titleIds.add("10");
		titleIds.add("11");
		titleIds.add("12");
		
		InformationResult query = new InformationResult();
		query.setProjectId("1");
		query.setTitleIds(titleIds);
		service.delete(query);
	}
}
