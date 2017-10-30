package com.galaxyinternet.hologram.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.galaxyinternet.dao.hologram.InformationResultDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.utils.BeanUtils;
import com.galaxyinternet.model.hologram.InformationResult;

@Repository("InformationResultDao")
public class InformationResultDaoImpl extends BaseDaoImpl<InformationResult, Long> implements InformationResultDao 
{

	@Override
	public void insertInBatch(List<InformationResult> entityList)
	{
		sqlSessionTemplate.insert(getSqlName("insertBatch"), entityList);
	}
	@Override
	public List<InformationResult> selectResultByRelate(InformationResult ir) {
		try {
			Map<String, Object> params = BeanUtils.toMap(ir);
			List<InformationResult> contentList = sqlSessionTemplate.selectList(getSqlName("selectResultByRelateType") ,params);
			return contentList;
		} catch (Exception e) {
			throw new DaoException(String.format("根据Relate查询titile出错！语句:%s", getSqlName("selectResultByRelateType")), e);
		}
	}

	
}
