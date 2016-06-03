package com.galaxyinternet.scheduling;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.timer.PassRate;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.model.user.UserRole;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.PassRateService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.UserRoleService;
import com.galaxyinternet.service.UserService;

/**
 * 前三月所有投资经理 CEO评审会议、立项会、投决会 三个排期会议的过会率
 * 
 * 业务逻辑：
 */
@Component("meetingPassStageTask")
public class MeetingPassStageTask extends BaseGalaxyTask {
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(MeetingPassStageTask.class);

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	ProjectService projectService;

	@Autowired
	MeetingRecordService meetingRecordService;

	@Autowired
	PassRateService passRateService;
	
	
	protected void executeInteral() throws BusinessException {
		
		passRateService.deleteAll();     //删除所有数据
		
		Date beginbeforD = beforeMonthDate(3);
		Date endbeforD = beforeMonthDate(1);
		Date beginD = mouthBeginDateStr(beginbeforD);  //2016-03-01 00:00:00
		Date endD = mouthEndDateStr(endbeforD);        //2016-05-31 23:59:59
		Long beginTimeL = beginD.getTime();     //1464796800000
		Long endTimeL = endD.getTime();
		
		//查询出所有的投资经理
		List<User> users = null;
		//查询出所有的投资经理角色的 userIdList
		UserRole ur = new UserRole();
		ur.setRoleId(UserConstant.TZJL);
		List<UserRole> roles = userRoleService.queryList(ur);
		List<Long> userIdList = new ArrayList<Long>();
		for (UserRole userRole : roles) {
			userIdList.add(userRole.getUserId());
		}
		//由 userIdList 查询出所有 userlist
		if (userIdList != null && !userIdList.isEmpty()) {
			User user = new User();
			user.setIds(userIdList);
			users = userService.queryList(user);
		}
		if (users == null || users.isEmpty()) {   
			return;
		}
		
		List<PassRate> insertList = new ArrayList<PassRate>();
		List<PassRate> edittList = new ArrayList<PassRate>();
		
		List<Project> projectList = null;    //投资经理  项目集合
		Project project = new Project();
		project.setStartTime(beginTimeL);
		project.setEndTime(endTimeL);
		
		MeetingRecord mr = new MeetingRecord();  //会议查询条件
		mr.setMeetingResult(DictEnum.meetingResult.通过.getCode());
		
		//=====  循环  投资经理 ，   每人的   ceo会议、 立项会、投决会 过会率入库
		for (User u : users) {
			
			//每人数据初始化
			int tTotal = 0;
			int tNum = 0;
			int lTotal = 0;
			int lNum = 0;
			int cTotal = 0;
			int cNum = 0;
			
			//每人各会议过会率   
			double cRate = 0;
			double lRate = 0;
			double tRate = 0;
			
			project.setCreateUid(u.getId());
			projectList = projectService.queryList(project);  //该投资经理  前3月内 创建的项目
			
			//=====   循环  投资经理  的项目，  total 、num 数据收集
			if(projectList == null || projectList.isEmpty()){
				//passRate(0, u.getId(),insertList,edittList);  // 过会率 null 入库，
				//passRate(1, u.getId(),insertList,edittList);
				//passRate(2, u.getId(),insertList,edittList);
				continue;
			}
			for(Project p : projectList) {
				mr.setProjectId(p.getId());  //会议查询条件
				
				String progress = p.getProjectProgress();
				if (Integer.parseInt(progress.substring(progress.indexOf(":") + 1)) > 6) {  // 投决会
					tTotal++;
					
					lTotal++;
					lNum++;
					
					cTotal++;
					cNum++;
					
					mr.setMeetingType(DictEnum.meetingType.投决会.getCode());
					Long count = meetingRecordService.queryCount(mr);
					if (count > 0) { 
						tNum++; 
					}
				}else if (Integer.parseInt(progress.substring(progress.indexOf(":") + 1)) > 3) {  // 立项会
					lTotal++;
					
					cTotal++;
					cNum++;
					
					mr.setMeetingType(DictEnum.meetingType.立项会.getCode());
					Long count = meetingRecordService.queryCount(mr);
					if (count > 0) {
						lNum++;
					}
				}else if (Integer.parseInt(progress.substring(progress.indexOf(":") + 1)) > 2) {  // CEO评审
					cTotal++;
					
					mr.setMeetingType(DictEnum.meetingType.CEO评审.getCode());
					Long count = meetingRecordService.queryCount(mr);
					if (count > 0) {
						cNum++;
					}
				}
			}
			
			//=====   每人  根据   total num 操作数据库
			if(cTotal == 0){  //过会率 null 入库
				//passRate(2, u.getId(),insertList,edittList);   // 默认0表示立项会，1表示投决会，2表示CEO评审
			}else{
				cRate = (float) cNum * 100 / cTotal;
				passRate(2, u.getId(),cRate,insertList,edittList);
			}
			
			if(lTotal == 0){
				//passRate(0, u.getId(),insertList,edittList);
			}else{
				lRate = (float) lNum * 100 / lTotal;
				passRate(0, u.getId(),lRate,insertList,edittList);
			}
			
			if(tTotal == 0){
				//passRate(1, u.getId(),insertList,edittList);
			}else{
				tRate = (float) tNum * 100 / tTotal;
				passRate(1, u.getId(),tRate,insertList,edittList);
			}
		}
		
		//批量操作数据库
		if(insertList!=null && !insertList.isEmpty()){
			passRateService.insertInBatch(insertList);
		}	
		if(edittList!=null && !edittList.isEmpty()){
			passRateService.updateInBatch(edittList);
		}	
	}

	
	
	
	public void passRate(int rateType, Long uid,double rate){
		PassRate pr = new PassRate();
		pr.setUid(uid);
		pr.setRateType(rateType);  		// 默认0表示立项会，1表示投决会，2表示CEO评审
		
		List<PassRate> tList = passRateService.queryList(pr);
		
		if (tList != null && !tList.isEmpty()) {
			pr = tList.get(0);
			pr.setRate(rate);
			passRateService.updateById(pr);
		} else {
			pr.setId(null);
			pr.setRate(rate);
			passRateService.insert(pr);
		}
	}
	
