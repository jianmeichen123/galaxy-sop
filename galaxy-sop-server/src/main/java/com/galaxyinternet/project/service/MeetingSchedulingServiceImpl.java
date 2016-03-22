package com.galaxyinternet.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.galaxyinternet.bo.project.MeetingSchedulingBo;
import com.galaxyinternet.dao.project.MeetingSchedulingDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.MeetingSchedulingService;
import com.galaxyinternet.service.ProjectService;


@Service("com.galaxyinternet.service.MeetingSchedulingService")
public class MeetingSchedulingServiceImpl extends BaseServiceImpl<MeetingScheduling> implements MeetingSchedulingService {

	@Autowired
	private MeetingSchedulingDao meetingSchedulingDao;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private DepartmentService deptService;
	@Override
	protected BaseDao<MeetingScheduling, Long> getBaseDao() {
		return this.meetingSchedulingDao;
	}

	@Override
	public int updateCountBySelective(MeetingScheduling entity) {
		return meetingSchedulingDao.updateCountBySelective(entity);
	}

	@Override
	public List<MeetingSchedulingBo> selectTop5ProjectMeetingByType(
			String type) {
		 List<MeetingSchedulingBo> meetingList = meetingSchedulingDao.selectTop5ProjectMeetingByType(type);
		 List<Project> projectList = projectService.queryAll();
		 List<Department> depList = deptService.queryAll();
		
		 for (MeetingSchedulingBo meeting : meetingList) {
			 for (Project project :projectList)   {
				 if ((meeting.getProjectId()!=null) && (meeting.getProjectId().longValue() == project.getId().longValue())) {
					 meeting.setProjectName(project.getProjectName());
					 String deptName = findDeptName(meeting.getProjectId(),depList);
					 meeting.setProjectCareerline(deptName);
					 meeting.setCreateUname(project.getCreateUname());
				 }
			 }
			 setDefaultValue(meeting);
		 }
		 
		 return meetingList;
	}

	@Override
	public List<MeetingSchedulingBo> selectProjectMeetingByType(String type) {
		 List<MeetingSchedulingBo> meetingList = meetingSchedulingDao.selectProjectMeetingByType(type);
		 List<Project> projectList = projectService.queryAll();
		 List<Department> depList = deptService.queryAll();
		 for (MeetingSchedulingBo meeting : meetingList) {
			 for (Project project :projectList)   {
				 if ((meeting.getProjectId()!=null) && (meeting.getProjectId().longValue() == project.getId().longValue())) {
					 meeting.setProjectName(project.getProjectName());
					 String deptName = findDeptName(meeting.getProjectId(),depList);
					 meeting.setProjectCareerline(deptName);
					 meeting.setCreateUname(project.getCreateUname());
				 }
			 }
			 
			 setDefaultValue(meeting);
		 }
		 
		 return meetingList;
	}

	private void setDefaultValue(MeetingScheduling meeting) {
		if (meeting.getMeetingCount()==null) {
			 meeting.setMeetingCount(0);
		 }
		 if (meeting.getProjectName() == null) {
			 meeting.setProjectName("");
		 }
		 
		 if (meeting.getProjectCareerline()==null) {
			 meeting.setProjectCareerline("");
		 }
		 
		 if (meeting.getRemark() == null) {
			 meeting.setRemark("");
		 }
		 
		 if (meeting.getCreateUname() == null) {
			 meeting.setCreateUname("");
		 }
	}

	@Override
	public Page<MeetingScheduling> queryMeetingPageList(MeetingScheduling query, Pageable pageable) {
		
		 List<Project> projectList = projectService.queryAll();
		 List<Department> depList = deptService.queryAll();
		 Page<MeetingScheduling> page = meetingSchedulingDao.selectPageList(query, pageable);
		 List<MeetingScheduling> content = page.getContent();
		 for (MeetingScheduling meeting : content) {
			 for (Project project :projectList)   {
				 if ((meeting.getProjectId()!=null) && (meeting.getProjectId().longValue() == project.getId().longValue())) {
					 meeting.setProjectName(project.getProjectName());
					 String deptName = findDeptName(meeting.getProjectId(),depList);
					 meeting.setProjectCareerline(deptName);
					 meeting.setCreateUname(project.getCreateUname());
				 }
			 }
			 setDefaultValue(meeting);
		 }
	    page.setContent(content);
		return page;
	}

	@Override
	public int updateBySelective(MeetingScheduling ms) {
		return meetingSchedulingDao.updateBySelective(ms);
	}

	private String findDeptName(Long deptId,List<Department> depList) {
		 String deptName = "未知";
		 for (Department dept:depList) {
			 if (dept.getId().equals(deptId)) {
				 deptName = dept.getName();
			 }
		 }
		 
		 return deptName;
	}
}