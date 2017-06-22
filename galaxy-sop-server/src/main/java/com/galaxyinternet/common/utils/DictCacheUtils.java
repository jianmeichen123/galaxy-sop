package com.galaxyinternet.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.model.dict.Dict;
import com.galaxyinternet.service.DictService;
import com.galaxyinternet.utils.SopConstatnts;


@Component(value="dictCacheUtils")
public class DictCacheUtils{

	@Autowired
	private  DictService dictService;
	
	@Autowired
	private  Cache cache;
	
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
		List<Dict> parentDictList=new ArrayList<Dict>();
		if(null!=queryAll&&!queryAll.isEmpty()){
			for(Dict dict:queryAll){
				//判断如果parentCode=xhhl，父级接点（根据父级dictCode得到父级下面的子集list）
				if(dict.getParentCode().equals("xhhl")){
					parentDictList.add(dict);
					saveParentRedis(dict.getCode(), parentDictList);
				}else{
				   //子集保存到相应的父级下面
					List<Dict> list = getCachValues(dict.getParentCode().toString());
					if(null==list||list.isEmpty()){
						list=new ArrayList<Dict>();
					}
					saveParentRedis(dict.getParentCode(),list);
				}
				saveDictRedis(dict.getCode(),dict);
			}
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
	public synchronized void saveDictRedis(String key,Dict dict){
		if(dict == null){
			dict =new Dict();
		}
		cache.set(key, dict);
	}
	
	/**
		ids值 保存到 redis， 
		初始化 redis值时 用到
	*/
	public synchronized void saveDictNameRedis(String key,String dictName){
		if(dictName == null){
			dictName ="";
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
		//Object obj = cache.get(key);
		if(obj != null){
			values = (List<Dict>) obj;
		}
		return values;
	}
	
	
	public static Dict getDict(String key){
		Dict values = new Dict();
	//	Object obj = cache.get(key);
		if(obj != null){
			values = (Dict) obj;
		}
		return values;
	}
	
   public static String getDictName(String key){
		String values = "";
		Object obj = cache.get(key);
		if(obj != null){
			values = (String) obj;
		}
		return values;
	}*/
	public static void main(String[] args){
		DictCacheUtils o=new DictCacheUtils();
		o.initIds();
	}
}
