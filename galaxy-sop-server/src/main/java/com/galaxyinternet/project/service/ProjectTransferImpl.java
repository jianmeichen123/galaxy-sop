package com.galaxyinternet.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.dao.project.ProjectTransferDao;
import com.galaxyinternet.dao.soptask.SopTaskDao;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.project.ProjectTransfer;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.service.ProjectTransferService;
import com.galaxyinternet.utils.SopConstatnts;


@Service("com.galaxyinternet.service.ProjectTransferService")
public class ProjectTransferImpl extends BaseServiceImpl<ProjectTransfer> implements ProjectTransferService {
	
	@Autowired
	private ProjectTransferDao projectTransferDao;
	@Autowired
	private SopTaskDao sopTaskDao;
	
	
	
	
	@Override
	protected BaseDao<ProjectTransfer, Long> getBaseDao() {
		return this.projectTransferDao;
	}
	@Override
	public List<ProjectTransfer> applyTransferData(Long pid) {
		return projectTransferDao.selectApplyRecord(pid);
	}
	
	@Transactional
	@Override
	public void applyProjectTransfer(ProjectTransfer projectTransfer) {
		projectTransferDao.insert(projectTransfer);
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
	}
	
	
	@Override
	public void undoProjectTransfer(ProjectTransfer projectTransfer) {
		projectTransferDao.updateByIdSelective(projectTransfer);
		SopTask task = new SopTask();
		task.setProjectId(projectTransfer.getProjectId());
		task.setTaskFlag(SopConstatnts.TaskCode._accept_project_flag_);
		sopTaskDao.updateByIdSelective(task);
	}
	
	@Override
	public void setTransferProjectInRedis(Cache cache, Project project) {
		synchronized (this) {
			List<Long> transferProjectList = null;
			Object cacheObj = cache.get(SopConstatnts.Redis._transfer_projects_key_);
			if(cacheObj == null){
				transferProjectList = new ArrayList<Long>();
			}else{
				transferProjectList = (List<Long>) cacheObj;
			}
			transferProjectList.add(project.getId());
			cache.set(SopConstatnts.Redis._transfer_projects_key_, transferProjectList);
		}
	}
	@Override
	public void removeTransferProjectFromRedis(Cache cache, Project project) {
		synchronized (this) {
			List<Long> transferProjectList = null;
			Object cacheObj = cache.get(SopConstatnts.Redis._transfer_projects_key_);
			if(cacheObj != null){
				transferProjectList = (List<Long>) cacheObj;
				transferProjectList.remove(project);
			}
		}
	}
	
}