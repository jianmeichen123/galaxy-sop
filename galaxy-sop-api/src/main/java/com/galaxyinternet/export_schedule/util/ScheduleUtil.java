package com.galaxyinternet.export_schedule.util;


import java.util.List;
import java.util.Map;



public interface ScheduleUtil {
	
	public static final Byte BAI_FAN_TYPE = 2;
	
	
	/**
	 * 获取所有事业线(或 指定部门) departId - Name Map
	 * @param departmentIdList  
	 * 			<br> 部门ids，查询指定部门的信息
	 * 			<br> 如果为 null 查询全部事业线的信息
	 * 			
	 * @return departId - Name Map
	 */
	public Map<Long, String> queryAllSYX(List<Long> departmentIdList);
	
	
	/**
	 * 获取投资经理  uid-deptid map 
	 * @param   uids  用户 ids，  查询部门的id信息
	 * @return uId - deptid Map
	 */
	public Map<Long, Long> queryAllTzjlDeptMap(List<Long> uids);
	/**
	 * 获取投资经理  uid-deptid   map 
	 * @param   uids  根据用户 ids 过滤出 tzjl ids
	 * @return  
	 */
	public Map<Long, Long> queryTzjlUidsAndDeptIds(List<Long> uids);
	
	
	/**
	 * 获取部门下投资经理  uid-uname   map 
	 * @param   deptId  查询部门id下的信息
	 * @return  
	 */
	public Map<Long, String> queryUidNameByDept(Long deptId);
	
	
	/**
	 * 查询  user id-name 对应的map
	 */
	Map<Long,String> queryUidNameMap(List<Long> uids);

	
	/**
	 * 查询 dept id-name 对应的map
	 */
	Map<Long,String> queryDeptIdNameMap(List<Long> depts);
	
	
	
	/*
	*//**
	 * 获取部门下所有投资经理 （或公司所有） 
	 * @param depID  
	 * 			<br> 部门id，         查询指定部门下的投资经理
	 * 			<br> 如果为 null 查询全部投资经理的信息
	 * 			
	 * @return departId - uId-Name Map
	 *//*
	public Map<Long, Map<Long, String>> queryAllTZJL(Long depID);
	
	*/
	
}