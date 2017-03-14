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

import com.galaxyinternet.bo.hologram.InformationTitleBo;
import com.galaxyinternet.dao.hologram.InformationTitleDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.service.hologram.InformationTitleService;

@Service("com.galaxyinternet.service.hologram.InformationTitleService")
public class InformationTitleServiceImpl extends BaseServiceImpl<InformationTitle> implements InformationTitleService{

	@Autowired
	private InformationTitleDao informationTitleDao;
	
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
		InformationTitle ptitle = selectTitleByPinfo(pinfoKey);
		
		List<InformationTitle> ptitleList = selectChildsByPid(ptitle.getId());
		return ptitleList == null ? new ArrayList<InformationTitle>() : ptitleList;
	}
	
	
	
	
	/**
	 * 根据 code 、 id 父信息递归查询其下所有子集合
	 */
	@Override
	public InformationTitle selectPchildsByPinfo(String pinfoKey) {
		InformationTitle title = selectTitleByPinfo(pinfoKey);
		List<InformationTitle> childList = selectByTlist(selectChildsByPid(title.getId()));
		title.setChildList(childList);
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
	

	
	
	
	
	
	
}
