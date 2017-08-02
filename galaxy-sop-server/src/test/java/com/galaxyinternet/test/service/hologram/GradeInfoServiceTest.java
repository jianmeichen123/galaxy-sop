package com.galaxyinternet.test.service.hologram;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.galaxyinternet.model.hologram.ItemParam;
import com.galaxyinternet.model.hologram.ReportParam;
import com.galaxyinternet.model.hologram.ScoreInfo;
import com.galaxyinternet.service.hologram.ScoreInfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/server_ctx.xml")
public class GradeInfoServiceTest
{
	@Autowired
	ScoreInfoService service;
	
	@Test
	public void testGet()
	{
		ScoreInfo query = new ScoreInfo();
		query.setReportType(1);
		List<ScoreInfo> list = service.queryList(query);
		Assert.notEmpty(list);
	}
	@Test
	public void calculate1()
	{
		ReportParam param = new ReportParam();
		param.setRelateId(1001l);
		param.setProjectId(555l);
		List<ItemParam> items =new ArrayList<>();
		
		ItemParam item = new ItemParam();
		item.setRelatedId(1004l);
		item.setScore(BigDecimal.valueOf(20l));
		items.add(item);
		
		item = new ItemParam();
		item.setRelatedId(1008l);
		item.setScore(BigDecimal.valueOf(5l));
		items.add(item);
		
		String[] values = {"1203"};
		item = new ItemParam();
		item.setRelatedId(1014l);
		item.setValues(values);
		items.add(item);
		
		param.setItems(items);
		
		System.out.println(service.calculateSingleReport(param));
	}
	@Test
	public void get() throws Exception
	{
		Long[] ids = {0L};
		List<Long> relateIds = Arrays.asList(ids);
		Map<Long,BigDecimal> scores = service.calculateMutipleReport(relateIds, 555l);
		System.out.println(scores);
	}
}
