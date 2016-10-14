package com.galaxyinternet.touhou.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.touhou.ProjectHealthDao;
import com.galaxyinternet.framework.core.constants.SqlId;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.utils.BeanUtils;
import com.galaxyinternet.model.touhou.ProjectHealth;


@Repository("projectHealthDao")
public class ProjectHealthDaoImpl extends BaseDaoImpl<ProjectHealth, Long> implements ProjectHealthDao {

	@Override
	public List<ProjectHealth> getHealthyChart(Map<String, Object> params) {
		return sqlSessionTemplate.selectList(getSqlName("getHealthyCharts"),params);
     }

	@Override
	public Page<ProjectHealth> getHealthChartGrid(ProjectHealth query, Pageable pageable) {
		try {
			List<ProjectHealth> contentList = sqlSessionTemplate.selectList(getSqlName("getHealthyChartsGrid"),getParams(query, pageable));
			return new Page<ProjectHealth>(contentList, pageable, this.selectCountChart(query));
		} catch (Exception e) {
			throw new DaoException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(SqlId.SQL_SELECT)), e);
		}
}
	@Override
	public Long selectCountChart(ProjectHealth query) {
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return sqlSessionTemplate.selectOne(getSqlName("selectCountChart"), params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象总数出错！语句：%s", getSqlName(SqlId.SQL_SELECT_COUNT)), e);
		}
	}
}