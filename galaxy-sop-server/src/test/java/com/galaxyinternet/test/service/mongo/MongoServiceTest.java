package com.galaxyinternet.test.service.mongo;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.galaxyinternet.framework.core.exception.MongoDBException;
import com.galaxyinternet.framework.core.utils.UUIDUtils;
import com.galaxyinternet.model.project.ProjectShares;
import com.galaxyinternet.mongodb.model.Project;
import com.galaxyinternet.mongodb.service.ProjectService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/server_ctx.xml")
public class MongoServiceTest {
	
	private static final Logger loggger = LoggerFactory.getLogger(MongoServiceTest.class);
	
	@Autowired
	private ProjectService projectService;
	

	@Test
	public void test(){
		try {
			Project project = new Project();
			project.setPn("aaaaaaaaa");
			project.setStatus(1);
			project.setUid(100L);
			project.setUuid(UUIDUtils.create().toString());
			/*List<ProjectShares> list = new ArrayList<ProjectShares>(2);
			ProjectShares ps = new ProjectShares();
			ps.setSharesType("ssss");
			list.add(ps);
			project.setPsc(list);*/
			projectService.save(project);
		} catch (MongoDBException e) {
			loggger.error(e.toString());
		}
	}
}
