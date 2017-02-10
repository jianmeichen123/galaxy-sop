package com.galaxyinternet.scheduling;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.operationMessage.OperationMessage;
import com.galaxyinternet.model.operationMessage.OperationType;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.role.Role;
import com.galaxyinternet.model.timer.PassRate;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.model.user.UserRole;
import com.galaxyinternet.platform.constant.PlatformConst;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.OperationMessageService;
import com.galaxyinternet.service.PassRateService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.UserRoleService;
import com.galaxyinternet.service.UserService;

/**
 * 前三月所有投资经理 CEO评审会议、立项会、投决会 三个排期会议的过会率
 * 
 * 业务逻辑：
 */
@Component("pushMessageOperation")
public class PushMessageOperation extends BaseGalaxyTask {
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(PushMessageOperation.class);

	@Autowired
	ProjectService projectService;
	@Autowired
	private UserService useService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	OperationMessageService operationMessageService;

	
	
	protected void executeInteral() throws BusinessException {
			List<Project> listPorject = projectService.
					selectProjectForPushMessage();
			Map<Long,Department> deparmentMap=deparmentMap();
			Map<Long,User> userMap=userMap();
			try {
				List<OperationMessage> listMessage=new ArrayList<OperationMessage>();
				if(null!=listPorject&&listPorject.size()>0){
						for(Project project:listPorject){
							OperationMessage om=new OperationMessage();
							om.setDepartment((String)deparmentMap.get(project.getProjectDepartid()).getName());
							om.setRole("投资经理");
							om.setOperatorDepartmentId(project.getProjectDepartid());
							om.setOperatorId(project.getCreateUid());
							om.setOperator(userMap.get(project.getCreateUid()).getRealName());
							om.setBelongDepartmentId(project.getProjectDepartid());
							om.setBelongUid(project.getCreateUid());
							om.setBelongUname(userMap.get(project.getCreateUid()).getRealName());
							om.setType("项目");
							om.setProjectName(project.getProjectName());
							om.setProjectId(project.getId());
							om.setMessageType("16.1");
							om.setModule(0);
							StringBuffer sf=new StringBuffer();
							sf.append("项目");
							sf.append(project.getProjectName());
							sf.append("需要填写上月运营数据。");
							om.setContent(sf.toString());
							listMessage.add(om);
					}		
				}
				operationMessageService.insertInBatch(listMessage);
			} catch (Exception e) {
				logger.error("生成上月运营数据消息失败。：" + "PushMessageOperation", e);
				// TODO: handle exception
			}
			
	}
    public Map<Long,Department> deparmentMap(){
    	Map<Long,Department> map=new HashMap<Long,Department>();
    	List<Department> queryAll = departmentService.queryAll();
    	for(Department department: queryAll){
    		  map.put(department.getId(), department);
    	}
    	return map;
    }
    public Map<Long,User> userMap(){
    	Map<Long,User> map=new HashMap<Long,User>();
    	List<User> queryAll = useService.queryAll();
    	for(User user: queryAll){
    		  map.put(user.getId(), user);
    	}
    	return map;
    }
	
	
	
	
}
