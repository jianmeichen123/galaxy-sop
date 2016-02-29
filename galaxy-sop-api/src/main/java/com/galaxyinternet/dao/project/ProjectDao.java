package com.galaxyinternet.dao.project;

import java.util.List;

import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.model.project.Project;

public interface ProjectDao extends BaseDao<Project, Long> {
	/**
	 * @author chenjianmei
	 * @category 根据条件查询项目
	 * @param query
	 * @return
	 */
	public List<Project> selectProjectByMap(ProjectBo query);
	

}