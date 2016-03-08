package com.galaxyinternet.dao.sopfile;

import java.util.List;

import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.model.sopfile.SopFile;

public interface SopFileDao extends BaseDao<SopFile, Long> {

	public List<SopFile> queryByFileTypeList(SopFileBo sbo);
	
	/**
	 * 通过项目及业务分类获取唯一档案
	 * @param sf
	 * @return
	 */
	public SopFile queryByProjectAndFileWorkType(SopFile sf);
	
}