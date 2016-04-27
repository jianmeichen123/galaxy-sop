package com.galaxyinternet.sopfile.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.sopfile.SopVoucherFileDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.sopfile.SopVoucherFile;

@Repository("sopVoucherFileDao")
public class SopVoucherFileDaoImpl extends BaseDaoImpl<SopVoucherFile, Long> implements SopVoucherFileDao  {

	@Override
	public List<SopVoucherFile> selectListById(List<Long> idList) {
		return sqlSessionTemplate.selectList(getSqlName("selectListById"),idList);
	}

}
