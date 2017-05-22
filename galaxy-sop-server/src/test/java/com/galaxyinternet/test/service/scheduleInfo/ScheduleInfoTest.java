package com.galaxyinternet.test.service.scheduleInfo;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.galaxyinternet.export_schedule.model.ScheduleInfo;
import com.galaxyinternet.export_schedule.service.ScheduleInfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/server_ctx.xml")
public class ScheduleInfoTest {
	@Autowired
	ScheduleInfoService service;
	
	@Test
	public void testGetTendency()
	{
		ScheduleInfo info = new ScheduleInfo();
		info.setProperty("start_time");
		info.setPeriodType(Byte.valueOf("1"));
		
		List<Map<String,Object>> data = service.selectTendency(info);
		System.out.println(data);
		
	}
}
