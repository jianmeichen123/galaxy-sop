package com.galaxyinternet.test.service.soptask;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.galaxyinternet.model.soptask.SopUserSchedule;
import com.galaxyinternet.service.SopUserScheduleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/server_ctx.xml")
public class sopUserScheduleServiceTest {

	@Resource
	private SopUserScheduleService sopUserScheduleService;
	
	@Test
	public void test(){
		List<SopUserSchedule> list=sopUserScheduleService.queryAll();
		System.out.println(list);
	}
}
