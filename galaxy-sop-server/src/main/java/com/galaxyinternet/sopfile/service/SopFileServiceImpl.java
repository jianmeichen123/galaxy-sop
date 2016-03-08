package com.galaxyinternet.sopfile.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.service.SopFileService;

@Service("com.galaxyinternet.service.SopFileService")
public class SopFileServiceImpl extends BaseServiceImpl<SopFile> implements
		SopFileService {
	
	@Autowired
	private SopFileDao sopFileDao;

	@Override
	protected BaseDao<SopFile, Long> getBaseDao() {
		// TODO Auto-generated method stub
		return this.sopFileDao;
	}

	
	
	@Override
	public List<SopFile> selectByFileTypeList(SopFileBo sbo) {
		return sopFileDao.queryByFileTypeList(sbo);
	}
	
	/**
	 * 通过项目ID及业务分类获取唯一档案
	 * @param sf
	 * @return
	 */
	public SopFile selectByProjectAndFileWorkType(SopFile sf){
		return sopFileDao.queryByProjectAndFileWorkType(sf);
	}
	
	
	
	
	

}
