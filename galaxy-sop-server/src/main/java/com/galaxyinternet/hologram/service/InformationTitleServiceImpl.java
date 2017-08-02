package com.galaxyinternet.hologram.service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.bo.hologram.InformationTitleBo;
import com.galaxyinternet.dao.hologram.InformationDictionaryDao;
import com.galaxyinternet.dao.hologram.InformationFixedTableDao;
import com.galaxyinternet.dao.hologram.InformationListdataDao;
import com.galaxyinternet.dao.hologram.InformationListdataRemarkDao;
import com.galaxyinternet.dao.hologram.InformationResultDao;
import com.galaxyinternet.dao.hologram.InformationTitleDao;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.cache.LocalCache;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.framework.core.thread.GalaxyThreadPool;
import com.galaxyinternet.model.hologram.InformationDictionary;
import com.galaxyinternet.model.hologram.InformationFixedTable;
import com.galaxyinternet.model.hologram.InformationListdata;
import com.galaxyinternet.model.hologram.InformationListdataRemark;
import com.galaxyinternet.model.hologram.InformationResult;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.model.hologram.ScoreAutoInfo;
import com.galaxyinternet.model.hologram.ScoreInfo;
import com.galaxyinternet.service.hologram.InformationDictionaryService;
import com.galaxyinternet.service.hologram.InformationTitleService;
import com.galaxyinternet.service.hologram.ScoreInfoService;

@Service("com.galaxyinternet.service.hologram.InformationTitleService")
public class InformationTitleServiceImpl extends BaseServiceImpl<InformationTitle> implements InformationTitleService{

	@Autowired
	private Cache cache;
	@Autowired
	private InformationTitleDao informationTitleDao;
	@Autowired
	private InformationDictionaryDao informationDictionaryDao;
	@Autowired
	private InformationResultDao resultDao;
	@Autowired
	private InformationFixedTableDao fixedTableDao;
	@Autowired
	private InformationListdataDao listDataDao;
	@Autowired
	private InformationListdataRemarkDao headerDao;
	
	@Autowired
	private InformationDictionaryService informationDictionaryService;
	@Autowired
	private LocalCache<String,Object> localCache;
	@Autowired
	private ScoreInfoService scoreInfoService;
	
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
		params.put("isShow",'t');
		params.put("sorting", new Sort(direction, property).toString().replace(":", ""));
		List<InformationTitle> ptitleList = informationTitleDao.selectChildsByPid(params);
		
		ptitleList = ptitleList == null ? new ArrayList<InformationTitle>() : ptitleList;
		
		for(InformationTitle title : ptitleList){
			if(title.getSign() != null && title.getSign().intValue() == 2){
				title.setName(title.getName()+"：");
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
			ptitle.setChildList(ptitleList);
		}
		
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
		if(title != null && title.getSign().intValue() == 2){
			List<InformationResult> title_r =  selectResultByPidTids(pid ,null,title.getId());
			title.setResultList(title_r);
		}
		
		if(title != null) tchilds = title.getChildList();  //得到子title
		
		//得到子title 所有  result 集合
		if( tchilds != null && !tchilds.isEmpty()){
			Set<String> tids = new HashSet<String>();
			for(InformationTitle at : tchilds ){
				tids.add(at.getId()+"");
			}
			results = selectResultByPidTids(pid , tids, null);   
		}
		
		//各 title result 对应封装
		titleSwitchByType( pid, tchilds, results);
		/*if(results!=null && !results.isEmpty()){
		}*/
		
		return title;
	}
	
