package com.galaxyinternet.test.service.project;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.enums.DictEnum.RecordType;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.service.MeetingRecordService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/server_ctx.xml")
public class MeetingRecordServiceTest {
	
	@Autowired
	private MeetingRecordService meetingRecordService;

	@Test
	public void testProjectSchedule()throws Exception{
//		Project mrobj = getMeetingRecord();
	//	meetingRecordService.projectSchedule(mrobj);
	}
	
//	private MeetingRecord getMeetingRecord(){
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
//	}
	@Test
	public void testInsert()
	{
		MeetingRecord entity = new MeetingRecord();
		entity.setRecordType(RecordType.IDEAS.getType());
		entity.setProjectId(1L);
		entity.setMeetingDate(new Date());
		entity.setMeetingNotes("Note");
		entity.setMeetingType("ideaMeetingType:1");
		entity.setMeetingResult(DictEnum.meetingResult.通过.getCode());
		entity.setCreatedTime(new Date().getTime());
		entity.setParticipant("参会人1,参会人2");
		meetingRecordService.insert(entity);
		Assert.notNull(entity.getId());
	}
	@Test
	public void testQuery()
	{
		try {
			MeetingRecordBo query = new MeetingRecordBo();
			query.setRecordType(RecordType.IDEAS.getType());
			meetingRecordService.queryList(query);
			meetingRecordService.queryMeetPageList(query, new PageRequest(0,10));
			Assert.isTrue(true);
		} catch (Exception e) {
			Assert.isTrue(false,e.getMessage());
		}
	}
	

}
