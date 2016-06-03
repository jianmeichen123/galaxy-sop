package com.galaxyinternet.project.service.handler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.common.SopResult;
import com.galaxyinternet.common.ViewQuery;
import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.query.ProjectQuery;
import com.galaxyinternet.dao.project.MeetingRecordDao;
import com.galaxyinternet.dao.project.MeetingSchedulingDao;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.dao.soptask.SopTaskDao;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.soptask.SopTask;

/**
 * 添加立项会议记录
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
@Service("lxMeetingHandler")
public class LxMeetingHandler implements Handler {
	
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private SopFileDao sopFileDao;
	@Autowired
	private MeetingRecordDao meetingRecordDao;
	@Autowired
	private SopTaskDao sopTaskDao;
	@Autowired
	private MeetingSchedulingDao meetingSchedulingDao;

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
		//添加立项会议记录
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
		//修改立项会排期记录:过会次数和状态
		MeetingScheduling m = new MeetingScheduling();
		m.setProjectId(q.getPid());
		m.setMeetingType(DictEnum.meetingType.立项会.getCode());
		MeetingScheduling tm = meetingSchedulingDao.selectOne(m);
        tm.setStatus(DictEnum.meetingResult.通过.getCode());
        tm.setScheduleStatus(DictEnum.meetingSheduleResult.已通过.getCode());
		/**
		 * 项目的当前状态为立项会阶段，添加通过的会议记录才回去修改项目阶段到投资意向书
		 * 否则仅仅保存会议记录，不做项目阶段跳转
		 */
		int in = Integer.parseInt(DictEnum.projectProgress.立项会.getCode().substring(DictEnum.projectProgress.立项会.getCode().length()-1));
		int pin = Integer.parseInt(project.getProjectProgress().substring(project.getProjectProgress().length()-1)) ;
		if(q.getResult().equals(DictEnum.meetingResult.通过.getCode()) && (in == pin)){
			p.setProjectProgress(DictEnum.projectProgress.投资意向书.getCode());
			p.setUpdatedTime((new Date()).getTime());
			projectDao.updateById(p);
			SopTask task = new SopTask();
			task.setProjectId(q.getPid());
			task.setTaskName(SopConstant.TASK_NAME_SCTZYXS);
			task.setTaskType(DictEnum.taskType.协同办公.getCode());
			task.setTaskFlag(SopConstant.TASK_FLAG_SCTZYXS);
			task.setTaskOrder(SopConstant.NORMAL_STATUS);
			task.setDepartmentId(q.getDepartmentId());
			task.setAssignUid(q.getCreatedUid());
			task.setTaskStatus(DictEnum.taskStatus.待完工.getCode());
			task.setCreatedTime(System.currentTimeMillis());
			sopTaskDao.insert(task);
		}
		
		if((q.getResult().equals(DictEnum.meetingResult.待定.getCode()))){
			tm.setReserveTimeStartStr(null);
			tm.setReserveTimeEndStr(null);
			tm.setReserveTimeEnd(null);
			tm.setReserveTimeStart(null);
		}
		
		if(q.getResult().equals(DictEnum.meetingResult.否决.getCode())){
			p.setProjectStatus(DictEnum.meetingResult.否决.getCode());
			p.setUpdatedTime((new Date()).getTime());
			projectDao.updateById(p);
			tm.setStatus(DictEnum.meetingResult.否决.getCode());
			tm.setScheduleStatus(DictEnum.meetingSheduleResult.已否决.getCode());
		}
		if(in == pin){
			tm.setMeetingDate(q.getParseDate() == null ? new Date() : q.getParseDate());
			tm.setMeetingCount(tm.getMeetingCount() + 1);
			tm.setUpdatedTime((new Date()).getTime());
			meetingSchedulingDao.updateById(tm);
		}
		return new SopResult(Status.OK,null,"添加立项会议记录要成功!",UrlNumber.four);
	}
	
}
