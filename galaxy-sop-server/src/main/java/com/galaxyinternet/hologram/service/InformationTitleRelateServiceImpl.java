package com.galaxyinternet.hologram.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.hologram.InformationTitleRelateDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.model.hologram.InformationTitleRelate;
import com.galaxyinternet.service.hologram.InformationTitleRelateService;

@Service("com.galaxyinternet.service.hologram.InformationTitleRelateService")
public class InformationTitleRelateServiceImpl extends BaseServiceImpl<InformationTitleRelate> implements InformationTitleRelateService{

	
	@Autowired
	private InformationTitleRelateDao informationTitleRelateDao;
	
	@Override
	protected BaseDao<InformationTitleRelate, Long> getBaseDao() {
		return this.informationTitleRelateDao;
	}

	
	
	@Override
	public List<InformationTitle> selectTitleByRelate(Map<String, Object> params) {
		return informationTitleRelateDao.selectTitleByRelate(params);
	}

	
	/**
	 * 根据父id 查询其子集
	 */
	@Override
	public List<InformationTitle> selectChildsByPid(Long pid) {
		Direction direction = Direction.ASC;
		String property = "relate.index_no";
		
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("parentId",pid);
		params.put("isValid",0);
		params.put("sorting", new Sort(direction, property).toString().replace(":", ""));
		List<InformationTitle> ptitleList = informationTitleRelateDao.selectTitleByRelate(params);
		
		ptitleList = ptitleList == null ? new ArrayList<InformationTitle>() : ptitleList;
		
		for(InformationTitle title : ptitleList){
			if(title.getSign() != null && title.getSign().intValue() == 2){
				title.setName(title.getName()+"：");
			}
		}
		
		return ptitleList;
	}



	@Override
	public List<InformationTitle> selectChildsGradeByPid(Long pid) {
		
		Direction direction = Direction.ASC;
		String property = "relate.index_no";
		
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("parentId",pid);
		params.put("isValid",0);
		params.put("sorting", new Sort(direction, property).toString().replace(":", ""));
		List<InformationTitle> ptitleList = informationTitleRelateDao.selectTitleGradeByRelate(params);
		
		ptitleList = ptitleList == null ? new ArrayList<InformationTitle>() : ptitleList;
		
		/*for(InformationTitle title : ptitleList){
			if(title.getSign() != null && title.getSign().intValue() == 2){
				title.setName(title.getName()+"：");
			}
		}*/
		
		return ptitleList;
	}
	
	
}
