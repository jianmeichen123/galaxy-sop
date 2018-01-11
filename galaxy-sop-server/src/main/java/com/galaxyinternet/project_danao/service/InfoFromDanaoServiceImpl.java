package com.galaxyinternet.project_danao.service;

import com.galaxyinternet.dao.hologram.InformationDictionaryDao;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.model.DaNao.DnProject;
import com.galaxyinternet.model.DaNao.DnZixun;
import com.galaxyinternet.model.hologram.InformationDictionary;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.service.InfoFromDanaoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service("com.galaxyinternet.service.InfoFromDanaoService")
public class InfoFromDanaoServiceImpl implements InfoFromDanaoService {

	final Logger logger = LoggerFactory.getLogger(InfoFromDanaoServiceImpl.class);

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private InformationDictionaryDao informationDictionaryDao;

	@Autowired
	private RestTemplate restTemplate;

	private  @Value("${danao.domain}") String  danaoDomain;
	private  @Value("${danao.static.domain}") String  danaoStaticDomain;
	private  @Value("${xht.app.domain}") String  xhtAppDomain;



	//post 查询项目接口
	private String searchProject = "search/project";

	//post 获取资讯列表
	private String searchNews = "search/news";

	//跳转到 大脑项目详情页
	private String toDanaoDetailInfo = "project_qy.html?projCode=";

	//get  根据code查询项目详情 /{code}， 获取公司code
	private String projectInfo = "api/projectList/queryProjectByCode/";

	//get 查询项目的工商信息 /{code}  法人信息， 股权结构
	private String businessInfo = "api/businessInfo/getListBySourceCode/";

	//post 融资历史
	private String projectEven = "api/eventInfo/queryProjectEventList";

	//post 查询项目成员
	private String projectTeam = "api/projectList/queryProjectTeamByCode";





	/**
	 * 查询项目详情 -> 项目compCode
	 * @param projCode
	 * @return
	 * @throws Exception
	 */
	public DnProject queryDanaoProjCompCode(String projCode) throws Exception
	{
		DnProject pro = new DnProject();
		String uri = danaoDomain + projectInfo + projCode;
		Map<String,Object> object = restTemplate.getForObject(uri, Map.class);

		Integer status = (Integer) object.get("status");
		if(status.intValue() != 10000)
			throw new Exception(status.toString());

		String compCode = null;
		if(object.get("data") != null){
			if(((Map<String, Object>)object.get("data")).containsKey("compCode")){
				compCode = ((String)((Map<String, Object>)object.get("data")).get("compCode"));
				pro.setProjCode(compCode);
				pro.setProjCompanyName((String)((Map<String, Object>)object.get("data")).get("company"));
			}
		}

		return pro;
	}



	/**
	 * 查询大脑项目列表,封装项目公司信息
	 * 分条查询项目公司名称
	 */
	public Page<DnProject> queryDnaoProjectPage(Map<String, Object> map) throws Exception
	{
		Page<DnProject> dnProjectPage = null;
		List<DnProject> dnProjectList = new ArrayList<DnProject>();
		Long total = 0l;

		String uri = danaoDomain + searchProject; //+ "?uid="+map.get("uid");

		Map<String,Object> object = restTemplate.postForObject(uri,map.containsKey("query")?(Map<String,Object>)map.get("query"):map, Map.class);

		Integer status = (Integer) object.get("status"); //成功:10000 失败:10001 缺少参数:10002
		if(status.intValue() != 10000)
			throw new Exception(status.toString());

		Map<String,Object> pageMap = (Map<String, Object>) object.get("page");

		if(pageMap!=null && pageMap.get("total")!= null) total = new Long(pageMap.get("total").toString());
		if(total.intValue() == 0)
			return new Page<DnProject>(dnProjectList, total);

		List<Map<String,Object>> objectList = (List<Map<String, Object>>) pageMap.get("records");
		for(Map<String,Object> tempMap : objectList){
			DnProject target = new DnProject();
			org.apache.commons.beanutils.BeanUtils.populate(target, tempMap);

			target.setProjImage(danaoStaticDomain + "project/" + target.getProjCode() + ".png" );
			target.setHref(danaoDomain + toDanaoDetailInfo + target.getProjCode() );

			uri = danaoDomain + businessInfo + target.getCompCode(); //+ "?uid="+map.get("uid");
			Map<String,Object> gsxx = restTemplate.getForObject(uri, Map.class);
			status = (Integer) gsxx.get("status");
			if(status.intValue() == 10000 && gsxx.get("data") !=null )
			{
				target.setProjCompanyName((String)((Map<String, Object>)gsxx.get("data")).get("company"));
			}

			dnProjectList.add(target);
		}

		dnProjectPage = new Page<DnProject>(dnProjectList, total);

		return dnProjectPage;
	}

