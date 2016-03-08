package com.galaxyinternet.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.common.enums.DictEnum;
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
		f.setFileStatus(DictEnum.fileStatus.缺失.getCode());
		
		f.setFileWorktype(DictEnum.fileWorktype.业务尽职调查报告.getCode());
		f.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
		sopFileDao.insert(f);
		f.setFileWorktype(DictEnum.fileWorktype.人力资源尽职调查报告.getCode());
		f.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
		sopFileDao.insert(f);
		f.setFileWorktype(DictEnum.fileWorktype.法务尽职调查报告.getCode());
		f.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
		sopFileDao.insert(f);
		f.setFileWorktype(DictEnum.fileWorktype.财务尽职调查报告.getCode());
		f.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
		sopFileDao.insert(f);
		f.setFileWorktype(DictEnum.fileWorktype.投资意向书.getCode());
		f.setProjectProgress(DictEnum.projectProgress.投资意向书.getCode());
		sopFileDao.insert(f);
		f.setFileWorktype(DictEnum.fileWorktype.投资协议.getCode());
		f.setProjectProgress(DictEnum.projectProgress.投资协议.getCode());
		sopFileDao.insert(f);
		f.setFileWorktype(DictEnum.fileWorktype.股权转让协议.getCode());
		f.setProjectProgress(DictEnum.projectProgress.投资协议.getCode());
		sopFileDao.insert(f);
		f.setFileWorktype(DictEnum.fileWorktype.工商转让凭证.getCode());
		f.setProjectProgress(DictEnum.projectProgress.股权交割.getCode());
		sopFileDao.insert(f);
		f.setFileWorktype(DictEnum.fileWorktype.资金拨付凭证.getCode());
		f.setProjectProgress(DictEnum.projectProgress.股权交割.getCode());
		sopFileDao.insert(f);
		f.setFileWorktype(DictEnum.fileWorktype.公司资料.getCode());
		f.setProjectProgress(DictEnum.projectProgress.投后运营.getCode());
		sopFileDao.insert(f);
		f.setFileWorktype(DictEnum.fileWorktype.财务预测报告.getCode());
		f.setProjectProgress(DictEnum.projectProgress.投后运营.getCode());
		sopFileDao.insert(f);
		f.setFileWorktype(DictEnum.fileWorktype.商业计划.getCode());
		f.setProjectProgress(DictEnum.projectProgress.投后运营.getCode());
		sopFileDao.insert(f);
		return id;
	}


}