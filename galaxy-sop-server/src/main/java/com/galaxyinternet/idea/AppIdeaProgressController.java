package com.galaxyinternet.idea;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.common.ProgressLog;
import com.galaxyinternet.model.idea.AppIdea;
import com.galaxyinternet.model.idea.Idea;
import com.galaxyinternet.model.operationLog.OperationLogs;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.service.IdeaService;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.ProgressLogService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;

@Controller
@RequestMapping("/galaxy/appidea")
public class AppIdeaProgressController extends BaseControllerImpl<Idea, Idea> {

	final Logger logger = LoggerFactory.getLogger(AppIdeaProgressController.class);

	@Autowired
	private IdeaService ideaService;


	@Autowired
	private  SopFileService sopFileService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private MeetingRecordService meetingRecordService;
/*	@Autowired
	private ConfigService configService;*/

	@Autowired
	private ProgressLogService progressLogService;
	@Override
	protected BaseService<Idea> getBaseService() {
		return this.ideaService;
	}
	/**
	 * 供app端用创意流程接口
	 * @param id
	 * @param request
	 * @return  
	 * 
	 * CYCJ("创意已创建/待认领","ideaProgress:1"),
		CYDY("调研","ideaProgress:2"),
		CYLXH("创建立项会","ideaProgress:3"),
		CYLGZ("搁置","ideaProgress:4"),
		CYXM("创建项目","ideaProgress:5");
	 * 
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/ideaProgress/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<AppIdea> search(@PathVariable("id") Long id, HttpServletRequest request) {
		ResponseData<AppIdea> responseBody = new ResponseData<AppIdea>();
		if (id == null) {
			responseBody.setResult(new Result(Status.ERROR, null, "必要的参数丢失!"));
			return responseBody;
		}
		Idea idea = ideaService.queryById(id);
		if (idea == null) {
			responseBody.setResult(new Result(Status.ERROR, null, "未查找到指定创意信息!"));
			return responseBody;
		}
		String ideaProgress = idea.getIdeaProgress();
		if (ideaProgress == null) {
			responseBody.setResult(new Result(Status.ERROR, null, "该创意没有进行创意流程"));
			return responseBody;
		}	  
		// 截取projectProgress取到:后的值
		String a[] = ideaProgress.split(":");
		String num = a[1];
		try {
			int n = Integer.parseInt(num);		
			List<AppIdea> appIdealist =  new ArrayList<AppIdea>();
			
			SopFile sopFile = null;
			
/*			// 业务类型的类
			AppSopFile appSopFile = null;
			// 上传list给到类中
			List<AppSopFile> appS = null;*/
			ProgressLog progressLog = null;
			// 会议记录的类
			MeetingRecord meetingRecord = null;
			AppIdea appIdea = null;
			for (int i = n; i > 0; i--) {
				
				meetingRecord = new MeetingRecord();
				
				progressLog=new ProgressLog();
				sopFile = new SopFile();
				
				appIdea = new AppIdea();
				
				if(i==5){//创建项目的阶段
					appIdea.setIdeaProgresss(DictEnum.IdeaProgress.getName("ideaProgress:5"));
					appIdea.setIpCode(DictEnum.IdeaProgress.CYXM.getCode());
					if(idea.getProjectId()!=null){
						Project p = projectService.queryById(idea.getProjectId());						
						appIdea.setProjectName(p.getProjectName()); //项目名称
						appIdea.setProjectProgress(p.getProgress());//项目阶段
						appIdea.setProjectCode(p.getProjectProgress());//项目阶段的code
						appIdea.setProjectId(idea.getProjectId().toString());
						progressLog.setRecordType(DictEnum.RecordType.IDEAS.getType());
						progressLog.setRelatedId(id);
						List<ProgressLog> listSop = progressLogService.queryList(progressLog);
						if (listSop!=null && listSop.size()>0) {
							ProgressLog ot = listSop.get(0);
							appIdea.setProjectDtName(ot.getUname());
							appIdea.setdTime(ot.getCreatedTime().toString());
							appIdea.setDtcaoZuo(ot.getOperationType());
							appIdea.setDtWork(ot.getOperationContent());
						}
						appIdealist.add(appIdea);					
					}
				}
				if(i==3){//创建立项会的阶段   PROJECT((byte) 0, "项目"), IDEAS((byte) 1, "创意");
					appIdea.setIdeaProgresss(DictEnum.IdeaProgress.getName("ideaProgress:3"));
					appIdea.setIpCode(DictEnum.IdeaProgress.CYLXH.getCode());
					meetingRecord.setRecordType(DictEnum.RecordType.IDEAS.getType());
					meetingRecord.setMeetValid((byte)0);
					meetingRecord.setProjectId(id);
					List<MeetingRecord> listSop = meetingRecordService.queryList(meetingRecord);
					if (listSop!=null && listSop.size()>0) {
						for(MeetingRecord met:listSop){
							if(met.getMeetingResult().contains(DictEnum.meetingResult.否决.getCode())){
								appIdea.setMeetTime(met.getMeetingDateStr());	//会议时间					
								appIdea.setMeetCode(met.getMeetingResult());		//会议结果类型
								appIdea.setMeetResult(met.getMeetingResultStr()); //会议结果
								appIdea.setMeetNote(met.getMeetingNotes());		//会议纪要	
								appIdea.setId(met.getId()); //会议ID
								break;
							}else if(met.getMeetingResult().contains(DictEnum.meetingResult.通过.getCode())){
								appIdea.setMeetTime(met.getMeetingDateStr());	//会议时间					
								appIdea.setMeetCode(met.getMeetingResult());		//会议结果类型
								appIdea.setMeetResult(met.getMeetingResultStr()); //会议结果
								appIdea.setMeetNote(met.getMeetingNotes());		//会议纪要	
								appIdea.setId(met.getId()); //会议ID
								break;
							}
						}
						 if(appIdea.getId()==null){
							MeetingRecord me = listSop.get(0);
							appIdea.setMeetTime(me.getMeetingDateStr());	//会议时间					
							appIdea.setMeetCode(me.getMeetingResult());		//会议结果类型
							appIdea.setMeetResult(me.getMeetingResultStr()); //会议结果
							appIdea.setMeetNote(me.getMeetingNotes());		//会议纪要	
							appIdea.setId(me.getId()); //会议ID
							
						}
		
					}			
					appIdealist.add(appIdea);						
				}
				if(i==2){
					appIdea.setIdeaProgresss(DictEnum.IdeaProgress.getName("ideaProgress:2"));//创意流程阶段
					appIdea.setIpCode(DictEnum.IdeaProgress.CYDY.getCode());//创意的code
					sopFile.setRecordType(DictEnum.RecordType.IDEAS.getType());
					sopFile.setFileValid(1);
					sopFile.setProjectId(id);
					List<SopFile> listSop = sopFileService.queryList(sopFile);
					if (listSop!=null && listSop.size()>0) {
						SopFile me = listSop.get(listSop.size()-1);
						if(me.getFileName()!=null||me.getFileSuffix()!=null){
							String fn = me.getFileName();
							String fs = me.getFileSuffix();
							String fileName = fn + "." + fs;
							appIdea.setFileName(fileName);//文件名字
						}
						appIdea.setFileKey(me.getFileKey());//文件的key
						Long ti = null;
						if (me.getUpdatedTime() != null) {
							ti = me.getUpdatedTime();
						} else {
							ti = me.getCreatedTime();
						}
						if (ti != null) {									
							appIdea.setFileTime(ti.toString());//文件上传时间
						}
						
					}	
					appIdealist.add(appIdea);
				}
			}
			responseBody.setEntityList(appIdealist);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null, "appProgreess faild"));
		}
		return responseBody;

	}

}
