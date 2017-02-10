package com.galaxyinternet.project.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.framework.core.constants.SqlId;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.utils.BeanUtils;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.model.project.Project;


@Repository("projectDao")
public class ProjectDaoImpl extends BaseDaoImpl<Project, Long> implements ProjectDao {
	@Override
	public List<Project> selectProjectByMap(ProjectBo query) {
		Assert.notNull(query);
		Map<String, Object> params = BeanUtils.toMap(query);
		try {
		return sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_SELECT),params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象列表出错：%s", getSqlName(SqlId.SQL_SELECT)), e);
		}
	}

	@Override
	public Project selectTotalSummary(ProjectBo query) {
		return sqlSessionTemplate.selectOne(getSqlName("selectTotalSummary"),query);
	}

	@Override
	public List<Project> selectStageSummary(ProjectBo query) {
		return sqlSessionTemplate.selectList(getSqlName("selectStageSummary"),query);
	}

	@Override
	public long insertProject(Project project) {
		Assert.notNull(project);
		try {
			/*ID id = entity.getId();
			if (null == id) {
				if (StringUtils.isBlank(stringId)) {
					entity.setId((ID) generateId());
				}
			}*/
			sqlSessionTemplate.insert(getSqlName(SqlId.SQL_INSERT), project);
			return project.getId();
		} catch (Exception e) {
			throw new DaoException(String.format("添加对象出错！语句：%s", getSqlName(SqlId.SQL_INSERT)), e);
		}
	}

	@Override
	public List<Project> selectListById(List<Long> idList) {
		return sqlSessionTemplate.selectList(getSqlName("selectListById"),idList);
	}

	@Override
	public List<Long> queryProjectByUserId(Project project) {
		return sqlSessionTemplate.selectList(getSqlName("selectByUserId"),project);
	}


	
	
	
	
	
	//=== report
	
	@Override
	public List<Project> selectProjectReportForGg(ProjectBo query) {
		Map<String, Object> params = BeanUtils.toMap(query);
		return sqlSessionTemplate.selectList(getSqlName("selectProjectReportForGg"),params);
	}
	
	/**排序，不分页，手动分页*/
	@Override
	public List<Project> selectDeptAllProNumAndByType(ProjectBo query, Pageable pageable) {
		return sqlSessionTemplate.selectList(getSqlName("selectDeptAllProNumAndByType"),getParams(query, pageable));
	}
	/**排序，不分页，手动分页*/
	@Override
	public List<Project> selectTzjlAllProNumAndByType(ProjectBo query, Pageable pageable) {
		/*Map<String, Object> params = null;
		if(pageable == null){
			params = BeanUtils.toMap(query);
		}else params = getParams(query, pageable);*/
		return sqlSessionTemplate.selectList(getSqlName("selectTzjlAllProNumAndByType"),getParams(query, pageable));
	}
	
	
	@Override
	public List<Project> selectUserProNumOrderByNum(ProjectBo query, Pageable pageable) {
		return sqlSessionTemplate.selectList(getSqlName("selectUserProNumOrderByNum"),getParams(query, pageable));
	}
	@Override
	public List<Project> selectDeptProNumOrderByNum(ProjectBo query, Pageable pageable) {
		return sqlSessionTemplate.selectList(getSqlName("selectDeptProNumOrderByNum"),getParams(query, pageable));
	}
	
	@Override
	public List<Project> selectUserCompletedProNum(ProjectBo query) {
		Map<String, Object> params = BeanUtils.toMap(query);
		return sqlSessionTemplate.selectList(getSqlName("selectUserCompletedProNum"),params);
	}
	@Override
	public List<Project> selectDeptCompletedProNum(ProjectBo query) {
		Map<String, Object> params = BeanUtils.toMap(query);
		return sqlSessionTemplate.selectList(getSqlName("selectDeptCompletedProNum"),params);
	}
	

	@Override
	public Long selectUserProNumRowCount(ProjectBo proQuery) {
		Map<String, Object> params = BeanUtils.toMap(proQuery);
		return sqlSessionTemplate.selectOne(getSqlName("selectUserProNumRowCount"),params);
	}
	
	@Override
	public Long selectDeptProNumRowCount(ProjectBo proQuery) {
		Map<String, Object> params = BeanUtils.toMap(proQuery);
		return sqlSessionTemplate.selectOne(getSqlName("selectDeptProNumRowCount"),params);
	}

	@Override
	public List<Project> selectHasMeetProList(MeetingRecordBo query, Pageable pageable) {
		
		Map<String, Object> params = BeanUtils.toMap(query, getRowBounds(pageable));
		if (pageable != null && pageable.getSort() != null) {
			String sorting = pageable.getSort().toString();
			params.put("sorting", sorting.replace(":", ""));
			String str=(String)params.get("sorting");
			if(str.contains("---")){
				params.put("sorting", str.replace("---", ":"));
			}
		}
		return sqlSessionTemplate.selectList(getSqlName("selectHasMeetProList"),params);
	}
	
	
	@Override
	public List<Project> selectColumnList(ProjectBo query) {
		Map<String, Object> params = BeanUtils.toMap(query);
		return sqlSessionTemplate.selectList(getSqlName("selectColumnList"),params);
	}

	
	@Override
	public List<Long> selectProIdsForPrivilege(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(getSqlName("selectProIdsForPrivilege"),params);
	}
	
	@Override
	public Page<Project> selectDeptProject(Project query, Pageable pageable) {
		
		Map<String, Object> params = BeanUtils.toMap(query, getRowBounds(pageable));
		if (pageable != null && pageable.getSort() != null) {
			String sorting = pageable.getSort().toString();
			params.put("sorting", sorting.replace(":", ""));
			String str=(String)params.get("sorting");
			if(str.contains("---")){
				params.put("sorting", str.replace("---", ":"));
			}
		}
		List<Project> selectList = sqlSessionTemplate.selectList(getSqlName("selectDeptProject"),params);
	  return new  Page<Project>(selectList, pageable, deptProjectCount(query));
	}
		@Override
		public Page<Project> selectProjectTotalTime(Project query, Pageable pageable) {
			
			Map<String, Object> params = BeanUtils.toMap(query, getRowBounds(pageable));
			if (pageable != null && pageable.getSort() != null) {
				String sorting = pageable.getSort().toString();
				params.put("sorting", sorting.replace(":", ""));
				String str=(String)params.get("sorting");
				if(str.contains("---")){
					params.put("sorting", str.replace("---", ":"));
				}
			}
			List<Project> selectList = sqlSessionTemplate.selectList(getSqlName("selectProjectTotalTime"),params);
		
		  return new  Page<Project>(selectList, pageable, projectTotalTimeCount(query));
		}
		@Override
		public Long deptProjectCount(Project query) {
			try {
				Map<String, Object> params = BeanUtils.toMap(query);
				Long reslut=sqlSessionTemplate.selectOne(this.getSqlNamespace()+ ".deptProjectCount", params);
				return reslut;
			} catch (Exception e) {
				throw new DaoException(String.format("查询对象总数出错！语句：%s", getSqlName("deptProjectCount")), e);
			}
		}
		@Override
		public Long projectTotalTimeCount(Project query) {
			try {
				Map<String, Object> params = BeanUtils.toMap(query);
				return sqlSessionTemplate.selectOne(this.getSqlNamespace()+ ".projectTotalTimeCount", params);
			} catch (Exception e) {
				throw new DaoException(String.format("查询对象总数出错！语句：%s", getSqlName("projectTotalTimeCount")), e);
			}
		}
		
		@Override
		public List<Project>  selectProjectForPushMessage() {
			try {
						return sqlSessionTemplate.selectList("selectProjectForPushMessage");
			} catch (Exception e) {
				throw new DaoException(String.format("查询对象总数出错！语句：%s", getSqlName("projectTotalTimeCount")), e);
			}
		}
		
		
	}