package com.galaxyinternet.dao.sopfile;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.query.Query;
import com.galaxyinternet.model.sopfile.SopFile;

public interface SopFileDao extends BaseDao<SopFile, Long> {

	public List<SopFile> queryByFileTypeList(SopFileBo sbo);
	
	/**
	 * 通过项目及业务分类获取唯一档案
	 * @param sf
	 * @return
	 */
	public SopFile queryByProjectAndFileWorkType(SopFile sf);
	
	/**
	 * 查询项目名称
	 * @param map
	 * @return
	 */
	public List<Map<String,String>> queryProjectName(Map<String,Object> map);
	
	/**
	 * 项目移交时修改文档部门ID
	 */
	int updateDepartmentId(SopFile f);
	
	/**
	 * 分页查询（新）
	 * @param query
	 * @param pageable
	 * @return
	 */
	public Page<SopFile> selectPageFile(SopFile query, Pageable pageable);
	
	/**
	 * 分页查询数量
	 * @param query
	 * @return
	 */
	public Long selectQueryCount(Query query);
	
	
}