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
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.dao.soptask.SopTaskDao;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.operationMessage.handler.SopFileMessageHandler;

/**
 * 上传业务尽职调查报告
 * {
 * 		pid:必传
 * 		stage:必传
 * 		file:非必须
 * 		type:必传，档案来源
 * 		fileType:必传，存储类型
 * 		fileWorktype:必传，文档所属业务分类
 * 		content:非必传
 * }
 */
@Service("ywjzdcHandler")
public class YwjzdcHandler implements Handler {
	
	@Autowired
	private SopFileDao sopFileDao;
	@Autowired
	private SopTaskDao sopTaskDao;

	@Override
	@Transactional
	public SopResult handler(ViewQuery query, Project project) throws Exception {
		ProjectQuery q = (ProjectQuery) query;
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
		task.setTaskFlag(SopConstant.TASK_FLAG_YWJD);
		//修改
		Date time = new Date();
		task.setTaskStatus(DictEnum.taskStatus.已完成.getCode());
		task.setUpdatedTime(time.getTime());
		task.setTaskDeadline(time);
		sopTaskDao.updateTask(task);
		
		return new SopResult(Status.OK,null,"上传业务尽职调查报告成功!",UrlNumber.seven,SopFileMessageHandler._5_4_,f);
	}
	
}
