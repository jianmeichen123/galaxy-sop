package com.galaxyinternet.test.service.project;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.galaxyinternet.bo.project.MeetingSchedulingBo;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.service.MeetingSchedulingService;

/**
 * 排期池测试类
 * @author lizijian
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/server_ctx.xml")
public class MeetingSchedulingTest {
	@Autowired
	private MeetingSchedulingService mss ;
//int updateBySelective(MeetingScheduling ms);
    @After
	@Test
	@Rollback(true)
	public void testUpdateBySelective()throws Exception{		
		MeetingScheduling msobj = getMeetingSchedulingObject();	
		Integer r  = mss.updateByIdSelective(msobj);
		Assert.notNull(r);
	}
    @After
	@Test
	@Rollback(true)
	public void testupdateCountBySelective()throws Exception{
		
		MeetingScheduling ms = getMeetingSchedulingObject();
		Integer r = mss.updateCountBySelective(ms);
		Assert.notNull(r);
	}
	
    /**
     * 排期top5列表 --单元测试类
     * @throws Exception
     */
	@Test
	public void testSelectTop5ProjectMeetingByType()throws Exception{		
	
		List<MeetingSchedulingBo> list =  mss.selectTop5ProjectMeetingByType("meetingType:2");
		if(list!=null && list.size()>0){
			Assert.notEmpty(list);
		}
		
	}

	/**
	 * 排期 all --单元测试
	 * @throws Exception
	 */
	@Test
	public void testSelectProjectMeetingByType()throws Exception{		
	
		List<MeetingSchedulingBo> list =  mss.selectProjectMeetingByType("meetingType:3");
		if(list!=null && list.size()>0){
			Assert.notEmpty(list);
		}
		
	}
	
	
	
	private MeetingScheduling getMeetingSchedulingObject()throws Exception{					
		MeetingScheduling msobj = new MeetingScheduling();
		msobj.setProjectId(123456867L);
		msobj.setMeetingType("meetingType:2");
		msobj.setMeetingCount(0);
		msobj.setMeetingDate(new Date());
		msobj.setStatus("meetingResult:1");
		msobj.setRemark("");
		msobj.setUpdatedTime(201603281552L);
		return msobj;
	}

}
