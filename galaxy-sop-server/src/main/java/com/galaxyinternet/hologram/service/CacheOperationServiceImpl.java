package com.galaxyinternet.hologram.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.model.hologram.InformationDictionary;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.service.hologram.CacheOperationService;
import com.galaxyinternet.service.hologram.InformationDictionaryService;
import com.galaxyinternet.service.hologram.InformationTitleService;



@Service("com.galaxyinternet.service.CacheOperationService")
public class CacheOperationServiceImpl implements CacheOperationService{
	
	public static final String CACHE_KEY_PAGE_AREA = "QXT_PAGE_AREA_";               //各区域块下的   题：value   ==  List<InformationTitle>
	public static final String CACHE_KEY_PAGE_AREA_HASKEY = "QXT_PAGE_AREA_KEYLIST"; //各区域块下的   题：code   ==  List<String>
	
	public static final String CACHE_KEY_TITLE_ID_NAME = "QXT_TITLE_ID_NAME"; //各区域块下的   题：value   ==  Map<Long,String>
	
	public static final String CACHE_KEY_VALUE_ID_NAME = "QXT_VALUE_ID_NAME"; //各区域块下的   题：value   ==  Map<Long,String>

	
	@Autowired
	private Cache cache;
	
	@Autowired
	private InformationTitleService informationTitleService;
	
	@Autowired
	private InformationDictionaryService informationDictionaryService;
	
	
	//阻塞判断
	private static boolean hasLock = false;
	
	
	/**
	 * 启动时：
	 * 1、清空缓存
	 * 2、写入缓存
	*/
	@PostConstruct  
    public void  init(){ 
		initTitleIdName();
		initValueIdName();
		initAreaTitleValue();
		
	}
	
	
	//title 表  id - name
	public void initTitleIdName() {
		Map<Long,String> titleIdName = new HashMap<Long,String>();
		
		//cache.remove(CacheOperationServiceImpl.CACHE_KEY_TITLE_ID_NAME);
		List<InformationTitle> allTitle = informationTitleService.queryAll();
		for(InformationTitle atitle : allTitle){
			titleIdName.put(atitle.getId(), atitle.getName());
		}
		cache.set(CacheOperationServiceImpl.CACHE_KEY_TITLE_ID_NAME , titleIdName);

	}
	

	//title 表  id - name
	public void initValueIdName() {
		Map<Long,String> valueIdName = new HashMap<Long,String>();
		
		//cache.remove(CacheOperationServiceImpl.CACHE_KEY_VALUE_ID_NAME);
		List<InformationDictionary> allValue = informationDictionaryService.queryAll();
		for(InformationDictionary avalue : allValue){
			valueIdName.put(avalue.getId(), avalue.getName());
		}
		cache.set(CacheOperationServiceImpl.CACHE_KEY_VALUE_ID_NAME , valueIdName);

	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public void initAreaTitleValue() {
		//Map<String,List<InformationTitle>> pagesAreacode = new HashMap<String,List<InformationTitle>>();
		List<String> cacheAreascode = new ArrayList<String>();
		
		Object getK = cache.get(CacheOperationServiceImpl.CACHE_KEY_PAGE_AREA_HASKEY);
		if(getK != null){
			List<String> cacheKey = (List<String>) getK;
			for(String ak : cacheKey){
				cache.remove(CacheOperationServiceImpl.CACHE_KEY_PAGE_AREA +ak);
			}
		}
		
		//顶级：基本信息 ……
		List<InformationTitle> title_0_List = informationTitleService.selectFirstTitle();
		
		for(InformationTitle title_0 : title_0_List){
			List<InformationTitle> title_List =  informationTitleService.selectChildsByPid(title_0.getId()); //第一页下的各区域：基础信息 、事业部……
			for(InformationTitle title_1 : title_List){
				List<InformationTitle> title_title_value =  informationDictionaryService.selectTsTvalueInfo(title_1.getId());
				//pagesAreacode.put(title_1.getCode(), title_title_value);
				cacheAreascode.add(title_1.getCode());
				cache.set(CacheOperationServiceImpl.CACHE_KEY_PAGE_AREA +title_1.getCode(), title_title_value);
			}
		}
		
		cache.set(CacheOperationServiceImpl.CACHE_KEY_PAGE_AREA_HASKEY, cacheAreascode);
	}
	
	
	
}
