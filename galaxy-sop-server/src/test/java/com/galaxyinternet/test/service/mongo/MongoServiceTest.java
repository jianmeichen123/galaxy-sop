package com.galaxyinternet.test.service.mongo;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.galaxyinternet.framework.core.exception.MongoDBException;
import com.galaxyinternet.test.service.mongo.impl.ProjectMongoDBDaoImpl;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/server_ctx.xml")
public class MongoServiceTest {
	
	@Autowired
	private ProjectMongoDBDaoImpl projectMongoDBDaoImpl;

	@SuppressWarnings("unchecked")
	@Test
	public void test(){
	
			/*ProjectMongoDB pd = new ProjectMongoDB();
			pd.setId(1342L);
			pd.setDirection("3546576");
			pd.setProjectName("测试");
			projectMongoDBDaoImpl.save(pd);*/
			//List<ProjectMongoDB>  list = projectMongoDBDaoImpl.find(pd);
			//System.out.println("999999999999999999999:"+list);
		
		System.out.println(projectMongoDBDaoImpl);
		
		
	}
}
