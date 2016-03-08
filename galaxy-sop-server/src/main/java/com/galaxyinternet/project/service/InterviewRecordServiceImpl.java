package com.galaxyinternet.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.dao.project.InterviewRecordDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.service.InterviewRecordService;


@Service("com.galaxyinternet.service.InterviewRecordService")
public class InterviewRecordServiceImpl extends BaseServiceImpl<InterviewRecord> implements InterviewRecordService {

	@Autowired
	private InterviewRecordDao interviewRecordDao;
	
	@Autowired
	private SopFileDao sopFileDao;
	
	
	@Override
	protected BaseDao<InterviewRecord, Long> getBaseDao() {
		return this.interviewRecordDao;
	}

	
	@Override
	public Page<InterviewRecordBo> queryInterviewPageList(InterviewRecordBo query, Pageable pageable) {
		Page<InterviewRecordBo> viewPage = interviewRecordDao.selectInterviewPageList(query, pageable);
		List<InterviewRecordBo> contentList = viewPage.getContent();
		
		if(contentList!=null){
			SopFile file = null;
			String fileInfo = "";
			
			for(InterviewRecordBo ib : contentList){
				if(ib.getFileId()!=null){
					file = sopFileDao.selectById(ib.getFileId());
					
					if(file!=null){
						//ib.setFname(file.getFileName());
						ib.setFkey(file.getFileKey());
						fileInfo = "<a href=\"javascript:;\" key=\""+ file.getFileKey()+"\">"+file.getFileKey()+"</a>";
					}
				}
				
				ib.setFtgk("<div style=\"text-align:left;margin-left:20%;\">会议日期："+ib.getViewDateStr()+"</br>访谈对象："+ib.getViewTarget()+"</br>会议录音："+fileInfo+"</div>");
			}
		}
		
		
		
		return viewPage;
	}
	
	
}