	public void passRate(int rateType, Long uid,List<PassRate> insertList,List<PassRate> edittList){
		PassRate pr = new PassRate();
		pr.setUid(uid);
		pr.setRateType(rateType);  		// 默认0表示立项会，1表示投决会，2表示CEO评审
		
		List<PassRate> tList = passRateService.queryList(pr);
		
		if (tList != null && !tList.isEmpty()) {
			pr = tList.get(0);
			//pr.setRate(rate);
			pr.setUpdatedTime(new Date().getTime());
			edittList.add(pr);
		} else {
			pr.setId(null);
			//pr.setRate(rate);
			pr.setCreatedTime(new Date().getTime());
			insertList.add(pr);
		}
	}
	
	public void passRate(int rateType, Long uid,double rate,List<PassRate> insertList,List<PassRate> edittList){
		PassRate pr = new PassRate();
		pr.setUid(uid);
		pr.setRateType(rateType);  		// 默认0表示立项会，1表示投决会，2表示CEO评审
		
		List<PassRate> tList = passRateService.queryList(pr);
		
		if (tList != null && !tList.isEmpty()) {
			pr = tList.get(0);
			pr.setRate(rate);
			pr.setUpdatedTime(new Date().getTime());
			edittList.add(pr);
		} else {
			pr.setId(null);
			pr.setRate(rate);
			pr.setCreatedTime(new Date().getTime());
			insertList.add(pr);
		}
	}
	
	
	
	/**
	 * 获取当前月的前n月的日期
	 */
	public static Date beforeMonthDate(int nMonth) {
		Date dNow = new Date(); 					// 当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(dNow);						// 把当前时间赋给日历
		calendar.add(Calendar.MONTH, -nMonth); 		// 设置为前 n 月
		dBefore = calendar.getTime(); 				// 得到前 n 月的时间
		return dBefore;
	}
	
	/**
	 * 获取月第一天     :00:00:00:00
	 */
	public static Date mouthBeginDateStr(Date date){
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		calendar.set(Calendar.MILLISECOND, 00);
		return calendar.getTime();
	}
	/**
	 * 获取月最后一天  :23:59:59
	 */
	public static Date mouthEndDateStr(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 00);
		return calendar.getTime();
	}
	
	
	
}
