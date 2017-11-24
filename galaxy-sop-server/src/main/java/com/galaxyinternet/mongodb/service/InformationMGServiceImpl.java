package com.galaxyinternet.mongodb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.galaxyinternet.common.utils.WebUtils;
import com.galaxyinternet.dao.hologram.InformationListdataRemarkDao;
import com.galaxyinternet.dao.hologram.InformationTitleDao;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.cache.LocalCache;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.exception.MongoDBException;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.framework.core.utils.AuthRequestUtil;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.framework.core.utils.StringEx;
import com.galaxyinternet.hologram.service.CacheOperationServiceImpl;
import com.galaxyinternet.hologram.util.RegexUtil;
import com.galaxyinternet.model.hologram.InformationFixedTable;
import com.galaxyinternet.model.hologram.InformationListdata;
import com.galaxyinternet.model.hologram.InformationListdataRemark;
import com.galaxyinternet.model.hologram.InformationResult;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.mongodb.model.FixedTableModelMG;
import com.galaxyinternet.mongodb.model.InformationDataMG;
import com.galaxyinternet.mongodb.model.InformationFixedTableMG;
import com.galaxyinternet.mongodb.model.InformationListdataMG;
import com.galaxyinternet.mongodb.model.InformationListdataRemarkMG;
import com.galaxyinternet.mongodb.model.InformationModelMG;
import com.galaxyinternet.mongodb.model.InformationResultMG;
import com.galaxyinternet.mongodb.model.InformationTitleMG;
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
	private Cache cache;
	
	@Autowired
	private AuthRequestUtil authReq;
	@Override
	public void save(InformationDataMG data) throws CloneNotSupportedException {
		// TODO Auto-generated method stub
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
			entity.setContentChoose(model.getValue());
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
			    result.setParentId(data.getParentId());
				informationResultMGService.save(result);
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
		if(list == null || list.size() ==0)
		{
			return;
		}
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
			User user = WebUtils.getUserFromSession();
			Long userId = user != null ? user.getId() : null;
			Long now = new Date().getTime();
			entity.setCreateId(userId);
			entity.setCreateTime(now);
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
			 if(null!=data.getInfoModeList()&&data.getInfoModeList().size()>0){
				InformationResultMG param=new InformationResultMG();
				param.setParentId(data.getParentId());
				param.setProjectId(data.getProjectId());
				findInfoModeList = informationResultMGService.find(param);
				if(null!=findInfoModeList&&findInfoModeList.size()>0){
					try {
						informationResultMGService.deleteByCondition(param);
					} catch (MongoDBException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
				if(null!=data.getInfoFixedTableList()&&data.getInfoFixedTableList().size()>0){
					InformationFixedTableMG param=new InformationFixedTableMG();
					param.setParentId(data.getParentId());
					param.setProjectId(data.getProjectId());
					param.setTitleId(data.getInfoFixedTableList().get(0).getTitleId().toString());
					findInfoFixTableModelList=informationFixedTableMGService.find(param);
					if(null!=findInfoFixTableModelList&&findInfoFixTableModelList.size()>0){
						informationFixedTableMGService.deleteByCondition(param);
					}
			
				}
			} catch (MongoDBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	public void judgeAndDeleteTableModel(InformationDataMG data){
		List<InformationListdataMG> findInfoTableModelList=new ArrayList<InformationListdataMG>();
		try {
			if(null!=data.getInfoTableModelList()&&data.getInfoTableModelList().size()>0){
				InformationListdataMG param=new InformationListdataMG();
				param.setParentId(data.getParentId());
				param.setProjectId(data.getProjectId());
				param.setTitleId(data.getInfoTableModelList().get(0).getTitleId().toString());
				findInfoTableModelList=informationListdataMGService.find(param);
				if(null!=findInfoTableModelList&&findInfoTableModelList.size()>0){
					informationListdataMGService.deleteByCondition(param);
				}
			
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
		resultQuery.setParentId(parentId);
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
					if(title.getResultList() == null)
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
		fixedTableQuery.setParentId(parentId);
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
		listdataQuery.setParentId(parentId);
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
					if(item.getCreateId() != null)
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
						item.setCreateTimeStr(DateUtil.longString(item.getCreateTime()));
					}
					if(item.getUpdateTime() != null)
					{
						item.setUpdateTimeStr(DateUtil.longToString(item.getUpdateTime()));
					}
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
}
