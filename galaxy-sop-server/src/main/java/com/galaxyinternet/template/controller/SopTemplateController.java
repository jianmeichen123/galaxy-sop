package com.galaxyinternet.template.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.galaxyinternet.bo.template.SopTemplateBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.template.SopTemplate;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.SopTemplateService;
@Controller
@RequestMapping("/galaxy/template")
public class SopTemplateController extends BaseControllerImpl<SopTemplate, SopTemplateBo> {
	final Logger logger = LoggerFactory.getLogger(SopTemplateController.class);
	@Autowired
	private SopTemplateService templateService;
	
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
	public ResponseData<SopTemplate> queryTemplate()
	{
		ResponseData<SopTemplate> rtn = new ResponseData<SopTemplate>();
		try {
			List<SopTemplate> list = templateService.queryAll();
			rtn.setEntityList(list);
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
	public String upload(@RequestParam MultipartFile file)
	{
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
				
			 }
		} catch (Exception e) {
			Object msg = "上传失败！";
			logger.error(msg.toString(),e);
		}
		return key;
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
			templateService.updateById(template);
		} catch (Exception e) {
			Object msg = "保存失败";
			logger.error(msg.toString(),e);
			resp.getResult().addError(msg);
		}
		
		return resp;
	}
	@RequestMapping("/download/{id}")
	public void download(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response)
	{
		InputStream fis = null;
		OutputStream out = null;
		try {
			SopTemplate template = templateService.queryById(id);
			if(template == null)
			{
				throw new Exception();
			}
			String fileName = template.getBucketName();
			int dotPos = fileName.lastIndexOf(".");
			String[] part = fileName.split(".");
			String prefix = fileName.substring(0, dotPos);
			String suffix = fileName.substring(dotPos);
			File temp = File.createTempFile(prefix, suffix);
			
			OSSHelper.simpleDownloadByOSS(temp, template.getFileKey());
			
			response.reset();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-download");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(template.getBucketName().getBytes("UTF-8"),"UTF-8"));
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
	
}
