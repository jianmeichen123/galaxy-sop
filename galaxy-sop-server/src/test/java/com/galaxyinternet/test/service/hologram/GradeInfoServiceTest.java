package com.galaxyinternet.test.service.hologram;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.galaxyinternet.model.hologram.GradeInfo;
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
		Long id = 1001L;
		GradeInfo info = service.queryById(id);
		Assert.notNull(info);
	}
}
