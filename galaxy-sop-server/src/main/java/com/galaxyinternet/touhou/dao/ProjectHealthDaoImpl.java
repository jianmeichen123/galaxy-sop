package com.galaxyinternet.touhou.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.touhou.ProjectHealthDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.touhou.ProjectHealth;


@Repository("projectHealthDao")
public class ProjectHealthDaoImpl extends BaseDaoImpl<ProjectHealth, Long> implements ProjectHealthDao {

}