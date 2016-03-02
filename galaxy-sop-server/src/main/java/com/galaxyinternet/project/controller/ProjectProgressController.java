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
import com.galaxyinternet.framework.core.constants.Constants;
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
	
	
	public String errMessage(Project project,User user,String prograss){
		if(project == null){
			return "项目检索为空";
		}else if(project.getProjectStatus().equals("关闭")){
			return "项目已经关闭";
		}
		
		if(user != null){
			if(project.getCreateUid()==null || user.getId().longValue()!=project.getCreateUid().longValue()){ 
				return "不允许操作他人项目";
			}
		}
		if(prograss != null){
			if(project.getProjectProgress()==null || !project.getProjectProgress().equals(prograss) ){
				return "项目当前阶段不允许进行该操作";
			}
		}
		return null;
	}
	
	
	/**
	 * 接触访谈阶段: 访谈添加
	 * @param   interviewRecord 
	 * 			produces="application/text;charset=utf-8"
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addInterview", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InterviewRecord> addInterview(@RequestBody InterviewRecord interviewRecord ,HttpServletRequest request ) {
		
		ResponseData<InterviewRecord> responseBody = new ResponseData<InterviewRecord>();
		
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
		if(interviewRecord.getProjectId() == null 
				|| interviewRecord.getViewDate() == null 
				|| interviewRecord.getViewTarget() == null
				|| interviewRecord.getViewNotes() == null ){
			responseBody.setResult(new Result(Status.ERROR, "interviewRecord info not complete"));
			return responseBody;
		}
		
		//project id 验证
		Project project = new Project();
		project = projectService.queryById(interviewRecord.getProjectId());
		
		String err = errMessage(project,user,"接触访谈");
		if(err!=null && err.length()>0){
			responseBody.setResult(new Result(Status.ERROR, err));
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
	@RequestMapping(value = "/queryInterview", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InterviewRecordBo> queryInterview(HttpServletRequest request,@RequestBody InterviewRecordBo query ,PageRequest pageable ) {
		
		ResponseData<InterviewRecordBo> responseBody = new ResponseData<InterviewRecordBo>();
		
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
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
	 * 接触访谈阶段: 启动内部评审，
	 * 				判断操作人为项目创建人
	 * 				判断项目当前阶段
	 * 				修改项目进度、状态
	 * @param   project 
	 * @return  
	 */
	@ResponseBody
	@RequestMapping(value = "/startReview/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> startReview(HttpServletRequest request,@PathVariable Long pid) {
		
		ResponseData<Project> responseBody = new ResponseData<Project>();
		
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
		Project project = new Project();
		project = projectService.queryById(pid);
		
		String err = errMessage(project,user,"接触访谈");
		if(err!=null && err.length()>0){
			responseBody.setResult(new Result(Status.ERROR, err));
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
	 * 内部评审、 CEO评审 、 立项会、投决会  阶段 : 添加会议记录   
	 * 			判断会议结论，更新项目进度，启动关联任务
	 * @param   interviewRecord 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addmeet", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingRecord> addmeet(HttpServletRequest request,@RequestBody MeetingRecord meetingRecord ) {
		ResponseData<MeetingRecord> responseBody = new ResponseData<MeetingRecord>();
		
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
		if(meetingRecord.getProjectId() == null 
				|| meetingRecord.getMeetingDate() == null 
				|| meetingRecord.getMeetingType() == null 
				|| meetingRecord.getMeetingResult() == null 
				|| meetingRecord.getMeetingNotes() == null ){
			responseBody.setResult(new Result(Status.ERROR, "meetingRecord info not complete"));
			return responseBody;
		}
		
		String prograss = "";
		if(meetingRecord.getMeetingType().equals("内评会")){
			prograss = "内部评审";
		}else if(meetingRecord.getMeetingType().equals("CEO评审")){
			prograss = "CEO评审";
		}else if(meetingRecord.getMeetingType().equals("立项会")){
			prograss = "立项会";
		}else if(meetingRecord.getMeetingType().equals("投决会")){
			prograss = "投决会";
		}/*else{
			responseBody.setResult(new Result(Status.ERROR, "会议类型无法识别"));
			return responseBody;
		}*/
		
		//project id 验证
		Project project = new Project();
		project = projectService.queryById(meetingRecord.getProjectId());
		
		String err = errMessage(project,user,prograss);
		if(err!=null && err.length()>0){
			responseBody.setResult(new Result(Status.ERROR, err));
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
	@RequestMapping(value = "/queryMeet", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingRecordBo> queryMeet(HttpServletRequest request,@RequestBody MeetingRecordBo query ,PageRequest pageable ) {
		
		ResponseData<MeetingRecordBo> responseBody = new ResponseData<MeetingRecordBo>();
		
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
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
	 * CEO评审阶段： 申请 立项会 排期，    
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
		
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
		//project id 验证
		Project project = new Project();
		project = projectService.queryById(pid);
		
		String err = errMessage(project,user,"CEO评审");
		if(err!=null && err.length()>0){
			responseBody.setResult(new Result(Status.ERROR, err));
			return responseBody;
		}
		
		if(!project.getProjectStatus().equals("通过")){
			responseBody.setResult(new Result(Status.ERROR, "会议未通过"));
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
	 * 投资意向书  阶段： 上传  投资意向书；
	 * 尽职调查       阶段: 上传  业务尽职调查报告；
	 * 投资协议       阶段： 上传 投资协议、股权转让协议
	 * 				判断操作人为项目创建人；
	 * 				判断项目当前阶段；
	 * 
	 * 更新任务状态；
	 * @param   workType 文件业务类型 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/upProjectFile/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> upProjectFile(HttpServletRequest request,String workType,@PathVariable Long pid) {
		
		ResponseData<Project> responseBody = new ResponseData<Project>();
		
		String proProgress = "";
		String taskName = "";
		
		if(workType!=null){
			if(workType.equals("投资意向书")){
				proProgress = "投资意向书";
				taskName = "上传投资意向书";
				
			}else if(workType.equals("业务尽职调查报告")){
				proProgress = "尽职调查";
				taskName = "上传业务尽职调查报告";
				
			}else if(workType.equals("投资协议")){
				proProgress = "投资协议";
				taskName = "上传投资协议";
				
			}else if(workType.equals("股权转让协议")){
				proProgress = "投资协议";
				taskName = "上传股权转让协议";
			}else{
				responseBody.setResult(new Result(Status.OK, "文件业务类型不能识别"));
				return responseBody;
			}
		}else{
			responseBody.setResult(new Result(Status.ERROR, "文件业务类型为空"));
			return responseBody;
		}
		
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
		//project id 验证
		Project project = new Project();
		project = projectService.queryById(pid);
		
		//项目为 内部创建，不需要  股权转让协议
		if(project.getProjectType()!=null && project.getProjectType().equals("内部创建") && workType.equals("股权转让协议")){
			responseBody.setResult(new Result(Status.ERROR, "该项目不需要股权转让协议"));
			return responseBody;
		}
				
		String err = errMessage(project,user,proProgress);
		if(err!=null && err.length()>0){
			responseBody.setResult(new Result(Status.ERROR, err));
			return responseBody;
		}

		try {
			SopTask task = new SopTask();
			task.setProjectId(pid);
			task.setTaskName(taskName);          
			task.setTaskReceiveUid(user.getId());
			task = sopTaskService.queryOne(task);
			if(task==null){
				responseBody.setResult(new Result(Status.ERROR, "任务检索为空"));
				return responseBody;
			}
			
			//修改任务状态完成
			task.setTaskStatus("已完成");
			sopTaskService.updateById(task);
			
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(project.getId());
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "upProjectFile-task faild"));
			
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
		
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
		//project id 验证
		Project project = new Project();
		project = projectService.queryById(pid);
		
		String err = errMessage(project,user,"投资意向书");
		if(err!=null && err.length()>0){
			responseBody.setResult(new Result(Status.ERROR, err));
			return responseBody;
		}
		
		try {
			//上传  投资意向书  任务验证已完成
			SopTask task = new SopTask();
			task.setProjectId(pid);
			task.setTaskName("上传投资意向书");          //任务名称：    上传投资意向书
			task.setTaskReceiveUid(user.getId());
			task = sopTaskService.queryOne(task);
			if(task.getTaskStatus()==null || !task.getTaskStatus().equals("已完成")){
				responseBody.setResult(new Result(Status.ERROR, "Front task is not complete"));
				return responseBody;
			}
			
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
	 * 尽职调查阶段，申请 投决会；
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
		
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
		//project id 验证
		Project project = new Project();
		project = projectService.queryById(pid);
		
		String err = errMessage(project,user,"尽职调查");
		if(err!=null && err.length()>0){
			responseBody.setResult(new Result(Status.ERROR, err));
			return responseBody;
		}
		
		try {
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
			
			meetingRecordService.decisionSchedule(project);
			
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(project.getId());
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "applyDecision faild"));
			
			if(logger.isErrorEnabled()){
				logger.error("update project faild ",e);
			}
		}
		
		return responseBody;
	}
	
	
	/**
	 * 投资协议 阶段：上传  投资协议-签署证明；
	 * 				判断操作人为项目创建人；
	 * 				判断项目当前阶段、当前任务状态；
	 * 				更新项目阶段；
	 * 				生成任务
	 * @param   project 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/upInvestmentSign/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> upInvestmentSign(HttpServletRequest request,@PathVariable Long pid) {
		
		ResponseData<Project> responseBody = new ResponseData<Project>();
		
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
		//project id 验证
		Project project = new Project();
		project = projectService.queryById(pid);
		
		String err = errMessage(project,user,"投资协议");
		if(err!=null && err.length()>0){
			responseBody.setResult(new Result(Status.ERROR, err));
			return responseBody;
		}
		
		try {
			//验证任务完成
			SopTaskBo task = new SopTaskBo();
			task.setProjectId(pid);
			task.setTaskReceiveUid(user.getId());
			List<String> sl = new ArrayList<String>();
			sl.add("待认领");
			sl.add("待完工");
			task.setTaskStatusList(sl);
			List<SopTask> tlist = sopTaskService.selectForTaskOverList(task);
			if(tlist!=null && tlist.size()>0){
				responseBody.setResult(new Result(Status.ERROR, "task is not over"));
				return responseBody;
			}
			
			//修改项目进度、生成任务
			meetingRecordService.upInvestmentSign(project);
			
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



	
	
	
}
