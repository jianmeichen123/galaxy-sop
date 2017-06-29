package com.galaxyinternet.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.model.dict.Dict;
import com.galaxyinternet.service.DictService;


@Component(value="dictCacheUtils")
public class DictCacheUtils{

	@Autowired
	private  DictService dictService;
	
	@Autowired
	private Cache cache;
	
	/**
		设置过时时间， 重新检索时间，单位秒，2小时 = 4 * 60 * 60
		refreshTime = 2 * 60 * 60;
	*/
	protected void executeInteral() throws Exception {
		   initIds();
	}
	
	/**
	 * 封装数据
	 */
	public void initIds(){
		List<Dict> queryAll = dictService.queryAll();
		
		if(null!=queryAll&&!queryAll.isEmpty()){
			for(Dict dict:queryAll){
				//判断如果parentCode=xhhl，父级接点（根据父级dictCode得到父级下面的子集list）
				if(dict.getParentCode().equals("xhhl")){
					Map<String,Dict> map=new HashMap<String,Dict>();
					saveParentRedis(dict.getCode(),map);
				}else{
					 //子集保存到相应的父级下面
		           Map<String,Dict> mapParent=(Map<String, Dict>) getCachValues(dict.getParentCode());
				
					if(null==mapParent){
						mapParent=new HashMap<String,Dict>();
					}
					mapParent.put(dict.getCode(), dict);
					saveParentRedis(dict.getParentCode(),mapParent);
				}
			}
		}
	}
	
	/**
		ids值 保存到 redis， 
		初始化 redis值时 用到
	*/
	public synchronized void saveParentRedis(String key,Map<String,Dict> dictParentMap){
		if(dictParentMap == null){
			dictParentMap = new HashMap<String,Dict>();
		}
		cache.set(key, dictParentMap);
	}
	
		/**
		ids值 保存到 redis， 
		初始化 redis值时 用到
	*/
	public synchronized void saveDictRedis(String key,Map<String,Dict> dict){
		if(dict == null){
			dict =new HashMap<String,Dict>();
		}
		cache.set(key, dict);
	}
	
	/**
		ids值 保存到 redis， 
		初始化 redis值时 用到
	*/
	public synchronized void saveDictNameRedis(String key,Map<String,String> dictName){
		if(dictName == null){
			dictName =new HashMap<String,String>();
		}
		cache.set(key, dictName);
	}
	/**
     * 根据key获取缓存中数据字典的值
	 *@param key = SopConstatnts.Redis._VOCHER_CHANNEL
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Dict> getCachValues(String key){
		Map<String,Dict>  values = new HashMap<String,Dict>();
		Object obj = cache.get(key);
		if(obj != null){
			values = (Map<String,Dict>) obj;
		}
		return values;
	}
	@Autowired
	public void execute()  {
			try {
				executeInteral();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
}
