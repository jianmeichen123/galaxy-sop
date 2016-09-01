package com.galaxyinternet.project.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.galaxyinternet.bo.project.MeetingSchedulingBo;
import com.galaxyinternet.dao.project.MeetingSchedulingDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.MeetingSchedulingService;
import com.galaxyinternet.service.ProjectService;

@Service("com.galaxyinternet.service.MeetingSchedulingService")
public class MeetingSchedulingServiceImpl
		extends
			BaseServiceImpl<MeetingScheduling>
		implements
			MeetingSchedulingService {

	@Autowired
	private MeetingSchedulingDao meetingSchedulingDao;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private DepartmentService deptService;
	
	@Autowired	
/*	private MeetingRecordService meetingRecordService;*/
	
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
		List<MeetingSchedulingBo> meetingList = meetingSchedulingDao
				.selectTop5ProjectMeetingByType(type);
		List<Long> projectIdList = new ArrayList<Long>();
		for (MeetingSchedulingBo meeting : meetingList) {
			if (meeting.getProjectId() != null) {
				
				projectIdList.add(meeting.getProjectId());
			}

		}
		List<Project> projectList = null;

		if (projectIdList.size() > 0) {
			projectIdList = removeDuplicateWithOrder(projectIdList);
			projectList = projectService.queryListById(projectIdList);
		}

		List<Department> depList = deptService.queryAll();

		for (MeetingSchedulingBo meeting : meetingList) {
			for (Project project : projectList) {
				if ((meeting.getProjectId() != null) && (meeting.getProjectId()
						.longValue() == project.getId().longValue())) {
					meeting.setProjectName(project.getProjectName());
					String deptName = findDeptName(project.getProjectDepartid(),
							depList);
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
		List<MeetingSchedulingBo> meetingList = meetingSchedulingDao
				.selectProjectMeetingByType(type);
		List<Long> projectIdList = new ArrayList<Long>();
		for (MeetingSchedulingBo meeting : meetingList) {
			if (meeting.getProjectId() != null) {
				projectIdList.add(meeting.getProjectId());
			}

		}
		List<Project> projectList = null;

		if (projectIdList.size() > 0) {
			projectIdList = removeDuplicateWithOrder(projectIdList);
			projectList = projectService.queryListById(projectIdList);
		}
		List<Department> depList = deptService.queryAll();
		for (MeetingSchedulingBo meeting : meetingList) {
			for (Project project : projectList) {
				if ((meeting.getProjectId() != null) && (meeting.getProjectId()
						.longValue() == project.getId().longValue())) {
					meeting.setProjectName(project.getProjectName());
					String deptName = findDeptName(project.getProjectDepartid(),
							depList);
					meeting.setProjectCareerline(deptName);
					meeting.setCreateUname(project.getCreateUname());
				}
			}

			setDefaultValue(meeting);
		}

		return meetingList;
	}

	private void setDefaultValue(MeetingScheduling meeting) {
		if (meeting.getMeetingCount() == null) {
			meeting.setMeetingCount(0);
		}
		if (meeting.getProjectName() == null) {
			meeting.setProjectName("");
		}

		if (meeting.getProjectCareerline() == null) {
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
	public Page<MeetingScheduling> queryMeetingPageList(MeetingScheduling query,
			Pageable pageable) {
		
		List<Department> depList = deptService.queryAll();
		Page<MeetingScheduling> page = null;
		List<MeetingScheduling> content = new ArrayList<MeetingScheduling>();
		List<Project> projectList = null;
		//增加获取用户所在部门排期表
		
		if(query != null && query.getCareline()!=null) {
			Project entity = new Project();
			entity.setProjectDepartid(query.getCareline().longValue());
			projectList = projectService.queryList(entity);
			
			if (projectList.size() == 0) {
				Page<MeetingScheduling> page1 = new Page<MeetingScheduling>(
						null, pageable, (long) 0);
				return page1;
			}
		} else {
			List<Long> projectIdList = new ArrayList<Long>();
			page = meetingSchedulingDao.selectPageList(query, pageable);
			content = page.getContent();
			for (MeetingScheduling meeting : content) {
				if (meeting.getProjectId() != null) {
					projectIdList.add(meeting.getProjectId());
				}

			}

			if (projectIdList.size() > 0) {
				projectIdList = removeDuplicateWithOrder(projectIdList);
				projectList = projectService.queryListById(projectIdList);
				
			}
		}
		
		if (query != null &&query.getFilterName()!=null && query.getFilterName().equals("deptId")) {
			Project project = new Project();
			project.setDeptIdList(query.getDeptIdList());
			projectList = projectService.queryList(project);
			List<Long> projectIdList = new ArrayList<Long>();
			if (projectList.size() == 0) {
				Page<MeetingScheduling> page1 = new Page<MeetingScheduling>(
						null, pageable, (long) 0);
				return page1;
			}
			for (Project temp : projectList) {
				projectIdList.add(temp.getId());
			}
			query.setProjectIdList(projectIdList);
			page = meetingSchedulingDao.selectPageList(query, pageable);
			content = page.getContent();
		}

		for (MeetingScheduling meeting : content) {
			for (Project project : projectList) {
				if ((meeting.getProjectId() != null) && (meeting.getProjectId()
						.longValue() == project.getId().longValue())) {
					meeting.setProjectName(project.getProjectName());
					String deptName = findDeptName(project.getProjectDepartid(),
							depList);
					meeting.setProjectCareerline(deptName);
					meeting.setCreateUname(project.getCreateUname());

					if (meeting.getMeetingDate() != null) {
						String meetingDateStr = DateUtil
								.convertDateToString(meeting.getMeetingDate());
						meeting.setMeetingDateStr(meetingDateStr);
					}
				}
			}
			setDefaultValue(meeting);
		}
		if (page != null) {
			page.setContent(content);
		}
		
		return page;
	}

	//秘书用排期池   
	@Override
	public Page<MeetingScheduling> queryMeetPageList(MeetingSchedulingBo query,
			Pageable pageable) {
		List<Project> projectList = null;
		List<Department> depList = deptService.queryAll();
		Page<MeetingScheduling> page = null;
		List<MeetingScheduling> content = new ArrayList<MeetingScheduling>();
		if (query.getFilterName() == null) {
			List<Long> projectIdList = new ArrayList<Long>();
			page = meetingSchedulingDao.selectPageList(query, pageable);
			content = page.getContent();
			for (MeetingScheduling meeting : content) {
				if (meeting.getProjectId() != null) {
					projectIdList.add(meeting.getProjectId());
				}
			}

			if (projectIdList.size() > 0) {
				projectIdList = removeDuplicateWithOrder(projectIdList);
				projectList = projectService.queryListById(projectIdList);
			}

		} else if (query.getFilterName().equals("deptId")) {
			Project project = new Project();
			project.setDeptIdList(query.getDeptIdList());
			projectList = projectService.queryList(project);
			List<Long> projectIdList = new ArrayList<Long>();
			if (projectList.size() == 0) {
				Page<MeetingScheduling> page1 = new Page<MeetingScheduling>(
						null, pageable, (long) 0);
				return page1;
			}
			for (Project temp : projectList) {
				projectIdList.add(temp.getId());
			}
			query.setProjectIdList(projectIdList);
			page = meetingSchedulingDao.selectPageList(query, pageable);
			content = page.getContent();
		}

		for (MeetingScheduling meeting : content) {
			for (Project project : projectList) {
				if ((meeting.getProjectId() != null) && (meeting.getProjectId()
						.longValue() == project.getId().longValue()) ) {
					meeting.setProjectName(project.getProjectName());
					String deptName = findDeptName(project.getProjectDepartid(),
							depList);
					meeting.setProjectCareerline(deptName);
					meeting.setCreateUname(project.getCreateUname());
					//添加项目编码
					meeting.setProjectCode(project.getProjectCode());
					if (meeting.getMeetingDate() != null) {
						String meetingDateStr = DateUtil
								.convertDateToString(meeting.getMeetingDate());
						meeting.setMeetingDateStr(meetingDateStr);
					}
				}
			}
			setDefaultValue(meeting);
		}
		if (page != null) {
			page.setContent(content);
		}
		
		return page;
	}

	
	
	
	@Override
	public int updateBySelective(MeetingScheduling ms) {
		return meetingSchedulingDao.updateBySelective(ms);
	}

	private String findDeptName(Long deptId, List<Department> depList) {
		String deptName = "未知";
		for (Department dept : depList) {
			if (deptId != null && dept.getId().equals(deptId)) {
				deptName = dept.getName();
			}
		}

		return deptName;
	}
	// 去重
	@SuppressWarnings({"rawtypes", "unchecked"})
	private List<Long> removeDuplicateWithOrder(List<Long> list) {
		Set set = new HashSet();
		List<Long> newList = new ArrayList<Long>();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Long element = (Long) iter.next();
			if (set.add(element))
				newList.add(element);
		}
		return newList;
	}

	/**
	 * 批量更新
	 */
	@Override
	public void updateBatch(List<MeetingScheduling> entityList) {
		
		 meetingSchedulingDao.updateBatch(entityList);
		
	}
	
	@Override
	public Page<MeetingScheduling> getMeetingList(MeetingScheduling bo,PageRequest page) {
		// TODO Auto-generated method stub
		Page<MeetingScheduling> list=meetingSchedulingDao.getMeetingList(bo,page);
		return list;
	}

	@Override
	public List<MeetingScheduling> getMeetingListByIds(MeetingScheduling bo) {
		// TODO Auto-generated method stub
		return meetingSchedulingDao.getMeetingListByIds(bo);
	}

	@Override
	public List<MeetingSchedulingBo> meetingListByCondition(MeetingScheduling ms) {
		// TODO Auto-generated method stub
		return meetingSchedulingDao.meetingListByCondition(ms);
	}


	
	
	
	//=== report
	@Override
	public MeetingSchedulingBo getMeetingScheduling(MeetingSchedulingBo bo) {
		// TODO Auto-generated method stub
		String currentTime = DateUtil.refFormatNowDate();
		String start = currentTime + " 00:00:00";
		String end = currentTime + " 23:59:59";
		//获取总数
		MeetingSchedulingBo meetotal=new MeetingSchedulingBo();
		meetotal.setReserveTimeStart(Timestamp.valueOf(start));
		meetotal.setReserveTimeEnd(Timestamp.valueOf(end));
		Long total = meetingSchedulingDao.getMeetingScheduling(meetotal);
		//立项会
		MeetingSchedulingBo lxhtotal=new MeetingSchedulingBo();
		lxhtotal.setReserveTimeStart(Timestamp.valueOf(start));
		lxhtotal.setReserveTimeEnd(Timestamp.valueOf(end));
		lxhtotal.setMeetingType("meetingType:3");
		Long lxh= meetingSchedulingDao.getMeetingScheduling(lxhtotal);
		//投决会
		MeetingSchedulingBo tjhtotal=new MeetingSchedulingBo();
		tjhtotal.setReserveTimeStart(Timestamp.valueOf(start));
		tjhtotal.setReserveTimeEnd(Timestamp.valueOf(end));
		tjhtotal.setMeetingType("meetingType:4");
		Long tjh= meetingSchedulingDao.getMeetingScheduling(tjhtotal);
		//评审会
		MeetingSchedulingBo pshtotal=new MeetingSchedulingBo();
		pshtotal.setReserveTimeStart(Timestamp.valueOf(start));
		pshtotal.setReserveTimeEnd(Timestamp.valueOf(end));
		pshtotal.setMeetingType("meetingType:2");
		Long psh= meetingSchedulingDao.getMeetingScheduling(pshtotal);
		
		//立项会等待
		MeetingSchedulingBo lxhwait=new MeetingSchedulingBo();
		lxhwait.setScheduleStatus(0);
		lxhwait.setMeetingType("meetingType:3");
		Long lxhwaitCount =  meetingSchedulingDao.getMeetingScheduling(lxhwait);
		//投决会等待
		MeetingSchedulingBo tjhwait=new MeetingSchedulingBo();
		tjhwait.setScheduleStatus(0);
		tjhwait.setMeetingType("meetingType:4");
		Long tjhwaitCount =  meetingSchedulingDao.getMeetingScheduling(tjhwait);
		//评审会等待
		MeetingSchedulingBo pshwait=new MeetingSchedulingBo();
		pshwait.setScheduleStatus(0);
		pshwait.setMeetingType("meetingType:2");
		Long pshwaitCount =  meetingSchedulingDao.getMeetingScheduling(pshwait);
		
		
		MeetingSchedulingBo mbo=new MeetingSchedulingBo();
		mbo.setMeetingTotal(total);
		mbo.setLxhTotal(lxh);
		mbo.setTjhTotal(tjh);
		mbo.setPshTotal(psh);
		mbo.setLxhWait(lxhwaitCount);
		mbo.setTjhWait(tjhwaitCount);
		mbo.setPshWait(pshwaitCount);
		return mbo;
	}

	@Override
	public Page<MeetingScheduling> getMeetingListForReport(MeetingSchedulingBo bo,PageRequest page) {
		// TODO Auto-generated method stub
		Page<MeetingScheduling> list=meetingSchedulingDao.getMeetingListForReport(bo,page);
		return list;
	}

}