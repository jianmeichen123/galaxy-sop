package com.galaxyinternet.hologram.service;

import com.galaxyinternet.dao.hologram.InformationDictionaryDao;
import com.galaxyinternet.dao.hologram.InformationTitleDao;
import com.galaxyinternet.dao.hologram.InformationTitleRelateDao;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.cache.LocalCache;
import com.galaxyinternet.model.hologram.InformationDictionary;
import com.galaxyinternet.model.hologram.InformationGrade;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.service.hologram.CacheOperationService;
import com.galaxyinternet.service.hologram.InformationDictionaryService;
import com.galaxyinternet.utils.SopConstatnts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;



@Service("com.galaxyinternet.service.hologram.CacheOperationService")
@Order
public class CacheOperationServiceImpl implements CacheOperationService,InitializingBean{
	
	//public static final String CACHE_KEY_PAGE_AREA_TITLE = "QXT_PAGE_AREA_TITLE_";               //各区域块下的   题：value   ==  InformationTitle
	//public static final String CACHE_KEY_PAGE_AREA_TITLE_HASKEY = "QXT_PAGE_AREA_TITLE_KEYLIST"; //各区域块下的   题：code   ==  List<String>
	
	public static final String CACHE_KEY_TITLE_ID_NAME = "QXT_TITLE_ID_NAME"; //各区域块下的   题：value   ==  Map<Long,String>
	
	public static final String CACHE_KEY_VALUE_ID_NAME = "QXT_VALUE_ID_NAME"; //各区域块下的   题：value   ==  Map<Long,String>

	
	@Autowired
	private Cache cache;

	@Autowired
	private LocalCache<String,Object> localCache;

	
	@Autowired
	private InformationTitleDao informationTitleDao;

	@Autowired
	private InformationTitleRelateDao informationTitleRelateDao;

	@Autowired
	private InformationDictionaryDao informationDictionaryDao;


	@Autowired
	private InformationDictionaryService informationDictionaryService;


	
	
	/**
	 * 启动时：
	 * 1、清空缓存
	 * 2、写入缓存
	*/
	//@PostConstruct  
	@Override
	public void afterPropertiesSet() throws Exception {
		initTitleIdName();
		initValueIdName();

		initReportsCodeIdsAndNum();

		//initAreaTitleAndTValue();
		//清理  initAreaTitleAndTValue 中的cache， 下个版本 1.7 后  删除该代码
		Object getK = cache.get("QXT_PAGE_AREA_TITLE_KEYLIST");
		if(getK != null){
			List<String> cacheKey = (List<String>) getK;
			for(String ak : cacheKey){
				cache.remove("QXT_PAGE_AREA_TITLE_KEYLIST" +ak);
			}
		}
	}
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public synchronized void saveAllByRedies(String code, InformationTitle title){
		InformationTitle t_title = null;
		
		Set<String> cachVs = new HashSet<>();
		String key_codes = SopConstatnts.Redis.ALL_TITLE_CACHE_CODE_KEY;
		
		String key_pre = SopConstatnts.Redis.ALL_TITLE_VALUE_CACHE_PRE_KEY;
		
		Object kv = cache.get(key_pre+code);
		if(kv != null){
			t_title = (InformationTitle) kv;
		}
		
		
		if(t_title == null){
			cache.set(key_pre+code, title);
			
			Object cs = cache.get(key_codes);
			if(cs != null) cachVs= (Set<String>) cs;
			cachVs.add(code);
			cache.set(key_codes, cachVs);
		}
		localCache.clear();
	}
	
	
	
