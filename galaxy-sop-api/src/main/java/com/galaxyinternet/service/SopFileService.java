package com.galaxyinternet.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.sopfile.SopVoucherFile;
import com.galaxyinternet.model.soptask.SopTask;

/**
 * @author zhongliangzhang
 *
 */
public interface SopFileService extends BaseService<SopFile> {

	
	public List<SopFile> selectByFileTypeList(SopFileBo sbo);
	
	
	/**
	 * 通过项目ID及业务分类获取唯一档案
	 * @param sf
	 * @return
	 */
	public SopFile selectByProjectAndFileWorkType(SopFile sf);
	
	/**
	 * 签署凭证上传时业务逻辑处理
	 * @param sopFile
	 * @param sopTask
	 * @param project
	 * @return
	 */
	public SopVoucherFile updateProve(SopVoucherFile sopVoucherFile,SopTask sopTask,Project project,Long userId,Long departmentId);
	

	/**
	 * 文档更新:
	 * 		删除aliyun原文件
	 * 		上传新文件
	 * 		更新sopfile表记录
	 @param request
	 @param fid  file_id
	 @return Result.Status.ERROR\OK 失败\成功
	 * */
	public Result updateFile(HttpServletRequest request, Long fid) throws Exception;
	
	
	/**
	 * 单纯文档上传
	 * 		仅上传到aliyun
	 * @param request 转为 MultipartFile，获取key=file
	 * @param fileKey 调用OSSHelper生成的key
	 * @param bucketName  默认传入 BucketName.DEV.getName()
	 * @return MultipartFile null=上传失败
	 */	
	public File aLiColoudUpload(HttpServletRequest request, String fileKey,String bucketName) throws Exception;
	
}
