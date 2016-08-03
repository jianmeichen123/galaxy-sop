package com.galaxyinternet.dao.sopfile;

import java.util.List;

import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.model.sopfile.SopVoucherFile;

public interface SopVoucherFileDao extends BaseDao<SopVoucherFile, Long> {
	
	public List<SopVoucherFile> selectListById(List<Long> idList);
	
	/**
	 * 项目移交时修改文档部门ID
	 */
	int updateDepartmentId(SopVoucherFile f);
	
}