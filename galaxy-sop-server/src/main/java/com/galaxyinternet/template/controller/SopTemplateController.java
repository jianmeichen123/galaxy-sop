package com.galaxyinternet.template.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.galaxyinternet.bo.template.SopTemplateBo;
import com.galaxyinternet.common.annotation.LogType;
import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.enums.DictEnum;
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
import com.galaxyinternet.model.role.Role;
import com.galaxyinternet.model.sopfile.SopDownLoad;
import com.galaxyinternet.model.template.SopTemplate;
import com.galaxyinternet.model.template.TemplateMailInfo;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.SopTemplateService;
import com.galaxyinternet.service.UserService;
@Controller
@RequestMapping("/galaxy/template")
public class SopTemplateController extends BaseControllerImpl<SopTemplate, SopTemplateBo> {
	final Logger logger = LoggerFactory.getLogger(SopTemplateController.class);
	@Autowired
	private SopTemplateService templateService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private SopFileService sopFileService;
	
	private String tempfilePath;
	
	
	public String getTempfilePath() {
		return tempfilePath;
	}
	@Value("${sop.oss.tempfile.path}")
	public void setTempfilePath(String tempfilePath) {
		this.tempfilePath = tempfilePath;
	}
	@Override
	protected BaseService<SopTemplate> getBaseService() {
		return this.templateService;
	}
	@RequestMapping(method = RequestMethod.GET)
	public String list()
	{
		return "template/template_list";
	}
	@ResponseBody
	@RequestMapping(value = "/queryTemplate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopTemplate> queryTemplate(HttpServletRequest request)
	{
		ResponseData<SopTemplate> rtn = new ResponseData<SopTemplate>();
		try {
			User user = (User) getUserFromSession(request);
			if(user != null && user.getId() != null)
			{
				Role role = userService.getRoleByUserId(user.getId());
				List<String>  types = null;
				String typesStr = "";
				String editableTypes = "";
				SopTemplateBo query = new SopTemplateBo();
				if(UserConstant.TZJL == role.getId())
				{
					List<Long> departmentIdList = new ArrayList<Long>();
					typesStr = "templateType:1,templateType:2,templateType:3,templateType:4,templateType:5,templateType:6,templateType:7";
					editableTypes = "templateType:3";
					departmentIdList.add(user.getDepartmentId());
					departmentIdList.add(SopConstant.DEPARTMENT_RS_ID);
					departmentIdList.add(SopConstant.DEPARTMENT_FW_ID);
					departmentIdList.add(SopConstant.DEPARTMENT_CW_ID);
					query.setDepartmentIds(departmentIdList.toArray(new Long[departmentIdList.size()]));
				}
				else if(UserConstant.HRJL == role.getId() || UserConstant.HRZJ == role.getId() )
				{
					typesStr = DictEnum.TemplateType.RSJD.getCode();
					editableTypes = typesStr;
				}
				else if(UserConstant.CWJL == role.getId() || UserConstant.CWZJ == role.getId() )
				{
					typesStr = DictEnum.TemplateType.CWJD.getCode();
					editableTypes = typesStr;
				}
				else if(UserConstant.FWJL == role.getId() || UserConstant.FWZJ == role.getId() )
				{
					typesStr = DictEnum.TemplateType.FWJD.getCode()+","+DictEnum.TemplateType.TZXY.getCode()+","+DictEnum.TemplateType.TZYXS.getCode()+","+DictEnum.TemplateType.GQZR.getCode();
					editableTypes = typesStr;
				}
				
				if(typesStr != null && typesStr.length()>0)
				{
					types = Arrays.asList(typesStr.split(","));
					query.setFileWorktypes(types);
					List<SopTemplate> list = templateService.queryList(query);
					rtn.setEntityList(list);
					Map<String, Object> userData = new HashMap<String, Object>();
					userData.put("editableTypes", editableTypes);
					rtn.setUserData(userData);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			rtn.getResult().addError("模板查询失败.");
			logger.error("模板查询失败.",e);
		}
		return rtn;
	}
	
	@RequestMapping("/getRelatedData")
	@ResponseBody
	public Map<String,Object> getRelatedData()
	{
		return templateService.getRelatedData();
	}
	@RequestMapping("/upload")
	@ResponseBody
	public ResponseData<SopTemplate> upload(HttpServletRequest request, SopTemplate template,HttpSession session )
	{
		ResponseData<SopTemplate> resp = new ResponseData<SopTemplate>();
		Object obj = session.getAttribute(Constants.SESSION_USER_KEY);
		if(obj == null){
			resp.setResult(new Result(Status.ERROR, "未登录!"));
			return resp;
		}
		try {
			SopTemplate po = null;
			String fileKey = null;
			if(template.getId() != null){
				po = templateService.queryById(template.getId());
				fileKey = po.getFileKey();
			} else {
				po = template;
			}
			if(fileKey == null){
				fileKey = String.valueOf(IdGenerator.generateId(OSSHelper.class));
			}
			
			MultipartFile file = null;
			if(request instanceof MultipartHttpServletRequest){
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				file = multipartRequest.getFile("file");
			}
			if(file != null){
				String fileName = file.getOriginalFilename();
				
				UploadFileResult ut = uploadFileToOSS(request, fileKey, tempfilePath);
				resp.setResult(ut.getResult());
				if(ut.getResult() != null && Result.Status.OK == ut.getResult().getStatus())
				{
					po.setFileName(fileName);
					po.setFileKey(fileKey);
					po.setFileLength(ut.getContentLength());
					po.setUpdateUid(((User)obj).getId());
					po.setUpdateUname(((User)obj).getRealName());
					templateService.updateById(po);
				}
			}
		} catch (Exception e) {
			resp.getResult().addError("上传失败");
			logger.error("上传失败",e);
		}
		return resp;
	}
	
	@RequestMapping("/download")
	@com.galaxyinternet.common.annotation.Logger(operationScope=LogType.LOG)
	public void download(SopTemplate query, HttpServletRequest request, HttpServletResponse response)
	{
		try {
			List<SopTemplate> list = templateService.queryList(query);
			if(list == null || list.size() == 0)
			{
				throw new Exception("未找到模板");
			}
			SopTemplate template = list.iterator().next();
			
			String fileName = template.getFileName();
			int dotPos = fileName.lastIndexOf(".");
			String prefix = fileName.substring(0, dotPos);
			String suffix = fileName.substring(dotPos);
//			File temp = File.createTempFile(prefix, suffix);
			

			
			SopDownLoad downloadEntity = new SopDownLoad();
			downloadEntity.setFileName(prefix);
			downloadEntity.setFileSuffix(suffix);
			downloadEntity.setFileSize(template.getFileLength());
			downloadEntity.setFileKey(template.getFileKey());
			
			sopFileService.download(request, response, tempfilePath, downloadEntity);
			
						
			String worktype = template.getWorktype();
			String projectId = request.getParameter("projectId");
			if(projectId != null && worktype!= null && worktype.indexOf(":")>-1)
			{
				String index = worktype.split(":")[1];
				int num = Integer.valueOf(index);
				UrlNumber[] numbers = UrlNumber.values();
				Long pid = Long.valueOf(projectId);
				Project project = projectService.queryById(pid);
				if(project != null)
				{
					ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId(),numbers[num-1]);
				}
			}
		} catch (Exception e) {
			logger.error("下载失败.",e);
		}
		finally
		{
		}
	}	
	
	
	
	@RequestMapping("/sendMail")
	@ResponseBody
	public Result sendMail(HttpServletRequest request,@RequestBody TemplateMailInfo mailInfo)
	{
		Result rtn = new Result();
		try 
		{
			SopTemplateBo bo = new SopTemplateBo();
			bo.setIds(mailInfo.getTemplateIds());
			
			List<SopTemplate> list = templateService.queryList(bo);
			if(list == null || list.size()==0)
			{
				throw new Exception("未找到文件。");
			}
			
			User curUser = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			String content = MailTemplateUtils.getContentByTemplate(Constants.MAIL_FILESHARE_CONTENT);
			String userName = "大家好：";
			String curTime = DateUtil.convertDateToString(new Date());
			
			String toAddress = mailInfo.getToAddress();
			String[] to = toAddress.split(";");
			
			if(to != null && to.length==1)
			{
				int atIndex = toAddress.lastIndexOf("@");
				userName = toAddress.substring(0, atIndex)+":<br>您好!";
			}
			String endpoint = getCurrEndpoint(request);
			String linkTemplate = "<a href=\"%s\">%s</a><br/>";
			String fileLinks = "";
			for(SopTemplate template : list)
			{
				String fileName = template.getWorkTypeDesc();
				String href = endpoint+"galaxy/openEntry/download/template/"+template.getId();
				fileLinks += String.format(linkTemplate, href,fileName);
			}
			content = PlaceholderConfigurer.formatText(content, userName, curUser.getRealName(), curTime, list.size(),fileLinks);
			boolean success = SimpleMailSender.sendMultiMail(mailInfo.getToAddress(),  "繁星 - 模板分享",content.toString());
			
			if(success)
			{
				rtn.addOK("邮件发送成功.");
			}
			else
			{
				rtn.addError("邮件发送失败。");
			}
			
		} catch (Exception e) {
			logger.error("邮件发送失败。",e);
			rtn.addError("邮件发送失败。");
		}
		return rtn;
	}
	
	
	
	
	
	/**
	 * 本地静态页面路径
	 */
	@RequestMapping(method = RequestMethod.GET,value="/demo")
	public String demo()
	{
		return "test/template_demo";
	}
}