	/**
	 * 由大脑项目compCode
	 * 查询大脑项目工商信息
	 *   法人信息 legalInfo
	 *   股权结构 equityInfo
	 */
	public Map<String,Object> queryDnaoBusinessInfo(String compCode,String checkToChooseCode) throws Exception
	{
		Map<String,Object> result = new HashMap<>();
		Map<String,Object> legalInfo = new HashMap<>();
		List<Map<String,Object>> equityInfo = new ArrayList<>();

		String uri = danaoDomain + businessInfo + compCode;
		Map<String,Object> object = restTemplate.getForObject(uri, Map.class);

		Integer status = (Integer) object.get("status");
		if(status.intValue() != 10000)
			throw new Exception(status.toString());


		if(object.get("data") !=null)
		{
			Map<String,Object> dataD = (Map<String, Object>) object.get("data");

			//法人信息
			if(checkToChooseCode == null || "legalInfo".equals(checkToChooseCode))
			{
				legalInfo.put("company",dataCheck(dataD.get("company")));  // 公司名称
				legalInfo.put("foundDate",dataCheck(dataD.get("foundDate"))); // 成立日期
				legalInfo.put("legalPerson",dataCheck(dataD.get("legalPerson"))); // 法人
			}

			//股权结构
			if(checkToChooseCode == null || "equityInfo".equals(checkToChooseCode))
			{
				if(dataD.get("projectShareholderInfoList") !=null){
					List<LinkedHashMap<String,Object>> dataL = (List<LinkedHashMap<String,Object>>) dataD.get("projectShareholderInfoList");
					for(Map<String,Object> tempMap : dataL)
					{
						Map<String, Object> addMap = new HashMap<>();

						//股东名称
						addMap.put("shareholder", dataCheck(tempMap.get("shareholder")));

						//股东性质
						// id 2170 ;  下拉选： pid 2170
						String shareholderType = dataCheck(tempMap.get("shareholderType"));
						if(StringUtils.isNotBlank(shareholderType)){
							shareholderType = shareholderType.replace("股东","");
							InformationDictionary temp = selectDictionaryRecord(2170l,shareholderType);
							if(temp!=null){
								addMap.put("shareholderType", temp.getName());
								addMap.put("shareholderTypeId", temp.getId());
							}
						}
						//占股比例
						String equityRate = dataCheck(tempMap.get("equityRate"));
						if(StringUtils.isNotBlank(equityRate)){
							addMap.put("equityRate", equityRate.replace("%",""));
						}

						addMap.put("prePayDate", dataCheck(tempMap.get("prePayDate"))); // 认缴出资时间
						addMap.put("prePayAmountStr", dataCheck(tempMap.get("prePayAmountStr"))); // 认缴出资金额
						addMap.put("paidDate", dataCheck(tempMap.get("paidDate"))); // 实缴出资时间
						addMap.put("paidPayAmountStr", dataCheck(tempMap.get("paidPayAmountStr"))); // 实缴出资金额
						addMap.put("payType", dataCheck(tempMap.get("payType"))); // 出资方式

						equityInfo.add(addMap);
					}
				}
			}

			result.put("legalInfo",legalInfo);
			result.put("equityInfo",equityInfo);
		}

		return result;
	}



