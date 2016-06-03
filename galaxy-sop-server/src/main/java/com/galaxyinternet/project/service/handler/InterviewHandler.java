package com.galaxyinternet.project.service.handler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.common.SopResult;
import com.galaxyinternet.common.ViewQuery;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.query.ProjectQuery;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.service.InterviewRecordService;

/**
 * 添加访谈纪要
 * {
 * 		pid:必传
 * 		stage:必传
 * 		file:非必须
 * 		createDate:必传
 * 		target:必传
 * 		content:非必传
 * }
 */
@Service("interviewHandler")
public class InterviewHandler implements Handler {
	
	@Autowired
	private SopFileDao sopFileDao;
	@Autowired
	private InterviewRecordService interviewRecordService;

	@Override
	@Transactional
	public SopResult handler(ViewQuery query, Project project) throws Exception {
		ProjectQuery q = (ProjectQuery) query;
		Long fid = null;
		if(q.getFileKey() != null){
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
			fid = sopFileDao.insert(file);
		}
		//添加访谈文件记录
		InterviewRecord ir = new InterviewRecord();
		ir.setProjectId(q.getPid());
		ir.setFileId(fid);
		ir.setViewDate(q.getParseDate() == null ? new Date() : q.getParseDate());
		ir.setViewTarget(q.getTarget());
		ir.setViewNotes(q.getContent());
		ir.setCreatedId(project.getCreateUid());
		ir.setCreatedTime((new Date()).getTime());
		interviewRecordService.insert(ir);
		return new SopResult(Status.OK,null,"添加访谈纪要成功!",UrlNumber.one);
	}
	
}