	/**
	 * 根据题的各 type 分类，分别封装 题：结果
	 * 
	 * //1:文本、2:单选、3:复选、4:级联选择、5:单选带备注(textarea)、6:复选带备注(textarea)、
	   //7:附件、8:文本域、9:固定表格、10:动态表格、11:静态数据、12:单选带备注(input)、13:复选带备注(input)  14select 15:2_textarea
	 */
	public void titleSwitchByType(String pid,List<InformationTitle> titles,List<InformationResult> results){
		Set<String> title_tableids = new HashSet<String>();
		for(InformationTitle atitle : titles ){
			if(atitle.getType() != null){
				
				if(results != null && !results.isEmpty()){
					//Type ： 1 2 8 11
					if(atitle.getType().intValue() == 1 ||  atitle.getType().intValue() == 2 || atitle.getType().intValue() == 8  || 
							atitle.getType().intValue() == 11 || atitle.getType().intValue() == 14){
						findResultByArecord(atitle, results);
					}//Type ： 3 4 5 6 12 13
					else if(atitle.getType().intValue() == 3 || atitle.getType().intValue() == 4 || atitle.getType().intValue() == 5 || 
							atitle.getType().intValue() == 6 || atitle.getType().intValue() == 12 || atitle.getType().intValue() == 13 || 
							atitle.getType().intValue() == 15 ){
						findResultByNrecord(atitle, results);  //findResultByNcontact
					}
				}
				
				//Type ： 7 
				if(atitle.getType().intValue() == 7){
					
				}//Type ： 9
				else if(atitle.getType().intValue() == 9){
					
				}//Type ： 10
				else if(atitle.getType().intValue() == 10){
					title_tableids.add(atitle.getId()+"");
				}
			}
		}
		
		if(!title_tableids.isEmpty()){
			findResultByTable(titles,title_tableids,pid); 
		}
	}
	
	
	/**
	 * 题的值只能有一条记录的
	 * Type ： 1 2 5 8 11
	 */
	@SuppressWarnings("unchecked")
	public void findResultByArecord(InformationTitle atitle, List<InformationResult> results){
		Map<Long, String> dict = (Map<Long, String>) cache.get(CacheOperationServiceImpl.CACHE_KEY_VALUE_ID_NAME);
		List<InformationDictionary> values = atitle.getValueList();
		
		InformationResult isr = null;
		for(InformationResult aresult : results){
			
			if(Long.parseLong(aresult.getTitleId()) == atitle.getId().longValue() ){
				
				isr = new InformationResult();
				
				isr.setId(aresult.getId());
				if(aresult.getContentChoose() != null){
					isr.setValueId(Long.parseLong(aresult.getContentChoose()));
					isr.setValueName(dict.get(isr.getValueId()));
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
		Map<Long, String> dict = (Map<Long, String>) cache.get(CacheOperationServiceImpl.CACHE_KEY_VALUE_ID_NAME);
		List<InformationResult> resultList_toremove = new ArrayList<InformationResult>();
		
		List<InformationDictionary> values = atitle.getValueList();
		
		InformationResult isr = null;
		for(InformationResult aresult : results){
			
			if(Long.parseLong(aresult.getTitleId()) == atitle.getId().longValue() ){
				isr = new InformationResult();
				
				isr.setId(aresult.getId());
				if(aresult.getContentChoose() != null){
					isr.setValueId(Long.parseLong(aresult.getContentChoose()));
					isr.setValueName(dict.get(isr.getValueId()));
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
	 * 拼装 table head body
	 * Type ：10
	 */
	public void findResultByTable(List<InformationTitle> titles, Set<String> title_tableids, String pid ){
		//查询表格头
		InformationListdataRemark headerQuery = new InformationListdataRemark();
		headerQuery.setTitleIds(title_tableids);
		List<InformationListdataRemark> headerList = headerDao.selectList(headerQuery);
		if(headerList != null && headerList.size() > 0){
			for(InformationListdataRemark item : headerList){
				if(item.getSubCode() == null){
					for(InformationTitle at : titles){
						if(at.getId().longValue() == item.getTitleId().longValue()){
							at.setTableHeader(item);
							break;
						}
					}
				}
				
			}
		}
		//查询表格
		InformationListdata listdataQuery = new InformationListdata();
		listdataQuery.setProjectId(Long.valueOf(pid));
		listdataQuery.setTitleIds(title_tableids);
		listdataQuery.setProperty("title_id");
		listdataQuery.setDirection(Direction.ASC.toString());
		List<InformationListdata> listdataList = listDataDao.selectList(listdataQuery);
		if(listdataList != null && listdataList.size() > 0){
			for(InformationListdata item : listdataList){
				for(InformationTitle at : titles){
					if(at.getId().longValue() == item.getTitleId().longValue()){
						if(at.getDataList() == null){
							List<InformationListdata> tempList = new ArrayList<InformationListdata>();
							tempList.add(item);
							at.setDataList(tempList);
						}else{
							at.getDataList().add(item);
						}
						break;
					}
				}
			}
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
		if(title.getSign().intValue() == 2){
			List<InformationResult> title_r =  selectResultByPidTids(pid ,null,title.getId());
			title.setResultList(title_r);
		}
		
		List<InformationTitle> tchilds = null;
		List<InformationResult> results = null;
		
		if(title != null && title.getChildList() != null && !title.getChildList().isEmpty()){
			tchilds = title.getChildList();    //得到 子 title
			
			//得到 各 title 的 result 结果集
			Set<String> tids = new HashSet<String>();
			for(InformationTitle at : tchilds ){
				tids.add(at.getId()+"");
			}
			results = selectResultByPidTids(pid , tids ,null);  
			
			//各 title result 对应封装，value 选中标记
			titleSwitchByType( pid, tchilds, results);
			/*if(results != null && !results.isEmpty()){
			}*/
		}
		
		return title;

	}
	
	
	
	
	public List<InformationResult> selectResultByPidTids(String pid,Set<String> tids,Long tid){
		InformationResult rq = new InformationResult();
		rq.setProjectId(pid);
		if(tids != null) rq.setTitleIds(tids);
		if(tid != null) rq.setTitleId(tid+"");
		//rq.setProperty("title_id sort");
		rq.setProperty("title_id");
		rq.setDirection(Direction.ASC.toString());
		return resultDao.selectList(rq);
	}
	
	
	
	/**
	 * 页面级联功能
	 * 传入项目 id，    title id，    级联 value的 pid，  
	 * 返回 value 的 pid 下的 values
	 */
	@Override
	@Transactional
	public List<InformationDictionary> selectProNvaluesInfo(String pid, String tid, String vpid) {
		
		InformationResult rq = new InformationResult();
		rq.setProjectId(pid);
		rq.setTitleId(tid);
		//rq.setProperty("title_id sort");
		//rq.setProperty("title_id");
		//rq.setDirection(Direction.ASC.toString());
		List<InformationResult> results = resultDao.selectList(rq);
		
		List<String> Vds = new ArrayList<String>();
		if(results != null){
			for(InformationResult ar : results ){
				if( ar.getContentChoose() != null ){
					Vds.add(ar.getContentChoose());
				}
			}
		}
		
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("parentId",vpid);
		params.put("isValid",0);
		
		Direction direction = Direction.ASC;
		String property = "sort";
		params.put("sorting", new Sort(direction, property).toString().replace(":", ""));
		
		List<InformationDictionary> ptitleList = informationDictionaryDao.selectValues(params);
		
		for(InformationDictionary ad : ptitleList){
			if(Vds.contains(ad.getId()+"")){
				ad.setChecked(true);
			}
		}
		
		return ptitleList == null ? new ArrayList<InformationDictionary>() : ptitleList;
	}
	
	
	
	
	// ===  TODO 页面功能
	@SuppressWarnings({ "unchecked" })
	private Map<String,InformationTitle> getChildTitleMap(String parentId)
	{
		Map<String,InformationTitle> titleMap = null;
		String key = "title:map:pid="+parentId;
		if(localCache.containsKey(key))
		{
			titleMap = (Map<String,InformationTitle>)localCache.get(key);
		}
		else
		{
			List<InformationTitle> list = selectByTlist(selectChildsByPid(Long.valueOf(parentId)));
			titleMap = new HashMap<>();
			popTitleMap(list, titleMap);
			localCache.put(key, titleMap);
		}
		return titleMap;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<InformationTitle> searchWithData(String titleId,String projectId) 
	{
		List<InformationTitle> list = null;
		Map<String,InformationTitle> titleMap = getChildTitleMap(titleId);
		Set<String> titleIds = titleMap.keySet();
		list = new ArrayList<>(titleMap.values());
		//reset info
		if(list != null)
		{
			for(InformationTitle item : list)
			{
				if(item.getChildList() != null)
				{
					item.getChildList().clear();
				}
				if(item.getResultList() != null)
				{
					item.getResultList().clear();
				}
				if(item.getDataList() != null)
				{
					item.getDataList().clear();
				}
				if(item.getFixedTableList() != null)
				{
					item.getFixedTableList().clear();
				}
			}
		}
		//查询result
		InformationResult resultQuery = new InformationResult();
		resultQuery.setProjectId(projectId);
		resultQuery.setTitleIds(titleIds);
		resultQuery.setProperty("title_id");
		resultQuery.setDirection(Direction.ASC.toString());
		Map<Long, String> dict = (Map<Long, String>) cache.get(CacheOperationServiceImpl.CACHE_KEY_VALUE_ID_NAME);
		List<InformationResult> resultList = resultDao.selectList(resultQuery);
		if(resultList != null && resultList.size() > 0)
		{
			InformationTitle title = null;
			List<InformationResult> tempList = null;
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
				if(item.getContent() != null)
				{
					if(dict != null)
					{
						item.setValueName(dict.get(Long.valueOf(item.getContent())));
					}
				}
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
		//查询表格头
		InformationListdataRemark headerQuery = new InformationListdataRemark();
		headerQuery.setTitleIds(titleIds);
		List<InformationListdataRemark> headerList = headerDao.selectList(headerQuery);
		if(headerList != null && headerList.size() > 0)
		{
			InformationTitle title = null;
			for(InformationListdataRemark item : headerList)
			{
				if(item.getSubCode() == null){
					title = titleMap.get(item.getTitleId()+"");
					if(title != null)
					{
						title.setTableHeader(item);
					}
				}
			}
		}
		//查询表格
		InformationListdata listdataQuery = new InformationListdata();
		listdataQuery.setProjectId(Long.valueOf(projectId));
		listdataQuery.setTitleIds(titleIds);
		listdataQuery.setProperty("created_time");
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
	private void popTitleMap(List<InformationTitle> list, Map<String,InformationTitle> map)
	{
		if(list != null && list.size() >0)
		{
			for(InformationTitle item : list)
			{
				map.put(item.getId()+"", item);
				if(item.getChildList() != null)
				{
					popTitleMap(item.getChildList(),map);
				}
			}
		}
	}

	@Override
	public List<InformationTitle> searchRelateTitleWithData(final Integer reportType, final Long relateId, final Long projectId)
	{
		final TitleInfoWapper titleInfo = getTitleCache(reportType, relateId);
		if(titleInfo.getRelateIdMap() == null || titleInfo.getRelateIdMap().size() == 0)
		{
			return null;
		}
		List<InformationTitle> list = new ArrayList<>();
		try
		{
			ExecutorService threadPool = GalaxyThreadPool.getExecutorService();
			final CountDownLatch countDownLatch = new CountDownLatch(5);
			threadPool.submit(new Runnable(){
				@Override
				public void run()
				{
					getAndSetFixedTable(projectId, titleInfo);
					countDownLatch.countDown();
				}
			});
			
			threadPool.submit(new Runnable(){
				@Override
				public void run()
				{
					getAndSetTableHeader(titleInfo);
					countDownLatch.countDown();
				}
			});
			threadPool.submit(new Runnable(){
				@Override
				public void run()
				{
					getAndSetTableContent(projectId, titleInfo);
					countDownLatch.countDown();
				}
			});
			
			threadPool.submit(new Runnable(){
				@Override
				public void run()
				{
					getAndSetResult(projectId, titleInfo);
					countDownLatch.countDown();
				}
			});
			
			threadPool.submit(new Runnable(){
				@Override
				public void run()
				{
					getAndSetScore(projectId, titleInfo);
					countDownLatch.countDown();
				}
				
			});
			countDownLatch.await();
			
			list.addAll(titleInfo.getRelateIdMap().values());
		} catch (InterruptedException e)
		{
			throw new BusinessException("获取结果错误",e);
		}
		return list;
		
	}
	
	public TitleInfoWapper getTitleCache(Integer reportType,Long relateId)
	{
		TitleInfoWapper result = null;
		String key = "titleRelate:"+reportType+":"+relateId;
		if(localCache.containsKey(key))
		{
			result = (TitleInfoWapper)localCache.get(key);
			result.init();
		}
		else
		{
			List<InformationTitle> titles = new ArrayList<>();
			InformationTitle title = null;
			if(0l == relateId)
			{
				title = new InformationTitle();
				title.setRelateId(relateId);
				title.setReportType(reportType);
				titles.add(title);
				
				InformationTitle query = new InformationTitle();
				query.setParentId(relateId+"");
				query.setReportType(reportType);
				List<InformationTitle> children = informationTitleDao.selectRelateTitle(query);
				if(children != null && children.size() > 0)
				{
					title = children.iterator().next();
				}
			}
			else
			{
				title = getRelateTitleById(Long.valueOf(relateId));
			}
			if(title != null)
			{
				String code = title.getCode();
				InformationTitle query = new InformationTitle();
				query.setCode(code);
				List<InformationTitle> list = informationTitleDao.selectRelateTitle(query);
				titles.addAll(list);
			}
			result = new TitleInfoWapper(titles);
			localCache.put(key,  result);
		}
		return result;
	}
	
	public InformationTitle getRelateTitleById(Long id)
	{
		InformationTitle query = new InformationTitle();
		query.setId(id);
		List<InformationTitle> list = informationTitleDao.selectRelateTitle(query);
		if(list != null && list.size() >0)
		{
			return list.iterator().next();
		}
		return null;
	}
	
	private static final class TitleInfoWapper
	{
		private Set<String> ids;
		private Set<Long> relateIds;
		private Map<String,InformationTitle> idMap = new ConcurrentHashMap<>();
		private Map<Long,InformationTitle> relateIdMap = new ConcurrentHashMap<>();
		
		public TitleInfoWapper(List<InformationTitle> list)
		{
			if(list != null && list.size()>0)
			{
				for(InformationTitle item : list)
				{
					if(item.getId() != null)
					{
						idMap.put(item.getId()+"", item);
					}
					if(item.getRelateId() != null)
					{
						relateIdMap.put(item.getRelateId(), item);
					}
				}
				ids = idMap.keySet();
				relateIds = relateIdMap.keySet();
			}
		}

		public Set<String> getIds()
		{
			return ids;
		}

		public Set<Long> getRelateIds()
		{
			return relateIds;
		}

		public Map<String, InformationTitle> getIdMap()
		{
			return idMap;
		}

		public Map<Long, InformationTitle> getRelateIdMap()
		{
			return relateIdMap;
		}
		public void init()
		{
			if(relateIdMap.size()>0)
			{
				Collection<InformationTitle> titles = relateIdMap.values();
				for(InformationTitle title : titles)
				{
					if(title.getFixedTableList() != null && title.getFixedTableList().size() >0)
					{
						title.getFixedTableList().clear();
					}
					if(title.getTableHeader() != null)
					{
						title.setTableHeader(null);
					}
					if(title.getResultList() != null && title.getResultList().size()>0)
					{
						title.getResultList().clear();
					}
					if(title.getDataList() != null && title.getDataList().size()>0)
					{
						title.getDataList().clear();
					}
					if(title.getWeight() != null)
					{
						title.setWeight(null);
					}
				}
			}
		}
	}
	
	
	/**
	 * 固定表格
	 * @param projectId
	 * @param titleInfo
	 */
	public void getAndSetFixedTable(Long projectId, TitleInfoWapper titleInfo)
	{
		InformationFixedTable fixedTableQuery = new InformationFixedTable();
		fixedTableQuery.setProjectId(projectId+"");
		fixedTableQuery.setTitleIds(titleInfo.getIds());
		List<InformationFixedTable> fixedTableList = fixedTableDao.selectList(fixedTableQuery);
		if(fixedTableList != null && fixedTableList.size() < 0)
		{
			Map<String,InformationTitle> idMap = titleInfo.getIdMap();
			for(InformationFixedTable item : fixedTableList)
			{
				String titleId = item.getTitleId();
				if(idMap.containsKey(titleId))
				{
					InformationTitle title = idMap.get(titleId);
					List<InformationFixedTable> list = null;
					if(title.getFixedTableList() == null)
					{
						list = new ArrayList<>();
						title.setFixedTableList(list);
					}
					title.getFixedTableList().add(item);
				}
			}
		}
	}
	
	/**
	 * 动态表格-表头
	 * @param titleInfo
	 */
	public void getAndSetTableHeader(TitleInfoWapper titleInfo)
	{
		InformationListdataRemark headerQuery = new InformationListdataRemark();
		headerQuery.setTitleIds(titleInfo.getIds());
		List<InformationListdataRemark> headers = headerDao.selectList(headerQuery);
		if(headers != null && headers.size()>0)
		{
			Map<String,InformationTitle> idMap = titleInfo.getIdMap();
			for(InformationListdataRemark item : headers)
			{
				String titleId = item.getTitleId()+"";
				if(idMap.containsKey(titleId))
				{
					InformationTitle title = idMap.get(titleId);
					title.setTableHeader(item);
				}
			}
		}
	}
	/**
	 * 表格内容
	 * @param projectId
	 * @param titleInfo
	 */
	public void getAndSetTableContent(Long projectId, TitleInfoWapper titleInfo)
	{
		InformationListdata listdataQuery = new InformationListdata();
		listdataQuery.setProjectId(Long.valueOf(projectId));
		listdataQuery.setTitleIds(titleInfo.getIds());
		listdataQuery.setProperty("project_id,created_time");
		listdataQuery.setDirection(Direction.ASC.toString());
		List<InformationListdata> listdataList = listDataDao.selectList(listdataQuery);
		if(listdataList != null && listdataList.size()>0)
		{
			Map<String,InformationTitle> idMap = titleInfo.getIdMap();
			for(InformationListdata item : listdataList)
			{
				String titleId = item.getTitleId()+"";
				if(idMap.containsKey(titleId))
				{
					InformationTitle title = idMap.get(titleId);
					List<InformationListdata> list = null;
					if(title.getFixedTableList() == null)
					{
						list = new ArrayList<>();
						title.setDataList(list);;
					}
					title.getDataList().add(item);
				}
			}
		}
	}
	/**
	 * 	普通结果
	 * @param projectId
	 * @param titleInfo
	 */
	public void getAndSetResult(Long projectId, TitleInfoWapper titleInfo)
	{
		InformationResult resultQuery = new InformationResult();
		resultQuery.setTitleIds(titleInfo.getIds());
		resultQuery.setProjectId(projectId+"");
		List<InformationResult> resultList = resultDao.selectList(resultQuery);
		if(resultList != null && resultList.size()>0)
		{
			Map<String,InformationTitle> idMap = titleInfo.getIdMap();
			for(InformationResult item : resultList)
			{
				String titleId = item.getTitleId()+"";
				if(idMap.containsKey(titleId))
				{
					InformationTitle title = idMap.get(titleId);
					List<InformationResult> list = null;
					if(title.getResultList() == null)
					{
						list = new ArrayList<>();
						title.setResultList(list);;
					}
					title.getResultList().add(item);
				}
			}
		}
	}
	/**
	 * 分数权重
	 * @param projectId
	 * @param titleInfo
	 */
	public void getAndSetScore(Long projectId, TitleInfoWapper titleInfo)
	{
		ScoreInfo scoreQuery = new ScoreInfo();
		scoreQuery.setIds(titleInfo.getRelateIds());
		List<ScoreInfo> scoreList= scoreInfoService.queryList(scoreQuery);
		if(scoreList != null && scoreList.size()>0)
		{
			Map<Long,InformationTitle> relateIdMap = titleInfo.getRelateIdMap();
			for(ScoreInfo score : scoreList)
			{
				Long rId = score.getRelateId();
				if(relateIdMap.containsKey(rId))
				{
					InformationTitle title = relateIdMap.get(rId);
					//权重
					if(score != null && score.getProcessMode() != null && 1 == score.getProcessMode().intValue())
					{
						BigDecimal weight = scoreInfoService.getWeight(score.getRelateId(), Long.valueOf(projectId));
						if(weight != null)
						{
							title.setWeight(weight);
						}
					}
					//分数选项
					List<ScoreAutoInfo> autoList = score.getAutoList();
					if(autoList!=null && autoList.size()>0)
					{
						Integer scoreType = score.getScoreType();
						if( scoreType!=null && scoreType.intValue() == 1)
						{
							title.setAutoList(autoList);
						}
					}
				}
			}
		}
	}
	
}
