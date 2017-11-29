package com.galaxyinternet.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.dao.hologram.InformationFileDao;
import com.galaxyinternet.dao.hologram.InformationListdataDao;
import com.galaxyinternet.dao.project.InterviewRecordDao;
import com.galaxyinternet.dao.project.MeetingRecordDao;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.project.ProjectTransferDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.dao.sopfile.SopVoucherFileDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.hologram.InformationFile;
import com.galaxyinternet.model.hologram.InformationListdata;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.project.ProjectTransfer;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.sopfile.SopVoucherFile;
import com.galaxyinternet.service.ProjectTransferService;


@Service("com.galaxyinternet.service.ProjectTransferService")
public class ProjectTransferImpl extends BaseServiceImpl<ProjectTransfer> implements ProjectTransferService {
	
	@Autowired
	private ProjectTransferDao projectTransferDao;
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private MeetingRecordDao meetingRecordDao;
	@Autowired
	private SopFileDao sopFileDao;
	@Autowired
	private SopVoucherFileDao sopVoucherFileDao;
	@Autowired
	private InterviewRecordDao interviewRecordDao;
	@Autowired
	private InformationListdataDao informationListdataDao;
	@Autowired
	private InformationFileDao informationFileDao;
	
	
	@Override
	protected BaseDao<ProjectTransfer, Long> getBaseDao() {
		return this.projectTransferDao;
	}
	
	
	
	
	
	@Override
	@Transactional
	public void receiveProjectTransfer(ProjectTransfer projectTransfer, Long createId, String createName, Long departmentId) {
		Long projectId = projectTransfer.getProjectId();
		Project po = new Project();
		po.setId(projectId);
		po.setCreateUid(createId);
		po.setCreateUname(createName);
		po.setProjectDepartid(departmentId);
		projectDao.updateById(po);
		
		MeetingRecord mr = new MeetingRecord();
		mr.setProjectId(projectId);
		List<MeetingRecord> ms = meetingRecordDao.selectList(mr);
		if(ms != null){
			for(MeetingRecord m : ms){
				m.setCreateUid(createId);
				meetingRecordDao.updateById(m);
			}
		}
		
		SopFile file = new SopFile();
		file.setProjectId(projectId);
		file.setCareerLine(departmentId);
		sopFileDao.updateDepartmentId(file);
		
		SopVoucherFile f = new SopVoucherFile();
		f.setProjectId(projectId);
		f.setCareerLine(departmentId);
		sopVoucherFileDao.updateDepartmentId(f);
		
		InterviewRecord ir = new InterviewRecord();
		ir.setProjectId(projectId);
		ir.setCreatedId(createId);
		interviewRecordDao.updateCreateUid(ir);
		
		/**交割前事项**/
		InformationListdata ild = new InformationListdata();
		ild.setProjectId(projectId);
		ild.setCreateId(createId);
		//ild.setUpdateId(createId);
		ild.setTitleId(1810l);
		informationListdataDao.updateCreateUid(ild);
		
		/**交割事件文件更新***/
		InformationFile iF = new InformationFile();
		iF.setProjectId(projectId);
		iF.setCreateId(createId);
		//iF.setUpdateId(createId);
		iF.setTitleId(1810l);
		informationFileDao.updateCreateUid(iF);
	}
}