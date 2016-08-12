package com.galaxyinternet.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.dao.project.InterviewRecordDao;
import com.galaxyinternet.dao.project.MeetingRecordDao;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.project.ProjectTransferDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.dao.sopfile.SopVoucherFileDao;
import com.galaxyinternet.dao.soptask.SopTaskDao;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.project.ProjectTransfer;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.sopfile.SopVoucherFile;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.service.ProjectTransferService;
import com.galaxyinternet.utils.SopConstatnts;


@Service("com.galaxyinternet.service.ProjectTransferService")
public class ProjectTransferImpl extends BaseServiceImpl<ProjectTransfer> implements ProjectTransferService {
	
	@Autowired
	private ProjectTransferDao projectTransferDao;
	@Autowired
	private SopTaskDao sopTaskDao;
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private MeetingRecordDao meetingRecordDao;
	@Autowired
	private SopFileDao sopFileDao;
	@Autowired
	private SopVoucherFileDao sopVoucherFileDao;
	@Autowired
	private InterviewRecordDao interviewRecordDao;
	
	
	@Override
	protected BaseDao<ProjectTransfer, Long> getBaseDao() {
		return this.projectTransferDao;
	}
	@Override
	public List<ProjectTransfer> applyTransferData(Long pid) {
		return projectTransferDao.selectApplyRecord(pid);
	}
	
	@Override
	@Transactional
	public Long  applyProjectTransfer(ProjectTransfer projectTransfer) {
		Long id=projectTransferDao.insert(projectTransfer);
		SopTask task = new SopTask();
		task.setProjectId(projectTransfer.getProjectId());
		task.setTaskName("接收项目");
		task.setTaskType(DictEnum.taskType.协同办公.getCode());
		task.setTaskFlag(SopConstatnts.TaskCode._accept_project_flag_);
		task.setTaskOrder(0);
		task.setDepartmentId(projectTransfer.getAfterDepartmentId());
		task.setAssignUid(projectTransfer.getAfterUid());
		task.setTaskStatus(DictEnum.taskStatus.待完工.getCode());
		sopTaskDao.insert(task);
		return id;
	}
	
	
	@Override
	@Transactional
	public void undoProjectTransfer(ProjectTransfer projectTransfer) {
		projectTransferDao.updateById(projectTransfer);
		SopTask task = new SopTask();
		task.setProjectId(projectTransfer.getProjectId());
		task.setTaskFlag(SopConstatnts.TaskCode._accept_project_flag_);
		task.setTaskStatus(DictEnum.taskStatus.已完成.getCode());
		task.setQueryTaskStatus(DictEnum.taskStatus.待完工.getCode());
		sopTaskDao.updateByIdSelective(task);
	}
	
	
	@Override
	@Transactional
	public void receiveProjectTransfer(ProjectTransfer projectTransfer, Long createId, String createName, Long departmentId) {
		projectTransferDao.updateById(projectTransfer);
		SopTask task = new SopTask();
		task.setProjectId(projectTransfer.getProjectId());
		task.setTaskFlag(SopConstatnts.TaskCode._accept_project_flag_);
		task.setTaskStatus(DictEnum.taskStatus.已完成.getCode());
		task.setQueryTaskStatus(DictEnum.taskStatus.待完工.getCode());
		sopTaskDao.updateByIdSelective(task);
		Project project = projectDao.selectById(projectTransfer.getProjectId());
		project.setCreateUid(createId);
		project.setCreateUname(createName);
		project.setProjectDepartid(departmentId);
		projectDao.updateById(project);
		
		MeetingRecord mr = new MeetingRecord();
		mr.setProjectId(project.getId());
		List<MeetingRecord> ms = meetingRecordDao.selectList(mr);
		if(ms != null){
			for(MeetingRecord m : ms){
				m.setCreateUid(createId);
				meetingRecordDao.updateById(m);
			}
		}
		
		SopFile file = new SopFile();
		file.setProjectId(project.getId());
		file.setCareerLine(departmentId);
		sopFileDao.updateDepartmentId(file);
		
		SopVoucherFile f = new SopVoucherFile();
		f.setProjectId(project.getId());
		f.setCareerLine(departmentId);
		sopVoucherFileDao.updateDepartmentId(f);
		
		InterviewRecord ir = new InterviewRecord();
		ir.setProjectId(project.getId());
		ir.setCreatedId(createId);
		ir.setUpdatedTime(System.currentTimeMillis());
		interviewRecordDao.updateById(ir);
		SopTask t = new SopTask();
		t.setDepartmentId(departmentId);
		t.setAssignUid(createId);
		t.setCreatedTime(System.currentTimeMillis());
		t.setUpdatedTime(System.currentTimeMillis());
		t.setProjectId(project.getId());
		t.setQueryAssignUid(projectTransfer.getBeforeUid());
		t.setTaskStatus(DictEnum.taskStatus.待完工.getCode());
		sopTaskDao.updateAtProjectTranfer(t);
	}
	
	@Override
	@Transactional
	public void rejectProjectTransfer(ProjectTransfer projectTransfer) {
		projectTransferDao.updateById(projectTransfer);
		SopTask task = new SopTask();
		task.setProjectId(projectTransfer.getProjectId());
		task.setTaskFlag(SopConstatnts.TaskCode._accept_project_flag_);
		task.setTaskStatus(DictEnum.taskStatus.已完成.getCode());
		task.setQueryTaskStatus(DictEnum.taskStatus.待完工.getCode());
		sopTaskDao.updateByIdSelective(task);
	}
	
	@Override
	public void setTransferProjectInRedis(Cache cache, Long pid) {
		synchronized (this) {
			List<Long> transferProjectList = null;
			Object cacheObj = cache.get(SopConstatnts.Redis._transfer_projects_key_);
			if(cacheObj == null){
				transferProjectList = new ArrayList<Long>();
			}else{
				transferProjectList = (List<Long>) cacheObj;
			}
			if(!transferProjectList.contains(pid)){
				transferProjectList.add(pid);
				cache.set(SopConstatnts.Redis._transfer_projects_key_, transferProjectList);
			}
		}
	}
	@Override
	public void removeTransferProjectFromRedis(Cache cache, Long pid) {
		synchronized (this) {
			List<Long> transferProjectList = null;
			Object cacheObj = cache.get(SopConstatnts.Redis._transfer_projects_key_);
			if(cacheObj != null){
				transferProjectList = (List<Long>) cacheObj;
				if(transferProjectList.contains(pid)){
					transferProjectList.remove(pid);
				}
				cache.set(SopConstatnts.Redis._transfer_projects_key_, transferProjectList);
			}
		}
	}
}