package com.galaxyinternet.test.service.progresslog;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.model.common.ProgressLog;
import com.galaxyinternet.service.ProgressLogService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/server_ctx.xml")
public class ProgressLogServiceTest {
	@Autowired
	private ProgressLogService progressLogService;
	
	@Test
	public void testAdd()
	{
		ProgressLog log = new ProgressLog();
		log.setUid(1L);
		log.setUname("徐茂栋");
		log.setDepartName("懂事长");
		log.setUserDepartid(2L);
		log.setOperationContent("测试content");
		log.setCreatedTime(new Date().getTime());
		log.setOperationType("测试");
		log.setRelatedId(3L);
		log.setRelatedName("测试name");
		log.setSopstage("测试stage");
		log.setUserRole("测试角色");
		log.setUserRoleid(4L);
		progressLogService.insert(log);
		Assert.notNull(log.getId());
	}
	@Test
	public void testSearch()
	{
		ProgressLog query = new ProgressLog();
		query.setPageNum(0);
		query.setPageSize(1);;
		List<ProgressLog> result = progressLogService.queryList(query, new PageRequest(0,1));
		Assert.notNull(result);
	}
}
