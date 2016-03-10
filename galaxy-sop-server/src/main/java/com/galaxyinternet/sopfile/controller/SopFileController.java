package com.galaxyinternet.sopfile.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
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
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.oss.OSSFactory;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.dict.Dict;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DictService;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.SopTaskService;

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
	
	@Autowired
	private SopTaskService sopTaskService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private MeetingRecordService meetingRecordService;
	
	@Override
	protected BaseService<SopFile> getBaseService() {
		// TODO Auto-generated method stub
		return this.sopFileService;
	}
	
	@ResponseBody
	@RequestMapping(value="/simpleUpload",method=RequestMethod.POST)
	public Result uploadFile(HttpServletRequest request,HttpServletResponse response){
		ResponseData<SopFile> responseBody = new ResponseData<SopFile>();
		//BTO
		SopFile sopFile = null;
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
				sopFile = new SopFile();
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
			result.setMessage(sopFile.getProjectId());
		}catch(DaoException e){
			result.addError(ERR_UPLOAD_DAO);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			result.addError(e.getMessage()+ERR_UPLOAD_IO);
		}
		
		return result;
	}
	
	
	/**
	 * 非流程类上传文件-签署证明
	 * @param request
	 * @param response
	 * @param progress
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/commonUpload",method=RequestMethod.POST)
	public Result uploadCommonFile(HttpServletRequest request,HttpServletResponse response){
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
			
			//上传  投资意向书  任务验证已完成
			SopTask task = new SopTask();
			task.setProjectId(Long.valueOf(projectId));
			task.setTaskName("上传投资意向书");          //任务名称：    上传投资意向书
			task.setAssignUid(user.getId());
			task.setTaskStatus(DictEnum.taskStatus.已完成.getCode());
			task = sopTaskService.queryOne(task);
			if(task.getTaskStatus()==null || !task.getTaskStatus().equals(DictEnum.taskStatus.已完成.getCode())){
				result.addError("Front task is not complete");
			}
			//存储临时文件
			multipartFile.transferTo(tempFile);
			//上传至阿里云
			UploadFileResult upResult = OSSHelper.simpleUploadByOSS(tempFile,fileKey);
			
			//若文件上传成功
			if(upResult.getResult().getStatus().equals(Status.OK)){
				SopFile sopFile = new SopFile();
				sopFile.setProjectId(Long.parseLong(projectId));
				sopFile.setFileWorktype(fileWorkType);
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
				sopFile.setProjectProgress("签署证明");
				sopFile.setFileStatus(DictEnum.fileStatus.已上传.getCode());
				//将文件信息保存到数据库中
				sopFileService.insert(sopFile);
				
				//project id 验证
				Project project = new Project();
				project = projectService.queryById(Long.valueOf(projectId));
				
				String err = errMessage(project,user,DictEnum.projectProgress.投资意向书.getCode());
				if(err!=null && err.length()>0){
					result.addError(err);
				}
				//修改项目进度、生成任务
				meetingRecordService.upTermSheetSign(project,user.getId(),user.getDepartmentId());
			}		
			result.setStatus(Status.OK);	
		}catch(DaoException e){
			e.printStackTrace();
			result.addError(ERR_UPLOAD_DAO);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	/**
	 * 获取档案列表(分页查询)
	 * 
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchSopFileList", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopFile> searchSopFileList(HttpServletRequest request, @RequestBody SopFile sopFile) {
		ResponseData<SopFile> responseBody = new ResponseData<SopFile>();
		Object obj = request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if (obj == null) {
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}
		try {
			Page<SopFile> pageSopFile = sopFileService.queryPageList(sopFile,new PageRequest(sopFile.getPageNum(), sopFile.getPageSize()));
			responseBody.setPageList(pageSopFile);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
		} catch (PlatformException e) {
			responseBody.setResult(new Result(Status.ERROR, "queryUserList faild"));
			if (logger.isErrorEnabled()) {
				logger.error("queryUserList ", e);
			}
		}
		return responseBody;
	}

	/**
	 * 获取档案列表
	 * 
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchSopFileListWithoutPage", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopFile> searchSopFileListWithoutPage(HttpServletRequest request, @RequestBody SopFile sopFile) {
		ResponseData<SopFile> responseBody = new ResponseData<SopFile>();
		Object obj = request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if (obj == null) {
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}
		SopFileBo sbo = new SopFileBo();
		List<String> fileWorktypeList = new ArrayList<String>();
		fileWorktypeList.add(DictEnum.fileWorktype.投资协议.getCode());   //字典   投资协议
		fileWorktypeList.add(DictEnum.fileWorktype.股权转让协议.getCode());
		sbo.setProjectId(sopFile.getProjectId());
		sbo.setFileworktypeList(fileWorktypeList);
		try {
			List<SopFile> sopFileList = sopFileService.selectByFileTypeList(sbo);
			responseBody.setEntityList(sopFileList);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
		} catch (DaoException e) {
			responseBody.setResult(new Result(Status.ERROR, "queryUserList faild"));
			if (logger.isErrorEnabled()) {
				logger.error("queryUserList ", e);
			}
		}
		return responseBody;
	}
	    

	
	
	/**
	 * 档案管理模块入口
	 * @return
	 */
	@RequestMapping(value="/toFileList",method = RequestMethod.GET)
	public String toFileList(){
		return "sopFile/fileList";
	}
	
	
	@RequestMapping(value="/toUploadFile",method = RequestMethod.GET)
	public String toUploadFile(){
		return "sopFile/uploadFile";
	}

	@RequestMapping(value = "/query", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData<SopFile> query(@RequestBody SopFileBo query) {
		ResponseData<SopFile> resp = new ResponseData<SopFile>();
		try {
			List<SopFile> entityList = sopFileService.queryList(query);
			resp.setEntityList(entityList);
		} catch (Exception e) {
			Object msg = "查询出错.";
			logger.error(msg.toString());
			resp.getResult().addError(msg);
		}
		return resp;
	}
	
	@RequestMapping("/downloadFile/{id}")
	public void download(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response)
	{
		InputStream fis = null;
		OutputStream out = null;
		try {
			SopFile file = sopFileService.queryById(id);
			if(file == null)
			{
				throw new Exception();
			}
			String fileName = file.getFileName();
			int dotPos = fileName.lastIndexOf(".");
			String prefix = fileName.substring(0, dotPos);
			String suffix = fileName.substring(dotPos);
			File temp = File.createTempFile(prefix, suffix);
			
			OSSHelper.simpleDownloadByOSS(temp, file.getFileKey());
			
			if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {  
				fileName = URLEncoder.encode(fileName, "UTF-8");  
			} else {  
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");  
			} 
			response.reset();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-download");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
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
	
	/***
	 * 投资意向书
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/projectFileUpload",method=RequestMethod.POST)
	public ResponseData<SopFile> projectFileUpload(HttpServletRequest request,HttpServletResponse response){
		ResponseData<SopFile> responseBody = new ResponseData<SopFile>();
		
		String proProgress = "";
		String taskName = "";
		Object obj = request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		User user = (User) obj;
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, ERROR_NO_LOGIN));
		}
		//获取参数
		String fileSource = request.getParameter("fileSource");
		String fileType = request.getParameter("fileType");
		String workType = request.getParameter("fileWorkType");
		String projectId = request.getParameter("projectId");
		
		if(workType!=null){
			if(workType.equals(DictEnum.fileWorktype.投资意向书.getCode())){  //字典   档案业务类型   投资意向书
				proProgress = DictEnum.projectProgress.投资意向书.getCode() ;									  
				taskName = "上传投资意向书";
				
			}else if(workType.equals(DictEnum.fileWorktype.业务尽职调查报告.getCode())){
				proProgress = DictEnum.projectProgress.尽职调查.getCode() ;
				taskName = "上传业务尽职调查报告";
				
			}else if(workType.equals(DictEnum.fileWorktype.投资协议.getCode())){
				proProgress = DictEnum.projectProgress.投资协议.getCode() ; 
				taskName = "上传投资协议";
				
			}else if(workType.equals(DictEnum.fileWorktype.股权转让协议.getCode())){
				proProgress = DictEnum.projectProgress.投资协议.getCode() ; 
				taskName = "上传股权转让协议";
				
			}else if(workType.equals(DictEnum.fileWorktype.人力资源尽职调查报告.getCode())){
				proProgress = DictEnum.projectProgress.尽职调查.getCode() ;
				taskName = "上传人力资源尽职调查报告";
				
			}else if(workType.equals(DictEnum.fileWorktype.财务尽职调查报告.getCode())){
				proProgress = DictEnum.projectProgress.尽职调查.getCode() ;
				taskName = "上传财务尽职调查报告";
				
			}else if(workType.equals(DictEnum.fileWorktype.法务尽职调查报告.getCode())){
				proProgress = DictEnum.projectProgress.尽职调查.getCode() ;
				taskName = "上传法务尽职调查报告";
				
			}else if(workType.equals(DictEnum.fileWorktype.工商转让凭证.getCode())){
				proProgress = DictEnum.projectProgress.股权交割.getCode() ;
				taskName = "上传工商转让凭证";
				
			}else if(workType.equals(DictEnum.fileWorktype.资金拨付凭证.getCode())){
				proProgress = DictEnum.projectProgress.股权交割.getCode() ;
				taskName = "上传资金拨付凭证";
				
			}else{
				responseBody.setResult(new Result(Status.OK, "文件业务类型不能识别"));
				return responseBody;
			}
		}else{
			responseBody.setResult(new Result(Status.ERROR,null, "文件业务类型为空"));
			return responseBody;
		}
		
		SopTask task = new SopTask();
		task.setProjectId(Long.valueOf(projectId));
		task.setTaskName(taskName);
		task.setAssignUid(user.getId());
		task = sopTaskService.queryOne(task);
		
		//project id 验证
		Project project = new Project();
		project = projectService.queryById(Long.valueOf(projectId));
		
		//项目为 内部创建，不需要  股权转让协议
		if(project.getProjectType()!=null && project.getProjectType().equals(DictEnum.projectType.内部创建.getCode()) && workType.equals(DictEnum.fileWorktype.股权转让协议.getCode())){
			responseBody.setResult(new Result(Status.ERROR,null, "该项目不需要股权转让协议"));
			return responseBody;
		}
				
		String err = errMessage(project,user,proProgress);
		if(err!=null && err.length()>0){
			responseBody.setResult(new Result(Status.ERROR,null, err));
			return responseBody;
		}

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
				sopFile.setFileWorktype(workType);
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
		
				if(task==null){
					responseBody.setResult(new Result(Status.ERROR, null,"任务检索为空"));
					return responseBody;
				}
				//修改任务状态完成
				task.setTaskStatus(DictEnum.taskStatus.已完成.getCode());
				sopTaskService.updateById(task);
			}else{
				responseBody.setResult(new Result(Status.ERROR, ERR_UPLOAD_ALCLOUD));
			}
			responseBody.setResult(new Result(Status.OK, null));
		}catch(DaoException e){
			responseBody.setResult(new Result(Status.ERROR, ERR_UPLOAD_DAO));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			responseBody.setResult(new Result(Status.ERROR, e.getMessage()+ERR_UPLOAD_IO));
		}
		
		return responseBody;
	}
	
	public String errMessage(Project project,User user,String prograss){
		if(project == null){
			return "项目检索为空";
		}else if(project.getProjectStatus().equals(DictEnum.meetingResult.否决.getCode())){ //字典 项目状态 = 会议结论 关闭
			return "项目已经关闭";
		}
		
		if(user != null){
			if(project.getCreateUid()==null || user.getId().longValue()!=project.getCreateUid().longValue()){ 
				return "不允许操作他人项目";
			}
		}
		if(prograss != null){
			if(project.getProjectProgress()==null || !project.getProjectProgress().equals(prograss) ){
				return "项目当前阶段不允许进行该操作";
			}
		}
		return null;
	}
	@ResponseBody
	@RequestMapping(value = "/selectProjectFiles", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopFile> selectProjectFiles(@RequestBody SopFileBo query)
	{
		ResponseData<SopFile> resp = new ResponseData<SopFile>();
		try {
			List<SopFile> entityList = sopFileService.selectByFileTypeList(query);
			resp.setEntityList(entityList);
		} catch (Exception e) {
			Object msg = "查询失败.";
			resp.getResult().addError(msg);
			logger.error(msg.toString(),e);
		}
		return resp;
	}
	
	
}
