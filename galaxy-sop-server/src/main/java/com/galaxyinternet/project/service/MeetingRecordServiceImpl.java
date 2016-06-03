package com.galaxyinternet.project.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.bo.IdeaBo;
import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.dao.project.MeetingRecordDao;
import com.galaxyinternet.dao.project.MeetingSchedulingDao;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.dao.soptask.SopTaskDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.idea.Idea;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.IdeaService;
import com.galaxyinternet.service.MeetingRecordService;


@Service("com.galaxyinternet.service.MeetingRecordService")
public class MeetingRecordServiceImpl extends BaseServiceImpl<MeetingRecord> implements MeetingRecordService {

	@Autowired
	private MeetingRecordDao meetingRecordDao;
	
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private SopTaskDao sopTaskDao;
	@Autowired
	private SopFileDao sopFileDao;
	@Autowired
	private MeetingSchedulingDao meetingSchedulingDao;
	
	
	@Autowired
	private IdeaService ideaService;
	
	@Override
	protected BaseDao<MeetingRecord, Long> getBaseDao() {
		return this.meetingRecordDao;
	}
	
	
	@Override
	@Transactional
	public Long insertMeet(MeetingRecord meetingRecord,Project project,SopFile sopFile,boolean equalNowPrograss) {
		Long fid = null;
		if(sopFile.getFileKey()!=null){
			fid = sopFileDao.insert(sopFile);
		}
		meetingRecord.setFileId(fid);
		Long id = getBaseDao().insert(meetingRecord);
		
		// 会议结论： 待定(默认)、 否决、 通过
		// 会议类型 2:内评会、3：CEO评审、 4:立项会、 7投决会、
		// 操作项目、排期池
		if(equalNowPrograss){
			if (meetingRecord.getMeetingType() != null && meetingRecord.getMeetingType().equals(DictEnum.meetingType.内评会.getCode())) {
				if (meetingRecord.getMeetingResult() != null && !meetingRecord.getMeetingResult().equals(DictEnum.meetingResult.待定.getCode())) {
					lph(meetingRecord.getProjectId(), meetingRecord.getMeetingResult());
				}
			} else if (meetingRecord.getMeetingType() != null && meetingRecord.getMeetingType().equals(DictEnum.meetingType.CEO评审.getCode())) {
				pqcUpdate(meetingRecord);
				if (meetingRecord.getMeetingResult() != null && !meetingRecord.getMeetingResult().equals(DictEnum.meetingResult.待定.getCode())) {
					ceops(meetingRecord.getProjectId(), meetingRecord.getMeetingResult());
				}
			} else if (meetingRecord.getMeetingType() != null && meetingRecord.getMeetingType().equals(DictEnum.meetingType.立项会.getCode())) {
				pqcUpdate(meetingRecord);
				if (meetingRecord.getMeetingResult() != null && !meetingRecord.getMeetingResult().equals(DictEnum.meetingResult.待定.getCode())) {
					lxh(meetingRecord.getProjectId(), meetingRecord.getMeetingResult(), sopFile.getFileUid(), sopFile.getCareerLine());
				}
			} else if (meetingRecord.getMeetingType() != null && meetingRecord.getMeetingType().equals(DictEnum.meetingType.投决会.getCode())) {
				pqcUpdate(meetingRecord);
				if (meetingRecord.getMeetingResult() != null && !meetingRecord.getMeetingResult().equals(DictEnum.meetingResult.待定.getCode())) {
					tjh(meetingRecord.getProjectId(), project, meetingRecord.getMeetingResult(), sopFile.getFileUid(), sopFile.getCareerLine());
				}
			}
		}
		return id;
	}
	
	
	//内评会
	@Transactional
	public void lph(Long pid,String meetingResult){
		Project pro = new Project();
		pro.setId(pid);
		
		if(meetingResult.equals(DictEnum.meetingResult.否决.getCode()) ){ //update 项目状态
			pro.setProjectStatus(DictEnum.meetingResult.否决.getCode()); 
			projectDao.updateById(pro);
			
		}else if(meetingResult.equals(DictEnum.meetingResult.通过.getCode()) ){ //update 项目进度
			pro.setProjectProgress(DictEnum.projectProgress.CEO评审.getCode());
			pro.setProjectStatus(DictEnum.meetingResult.待定.getCode()); 
			projectDao.updateById(pro);
			
			MeetingScheduling ms = new MeetingScheduling();
			ms.setProjectId(pro.getId());
			ms.setMeetingType(DictEnum.meetingType.CEO评审.getCode());
			ms.setMeetingCount(0);
			ms.setStatus(DictEnum.meetingResult.待定.getCode());
			ms.setScheduleStatus(DictEnum.meetingSheduleResult.待排期.getCode());
			ms.setApplyTime(new Timestamp(new Date().getTime()));
			meetingSchedulingDao.insert(ms);
		}
	}
	
