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
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.hologram.InformationDictionary;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.service.hologram.InformationDictionaryService;
import com.galaxyinternet.service.hologram.InformationTitleService;


@Service("com.galaxyinternet.service.hologram.InformationDictionaryService")
public class InformationDictionaryServiceImpl extends BaseServiceImpl<InformationDictionary> implements InformationDictionaryService{

	@Autowired
	private InformationDictionaryDao informationDictionaryDao;
	
	@Autowired
	private InformationTitleService informationTitleService;
	
	@Override
	protected BaseDao<InformationDictionary, Long> getBaseDao() {
		return this.informationDictionaryDao;
	}

	
	
	/**
	 * 根据 title.id  查询 value 集合
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
	 * 根据 code 、 id  的 title 信息查询 title value 信息集合
	 */
	@Override
	@Transactional
	public InformationTitle selectValuesByTinfo(String pinfoKey) {
		InformationTitle ptitle = informationTitleService.selectTitleByPinfo(pinfoKey);
		List<InformationDictionary> valueList = selectValuesByTid(ptitle.getId());
		ptitle.setValueList(valueList);
		return ptitle;
	}
	
	
	
	
	
	/**
	 * 根据 code 、 id  的 title 信息  递归查询 各 title下的 value
	 */
	@Override
	@Transactional
	public InformationTitle selectTitlesValues(String pinfoKey) {
		InformationTitle ptitle = selectValuesByTinfo(pinfoKey);
		List<InformationTitle> childList = selectByTlist(informationTitleService.selectChildsByPid(ptitle.getId()));
		ptitle.setChildList(childList);
		return ptitle;
	}
	public List<InformationTitle> selectByTlist(List<InformationTitle> tList) {
		for(InformationTitle title : tList){
			List<InformationDictionary> valueList = selectValuesByTid(title.getId());
			title.setValueList(valueList);
			List<InformationTitle> ptitleList = informationTitleService.selectChildsByPid(title.getId());
			if(ptitleList !=null && !ptitleList.isEmpty()) selectByTlist(ptitleList);
			title.setChildList(tList);
		}
		return tList;
	}
	
	
	
	
	
}


