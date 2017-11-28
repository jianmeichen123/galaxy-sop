package com.galaxyinternet.OperationLogs.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.galaxyinternet.common.annotation.RecordType;
import com.galaxyinternet.model.operationLog.OperType;
import com.galaxyinternet.model.operationLog.OperationLogType;
import com.galaxyinternet.model.operationLog.OperationLogs;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.platform.constant.PlatformConst;
/**
 * 认领任务 - 任务、项目生成一条记录
 * @author wangsong
 *
 */
@Component
public class TaskClaimHandler implements OperationLogHandler
{

	@Override
	public int getOrder()
	{
		return 100;
	}

	@Override
	public boolean support(OperationLogParams params)
	{
		OperationLogType type = params.getType();
		if(type == null)
		{
			return false;
		}
		return OperType.CLAIMT.getOperationType().equals(type.getType());
	}

	@Override
	public List<OperationLogs> handle(OperationLogParams params)
	{
		OperationLogType type = params.getType();
		User user = params.getUser();
		Map<String, Object> map = params.getParams();
		List<OperationLogs> list = new ArrayList<>();
		String reason = null;
		if(map.containsKey(PlatformConst.REQUEST_SCOPE_MESSAGE_REASON))
		{
			reason = map.get(PlatformConst.REQUEST_SCOPE_MESSAGE_REASON)+"";
		}
		//项目日志
		OperationLogs entity = new OperationLogs();
		entity.setOperationContent(type.getContent());
		entity.setOperationType(type.getType());
		entity.setUid(user.getId());
		entity.setUname(user.getRealName());
		entity.setDepartName(user.getDepartmentName());
		entity.setUserDepartid(user.getDepartmentId());
		entity.setProjectName(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_PROJECT_NAME)));
		entity.setProjectId(Long.valueOf(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_PROJECT_ID))));
		entity.setSopstage(type.getSopstage());
		entity.setReason(reason);
		entity.setRecordType(RecordType.PROJECT.getType());
		list.add(entity);
		//任务日志
		entity = new OperationLogs();
		entity.setOperationContent(type.getContent());
		entity.setOperationType(type.getType());
		entity.setUid(user.getId());
		entity.setUname(user.getRealName());
		entity.setDepartName(user.getDepartmentName());
		entity.setUserDepartid(user.getDepartmentId());
		entity.setProjectName(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_PROJECT_NAME)));
		entity.setSopstage(type.getSopstage());
		entity.setReason(reason);
		entity.setRecordType(RecordType.TASK.getType());
		entity.setRecordId(Long.parseLong(map.get(PlatformConst.REQUEST_SCOPE_RECORD_ID)+""));
		list.add(entity);
		return list;
	}

}
