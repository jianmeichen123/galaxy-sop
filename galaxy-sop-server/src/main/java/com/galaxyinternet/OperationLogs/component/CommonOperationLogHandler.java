package com.galaxyinternet.OperationLogs.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.galaxyinternet.common.annotation.RecordType;
import com.galaxyinternet.model.operationLog.OperationLogType;
import com.galaxyinternet.model.operationLog.OperationLogs;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.platform.constant.PlatformConst;

@Component
public class CommonOperationLogHandler implements OperationLogHandler
{

	@Override
	public int getOrder()
	{
		return LOWEST_PRECEDENCE;
	}

	@Override
	public boolean support(OperationLogParams params)
	{
		return params != null;
	}

	@Override
	public List<OperationLogs> handle(OperationLogParams params)
	{
		List<OperationLogs> list = new ArrayList<>();
		OperationLogType type = params.getType();
		User user = params.getUser();
		Map<String, Object> map = params.getParams();
		RecordType recordType = params.getRecordType();

		OperationLogs entity = new OperationLogs();

		entity.setOperationContent(type.getContent());
		if (type.getUniqueKey().contains("progress/reject"))
		{
			entity.setOperationContent(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_PROJECT_NAME)));
		}
		entity.setOperationType(type.getType());
		entity.setUid(user.getId());
		entity.setUname(user.getRealName());
		entity.setDepartName(user.getDepartmentName());
		entity.setUserDepartid(user.getDepartmentId());

		entity.setProjectName(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_PROJECT_NAME)));
		entity.setProjectId(Long.valueOf(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_PROJECT_ID))));

		if (map.containsKey(PlatformConst.REQUEST_SCOPE_PROJECT_PROGRESS) && map.get(PlatformConst.REQUEST_SCOPE_PROJECT_PROGRESS) != null)
		{
			entity.setSopstage(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_PROJECT_PROGRESS)));
		} else
		{
			Object flag = map.get(PlatformConst.REQUEST_SCOPE_MESSAGE_NUM);
			Object obj = map.get(PlatformConst.REQUEST_SCOPE_MESSAGE_STAGE);
			if (null != flag && null != obj)
			{
				boolean f = (boolean) flag;
				String stage = (String) obj;
				if (f == true && !"".equals(stage))
				{
					entity.setSopstage(stage);
				}
			} else
			{
				entity.setSopstage(type.getSopstage());
			}
		}
		entity.setReason(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_MESSAGE_REASON)));
		entity.setRecordType(recordType.getType());
		list.add(entity);
		return list;
	}

}
