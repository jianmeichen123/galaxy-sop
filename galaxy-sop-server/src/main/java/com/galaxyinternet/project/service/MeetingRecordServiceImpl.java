package com.galaxyinternet.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.dao.project.MeetingRecordDao;
import com.galaxyinternet.dao.project.MeetingSchedulingDao;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.soptask.SopTaskDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.model.project.Project;
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
	private MeetingSchedulingDao meetingSchedulingDao;
	
	
	@Override
	protected BaseDao<MeetingRecord, Long> getBaseDao() {
		return this.meetingRecordDao;
	}

	@Override
	@Transactional
	public Long insertMeet(MeetingRecord meetingRecord,Long userId) {
		Long id = getBaseDao().insert(meetingRecord);
		
		// 会议结论： 待定(默认)、 否决、 通过
		// 会议类型 2:内评会、3：CEO评审、 4:立项会、 7投决会、
		if (meetingRecord.getMeetingType() != null && meetingRecord.getMeetingType().equals("内评会")) {
			if (meetingRecord.getMeetingResult() != null && !meetingRecord.getMeetingResult().equals("待定")) {
				lph(meetingRecord.getProjectId(), meetingRecord.getMeetingResult());
			}
		} else if (meetingRecord.getMeetingType() != null && meetingRecord.getMeetingType().equals("CEO评审")) {
			if (meetingRecord.getMeetingResult() != null && !meetingRecord.getMeetingResult().equals("待定")) {
				ceops(meetingRecord.getProjectId(), meetingRecord.getMeetingResult());
			}
		} else if (meetingRecord.getMeetingType() != null && meetingRecord.getMeetingType().equals("立项会")) {
			pqcUpdate(meetingRecord);
			if (meetingRecord.getMeetingResult() != null && !meetingRecord.getMeetingResult().equals("待定")) {
				lxh(meetingRecord.getProjectId(), meetingRecord.getMeetingResult(), userId);
			}
		} else if (meetingRecord.getMeetingType() != null && meetingRecord.getMeetingType().equals("投决会")) {
			pqcUpdate(meetingRecord);
			if (meetingRecord.getMeetingResult() != null && !meetingRecord.getMeetingResult().equals("待定")) {
				tjh(meetingRecord.getProjectId(), meetingRecord.getMeetingResult(), userId);
			}
		}
		
		return id;
	}
	
	
	//内评会
	public void lph(Long pid,String meetingResult){
		Project pro = new Project();
		pro.setId(pid);
		
		if(meetingResult.equals("否决") ){ //update 项目状态
			pro.setProjectStatus("关闭"); 
			projectDao.updateById(pro);
			
		}else if(meetingResult.equals("通过") ){ //update 项目进度
			pro.setProjectProgress("CEO评审");
			pro.setProjectStatus("待定"); 
			projectDao.updateById(pro);
		}
	}
	
	//CEO评审  
	public void ceops(Long pid,String meetingResult){
		Project pro = new Project();
		pro.setId(pid);
		
		if(meetingResult.equals("否决") ){ //update 项目状态
			pro.setProjectStatus("关闭"); 
			projectDao.updateById(pro);
			
		}else if(meetingResult.equals("通过") ){ //update 项目状态
			pro.setProjectStatus("通过"); 
			projectDao.updateById(pro);
		}
	}
	
	//会议排期池 修改
	public void pqcUpdate(MeetingRecord meetingRecord){
		MeetingScheduling ms = new MeetingScheduling();
		ms.setProjectId(meetingRecord.getProjectId());
		ms.setMeetingDate(meetingRecord.getMeetingDate());
		ms.setMeetingType(meetingRecord.getMeetingType());
		
		if (meetingRecord.getMeetingResult() != null && meetingRecord.getMeetingResult().equals("待定")) {
			ms.setStatus("待定");
		}else if (meetingRecord.getMeetingResult() != null && meetingRecord.getMeetingResult().equals("否决")) {
			ms.setStatus("否决");
		}if (meetingRecord.getMeetingResult() != null && meetingRecord.getMeetingResult().equals("通过")) {
			ms.setStatus("通过");
		}
		
		meetingSchedulingDao.updateCountBySelective(ms);
	}
		
	//立项会
	public void lxh(Long pid,String meetingResult,Long userId){
		Project pro = new Project();
		pro.setId(pid);
		
		if(meetingResult.equals("否决") ){ 
			//update 项目状态
			pro.setProjectStatus("关闭"); 
			projectDao.updateById(pro);
			
		}else if(meetingResult.equals("通过") ){
			//update 项目进度
			pro.setProjectProgress("投资意向书");
			pro.setProjectStatus("待定"); 
			projectDao.updateById(pro);

			//投资意向书  任务生成
			SopTask task = new SopTask();
			task.setProjectId(pid);                     //项目id
			task.setTaskDestination("投资经理");  		//任务分派到: 投资经理
			task.setTaskName("上传投资意向书");          //任务名称：    上传投资意向书
			task.setTaskReceiveUid(userId);             //任务认领人id 
			task.setTaskStatus("待完工");				//任务状态: 2:待完工
			task.setTaskType("协同");					//任务类型    协同
			
			sopTaskDao.insert(task);
			
		}
	}
	
	
	//投决会
	public void tjh(Long pid,String meetingResult,Long userId){
		Project pro =  projectDao.selectById(pid);
		
		if(meetingResult.equals("否决") ){
			//update 项目状态
			pro.setProjectStatus("关闭"); 
			projectDao.updateById(pro);
			
		}else if(meetingResult.equals("通过") ){ 
			//update 项目进度
			pro.setProjectProgress("投资协议");
			pro.setProjectStatus("待定"); 
			projectDao.updateById(pro);
			
			//投资协议  任务生成
			SopTask task1 = new SopTask();
			task1.setProjectId(pid);                     //项目id
			task1.setTaskDestination("投资经理");  		//任务分派到: 投资经理
			task1.setTaskName("上传投资协议");          //任务名称：   上传投资协议
			task1.setTaskReceiveUid(userId);             //任务认领人id 
			task1.setTaskStatus("待完工");				//任务状态: 2:待完工
			task1.setTaskType("协同");					//任务类型    协同
			sopTaskDao.insert(task1);
			
			if(pro.getProjectType().equals("外部投资")){
				//股权转让协议  任务生成
				SopTask task2 = new SopTask();
				task2.setProjectId(pid);                    //项目id
				task2.setTaskDestination("投资经理");  		//任务分派到: 投资经理
				task2.setTaskName("上传股权转让协议");       //任务名称：  上传股权转让协议
				task2.setTaskReceiveUid(userId);            //任务认领人id 
				task2.setTaskStatus("待完工");				//任务状态: 2:待完工
				task2.setTaskType("协同");					//任务类型    协同
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
		
		if(mpage.getContent()!=null && mpage.getContent().size()>0){
			//file实体
			for(MeetingRecordBo ib : mpage.getContent()){
				//查询附件信息
				ib.setFname("");
				ib.setFuri("");
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
		project.setProjectProgress("立项会");
		project.setProjectStatus("待定");
		int i = projectDao.updateById(project);
		
		MeetingScheduling ms = new MeetingScheduling();
		ms.setProjectId(project.getId());
		ms.setMeetingType("立项会");
		ms.setStatus("新进");
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
	public void upTermSheetSign(Project project,Long userId){
		project.setProjectProgress("尽职调查");
		project.setProjectStatus("待定");
		int i = projectDao.updateById(project);
		
		//业务dd  任务生成
		SopTask task1 = new SopTask();
		task1.setProjectId(project.getId());         //项目id
		task1.setTaskDestination("投资经理");  		 //任务分派到: 投资经理
		task1.setTaskName("上传业务尽职调查报告");    //任务名称：  上传股权转让协议
		task1.setTaskReceiveUid(userId);             //任务认领人id 
		task1.setTaskStatus("待完工");				 //任务状态: 2:待完工
		task1.setTaskType("协同");					 //任务类型    协同
		sopTaskDao.insert(task1);
		
		//人事dd  任务生成
		SopTask task2 = new SopTask();
		task2.setProjectId(project.getId());         //项目id
		task2.setTaskDestination("人事部");  		 //任务分派到: 投资经理
		task2.setTaskName("上传人事尽职调查报告");        //任务名称：  上传股权转让协议
		task2.setTaskStatus("待认领");				 //任务状态: 2:待完工
		task2.setTaskType("协同");					 //任务类型    协同
		sopTaskDao.insert(task2);
		
		//财务dd  任务生成
		SopTask task3 = new SopTask();
		task3.setProjectId(project.getId());         //项目id
		task3.setTaskDestination("财务部");  		 //任务分派到: 投资经理
		task3.setTaskName("上传财务尽职调查报告");     //任务名称：  上传股权转让协议
		task3.setTaskStatus("待认领");				 //任务状态: 2:待完工
		task3.setTaskType("协同");					 //任务类型    协同
		sopTaskDao.insert(task3);
		
		//法务dd  任务生成
		SopTask task4 = new SopTask();
		task4.setProjectId(project.getId());         //项目id
		task4.setTaskDestination("法务部");  		 //任务分派到: 投资经理
		task4.setTaskName("上传法务尽职调查报告");        //任务名称：  上传股权转让协议
		task4.setTaskStatus("待认领");				 //任务状态: 2:待完工
		task4.setTaskType("协同");					 //任务类型    协同
		sopTaskDao.insert(task4);
		
		
	}
	
	
	/**
	 * 尽职调查              申请投决会排期，    
	 * 				修改项目进度、状态；
	 * 				新建 投决会 排期；
	 * @param   project 
	 * @return
	 */
	@Override
	@Transactional
	public void decisionSchedule(Project project){
		project.setProjectProgress("投决会");
		project.setProjectStatus("待定");
		int i = projectDao.updateById(project);
		
		MeetingScheduling ms = new MeetingScheduling();
		ms.setProjectId(project.getId());
		ms.setMeetingType("投决会");
		ms.setStatus("新进");
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
		project.setProjectProgress("股权交割");
		project.setProjectStatus("待定");
		int i = projectDao.updateById(project);
		
		//财务  任务生成
		SopTask task3 = new SopTask();
		task3.setProjectId(project.getId());         //项目id
		task3.setTaskDestination("财务部");  		 //任务分派到: 投资经理
		task3.setTaskName("上传资金拨付凭证");        //任务名称：  上传资金拨付凭证
		task3.setTaskStatus("待认领");				 //任务状态: 2:待认领
		task3.setTaskType("协同");					 //任务类型    协同
		sopTaskDao.insert(task3);
		
		//法务  任务生成
		SopTask task4 = new SopTask();
		task4.setProjectId(project.getId());         //项目id
		task4.setTaskDestination("法务部");  		 //任务分派到: 投资经理
		task4.setTaskName("上传工商变更登记凭证");        //任务名称：  上传工商变更登记凭证
		task4.setTaskStatus("待认领");				 //任务状态: 2:待认领
		task4.setTaskType("协同");					 //任务类型    协同
		sopTaskDao.insert(task4);
		
	}
	
	
	
	
	
	
}