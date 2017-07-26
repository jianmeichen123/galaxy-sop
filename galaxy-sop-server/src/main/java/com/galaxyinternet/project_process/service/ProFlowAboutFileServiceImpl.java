package com.galaxyinternet.project_process.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.dictEnum.DictEnum.fileStatus;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.enums.DictEnum.RecordType;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.dao.soptask.SopTaskDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.file.UploadFileResult;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.oss.OSSFactory;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.project_process.util.ProFlowUtilImpl;
import com.galaxyinternet.service.UserService;


@Service("com.galaxyinternet.project_process.service.ProjectFlowAboutFileService")
public class ProFlowAboutFileServiceImpl extends BaseServiceImpl<Project> implements ProFlowAboutFileService  {

	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private SopFileDao sopFileDao;
	@Autowired
	private SopTaskDao sopTaskDao;
	@Autowired
	private UserService userService;

	
	@Override
	protected BaseDao<Project, Long> getBaseDao() {
		return this.projectDao;
	}




	
	
	/**  * id
	 * 
	 * Boolean init;      // 文档是否是初始化状态， 是：记录update  否：记录dead，insert
	 
		 Boolean canOpt;    // 文档可操作： 上传、编辑
		 Boolean canDown;   // 文档可操作： 下载

		 String taskStatusStr;   // 关联任务状态
		 Long taskUid;           // 认领人id
		 String taskUname;       // 认领人name

		FilUri                   // 文档地址
	
	 * 上传人/起草者                Long fileUid;
	 * 
     * 文件大小                              Long fileLength;
     * 档案阿里云存储KEY   String fileKey;
    
     * 文档名称                               String fileName;
     * 文档后缀                               String fileSuffix;
     * 
     * 业务分类                              String fileWorktype;
     *                           fWorktype
     * 
     * 项目ID             Long projectId;
     * 档案BackName       String bucketName;
     */
	
	
	
