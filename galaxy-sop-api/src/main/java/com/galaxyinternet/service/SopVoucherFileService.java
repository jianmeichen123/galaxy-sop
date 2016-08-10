package com.galaxyinternet.service;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.sopfile.SopVoucherFile;

public interface SopVoucherFileService extends BaseService<SopVoucherFile>{
	
	/**
	 * 项目移交时修改文档部门ID
	 */
	int updateDepartmentId(SopVoucherFile f);

}
