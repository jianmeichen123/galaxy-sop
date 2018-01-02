package com.galaxyinternet.project.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.galaxyinternet.service.InfoFromDanaoService;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	//@Value("${sop.oss.tempfile.path}")
	private  @Value("${danao.domain}") String  danaoDomain;
	private  @Value("${danao.static.domain}") String  danaoStaticDomain;



	//查询项目接口 ?uid= user.getSessionId()
	private String SearchProjest = "search/project";

	



	public void queryDnaoProjectPage(Map<String, Object> map) throws Exception {
		String uri = danaoDomain + SearchProjest;
				//+ "?uid="+map.get("uid");

		Map<String,Object> object = restTemplate.postForObject(uri,(Map<String,Object>)map.get("query"), Map.class);


		System.out.println("");

	}



	
	/*public Page<InterviewRecordBo> queryInterviewPageList(InterviewRecordBo query, Pageable pageable) {
		Page<InterviewRecordBo> viewPage = interviewRecordDao.selectInterviewPageList(query, pageable);
		List<InterviewRecordBo> contentList = viewPage.getContent();
		
		if(contentList!=null){
			for(InterviewRecordBo ib : contentList){
				if(ib.getFileId()!=null){
					SopFile file  = sopFileDao.selectById(ib.getFileId());
					if(file!=null){
						ib.setFname(file.getFileName());
						ib.setFkey(file.getFileKey());
					}
				}
			}
		}
		return viewPage;
	}

	
	// 项目tab查询     projectId
	// 列表查询， uid;  project_name\project_code ~ keyword  ||  startTime;  endTime; 
	public Page<InterviewRecordBo> queryInterviewPage(InterviewRecordBo query, Pageable pageable) {
		Page<InterviewRecordBo> viewPage = null;
		List<InterviewRecordBo> viewBoList = null;
		List<InterviewRecord> viewList = null;
		Long total = null;
		Map<Long,String> proIdNameMap = new HashMap<Long,String>();

		if(query.getProjectId()!=null){   // 项目tab查询
			Project po = projectDao.selectById(query.getProjectId());
			proIdNameMap.put(query.getProjectId(), po.getProjectName());
			viewList = interviewRecordDao.selectList(query, pageable);
			total = interviewRecordDao.selectCount(query);
		}else{    //列表查询_个人创建/部门
			Project  proQ = new Project();
			proQ.setCreateUid(query.getUid());
			proQ.setProjectDepartid(query.getDepartId());
			proQ.setKeyword(query.getKeyword());
			List<Project> proList = projectDao.selectList(proQ);
			
			//获取 projectId List
			if(proList!=null&&!proList.isEmpty()){
				List<Long> proIdList = new ArrayList<Long>();
				for(Project apro : proList){
					proIdList.add(apro.getId());
					proIdNameMap.put(apro.getId(), apro.getProjectName());
				}
				//查询访谈列表  
				query.setProIdList(proIdList);
				viewList = interviewRecordDao.selectList(query, pageable);
				total = interviewRecordDao.selectCount(query);
			}
		}
		 Map<Long,ScheduleContacts> map=new HashMap<>();
		if(viewList!=null&&!viewList.isEmpty()){
			viewBoList = new ArrayList<InterviewRecordBo>();
			for(InterviewRecord ib : viewList){
				InterviewRecordBo bo = new InterviewRecordBo();
				bo.setId(ib.getId());
				if(ib.getScheduleId()!=0){
					Long s=Long.parseLong(ib.getViewTarget());
					ScheduleContacts sc=(ScheduleContacts)map.get(s);
					bo.setViewTarget(sc!=null?sc.getName():"");
				}else{
					bo.setViewTarget(ib.getViewTarget());
				}
				
				bo.setProjectId(ib.getProjectId());
				bo.setProName(proIdNameMap.get(ib.getProjectId()));
				bo.setViewDateStr(ib.getViewDateStr());
				bo.setViewNotes(ib.getViewNotes());
				bo.setCreatedId(ib.getCreatedId());
				bo.setInterviewResult(ib.getInterviewResult());
				bo.setInterviewResultStr(DictEnum.meetingResult.getNameByCode(ib.getInterviewResult()));
				bo.setReasonOther(ib.getReasonOther());
				Map<String,Dict> dictMap = new HashMap<String,Dict>();


				if(ib.getFileId()!=null){
					SopFile file  = sopFileDao.selectById(ib.getFileId());
					if(file!=null){
						bo.setFileId(ib.getFileId());
						bo.setFname(file.getFileName());
						bo.setFkey(file.getFileKey());
					}
				}
				viewBoList.add(bo);
			}
			viewPage = new Page<InterviewRecordBo>(viewBoList, pageable, total);
		}else{
			viewPage = new Page<InterviewRecordBo>(new ArrayList<InterviewRecordBo>() , pageable, 0l);
		}
		
		return viewPage;
	}

*/

}