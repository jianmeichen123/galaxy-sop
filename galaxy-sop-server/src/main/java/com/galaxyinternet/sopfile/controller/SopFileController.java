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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.dictEnum.DictEnum;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.dao.sopfile.SopVoucherFileDao;
import com.galaxyinternet.exception.PlatformException;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.config.PlaceholderConfigurer;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.file.BucketName;
import com.galaxyinternet.framework.core.file.DownloadFileResult;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.oss.OSSConstant;
import com.galaxyinternet.framework.core.oss.OSSFactory;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.framework.core.utils.mail.MailTemplateUtils;
import com.galaxyinternet.framework.core.utils.mail.SimpleMailSender;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.dict.Dict;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopDownLoad;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.sopfile.SopVoucherFile;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.model.template.TemplateMailInfo;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.DictService;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.SopTaskService;
import com.galaxyinternet.service.SopVoucherFileService;
import com.galaxyinternet.service.UserRoleService;
import com.galaxyinternet.service.UserService;
import com.galaxyinternet.utils.RoleUtils;

@Controller
@RequestMapping("/galaxy/sopFile")
public class SopFileController extends BaseControllerImpl<SopFile, SopFileBo> {

	final Logger logger = LoggerFactory.getLogger(SopFileController.class);
	private static final String ERROR_NO_LOGIN = "not login.";
	private static final String ERR_UPLOAD_ALCLOUD = "上传云端时失败";
	private static final String ERR_UPLOAD_DAO = "上传数据时失败";
	private static final String ERR_UPLOAD_IO = "上传数据流错误";
	private static final String USER_AGENT = "User-Agent";
	
	@Autowired
	private SopFileService sopFileService;
	@Autowired
	private DictService dictService;
	@Autowired
	private ProjectService proJectService;
	@Autowired
	private DepartmentService departMentService;
	@Autowired
	private SopVoucherFileDao sopVoucherFileDao;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private UserService userService;
	@Autowired
	Cache cache;
	
	
	@Autowired
	private SopTaskService sopTaskService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private MeetingRecordService meetingRecordService;
	@Autowired
	private SopVoucherFileService sopVoucherFileService;
	
	private String tempfilePath;
	
	
	public String getTempfilePath() {
		return tempfilePath;
	}

