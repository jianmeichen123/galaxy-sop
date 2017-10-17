package com.galaxyinternet.hologram.dao;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.galaxyinternet.dao.hologram.InformationFileDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.model.hologram.InformationFile;

@Repository("informationFileDao")
public class InformationFileDaoImpl extends BaseDaoImpl<InformationFile, Long> implements InformationFileDao{

	@Override
	public int updateCreateUid(InformationFile ir) {
		Assert.notNull(ir);
		try {
			return sqlSessionTemplate.update(getSqlName("updateCreateUid"), ir);
		} catch (Exception e) {
			throw new DaoException(String.format("根据ID更新对象出错！语句：%s", getSqlName("updateCreateUid")), e);
		}
	}

}
