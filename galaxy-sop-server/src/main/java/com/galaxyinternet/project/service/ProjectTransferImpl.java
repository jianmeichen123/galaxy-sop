package com.galaxyinternet.project.service;

import java.math.BigDecimal;

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
		mr.setCreateUid(createId);
 		meetingRecordDao.updateByIdSelective(mr);
		
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
		
		//更新主承做人
		ild = new InformationListdata();
		ild.setTitleId(1103L);
		ild.setProjectId(projectId);
		ild.setField1(projectTransfer.getBeforeUid()+"");
		ild.setField5("0");
		
		ild = informationListdataDao.selectOne(ild);
		
		
		if(ild != null)
		{
			InformationListdata entity = new InformationListdata();
			entity.setId(ild.getId());
			entity.setField1(createId+"");
			
			//如果接收用户为副承做人 - 删除原承做人并将用户升级为主承做人，并且添加比例 
			InformationListdata currQuery = new InformationListdata();
			currQuery.setProjectId(projectId);
			currQuery.setTitleId(1103L);
			currQuery.setProjectId(projectId);
			currQuery.setField1(createId+"");
			currQuery.setField5("1");
			InformationListdata curr = informationListdataDao.selectOne(currQuery);
			if(curr != null)
			{
				BigDecimal pect = BigDecimal.ZERO;
				try
				{
					pect = new BigDecimal(ild.getField2());
					pect = new BigDecimal(curr.getField2()).add(pect);
				} catch (Exception e)
				{
				}
				entity.setField2(pect.doubleValue()+"");
				informationListdataDao.deleteById(curr.getId());
			}
			informationListdataDao.updateById(entity);
		}
		
	}
}