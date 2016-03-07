package com.galaxyinternet.template.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

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
	
}
