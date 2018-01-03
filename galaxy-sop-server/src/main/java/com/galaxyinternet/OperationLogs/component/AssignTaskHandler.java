package com.galaxyinternet.OperationLogs.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxyinternet.common.annotation.RecordType;
import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.soptask.SopTaskDao;
import com.galaxyinternet.model.operationLog.OperationLogType;
import com.galaxyinternet.model.operationLog.OperationLogs;
import com.galaxyinternet.model.operationLog.SopStage;
import com.galaxyinternet.model.operationLog.Target;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.platform.constant.PlatformConst;
@Component
public class AssignTaskHandler implements OperationLogHandler
{
	@Autowired
	private SopTaskDao taskDao;
	@Autowired
	private ProjectDao projectDao;
	private Map<Integer, String> contentMap = new HashMap<>();
	private Map<Integer, String> stageMap = new HashMap<>();

	public AssignTaskHandler()
	{
		contentMap.put(SopConstant.TASK_FLAG_RSJD, Target.DUE_DILIGENCE_RS_JD.getTargetName());
		contentMap.put(SopConstant.TASK_FLAG_CWJD, Target.DUE_DILIGENCE_CW_JD.getTargetName());
		contentMap.put(SopConstant.TASK_FLAG_FWJD, Target.DUE_DILIGENCE_FW_JD.getTargetName());
		contentMap.put(SopConstant.TASK_FLAG_GSBG, Target.DUE_DILIGENCE_FW_GSBG.getTargetName());
		contentMap.put(SopConstant.TASK_FLAG_ZJBF, Target.DUE_DILIGENCE_CW_FKPZ.getTargetName());
		
		stageMap.put(SopConstant.TASK_FLAG_RSJD, SopStage.DUE_DILIGENCE_INVESTIGATION.getStageName());
		stageMap.put(SopConstant.TASK_FLAG_CWJD, SopStage.DUE_DILIGENCE_INVESTIGATION.getStageName());
		stageMap.put(SopConstant.TASK_FLAG_FWJD, SopStage.DUE_DILIGENCE_INVESTIGATION.getStageName());
		stageMap.put(SopConstant.TASK_FLAG_GSBG, SopStage.EQUITY_DELIVERY_STAGE.getStageName());
		stageMap.put(SopConstant.TASK_FLAG_ZJBF, SopStage.EQUITY_DELIVERY_STAGE.getStageName());
	}

	@Override
	public int getOrder()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean support(OperationLogParams params)
	{
		return OperationLogType.ASSIGN_TASK.equals(params.getType());
	}

	@Override
	public List<OperationLogs> handle(OperationLogParams params)
	{
		List<OperationLogs> list = new ArrayList<>();
		OperationLogType type = params.getType();
		Map<String,Object> map = params.getParams();
		User user = params.getUser();
		Long[] ids = (Long[])map.get(PlatformConst.REQUEST_SCOPE_TASK_IDS);
		String reason = (String)map.get(PlatformConst.REQUEST_SCOPE_MESSAGE_REASON);
		SopTask task = null;
		Project project = null;
		OperationLogs entity = null;
		for(Long id : ids)
		{
			task = taskDao.selectById(id);
			project = projectDao.selectById(task.getProjectId());
			entity = new OperationLogs();
			entity.setOperationContent(contentMap.get(task.getTaskFlag()));
			entity.setOperationType(type.getType());
			entity.setUid(user.getId());
			entity.setUname(user.getRealName());
			entity.setDepartName(user.getDepartmentName());
			entity.setUserDepartid(user.getDepartmentId());
			entity.setProjectName(project.getProjectName());
			entity.setProjectId(task.getProjectId());
			entity.setSopstage(stageMap.get(task.getTaskFlag()));
			entity.setReason(reason);
			entity.setRecordType(RecordType.TASK.getType());
			entity.setRecordId(id);
			list.add(entity);
		}
		return list;
	}

}
