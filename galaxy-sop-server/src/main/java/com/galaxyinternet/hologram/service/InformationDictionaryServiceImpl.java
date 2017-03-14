package com.galaxyinternet.hologram.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.dao.hologram.InformationDictionaryDao;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.hologram.InformationDictionary;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.service.hologram.InformationDictionaryService;
import com.galaxyinternet.service.hologram.InformationTitleService;


@Service("com.galaxyinternet.service.hologram.InformationDictionaryService")
public class InformationDictionaryServiceImpl extends BaseServiceImpl<InformationDictionary> implements InformationDictionaryService{

	@Autowired
	private Cache cache;
	
	@Autowired
	private InformationDictionaryDao informationDictionaryDao;
	
	@Autowired
	private InformationTitleService informationTitleService;
	
	@Override
	protected BaseDao<InformationDictionary, Long> getBaseDao() {
		return this.informationDictionaryDao;
	}

	
	
	/**
	 * 根据 title.id  查询其  value 集合
	 */
	@Override
	@Transactional
	public List<InformationDictionary> selectValuesByTid(Long tid) {
		Direction direction = Direction.DESC;
		String property = "sort";
		
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("titleId",tid);
		params.put("isValid",0);
		params.put("sorting", new Sort(direction, property).toString().replace(":", ""));
		List<InformationDictionary> ptitleList = informationDictionaryDao.selectValues(params);
		
		return ptitleList == null ? new ArrayList<InformationDictionary>() : ptitleList;
	}
	
	
	
	
	
	/**
	 * 根据title 的  id 或 code ，查询 title及其value集合信息
	 */
	@Override
	@Transactional
	public InformationTitle selectValuesByTinfo(String pinfoKey) {
		InformationTitle ptitle = informationTitleService.selectTitleByPinfo(pinfoKey);
		if(ptitle!=null){
			List<InformationDictionary> valueList = selectValuesByTid(ptitle.getId());
			ptitle.setValueList(valueList);
		}
		return ptitle;
	}
	
	
	
	
	/**
	 * 根据title的 id或 code ，查询 该title下一级的 title-value
	 */
	@Override
	@Transactional
	public List<InformationTitle> selectTsTvalueInfo(Object pinfoKey) {
		List<InformationTitle> ts = null;
		if(pinfoKey instanceof Long){
			ts = informationTitleService.selectChildsByPid((long)pinfoKey);
		}else{
			InformationTitle ptitle = informationTitleService.selectTitleByPinfo(pinfoKey.toString());
			ts = informationTitleService.selectChildsByPid(ptitle.getId());
		}
		for(InformationTitle title : ts){
			List<InformationDictionary> valueList = selectValuesByTid(title.getId());
			title.setValueList(valueList);
		}
		return ts;
	}
	
	
	/**
	 * 运用cache ,根据title的 id或 code ，查询 该title下一级的 title-value
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<InformationTitle> selectTsTvalueInfoByCache(Object pinfoKey) {
		
		boolean useC = false;
		List<InformationTitle> titles = new ArrayList<InformationTitle>();
		Object getR = null;
		List<String> cacheKey = new ArrayList<String>();
				
		Object getK = cache.get(CacheOperationServiceImpl.CACHE_KEY_PAGE_AREA_HASKEY);
		if(getK != null){
			cacheKey = (List<String>) getK;
			if(cacheKey.contains(pinfoKey)){
				useC = true;
				getR = cache.get(CacheOperationServiceImpl.CACHE_KEY_PAGE_AREA +pinfoKey);
				if(getR!=null){
					titles = (List<InformationTitle>) getR;
				}
			}
		}
		
		if(!useC){
			titles = selectTsTvalueInfo(pinfoKey);
			cacheKey.add(pinfoKey.toString());
			cache.set(CacheOperationServiceImpl.CACHE_KEY_PAGE_AREA_HASKEY, cacheKey);
			cache.set(CacheOperationServiceImpl.CACHE_KEY_PAGE_AREA +pinfoKey, titles);
		}
		
		
		return titles;
	}
	
	
	
	
	
	
	
	/**
	 * 根据  title的id  递归查询 该 title下的各 title - value
	 */
	@Override
	@Transactional
	public List<InformationTitle> selectTitlesValues(Long titleId) {
		List<InformationTitle> childList = selectByTlist(informationTitleService.selectChildsByPid(titleId));
		return childList;
	}
	
	
	
	/**
	 * 根据title 的  id 或 code ，  递归查询  title 及其下的所有 title - value
	 */
	@Override
	@Transactional
	public InformationTitle selectTitlesValues(String pinfoKey) {
		InformationTitle ptitle = selectValuesByTinfo(pinfoKey);
		if(ptitle!=null){
			List<InformationTitle> childList = selectByTlist(informationTitleService.selectChildsByPid(ptitle.getId()));
			ptitle.setChildList(childList);
		}
		return ptitle;
	}
	public List<InformationTitle> selectByTlist(List<InformationTitle> tList) {
		for(InformationTitle title : tList){
			List<InformationDictionary> valueList = selectValuesByTid(title.getId());
			title.setValueList(valueList);
			List<InformationTitle> ptitleList = informationTitleService.selectChildsByPid(title.getId());
			if(ptitleList !=null && !ptitleList.isEmpty()){
				selectByTlist(ptitleList);
				title.setChildList(ptitleList);
			} 
		}
		return tList;

	}
	
	
	
	
	
	
}


