package com.galaxyinternet.project.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.dao.project.MeetingRecordDao;
import com.galaxyinternet.dao.project.MeetingSchedulingDao;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.dao.soptask.SopTaskDao;
import com.galaxyinternet.framework.core.constants.SqlId;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.file.UploadFileResult;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.soptask.SopTask;
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
	
	
	@Override
	protected BaseDao<MeetingRecord, Long> getBaseDao() {
		return this.meetingRecordDao;
	}

	
	//文件上传, 成功后插入 sopfile 数据库
	@Transactional
	public Long upfile(MultipartFile file,Long uid,String path,Long projectId,String projectProgress){
		Long fileId = null;
		try {
			UploadFileResult upResult = null;
			
			if(file != null){
				String fileName = file.getOriginalFilename(); // secondarytile.png  全名
				
				int dotPos = fileName.lastIndexOf(".");
				
				String key = String.valueOf(IdGenerator.generateId(OSSHelper.class));
				
				String ext = fileName.substring(dotPos);  // .png  
				
				File temp = new File(path,fileName);
				if (!temp.exists()) {
					temp.mkdirs();
				}
				
				file.transferTo(temp);  //存储临时文件
				
				upResult = OSSHelper.simpleUploadByOSS(temp,key);  //上传至阿里云
				
				//若文件上传成功
				if(upResult.getResult().getStatus()!=null && upResult.getResult().getStatus().equals(Status.OK)){
					
					SopFile sopFile = new SopFile();
					sopFile.setProjectId(projectId);
					sopFile.setProjectProgress(projectProgress);
					sopFile.setBucketName(upResult.getBucketName()); //bucketName
					sopFile.setFileKey(key);   //fileKey
					sopFile.setFileLength(upResult.getContentLength());  //文件大小
					sopFile.setFileName(fileName);  //文件名称 temp.getName()  upload4196736950003923576secondarytile.png
					sopFile.setFileUid(uid);	 //上传人
					//sopFile.setFileType("");   //存储类型
					//sopFile.setFileSource(Integer.parseInt(fileSource));  //档案来源
					//sopFile.setFileWorktype(fileWorkType);    //业务分类
					sopFile.setFileStatus(DictEnum.fileStatus.已上传.getCode());  //档案状态
					
					fileId = sopFileDao.insert(sopFile);
					
					//meetingRecord.setFileId(sopFile.getId());
				}else{
					throw new BusinessException("meeting service upfile failed");
				}
			}
		} catch (Exception e) {
			throw new BusinessException("meeting service upfile failed", e);
		}
		
		return fileId;
	}
	
	
	
	
	@Override
	@Transactional
	public Long insertMeet(MeetingRecord meetingRecord,Project project,MultipartFile file, String path,Long userid,Long udepartid) {
		
		if(file != null){
			Long fileId = upfile(file,userid,path,project.getId(),project.getProgress());
			meetingRecord.setFileId(fileId);
		}
		
		Long id = getBaseDao().insert(meetingRecord);
		
		// 会议结论： 待定(默认)、 否决、 通过
		// 会议类型 2:内评会、3：CEO评审、 4:立项会、 7投决会、
		if (meetingRecord.getMeetingType() != null && meetingRecord.getMeetingType().equals(DictEnum.meetingType.内评会.getCode())) {
			if (meetingRecord.getMeetingResult() != null && !meetingRecord.getMeetingResult().equals(DictEnum.meetingResult.待定.getCode())) {
				lph(meetingRecord.getProjectId(), meetingRecord.getMeetingResult());
			}
		} else if (meetingRecord.getMeetingType() != null && meetingRecord.getMeetingType().equals(DictEnum.meetingType.CEO评审.getCode())) {
			if (meetingRecord.getMeetingResult() != null && !meetingRecord.getMeetingResult().equals(DictEnum.meetingResult.待定.getCode())) {
				ceops(meetingRecord.getProjectId(), meetingRecord.getMeetingResult());
			}
		} else if (meetingRecord.getMeetingType() != null && meetingRecord.getMeetingType().equals(DictEnum.meetingType.立项会.getCode())) {
			pqcUpdate(meetingRecord);
			if (meetingRecord.getMeetingResult() != null && !meetingRecord.getMeetingResult().equals(DictEnum.meetingResult.待定.getCode())) {
				lxh(meetingRecord.getProjectId(), meetingRecord.getMeetingResult(), userid, udepartid);
			}
		} else if (meetingRecord.getMeetingType() != null && meetingRecord.getMeetingType().equals(DictEnum.meetingType.投决会.getCode())) {
			pqcUpdate(meetingRecord);
			if (meetingRecord.getMeetingResult() != null && !meetingRecord.getMeetingResult().equals(DictEnum.meetingResult.待定.getCode())) {
				tjh(meetingRecord.getProjectId(), project, meetingRecord.getMeetingResult(), userid ,udepartid);
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
			pro.setProjectStatus(DictEnum.meetingResult.通过.getCode()); 
			projectDao.updateById(pro);
		}
	}
	
	//会议排期池 修改
	@Transactional
	public void pqcUpdate(MeetingRecord meetingRecord){
		MeetingScheduling ms = new MeetingScheduling();
		ms.setProjectId(meetingRecord.getProjectId());
		ms.setMeetingDate(meetingRecord.getMeetingDate());
		ms.setMeetingType(meetingRecord.getMeetingType());
		
		if (meetingRecord.getMeetingResult() != null && meetingRecord.getMeetingResult().equals(DictEnum.meetingResult.待定.getCode())) {
			ms.setStatus(DictEnum.meetingResult.待定.getCode());
		}else if (meetingRecord.getMeetingResult() != null && meetingRecord.getMeetingResult().equals(DictEnum.meetingResult.否决.getCode())) {
			ms.setStatus(DictEnum.meetingResult.否决.getCode());
		}if (meetingRecord.getMeetingResult() != null && meetingRecord.getMeetingResult().equals(DictEnum.meetingResult.通过.getCode())) {
			ms.setStatus(DictEnum.meetingResult.通过.getCode());
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
			task.setAssignUid(udepartid);  	//任务分派到: 投资经理
			task.setTaskName("上传投资意向书");          //任务名称：    上传投资意向书
			task.setAssignUid(userid);             //任务认领人id 
			task.setTaskStatus(DictEnum.taskStatus.待完工.getCode());		//任务状态: 2:待完工
			task.setTaskType(DictEnum.taskType.协同办公.getCode());			//任务类型    协同
			
			sopTaskDao.insert(task);
			
		}
	}
	
	
	//投决会
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
			task1.setProjectId(pid);                     //项目id
			task1.setAssignUid(udepartid);  		//任务分派到: 投资经理
			task1.setTaskName("上传投资协议");          //任务名称：   上传投资协议
			task1.setAssignUid(userid);             //任务认领人id 
			task1.setTaskStatus(DictEnum.taskStatus.待完工.getCode());				//任务状态: 2:待完工
			task1.setTaskType(DictEnum.taskType.协同办公.getCode());				//任务类型    协同
			sopTaskDao.insert(task1);
			
			if(pro.getProjectType().equals(DictEnum.projectType.外部项目.getCode())){
				//股权转让协议  任务生成
				SopTask task2 = new SopTask();
				task2.setProjectId(pid);                    //项目id
				task1.setAssignUid(udepartid);  		//任务分派到: 投资经理
				task2.setTaskName("上传股权转让协议");       //任务名称：  上传股权转让协议
				task2.setAssignUid(userid);            //任务认领人id 
				task2.setTaskStatus(DictEnum.taskStatus.待完工.getCode());				//任务状态: 2:待完工
				task2.setTaskType(DictEnum.taskType.协同办公.getCode());					//任务类型    协同
				sopTaskDao.insert(task2);
			}
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
				String fileInfo = "";
				
				if(ib.getFileId()!=null){
					SopFile file = sopFileDao.selectById(ib.getFileId());
					
					if(file!=null){
						//ib.setFname(file.getFileName());
						ib.setFkey(file.getFileKey());
						fileInfo = "<a href=\"javascript:filedown("+ib.getFileId()+","+file.getFileKey()+");\" key=\""+ file.getFileKey()+"\">"+file.getFileName()+"</a>";
					}
				}
				ib.setHygk("<div style=\"text-align:left;margin-left:20%;\">会议日期："+ib.getMeetingDateStr()+"</br>会议结论："+ib.getMeetingResultStr()+"</br>会议录音："+fileInfo+"</div>");
			
				//ib.setProInfo("<div style=\"text-align:left;margin-left:20%;\">"+ib.getProName()+"</br>"+ib.getMeetingResultStr()+"</div>");
				ib.setProInfo(ib.getProName()+"</br>"+ib.getMeetingTypeStr());
			}
		}
		
		return mpage;
	}
	
	
	
	/**
	 * 立项会排期，    修改项目进度、状态
	 * 				新建 立项会 排期
	 * @param   project 
	 * @return
	 */
	@Override
	@Transactional
	public void projectSchedule(Project project){
		project.setProjectProgress(DictEnum.projectProgress.立项会.getCode());
		project.setProjectStatus(DictEnum.meetingResult.待定.getCode());
		int i = projectDao.updateById(project);
		
		MeetingScheduling ms = new MeetingScheduling();
		ms.setProjectId(project.getId());
		ms.setMeetingType(DictEnum.meetingType.立项会.getCode());
		ms.setMeetingCount(0);
		Long id = meetingSchedulingDao.insert(ms);
		
	}
	
	
	
	/**
	 * 投资意向书阶段，    上传  投资意向书-签署证明；
	 * 				更新项目阶段；
	 * 				生成任务;
	 * @param   project 
	 * @return
	 */
	@Override
	@Transactional
	public void upTermSheetSign(Project project,Long userid,Long departid){
		project.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
		project.setProjectStatus(DictEnum.meetingResult.待定.getCode());
		int i = projectDao.updateById(project);
		
		//业务dd  任务生成
		SopTask task1 = new SopTask();
		task1.setProjectId(project.getId());         //项目id
		task1.setAssignUid(departid);  		 //任务分派到: 投资经理
		task1.setTaskName("上传业务尽职调查报告");    //任务名称：  上传股权转让协议
		task1.setAssignUid(userid);             //任务认领人id 
		task1.setTaskStatus(DictEnum.taskStatus.待完工.getCode());				 //任务状态: 2:待完工
		task1.setTaskType(DictEnum.taskType.协同办公.getCode());					 //任务类型    协同
		sopTaskDao.insert(task1);
		
		//人事dd  任务生成
		SopTask task2 = new SopTask();
		task2.setProjectId(project.getId());         //项目id
		task2.setDepartmentId(SopConstant.DEPARTMENT_RS_ID);  		 //任务分派到: 投资经理
		task2.setTaskName("上传人事尽职调查报告");        //任务名称：  上传股权转让协议
		task2.setTaskStatus(DictEnum.taskStatus.待认领.getCode());				 //任务状态: 2:待完工
		task2.setTaskType(DictEnum.taskType.协同办公.getCode());					 //任务类型    协同
		sopTaskDao.insert(task2);
		
		//财务dd  任务生成
		SopTask task3 = new SopTask();
		task3.setProjectId(project.getId());         //项目id
		task3.setDepartmentId(SopConstant.DEPARTMENT_CW_ID);  		 //任务分派到: 投资经理
		task3.setTaskName("上传财务尽职调查报告");     //任务名称：  上传股权转让协议
		task3.setTaskStatus(DictEnum.taskStatus.待认领.getCode());				 //任务状态: 2:待完工
		task3.setTaskType(DictEnum.taskType.协同办公.getCode());					 //任务类型    协同
		sopTaskDao.insert(task3);
		
		//法务dd  任务生成
		SopTask task4 = new SopTask();
		task4.setProjectId(project.getId());         //项目id
		task4.setDepartmentId(SopConstant.DEPARTMENT_FW_ID);  		 //任务分派到: 投资经理
		task4.setTaskName("上传法务尽职调查报告");        //任务名称：  上传股权转让协议
		task4.setTaskStatus(DictEnum.taskStatus.待认领.getCode());				 //任务状态: 2:待完工
		task4.setTaskType(DictEnum.taskType.协同办公.getCode());					 //任务类型    协同
		sopTaskDao.insert(task4);
		
		
	}
	
	
	/**
	 * 尽职调查              申请  投决会 排期，    
	 * 				修改项目进度、状态；
	 * 				新建 投决会 排期；
	 * @param   project 
	 * @return
	 */
	@Override
	@Transactional
	public void decisionSchedule(Project project){
		project.setProjectProgress(DictEnum.projectProgress.投资决策会.getCode());
		project.setProjectStatus(DictEnum.meetingResult.待定.getCode());
		int i = projectDao.updateById(project);
		
		MeetingScheduling ms = new MeetingScheduling();
		ms.setProjectId(project.getId());
		ms.setMeetingType(DictEnum.meetingType.投决会.getCode());
		//ms.setStatus("新进");
		ms.setMeetingCount(0);
		Long id = meetingSchedulingDao.insert(ms);
		
	}
	
	
	
	/**
	 * 投资协议阶段，    上传  投资协议-签署证明；
	 * 				更新项目阶段；
	 * 				生成任务;
	 * @param   project 
	 * @return
	 */
	@Override
	@Transactional
	public void upInvestmentSign(Project project){
		project.setProjectProgress(DictEnum.projectProgress.股权交割.getCode());
		project.setProjectStatus(DictEnum.meetingResult.待定.getCode());
		int i = projectDao.updateById(project);
		
		//财务  任务生成
		SopTask task3 = new SopTask();
		task3.setProjectId(project.getId());         //项目id
		task3.setDepartmentId(SopConstant.DEPARTMENT_CW_ID);  		 //任务分派到: 投资经理
		task3.setTaskName("上传资金拨付凭证");        //任务名称：  上传资金拨付凭证
		task3.setTaskStatus(DictEnum.taskStatus.待认领.getCode());				 //任务状态: 2:待认领
		task3.setTaskType(DictEnum.taskType.协同办公.getCode());					 //任务类型    协同
		sopTaskDao.insert(task3);
		
		//法务  任务生成
		SopTask task4 = new SopTask();
		task4.setProjectId(project.getId());         //项目id
		task4.setDepartmentId(SopConstant.DEPARTMENT_FW_ID); 		 //任务分派到: 投资经理
		task4.setTaskName("上传工商变更登记凭证");        //任务名称：  上传工商变更登记凭证
		task4.setTaskStatus(DictEnum.taskStatus.待认领.getCode());				 //任务状态: 2:待认领
		task4.setTaskType(DictEnum.taskType.协同办公.getCode());					 //任务类型    协同
		sopTaskDao.insert(task4);
		
	}
	
	
	
	
	
	
}