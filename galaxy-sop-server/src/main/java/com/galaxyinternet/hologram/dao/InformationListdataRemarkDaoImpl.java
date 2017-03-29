package com.galaxyinternet.hologram.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.hologram.InformationListdataRemarkDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.hologram.InformationListdataRemark;

@Repository("informationListdataRemarkDao")
public class InformationListdataRemarkDaoImpl extends BaseDaoImpl<InformationListdataRemark, Long> implements InformationListdataRemarkDao{

    @Override
    public InformationListdataRemark selectByTitleId(Long titleId) {
        InformationListdataRemark  remark = sqlSessionTemplate.selectOne(getSqlName("selectByTitleId"), titleId);
        return remark;
    }
}
