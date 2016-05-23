package com.galaxyinternet.sopfile.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
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

	/**
	 * 查询项目名称
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> queryProjectName(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(getSqlName("queryProjectName"), map);
	}
	



	
	
}
