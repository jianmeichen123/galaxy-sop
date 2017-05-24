package com.galaxyinternet.export_schedule.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.UserService;

@Service("com.galaxyinternet.rili.service.ScheduleUtil")
public class ScheduleUtilImpl implements ScheduleUtil {

	
	
	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private UserService userService;
	
	
	
	/**
	 * 获取所有事业线(或 指定部门) departId - Name Map
	 * @param departmentIdList  
	 * 			<br> 部门ids，查询指定部门的信息
	 * 			<br> 如果为 null 查询全部事业线的信息
	 * 			
	 * @return 
	 */
	public Map<Long, String> queryAllSYX(List<Long> departmentIdList) {
		Department query = new Department();
		
		if (departmentIdList != null && !departmentIdList.isEmpty()) {
			query.setIds(departmentIdList);
		}else{
			query.setType(1);
		}
		
		List<Department> dpts = departmentService.queryList(query);
		
		Map<Long, String> departIdNameMap = new HashMap<Long, String>();
		if(dpts!=null && !dpts.isEmpty()){
			for (Department dep : dpts) {
				departIdNameMap.put(dep.getId(), dep.getName());
			}
		}

		return departIdNameMap;
	}
	
	
	
	
	/**
	 * 获取投资经理  uid-deptid   map 
	 * @param   uids  用户 ids，查询部门的id信息
	 * @return  
	 */
	public Map<Long, Long> queryAllTzjlDeptMap(List<Long> uids) {
		
		Map<Long, Long> uId_deptidMap = new HashMap<Long, Long>();
		if (uids != null && !uids.isEmpty()) {
			User userQ = new User();
			userQ.setIds(uids);
			List<User> uList = userService.queryList(userQ);
			for (User aUser : uList) {
				uId_deptidMap.put(aUser.getId(), aUser.getDepartmentId());
			}
		}
		return uId_deptidMap;
		
	}
	
	
	
	/**
	 * 获取部门下投资经理  uid-uname   map 
	 * @param   deptId  查询部门id下的信息
	 * @return  
	 */
	public Map<Long, String> queryUidNameByDept(Long deptId){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("departmentId", deptId);
		List<User> users = userService.querytUserByParams(params);
		
		Map<Long, String> uIdNameMap = new HashMap<Long, String>();
		if (users != null && !users.isEmpty()) {
			for (User aUser : users) {
				uIdNameMap.put(aUser.getId(), aUser.getRealName());
			}
		}
		
		return uIdNameMap;
	}
	
	
	/**
	 * 查询 user id-name 对应的map
	 */
	public Map<Long, String> queryUidNameMap(List<Long> uids) {
		Map<Long, String> uIdNameMap = new HashMap<Long, String>();
		if (uids != null && !uids.isEmpty()) {
			User userQ = new User();
			userQ.setIds(uids);
			List<User> uList = userService.queryList(userQ);
			for (User aUser : uList) {
				uIdNameMap.put(aUser.getId(), aUser.getRealName());
			}
		}
		return uIdNameMap;
	}
	

	/**
	 * 查询 dept id-name 对应的map
	 */
	public Map<Long, String> queryDeptIdNameMap(List<Long> depts) {

		Map<Long, String> departIdNameMap = new HashMap<Long, String>();

		if (depts != null && !depts.isEmpty()) {
			Department query = new Department();
			// query.setType(1);
			query.setIds(depts);
			List<Department> deptList = departmentService.queryList(query);

			for (Department dep : deptList) {
				departIdNameMap.put(dep.getId(), dep.getName());
			}
		}

		return departIdNameMap;
	}


	
	
	/*
	*//**
	 * 获取部门下所有投资经理 （或公司所有投资经理） 
	 * @param depID  
	 * 			<br> 部门id，         查询指定部门下的投资经理
	 * 			<br> 如果为 null 查询全部投资经理的信息
	 * 			
	 * @return departId - uId-Name Map
	 *//*
	public Map<Long, Map<Long, String>> queryAllTZJL(Long depID) {
		Map<String, Object> userQuery = new HashMap<String, Object>();
		if (depID != null) {
			userQuery.put("departmentId", depID);
		}
		userQuery.put("roleId", 4);
		
		List<User> ulist = userService.querytUserByParams(userQuery);
		
		
		Map<Long, Map<Long, String>> dept_uIdNameMap = new HashMap<Long, Map<Long, String>>();
		Map<Long, String> uIdNameMap = new HashMap<Long, String>();
		if (ulist != null && !ulist.isEmpty()) {
			for (User aUser : ulist) {
				if(dept_uIdNameMap.containsKey(aUser.getDepartmentId())){
					dept_uIdNameMap.get(aUser.getDepartmentId()).put(aUser.getId(), aUser.getRealName());
				}else{
					uIdNameMap = new HashMap<Long, String>();
					uIdNameMap.put(aUser.getId(), aUser.getRealName());
					
					dept_uIdNameMap.put(aUser.getDepartmentId(), uIdNameMap);
				}
			}
		}
		
		return dept_uIdNameMap;
	}

		
	
	
	// 事业线(各部门) - 投资经理人数统计
	public Map<Long, Integer> queryDeptTzjlSum(List<Long> departmentIdList) {
		Map<Long, Integer> deptTzjlNumMap = new HashMap<Long, Integer>();
		Map<String, Object> deptTzjlNumQuery = new HashMap<String, Object>();
		deptTzjlNumQuery.put("roleId", 4);
		if (departmentIdList != null && !departmentIdList.isEmpty()) {
			deptTzjlNumQuery.put("departmentIds", departmentIdList);
		}
		// userQuery.setStatus("0");
		List<User> userlist = userService.querytTzjlSum(deptTzjlNumQuery);
		if (userlist != null && !userlist.isEmpty()) {
			for (User au : userlist) {
				deptTzjlNumMap.put(au.getDepartmentId(), au.getUserTzjlSum());
			}
		}
		return deptTzjlNumMap;
	}

	
	// 事业线(各部门) - 合伙人
	public Map<Long, User> queryDeptOfHHR(List<Long> departmentIdList, Integer roleId) {
		Map<Long, User> deptOfHHRMap = new HashMap<Long, User>();
		Map<String, Object> deptHHRQuery = new HashMap<String, Object>();
		deptHHRQuery.put("roleId", roleId);
		if (departmentIdList != null && !departmentIdList.isEmpty()) {
			deptHHRQuery.put("departmentIds", departmentIdList);
		}
		// deptHHRQuery.setStatus("0");
		List<User> userlist = userService.querytUserByParams(deptHHRQuery);
		if (userlist != null && !userlist.isEmpty()) {
			for (User au : userlist) {
				deptOfHHRMap.put(au.getDepartmentId(), au);
			}
		}
		return deptOfHHRMap;
	}
*/
	
}