	/**
	 * CACHE_KEY_PAGE_AREA_TITLE 中没有的数据 存入缓存中

	@SuppressWarnings("unchecked")
	@Override
	public synchronized void saveAreaInfoByRedies(String hasKey_toAddkey, InformationTitle area_tinfo){
		
		List<String> cacheKey = new ArrayList<String>();
		Object getK = cache.get(CacheOperationServiceImpl.CACHE_KEY_PAGE_AREA_TITLE_HASKEY);
		if(getK != null){
			cacheKey = (List<String>) getK;
		}
		if(!cacheKey.contains(hasKey_toAddkey)){
			cacheKey.add(hasKey_toAddkey);
			
			cache.set(CacheOperationServiceImpl.CACHE_KEY_PAGE_AREA_TITLE_HASKEY, cacheKey);
			cache.set(CacheOperationServiceImpl.CACHE_KEY_PAGE_AREA_TITLE +hasKey_toAddkey, area_tinfo);
		}
	}
	 */

	
	/**
	 * CACHE_KEY_PAGE_AREA 中没有的数据 存入缓存中
	 */
	@SuppressWarnings("unchecked")
	@Override
	public synchronized void refreshCache(){
		String key_pre = SopConstatnts.Redis.ALL_TITLE_VALUE_CACHE_PRE_KEY;
		
		Set<String> cachVs = new HashSet<>();
		String key_codes = SopConstatnts.Redis.ALL_TITLE_CACHE_CODE_KEY;
		Object cs = cache.get(key_codes);
		if(cs != null) cachVs= (Set<String>) cs;
		
		if(cachVs!=null && !cachVs.isEmpty()){
			for(String code : cachVs){
				cache.remove(key_pre+code);
			}
			cache.remove(key_codes);
		}


		initTitleIdName();
		initValueIdName();
		//initAreaTitleAndTValue();
		initReportsCodeIdsAndNum();
	}
	
	
	
	//title 表  id - name
	public void initTitleIdName() {
		Map<Long,String> titleIdName = new HashMap<Long,String>();
		
		//cache.remove(CacheOperationServiceImpl.CACHE_KEY_TITLE_ID_NAME);
		List<InformationTitle> allTitle = informationTitleDao.selectAll();
		for(InformationTitle atitle : allTitle){
			titleIdName.put(atitle.getId(), atitle.getName());
		}
		cache.set(CacheOperationServiceImpl.CACHE_KEY_TITLE_ID_NAME , titleIdName);

	}
	

	//title 表  id - name
	public void initValueIdName() {
		Map<Long,String> valueIdName = new HashMap<Long,String>();
		
		//cache.remove(CacheOperationServiceImpl.CACHE_KEY_VALUE_ID_NAME);
		List<InformationDictionary> allValue = informationDictionaryDao.selectAll();
		for(InformationDictionary avalue : allValue){
			valueIdName.put(avalue.getId(), avalue.getName());
		}
		cache.set(CacheOperationServiceImpl.CACHE_KEY_VALUE_ID_NAME , valueIdName);

	}
	
	
	/*
	public void initAreaTitleAndTValue() {
		//Map<String,List<InformationTitle>> pagesAreacode = new HashMap<String,List<InformationTitle>>();
		List<String> cacheAreascode = new ArrayList<String>();
		
		Object getK = cache.get(CacheOperationServiceImpl.CACHE_KEY_PAGE_AREA_TITLE_HASKEY);
		if(getK != null){
			List<String> cacheKey = (List<String>) getK;
			for(String ak : cacheKey){
				cache.remove(CacheOperationServiceImpl.CACHE_KEY_PAGE_AREA_TITLE +ak);
			}
		}
		
		//顶级：基本信息 ……
		List<InformationTitle> title_0_List = selectFirstTitle();
		
		for(InformationTitle title_0 : title_0_List){
			List<InformationTitle> title_List =  selectChildsByPid(title_0.getId()); //第一页下的各区域：基础信息 、事业部……
			for(InformationTitle title_1 : title_List){
				List<InformationTitle> title_title_value = selectTsTvalueInfo(title_1.getId());
				title_1.setChildList(title_title_value);
				//pagesAreacode.put(title_1.getCode(), title_title_value);
				cacheAreascode.add(title_1.getCode());
				cache.set(CacheOperationServiceImpl.CACHE_KEY_PAGE_AREA_TITLE +title_1.getCode(), title_1);
			}
		}
		
		cache.set(CacheOperationServiceImpl.CACHE_KEY_PAGE_AREA_TITLE_HASKEY, cacheAreascode);
	}
	*/
	
	
	
