package com.galaxyinternet.soptask.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.galaxyinternet.bo.SopTaskBo;
import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.dictEnum.DictEnum;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.framework.core.config.PlaceholderConfigurer;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.file.UploadFileResult;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.framework.core.utils.mail.MailTemplateUtils;
import com.galaxyinternet.framework.core.utils.mail.SimpleMailSender;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.sopfile.SopVoucherFile;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.model.user.UserRole;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.SopTaskService;
import com.galaxyinternet.service.SopVoucherFileService;
import com.galaxyinternet.service.UserRoleService;
import com.galaxyinternet.service.UserService;
import com.galaxyinternet.utils.SopConstatnts;


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
	private UserRoleService userRoleService;
	@Autowired
	private ProjectService projectService;


	@Override
	protected BaseService<SopTask> getBaseService() {
		return sopTaskService;
	}
	
	@RequestMapping("/showFileList")
	public ModelAndView showFileList(@RequestParam Integer taskFlag,@RequestParam Long projectId)
	{
		Project project=new Project();
		project.setId(projectId);
		Project queryOne = projectService.queryOne(project);
		ModelAndView mv = new ModelAndView();
		String viewName = "";
		String btnTxt = "";
		String fileWorktype = "";
		boolean isShow=false;
		switch(taskFlag)
		{
			case 0: //完善简历
				viewName = "/resumetc/wanshanjianli";
				break;
			case 1 : //表示投资意向书
				fileWorktype = "fileWorktype:5";
				viewName = "/taskProcess/tzyxs";
				break;
			case 2 : //人事尽职调查报告
				btnTxt = "上传尽调报告";
				fileWorktype = "fileWorktype:2";
				viewName = "/taskProcess/singleFileUpload";
				break;
			case 3 : //法务尽职调查报告
				btnTxt = "上传尽调报告";
				fileWorktype = "fileWorktype:3";
				if(queryOne.getProjectType().equals("projectType:2")){
					isShow=true;
				}
				viewName = "/taskProcess/singleFileUpload";
				break;
			case 4 : //财务尽调报告
				btnTxt = "上传尽调报告";
				fileWorktype = "fileWorktype:4";
				if(queryOne.getProjectType().equals("projectType:2")){
					isShow=true;
				}
				viewName = "/taskProcess/singleFileUpload";
				break;
			case 5 : //业务尽调报告
				viewName = "/taskProcess/ywjd";
				break;
			case 6 : //投资协议
			case 7 : //股权转让协议
				viewName = "/taskProcess/tzxy";
				break;
			case 8 : //资金拨付凭证
				btnTxt = "上传资金拨付凭证";
				fileWorktype = "fileWorktype:9";
				viewName = "/taskProcess/singleFileUpload";
				break;
			case 9 : //工商变更登记凭证
				btnTxt = "上传工商变更登记凭证";
				fileWorktype = "fileWorktype:8";
				viewName = "/taskProcess/singleFileUpload";
				break;
			case SopConstatnts.TaskCode._accept_project_flag_ : //工商变更登记凭证
				btnTxt = "接收项目";
				viewName = "/project/projectTransfer/receiveTask";
				break;
			default :
				logger.error("Error taskFlag "+ taskFlag);
		}
		mv.setViewName(viewName);
		mv.addObject("fileWorktype", fileWorktype);
		mv.addObject("btnTxt", btnTxt);
		mv.addObject("taskFlag", taskFlag);
		mv.addObject("isShow", isShow);
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/uploadFile")
	@com.galaxyinternet.common.annotation.Logger
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
				String key = po != null && po.getFileKey() != null ? po.getFileKey() : String.valueOf(IdGenerator.generateId(OSSHelper.class));
				String prefix = fileName.substring(0, dotPos);
				String suffix = fileName.substring(dotPos);
				
				UploadFileResult ur = uploadFileToOSS(request, key, tempfilePath);
				result = ur.getResult();
				if(ur.getResult() != null && Result.Status.OK.equals(ur.getResult().getStatus()))
				{
					bo.setFileKey(key);
					bo.setFileValid(0);
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
					
					/*****Log/Message Start*****/
					Project project = projectService.queryById(po.getProjectId());
					if(project != null && project.getCreateUid() != null)
					{
						UrlNumber number = UrlNumber.one;
						if(po.getFileKey() != null)
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
							ControllerUtils.setRequestParamsForMessageTip(request, manager, project.getProjectName(), project.getId(), messageType, number);
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

	/**
	 * 文档上传任务催办
	 * @param id 传入sop_file.id；
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/taskUrged", produces = MediaType.APPLICATION_JSON_VALUE)
	@com.galaxyinternet.common.annotation.Logger
	public ResponseData<User> taskUrged(Long id,HttpServletRequest request)
	{
		ResponseData<User> resp = new ResponseData<User>();
		boolean flag  = true;
		//当前登录人
		User curUser = (User) request.getSession().getAttribute(
				Constants.SESSION_USER_KEY);
		try {
			SopTask task = sopTaskService.getByFileInfo(id);
			if(task == null)
			{
				logger.error("No task fount. file id = "+id);
				resp.getResult().addError("请求参数错误");
				return resp;
			}
			Project project = projectService.queryById(task.getProjectId());
			User user = null;
			if(task.getAssignUid() != null) //已认领的任务 - 认领人
			{
				user = userService.queryById(task.getAssignUid());
				String time = DateUtil.longToString(task.getUpdatedTime());
				Date date = new Date();
				String taskUrgedTime = DateUtil.convertDateToString(date);
				String toMail = user.getEmail() + Constants.MAIL_SUFFIX;
				String str = MailTemplateUtils.getContentByTemplate(Constants.MAIL_URGE_CONTENT);
				String content = PlaceholderConfigurer.formatText(str, user.getRealName(),time,task.getTaskName(),taskUrgedTime,curUser.getRealName());
				String subject = "催办通知";// 邮件主题
				flag = SimpleMailSender.sendHtmlMail(toMail, subject, content)&& flag;
			}
			else if(task.getDepartmentId() != null) //待认领的任务 - 部门总监
			{
				
				Long roleId = null;
				if(task.getDepartmentId().longValue() == SopConstant.DEPARTMENT_RS_ID)
				{
					roleId = UserConstant.HRZJ;
				}
				else if(task.getDepartmentId().longValue() == SopConstant.DEPARTMENT_CW_ID)
				{
					roleId = UserConstant.CWZJ;
				}
				else if(task.getDepartmentId().longValue() == SopConstant.DEPARTMENT_FW_ID)
				{
					roleId = UserConstant.FWZJ;
				}
				if(roleId != null)
				{
					UserRole urQuery = new UserRole();
					urQuery.setRoleId(roleId);
					List<UserRole> urList = userRoleService.queryList(urQuery);
					//添加催办邮件 
					if(urList != null && urList.size() >0)
					{
						List<Long> usrIds = new ArrayList<Long>();
						for(UserRole au : urList){
							usrIds.add(au.getUserId());
						}
						if(usrIds != null && !usrIds.isEmpty())
						{
							User quser  = new User();
							quser.setStatus("0");
							quser.setIds(usrIds);
							List<User> users = userService.queryList(quser);
							if(users != null && !users.isEmpty()){
								user = users.iterator().next();   //默认  只有各部门仅一总监
								
								Date date = new Date();
								String taskCreateDate = DateUtil.longToString(task.getCreatedTime());
								String taskUrgedTime = DateUtil.convertDateToString(date);
								String toMail = user.getEmail() + Constants.MAIL_SUFFIX;
								String str = MailTemplateUtils.getContentByTemplate(Constants.MAIL_URGE_CONTENT_SPECIAL);
								String content = PlaceholderConfigurer.formatText(str, user.getRealName(),taskCreateDate,task.getTaskName(),taskUrgedTime,curUser.getRealName());
								String subject = "催办通知";// 邮件主题
								flag = SimpleMailSender.sendHtmlMail(toMail, subject, content)&& flag;
							}
						}
						
					}
				}
			}
			if(user == null)
			{
				logger.error("No user fount. file id = "+id);
				resp.getResult().addError("请求参数错误");
				return resp;
			}
			if(project != null)
			{
				String messageType = null;
				if(SopConstant.TASK_NAME_RSJD.equals(task.getTaskName()))
				{
					messageType = "7.1";
				}
				else if(SopConstant.TASK_NAME_CWJD.equals(task.getTaskName()))
				{
					messageType = "7.2";
				}
				else if(SopConstant.TASK_NAME_FWJD.equals(task.getTaskName()))
				{
					messageType = "7.3";
				}
				else if(SopConstant.TASK_NAME_GSBG.equals(task.getTaskName()))
				{
					messageType = "7.4";
				}
				else if(SopConstant.TASK_NAME_ZJBF.equals(task.getTaskName()))
				{
					messageType = "7.5";
				}
				
				ControllerUtils.setRequestParamsForMessageTip(request, user, project.getProjectName(), project.getId(), messageType, UrlNumber.one);
			}
			//邮件发送失败 返回
			if (flag == false) {
				resp.getResult().addError("邮件发送失败");
			} else {
				resp.getResult().addOK("催办成功");
			}
			
		} catch (Exception e) {
			String msg = "任务催办失败";
			logger.error(msg+". file id = "+id,e);
			resp.getResult().addError(e.getMessage());
		}
		
		return resp;
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
