package com.galaxyinternet.sopfile.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.framework.core.constants.SqlId;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.utils.BeanUtils;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.model.sopfile.SopFile;

@Repository("sopFileDao")
public class SopFileDaoImpl extends BaseDaoImpl<SopFile, Long> implements SopFileDao  {

	
	@Override
	public List<SopFile> queryByFileTypeList(SopFileBo sbo) {
		
		try {
			List<SopFile> list = sqlSessionTemplate.selectList(getSqlName("selectFileListByType") ,sbo);
			
			System.err.println("contentList==>>"+GSONUtil.toJson(list));
			
			return list;
		} catch (Exception e) {
			throw new DaoException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName("selectMeetPage")), e);
		}
		
		
	}
	
	/**
	 * 通过项目及业务分类获取唯一档案
	 * @param sf
	 * @return
	 */
	public SopFile queryByProjectAndFileWorkType(SopFile sf){
		return sqlSessionTemplate.selectOne(getSqlName("queryByProjectAndFileWorkType"), sf);
	}
	
	
	public Page<SopFile> selectPageFile(SopFile query, Pageable pageable){
		try {
			List<SopFile> contentList = sqlSessionTemplate.selectList(getSqlName("selectPageFile"),
					getParams(query, pageable));
			Long count =  this.selectPageFileCount(query);
			return new  Page<SopFile>(contentList, pageable,count);
		} catch (Exception e) {
			throw new DaoException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName("selectPageFile")), e);
		}
	}
	
	
	public Long selectPageFileCount(SopFile query) {
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return sqlSessionTemplate.selectOne(getSqlName("selectPageFileCount"), params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询对象总数出错！语句：%s", getSqlName("selectPageFileCount")), e);
		}
	}
	

	/**
	 * 查询项目名称
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> queryProjectName(Map<String, Object> map) {
		return sqlSessionTemplate.selectList(getSqlName("queryProjectName"), map);
	}

	@Override
	public int updateDepartmentId(SopFile f) {
		return sqlSessionTemplate.update(getSqlName("updateDepartmentId"), f);
	}
	



	
	
}
