package com.galaxyinternet.hologram.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.hologram.InformationOperationTimeDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.hologram.InformationOperationTime;
import com.galaxyinternet.service.hologram.InformationOperationTimeService;

@Service("informationOperationTimeService")
public class InformationOperationTimeServiceImpl extends BaseServiceImpl<InformationOperationTime> implements InformationOperationTimeService{

	@Autowired
	private InformationOperationTimeDao informationOperationTimeDao;
	
	@Override
	protected BaseDao<InformationOperationTime, Long> getBaseDao() {
		// TODO Auto-generated method stub
		return this.informationOperationTimeDao;
	}

	@Override
	public void updateInformationTime(InformationOperationTime infromation) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		try {
			InformationOperationTime oldInformation = informationOperationTimeDao.selectOne(infromation);
			if(oldInformation == null){
				Long id = informationOperationTimeDao.insert(infromation);
				oldInformation = informationOperationTimeDao.selectById(id);
			}
			oldInformation.setReflect(infromation.getReflect());
			Method	method = (Method) oldInformation.getClass().getMethod("set"+oldInformation.getReflect().toUpperCase().substring(0, 1)+oldInformation.getReflect().substring(1),Date.class);
			method.invoke(oldInformation, new Date());
			informationOperationTimeDao.updateByIdSelective(oldInformation);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
