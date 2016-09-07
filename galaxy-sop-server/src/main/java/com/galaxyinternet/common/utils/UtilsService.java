package com.galaxyinternet.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.utils.SopConstatnts;


@Component(value="utilsService")
public class UtilsService{

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private Cache cache;
	
	//用于检查是否检索过
	private boolean hasCheckedGreenChannel = false;
	
	
	/*//程序启动时，清空redis中签署的值
	@Override
	public void afterPropertiesSet() throws Exception {
		cache.remove(SopConstatnts.Redis._VOCHER_CHANNEL);
	}*/
	
	/*
	*//**
		设置过时时间， 重新检索时间，单位秒，2小时 = 4 * 60 * 60
		refreshTime = 2 * 60 * 60;
	*//*
	@Override
	protected void executeInteral() throws BusinessException {
		 saveByRedis(SopConstatnts.Redis._GREEN_CHANNEL_6_,initIds());
	}
	*/
	
	/**
	 * 检查 项目id 是否跳过尽调阶段
	 * true  有跳过
	 */
	public boolean checkProIsGreenChannel(Long pid){
		boolean isGreen = false;
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("progressIntValue", 6);
		params.put("notCompleteTaskStatus", DictEnum.taskStatus.已完成.getCode());
		params.put("projectId", pid);
		params.put("resultCloseFilter", DictEnum.projectStatus.YFJ.getCode());
		List<Integer> taskFalgs = new ArrayList<Integer>();
		taskFalgs.add(SopConstant.TASK_FLAG_RSJD);
		taskFalgs.add(SopConstant.TASK_FLAG_FWJD);
		taskFalgs.add(SopConstant.TASK_FLAG_CWJD);
		taskFalgs.add(SopConstant.TASK_FLAG_YWJD);
		params.put("taskFlags", taskFalgs);
		List<Long> ids = projectService.getProIdsForPrivilege(params);
		if(ids == null || ids.isEmpty()){
			isGreen = false;
		}else isGreen = true;
		
		return isGreen;
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
		params.put("taskFlags", taskFalgs);
		List<Long> ids = projectService.getProIdsForPrivilege(params);
		return ids==null || ids.isEmpty() ? null : ids;
	}
	
	
	
	/**
		ids值 保存到 redis， 
		初始化 redis值时 用到
	*/
	public synchronized void saveByRedis(String key,List<Long> ids){
		if(ids == null){
			ids = new ArrayList<Long>();
		}
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



	
	
	
	/*
	//所有   签署证明  并发检查 key，
	public static final String _VOCHER_CHANNEL = "voucher_channel_key";
	//投资意向书阶段   签署证明 并发检查， 在redis中的list中的值，为   "voucher_channel_5_" + 项目id
	public static final String _VOCHER_CHANNEL_5_ = "voucher_channel_5_";
	//设置签署并发过时时间， 单位秒，5分钟= 5 * 60 
	private	final static int concurrentRefreshTime = 5 * 60;
	*/
	/**
	 * 检查 value 是否在 key 中
	 * true  有跳过
	 */
	public boolean checkValueInKey(String key, String value){
		boolean isGreen = false;
		List<String> values = getCachValues(key);
		if(values.contains(value)){
			isGreen = true;
		}
		return isGreen;
	}
	
	/**
		获取缓存中的 正在进行签署凭证上传的 key 值 
	*@param key = SopConstatnts.Redis._VOCHER_CHANNEL
	*/
	@SuppressWarnings("unchecked")
	public List<String> getCachValues(String key){
		List<String> values = new ArrayList<String>();
		Object obj = cache.get(key);
		if(obj != null){
			values = (List<String>) obj;
		}
		return values;
	}
	
	/**
		values 值 保存到 redis
		String value = SopConstatnts.Redis._VOCHER_CHANNEL_5_ + pid;
		@return 1:成功  2：存在并发
	*/
	public synchronized int saveByRedis(String key, String value){
		int result = 1;
		List<String> values = getCachValues(key);
		if(values.contains(value)){
			result = 2;
		}else{
			values.add(value);
			cache.set(key,values); //concurrentRefreshTime
		}
		return result;
	}
	 
	/**
		values 值 从 redis 中移除
		String value = SopConstatnts.Redis._VOCHER_CHANNEL_5_ + pid;
		@return 1:成功  2：存在并发
	*/
	public synchronized void removeByRedis(String key, String value){
		List<String> values = getCachValues(key);
		if(values.contains(value)){
			values.remove(value);
			cache.set(key, values);
		}
	}



	
	

}
