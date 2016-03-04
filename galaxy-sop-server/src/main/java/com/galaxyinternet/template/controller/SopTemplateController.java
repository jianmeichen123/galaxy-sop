package com.galaxyinternet.template.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.galaxyinternet.bo.template.SopTemplateBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.template.SopTemplate;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.DictService;
import com.galaxyinternet.service.SopTemplateService;
@Controller
@RequestMapping("/galaxy/template")
public class SopTemplateController extends BaseControllerImpl<SopTemplate, SopTemplateBo> {
	final Logger logger = LoggerFactory.getLogger(SopTemplateController.class);
	@Autowired
	private SopTemplateService templateService;
	@Autowired
	private DictService dictService;
	@Autowired
	private DepartmentService deptService;
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
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
	public void upload(HttpServletRequest request, HttpServletResponse response)
	{
		
		
	}
	
}
