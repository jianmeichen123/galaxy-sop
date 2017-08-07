package com.galaxyinternet.test.service.project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.galaxyinternet.common.dictEnum.DictEnum.projectProgress;
import com.galaxyinternet.service.ProjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/server_ctx.xml")
public class ProjectProgressTest
{
	@Autowired
	private ProjectService service;
	
	@Test
	public void test()
	{
		Long id = 483l;
		String next = projectProgress.内部评审.getCode();
		service.updateProgress(id, next);
	}
}