	/*
	private List<InformationTitle> selectFirstTitle() {
		List<InformationTitle> ptitleList = informationTitleDao.selectFirstTitle();
		ptitleList = ptitleList == null ? new ArrayList<InformationTitle>() : ptitleList;
		return ptitleList;
	}
	private List<InformationTitle> selectChildsByPid(Long pid) {
		Direction direction = Direction.ASC;
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
	private List<InformationTitle> selectTsTvalueInfo(Object pinfoKey) {
		List<InformationTitle> ts = selectChildsByPid((long)pinfoKey);
		for(InformationTitle title : ts){
			List<InformationDictionary> valueList = selectValuesByTid(title.getId());
			title.setValueList(valueList);
		}
		return ts;
	}
	private List<InformationDictionary> selectValuesByTid(Long tid) {
		Direction direction = Direction.ASC;
		String property = "sort";
		
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("titleId",tid);
		params.put("isValid",0);
		params.put("sorting", new Sort(direction, property).toString().replace(":", ""));
		List<InformationDictionary> ptitleList = informationDictionaryDao.selectValues(params);
		
		return ptitleList == null ? new ArrayList<InformationDictionary>() : ptitleList;
	}
	*/








	/*
	记入 code 对应的题目数目， 和 题 ids

	code ： NO  / DN PN GN ON   /  EN CN

	titletype :
		project     静态数据    "project"       ,11,
		result      结果表      "result"        ,1,2,3,4,5,6,8,12,13,14,15,16,18,19,20,
		listdata    结果表      "listdata"      ,10,
		fixedtable  结果表      "fixedtable"    ,9,
		file        结果表	   "file"          ,7,
		result_grage 结果表     "resultGrage" (relate_id)
	*/
	public static Map<String,Integer> code_titleNum = new HashMap<>();
	public static Map<String,Map<String,Set<Long>>> code_titletype_titleIds = new HashMap<>();

	public static final String result_titletype = ",1,2,3,4,5,6,8,12,13,14,15,16,18,19,20,";
	public static final String pre_reports_codes[] = new String[]{"NO","DN","PN","GN","ON","EN","CN"};

	public static Map<String,String> code_report$type = new HashMap<>();
	public static Map<String,String> code_report$type$grade = new HashMap<>();
	public void initMapConf(){
		code_titleNum = new HashMap<>();

		code_report$type.put("DN", "2"); // "尽调报告"
		code_report$type.put("PN", "3"); // "决策报告"
		code_report$type.put("GN", "5"); // "融资报告"
		code_report$type.put("ON", "7"); // "运营报告"

		code_report$type$grade.put("EN", "1"); // "评测报告"
		code_report$type$grade.put("CN", "6"); // "初评报告"


		code_titletype_titleIds = new HashMap<>();
		for(String code : pre_reports_codes){
			Map<String, Set<Long>> titletype_titleIds = new HashMap<>();
			titletype_titleIds.put("project", new HashSet<Long>());
			titletype_titleIds.put("result", new HashSet<Long>());
			titletype_titleIds.put("listdata", new HashSet<Long>());
			titletype_titleIds.put("fixedtable", new HashSet<Long>());
			titletype_titleIds.put("file", new HashSet<Long>());
			titletype_titleIds.put("resultGrage", new HashSet<Long>());

			code_titletype_titleIds.put(code,titletype_titleIds);
		}
	}


