package com.galaxyinternet.mongodb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.galaxyinternet.bo.hologram.InformationTitleBo;
import com.galaxyinternet.common.utils.WebUtils;
import com.galaxyinternet.dao.hologram.InformationListdataRemarkDao;
import com.galaxyinternet.dao.hologram.InformationTitleDao;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.cache.LocalCache;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.exception.MongoDBException;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.framework.core.utils.AuthRequestUtil;
import com.galaxyinternet.framework.core.utils.StringEx;
import com.galaxyinternet.hologram.service.CacheOperationServiceImpl;
import com.galaxyinternet.hologram.util.RegexUtil;
import com.galaxyinternet.model.hologram.InformationDictionary;
import com.galaxyinternet.model.hologram.InformationListdataRemark;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.mongodb.model.FixedTableModelMG;
import com.galaxyinternet.mongodb.model.InformationCreateTimeMG;
import com.galaxyinternet.mongodb.model.InformationDataMG;
import com.galaxyinternet.mongodb.model.InformationFixedTableMG;
import com.galaxyinternet.mongodb.model.InformationListdataMG;
import com.galaxyinternet.mongodb.model.InformationModelMG;
import com.galaxyinternet.mongodb.model.InformationResultMG;
import com.galaxyinternet.mongodb.model.TableModelMG;

@Service("com.galaxyinternet.mongodb.service.InformationMGService")
public class InformationMGServiceImpl extends BaseServiceImpl<InformationDataMG>implements InformationMGService {
	
	
	@Autowired
	private InformationFixedTableMGService informationFixedTableMGService;
	@Autowired
	private InformationListdataMGService informationListdataMGService;
	@Autowired
	private InformationResultMGService informationResultMGService;
	@Autowired
	private LocalCache<String,Object> localCache;
	@Autowired
	private InformationListdataRemarkDao headerDao;
	@Autowired
	private InformationTitleDao informationTitleDao;
	@Autowired
	private InformationCreateTimeMGService informationCreateTimeMGService;
	
	@Autowired
	private Cache cache;
	
