package com.galaxyinternet.project.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.SopTaskBo;
import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.InterviewRecordService;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopTaskService;

@Controller
@RequestMapping("/galaxy/project/progress")
public class ProjectProgressController extends BaseControllerImpl<Project, ProjectBo> {
	
	final Logger logger = LoggerFactory.getLogger(ProjectProgressController.class);
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private MeetingRecordService meetingRecordService;
	
	@Autowired
	private SopTaskService sopTaskService;
	
	@Autowired
	private InterviewRecordService interviewRecordService;
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	@Override
	protected BaseService<Project> getBaseService() {
		return this.projectService;
	}
	
	
	
	/**
	 * 接触访谈阶段
	 * 			访谈添加
	 * @param   interviewRecord 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addInterview", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InterviewRecord> addInterview(HttpServletRequest request,@RequestBody InterviewRecord interviewRecord ) {
		
		ResponseData<InterviewRecord> responseBody = new ResponseData<InterviewRecord>();
		
		if(interviewRecord.getProjectId() == null 
				|| interviewRecord.getViewDate() == null 
				|| interviewRecord.getViewTarget() == null
				|| interviewRecord.getViewNotes() == null ){
			responseBody.setResult(new Result(Status.ERROR, "interviewRecord info not complete"));
			return responseBody;
		}
		
		try {
			Long id = interviewRecordService.insert(interviewRecord);
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(id);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "insert interviewRecord faild"));
			
			if(logger.isErrorEnabled()){
				logger.error("insert interviewRecord faild ",e);
			}
		}
		
		return responseBody;
	}
	
	
	
	
	/**
	 * 访谈查询,
	 * 			查询个人项目下的访谈记录  
	 * @param   interviewRecord 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryInterview", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InterviewRecordBo> queryInterview(HttpServletRequest request,@RequestBody InterviewRecordBo query ,PageRequest pageable ) {
		
		ResponseData<InterviewRecordBo> responseBody = new ResponseData<InterviewRecordBo>();
		
		//String sessionId = request.getHeader("sessionID");
		Object obj = request.getSession().getAttribute("sessionUser"); 
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, "validate loging session failed"));
			return responseBody;
		}
		User user = (User)obj;
		
		try {
			query.setUid(user.getId());
			
			Page<InterviewRecordBo> pageList = interviewRecordService.queryInterviewPageList(query, pageable);
			responseBody.setPageList(pageList);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
			
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "queryInterviewPageList faild"));
			
			if(logger.isErrorEnabled()){
				logger.error("queryInterviewPageList ",e);
			}
		}
		
		return responseBody;
	}
	
	
	
	/**
	 * 接触访谈阶段
	 * 				启动内部评审，判断操作人为项目创建人
	 * 				判断项目当前阶段
	 * 				修改项目进度、状态
	 * @param   project 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/startReview/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> addInterview(HttpServletRequest request,@PathVariable Long pid) {
		
		ResponseData<Project> responseBody = new ResponseData<Project>();
		
		//session 验证
		Object obj = request.getSession().getAttribute("sessionUser"); 
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, "validate loging session failed"));
			return responseBody;
		}
		User user = (User)obj;
		
		//project id 验证
		Project project = new Project();
		project = projectService.queryById(pid);
		
		if(project==null){
			responseBody.setResult(new Result(Status.ERROR, "project id is not exist"));
			return responseBody;
		}else if(!project.getProjectProgress().equals("接触访谈")){
			responseBody.setResult(new Result(Status.ERROR, "project now progress is not allowed"));
			return responseBody;
		}
		if(user.getId()!=project.getCreateUid()){ //操作权限验证
			responseBody.setResult(new Result(Status.ERROR, "can not operation by other"));
			return responseBody;
		}

		try {
			project.setProjectProgress("内部评审");
			project.setProjectStatus("待定");
			int i = projectService.updateById(project);
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(project.getId());
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "project startReview faild"));
			
			if(logger.isErrorEnabled()){
				logger.error("update project faild ",e);
			}
		}
		
		return responseBody;
	}
	
	
	
	
	
	
	
	/**
	 * 内部评审、 CEO评审 、 立项会、投决会  阶段
	 * 			添加会议记录   
	 * 			判断会议结论，更新项目进度，启动关联任务
	 * @param   interviewRecord 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InterviewRecord> addInterview(HttpServletRequest request,@RequestBody MeetingRecord meetingRecord ) {
		ResponseData<InterviewRecord> responseBody = new ResponseData<InterviewRecord>();
		
		//String sessionId = request.getHeader("sessionID");
		Object obj = request.getSession().getAttribute("sessionUser"); 
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, "validate loging session failed"));
			return responseBody;
		}
		User user = (User)obj;
		
		if(meetingRecord.getProjectId() == null 
				|| meetingRecord.getMeetingDate() == null 
				|| meetingRecord.getMeetingType() == null 
				|| meetingRecord.getMeetingResult() == null 
				|| meetingRecord.getMeetingNotes() == null ){
			responseBody.setResult(new Result(Status.ERROR, "meetingRecord info not complete"));
			return responseBody;
		}
		
		try {
			Long id = meetingRecordService.insertMeet(meetingRecord, user.getId());
			
			responseBody.setId(id);
			responseBody.setResult(new Result(Status.OK, ""));
			
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "add meetingRecord faild"));
			
			if(logger.isErrorEnabled()){
				logger.error("add meetingRecord faild ",e);
			}
		}
		
		return responseBody;
	}
	
	
	/**
	 * 内部评审、 CEO评审 、 立项会、投决会  阶段
	 * 			查询个人项目下的会议记录
	 * @param   interviewRecord 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryMeet", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingRecordBo> queryMeet(HttpServletRequest request,@RequestBody MeetingRecordBo query ,PageRequest pageable ) {
		
		ResponseData<MeetingRecordBo> responseBody = new ResponseData<MeetingRecordBo>();
		
		//String sessionId = request.getHeader("sessionID");
		Object obj = request.getSession().getAttribute("sessionUser"); 
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, "validate loging session failed"));
			return responseBody;
		}
		User user = (User)obj;
		
		try {
			query.setUid(user.getId());
			
			Page<MeetingRecordBo> pageList = meetingRecordService.queryMeetPageList(query, pageable);
			responseBody.setPageList(pageList);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
			
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "queryMeetPageList faild"));
			
			if(logger.isErrorEnabled()){
				logger.error("queryInterviewPageList ",e);
			}
		}
		
		return responseBody;
	}
	
	
	

	/**
	 * CEO评审阶段： 申请立项会排期，    
	 * 				判断操作人为项目创建人；
	 * 				判断项目当前阶段、当前状态；
	 * 				修改项目进度、状态；
	 * 				新建  立项会 排期；
	 * @param   project 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/proSchedule/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> projectSchedule(HttpServletRequest request,@PathVariable Long pid) {
		
		ResponseData<Project> responseBody = new ResponseData<Project>();
		
		//session 验证
		Object obj = request.getSession().getAttribute("sessionUser"); 
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, "validate loging session failed"));
			return responseBody;
		}
		User user = (User)obj;
		
		//project id 验证
		Project project = new Project();
		project = projectService.queryById(pid);
		
		if(project==null){
			responseBody.setResult(new Result(Status.ERROR, "project id is not exist"));
			return responseBody;
		}else if(!project.getProjectProgress().equals("CEO评审") ){
			responseBody.setResult(new Result(Status.ERROR, "project now progress is not allowed"));
			return responseBody;
		}else if(!project.getProjectStatus().equals("通过")){
			responseBody.setResult(new Result(Status.ERROR, "project now status is not allowed"));
			return responseBody;
		}
		if(user.getId()!=project.getCreateUid()){ //操作权限验证
			responseBody.setResult(new Result(Status.ERROR, "can not operation by other"));
			return responseBody;
		}

		try {
			meetingRecordService.projectSchedule(project);
			
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(project.getId());
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "projectSchedule faild"));
			
			if(logger.isErrorEnabled()){
				logger.error("update project faild ",e);
			}
		}
		
		return responseBody;
	}
	
	
	
	
	/**
	 * 投资意向书阶段： 上传  投资意向书；
	 * 				判断操作人为项目创建人；
	 * 				判断项目当前阶段；
	 * 				更新任务状态；
	 * @param   project 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/upTermSheet/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> opTermSheet(HttpServletRequest request,@PathVariable Long pid) {
		
		ResponseData<Project> responseBody = new ResponseData<Project>();
		
		//session 验证
		Object obj = request.getSession().getAttribute("sessionUser"); 
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, "validate loging session failed"));
			return responseBody;
		}
		User user = (User)obj;
		
		//project id 验证
		Project project = new Project();
		project = projectService.queryById(pid);
		
		if(project==null){
			responseBody.setResult(new Result(Status.ERROR, "project id is not exist"));
			return responseBody;
		}else if(!project.getProjectProgress().equals("投资意向书") ){
			responseBody.setResult(new Result(Status.ERROR, "project now progress is not allowed"));
			return responseBody;
		}
		if(user.getId()!=project.getCreateUid()){ //操作权限验证
			responseBody.setResult(new Result(Status.ERROR, "can not operation by other"));
			return responseBody;
		}

		try {
			SopTask task = new SopTask();
			task.setProjectId(pid);
			task.setTaskName("上传投资意向书");          //任务名称：    上传投资意向书
			task.setTaskReceiveUid(user.getId());
			task = sopTaskService.queryOne(task);
			//上传
			
			//修改任务状态完成
			task.setTaskStatus("已完成");
			sopTaskService.updateById(task);
			
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(project.getId());
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "upTermSheet faild"));
			
			if(logger.isErrorEnabled()){
				logger.error("update project faild ",e);
			}
		}
		
		return responseBody;
	}
	
	
	
	
	/**
	 * 投资意向书阶段：上传  投资意向书-签署证明；
	 * 				判断操作人为项目创建人；
	 * 				判断项目当前阶段、当前任务状态；
	 * 				更新项目阶段；
	 * 				生成任务
	 * @param   project 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/upTermSheetSign/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> upTermSheetSign(HttpServletRequest request,@PathVariable Long pid) {
		
		ResponseData<Project> responseBody = new ResponseData<Project>();
		
		//session 验证
		Object obj = request.getSession().getAttribute("sessionUser"); 
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, "validate loging session failed"));
			return responseBody;
		}
		User user = (User)obj;
		
		//project id 验证
		Project project = new Project();
		project = projectService.queryById(pid);
		
		if(project==null){
			responseBody.setResult(new Result(Status.ERROR, "project id is not exist"));
			return responseBody;
		}else if(!project.getProjectProgress().equals("投资意向书") ){
			responseBody.setResult(new Result(Status.ERROR, "project now progress is not allowed"));
			return responseBody;
		}
		//上传  投资意向书  任务验证已完成
		SopTask task = new SopTask();
		task.setProjectId(pid);
		task.setTaskName("上传投资意向书");          //任务名称：    上传投资意向书
		task.setTaskReceiveUid(user.getId());
		task = sopTaskService.queryOne(task);
		if(!task.getTaskStatus().equals("已完成")){
			responseBody.setResult(new Result(Status.ERROR, "Front task is not complete"));
			return responseBody;
		}
		
		if(user.getId()!=project.getCreateUid()){ //操作权限验证
			responseBody.setResult(new Result(Status.ERROR, "can not operation by other"));
			return responseBody;
		}

		try {
			//上传 签署证明
			
			
			//修改项目进度、生成任务
			meetingRecordService.upTermSheetSign(project,user.getId());
			
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(project.getId());
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "upTermSheet faild"));
			
			if(logger.isErrorEnabled()){
				logger.error("update project faild ",e);
			}
		}
		
		return responseBody;
	}
	
	
	
	
	/**
	 * 尽职调查阶段，申请投决会；
	 * 				判断操作人为项目创建人；
	 * 				判断项目当前阶段、当前任务；
	 * 				更新项目阶段；
	 * 				新建会议排期池
	 * @param   project 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/applyDecision/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> applyDecision(HttpServletRequest request,@PathVariable Long pid) {
		
		ResponseData<Project> responseBody = new ResponseData<Project>();
		
		//session 验证
		Object obj = request.getSession().getAttribute("sessionUser"); 
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, "validate loging session failed"));
			return responseBody;
		}
		User user = (User)obj;
		
		//project id 验证
		Project project = new Project();
		project = projectService.queryById(pid);
		
		if(project==null){
			responseBody.setResult(new Result(Status.ERROR, "project id is not exist"));
			return responseBody;
		}else if(!project.getProjectProgress().equals("尽职调查") ){
			responseBody.setResult(new Result(Status.ERROR, "project now progress is not allowed"));
			return responseBody;
		}
		if(user.getId()!=project.getCreateUid()){ //操作权限验证
			responseBody.setResult(new Result(Status.ERROR, "can not operation by other"));
			return responseBody;
		}

		//验证任务都完成
		SopTaskBo task = new SopTaskBo();
		task.setProjectId(pid);
		List<String> sl = new ArrayList<String>();
		sl.add("待认领");
		sl.add("待完工");
		task.setTaskStatusList(sl);
		List<SopTask> tlist = sopTaskService.selectForTaskOverList(task);
		if(tlist!=null && tlist.size()>0){
			responseBody.setResult(new Result(Status.ERROR, "task is not over"));
			return responseBody;
		}
		
		try {
			meetingRecordService.decisionSchedule(project);
			
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(project.getId());
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "projectSchedule faild"));
			
			if(logger.isErrorEnabled()){
				logger.error("update project faild ",e);
			}
		}
		
		return responseBody;
	}
	
	
	
	
}