	//CEO评审  
	@Transactional
	public void ceops(Long pid,String meetingResult){
		Project pro = new Project();
		pro.setId(pid);
		
		if(meetingResult.equals(DictEnum.meetingResult.否决.getCode()) ){ //update 项目状态
			pro.setProjectStatus(DictEnum.meetingResult.否决.getCode()); 
			projectDao.updateById(pro);
			
		}else if(meetingResult.equals(DictEnum.meetingResult.通过.getCode()) ){ //update 项目状态
			//pro.setProjectStatus(DictEnum.meetingResult.通过.getCode()); 
			pro.setProjectProgress(DictEnum.projectProgress.立项会.getCode());
			pro.setProjectStatus(DictEnum.meetingResult.待定.getCode()); 
			projectDao.updateById(pro);
			
			MeetingScheduling ms = new MeetingScheduling();
			ms.setProjectId(pro.getId());
			ms.setMeetingType(DictEnum.meetingType.立项会.getCode());
			ms.setMeetingCount(0);
			ms.setStatus(DictEnum.meetingResult.待定.getCode());
			ms.setScheduleStatus(DictEnum.meetingSheduleResult.待排期.getCode());
			ms.setApplyTime(new Timestamp(new Date().getTime()));
			meetingSchedulingDao.insert(ms);
		}
	}
	
	//ceo lxh tjh 会议排期池 修改
	@Transactional
	public void pqcUpdate(MeetingRecord meetingRecord){
		MeetingScheduling ms = new MeetingScheduling();
		ms.setProjectId(meetingRecord.getProjectId());
		ms.setMeetingDate(meetingRecord.getMeetingDate());
		ms.setMeetingType(meetingRecord.getMeetingType());
		
		if (meetingRecord.getMeetingResult() != null && meetingRecord.getMeetingResult().equals(DictEnum.meetingResult.待定.getCode())) {
			//ms.setStatus(DictEnum.meetingResult.待定.getCode());
			ms.setStatus(DictEnum.meetingResult.通过.getCode());
			ms.setScheduleStatus(DictEnum.meetingSheduleResult.待排期.getCode());
		}else if (meetingRecord.getMeetingResult() != null && meetingRecord.getMeetingResult().equals(DictEnum.meetingResult.否决.getCode())) {
			ms.setStatus(DictEnum.meetingResult.否决.getCode());
			ms.setScheduleStatus(DictEnum.meetingSheduleResult.已否决.getCode());
		}if (meetingRecord.getMeetingResult() != null && meetingRecord.getMeetingResult().equals(DictEnum.meetingResult.通过.getCode())) {
			ms.setStatus(DictEnum.meetingResult.通过.getCode());
			ms.setScheduleStatus(DictEnum.meetingSheduleResult.已通过.getCode());
		}
		
		meetingSchedulingDao.updateCountBySelective(ms);
	}
		
