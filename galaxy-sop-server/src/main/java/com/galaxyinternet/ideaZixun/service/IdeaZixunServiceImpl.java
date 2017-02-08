package com.galaxyinternet.ideaZixun.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.galaxyinternet.bo.IdeaZixunBo;
import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.dao.idea.IdeaZixunDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.idea.IdeaZixun;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.IdeaZixunService;
import com.galaxyinternet.service.SopTaskService;
import com.galaxyinternet.service.UserRoleService;
import com.galaxyinternet.service.UserService;


@Service("com.galaxyinternet.service.IdeaZixunService")
public class IdeaZixunServiceImpl extends BaseServiceImpl<IdeaZixun> implements IdeaZixunService{

	@Autowired
	private IdeaZixunDao ideaZixunDao;
	
	@Autowired
	private UserService userService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private DepartmentService departmentService;
	
	
	
	@Override
	protected BaseDao<IdeaZixun, Long> getBaseDao() {
		return this.ideaZixunDao;
	}

	
		
		
	
	@Override
	public Page<IdeaZixunBo> queryZixunPage(IdeaZixunBo query, PageRequest pageable) {
		
		Long total = 0l;
		Page<IdeaZixun> viewPage = null;
		List<IdeaZixun> viewList = null;
		List<IdeaZixunBo> viewBoList = new ArrayList<IdeaZixunBo>();
		
		Map<Long,String> dptIdNameMap = new HashMap<Long,String>();
		Map<Long,String> userIdNameMap = new HashMap<Long,String>();
		
		
		viewPage = ideaZixunDao.selectPageList(query, pageable);
		total = viewPage.getTotal();
		
		viewList = viewPage.getContent();
		
		if(viewList != null){
			for(IdeaZixun zix : viewList){
				if(zix.getDepartmentId() != null){
					dptIdNameMap.put(zix.getDepartmentId(), "");
				}
				if(zix.getUpdatedUid() != null){
					userIdNameMap.put(zix.getUpdatedUid(), "");
				}
			}
			
			if(dptIdNameMap.size() != 0){
				Department dq = new Department();
				dq.setIds(new ArrayList<>(dptIdNameMap.keySet()));
				List<Department> dpts = departmentService.queryList(dq);
				
				for(Department ad : dpts){
					dptIdNameMap.put(ad.getId(),ad.getName());
				}
			}
			
			if(userIdNameMap.size() != 0){
				User uq = new User();
				uq.setIds(new ArrayList<>(userIdNameMap.keySet()));
				List<User> us = userService.queryList(uq);
				
				for(User au : us){
					userIdNameMap.put(au.getId(),au.getRealName());
				}
			}
		}

		for(IdeaZixun zix : viewList){
			
			IdeaZixunBo ab = new IdeaZixunBo();
			
			ab.setId(zix.getId());
			ab.setCode(zix.getCode());
			ab.setCompanyField(zix.getCompanyField());
			ab.setDepartName(dptIdNameMap.get(zix.getDepartmentId()));
			ab.setUserName(userIdNameMap.get(zix.getUpdatedUid()));
			ab.setUpdatedTime(zix.getUpdatedTime());

			viewBoList.add(ab);
		}
		
		return new Page<IdeaZixunBo>(viewBoList, pageable, total);
	}


	
	
}