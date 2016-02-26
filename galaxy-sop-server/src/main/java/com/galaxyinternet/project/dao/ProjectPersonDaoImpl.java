package com.galaxyinternet.project.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.project.ProjectPersonDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.project.ProjectPerson;


@Repository("projectPersonDao")
public class ProjectPersonDaoImpl extends BaseDaoImpl<ProjectPerson, Long> implements ProjectPersonDao {


}