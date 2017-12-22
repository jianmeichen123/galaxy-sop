package com.galaxyinternet.OperationLogs.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.galaxyinternet.common.annotation.RecordType;
import com.galaxyinternet.model.operationLog.OperationLogType;
import com.galaxyinternet.model.operationLog.OperationLogs;
import com.galaxyinternet.model.operationLog.SopStage;
import com.galaxyinternet.model.operationLog.Target;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.platform.constant.PlatformConst;
/**
 * 上传任务文档
 * @author wangsong
 *
 */
@Component
public class UploadTaskFileHandler implements OperationLogHandler
{
	/**
	 * 5.5	上传/更新人事尽调
	 */
	private String MESSAGE_TYPE_RSJD = "_5.5";
	/**
	 * 5.6	上传/更新财务尽调
	 */
	private String MESSAGE_TYPE_CWJD = "_5.6";
	/**
	 * 5.7	上传/更新法务尽调
	 */
	private String MESSAGE_TYPE_FWJD = "_5.7";
	/**
	 * 5.10	上传/更新股权变更凭证
	 */
	private String MESSAGE_TYPE_GSZR = "_5.10";
	/**
	 * 5.11	上传/更新财务打款凭证
	 */
	private String MESSAGE_TYPE_ZJBF = "_5.11";
	
	private Map<String, String> contentMap = new HashMap<>();
	private Map<String, String> stageMap = new HashMap<>();
	
	public UploadTaskFileHandler()
	{
		contentMap.put(MESSAGE_TYPE_RSJD, Target.DUE_DILIGENCE_RS_JD.getTargetName());
		contentMap.put(MESSAGE_TYPE_CWJD, Target.DUE_DILIGENCE_CW_JD.getTargetName());
		contentMap.put(MESSAGE_TYPE_FWJD, Target.DUE_DILIGENCE_FW_JD.getTargetName());
		contentMap.put(MESSAGE_TYPE_GSZR, Target.DUE_DILIGENCE_FW_GSBG.getTargetName());
		contentMap.put(MESSAGE_TYPE_ZJBF, Target.DUE_DILIGENCE_CW_FKPZ.getTargetName());
		
		stageMap.put(MESSAGE_TYPE_RSJD, SopStage.DUE_DILIGENCE_INVESTIGATION.getStageName());
		stageMap.put(MESSAGE_TYPE_CWJD, SopStage.DUE_DILIGENCE_INVESTIGATION.getStageName());
		stageMap.put(MESSAGE_TYPE_FWJD, SopStage.DUE_DILIGENCE_INVESTIGATION.getStageName());
		stageMap.put(MESSAGE_TYPE_GSZR, SopStage.EQUITY_DELIVERY_STAGE.getStageName());
		stageMap.put(MESSAGE_TYPE_ZJBF, SopStage.EQUITY_DELIVERY_STAGE.getStageName());
	}

	@Override
	public int getOrder()
	{
		return 101;
	}

	@Override
	public boolean support(OperationLogParams params)
	{
		OperationLogType type = params.getType();
		if(type == null)
		{
			return false;
		}
		return OperationLogType.UPLOAD_JD.equals(type) || OperationLogType.UPDATE_JD.equals(type);
	}

	@Override
	public List<OperationLogs> handle(OperationLogParams params)
	{
		List<OperationLogs> list = new ArrayList<>();
		OperationLogType type = params.getType();
		User user = params.getUser();
		Map<String, Object> map = params.getParams();
		String messageType = map.get(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE)+"";
		Long recordId = Long.parseLong(map.get(PlatformConst.REQUEST_SCOPE_RECORD_ID)+"");
		String reason = null;
		if(map.containsKey(PlatformConst.REQUEST_SCOPE_MESSAGE_REASON))
		{
			reason = map.get(PlatformConst.REQUEST_SCOPE_MESSAGE_REASON)+"";
		}
		OperationLogs entity = new OperationLogs();
		entity.setOperationContent(contentMap.get(messageType));
		entity.setOperationType(type.getType());
		entity.setUid(user.getId());
		entity.setUname(user.getRealName());
		entity.setDepartName(user.getDepartmentName());
		entity.setUserDepartid(user.getDepartmentId());
		entity.setProjectName(String.valueOf(map.get(PlatformConst.REQUEST_SCOPE_PROJECT_NAME)));
		entity.setSopstage(stageMap.get(messageType));
		entity.setReason(reason);
		entity.setRecordType(RecordType.TASK.getType());
		entity.setRecordId(recordId);
		list.add(entity);
		return list;
	}

}
