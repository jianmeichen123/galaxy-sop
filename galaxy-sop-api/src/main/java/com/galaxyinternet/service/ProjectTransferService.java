package com.galaxyinternet.service;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.ProjectTransfer;

/**
 * @author chenjianmei
 */
public interface ProjectTransferService extends BaseService<ProjectTransfer> {
	
	
	
	/**
	 * 接收项目移交动作
	 * 1.sop_project[project_departId、create_uname、create_uid、project_careerline（暂时无用）]
	 * 2.sop_file[career_line]
	 *   sop_voucher_file[career_line]
	 *   注:在创建项目时，即已经录入了，在档案管理员有按部门搜索的
	 * 3.sop_meeting_record[create_uid]
	 * 注:编辑会议时，校验使用
	 * 4.sop_interview_record[created_id]
	 * 5.将原投资经理该项目的未完成的待办任务切换给接收人
	 */
	void receiveProjectTransfer(ProjectTransfer projectTransfer, Long createId, String createName, Long departmentId);
	
	
	
	
}

