package com.galaxyinternet.touhou.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.touhou.ProjectHealthDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.touhou.ProjectHealth;
import com.galaxyinternet.service.ProjectHealthService;

@Service("com.galaxyinternet.touhou.service.ProjectHealthServiceImpl")
public class ProjectHealthServiceImpl extends BaseServiceImpl<ProjectHealth> implements ProjectHealthService {

	@Autowired
	private ProjectHealthDao projectHealthDao;
	
	@Override
	protected BaseDao<ProjectHealth, Long> getBaseDao() {
		return this.projectHealthDao;
	}
	
	@Override
	public Map<String, Object> gtHealthyChart(Map<String, Object> params) {
		List<ProjectHealth> healthyChart = projectHealthDao.getHealthyChart(params);
		Map<String,Object> map=new HashMap<String,Object>();
		if(null!=healthyChart&&!healthyChart.isEmpty()){
			for(ProjectHealth p:healthyChart){
			    Byte healthState=p.getHealthState();
			     int num=0;
			    String attr="";
				switch(healthState)
				{
					case 1://高于预期
						num=p.getHealthHighNum();
						attr="healthHighNum";
						break;
					case 2 : //健康
						num=p.getHealthGoodNum();
						attr="healthGoodNum";
						break;
					case 3 : //健康预警
						num=p.getHealthWarnNum();
						attr="healthWarnNum";
						break;
					default :
						num=0;
				}
				map.put(attr, num);
				
			}
			
		}else{
			map.put("healthHighNum", 0);
			map.put("healthGoodNum", 0);
			map.put("healthWarnNum", 0);
		}
		return map;
	}


}