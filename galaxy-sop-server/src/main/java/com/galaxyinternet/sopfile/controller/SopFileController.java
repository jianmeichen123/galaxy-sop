package com.galaxyinternet.sopfile.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.service.SopFileService;

@Controller
@RequestMapping("/galaxy/sopfile")
public class SopFileController extends BaseControllerImpl<SopFile, SopFileBo> {

	private static final String ERROR_NO_LOGIN = "not login.";
	private static final String ERROR_COMMON = "error,";
	
	@Autowired
	private SopFileService sopFileService;
	@Autowired
	Cache cache;
	
	@Override
	protected BaseService<SopFile> getBaseService() {
		// TODO Auto-generated method stub
		return this.sopFileService;
	}
	@ResponseBody
	@RequestMapping(value="/simpleUpload")
	public ResponseData<SopFile> uploadFile(@RequestBody SopFile entity,HttpServletRequest request){
		ResponseData<SopFile> responseBody = new ResponseData<SopFile>();
		Result result = new Result();
		Object obj = request.getSession().getAttribute("session");
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, ERROR_NO_LOGIN));
		}
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		
		//文件上传
		String key = String.valueOf(IdGenerator.generateId(OSSHelper.class));
		MultipartFile multipartFile = multipartRequest.getFile("file");
		
		try{
			File file = (File)multipartFile;
			OSSHelper.simpleUploadByOSS(file,key);
			result.setStatus(Status.OK);
		}catch(Exception e){
			result.addError(ERROR_COMMON);
		}
		responseBody.setResult(result);
		return responseBody;
	}

}
