package com.galaxyinternet.project.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.idea.Idea;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.model.touhou.DeliveryFile;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.IdeaService;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.UserService;


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
	private UserService userService;
	
	
	@Autowired
	private IdeaService ideaService;
	
	@Override
	protected BaseDao<MeetingRecord, Long> getBaseDao() {
		return this.meetingRecordDao;
	}
	
	
	@Override
	@Transactional
	public Long insertMeet(MeetingRecordBo meetingRecord,Project project,SopFile sopFile,boolean equalNowPrograss) {
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
				if (meetingRecord.getMeetingResult() != null ) {
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
			pro.setProjectStatus(DictEnum.projectStatus.YFJ.getCode()); 
			projectDao.updateById(pro);
			
		}else if(meetingResult.equals(DictEnum.meetingResult.通过.getCode()) ){ //update 项目进度
			pro.setProjectProgress(DictEnum.projectProgress.CEO评审.getCode());
			pro.setProjectStatus(DictEnum.projectStatus.GJZ.getCode()); 
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
			pro.setProjectStatus(DictEnum.projectStatus.YFJ.getCode()); 
			projectDao.updateById(pro);
			
		}else if(meetingResult.equals(DictEnum.meetingResult.通过.getCode()) ){ //update 项目状态
			//pro.setProjectStatus(DictEnum.meetingResult.通过.getCode()); 
			pro.setProjectProgress(DictEnum.projectProgress.立项会.getCode());
			pro.setProjectStatus(DictEnum.projectStatus.GJZ.getCode()); 
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
			ms.setScheduleStatus(DictEnum.meetingSheduleResult.已通过.getCode());
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
			pro.setProjectStatus(DictEnum.projectStatus.YFJ.getCode()); 
			projectDao.updateById(pro);
			
		}else if(meetingResult.equals(DictEnum.meetingResult.通过.getCode()) ){
			//update 项目进度
			pro.setProjectProgress(DictEnum.projectProgress.投资意向书.getCode());
			pro.setProjectStatus(DictEnum.projectStatus.GJZ.getCode()); 
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
			pro.setProjectStatus(DictEnum.projectStatus.YFJ.getCode()); 
			projectDao.updateById(pro);
			
		}else if(meetingResult.equals(DictEnum.meetingResult.通过.getCode()) ){ 
			//update 项目进度
			pro.setProjectProgress(DictEnum.projectProgress.投资协议.getCode());
			pro.setProjectStatus(DictEnum.projectStatus.GJZ.getCode()); 
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
		/*else{
			projectDao.updateById(pro);
		}*/
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
				bo.setResultReason(ib.getResultReason());
				bo.setReasonOther(ib.getReasonOther());
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
		project.setProjectStatus(DictEnum.projectStatus.GJZ.getCode());
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
		
		if(project.getProjectType()!=null && project.getProjectType().equals(DictEnum.projectType.投资.getCode())){
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


	@Override
	public Long queryMeetNumberByType(MeetingRecord query) {
		// TODO Auto-generated method stub
		return meetingRecordDao.selectMeetNumberByType(query);	
	}
	
	
	@Override
	public Page<MeetingRecord> queryPageList(MeetingRecord query,
			Pageable pageable) {
		// TODO Auto-generated method stub
		Page<MeetingRecord> pageEntity = super.queryPageList(query, pageable);
		List<User> userList = getUser(pageEntity.getContent());
		
		for(MeetingRecord meetingRecord : pageEntity.getContent()){
			//设置头后运营会议是否存在文件
			SopFile tempQuery = new SopFile();
			tempQuery.setMeetingId(meetingRecord.getId());
		    List<SopFile> sopFileList = sopFileDao.selectList(tempQuery);
			if(sopFileList!=null && sopFileList.size() > 0){
				meetingRecord.setHasFile("true");
			}else{
				meetingRecord.setHasFile("false");
			}
			
			//设置用户名称
			for(User user : userList){
				if(user.getId().equals(meetingRecord.getCreateUid())){
					meetingRecord.setCreateUName(user.getRealName());
				}
			}	
		}
		
		return pageEntity;
	}
	
	
	/**
	 * 删除投后运营会议
	 * @param id
	 * @return
	 */
	public int deletePostMeetingById(Long id){
		List<String> keyList = new ArrayList<String>();
		SopFile sopFile = new SopFile();
		sopFile.setMeetingId(id);
		List<SopFile> sopFileList = sopFileDao.selectList(sopFile);
		for(SopFile temp : sopFileList){
			if(!keyList.contains(temp.getFileKey())){
				keyList.add(temp.getFileKey());
			}
		}
		if(keyList.size() > 0){
			OSSHelper.deleteMultipleFiles(keyList);
			sopFileDao.delete(sopFile);
		}	
		return super.deleteById(id);		
	}
	
	private List<User> getUser(List<MeetingRecord> meetingRecordList){
		User user = new User();
		List<Long> ids = new ArrayList<Long>();	
		for(MeetingRecord meetingRecord : meetingRecordList){
			if(meetingRecord.getCreateUid()!=null && !ids.contains(meetingRecord.getCreateUid())){
				ids.add(meetingRecord.getCreateUid());
			}	
		}
		user.setIds(ids);
		if(ids.size() > 0){
			return userService.queryList(user);
		}
		return null;
	}

	@Override
	@Transactional
	public Long insertMeeting(MeetingRecord query) {
		Byte fnum = null;
		Long delid = null;
		List<SopFile> sopfiles = query.getFiles();
		
		if(sopfiles!=null && !sopfiles.isEmpty()){
			fnum = (byte) sopfiles.size();
			query.setFileNum(fnum);
		}
		delid = meetingRecordDao.insert(query);   
		
		if(sopfiles!=null && !sopfiles.isEmpty() && fnum !=null){
			Project project = projectDao.selectById(query.getProjectId());
			for(SopFile sopfile:sopfiles){
				sopfile.setProjectId(project.getId());
				sopfile.setProjectProgress(project.getProjectProgress());
				sopfile.setCareerLine(project.getProjectDepartid());
				sopfile.setFileStatus(DictEnum.fileStatus.已上传.getCode());
				sopfile.setFileUid(project.getCreateUid());
				sopfile.setMeetingId(delid);
				sopfile.setFileLength(sopfile.getFileLength());
				sopfile.setFileKey(sopfile.getFileKey());
				sopfile.setBucketName(sopfile.getBucketName());
				sopfile.setFileName(sopfile.getFileName());
				sopfile.setFileSuffix(sopfile.getFileSuffix());
			}
			sopFileDao.insertInBatch(sopfiles);
			
		}
		return delid;
	}

	private void saveFiles(List<SopFile> sopFileList,List<Long> fileIds,Long meetingId,Long projectId){
		//获取会议现有所有文件
		SopFile fQuery = new SopFile();
		fQuery.setMeetingId(meetingId);
		List<SopFile> oldFileList = sopFileDao.selectList(fQuery);
		//删除文件
		if(oldFileList!=null && oldFileList.size()>0){
			List<Long> oldFileIds = new ArrayList<Long>();
			for(SopFile sopFile : oldFileList){
				oldFileIds.add(sopFile.getId());
			}

			//删除的文件ID列表
			List<Long> deleteFileIds = getDeleteFileIds(oldFileIds, fileIds);
			SopFile query = new SopFile();
			if(deleteFileIds != null && deleteFileIds.size() > 0 ){
				query.setIds(deleteFileIds);
				
				List<SopFile> deleteFileList = sopFileDao.selectList(query);
				//删除文件的Filekey列表
				List<String> deleteFileKeyList = new ArrayList<String>();
				for(SopFile sopFile : deleteFileList){
					if(!deleteFileKeyList.contains(sopFile.getFileKey())){
						deleteFileKeyList.add(sopFile.getFileKey());
					}	
				}
				OSSHelper.deleteMultipleFiles(deleteFileKeyList);
				sopFileDao.deleteByIdInBatch(deleteFileIds);
			}
			
			/*List<Long> deleteFileIds = getDeleteFileIds(oldFileIds, fileIds);
			sopFileDao.deleteByIdInBatch(deleteFileIds);*/
		}	
		if(sopFileList!=null && !sopFileList.isEmpty()){
			Project project = projectDao.selectById(projectId);
			for(SopFile sopfile:sopFileList){
				sopfile.setProjectId(project.getId());
				sopfile.setProjectProgress(project.getProjectProgress());
				sopfile.setCareerLine(project.getProjectDepartid());
				sopfile.setFileStatus(DictEnum.fileStatus.已上传.getCode());
				sopfile.setFileUid(project.getCreateUid());
				sopfile.setMeetingId(meetingId);
				sopfile.setFileLength(sopfile.getFileLength());
				sopfile.setFileKey(sopfile.getFileKey());
				sopfile.setBucketName(sopfile.getBucketName());
				sopfile.setFileName(sopfile.getFileName());
				sopfile.setFileSuffix(sopfile.getFileSuffix());
			}
			sopFileDao.insertInBatch(sopFileList);	
		}
	}
	
	private List<Long> getDeleteFileIds(List<Long> oldFileIds,List<Long> fileIds){
		List<Long> deleteFileIds = null;
		if(fileIds==null){
			deleteFileIds = oldFileIds;
		}else{
			deleteFileIds = new ArrayList<Long>();
			for(Long fileId : oldFileIds){
				if(!fileIds.contains(fileId)){
					deleteFileIds.add(fileId);
				}
			}
		}
		return deleteFileIds;	
	}

	@Override
	public boolean saveMeeting(MeetingRecord query,Long userId) {
		// TODO Auto-generated method stub
		if(query.getId()!=null && query.getId().intValue()!=0){
			//更新
			meetingRecordDao.updateByIdSelective(query);
		}else{
			//插入
			//设置会议发起人
			query.setCreateUid(userId);
			//
			query.setMeetingResult(DictEnum.meetingResult.通过.getCode());
			//插入
			meetingRecordDao.insert(query);
		}
		//文件处理
		List<SopFile> sopFileList = query.getFiles();
		List<Long> fileIds = query.getFileIds();
		saveFiles(sopFileList, fileIds, query.getId(), query.getProjectId());
		return true;
	}


	@Override
	public Long selectCount(MeetingRecordBo query)
	{
		return meetingRecordDao.selectCount(query);
	}


	@Override
	@Transactional
	public void operateFlowMeeting(SopFile file, MeetingRecord meet) {
		// TODO Auto-generated method stub
		Long fid = null;
		if(file != null){
			if(file.getId() != null)
				sopFileDao.updateById(file);
			else
				fid = sopFileDao.insert(file);
			meet.setFileId(fid);
		}
		if(meet != null){
			if(meet.getId() != null)
				meetingRecordDao.updateById(meet);
			else
				meetingRecordDao.insert(meet);
		}
	}


	
	
	
}