	@Autowired
	private AuthRequestUtil authReq;
	@Override
	public void save(InformationDataMG data) throws CloneNotSupportedException {
		// TODO Auto-generated method stubime
		saveTime(data);
		saveResult(data);
	    saveListData(data);
		saveFixedTable(data);
	//	saveFiles(data);
	}
	private void saveResult(InformationDataMG data)
	{
		String projectId = data.getProjectId();
		List<InformationModelMG> list = data.getInfoModeList();
		InformationResultMG entity = null;
		List<InformationResultMG> entityList = new ArrayList<>(); // 增加
		for (InformationModelMG model : list) {
			entity = new InformationResultMG();
			entity.setProjectId(projectId);
			entity.setTitleId(model.getTitleId());
			//项目融资状态（阶段）可以为文本(尚未获投/不明确)
			if (!StringEx.isNullOrEmpty(model.getValue()) && (StringUtils.isNumeric(model.getValue())||"1108".equals(model.getTitleId()))) {
				entity.setContentChoose(model.getValue());
			}
			if (!StringEx.isNullOrEmpty(model.getRemark1())) {
				if (model.getType().equals("5") || model.getType().equals("6") || model.getType().equals("8")
						|| model.getType().equals("15")) {
					entity.setContentDescribe1(RegexUtil.getTextFromHtml(model.getRemark1()));
				} else {
					entity.setContentDescribe1(model.getRemark1());
				}
			}
			if (!StringEx.isNullOrEmpty(model.getRemark2())) {
				if (model.getType().equals("15")) {
					entity.setContentDescribe2(RegexUtil.getTextFromHtml(model.getRemark2()));
				} else {
					entity.setContentDescribe2(model.getRemark2());
				}
			}
			User user = WebUtils.getUserFromSession();
			Long userId = user != null ? user.getId() : null;
			Long now = new Date().getTime();
			entity.setCreateId(userId.toString());
			entity.setCreateTime(now.toString());
			entityList.add(entity); // 新增
		}
		 //判断并且删除已经存在的模块答案
		judgeAndDeleteModel(data);
		// 插入数据
		for (int i=0;i<entityList.size();i++) {
			InformationResultMG result=entityList.get(i);
			  try {
				  if((null!=result.getContentChoose()&&!"".equals(result.getContentChoose()))||
						  (null!=result.getContentDescribe1()&&!"".equals(result.getContentDescribe1()))||  
						 ( null!=result.getContentDescribe2()&&!"".equals(result.getContentDescribe2()) )){
					  result.setParentId(data.getParentId());
						informationResultMGService.save(result);
				  }
			} catch (MongoDBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		
	}			
			
		
		

	private void saveFixedTable(InformationDataMG data)
	{
		String projectId = data.getProjectId();
		List<FixedTableModelMG> list = data.getInfoFixedTableList();
		if(projectId == null || list == null || list.size() ==0)
		{
			return;
		}
		InformationFixedTableMG entity = null;
		List<InformationFixedTableMG> insertEntityList = new ArrayList<>();
		User user = WebUtils.getUserFromSession();
		Long userId = user != null ? user.getId() : null;
		Long now = new Date().getTime();
		for(FixedTableModelMG model : list)
		{
			entity = new InformationFixedTableMG();
			entity.setParentId(data.getParentId());
			entity.setProjectId(projectId);
			entity.setTitleId(model.getTitleId());
			entity.setRowNo(model.getRowNo());
			entity.setColNo(model.getColNo());
			entity.setType(model.getType());
			entity.setContent(model.getValue());
			entity.setCreateId(userId+"");
			entity.setCreateTime(now.toString());
			insertEntityList.add(entity);
		}
		judgeAndDeleteFixTable(data);
		//插入数据
		if(insertEntityList.size() > 0)
		{
			for(int i=0;i<insertEntityList.size();i++){
				InformationFixedTableMG result=insertEntityList.get(i);

				try {
					informationFixedTableMGService.save(result);
				} catch (MongoDBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	private void saveListData(InformationDataMG data)
	{
		String projectId = data.getProjectId();
		if(projectId == null)
		{
			return;
		}
		List<TableModelMG> list = data.getInfoTableModelList();
	/*	if(list == null || list.size() ==0)
		{
			return;
		}*/
		InformationListdataMG entity = null;
		Set<String> titleIds = new HashSet<>();
		judgeAndDeleteTableModel(data);
		for(TableModelMG model : list)
		{
			titleIds.add(model.getTitleId()+"");
			entity = new InformationListdataMG();
			entity.setParentId(data.getParentId());
			entity.setProjectId(projectId);
			entity.setTitleId(model.getTitleId().toString());
			entity.setResultId(model.getId());
			entity.setCode(model.getCode());
			entity.setField1(model.getField1());
			entity.setField2(model.getField2());
			entity.setField3(model.getField3());
			entity.setField4(model.getField4());
			entity.setField5(model.getField5());
			entity.setField6(model.getField6());
			entity.setField7(model.getField7());
			entity.setField8(model.getField8());
			entity.setField9(model.getField9());
			entity.setField10(model.getField10());
			entity.setField11(model.getField11());
			entity.setField12(model.getField12());
			entity.setField13(model.getField13());
			entity.setField14(model.getField14());
			entity.setField15(model.getField15());
			entity.setField16(model.getField16());
			entity.setRelateFileId(model.getRelateFileId());
			entity.setCreateId(model.getCreateId());
			entity.setCreateTimeStr(model.getCreateTimeStr());
			entity.setCreateUserName(model.getCreateUserName());
			entity.setCreatedTime(model.getCreatedTime());
			entity.setUpdateId(model.getUpdateId());
			entity.setUpdateTime(model.getUpdateTime());
			entity.setUpdateUserName(model.getUpdateUserName());
			entity.setUpdateTimeStr(model.getUpdateTimeStr());
			User user = WebUtils.getUserFromSession();
			Long userId = user != null ? user.getId() : null;
			Long now = new Date().getTime();
			entity.setCreateId(userId.toString());
			entity.setCreateTime(now.toString());
				try {
					informationListdataMGService.save(entity);
				} catch (MongoDBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	/**/
	@Override
	protected BaseDao<InformationDataMG, Long> getBaseDao() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void judgeAndDeleteModel(InformationDataMG data){
		List<InformationResultMG> findInfoModeList=new ArrayList<InformationResultMG>();
		try {
				InformationResultMG param=new InformationResultMG();
				Map<String,InformationTitle> titleMap = getChildTitleMap(data.getParentId());
				Set<String> titleIds = titleMap.keySet();
				param.setProjectId(data.getProjectId());
				List<String> list=new ArrayList<String>(titleIds);
				param.setTitleIds(list);
				findInfoModeList = informationResultMGService.find(param);
				if(null!=findInfoModeList&&findInfoModeList.size()>0){
					try {
						informationResultMGService.deleteByCondition(param);
					} catch (MongoDBException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		} catch (MongoDBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	public void judgeAndDeleteFixTable(InformationDataMG data){
			
			List<InformationFixedTableMG> findInfoFixTableModelList=new ArrayList<InformationFixedTableMG>();
			try {
					InformationFixedTableMG param=new InformationFixedTableMG();
					Map<String,InformationTitle> titleMap = getChildTitleMap(data.getParentId());
					Set<String> titleIds = titleMap.keySet();
					param.setProjectId(data.getProjectId());
					List<String> list=new ArrayList<String>(titleIds);
					param.setTitleIds(list);
					findInfoFixTableModelList=informationFixedTableMGService.find(param);
					if(null!=findInfoFixTableModelList&&findInfoFixTableModelList.size()>0){
						informationFixedTableMGService.deleteByCondition(param);
					}
			} catch (MongoDBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	public void judgeAndDeleteTableModel(InformationDataMG data){
		List<InformationListdataMG> findInfoTableModelList=new ArrayList<InformationListdataMG>();
		try {
			
				InformationListdataMG param=new InformationListdataMG();
				Map<String,InformationTitle> titleMap = getChildTitleMap(data.getParentId());
				Set<String> titleIds = titleMap.keySet();
				//param.setParentId(data.getParentId());
				param.setProjectId(data.getProjectId());
				List<String> list=new ArrayList<String>(titleIds);
				param.setTitleIds(list);
				findInfoTableModelList=informationListdataMGService.find(param);
				if(null!=findInfoTableModelList&&findInfoTableModelList.size()>0){
					informationListdataMGService.deleteByCondition(param);
				}
			
			
		} catch (MongoDBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
       }

	@SuppressWarnings("unchecked")
	@Override
	public List<InformationTitle> searchWithData(String parentId,String projectId) 
	{
		List<InformationTitle> list = null;
		Map<String,InformationTitle> titleMap = getChildTitleMap(parentId);
		Set<String> titleIds = titleMap.keySet();
		
		List<String> valueList = getListByMap(titleMap,true);
		list = new ArrayList<InformationTitle>(titleMap.values());
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
		InformationResultMG resultQuery = new InformationResultMG();
		resultQuery.setProjectId(projectId);
		//resultQuery.setParentId(parentId);
		resultQuery.setTitleIds(valueList);
		Map<Long, String> dict = (Map<Long, String>) cache.get(CacheOperationServiceImpl.CACHE_KEY_VALUE_ID_NAME);
		List<InformationResultMG> resultList = null;
		try {
			resultList = informationResultMGService.find(resultQuery);
		} catch (MongoDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(resultList != null && resultList.size() > 0)
		{
			InformationTitle title = null;
			List<InformationResultMG> tempList = null;
			for(InformationResultMG item : resultList)
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
					if(title.getResultMGList() == null)
					{
						tempList = new ArrayList<>();
						title.setResultMGList(tempList);
					}
					else
					{
						tempList = title.getResultMGList();
					}
					tempList.add(item);
				}
			}
		}
		//查询FixedTable
		InformationFixedTableMG fixedTableQuery = new InformationFixedTableMG();
		fixedTableQuery.setProjectId(projectId);
		//fixedTableQuery.setParentId(parentId);
		resultQuery.setTitleIds(valueList);
		List<InformationFixedTableMG> fixedTableList = null;
		try {
			fixedTableList = informationFixedTableMGService.find(fixedTableQuery);
		} catch (MongoDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(fixedTableList != null && fixedTableList.size() > 0)
		{
			InformationTitle title = null;
			List<InformationFixedTableMG> tempList = null;
			for(InformationFixedTableMG item : fixedTableList)
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
					if(title.getFixedTableMGList() == null)
					{
						tempList = new ArrayList<>();
						title.setFixedTableMGList(tempList);
					}
					else
					{
						tempList = title.getFixedTableMGList();
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
		InformationListdataMG listdataQuery = new InformationListdataMG();
		listdataQuery.setProjectId(projectId);
		listdataQuery.setTitleIds(valueList);
		Map<String,String> userMap=initCache();
		List<InformationListdataMG> listdataList = null;
		try {
			listdataList = informationListdataMGService.find(listdataQuery);
		} catch (MongoDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(listdataList != null && listdataList.size() > 0)
		{
			InformationTitle title = null;
			List<InformationListdataMG> tempList = null;
			for(InformationListdataMG item : listdataList)
			{
				title = titleMap.get(item.getTitleId()+"");
				if(title != null)
				{
					if(title.getDataMGList() == null)
					{
						tempList = new ArrayList<>();
						title.setDataMGList(tempList);
					}
					else
					{
						tempList = title.getDataMGList();
					}
				/*	if(item.getCreateId() != null)
					{
						String createUserName =userMap.get(item.getCreateId().toString());
						item.setCreateUserName(createUserName);
					}
					if(item.getUpdateId() != null)
					{
						String updateUserName = userMap.get(item.getUpdateId().toString());
						item.setUpdateUserName(updateUserName);
					}
					if(item.getCreateTime() != null)
					{
						item.setCreateTimeStr(item.getCreateTime());
					}
					if(item.getUpdateTime() != null)
					{
						item.setUpdateTimeStr(item.getUpdateTime());
					}*/
					tempList.add(item);
				}
			}
		}
		
		return list;
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
			Map<String,InformationTitle> map = new ConcurrentHashMap<>(titleMap.size());
			for (Map.Entry<String,InformationTitle> e : titleMap.entrySet())
			{
				map.put(e.getKey(), e.getValue().clone());
			}
			return map;
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
		public Map<String ,String > initCache( )
		{
			Map<String ,String > map=new HashMap<String ,String>();
			List<Map<String,Object>> userList =(List<Map<String, Object>>) authReq.getUserList();
				if(userList != null && userList.size() >0)
				{
					for(int i=0;i<userList.size();i++)
					{
						Map<String,Object> mapNew=userList.get(i);
					map.put(mapNew.get("userId").toString(), String.valueOf(mapNew.get("userName")));
					}
				}
			return map;
			
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
		public static List<String> getListByMap(Map<String, InformationTitle> map,  
	            boolean isKey) {  
	        List<String> list = new ArrayList<String>();  
	  
	        Iterator<String> it = map.keySet().iterator();  
	        while (it.hasNext()) {  
	            String key = it.next().toString();  
	            if (isKey) {  
	                list.add(key);  
	            } else {  
	                list.add(map.get(key).getId().toString());  
	            }  
	        }  
	  
	        return list;  
	    }
		
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
			List<InformationResultMG> results = null;
			
			InformationTitle title = selectTChildsByPinfo(pinfoKey); //得到父title
			if(title != null && title.getSign().intValue() == 2){
				List<InformationResultMG> title_r =  selectResultByPidTids(pid ,null,title.getId());
				title.setResultMGList(title_r);
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
		public List<InformationResultMG> selectResultByPidTids(String pid,Set<String> tids,Long tid){
			InformationResultMG rq = new InformationResultMG();
			rq.setProjectId(pid);
			rq.setIsValid(0+"");
			List<String> titles=new ArrayList<String>(tids);
			if(tids != null) rq.setTitleIds(titles);
			if(tid != null) rq.setTitleId(tid+"");
			List<InformationResultMG> list=new ArrayList<InformationResultMG>();
			try {
				list= informationResultMGService.find(rq);
			} catch (MongoDBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}
		/**
		 * 根据题的各 type 分类，分别封装 题：结果
		 * 
		 * //1:文本、2:单选、3:复选、4:级联选择、5:单选带备注(textarea)、6:复选带备注(textarea)、
		   //7:附件、8:文本域、9:固定表格、10:动态表格、11:静态数据、12:单选带备注(input)、13:复选带备注(input)  14select 15:2_textarea
		 */
		public void titleSwitchByType(String pid,List<InformationTitle> titles,List<InformationResultMG> results){
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
								atitle.getType().intValue() == 15 || atitle.getType().intValue() == 21 ){
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
			InformationListdataMG listdataQuery = new InformationListdataMG();
			List<String> paramList=new ArrayList<String>(title_tableids);
			listdataQuery.setTitleIds(paramList);
			//listdataQuery.setProperty("title_id");
			//listdataQuery.setDirection(Direction.ASC.toString());
			List<InformationListdataMG> listdataList = null;
			try {
				listdataList = informationListdataMGService.find(listdataQuery);
			} catch (MongoDBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(listdataList != null && listdataList.size() > 0){
				for(InformationListdataMG item : listdataList){
					for(InformationTitle at : titles){
						if(at.getId().equals(item.getTitleId())){
							if(at.getDataList() == null){
								List<InformationListdataMG> tempList = new ArrayList<InformationListdataMG>();
								tempList.add(item);
								at.setDataMGList(tempList);
							}else{
								at.getDataMGList().add(item);
							}
							break;
						}
					}
				}
			}
		}
		/**
		 * 题的值只能有一条记录的
		 * Type ： 1 2 5 8 11
		 */
		@SuppressWarnings("unchecked")
		public void findResultByArecord(InformationTitle atitle, List<InformationResultMG> results){
			Map<Long, String> dict = (Map<Long, String>) cache.get(CacheOperationServiceImpl.CACHE_KEY_VALUE_ID_NAME);
			List<InformationDictionary> values = atitle.getValueList();
			
			InformationResultMG isr = null;
			for(InformationResultMG aresult : results){
				
				if(Long.parseLong(aresult.getTitleId()) == atitle.getId().longValue() ){
					
					isr = new InformationResultMG();
					
					isr.setUuid(aresult.getUuid());
					if(aresult.getContentChoose() != null){
						if(!isNumeric(aresult.getContentChoose())){
							isr.setValueId(null);
							isr.setValueName(aresult.getContentChoose());
						}else{
							isr.setValueId(Long.parseLong(aresult.getContentChoose()));
							isr.setValueName(dict.get(isr.getValueId()));
						}
						
					}
					isr.setContentDescribe1(aresult.getContentDescribe1());
					isr.setContentDescribe2(aresult.getContentDescribe2());
					
					//对应的结果放入 title ResultList 中
					List<InformationResultMG> isv = atitle.getResultMGList();
					if(isv == null){
						List<InformationResultMG> t_resultList = new ArrayList<InformationResultMG>();
						t_resultList.add(isr);
						atitle.setResultMGList(t_resultList);
					}else{
						atitle.getResultMGList().add(isr);
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
		
		public static boolean isNumeric(String str){
			 for (int i = str.length();--i>=0;){  
			  if (!Character.isDigit(str.charAt(i))){
			  return false;
			  }
			 }
			 return true;
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
		 * 题的值可能有多条记录的
		 * Type ： 3 4 6
		 */
		@SuppressWarnings("unchecked")
		public void findResultByNrecord(InformationTitle atitle, List<InformationResultMG> results){
			Map<Long, String> dict = (Map<Long, String>) cache.get(CacheOperationServiceImpl.CACHE_KEY_VALUE_ID_NAME);
			List<InformationResultMG> resultList_toremove = new ArrayList<InformationResultMG>();
			
			List<InformationDictionary> values = atitle.getValueList();
			
			InformationResultMG isr = null;
			for(InformationResultMG aresult: results){
				
				if(Long.parseLong(aresult.getTitleId()) == atitle.getId().longValue() ){
					isr = new InformationResultMG();
					
					isr.setUuid(aresult.getUuid());
					if(aresult.getContentChoose() != null){
						isr.setValueId(Long.parseLong(aresult.getContentChoose()));
						isr.setValueName(dict.get(isr.getValueId()));
					}
					isr.setContentDescribe1(aresult.getContentDescribe1());
					isr.setContentDescribe2(aresult.getContentDescribe2());
					
					//对应的结果放入 title ResultList 中
					List<InformationResultMG> isv = atitle.getResultMGList();
					if(isv == null){
						List<InformationResultMG> t_resultList = new ArrayList<InformationResultMG>();
						t_resultList.add(isr);
						atitle.setResultMGList(t_resultList);
					}else{
						atitle.getResultMGList().add(isr);
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
			
			for(InformationResultMG ar : resultList_toremove){
				results.remove(ar);
			}
			
		}
		private final String STUDYEXPERIENCE ="study-experience";

		private final String WORKEXPERIENCE ="work-experience";

		private final String STARTUPEXPERIENCE="entrepreneurial-experience";
		/**
		 * 先根据 id 查 成员,再根据成员查学习经历
		 * @param id
		 * @return
		 */
		@Override
		public InformationListdataMG queryMemberById(String id) {
			InformationListdataMG one=new InformationListdataMG();
			one.setId(id);
			InformationListdataMG data = null;
			try {
				data = informationListdataMGService.findOne(one);
			} catch (MongoDBException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			if(data != null){
				InformationListdataMG query = new InformationListdataMG();
				query.setParentId(data.getId());
				//按毕业时间倒序查询学习经历
				query.setCode(STUDYEXPERIENCE);
				query.setProperty("field_1");
				query.setDirection("desc");
				List<InformationListdataMG> studyList = null;
				try {
					studyList = informationListdataMGService.find(query);
				} catch (MongoDBException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//按结束时间倒序查询创业经历
				query.setCode(WORKEXPERIENCE);
				query.setProperty("field_2");
				query.setDirection("desc");
				List<InformationListdataMG> workList = null;
				try {
					workList = informationListdataMGService.find(query);
				} catch (MongoDBException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//按结束时间倒序查询创业经历
				query.setCode(STARTUPEXPERIENCE);
				query.setProperty("field_2");
				query.setDirection("desc");
				List<InformationListdataMG> startupList = null;
				try {
					startupList = informationListdataMGService.find(query);
				} catch (MongoDBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				data.setWorkList(workList);
				data.setStudyList(studyList);
				data.setStartupList(startupList);
			}
			return data;
		}

		public void removeData(String parentId,String projectId){
			InformationListdataMG paramTable=new InformationListdataMG();
			InformationResultMG   paramResult=new InformationResultMG();
			InformationFixedTableMG paramFixtable=new InformationFixedTableMG();
			Map<String,InformationTitle> titleMap = getChildTitleMap(parentId);
			Set<String> titleIds = titleMap.keySet();
			List<String> list=new ArrayList<String>(titleIds);
			paramTable.setTitleIds(list);
			paramTable.setProjectId(projectId);
			paramResult.setProjectId(projectId);
			paramResult.setTitleIds(list);
			paramFixtable.setProjectId(projectId);
			paramFixtable.setTitleIds(list);
			try {
				informationResultMGService.deleteByCondition(paramResult);
				informationListdataMGService.deleteByCondition(paramTable);
				informationFixedTableMGService.deleteByCondition(paramFixtable);
			} catch (MongoDBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void saveTime(InformationDataMG data){
			User user = WebUtils.getUserFromSession();
			Long userId = user != null ? user.getId() : null;
			InformationCreateTimeMG informationCreateTimeMG=new InformationCreateTimeMG();
			Long now = new Date().getTime();
		
			informationCreateTimeMG.setProjectId(data.getProjectId());
			informationCreateTimeMG.setParentId(data.getParentId());
			try {
				InformationCreateTimeMG findOne = informationCreateTimeMGService.findOne(informationCreateTimeMG);
				if(null!=findOne&&!"".equals(findOne)){
					informationCreateTimeMGService.deleteByCondition(informationCreateTimeMG);
				}
				informationCreateTimeMG.setCreateId(userId.toString());
				informationCreateTimeMG.setCreateTime(now.toString());
				informationCreateTimeMGService.save(informationCreateTimeMG);
			} catch (MongoDBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
}
