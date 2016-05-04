package com.galaxyinternet.common.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.idea.Idea;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.IdeaService;
import com.galaxyinternet.service.UserRoleService;
@Controller
@RequestMapping("/galaxy/idea")
public class IdeaController extends BaseControllerImpl<Idea, Idea> {
	private static final Logger logger = Logger.getLogger(IdeaController.class);
	@Autowired
	private IdeaService ideaService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private DepartmentService departmentService;
	@Override
	protected BaseService<Idea> getBaseService() {
		return this.ideaService;
	}

	
	@RequestMapping(method = RequestMethod.GET)
	public String list()
	{
		return "idea/idea_list";
	}
	@ResponseBody
	@RequestMapping("/search")
	public ResponseData<Idea> search(@RequestBody Idea idea, HttpServletRequest request)
	{
		ResponseData<Idea> resp = new ResponseData<Idea>();
		
		try {
			PageRequest pageable = new PageRequest();
			Integer pageNum = idea.getPageNum() != null ? idea.getPageNum() : 0;
			Integer pageSize = idea.getPageSize() != null ? idea.getPageSize() : 10;
			pageable.setPage(pageNum);
			pageable.setSize(pageSize);
			Page<Idea> page = ideaService.queryPageList(idea, pageable);
			resp.setPageList(page);
		} catch (Exception e) {
			resp.getResult().addError("查询创意出错");
			logger.error("查询创意出错", e);
		}
		return resp;
	}
	@ResponseBody
	@RequestMapping("/getDepartment")
	public ResponseData<Department> getDepartment(HttpServletRequest request)
	{
		ResponseData<Department> resp = new ResponseData<Department>();
		try {
			User user = (User)getUserFromSession(request);
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
			if(roleIdList != null && roleIdList.size()>0)
			{
				List<Department> departments = new ArrayList<Department>();
				if(roleIdList.contains(UserConstant.TZJL) || roleIdList.contains(UserConstant.HHR))
				{
					Department department = departmentService.queryById(user.getDepartmentId());
					departments.add(department);
				}
				else if(roleIdList.contains(UserConstant.CEO) || roleIdList.contains(UserConstant.DSZ))
				{
					Department depQuery = new Department();
					depQuery.setType(1);
					departments =  departmentService.queryList(depQuery);
				}
				resp.setEntityList(departments);
			}
		} catch (Exception e) {
			resp.getResult().addError("查询事业线失败");
			logger.error("查询事业线失败",e);
		}
		return resp;
	}
}
