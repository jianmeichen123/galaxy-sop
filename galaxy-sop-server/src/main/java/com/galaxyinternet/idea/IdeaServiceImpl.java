package com.galaxyinternet.idea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.bo.IdeaBo;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.enums.DictEnum.RecordType;
import com.galaxyinternet.common.enums.EnumUtil;
import com.galaxyinternet.dao.idea.AbandonedDao;
import com.galaxyinternet.dao.idea.IdeaDao;
import com.galaxyinternet.dao.project.MeetingRecordDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.model.common.Config;
import com.galaxyinternet.model.common.ProgressLog;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.idea.Abandoned;
import com.galaxyinternet.model.idea.Idea;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.ConfigService;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.IdeaService;
import com.galaxyinternet.service.ProgressLogService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.UserService;
import com.galaxyinternet.utils.CollectionUtils;
@Service
public class IdeaServiceImpl extends BaseServiceImpl<Idea>implements IdeaService {

	@Autowired
	private IdeaDao ideaDao;
	@Autowired
	private UserService userService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ConfigService configService;
	@Autowired
	private AbandonedDao abandonedDao;
	@Autowired
	private MeetingRecordDao meetingRecordDao;
	@Autowired
	private SopFileDao sopFileDao;
	@Autowired
	private ProgressLogService progressLogService;
	
	
	@Override
	protected BaseDao<Idea, Long> getBaseDao() {
		// TODO Auto-generated method stub
		return ideaDao;
	}
	
	@Override
	public List<Idea> queryList(Idea query) {
		List<Idea> list = super.queryList(query);
		queryReleated(list);
		return list;
	}
	@Override
	public Page<Idea> queryPageList(Idea query, Pageable pageable) 
	{
		Page<Idea> page = super.queryPageList(query, pageable);
		List<Idea> list = page.getContent();
		queryReleated(list);
		return page;
	}
	
	@Override
	public Idea queryById(Long id) {
		Idea idea = super.queryById(id);
		List<Idea> list = Arrays.asList(idea);
		queryReleated(list);
		ProgressLog query = new ProgressLog();
		query.setRecordType(RecordType.IDEAS.getType());
		query.setRelatedId(idea.getId());
		List<ProgressLog> result = progressLogService.queryList(query, new PageRequest(0,1));
		if(result != null && result.size()>0)
		{
			ProgressLog log = result.iterator().next();
			String msg = DateUtil.longToString(log.getCreatedTime())+" "+log.getUname()+" "+log.getOperationContent();
			idea.setLatestLog(msg);
		}
		return idea;
	}

