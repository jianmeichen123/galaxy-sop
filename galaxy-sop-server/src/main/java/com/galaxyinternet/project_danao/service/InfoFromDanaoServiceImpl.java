package com.galaxyinternet.project_danao.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.aliyun.oss.ServiceException;
import com.galaxyinternet.dao.hologram.InformationDictionaryDao;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.model.DongNao.DnProject;
import com.galaxyinternet.model.hologram.InformationDictionary;
import com.galaxyinternet.service.InfoFromDanaoService;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.common.SopResult;
import com.galaxyinternet.common.annotation.MessageHandlerInterceptor;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.query.ProjectQuery;
import com.galaxyinternet.dao.project.InterviewRecordDao;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.export_schedule.dao.ScheduleContactsDao;
import com.galaxyinternet.export_schedule.model.ScheduleContacts;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.file.UploadFileResult;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.dict.Dict;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.service.DictService;
import com.galaxyinternet.service.InterviewRecordService;
import org.springframework.web.client.RestTemplate;


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



	//post 查询项目接口
	private String searchProject = "search/project";

	//get 查询项目的工商信息 /{code}  法人信息， 股权结构
	private String businessInfo = "api/businessInfo/getListBySourceCode/";

	//post 融资历史
	private String projectEven = "api/eventInfo/queryProjectEventList";

	//post 查询项目成员
	private String projectTeam = "api/projectList/queryProjectTeamByCode";



	/**
	 * 查询大脑项目列表
	 * 分条查询项目公司名称
	 */
	public Page<DnProject> queryDnaoProjectPage(Map<String, Object> map) throws Exception
	{
		Page<DnProject> dnProjectPage = null;
		List<DnProject> dnProjectList = new ArrayList<DnProject>();
		Long total = 0l;

		String uri = danaoDomain + searchProject; //+ "?uid="+map.get("uid");

		Map<String,Object> object = restTemplate.postForObject(uri,(Map<String,Object>)map.get("query"), Map.class);

		Integer status = (Integer) object.get("status"); //成功:10000 失败:10001 缺少参数:10002
		if(status.intValue() != 10000)
			throw new Exception(status.toString());

		Map<String,Object> pageMap = (Map<String, Object>) object.get("page");

		total =  new Long(pageMap.get("total").toString());
		if(total.intValue() == 0)
			return new Page<DnProject>(dnProjectList, total);

		List<Map<String,Object>> objectList = (List<Map<String, Object>>) pageMap.get("records");
		for(Map<String,Object> tempMap : objectList){
			DnProject target = new DnProject();
			org.apache.commons.beanutils.BeanUtils.populate(target, tempMap);

			target.setProjImage(danaoStaticDomain + target.getProjCode() + ".png" );

			uri = danaoDomain + businessInfo + target.getProjCode(); //+ "?uid="+map.get("uid");
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
	 * 由大脑项目code
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

			if(!legalInfo.isEmpty()) result.put("legalInfo",legalInfo);
			if(!legalInfo.isEmpty()) result.put("equityInfo",equityInfo);
		}

		return result;
	}





    //团队成员 teamInfo
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



    //融资历史 financeInfo
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
		result.put("num", bnum.toString());
		result.put("unit",unit);

		return result;
	}

}