package com.galaxyinternet.project.service.handler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.common.SopResult;
import com.galaxyinternet.common.ViewQuery;
import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.query.ProjectQuery;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.dao.sopfile.SopVoucherFileDao;
import com.galaxyinternet.dao.soptask.SopTaskDao;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.sopfile.SopVoucherFile;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.operationMessage.handler.SopFileMessageHandler;
import com.galaxyinternet.operationMessage.handler.StageChangeHandler;

/**
 * 上传投资意向书/投资意向书的签署证明
 * {
 * 		pid:必传
 * 		stage:必传
 * 		file:非必须
 * 		type:必传，档案来源
 * 		fileType:必传，存储类型
 * 		fileWorktype:必传，文档所属业务分类
 * 		voucherType:若为签署证明，则必传
 * 		content:非必传
 * }
 */
@Service("tzyxsHandler")
public class TzyxsHandler implements Handler {
	
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private SopFileDao sopFileDao;
	@Autowired
	private SopTaskDao sopTaskDao;
	@Autowired
	private SopVoucherFileDao sopVoucherFileDao;

	/*@Override
	@Transactional
	public SopResult handler(ViewQuery query, Project project) throws Exception {
		ProjectQuery q = (ProjectQuery) query;
		String messageType = null;
		if(q.getVoucherType() != null && q.getVoucherType().intValue() == 1){
			//保存签署证明记录信息
			SopVoucherFile qvf = new SopVoucherFile();
			qvf.setProjectId(q.getPid());
			qvf.setProjectProgress(q.getStage());
			qvf.setFileWorktype(q.getFileWorktype());
			SopVoucherFile fv = sopVoucherFileDao.selectOne(qvf);
			fv.setFileSource(String.valueOf(q.getType()));
			fv.setFileType(q.getFileType());
			fv.setRemark(q.getContent());
			fv.setFileStatus(DictEnum.fileStatus.已签署.getCode());
			fv.setFileUid(q.getCreatedUid());
			fv.setUpdatedTime((new Date()).getTime());
			fv.setFileLength(q.getFileSize());
			fv.setFileKey(q.getFileKey());
			fv.setBucketName(q.getBucketName());
			fv.setFileName(q.getFileName());
			fv.setFileSuffix(q.getSuffix());
			sopVoucherFileDao.updateById(fv);
			//将file表记录状态修改为"已签署"
			SopFile file = new SopFile();
			file.setProjectId(q.getPid());
			file.setProjectProgress(q.getStage());
			file.setFileWorktype(q.getFileWorktype());
			SopFile f = sopFileDao.selectOne(file);
			f.setFileStatus(DictEnum.fileStatus.已签署.getCode());
			sopFileDao.updateById(f);
			//修改项目阶段
			project.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
			project.setUpdatedTime((new Date()).getTime());
			projectDao.updateById(project);
			messageType = StageChangeHandler._6_6_;
			
			SopTask task = new SopTask();
			//条件
			task.setProjectId(q.getPid());
			task.setTaskType(DictEnum.taskType.协同办公.getCode());
			task.setTaskFlag(SopConstant.TASK_FLAG_SCTZYXS);
			//修改
			Date time=new Date();
			task.setTaskStatus(DictEnum.taskStatus.已完成.getCode());
			task.setUpdatedTime(time.getTime());
			task.setTaskDeadline(time);
			sopTaskDao.updateTask(task);
			task.setTaskDeadline(null);
			
			//待办任务
			task.setTaskOrder(SopConstant.NORMAL_STATUS);
			task.setTaskStatus(DictEnum.taskStatus.待认领.getCode());
			task.setCreatedTime(System.currentTimeMillis());
			//业务尽调
			task.setTaskName(SopConstant.TASK_NAME_YWJD);
			task.setTaskFlag(SopConstant.TASK_FLAG_YWJD);
			task.setAssignUid(q.getCreatedUid());
			task.setTaskStatus(DictEnum.taskStatus.待完工.getCode());
			task.setDepartmentId(q.getDepartmentId());
			sopTaskDao.insert(task);
			
			//人事尽调
			task.setAssignUid(null);
			task.setId(null);
			task.setTaskName(SopConstant.TASK_NAME_RSJD);
			task.setTaskFlag(SopConstant.TASK_FLAG_RSJD);
			task.setTaskStatus(DictEnum.taskStatus.待认领.getCode());
			task.setDepartmentId(SopConstant.DEPARTMENT_RS_ID);
			sopTaskDao.insert(task);
			
			//投资需要额外的财务和法务尽调
			if(project.getProjectType() == null || project.getProjectType().equals(DictEnum.projectType.投资.getCode())){
				task.setId(null);
				task.setTaskName(SopConstant.TASK_NAME_FWJD);
				task.setTaskFlag(SopConstant.TASK_FLAG_FWJD);
				task.setTaskStatus(DictEnum.taskStatus.待认领.getCode());
				task.setDepartmentId(SopConstant.DEPARTMENT_FW_ID);
				sopTaskDao.insert(task);
				task.setId(null);
				task.setTaskName(SopConstant.TASK_NAME_CWJD);
				task.setTaskFlag(SopConstant.TASK_FLAG_CWJD);
				task.setTaskStatus(DictEnum.taskStatus.待认领.getCode());
				task.setDepartmentId(SopConstant.DEPARTMENT_CW_ID);
				sopTaskDao.insert(task);
			}
			return new SopResult(Status.OK,null,"上传投资意向书签署证明成功!",UrlNumber.six,messageType,fv);
		}else{
			//投资意向书
			SopFile qf = new SopFile();
			qf.setProjectId(q.getPid());
			qf.setProjectProgress(q.getStage());
			qf.setFileWorktype(q.getFileWorktype());
			SopFile f = sopFileDao.selectOne(qf);
			f.setFileSource(String.valueOf(q.getType()));
			f.setFileType(q.getFileType());
			f.setRemark(q.getContent());
			f.setFileStatus(DictEnum.fileStatus.已上传.getCode());
			f.setFileUid(q.getCreatedUid());
			f.setUpdatedTime((new Date()).getTime());
			f.setFileLength(q.getFileSize());
			f.setFileKey(q.getFileKey());
			f.setBucketName(q.getBucketName());
			f.setFileName(q.getFileName());
			f.setFileSuffix(q.getSuffix());
			sopFileDao.updateById(f);
			return new SopResult(Status.OK,null,"上传投资意向书成功!",UrlNumber.five,SopFileMessageHandler._5_2_,f);
		}
	}
	*/
	