	//立项会
	@Transactional
	public void lxh(Long pid,String meetingResult,Long userid,Long udepartid){
		Project pro = new Project();
		pro.setId(pid);
		
		if(meetingResult.equals(DictEnum.meetingResult.否决.getCode()) ){ 
			//update 项目状态
			pro.setProjectStatus(DictEnum.meetingResult.否决.getCode()); 
			projectDao.updateById(pro);
			
		}else if(meetingResult.equals(DictEnum.meetingResult.通过.getCode()) ){
			//update 项目进度
			pro.setProjectProgress(DictEnum.projectProgress.投资意向书.getCode());
			pro.setProjectStatus(DictEnum.meetingResult.待定.getCode()); 
			projectDao.updateById(pro);

			//投资意向书  任务生成
			SopTask task = new SopTask();
			task.setProjectId(pid);                     //项目id
			task.setDepartmentId(udepartid);  	//任务分派到: 投资经理
			task.setTaskName("上传投资意向书");          //任务名称：    上传投资意向书
			task.setTaskFlag(1);  //0 完善简历、 1 投资意向书、 2 人事尽职调查报告、 3 法务尽职调查报告、 4 财务尽调报告、
									//5 业务尽调报告、 6 投资协议、 7 股权转让协议、 8 资金拨付凭证、 9 工商变更登记凭证
			task.setAssignUid(userid);             //任务认领人id 
			task.setTaskStatus(DictEnum.taskStatus.待完工.getCode());		//任务状态: 2:待完工
			task.setTaskType(DictEnum.taskType.协同办公.getCode());			//任务类型    协同
			
			sopTaskDao.insert(task);
			
		}
	}
	
	
	//投决会 
	// --> 投资协议
	@Transactional
	public void tjh(Long pid,Project pro,String meetingResult,Long userid,Long udepartid){
		
		if(meetingResult.equals(DictEnum.meetingResult.否决.getCode()) ){
			//update 项目状态
			pro.setProjectStatus(DictEnum.meetingResult.否决.getCode()); 
			projectDao.updateById(pro);
			
		}else if(meetingResult.equals(DictEnum.meetingResult.通过.getCode()) ){ 
			//update 项目进度
			pro.setProjectProgress(DictEnum.projectProgress.投资协议.getCode());
			pro.setProjectStatus(DictEnum.meetingResult.待定.getCode()); 
			projectDao.updateById(pro);
			
			//投资协议  任务生成
			SopTask task1 = new SopTask();
			task1.setProjectId(pid);                     
			task1.setDepartmentId(udepartid);  		   //任务分派到: 投资经理
			task1.setTaskName("上传投资协议");          //任务名称：   上传投资协议
			task1.setTaskFlag(6);   //0 完善简历、 1 投资意向书、 2 人事尽职调查报告、 3 法务尽职调查报告、 4 财务尽调报告、
									//5 业务尽调报告、 6 投资协议、 7 股权转让协议、 8 资金拨付凭证、 9 工商变更登记凭证
			task1.setAssignUid(userid);             
			task1.setTaskStatus(DictEnum.taskStatus.待完工.getCode());				
			task1.setTaskType(DictEnum.taskType.协同办公.getCode());				
			sopTaskDao.insert(task1);
			
			/*if(pro.getProjectType().equals(DictEnum.projectType.外部项目.getCode())){
				//股权转让协议  任务生成
				SopTask task2 = new SopTask();
				task2.setProjectId(pid);                   
				task2.setDepartmentId(udepartid);  		//任务分派到: 投资经理
				task2.setTaskName("上传股权转让协议");       //任务名称：  上传股权转让协议
				task2.setTaskFlag(7);
				task2.setAssignUid(userid);           
				task2.setTaskStatus(DictEnum.taskStatus.待完工.getCode());				
				task2.setTaskType(DictEnum.taskType.协同办公.getCode());				
				sopTaskDao.insert(task2);
			}*/
		}
	}
	
	
	/**
	 * 会议查询
	 * @return Page
	 */
	@Override
	public Page<MeetingRecordBo> queryMeetPageList(MeetingRecordBo query, Pageable pageable) {
		Page<MeetingRecordBo> mpage = meetingRecordDao.selectMeetPageList(query, pageable);
		List<MeetingRecordBo> contentList = mpage.getContent();
		if(contentList!=null){
			for(MeetingRecordBo ib : contentList){
				if(ib.getFileId()!=null){
					SopFile file = sopFileDao.selectById(ib.getFileId());
					if(file!=null){
						ib.setFname(file.getFileName());
						ib.setFkey(file.getFileKey());
					}
				}
			}
		}
		return mpage;
	}
	
	
	
