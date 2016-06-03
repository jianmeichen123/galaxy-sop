package com.galaxyinternet.progresslog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.common.ProgressLogDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.common.ProgressLog;
import com.galaxyinternet.service.ProgressLogService;
@Service
public class ProgressLogServiceImpl extends BaseServiceImpl<ProgressLog>implements ProgressLogService {

	@Autowired
	private ProgressLogDao progressLogDao;
	@Override
	protected BaseDao<ProgressLog, Long> getBaseDao() {
		return progressLogDao;
	}

}
