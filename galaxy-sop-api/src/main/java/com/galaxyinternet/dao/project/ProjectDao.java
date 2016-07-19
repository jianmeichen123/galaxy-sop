package com.galaxyinternet.dao.project;

import java.util.List;

import org.springframework.data.domain.Pageable;

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
	
	public Project selectTotalSummary(ProjectBo query);
	
	public List<Project> selectStageSummary(ProjectBo query);
	
	public long insertProject(Project project);
	
	
	public List<Project> selectListById(List<Long> idList);
	
	public List<Long> queryProjectByUserId(Project project);
	
	
	
	//=======REPORT

	public List<Project> selectProjectReportForGg(ProjectBo query);

	List<Project> selectUserProNumByProType(ProjectBo query);

	List<Project> selectUserProNumOrderByNum(ProjectBo query, Pageable pageable);

	public List<Project> selectUserCompletedProNum(ProjectBo query);

	List<Project> selectDeptrCompletedProNum(ProjectBo query);

	public Long selectUserProNumCount(ProjectBo proQuery);
}