	/**
	 * 查询项目团队成员 teamInfo
	 * @param projCode
	 * @return
	 * @throws Exception
	 */
    public Map<String,Object> queryDnaoProjTeam(String projCode) throws Exception {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> teamInfo = new ArrayList<>();

        Map<String, Object> query = new HashMap<>();
        query.put("projectCode", projCode);
        query.put("state", "0"); //0 在职 1离职

        String uri = danaoDomain + projectTeam;
        Map<String,Object> object = restTemplate.postForObject(uri, query, Map.class);

        Integer status = (Integer) object.get("status");
        if(status.intValue() != 10000)
            throw new Exception(status.toString());

        if(object.get("data") !=null)
        {
			List<LinkedHashMap<String,Object>> dataL = (List<LinkedHashMap<String,Object>>) object.get("data");

			for(Map<String,Object> tempMap : dataL) {
				Map<String, Object> addMap = new HashMap<>();

				addMap.put("name", dataCheck(tempMap.get("name"))); //姓名

				//职位
				// 字典id 1351 ;  下拉选： pid 1351， other其他：1363
				String job = dataCheck(tempMap.get("job"));
				if(StringUtils.isNotBlank(job)){
					InformationDictionary temp = selectDictionaryRecord(1351l,job);
					if(temp!=null){
						addMap.put("job", temp.getName());
						addMap.put("jobId", temp.getId());
					}else{
						addMap.put("job", "other其他");
						addMap.put("jobId", 1363l);
						addMap.put("jobContentDescribe1", job);
					}
				}

				teamInfo.add(addMap);
			}
        }

        if(!teamInfo.isEmpty()) result.put("teamInfo", teamInfo);

		return result;
    }


	/**
	 * 查询项目融资历史 financeInfo
	 * @param projCode
	 * @return
	 * @throws Exception
	 */
    public Map<String,Object> queryDnaoProjFinance(String projCode) throws Exception {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> info = new ArrayList<>();

        Map<String, Object> query = new HashMap<>();
        query.put("sourceCode", projCode);

        String uri = danaoDomain + projectEven;
        Map<String,Object> object = restTemplate.postForObject(uri, query, Map.class);

        Integer status = (Integer) object.get("status");
        if(status.intValue() != 10000)
            throw new Exception(status.toString());

        if(object.get("data") !=null)
        {
			List<LinkedHashMap<String,Object>> dataL = (List<LinkedHashMap<String,Object>>) object.get("data");

			for(Map<String,Object> tempMap : dataL) {
				Map<String, Object> addMap = new HashMap<>();

				addMap.put("investDate", dataCheck(tempMap.get("investDate"))); //融资时间
				addMap.put("stock", dataCheck(tempMap.get("stock"))); //股权占比

				//融资轮次
				// 字典id 2183 ;  下拉选： pid 2183，
				String round = dataCheck(tempMap.get("round"));
				if(StringUtils.isNotBlank(round)){
					InformationDictionary temp = selectDictionaryRecord(2183l,round);
					if(temp!=null){
						addMap.put("round", temp.getName());
						addMap.put("roundId", temp.getId());
					}
				}

				Map<String,String> m_unit =  moneyCheck(tempMap.get("amountStr"));
				if(m_unit!=null){
					addMap.put("money", m_unit.get("num"));

					//融资历史币种 2180
					// 字典id 2180 ;  下拉选： pid 2180，
					String unit = m_unit.get("unit");
					if(StringUtils.isNotBlank(unit)){
						InformationDictionary temp = selectDictionaryRecord(2180l,unit);
						if(temp!=null){
							addMap.put("unit", temp.getName());
							addMap.put("unitId", temp.getId());
						}
					}
				}


				//投资方
				String investSideJson = dataCheck(tempMap.get("investSideJson"));
				if(StringUtils.isNotBlank(investSideJson)){
					//Map<String,Object> map = GSONUtil.fromJson(investSideJson, Map.class);
				}

				info.add(addMap);
			}
        }

		if(!info.isEmpty()) result.put("financeInfo", info);

		return result;
    }



	/*
    */
	public InformationDictionary selectDictionaryRecord(Long pid,String nameLike){
		InformationDictionary query = new InformationDictionary();
		query.setParentId(pid);
		query.setName(nameLike);
		InformationDictionary result = informationDictionaryDao.selectOne(query);

		if(result == null){
			query = new InformationDictionary();
			query.setParentId(pid);
			query.setNameLike(nameLike);
			result = informationDictionaryDao.selectOne(query);
		}
		return result;
	}

