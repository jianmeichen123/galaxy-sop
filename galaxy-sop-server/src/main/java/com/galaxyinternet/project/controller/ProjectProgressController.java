package com.galaxyinternet.project.controller;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.galaxyinternet.bo.SopTaskBo;
import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.file.BucketName;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.InterviewRecordService;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.SopTaskService;
import com.galaxyinternet.service.UserRoleService;

@Controller
@RequestMapping("/galaxy/project/progress")
public class ProjectProgressController extends BaseControllerImpl<Project, ProjectBo> {
	
	final Logger logger = LoggerFactory.getLogger(ProjectProgressController.class);
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private MeetingRecordService meetingRecordService;
	
	@Autowired
	private SopTaskService sopTaskService;
	
	@Autowired
	private InterviewRecordService interviewRecordService;
	
	@Autowired
	private  SopFileService sopFileService;
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	@Override
	protected BaseService<Project> getBaseService() {
		return this.projectService;
	}
	
	
	
	public String errMessage(Project project,User user,String prograss){
		if(project == null){
			return "项目检索为空";
		}else if(project.getProjectStatus().equals(DictEnum.meetingResult.否决.getCode())){ //字典 项目状态 = 会议结论 关闭
			return "项目已经关闭";
		}
		if(user != null){
			if(project.getCreateUid()==null || user.getId().longValue()!=project.getCreateUid().longValue()){ 
				return "不允许操作他人项目";
			}
		}
		if(prograss != null){
			if(project.getProjectProgress()!=null){
				try {
					int operationPro = Integer.parseInt(prograss.substring(prograss.length()-1)) ;
					int projectPro = Integer.parseInt(project.getProjectProgress().substring(project.getProjectProgress().length()-1)) ;
					if(projectPro < operationPro){
						return "项目当前阶段不允许进行该操作";
					}
				} catch (Exception e) {
					return "项目阶段不和规范";
				}
			}else{
				return "项目阶段出错";
			}
		}
		
		return null;
	}
	

	
	/**
	 * 访谈默认页面 
	 */
	@RequestMapping(value = "/interView", method = RequestMethod.GET)
	public String interView() {
		return "interview/view";
	}
	
	/**
	 * 添加页面
	 */
	@RequestMapping(value = "/interViewAdd", method = RequestMethod.GET)
	public String interViewAdd() {
		return "interview/interviewtc";
	}
	
	
	/**
	 * 接触访谈阶段: 附件添加 -访谈添加
	 * @param   interviewRecord 
	 * 			produces="application/text;charset=utf-8"
	 * @RequestBody InterviewRecord interviewRecord ,
	 * @return
	 */
	@com.galaxyinternet.common.annotation.Logger
	@ResponseBody
	@RequestMapping(value = "/addFileInterview", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InterviewRecord> addFileInterview(HttpServletRequest request,HttpServletResponse response ) {
		ResponseData<InterviewRecord> responseBody = new ResponseData<InterviewRecord>();
		
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
		String projectId = request.getParameter("projectId");
		String viewDateStr = request.getParameter("viewDateStr");
		String viewTarget = request.getParameter("viewTarget");
		String viewNotes = request.getParameter("viewNotes");
		String fname = request.getParameter("fname");
					
		if(projectId == null  || viewDateStr == null  || viewTarget == null || viewNotes == null ){
			responseBody.setResult(new Result(Status.ERROR,null, "请完善访谈信息"));
			return responseBody;
		}
		
		try {
			//project  验证
			Project project = new Project();
			project = projectService.queryById(Long.parseLong(projectId));
			String err = errMessage(project,user,DictEnum.projectProgress.接触访谈.getCode());   //字典  项目进度  接触访谈 
			if(err!=null && err.length()>0){
				responseBody.setResult(new Result(Status.ERROR,null, err));
				return responseBody;
			}
			
			InterviewRecord interviewRecord = new InterviewRecord();
			interviewRecord.setProjectId(Long.parseLong(projectId));
			interviewRecord.setViewDateStr(viewDateStr);
			interviewRecord.setViewTarget(viewTarget);
			interviewRecord.setViewNotes(viewNotes);
			
			//调接口上传
			String fileKey = String.valueOf(IdGenerator.generateId(OSSHelper.class));
			String bucketName = BucketName.DEV.getName();
			
//			File file = sopFileService.aLiColoudUpload(request, fileKey, bucketName);
			Map<String,Object> map = sopFileService.aLiColoudUpload(request, fileKey, bucketName);
			//上传成功后
			if(map!=null){
				Map<String,String> nameMap = (Map<String, String>) map.get("nameMap");
				File file = (File) map.get("file");
				String fileName = "";
				if(fname!=null && fname.trim().length()>0){
					fileName = fname;
				}else{
					fileName = nameMap.get("fileName");// secondarytile.png  全名
				}
				if(fileName == null || fileName.trim().length()==0){
					responseBody.setResult(new Result(Status.ERROR,null, "get file name failed"));
					return responseBody;
				}//end get file name 
				
				
				SopFile sopFile = new SopFile();
				sopFile.setProjectId(project.getId());
				sopFile.setProjectProgress(project.getProjectProgress());
				sopFile.setBucketName(bucketName); 
				sopFile.setFileKey(fileKey);  
				sopFile.setFileLength(file.length());  //文件大小
				sopFile.setFileName(fileName);
				sopFile.setFileSuffix(nameMap.get("fileSuffix"));
				sopFile.setFileUid(user.getId());	 //上传人
				sopFile.setCareerLine(user.getDepartmentId());
				sopFile.setFileType(DictEnum.fileType.音频文件.getCode());   //存储类型
				sopFile.setFileSource(DictEnum.fileSource.内部.getCode());  //档案来源
				//sopFile.setFileWorktype(fileWorkType);    //业务分类
				sopFile.setFileStatus(DictEnum.fileStatus.已上传.getCode());  //档案状态
				
				Long id = interviewRecordService.insertInterview(interviewRecord,sopFile); //掉接口保存 interview、sopfile
				
				responseBody.setResult(new Result(Status.OK, ""));
				responseBody.setId(id);
				
				ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId());
			}else{
				responseBody.setResult(new Result(Status.ERROR,null, "上传失败"));
				return responseBody;
			}
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "操作失败"));
			
