package com.galaxyinternet.iosMessage.handle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.iosMessage.handleInter.IosMessageHandler;
import com.galaxyinternet.model.iosMessage.IosMessage;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.UserService;
@Component
public class IosMeetSchedulingMessageHandler implements IosMessageHandler
{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectService projectService;
	
	private static final long serialVersionUID = 1L;
	
	/**
		11.1	进入CEO评审会会议排期
		11.2	进入立项会会议排期
		11.3	进入投决会会议排期
	*/		
	private String ceo_schedul_type = "11.1";
	private String lxh_schedul_type = "11.2";
	private String tjh_schedul_type = "11.3";
	
	@Override
	public int getOrder()
	{
		return 11;
	}

	@Override
	public boolean support(IosMessage message)
	{
		return message != null && StringUtils.isNoneBlank(message.getMessageType()) && message.getMessageType().startsWith("11");
	}

	@Override
	public IosMessage handle(IosMessage message)
	{	
		
		//boolean toceo = true;
		//boolean tohhr = true;
		
		StringBuffer content = new StringBuffer();
		
		content.append(message.getProjectName()).append("项目的");
		
		if(message.getMessageType().equals(ceo_schedul_type)){
			content.append("【CEO评审会】");
		}else if(message.getMessageType().equals(lxh_schedul_type)){
			content.append("【立项会】");
		}else if(message.getMessageType().equals(tjh_schedul_type)){
			content.append("【投决会】");
		}else{
			return null;
		}
		
		if(message.getKeyword().contains("cancle:")){
			/*content.append("原安排于");
			content.append(message.getKeyword().replace("cancle:", ""));
			content.append("的会议已取消");*/
			return message;
		}else if(message.getKeyword().contains("update:")){
			content.append("已调整到");
			content.append(message.getKeyword().replace("update:", ""));
			content.append("召开");
		}else if(message.getKeyword().contains("insert:")){
			content.append("已安排在");
			content.append(message.getKeyword().replace("insert:", ""));
			content.append("召开");
		}
		
		if(!"operate".equals(message.getKeyword())){
			message.setContent(content.append(message.getContent()).toString());
		}
		
		
		Project project = projectService.queryById(message.getProjectId());
		Long tzjlId = project.getCreateUid();
		Long departId = project.getProjectDepartid();
		List<Long> hrrIds = getUsersId(queryUsersByRole(UserConstant.HHR,departId));
		//List<Long> ceoIds = getUsersId(queryUsersByRole(UserConstant.CEO,null));
		
		List<String> hrrIdsS = new ArrayList<String>();
		hrrIdsS.add(String.valueOf(tzjlId));
		if(hrrIds!=null){
			for(Long id : hrrIds){
				hrrIdsS.add(String.valueOf(id));
			}
		}
		message.setUidList(hrrIdsS);
		
		return message;
	}

	// 获取user
	public List<User> queryUsersByRole(Long roleId, Long deptId){
		Map<String, Object> deptHHRQuery = new HashMap<String, Object>();
		deptHHRQuery.put("roleId", roleId);
		deptHHRQuery.put("departmentId", deptId);  
		deptHHRQuery.put("status", 0);
		List<User> userlist = userService.querytUserByParams(deptHHRQuery);
		return userlist!=null && !userlist.isEmpty() ? userlist : null;
	}
	
	
	
	// 获取useridlist
	public List<Long> getUsersId(List<User> users){
		List<Long> ids = new ArrayList<Long>();
		if(users!=null){
			for (int i = 0; i < users.size(); i++) {
				ids.add(users.get(i).getId());
			}
		}
		return ids!=null && !ids.isEmpty() ? ids : null;
	}
}

