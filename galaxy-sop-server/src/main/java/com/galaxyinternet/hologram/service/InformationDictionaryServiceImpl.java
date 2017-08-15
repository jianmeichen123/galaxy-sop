package com.galaxyinternet.hologram.service;

import com.galaxyinternet.dao.hologram.InformationDictionaryDao;
import com.galaxyinternet.dao.hologram.InformationListdataDao;
import com.galaxyinternet.dao.hologram.InformationListdataRemarkDao;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.hologram.InformationDictionary;
import com.galaxyinternet.model.hologram.InformationListdata;
import com.galaxyinternet.model.hologram.InformationListdataRemark;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.service.hologram.CacheOperationService;
import com.galaxyinternet.service.hologram.InformationDictionaryService;
import com.galaxyinternet.service.hologram.InformationTitleRelateService;
import com.galaxyinternet.service.hologram.InformationTitleService;
import com.galaxyinternet.utils.SopConstatnts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("com.galaxyinternet.service.hologram.InformationDictionaryService")
public class InformationDictionaryServiceImpl extends BaseServiceImpl<InformationDictionary> implements InformationDictionaryService{
	final Logger logger = LoggerFactory.getLogger(InformationDictionaryServiceImpl.class);

	@Autowired
	private Cache cache;

	@Autowired
	private InformationListdataRemarkDao informationListdataRemarkDao;
	@Autowired
	private InformationListdataDao informationListdataDao;

	
	@Autowired
	private CacheOperationService cacheOperationService;
	
	@Autowired
	private InformationDictionaryDao informationDictionaryDao;
	
	@Autowired
	private InformationTitleService informationTitleService;
	
	@Autowired
	private InformationTitleRelateService informationTitleRelateService;
	
	@Override
	protected BaseDao<InformationDictionary, Long> getBaseDao() {
		return this.informationDictionaryDao;
	}

	
	/**
	 * 根据 value.pid  查询其下级  value 集合
	 */
	@Override
	@Transactional
	public List<InformationDictionary> selectValuesByVpid(Long pid) {
		Direction direction = Direction.ASC;
		String property = "sort";
		
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("parentId",pid);
		params.put("isValid",0);
		params.put("sorting", new Sort(direction, property).toString().replace(":", ""));
		List<InformationDictionary> ptitleList = informationDictionaryDao.selectValues(params);
		
		return ptitleList == null ? new ArrayList<InformationDictionary>() : ptitleList;
	}
	
	
	
