package com.galaxyinternet.idea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.idea.IdeaDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.idea.Idea;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.IdeaService;
import com.galaxyinternet.service.UserService;
@Service
public class IdeaServiceImpl extends BaseServiceImpl<Idea>implements IdeaService {

	@Autowired
	private IdeaDao ideaDao;
	@Autowired
	private UserService userService;
	@Autowired
	private DepartmentService departmentService;
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
			}
			
			List<User> users = null;
			List<Department> departments = null;
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
			
			for(Idea idea : list)
			{
				String departmentDesc = getDesc(departments, "id", "name", idea.getDepartmentId());
				String createdUname = getDesc(users, "id", "realName", idea.getCreatedUid());
				String claimantUname = getDesc(users, "id", "realName", idea.getClaimantUid());
				idea.setDepartmentDesc(departmentDesc);
				idea.setCreatedUname(createdUname);
				idea.setClaimantUname(claimantUname);
			}
		}
		
		return page;
	}
	
	private String getDesc(Collection items, String propCodeName, String propDescName, Object val)
	{
		String result = null;
		if(items != null && items.size() > 0 && val != null)
		{
			for(Object item : items)
			{
				BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(item);
				Object code = wrapper.getPropertyValue(propCodeName);
				Object desc = wrapper.getPropertyValue(propDescName);
				if(val.equals(code))
				{
					result = desc != null ? desc.toString() : null;
					break;
				}
			}
		}
		
		return result;
	}

	
}
