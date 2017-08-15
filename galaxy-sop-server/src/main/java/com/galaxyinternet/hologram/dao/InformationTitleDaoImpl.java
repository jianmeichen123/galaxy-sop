package com.galaxyinternet.hologram.dao;

import com.galaxyinternet.dao.hologram.InformationTitleDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.utils.BeanUtils;
import com.galaxyinternet.model.hologram.InformationTitle;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("informationTitleDao")
public class InformationTitleDaoImpl extends BaseDaoImpl<InformationTitle, Long> implements InformationTitleDao{

	
	/**
	 * 根据父信息  code 、 id 查询子 code 集合
	 * 
	 */
	@Override
	public List<InformationTitle> selectChildsByPid(Map<String, Object> params) {

		try {
			List<InformationTitle> contentList = sqlSessionTemplate.selectList(getSqlName("selectChildsByPid") ,params);
			//System.err.println("contentList==>>"+GSONUtil.toJson(contentList));
			return contentList;
		} catch (Exception e) {
			throw new DaoException(String.format("根据titile查询子code出错！语句:%s", getSqlName("selectChildsByPid")), e);
		}
	}

	
	/**
	 * 查询 parentid 为空的 题， 即顶级目录
	 */
	@Override
	public List<InformationTitle> selectFirstTitle() {
		try {
			List<InformationTitle> contentList = sqlSessionTemplate.selectList(getSqlName("selectFirstTitle"));
			//System.err.println("contentList==>>"+GSONUtil.toJson(contentList));
			return contentList;
		} catch (Exception e) {
			throw new DaoException(String.format("根据titile查询子code出错！语句:%s", getSqlName("selectFirstTitle")), e);
		}
	}


	@Override
	public List<InformationTitle> selectRelateTitle(InformationTitle query)
	{
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return sqlSessionTemplate.selectList(getSqlName("selectRelateTitle"),params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询出错！语句:%s", getSqlName("selectRelateTitle")), e);
		}
	}




	/**
	 * 根据 title id 查询results
	 */
	@Override
	public List<InformationTitle> selectTitleOfResults(Map<String, Object> params) {

		try {
			List<InformationTitle> contentList = sqlSessionTemplate.selectList(getSqlName("selectTitleOfResults") ,params);
			//System.err.println("contentList==>>"+GSONUtil.toJson(contentList));
			return contentList;
		} catch (Exception e) {
			throw new DaoException(String.format("根据titiles查询results出错！语句:%s", getSqlName("selectTitleOfResults")), e);
		}
	}

	/**
	 * 根据 title id 查询results,  统计 title 记录数
	 */
	@Override
	public Integer selectCountForTitleOfResults(Map<String, Object> params) {
		try {
			Integer content = sqlSessionTemplate.selectOne(getSqlName("selectCountForTitleOfResults") ,params);
			//System.err.println("contentList==>>"+GSONUtil.toJson(contentList));
			return content;
		} catch (Exception e) {
			throw new DaoException(String.format("根据titiles查询results count title出错 ！语句:%s", getSqlName("selectCountForTitleOfResults")), e);
		}
	}
	public Integer selectCountForTitleOfListdata(Map<String, Object> params) {
		try {
			Integer content = sqlSessionTemplate.selectOne(getSqlName("selectCountForTitleOfListdata") ,params);
			//System.err.println("contentList==>>"+GSONUtil.toJson(contentList));
			return content;
		} catch (Exception e) {
			throw new DaoException(String.format("根据titiles查询listdata count title出错 ！语句:%s", getSqlName("selectCountForTitleOfListdata")), e);
		}
	}
	public Integer selectCountForTitleOfFixedTable(Map<String, Object> params) {
		try {
			Integer content = sqlSessionTemplate.selectOne(getSqlName("selectCountForTitleOfFixedTable") ,params);
			//System.err.println("contentList==>>"+GSONUtil.toJson(contentList));
			return content;
		} catch (Exception e) {
			throw new DaoException(String.format("根据titiles查询listdata count title出错 ！语句:%s", getSqlName("selectCountForTitleOfFixedTable")), e);
		}
	}
	public Integer selectCountForTitleOfFile(Map<String, Object> params) {
		try {
			Integer content = sqlSessionTemplate.selectOne(getSqlName("selectCountForTitleOfFile") ,params);
			//System.err.println("contentList==>>"+GSONUtil.toJson(contentList));
			return content;
		} catch (Exception e) {
			throw new DaoException(String.format("根据titiles查询listdata count title出错 ！语句:%s", getSqlName("selectCountForTitleOfFile")), e);
		}
	}
	
}