	public void initReportsCodeIdsAndNum() {
		//Long btime = System.currentTimeMillis();
		initMapConf();

		InformationTitle title = null;

		Set<Long> project_ids = new HashSet<>();
		Set<Long> result_ids = new HashSet<>();
		Set<Long> listdata_ids = new HashSet<>();
		Set<Long> fixedtable_ids = new HashSet<>();
		Set<Long> file_ids = new HashSet<>();
		Set<Long> resultGrage_ids = new HashSet<>();

		//全息报告
		Integer noNum = 0;
		List<InformationTitle> title_0_List = informationTitleDao.selectFirstTitle();
		//for(int i = 0 ; i < title_0_List.size(); i++){
		for(InformationTitle title_0 : title_0_List)
		{
			project_ids.clear();
			result_ids.clear();
			listdata_ids.clear();
			fixedtable_ids.clear();
			file_ids.clear();
			resultGrage_ids.clear();

			title = informationDictionaryService.selectTitlesValuesForAll(title_0.getCode(),null);
			noNum += getNumForTypeIsNotNull(title,project_ids,result_ids,listdata_ids,fixedtable_ids,file_ids);

			setCodeTypeTids("NO",  code_titletype_titleIds, project_ids,result_ids,listdata_ids,fixedtable_ids,file_ids,resultGrage_ids);

			/*System.err.println("全息报告　" + title_0.getCode() + " : " + noNum);
			System.err.println("project_ids　" + project_ids.size() + " : " + Arrays.toString(project_ids.toArray()));
			System.err.println("result_ids　" + result_ids.size() + " : " + Arrays.toString(result_ids.toArray()));
			System.err.println("listdata_ids　" + listdata_ids.size() + " : " + Arrays.toString(listdata_ids.toArray()));
			System.err.println("fixedtable_ids　" + fixedtable_ids.size() + " : " + Arrays.toString(fixedtable_ids.toArray()));
			System.err.println("file_ids　" + file_ids.size() + " : " +Arrays.toString(file_ids.toArray()));*/
		}
		/*System.err.println(" NO  tnum " + noNum);
		System.err.println(" NO  ids " + code_titletype_titleIds.get("NO"));*/


		Integer dnNum = 0;
		Integer pnNum = 0;
		Integer gnNum = 0;
		Integer onNum = 0;

		Integer num = 0;
		Map<String, Object> params = null;
		for(String codeLike : code_report$type.keySet()){
			num = 0;
			params = new HashMap<String,Object>();
			params.put("codeLike",codeLike);
			params.put("isValid",0);
			params.put("parentId",0);
			List<InformationTitle> titles = informationTitleRelateDao.selectTitleByRelate(params);

			for(int i = 0 ; i < titles.size(); i++)
			{
				project_ids.clear();
				result_ids.clear();
				listdata_ids.clear();
				fixedtable_ids.clear();
				file_ids.clear();
				resultGrage_ids.clear();

				title = informationDictionaryService.selectTitlesValuesForAll(titles.get(i).getRelateCode(),code_report$type.get(codeLike));
				num += getNumForTypeIsNotNull(title,project_ids,result_ids,listdata_ids,fixedtable_ids,file_ids);

				if (codeLike.equals("DN"))
				{
					setCodeTypeTids("DN",  code_titletype_titleIds, project_ids,result_ids,listdata_ids,fixedtable_ids,file_ids,resultGrage_ids);
				}else if (codeLike.equals("PN"))
				{
					setCodeTypeTids("PN",  code_titletype_titleIds, project_ids,result_ids,listdata_ids,fixedtable_ids,file_ids,resultGrage_ids);
				}else if (codeLike.equals("GN"))
				{
					setCodeTypeTids("GN",  code_titletype_titleIds, project_ids,result_ids,listdata_ids,fixedtable_ids,file_ids,resultGrage_ids);
				}else if (codeLike.equals("ON"))
				{
					setCodeTypeTids("ON",  code_titletype_titleIds, project_ids,result_ids,listdata_ids,fixedtable_ids,file_ids,resultGrage_ids);
				}

				/*System.err.println("其它报告　" + titles.get(i).getRelateCode() + " : " + num);
				System.err.println("project_ids　" + project_ids.size() + " : " + Arrays.toString(project_ids.toArray()));
				System.err.println("result_ids　" + result_ids.size() + " : " + Arrays.toString(result_ids.toArray()));
				System.err.println("listdata_ids　" + listdata_ids.size() + " : " + Arrays.toString(listdata_ids.toArray()));
				System.err.println("fixedtable_ids　" + fixedtable_ids.size() + " : " + Arrays.toString(fixedtable_ids.toArray()));
				System.err.println("file_ids　" + file_ids.size() + " : " +Arrays.toString(file_ids.toArray()));*/
			}
			/*System.err.println(codeLike + " tnum :  " + num);
			System.err.println(codeLike + " ids :  " + code_titletype_titleIds.get(codeLike));*/


			if (codeLike.equals("DN"))
			{
				dnNum = num;
			}else if (codeLike.equals("PN"))
			{
				pnNum = num;
			}else if (codeLike.equals("GN"))
			{
				gnNum = num;
			}else if (codeLike.equals("ON"))
			{
				onNum = num;
			}
		}


		Integer enNum = 0;
		Integer cnNum = 0;
		for(String codeLike : code_report$type$grade.keySet()){
			num = 0;
			params = new HashMap<String,Object>();
			params.put("codeLike",codeLike);
			params.put("isValid",0);
			params.put("parentId",0);
			List<InformationTitle> titles = informationTitleRelateDao.selectTitleByRelate(params);

			//for(InformationTitle title_0 : titles){
			for(int i = 0 ; i < titles.size(); i++){
				project_ids.clear();
				result_ids.clear();
				listdata_ids.clear();
				fixedtable_ids.clear();
				file_ids.clear();
				resultGrage_ids.clear();

				title = informationDictionaryService.selectTitlesValuesForAll( titles.get(i).getRelateCode(),code_report$type$grade.get(codeLike));
				num += getNumForTypeIsNotNullByGrade(title,project_ids,result_ids,listdata_ids,fixedtable_ids,file_ids,resultGrage_ids);

				if (codeLike.equals("EN"))
				{
					setCodeTypeTids("EN",  code_titletype_titleIds, project_ids,result_ids,listdata_ids,fixedtable_ids,file_ids,resultGrage_ids);
				}else if (codeLike.equals("CN"))
				{
					setCodeTypeTids("CN",  code_titletype_titleIds, project_ids,result_ids,listdata_ids,fixedtable_ids,file_ids,resultGrage_ids);
				}

				/*System.err.println("打分报告　" +  titles.get(i).getRelateCode() + " : " + num);
				System.err.println("project_ids　" + project_ids.size() + " : " + Arrays.toString(project_ids.toArray()));
				System.err.println("result_ids　" + result_ids.size() + " : " + Arrays.toString(result_ids.toArray()));
				System.err.println("listdata_ids　" + listdata_ids.size() + " : " + Arrays.toString(listdata_ids.toArray()));
				System.err.println("fixedtable_ids　" + fixedtable_ids.size() + " : " + Arrays.toString(fixedtable_ids.toArray()));
				System.err.println("file_ids　" + file_ids.size() + " : " + Arrays.toString(file_ids.toArray()));*/
			}
			/*System.err.println(codeLike + " tnum :  " + num);
			System.err.println(codeLike + " ids :  " + code_titletype_titleIds.get(codeLike));*/

			if (codeLike.equals("EN"))
			{
				enNum = num;
			}else if (codeLike.equals("CN"))
			{
				cnNum = num;
			}
		}

		code_titleNum.put("NO",noNum);
		code_titleNum.put("DN",dnNum);
		code_titleNum.put("PN",pnNum);
		code_titleNum.put("GN",gnNum);
		code_titleNum.put("ON",onNum);
		code_titleNum.put("EN",enNum);
		code_titleNum.put("CN",cnNum);

		//System.err.println( "===========  用时 ： "  +  (System.currentTimeMillis() -  btime));
		//System.err.println("=============");
	}


