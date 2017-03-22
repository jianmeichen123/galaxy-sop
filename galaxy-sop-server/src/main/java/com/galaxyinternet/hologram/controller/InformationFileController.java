package com.galaxyinternet.hologram.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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

import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.file.FileResult;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.file.UploadFileResult;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.oss.OSSConstant;
import com.galaxyinternet.framework.core.oss.OSSFactory;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationFile;
import com.galaxyinternet.model.touhou.Delivery;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.hologram.InformationFileService;
import com.galaxyinternet.utils.BatchUploadFile;
import com.galaxyinternet.utils.FileUtils;

@Controller
@RequestMapping("/galaxy/informationFile")
public class InformationFileController extends BaseControllerImpl<InformationFile, InformationFile>{
	
	final Logger logger = LoggerFactory.getLogger(InformationFileController.class);
	@Autowired
	Cache cache;
	
	@Autowired
	InformationFileService informationFileService;

	@Autowired
	BatchUploadFile batchUpload;
	
	public static final ExecutorService threadpool =  Executors.newFixedThreadPool(10);
	
	private String tempfilePath;
	
	public String getTempfilePath() {
		return tempfilePath;
	}

	@Value("${sop.oss.tempfile.path}")
	public void setTempfilePath(String tempfilePath) {
		this.tempfilePath = tempfilePath;
	}
	
