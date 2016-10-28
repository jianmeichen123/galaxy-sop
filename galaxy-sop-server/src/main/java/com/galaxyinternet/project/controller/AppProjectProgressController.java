package com.galaxyinternet.project.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.galaxyinternet.exception.PlatformException;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.AppCounts;
import com.galaxyinternet.model.project.AppFileDTO;
import com.galaxyinternet.model.project.AppProgress;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.AppSopFile;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.sopfile.SopVoucherFile;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.InterviewRecordService;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.MeetingSchedulingService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.SopVoucherFileService;
import com.galaxyinternet.service.UserRoleService;
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
	private UserRoleService userRoleService;
	@Autowired
	private InterviewRecordService interviewRecordService;

	@Autowired
	private SopFileService sopFileService;

	@Autowired
	private SopVoucherFileService sopVoucherFileService;
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	@Autowired
	private MeetingSchedulingService meetingSchedulingService;

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
	   String projectType = p.getProjectType();
	   if(null==projectType || projectType.trim().equals("")){
			responseBody.setResult(new Result(Status.ERROR, null, "该项目缺少项目类型数据项值"));
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
			// 会议记录的类
			MeetingRecord meetingRecord = null;
			AppProgress appProgress = null;
			for (int i = n; i > 0; i--) {				
				meetingRecord = new MeetingRecord();
				appSopFile = new AppSopFile();
				interviewRecord = new InterviewRecord();
				sopFile = new SopFile();
				sopVoucherFile = new SopVoucherFile();
				appProgress = new AppProgress();
				
				// 股权交割
				if (i == 9) {
					// 项目阶段
					appProgress.setProjectProgressName(DictEnum.projectProgress.getNameByCode("projectProgress:9"));//→项目流程阶段名称
					appProgress.setProjectProgress("projectProgress:9");//→项目流程阶段编码
					// 据流程阶段编码、项目ID查询文件信息SopFile
					sopFile.setProjectProgress("projectProgress:9");
					sopFile.setProjectId(Long.parseLong(pid));
					List<String> fileworkTypeList = new ArrayList<String>();
					fileworkTypeList.add(DictEnum.fileWorktype.工商转让凭证.getCode());
					fileworkTypeList.add(DictEnum.fileWorktype.资金拨付凭证.getCode());
					sopFile.setFileworktypeList(fileworkTypeList);	
					sopFile.setFileValid(1); //生效文件
					List<SopFile> listSop = sopFileService.queryList(sopFile);
					
					List<AppSopFile> zjbfFileList = new ArrayList<AppSopFile>();
					List<AppSopFile> gsbgdjFileList = new ArrayList<AppSopFile>();
					AppSopFile asfile9 = null;
					if (!listSop.isEmpty()) {
						for (SopFile sop : listSop) {
							asfile9 = new AppSopFile();
							if (sop.getFileWorktype().equals(DictEnum.fileWorktype.资金拨付凭证.getCode())) {
								asfile9.setFileYwCode(sop.getFileWorktype()); //→文件（档案）业务分类编码
								asfile9.setFileWorktype(sop.getfWorktype()); //→文件（档案）业务分类名称
									if(sop.getFileName()!=null || sop.getFileSuffix()!=null){
										String fn = sop.getFileName();
										String fs = sop.getFileSuffix();
										String fileName = fn + "." + fs;
										asfile9.setFileName(fileName);//→文件名称（含后缀）
									}
									asfile9.setFileDsCode(sop.getFileStatus());	//→文件(档案)状态编码；当fileStatus:1时，该档案是缺失状态								
									asfile9.setFileDs(DictEnum.fileStatus.getNameByCode(sop.getFileStatus()));//→文件(档案)状态名称		
									Long ti = null;
									if (sop.getUpdatedTime() != null) {
										ti = sop.getUpdatedTime();
									} else {
										ti = sop.getCreatedTime();
									}
									if (ti != null) {
										//asfile9.setFileTime(DateUtil.longString(ti));
										asfile9.setFileTime(ti.toString()); //→文件(档案)最后更新时间（数据格式：时间戳）
									}
									asfile9.setFileKey(sop.getFileKey()); //→文件(档案)上传阿里云返回的KEY
									Long uid = sop.getFileUid(); //→文件(档案)上传人的ID
									if(uid!=null){
										User user = userService.queryById(uid);
										asfile9.setName(user.getRealName());  //→文件(档案)上传人的真实姓名
									}
									asfile9.setFileValid(sop.getFileValid()); //→文件(档案)待办任务提交，档案生效（是否显示催办）1是生效，0是
									asfile9.setId(sop.getId()); //→文件(档案)表的ID主键
									zjbfFileList.add(asfile9);
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
										asfile9.setFileTime(ti.toString());
									}
									asfile9.setFileKey(sop.getFileKey());
									Long uid = sop.getFileUid();
									if(uid!=null){
										User user = userService.queryById(uid);
										asfile9.setName(user.getRealName());
									}
									asfile9.setFileValid(sop.getFileValid()); //→文件(档案)待办任务提交，档案生效（是否显示催办）1是生效，0是
									asfile9.setId(sop.getId()); //→文件(档案)表的ID主键
									gsbgdjFileList.add(asfile9);								
							} 
						}

					}				
					List<AppFileDTO> list = new ArrayList<AppFileDTO>();
					AppFileDTO appfileDto = new AppFileDTO();
					appfileDto.setFileTypeCode(DictEnum.fileWorktype.资金拨付凭证.getCode());
					appfileDto.setFileTypeName(DictEnum.fileWorktype.资金拨付凭证.getName());
					appfileDto.setAppSopFile(zjbfFileList);		
					list.add(appfileDto);
					
					AppFileDTO gqzr_appfileDto = new AppFileDTO();
					gqzr_appfileDto.setFileTypeCode(DictEnum.fileWorktype.工商转让凭证.getCode());
					gqzr_appfileDto.setFileTypeName(DictEnum.fileWorktype.工商转让凭证.getName());
					gqzr_appfileDto.setAppSopFile(gsbgdjFileList);		
					list.add(gqzr_appfileDto);
					
					appProgress.setAppFileDtoList(list);					
					appProgresslist.add(appProgress);					
				}
				// 投资协议
				else if (i == 8) {
					List<AppSopFile> tzxyAppSopFileList = new ArrayList<AppSopFile>();//投资协议的档案list
					List<AppSopFile> gqzrxyAppSopFileList = new ArrayList<AppSopFile>();//股权转让协议的档案list
					// 项目阶段
					appProgress.setProjectProgressName(DictEnum.projectProgress.getNameByCode("projectProgress:8"));
					appProgress.setProjectProgress("projectProgress:8");//→项目流程阶段编码
					
					// 据流程阶段编码、项目ID查询档案文件信息SopFile
					SopFile query_sopfile  = new SopFile();
					query_sopfile.setProjectProgress("projectProgress:8");
					query_sopfile.setProjectId(Long.parseLong(pid));
					
					List<String> fileworkTypeList = new ArrayList<String>();
					fileworkTypeList.add(DictEnum.fileWorktype.投资协议.getCode());
					fileworkTypeList.add(DictEnum.fileWorktype.股权转让协议.getCode());
					query_sopfile.setFileworktypeList(fileworkTypeList);
					query_sopfile.setFileValid(1); //生效文件
					
					AppSopFile $asfile = null;
					List<SopFile> sfList = sopFileService.queryList(query_sopfile);
					if(sfList!=null && sfList.size()>0){
					    for(SopFile sfile : sfList){
					    	$asfile = new AppSopFile();
					    	
					    	if(sfile.getFileWorktype().equals(DictEnum.fileWorktype.投资协议.getCode())){
					    		$asfile.setFileYwCode(sfile.getFileWorktype()); //fileworktype档案业务分类Code	--  fileWorktype:6				    		
					    		$asfile.setFileWorktype(sfile.getfWorktype()); //fileworktype档案业务分类Code对应的名称	投资协议
								
								if(sfile.getFileName()!=null||sfile.getFileSuffix()!=null){
									String fn = sfile.getFileName();
									String fs = sfile.getFileSuffix();
									String fileName = fn + "." + fs;
									$asfile.setFileName(fileName); //上传文件的名称
								}
								$asfile.setFileDsCode(sfile.getFileStatus());  //上传的文件状态的code	; fileStatus:1缺失 ; fileStatus:2:已上传	; fileStatus:3	已签署				
								$asfile.setFileDs(DictEnum.fileStatus.getNameByCode(sfile.getFileStatus())); //上传的文件状态的名称
								Long ti = null;
								if (sfile.getUpdatedTime() != null) {
									ti = sfile.getUpdatedTime();
								} else {
									ti = sfile.getCreatedTime();
								}
								if (ti != null) {								
									$asfile.setFileTime(ti.toString());  //文件创建或最新更的时间
								}
								$asfile.setFileKey(sfile.getFileKey()); //档案阿里云存储Key
								Long uid = sfile.getFileUid();
								if(uid!=null){
									User user = userService.queryById(uid);
									$asfile.setName(user.getRealName()); //上传人/起草者 的姓名
								}
								$asfile.setFileValid(sfile.getFileValid());
								$asfile.setId(sfile.getId()); //文件ID
								$asfile.setSignFlag(0); //档案文件
								tzxyAppSopFileList.add($asfile);					
					    	}
					    	
							else if (sfile.getFileWorktype().equals(DictEnum.fileWorktype.股权转让协议.getCode())) {
								$asfile.setFileYwCode(sfile.getFileWorktype()); //fileworktype档案业务分类Code	fileWorktype:7
								$asfile.setFileWorktype(sfile.getfWorktype()); //fileworktype档案业务分类Code对应的名称	  股权转让协议
									if(sfile.getFileName()!=null||sfile.getFileSuffix()!=null){
										String fn = sfile.getFileName();
										String fs = sfile.getFileSuffix();
										String fileName = fn + "." + fs;
										$asfile.setFileName(fileName);  //上传文件的名称
									}
									$asfile.setFileDsCode(sfile.getFileStatus());		 //上传的文件状态的code	; fileStatus:1缺失 ; fileStatus:2:已上传	; fileStatus:3	已签署									
									$asfile.setFileDs(DictEnum.fileStatus.getNameByCode(sfile.getFileStatus()));  //上传的文件状态的名称
									Long ti = null;
									if (sfile.getUpdatedTime() != null) {
										ti = sfile.getUpdatedTime();
									} else {
										ti = sfile.getCreatedTime();
									}
									if (ti != null) {									
										$asfile.setFileTime(ti.toString());  //文件创建或最新更的时间
									}
									$asfile.setFileKey(sfile.getFileKey());
									Long uid = sfile.getFileUid();
									if(uid!=null){
										User user = userService.queryById(uid);
										$asfile.setName(user.getRealName()); //档案阿里云存储Key
									}
									$asfile.setFileValid(sfile.getFileValid());
									$asfile.setId(sfile.getId());  //文件ID
									$asfile.setSignFlag(0); //档案文件
									gqzrxyAppSopFileList.add($asfile);
							} 					    	
					    }	
					}					
					//据流程阶段编码、项目ID查询签署文件信息SopVouchFile
					sopVoucherFile.setProjectProgress("projectProgress:8");
					sopVoucherFile.setProjectId(Long.parseLong(pid));
					List<SopVoucherFile> listSop = sopVoucherFileService.queryList(sopVoucherFile); //查询出2种签署文件				
					AppSopFile asfile8 = null;
					if (listSop!=null && listSop.size()>0) {
						for (SopVoucherFile sop : listSop) {
							asfile8 = new AppSopFile();
							if (sop.getFileWorktype().equals(DictEnum.fileWorktype.投资协议.getCode())) {	
								asfile8.setFileYwCode(sop.getFileWorktype()); // //fileworktype档案业务分类Code	fileWorktype:6
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
										asfile8.setFileTime(ti.toString());
									}
									asfile8.setFileKey(sop.getFileKey());
									Long uid = sop.getFileUid();
									if(uid!=null){
										User user = userService.queryById(uid);
										asfile8.setName(user.getRealName());
									}									
									asfile8.setId(sop.getId()); //→签署档案表的ID主键	
									asfile8.setSignFlag(1); //档案文件
									tzxyAppSopFileList.add(asfile8);
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
										asfile8.setFileTime(ti.toString());
									}
									asfile8.setFileKey(sop.getFileKey());
									Long uid = sop.getFileUid();
									if(uid!=null){
										User user = userService.queryById(uid);
										asfile8.setName(user.getRealName());
									}
									asfile8.setId(sop.getId()); //→签署档案表的ID主键
									asfile8.setSignFlag(1); //档案文件
									gqzrxyAppSopFileList.add(asfile8);						
							} 
						}
					}
					List<AppFileDTO> list = new ArrayList<AppFileDTO>();
					
					if(tzxyAppSopFileList.size()>0){
						AppFileDTO appfiledto = new AppFileDTO();
						appfiledto.setFileTypeCode(DictEnum.fileWorktype.投资协议.getCode());
						appfiledto.setFileTypeName(DictEnum.fileWorktype.投资协议.getName());
						appfiledto.setAppSopFile(tzxyAppSopFileList);			
						list.add(appfiledto);
					}
					
					if(gqzrxyAppSopFileList.size()>0){
						AppFileDTO gq_appfileDto = new AppFileDTO();
						gq_appfileDto.setFileTypeCode(DictEnum.fileWorktype.股权转让协议.getCode());
						gq_appfileDto.setFileTypeName(DictEnum.fileWorktype.股权转让协议.getName());
						gq_appfileDto.setAppSopFile(gqzrxyAppSopFileList);					
						list.add(gq_appfileDto);
					}															
					appProgress.setAppFileDtoList(list);
					appProgresslist.add(appProgress);
					
				}
				// 投资决策会
				else if (i == 7) {
					appProgress.setProjectProgressName(DictEnum.projectProgress.getNameByCode("projectProgress:7"));//→项目流程阶段名称
					appProgress.setProjectProgress("projectProgress:7");//→项目流程阶段编码

					meetingRecord.setMeetingType("meetingType:4");
					meetingRecord.setProjectId(Long.parseLong(pid));
					// 默认的是倒序排新添加的在最上面
					List<MeetingRecord> listSop = meetingRecordService.queryList(meetingRecord);
					
					List<MeetingRecord> filterList = new ArrayList<MeetingRecord>();			
					for(MeetingRecord mr : listSop){
						if(StringUtils.isNotBlank(mr.getMeetingResult()) && mr.getMeetingResult().equals(DictEnum.meetingResult.否决.getCode())){
							filterList.clear();
							filterList.add(mr);
							break;
						}else if (StringUtils.isNotBlank(mr.getMeetingResult()) && mr.getMeetingResult().equals(DictEnum.meetingResult.通过.getCode())){
							filterList.clear();
							filterList.add(mr);
							break;
						}else if (StringUtils.isNotBlank(mr.getMeetingResult()) && mr.getMeetingResult().equals(DictEnum.meetingResult.待定.getCode())){
							filterList.add(mr);	
						}					
					}
					if( filterList.size()>0 ){										
						MeetingRecord me = filterList.get(0);
						appProgress.setMeetTime(me.getMeetingDateStr());
						appProgress.setMeetNotes(me.getMeetingNotes());
						appProgress.setMeetResult(me.getMeetingResult());
						appProgress.setMeetingResultStr(me.getMeetingResultStr());
						appProgress.setMeetCode(me.getMeetingType());
						appProgress.setId(me.getId()); //会议ID
						
						//添加排情结果标识
						MeetingScheduling query = new MeetingScheduling();
						query.setProjectId(me.getProjectId());
						query.setMeetingType(DictEnum.meetingType.投决会.getCode());
						List<MeetingScheduling> mslist = meetingSchedulingService.queryList(query);
						if(mslist!=null && mslist.size()>0){
							appProgress.setSchedulingFlag(mslist.get(0).getScheduleStatus());//默认0表示待排期，1表示已排期，2表示已通过，3表示已否决
						}
					}			
					appProgresslist.add(appProgress);						
				}
				// 尽职调查
				else 	if (i == 6) {
					List<AppSopFile> ywFileList = new ArrayList<AppSopFile>();
					List<AppSopFile> fwjFileList = new ArrayList<AppSopFile>();
					List<AppSopFile> cwFileList = new ArrayList<AppSopFile>();
					List<AppSopFile> rsFileList = new ArrayList<AppSopFile>();
					// 项目阶段
					appProgress.setProjectProgressName(DictEnum.projectProgress.getNameByCode("projectProgress:6"));//→项目流程阶段名称
					appProgress.setProjectProgress("projectProgress:6");//→项目流程阶段编码				
					// 据流程阶段编码、项目ID查询文件信息SopFile
					sopFile.setProjectProgress("projectProgress:6");
					sopFile.setProjectId(Long.parseLong(pid));
					
					List<String> fileworkTypeList = new ArrayList<String>();
					fileworkTypeList.add(DictEnum.fileWorktype.业务尽职调查报告.getCode());
					fileworkTypeList.add(DictEnum.fileWorktype.人力资源尽职调查报告.getCode());
					fileworkTypeList.add(DictEnum.fileWorktype.法务尽职调查报告.getCode());
					fileworkTypeList.add(DictEnum.fileWorktype.财务尽职调查报告.getCode());
					sopFile.setFileworktypeList(fileworkTypeList);
					sopFile.setFileValid(1); //生效文件
					List<SopFile> listSop = sopFileService.queryList(sopFile);
					
					AppSopFile _tsopFile = null;
					if (listSop!=null && listSop.size()>0) {
						for (SopFile sop : listSop) {					
							_tsopFile = new AppSopFile();
							//如果项目是投资
							if(projectType.equals(DictEnum.projectType.投资.getCode())){
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
											_tsopFile.setFileTime(ti.toString());
										}
										_tsopFile.setFileKey(sop.getFileKey());
										Long uid = sop.getFileUid();
										if(uid!=null){
											User user = userService.queryById(uid);
											_tsopFile.setName(user.getRealName());
										}
										_tsopFile.setFileValid(sop.getFileValid()); //→文件(档案)待办任务提交，档案生效（是否显示催办）1是生效，0是
										_tsopFile.setId(sop.getId());////→文件(档案)表的ID主键
										ywFileList.add(_tsopFile);
								}												
								else if (sop.getFileWorktype().equals(DictEnum.fileWorktype.法务尽职调查报告.getCode())) {
									_tsopFile.setFileYwCode(sop.getFileWorktype());
									_tsopFile.setFileWorktype(sop.getfWorktype());
										if(sop.getFileName()!=null||sop.getFileSuffix()!=null){
											String fn = sop.getFileName();
											String fs = sop.getFileSuffix();
											String fileName = fn + "." + fs;
											_tsopFile.setFileName(fileName);
										}
										_tsopFile.setFileDsCode(sop.getFileStatus());//→文件(档案)状态编码；当fileStatus:1时，该档案是缺失状态					
										_tsopFile.setFileDs(DictEnum.fileStatus.getNameByCode(sop.getFileStatus()));  //→文件(档案)状态名称
										Long ti = null;
										if (sop.getUpdatedTime() != null) {
											ti = sop.getUpdatedTime();
										} else {
											ti = sop.getCreatedTime();
										}
										if (ti != null) {									
											_tsopFile.setFileTime(ti.toString());
										}
										_tsopFile.setFileKey(sop.getFileKey());
										Long uid = sop.getFileUid();
										if(uid!=null){
											User user = userService.queryById(uid);
											_tsopFile.setName(user.getRealName());
										}
										_tsopFile.setFileValid(sop.getFileValid());//→文件(档案)待办任务提交，档案生效（是否显示催办）1是生效，0是
										_tsopFile.setId(sop.getId());//→文件(档案)表的ID主键	
										fwjFileList.add(_tsopFile);
								} 
						
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
											_tsopFile.setFileTime(ti.toString());
										}
										_tsopFile.setFileKey(sop.getFileKey());
										Long uid = sop.getFileUid();
										if(uid!=null){
											User user = userService.queryById(uid);
											_tsopFile.setName(user.getRealName());
										}
										_tsopFile.setFileValid(sop.getFileValid());//→文件(档案)待办任务提交，档案生效（是否显示催办）1是生效，0是
										_tsopFile.setId(sop.getId()); //→文件(档案)表的ID主键
										cwFileList.add(_tsopFile);
								}
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
											_tsopFile.setFileTime(ti.toString());
										}
										_tsopFile.setFileKey(sop.getFileKey());
										Long uid = sop.getFileUid();
										if(uid!=null){
											User user = userService.queryById(uid);
											_tsopFile.setName(user.getRealName());
										}
										_tsopFile.setFileValid(sop.getFileValid());//→文件(档案)待办任务提交，档案生效（是否显示催办）1是生效，0是
										_tsopFile.setId(sop.getId());////→文件(档案)表的ID主键
										rsFileList.add(_tsopFile);
								}							
							}
							//如果项目是内部投资
							else if(projectType.equals(DictEnum.projectType.创建.getCode())){
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
											_tsopFile.setFileTime(ti.toString());
										}
										_tsopFile.setFileKey(sop.getFileKey());
										Long uid = sop.getFileUid();
										if(uid!=null){
											User user = userService.queryById(uid);
											_tsopFile.setName(user.getRealName());
										}
										_tsopFile.setFileValid(sop.getFileValid()); //→文件(档案)待办任务提交，档案生效（是否显示催办）1是生效，0是
										_tsopFile.setId(sop.getId());////→文件(档案)表的ID主键
										ywFileList.add(_tsopFile);
								}							
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
											_tsopFile.setFileTime(ti.toString());
										}
										_tsopFile.setFileKey(sop.getFileKey());
										Long uid = sop.getFileUid();
										if(uid!=null){
											User user = userService.queryById(uid);
											_tsopFile.setName(user.getRealName());
										}
										_tsopFile.setFileValid(sop.getFileValid());//→文件(档案)待办任务提交，档案生效（是否显示催办）1是生效，0是
										_tsopFile.setId(sop.getId());////→文件(档案)表的ID主键	
										rsFileList.add(_tsopFile);
								}
							}	
						}
					}
					
					List<AppFileDTO> list = new ArrayList<AppFileDTO>();
					if(projectType.equals(DictEnum.projectType.投资.getCode())){
						AppFileDTO appfileDto = new AppFileDTO();
						appfileDto.setFileTypeCode(DictEnum.fileWorktype.业务尽职调查报告.getCode());
						appfileDto.setFileTypeName("业务尽调");
						appfileDto.setAppSopFile(ywFileList);		
						list.add(appfileDto);
						
						AppFileDTO fwappfileDto = new AppFileDTO();
						fwappfileDto.setFileTypeCode(DictEnum.fileWorktype.法务尽职调查报告.getCode());
						fwappfileDto.setFileTypeName("法务尽调");
						fwappfileDto.setAppSopFile(fwjFileList);		
						list.add(fwappfileDto);
						
						AppFileDTO cwappfileDto = new AppFileDTO();
						cwappfileDto.setFileTypeCode(DictEnum.fileWorktype.财务尽职调查报告.getCode());
						cwappfileDto.setFileTypeName("财务尽调");
						cwappfileDto.setAppSopFile(cwFileList);	
						list.add(cwappfileDto);
						
						AppFileDTO rsappfileDto = new AppFileDTO();
						rsappfileDto.setFileTypeCode(DictEnum.fileWorktype.人力资源尽职调查报告.getCode());
						rsappfileDto.setFileTypeName("人事尽调");
						rsappfileDto.setAppSopFile(rsFileList);	
						list.add(rsappfileDto);
						
						appProgress.setAppFileDtoList(list);
						appProgresslist.add(appProgress);	
					}else if(projectType.equals(DictEnum.projectType.创建.getCode())){
						AppFileDTO appfileDto = new AppFileDTO();
						appfileDto.setFileTypeCode(DictEnum.fileWorktype.业务尽职调查报告.getCode());
						appfileDto.setFileTypeName("业务尽调");
						appfileDto.setAppSopFile(ywFileList);		
						list.add(appfileDto);
						
						AppFileDTO rsappfileDto = new AppFileDTO();
						rsappfileDto.setFileTypeCode(DictEnum.fileWorktype.人力资源尽职调查报告.getCode());
						rsappfileDto.setFileTypeName("人事尽调");
						rsappfileDto.setAppSopFile(rsFileList);	
						list.add(rsappfileDto);
						
						appProgress.setAppFileDtoList(list);
						appProgresslist.add(appProgress);	
					}	
				}
				// 投资意向书
				else if (i == 5) {
					// 项目阶段					
					appProgress.setProjectProgressName(DictEnum.projectProgress.getNameByCode("projectProgress:5"));//→项目流程阶段名称
					appProgress.setProjectProgress("projectProgress:5");//→项目流程阶段编码
					// 业务类型
					sopVoucherFile.setProjectProgress("projectProgress:5");
					sopVoucherFile.setProjectId(Long.parseLong(pid));
					List<SopVoucherFile> listSop = sopVoucherFileService.queryList(sopVoucherFile);
					AppSopFile asfile5 = null; 
					List<AppSopFile> tzyxsFileList = new ArrayList<AppSopFile>();
					if (!listSop.isEmpty()) {
						for (SopVoucherFile sop : listSop) {
							asfile5 = new AppSopFile();
							
							if (sop.getFileWorktype().equals(DictEnum.fileWorktype.投资意向书.getCode())) {
								asfile5.setFileYwCode(sop.getFileWorktype());
								asfile5.setFileWorktype(DictEnum.fileWorktype.getNameByCode(sop.getFileWorktype()));
									if(sop.getFileName()!=null||sop.getFileSuffix()!=null){
										String fn = sop.getFileName();
										String fs = sop.getFileSuffix();
										String fileName = fn + "." + fs;
										asfile5.setFileName(fileName);
									}
									asfile5.setFileDsCode(sop.getFileStatus());	//→文件(档案)状态编码；当fileStatus:1时，该档案是缺失状态								
									asfile5.setFileDs(DictEnum.fileStatus.getNameByCode(sop.getFileStatus()));//→文件(档案)状态名称
									Long ti = null;
									if (sop.getUpdatedTime() != null) {
										ti = sop.getUpdatedTime();
									} else {
										ti = sop.getCreatedTime();
									}
									if (ti != null) {									
										asfile5.setFileTime(ti.toString());
									}
									asfile5.setFileKey(sop.getFileKey());
									Long uid = sop.getFileUid();
									if(uid!=null){
										User user = userService.queryById(uid);
										asfile5.setName(user.getRealName());
									}								
									asfile5.setId(sop.getId()); //→文件(档案)表的ID主键
									tzyxsFileList.add(asfile5);
							}
						}
					}
					List<AppFileDTO> list = new ArrayList<AppFileDTO>();		
					AppFileDTO appfileDto = new AppFileDTO();
					appfileDto.setFileTypeCode(DictEnum.fileWorktype.投资意向书.getCode());
					appfileDto.setFileTypeName(DictEnum.fileWorktype.投资意向书.getName());
					appfileDto.setAppSopFile(tzyxsFileList);		
					list.add(appfileDto);

					appProgress.setAppFileDtoList(list);
					appProgresslist.add(appProgress);
				}
				// 立项会
				else if (i == 4) {
					appProgress.setProjectProgressName(DictEnum.projectProgress.getNameByCode("projectProgress:4"));//→项目流程阶段名称
					appProgress.setProjectProgress("projectProgress:4");//→项目流程阶段编码
					//据会议类型、项目ID查询会议信息
					meetingRecord.setMeetingType("meetingType:3");
					meetingRecord.setProjectId(Long.parseLong(pid));
					// 默认的是倒序排添加的最上面
					List<MeetingRecord> listSop = meetingRecordService.queryList(meetingRecord);
					
					List<MeetingRecord> filterList = new ArrayList<MeetingRecord>();				
					for(MeetingRecord mr : listSop){
						if(StringUtils.isNotBlank(mr.getMeetingResult()) && mr.getMeetingResult().equals(DictEnum.meetingResult.否决.getCode())){
							filterList.clear();
							filterList.add(mr);
							break;
						}else if (StringUtils.isNotBlank(mr.getMeetingResult()) && mr.getMeetingResult().equals(DictEnum.meetingResult.通过.getCode())){
							filterList.clear();
							filterList.add(mr);
							break;
						}else if (StringUtils.isNotBlank(mr.getMeetingResult()) && mr.getMeetingResult().equals(DictEnum.meetingResult.待定.getCode())){
							filterList.add(mr);	
						}					
					}
					if( filterList.size()>0 ){ //处理待定 、通过或否决
						MeetingRecord me = filterList.get(0);				
						appProgress.setMeetTime(me.getMeetingDateStr());
						appProgress.setMeetNotes(me.getMeetingNotes());
						appProgress.setMeetResult(me.getMeetingResult());
						appProgress.setMeetingResultStr(me.getMeetingResultStr());
						appProgress.setMeetCode(me.getMeetingType());
						
						//添加排情结果标识
						MeetingScheduling query = new MeetingScheduling();
						query.setProjectId(me.getProjectId());
						query.setMeetingType(DictEnum.meetingType.立项会.getCode());
						List<MeetingScheduling> mslist = meetingSchedulingService.queryList(query);
						if(mslist!=null && mslist.size()>0){
							appProgress.setSchedulingFlag(mslist.get(0).getScheduleStatus());//默认0表示待排期，1表示已排期，2表示已通过，3表示已否决
						}
					}
					appProgresslist.add(appProgress);			
				}
				// CEO评审
				else if (i == 3) {
					appProgress.setProjectProgressName(DictEnum.projectProgress.getNameByCode("projectProgress:3"));//→项目流程阶段名称
					appProgress.setProjectProgress("projectProgress:3");//→项目流程阶段编码
					//据会议类型、项目ID查询会议信息
					meetingRecord.setMeetingType("meetingType:2");
					meetingRecord.setProjectId(Long.parseLong(pid));
					// 默认的是倒序排新添加的在最上面
					List<MeetingRecord> listSop = meetingRecordService.queryList(meetingRecord);
					
					List<MeetingRecord> filterList = new ArrayList<MeetingRecord>();				
					for(MeetingRecord mr : listSop){
						if(StringUtils.isNotBlank(mr.getMeetingResult()) && mr.getMeetingResult().equals(DictEnum.meetingResult.否决.getCode())){
							filterList.clear();
							filterList.add(mr);
							break;
						}else if (StringUtils.isNotBlank(mr.getMeetingResult()) && mr.getMeetingResult().equals(DictEnum.meetingResult.通过.getCode())){
							filterList.clear();
							filterList.add(mr);
							break;
						}else if (StringUtils.isNotBlank(mr.getMeetingResult()) && mr.getMeetingResult().equals(DictEnum.meetingResult.待定.getCode())){
							filterList.add(mr);	
						}					
					}
					if( filterList.size()>0 ){					
						MeetingRecord me = filterList.get(0);						
						appProgress.setMeetTime(me.getMeetingDateStr());
						appProgress.setMeetNotes(me.getMeetingNotes());
						appProgress.setMeetResult(me.getMeetingResult());
						appProgress.setMeetingResultStr(me.getMeetingResultStr());
						appProgress.setMeetCode(me.getMeetingType());
						
						//添加排情结果标识
						MeetingScheduling query = new MeetingScheduling();
						query.setProjectId(me.getProjectId());
						query.setMeetingType(DictEnum.meetingType.CEO评审.getCode());
						List<MeetingScheduling> mslist = meetingSchedulingService.queryList(query);
						if(mslist!=null && mslist.size()>0){
							appProgress.setSchedulingFlag(mslist.get(0).getScheduleStatus());//默认0表示待排期，1表示已排期，2表示已通过，3表示已否决
						}
					}				
					appProgresslist.add(appProgress);
				}
				// 内部评审
				else if (i == 2) {			
					appProgress.setProjectProgressName(DictEnum.projectProgress.getNameByCode("projectProgress:2"));//→项目流程阶段名称
					appProgress.setProjectProgress("projectProgress:2");//→项目流程阶段编码
					//据会议类型、项目ID查询会议信息
					meetingRecord.setMeetingType("meetingType:1");
					meetingRecord.setProjectId(Long.parseLong(pid));
					// 默认的是倒序排新添加的在最上面
					List<MeetingRecord> listSop = meetingRecordService.queryList(meetingRecord);
					
					List<MeetingRecord> filterList = new ArrayList<MeetingRecord>();				
					for(MeetingRecord mr : listSop){
						if(StringUtils.isNotBlank(mr.getMeetingResult()) && mr.getMeetingResult().equals(DictEnum.meetingResult.否决.getCode())){
							filterList.clear();
							filterList.add(mr);
							break;
						}else if (StringUtils.isNotBlank(mr.getMeetingResult()) && mr.getMeetingResult().equals(DictEnum.meetingResult.通过.getCode())){
							filterList.clear();
							filterList.add(mr);
							break;
						}else if (StringUtils.isNotBlank(mr.getMeetingResult()) && mr.getMeetingResult().equals(DictEnum.meetingResult.待定.getCode())){
							filterList.add(mr);	
						}					
					}
					if( filterList.size()>0 ){									
						MeetingRecord me = filterList.get(0);					
						appProgress.setMeetTime(me.getMeetingDateStr());
						appProgress.setMeetNotes(me.getMeetingNotes());
						appProgress.setMeetResult(me.getMeetingResult());
						appProgress.setMeetingResultStr(me.getMeetingResultStr());
						appProgress.setMeetCode(me.getMeetingType());
					}				
					appProgresslist.add(appProgress);				
				}
				// 访谈记录
				else if (i == 1) {			
					appProgress.setProjectProgressName(DictEnum.projectProgress.getNameByCode("projectProgress:1"));//→项目流程阶段名称
					appProgress.setProjectProgress("projectProgress:1");//→项目流程阶段编码
					
					interviewRecord.setProjectId(Long.parseLong(pid));
					List<InterviewRecord> listSop = interviewRecordService.queryList(interviewRecord);
					if (listSop!=null && listSop.size()>0) {
						InterviewRecord me = listSop.get(0);						
						appProgress.setInterviewTime(me.getViewDateStr());//访谈时间
						appProgress.setInterviewNotes(me.getViewNotes());//访谈日志
						appProgress.setInterviewTarget(me.getViewTarget());//访谈对象
					}
					appProgresslist.add(appProgress);		
				}
			}
			responseBody.setEntityList(appProgresslist);
		} catch (Exception e) {
			logger.error("appProgreess faild", e);
			responseBody.setResult(new Result(Status.ERROR, null, "appProgreess faild"));
		}
		return responseBody;
	}

	/**
	   * 供app端查询数据快览
	   * 
	   */
	   	@ResponseBody
		@RequestMapping(value = "/appCounts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	   	public ResponseData<AppCounts> searchAppProjectList(HttpServletRequest request) {
			ResponseData<AppCounts> responseBody = new ResponseData<AppCounts>();
			User user = (User) getUserFromSession(request);
			// 判断当前用户是否为投资经理
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user
					.getId());
			if (!roleIdList.contains(UserConstant.HHR)
					&& !roleIdList.contains(UserConstant.CEO)
					&&!roleIdList.contains(UserConstant.DSZ)) {
				responseBody.setResult(new Result(Status.ERROR, null, "没有权限查询!"));
				return responseBody;
			}
			try {
				
				if(roleIdList.contains(UserConstant.HHR)){
					AppCounts appCounts=new AppCounts();
					
					if(user.getDepartmentId()!=null){	
						Project project = new Project();
						project.setProjectDepartid(user.getDepartmentId());
						Long it=projectService.queryCount(project);
						appCounts.setProjectCounts(it.intValue());
						List<Project> listp=projectService.queryList(project);
						String st="0.00";
						double d=(Double.parseDouble(st));
						for(Project pro :listp){
							if(pro.getProjectValuations()!=null){
								d=d+pro.getProjectValuations();
							}
						}	
						DecimalFormat df = new DecimalFormat("#.00");					
						appCounts.setProjectValuations(df.format(d));					
					}
					Project project1 = new Project();
					project1.setProjectDepartid(user.getDepartmentId());
					project1.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
					Long it=projectService.queryCount(project1);
					appCounts.setJzproject(it.intValue());
					
					Project project2 = new Project();
					project2.setProjectDepartid(user.getDepartmentId());
					project2.setProjectProgress(DictEnum.projectProgress.投后运营.getCode());
					Long it1=projectService.queryCount(project2);
					appCounts.setThproject(it1.intValue());					
					responseBody.setResult(new Result(Status.OK, ""));
					responseBody.setEntity(appCounts);
					return responseBody;		
				}else{		
						AppCounts appCounts=new AppCounts();
						Long it=projectService.queryCount();
						appCounts.setProjectCounts(it.intValue());
						List<Project> listp=projectService.queryAll();
						String st="0.00";
						double d=(Double.parseDouble(st));
						for(Project pro :listp){
							if(pro.getProjectValuations()!=null){
								d=d+pro.getProjectValuations();
							}
						}	
						DecimalFormat df = new DecimalFormat("#.00");					
						appCounts.setProjectValuations(df.format(d));					
						Project project1 = new Project();					
						project1.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
						Long it1=projectService.queryCount(project1);
						appCounts.setJzproject(it1.intValue());					
						Project project2 = new Project();					
						project2.setProjectProgress(DictEnum.projectProgress.投后运营.getCode());
						Long it2=projectService.queryCount(project2);
						appCounts.setThproject(it2.intValue());					
						responseBody.setResult(new Result(Status.OK, ""));
						responseBody.setEntity(appCounts);
						return responseBody;					
				}	
			} catch (PlatformException e) {
				
				return responseBody;
			}
	   	}

}
