package com.galaxyinternet.hologram.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

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
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.touhou.Delivery;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.hologram.InformationFileService;
import com.galaxyinternet.utils.BatchUploadFile;
import com.galaxyinternet.utils.FileUtils;

@Controller("/galaxy/informationFile")
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
	 * 将文件上传到redis中
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendUploadByRedis", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)  
    public Result batchUpload(HttpServletRequest request){
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; // 请求转换
			MultipartFile multipartFile = multipartRequest.getFile("file"); // 获取multipartFile文件
			SopFile form = new SopFile();
			form.setBucketName(OSSFactory.getDefaultBucketName());
			form.setFileKey(String
						.valueOf(IdGenerator.generateId(OSSHelper.class)));
			Map<String, String> nameMap = FileUtils.transFileNames(multipartFile.getOriginalFilename());
			String fileKey = request.getParameter("fileReidsKey");
			form.setFileName(request.getParameter("fileName").substring(0, request.getParameter("fileName").lastIndexOf(".")));
			form.setFileSuffix(nameMap.get("fileSuffix"));
			form.setFileType(FileUtils.getFileType(form.getFileSuffix()));
			form.setFileLength(multipartFile.getSize());
			form.setFileStatus(DictEnum.fileStatus.已上传.getCode());
			form.setFileUid(user.getId());
			form.setRecordType((byte)0);
			form.setCareerLine(user.getDepartmentId());
			form.setCreatedTime(System.currentTimeMillis());
			form.setMultipartFile(multipartFile);
			form.setTempPath(tempfilePath);
			cache.setRedisSetOBJ(user.getId()+fileKey,form);
			return new Result(Status.OK, "");
		} catch (Exception e) {
			cache.removeRedisKeyOBJ(user.getTelephone()+request.getParameter("fileReidsKey"));
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
								    	 cache.getByMemc(redisKey);
								    	 logger.error("文件上次失败:",fileObject.getFileName());
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
			logger.error("operInformation 操作失败",e);
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