	/**
	 *  code - id 赋值
	 */
	public void setCodeTypeTids(String code, Map<String,Map<String,Set<Long>>> code_titletype_titleIds,
		Set<Long> project_ids, Set<Long> result_ids, Set<Long> listdata_ids, Set<Long> fixedtable_ids, Set<Long> file_ids,Set<Long> resultGrage_ids){
		code_titletype_titleIds.get(code).get("project").addAll(project_ids);
		code_titletype_titleIds.get(code).get("result").addAll(result_ids);
		code_titletype_titleIds.get(code).get("listdata").addAll(listdata_ids);
		code_titletype_titleIds.get(code).get("fixedtable").addAll(fixedtable_ids);
		code_titletype_titleIds.get(code).get("file").addAll(file_ids);
		code_titletype_titleIds.get(code).get("resultGrage").addAll(resultGrage_ids);
	}

	/**
	 * 递归计数、记入id
	 */
	public static int count;
	public Integer getNumForTypeIsNotNull(InformationTitle title,
										  Set<Long> project_ids, Set<Long> result_ids, Set<Long> listdata_ids, Set<Long> fixedtable_ids, Set<Long> file_ids){
		count = 0;
		setNumAdd(title,project_ids,result_ids,listdata_ids,fixedtable_ids,file_ids);
		return count;
	}
	public void setNumAdd(InformationTitle title,
						  Set<Long> project_ids, Set<Long> result_ids, Set<Long> listdata_ids, Set<Long> fixedtable_ids, Set<Long> file_ids){

		if(title.getType() != null && (title.getSign()!=null && title.getSign().intValue() == 2)){
			count += 1;
			Long titleId = (title.getTitleId() == null ? title.getId() : title.getTitleId());

			switch(title.getType())
			{
				case 7 :
					file_ids.add(titleId);
					break;
				case 9:
					fixedtable_ids.add(titleId);
					break;
				case 10:
					listdata_ids.add(titleId);
					break;
				case 11:
					project_ids.add(titleId);
					break;
				default:
					//if(null != title.getType() && result_titletype.contains(","+ title.getType() +",")){
					result_ids.add(titleId);
					//}
			}
		}

		if(title.getChildList()!=null && !title.getChildList().isEmpty()){
			for(InformationTitle temp : title.getChildList()){
				setNumAdd(temp,project_ids,result_ids,listdata_ids,fixedtable_ids,file_ids);
			}
		}
	}




