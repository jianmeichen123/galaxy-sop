package com.galaxyinternet.service.hologram;
import java.lang.reflect.InvocationTargetException;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationOperationTime;

public interface InformationOperationTimeService extends BaseService<InformationOperationTime>{

	public void updateInformationTime(InformationOperationTime infromation) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	public InformationOperationTime getInformationTime(InformationOperationTime infromation) throws Exception;


}