	public String dataCheck(Object temp){
		if(temp == null){
			return null;
		}

		String str = temp.toString();
		if(StringUtils.isBlank(str)){
			return null;
		}

		String deal = str.replace("-","").replace("--","").replace("——","").trim();
		if(StringUtils.isBlank(deal)){
			return null;
		}

		return str.trim();
	}
	public Map<String,String> moneyCheck(Object temp){
		if(temp == null){
			return null;
		}

		String str = temp.toString().trim();

		//1.判断是否数字开头
		//2.数字 汉子 分离
		String num = "";
		String unit = "";
		Pattern pattern = Pattern.compile("^(\\d+\\.?\\d+)(.*)");
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {//数字开头
			num = matcher.group(1);
			unit = matcher.group(2);
		}else{
			return null;
		}

		BigDecimal bnum = new BigDecimal(num);

		Map<Character, String> mark = new HashMap<>();
		mark.put('十',"10");
		mark.put('百',"100");
		mark.put('千',"1000");
		mark.put('万',"10000");
		mark.put('亿',"100000000");

		for (int i = 0; i < unit.length();) {
			if(mark.containsKey(unit.charAt(0))){
				bnum = bnum.multiply(new BigDecimal(mark.get(unit.charAt(0))));
				unit = unit.substring(1);
			}else{
				break;
			}
		}

		Map<String, String> result = new HashMap<>();
		result.put("num", bnum.divide(new BigDecimal("10000"),new MathContext(2)).toPlainString());
		result.put("unit",unit);

		return result;
	}






	//todo 全局搜索

	/**
	 * 全局搜索的各类别total数目
	 * return Map
	 *   xhtProject        创投项目
	 *   dnProject         创投大脑的项目
	 *   dnZixun           创投大脑投融快讯
	 *   xhtAppZixun       星河资讯-app资讯
	 *
	 //升序:asc 降序:desc ,  private String order;
	 //排序字段              private String orderBy;
	 //当前页码              private Integer pageNo;
	 //每页记录数            private Integer pageSize;
	 //搜索关键字            private String keyword;
	 */
	public Map<String,Long> globalSearchTypesTotal(String keyword) throws Exception{
		Map<String, Long> result = new HashMap<>();
		Long xhtProjectTotal = 0l;
		Long dnProjectTotal =  0l;
		Long dnZixunTotal =  0l;
		Long xhtAppZixunTotal =  0l;

		//xhtProject        星河投创投项目
		Project project = new Project();
		project.setKeyword(keyword);
		xhtProjectTotal = projectDao.selectCount(project);

		Map<String, Object> query = new HashMap<>();
		query.put("pageNo",0);
		query.put("pageSize",10);
		query.put("newCaption",project.getKeyword());
		query.put("keyword",project.getKeyword());
		//query.put("order","desc");
		//query.put("orderBy","setupDT");

		//dnProject         创投大脑的项目
		String uri = danaoDomain + searchProject;
		Map<String,Object> object = restTemplate.postForObject(uri,query, Map.class);
		Integer status = (Integer) object.get("status");
		if(status.intValue() != 10000)
			throw new Exception(status.toString());
		Map<String,Object> pageMap = (Map<String, Object>) object.get("page");
		if(pageMap!=null && pageMap.get("total")!= null) dnProjectTotal = new Long(pageMap.get("total").toString());


		//dnZixun           创投大脑投融快讯
		uri = danaoDomain + searchNews;
		object = restTemplate.postForObject(uri,query, Map.class);
		status = (Integer) object.get("status");
		if(status.intValue() != 10000)
			throw new Exception(status.toString());
		pageMap = (Map<String, Object>) object.get("page");
		if(pageMap!=null && pageMap.get("total")!= null) dnZixunTotal = new Long(pageMap.get("total").toString());


		//xhtAppZixunZixun  星河资讯-app资讯
		uri = xhtAppDomain;
		object = restTemplate.postForObject(uri,query, Map.class);
		if(object!=null && object.get("listCount")!=null){
			xhtAppZixunTotal = new Long(object.get("listCount").toString());
		}

		result.put("xhtProjectTotal", xhtProjectTotal);
		result.put("dnProjectTotal", dnProjectTotal);
		result.put("dnZixunTotal", dnZixunTotal);
		result.put("xhtAppZixunTotal", xhtAppZixunTotal);
		return result;
	}


