package com.galaxyinternet.chart.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.galaxyinternet.framework.core.constants.SqlId;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.utils.BeanUtils;

public class BaseChartDaoImpl<T> {
	@Autowired(required = true)
	protected SqlSession sqlSessionTemplate;
	/**
	 * @fields sqlNamespace SqlMapping命名空间
	 */
	private String sqlNamespace = getDefaultSqlNamespace();
	
	public static final String SQLNAME_SEPARATOR = ".";
	
	/**
	 * 获取泛型类型的实体对象类全名
	 */
	protected String getDefaultSqlNamespace() {
		Class<?> genericClass = BeanUtils.getGenericClass(this.getClass());
		return genericClass == null ? null : genericClass.getName();
	}
	
	/**
	 * 获取SqlMapping命名空间
	 */
	public String getSqlNamespace() {
		return sqlNamespace;
	}
	
	/**
	 * 设置SqlMapping命名空间。 以改变默认的SqlMapping命名空间， 不能滥用此方法随意改变SqlMapping命名空间。
	 */
	public void setSqlNamespace(String sqlNamespace) {
		this.sqlNamespace = sqlNamespace;
	}
	
	/**
	 * 将SqlMapping命名空间与给定的SqlMapping名组合在一起。
	 * 
	 * @param sqlName
	 *            SqlMapping名
	 * @return 组合了SqlMapping命名空间后的完整SqlMapping名
	 */
	protected String getSqlName(String sqlName) {
		return sqlNamespace + SQLNAME_SEPARATOR + sqlName;
	}
	
	/**
	 * 获取分页查询参数
	 * 
	 * @param query
	 *            查询对象
	 * @return Map 查询参数
	 */
	protected Map<String, Object> getParams(T query,Pageable pageable) {
		Map<String, Object> params = BeanUtils.toMap(query,getRowBounds(pageable));
		if (pageable != null && pageable.getSort() != null) {
			String sorting = pageable.getSort().toString();
			params.put("sorting", sorting.replace(":", ""));
			String str=(String)params.get("sorting");
			if(str.contains("---")){
				
				params.put("sorting", str.replace("---", ":"));
			}
		
		}
		return params;
	}
	
	protected List<T> selectListBySqlName(String sqlName,
			T query) {
		// TODO Auto-generated method stub
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return sqlSessionTemplate.selectList(getSqlName(sqlName), params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象列表出错！语句：%s",
					getSqlName(SqlId.SQL_SELECT)), e);
		}
	}
	
	protected T selectEntityBySqlName(String sqlName, T query) {
		// TODO Auto-generated method stub
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return sqlSessionTemplate.selectOne(getSqlName(sqlName), params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象出错！语句：%s",
					getSqlName(SqlId.SQL_SELECT)), e);
		}
	}
	
	protected Long selectCountBySqlName(String sqlName,T query) {
		// TODO Auto-generated method stub
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return sqlSessionTemplate.selectOne(getSqlName(sqlName),params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象总数出错！语句：%s", getSqlName(SqlId.SQL_SELECT_COUNT)), e);
		}
	}
	
	/**
	 * 设置分页
	 * 
	 * @param pageInfo
	 *            分页信息
	 * @return SQL分页参数对象
	 */
	protected RowBounds getRowBounds(Pageable pageable) {
		RowBounds bounds = RowBounds.DEFAULT;
		if (null != pageable) {
			bounds = new RowBounds(pageable.getOffset(), pageable.getPageSize());
		}
		return bounds;
	}
	
	
}
