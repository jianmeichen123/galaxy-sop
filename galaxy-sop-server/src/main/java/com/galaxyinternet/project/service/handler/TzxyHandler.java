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
 * 上传投资协议/投资协议的签署证明、股权转让协议/股权转让协议的签署证明
 * {
 * 		pid:必传
 * 		stage:必传
 * 		file:非必须
 * 		type:必传，档案来源
 * 		fileType:必传，存储类型
 * 		fileWorktype:必传，文档所属业务分类
 * 		voucherType:若为签署证明，则必传
 * 		hasStockTransfer:当项目是投资，若涉及股权转让协议则必传
 * 		content:非必传
 * }
 */
@Service("tzxyHandler")
public class TzxyHandler implements Handler {
	
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
		SopResult r = null;
		if(q.getVoucherType() != null && q.getVoucherType().intValue() == 1){
			//保存签署证明记录数据
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
			
			SopFile file = new SopFile();
			file.setProjectId(q.getPid());
			file.setProjectProgress(q.getStage());
			file.setFileWorktype(q.getFileWorktype());
			SopFile f = sopFileDao.selectOne(file);
			f.setFileStatus(DictEnum.fileStatus.已签署.getCode());
			sopFileDao.updateById(f);
			
			*//**
			 * 如果创建，只有投资协议
			 * 如果是投资，投资协议必须，股权转让非必须[根据其在上传投资协议签署证明时是否勾选"涉及股权转让"来判断]
			 * 
			 * 前一个判断是可以将项目状态切换为股权交割
			 * 后一个判断是需要上传股权转让协议
			 *//*
			String messageType = null;
			if(project.getProjectType().equals(DictEnum.projectType.创建.getCode())
					|| (project.getProjectType().equals(DictEnum.projectType.投资.getCode())
							&& q.getFileWorktype().equals(DictEnum.fileWorktype.投资协议.getCode())
							&& (q.getHasStockTransfer() == null || q.getHasStockTransfer().intValue() != 1)
							|| project.getProjectType().equals(DictEnum.projectType.投资.getCode())
							&& q.getFileWorktype().equals(DictEnum.fileWorktype.股权转让协议.getCode()) && q.getHasStockTransfer() != null && q.getHasStockTransfer().intValue() == 1)){
				//修改项目阶段
				project.setProjectProgress(DictEnum.projectProgress.股权交割.getCode());
				project.setUpdatedTime((new Date()).getTime());
				projectDao.updateById(project);
				messageType = StageChangeHandler._6_9_;
				//生成待办任务:上传资金拨付凭证和上传工商变更登记凭证
				SopTask task = new SopTask();
				task.setProjectId(q.getPid());
				task.setTaskName(SopConstant.TASK_NAME_ZJBF);
				task.setTaskType(DictEnum.taskType.协同办公.getCode());
				task.setTaskFlag(SopConstant.TASK_FLAG_ZJBF);
				task.setTaskOrder(SopConstant.NORMAL_STATUS);
				task.setDepartmentId(SopConstant.DEPARTMENT_CW_ID);
				task.setTaskStatus(DictEnum.taskStatus.待认领.getCode());
				task.setCreatedTime(System.currentTimeMillis());
				sopTaskDao.insert(task);
				task.setId(null);
				task.setProjectId(q.getPid());
				task.setTaskName(SopConstant.TASK_NAME_GSBG);
				task.setTaskType(DictEnum.taskType.协同办公.getCode());
				task.setTaskFlag(SopConstant.TASK_FLAG_GSBG);
				task.setTaskOrder(SopConstant.NORMAL_STATUS);
				task.setDepartmentId(SopConstant.DEPARTMENT_FW_ID);
				task.setTaskStatus(DictEnum.taskStatus.待认领.getCode());
				task.setCreatedTime(System.currentTimeMillis());
				sopTaskDao.insert(task);
			}else{
				project.setStockTransfer(q.getHasStockTransfer());
				projectDao.updateById(project);
				SopTask task = new SopTask();
				task.setProjectId(q.getPid());
				task.setTaskName(SopConstant.TASK_NAME_GQZR);
				task.setTaskType(DictEnum.taskType.协同办公.getCode());
				task.setTaskFlag(SopConstant.TASK_FLAG_GQZR);
				task.setTaskOrder(SopConstant.NORMAL_STATUS);
				task.setDepartmentId(q.getDepartmentId());
				task.setAssignUid(q.getCreatedUid());
				task.setTaskStatus(DictEnum.taskStatus.待完工.getCode());
				task.setCreatedTime(System.currentTimeMillis());
				sopTaskDao.insert(task);
			}
			
			
			SopTask task = new SopTask();
			//条件
			task.setProjectId(q.getPid());
			task.setTaskType(DictEnum.taskType.协同办公.getCode());
			if(q.getFileWorktype().equals(DictEnum.fileWorktype.投资协议.getCode())){
				task.setTaskFlag(SopConstant.TASK_FLAG_TZXY);
			}else{
				task.setTaskFlag(SopConstant.TASK_FLAG_GQZR);
			}
			//修改
			Date time=new Date();
			task.setTaskStatus(DictEnum.taskStatus.已完成.getCode());
			task.setUpdatedTime(time.getTime());
			task.setTaskDeadline(time);
			sopTaskDao.updateTask(task);
			
			fv.setfWorktype(fv.getfWorktype() + "签署凭证");
			if(q.getFileWorktype().equals(DictEnum.fileWorktype.股权转让协议.getCode())){
				r = new SopResult(Status.OK,null,"上传股权转让协议签署证明成功!",UrlNumber.eleven,messageType == null ? SopFileMessageHandler._5_13_ : messageType, fv);
			}else{
				r = new SopResult(Status.OK,null,"上传投资协议签署证明成功!",UrlNumber.twelve,messageType == null ? SopFileMessageHandler._5_9_ : messageType, fv);
			}
		}else{
			if(q.getHasStockTransfer() != null && q.getHasStockTransfer().intValue() == 1){
				project.setStockTransfer(q.getHasStockTransfer().intValue());
				projectDao.updateById(project);
			}
			//非签署证明
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
			
			if(q.getFileWorktype().equals(DictEnum.fileWorktype.投资协议.getCode())){
				r = new SopResult(Status.OK,null,"上传投资协议成功!",UrlNumber.nine,SopFileMessageHandler._5_8_,f);
			}else{
				r = new SopResult(Status.OK,null,"上传股权转让协议成功!",UrlNumber.ten,SopFileMessageHandler._5_12_,f);
			}
		}
		return r;
	}*/
	
	@Override
	@Transactional
	public SopResult handler(ViewQuery query, Project project) throws Exception {
		ProjectQuery q = (ProjectQuery) query;
		SopResult r = null;
		//非签署证明
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
		if(q.getFileWorktype().equals(DictEnum.fileWorktype.投资协议.getCode())){
			task.setTaskFlag(SopConstant.TASK_FLAG_TZXY);
		}else{
			task.setTaskFlag(SopConstant.TASK_FLAG_GQZR);
		}
		//修改
		Date time=new Date();
		task.setTaskStatus(DictEnum.taskStatus.已完成.getCode());
		task.setUpdatedTime(time.getTime());
		task.setTaskDeadline(time);
		sopTaskDao.updateTask(task);
		
		if(q.getHasStockTransfer() != null && q.getHasStockTransfer().intValue() == 1){
			project.setStockTransfer(q.getHasStockTransfer().intValue());
			projectDao.updateById(project);
		}

		if(q.getFileWorktype().equals(DictEnum.fileWorktype.投资协议.getCode())){
			r = new SopResult(Status.OK,null,"上传投资协议成功!",UrlNumber.nine,SopFileMessageHandler._5_8_,f);
		}else{
			r = new SopResult(Status.OK,null,"上传股权转让协议成功!",UrlNumber.ten,SopFileMessageHandler._5_12_,f);
		}
		return r;
	}
	
}