	// 项目tab查询     projectId
	// 列表查询， uid;  project_name\project_code ~ keyword  ||  startTime;  endTime; 
	@Override
	public Page<MeetingRecordBo> queryMeetPage(MeetingRecordBo query, Pageable pageable) {
		Page<MeetingRecordBo> meetPage = null;
		List<MeetingRecordBo> meetBoList = null;
		List<MeetingRecord> meetList = null;
		Long total = null;
		Map<Long,String> proIdNameMap = new HashMap<Long,String>();
		Map<Long,Long> proIdUidMap = new HashMap<Long,Long>();
		
		if(query.getProjectId()!=null){   // 项目tab查询
			meetList = meetingRecordDao.selectList(query, pageable);
			total = meetingRecordDao.selectCount(query);
			
			//配合APP端新增获取相关字段
			Project  proQ = new Project();
			proQ.setId(query.getProjectId());
			proQ.setCreateUid(query.getUid());
			proQ.setKeyword(query.getKeyword());
			List<Project> proList = projectDao.selectList(proQ);
			
			//获取 projectId List
			if(proList!=null&&!proList.isEmpty()){
				List<Long> proIdList = new ArrayList<Long>();
				for(Project apro : proList){
					proIdList.add(apro.getId());
					proIdNameMap.put(apro.getId(), apro.getProjectName());
					proIdUidMap.put(apro.getId(), apro.getCreateUid());
				}
			}
		}else{    //列表查询_个人创建/部门
			Project  proQ = new Project();
			proQ.setCreateUid(query.getUid());
			proQ.setProjectDepartid(query.getDepartId());
			proQ.setKeyword(query.getKeyword());
			List<Project> proList = projectDao.selectList(proQ);
			
			//获取 projectId List
			if(proList!=null&&!proList.isEmpty()){
				List<Long> proIdList = new ArrayList<Long>();
				for(Project apro : proList){
					proIdList.add(apro.getId());
					proIdNameMap.put(apro.getId(), apro.getProjectName());
					proIdUidMap.put(apro.getId(), apro.getCreateUid());
				}
				//查询列表  
				query.setProIdList(proIdList);
				meetList = meetingRecordDao.selectList(query, pageable);
				total = meetingRecordDao.selectCount(query);
			}
		}
		    
		if(meetList!=null&&!meetList.isEmpty()){
			meetBoList = new ArrayList<MeetingRecordBo>();
			for(MeetingRecord ib : meetList){
				MeetingRecordBo bo = new MeetingRecordBo();
				bo.setId(ib.getId());
				bo.setProjectId(ib.getProjectId());
				bo.setProName(proIdNameMap.get(ib.getProjectId()));
				bo.setMeetingDateStr(ib.getMeetingDateStr());
				bo.setMeetingType(ib.getMeetingType());
				bo.setMeetingTypeStr(ib.getMeetingTypeStr());
				bo.setMeetingResult(ib.getMeetingResult());
				bo.setMeetingResultStr(ib.getMeetingResultStr());
				bo.setMeetingNotes(ib.getMeetingNotes());
				bo.setUid(proIdUidMap.get(ib.getProjectId()));
				if(ib.getFileId()!=null){
					SopFile file  = sopFileDao.selectById(ib.getFileId());
					if(file!=null){
						bo.setFileId(ib.getFileId());
						bo.setFname(file.getFileName());
						bo.setFkey(file.getFileKey());
					}
				}
				meetBoList.add(bo);
			}
			meetPage = new Page<MeetingRecordBo>(meetBoList, pageable, total);
		}else{
			meetPage = new Page<MeetingRecordBo>(new ArrayList<MeetingRecordBo>(), pageable, 0l);
		}
		
		return meetPage;
	}
	
		
	/**
	 * 投资意向书阶段，    上传  投资意向书-签署证明；
	 * 				更新项目阶段；  --》  尽职调查
	 * 				生成任务;
	 * @param   project 
	 * @return
	 */
	@Override
	@Transactional
	public void upTermSheetSign(Project project,Long userid,Long departid){
		project.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
		project.setProjectStatus(DictEnum.meetingResult.待定.getCode());
		projectDao.updateById(project);
		
		//业务dd  任务生成
		SopTask task1 = new SopTask();
		task1.setProjectId(project.getId());         //项目id
		task1.setDepartmentId(departid);  		 //任务分派到: 投资经理
		task1.setTaskName("上传业务尽职调查报告");    //任务名称：  上传股权转让协议
		task1.setTaskFlag(5);
		task1.setAssignUid(userid);             //任务认领人id 
		task1.setTaskStatus(DictEnum.taskStatus.待完工.getCode());				 //任务状态: 2:待完工
		task1.setTaskType(DictEnum.taskType.协同办公.getCode());					 //任务类型    协同
		sopTaskDao.insert(task1);
		
		//人事dd  任务生成
		SopTask task2 = new SopTask();
		task2.setProjectId(project.getId());         //项目id
		task2.setDepartmentId(SopConstant.DEPARTMENT_RS_ID);  		 //任务分派到: 投资经理
		task2.setTaskName("上传人事尽职调查报告");        //任务名称：  上传股权转让协议
		task2.setTaskFlag(2);
		task2.setTaskStatus(DictEnum.taskStatus.待认领.getCode());				 //任务状态: 2:待完工
		task2.setTaskType(DictEnum.taskType.协同办公.getCode());					 //任务类型    协同
		sopTaskDao.insert(task2);
		
		if(project.getProjectType()!=null && project.getProjectType().equals(DictEnum.projectType.外部投资.getCode())){
			//财务dd  任务生成
			SopTask task3 = new SopTask();
			task3.setProjectId(project.getId());         //项目id
			task3.setDepartmentId(SopConstant.DEPARTMENT_CW_ID);  		 //任务分派到: 投资经理
			task3.setTaskName("上传财务尽职调查报告");     //任务名称：  上传股权转让协议
			task3.setTaskFlag(4);
			task3.setTaskStatus(DictEnum.taskStatus.待认领.getCode());				 //任务状态: 2:待完工
			task3.setTaskType(DictEnum.taskType.协同办公.getCode());					 //任务类型    协同
			sopTaskDao.insert(task3);
			
			//法务dd  任务生成
			SopTask task4 = new SopTask();
			task4.setProjectId(project.getId());         //项目id
			task4.setDepartmentId(SopConstant.DEPARTMENT_FW_ID);  		 //任务分派到: 投资经理
			task4.setTaskName("上传法务尽职调查报告");        //任务名称：  上传股权转让协议
			task4.setTaskFlag(3);
			task4.setTaskStatus(DictEnum.taskStatus.待认领.getCode());				 //任务状态: 2:待完工
			task4.setTaskType(DictEnum.taskType.协同办公.getCode());				 //任务类型    协同
			sopTaskDao.insert(task4);
		}
	}
	
	
	
	
	
	/**
	 * 创意添加会议记录
	 */
	@Override
	@Transactional
	public Long addCyMeetRecord(MeetingRecord meetingRecord,SopFile sopFile) {
		Long fid = null;
		if(sopFile.getFileKey()!=null){
			fid = sopFileDao.insert(sopFile);
		}
		meetingRecord.setFileId(fid);
		
		/*if(meetingRecord.getMeetingResult().equals(DictEnum.meetingResult.否决.getCode())){
			Idea idea = new Idea();
			idea.setId(meetingRecord.getProjectId());
			idea.setIdeaProgress(SopConstant.IDEA_PROGRESS_GZ);
			ideaService.updateById(idea);
			
			meetingRecord.setMeetValid((byte)1);
		}*/
		
		Long id = getBaseDao().insert(meetingRecord);
		return id;
	}
	
}