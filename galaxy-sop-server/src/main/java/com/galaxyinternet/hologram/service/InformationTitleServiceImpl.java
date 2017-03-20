package com.galaxyinternet.hologram.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.bo.hologram.InformationTitleBo;
import com.galaxyinternet.dao.hologram.InformationFixedTableDao;
import com.galaxyinternet.dao.hologram.InformationListdataDao;
import com.galaxyinternet.dao.hologram.InformationResultDao;
import com.galaxyinternet.dao.hologram.InformationTitleDao;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.hologram.InformationDictionary;
import com.galaxyinternet.model.hologram.InformationFixedTable;
import com.galaxyinternet.model.hologram.InformationListdata;
import com.galaxyinternet.model.hologram.InformationResult;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.service.hologram.InformationDictionaryService;
import com.galaxyinternet.service.hologram.InformationTitleService;

@Service("com.galaxyinternet.service.hologram.InformationTitleService")
public class InformationTitleServiceImpl extends BaseServiceImpl<InformationTitle> implements InformationTitleService{

	@Autowired
	private Cache cache;
	@Autowired
	private InformationTitleDao informationTitleDao;
	@Autowired
	private InformationResultDao resultDao;
	@Autowired
	private InformationFixedTableDao fixedTableDao;
	@Autowired
	private InformationListdataDao listDataDao;
	
	@Autowired
	private InformationDictionaryService informationDictionaryService;
	
	@Override
	protected BaseDao<InformationTitle, Long> getBaseDao() {
		return this.informationTitleDao;
	}

	
	
	
	// ===  TODO 字典功能
	
	
	/**
	 * 查询 parentid 为空的 题， 即顶级目录
	 */
	@Override
	public List<InformationTitle> selectFirstTitle() {
		List<InformationTitle> ptitleList = informationTitleDao.selectFirstTitle();
		ptitleList = ptitleList == null ? new ArrayList<InformationTitle>() : ptitleList;
		return ptitleList;
	}
	
	
	
	
	/**
	 * 根据 code 或 id 查询 本 title
	 */
	@Override
	public InformationTitle selectTitleByPinfo(String pinfoKey) {
		InformationTitleBo pquery = new InformationTitleBo();
		pquery.setIdcodekey(pinfoKey);
		
		InformationTitle title = informationTitleDao.selectOne(pquery);
		if(title!=null && title.getSign() != null && title.getSign().intValue() == 2){
			title.setName(title.getName()+":");
		}
		return title;
	}
	
	
	
	/**
	 * 根据父id 查询其子集
	 */
	@Override
	public List<InformationTitle> selectChildsByPid(Long pid) {
		Direction direction = Direction.ASC;
		String property = "index_no";
		
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("parentId",pid);
		params.put("isValid",0);
		params.put("sorting", new Sort(direction, property).toString().replace(":", ""));
		List<InformationTitle> ptitleList = informationTitleDao.selectChildsByPid(params);
		
		ptitleList = ptitleList == null ? new ArrayList<InformationTitle>() : ptitleList;
		
		for(InformationTitle title : ptitleList){
			if(title.getSign() != null && title.getSign().intValue() == 2){
				title.setName(title.getName()+":");
			}
		}
		
		return ptitleList;
	}
	
	
	
	/**
	 * 根据 code 或   id 查询其子集
	 */
	@Override
	public List<InformationTitle> selectChildsByPinfo(String pinfoKey) {
		List<InformationTitle> ptitleList = null;
		InformationTitle ptitle = selectTitleByPinfo(pinfoKey);
		if(ptitle != null){
			ptitleList = selectChildsByPid(ptitle.getId());
		}
		return ptitleList == null ? new ArrayList<InformationTitle>() : ptitleList;
	}
	
	
	/**
	 * 根据 code 或   id ， 查询 该题 及其下一级的 题 信息
	 */
	@Override
	public InformationTitle selectTChildsByPinfo(String pinfoKey) {
		
		InformationTitle ptitle = selectTitleByPinfo(pinfoKey);
		
		List<InformationTitle> ptitleList = null;
		if(ptitle != null){
			ptitleList = selectChildsByPid(ptitle.getId());
		}
		ptitle.setChildList(ptitleList);
		
		return ptitle;
	}
	
	
	
	
	
