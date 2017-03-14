package com.galaxyinternet.hologram.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.model.hologram.InformationDictionary;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.hologram.CacheOperationService;
import com.galaxyinternet.service.hologram.InformationDictionaryService;
import com.galaxyinternet.service.hologram.InformationTitleService;
import com.galaxyinternet.utils.SopConstatnts;


@Service("com.galaxyinternet.service.CacheOperationService")
public class CacheOperationServiceImpl implements CacheOperationService{
	public static final String CACHE_KEY_PAGE_AREA = "QXT_PAGE_AREA_";               //各区域块下的   题：value   ==  List<InformationTitle>
	public static final String CACHE_KEY_PAGE_AREA_HASKEY = "QXT_PAGE_AREA_KEYLIST"; //各区域块下的   题：code   ==  List<String>
	
	public static final String CACHE_KEY_TITLE_ID_NAME = "QXT_TITLE_ID_NAME"; //各区域块下的   题：value   ==  Map<Long,String>
	
	public static final String CACHE_KEY_VALUE_ID_NAME = "QXT_VALUE_ID_NAME"; //各区域块下的   题：value   ==  Map<Long,String>

	/*
	public static final Map<String,List<String>> PAGE_AREA;
	static{
		List<String> page1 = new ArrayList<String>();
		page1.add("");
		page1.add("");
		PAGE_AREA.put("page1", page1);
	}
	*/
	
	
	private Map<String,List<InformationTitle>> pagesAreacode = new HashMap<String,List<InformationTitle>>();
	private List<String> cacheAreascode = new ArrayList<String>();
	
	private Map<Long,String> titleIdName = new HashMap<Long,String>();
	
	private Map<Long,String> valueIdName = new HashMap<Long,String>();
	
	
	public Map<String, List<InformationTitle>> getPagesAreacode() {
		return pagesAreacode;
	}
	public void setPagesAreacode(Map<String, List<InformationTitle>> pagesAreacode) {
		this.pagesAreacode = pagesAreacode;
	}
	public List<String> getCacheAreascode() {
		return cacheAreascode;
	}
	public void setCacheAreascode(List<String> cacheAreascode) {
		this.cacheAreascode = cacheAreascode;
	}
	public Map<Long, String> getTitleIdName() {
		return titleIdName;
	}
	public void setTitleIdName(Map<Long, String> titleIdName) {
		this.titleIdName = titleIdName;
	}
	public Map<Long, String> getValueIdName() {
		return valueIdName;
	}
	public void setValueIdName(Map<Long, String> valueIdName) {
		this.valueIdName = valueIdName;
	}


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
	


	
	public void initTitleIdName() {
		//title 表  id - name
		//cache.remove(CacheOperationServiceImpl.CACHE_KEY_TITLE_ID_NAME);
		List<InformationTitle> allTitle = informationTitleService.queryAll();
		for(InformationTitle atitle : allTitle){
			titleIdName.put(atitle.getId(), atitle.getName());
		}
		cache.set(CacheOperationServiceImpl.CACHE_KEY_TITLE_ID_NAME , titleIdName);

	}
	
	
	

	public void initValueIdName() {
		//title 表  id - name
		//cache.remove(CacheOperationServiceImpl.CACHE_KEY_VALUE_ID_NAME);
		List<InformationDictionary> allValue = informationDictionaryService.queryAll();
		for(InformationDictionary avalue : allValue){
			valueIdName.put(avalue.getId(), avalue.getName());
		}
		cache.set(CacheOperationServiceImpl.CACHE_KEY_VALUE_ID_NAME , valueIdName);

	}
	
	
	
	/*
	public void initAreaTitleValue() {
		cache.remove(CacheOperationServiceImpl.CACHE_KEY_PAGE_AREA);
		//顶级：基本信息 ……
		List<InformationTitle> title_0_List = informationTitleService.selectFirstTitle();
		
		for(InformationTitle title_0 : title_0_List){
			//第一页下的各区域：基础信息 、事业部……
			List<InformationTitle> title_List =  informationTitleService.selectChildsByPid(title_0.getId());
			for(InformationTitle title_1 : title_List){
				List<InformationTitle> title_title_value =  informationDictionaryService.selectTsTvalueInfo(title_0.getId());
				pagesAreacode.put(title_1.getCode(), title_title_value);
			}
		}
		cache.set(CacheOperationServiceImpl.CACHE_KEY_PAGE_AREA , pagesAreacode);
	}
	*/
	public void initAreaTitleValue() {
		//顶级：基本信息 ……
		List<InformationTitle> title_0_List = informationTitleService.selectFirstTitle();
		
		for(InformationTitle title_0 : title_0_List){
			List<InformationTitle> title_List =  informationTitleService.selectChildsByPid(title_0.getId()); //第一页下的各区域：基础信息 、事业部……
			for(InformationTitle title_1 : title_List){
				List<InformationTitle> title_title_value =  informationDictionaryService.selectTsTvalueInfo(title_1.getId());
				pagesAreacode.put(title_1.getCode(), title_title_value);
				cacheAreascode.add(title_1.getCode());
				cache.set(CacheOperationServiceImpl.CACHE_KEY_PAGE_AREA +title_1.getCode(), title_title_value);
			}
		}
		
		cache.set(CacheOperationServiceImpl.CACHE_KEY_PAGE_AREA_HASKEY, cacheAreascode);
	}
	
	
	
}
