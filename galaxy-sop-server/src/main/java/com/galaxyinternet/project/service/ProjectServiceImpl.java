package com.galaxyinternet.project.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.dao.project.MeetingSchedulingDao;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.dao.sopfile.SopVoucherFileDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.sopfile.SopVoucherFile;
import com.galaxyinternet.service.ProjectService;


@Service("com.galaxyinternet.service.ProjectService")
public class ProjectServiceImpl extends BaseServiceImpl<Project> implements ProjectService {

	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private SopFileDao sopFileDao;
	@Autowired
	private SopVoucherFileDao sopVoucherFileDao;
	@Autowired
	private MeetingSchedulingDao meetingSchedulingDao;
	
	@Override
	protected BaseDao<Project, Long> getBaseDao() {
		return this.projectDao;
	}

	@Override
	@Transactional
	public long newProject(Project project) throws Exception {
		long id = projectDao.insert(project);
		//通用属性
		SopFile f = new SopFile();
		f.setProjectId(id);
		f.setCareerLine(project.getProjectDepartid());
		f.setFileStatus(DictEnum.fileStatus.缺失.getCode());
		f.setCreatedTime((new Date()).getTime());
		SopVoucherFile svf = new SopVoucherFile();
		svf.setProjectId(id);
		svf.setCareerLine(project.getProjectDepartid());
		svf.setFileStatus(DictEnum.fileStatus.缺失.getCode());
		svf.setCreatedTime((new Date()).getTime());
		//投资意向书，先提交投资意向书-签署-上传签署证明
		svf.setProjectProgress(DictEnum.projectProgress.投资意向书.getCode());
		svf.setFileWorktype(DictEnum.fileWorktype.投资意向书.getCode());
		Long fid = sopVoucherFileDao.insert(svf);
		svf.setId(null);
		f.setProjectProgress(DictEnum.projectProgress.投资意向书.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.投资意向书.getCode());
		f.setVoucherId(fid);
		sopFileDao.insert(f);
		f.setId(null);
		//尽调阶段
		f.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.业务尽职调查报告.getCode());
		sopFileDao.insert(f);
		f.setId(null);
		f.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.人力资源尽职调查报告.getCode());
		sopFileDao.insert(f);
		f.setId(null);
		//投资协议文档，投资协议文档+签署证明
		svf.setProjectProgress(DictEnum.projectProgress.投资协议.getCode());
		svf.setFileWorktype(DictEnum.fileWorktype.投资协议.getCode());
		fid = sopVoucherFileDao.insert(svf);
		svf.setId(null);
		f.setProjectProgress(DictEnum.projectProgress.投资协议.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.投资协议.getCode());
		f.setVoucherId(fid);
		sopFileDao.insert(f);
		f.setId(null);
		//股权交割
		f.setProjectProgress(DictEnum.projectProgress.股权交割.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.工商转让凭证.getCode());
		sopFileDao.insert(f);
		f.setId(null);
		f.setProjectProgress(DictEnum.projectProgress.股权交割.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.资金拨付凭证.getCode());
		sopFileDao.insert(f);
		f.setId(null);
		
		//投后运营
		f.setProjectProgress(DictEnum.projectProgress.投后运营.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.公司资料.getCode());
		sopFileDao.insert(f);
		f.setId(null);
		f.setProjectProgress(DictEnum.projectProgress.投后运营.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.财务预测报告.getCode());
		sopFileDao.insert(f);
		f.setId(null);
		f.setProjectProgress(DictEnum.projectProgress.投后运营.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.商业计划.getCode());
		sopFileDao.insert(f);
		f.setId(null);
		
		
		if(project.getProjectType() != null && 
				DictEnum.projectType.外部项目.getCode().equals(project.getProjectType())){
			//外部投资项目必须四个尽调、内部创建必须两个尽调
			f.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
			f.setFileWorktype(DictEnum.fileWorktype.法务尽职调查报告.getCode());
			sopFileDao.insert(f);
			f.setId(null);
			f.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
			f.setFileWorktype(DictEnum.fileWorktype.财务尽职调查报告.getCode());
			sopFileDao.insert(f);
			f.setId(null);
			//投资转让协议文档，投资转让协议文档+签署证明
			svf.setProjectProgress(DictEnum.projectProgress.投资协议.getCode());
			svf.setFileWorktype(DictEnum.fileWorktype.股权转让协议.getCode());
			fid = sopVoucherFileDao.insert(svf);
			svf.setId(null);
			f.setProjectProgress(DictEnum.projectProgress.投资协议.getCode());
			f.setFileWorktype(DictEnum.fileWorktype.股权转让协议.getCode());
			f.setVoucherId(fid);
			sopFileDao.insert(f);
			f.setId(null);
		}
		return id;
	}

	@Override
	@Transactional
	public void toEstablishStage(Project project) throws Exception {
		project.setProjectProgress(DictEnum.projectProgress.立项会.getCode());
		projectDao.updateById(project);
		MeetingScheduling ms = new MeetingScheduling();
		ms.setProjectId(project.getId());
		ms.setMeetingType(DictEnum.meetingType.立项会.getCode());
		ms.setMeetingCount(0);
		ms.setStatus(DictEnum.meetingResult.待定.getCode());
		ms.setCreatedTime((new Date()).getTime());
		meetingSchedulingDao.insert(ms);
		
		//修改立项会排期记录为完成
		MeetingScheduling m = new MeetingScheduling();
		m.setProjectId(project.getId());
		m.setMeetingType(DictEnum.meetingType.CEO评审.getCode());
		m.setStatus(DictEnum.meetingResult.通过.getCode());
		m.setUpdatedTime((new Date()).getTime());
		meetingSchedulingDao.updateBySelective(m);
	}

	@Override
	public void toSureMeetingStage(Project project) throws Exception {
		project.setProjectProgress(DictEnum.projectProgress.投资决策会.getCode());
		projectDao.updateById(project);
		MeetingScheduling ms = new MeetingScheduling();
		ms.setProjectId(project.getId());
		ms.setMeetingType(DictEnum.meetingType.投决会.getCode());
		ms.setMeetingCount(0);
		ms.setStatus(DictEnum.meetingResult.待定.getCode());
		ms.setCreatedTime((new Date()).getTime());
		meetingSchedulingDao.insert(ms);
	}


}