	/**
	 * 根据 title.id  查询其  value 集合
	 */
	@Override
	@Transactional
	public List<InformationDictionary> selectValuesByTid(Long tid) {
		Direction direction = Direction.ASC;
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

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<InformationTitle> selectTsTvalueInfoByCache(Object pinfoKey) {
		
		boolean useC = false;
		InformationTitle ptitles = new InformationTitle();
		List<InformationTitle> titles = new ArrayList<InformationTitle>();
		Object getR = null;
		List<String> cacheKey = new ArrayList<String>();
				
		Object getK = cache.get(CacheOperationServiceImpl.CACHE_KEY_PAGE_AREA_TITLE_HASKEY);
		if(getK != null){
			cacheKey = (List<String>) getK;
			if(cacheKey.contains(pinfoKey)){
				useC = true;
				getR = cache.get(CacheOperationServiceImpl.CACHE_KEY_PAGE_AREA_TITLE +pinfoKey.toString());
				if(getR!=null){
					ptitles = (InformationTitle) getR;
					titles = ptitles.getChildList();
				}
			}
		}
		
		if(!useC){
			ptitles = selectTitleAndTsTvalues(pinfoKey);
			titles = ptitles.getChildList();
			final String k1 = pinfoKey.toString();
			final InformationTitle ts = ptitles;
			
			GalaxyThreadPool.getExecutorService().execute(new Runnable() {
				@Override
				public void run() {
					cacheOperationService.saveAreaInfoByRedies(k1, ts);
				}
			});
		}
		
		
		return titles;
	}
	 */
	
	
	
	
	/**
	 * 传入题 id 或  code， 返回该题信息，及该题的下一级的 题及value 信息
	 */
	@Override
	@Transactional
	public InformationTitle selectTitleAndTsTvalues(Object pinfoKey) {
		
		InformationTitle ptitle = informationTitleService.selectTitleByPinfo(pinfoKey.toString());
		List<InformationDictionary> pvalueList = selectValuesByTid(ptitle.getId());
		ptitle.setValueList(pvalueList);
		
		List<InformationTitle> ts = informationTitleService.selectChildsByPid(ptitle.getId());
		for(InformationTitle title : ts){
			List<InformationDictionary> valueList = selectValuesByTid(title.getId());
			title.setValueList(valueList);
		}
		ptitle.setChildList(ts);
		return ptitle;
	}
	/**
	 * 运用cache , 传入题 id 或  code， 返回该题信息，及该题的下一级的 题及value 信息

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public InformationTitle selectTitleAndTsTvaluesByCache(Object pinfoKey) {

		boolean useC = false;
		InformationTitle ptitles = new InformationTitle();
		Object getR = null;
		List<String> cacheKey = new ArrayList<String>();
				
		Object getK = cache.get(CacheOperationServiceImpl.CACHE_KEY_PAGE_AREA_TITLE_HASKEY);
		if(getK != null){
			cacheKey = (List<String>) getK;
			if(cacheKey.contains(pinfoKey)){
				useC = true;
				getR = cache.get(CacheOperationServiceImpl.CACHE_KEY_PAGE_AREA_TITLE +pinfoKey.toString());
				if(getR!=null){
					ptitles = (InformationTitle) getR;
				}
			}
		}
		
		if(!useC){
			ptitles = selectTitleAndTsTvalues(pinfoKey);
			
			final String k1 = pinfoKey.toString();
			final InformationTitle ts = ptitles;
			
			GalaxyThreadPool.getExecutorService().execute(new Runnable() {
				@Override
				public void run() {
					cacheOperationService.saveAreaInfoByRedies(k1, ts);
				}
			});
		}
		
		return ptitles;
	}
	 */
	
	
	
	
	/**
	 * 根据  title的id  递归查询 该 title下的各级的 title - value
	 */
	/*@Override
	@Transactional
	public List<InformationTitle> selectTitlesValues(Long titleId) {
		List<InformationTitle> childList = selectByTlist(informationTitleService.selectChildsByPid(titleId));
		return childList;
	}*/



	/**
	 * 根据title 的  id 或 code ，  递归查询  title 及其下的所有 title - value
	 */
	@Override
	public InformationTitle selectTitlesValuesForAll(String pinfoKey, String reportType) {
		InformationTitle title = null;

		boolean useCacha = true; //开关
		boolean toCache = false;

		try {
			String key_pre = SopConstatnts.Redis.ALL_TITLE_VALUE_CACHE_PRE_KEY;
			if(useCacha){
				Object kv = cache.get(key_pre+pinfoKey);
				if(kv != null) title = (InformationTitle) kv;

				if(title == null){
					toCache = true;
				}else{
					toCache = false;
				}
			}
		} catch (Exception e) {
			logger.error("selectTitlesValuesForAll cache use failed："+pinfoKey,e);
		}

		if(title == null){
			if(reportType != null ){
				Map<String, Object> params = new HashMap<String,Object>();
				params.put("reportType",reportType);
				params.put("code",pinfoKey);
				params.put("isValid",0);
				List<InformationTitle> titles = informationTitleRelateService.selectTitleByRelate(params);
				if(titles != null &&  titles.size() == 1){
					if(reportType.equals("1")|| reportType.equals("6")){
						title = selectTitlesValuesGrade(titles.get(0));
					}else{
						title = selectTitlesValues(titles.get(0));
					}
				}
			}else{
				title = selectTitlesValues(pinfoKey);
			}
		}
		if(useCacha && toCache && title!=null){
			cacheOperationService.saveAllByRedies(pinfoKey, title);
		}

		return title;
	}
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
	@Override
	public InformationTitle selectTitlesValues(InformationTitle info) {
		List<InformationDictionary> valueList = selectValuesByTid(info.getTitleId());
		info.setValueList(valueList);
		List<InformationTitle> childList = selectTitlesByRelate(informationTitleRelateService.selectChildsByPid(info.getId()));
		info.setChildList(childList);
		return info;
	}
	public List<InformationTitle> selectTitlesByRelate(List<InformationTitle> tList) {
		for(InformationTitle title : tList){
			List<InformationDictionary> valueList = selectValuesByTid(title.getTitleId());
			title.setValueList(valueList);
			List<InformationTitle> ptitleList = informationTitleRelateService.selectChildsByPid(title.getId());
			if(ptitleList !=null && !ptitleList.isEmpty()){
				selectTitlesByRelate(ptitleList);
				title.setChildList(ptitleList);
			} 
		}
		return tList;
	}
	@Override
	public InformationTitle selectTitlesValuesGrade(InformationTitle info) {
		List<InformationDictionary> valueList = selectValuesByTid(info.getTitleId());
		info.setValueList(valueList);

		List<InformationTitle> childList = selectTitlesGradeByRelate(informationTitleRelateService.selectChildsGradeByPid(info.getId()));
		info.setChildList(childList);
		return info;
	}
	public List<InformationTitle> selectTitlesGradeByRelate(List<InformationTitle> tList) {
		for(InformationTitle title : tList){
			List<InformationDictionary> valueList = selectValuesByTid(title.getTitleId());
			title.setValueList(valueList);
			List<InformationTitle> ptitleList = informationTitleRelateService.selectChildsGradeByPid(title.getId());
			if(ptitleList !=null && !ptitleList.isEmpty()){
				selectTitlesGradeByRelate(ptitleList);
				title.setChildList(ptitleList);
			} 
		}
		return tList;
	}



	/*
	特别定制，拼装后返回 valueList
	综合竞争比较 table code : competition-comparison
	ENO4_4_2   ： -valuelist  field_1 field_2
	ENO4_4_5   ： -valuelist  field_1 field_4
	*/
	public static Map<String,Map<String,List<String>>>  relateCode_tableCode_fieldList = new HashMap<>();
	static {
		//================  胜算度
		List<String> fieldList = new ArrayList<>();
		fieldList.add("field1");
		fieldList.add("field2");

		Map<String,List<String>> tableCode_fieldList = new HashMap<>();
		tableCode_fieldList.put("competition-comparison",fieldList);

		relateCode_tableCode_fieldList.put("ENO4_4_2",tableCode_fieldList);


		//=================  差异化策略
		fieldList = new ArrayList<>();
		fieldList.add("field1");
		fieldList.add("field4");

		tableCode_fieldList = new HashMap<>();
		tableCode_fieldList.put("competition-comparison",fieldList);

		relateCode_tableCode_fieldList.put("ENO4_4_5",tableCode_fieldList);
	}

	/**
	 * 特别定制，拼装后返回 valueList
	 *
	 * 胜算度查询:
	 * 	主要竞争对手  field_1  + 胜算度 field_2
	 *
	 * 差异化策略:
	 * 	field_1 + field_4
	 *
	 */
	@Override
	public void setValuesForTitleByTable(Long proId, InformationTitle title) throws Exception {

		if(relateCode_tableCode_fieldList.containsKey(title.getRelateCode()))
		{
			List<InformationDictionary> vs = selectValuesByTable(proId,title.getRelateCode());
			if(vs!= null && !vs.isEmpty()){
				title.setValueList(vs);
			}
		}

		if(title.getChildList()!=null && !title.getChildList().isEmpty()){
			for(InformationTitle temp : title.getChildList()){
				setValuesForTitleByTable(proId, temp);
			}
		}
	}
	@Override
	public List<InformationDictionary> selectValuesByTable(Long  proid, String relateCode) throws Exception {
		List<InformationDictionary> valueList = new ArrayList<>();

		// 得到 relatecode 要取值的表格 code 和 要取值的 field name
		Map<String,List<String>> tableCode_fieldList = relateCode_tableCode_fieldList.get(relateCode);
		String tableCode = tableCode_fieldList.keySet().iterator().next();
		List<String> fieldList = tableCode_fieldList.values().iterator().next();

		// 根据得到的 表格 code -->  title id
		InformationListdataRemark remark = new InformationListdataRemark();
		remark.setCode(tableCode);
		remark = informationListdataRemarkDao.selectOne(remark);
		Long titleId = remark.getTitleId();

		// 根据得到的 titleid 、 project id -->  表格的结果集
		InformationListdata dataq = new InformationListdata();
		dataq.setProjectId(proid);
		dataq.setTitleId(titleId);
		List<InformationListdata> listdata = informationListdataDao.selectList(dataq);

		// 封装取得结果集 --> valueList
		InformationDictionary informationDictionary = null;
		for(InformationListdata temp : listdata){
			informationDictionary = new InformationDictionary();
			String value = null;
			// 取值 赋值
			for(int i = 0 ; i < fieldList.size(); i++){
				//首字母转大写
				String newStr=fieldList.get(i).substring(0, 1).toUpperCase()+fieldList.get(i).replaceFirst("\\w","");
				String methodStr="get"+newStr;
				Method getMethod = InformationListdata.class.getMethod(methodStr);
				String fileValue = (String) getMethod.invoke(temp);

				String setMethodStr = "set"+"Content" + (i+1);
				Method setMethod = InformationDictionary.class.getMethod(setMethodStr,String.class);
				setMethod.invoke(informationDictionary,fileValue);

				if(i == 0){
					value = fileValue;
				}else{
					value += " " + fileValue;
				}
			}
			informationDictionary.setValue(value);

			valueList.add(informationDictionary);
		}
		return valueList;
	}


	
}


