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

import com.galaxyinternet.model.hologram.InformationFixedTable;
import com.galaxyinternet.model.hologram.InformationResult;
import com.galaxyinternet.service.hologram.InformationFixedTableService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/server_ctx.xml")
public class FixedTableServiceTest
{
	@Autowired
	InformationFixedTableService service;
	@Test
	public void insertBatchTest()
	{
		InformationFixedTable entity = null;
		List<InformationFixedTable> entityList = new ArrayList<>();
		
		for(int i=1;i<=10;i++)
		{
			entity = new InformationFixedTable();
			entity.setProjectId("1");
			entity.setTitleId("2");
			entity.setRowNo("1");
			entity.setColNo(i+"");
			entity.setContent("content"+i);
			entity.setType("3");
			entityList.add(entity);
		}
		service.insertInBatch(entityList);
	}
	@Test
	public void delTest()
	{
		Set<String> titleIds = new HashSet<>();
		titleIds.add("1");
		titleIds.add("2");
		titleIds.add("3");
		
		InformationFixedTable query = new InformationFixedTable();
		query.setProjectId("1");
		query.setTitleIds(titleIds);
		service.delete(query);
	}
	
	
}
