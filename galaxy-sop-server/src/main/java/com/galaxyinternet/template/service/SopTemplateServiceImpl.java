package com.galaxyinternet.template.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.template.SopTemplateDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.dict.Dict;
import com.galaxyinternet.model.template.SopTemplate;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.DictService;
import com.galaxyinternet.service.SopTemplateService;
@Service
public class SopTemplateServiceImpl extends BaseServiceImpl<SopTemplate>implements SopTemplateService {

	@Autowired
	private SopTemplateDao tempalteDao;
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	@Autowired
	private DictService dictService;
	@Autowired
	private DepartmentService deptService;
	
	@Override
	protected BaseDao<SopTemplate, Long> getBaseDao() {
		// TODO Auto-generated method stub
		return this.tempalteDao;
	}

	public Map<String,Object> getRelatedData()
	{
		Map<String,Object> dataMap = new HashMap<String,Object>();
		String key = "fileType";
		Object data = getCacheData(key,new DataNotExistHandler(){
			@Override
			public Object handle() {
				return dictService.selectByParentCode("fileType");
			}
		});
		dataMap.put(key, data);
		
		key = "fileWorktype";
		data = getCacheData(key,new DataNotExistHandler(){
			@Override
			public Object handle() {
				return dictService.selectByParentCode("fileWorktype");
			}
		});
		dataMap.put(key, data);
		
		key = "department";
		data = getCacheData(key,new DataNotExistHandler(){
			@Override
			public Object handle() {
				return deptService.queryAll();
			}
		});
		dataMap.put(key, data);
		return dataMap;
	}
	
	private Object getCacheData(String key, DataNotExistHandler handler)
	{
		
		Object data = cache.get(key);
		if(data == null)
		{
			data = handler.handle();
			cache.set(key, data);
		}
		return data;
	}
	
	private abstract class DataNotExistHandler
	{
		public abstract Object handle();
	}

}
