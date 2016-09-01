package com.galaxyinternet.project.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.common.annotation.LogType;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.file.UploadFileResult;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.oss.OSSFactory;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.framework.core.utils.JSONUtils;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.InterviewRecordService;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.MeetingSchedulingService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
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
	private InterviewRecordService interviewRecordService;
	
	@Autowired
	private MeetingSchedulingService meetingSchedulingService;
	
	@Autowired
	private  SopFileService sopFileService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	@Override
	protected BaseService<Project> getBaseService() {
		return this.projectService;
	}
	
	private String tempfilePath;
	
	
	public String getTempfilePath() {
		return tempfilePath;
	}
	@Value("${sop.oss.tempfile.path}")
	public void setTempfilePath(String tempfilePath) {
		this.tempfilePath = tempfilePath;
	}
	
	
	
	public String errMessage(Project project,User user,String prograss){
		if(project == null){
			return "项目检索为空";
		}else if(project.getProjectStatus().equals(DictEnum.meetingResult.否决.getCode())||project.getProjectStatus().equals(DictEnum.projectStatus.YFJ.getCode())){ //字典 项目状态 = 会议结论 关闭
			return "项目已经关闭";
		}else if(project.getProjectStatus().equals(DictEnum.projectStatus.YTC.getCode())){ //字典 项目状态 = 会议结论 关闭
			return "项目已退出";
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
	
	private Map<String, String> transFileNames(String fileName) {
		Map<String, String> retMap = new HashMap<String, String>();
		int dotPos = fileName.lastIndexOf(".");
		if(dotPos == -1){
			retMap.put("fileName", fileName);
			retMap.put("fileSuffix", "");
		}else{
			retMap.put("fileName", fileName.substring(0, dotPos));
			retMap.put("fileSuffix", fileName.substring(dotPos+1));
		}
		return retMap;
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
	 * 添加页面
	 */
	@RequestMapping(value = "/interViewLog", method = RequestMethod.GET)
	public String interViewLog() {
		return "project/sop/viewLogorEdit";
	}
	
	
	/**
	 * 接触访谈阶段: 附件添加 -访谈添加
	 * @param   interviewRecord 
	 * 			produces="application/text;charset=utf-8"
	 * @RequestBody InterviewRecord interviewRecord ,
	 * @return
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG, LogType.MESSAGE })
	@ResponseBody
	@RequestMapping(value = "/addFileInterview", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InterviewRecord> addFileInterview(InterviewRecordBo interviewRecord,HttpServletRequest request,HttpServletResponse response ) {
		ResponseData<InterviewRecord> responseBody = new ResponseData<InterviewRecord>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
		if(interviewRecord.getProjectId() == null){
			String json = JSONUtils.getBodyString(request);
			interviewRecord = GSONUtil.fromJson(json, InterviewRecordBo.class);
		}
		
		if(interviewRecord == null || interviewRecord.getProjectId() == null || interviewRecord.getViewDate() == null || interviewRecord.getViewTarget() == null ){
			responseBody.setResult(new Result(Status.ERROR,null, "请完善访谈信息"));
			return responseBody;
		}
		interviewRecord.setCreatedId(user.getId());
		try {
			//project  验证
			Project project = new Project();
			project = projectService.queryById(interviewRecord.getProjectId());
			String err = errMessage(project,user,null);   //字典  项目进度  接触访谈 
			if(err!=null && err.length()>0){
				responseBody.setResult(new Result(Status.ERROR,null, err));
				return responseBody;
			}
			
			//保存
			Long id = null;
			if(interviewRecord.getFkey()!=null){
				if(interviewRecord.getFileLength()==null||interviewRecord.getFname()==null){
					responseBody.setResult(new Result(Status.ERROR,null, "请完善附件信息"));
					return responseBody;
				}
				if(interviewRecord.getBucketName()==null){
					interviewRecord.setBucketName(OSSFactory.getDefaultBucketName());
				}		
						
				Map<String,String> nameMap = transFileNames(interviewRecord.getFname());
				SopFile sopFile = new SopFile();
				sopFile.setBucketName(interviewRecord.getBucketName());
				sopFile.setFileKey(interviewRecord.getFkey());
				sopFile.setFileLength(interviewRecord.getFileLength());
				sopFile.setFileName(nameMap.get("fileName"));
				sopFile.setFileSuffix(nameMap.get("fileSuffix"));
				
				sopFile.setProjectId(project.getId());
				sopFile.setProjectProgress(project.getProjectProgress());
				sopFile.setFileUid(user.getId());	 //上传人
				sopFile.setCareerLine(user.getDepartmentId());
				sopFile.setFileType(DictEnum.fileType.音频文件.getCode());   //存储类型
				sopFile.setFileSource(DictEnum.fileSource.内部.getCode());  //档案来源
				//sopFile.setFileWorktype(fileWorkType);    //业务分类
				sopFile.setFileStatus(DictEnum.fileStatus.已上传.getCode());  //档案状态
				id = interviewRecordService.insertInterview(interviewRecord,sopFile);
			}else if(!ServletFileUpload.isMultipartContent(request)){
				id = interviewRecordService.insert(interviewRecord);
			}else if(ServletFileUpload.isMultipartContent(request)){
				String fileKey = String.valueOf(IdGenerator.generateId(OSSHelper.class));
				UploadFileResult result = uploadFileToOSS(request, fileKey, tempfilePath);
			//	Map<String,Object> map = sopFileService.aLiColoudUpload(request, fileKey);//上传aliyun接口
				//上传成功后
				if(result!=null){
					Map<String,String> nameMap = new HashMap<String,String>();
				//	MultipartFile file = (MultipartFile) map.get("file");
					String fileName = "";
					if(interviewRecord.getFname()!=null && interviewRecord.getFname().trim().length()>0){
						fileName = interviewRecord.getFname().trim();
						nameMap = transFileNames(fileName);
					}else{
						 nameMap.put("fileName",result.getFileName());
						 nameMap.put("fileSuffix", result.getFileSuffix());
					}
					if(nameMap.get("fileName") == null || nameMap.get("fileName").trim().length()==0){
						responseBody.setResult(new Result(Status.ERROR,null, "文件名获取失败"));
						return responseBody;
					}//end get file name 
					
					SopFile sopFile = new SopFile();
					sopFile.setProjectId(project.getId());
					sopFile.setProjectProgress(project.getProjectProgress());
					sopFile.setBucketName(OSSFactory.getDefaultBucketName()); 
					sopFile.setFileKey(fileKey);  
					sopFile.setFileLength(result.getContentLength());  //文件大小
					sopFile.setFileName(nameMap.get("fileName"));
					sopFile.setFileSuffix(nameMap.get("fileSuffix"));
					sopFile.setFileUid(user.getId());	 //上传人
					sopFile.setCareerLine(user.getDepartmentId());
					sopFile.setFileType(DictEnum.fileType.音频文件.getCode());   //存储类型
					sopFile.setFileSource(DictEnum.fileSource.内部.getCode());  //档案来源
					//sopFile.setFileWorktype(fileWorkType);    //业务分类
					sopFile.setFileStatus(DictEnum.fileStatus.已上传.getCode());  //档案状态
					
					id = interviewRecordService.insertInterview(interviewRecord,sopFile); //掉接口保存 interview、sopfile
				}else{
					responseBody.setResult(new Result(Status.ERROR,null, "访谈添加文件上传失败"));
					return responseBody;
				}
			}
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(id);
			//ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId());
			ControllerUtils.setRequestParamsForMessageTip(request, null, project.getProjectName(), project.getId(), "3", UrlNumber.one);

		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "访谈添加失败"));
			
			if(logger.isErrorEnabled()){
				logger.error("addFileInterview 访谈添加失败",e);
			}
		}
		return responseBody;
	}
	
	
	
	/**
	 * OSS访谈录音追加
	 * @param   interviewRecord 
	 * 			produces="application/text;charset=utf-8"
	 * @param viewid 访谈id
	 * @return responseBody.setId(id) fileId
	 */
	@com.galaxyinternet.common.annotation.Logger
	@ResponseBody
	@RequestMapping(value = "/addInterview/{viewid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopFile> addFileForView(@PathVariable Long viewid ,@RequestBody SopFile sopFile, HttpServletRequest request ) {
		
		ResponseData<SopFile> responseBody = new ResponseData<SopFile>();
		try {
			User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			if(sopFile == null || sopFile.getFileKey()==null || sopFile.getBucketName()==null || sopFile.getFileLength()==null||sopFile.getFileName()==null){
				responseBody.setResult(new Result(Status.ERROR,null, "请完善附件信息"));
				return responseBody;
			}
			
			//view验证
			InterviewRecord view = interviewRecordService.queryById(viewid);
			if(view == null || view.getId() == null){
				responseBody.setResult(new Result(Status.ERROR,null, "访谈为空"));
				return responseBody;
			}
			//project id 验证
			Project project = projectService.queryById(view.getProjectId());
			if(project == null || project.getId() == null){
				responseBody.setResult(new Result(Status.ERROR,null, "关联项目为空"));
				return responseBody;
			}
			Map<String,String> nameMap = transFileNames(sopFile.getFileName());
			
			sopFile.setProjectId(project.getId());
			sopFile.setProjectProgress(project.getProjectProgress());
			sopFile.setFileName(nameMap.get("fileName"));
			sopFile.setFileSuffix(nameMap.get("fileSuffix"));
			sopFile.setFileUid(user.getId());	 //上传人
			sopFile.setCareerLine(user.getDepartmentId());
			sopFile.setFileType(DictEnum.fileType.音频文件.getCode());   //存储类型
			sopFile.setFileSource(DictEnum.fileSource.内部.getCode());  //档案来源
			//sopFile.setFileWorktype(fileWorkType);    //业务分类
			sopFile.setFileStatus(DictEnum.fileStatus.已上传.getCode());  //档案状态
			
			//调用接口 修改view 新增 sopfile，返回fileid
			Long fileid = interviewRecordService.updateViewForFile(sopFile,view);
			if(fileid == null){
				responseBody.setResult(new Result(Status.ERROR,null, "录音追加失败"));
				logger.error("addInterview addFileForView 录音追加失败，返回更新recordview为0 ");
				return responseBody;
			}
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(fileid);
			ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId());
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "录音追加失败"));
			
			if(logger.isErrorEnabled()){
				logger.error("addInterview addFileForView 录音追加失败",e);
			}
		}
		
		return responseBody;
	}

	@com.galaxyinternet.common.annotation.Logger
	@ResponseBody
	@RequestMapping(value = "/updateInterview", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InterviewRecord> updateInterview(@RequestBody InterviewRecord interviewRecord, HttpServletRequest request ) {
		ResponseData<InterviewRecord> responseBody = new ResponseData<InterviewRecord>();
		try {
			interviewRecordService.updateById(interviewRecord);
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(interviewRecord.getId());
			
			interviewRecord = interviewRecordService.queryById(interviewRecord.getId());
			Project project = projectService.queryById(interviewRecord.getProjectId());
			
			ControllerUtils.setRequestParamsForMessageTip(request, null, project.getProjectName(), project.getId(), "3", UrlNumber.one);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "修改访谈记录失败"));
			
			if(logger.isErrorEnabled()){
				logger.error("updateInterview 修改访谈记录失败",e);
			}
		}
		return responseBody;
	}
	
	
	
	
	/**
	 * 访谈查询,
	 * 			投资经理： 查询个人项目下		的访谈记录  
	 * 			合伙人：	    查询个人事业线下	的访谈记录  
	 * 			高管： 	    查询所有			的访谈记录  
	 * @param   interviewRecord 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryInterview", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InterviewRecordBo> queryInterview(HttpServletRequest request,@RequestBody InterviewRecordBo query ) {
		ResponseData<InterviewRecordBo> responseBody = new ResponseData<InterviewRecordBo>();
		
		try {
			User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
			if(roleIdList.contains(UserConstant.CEO) || roleIdList.contains(UserConstant.DSZ)){  //无限制，根据传参查询
				//query.setUid(null);
			}else if(roleIdList.contains(UserConstant.HHR)){   //固定为其部门
				query.setDepartId(user.getDepartmentId());
			}else if(roleIdList.contains(UserConstant.TZJL)){  //固定为其创建
				query.setUid(user.getId());
			}else{
				responseBody.setResult(new Result(Status.ERROR, null, "没有权限查看!"));
				return responseBody;
			}

			if(query.getProjectId()!=null){
				List<Project> proList = null;
				boolean checked = false;
				if(roleIdList.contains(UserConstant.HHR)){   //部门下项目校验
					Project  proQ = new Project();
					proQ.setId(query.getProjectId());
					proQ.setProjectDepartid(user.getDepartmentId());
					proList = projectService.queryList(proQ);
					checked = true;
				}else if(roleIdList.contains(UserConstant.TZJL)){  //个人下项目校验
					Project  proQ = new Project();
					proQ.setCreateUid(user.getId());
					proQ.setId(query.getProjectId());
					proList = projectService.queryList(proQ);
					checked = true;
				}
				if(checked){
					if(proList==null || proList.isEmpty()){
						responseBody.setResult(new Result(Status.ERROR, null, "没有权限查看!"));
						return responseBody;
					}
				}
			}
			
			Page<InterviewRecordBo> pageList = interviewRecordService.queryInterviewPage(query,  new PageRequest(query.getPageNum()==null?0:query.getPageNum(), query.getPageSize()==null?10:query.getPageSize()) );
			responseBody.setPageList(pageList);
			responseBody.setResult(new Result(Status.OK, ""));
			
			return responseBody;
			
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"查询失败"));
			
			if(logger.isErrorEnabled()){
				logger.error("queryInterview 查询失败",e);
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
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG, LogType.MESSAGE })
	@ResponseBody
	@RequestMapping(value = "/addfilemeet", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingRecord> addFileMeet(MeetingRecordBo meetingRecord,HttpServletRequest request,HttpServletResponse response  ) {
		ResponseData<MeetingRecord> responseBody = new ResponseData<MeetingRecord>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if(meetingRecord.getProjectId() == null){
			String json = JSONUtils.getBodyString(request);
			meetingRecord = GSONUtil.fromJson(json, MeetingRecordBo.class);
		}
		if(meetingRecord.getProjectId() == null 
				|| meetingRecord.getMeetingDate() == null 
				|| meetingRecord.getMeetingType() == null 
				|| meetingRecord.getMeetingResult() == null ){
			responseBody.setResult(new Result(Status.ERROR,null, "请完善会议信息"));
			return responseBody;
		}
		
		MeetingRecord mrQuery = new MeetingRecord();
		
		//时间判断：在  待定会议日期   之前的时间段   不能添加 通过/否决的 会议记录
		/*if(meetingRecord.getMeetingResult().equals(DictEnum.meetingResult.通过.getCode())|| meetingRecord.getMeetingResult().equals(DictEnum.meetingResult.否决.getCode())){
			mrQuery.setProjectId(meetingRecord.getProjectId());
			mrQuery.setMeetingResult(DictEnum.meetingResult.待定.getCode());
			List<MeetingRecord> checMeetList = meetingRecordService.queryList(mrQuery);//sqlMapper默认按时间倒叙
			if(checMeetList != null && !checMeetList.isEmpty()){
				if(meetingRecord.getMeetingDate().compareTo(checMeetList.get(0).getMeetingDate())<0){
					responseBody.setResult(new Result(Status.ERROR,null, ""+meetingRecord.getMeetingDateStr()+" 之后存在会议记录"));
					return responseBody;
				}
			}
		}*/
		
		try {
			String prograss = "";
			UrlNumber uNum = null;
			String messageType = null;
			if(meetingRecord.getMeetingType().equals(DictEnum.meetingType.内评会.getCode())){       
				prograss = DictEnum.projectProgress.内部评审.getCode();                                 	
				uNum = UrlNumber.one;
				if(meetingRecord.getMeetingResult().equals(DictEnum.meetingResult.通过.getCode())){
					messageType = "6.3";
				}else{
					messageType = "4.1";
				}
			}else if(meetingRecord.getMeetingType().equals(DictEnum.meetingType.CEO评审.getCode())){ 
				prograss = DictEnum.projectProgress.CEO评审.getCode(); 								
				uNum = UrlNumber.two;
				messageType = "4.2";
			}else if(meetingRecord.getMeetingType().equals(DictEnum.meetingType.立项会.getCode())){	
				prograss = DictEnum.projectProgress.立项会.getCode(); 										
				uNum = UrlNumber.three;
				if(meetingRecord.getMeetingResult().equals(DictEnum.meetingResult.通过.getCode())){
					messageType = "6.5";
				}else{
					messageType = "4.3";
				}
			}else if(meetingRecord.getMeetingType().equals(DictEnum.meetingType.投决会.getCode())){
				prograss = DictEnum.projectProgress.投资决策会.getCode(); 								
				uNum = UrlNumber.four;
				if(meetingRecord.getMeetingResult().equals(DictEnum.meetingResult.通过.getCode())){
					messageType = "6.8";
				}else{
					messageType = "4.4";
				}
			}
			
			//project id 验证
			Project project = new Project();
			project = projectService.queryById(meetingRecord.getProjectId());
			String err = errMessage(project,user,prograss);
			if(err!=null && err.length()>0){
				responseBody.setResult(new Result(Status.ERROR,null, err));
				return responseBody;
			}
			
			
			//已有通过的会议，不能再添加会议纪要
			mrQuery = new MeetingRecord();
			mrQuery.setProjectId(meetingRecord.getProjectId());
			mrQuery.setMeetingType(meetingRecord.getMeetingType());
			mrQuery.setMeetingResult(DictEnum.meetingResult.通过.getCode());
			Long mrCount = meetingRecordService.queryCount(mrQuery);
			if(mrCount != null && mrCount.longValue() > 0L)
			{
				responseBody.setResult(new Result(Status.ERROR, "","已有通过的会议，不能再添加会议纪要!"));
				return responseBody;
			}
			
			//排期池校验
			if(meetingRecord.getMeetingType().equals(DictEnum.meetingType.CEO评审.getCode()) || meetingRecord.getMeetingType().equals(DictEnum.meetingType.立项会.getCode()) || meetingRecord.getMeetingType().equals(DictEnum.meetingType.投决会.getCode())){	
				MeetingScheduling ms = new MeetingScheduling();
				ms.setProjectId(meetingRecord.getProjectId());
				ms.setMeetingType(meetingRecord.getMeetingType());
				ms.setStatus(DictEnum.meetingResult.待定.getCode());  //排期按钮置为 待定
				//ms.setScheduleStatus(1);  //秘书排期   2 搁置
				//ms.setScheduleStatus(0);  //排期池
				List<MeetingScheduling> mslist = meetingSchedulingService.queryList(ms);
				if(mslist==null || mslist.isEmpty()){
					responseBody.setResult(new Result(Status.ERROR, "","未在排期池中，不能添加会议记录!"));
					return responseBody;
				}
			}
			
			//保存
			Long id = null;
			boolean equalNowPrograss = true; //判断当前阶段、之后阶段
			int operationPro = Integer.parseInt(prograss.substring(prograss.length()-1)) ;//会议对应的阶段
			int projectPro = Integer.parseInt(project.getProjectProgress().substring(project.getProjectProgress().length()-1)) ; //项目阶段
			if(projectPro > operationPro){
				equalNowPrograss = false;
			}
			
			if(meetingRecord.getFkey()!=null){
				if( meetingRecord.getFileLength()==null||meetingRecord.getFname()==null){
					responseBody.setResult(new Result(Status.ERROR,null, "请完善附件信息"));
					return responseBody;
				}
				if(meetingRecord.getBucketName()==null){
					meetingRecord.setBucketName(OSSFactory.getDefaultBucketName());
				}		
						
				Map<String,String> nameMap = transFileNames(meetingRecord.getFname());
				SopFile sopFile = new SopFile();
				sopFile.setBucketName(meetingRecord.getBucketName());
				sopFile.setFileKey(meetingRecord.getFkey());
				sopFile.setFileLength(meetingRecord.getFileLength());
				sopFile.setFileName(nameMap.get("fileName"));
				sopFile.setFileSuffix(nameMap.get("fileSuffix"));
				
				sopFile.setProjectId(project.getId());
				sopFile.setProjectProgress(project.getProjectProgress());
				sopFile.setFileUid(user.getId());	 //上传人
				sopFile.setCareerLine(user.getDepartmentId());
				sopFile.setFileType(DictEnum.fileType.音频文件.getCode());   //存储类型
				sopFile.setFileSource(DictEnum.fileSource.内部.getCode());  //档案来源
				//sopFile.setFileWorktype(fileWorkType);    //业务分类
				sopFile.setFileStatus(DictEnum.fileStatus.已上传.getCode());  //档案状态
				
				id = meetingRecordService.insertMeet(meetingRecord,project,sopFile,equalNowPrograss);
			}else if(!ServletFileUpload.isMultipartContent(request)){
				SopFile file = new SopFile();
				file.setCareerLine(user.getDepartmentId());
				file.setFileUid(user.getId());
				id = meetingRecordService.insertMeet(meetingRecord,project,file,equalNowPrograss);
			}else if(ServletFileUpload.isMultipartContent(request)){
				//调接口上传
				String fileKey = String.valueOf(IdGenerator.generateId(OSSHelper.class));
				//Map<String,Object> map = sopFileService.aLiColoudUpload(request, fileKey);
				UploadFileResult result = uploadFileToOSS(request, fileKey, tempfilePath);
				//上传成功后
				if(result!=null){
					Map<String,String> nameMap = new HashMap<String,String>();
					//MultipartFile file = (MultipartFile) map.get("file");
					String fileName = "";
					if(meetingRecord.getFname()!=null && meetingRecord.getFname().trim().length()>0){
						fileName = meetingRecord.getFname().trim();
						nameMap = transFileNames(fileName);
					}else{
						//nameMap = (Map<String, String>) map.get("nameMap");
					    nameMap.put("fileName",result.getFileName());
					    nameMap.put("fileSuffix", result.getFileSuffix());
					}
					if(nameMap.get("fileName") == null || nameMap.get("fileName").trim().length()==0){
						responseBody.setResult(new Result(Status.ERROR,null, "文件名获取失败"));
						return responseBody;
					}//end get file name 
					
					SopFile sopFile = new SopFile();
					sopFile.setProjectId(project.getId());
					sopFile.setProjectProgress(project.getProjectProgress());
					sopFile.setBucketName(OSSFactory.getDefaultBucketName()); 
					sopFile.setFileKey(fileKey);  
					//sopFile.setFileLength(file.getSize());  //文件大小
					sopFile.setFileLength(result.getContentLength());  //文件大小
					sopFile.setFileName(nameMap.get("fileName"));
					sopFile.setFileSuffix(nameMap.get("fileSuffix"));
					sopFile.setFileUid(user.getId());	 //上传人
					sopFile.setCareerLine(user.getDepartmentId());
					sopFile.setFileType(DictEnum.fileType.音频文件.getCode());   //存储类型
					sopFile.setFileSource(DictEnum.fileSource.内部.getCode());  //档案来源
					//sopFile.setFileWorktype(fileWorkType);    //业务分类
					sopFile.setFileStatus(DictEnum.fileStatus.已上传.getCode());  //档案状态
					
					id = meetingRecordService.insertMeet(meetingRecord,project,sopFile,equalNowPrograss);
				}else{
					responseBody.setResult(new Result(Status.ERROR,null, "上传失败"));
					return responseBody;
				}
			}
			
			responseBody.setId(id);
			responseBody.setResult(new Result(Status.OK, ""));
			
			ControllerUtils.setRequestParamsForMessageTip(request, null, project.getProjectName(), project.getId(), messageType, uNum);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "会议添加失败"));
			if(logger.isErrorEnabled()){
				logger.error("addfilemeet 会议添加失败 ",e);
			}
		}
		return responseBody;
	}
	
	@com.galaxyinternet.common.annotation.Logger
	@ResponseBody
	@RequestMapping(value = "/updatemeet", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingRecord> updatemeet(@RequestBody MeetingRecord meetingRecord, HttpServletRequest request ) {
		ResponseData<MeetingRecord> responseBody = new ResponseData<MeetingRecord>();
		try {
			if(meetingRecord.getId()==null){
				responseBody.setResult(new Result(Status.ERROR,null, "主键缺失"));
				return responseBody;
			}
			//RecordType { PROJECT((byte) 0, "项目"), IDEAS((byte) 1, "创意");
			//meetingRecord.setRecordType((byte) (1));
			meetingRecordService.updateById(meetingRecord);
		    responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(meetingRecord.getId());
			
			meetingRecord = meetingRecordService.queryById(meetingRecord.getId());
			Project project = projectService.queryById(meetingRecord.getProjectId());
			String messageType = null;
			if(meetingRecord.getMeetingType().equals(DictEnum.meetingType.内评会.getCode())){       
				messageType = "4.1";
			}else if(meetingRecord.getMeetingType().equals(DictEnum.meetingType.CEO评审.getCode())){ 
				messageType = "4.2";
			}else if(meetingRecord.getMeetingType().equals(DictEnum.meetingType.立项会.getCode())){	
				messageType = "4.3";
			}else if(meetingRecord.getMeetingType().equals(DictEnum.meetingType.投决会.getCode())){
				messageType = "4.4";
			}
			ControllerUtils.setRequestParamsForMessageTip(request, null, project.getProjectName(), project.getId(), messageType, UrlNumber.one);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "修改会议纪要失败"));
			logger.error("updatemeet 修改会议纪要失败",e);
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
		
		try {
			User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			
			//角色校验
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
			if(roleIdList.contains(UserConstant.CEO) || roleIdList.contains(UserConstant.DSZ)){  //无限制，根据传参查询
				//query.setUid(null);
			}else if(roleIdList.contains(UserConstant.HHR)){   //固定为其部门
				query.setDepartId(user.getDepartmentId());
			}else if(roleIdList.contains(UserConstant.TZJL)){  //固定为其创建
				query.setUid(user.getId());
			}else{
				responseBody.setResult(new Result(Status.ERROR, null, "没有权限查看!"));
				return responseBody;
			}
			
			if(query.getProjectId()!=null){
				List<Project> proList = null;
				boolean checked = false;
				if(roleIdList.contains(UserConstant.HHR)){   //部门下项目校验
					Project  proQ = new Project();
					proQ.setId(query.getProjectId());
					proQ.setProjectDepartid(user.getDepartmentId());
					proList = projectService.queryList(proQ);
					checked = true;
				}else if(roleIdList.contains(UserConstant.TZJL)){  //个人下项目校验
					Project  proQ = new Project();
					proQ.setCreateUid(user.getId());
					proQ.setId(query.getProjectId());
					proList = projectService.queryList(proQ);
					checked = true;
				}
				if(checked){
					if(proList==null || proList.isEmpty()){
						responseBody.setResult(new Result(Status.ERROR, null, "没有权限查看!"));
						return responseBody;
					}
				}
			}
			
			Page<MeetingRecordBo> pageList = meetingRecordService.queryMeetPage(query, new PageRequest(query.getPageNum()==null?0:query.getPageNum(), query.getPageSize()==null?10:query.getPageSize()));
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
	 * 会议详情;  
	 * @param  mid 会议id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detailMeet/{mid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingRecord> detailMeet(HttpServletRequest request,@PathVariable("mid") Long mid) {
		
		ResponseData<MeetingRecord> responseBody = new ResponseData<MeetingRecord>();
		try {
			MeetingRecord meetRecord = meetingRecordService.queryById(mid);
			if(meetRecord == null ||meetRecord.getId()==null){
				responseBody.setResult(new Result(Status.ERROR, null, "会议id错误"));
				return responseBody;
			}else if(meetRecord.getFileId()!=null){
				SopFile file  = sopFileService.queryById(meetRecord.getFileId());
				if(file!=null){
					meetRecord.setFname(file.getFileName());
					meetRecord.setFkey(file.getFileKey());
				}
			}
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setEntity(meetRecord);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "查询会议详情失败"));
			
			if(logger.isErrorEnabled()){
				logger.error("detailMeet 查询会议详情失败",e);
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
		
		if(proProgress.equals(DictEnum.projectProgress.投资意向书.getCode()) && (roleIdList.contains(UserConstant.TZJL)
				||roleIdList.contains(UserConstant.HHR)||roleIdList.contains(UserConstant.CEO)||roleIdList.contains(UserConstant.DSZ))){  
			fileworktypeList.add(DictEnum.fileWorktype.投资意向书.getCode());   
		}else if(proProgress.equals(DictEnum.projectProgress.尽职调查.getCode())){
			//人事|投资经理
			if(roleIdList.contains(UserConstant.TZJL) || roleIdList.contains(UserConstant.HRJL) || roleIdList.contains(UserConstant.HHR) || roleIdList.contains(UserConstant.HRZJ)
					||roleIdList.contains(UserConstant.HHR)||roleIdList.contains(UserConstant.CEO)||roleIdList.contains(UserConstant.DSZ)){
			         fileworktypeList.add(DictEnum.fileWorktype.人力资源尽职调查报告.getCode());  
			}
			//财务|投资经理
			if(roleIdList.contains(UserConstant.TZJL) || roleIdList.contains(UserConstant.CWJL) || roleIdList.contains(UserConstant.CWZJ)
					||roleIdList.contains(UserConstant.HHR)||roleIdList.contains(UserConstant.CEO)||roleIdList.contains(UserConstant.DSZ)){
			        fileworktypeList.add(DictEnum.fileWorktype.财务尽职调查报告.getCode());
			}
			//法务|投资经理
			if(roleIdList.contains(UserConstant.TZJL) || roleIdList.contains(UserConstant.FWJL) || roleIdList.contains(UserConstant.FWZJ)
					||roleIdList.contains(UserConstant.HHR)||roleIdList.contains(UserConstant.CEO)||roleIdList.contains(UserConstant.DSZ)){
			        fileworktypeList.add(DictEnum.fileWorktype.法务尽职调查报告.getCode());
			}
			//投资经理
			if(roleIdList.contains(UserConstant.TZJL)
					||roleIdList.contains(UserConstant.HHR)||roleIdList.contains(UserConstant.CEO)||roleIdList.contains(UserConstant.DSZ)){
			        fileworktypeList.add(DictEnum.fileWorktype.业务尽职调查报告.getCode());
			}
		}else if(proProgress.equals(DictEnum.projectProgress.投资协议.getCode())){  
			        fileworktypeList.add(DictEnum.fileWorktype.投资协议.getCode());      
			        //fileworktypeList.add(DictEnum.fileWorktype.股权转让协议.getCode());  //废弃   
			
		}else if(proProgress.equals(DictEnum.projectProgress.股权交割.getCode())){
			//财务|投资经理
			if(roleIdList.contains(UserConstant.TZJL) || roleIdList.contains(UserConstant.CWJL) || roleIdList.contains(UserConstant.CWZJ)
					||roleIdList.contains(UserConstant.HHR)||roleIdList.contains(UserConstant.CEO)||roleIdList.contains(UserConstant.DSZ)){
			       fileworktypeList.add(DictEnum.fileWorktype.资金拨付凭证.getCode());   
			}
			//法务|投资经理
			if(roleIdList.contains(UserConstant.TZJL) || roleIdList.contains(UserConstant.FWJL) || roleIdList.contains(UserConstant.FWZJ)
					||roleIdList.contains(UserConstant.HHR)||roleIdList.contains(UserConstant.CEO)||roleIdList.contains(UserConstant.DSZ)){
			       fileworktypeList.add(DictEnum.fileWorktype.工商转让凭证.getCode());  
			}
		}else{
			responseBody.setResult(new Result(Status.OK,null, "项目阶段类型不能识别"));
			return responseBody;
		}
		
		try {
			List<SopFile> fileList = new ArrayList<SopFile>();
			
			SopFileBo  sbo =  new SopFileBo();
			sbo.setProjectId(pid);
			sbo.setProjectProgress(proProgress);
			sbo.setFileworktypeList(fileworktypeList);
			
			fileList = sopFileService.selectByFileTypeList(sbo);
			for(SopFile f : fileList){
				Department depart = departmentService.queryById(f.getCareerLine());
				f.setCareerLineName(depart.getName());
			}
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setEntityList(fileList);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "查询文件列表失败"));
			
			if(logger.isErrorEnabled()){
				logger.error("proFileInfo 根据角色项目阶段查询文件列表失败",e);
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
			responseBody.setResult(new Result(Status.ERROR,null, "获取登录人项目失败"));
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
			responseBody.setResult(new Result(Status.ERROR,null, "获取登录人项目失败"));
			if(logger.isErrorEnabled()){
				logger.error("queryPerPro faild ",e);
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
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG, LogType.MESSAGE })
	@ResponseBody
	@RequestMapping(value = "/addInterview", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InterviewRecord> addInterview(@RequestBody InterviewRecordBo interviewRecord ,HttpServletRequest request ) {
		ResponseData<InterviewRecord> responseBody = new ResponseData<InterviewRecord>();
		Long viewId;
		try {
			User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			
			if(interviewRecord == null || interviewRecord.getProjectId() == null || interviewRecord.getViewDate() == null || interviewRecord.getViewTarget() == null ){
				responseBody.setResult(new Result(Status.ERROR,null, "请完善访谈信息"));
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
			interviewRecord.setCreatedId(user.getId());
			//验证是否附件已上传
			if(interviewRecord.getFkey()!=null){
				if(interviewRecord.getBucketName()==null || interviewRecord.getFileLength()==null||interviewRecord.getFname()==null){
					responseBody.setResult(new Result(Status.ERROR,null, "请完善附件信息"));
					return responseBody;
				}
				
				Map<String,String> nameMap = transFileNames(interviewRecord.getFname());
				SopFile sopFile = new SopFile();
				sopFile.setBucketName(interviewRecord.getBucketName());
				sopFile.setFileKey(interviewRecord.getFkey());
				sopFile.setFileLength(interviewRecord.getFileLength());
				sopFile.setFileName(nameMap.get("fileName"));
				sopFile.setFileSuffix(nameMap.get("fileSuffix"));
				
				sopFile.setProjectId(project.getId());
				sopFile.setProjectProgress(project.getProjectProgress());
				sopFile.setFileUid(user.getId());	 //上传人
				sopFile.setCareerLine(user.getDepartmentId());
				sopFile.setFileType(DictEnum.fileType.音频文件.getCode());   //存储类型
				sopFile.setFileSource(DictEnum.fileSource.内部.getCode());  //档案来源
				//sopFile.setFileWorktype(fileWorkType);    //业务分类
				sopFile.setFileStatus(DictEnum.fileStatus.已上传.getCode());  //档案状态
				
				viewId = interviewRecordService.insertInterview(interviewRecord,sopFile);
			}else{
				viewId = interviewRecordService.insert(interviewRecord);
			}
			if(viewId == null){
				responseBody.setResult(new Result(Status.ERROR,null, "访谈添加失败"));
				logger.error("addInterview  谈添加失败 ");
				return responseBody;
			}
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(viewId);
			ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId());
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "访谈添加失败"));
			
			if(logger.isErrorEnabled()){
				logger.error("addInterview 访谈添加失败 ",e);
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
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG, LogType.MESSAGE })
	@ResponseBody
	@RequestMapping(value = "/addmeet", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingRecord> addmeet(HttpServletRequest request,@RequestBody MeetingRecord meetingRecord ) {
		ResponseData<MeetingRecord> responseBody = new ResponseData<MeetingRecord>();
		
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
		if(meetingRecord.getProjectId() == null 
				|| meetingRecord.getMeetingDate() == null 
				|| meetingRecord.getMeetingType() == null 
				|| meetingRecord.getMeetingResult() == null ){
			responseBody.setResult(new Result(Status.ERROR,null, "请完善会议信息"));
			return responseBody;
		}
		
		//已有通过的会议，不能再添加会议纪要
		MeetingRecord mrQuery = new MeetingRecord();
		mrQuery.setProjectId(meetingRecord.getProjectId());
		mrQuery.setMeetingType(meetingRecord.getMeetingType());
		mrQuery.setMeetingResult(DictEnum.meetingResult.通过.getCode());
		Long mrCount = meetingRecordService.queryCount(mrQuery);
		if(mrCount != null && mrCount.longValue() > 0L)
		{
			responseBody.setResult(new Result(Status.ERROR, "","已有通过的会议，不能再添加会议纪要!"));
			return responseBody;
		}
		
		String prograss = "";
		UrlNumber uNum = null;
		if(meetingRecord.getMeetingType().equals(DictEnum.meetingType.内评会.getCode())){       //字典  会议类型   内评会
			prograss = DictEnum.projectProgress.内部评审.getCode();                                 	//字典  项目进度  内部评审
			uNum = UrlNumber.one;
		}else if(meetingRecord.getMeetingType().equals(DictEnum.meetingType.CEO评审.getCode())){ //字典  会议类型   CEO评审
			prograss = DictEnum.projectProgress.CEO评审.getCode(); 								//字典   项目进度  CEO评审
			uNum = UrlNumber.two;
		}else if(meetingRecord.getMeetingType().equals(DictEnum.meetingType.立项会.getCode())){	//字典  会议类型   立项会
			prograss = DictEnum.projectProgress.立项会.getCode(); 										//字典   项目进度    立项会
			uNum = UrlNumber.three;
		}else if(meetingRecord.getMeetingType().equals(DictEnum.meetingType.投决会.getCode())){	//字典  会议类型   投决会
			prograss = DictEnum.projectProgress.投资决策会.getCode(); 									//字典   项目进度     投资决策会
			uNum = UrlNumber.four;
		}else{
			responseBody.setResult(new Result(Status.ERROR,null, "会议类型无法识别"));
			return responseBody;
		}
		
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
			ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId(),uNum);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "会议添加失败"));
			
			if(logger.isErrorEnabled()){
				logger.error("addmeet 会议添加失败",e);
			}
		}
		
		return responseBody;
	}
	
	
}
