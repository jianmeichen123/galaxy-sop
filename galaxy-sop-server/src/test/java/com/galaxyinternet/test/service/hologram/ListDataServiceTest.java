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
import com.galaxyinternet.model.hologram.InformationListdata;
import com.galaxyinternet.service.hologram.InformationListdataService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/server_ctx.xml")
public class ListDataServiceTest
{
	@Autowired
	InformationListdataService service;
	@Test
	public void insertBatchTest()
	{
		InformationListdata entity = null;
		List<InformationListdata> entityList = new ArrayList<>();
		
		for(int i=1;i<=10;i++)
		{
			entity = new InformationListdata();
			entity.setProjectId(1L);
			entity.setTitleId(2L);
			entity.setCode("0");
			entity.setParentId(-1L);
			entity.setField1("1");
			entity.setField2("2");
			entity.setField3("3");
			entity.setField4("4");
			entity.setField5("5");
			entity.setField6("6");
			entity.setField7("7");
			entity.setField8("8");
			entity.setField9("9");
			entity.setField10("10");
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
		
		InformationListdata query = new InformationListdata();
		query.setProjectId(1L);
		query.setTitleIds(titleIds);
		service.delete(query);
	}
	
	
}