	@Override
	@Transactional
	public SopResult handler(ViewQuery query, Project project) throws Exception {
		ProjectQuery q = (ProjectQuery) query;
		//投资意向书
		SopFile qf = new SopFile();
		qf.setProjectId(q.getPid());
		qf.setProjectProgress(q.getStage());
		qf.setFileWorktype(q.getFileWorktype());
		SopFile f = sopFileDao.selectOne(qf);
		f.setFileSource(String.valueOf(q.getType()));
		f.setFileType(q.getFileType());
		f.setRemark(q.getContent());
		f.setFileStatus(DictEnum.fileStatus.已上传.getCode());
		f.setFileUid(q.getCreatedUid());
		f.setUpdatedTime((new Date()).getTime());
		f.setFileLength(q.getFileSize());
		f.setFileKey(q.getFileKey());
		f.setBucketName(q.getBucketName());
		f.setFileName(q.getFileName());
		f.setFileSuffix(q.getSuffix());
		sopFileDao.updateById(f);
			
		SopTask task = new SopTask();
		//条件
		task.setProjectId(q.getPid());
		task.setTaskType(DictEnum.taskType.协同办公.getCode());
		task.setTaskFlag(SopConstant.TASK_FLAG_SCTZYXS);
		//修改
		Date time=new Date();
		task.setTaskStatus(DictEnum.taskStatus.已完成.getCode());
		task.setUpdatedTime(time.getTime());
		task.setTaskDeadline(time);
		sopTaskDao.updateTask(task);
			
		return new SopResult(Status.OK,null,"上传投资意向书成功!",UrlNumber.five,SopFileMessageHandler._5_2_,f);
	}
	
}
