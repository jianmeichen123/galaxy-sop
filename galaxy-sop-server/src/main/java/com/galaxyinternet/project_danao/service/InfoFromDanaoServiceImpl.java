package com.galaxyinternet.project_danao.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aliyun.oss.ServiceException;
import com.galaxyinternet.model.DongNao.DnProject;
import com.galaxyinternet.service.InfoFromDanaoService;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
	private RestTemplate restTemplate;

	private  @Value("${danao.domain}") String  danaoDomain;
	private  @Value("${danao.static.domain}") String  danaoStaticDomain;



	//post 查询项目接口 ?uid= user.getSessionId()
	private String searchProject = "search/project";
	//get 查询项目的工商信息 /getListBySourceCode/{code}
	private String businessInfo = "api/businessInfo/getListBySourceCode/";

	



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


	public Map<String,Object> queryDnaoBusinessInfo(String projCode) throws Exception
	{
		String uri = danaoDomain + businessInfo + projCode;

		Map<String,Object> object = restTemplate.getForObject(uri, Map.class);

		Integer status = (Integer) object.get("status"); //成功:10000 失败:10001 缺少参数:10002
		if(status.intValue() != 10000)
			throw new Exception(status.toString());

		return object;
	}


/*
法人信息，
	公司名称 没有则整行不显示
	成立日期 没有则整行不显示
	法人     没有则整行不显示

*/




/*
股权结构
	股东名称
	股东类型
		该字段星河投为选择项，
		目前内容为：战略投资人、财务投资人、创始团队；
		从创投大脑引用过来的数据，
		如内容可与星河投匹配上，则显示推荐内容，
		如内容匹配不上，则不显示推荐内容
	股东性质
		选择项
	占股比例
		别重复插入%
	备注
		将创投大脑的以下5个字段信息，组合显示到备注里，每个字段一行
		信息包括：
			”认缴出资时间”、
			”认缴出资金额”、
			”实缴出资时间”、
			”实缴出资金额”、
			”出资方式”
*/





}