	@Value("${sop.oss.tempfile.path}")
	public void setTempfilePath(String tempfilePath) {
		this.tempfilePath = tempfilePath;
	}

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
		try{
			Map<String,Object> map = sopFileService.aLiColoudUpload(multipartRequest, fileKey, null);
			
//			File tempfile = sopFileService.aLiColoudUpload(multipartRequest, fileKey, null);
			
			//若文件上传成功
			if(map != null){
				Map<String,String> nameMap = (Map<String, String>) map.get("nameMap");
				File tempFile = (File) map.get("file");
				
				
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
											
				sopFile.setFileName(nameMap.get("fileName"));
				sopFile.setFileSuffix(nameMap.get("fileSuffix"));	

//				//文件名称
//				sopFile.setFileName(tempFile.getName());
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
		} catch (Exception e) {
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
			SopFile queryFile=new SopFile();
			queryFile.setProjectId(Long.parseLong(projectId));
			queryFile.setFileWorktype(fileWorkType);
			SopFile sopf=sopFileService.queryOne(queryFile);
			
			if(!sopf.getFileStatus().equals(DictEnum.taskStatus.已完成.getCode())){
				result.addError("未上传投资意向书!");
			}		
//			File tempFile = sopFileService.aLiColoudUpload(multipartRequest, fileKey, null);
			Map<String,Object> map = sopFileService.aLiColoudUpload(multipartRequest, fileKey, null);
			
			//若文件上传成功
			if(map != null){
				Map<String,String> nameMap = (Map<String, String>) map.get("nameMap");
				File tempFile = (File) map.get("file");
				
				//project id 验证
				Project project = new Project();
				project = projectService.queryById(Long.valueOf(projectId));
				String err = errMessage(project,user,DictEnum.projectProgress.投资意向书.getCode());
				if(err!=null && err.length()>0){
					result.addError(err);
				}
				SopVoucherFile bo=new SopVoucherFile();
				bo.setId(sopf.getVoucherId());
				bo.setProjectId(Long.parseLong(projectId));
				bo.setProjectProgress(project.getProjectProgress());
				bo.setFileWorktype(sopf.getFileWorktype());
				bo.setFileType(sopf.getFileType());
				bo.setFileKey(fileKey);
				bo.setFileLength(tempFile.length());
				
				
		
				bo.setFileName(nameMap.get("fileName"));
				bo.setFileSuffix(nameMap.get("fileSuffix"));
				
//				bo.setFileName(tempFile.getName());
				bo.setUpdatedTime(System.currentTimeMillis());
				bo.setFileStatus(DictEnum.fileStatus.已上传.getCode());
				sopVoucherFileService.updateById(bo);
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
		} catch (Exception e) {
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
	 * 获取部门字典
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDepartmentDict/{parentCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Department> getDepartmentDict( @PathVariable String parentCode,HttpServletRequest request) {
		ResponseData<Department> responseBody = new ResponseData<Department>();
		List<Department> departDicts = null;
		Result result = new Result();
		try {
			departDicts = departMentService.queryAll();	
		}catch(PlatformException e){
			result.setErrorCode(e.getCode()+"");
			result.setMessage(e.getMessage());
		}catch(Exception e){
			result.setMessage("系统错误");
			result.addError("系统错误");
			logger.error("根据parentId查找数据字典错误",e);
		}
	    result.setStatus(Status.OK);
	    result.setMessage("department");
	    responseBody.setEntityList(departDicts);
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
		User obj = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if (obj == null) {
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}
		List<Long> roleIdList = userRoleService.selectRoleIdByUserId(obj.getId());
		if("dialog".equals(sopFile.getPageType())){
			sopFile.setFileWorktypeNullFilter("true");
		}else{
			//角色判断(人事)
			if(roleIdList.contains(UserConstant.HRZJ) || roleIdList.contains(UserConstant.HRJL)){
				sopFile.setFileUid(obj.getId());
			//财务
			}else if(roleIdList.contains(UserConstant.FWZJ) || roleIdList.contains(UserConstant.FWJL)){
				sopFile.setFileUid(obj.getId());
			//法务	
			}else if(roleIdList.contains(UserConstant.CWZJ) || roleIdList.contains(UserConstant.CWJL)){
				sopFile.setFileUid(obj.getId());
			//投资经理
			}else if(roleIdList.contains(UserConstant.TZJL)){
				Project project = new Project();
				project.setCreateUid(obj.getId());	
				List<Project> projectList = proJectService.queryList(project); 
				List<Long> projectIdList = new ArrayList<Long>();
				for(Project temp : projectList){
					projectIdList.add(temp.getId());
				}
				sopFile.setProjectIdList(projectIdList);
			//档案管理员	
			}else if(roleIdList.contains(UserConstant.DAGLY)){
			
			//其他人怎么办
			}else{
				
			}
			sopFile.setFileWorktypeNullFilter("true");
			sopFile.setFileStatus(DictEnum.fileStatus.已上传.getCode());	
			//模糊搜索
			if(sopFile.getProjectName()!=null && !sopFile.getProjectName().isEmpty()){
				
				ProjectBo project = new ProjectBo();
				project.setNameOnlyLike(sopFile.getProjectName());
				List<Project> projectList = projectService.queryList(project);
				
				User user = new User();
				user.setRealName(sopFile.getProjectName());
				List<User> userList = userService.queryList(user);
				if((projectList==null || projectList.size()<=0) && (userList==null || userList.size()<=0)){
					Page<SopFile> pageSopFile = new Page<SopFile>(new ArrayList<SopFile>(), new PageRequest(sopFile.getPageNum(), sopFile.getPageSize()), 0l);
					responseBody.setPageList(pageSopFile);
					responseBody.setResult(new Result(Status.OK, ""));
					return responseBody;
				}	
				if(projectList!=null && projectList.size()>0){
					List<Long> projectIdList = new ArrayList<Long>();
					for(Project tempPro : projectList){
						projectIdList.add(tempPro.getId());
					}
					sopFile.setProjectLikeIdList(projectIdList);
				}
				if(userList!=null && userList.size()>0){
					List<Long> fileUidList = new ArrayList<Long>();
					for(User tempUser : userList){
						fileUidList.add(tempUser.getId());
					}
					sopFile.setFileULikeidList(fileUidList);		
				}
				
						
			}
		}
		
		try {
			PageRequest pageRequest = null;
			if ("index".equals(sopFile.getPageType())) {
				//起始页从0开始
				pageRequest = new PageRequest(0,3,Direction.DESC, "updated_time");
			}else{
				pageRequest = new PageRequest(sopFile.getPageNum(), sopFile.getPageSize());
			}
			Page<SopFile> pageSopFile = sopFileService.queryPageList(sopFile,pageRequest);
			//操作权限判断
			for(SopFile temp : pageSopFile.getContent()){
				String isEdit = RoleUtils.getWorkTypeEdit(roleIdList, temp.getFileWorktype());
				temp.setIsEdit(isEdit);			
			}
			responseBody.setPageList(pageSopFile);
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
			for(SopFile f : sopFileList){
				SopVoucherFile sf = sopVoucherFileDao.selectById(f.getVoucherId());
				f.setVoucherFileKey(sf.getFileKey());
			}
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
//		File file = new File(parent, child)
		return resp;
	}
	
	
	@RequestMapping("/downloadFile/{id}")
	public void download(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response)
	{
		String type = request.getParameter("type");
		try {
			SopDownLoad downloadEntity = new SopDownLoad();
			if("voucher".equals(type))
			{
				SopVoucherFile file = sopVoucherFileService.queryById(id);
				if(file == null)
				{
					throw new Exception();
				}
				downloadEntity.setFileName(file.getFileName());
				downloadEntity.setFileSuffix("." + file.getFileSuffix());
				downloadEntity.setFileSize(file.getFileLength());
				downloadEntity.setFileKey(file.getFileKey());
			}
			else
			{
				SopFile file = sopFileService.queryById(id);
				if(file == null)
				{
					throw new Exception();
				}	
				downloadEntity.setFileName(file.getFileName());
				downloadEntity.setFileSuffix("." + file.getFileSuffix());
				downloadEntity.setFileSize(file.getFileLength());
				downloadEntity.setFileKey(file.getFileKey());
			}
			sopFileService.download(request, response, tempfilePath, downloadEntity);
		} catch (Exception e) {
			logger.error("下载失败.",e);
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
		if(task==null){
			responseBody.setResult(new Result(Status.ERROR, null,"任务检索为空"));
			return responseBody;
		}
		
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
		try{
//			File tempFile = sopFileService.aLiColoudUpload(multipartRequest, fileKey, null);
			Map<String,Object> map = sopFileService.aLiColoudUpload(multipartRequest, fileKey, null);
			//若文件上传成功
			if(map != null){
				Map<String,String> nameMap = (Map<String, String>) map.get("nameMap");
				File tempFile = (File) map.get("file");
				
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
									
				sopFile.setFileName(nameMap.get("fileName"));
				sopFile.setFileSuffix(nameMap.get("fileSuffix"));	
				//文件名称
//				sopFile.setFileName(tempFile.getName());			
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return responseBody;
	}
	
	/***
	 * 档案上传通用
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/commonUploadFile",method=RequestMethod.POST)
	public ResponseData<SopFile> commonUploadFile(HttpServletRequest request,HttpServletResponse response){
		ResponseData<SopFile> responseBody = new ResponseData<SopFile>();	
		UrlNumber num = null;
		//1收集参数
		//	(fileSource:非必需入力)
		//2校验参数
		//3上传阿里云
		//4业务控制
		//4.1判断上传是否为签署证明
		//if 非签署证明时：
		//	4.1.1 回填 文件表
		//else签署证明时
		//	4.1.1 上传签署证明文件表
		//	4.1.2 修改当前任务状态
		//		if 所有任务状态均为完成
		//			if 该签署证明已上传
		//				提示异常：签署证明不能上传2便
		//			else 签署证明已上传
		//				4.1.2.2 更新当前project阶段（张峰的方法）
		//				4.1.2.3 生成任务(张峰的方法)			
		//	4.1.3 成功返回当前上传文件信息
		
		//1获取参数
		//文档来源
		String fileSource = request.getParameter("fileSource");
		//文档类型
		String fileType = request.getParameter("fileType");
		//业务分类
		String workType = request.getParameter("fileWorkType");
		//项目ID
		String projectId = request.getParameter("projectId");
		//是否签署证明
		String isProve = request.getParameter("isProve");
		//备注
		String remark = request.getParameter("remark");
		//2校验参数
		//登录校验
		Object obj = request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		User user = (User) obj;
		if(obj == null){
			responseBody.setResult(new Result(Status.ERROR, ERROR_NO_LOGIN));
		}
		String proProgress = "";
		Integer taskFlag = null;
		//业务分类校验 并 初始化任务名称及项目阶段
		if(workType!=null){
			if(workType.equals(DictEnum.fileWorktype.投资意向书.getCode())){  //字典   档案业务类型   投资意向书
				proProgress = DictEnum.projectProgress.投资意向书.getCode() ;									  
				taskFlag = 1;
				num = UrlNumber.one;
			}else if(workType.equals(DictEnum.fileWorktype.业务尽职调查报告.getCode())){
				proProgress = DictEnum.projectProgress.尽职调查.getCode() ;
				taskFlag = 5;
//				num = UrlNumber.five;
			}else if(workType.equals(DictEnum.fileWorktype.投资协议.getCode())){
				proProgress = DictEnum.projectProgress.投资协议.getCode() ; 
				taskFlag = 6;
//				num = UrlNumber.six;
			}else if(workType.equals(DictEnum.fileWorktype.股权转让协议.getCode())){
				proProgress = DictEnum.projectProgress.投资协议.getCode() ; 
				taskFlag = 7;
//				num = UrlNumber.seven;
			}else if(workType.equals(DictEnum.fileWorktype.人力资源尽职调查报告.getCode())){
				proProgress = DictEnum.projectProgress.尽职调查.getCode() ;
				taskFlag = 2;
//				num = UrlNumber.two;
			}else if(workType.equals(DictEnum.fileWorktype.财务尽职调查报告.getCode())){
				proProgress = DictEnum.projectProgress.尽职调查.getCode() ;
				taskFlag = 4;
				num = UrlNumber.four;
			}else if(workType.equals(DictEnum.fileWorktype.法务尽职调查报告.getCode())){
				proProgress = DictEnum.projectProgress.尽职调查.getCode() ;
				taskFlag = 3;
//				num = UrlNumber.three;
			}else if(workType.equals(DictEnum.fileWorktype.工商转让凭证.getCode())){
				proProgress = DictEnum.projectProgress.股权交割.getCode() ;
				taskFlag = 9;
//				num = UrlNumber.nine;
			}else if(workType.equals(DictEnum.fileWorktype.资金拨付凭证.getCode())){
				proProgress = DictEnum.projectProgress.股权交割.getCode() ;
				taskFlag = 8;	
//				num = UrlNumber.eight;
			}else{
				responseBody.setResult(new Result(Status.OK, "文件业务类型不能识别"));
				return responseBody;
			}
		}else{
			responseBody.setResult(new Result(Status.ERROR,null, "文件业务类型为空"));
			return responseBody;
		}
		if(projectId==null){
			responseBody.setResult(new Result(Status.ERROR,null, "项目不能为空"));
			return responseBody;
		}
		//文档类型校验
		//文档来源校验		
		File file = null;
		try{
			// 文件上传
			// 文件唯一key
			String fileKey = String
					.valueOf(IdGenerator.generateId(OSSHelper.class));
			//3 上传阿里云
//			file = sopFileService.aLiColoudUpload(request,fileKey,null);
			Map<String,Object> map = sopFileService.aLiColoudUpload(request,fileKey,null);
			
			//4业务控制 --若文件上传成功-判断是否为签署
			if(map!=null){
				Map<String,String> nameMap = (Map<String, String>) map.get("nameMap");
				file = (File) map.get("file");
				//判断是否为签署凭证
				if("on".equals(isProve)){
					//为签署凭证
					//初始化签署凭证文件表
					SopVoucherFile sopVoucherFile = new SopVoucherFile();
					sopVoucherFile.setProjectId(Long.parseLong(projectId));
					sopVoucherFile.setFileWorktype(workType);
					sopVoucherFile = sopVoucherFileService.queryOne(sopVoucherFile);
					//bucketName
					sopVoucherFile.setBucketName(OSSFactory.getDefaultBucketName());
					//fileKey
					sopVoucherFile.setFileKey(fileKey);
					//文件大小
					sopVoucherFile.setFileLength(file.length());
					
//					if(fileStr.length > 0){
//						//文件名称
//						sopVoucherFile.setFileName(fileStr[0]);
//						//文件后缀
//						sopVoucherFile.setFileSuffix(fileStr[1]);
//					}else{
//						sopVoucherFile.setFileName(file.getOriginalFilename());
//					}	
					
					sopVoucherFile.setFileName(nameMap.get("fileName"));
					sopVoucherFile.setFileSuffix(nameMap.get("fileSuffix"));	
					
					//上传人
					sopVoucherFile.setFileUid(user.getId());		
					//存储类型
					sopVoucherFile.setFileType(fileType);
					//档案来源
					sopVoucherFile.setFileSource(fileSource);
					//档案摘要
					sopVoucherFile.setRemark(remark);
					//档案状态
					sopVoucherFile.setFileStatus(DictEnum.fileStatus.已上传.getCode());		
					//初始化任务表
					SopTask task = new SopTask();
					task.setProjectId(Long.valueOf(projectId));
					task.setTaskFlag(taskFlag);
					task.setAssignUid(user.getId());
					task = sopTaskService.queryOne(task);
					//校验task任务信息
					if(task==null){
						responseBody.setResult(new Result(Status.ERROR, null,"任务检索为空"));
						return responseBody;
					}
					//初始化任务信息
					Project project = new Project();
					project = projectService.queryById(Long.valueOf(projectId));
					//校验项目信息
					if(project.getProjectType()!=null && project.getProjectType().equals(DictEnum.projectType.内部创建.getCode()) && workType.equals(DictEnum.fileWorktype.股权转让协议.getCode())){
						responseBody.setResult(new Result(Status.ERROR,null, "该项目不需要股权转让协议"));
						return responseBody;
					}		
					String err = errMessage(project,user,proProgress);
					if(err!=null && err.length()>0){
						responseBody.setResult(new Result(Status.ERROR,null, err));
						return responseBody;
					}
					//签署凭证上传业务逻辑处理
					sopFileService.updateProve(sopVoucherFile,task,project,user.getId(),user.getDepartmentId());
					//此处结束
									
				}else{
					//非签署凭证
					//初始化sopFile对象
					SopFile sopFile = new SopFile();
					sopFile.setProjectId(Long.parseLong(projectId));
					sopFile.setFileWorktype(workType);
					sopFile = sopFileService.selectByProjectAndFileWorkType(sopFile);
					//bucketName
					sopFile.setBucketName(OSSFactory.getDefaultBucketName());
					//fileKey
					sopFile.setFileKey(fileKey);
					//文件大小
					sopFile.setFileLength(file.length());
							
					sopFile.setFileName(nameMap.get("fileName"));
					sopFile.setFileSuffix(nameMap.get("fileSuffix"));	

					//上传人
					sopFile.setFileUid(user.getId());		
					//存储类型
					sopFile.setFileType(fileType);
					//档案来源
					sopFile.setFileSource(fileSource);
					//档案摘要
					sopFile.setRemark(remark);
					//档案状态
					sopFile.setFileStatus(DictEnum.fileStatus.已上传.getCode());		
					//调用非签署凭证业务方法
					sopFileService.updateByIdSelective(sopFile);
				}		
			}else{
				responseBody.setResult(new Result(Status.ERROR, ERR_UPLOAD_ALCLOUD));
			}
			responseBody.setResult(new Result(Status.OK, null));
			if(num!=null){
				String projectName = proJectService.queryById(Long.parseLong(projectId)).getProjectName();
				ControllerUtils.setRequestParamsForMessageTip(request, projectName, Long.parseLong(projectId),num);
			}
		}catch(DaoException e){
			responseBody.setResult(new Result(Status.ERROR, ERR_UPLOAD_DAO));
		}catch(IOException e){
			responseBody.setResult(new Result(Status.ERROR, e.getMessage()+ERR_UPLOAD_IO));
		}catch(Exception e){
			responseBody.setResult(new Result(Status.ERROR, e.getMessage()+ERR_UPLOAD_IO));
		}finally{
			if(file!=null){
				file.delete();
			}	
		}
		
		return responseBody;
	}
	
	private Map<String, String> transFileNames(String fileName) {
		Map<String, String> retMap = new HashMap<String, String>();
		int dotPos = fileName.lastIndexOf(".");
		retMap.put("fileName", fileName.substring(0, dotPos));
		retMap.put("fileSuffix", fileName.substring(dotPos+1));
		return retMap;
	}
	
	
	
	/**
	 * 项目业务逻辑校验
	 * @param project
	 * @param user
	 * @param prograss
	 * @return
	 */
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
	
	@ResponseBody
	@RequestMapping(value = "/checkShow", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopFile> checkShow(HttpServletRequest request,@RequestBody(required=false) SopFile query)
	{
		ResponseData<SopFile> resp = new ResponseData<SopFile>();
		try {
			Object obj = request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			User user = (User) obj;
			if(user == null){
				resp.setResult(new Result(Status.ERROR, ERROR_NO_LOGIN));
			}
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
			boolean result = RoleUtils.isDAGLY(roleIdList);
			if(result){
				resp.setResult(new Result(Status.OK, ""));
			}else{
				resp.setResult(new Result(Status.ERROR, ""));
			}
			
		} catch (Exception e) {
			Object msg = "查询失败.";
			resp.getResult().addError(msg);
			logger.error(msg.toString(),e);
		}
		return resp;
	}
	@RequestMapping("/showMailDialog")
	public ModelAndView showMailDialog()
	{
		ModelAndView mv = new ModelAndView("/sopFile/mailDialog");
	
		return mv;
	}
	
	@RequestMapping("/sendMail")
	@ResponseBody
	public Result sendMail(HttpServletRequest request,@RequestBody TemplateMailInfo mailInfo)
	{
		Result rtn = new Result();
		try 
		{
			SopFileBo bo = new SopFileBo();
			if(mailInfo.getTemplateIds() != null && mailInfo.getTemplateIds().length>0)
			{
				bo.setIds(Arrays.asList(mailInfo.getTemplateIds()));
			}
			
			List<SopFile> list = sopFileService.queryList(bo);
			
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
			for(SopFile sopFile : list)
			{
				String fileName = sopFile.getfWorktype();
				String href = endpoint+"galaxy/openEntry/download/file/"+sopFile.getId();
				fileLinks += String.format(linkTemplate, href,fileName);
			}
			content = PlaceholderConfigurer.formatText(content, userName, curUser.getRealName(), curTime, list.size(), fileLinks);
			boolean success = SimpleMailSender.sendMultiMail(mailInfo.getToAddress(),  "繁星 - 档案分享",content.toString());
			
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
	
}
