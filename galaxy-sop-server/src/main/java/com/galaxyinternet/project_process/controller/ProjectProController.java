package com.galaxyinternet.project_process.controller;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.framework.core.utils.JSONUtils;
import com.galaxyinternet.model.dict.Dict;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.project.service.HandlerManager;
import com.galaxyinternet.project_process.service.ProFlowAboutFileService;
import com.galaxyinternet.project_process.util.ProFlowUtilImpl;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.DictService;
import com.galaxyinternet.service.InterviewRecordService;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.MeetingSchedulingService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.UserRoleService;


/**
 * 访谈、
 * 内部评审、 
 * CEO评审 、
 * 立项会、
 * 商务谈判、
 * 投资意向书、
 * 尽职调查、
 * 投决会 、
 * 投资协议 、
 * 股权交割、
 * 投后运营
 */
@Controller
@RequestMapping("/galaxy/progressT")
public class ProjectProController extends BaseControllerImpl<Project, ProjectBo> {
	
	final Logger logger = LoggerFactory.getLogger(ProjectProController.class);
	
	private String tempfilePath;
	public String getTempfilePath() { return tempfilePath; }
	@Value("${sop.oss.tempfile.path}")
	public void setTempfilePath(String tempfilePath) { this.tempfilePath = tempfilePath; }
	
	
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private SopFileDao sopFileDao;
	@Autowired
	private MeetingRecordService meetingRecordService;
	@Autowired
	private DictService dictService;
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
	
	@Autowired
	private HandlerManager handlerManager;
	
	@Override
	protected BaseService<Project> getBaseService() {
		return this.projectService;
	}
	
	@Autowired
	private  ProFlowAboutFileService proFlowAboutFileService;

	
	/**
	 * 根据  项目id、项目 progress ，封装当前文件的信息;  
	 * @param   pro.getId()  pro.getProjectProgress()
	 * @return
	 */
	@RequestMapping(value = "/showProFlowFiles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseData<SopFile> showProFlowFiles(HttpServletRequest request,@RequestBody Project query ) {
		ResponseData<SopFile> responseBody = new ResponseData<SopFile>();
		
		if(!ProFlowUtilImpl.file_about.containsKey(query.getProjectProgress())){
			return responseBody;
		}
		
		Map<String,Object> resultMap = new LinkedHashMap<>();
		try {
			User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			//Long uDept = user.getDepartmentId();
			
			Project pro = projectService.queryById(query.getId());
			//Long proDept = pro.getProjectDepartid();
			
			//登录人的角色
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
			
			//封装结果
			List<SopFile> files =  proFlowAboutFileService.getFileListForProFlow(query);
			
			if(!files.isEmpty()){
				Map<String,String> filetype_name = new HashMap<>();
				List<Dict> dictList = dictService.selectByParentCode(ProFlowUtilImpl.fileTypePareatCode);
				for(Dict tem : dictList){
					filetype_name.put(tem.getCode(), tem.getName());
				}
				
				//封装权限
				Boolean checkOpt = false;
				Boolean optAuth = false;
				Map<String,Boolean> fwType = null;   // projectProgress : <fileWorktype : canOpt(true\false) >
				
				if(DictEnum.projectStatus.YFJ.getCode().equals(pro.getProjectStatus()) 
						|| DictEnum.projectStatus.YTC.getCode().equals(pro.getProjectStatus())){ //以否决的项目
					checkOpt = true;
					optAuth = false;
				}else if(roleIdList.contains(UserConstant.TZJL)){   //投资经理
					fwType = ProFlowUtilImpl.role_file_opt_about.get(UserConstant.TZJL).get(query.getProjectProgress());
					if(user.getId().longValue() != pro.getCreateUid().longValue() || fwType == null){
						checkOpt = true;
						optAuth = false;
					}else{
						checkOpt = false;
						optAuth = false;
					}
				}else{   //其他角色
					checkOpt = true;
					optAuth = false;
				}
		
				if(checkOpt){
					for(SopFile temp : files){
						temp.setCanOpt(optAuth);
						temp.setCanDown(true);
						temp.setfWorktype(filetype_name.get(temp.getFileWorktype()));
						resultMap.put(temp.getFileWorktype(), temp);
					}
				}else{
					for(SopFile temp : files){
						temp.setCanOpt(fwType.get(temp.getFileWorktype()));
						temp.setCanDown(true);
						temp.setfWorktype(filetype_name.get(temp.getFileWorktype()));
						resultMap.put(temp.getFileWorktype(), temp);
					}
				}
			}
			
			//responseBody.setEntityList(files);
			responseBody.setUserData(resultMap);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (Exception e) {
			logger.error("showFilesProFlow 异常",e);
			responseBody.setResult(new Result(Status.ERROR, null,"系统异常"));
		}
		
		return responseBody;
	}
	
	
	
	
	
	
	
	
	/**
	 * 用户上传文档
	 * 根据  文档id、fileKey ，判断 insert update 
	 * @param   
	 * 	file.getId() 、 file.FileWorktype 、 file.getFileKey()
	 * 	file.projectId、file.projectProgress
	 * 
	 * 	file.FileType 、 file.FileSource
	 * 
	 * 	file.fileUid   file.CareerLine  
	 * @return 
	 */
	@RequestMapping(value = "/optProFlowFiles", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseData<SopFile> showFilesProFlow(HttpServletRequest request, SopFile fileTemp ) {
		ResponseData<SopFile> responseBody = new ResponseData<SopFile>();
		
		if(fileTemp.getProjectId() == null){
			responseBody.setResult(new Result(Status.ERROR, null,"参数缺失"));
			return responseBody;
		}
		try {
			//String json = JSONUtils.getBodyString(request);
			//fileTemp = GSONUtil.fromJson(json, SopFile.class);
			
			User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			
			Project pro = projectService.queryById(fileTemp.getProjectId());
			
			//封装结果
			fileTemp.setFileUid(user.getId());
			fileTemp.setCareerLine(user.getDepartmentId());
			SopFile result = proFlowAboutFileService.optFileAboutProgress(request,this, fileTemp, tempfilePath);
			
			if(result == null){
				logger.error("showFilesProFlow 异常");
				responseBody.setResult(new Result(Status.ERROR, null,"系统异常"));
			}else{
				Dict dict = dictService.selectByCode(result.getFileWorktype());
				result.setfWorktype(dict.getName());
				
				result.setCanOpt(true);
				result.setCanDown(true);
				
				responseBody.setEntity(result);
				responseBody.setResult(new Result(Status.OK, ""));
			}
		} catch (Exception e) {
			logger.error("showFilesProFlow 异常",e);
			responseBody.setResult(new Result(Status.ERROR, null,"系统异常"));
		}
		
		return responseBody;
	}
	
	
}
