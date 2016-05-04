package com.galaxyinternet.idea;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.idea.Idea;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.IdeaService;
import com.galaxyinternet.service.UserRoleService;
import com.galaxyinternet.service.UserService;
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
	@Autowired
	private UserService userService;
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
	public ResponseData<Idea> search(@RequestBody Idea query, HttpServletRequest request)
	{
		ResponseData<Idea> resp = new ResponseData<Idea>();
		
		try {
			PageRequest pageable = new PageRequest();
			Integer pageNum = query.getPageNum() != null ? query.getPageNum() : 0;
			Integer pageSize = query.getPageSize() != null ? query.getPageSize() : 10;
			pageable.setPage(pageNum);
			pageable.setSize(pageSize);
			//提出人
			if(StringUtils.isNotEmpty(query.getCreatedUname()))
			{
				User userQuery = new User();
				userQuery.setRealName(query.getCreatedUname());
				List<User> users = userService.queryList(userQuery);
				if(users == null || users.size() == 0)
				{
					return resp;
				}
				
				List<Long> userIds = new ArrayList<Long>();
				for(User user : users)
				{
					userIds.add(user.getId());
				}
				query.setCreatedUids(userIds);
			}
			//提出时间
			if(StringUtils.isNotEmpty(query.getCreatedDate()))
			{
				
				Date createdDate = DateUtil.convertStringToDate(query.getCreatedDate());
				query.setCreatedTimeFrom(DateUtil.getSearchFromDate(createdDate).getTime());
				query.setCreatedTimeThrough(DateUtil.getSearchToDate(createdDate).getTime());
			
			}
			//角色 - 投资经理、合伙人查看本事业线创意
			User user = (User)getUserFromSession(request);
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
			if(roleIdList != null && roleIdList.size()>0 && (roleIdList.contains(UserConstant.TZJL) || roleIdList.contains(UserConstant.HHR)))
			{
				query.setDepartmentId(user.getDepartmentId());
			}
			
			Page<Idea> page = ideaService.queryPageList(query, pageable);
			resp.setPageList(page);
		} catch (Exception e) {
			resp.getResult().addError("查询创意出错");
			logger.error("查询创意出错", e);
		}
		return resp;
	}
	
	@RequestMapping("/ideaProjectList")
	public String ideaProjectList(String ideaProgress, HttpSession session)
	{
		session.setAttribute("ideaProgress", ideaProgress);
		return "/idea/idea_project_list";
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
	/**
	 * 根据创意id获取创意相关信息
	 * @author jianmeichen
	 * @serialData 2016-05-04
	 * @param idea
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getIdea")
	public ResponseData<Idea> getIdea(@RequestBody Idea idea,HttpServletRequest request)
	{
		ResponseData<Idea> responseBody = new ResponseData<Idea>();
		if(idea.getId() == null){
			responseBody.setResult(new Result(Status.ERROR, null, "缺失必要的参数!"));
			return responseBody;
		}
		try {
			Idea queryById = ideaService.queryById(idea.getId());
			responseBody.setEntity(queryById);
			responseBody.setResult(new Result(Status.OK,null,"查询数据成功"));
		} catch (Exception e) {
			responseBody.getResult().addError("查询创意信息失败");
			logger.error("查询创意信息失败",e);
		}
		return responseBody;
	}
	/**
	 * 根据创意id获取创意相关信息
	 * @param idea
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateIdea")
	public ResponseData<Idea> updateIdea(@RequestBody Idea idea,HttpServletRequest request)
	{
		ResponseData<Idea> responseBody = new ResponseData<Idea>();
		try {
			int queryById = ideaService.updateById(idea);
			if(queryById<=0){
				responseBody.setResult(new Result(Status.ERROR, null, "编辑创意信息失败!"));
				return responseBody;
			}
			responseBody.setResult(new Result(Status.OK,null,"更新创意成功！"));
		} catch (Exception e) {
			responseBody.getResult().addError("编辑创意信息失败");
			logger.error("编辑创意信息失败",e);
		}
		return responseBody;
	}
	
}
