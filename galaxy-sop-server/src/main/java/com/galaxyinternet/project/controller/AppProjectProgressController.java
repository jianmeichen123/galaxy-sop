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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.AppProgress;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.AppSopFile;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.sopfile.SopVoucherFile;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.InterviewRecordService;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.SopVoucherFileService;
import com.galaxyinternet.service.UserService;

@Controller
@RequestMapping("/galaxy/approgress")
public class AppProjectProgressController extends BaseControllerImpl<Project, ProjectBo> {

	final Logger logger = LoggerFactory.getLogger(AppProjectProgressController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private MeetingRecordService meetingRecordService;

	@Autowired
	private InterviewRecordService interviewRecordService;

	@Autowired
	private SopFileService sopFileService;

	@Autowired
	private SopVoucherFileService sopVoucherFileService;
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;

	@Override
	protected BaseService<Project> getBaseService() {
		return this.projectService;
	}

	/**
	 * 供app端项目流程
	 * 
	 * @param pid
	 * @param request
	 * @param project
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/app/{pid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<AppProgress> search(@PathVariable("pid") String pid, HttpServletRequest request) {
		ResponseData<AppProgress> responseBody = new ResponseData<AppProgress>();
		if (pid == null) {
			responseBody.setResult(new Result(Status.ERROR, null, "必要的参数丢失!"));
			return responseBody;
		}
		Project p = projectService.queryById(Long.parseLong(pid));
		if (p == null) {
			responseBody.setResult(new Result(Status.ERROR, null, "未查找到指定项目信息!"));
			return responseBody;
		}
		String projectProgress = p.getProjectProgress();
		if (projectProgress == null) {
			responseBody.setResult(new Result(Status.ERROR, null, "该项目没有进行项目流程"));
			return responseBody;
		}
		// 截取projectProgress取到:后的值
		String a[] = projectProgress.split(":");
		String num = a[1];
		try {
			int n = Integer.parseInt(num);		
			List<AppProgress> appProgresslist =  new ArrayList<AppProgress>();
			// 获取上传的签署证明 5.投资意向书 6.投资协议 7.股权转让协议
			SopVoucherFile sopVoucherFile = null;
			// sopfile
			SopFile sopFile = null;
			// 访谈记录的类
			InterviewRecord interviewRecord = null;
			// 业务类型的类
			AppSopFile appSopFile = null;
			// 上传list给到类中
			List<AppSopFile> appS = null;
			// 会议记录的类
			MeetingRecord meetingRecord = null;
			AppProgress appProgress = null;
			for (int i = n; i > 0; i--) {				
				appS = new ArrayList<AppSopFile>();
				meetingRecord = new MeetingRecord();
				appSopFile = new AppSopFile();
				interviewRecord = new InterviewRecord();
				sopFile = new SopFile();
				sopVoucherFile = new SopVoucherFile();
				appProgress = new AppProgress();
				
				// 股权交割
				if (i == 9) {
					// 项目阶段
					appProgress.setProjectProgress(DictEnum.projectProgress.getNameByCode("projectProgress:9"));
					// 业务类型
					sopFile.setProjectProgress("projectProgress:9");
					sopFile.setProjectId(Long.parseLong(pid));
					List<SopFile> listSop = sopFileService.queryList(sopFile);
					AppSopFile asfile9 = null;
					if (!listSop.isEmpty()) {
						for (SopFile sop : listSop) {
							asfile9 = new AppSopFile();
							if (sop.getFileWorktype().equals(DictEnum.fileWorktype.资金拨付凭证.getCode())) {
								asfile9.setFileYwCode(sop.getFileWorktype());
								asfile9.setFileWorktype(sop.getfWorktype());
									if(sop.getFileName()!=null||sop.getFileSuffix()!=null){
										String fn = sop.getFileName();
										String fs = sop.getFileSuffix();
										String fileName = fn + "." + fs;
										asfile9.setFileName(fileName);
									}
									asfile9.setFileDsCode(sop.getFileStatus());									
									asfile9.setFileDs(DictEnum.fileStatus.getNameByCode(sop.getFileStatus()));
									Long ti = null;
									if (sop.getUpdatedTime() != null) {
										ti = sop.getUpdatedTime();
									} else {
										ti = sop.getCreatedTime();
									}
									if (ti != null) {
										//asfile9.setFileTime(DateUtil.longString(ti));
										asfile9.setFileTime(ti.toString());
									}
									asfile9.setFileKey(sop.getFileKey());
									Long uid = sop.getFileUid();
									if(uid!=null){
										User user = userService.queryById(uid);
										asfile9.setName(user.getRealName());
									}								
									//System.out.println("资金拨付凭证");
									appS.add(asfile9);

							} 
							else if (sop.getFileWorktype().equals(DictEnum.fileWorktype.工商转让凭证.getCode())) {
								asfile9.setFileYwCode(sop.getFileWorktype());
								asfile9.setFileWorktype(sop.getfWorktype());
									if(sop.getFileName()!=null||sop.getFileSuffix()!=null){
										String fn = sop.getFileName();
										String fs = sop.getFileSuffix();
										String fileName = fn + "." + fs;
										asfile9.setFileName(fileName);
									}
									asfile9.setFileDsCode(sop.getFileStatus());									
									asfile9.setFileDs(DictEnum.fileStatus.getNameByCode(sop.getFileStatus()));
									
									Long ti = null;
									if (sop.getUpdatedTime() != null) {
										ti = sop.getUpdatedTime();
									} else {
										ti = sop.getCreatedTime();
									}
									if (ti != null) {
										//asfile9.setFileTime(DateUtil.longString(ti));
										asfile9.setFileTime(ti.toString());
									}
									asfile9.setFileKey(sop.getFileKey());
									Long uid = sop.getFileUid();
									if(uid!=null){
										User user = userService.queryById(uid);
										asfile9.setName(user.getRealName());
									}
									appS.add(asfile9);
									//System.out.println("工商转让凭证");
								
							} 
						}

					}
					appProgress.setAppSopFile(appS);
					appProgresslist.add(appProgress);
					
				}
				// 投资协议
				else if (i == 8) {

					// 项目阶段
					appProgress.setProjectProgress(DictEnum.projectProgress.getNameByCode("projectProgress:8"));
					// 业务类型
					sopVoucherFile.setProjectProgress("projectProgress:8");
					sopVoucherFile.setProjectId(Long.parseLong(pid));
					List<SopVoucherFile> listSop = sopVoucherFileService.queryList(sopVoucherFile);
					AppSopFile asfile8 = null;
					if (!listSop.isEmpty()) {
						for (SopVoucherFile sop : listSop) {
							asfile8 = new AppSopFile();
							if (sop.getFileWorktype().equals(DictEnum.fileWorktype.投资协议.getCode())) {	
								asfile8.setFileYwCode(sop.getFileWorktype());
								asfile8.setFileWorktype(DictEnum.fileWorktype.getNameByCode(sop.getFileWorktype()));
									
									if(sop.getFileName()!=null||sop.getFileSuffix()!=null){
										String fn = sop.getFileName();
										String fs = sop.getFileSuffix();
										String fileName = fn + "." + fs;
										asfile8.setFileName(fileName);
									}
									asfile8.setFileDsCode(sop.getFileStatus());									
									asfile8.setFileDs(DictEnum.fileStatus.getNameByCode(sop.getFileStatus()));
									Long ti = null;
									if (sop.getUpdatedTime() != null) {
										ti = sop.getUpdatedTime();
									} else {
										ti = sop.getCreatedTime();
									}
									if (ti != null) {
										//asfile8.setFileTime(DateUtil.longString(ti));
										asfile8.setFileTime(ti.toString());
									}
									asfile8.setFileKey(sop.getFileKey());
									Long uid = sop.getFileUid();
									if(uid!=null){
										User user = userService.queryById(uid);
										asfile8.setName(user.getRealName());
									}
									appS.add(asfile8);
								//	System.out.println("投资协议");
								//	System.out.println(appSopFile.getFileWorktype());

							} 
							else if (sop.getFileWorktype().equals(DictEnum.fileWorktype.股权转让协议.getCode())) {
								asfile8.setFileYwCode(sop.getFileWorktype());
								asfile8.setFileWorktype(DictEnum.fileWorktype.getNameByCode(sop.getFileWorktype()));
									if(sop.getFileName()!=null||sop.getFileSuffix()!=null){
										String fn = sop.getFileName();
										String fs = sop.getFileSuffix();
										String fileName = fn + "." + fs;
										asfile8.setFileName(fileName);
									}
									asfile8.setFileDsCode(sop.getFileStatus());									
									asfile8.setFileDs(DictEnum.fileStatus.getNameByCode(sop.getFileStatus()));
									Long ti = null;
									if (sop.getUpdatedTime() != null) {
										ti = sop.getUpdatedTime();
									} else {
										ti = sop.getCreatedTime();
									}
									if (ti != null) {
										//asfile8.setFileTime(DateUtil.longString(ti));
										asfile8.setFileTime(ti.toString());
									}
									asfile8.setFileKey(sop.getFileKey());
									Long uid = sop.getFileUid();
									if(uid!=null){
										User user = userService.queryById(uid);
										asfile8.setName(user.getRealName());
									}
									appS.add(asfile8);
									//System.out.println("股权转让协议");
									//.out.println(appSopFile.getFileWorktype());
							
							} 
						}
					}
					appProgress.setAppSopFile(appS);
					appProgresslist.add(appProgress);

				}

				// 投资决策会
				else if (i == 7) {
					appProgress.setProjectProgress(DictEnum.projectProgress.getNameByCode("projectProgress:7"));
					meetingRecord.setMeetingType("meetingType:4");
					meetingRecord.setProjectId(Long.parseLong(pid));
					// 默认的是倒序排新添加的在最上面
					List<MeetingRecord> listSop = meetingRecordService.queryList(meetingRecord);
					if (!listSop.isEmpty()) {
						MeetingRecord me = listSop.get(0);
						appProgress.setMeetTime(me.getMeetingDateStr());
						appProgress.setMeetNotes(me.getMeetingNotes());
						appProgress.setMeetResult(me.getMeetingResult());
						appProgress.setMeetingResultStr(me.getMeetingResultStr());
						appProgress.setMeetCode(me.getMeetingType());
					}
					/* System.out.println("22222222222222222"); */
					appProgresslist.add(appProgress);
					//System.out.println("投资决策会");
					
				}
				// 尽职调查
				else 	if (i == 6) {
					// 项目阶段
					appProgress.setProjectProgress(DictEnum.projectProgress.getNameByCode("projectProgress:6"));
					// 业务类型
					sopFile.setProjectProgress("projectProgress:6");
					sopFile.setProjectId(Long.parseLong(pid));
					List<SopFile> listSop = sopFileService.queryList(sopFile);
					AppSopFile _tsopFile = null;
					if (!listSop.isEmpty()) {
						for (SopFile sop : listSop) {
							/* System.out.println(sop.getFileWorktype()); */
							_tsopFile = new AppSopFile();
							if (sop.getFileWorktype().equals(DictEnum.fileWorktype.业务尽职调查报告.getCode())) {
								_tsopFile.setFileYwCode(sop.getFileWorktype());
								_tsopFile.setFileWorktype(sop.getfWorktype());
									if(sop.getFileName()!=null||sop.getFileSuffix()!=null){
										String fn = sop.getFileName();
										String fs = sop.getFileSuffix();
										String fileName = fn + "." + fs;
										_tsopFile.setFileName(fileName);
									}
									_tsopFile.setFileDsCode(sop.getFileStatus());									
									_tsopFile.setFileDs(DictEnum.fileStatus.getNameByCode(sop.getFileStatus()));
									Long ti = null;
									if (sop.getUpdatedTime() != null) {
										ti = sop.getUpdatedTime();
									} else {
										ti = sop.getCreatedTime();
									}
									if (ti != null) {
										//_tsopFile.setFileTime(DateUtil.longString(ti));
										_tsopFile.setFileTime(ti.toString());
									}
									_tsopFile.setFileKey(sop.getFileKey());
									Long uid = sop.getFileUid();
									if(uid!=null){
										User user = userService.queryById(uid);
										_tsopFile.setName(user.getRealName());
									}
									appS.add(_tsopFile);
									//System.out.println("业务尽职调查报告");
									//System.out.println(appSopFile.getFileWorktype());																	
							} 
							/* System.out.println(sop.getFileWorktype()); */
							else if (sop.getFileWorktype().equals(DictEnum.fileWorktype.人力资源尽职调查报告.getCode())) {
								_tsopFile.setFileYwCode(sop.getFileWorktype());
								_tsopFile.setFileWorktype(sop.getfWorktype());
									if(sop.getFileName()!=null||sop.getFileSuffix()!=null){
										String fn = sop.getFileName();
										String fs = sop.getFileSuffix();
										String fileName = fn + "." + fs;
										_tsopFile.setFileName(fileName);
									}
									_tsopFile.setFileDsCode(sop.getFileStatus());									
									_tsopFile.setFileDs(DictEnum.fileStatus.getNameByCode(sop.getFileStatus()));
									Long ti = null;
									if (sop.getUpdatedTime() != null) {
										ti = sop.getUpdatedTime();
									} else {
										ti = sop.getCreatedTime();
									}
									if (ti != null) {
										//_tsopFile.setFileTime(DateUtil.longString(ti));
										_tsopFile.setFileTime(ti.toString());
									}
									appSopFile.setFileKey(sop.getFileKey());
									Long uid = sop.getFileUid();
									if(uid!=null){
										User user = userService.queryById(uid);
										_tsopFile.setName(user.getRealName());
									}
									appS.add(_tsopFile);
								//	System.out.println("人力资源尽职调查报告");
									//System.out.println(appSopFile.getFileWorktype());								
							}
							/* System.out.println(sop.getFileWorktype()); */
							else if (sop.getFileWorktype().equals(DictEnum.fileWorktype.法务尽职调查报告.getCode())) {
								_tsopFile.setFileYwCode(sop.getFileWorktype());
								_tsopFile.setFileWorktype(sop.getfWorktype());
									if(sop.getFileName()!=null||sop.getFileSuffix()!=null){
										String fn = sop.getFileName();
										String fs = sop.getFileSuffix();
										String fileName = fn + "." + fs;
										_tsopFile.setFileName(fileName);
									}
									_tsopFile.setFileDsCode(sop.getFileStatus());									
									_tsopFile.setFileDs(DictEnum.fileStatus.getNameByCode(sop.getFileStatus()));
									Long ti = null;
									if (sop.getUpdatedTime() != null) {
										ti = sop.getUpdatedTime();
									} else {
										ti = sop.getCreatedTime();
									}
									if (ti != null) {
										//_tsopFile.setFileTime(DateUtil.longString(ti));
										_tsopFile.setFileTime(ti.toString());
									}
									_tsopFile.setFileKey(sop.getFileKey());
									Long uid = sop.getFileUid();
									if(uid!=null){
										User user = userService.queryById(uid);
										_tsopFile.setName(user.getRealName());
									}
									appS.add(_tsopFile);
									//System.out.println("法务尽职调查报告");
									//System.out.println(appSopFile.getFileWorktype());
								
							} 

							/* System.out.println(sop.getFileWorktype()); */
							else if (sop.getFileWorktype().equals(DictEnum.fileWorktype.财务尽职调查报告.getCode())) {	
								_tsopFile.setFileYwCode(sop.getFileWorktype());
								_tsopFile.setFileWorktype(sop.getfWorktype());
									if(sop.getFileName()!=null||sop.getFileSuffix()!=null){
										String fn = sop.getFileName();
										String fs = sop.getFileSuffix();
										String fileName = fn + "." + fs;
										_tsopFile.setFileName(fileName);
									}
									appSopFile.setFileDsCode(sop.getFileStatus());									
									_tsopFile.setFileDs(DictEnum.fileStatus.getNameByCode(sop.getFileStatus()));
									Long ti = null;
									if (sop.getUpdatedTime() != null) {
										ti = sop.getUpdatedTime();
									} else {
										ti = sop.getCreatedTime();
									}
									if (ti != null) {
										//_tsopFile.setFileTime(DateUtil.longString(ti));
										_tsopFile.setFileTime(ti.toString());
									}
									_tsopFile.setFileKey(sop.getFileKey());
									Long uid = sop.getFileUid();
									if(uid!=null){
										User user = userService.queryById(uid);
										_tsopFile.setName(user.getRealName());
									}
									appS.add(_tsopFile);
								//	System.out.println("财务尽职调查报告");
								//.out.println(appSopFile.getFileWorktype());
								
							} 

						}
					}
					appProgress.setAppSopFile(appS);
					appProgresslist.add(appProgress);
					//System.out.println(appProgresslist.get(0));

				}
				// 投资意向书
				else if (i == 5) {
					// 项目阶段
					appProgress.setProjectProgress(DictEnum.projectProgress.getNameByCode("projectProgress:5"));
					// 业务类型
					sopVoucherFile.setProjectProgress("projectProgress:5");
					sopVoucherFile.setProjectId(Long.parseLong(pid));
					List<SopVoucherFile> listSop = sopVoucherFileService.queryList(sopVoucherFile);
					AppSopFile asfile5 = null; 
					if (!listSop.isEmpty()) {
						for (SopVoucherFile sop : listSop) {
							asfile5 = new AppSopFile();
							//System.out.println(sop.getFileWorktype());
							if (sop.getFileWorktype().equals(DictEnum.fileWorktype.投资意向书.getCode())) {
								asfile5.setFileYwCode(sop.getFileWorktype());
								asfile5.setFileWorktype(DictEnum.fileWorktype.getNameByCode(sop.getFileWorktype()));
									if(sop.getFileName()!=null||sop.getFileSuffix()!=null){
										String fn = sop.getFileName();
										String fs = sop.getFileSuffix();
										String fileName = fn + "." + fs;
										asfile5.setFileName(fileName);
									}
									asfile5.setFileDsCode(sop.getFileStatus());									
									asfile5.setFileDs(DictEnum.fileStatus.getNameByCode(sop.getFileStatus()));
									Long ti = null;
									if (sop.getUpdatedTime() != null) {
										ti = sop.getUpdatedTime();
									} else {
										ti = sop.getCreatedTime();
									}
									if (ti != null) {
										//asfile5.setFileTime(DateUtil.longString(ti));
										asfile5.setFileTime(ti.toString());
									}
									asfile5.setFileKey(sop.getFileKey());
									Long uid = sop.getFileUid();
									if(uid!=null){
										User user = userService.queryById(uid);
										asfile5.setName(user.getRealName());
									}
									appS.add(asfile5);
									//System.out.println("投资意向书");
									//System.out.println(appSopFile.getFileWorktype());
								
							}

						}
					}
					appProgress.setAppSopFile(appS);
					appProgresslist.add(appProgress);
				}
				// 立项会
				else if (i == 4) {
					appProgress.setProjectProgress(DictEnum.projectProgress.getNameByCode("projectProgress:4"));
					meetingRecord.setMeetingType("meetingType:3");
					meetingRecord.setProjectId(Long.parseLong(pid));
					// 默认的是倒序排新添加的在最上面
					List<MeetingRecord> listSop = meetingRecordService.queryList(meetingRecord);
					if (listSop!=null && listSop.size()>0) {
						MeetingRecord me = listSop.get(0);
						/*appProgress.setMeetTime(DateUtil.convertDateToStringForChina(me.getMeetingDate()));*/
						appProgress.setMeetTime(me.getMeetingDateStr());
						appProgress.setMeetNotes(me.getMeetingNotes());
						appProgress.setMeetResult(me.getMeetingResult());
						appProgress.setMeetingResultStr(me.getMeetingResultStr());
						appProgress.setMeetCode(me.getMeetingType());
					}
					/* System.out.println("22222222222222222"); */
					appProgresslist.add(appProgress);
					//System.out.println("立项会");
				}
				// CEO评审
				else if (i == 3) {
					appProgress.setProjectProgress(DictEnum.projectProgress.getNameByCode("projectProgress:3"));
					meetingRecord.setMeetingType("meetingType:2");
					meetingRecord.setProjectId(Long.parseLong(pid));
					// 默认的是倒序排新添加的在最上面
					List<MeetingRecord> listSop = meetingRecordService.queryList(meetingRecord);
					if (!listSop.isEmpty()) {
						MeetingRecord me = listSop.get(0);
						/*appProgress.setMeetTime(DateUtil.convertDateToStringForChina(me.getMeetingDate()));*/
						appProgress.setMeetTime(me.getMeetingDateStr());
						appProgress.setMeetNotes(me.getMeetingNotes());
						appProgress.setMeetResult(me.getMeetingResult());
						appProgress.setMeetingResultStr(me.getMeetingResultStr());
						appProgress.setMeetCode(me.getMeetingType());
					}
					/* System.out.println("22222222222222222"); */
					appProgresslist.add(appProgress);
					//System.out.println("CEO评审");
				}
				// 内部评审
				else if (i == 2) {
					appProgress.setProjectProgress(DictEnum.projectProgress.getNameByCode("projectProgress:2"));
					meetingRecord.setMeetingType("meetingType:1");
					meetingRecord.setProjectId(Long.parseLong(pid));
					// 默认的是倒序排新添加的在最上面
					List<MeetingRecord> listSop = meetingRecordService.queryList(meetingRecord);
					if (!listSop.isEmpty()) {
						MeetingRecord me = listSop.get(0);
						/*appProgress.setMeetTime(DateUtil.convertDateToStringForChina(me.getMeetingDate()));*/
						appProgress.setMeetTime(me.getMeetingDateStr());
						appProgress.setMeetNotes(me.getMeetingNotes());
						appProgress.setMeetResult(me.getMeetingResult());
						appProgress.setMeetingResultStr(me.getMeetingResultStr());
						appProgress.setMeetCode(me.getMeetingType());
					}
					/* System.out.println("22222222222222222"); */
					appProgresslist.add(appProgress);
					//System.out.println("内部评审");
				}
				// 访谈记录
				else if (i == 1) {
					appProgress.setProjectProgress(DictEnum.projectProgress.getNameByCode("projectProgress:1"));
					interviewRecord.setProjectId(Long.parseLong(pid));
					List<InterviewRecord> listSop = interviewRecordService.queryList(interviewRecord);
					if (!listSop.isEmpty()) {
						InterviewRecord me = listSop.get(0);
						/*appProgress.setInterviewTime(DateUtil.convertDateToStringForChina(me.getViewDate()));*/
						appProgress.setInterviewTime(me.getViewDateStr());
						appProgress.setInterviewNotes(me.getViewNotes());
						appProgress.setInterviewTarget(me.getViewTarget());
						
						
					}
					appProgresslist.add(appProgress);
					//System.out.println("访谈记录");
				}
/*				System.out.println("11111111111111111111111");
				System.out.println(appProgresslist.get(0));*/
			}
			responseBody.setEntityList(appProgresslist);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null, "appProgreess faild"));
		}
		return responseBody;

	}

}
