package com.galaxyinternet.project.service.handler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.common.SopResult;
import com.galaxyinternet.common.ViewQuery;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.query.ProjectQuery;
import com.galaxyinternet.dao.project.MeetingRecordDao;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;

/**
 * 添加投决会议记录
 * {
 * 		pid:必传
 * 		stage:必传
 * 		file:非必须
 * 		createDate:必传
 * 		meetingType:必传
 * 		result:必传
 * 		content:非必传
 * }
 */
@Service("tjMeetingHandler")
public class TjMeetingHandler implements Handler {
	
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private SopFileDao sopFileDao;
	@Autowired
	private MeetingRecordDao meetingRecordDao;

	@Override
	@Transactional
	public SopResult handler(ViewQuery query, Project project) throws Exception {
		ProjectQuery q = (ProjectQuery) query;
		//添加访谈文件记录
		SopFile file = new SopFile();
		file.setProjectId(q.getPid());
		file.setProjectProgress(q.getStage());
		file.setCareerLine(q.getDepartmentId());
		file.setFileType(DictEnum.fileType.音频文件.getCode());
		file.setFileStatus(DictEnum.fileStatus.已上传.getCode());
		file.setFileUid(q.getCreatedUid());
		file.setCreatedTime((new Date()).getTime());
		file.setFileLength(q.getFileSize());
		file.setFileKey(q.getFileKey());
		file.setBucketName(q.getBucketName());
		file.setFileName(q.getFileName());
		file.setFileSuffix(q.getSuffix());
		long fid = sopFileDao.insert(file);
		MeetingRecord mr = new MeetingRecord();
		mr.setProjectId(q.getPid());
		mr.setFileId(fid);
		mr.setMeetingDate(q.getParseDate() == null ? new Date() : q.getParseDate());
		mr.setMeetingType(q.getMeetingType());
		mr.setMeetingResult(q.getResult());
		mr.setMeetingNotes(q.getContent());
		mr.setCreatedTime((new Date()).getTime());
		meetingRecordDao.insert(mr);
		Project p = new Project();
		p.setId(q.getPid());
		p.setFinalValuations(q.getFinalValuations());
		p.setFinalContribution(q.getFinalContribution());
		p.setFinalShareRatio(q.getFinalShareRatio());
		p.setServiceCharge(q.getServiceCharge());
		
		int in = Integer.parseInt(DictEnum.projectProgress.投资决策会.getCode().substring(DictEnum.projectProgress.投资决策会.getCode().length()-1));
		int pin = Integer.parseInt(project.getProjectProgress().substring(project.getProjectProgress().length()-1)) ;
		if(q.getResult().equals(DictEnum.meetingResult.通过.getCode()) && (in == pin)){
			p.setProjectProgress(DictEnum.projectProgress.投资协议.getCode());
			p.setUpdatedTime((new Date()).getTime());
			projectDao.updateById(p);
		}
		if(q.getResult().equals(DictEnum.meetingResult.否决.getCode())){
			p.setProjectStatus(DictEnum.projectStatus.YFJ.getCode());
			p.setUpdatedTime((new Date()).getTime());
			projectDao.updateById(p);
		}
		return new SopResult(Status.OK,null,"添加访谈纪要成功!",UrlNumber.eight);
	}
	
}
