package com.galaxyinternet.test.service.soptask;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

//import static org.junit.Assert.*;
import com.galaxyinternet.bo.SopTaskBo;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.service.SopTaskService;
/**
 * 任务模块测试类
 * @author lizijian
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/server_ctx.xml")
public class SopTaskServiceTest {
	
	@Autowired
	private SopTaskService sopTaskService ;
	
	/**
	 * 根据文件信息查询任务-单元测试
	 * @throws Exception
	 */
	@Test
	public void  testGetByFileInfo()throws Exception{
		SopTask  st = sopTaskService.getByFileInfo(new Long(2312393));
		if(st==null){
			Assert.isNull(st);
		}else{
			Assert.notNull(st);
		}		
	}
	
	/**
	 * 紧急任务总数-单元测试
	 * @throws Exception
	 */
	@Test
	public void testSelectTotalUrgent()throws Exception{
		SopTaskBo stb = new SopTaskBo();
		stb.setAssignUid(new Long(16));
		Long l = sopTaskService.selectTotalUrgent(stb);
		if(l==null){
			Assert.isNull(l);
		}else{
			Assert.notNull(l);
		}	
	}
	
	private SopTask getSopTaskObject(){
		SopTask st = new SopTask();
		st.setId(2312394L);
		st.setProjectId(123456849L);
		st.setTaskType("taskType:2");
		st.setTaskFlag(0);
		st.setAssignUid(13L);
		st.setTaskStatus("taskStatus:2");
		st.setUpdatedTime(1459126263354L);
		return st;
	}
	
	@SuppressWarnings("unused")
	@Test
	@Rollback(true)
	public void testUpdateTask()throws Exception{			
		Integer l = sopTaskService.updateTask(getSopTaskObject());
		if(l==null){
			Assert.isNull(l);
		}else{
			Assert.notNull(l);
			System.out.println(l);
		}	
	}
	
	/***
	 * 待办任务总数--单元测试
	 * @throws Exception
	 */
	@Test
	public void testSelectMission()throws Exception{
		SopTaskBo  stb = new SopTaskBo();
		stb.setAssignUid(16L);
		Long count = sopTaskService.selectTotalMission(stb);
		System.out.println(count);
	}

    /**
     * 修改任务状态 - 单元测试
     * @throws Exception
     */
	@Test
	@After
	@Rollback(true)
	public void testUpdateById()throws Exception{
		int r = sopTaskService.updateById(getSopTaskObject());
		System.out.println(r);
	}
	
	/***
	 *  由任务状态查询任务 - 单元测试
	 * @throws Exception
	 */
	@Test
	public void testSelectForTaskOverList()throws Exception{
		SopTaskBo query = new SopTaskBo();
		query.setProjectId(123456850L);
		query.setTaskName("上传投资意向书");
		query.setTaskType("taskType:2");
		query.setTaskFlag(1);
		query.setDepartmentId(2L);
		query.setAssignUid(16L);
		query.setTaskStatus("taskStatus:3");
		
		List<String> statusList = new ArrayList<String>();
		statusList.add("taskStatus:3");
		statusList.add("taskStatus:2");
		statusList.add("taskStatus:1");
		query.setTaskStatusList(statusList);
		
		List<SopTask> list = sopTaskService.selectForTaskOverList(query);
	    if(list!=null && list.size()>0){
	    	System.out.println(list);
	    }else{
	    	System.out.println(list);
	    }
	}
    
	/**
	 * 生成任务 -单元测试
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
   public void testInsertSopTask()throws Exception{
		SopTask st = new SopTask();
		st = getSopTaskObject();
		st.setId(98765432133L);
		st.setDepartmentId(16L);
		Long l = sopTaskService.insertsopTask(st);
		if(l==null){
			Assert.isNull(l);
		}else{
			Assert.notNull(l);
			System.out.println(l);
		}	
   }
	

}
