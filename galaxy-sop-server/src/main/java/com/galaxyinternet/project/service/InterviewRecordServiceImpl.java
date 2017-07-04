package com.galaxyinternet.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.dao.project.InterviewRecordDao;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.export_schedule.dao.ScheduleContactsDao;
import com.galaxyinternet.export_schedule.model.ScheduleContacts;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.dict.Dict;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.service.DictService;
import com.galaxyinternet.service.InterviewRecordService;


@Service("com.galaxyinternet.service.InterviewRecordService")
public class InterviewRecordServiceImpl extends BaseServiceImpl<InterviewRecord> implements InterviewRecordService {

	@Autowired
	private InterviewRecordDao interviewRecordDao;
	
	@Autowired
	private SopFileDao sopFileDao;
	
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private ScheduleContactsDao scheduleContactsDao;
	
	@Autowired
	private Cache cache;
	
	@Autowired
	private DictService dictService;
	
	
	@Override
	protected BaseDao<InterviewRecord, Long> getBaseDao() {
		return this.interviewRecordDao;
	}
	
	
	@Override
	@Transactional
	public Long insertInterview(InterviewRecord interviewRecord,SopFile sopFile) {
		Long sid = sopFileDao.insert(sopFile);
		if(sid == null){
			return null;
		}
		interviewRecord.setFileId(sid);
		Long id = getBaseDao().insert(interviewRecord);
		return id;
	}
	
	
	@Override
	@Transactional
	public Long updateViewForFile(SopFile sopFile, InterviewRecord view) {
		Long fileid = sopFileDao.insert(sopFile);
		view.setFileId(fileid);
		int updateN = interviewRecordDao.updateById(view);
		if(updateN == 0 ){
			return null;
		}
		return fileid;
	}
	
	
	@Override
	public Page<InterviewRecordBo> queryInterviewPageList(InterviewRecordBo query, Pageable pageable) {
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
	@Override
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
		 Map<Long,ScheduleContacts> map=getMapScheduleContacts();
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
				String resultReson="";
				if(null!=ib.getInterviewResult()&&ib.getInterviewResult().equals("meetingResult:2")){
					dictMap=dictMap("meetingUndeterminedReason");
				}else if(null!=ib.getInterviewResult()&&(ib.getInterviewResult().equals("meetingResult:3")
						||ib.getInterviewResult().equals("meeting5Result:2")||ib.getInterviewResult().equals("meeting3Result:6"))){
					dictMap=dictMap("meetingVetoReason");
				}else if(null!=ib.getInterviewResult()&&ib.getInterviewResult().equals("meeting5Result:1")){
					dictMap=dictMap("meetingFollowingReason");
				}
				if(null!=dictMap&&null!=ib.getResultReason()&&!"".equals(ib.getResultReason())){
					Dict dict=dictMap.get(ib.getResultReason());
					if(null!=dict){
						resultReson=dict.getName();	
					}
				}
				bo.setResultReason(ib.getResultReason());
				bo.setResultReasonStr(resultReson);
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


	@Override
	public int updateCreateUid(InterviewRecord ir) {
		
		return interviewRecordDao.updateCreateUid(ir);
	}
	
	public Map<Long,ScheduleContacts> getMapScheduleContacts(){
		List<ScheduleContacts> list = scheduleContactsDao.selectAll();
		Map<Long,ScheduleContacts> map=new HashMap<Long,ScheduleContacts>();
		for(int i=0;i<list.size();i++){
			ScheduleContacts scheduleContacts=list.get(i);
			map.put(scheduleContacts.getId(), scheduleContacts);
		}
		return map;
	}


	@Override
	public Long selectCount(InterviewRecordBo query)
	{
		return interviewRecordDao.selectCount(query);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Dict> dictMap(String parentCOde){
		Map<String,Dict> map=new HashMap<String,Dict>();
           Object object = cache.get(parentCOde);
           if(null==object){
        	   object=new HashMap<String,Dict>();
           }else{
        	   map=(Map<String,Dict>)object;
           }
		return map;
	}
	
}