	/**
	 * 根据  项目id、项目 progress ，封装当前文件的信息;  
	 * @param   pro.getId()  pro.getProjectProgress()
	 * @return
	 */
	public List<SopFile> getFileListForProFlow(Project pro){
		List<SopFile> result = new ArrayList<SopFile>();
		
		//fileWorkType
		List<String> fileWorkType_progress = new ArrayList<>(ProFlowUtilImpl.file_about.get(pro.getProjectProgress()).keySet());
		
		//taskFlag
		List<Integer> taskFlag_progress = new ArrayList<>();
		Set<Integer> taskFlags = new HashSet<>(ProFlowUtilImpl.file_about.get(pro.getProjectProgress()).values());
		for(Integer tem : taskFlags){
			if(tem!=null) taskFlag_progress.add(tem);
		}
		
		
		SopFileBo sbo =  new SopFileBo();
		sbo.setProjectId(pro.getId());
		sbo.setFileworktypeList(fileWorkType_progress);
		sbo.setFileValid(1);
		sbo.setRecordType((byte) 0);
		List<SopFile> fileList = sopFileDao.queryByFileTypeList(sbo);
		
		
		List<SopTask> taskList = null;
		if(taskFlag_progress!=null && !taskFlag_progress.isEmpty()){
			SopTask sopTask  = new SopTask();
			sopTask.setProjectId(pro.getId());
			sopTask.setTaskFlagS(taskFlag_progress);
			//taskList = sopTaskDao.selectList(sopTask);
			taskList = sopTaskDao.selectXXXXXX(sopTask);
		}
		
		
		SopFile temFile =  null;
		for(Entry<String,Integer> tem : ProFlowUtilImpl.file_about.get(pro.getProjectProgress()).entrySet()){
			
			temFile =  new SopFile();
			//temFile.setInitMark(SopFile.INIT_FILE_MAEK_NO);
			//temFile.setfWorktype(DictEnum.fileWorktype.getNameByCode(tem.getKey()));
			temFile.setFileWorktype(tem.getKey());
			
			if(tem.getValue()!=null){
				temFile.setTaskStatusStr("待认领");
				
				for(SopTask ta : taskList){
					if(tem.getValue().intValue() == ta.getTaskFlag().intValue()){
						if(ta.getAssignUid() != null){
							temFile.setTaskStatusStr("已认领");
							temFile.setTaskUid(ta.getAssignUid());
							User user = userService.queryById(ta.getAssignUid());
							temFile.setTaskUname(user.getRealName());
						}
						break;
					}
				}
			}
			
			for(SopFile tf : fileList){

				if(tem.getKey().equals(tf.getFileWorktype())){
					temFile.setId(tf.getId());
					temFile.setFileUid(tf.getFileUid());
					temFile.setFileSuffix(tf.getFileSuffix());
					temFile.setFileKey(tf.getFileKey());
					temFile.setFileType(tf.getFileType());
					temFile.setFilUri(tf.getFilUri());
					if(fileStatus.已放弃.getCode().equals(tf.getFileStatus()))
					{
						temFile.setTaskStatusStr(fileStatus.已放弃.getName());
					}
					/*
					if(StringUtils.isBlank(tf.getFileKey())){
						temFile.setInitMark(SopFile.INIT_FILE_MAEK_INIT);
						temFile.setFileKey(null);
					}else{
						temFile.setInitMark(SopFile.INIT_FILE_MAEK_HAS);
						temFile.setFileKey(tf.getFileKey());
					}
					*/
					break;
				}
			}
			
			result.add(temFile);
		}
		
		
		return result;
	}
	
	
	
	
	/**
	 * 根据  文档id、fileKey ，判断 insert update 
	 * @param   
	 * 
	 * file.getId() 、 file.FileWorktype 、 file.getFileKey()
	 * file.projectId、file.projectProgress
	 * 
	 * file.FileType 、 file.FileSource
	 * 
	 * file.fileUid   file.CareerLine  
	 *    
	 * @return  null ： 异常
	 */
	@Transactional
	public SopFile optFileAboutProgress(HttpServletRequest request,BaseControllerImpl<?,?> clazz, SopFile file, String tempfilePath){
		SopFile resultFile = null;
		
		SopFile oldFile = null;
		// 文档状态， init(有初始记录)：记录update；     has(有完整记录)：记录dead，insert； no（没有记录）：insert；
		String initMark = "no";
		if(file.getId() != null){
			if( file.getFileKey() != null){
				initMark = "has";
			}else{
				initMark = "init";
			}
			oldFile = sopFileDao.selectById(file.getId());
			//BeanUtils.copyProperties(resultFile, oldFile);
		} 
		
		
		String fileKey = String.valueOf(IdGenerator.generateId(OSSHelper.class));
		//	Map<String,Object> map = sopFileService.aLiColoudUpload(request, fileKey);
		UploadFileResult result = clazz.uploadFileToOSS(request, fileKey, tempfilePath);
		
		if(!Status.ERROR.equals(result.getResult().getStatus())){
			//String url = OSSHelper.getUrl(result.getBucketName(),result.getFileKey());  
			String url = OSSHelper.getUrl(OSSFactory.getDefaultBucketName(),result.getFileKey());
			
			if(initMark.equals("no")){
				//file.setBucketName(OSSFactory.getDefaultBucketName());
				file.setBucketName(result.getBucketName());
				file.setFileKey(result.getFileKey());  
				file.setFileLength(result.getContentLength());
				file.setFileName(result.getFileName());
				file.setFileSuffix(result.getFileSuffix());
				file.setFileValid(1);
				file.setRecordType(RecordType.PROJECT.getType());
				file.setFilUri(url);
				file.setFileSource(DictEnum.fileSource.内部.getCode());
				file.setFileType(DictEnum.fileType.图片.getCode());
				file.setFileStatus(DictEnum.fileStatus.已上传.getCode());
				
				file.setId(null);
				sopFileDao.insert(file);
				resultFile = file;
			}else if(initMark.equals("init")){
				oldFile.setBucketName(result.getBucketName());
				oldFile.setFileKey(result.getFileKey());  
				oldFile.setFileLength(result.getContentLength());
				oldFile.setFileName(result.getFileName());
				oldFile.setFileSuffix(result.getFileSuffix());
				oldFile.setFileValid(1);
				oldFile.setRecordType(RecordType.PROJECT.getType());
				oldFile.setFilUri(url);
				oldFile.setFileStatus(DictEnum.fileStatus.已上传.getCode());
				oldFile.setFileUid(file.getFileUid());
				oldFile.setFileSource(DictEnum.fileSource.内部.getCode());
				oldFile.setFileType(DictEnum.fileType.图片.getCode());
				sopFileDao.updateById(oldFile);
				resultFile = oldFile;
			}else{
				file.setBucketName(result.getBucketName());
				file.setFileKey(result.getFileKey());  
				file.setFileLength(result.getContentLength());
				file.setFileName(result.getFileName());
				file.setFileSuffix(result.getFileSuffix());
				file.setFileValid(1);
				file.setRecordType(RecordType.PROJECT.getType());
				file.setFilUri(url);
				file.setFileStatus(DictEnum.fileStatus.已上传.getCode());
				file.setFileSource(DictEnum.fileSource.内部.getCode());
				file.setFileType(DictEnum.fileType.图片.getCode());
				file.setId(null);
				sopFileDao.insert(file);
				oldFile.setFileUid(file.getFileUid());
				oldFile.setFileValid(0);
				oldFile.setFileSource(DictEnum.fileSource.内部.getCode());
				oldFile.setFileType(DictEnum.fileType.图片.getCode());
				sopFileDao.updateById(oldFile);
				
				resultFile = file;
			}
	    }else{
	    	return null;
	    }
		
		if(ProFlowUtilImpl.filetype_taskflag_about.get(file.getFileWorktype()) != null){
			SopTask task = new SopTask();
			task.setProjectId(file.getProjectId());
			task.setTaskType(DictEnum.taskType.协同办公.getCode());
			task.setTaskFlag(ProFlowUtilImpl.filetype_taskflag_about.get(file.getFileWorktype()));
			Date time=new Date();
			task.setTaskStatus(DictEnum.taskStatus.已完成.getCode());
			task.setTaskDeadline(time);
			sopTaskDao.updateTask(task);
		}
		
		resultFile = ProFlowUtilImpl.setNumForFile(initMark,resultFile);
		
		return resultFile;
	}
	
	
	
	
	
}