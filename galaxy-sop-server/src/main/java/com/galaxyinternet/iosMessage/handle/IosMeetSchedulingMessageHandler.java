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
		return message != null && StringUtils.isNotBlank(message.getMessageType()) && message.getMessageType().startsWith("11");
	}

	@Override
	public IosMessage handle(IosMessage message)
	{	
		
		//boolean toceo = true;
		//boolean tohhr = true;
		
		String meetName = "";
		if(message.getMessageType().equals(ceo_schedul_type)){
			meetName = "【CEO评审会】";
		}else if(message.getMessageType().equals(lxh_schedul_type)){
			meetName = "【立项会】";
		}else if(message.getMessageType().equals(tjh_schedul_type)){
			meetName = "【投决会】";
		}else{
			return null;
		}
		
		StringBuffer content = new StringBuffer();
		
		if(message.getKeyword().contains("cancle:")){
			// 原定于2016.10.10日24：24召开的XXXXXXXXXX项目【CEO评审会】，因故取消，如有疑问请联系相关负责人。
			content.append("原定于")
			.append(message.getKeyword().replace("cancle:", ""))
			.append("召开的")
			.append(message.getProjectName())
			.append("项目的")
			.append(meetName)
			.append("，因故取消，如有疑问请联系相关负责人。");
			
		}else{
			//XXXXXXXXXX项目的【CEO评审会】，已安排在2016.10.10日24：24召开，请您准时参加，如对时间有疑问请联系相关负责人。
			content.append(message.getProjectName())
			.append("项目的")
			.append(meetName);
			if(message.getKeyword().contains("update:")){
				String timestr = message.getKeyword().replace("update", "");
				String time[] = new String[2];
				if(StringUtils.isNotEmpty(timestr)){
					time = timestr.split(",");
				}
				content.append(",已调整到")
				.append(time[1])
				.append("召开")
				.append(message.getContent());
			}else if(message.getKeyword().contains("insert:")){
				content.append(",已安排在")
				.append(message.getKeyword().replace("insert:", ""))
				.append("召开")
				.append(message.getContent());
			}
		}
		
		if(!"operate".equals(message.getKeyword())){
			message.setContent(content.toString());
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

