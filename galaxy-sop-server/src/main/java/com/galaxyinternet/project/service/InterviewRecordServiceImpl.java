package com.galaxyinternet.project.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.dao.project.InterviewRecordDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.service.InterviewRecordService;


@Service("com.galaxyinternet.service.InterviewRecordService")
public class InterviewRecordServiceImpl extends BaseServiceImpl<InterviewRecord> implements InterviewRecordService {

	@Autowired
	private InterviewRecordDao interviewRecordDao;
	
	
	@Override
	protected BaseDao<InterviewRecord, Long> getBaseDao() {
		return this.interviewRecordDao;
	}

	
	@Override
	public Page<InterviewRecordBo> queryInterviewPageList(InterviewRecordBo query, Pageable pageable) {
		Page<InterviewRecordBo> viewPage = interviewRecordDao.selectInterviewPageList(query, pageable);
		if(viewPage.getContent()!=null && viewPage.getContent().size()>0){
			//file实体
			for(InterviewRecordBo ib : viewPage.getContent()){
				//查询附件信息
				ib.setFname("");
				ib.setFuri("");
			}
		}
		
		return viewPage;
	}
	
	
}