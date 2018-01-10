package com.galaxyinternet.project_danao;

import com.galaxyinternet.dao.SopSearchHistoryDao;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.model.SopSearchHistory;
import org.springframework.stereotype.Repository;


@Repository("sopSearchHistoryDao")
public class SopSearchHistoryDaoImpl extends BaseDaoImpl<SopSearchHistory, Long> implements SopSearchHistoryDao {

}