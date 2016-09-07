package com.galaxyinternet.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.file.UploadFileResult;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.oss.OSSConstant;
import com.galaxyinternet.framework.core.oss.OSSFactory;
import com.galaxyinternet.framework.core.utils.FileUtils;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.touhou.Delivery;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.sopfile.controller.SopFileController;

@Component
public class BatchUploadFile {
	final Logger logger = LoggerFactory.getLogger(BatchUploadFile.class);
	@Autowired
	Cache cache;
    /**
     * 批量上传
     * @param request
     * @param user
     * @return
     */
	public  ResponseData<SopFile> batchUpload(String fileRedisKey){
		ResponseData<SopFile> resultResponse = new ResponseData<SopFile>();
		resultResponse.setResult(new Result(Status.OK,""));
		List<SopFile> sopFileList = new ArrayList<SopFile>();
		try{
			List<Object> fileList = cache.getRedisQuenOBJ(fileRedisKey);
			if(fileList != null && fileList.size() > 0){
				for(Object obj:fileList){
					SopFile sop = (SopFile) obj;
					     UploadFileResult res = uploadFileToOSS(sop, sop.getFileKey());
					     if(Status.ERROR.equals(res.getResult().getStatus())){
					    	 cache.getByMemc(fileRedisKey);
					    	 resultResponse.setResult(new Result(Status.ERROR,"系统异常!"));
					    	 break;
					     }
					     sopFileList.add(sop);
				}
			}else{
				resultResponse.setResult(new Result(Status.ERROR,""));
				cache.removeRedisKeyOBJ(fileRedisKey);
			}
			resultResponse.setEntityList(sopFileList);
		}catch(Exception e){
			logger.error(e.getLocalizedMessage());
			resultResponse.setResult(new Result(Status.ERROR,"系统异常!"));
		}finally {
			cache.removeRedisKeyOBJ(fileRedisKey);
		}
		return resultResponse;
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
	public UploadFileResult uploadFileToOSS(SopFile sop,  String fileKey) {
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
