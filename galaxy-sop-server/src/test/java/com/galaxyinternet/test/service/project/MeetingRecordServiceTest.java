package com.galaxyinternet.test.service.project;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.galaxyinternet.service.MeetingRecordService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/server_ctx.xml")
public class MeetingRecordServiceTest {
	
	@Autowired
	private MeetingRecordService meetingRecordService;

}
