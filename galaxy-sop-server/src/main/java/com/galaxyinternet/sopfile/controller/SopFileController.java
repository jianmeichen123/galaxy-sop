package com.galaxyinternet.sopfile.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.dictEnum.DictEnum;
import com.galaxyinternet.exception.PlatformException;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.file.UploadFileResult;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.oss.OSSFactory;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.dict.Dict;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DictService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;

@Controller
@RequestMapping("/galaxy/sopFile")
public class SopFileController extends BaseControllerImpl<SopFile, SopFileBo> {

	final Logger logger = LoggerFactory.getLogger(SopFileController.class);
	private static final String ERROR_NO_LOGIN = "not login.";
	private static final String ERR_UPLOAD_ALCLOUD = "上传云端时失败";
	private static final String ERR_UPLOAD_DAO = "上传数据时失败";
	private static final String ERR_UPLOAD_IO = "上传数据流错误";
	
	@Autowired
	private SopFileService sopFileService;
	@Autowired
	private DictService dictService;
	@Autowired
	private ProjectService proJectService;
	@Autowired
	Cache cache;
	
	@Override
	protected BaseService<SopFile> getBaseService() {
		// TODO Auto-generated method stub
		return this.sopFileService;
	}
	
	@ResponseBody
	@RequestMapping(value="/simpleUpload",method=RequestMethod.POST)
	public Result uploadFile(HttpServletRequest request,HttpServletResponse response){
		ResponseData<SopFile> responseBody = new ResponseData<SopFile>();
		Result result = new Result();
		Object obj = request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		User user = (User) obj;
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, ERROR_NO_LOGIN));
		}
		//获取参数
		String fileSource = request.getParameter("fileSource");
		String fileType = request.getParameter("fileType");
		String fileWorkType = request.getParameter("fileWorkType");
		String projectId = request.getParameter("projectId");
		//补充参数校验
		
		//请求转换
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		//文件上传
		//文件唯一key
		String fileKey = String.valueOf(IdGenerator.generateId(OSSHelper.class));
		MultipartFile multipartFile = multipartRequest.getFile("file");

		
		String path = request.getSession().getServletContext().getRealPath("upload");
		//文件名称
		String fileName = multipartFile.getOriginalFilename();
		File tempFile = new File(path,fileName);
		if (!tempFile.exists()) {
			tempFile.mkdirs();
		}
		try{
			//存储临时文件
			multipartFile.transferTo(tempFile);
			//上传至阿里云
			UploadFileResult upResult = OSSHelper.simpleUploadByOSS(tempFile,fileKey);
			
			//若文件上传成功
			if(upResult.getResult().getStatus().equals(Status.OK)){
				SopFile sopFile = new SopFile();
				sopFile.setProjectId(Long.parseLong(projectId));
				sopFile.setFileWorktype(fileWorkType);
				sopFile = sopFileService.selectByProjectAndFileWorkType(sopFile);
				//bucketName
				sopFile.setBucketName(OSSFactory.getDefaultBucketName());
				//fileKey
				sopFile.setFileKey(fileKey);
				//文件大小
				sopFile.setFileLength(tempFile.length());
				//文件名称
				sopFile.setFileName(tempFile.getName());
				//上传人
				sopFile.setFileUid(user.getId());		
				//存储类型
				sopFile.setFileType(fileType);
				//档案来源
				sopFile.setFileSource(fileSource);
//				//业务分类
//				sopFile.setFileWorktype(fileWorkType);
				//档案摘要
				
				//档案状态
				sopFile.setFileStatus(DictEnum.fileStatus.已上传.getCode());
				//将文件信息保存到数据库中
				sopFileService.updateByIdSelective(sopFile);
			}else{
				result.setStatus(Status.ERROR);
				result.addError(ERR_UPLOAD_ALCLOUD);
			}
			result.setStatus(Status.OK);	
		}catch(DaoException e){
			result.addError(ERR_UPLOAD_DAO);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			result.addError(e.getMessage()+ERR_UPLOAD_IO);
		}
		
		return result;
	}
	
	

	@ResponseBody
	@RequestMapping(value = "/getDictByParent/{parentCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Dict> getDictByParent( @PathVariable String parentCode,HttpServletRequest request) {
		ResponseData<Dict> responseBody = new ResponseData<Dict>();
		List<Dict> dicts = null;
		Result result = new Result();
		try {
			dicts = dictService.selectByParentCode(parentCode);	
		}catch(PlatformException e){
			result.setErrorCode(e.getCode()+"");
			result.setMessage(e.getMessage());
		}catch(Exception e){
			result.setMessage("系统错误");
			result.addError("系统错误");
			logger.error("根据parentId查找数据字典错误",e);
		}
		result.setMessage(parentCode);
	    result.setStatus(Status.OK);
	    responseBody.setEntityList(dicts);
		responseBody.setResult(result);
		return responseBody;
	}
	
	/**
	 * 获取项目字典
	 * @param param
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/searchProjectDic/{param}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> searchProjectDic(@PathVariable String param,HttpServletRequest request){
		ResponseData<Project> responseBody = new ResponseData<Project>();
		List<Project> projects = null;
		Result result = new Result();
		try {
			Project project = new Project();
			project.setProjectName(param);
			projects = proJectService.queryList(project);
		}catch(PlatformException e){
			result.setErrorCode(e.getCode()+"");
			result.setMessage(e.getMessage());
		}catch(Exception e){
			result.setMessage("系统错误");
			result.addError("系统错误");
			logger.error("根据parentId查找数据字典错误",e);
		}
//		result.setMessage(parentCode);
	    result.setStatus(Status.OK);
	    responseBody.setEntityList(projects);
		responseBody.setResult(result);
		return responseBody;
	}

	    

	
	
	@RequestMapping(value="/toFileList",method = RequestMethod.GET)
	public String toFileList(){
		return "sopFile/fileList";
	}
	
	@RequestMapping(value="/toUploadFile",method = RequestMethod.GET)
	public String toUploadFile(){
		return "sopFile/uploadFile";
	}

	@Override
	@RequestMapping(value = "/selectList", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData<SopFile> selectList(@RequestBody SopFileBo query) {
		// TODO Auto-generated method stub
		return super.selectList(query);
	}
	
	
}
