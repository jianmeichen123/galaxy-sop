package com.galaxyinternet.hologram.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.hologram.InformationResultDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.framework.core.utils.StringEx;
import com.galaxyinternet.model.hologram.InformationData;
import com.galaxyinternet.model.hologram.InformationModel;
import com.galaxyinternet.model.hologram.InformationResult;
import com.galaxyinternet.service.hologram.InformationDataService;

@Service
public class InformationDataServiceImpl extends BaseServiceImpl<InformationData>implements InformationDataService
{

	@Autowired
	private InformationResultDao resultDao;

	@Override
	public void save(InformationData data)
	{
		String projectId = data.getProjectId();
		List<InformationModel> list = data.getInfoModeList();
		if(projectId == null || list == null || list.size() ==0)
		{
			return;
		}
		
		String titleId = null;
		InformationResult entity = null;
		List<InformationResult> entityList = new ArrayList<>();
		Set<String> titleIds = new HashSet<>();
		for(InformationModel model : list)
		{
			titleIds.add(model.getTitleId());
			entity = new InformationResult();
			entity.setProjectId(projectId);
			entity.setTitleId(titleId);
			if(!StringEx.isNullOrEmpty(model.getValue()))
			{
				entity.setContentChoose(model.getValue());
			}
			if(!StringEx.isNullOrEmpty(model.getRemark()))
			{
				entity.setContentDescribe(model.getRemark());
			}
			entityList.add(entity);
		}
		InformationResult query = new InformationResult();
		query.setProjectId(projectId);
		query.setTitleIds(titleIds);
		resultDao.delete(query);
		//插入数据
		if(entityList.size() > 0)
		{
			resultDao.insertInBatch(entityList);
		}
		
	}

	@Override
	protected BaseDao<InformationData, Long> getBaseDao()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
