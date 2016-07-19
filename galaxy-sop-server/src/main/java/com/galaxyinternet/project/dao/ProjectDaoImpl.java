package com.galaxyinternet.project.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.framework.core.constants.SqlId;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.utils.BeanUtils;
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
	
	@Override
	public List<Project> selectUserProNumByProType(ProjectBo query) {
		Map<String, Object> params = BeanUtils.toMap(query);
		return sqlSessionTemplate.selectList(getSqlName("selectUserProNumByProType"),params);
	}
	
	@Override
	public List<Project> selectUserProNumOrderByNum(ProjectBo query, Pageable pageable) {
		return sqlSessionTemplate.selectList(getSqlName("selectUserProNumOrderByNum"),getParams(query, pageable));
	}

	@Override
	public List<Project> selectUserCompletedProNum(ProjectBo query) {
		Map<String, Object> params = BeanUtils.toMap(query);
		return sqlSessionTemplate.selectList(getSqlName("selectUserCompletedProNum"),params);
	}
	
	@Override
	public List<Project> selectDeptrCompletedProNum(ProjectBo query) {
		Map<String, Object> params = BeanUtils.toMap(query);
		return sqlSessionTemplate.selectList(getSqlName("selectDeptCompletedProNum"),params);
	}

	@Override
	public Long selectUserProNumCount(ProjectBo proQuery) {
		Map<String, Object> params = BeanUtils.toMap(proQuery);
		return sqlSessionTemplate.selectOne(getSqlName("selectUserProNumCount"),params);
	}

}