	/**
	 * 根据 code 、 id 父信息递归查询其下所有子集合
	 */
	@Override
	public InformationTitle selectPchildsByPinfo(String pinfoKey) {
		InformationTitle title = selectTitleByPinfo(pinfoKey);
		if(title != null){
			List<InformationTitle> childList = selectByTlist(selectChildsByPid(title.getId()));
			title.setChildList(childList);
		}
		return title;
	}
	public List<InformationTitle> selectByTlist(List<InformationTitle> tList) {
		for(InformationTitle title : tList){
			List<InformationTitle> ptitleList = selectChildsByPid(title.getId());
			if(ptitleList !=null && !ptitleList.isEmpty()){
				selectByTlist(ptitleList);
				title.setChildList(ptitleList);
			}
		}
		return tList;

	}



	
	
	
	
	// ===  TODO 页面功能
	
	
	/**
	 * 查看题和保存的结果信息
	 * 传入项目 id， 区域  code， 返回 该区域下 题和保存的结果信息
	 * return : 
	 *       title  - title
	 *                  - resultList
	 *              - title  
	 *                  - resultList   
	 */
	@Override
	public InformationTitle selectAreaTitleResutl(String pid, String pinfoKey) {
		List<InformationTitle> tchilds = null;
		List<InformationResult> results = null;
		
		InformationTitle title = selectTChildsByPinfo(pinfoKey); //得到父title
		
		if(title != null) tchilds = title.getChildList();  //得到子title
		
		//得到子title 所有  result 集合
		if( tchilds != null ){
			Set<String> tids = new HashSet<String>();
			for(InformationTitle at : tchilds ){
				tids.add(at.getId()+"");
			}
			results = selectResultByPidTids(pid , tids);   
		}
		
		//各 title result 对应封装
		if(results!=null && !results.isEmpty()){
			
			for(InformationTitle atitle : tchilds ){
				if(atitle.getType() != null){
					
					//1:文本、2:单选、3:复选、4:级联选择、5:单选带备注、6:复选带备注、7:附件、8:文本域、9:固定表格、10:动态表格、11:静态数据
					
					//Type ： 1 2 5 8 11
					if(atitle.getType().intValue() == 1 ||  atitle.getType().intValue() == 2 || atitle.getType().intValue() == 5 || atitle.getType().intValue() == 8 || atitle.getType().intValue() == 11 ){
						findResultByArecord(atitle, results);
					}//Type ： 3 4 6
					else if(atitle.getType().intValue() == 3 || atitle.getType().intValue() == 6 ){
						findResultByNrecord(atitle, results);  //findResultByNcontact
					}//Type ： 7 
					else if(atitle.getType().intValue() == 7){
						
					}//Type ： 9
					else if(atitle.getType().intValue() == 9){
						
					}//Type ： 10
					else if(atitle.getType().intValue() == 10){
						
					}
					
				}
			}
			
		}
		
		return title;
	}
	
	/**
	 * 题的值只能有一条记录的
	 * Type ： 1 2 5 8 11
	 */
	@SuppressWarnings("unchecked")
	public void findResultByArecord(InformationTitle atitle, List<InformationResult> results){
		
		List<InformationDictionary> values = atitle.getValueList();
		
		InformationResult isr = null;
		for(InformationResult aresult : results){
			
			if(Long.parseLong(aresult.getTitleId()) == atitle.getId().longValue() ){
				
				isr = new InformationResult();
				
				isr.setId(aresult.getId());
				if(aresult.getContentChoose() != null){
					isr.setValueId(Long.parseLong(aresult.getContentChoose()));
					isr.setValueName(((Map<Long, String>) cache.get(CacheOperationServiceImpl.CACHE_KEY_VALUE_ID_NAME)).get(isr.getValueId()));
				}
				isr.setContentDescribe1(aresult.getContentDescribe1());
				isr.setContentDescribe2(aresult.getContentDescribe2());
				
				//对应的结果放入 title ResultList 中
				List<InformationResult> isv = atitle.getResultList();
				if(isv == null){
					List<InformationResult> t_resultList = new ArrayList<InformationResult>();
					t_resultList.add(isr);
					atitle.setResultList(t_resultList);
				}else{
					atitle.getResultList().add(isr);
				}
				
				//结果对应 value list 中的数据，设置选中状态
				if(values != null && !values.isEmpty() && isr.getValueId()!=null){
					for(InformationDictionary avalue : values){
						if(avalue.getId().longValue() == isr.getValueId().longValue()){
							avalue.setChecked(true);
							break;
						}
					}
				}
				
				results.remove(aresult);
				break;
			}
		}
	}
	
