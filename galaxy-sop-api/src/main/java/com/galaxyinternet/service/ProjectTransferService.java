package com.galaxyinternet.service;

import java.util.List;

import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.project.ProjectTransfer;

/**
 * @author chenjianmei
 */
public interface ProjectTransferService extends BaseService<ProjectTransfer> {
	
	List<ProjectTransfer> applyTransferData(Long pid);
	
	/**
	 * 项目移交申请
	 * 1.生成移交记录
	 * 2.生成待办任务
	 * @param projectTransfer
	 */
	void applyProjectTransfer(ProjectTransfer projectTransfer);
	
	/**
	 * 撤销项目移交
	 * 1.修改项目移交记录
	 * 2.修改待办任务
	 */
	void undoProjectTransfer(ProjectTransfer projectTransfer);
	/**
	 * 接收项目移交动作
	 */
	void receiveProjectTransfer(ProjectTransfer projectTransfer);
	
	/**
	 * 拒接项目移交
	 */
	void rejectProjectTransfer(ProjectTransfer projectTransfer);
	
	/**
	 * 将项目移交中的项目放入Redis
	 * @param cache
	 * @param pid
	 */
	void setTransferProjectInRedis(Cache cache, Long pid);
	
	/**
	 * 当撤销、接收、拒接时，从Redis中移除对应的项目
	 * @param cache
	 * @param pid
	 */
	void removeTransferProjectFromRedis(Cache cache, Long pid);
	
	
	
}

