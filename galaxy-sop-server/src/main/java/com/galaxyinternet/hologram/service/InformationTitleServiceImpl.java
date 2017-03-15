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
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.hologram.InformationFixedTable;
import com.galaxyinternet.model.hologram.InformationListdata;
import com.galaxyinternet.model.hologram.InformationResult;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.service.hologram.InformationTitleService;

@Service("com.galaxyinternet.service.hologram.InformationTitleService")
public class InformationTitleServiceImpl extends BaseServiceImpl<InformationTitle> implements InformationTitleService{

	@Autowired
	private InformationTitleDao informationTitleDao;
	@Autowired
	private InformationResultDao resultDao;
	@Autowired
	private InformationFixedTableDao fixedTableDao;
	@Autowired
	private InformationListdataDao listDataDao;
	
	@Override
	protected BaseDao<InformationTitle, Long> getBaseDao() {
		return this.informationTitleDao;
	}

	
	
	
	/**
	 * 查询 parentid 为空的 题， 即顶级目录
	 */
	@Override
	@Transactional
	public List<InformationTitle> selectFirstTitle() {
		List<InformationTitle> ptitleList = informationTitleDao.selectFirstTitle();
		ptitleList = ptitleList == null ? new ArrayList<InformationTitle>() : ptitleList;
		return ptitleList;
	}
	
	
	
	
	/**
	 * 根据 code 或 id 查询 本 title
	 */
	@Override
	@Transactional
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
	@Transactional
	public List<InformationTitle> selectChildsByPid(Long pid) {
		Direction direction = Direction.DESC;
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
	@Transactional
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
	@Transactional
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




	@Override
	public List<InformationTitle> searchWithData(String titleId) 
	{
		//查询子标题
		InformationTitle query = new InformationTitle();
		query.setParentId(titleId);
		List<InformationTitle> list = getBaseDao().selectList(query);
		if(list == null || list.size()==0)
		{
			return null;
		}
		Set<String> titleIds = new HashSet<>();
		Map<String,InformationTitle> titleMap = new HashMap<>();
		for(InformationTitle item : list)
		{
			titleIds.add(item.getId()+"");
			titleMap.put(item.getId()+"", item);
		}
		//查询result
		InformationResult resultQuery = new InformationResult();
		resultQuery.setTitleIds(titleIds);
		resultQuery.setProperty("title_id");
		resultQuery.setDirection(Direction.ASC.toString());
		List<InformationResult> resultList = resultDao.selectList(resultQuery);
		if(resultList != null && resultList.size() > 0)
		{
			InformationTitle title = null;
			List<InformationResult> tempList = null;
			for(InformationResult item : resultList)
			{
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
	
}
