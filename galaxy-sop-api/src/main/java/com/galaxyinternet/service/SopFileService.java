package com.galaxyinternet.service;

import java.util.List;

import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.sopfile.SopFile;

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
	
	
}