	private void queryReleated(List<Idea> list) 
	{
		if(list != null && list.size() > 0)
		{
			List<Long> userIds = new ArrayList<Long>();
			List<String> departmentIds = new ArrayList<String>();
			List<String> projectIds = new ArrayList<String>();
			for(Idea idea : list)
			{
				if(idea.getDepartmentId() != null && !departmentIds.contains(String.valueOf(idea.getDepartmentId())))
				{
					departmentIds.add(String.valueOf(idea.getDepartmentId()));
				}
				if(idea.getCreatedUid() != null && !userIds.contains(idea.getCreatedUid()))
				{
					userIds.add(idea.getCreatedUid());
				}
				if(idea.getClaimantUid() != null && !userIds.contains(idea.getClaimantUid()))
				{
					userIds.add(idea.getClaimantUid());
				}
				if(idea.getProjectId() != null && !projectIds.contains(String.valueOf(idea.getProjectId())))
				{
					projectIds.add(String.valueOf(idea.getProjectId()));
				}
			}
			
			List<User> users = null;
			List<Department> departments = null;
			List<Project> projects = null;
			if(departmentIds.size()>0)
			{
				departments = departmentService.queryListById(departmentIds);
				if(departments != null && departments.size() > 0)
				{
					for(Department dep : departments)
					{
						if(dep.getManagerId() != null && !userIds.contains(dep.getManagerId()))
						{
							userIds.add(dep.getManagerId());
						}
					}
				}
			}
			if(userIds.size() >0)
			{
				User userQuery = new User();
				userQuery.setIds(userIds);
				users = userService.queryList(userQuery);
			}
			
			if(projectIds.size() > 0)
			{
				ProjectBo projectQuery = new ProjectBo();
				projectQuery.setIds(projectIds);
				projects = projectService.queryList(projectQuery);
			}
			
			for(Idea idea : list)
			{
				Department depart = CollectionUtils.getItem(departments, "id", idea.getDepartmentId());
				String departmentDesc = depart != null ? depart.getName() : "";
				String createdUname = CollectionUtils.getItemProp(users, "id", idea.getCreatedUid(), "realName");
				String claimantUname = CollectionUtils.getItemProp(users, "id", idea.getClaimantUid(), "realName");
				Project project = CollectionUtils.getItem(projects, "id", idea.getProjectId());
				String projectName = project != null ? project.getProjectName() : "";
				String projectProgressDesc = project != null ? project.getProgress() : "";
				String hhrName = null;
				if(depart != null && depart.getManagerId() != null){
					hhrName = CollectionUtils.getItemProp(users, "id", depart.getManagerId(), "realName");
				}
				idea.setDepartmentDesc(departmentDesc);
				idea.setCreatedUname(createdUname);
				idea.setClaimantUname(claimantUname);
				idea.setProjectName(projectName);
				idea.setHhrName(hhrName);
				idea.setProjectProgressDesc(projectProgressDesc);
			}
		}
	}
	@Override
	public void createProject(Long id, String projectName) throws BusinessException
	{
		Idea idea = queryById(id);
		if(idea == null)
		{
			throw new BusinessException("数据错误。");
		}
		if(idea.getProjectId() != null)
		{
			throw new BusinessException("不能重复创建项目。");
		}
		
		
		ProjectBo projectQuery = new ProjectBo();
		projectQuery.setProjectName(projectName);
		List<Project> projectList = projectService.queryList(projectQuery);
		if(projectList != null && projectList.size() > 0)
		{
			throw new BusinessException("项目名已存在。");
		}
		User user = userService.queryById(idea.getClaimantUid());
		Project project = new Project();
		project.setIdeaId(id);
		project.setProjectName(projectName);
		project.setCreatedTime(new Date().getTime());
		project.setCreateUid(idea.getClaimantUid());
		project.setCurrencyUnit(0);
		project.setFinanceStatus(DictEnum.financeStatus.尚未获投.getCode());
		if(user != null)
		{
			project.setCreateUname(user.getRealName());
		}
		project.setProjectDepartid(idea.getDepartmentId());
		project.setIndustryOwn(idea.getDepartmentId());
		project.setStockTransfer(0);
		project.setProjectProgress(DictEnum.projectProgress.接触访谈.getCode());
		project.setProjectType(DictEnum.projectType.创建.getCode());
		project.setProjectStatus(DictEnum.projectStatus.GJZ.getCode());
		try 
		{
			String projectCode = generateProjectCode(project.getProjectDepartid());
			project.setProjectCode(projectCode);
			project.setFaFlag(0);
			projectService.newProject(project, null);
			idea.setProjectId(project.getId());
			idea.setIdeaProgress(SopConstant.IDEA_PROGRESS_CJXM);
			updateById(idea);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		
	}
	
	private String generateProjectCode(Long depId) throws Exception
	{
		Config config = configService.createCode();
		String prefix = String.valueOf(EnumUtil.getCodeByCareerline(depId.longValue()));
		return prefix+StringUtils.leftPad(config.getValue(), 6, '0');
	}
	
	@Override
	@Transactional
	public int updateById(IdeaBo idea){
		int result=0;
		try 
		{
			result=ideaDao.updateById(idea);
			if(idea.getIdeaProgress().equals("ideaProgress:4")&&result>=1){
				Abandoned abandoned=new Abandoned();
				User user = userService.queryById(idea.getGiveUpId());
				abandoned.setAbUserid(user!=null?user.getId():0);
				abandoned.setAbUsername(user!=null?user.getRealName():"");
				abandoned.setIdeaId(idea.getId());
				abandoned.setAbReason(idea.getAbReason());
				abandoned.setAbDatetime(new Date());
				Long insert = abandonedDao.insert(abandoned);
				SopFile sopFile=new SopFile();
				sopFile.setProjectId(idea.getId());
				sopFile.setRecordType((byte)1);
				sopFile.setFileValid(1);
				List<SopFile> queryByIdea = sopFileDao.selectList(sopFile);
				int res=0;
				if(!queryByIdea.isEmpty()){
					boolean flagr=true;
					for(SopFile  s:queryByIdea){
						s.setFileValid(0);
						res=sopFileDao.updateById(s);
						if(res<=0){
							flagr=false;
							break;
						}
					}
				   if(insert<=0||flagr==false){
						result=0;
					}
				}else{
					if(insert<=0){
						result=0;
					}
				}
				
				MeetingRecord meet = new MeetingRecord();
				meet.setProjectId(idea.getId());
				meet.setRecordType(RecordType.IDEAS.getType());
				meet.setMeetValid((byte)0);
				List<MeetingRecord> meetList  = meetingRecordDao.selectList(meet);
				if(meetList!=null && !meetList.isEmpty()){
					for(MeetingRecord ameet :  meetList){
						ameet.setMeetValid((byte)1);
						meetingRecordDao.updateById(ameet);
					}
				}
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	   return result;
		
	}
	
	
}
