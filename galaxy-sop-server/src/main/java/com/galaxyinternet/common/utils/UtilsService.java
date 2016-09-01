package com.galaxyinternet.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.scheduling.BaseGalaxyTask;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.utils.SopConstatnts;


@Component(value="utilsService")
public class UtilsService extends BaseGalaxyTask {

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private Cache cache;
	
	
	//用于检查是否检索过
	private boolean hasCheckedGreenChannel = false;
	
	
	/**
		设置过时时间， 重新检索时间，单位秒，2小时 = 4 * 60 * 60
		refreshTime = 2 * 60 * 60;
	*/
	@Override
	protected void executeInteral() throws BusinessException {
		 saveByRedis(SopConstatnts.Redis._GREEN_CHANNEL_6_,initIds());
	}
	
	
	/**
	 * 检查 项目id 是否跳过尽调阶段
	 * true  有跳过
	 */
	public boolean checkProIsGreenChannel(String key, Long pid){
		boolean isGreen = false;
		List<Long> proIds = getProids_GreenChannel(key);
		if(proIds != null){
			if(proIds.contains(pid)){
				isGreen = true;
			}
		}
		return isGreen;
	}
	
	
	
	/**
	获取缓存中的 跳过尽调阶段的 项目ids
	*@param key = SopConstatnts.Redis._GREEN_CHANNEL_6_
	*/
	@SuppressWarnings("unchecked")
	public List<Long> getProids_GreenChannel(String key){
		List<Long> proIds = null;
		if(!hasCheckedGreenChannel){
			synchronized(this){
				if(!hasCheckedGreenChannel){
					proIds = initIds();
					saveByRedis(key,proIds);
					hasCheckedGreenChannel = true;
					return proIds;
				}
			}
		}
		Object cacheObj = cache.get(key);
		if(cacheObj != null){
			proIds = (List<Long>) cacheObj;
		}
		return proIds;
	}
	
	
	
	/**
	Privilege 特权项目id 一次获取
	项目    跳过 尽调 阶段
		1.尽调任务有一条未完成
		2.项目阶段 大于 尽调阶段
	*/
	public List<Long> initIds(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("progressIntValue", 6);
		params.put("notCompleteTaskStatus", DictEnum.taskStatus.已完成.getCode());
		params.put("resultCloseFilter", DictEnum.projectStatus.YFJ.getCode());
		List<Integer> taskFalgs = new ArrayList<Integer>();
		taskFalgs.add(SopConstant.TASK_FLAG_RSJD);
		taskFalgs.add(SopConstant.TASK_FLAG_FWJD);
		taskFalgs.add(SopConstant.TASK_FLAG_CWJD);
		taskFalgs.add(SopConstant.TASK_FLAG_YWJD);
		List<Long> ids = projectService.getProIdsForPrivilege(params);
		return ids==null || ids.isEmpty() ? null : ids;
	}
	
	
	
	/**
		ids值 保存到 redis， 
		初始化 redis值时 用到
	*/
	public synchronized void saveByRedis(String key,List<Long> ids){
		/*if(ids == null){
			ids = new ArrayList<Long>();
		}*/
		cache.set(key, ids);
	}
	
	
	
	/**
		id 值 保存到 redis
		替换原有 key 的值
	*/
	public synchronized void saveByRedis(String key,Long id){
		List<Long> proIds = getProids_GreenChannel(key);
		if(proIds != null){
			if(!proIds.contains(id)){
				proIds.add(id);
			}
		}else{
			proIds = new ArrayList<Long>();
			proIds.add(id);
		}
		cache.set(key, proIds);
	}



	
	
	

}
