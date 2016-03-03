package com.galaxyinternet.dao.sopfile;

import java.util.List;

import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.model.sopfile.SopFile;

public interface SopFileDao extends BaseDao<SopFile, Long> {

	public List<SopFile> queryByFileTypeList(SopFileBo sbo);
	
}