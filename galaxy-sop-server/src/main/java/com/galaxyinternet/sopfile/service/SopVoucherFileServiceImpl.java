package com.galaxyinternet.sopfile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.sopfile.SopVoucherFileDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.sopfile.SopVoucherFile;
import com.galaxyinternet.service.SopVoucherFileService;

@Service("com.galaxyinternet.service.SopVoucherFileService")
public class SopVoucherFileServiceImpl extends BaseServiceImpl<SopVoucherFile> implements SopVoucherFileService {
	
	@Autowired
	private SopVoucherFileDao sopVoucherFileDao;

	@Override
	protected BaseDao<SopVoucherFile, Long> getBaseDao() {
		return this.sopVoucherFileDao;
	}

}
