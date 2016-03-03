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
	
	
}
