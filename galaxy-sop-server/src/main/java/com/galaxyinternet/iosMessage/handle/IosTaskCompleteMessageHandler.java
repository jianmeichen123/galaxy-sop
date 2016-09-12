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
public class IosTaskCompleteMessageHandler implements IosMessageHandler
{
	@Autowired
	private ProjectService projectService;
	
	private Map<String,String> map = new HashMap<String,String>();
	public IosTaskCompleteMessageHandler()
	{
		map.put("9.1", SopConstant.TASK_NAME_RSJD);
		map.put("9.2", SopConstant.TASK_NAME_CWJD);
		map.put("9.3", SopConstant.TASK_NAME_FWJD);
/*		map.put("9.4", SopConstant.TASK_NAME_GSBG);
		map.put("9.5", SopConstant.TASK_NAME_ZJBF);*/
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int getOrder()
	{
		return 9;
	}

	@Override
	public boolean support(IosMessage message)
	{
		return message != null && map.containsKey(message.getMessageType());
	}

	@Override
	public IosMessage handle(IosMessage message)
	{
		
		//XXX项目的【人事/法务/财务尽职调查任务】，已经完成资料上传，请您关注
		StringBuffer content = new StringBuffer();
		content.append(message.getProjectName()).append("项目的")
		.append("【")
		.append(map.get(message.getMessageType()).replace("上传", "").replace("报告", "任务"))
		.append("】")
		.append("，已经完成资料上传");
		message.setContent(content.append(message.getContent()).toString());
		
		Project project = projectService.queryById(message.getProjectId());
		Long tzjlId = project.getCreateUid();
		List<String> hrrIdsS = new ArrayList<String>();
		hrrIdsS.add(String.valueOf(tzjlId));
		message.setUidList(hrrIdsS);
		
		return message;
	}

}
