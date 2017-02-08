package com.galaxyinternet.touhou.dao;

import org.springframework.stereotype.Repository;
import com.galaxyinternet.dao.touhou.OperationalDataDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.touhou.OperationalData;

@Repository("operationalDataDao")
public class OperationalDataDaoImpl extends BaseDaoImpl<OperationalData, Long> implements OperationalDataDao{

}
