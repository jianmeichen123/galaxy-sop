package com.galaxyinternet.sopfile.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.file.UploadFileResult;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.service.SopFileService;

@Controller
@RequestMapping("/galaxy/sopFile")
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
	
	@RequestMapping(value="/simpleUpload",method=RequestMethod.POST)
	public void uploadFile(HttpServletRequest request){
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
//		CommonsMultipartFile cf= (CommonsMultipartFile)multipartFile; 
//        DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
//        File file = fi.getStoreLocation();
		
		String path = request.getSession().getServletContext().getRealPath("upload");
		String fileName = multipartFile.getOriginalFilename();
		File tempFile = new File(path,fileName);
		if (!tempFile.exists()) {
			tempFile.mkdirs();
		}

		
        
		try{
			multipartFile.transferTo(tempFile);
			UploadFileResult upResult = OSSHelper.simpleUploadByOSS(tempFile,key);
			result.setStatus(Status.OK);
		}catch(Exception e){
			result.addError(ERROR_COMMON);
		}
		responseBody.setResult(result);
	}
	

	    

	
	
	@RequestMapping(value="/toFileList",method = RequestMethod.GET)
	public String toFileList(){
		return "sopFile/fileList";
	}
	
	@RequestMapping(value="/toUploadFile",method = RequestMethod.GET)
	public String toUploadFile(){
		return "sopFile/uploadFile";
	}
	

}
