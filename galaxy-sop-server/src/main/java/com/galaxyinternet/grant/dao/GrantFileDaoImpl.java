package com.galaxyinternet.grant.dao;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.GrantFileDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.GrantFile;

@Repository("grantFileDao")
public class GrantFileDaoImpl extends BaseDaoImpl<GrantFile, Long> implements GrantFileDao{

}
