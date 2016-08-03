package com.galaxyinternet.service;

import java.util.List;

import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.ProjectTransfer;

/**
 * @author chenjianmei
 */
public interface ProjectTransferService extends BaseService<ProjectTransfer> {
	
	/**
	 * 根据项目ID查询其所属的项目移交中的申请记录
	 * @param pid
	 * @return
	 */
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
	 * 1.sop_project[project_departId、create_uname、create_uid、project_careerline（暂时无用）]
	 * 2.sop_file[career_line]
	 *   sop_voucher_file[career_line]
	 *   注:在创建项目时，即已经录入了，在档案管理员有按部门搜索的
	 * 3.sop_meeting_record[create_uid]
	 *   注:编辑会议时，校验使用
	 */
	void receiveProjectTransfer(ProjectTransfer projectTransfer, Long createId, String createName, Long departmentId);
	
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

