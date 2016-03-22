package com.galaxyinternet.template.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.galaxyinternet.bo.template.SopTemplateBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.mail.SimpleMailSender;
import com.galaxyinternet.model.role.Role;
import com.galaxyinternet.model.template.SopTemplate;
import com.galaxyinternet.model.template.TemplateMailInfo;
import com.galaxyinternet.model.user.User;
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
				if(UserConstant.TZJL == role.getId())
				{
					typesStr = "fileWorktype:1,fileWorktype:2,fileWorktype:3,fileWorktype:4,fileWorktype:5,fileWorktype:6,fileWorktype:7";
					editableTypes = "fileWorktype:1";
				}
				else if(UserConstant.HRJL == role.getId() || UserConstant.HRJL == role.getId() )
				{
					typesStr = "fileWorktype:2";
					editableTypes = "fileWorktype:2";
				}
				else if(UserConstant.CWJL == role.getId() || UserConstant.CWJL == role.getId() )
				{
					typesStr = "fileWorktype:4";
					editableTypes = "fileWorktype:4";
				}
				else if(UserConstant.FWJL == role.getId() || UserConstant.FWJL == role.getId() )
				{
					typesStr = "fileWorktype:3,fileWorktype:5,fileWorktype:6,fileWorktype:7";
					editableTypes = "fileWorktype:3,fileWorktype:5,fileWorktype:6,fileWorktype:7";
				}
				
				if(typesStr != null && typesStr.length()>0)
				{
					types = Arrays.asList(typesStr.split(","));
					SopTemplateBo query = new SopTemplateBo();
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
	public Map<String,Object> upload(@RequestParam MultipartFile file)
	{
		Map<String,Object> rtn = new HashMap<String,Object> ();
		rtn.put("fileKey", "");
		rtn.put("fileLength", "0");
		String key = null;
		 try {
			if(file != null)
			 {
				String fileName = file.getOriginalFilename();
				int dotPos = fileName.lastIndexOf(".");
				key = String.valueOf(IdGenerator.generateId(OSSHelper.class));
				String ext = fileName.substring(dotPos);
				File temp = File.createTempFile(key, ext);
				file.transferTo(temp);
				OSSHelper.simpleUploadByOSS(temp,key);
				rtn.put("fileKey", key);
				rtn.put("fileLength", temp.length());
			 }
		} catch (Exception e) {
			Object msg = "上传失败！";
			logger.error(msg.toString(),e);
		}
		return rtn;
	}
	@RequestMapping("/save")
	@ResponseBody
	public ResponseData<SopTemplate> save(@RequestBody SopTemplate template,HttpSession session)
	{
		ResponseData<SopTemplate> resp = new ResponseData<SopTemplate>();
		Object obj = session.getAttribute(Constants.SESSION_USER_KEY);
		if(obj == null)
		{
			resp.setResult(new Result(Status.ERROR, "未登录!"));
			return resp;
		}
		try {
			template.setUpdateUid(((User)obj).getId());
			template.setUpdateUname(((User)obj).getRealName());
			templateService.updateById(template);
		} catch (Exception e) {
			Object msg = "保存失败";
			logger.error(msg.toString(),e);
			resp.getResult().addError(msg);
		}
		
		return resp;
	}
	@RequestMapping("/download")
	public void download(SopTemplate query, HttpServletRequest request, HttpServletResponse response)
	{
		InputStream fis = null;
		OutputStream out = null;
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
			File temp = File.createTempFile(prefix, suffix);
			
			OSSHelper.simpleDownloadByOSS(temp, template.getFileKey());
			String dlName = fileName;
			
			if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {  
				dlName = URLEncoder.encode(fileName, "UTF-8");  
			} else {  
				dlName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");  
			} 
			
			response.reset();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-download");
			response.setHeader("Content-Disposition", "attachment;filename=" + dlName);
			response.setHeader("Content-Length", "" + temp.length());
			out = new BufferedOutputStream(response.getOutputStream());
			fis = new BufferedInputStream(new FileInputStream(temp.getPath()));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			out.write(buffer);
			out.flush();
			response.flushBuffer();
		} catch (Exception e) {
			logger.error("下载失败.",e);
		}
		finally
		{
			try {
				if(fis != null)
				{
					fis.close();
				}
				if(out != null)
				{
					out.close();
				}
			} catch (IOException e) {
				logger.error("下载失败.",e);
			}
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
			/*List<String> fileList = new ArrayList<String>();
			for(SopTemplate template : list)
			{
				String fileName = template.getBucketName();
				int dotPos = fileName.lastIndexOf(".");
				String prefix = fileName.substring(0, dotPos);
				String suffix = fileName.substring(dotPos);
				File temp = File.createTempFile(prefix, suffix);
				OSSHelper.simpleDownloadByOSS(temp, template.getFileKey());
				fileList.add(temp.getAbsolutePath());
			}
			*/
			if(StringUtils.isNotEmpty(mailInfo.getZipFlag()))
			{
				//TODO generate zip file
			}
			
			StringBuffer content = new StringBuffer();
			content.append(mailInfo.getContent());
			content.append("<br/>");
			content.append("邮件附件:<br/>");
			
			for(SopTemplate template : list)
			{
				String fileName = template.getBucketName();
				content.append("<a href=\"").append(request.getContextPath()).append("/galaxy/commondl").append("\">").append(fileName).append("</a>");
			}
			boolean success = SimpleMailSender.sendHtmlMail(mailInfo.getToAddress(),  mailInfo.getTitle(),"");
			
			if(success)
			{
				if(StringUtils.isNotEmpty(mailInfo.getSmFlag()))
				{
					//TODO generate SM notify
				}
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
	
}
