package com.galaxyinternet.dao.project;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
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
	/**
	 * @author chenjianmei
	 * @serialData  2017-01-03
	 * 投后运营-头后项目跟踪-事业部创投项目列表（查询条件不包括融资时间）
	 * @param query
	 * @param pageable
	 * @return
	 */
	public Page<Project> selectDeptProject(Project query, Pageable pageable);
	/**
	 * @author chenjianmei
	 * 投后运营-头后项目跟踪-事业部创投项目列表（查询条件包括融资时间）
	 * @serialData  2017-01-03
	 * @param query
	 * @param pageable
	 * @return
	 */
	public Page<Project> selectProjectTotalTime(Project query, Pageable pageable);
	public Long deptProjectCount(Project query);
	public Long projectTotalTimeCount(Project query);
	public List<Project>  selectProjectForPushMessage();
}