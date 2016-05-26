package com.galaxyinternet.project.controller;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.common.annotation.LogType;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.enums.DictEnum.RecordType;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.oss.OSSFactory;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.framework.core.utils.JSONUtils;
import com.galaxyinternet.model.idea.Idea;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.sopfile.AppSopFile;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.AppProjectMeetingService;
import com.galaxyinternet.service.IdeaService;
import com.galaxyinternet.service.MeetingRecordService;
/**
 * 
 * @author gxc
 * @ClassName  : AppIdeaMeetingController  
 * @Version  版本   
 * @ModifiedBy修改人  
 * @Copyright  Galaxyinternet  
 * @date  2016年5月20日 上午8:33:51
 */
@Controller
@RequestMapping("/galaxy/ideameeting")
public class AppIdeaMeetingController extends BaseControllerImpl<Idea, Idea>{

	final Logger logger = LoggerFactory.getLogger(AppIdeaMeetingController.class);
		

	@Autowired
	private AppProjectMeetingService appPmService;
	@Autowired
	private MeetingRecordService meetingRecordService;
		
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	@Autowired
	private IdeaService ideaService;
	
	private String tempfilePath;

	public String getTempfilePath() {
		return tempfilePath;
	}
	@Value("${sop.oss.tempfile.path}")
	public void setTempfilePath(String tempfilePath) {
		this.tempfilePath = tempfilePath;
	}
	
	@Override
	protected BaseService<Idea> getBaseService() {
		return this.ideaService;
	}

	
	
