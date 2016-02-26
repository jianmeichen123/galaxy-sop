package com.galaxyinternet.project.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.project.ProjectSharesDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.project.ProjectShares;


@Repository("projectSharesDao")
public class ProjectSharesDaoImpl extends BaseDaoImpl<ProjectShares, Long> implements ProjectSharesDao {


}