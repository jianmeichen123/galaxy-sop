package com.galaxyinternet.touhou.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.bo.OperationalDataBo;
import com.galaxyinternet.dao.touhou.OperationalDataDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.touhou.OperationalData;
import com.galaxyinternet.service.OperationalDataService;


@Service("com.galaxyinternet.service.OperationalDataService")
public class OperationalDataServiceImpl extends BaseServiceImpl<OperationalData> implements OperationalDataService{

	@Autowired
	private OperationalDataDao operationalDataDao;
	
	@Override
	public Long insertOperationalData(OperationalData operationalData) {
		// TODO Auto-generated method stub
		return operationalDataDao.insert(operationalData);
	}

	@Override
	protected BaseDao<OperationalData, Long> getBaseDao() {
		// TODO Auto-generated method stub
		return this.operationalDataDao;
	}

	@Override
	public OperationalData selectOperationalDataById(Long operationalDataId) {
		// TODO Auto-generated method stub
		return operationalDataDao.selectById(operationalDataId);
	}

	@Override
	public void deleteOperationalDataById(Long operationalDataId) {
		// TODO Auto-generated method stub
		operationalDataDao.deleteById(operationalDataId);
	}

	@Override
	public Page<OperationalData> queryOperationalDataPageList(
			OperationalData query, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return operationalDataDao.selectOperationalDataPageList(query, pageRequest);
	}

}
