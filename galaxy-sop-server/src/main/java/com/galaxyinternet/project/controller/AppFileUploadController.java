package com.galaxyinternet.project.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.common.annotation.LogType;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.dictEnum.DictEnum;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.AppSopFile;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
/**
 * APP端文件上传管理
 * @author LZJ
 * @ClassName  : AppFileUploadController  
 * @Version  版本   
 * @ModifiedBy
 * @Copyright  Galaxyinternet  
 * @date  2016年6月8日 上午10:28:40
 */
@Controller
@RequestMapping("/galaxy/mobile/filemanage")
public class AppFileUploadController extends BaseControllerImpl<SopFile, AppSopFile> {
	
	final Logger logger = LoggerFactory.getLogger(AppFileUploadController.class);		
	
	@Autowired
	private SopFileService sopFileService;
	
	@Autowired
	private ProjectService projectService;

	@Override
	protected BaseService<SopFile> getBaseService() {
		return this.sopFileService;
	}
	
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG, LogType.MESSAGE })
	@ResponseBody
	@RequestMapping(value = "/uploadBizplan", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingRecord> fileUploadHandle(@RequestBody AppSopFile appSopFile , HttpServletRequest request) {
		
		ResponseData<MeetingRecord> responseBody = new ResponseData<MeetingRecord>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		/*
		 * 参数是否空值检查
		 * 包括项目ID，文件KEY，文件名称，文件后缀名，文件长度，上传人ID
		 */
		if (appSopFile.getProjectId() == null || StringUtils.isBlank(appSopFile.getFileKey())
				|| StringUtils.isBlank(appSopFile.getFileName()) || StringUtils.isBlank(appSopFile.getFileSuffix())
				|| StringUtils.isBlank(appSopFile.getFileKey()) || appSopFile.getFileLength()==null 
				|| appSopFile.getBelongUid()==null ) {
			responseBody.setResult(new Result(Status.ERROR,null, "请完善档案文件信息，传入参数值不完整或缺失"));
			return responseBody;
		}
		
		try{
			SopFile sfile = new SopFile();
			sfile.setFileKey(appSopFile.getFileKey());
			sfile.setProjectId(appSopFile.getProjectId());
			List<SopFile> sopFileList = sopFileService.queryList(sfile);
			//更新上传
			if(sopFileList!=null && sopFileList.size()>0){
				Long sopFileId = sopFileList.get(0).getId();
				SopFile entity = new SopFile();
//				entity.setProjectId(appSopFile.getProjectId());
				entity.setFileKey(appSopFile.getFileKey());
				entity.setFileLength(appSopFile.getFileLength());
				entity.setFileName(appSopFile.getFileName());
				entity.setFileSuffix(appSopFile.getFileSuffix());
				entity.setCareerLine(user.getDepartmentId()); //事业线ID(部门表中除类型是职能部门外的ID)
//				entity.setFileWorktype(DictEnum.fileWorktype.商业计划.getCode());
				entity.setId(sopFileId);
				entity.setUpdatedTime(System.currentTimeMillis());
				sopFileService.updateByIdSelective(entity);				
			}
			//首次上传
			else{
				SopFile entity = new SopFile();
				entity.setProjectId(appSopFile.getProjectId());
				entity.setFileKey(appSopFile.getFileKey());
				entity.setFileLength(appSopFile.getFileLength());
				entity.setFileName(appSopFile.getFileName());
				entity.setFileSuffix(appSopFile.getFileSuffix());
				entity.setBucketName("");
				entity.setProjectProgress(DictEnum.projectProgress.接触访谈.getCode());
				entity.setFileWorktype(DictEnum.fileWorktype.商业计划.getCode());
				entity.setCareerLine(user.getDepartmentId()); //事业线ID(部门表中除类型是职能部门外的ID)
				
				Project  project = projectService.queryById(appSopFile.getProjectId());
				String proType = project.getProjectType();
				if(StringUtils.isNotBlank(proType) && proType.equals(DictEnum.projectType.投资.getCode())){//投资
					entity.setFileSource("2");
				}else if (StringUtils.isNotBlank(proType) && proType.equals(DictEnum.projectType.创建.getCode())){//创建
					entity.setFileSource("1");
				}			
				entity.setFileType(DictEnum.fileType.文档.getCode());
				entity.setFileStatus(DictEnum.fileStatus.已上传.getCode());
				entity.setFileUid(appSopFile.getBelongUid());//上传所属人
				entity.setCreatedTime(System.currentTimeMillis());			
				sopFileService.insert(entity);
			}
		}catch(Exception ex){
			logger.error("文件上传信息保存失败", ex);
			responseBody.setResult(new Result(Status.ERROR,null, "文件上传信息保存失败"));
			return responseBody;
		}
		responseBody.setResult(new Result(Status.OK,null, "商业计划文件上传成功"));
		return responseBody;
		
		
	}

}
