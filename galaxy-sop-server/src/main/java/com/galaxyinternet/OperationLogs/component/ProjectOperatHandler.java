package com.galaxyinternet.OperationLogs.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxyinternet.common.annotation.RecordType;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.model.operationLog.OperationLogType;
import com.galaxyinternet.model.operationLog.OperationLogs;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.platform.constant.PlatformConst;
/**
 * 项目指派，移交，删除
 * @author wangsong
 *
 */
@Component
public class ProjectOperatHandler implements OperationLogHandler
{
	@Autowired
	private ProjectDao projectDao;

	@Override
	public int getOrder()
	{
		return 0;
	}

	@Override
	public boolean support(OperationLogParams params)
	{
		OperationLogType type = params.getType();
		if(type == null)
		{
			return false;
		}
		return OperationLogType.TRANSFER_PROJECT.equals(type) 
				|| OperationLogType.ASSIGN_PROJECT.equals(type)
				|| OperationLogType.DELETE_PROJECT.equals(type);
	}

	@SuppressWarnings("unchecked")
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
		if( !map.containsKey(PlatformConst.REQUEST_SCOPE_RECORD_IDS))
		{
			return list;
		}
		List<Long> projectIds = (List<Long>)map.get(PlatformConst.REQUEST_SCOPE_RECORD_IDS);
		if( projectIds == null || projectIds.size() == 0)
		{
			return list;
		}
		//项目日志
		OperationLogs entity = null;
		for(Long projectId : projectIds)
		{
			Project project = projectDao.selectById(projectId);
			entity = new OperationLogs();
			entity.setOperationContent(type.getContent());
			entity.setOperationType(type.getType());
			entity.setUid(user.getId());
			entity.setUname(user.getRealName());
			entity.setDepartName(user.getDepartmentName());
			entity.setUserDepartid(user.getDepartmentId());
			entity.setProjectName(project.getProjectName());
			entity.setProjectId(projectId);
			entity.setSopstage(type.getSopstage());
			entity.setReason(reason);
			entity.setRecordType(RecordType.PROJECT.getType());
			list.add(entity);
		}
		return list;
	}

}