	/**
	 * 递归计数、记入id
	 * 有 grade.score_explain   countByGrade+
	 * grade.is_score
	 * 			result         结果表 :   0：系统打分
	 * 			result_grade   结果表 :   1：人工打分（下拉框），2：人工打分（数值范围）
	 */
	public static int countByGrade;
	public Integer getNumForTypeIsNotNullByGrade(InformationTitle title,
		Set<Long> project_ids, Set<Long> result_ids, Set<Long> listdata_ids, Set<Long> fixedtable_ids, Set<Long> file_ids,Set<Long> resultGrage_ids){
		countByGrade = 0;
		setNumAddByGrade(title,project_ids,result_ids,listdata_ids,fixedtable_ids,file_ids,resultGrage_ids);
		return countByGrade;
	}
	public void setNumAddByGrade(InformationTitle title,
		Set<Long> project_ids, Set<Long> result_ids, Set<Long> listdata_ids, Set<Long> fixedtable_ids, Set<Long> file_ids,Set<Long> resultGrage_ids){

		List<InformationGrade> informationGrades = title.getInformationGrades();

		if(null != informationGrades && !informationGrades.isEmpty()){

			for(InformationGrade tempGrade : informationGrades){

				if(StringUtils.isNotBlank(tempGrade.getScoreExplain())){
					countByGrade += 1;
				}

				if(null != tempGrade.getIsScore()){
					switch(tempGrade.getIsScore())
					{
						case 0 :
							result_ids.add(title.getTitleId());
							break;
						case 1:
							resultGrage_ids.add(title.getId());
							break;
						case 2:
							resultGrage_ids.add(title.getId());
							break;
					}
				}


			}
		}

		if(title.getChildList()!=null && !title.getChildList().isEmpty()){
			for(InformationTitle temp : title.getChildList()){
				setNumAddByGrade(temp,project_ids,result_ids,listdata_ids,fixedtable_ids,file_ids,resultGrage_ids);
			}
		}

	}




}
