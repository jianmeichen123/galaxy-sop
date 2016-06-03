package com.galaxyinternet.idea;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.idea.AbandonedDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.idea.Abandoned;
@Repository
public class AbandonedDaoImpl extends BaseDaoImpl<Abandoned, Long>implements AbandonedDao {

	
 
}
