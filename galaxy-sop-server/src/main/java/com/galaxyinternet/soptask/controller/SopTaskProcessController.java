package com.galaxyinternet.soptask.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.galaxyinternet.bo.SopTaskBo;
import com.galaxyinternet.common.annotation.LogType;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.dictEnum.DictEnum;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.file.UploadFileResult;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.sopfile.SopVoucherFile;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.platform.constant.PlatformConst;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.SopTaskService;
import com.galaxyinternet.service.SopVoucherFileService;
import com.galaxyinternet.service.UserRoleService;
import com.galaxyinternet.service.UserService;


@Controller
@RequestMapping("/galaxy/taskprocess")
public class SopTaskProcessController extends BaseControllerImpl<SopTask, SopTaskBo> 
{
	private final Logger logger = LoggerFactory.getLogger(SopTaskProcessController.class);
	@Autowired
	private SopTaskService sopTaskService;
	@Autowired
	private SopFileService sopFileService;
	@Autowired
	private SopVoucherFileService sopVoucherFileService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProjectService projectService;


	@Override
	protected BaseService<SopTask> getBaseService() {
		return sopTaskService;
	}
	
	
	
	@ResponseBody
	@RequestMapping("/uploadFile")
	@com.galaxyinternet.common.annotation.Logger(operationScope=LogType.LOG)
	public Result uploadFile(SopFile bo, HttpServletRequest request)
	{
		Result result = new Result();
		try {
			User user = (User) getUserFromSession(request);
			MultipartFile file = null;
			if(request instanceof MultipartHttpServletRequest)
			{
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				file = multipartRequest.getFile("file");
			}
			if(file != null)
			{
				SopFile po = sopFileService.queryById(bo.getId());
				String fileName = file.getOriginalFilename();
				int dotPos = fileName.lastIndexOf(".");
				String key = String.valueOf(IdGenerator.generateId(OSSHelper.class));
				String prefix = fileName.substring(0, dotPos);
				String suffix = fileName.substring(dotPos);
				
				UploadFileResult ur = uploadFileToOSS(request, key, tempfilePath);
				result = ur.getResult();
				if(ur.getResult() != null && Result.Status.OK.equals(ur.getResult().getStatus()))
				{
					bo.setFileKey(key);
					bo.setFileValid(1);
					bo.setFileLength(ur.getContentLength());
					bo.setFileName(prefix);
					bo.setFileSuffix(suffix.replaceAll("\\.", ""));
					bo.setUpdatedTime(System.currentTimeMillis());
					bo.setFileStatus(DictEnum.fileStatus.已上传.getCode());
					if(user != null)
					{
						bo.setFileUid(user.getId());
					}
					sopFileService.updateById(bo);
					SopFile history = new SopFile();
					history.setFileId(bo.getId());
					sopFileService.insertHistory(history);
					/*****Log/Message Start*****/
					Project project = projectService.queryById(po.getProjectId());
					if(project != null && project.getCreateUid() != null)
					{
						UrlNumber number = UrlNumber.one;
						if(!StringUtils.isEmpty(po.getFileKey()))
						{
							number = UrlNumber.two;
						}
						User manager = userService.queryById(project.getCreateUid());
						String messageType = null;
						if(DictEnum.fileWorktype.人力资源尽职调查报告.getCode().equals(po.getFileWorktype()))
						{
							messageType = "_5.5";
						}
						else if(DictEnum.fileWorktype.财务尽职调查报告.getCode().equals(po.getFileWorktype()))
						{
							messageType = "_5.6";
						}
						else if(DictEnum.fileWorktype.法务尽职调查报告.getCode().equals(po.getFileWorktype()))
						{
							messageType = "_5.7";
						}
						else if(DictEnum.fileWorktype.工商转让凭证.getCode().equals(po.getFileWorktype()))
						{
							messageType = "_5.10";
						}
						else if(DictEnum.fileWorktype.资金拨付凭证.getCode().equals(po.getFileWorktype()))
						{
							messageType = "_5.11";
						}
						if(messageType != null)
						{
							SopTask task = sopTaskService.getByFileInfo(bo.getId());
							
							Map<String, Object> params = new HashMap<>();
							params.put(PlatformConst.REQUEST_SCOPE_PROJECT_NAME, project.getProjectName());
							params.put(PlatformConst.REQUEST_SCOPE_PROJECT_ID, project.getId());
							params.put(PlatformConst.REQUEST_SCOPE_MESSAGE_TYPE, messageType);
							params.put(PlatformConst.REQUEST_SCOPE_URL_NUMBER, number.name());
							if(task != null)
							{
								params.put(PlatformConst.REQUEST_SCOPE_RECORD_ID, task.getId());
							}
							params.put(PlatformConst.REQUEST_SCOPE_USER, manager);
							ControllerUtils.setRequestParamsForMessageTip(request, params);
						}
					}
					
					/*****Log/Message End*****/
					
				}
			}
			
		} catch (Exception e) {
			Object msg = "上传失败";
			result.addError(msg);
			logger.error(msg.toString(),e);
		}
		
		return result;
	}
	@ResponseBody
	@RequestMapping("/uploadVoucher")
	public Result uploadVoucher(SopVoucherFile bo, HttpServletRequest request)
	{
		Result result = new Result();
		try {
			MultipartFile file = null;
			if(request instanceof MultipartHttpServletRequest)
			{
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				file = multipartRequest.getFile("file");
			}
			if(file != null)
			{
				String fileName = file.getOriginalFilename();
				int dotPos = fileName.lastIndexOf(".");
				SopVoucherFile po = sopVoucherFileService.queryById(bo.getId());
				String key = po != null && po.getFileKey() != null ? po.getFileKey() : String.valueOf(IdGenerator.generateId(OSSHelper.class));
				String ext = fileName.substring(dotPos);
				File temp = File.createTempFile(key, ext);
				Long length = temp.length();
				file.transferTo(temp);
				OSSHelper.simpleUploadByOSS(temp,key);
				
				bo.setFileKey(key);
				bo.setFileLength(length);
				bo.setFileName(fileName);
				bo.setUpdatedTime(System.currentTimeMillis());
				bo.setFileStatus(DictEnum.fileStatus.已上传.getCode());
			}
			sopVoucherFileService.updateById(bo);
			result.setStatus(Status.OK);
			
		} catch (Exception e) {
			Object msg = "上传失败";
			result.addError(msg);
			logger.error(msg.toString(),e);
		}
		
		return result;
	}

	
	private String tempfilePath;
	public String getTempfilePath() {
		return tempfilePath;
	}
	@Value("${sop.oss.tempfile.path}")
	public void setTempfilePath(String tempfilePath) {
		this.tempfilePath = tempfilePath;
	}
}