	@Override
	protected BaseService<InformationFile> getBaseService() {
		// TODO Auto-generated method stub
		return this.informationFileService;
	}
	
	
	/**
	 * 删除文件
	 * @param informationFile
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteRedisFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)  
    public Result deleteRedisFile(@RequestBody InformationFile informationFile,HttpServletRequest request){
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		try {
			String fileKey = informationFile.getFileReidsKey();
			String fileId = informationFile.getNewFileName();
			if(StringUtils.isEmpty(fileKey) || StringUtils.isEmpty(fileId)){
				return new Result(Status.ERROR, "参数丢失!");
			}
			String redisKey = user.getId()+fileKey;
			List<Object> fileList = cache.getRedisQuenOBJ(redisKey);
			List<Object> delFileList = new ArrayList<Object>();
			if(fileList != null && fileList.size() > 0){
				for(Object file : fileList){
			    	 InformationFile fileObject = (InformationFile) file;
			    	 if(fileId.equals(fileObject.getNewFileName())){
			    		 delFileList.add(file);
			    	 }
			    }
				fileList.removeAll(delFileList);
				cache.removeRedisKeyOBJ(redisKey);
				cache.set(redisKey,fileList);
			}
			return new Result(Status.OK, "");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除文件操作失败!");
			// TODO: handle exception
			return new Result(Status.ERROR, "系统出现异常");
		}
		
	}
	
	
	/**
	 * 将文件上传到redis中
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendInformationByRedis", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)  
    public Result batchUpload(HttpServletRequest request){
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; // 请求转换
			MultipartFile multipartFile = multipartRequest.getFile("file"); // 获取multipartFile文件
			InformationFile form = new InformationFile();
			form.setBucketName(OSSFactory.getDefaultBucketName());
			form.setFileKey(String
						.valueOf(IdGenerator.generateId(OSSHelper.class)));
			Map<String, String> nameMap = FileUtils.transFileNames(multipartFile.getOriginalFilename());
			String fileKey = request.getParameter("fileReidsKey");
			String newFileName = request.getParameter("newFileName");
			String projectId = request.getParameter("projectId") == null ? "" :request.getParameter("projectId");
			String titleId = request.getParameter("titleId") == null ? "":request.getParameter("titleId");
			
			if(StringUtils.isEmpty(projectId) || StringUtils.isEmpty(fileKey) || StringUtils.isEmpty(titleId)){
				return new Result(Status.ERROR, "上传redis失败!");
			}
			form.setFileName(request.getParameter("fileName").substring(0, request.getParameter("fileName").lastIndexOf(".")));
			form.setFileSuffix(nameMap.get("fileSuffix"));
			form.setFileType(FileUtils.getFileType(form.getFileSuffix()));
			form.setFileLength(String.valueOf(multipartFile.getSize()));
			form.setCreateId(user.getId());
			form.setCreatedTime(System.currentTimeMillis());
			form.setMultipartFile(multipartFile);
			form.setProjectId(Long.valueOf(projectId));
			form.setTitleId(Long.valueOf(titleId));
			form.setNewFileName(newFileName);
			form.setTempPath(tempfilePath);
			cache.setRedisSetOBJ(user.getId()+fileKey,form);
			return new Result(Status.OK, "");
		} catch (Exception e) {
			cache.removeRedisKeyOBJ(user.getId()+request.getParameter("fileReidsKey"));
			// TODO: handle exception
			return new Result(Status.ERROR, "系统出现异常");
		}
		
	}
	
	/**
	 * 全息图上传文件
	 */
	@ResponseBody
	@RequestMapping(value = "/operInformationFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Delivery> operDelivery(@RequestBody InformationFile informationFile,HttpServletRequest request) {
		
		ResponseData<Delivery> responseBody = new ResponseData<Delivery>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
		if(informationFile == null || informationFile.getProjectId() == null){
			responseBody.setResult(new Result(Status.ERROR,null, "请完善信息"));
			return responseBody;
		}
		try {
			//如有历史上传文件进行删除
			String deleteids = informationFile.getDeleteids();
			if(StringUtils.isNotEmpty(deleteids) && deleteids.contains(",")){
				String [] fileids = deleteids.split(",");
				for(int i=0;i < fileids.length; i++){
					if(StringUtils.isNotEmpty(fileids[i])){
						//删除历史文件
						InformationFile file = informationFileService.queryById(Long.valueOf(fileids[i]));
						if(file != null){
							 FileResult result = OSSHelper.deleteFile(file.getBucketName(), file.getFileKey());
							 if(Status.OK.equals(result.getResult().getStatus())){
								 informationFileService.deleteById(Long.valueOf(fileids[i]));
								 logger.info("删除文件名:{},项目id:{}",file.getFileName(),file.getProjectId());
							 }
						}
					}
				}
				
			}
			//进行上传文件
			if(informationFile.getFileReidsKey() != null){
				    final String redisKey = user.getId()+informationFile.getFileReidsKey();
					final List<Object> fileList = cache.getRedisQuenOBJ(redisKey);
					final CountDownLatch startSignal = new CountDownLatch(fileList.size());
					if(fileList != null && fileList.size() > 0){     
					    for(Object file : fileList){
					    	final InformationFile fileObject = (InformationFile) file;
							// 开始一个线程
							Thread process = new Thread(new Runnable() {
								public void run() {
									 UploadFileResult res = uploadFileToOSS(fileObject, fileObject.getFileKey());
								     if(Status.ERROR.equals(res.getResult().getStatus())){
								    	 cache.removeRedisKeyOBJ(redisKey);
								    	 logger.error("文件上次失败！文件名{}",fileObject.getFileName());
								     }else{
								    	 String url = OSSHelper.getUrl(fileObject.getBucketName(),fileObject.getFileKey());
								    	 fileObject.setFileUrl(url);
								    	 informationFileService.insert(fileObject);
								     }
									startSignal.countDown();
								}
							});
							threadpool.execute(process);
					    }
					    startSignal.await();
					}
			}
			responseBody.setResult(new Result(Status.OK,null));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "操作失败"));
			logger.error("operInformationFile 操作失败",e);
		}
		
		return responseBody;
	}
	
	/**
	 * 根据项目ids来获取全息图上传的图片
	 */
	@ResponseBody
	@RequestMapping(value = "/getFileByProjectByType", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InformationFile> getFileByProjectByType(@RequestBody InformationFile informationFile,HttpServletRequest request) {
		
		ResponseData<InformationFile> responseBody = new ResponseData<InformationFile>();
		if(informationFile.getProjectId() == null){
			responseBody.setResult(new Result(Status.ERROR,null, "项目ID为空!"));
			return responseBody;
		}
		try {
			InformationFile fileslist = new InformationFile();
			String infoFileids = informationFile.getInfoFileids();
			if(StringUtils.isNotEmpty(infoFileids) && infoFileids.contains(",")){
				String [] typeFiles = infoFileids.split(",");
				Map<String,List<InformationFile>> mapfile = new HashMap<String,List<InformationFile>>();
				for(int i=0; i < typeFiles.length; i++){
					if(StringUtils.isNotEmpty(typeFiles[i])){
							informationFile.setTitleId(Long.valueOf(typeFiles[i]));
							List<InformationFile> files = informationFileService.queryList(informationFile);
							if(files != null && files.size() > 0){
								mapfile.put(typeFiles[i], files);
							}
						
					}
				}
				if(mapfile != null){
					fileslist.setCommonFileList(mapfile);
				}
			}
			responseBody.setEntity(fileslist);
			responseBody.setResult(new Result(Status.OK,null));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "操作失败"));
			logger.error("getFileByProject 操作失败",e);
		}
		
		return responseBody;
	}
	
	/**
	 * 根据项目id来获取全息图上传的图片
	 */
	@ResponseBody
	@RequestMapping(value = "/getFileByProject", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InformationFile> getFileByProject(@RequestBody InformationFile informationFile,HttpServletRequest request) {
		
		ResponseData<InformationFile> responseBody = new ResponseData<InformationFile>();
		if(informationFile.getProjectId() == null && informationFile.getTitleId() == null){
			responseBody.setResult(new Result(Status.ERROR,null, "参数不全!"));
			return responseBody;
		}
		try {
			List<InformationFile> files = informationFileService.queryList(informationFile);
			responseBody.setEntityList(files);
			responseBody.setResult(new Result(Status.OK,null));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "操作失败"));
			logger.error("getFileByProject 操作失败",e);
		}
		
		return responseBody;
	}
	
	
	/**
	 * 文件上传/更新
	 * @param fileKey  OSS对文件的唯一标识
	 * 注意点：对于上传操作，fileKey需要新生成；但对于更新操作，fileKey需要从sop_file表中获取到老的fileKey进行覆盖
	 * 类似于：
	    if(StringUtils.isBlank(sopFile.getFileKey())){
			sopFile.setFileKey(String.valueOf(IdGenerator.generateId(OSSHelper.class)));
		}
	 * @param tempfilePath  服务器保存文件的临时目录
	 * @return 不会出现null，只需要对result.getResult().getStatus().equals(Status.ERROR)验证即可知道操作结果状态
	 *         其包含文件fileKey、bucketName、文件名、文件后缀、文件大小
	 */
	public UploadFileResult uploadFileToOSS(InformationFile sop,  String fileKey) {
		UploadFileResult result = null;
		try {
			MultipartFile multipartFile = sop.getMultipartFile();
			String fileName = multipartFile.getOriginalFilename();
			Map<String,String> nameMap = FileUtils.transFileNames(fileName);
			File tempFile = new File(sop.getTempPath(), fileKey + "_" + nameMap.get("fileName"));
			if (!tempFile.exists()) {
				tempFile.mkdirs();
			}
			multipartFile.transferTo(tempFile);
			long asize = multipartFile.getSize(); 
			if(asize > OSSConstant.UPLOAD_PART_SIZE){//大文件线程池上传
				result = OSSHelper.uploadWithBreakpoint(fileName, tempFile, fileKey);
				if(result.getResult().getStatus()==null || result.getResult().getStatus().equals(Status.ERROR)){
					return result;
				}
			}else{
				result = OSSHelper.simpleUploadByOSS(tempFile, fileKey, OSSHelper.setRequestHeader(fileName, multipartFile.getSize())); //上传至阿里云
				//若文件上传成功
				if(result.getResult().getStatus()==null || result.getResult().getStatus().equals(Status.ERROR)){
					return result;
				}
			}
			result.setFileName(nameMap.get("fileName"));
			result.setFileSuffix(nameMap.get("fileSuffix"));
		} catch (Exception e) {
			result = new UploadFileResult();
			result.setResult(new Result(Status.ERROR, null, "异常"));
		}
		return result;
	}

}
