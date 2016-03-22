package com.galaxyinternet.test.service.project;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.galaxyinternet.service.ProjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/server_ctx.xml")
public class ProjectServiceTest 
{
	@Autowired
	ProjectService projectService;
	
	@Test
	@Rollback(true)
	public void testGetSummary() throws Exception
	{
		Map<String,Object> summary = projectService.getSummary(1L);
		Assert.notEmpty(summary);
	}
}
