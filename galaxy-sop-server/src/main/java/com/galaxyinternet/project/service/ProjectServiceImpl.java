package com.galaxyinternet.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.service.ProjectService;


@Service("com.galaxyinternet.service.ProjectService")
public class ProjectServiceImpl extends BaseServiceImpl<Project> implements ProjectService {

	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private SopFileDao sopFileDao;
	
	@Override
	protected BaseDao<Project, Long> getBaseDao() {
		return this.projectDao;
	}

	@Override
	@Transactional
	public long newProject(Project project) throws Exception {
		long id = projectDao.insert(project);
		SopFile f = new SopFile();
		f.setProjectId(id);
		f.setCareerLine(project.getProjectDepartid());
		f.setFileStatus("fileStatus:1");
		
		
		f.setFileWorktype("fileWorktype:1");
		f.setProjectProgress("projectProgress:6");
		sopFileDao.insert(f);
		f.setFileWorktype("fileWorktype:2");
		f.setProjectProgress("projectProgress:6");
		sopFileDao.insert(f);
		f.setFileWorktype("fileWorktype:3");
		f.setProjectProgress("projectProgress:6");
		sopFileDao.insert(f);
		f.setFileWorktype("fileWorktype:4");
		f.setProjectProgress("projectProgress:6");
		sopFileDao.insert(f);
		f.setFileWorktype("fileWorktype:5");
		f.setProjectProgress("projectProgress:5");
		sopFileDao.insert(f);
		f.setFileWorktype("fileWorktype:6");
		f.setProjectProgress("projectProgress:8");
		sopFileDao.insert(f);
		f.setFileWorktype("fileWorktype:7");
		f.setProjectProgress("projectProgress:8");
		sopFileDao.insert(f);
		f.setFileWorktype("fileWorktype:8");
		f.setProjectProgress("projectProgress:9");
		sopFileDao.insert(f);
		f.setFileWorktype("fileWorktype:9");
		f.setProjectProgress("projectProgress:9");
		sopFileDao.insert(f);
		f.setFileWorktype("fileWorktype:12");
		f.setProjectProgress("projectProgress:10");
		sopFileDao.insert(f);
		f.setFileWorktype("fileWorktype:13");
		f.setProjectProgress("projectProgress:10");
		sopFileDao.insert(f);
		f.setFileWorktype("fileWorktype:14");
		f.setProjectProgress("projectProgress:10");
		sopFileDao.insert(f);
		return id;
	}


}