package com.galaxyinternet.test.service.project;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.service.MeetingRecordService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/server_ctx.xml")
public class MeetingRecordServiceTest {
	
	@Autowired
	private MeetingRecordService meetingRecordService;

	@Test
	public void testProjectSchedule()throws Exception{
//		Project mrobj = getMeetingRecord();
		meetingRecordService.projectSchedule(mrobj);
	}
	
	private MeetingRecord getMeetingRecord(){
//		Project mr = new Project();
//		mr.setId(3948683620254368L);
//		mr(123456867L);
//		//mr.setFileId(fileId);
//		mr.setMeetingDate(new Date());
//		mr.setMeetingType("meetingType:2");
//		mr.setMeetingResult("meetingResult:1");
//		//mr.setMeetingNotes(meetingNotes);
//		mr.setCreatedTime(1459151188338L);
//		return mr;
	}
	

}
