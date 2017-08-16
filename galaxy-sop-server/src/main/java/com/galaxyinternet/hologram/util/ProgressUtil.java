package com.galaxyinternet.hologram.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;


@Service("com.galaxyinternet.hologram.util.ProgressUtil")
@Order
public class ProgressUtil implements InitializingBean{

	public void afterPropertiesSet() throws Exception {

	}
	/*
	code ： NO  / DN PN GN ON   /  EN CN
	*//*
	public static final String pre_reports_codes[] = new String[]{"NO","DN","PN","GN","ON","EN","CN"};

	public static Map<String,Integer> code_titleNum = new HashMap<>();

	public static Map<String,String> code_report$type = new HashMap<>();
	public static Map<String,String> code_report$type$grade = new HashMap<>();
	static{
		code_report$type.put("DN", "2"); // "尽调报告"
		code_report$type.put("PN", "3"); // "决策报告"
		code_report$type.put("GN", "5"); // "融资报告"
		code_report$type.put("ON", "7"); // "运营报告"

		code_report$type$grade.put("EN", "1"); // "评测报告"
		code_report$type$grade.put("CN", "6"); // "初评报告"
	}


	*//* code - tids
	   titletype :
	project     静态数据    "project"     ,11,
	result      结果表      "result"      ,1,2,3,4,5,6,8,12,13,14,15,16,18,19,20,
	listdata    结果表      "listdata"    ,10,
	fixedtable  结果表      "fixedtable"  ,9,
	file        结果表	   "file"        ,7,
	*//*
	public static final String result_titletype = ",1,2,3,4,5,6,8,12,13,14,15,16,18,19,20,";
	public static Map<String,Map<String,Set<Long>>> code_titletype_titleIds = new HashMap<>();
	static{
		for(String code : pre_reports_codes)
		{
 			Map<String, Set<Long>> titletype_titleIds = new HashMap<>();
			titletype_titleIds.put("project", new HashSet<Long>());
			titletype_titleIds.put("result", new HashSet<Long>());
			titletype_titleIds.put("listdata", new HashSet<Long>());
			titletype_titleIds.put("fixedtable", new HashSet<Long>());
			titletype_titleIds.put("file", new HashSet<Long>());

			code_titletype_titleIds.put(code,titletype_titleIds);
		}
	}



	@Autowired
	private Cache cache;

	@Autowired
	private InformationTitleRelateDao informationTitleRelateDao;

	@Autowired
	private InformationTitleDao informationTitleDao;

	@Autowired
	private InformationDictionaryService informationDictionaryService;


	@Override
	public void afterPropertiesSet() throws Exception {
		Long btime = System.currentTimeMillis();

		InformationTitle title = null;

		Set<Long> project_ids = new HashSet<>();
		Set<Long> result_ids = new HashSet<>();
		Set<Long> listdata_ids = new HashSet<>();
		Set<Long> fixedtable_ids = new HashSet<>();
		Set<Long> file_ids = new HashSet<>();

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

			title = informationDictionaryService.selectTitlesValuesForAll(title_0.getCode(),null);
			noNum += getNumForTypeIsNotNull(title,project_ids,result_ids,listdata_ids,fixedtable_ids,file_ids);

			setCodeTypeTids("NO",  code_titletype_titleIds, project_ids,result_ids,listdata_ids,fixedtable_ids,file_ids);

			*//*System.err.println("全息报告　" + title_0.getCode() + " : " + noNum);
			System.err.println("project_ids　" + project_ids.size() + " : " + Arrays.toString(project_ids.toArray()));
			System.err.println("result_ids　" + result_ids.size() + " : " + Arrays.toString(result_ids.toArray()));
			System.err.println("listdata_ids　" + listdata_ids.size() + " : " + Arrays.toString(listdata_ids.toArray()));
			System.err.println("fixedtable_ids　" + fixedtable_ids.size() + " : " + Arrays.toString(fixedtable_ids.toArray()));
			System.err.println("file_ids　" + file_ids.size() + " : " +Arrays.toString(file_ids.toArray()));*//*
		}


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

				title = informationDictionaryService.selectTitlesValuesForAll(titles.get(i).getRelateCode(),code_report$type.get(codeLike));
				num += getNumForTypeIsNotNull(title,project_ids,result_ids,listdata_ids,fixedtable_ids,file_ids);

				if (codeLike.equals("DN"))
				{
					setCodeTypeTids("DN",  code_titletype_titleIds, project_ids,result_ids,listdata_ids,fixedtable_ids,file_ids);
				}else if (codeLike.equals("PN"))
				{
					setCodeTypeTids("PN",  code_titletype_titleIds, project_ids,result_ids,listdata_ids,fixedtable_ids,file_ids);
				}else if (codeLike.equals("GN"))
				{
					setCodeTypeTids("GN",  code_titletype_titleIds, project_ids,result_ids,listdata_ids,fixedtable_ids,file_ids);
				}else if (codeLike.equals("ON"))
				{
					setCodeTypeTids("ON",  code_titletype_titleIds, project_ids,result_ids,listdata_ids,fixedtable_ids,file_ids);
				}


				*//*System.err.println("其它报告　" + titles.get(i).getRelateCode() + " : " + num);
				System.err.println("project_ids　" + project_ids.size() + " : " + Arrays.toString(project_ids.toArray()));
				System.err.println("result_ids　" + result_ids.size() + " : " + Arrays.toString(result_ids.toArray()));
				System.err.println("listdata_ids　" + listdata_ids.size() + " : " + Arrays.toString(listdata_ids.toArray()));
				System.err.println("fixedtable_ids　" + fixedtable_ids.size() + " : " + Arrays.toString(fixedtable_ids.toArray()));
				System.err.println("file_ids　" + file_ids.size() + " : " +Arrays.toString(file_ids.toArray()));*//*
			}

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

				title = informationDictionaryService.selectTitlesValuesForAll( titles.get(i).getRelateCode(),code_report$type$grade.get(codeLike));
				num += getNumForTypeIsNotNull(title,project_ids,result_ids,listdata_ids,fixedtable_ids,file_ids);

				if (codeLike.equals("EN"))
				{
					setCodeTypeTids("EN",  code_titletype_titleIds, project_ids,result_ids,listdata_ids,fixedtable_ids,file_ids);
				}else if (codeLike.equals("CN"))
				{
					setCodeTypeTids("CN",  code_titletype_titleIds, project_ids,result_ids,listdata_ids,fixedtable_ids,file_ids);
				}

				*//*System.err.println("打分报告　" +  titles.get(i).getRelateCode() + " : " + num);
				System.err.println("project_ids　" + project_ids.size() + " : " + Arrays.toString(project_ids.toArray()));
				System.err.println("result_ids　" + result_ids.size() + " : " + Arrays.toString(result_ids.toArray()));
				System.err.println("listdata_ids　" + listdata_ids.size() + " : " + Arrays.toString(listdata_ids.toArray()));
				System.err.println("fixedtable_ids　" + fixedtable_ids.size() + " : " + Arrays.toString(fixedtable_ids.toArray()));
				System.err.println("file_ids　" + file_ids.size() + " : " + Arrays.toString(file_ids.toArray()));*//*
			}

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


checkUsersProReportProgress(new Long(104),new Long(385));


	}




	*//**
	 * 公用 code - id 赋值
	 *//*
	public void setCodeTypeTids(String code, Map<String,Map<String,Set<Long>>> code_titletype_titleIds,
								Set<Long> project_ids, Set<Long> result_ids, Set<Long> listdata_ids, Set<Long> fixedtable_ids, Set<Long> file_ids){
		if(code_titletype_titleIds.containsKey(code)){
			code_titletype_titleIds.get(code).get("project").addAll(project_ids);
			code_titletype_titleIds.get(code).get("result").addAll(result_ids);
			code_titletype_titleIds.get(code).get("listdata").addAll(listdata_ids);
			code_titletype_titleIds.get(code).get("fixedtable").addAll(fixedtable_ids);
			code_titletype_titleIds.get(code).get("file").addAll(file_ids);
		}else{
			System.err.println(" ==== err ===== " + code);
		}
	}



	*//**
	 * 递归计数、记入id
	 *//*
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

			switch(title.getType())
			{
				case 7 :
					file_ids.add(title.getId());
					break;
				case 9:
					fixedtable_ids.add(title.getId());
					break;
				case 10:
					listdata_ids.add(title.getId());
					break;
				case 11:
					project_ids.add(title.getId());
					break;
				default:
					//if(null != title.getType() && result_titletype.contains(","+ title.getType() +",")){
						result_ids.add(title.getId());
					//}
			}
		}

		if(title.getChildList()!=null && !title.getChildList().isEmpty()){
			for(InformationTitle temp : title.getChildList()){
				setNumAdd(temp,project_ids,result_ids,listdata_ids,fixedtable_ids,file_ids);
			}
		}
	}





	*//**
	 * user project 的 各报告进度

	public void checkUsersProReportProgress(Long uid,Long proId,String code){


	}
	 *//*


























*/





}
