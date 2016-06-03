package com.galaxyinternet.project.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.dao.project.MeetingRecordDao;
import com.galaxyinternet.dao.project.MeetingSchedulingDao;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.dao.soptask.SopTaskDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.idea.Idea;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.AppSopFile;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.service.AppProjectMeetingService;

@Service("com.galaxyinternet.service.AppProjectMeetingService")
public class AppProjectMeetingServiceImpl extends BaseServiceImpl<MeetingRecord> implements AppProjectMeetingService{
	
	@Autowired
	private MeetingRecordDao meetingRecordDao;	
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private SopFileDao sopFileDao;
	@Autowired
	private SopTaskDao sopTaskDao;
	@Autowired
	private MeetingSchedulingDao  meetingSchedulingDao ;
	
	@Override
	@Transactional
	public Long addingMeeting(MeetingRecord meetingRecord,Project project,SopFile sopFile) throws Exception{
		Long fid = null;
		/* 保存上传文件的信息至数据库  */
		if(StringUtils.isNotBlank(sopFile.getFileKey())){
			fid = sopFileDao.insert(sopFile);
		}
		/* 保存会议的信息至数据库 */
		meetingRecord.setFileId(fid);
		Long meetingId = meetingRecordDao.insert(meetingRecord);
		
		String currProjectProgress = project.getProjectProgress().trim(); //当前项目阶段
		String currMeetingType = meetingRecord.getMeetingType().trim(); //当前会议类型
		String currMeetingResult = meetingRecord.getMeetingResult().trim(); //当前会议结果		
		// 如果当前项目阶段是内评审，会议类型是内评会，会议结果是通过或否决时，更新当前项目信息	
		if (currProjectProgress.equals(DictEnum.projectProgress.内部评审.getCode())
				&& currMeetingType.equals(DictEnum.meetingType.内评会.getCode())
				&& !currMeetingResult.equals(DictEnum.meetingResult.待定.getCode())) {
			
			Project  proEntiry = new Project();
			proEntiry.setId(project.getId());		
			if(currMeetingResult.equals(DictEnum.meetingResult.否决.getCode()) ){
				//更新项目状态为否决状态，更新变更时间
				proEntiry.setProjectStatus(DictEnum.meetingResult.否决.getCode());
				proEntiry.setUpdatedTime((new Date()).getTime());
				projectDao.updateById(proEntiry);			
			}
			else if(currMeetingResult.equals(DictEnum.meetingResult.通过.getCode()) ){
				//变更项目阶段状态为 CEO评审,更新变更时间
				proEntiry.setProjectProgress(DictEnum.projectProgress.CEO评审.getCode());
				proEntiry.setUpdatedTime((new Date()).getTime());
				projectDao.updateById(proEntiry);
				//新增当前项目在CEO评审阶段的待排期信息
				MeetingScheduling msEntity = new MeetingScheduling();
				msEntity.setProjectId(project.getId());
				msEntity.setMeetingType(DictEnum.meetingType.CEO评审.getCode());
				msEntity.setMeetingCount(0);
				msEntity.setStatus(DictEnum.meetingResult.待定.getCode());
				msEntity.setScheduleStatus(DictEnum.meetingSheduleResult.待排期.getCode());
				msEntity.setCreatedTime((new Date()).getTime());
				msEntity.setApplyTime(new Timestamp(new Date().getTime()));
				meetingSchedulingDao.insert(msEntity);
			}		
		}
		// 如果当前项目阶段是CEO评审，会议类型是CEO评审，会议结果分别是待定、通过或否决时，更新当前项目信息	
		else if (currProjectProgress.equals(DictEnum.projectProgress.CEO评审.getCode())
				&& currMeetingType.equals(DictEnum.meetingType.CEO评审.getCode())){
			
			MeetingScheduling scheduEntity = new MeetingScheduling();
			scheduEntity.setProjectId(project.getId());
			scheduEntity.setMeetingType(DictEnum.meetingType.CEO评审.getCode());
			MeetingScheduling querySchedu = meetingSchedulingDao.selectOne(scheduEntity);
			
			if(currMeetingResult.equals(DictEnum.meetingResult.待定.getCode())){
				querySchedu.setScheduleStatus(DictEnum.meetingSheduleResult.待排期.getCode());
				querySchedu.setReserveTimeStart(null);
				querySchedu.setReserveTimeEnd(null);
				querySchedu.setStatus(DictEnum.meetingResult.通过.getCode());
				
				querySchedu.setMeetingDate(meetingRecord.getMeetingDate() == null ? new Date() : meetingRecord.getMeetingDate());
				querySchedu.setMeetingCount(querySchedu.getMeetingCount() + 1);//过会次数据累加1
				querySchedu.setUpdatedTime((new Date()).getTime()); //变更操作时间
				meetingSchedulingDao.updateBySelective(querySchedu); 
				
			}else if(currMeetingResult.equals(DictEnum.meetingResult.否决.getCode())){
				Project proEntity = new Project();
				proEntity.setId(project.getId());
				proEntity.setProjectStatus(DictEnum.meetingResult.否决.getCode());
				proEntity.setUpdatedTime((new Date()).getTime());
				projectDao.updateById(proEntity);
				
				querySchedu.setStatus(DictEnum.meetingResult.否决.getCode());
				querySchedu.setScheduleStatus(DictEnum.meetingSheduleResult.已否决.getCode());			
				querySchedu.setMeetingDate(meetingRecord.getMeetingDate() == null ? new Date() : meetingRecord.getMeetingDate());
				querySchedu.setMeetingCount(querySchedu.getMeetingCount() + 1);//过会次数据累加1
				querySchedu.setUpdatedTime((new Date()).getTime()); //变更操作时间
				meetingSchedulingDao.updateBySelective(querySchedu); 
				
			}else if (currMeetingResult.equals(DictEnum.meetingResult.通过.getCode())){
				querySchedu.setStatus(DictEnum.meetingResult.通过.getCode());//会议结果状态 通过
				querySchedu.setScheduleStatus(DictEnum.meetingSheduleResult.已通过.getCode()); //
				querySchedu.setMeetingDate(meetingRecord.getMeetingDate() == null ? new Date() : meetingRecord.getMeetingDate());
				querySchedu.setMeetingCount(querySchedu.getMeetingCount() + 1);  //过会次数据累加1
				querySchedu.setUpdatedTime((new Date()).getTime()); //变更操作时间
				meetingSchedulingDao.updateBySelective(querySchedu); 
				
				//变更项目阶段为下一阶段为 CEO评审 , 更新变更时间
				Project  proEntiry = new Project();
				proEntiry.setId(project.getId());		
				proEntiry.setProjectProgress(DictEnum.projectProgress.立项会.getCode());
				proEntiry.setUpdatedTime((new Date()).getTime());
				projectDao.updateById(proEntiry);
				
				//新增立项会的排期记录，排期状态为待排期，会议结果为待定
				MeetingScheduling msEntity = new MeetingScheduling();
				msEntity.setProjectId(project.getId());
				msEntity.setMeetingType(DictEnum.meetingType.立项会.getCode());
				msEntity.setMeetingCount(0);
				msEntity.setStatus(DictEnum.meetingResult.待定.getCode());
				msEntity.setScheduleStatus(DictEnum.meetingSheduleResult.待排期.getCode());
				msEntity.setCreatedTime((new Date()).getTime());
				msEntity.setApplyTime(new Timestamp(new Date().getTime()));
				meetingSchedulingDao.insert(msEntity);				
			}
		}
		// 如果当前项目阶段是立项会，会议类型是立项会，会议结果分别是待定、通过或否决时，更新当前项目信息	
		else if (currProjectProgress.equals(DictEnum.projectProgress.立项会.getCode())
				&& currMeetingType.equals(DictEnum.meetingType.立项会.getCode())){
			
			MeetingScheduling scheduling = new MeetingScheduling();			
			List<Long> idList = new ArrayList<Long>();
			idList.add(project.getId());
			scheduling.setProjectIdList(idList);
			scheduling.setMeetingType(DictEnum.meetingType.立项会.getCode());
			MeetingScheduling queryScheduling = meetingSchedulingDao.selectOne(scheduling);		
					
			if(currMeetingResult.equals(DictEnum.meetingResult.待定.getCode())){
				queryScheduling.setStatus(DictEnum.meetingResult.通过.getCode());
				queryScheduling.setScheduleStatus(DictEnum.meetingSheduleResult.待排期.getCode());
				queryScheduling.setReserveTimeStart(null);
				queryScheduling.setReserveTimeEnd(null);
				
				queryScheduling.setMeetingDate(meetingRecord.getMeetingDate() == null ? new Date() : meetingRecord.getMeetingDate());
				queryScheduling.setMeetingCount(queryScheduling.getMeetingCount() + 1);
				queryScheduling.setUpdatedTime((new Date()).getTime());
				meetingSchedulingDao.updateBySelective(queryScheduling);
			}
			else if (currMeetingResult.equals(DictEnum.meetingResult.否决.getCode())){
				//关闭项目，更新项目状态信息
				Project proEntity = new Project();
				proEntity.setId(project.getId());
				proEntity.setProjectStatus(DictEnum.meetingResult.否决.getCode());
				proEntity.setUpdatedTime((new Date()).getTime());
				projectDao.updateById(proEntity);
				//更新该项目在当前阶段的排期结果状态“已否决”
				queryScheduling.setStatus(DictEnum.meetingResult.否决.getCode());
				queryScheduling.setScheduleStatus(DictEnum.meetingSheduleResult.已否决.getCode());
				queryScheduling.setMeetingDate(meetingRecord.getMeetingDate() == null ? new Date() : meetingRecord.getMeetingDate());
				queryScheduling.setMeetingCount(queryScheduling.getMeetingCount() + 1);
				queryScheduling.setUpdatedTime((new Date()).getTime());
				meetingSchedulingDao.updateBySelective(queryScheduling);
			}
			else if (currMeetingResult.equals(DictEnum.meetingResult.通过.getCode())){
				//更新当前项目的阶段状态为下一阶段“投资意见书”
				Project proEntity = new Project();
				proEntity.setId(project.getId());
				proEntity.setProjectProgress(DictEnum.projectProgress.投资意向书.getCode());
				proEntity.setUpdatedTime((new Date()).getTime());
				projectDao.updateById(proEntity);
				//新增一条待办任务“协同办公”记录至数据库任务表中
				SopTask task = new SopTask();
				task.setProjectId(project.getId());
				task.setTaskName(SopConstant.TASK_NAME_SCTZYXS);
				task.setTaskType(DictEnum.taskType.协同办公.getCode());
				task.setTaskFlag(SopConstant.TASK_FLAG_SCTZYXS);
				task.setTaskOrder(SopConstant.NORMAL_STATUS);
				task.setDepartmentId(project.getProjectDepartid());
				task.setAssignUid(project.getCreateUid());
				task.setTaskStatus(DictEnum.taskStatus.待完工.getCode());
				task.setCreatedTime(System.currentTimeMillis());
				sopTaskDao.insert(task);
				//更新项目的当前阶段的排期结果为"通过"以及过会次数据，变更时间
				queryScheduling.setStatus(DictEnum.meetingResult.通过.getCode());
				queryScheduling.setScheduleStatus(DictEnum.meetingSheduleResult.已通过.getCode());
				queryScheduling.setMeetingDate(meetingRecord.getMeetingDate() == null ? new Date() : meetingRecord.getMeetingDate());
				queryScheduling.setMeetingCount(queryScheduling.getMeetingCount() + 1);
				queryScheduling.setUpdatedTime((new Date()).getTime());
				meetingSchedulingDao.updateBySelective(queryScheduling);
			}			
		}
		else if (currProjectProgress.equals(DictEnum.projectProgress.投资决策会.getCode())
				&& currMeetingType.equals(DictEnum.meetingType.投决会.getCode())){
			
			MeetingScheduling scheduling = new MeetingScheduling();			
			List<Long> idList = new ArrayList<Long>();
			idList.add(project.getId());
			scheduling.setProjectIdList(idList);
			scheduling.setMeetingType(DictEnum.meetingType.投决会.getCode());
			MeetingScheduling queryScheduling = meetingSchedulingDao.selectOne(scheduling);		
			
			Project proEntity = new Project();
			proEntity.setId(project.getId());			
		    if (currMeetingResult.equals(DictEnum.meetingResult.通过.getCode())){
		    	//更新当前项目的阶段状态为下一阶段“投资协议”
		    	proEntity.setProjectProgress(DictEnum.projectProgress.投资协议.getCode());
		    	proEntity.setUpdatedTime((new Date()).getTime());
				projectDao.updateById(proEntity);
				//更新项目的当前阶段的排期结果为"通过"以及过会次数据，变更时间
				queryScheduling.setStatus(DictEnum.meetingResult.通过.getCode());//会议结果状态 通过
				queryScheduling.setScheduleStatus(DictEnum.meetingSheduleResult.已通过.getCode()); //
				queryScheduling.setMeetingDate(meetingRecord.getMeetingDate() == null ? new Date() : meetingRecord.getMeetingDate());
				queryScheduling.setMeetingCount(queryScheduling.getMeetingCount() + 1);  //过会次数据累加1
				queryScheduling.setUpdatedTime((new Date()).getTime()); //变更操作时间
				meetingSchedulingDao.updateBySelective(queryScheduling); 
			}
		    else if (currMeetingResult.equals(DictEnum.meetingResult.否决.getCode())){
		    	//关闭项目，更新项目的状态为“已关闭”、变更操作时间
		    	proEntity.setProjectStatus(DictEnum.meetingResult.否决.getCode());
		    	proEntity.setUpdatedTime((new Date()).getTime());
				projectDao.updateById(proEntity);
				//更新该项目在当前阶段的排期结果状态“已否决”
				queryScheduling.setStatus(DictEnum.meetingResult.否决.getCode());
				queryScheduling.setScheduleStatus(DictEnum.meetingSheduleResult.已否决.getCode());			
				queryScheduling.setMeetingDate(meetingRecord.getMeetingDate() == null ? new Date() : meetingRecord.getMeetingDate());
				queryScheduling.setMeetingCount(queryScheduling.getMeetingCount() + 1);//过会次数据累加1
				queryScheduling.setUpdatedTime((new Date()).getTime()); //变更操作时间
				meetingSchedulingDao.updateBySelective(queryScheduling); 
		    }
		    else if (currMeetingResult.equals(DictEnum.meetingResult.待定.getCode())){
		    	/* 1.更新项目的当前阶段的排期结果为“待排期”,排期起始和结束时间置空，待秘书重新选择
		    	 * 2.更新过会次数、变更操作时间   */	    	
		    	queryScheduling.setScheduleStatus(DictEnum.meetingSheduleResult.待排期.getCode());
		    	queryScheduling.setReserveTimeStart(null);
		    	queryScheduling.setReserveTimeEnd(null);
		    	queryScheduling.setStatus(DictEnum.meetingResult.通过.getCode());				
		    	queryScheduling.setMeetingDate(meetingRecord.getMeetingDate() == null ? new Date() : meetingRecord.getMeetingDate());
		    	queryScheduling.setMeetingCount(queryScheduling.getMeetingCount() + 1);//过会次数据累加1
		    	queryScheduling.setUpdatedTime((new Date()).getTime()); //变更操作时间
				meetingSchedulingDao.updateBySelective(queryScheduling); 
		    }
		}
		return meetingId;
	}

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