			if(logger.isErrorEnabled()){
				logger.error("insert interviewRecord faild ",e);
			}
		}
		return responseBody;
	}
	
	
	/**
	 * 接触访谈阶段: 访谈添加
	 * @param   interviewRecord 
	 * 			produces="application/text;charset=utf-8"
	 * @return
	 */
	@com.galaxyinternet.common.annotation.Logger
	@ResponseBody
	@RequestMapping(value = "/addInterview", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InterviewRecord> addInterview(@RequestBody InterviewRecord interviewRecord ,HttpServletRequest request ) {
		
		ResponseData<InterviewRecord> responseBody = new ResponseData<InterviewRecord>();
		
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
		if(interviewRecord.getProjectId() == null 
				|| interviewRecord.getViewDate() == null 
				|| interviewRecord.getViewTarget() == null
				|| interviewRecord.getViewNotes() == null ){
			responseBody.setResult(new Result(Status.ERROR,null, "interviewRecord info not complete"));
			return responseBody;
		}
		
		//project id 验证
		Project project = new Project();
		project = projectService.queryById(interviewRecord.getProjectId());
		
		String err = errMessage(project,user,DictEnum.projectProgress.接触访谈.getCode());   //字典  项目进度  接触访谈 
		if(err!=null && err.length()>0){
			responseBody.setResult(new Result(Status.ERROR,null, err));
			return responseBody;
		}
				
		try {
			
			Long id = interviewRecordService.insert(interviewRecord);
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(id);
			ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId());
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "请完善访谈信息"));
			
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
	public ResponseData<InterviewRecordBo> queryInterview(HttpServletRequest request,@RequestBody InterviewRecordBo query ) {
		
		ResponseData<InterviewRecordBo> responseBody = new ResponseData<InterviewRecordBo>();
		
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
		try {
			query.setUid(user.getId());
			
			Page<InterviewRecordBo> pageList = interviewRecordService.queryInterviewPageList(query,  new PageRequest(query.getPageNum()==null?0:query.getPageNum(), query.getPageSize()==null?10:query.getPageSize()) );
			responseBody.setPageList(pageList);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
			
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"查询失败"));
			
			if(logger.isErrorEnabled()){
				logger.error("queryInterviewPageList ",e);
			}
		}
		
		return responseBody;
	}
	
	
	
	
	/**
	 * 会议默认页面 
	 */
	@RequestMapping(value = "/meetView", method = RequestMethod.GET)
	public String meetView() {
		return "meeting/view";
	}
	
	/**
	 * 会议添加页面
	 */
	@RequestMapping(value = "/meetAddView", method = RequestMethod.GET)
	public String meetAddView() {
		return "meeting/meetingtc";
	}
	
	
	
	/**
	 * 内部评审、 CEO评审 、 立项会、投决会  阶段 : 添加会议记录   
	 * 			判断会议结论，更新项目进度，启动关联任务
	 * @param   interviewRecord 
	 * @return
	 */
	@com.galaxyinternet.common.annotation.Logger
	@ResponseBody
	@RequestMapping(value = "/addfilemeet", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingRecord> addFileMeet(HttpServletRequest request,HttpServletResponse response  ) {
		ResponseData<MeetingRecord> responseBody = new ResponseData<MeetingRecord>();
		
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		 
		String projectId = request.getParameter("projectId");
		String meetingDateStr = request.getParameter("meetingDateStr");
		String meetingType = request.getParameter("meetingType");
		String meetingResult = request.getParameter("meetingResult");
		String meetingNotes = request.getParameter("meetingNotes");
		String fname = request.getParameter("fname");
			
		if(projectId == null || meetingDateStr == null|| meetingType == null|| meetingResult == null|| meetingNotes == null ){
			responseBody.setResult(new Result(Status.ERROR,null, "请完善会议信息"));
			return responseBody;
		}
		
		
		try {
			String prograss = "";
			if(meetingType.equals(DictEnum.meetingType.内评会.getCode())){       //字典  会议类型   内评会
				prograss = DictEnum.projectProgress.内部评审.getCode();                                 	//字典  项目进度  内部评审
				
			}else if(meetingType.equals(DictEnum.meetingType.CEO评审.getCode())){ //字典  会议类型   CEO评审
				prograss = DictEnum.projectProgress.CEO评审.getCode(); 								//字典   项目进度  CEO评审
				
			}else if(meetingType.equals(DictEnum.meetingType.立项会.getCode())){	//字典  会议类型   立项会
				prograss = DictEnum.projectProgress.立项会.getCode(); 										//字典   项目进度    立项会
				
			}else if(meetingType.equals(DictEnum.meetingType.投决会.getCode())){	//字典  会议类型   投决会
				prograss = DictEnum.projectProgress.投资决策会.getCode(); 									//字典   项目进度     投资决策会
			}/*else{
				responseBody.setResult(new Result(Status.ERROR,null, "会议类型无法识别"));
				return responseBody;
			}*/
			
			//project id 验证
			Project project = new Project();
			project = projectService.queryById(Long.parseLong(projectId));
			
			String err = errMessage(project,user,prograss);
			if(err!=null && err.length()>0){
				responseBody.setResult(new Result(Status.ERROR,null, err));
				return responseBody;
			}
			
			MeetingRecord meetingRecord = new  MeetingRecord();
			meetingRecord.setProjectId(Long.parseLong(projectId));
			meetingRecord.setMeetingDateStr(meetingDateStr);
			meetingRecord.setMeetingType(meetingType);
			meetingRecord.setMeetingResult(meetingResult);
			meetingRecord.setMeetingNotes(meetingNotes);
			
			
			//调接口上传
			String fileKey = String.valueOf(IdGenerator.generateId(OSSHelper.class));
			String bucketName = BucketName.DEV.getName();
			Map<String,Object> map = sopFileService.aLiColoudUpload(request, fileKey, bucketName);
//			File file = sopFileService.aLiColoudUpload(request, fileKey, bucketName);
			
			//上传成功后
			if(map!=null){
				Map<String,String> nameMap = (Map<String, String>) map.get("nameMap");
				File file = (File) map.get("file");
				String fileName = "";
				if(fname!=null && fname.trim().length()>0){
					fileName = fname;
				}else{
					fileName = nameMap.get("fileName"); // secondarytile.png  全名
				}
				if(fileName == null || fileName.trim().length()==0){
					responseBody.setResult(new Result(Status.ERROR,null, "获取文件名失败"));
					return responseBody;
				}//end get file name 
				
				SopFile sopFile = new SopFile();
				sopFile.setProjectId(project.getId());
				sopFile.setProjectProgress(project.getProjectProgress());
				sopFile.setBucketName(bucketName); 
				sopFile.setFileKey(fileKey);  
				sopFile.setFileLength(file.length());  //文件大小
				sopFile.setFileName(fileName);
				sopFile.setFileSuffix(nameMap.get("fileSuffix"));
				sopFile.setFileUid(user.getId());	 //上传人
				sopFile.setCareerLine(user.getDepartmentId());
				sopFile.setFileType(DictEnum.fileType.音频文件.getCode());   //存储类型
				sopFile.setFileSource(DictEnum.fileSource.内部.getCode());  //档案来源
				//sopFile.setFileWorktype(fileWorkType);    //业务分类
				sopFile.setFileStatus(DictEnum.fileStatus.已上传.getCode());  //档案状态
				
				//调接口保存 meetrecord 、 sopfile
				boolean equalNowPrograss = true;  
				int operationPro = Integer.parseInt(prograss.substring(prograss.length()-1)) ;//会议对应的阶段
				int projectPro = Integer.parseInt(project.getProjectProgress().substring(project.getProjectProgress().length()-1)) ; //项目阶段
				if(projectPro > operationPro){
					equalNowPrograss = false;
				}
				Long id = meetingRecordService.insertMeet(meetingRecord,project,sopFile,equalNowPrograss);
			
				responseBody.setId(id);
				responseBody.setResult(new Result(Status.OK, ""));
				
				ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId());
			}else{
				responseBody.setResult(new Result(Status.ERROR,null, "上传失败"));
				return responseBody;
			}
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "操作失败"));
			if(logger.isErrorEnabled()){
				logger.error("add meetingRecord faild ",e);
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
	@com.galaxyinternet.common.annotation.Logger
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
			responseBody.setResult(new Result(Status.ERROR,null, "请完善会议信息"));
			return responseBody;
		}
		
		String prograss = "";
		if(meetingRecord.getMeetingType().equals(DictEnum.meetingType.内评会.getCode())){       //字典  会议类型   内评会
			prograss = DictEnum.projectProgress.内部评审.getCode();                                 	//字典  项目进度  内部评审
			
		}else if(meetingRecord.getMeetingType().equals(DictEnum.meetingType.CEO评审.getCode())){ //字典  会议类型   CEO评审
			prograss = DictEnum.projectProgress.CEO评审.getCode(); 								//字典   项目进度  CEO评审
			
		}else if(meetingRecord.getMeetingType().equals(DictEnum.meetingType.立项会.getCode())){	//字典  会议类型   立项会
			prograss = DictEnum.projectProgress.立项会.getCode(); 										//字典   项目进度    立项会
			
		}else if(meetingRecord.getMeetingType().equals(DictEnum.meetingType.投决会.getCode())){	//字典  会议类型   投决会
			prograss = DictEnum.projectProgress.投资决策会.getCode(); 									//字典   项目进度     投资决策会
		}/*else{
			responseBody.setResult(new Result(Status.ERROR,null, "会议类型无法识别"));
			return responseBody;
		}*/
		
		try {
			//project id 验证
			Project project = new Project();
			project = projectService.queryById(meetingRecord.getProjectId());
			
			String err = errMessage(project,user,prograss);
			if(err!=null && err.length()>0){
				responseBody.setResult(new Result(Status.ERROR,null, err));
				return responseBody;
			}
		
			boolean equalNowPrograss = true;
			int operationPro = Integer.parseInt(prograss.substring(prograss.length()-1)) ;//会议对应的阶段
			int projectPro = Integer.parseInt(project.getProjectProgress().substring(project.getProjectProgress().length()-1)) ; //项目阶段
			if(projectPro > operationPro){
				equalNowPrograss = false;
			}
			SopFile file = new SopFile();
			file.setCareerLine(user.getDepartmentId());
			file.setFileUid(user.getId());
			Long id = meetingRecordService.insertMeet(meetingRecord,project,file,equalNowPrograss);
			//Long id = meetingRecordService.insertMeet(meetingRecord,project, user.getId(),user.getDepartmentId());
			
			responseBody.setId(id);
			responseBody.setResult(new Result(Status.OK, ""));
			ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId());
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "操作失败"));
			
			if(logger.isErrorEnabled()){
				logger.error("add meetingRecord faild ",e);
			}
		}
		
		return responseBody;
	}
	
	
	/**
	 * 内部评审、 CEO评审 、 立项会、投决会  阶段
	 * 			查询个人项目下的会议记录
	 * @param    
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryMeet", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingRecordBo> queryMeet(HttpServletRequest request,@RequestBody MeetingRecordBo query ) {
		
		ResponseData<MeetingRecordBo> responseBody = new ResponseData<MeetingRecordBo>();
		
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
		try {
			query.setUid(user.getId());
			
			Page<MeetingRecordBo> pageList = meetingRecordService.queryMeetPageList(query, new PageRequest(query.getPageNum()==null?0:query.getPageNum(), query.getPageSize()==null?10:query.getPageSize()));
			responseBody.setPageList(pageList);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
			
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"查询失败"));
			
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
		
		String err = errMessage(project,user,DictEnum.projectProgress.CEO评审.getCode());   //字典   项目进度  CEO评审
		if(err!=null && err.length()>0){
			responseBody.setResult(new Result(Status.ERROR,null, err));
			return responseBody;
		}
		
		if(!project.getProjectStatus().equals(DictEnum.meetingResult.通过.getCode())){   //字典 项目状态 = 会议结论  通过
			responseBody.setResult(new Result(Status.ERROR,null, "会议未通过"));
			return responseBody;
		}
		
		try {
			meetingRecordService.projectSchedule(project);
			
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(project.getId());
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "projectSchedule faild"));
			
			if(logger.isErrorEnabled()){
				logger.error("update project faild ",e);
			}
		}
		
		return responseBody;
	}
	
	
	/**
	 * 显示文件上传信息;  
	 * 投资意向书  ； 尽职调查     ； 投资协议     ; 股权交割     
	 * @param   proProgress 项目当前阶段
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/proFileInfo/{pid}/{progress}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopFile> proFileInfo(HttpServletRequest request,@PathVariable("pid") Long pid,@PathVariable("progress") String progress) {
		
		ResponseData<SopFile> responseBody = new ResponseData<SopFile>();
		
		List<String> fileworktypeList = new ArrayList<String>();
		String proProgress="projectProgress:"+progress;
		
		//根据角色判断-显示文件上传列表
		User user =(User)request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
		
		if(proProgress!=null){
			if(proProgress.equals(DictEnum.projectProgress.投资意向书.getCode()) && roleIdList.contains(UserConstant.TZJL)){      //字典   项目进度     投资意向书
				fileworktypeList.add(DictEnum.fileWorktype.投资意向书.getCode());   //字典   档案业务类型   投资意向书
				
			}else if(proProgress.equals(DictEnum.projectProgress.尽职调查.getCode())){  //字典   项目进度     尽职调查
				
				//人事|投资经理
				if(roleIdList.contains(UserConstant.TZJL) || roleIdList.contains(UserConstant.HRJL) 
						|| roleIdList.contains(UserConstant.HHR) || roleIdList.contains(UserConstant.HRZJ)){
				         fileworktypeList.add(DictEnum.fileWorktype.人力资源尽职调查报告.getCode());  //字典   档案业务类型   尽职调查报告
				}
				//财务|投资经理
				if(roleIdList.contains(UserConstant.TZJL) || roleIdList.contains(UserConstant.CWJL) 
						|| roleIdList.contains(UserConstant.CWZJ)){
				        fileworktypeList.add(DictEnum.fileWorktype.财务尽职调查报告.getCode());
				}
				//法务|投资经理
				if(roleIdList.contains(UserConstant.TZJL) || roleIdList.contains(UserConstant.FWJL) 
						|| roleIdList.contains(UserConstant.FWZJ)){
				        fileworktypeList.add(DictEnum.fileWorktype.法务尽职调查报告.getCode());
				}
				//投资经理
				if(roleIdList.contains(UserConstant.TZJL)){
				        fileworktypeList.add(DictEnum.fileWorktype.业务尽职调查报告.getCode());
				}
				
			}else if(proProgress.equals(DictEnum.projectProgress.投资协议.getCode())){   //字典   项目进度     投资协议 
				        fileworktypeList.add(DictEnum.fileWorktype.投资协议.getCode());      //字典   档案业务类型   投资协议
				        fileworktypeList.add(DictEnum.fileWorktype.股权转让协议.getCode());     //字典   档案业务类型   股权转让协议
				
			}else if(proProgress.equals(DictEnum.projectProgress.股权交割.getCode())){   //字典   项目进度   股权交割
				//财务|投资经理
				if(roleIdList.contains(UserConstant.TZJL) || roleIdList.contains(UserConstant.CWJL) ||
						roleIdList.contains(UserConstant.CWZJ)){
				       fileworktypeList.add(DictEnum.fileWorktype.资金拨付凭证.getCode());   //字典   档案业务类型   资金拨付凭证
				}
				//法务|投资经理
				if(roleIdList.contains(UserConstant.TZJL) || roleIdList.contains(UserConstant.FWJL) 
						|| roleIdList.contains(UserConstant.FWZJ)){
				       fileworktypeList.add(DictEnum.fileWorktype.工商转让凭证.getCode());  //字典   档案业务类型   工商变更登记凭证
				}
				
			}else{
				responseBody.setResult(new Result(Status.OK,null, "项目阶段类型不能识别"));
				return responseBody;
			}
		}else{
			responseBody.setResult(new Result(Status.ERROR,null, "项目阶段为空"));
			return responseBody;
		}
		

		try {
			List<SopFile> fileList = new ArrayList<SopFile>();
			
			SopFileBo  sbo =  new SopFileBo();
			sbo.setProjectId(pid);
			sbo.setProjectProgress(proProgress);
			sbo.setFileworktypeList(fileworktypeList);
			
			fileList = sopFileService.selectByFileTypeList(sbo);
			
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setEntityList(fileList);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "操作失败"));
			
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
	@RequestMapping(value = "/upProjectFile/{pid}/{fileid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> upProjectFile(HttpServletRequest request,String workType,@PathVariable Long pid,@PathVariable Long fileid,@RequestBody SopFile sopFile) {
		
		ResponseData<Project> responseBody = new ResponseData<Project>();
		
		String proProgress = "";
		String taskName = "";
		//根据角色判断-显示文件上传列表
		User user =(User)request.getSession().getAttribute(Constants.SESSION_USER_KEY);
				
		if(workType!=null){
			if(workType.equals(DictEnum.fileWorktype.投资意向书.getCode())){  //字典   档案业务类型   投资意向书
				proProgress = DictEnum.projectProgress.投资意向书.getCode() ;									  
				taskName = "上传投资意向书";
				
			}else if(workType.equals(DictEnum.fileWorktype.业务尽职调查报告.getCode())){
				proProgress = DictEnum.projectProgress.尽职调查.getCode() ;
				taskName = "上传业务尽职调查报告";
				
			}else if(workType.equals(DictEnum.fileWorktype.投资协议.getCode())){
				proProgress = DictEnum.projectProgress.投资协议.getCode() ; 
				taskName = "上传投资协议";
				
			}else if(workType.equals(DictEnum.fileWorktype.股权转让协议.getCode())){
				proProgress = DictEnum.projectProgress.投资协议.getCode() ; 
				taskName = "上传股权转让协议";
				
			}else if(workType.equals(DictEnum.fileWorktype.人力资源尽职调查报告.getCode())){
				proProgress = DictEnum.projectProgress.尽职调查.getCode() ;
				taskName = "上传人力资源尽职调查报告";
				
			}else if(workType.equals(DictEnum.fileWorktype.财务尽职调查报告.getCode())){
				proProgress = DictEnum.projectProgress.尽职调查.getCode() ;
				taskName = "上传财务尽职调查报告";
				
			}else if(workType.equals(DictEnum.fileWorktype.法务尽职调查报告.getCode())){
				proProgress = DictEnum.projectProgress.尽职调查.getCode() ;
				taskName = "上传法务尽职调查报告";
				
			}else if(workType.equals(DictEnum.fileWorktype.工商转让凭证.getCode())){
				proProgress = DictEnum.projectProgress.股权交割.getCode() ;
				taskName = "上传工商转让凭证";
				
			}else if(workType.equals(DictEnum.fileWorktype.资金拨付凭证.getCode())){
				proProgress = DictEnum.projectProgress.股权交割.getCode() ;
				taskName = "上传资金拨付凭证";
				
			}else{
				responseBody.setResult(new Result(Status.OK, "文件业务类型不能识别"));
				return responseBody;
			}
		}else{
			responseBody.setResult(new Result(Status.ERROR,null, "文件业务类型为空"));
			return responseBody;
		}
		
		//project id 验证
		Project project = new Project();
		project = projectService.queryById(pid);
		
		//项目为 内部创建，不需要  股权转让协议
		if(project.getProjectType()!=null && project.getProjectType().equals(DictEnum.projectType.内部创建.getCode()) && workType.equals(DictEnum.fileWorktype.股权转让协议.getCode())){
			responseBody.setResult(new Result(Status.ERROR,null, "该项目不需要股权转让协议"));
			return responseBody;
		}
				
		String err = errMessage(project,user,proProgress);
		if(err!=null && err.length()>0){
			responseBody.setResult(new Result(Status.ERROR,null, err));
			return responseBody;
		}

		try {
			
			//上传成功修改 sopfile里面的数据-根据fileid修改sopfile
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			//文件上传
			String key = String.valueOf(IdGenerator.generateId(OSSHelper.class));
			MultipartFile multipartFile = multipartRequest.getFile("file");
			try{
				File file = (File)multipartFile;
				OSSHelper.simpleUploadByOSS(file,key);
				sopFileService.updateById(sopFile);
			}catch(Exception e){
				logger.error("上传文件错误：", e);
				responseBody.setResult(new Result(Status.ERROR,"上传文件失败!"));
			}
			SopTask task = new SopTask();
			task.setProjectId(pid);
			task.setTaskName(taskName);
			task.setAssignUid(user.getId());
			task = sopTaskService.queryOne(task);
			if(task==null){
				responseBody.setResult(new Result(Status.ERROR, null,"任务检索为空"));
				return responseBody;
			}
			
			//修改任务状态完成
			task.setTaskStatus(DictEnum.taskStatus.已完成.getCode());
			sopTaskService.updateById(task);
			
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(project.getId());
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "upProjectFile-task faild"));
			
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
		
		String err = errMessage(project,user,DictEnum.projectProgress.投资意向书.getCode());
		if(err!=null && err.length()>0){
			responseBody.setResult(new Result(Status.ERROR,null, err));
			return responseBody;
		}
		
		try {
			//上传  投资意向书  任务验证已完成
			SopTask task = new SopTask();
			task.setProjectId(pid);
			task.setTaskName("上传投资意向书");          //任务名称：    上传投资意向书
			task.setAssignUid(user.getId());
			task = sopTaskService.queryOne(task);
			if(task.getTaskStatus()==null || !task.getTaskStatus().equals(DictEnum.taskStatus.已完成.getCode())){
				responseBody.setResult(new Result(Status.ERROR,null, "Front task is not complete"));
				return responseBody;
			}
			
			//修改项目进度、生成任务
			meetingRecordService.upTermSheetSign(project,user.getId(),user.getDepartmentId());
			
			
			
			
			
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(project.getId());
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"upTermSheet faild"));
			
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
		
		String err = errMessage(project,user,DictEnum.projectProgress.尽职调查.getCode());
		if(err!=null && err.length()>0){
			responseBody.setResult(new Result(Status.ERROR,null, err));
			return responseBody;
		}
		
		try {
			//验证任务都完成
			SopTaskBo task = new SopTaskBo();
			task.setProjectId(pid);
			List<String> sl = new ArrayList<String>();
			sl.add(DictEnum.taskStatus.待认领.getCode());
			sl.add(DictEnum.taskStatus.待完工.getCode());
			task.setTaskStatusList(sl);
			List<SopTask> tlist = sopTaskService.selectForTaskOverList(task);
			if(tlist!=null && tlist.size()>0){
				responseBody.setResult(new Result(Status.ERROR,null, "task is not over"));
				return responseBody;
			}
			
			meetingRecordService.decisionSchedule(project);
			
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(project.getId());
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "applyDecision faild"));
			
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
		
		String err = errMessage(project,user,DictEnum.projectProgress.投资协议.getCode());
		if(err!=null && err.length()>0){
			responseBody.setResult(new Result(Status.ERROR,null, err));
			return responseBody;
		}
		
		try {
			//验证任务完成
			SopTaskBo task = new SopTaskBo();
			task.setProjectId(pid);
			task.setAssignUid(user.getId());
			List<String> sl = new ArrayList<String>();
			sl.add(DictEnum.taskStatus.待认领.getCode());
			sl.add(DictEnum.taskStatus.待完工.getCode());
			task.setTaskStatusList(sl);
			List<SopTask> tlist = sopTaskService.selectForTaskOverList(task);
			if(tlist!=null && tlist.size()>0){
				responseBody.setResult(new Result(Status.ERROR,null, "task is not over"));
				return responseBody;
			}
			
			//修改项目进度、生成任务
			meetingRecordService.upInvestmentSign(project);
			
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(project.getId());
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "upTermSheet faild"));
			
			if(logger.isErrorEnabled()){
				logger.error("update project faild ",e);
			}
		}
		
		return responseBody;
	}



	
	
	/**获取登录人的项目
	 * 		绑定登录人
	 * 		项目进度
	 * 		
	 * @param progress 1:接触访谈   2:
	 */	
	@ResponseBody
	@RequestMapping(value = "/queryPerPro", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> queryPerProList(HttpServletRequest request,String progress ,String nameLike,String meetingType) {
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		ResponseData<Project> responseBody = new ResponseData<Project>();
		List<Project> perProList = new ArrayList<Project>();
		try {
			ProjectBo query = new ProjectBo();
			/*if(progress!=null){
				query.setProjectProgress(progress);
			}*/
				
			if(meetingType!=null){
				if(meetingType.equals(DictEnum.meetingType.CEO评审.getCode())){
					query.setProjectProgress(DictEnum.projectProgress.CEO评审.getCode());
					
				}else if(meetingType.equals(DictEnum.meetingType.内评会.getCode())){
					query.setProjectProgress(DictEnum.projectProgress.内部评审.getCode());
					
				}else if(meetingType.equals(DictEnum.meetingType.立项会.getCode())){
					query.setProjectProgress(DictEnum.projectProgress.立项会.getCode());
					
				}else if(meetingType.equals(DictEnum.meetingType.投决会.getCode())){
					query.setProjectProgress(DictEnum.projectProgress.投资决策会.getCode());
				}
			}
			
			if(nameLike!=null){
				query.setNameLike(nameLike);
			}
			query.setCreateUid(user.getId());
			query.setResultCloseFilter(DictEnum.meetingResult.否决.getCode());//过滤已关闭
			perProList = projectService.queryList(query);
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setEntityList(perProList);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "queryPerPro faild"));
			if(logger.isErrorEnabled()){
				logger.error("queryPerPro faild ",e);
			}
		}
		
		return responseBody;
	}
	
	
	
	/**获取登录人的项目
	 * 		绑定登录人
	 * 		项目进度
	 * 		
	 * @param progress 1:接触访谈   2:   String progress ,String nameLike,String meetingType
	 */	
	@ResponseBody
	@RequestMapping(value = "/queryPerProPage", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> queryPerProPage(HttpServletRequest request,@RequestBody ProjectBo query ) {
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		ResponseData<Project> responseBody = new ResponseData<Project>();
		
		try {
			if(query.getMeetingType()!=null){
				if(query.getMeetingType().equals(DictEnum.meetingType.CEO评审.getCode())){
					query.setProjectProgress(DictEnum.projectProgress.CEO评审.getCode());
					
				}else if(query.getMeetingType().equals(DictEnum.meetingType.内评会.getCode())){
					query.setProjectProgress(DictEnum.projectProgress.内部评审.getCode());
					
				}else if(query.getMeetingType().equals(DictEnum.meetingType.立项会.getCode())){
					query.setProjectProgress(DictEnum.projectProgress.立项会.getCode());
					
				}else if(query.getMeetingType().equals(DictEnum.meetingType.投决会.getCode())){
					query.setProjectProgress(DictEnum.projectProgress.投资决策会.getCode());
				}
			}
			query.setCreateUid(user.getId());
			query.setResultCloseFilter(DictEnum.meetingResult.否决.getCode());//过滤已关闭
			//query.setProjectStatus(DictEnum.meetingResult.否决.getCode()); //not equals
			Page<Project> pageProject = projectService.queryPageList(query,new PageRequest(query.getPageNum(), query.getPageSize()));
			responseBody.setPageList(pageProject);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "queryPerProPage faild"));
			if(logger.isErrorEnabled()){
				logger.error("queryPerPro faild ",e);
			}
		}
		
		return responseBody;
	}
	
	
	
	
	
	
	/**
	 * app
	 * 下载文件 
	 * @param service
	 */
	@RequestMapping("/downloadAppFile/{id}")
    public ResponseData<SopFile> download(@PathVariable("id") Long id,HttpServletRequest request,HttpServletResponse response) throws IOException { 
    	InputStream inputStream = null;
    	ServletOutputStream out = null;
    	
        SopFile queryfile = sopFileService.queryById(id);
		if(queryfile == null){
			return null;
		}
		
		String key = queryfile.getFileKey();
		long fSize = queryfile.getFileLength();
		
		String downFileName = queryfile.getFileName()+ "." + queryfile.getFileSuffix();
		String path = request.getSession().getServletContext().getRealPath("upload") + File.separator + downFileName;  
		
        // File tempfile=new File(path);  
        // OSSHelper.simpleDownloadByOSS(temp, key);

        
		String fileName= "";
		if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {  
			fileName = URLEncoder.encode(downFileName, "UTF-8");  
		} else {  
			fileName = new String((downFileName).getBytes("UTF-8"), "ISO8859-1");  
		} 
		response.reset();
		response.setCharacterEncoding("utf-8");  
        response.setContentType("application/x-download");    
        response.setHeader("Accept-Ranges", "bytes");    
        response.setHeader("Content-Length", String.valueOf(fSize));    
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName); 
        
        //inputStream = new FileInputStream(file);
        long pos = 0;
        String range = null;
        if(null != request.getHeader("Range")){
        	response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
        	range = request.getHeader("Range").trim();
        }
        
        try{
        	String temp = range.substring(range.indexOf("=")+1,range.indexOf("-"));
        	pos = Long.parseLong(temp);
        }catch(Exception e){
        	pos = 0;
        }
        
        try{
        	out = response.getOutputStream();
	        String contentRange = new StringBuffer("bytes").append(pos).append("-").append(fSize-1).append("/").append(fSize).toString();
	        response.setHeader("Content-Range", contentRange);
	        inputStream.skip(pos);
	        
        	byte[] buffer = new byte[1024*10];  
            int length = 0;    
            while ((length = inputStream.read(buffer, 0, buffer.length)) != -1) {    
                out.write(buffer, 0, length);  
            }
        }catch(Exception e){
        	System.out.println("ODEX软件下载异常："+e);
        }
        
        return null;
        
    }
	
	
	
	
	
	
	
	
}
