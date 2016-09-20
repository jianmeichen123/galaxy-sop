package com.galaxyinternet.dao.project;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.model.project.MeetingRecord;
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

	public List<Project> selectUserProNumOrderByNum(ProjectBo query, Pageable pageable);
	public List<Project> selectDeptProNumOrderByNum(ProjectBo query, Pageable pageable);
	
	public List<Project> selectUserCompletedProNum(ProjectBo query);

	public Long selectUserProNumRowCount(ProjectBo proQuery);
	
	public Long selectDeptProNumRowCount(ProjectBo proQuery);

	public List<Project> selectDeptCompletedProNum(ProjectBo proOverCount);

	List<Project> selectDeptAllProNumAndByType(ProjectBo query, Pageable pageable);

	List<Project> selectTzjlAllProNumAndByType(ProjectBo query, Pageable pageable);


	List<Project> selectHasMeetProList(MeetingRecordBo query, Pageable pageable);

	public List<Project> selectColumnList(ProjectBo proQuery);

	public List<Long> selectProIdsForPrivilege(Map<String, Object> params);
	
	public List<Project> getHealthyChart(Map<String, Object> params) ;

	
	
}