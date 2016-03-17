package com.galaxyinternet.project.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.dao.project.InterviewRecordDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.file.UploadFileResult;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.service.InterviewRecordService;
import com.galaxyinternet.service.MeetingRecordService;


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
	@Transactional
	public Long insertInterview(InterviewRecord interviewRecord,SopFile sopFile) {
		Long sid = sopFileDao.insert(sopFile);
		interviewRecord.setFileId(sid);
		Long id = getBaseDao().insert(interviewRecord);
		
		return id;
	}
	
	@Override
	public Page<InterviewRecordBo> queryInterviewPageList(InterviewRecordBo query, Pageable pageable) {
		Page<InterviewRecordBo> viewPage = interviewRecordDao.selectInterviewPageList(query, pageable);
		List<InterviewRecordBo> contentList = viewPage.getContent();
		
		if(contentList!=null){
			for(InterviewRecordBo ib : contentList){
				String fileInfo = "";
				if(ib.getFileId()!=null){
					SopFile file  = sopFileDao.selectById(ib.getFileId());
					if(file!=null){
						ib.setFname(file.getFileName());
						ib.setFkey(file.getFileKey());
						//fileInfo = "<a href=\"javascript:filedown("+ib.getFileId()+","+file.getFileKey()+");\" key=\""+ file.getFileKey()+"\">"+file.getFileName()+"</a>";
					}
				}
				//ib.setFtgk("<div style=\"text-align:left;margin-left:20%;\">会议日期："+ib.getViewDateStr()+"</br>访谈对象："+ib.getViewTarget()+"</br>会议录音："+fileInfo+"</div>");
			}
		}
		return viewPage;
	}
	
	
}