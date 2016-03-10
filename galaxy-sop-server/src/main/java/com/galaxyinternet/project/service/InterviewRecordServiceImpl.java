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

	
	//文件上传, 成功后插入 sopfile 数据库
	@Transactional
	public Long upfile(MultipartFile file,Long uid,String path,Long projectId,String projectProgress){
		Long fileId = null;
		try {
			UploadFileResult upResult = null;
			
			if(file != null){
				String fileName = file.getOriginalFilename(); // secondarytile.png  全名
				
				int dotPos = fileName.lastIndexOf(".");
				
				String key = String.valueOf(IdGenerator.generateId(OSSHelper.class));
				
				String ext = fileName.substring(dotPos);  // .png  
				
				File temp = new File(path,fileName);
				if (!temp.exists()) {
					temp.mkdirs();
				}
				
				file.transferTo(temp);  //存储临时文件
				
				upResult = OSSHelper.simpleUploadByOSS(temp,key);  //上传至阿里云
				
				//若文件上传成功
				if(upResult.getResult().getStatus()!=null && upResult.getResult().getStatus().equals(Status.OK)){
					
					SopFile sopFile = new SopFile();
					sopFile.setProjectId(projectId);
					sopFile.setProjectProgress(projectProgress);
					sopFile.setBucketName(upResult.getBucketName()); //bucketName
					sopFile.setFileKey(key);   //fileKey
					sopFile.setFileLength(upResult.getContentLength());  //文件大小
					sopFile.setFileName(fileName);  //文件名称 temp.getName()  upload4196736950003923576secondarytile.png
					sopFile.setFileUid(uid);	 //上传人
					//sopFile.setFileType("");   //存储类型
					//sopFile.setFileSource(Integer.parseInt(fileSource));  //档案来源
					//sopFile.setFileWorktype(fileWorkType);    //业务分类
					sopFile.setFileStatus(DictEnum.fileStatus.已上传.getCode());  //档案状态
					
					fileId = sopFileDao.insert(sopFile);
					
					//meetingRecord.setFileId(sopFile.getId());
				}else{
					throw new BusinessException("meeting service upfile failed");
				}
			}
		} catch (Exception e) {
			throw new BusinessException("meeting service upfile failed", e);
		}
		
		return fileId;
	}
	
	
	@Override
	@Transactional
	public Long insertInterview(InterviewRecord interviewRecord,Project project,MultipartFile file, String path,Long userid) {
		if(file != null){
			Long fileId = upfile(file,userid,path,project.getId(),project.getProgress());
			interviewRecord.setFileId(fileId);
		}
		
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