	/**
	 * 查询创投大脑 投融快讯列表,
	 *
	 */
	public Page<DnZixun> queryDnaoZixunPage(Map<String, Object> map) throws Exception
	{
		Page<DnZixun> page = null;
		List<DnZixun> list = new ArrayList<DnZixun>();
		Long total = 0l;

		String uri = danaoDomain + searchNews;

		Map<String,Object> object = restTemplate.postForObject(uri,map, Map.class);

		Integer status = (Integer) object.get("status"); //成功:10000 失败:10001 缺少参数:10002
		if(status.intValue() != 10000)
			throw new Exception(status.toString());

		Map<String,Object> pageMap = (Map<String, Object>) object.get("page");

		if(pageMap!=null && pageMap.get("total")!= null) total = new Long(pageMap.get("total").toString());
		if(total.intValue() == 0)
			return new Page<DnZixun>(list, total);

		List<Map<String,Object>> objectList = (List<Map<String, Object>>) pageMap.get("records");
		for(Map<String,Object> tempMap : objectList){
			DnZixun target = new DnZixun();
			org.apache.commons.beanutils.BeanUtils.populate(target, tempMap);

			if(StringUtils.isNotBlank(target.getImgmd5())){
				target.setZixunImage(danaoStaticDomain  + "news/" + target.getImgmd5() + ".PNG" );
			}

			target.setCtimeStr(convertTime(target.getCtime()));

			list.add(target);
		}

		page = new Page<DnZixun>(list, total);

		return page;
	}


	/**
	 * 查询 星河资讯- app资讯 列表,
	 *
	 */
	public Page<DnZixun> queryXhtAppZixunPage(Map<String, Object> map) throws Exception
	{
		Page<DnZixun> page = null;
		List<DnZixun> list = new ArrayList<DnZixun>();
		Long total = 0l;

		String uri = xhtAppDomain;
		map.put("newCaption",map.get("keyword"));
		Map<String,Object> object = restTemplate.postForObject(uri,map, Map.class);
		if(object!=null && object.get("listCount")!=null){
			total = new Long(object.get("listCount").toString());
		}

		List<Map<String,Object>> objectList = (List<Map<String, Object>>) object.get("newsList");
		for(Map<String,Object> tempMap : objectList){
			DnZixun target = new DnZixun();

			target.setHref((String) tempMap.get("newsUrl"));
			target.setTitle((String) tempMap.get("newsCaption"));
			target.setAuther((String) tempMap.get("newsSource"));
			if(tempMap.get("listImgArray") != null){
				List<Map<String,String>> listImgMap = (List<Map<String,String>>) tempMap.get("listImgArray");
				target.setZixunImage(listImgMap.get(0).get("url"));
			}

			String ctime = (String) tempMap.get("createTime");
			target.setCtime(ctime);
			target.setCtimeStr(convertTime(ctime));

			list.add(target);
		}

		page = new Page<DnZixun>(list, total);

		return page;
	}

    /**
     1小时之内的新闻                 按照分钟展示（如29分钟前）
     1小时之前，24小时之内的新闻     按小时展示（如2小时前或23小时前）
     24小时之前    ，                格式为“YYYY-MM-DD”
     * @param ctime
     * @return
     */
    public String convertTime(String ctime){
	    if(StringUtils.isBlank(ctime)){
	        return null;
        }

	    if(ctime.indexOf(".")!=-1){
	        ctime = ctime.substring(0,ctime.indexOf("."));
        }

        if(ctime.length()<13){
	        for(;ctime.length()<13;){
                ctime+="0";
            }
        }

        Long curr = System.currentTimeMillis();
        Long ctimeL = Long.parseLong(ctime);

        Long value = curr - ctimeL;
        if(value.intValue() < 1*60*60*1000){
        	int ts = value.intValue()/(60*1000);
			ctime = ts + "分钟前";
        }else if(value.intValue() < 24*60*60*1000 ){
			int ts = value.intValue()/(60*60*1000);
			ctime = ts + "小时前";
		}else{
        	ctime = DateUtil.longToString(ctimeL);
		}

	    return ctime;
    }



}