	/**
	 * 题的值可能有多条记录的
	 * Type ： 3 4 6
	 */
	@SuppressWarnings("unchecked")
	public void findResultByNrecord(InformationTitle atitle, List<InformationResult> results){
		
		List<InformationResult> resultList_toremove = new ArrayList<InformationResult>();
		
		List<InformationDictionary> values = atitle.getValueList();
		
		InformationResult isr = null;
		for(InformationResult aresult : results){
			
			if(Long.parseLong(aresult.getTitleId()) == atitle.getId().longValue() ){
				isr = new InformationResult();
				
				isr.setId(aresult.getId());
				if(aresult.getContentChoose() != null){
					isr.setValueId(Long.parseLong(aresult.getContentChoose()));
					isr.setValueName(((Map<Long, String>) cache.get(CacheOperationServiceImpl.CACHE_KEY_VALUE_ID_NAME)).get(isr.getValueId()));
				}
				isr.setContentDescribe1(aresult.getContentDescribe1());
				isr.setContentDescribe2(aresult.getContentDescribe2());
				
				//对应的结果放入 title ResultList 中
				List<InformationResult> isv = atitle.getResultList();
				if(isv == null){
					List<InformationResult> t_resultList = new ArrayList<InformationResult>();
					t_resultList.add(isr);
					atitle.setResultList(t_resultList);
				}else{
					atitle.getResultList().add(isr);
				}
				
				//结果对应 value list 中的数据，设置选中状态
				if(values != null && !values.isEmpty() && isr.getValueId()!=null){
					for(InformationDictionary avalue : values){
						if(avalue.getId().longValue() == isr.getValueId().longValue()){
							avalue.setChecked(true);
							break;
						}
					}
				}
				
				//remove add
				resultList_toremove.add(aresult);
			}
		}
		
		for(InformationResult ar : resultList_toremove){
			results.remove(ar);
		}
		
	}
	
	
	
	
	/**
	 * 编辑区域查看题和保存的结果信息
	 * 传入项目 id， 区域  code， 返回 该区域下 题和保存的结果信息
	 * return : 
	 *       title  - title
	 *                  - valueList
	 *                  - resultList
	 *              - title  
	 *                  - valueList
	 *                  - resultList   
	 */
	@Override
	public InformationTitle editAreaTitleResutl(String pid, String pinfoKey) {
		
		InformationTitle title = informationDictionaryService.selectTitleAndTsTvaluesByCache(pinfoKey);  //得到 父 title
		
		List<InformationTitle> tchilds = null;
		List<InformationResult> results = null;
		
		if(title != null && title.getChildList() != null && !title.getChildList().isEmpty()){
			tchilds = title.getChildList();    //得到 子 title
			
			//得到 各 title 的 result 结果集
			Set<String> tids = new HashSet<String>();
			for(InformationTitle at : tchilds ){
				tids.add(at.getId()+"");
			}
			results = selectResultByPidTids(pid , tids);  
			
			
			//各 title result 对应封装，value 选中标记
			if(results != null && !results.isEmpty()){
				
				for(InformationTitle atitle : tchilds ){
					if(atitle.getType() != null){
						
						//1:文本、2:单选、3:复选、4:级联选择、5:单选带备注、6:复选带备注、7:附件、8:文本域、9:固定表格、10:动态表格、11:静态数据
						
						
						//Type ：1  2 5 8 11
						if(atitle.getType().intValue() == 1 ||  atitle.getType().intValue() == 2 || atitle.getType().intValue() == 5 || atitle.getType().intValue() == 8 || atitle.getType().intValue() == 11 ){
							findResultByArecord(atitle, results);
						}//Type ： 3 4 6
						else if(atitle.getType().intValue() == 3 || atitle.getType().intValue() == 6 ){
							findResultByNrecord(atitle, results);  //findResultByNcontact
						}//Type ： 7 
						else if(atitle.getType().intValue() == 7){
							
						}//Type ： 9
						else if(atitle.getType().intValue() == 9){
							
						}//Type ： 10
						else if(atitle.getType().intValue() == 10){
							
						}
						
					}
				}
			}
		}
		
		return title;

	}
	
	
	
	
	public List<InformationResult> selectResultByPidTids(String pid,Set<String> tids){
		InformationResult rq = new InformationResult();
		rq.setProjectId(pid);
		rq.setTitleIds(tids);
		//rq.setProperty("title_id sort");
		rq.setProperty("title_id");
		rq.setDirection(Direction.ASC.toString());
		return resultDao.selectList(rq);
	}
	
	
	
	
	// ===  TODO 页面功能
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<InformationTitle> searchWithData(String titleId,String projectId) 
	{
		//查询子标题
		List<InformationTitle> treeList = selectByTlist(selectChildsByPid(Long.valueOf(titleId)));
		if(treeList == null || treeList.size()==0)
		{
			return null;
		}
		Set<String> titleIds = new HashSet<>();
		Map<String,InformationTitle> titleMap = new HashMap<>();
		populateTitleIds(treeList,titleIds,titleMap);
		List<InformationTitle> list = new ArrayList<>(titleMap.values());
		if(list != null)
		{
			for(InformationTitle item : list)
			{
				if(item.getChildList() != null)
				{
					item.getChildList().clear();
				}
			}
		}
		//查询result
		InformationResult resultQuery = new InformationResult();
		resultQuery.setProjectId(projectId);
		resultQuery.setTitleIds(titleIds);
		resultQuery.setProperty("title_id");
		resultQuery.setDirection(Direction.ASC.toString());
		List<InformationResult> resultList = resultDao.selectList(resultQuery);
		if(resultList != null && resultList.size() > 0)
		{
			InformationTitle title = null;
			List<InformationResult> tempList = null;
			Map<Long, String> dict = (Map<Long, String>) cache.get(CacheOperationServiceImpl.CACHE_KEY_VALUE_ID_NAME);
			for(InformationResult item : resultList)
			{
				if(item.getContentChoose() != null)
				{
					if(dict != null)
					{
						item.setValueName(dict.get(Long.valueOf(item.getContentChoose())));
					}
				}
				title = titleMap.get(item.getTitleId());
				if(title != null)
				{
					if(title.getResultList() == null)
					{
						tempList = new ArrayList<>();
						title.setResultList(tempList);
					}
					else
					{
						tempList = title.getResultList();
					}
					tempList.add(item);
				}
			}
		}
		//查询FixedTable
		InformationFixedTable fixedTableQuery = new InformationFixedTable();
		fixedTableQuery.setProjectId(projectId);
		fixedTableQuery.setTitleIds(titleIds);
		fixedTableQuery.setProperty("title_id");
		fixedTableQuery.setDirection(Direction.ASC.toString());
		List<InformationFixedTable> fixedTableList = fixedTableDao.selectList(fixedTableQuery);
		if(fixedTableList != null && fixedTableList.size() > 0)
		{
			InformationTitle title = null;
			List<InformationFixedTable> tempList = null;
			for(InformationFixedTable item : fixedTableList)
			{
				title = titleMap.get(item.getTitleId());
				if(title != null)
				{
					if(title.getFixedTableList() == null)
					{
						tempList = new ArrayList<>();
						title.setFixedTableList(tempList);
					}
					else
					{
						tempList = title.getFixedTableList();
					}
					tempList.add(item);
				}
			}
		}
		
		//查询FixedTable
		InformationListdata listdataQuery = new InformationListdata();
		listdataQuery.setParentId(Long.valueOf(projectId));
		listdataQuery.setTitleIds(titleIds);
		listdataQuery.setProperty("title_id");
		listdataQuery.setDirection(Direction.ASC.toString());
		List<InformationListdata> listdataList = listDataDao.selectList(listdataQuery);
		if(listdataList != null && listdataList.size() > 0)
		{
			InformationTitle title = null;
			List<InformationListdata> tempList = null;
			for(InformationListdata item : listdataList)
			{
				title = titleMap.get(item.getTitleId()+"");
				if(title != null)
				{
					if(title.getDataList() == null)
					{
						tempList = new ArrayList<>();
						title.setDataList(tempList);;
					}
					else
					{
						tempList = title.getDataList();
					}
					tempList.add(item);
				}
			}
		}
		
		return list;
	}
	
	private void populateTitleIds(List<InformationTitle> list, Set<String> ids, Map<String,InformationTitle> map)
	{
		if(list != null && list.size() >0)
		{
			for(InformationTitle item : list)
			{
				ids.add(item.getId()+"");
				map.put(item.getId()+"", item);
				if(item.getChildList() != null)
				{
					populateTitleIds(item.getChildList(),ids,map);
				}
			}
		}
	}
	
}
