package com.galaxyinternet.iosMessage.handle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.iosMessage.handleInter.IosMessageHandler;
import com.galaxyinternet.model.iosMessage.IosMessage;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.service.ProjectService;
@Component
public class IosTaskgoClaimtMessageHandler implements IosMessageHandler
{
	@Autowired
	private ProjectService projectService;
	
	private Map<String,String> map = new HashMap<String,String>();
	public IosTaskgoClaimtMessageHandler()
	{
		map.put("8.1", SopConstant.TASK_NAME_RSJD);
		map.put("8.2", SopConstant.TASK_NAME_CWJD);
		map.put("8.3", SopConstant.TASK_NAME_FWJD);
		//map.put("8.4", "上传工商转让凭证");
		//map.put("8.5", SopConstant.TASK_NAME_ZJBF);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int getOrder()
	{
		return 8;
	}

	@Override
	public boolean support(IosMessage message)
	{
		return message != null && map.containsKey(message.getMessageType());
	}

	@Override
	public IosMessage handle(IosMessage message)
	{
		//XXX项目的【人事/法务/财务尽职调查任务】，已经被XXX认领，请您关注
		
		StringBuffer content = new StringBuffer();
		content.append(message.getProjectName()).append("项目的")
		.append("【")
		.append(map.get(message.getMessageType()).replace("上传", "").replace("报告", "任务"))
		.append("】")
		.append("，已经被")
		.append(message.getOperator())
		.append("认领");
		message.setContent(content.append(message.getContent()).toString());
		
		Project project = projectService.queryById(message.getProjectId());
		Long tzjlId = project.getCreateUid();
		List<String> hrrIdsS = new ArrayList<String>();
		hrrIdsS.add(String.valueOf(tzjlId));
		message.setUidList(hrrIdsS);
		
		return message;
	}

}
