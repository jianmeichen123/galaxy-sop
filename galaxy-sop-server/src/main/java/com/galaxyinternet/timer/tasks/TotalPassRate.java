package com.galaxyinternet.timer.tasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.model.user.UserRole;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.timer.PassRate;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.PassRateService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.UserRoleService;
import com.galaxyinternet.service.UserService;
import com.galaxyinternet.timer.AutoThread;
import com.galaxyinternet.timer.beans.SpringContextManager;

public class TotalPassRate extends AutoThread{

	@Override
	protected void executeTask() {
		UserService userService = SpringContextManager.getBean("com.galaxyinternet.service.UserService", UserService.class);
		/**
		 * 查询出所有的user
		 */
		List<User> users = userService.queryAll();
		UserRoleService userRoleService = SpringContextManager.getBean("com.galaxyinternet.service.UserRoleService", UserRoleService.class);
		UserRole ur = new UserRole();
		ur.setRoleId(UserConstant.TZJL);
		/**
		 * 查询出所有的投资经理
		 */
		List<UserRole> roles = userRoleService.queryList(ur);
		List<Long> rIds = new ArrayList<Long>();
		for(UserRole userRole : roles){
			rIds.add(userRole.getUserId());
		}
		ProjectService projectService = SpringContextManager.getBean("com.galaxyinternet.service.ProjectService", ProjectService.class);
		MeetingRecordService meetingRecordService = SpringContextManager.getBean("com.galaxyinternet.service.MeetingRecordService", MeetingRecordService.class);
		PassRateService passRateService = SpringContextManager.getBean("com.galaxyinternet.service.PassRateService", PassRateService.class);
		MeetingRecord mr = new MeetingRecord();
		for(int i = 0; i < users.size(); i++){
			User u = users.get(i);
			if(rIds.contains(u.getId())){
				//是投资经理
				int tTotal = 0;
				int tNum = 0;
				int lTotal = 0;
				int lNum = 0;
				int cTotal = 0;
				int cNum = 0;
				Project project = new Project();
				project.setCreateUid(u.getId());
				List<Project> projectList = projectService.queryList(project);
				if(projectList == null || projectList.isEmpty()){
					continue;
				}
				for(Project p : projectList){
					mr.setProjectId(p.getId());
					String progress = p.getProjectProgress();
					if(Integer.parseInt(progress.substring(progress.indexOf(":") + 1)) > 6){
						//投决会
						mr.setMeetingType(DictEnum.meetingType.投决会.getCode());
						mr.setMeetingResult(DictEnum.meetingResult.通过.getCode());
						Long count = meetingRecordService.queryCount(mr);
						tTotal++;
						if(count > 0){
							tNum++;
						}
					}
					if(Integer.parseInt(progress.substring(progress.indexOf(":") + 1)) > 3){
						//立项会
						mr.setMeetingType(DictEnum.meetingType.立项会.getCode());
						mr.setMeetingResult(DictEnum.meetingResult.通过.getCode());
						Long count = meetingRecordService.queryCount(mr);
						lTotal++;
						if(count > 0){
							lNum++;
						}
					}
					if(Integer.parseInt(progress.substring(progress.indexOf(":") + 1)) > 2){
						//立项会
						mr.setMeetingType(DictEnum.meetingType.CEO评审.getCode());
						mr.setMeetingResult(DictEnum.meetingResult.通过.getCode());
						Long count = meetingRecordService.queryCount(mr);
						cTotal++;
						if(count > 0){
							cNum++;
						}
					}
				}
				System.out.println("tTotal:"+tTotal+", tNum:"+tNum+", lTotal:"+lTotal+", lNum:"+lNum+", cTotal:"+cTotal+", cNum:"+cNum);
				double tRate = (float) tNum*100/tTotal;
				double lRate = (float) lNum*100/lTotal;
				double cRate = (float) cNum*100/cTotal;
				PassRate pr = new PassRate();
				pr.setUid(u.getId());
				pr.setRateType(1);
				List<PassRate> tList = passRateService.queryList(pr);
				if(tList != null && !tList.isEmpty()){
					pr = tList.get(0);
					pr.setRate(tRate);
					pr.setUpdatedTime(new Date().getTime());
					passRateService.updateById(pr);
				}else{
					pr.setId(null);
					pr.setRate(tRate);
					pr.setCreatedTime(new Date().getTime());
					passRateService.insert(pr);
				}
				
				pr.setRateType(0);
				pr.setRate(null);
				pr.setId(null);
				pr.setUpdatedTime(null);
				pr.setCreatedTime(null);
				List<PassRate> lList = passRateService.queryList(pr);
				if(lList != null && !lList.isEmpty()){
					pr = lList.get(0);
					pr.setRate(lRate);
					pr.setUpdatedTime(new Date().getTime());
					passRateService.updateById(pr);
				}else{
					pr.setId(null);
					pr.setRate(lRate);
					pr.setCreatedTime(new Date().getTime());
					passRateService.insert(pr);
				}
				
				pr.setRateType(2);
				pr.setRate(null);
				pr.setId(null);
				pr.setUpdatedTime(null);
				pr.setCreatedTime(null);
				List<PassRate> cList = passRateService.queryList(pr);
				if(cList != null && !cList.isEmpty()){
					pr = cList.get(0);
					pr.setRate(cRate);
					pr.setUpdatedTime(new Date().getTime());
					passRateService.updateById(pr);
				}else{
					pr.setId(null);
					pr.setRate(cRate);
					pr.setCreatedTime(new Date().getTime());
					passRateService.insert(pr);
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println((float) 1/3);
	}
}
