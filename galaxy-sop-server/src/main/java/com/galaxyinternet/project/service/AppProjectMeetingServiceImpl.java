package com.galaxyinternet.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.dao.project.MeetingRecordDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.idea.Idea;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.AppSopFile;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.service.AppProjectMeetingService;

@Service("com.galaxyinternet.service.AppProjectMeetingService")
public class AppProjectMeetingServiceImpl extends BaseServiceImpl<MeetingRecord> implements AppProjectMeetingService{
	
	@Autowired
	private MeetingRecordDao meetingRecordDao;	

	@Autowired
	private SopFileDao sopFileDao;
	
	@Transactional
	public void addToAudioFileByMeeting(Project p1, Long careerLine ,Long fileUid ,AppSopFile sopFile)throws Exception{
		long nowTime = System.currentTimeMillis();
		SopFile  sfEntity = new SopFile();
		sfEntity.setProjectId(p1.getId());
		sfEntity.setProjectProgress(p1.getProjectProgress());
//		sfEntity.setCareerLine(new Long(user.getDepartmentId()));
		sfEntity.setCareerLine(careerLine);
		sfEntity.setFileType(DictEnum.fileType.音频文件.getCode());
		sfEntity.setFileStatus(DictEnum.fileStatus.已上传.getCode());
//		sfEntity.setFileUid(user.getId());
		sfEntity.setFileUid(fileUid);
		sfEntity.setCreatedTime(nowTime);
		
		sfEntity.setFileLength(sopFile.getFileLength());//文件长度
		sfEntity.setFileKey(sopFile.getFileKey());//阿里云key
		sfEntity.setFileName(sopFile.getFileName());//文件前缀名
		sfEntity.setFileSuffix(sopFile.getFileSuffix());//文件后缀名
		sopFileDao.insert(sfEntity);
		
		MeetingRecord mrEntity = new MeetingRecord();
		mrEntity.setId(sopFile.getMeetingId());//会议Id
		
		SopFile query = new SopFile();
		query.setProjectId(p1.getId());
		query.setProjectProgress(p1.getProjectProgress());
//		query.setFileType(DictEnum.fileType.音频文件.getCode());	
		query.setFileKey(sopFile.getFileKey());
		List<SopFile> list =sopFileDao.selectList(query);
		SopFile _sf = new SopFile();
		if(list!=null && list.size()>0){
			 _sf = list.get(0);
		}
	
		mrEntity.setFileId(_sf.getId());
		meetingRecordDao.updateById(mrEntity);
	}

	@Transactional
	@Override
	public void addIdeaFileByMeeting(MeetingRecord meetingRecord , Idea idea, Long careerLine, Long fileUid, AppSopFile sopFile) throws Exception {
		long nowTime = System.currentTimeMillis();
		SopFile  sfEntity = new SopFile();
		sfEntity.setProjectId(idea.getId());
		sfEntity.setProjectProgress(idea.getIdeaProgress());
//		sfEntity.setCareerLine(new Long(user.getDepartmentId()));
		sfEntity.setCareerLine(careerLine);
		sfEntity.setFileType(DictEnum.fileType.音频文件.getCode());
		sfEntity.setFileStatus(DictEnum.fileStatus.已上传.getCode());
//		sfEntity.setFileUid(user.getId());
		sfEntity.setFileUid(fileUid);
		sfEntity.setCreatedTime(nowTime);
		//添加属性
		sfEntity.setRecordType(DictEnum.RecordType.IDEAS.getType());
		sfEntity.setFileLength(sopFile.getFileLength());//文件长度
		sfEntity.setFileKey(sopFile.getFileKey());//阿里云key
		sfEntity.setFileName(sopFile.getFileName());//文件前缀名
		sfEntity.setFileSuffix(sopFile.getFileSuffix());//文件后缀名
		sopFileDao.insert(sfEntity);
		
		MeetingRecord mrEntity = new MeetingRecord();
		mrEntity.setId(sopFile.getMeetingId());//会议Id
		mrEntity.setRecordType(DictEnum.RecordType.IDEAS.getType());
		SopFile query = new SopFile();
		query.setProjectId(idea.getId());
		query.setProjectProgress(idea.getIdeaProgress());
//		query.setFileType(DictEnum.fileType.音频文件.getCode());	
		query.setFileKey(sopFile.getFileKey());
		query.setRecordType(DictEnum.RecordType.IDEAS.getType());
		List<SopFile> list =sopFileDao.selectList(query);
		SopFile _sf = new SopFile();
		if(list!=null && list.size()>0){
			 _sf = list.get(0);
		}
	    mrEntity.setMeetingDate(meetingRecord.getMeetingDate());
		mrEntity.setFileId(_sf.getId());
		meetingRecordDao.updateById(mrEntity);
	}


	@Override
	protected BaseDao<MeetingRecord, Long> getBaseDao() {
		// TODO Auto-generated method stub
		return null;
	}


}
