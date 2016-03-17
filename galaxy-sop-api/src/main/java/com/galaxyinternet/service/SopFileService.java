package com.galaxyinternet.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
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
	 * 单纯更新file
	 * @param request
	 * @param fid  file_id
	 * @return Result.Status.ERROR\OK 失败\成功
	 */
	public Result updateFile(HttpServletRequest request, Long fid) throws Exception;
	
}