	/**
	 * 验证提取，
	 * @param  idea           数据库数据
	 * @param  ClaimantUser   认领人验证
	 * @param  CreateUser     创建人验证
	 * @param  ideaProgressEquals     当前阶段验证
	 * @param  ideaProgress           后面阶段 可以操作 之前阶段
	 */
	public Result errMessage(Idea idea,User ClaimantUser,User CreateUser,String ideaProgressEquals,String ideaProgress){
		if(idea == null){
			return new Result(Status.ERROR, null, "创意检索为空!");
		}
		if(ClaimantUser != null){  // 登陆用户id 和  认领人Id 
			if(idea.getClaimantUid()==null || ClaimantUser.getId().longValue()!=idea.getClaimantUid().longValue()){ 
				return new Result(Status.ERROR, null, "没有权限!");
			}
		}
		if(CreateUser != null){    // 登陆用户id 和   创建人Id 
			if(idea.getClaimantUid()==null || CreateUser.getId().longValue()!=idea.getCreatedUid().longValue()){ 
				return new Result(Status.ERROR, null, "没有权限!");
			}
		}
		if(ideaProgressEquals != null){  //仅当前阶段能用
			if(idea.getIdeaProgress()!=null){
				if(!ideaProgressEquals.equals(idea.getIdeaProgress())){
					return new Result(Status.ERROR, "501", "操作违规!");
				}
			}else{
				return new Result(Status.ERROR, null, "创意阶段出错");
			}
		}
		if(ideaProgress != null){   //之后阶段 能操作 之前阶段
			if(idea.getIdeaProgress()!=null){
				try {
					int operationPro = Integer.parseInt(ideaProgress.substring(ideaProgress.length()-1)) ;
					int ideaPro = Integer.parseInt(idea.getIdeaProgress().substring(idea.getIdeaProgress().length()-1)) ;
					if(ideaPro < operationPro){
						return new Result(Status.ERROR, "501", "操作违规!");
					}
				} catch (Exception e) {
					return new Result(Status.ERROR, null, "信息不和规范");
				}
			}else{
				return new Result(Status.ERROR, null, "创意阶段出错");
			}
		}
		return null;
	}
	 /**
		 * App端接口--添加会议(无录音文件)
		 * @param meetingRecord
		 * @param request    测试调用URL/galaxy/ideameeting/filemeetByNofile
		 * @param response
		 * @return
		 */
		@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG, LogType.MESSAGE })
		@ResponseBody
		@RequestMapping(value = "/filemeetByNofile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseData<MeetingRecord> addIosFileMeetByNoFile(@RequestBody MeetingRecordBo meetingRecord,HttpServletRequest request,HttpServletResponse response  ) {
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
			meetingRecord.setRecordType(RecordType.IDEAS.getType());
			//已有通过的会议，不能再添加会议纪要
			MeetingRecord mrQuery = new MeetingRecord();
			mrQuery.setProjectId(meetingRecord.getProjectId());
			mrQuery.setMeetingType(meetingRecord.getMeetingType());
			mrQuery.setMeetingResult(DictEnum.meetingResult.通过.getCode());
			mrQuery.setRecordType(RecordType.IDEAS.getType());
			Long mrCount = meetingRecordService.queryCount(mrQuery);
			
			if(mrCount != null && mrCount.longValue() > 0L)
			{
				responseBody.setResult(new Result(Status.ERROR, "","已有通过的会议，不能再添加会议纪要!"));
				return responseBody;
			}			
			
			try {
				//Idea  验证
				Idea idea = new Idea();
				Long id = null;
				idea = ideaService.queryById(meetingRecord.getProjectId());
				//Result err = errMessage(idea,user,null,"ideaProgress:3"); 
				Result err = errMessage(idea,user,null,DictEnum.IdeaProgress.CYLXH.getCode(),null);   //仅创建人、当前阶段
				if(err!=null && err.getStatus()!=null){
					responseBody.setResult(err);
					return responseBody;
				}
				//保存
				SopFile sopFile = null;
				if(meetingRecord.getFkey()!=null){
					if( meetingRecord.getFileLength()==null||meetingRecord.getFname()==null){
						responseBody.setResult(new Result(Status.ERROR,null, "请完善附件信息"));
						return responseBody;
					}
					if(meetingRecord.getBucketName()==null){
						meetingRecord.setBucketName(OSSFactory.getDefaultBucketName());
					}		
							
					Map<String,String> nameMap = transFileNames(meetingRecord.getFname());	
					sopFile = new SopFile();
					sopFile.setBucketName(meetingRecord.getBucketName());
					sopFile.setFileKey(meetingRecord.getFkey());
					sopFile.setFileLength(meetingRecord.getFileLength());
					sopFile.setFileName(nameMap.get("fileName"));
					sopFile.setFileSuffix(nameMap.get("fileSuffix"));
					
					sopFile.setRecordType(RecordType.IDEAS.getType());
					sopFile.setProjectId(idea.getId());
					sopFile.setProjectProgress(idea.getIdeaProgress());
					sopFile.setFileUid(user.getId());	 //上传人
					sopFile.setCareerLine(user.getDepartmentId());
					sopFile.setFileType(DictEnum.fileType.音频文件.getCode());   //存储类型
					sopFile.setFileSource(DictEnum.fileSource.内部.getCode());  //档案来源
					//sopFile.setFileWorktype(fileWorkType);    //业务分类
					sopFile.setFileStatus(DictEnum.fileStatus.已上传.getCode());  //档案状态			
					id = meetingRecordService.addCyMeetRecord(meetingRecord,sopFile);
				}else if(!ServletFileUpload.isMultipartContent(request)){
					SopFile file = new SopFile();
					file.setCareerLine(user.getDepartmentId());
					file.setFileUid(user.getId());
					id = meetingRecordService.addCyMeetRecord(meetingRecord,file);
				}
				responseBody.setId(id);
				responseBody.setResult(new Result(Status.OK, ""));			
			} catch (Exception e) {
				responseBody.setResult(new Result(Status.ERROR,null, "会议添加失败"));
				if(logger.isErrorEnabled()){
					logger.error("addfilemeet 会议添加失败 ",e);
				}
			}
			return responseBody;
		}
		
		
		
		/**
		 * App端接口--添加会议（有录音文件）
		 *  测试调用URL/galaxy/ideameeting/audioFile
		 * @param sopFile
		 * @param request
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value="/audioFile" , method=RequestMethod.POST , produces=MediaType.APPLICATION_JSON_VALUE)
		public ResponseData<AppSopFile> addAppAudioFile(@RequestBody AppSopFile sopFile, HttpServletRequest request){
			ResponseData<AppSopFile> responseBody = new ResponseData<AppSopFile>();			
			try {
				
				if(sopFile == null ){				
					responseBody.setResult(new Result(Status.ERROR, null, "请完善附件信息"));
					return responseBody;
				}
				//会议ID验证
				if(sopFile!=null && (sopFile.getMeetingId()==null||sopFile.getMeetingId()==0)){
					responseBody.setResult(new Result(Status.ERROR, null, "会议ID缺失"));
					return responseBody;
				}
				//会议验证
				MeetingRecord meeting = meetingRecordService.queryById(sopFile.getMeetingId());
				if(meeting == null || meeting.getId() == null){			
					responseBody.setResult(new Result(Status.ERROR, null, "会议记录为空"));
					return responseBody;
				}				
				
				User user  = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
				Idea idea = ideaService.queryById(sopFile.getProjectId());
				appPmService.addIdeaFileByMeeting(meeting , idea, user.getDepartmentId(), user.getId(), sopFile);
				
				responseBody.setResult(new Result(Status.OK,null, "录音添加成功"));
			} catch (Exception e) {
				responseBody.setResult(new Result(Status.ERROR,null, "录音追加失败"));
				logger.error("App端录音追加失败",e);
			}
			return responseBody;
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

}
