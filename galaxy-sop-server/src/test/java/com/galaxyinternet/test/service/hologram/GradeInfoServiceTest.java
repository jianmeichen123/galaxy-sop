package com.galaxyinternet.test.service.hologram;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.galaxyinternet.model.hologram.GradeInfo;
import com.galaxyinternet.model.hologram.ItemParam;
import com.galaxyinternet.model.hologram.SingleReportParam;
import com.galaxyinternet.service.hologram.GradeInfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/server_ctx.xml")
public class GradeInfoServiceTest
{
	@Autowired
	GradeInfoService service;
	
	@Test
	public void testGet()
	{
		GradeInfo query = new GradeInfo();
		query.setReportType(1);
		List<GradeInfo> list = service.queryList(query);
		Assert.notEmpty(list);
	}
	@Test
	public void calculate1()
	{
		SingleReportParam param = new SingleReportParam();
		param.setRelateId(1001l);
		List<ItemParam> items =new ArrayList<>();
		
		ItemParam item = new ItemParam();
		item.setRelatedId(1004l);
		item.setScore(20);
		items.add(item);
		
		item = new ItemParam();
		item.setRelatedId(1008l);
		item.setScore(5);
		items.add(item);
		
		String[] values = {"1203"};
		item = new ItemParam();
		item.setRelatedId(1014l);
		item.setValues(values);
		items.add(item);
		
		param.setItems(items);
		
		System.out.println(service.calculateSingleReport(param));
	}
}
