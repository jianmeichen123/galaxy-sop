package com.galaxyinternet.project.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.project.ProjectTransferDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.project.ProjectTransfer;


@Repository("projectTransferDao")
public class ProjectTransferDaoImpl extends BaseDaoImpl<ProjectTransfer, Long> implements ProjectTransferDao {

	@Override
	public List<ProjectTransfer> selectApplyRecord(Long pid) {
		return sqlSessionTemplate.selectList(getSqlName("selectApplyRecord"), pid);
	}


}