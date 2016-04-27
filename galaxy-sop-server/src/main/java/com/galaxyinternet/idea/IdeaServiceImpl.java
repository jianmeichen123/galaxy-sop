package com.galaxyinternet.idea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.dao.idea.IdeaDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.idea.Idea;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.IdeaService;
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
	@Override
	protected BaseDao<Idea, Long> getBaseDao() {
		// TODO Auto-generated method stub
		return ideaDao;
	}
	@Override
	public Page<Idea> queryPageList(Idea query, Pageable pageable) {
		// TODO Auto-generated method stub
		Page<Idea> page = super.queryPageList(query, pageable);
		List<Idea> list = page.getContent();
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
			if(userIds.size() >0)
			{
				User userQuery = new User();
				userQuery.setIds(userIds);
				users = userService.queryList(userQuery);
			}
			if(departmentIds.size()>0)
			{
				departments = departmentService.queryListById(departmentIds);
			}
			if(projectIds.size() > 0)
			{
				ProjectBo projectQuery = new ProjectBo();
				projectQuery.setIds(projectIds);
				projects = projectService.queryList(projectQuery);
			}
			
			for(Idea idea : list)
			{
				String departmentDesc = CollectionUtils.getItemProp(departments, "id", idea.getDepartmentId(), "name");
				String createdUname = CollectionUtils.getItemProp(users, "id", idea.getCreatedUid(), "realName");
				String claimantUname = CollectionUtils.getItemProp(users, "id", idea.getClaimantUid(), "realName");
				String projectName = CollectionUtils.getItemProp(projects, "id", idea.getProjectId(), "projectName");
				idea.setDepartmentDesc(departmentDesc);
				idea.setCreatedUname(createdUname);
				idea.setClaimantUname(claimantUname);
				idea.setProjectName(projectName);
			}
		}
		return page;
	}
}
