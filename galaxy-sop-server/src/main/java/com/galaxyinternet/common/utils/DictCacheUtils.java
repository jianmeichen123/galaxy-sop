package com.galaxyinternet.common.utils;

import java.util.ArrayList;
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
		 //saveByRedis(SopConstatnts.Redis._GREEN_CHANNEL_6_,initIds());
	}
	
	/**
	 * 封装数据
	 */
	public void initIds(){
		List<Dict> queryAll = dictService.queryAll();
		Map<String,Dict> map=new HashMap<String,Dict>();
		if(null!=queryAll&&!queryAll.isEmpty()){
			for(Dict dict:queryAll){
				List<Dict> parentDictList=new ArrayList<Dict>();
				//判断如果parentCode=xhhl，父级接点（根据父级dictCode得到父级下面的子集list）
				if(dict.getParentCode().equals("xhhl")){
					saveParentRedis(dict.getParentCode()+"_"+dict.getCode(), parentDictList);
				}else{
				   //子集保存到相应的父级下面
					List<Dict> list = getCachValues("xhhl"+"_"+dict.getParentCode());
					if(null==list||list.isEmpty()){
						list=new ArrayList<Dict>();
					}
					list.add(dict);
					saveParentRedis("xhhl"+"_"+dict.getParentCode(),list);
					map.put(dict.getCode()+"_"+dict.getId(),dict);
				}
		
				
			}
			saveDictRedis("xhhl_dict",map);
		}
	}
	
	/**
		ids值 保存到 redis， 
		初始化 redis值时 用到
	*/
	public synchronized void saveParentRedis(String key,List<Dict> dictParentMap){
		if(dictParentMap == null){
			dictParentMap = new ArrayList<Dict>();
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
	public List<Dict> getCachValues(String key){
		List<Dict> values = new ArrayList<Dict>();
		Object obj = cache.get(key);
		if(obj != null){
			values = (List<Dict>) obj;
		}
		return values;
	}
	
	/**
	 * 从缓存中移除数据字典的值
	 * @param key
	 * @param value
	 */
	public synchronized void removeByRedis(String key, String value){
		List<Dict> values = getCachValues(key);
		if(values.contains(value)){
			values.remove(value);
			cache.set(key, values);
		}
	}
	
     /*@SuppressWarnings("unchecked")
	public static List<Dict> getParentDictList(String key){
		List<Dict> values = new ArrayList<Dict>();
		Object obj = cache.get(key);
		if(obj != null){
			values = (List<Dict>) obj;
		}
		return values;
	}
	
	*//**
	 * 根据数据字典code+id 作为key获取某个数据字典类
	 * @param key
	 * @return
	 *//*
	@SuppressWarnings("unchecked")
	public static Dict getDict(String key){
		Dict values = new Dict();
		Map<String,Dict> map=new HashMap<String,Dict>();
		Object obj = cache.get("xhhl_dict");
		if(obj != null){
			map = (Map<String,Dict>) obj;
		}
		values=map.get(key);
		if(null==values){
			values=new Dict();
		}
		return values;
	}
	*//**
	 * 根据数据字典code+id 作为key获取某个数据字典类
	 * @param key
	 * @return
	 *//*
   public static String getDictName(String key){
	     Dict values = new Dict();
	     String dictName="";
		Map<String,Dict> map=new HashMap<String,Dict>();
		Object obj = cache.get("xhhl_dict");
		if(obj != null){
			map = (Map<String,Dict>) obj;
		}
		values=map.get(key);
		if(null==values){
			dictName="";
		}
		return dictName;
	}*/
	public static void main(String[] args){
		DictCacheUtils o=new DictCacheUtils();
		o.initIds();
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
