package com.galaxyinternet.idea;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.dao.idea.IdeaDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.idea.Idea;
@Repository
public class IdeaDaoImpl extends BaseDaoImpl<Idea, Long>implements IdeaDao {

	

}
