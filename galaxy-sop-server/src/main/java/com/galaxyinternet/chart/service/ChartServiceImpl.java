package com.galaxyinternet.chart.service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.dao.chart.ChartDao;
import com.galaxyinternet.dao.department.DepartmentDao;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.user.UserDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.model.chart.Chart;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.UserService;
import com.galaxyinternet.service.chart.ChartService;

@Service("com.galaxyinternet.service.ChartService")
public class ChartServiceImpl extends BaseServiceImpl<Chart>implements ChartService {
	// private final Logger logger = LoggerFactory.getLogger(getClass());

	public static final int perAllProTarget = 500; // 每人 每年 项目目标
	public static final int perWbProTarget = 500; // 每人 每年 投资 项目目标数
	public static final int perZjProTarget = 500; // 每人 每年 内部自建 项目目标数

	@Autowired
	private ChartDao chartDao;

	@Autowired
	private ProjectDao projectDao;

	/*@Autowired
	private UserDao userDao;*/
	@Autowired
	private UserService userService;

	/*@Autowired
	private DepartmentDao departmentDao;*/
	@Autowired
	private DepartmentService departmentService;
	
	
	

	@Override
	protected BaseDao<Chart, Long> getBaseDao() {
		return this.chartDao;
	}

	/*
	 * @Override public List<CareerlineChart> careerlineChart(Map<String,
	 * Object> params) { return chartDao.careerlineChart(params); }
	 */

	@Override
	public List<Map<String, Object>> ggLineChart(Map<String, Object> params) throws ParseException {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Map<Long, Map<String, Object>> mmm = new HashMap<Long, Map<String, Object>>(); // 初始化各部门数据结果
																						// key=departid

		Integer pageNum = (Integer) (params.get("pageNum") == null ? 0 : params.get("pageNum"));
		Integer pageSize = (Integer) (params.get("pageSize") == null ? 0 : params.get("pageSize"));

		Integer model = (Integer) (params.get("model") == null ? 0 : params.get("model")); // 1分页模式
																							// 2返回全部
		Integer datatype = (Integer) (params.get("datatype") == null ? 0 : params.get("datatype")); // 当model＝1时有效
																									// -1:返回总数
																									// <>-1:返回数据行

		// 获取所有事业线
		List<Long> departmentIdList = new ArrayList<Long>();
		Department query = new Department();
		query.setType(1);
		List<Department> departmentList = departmentService.queryList(query);

		if (model == 1 && datatype == -1) {
			Map<String, Object> subResult = new HashMap<String, Object>();
			subResult.put("c", Long.valueOf((departmentList == null ? 0 : departmentList.size())));
			resultList.add(subResult);
			return resultList;
		}

		if (departmentList != null && !departmentList.isEmpty()) {
			for (Department dep : departmentList) {
				departmentIdList.add(dep.getId()); // [5, 6, 7, 8, 9, 10, 11,12,
													// 13, 14, 15, 16, 17,24,
													// 25, 26, 28]

				Map<String, Object> subResult = new HashMap<String, Object>();
				subResult.put("target", 0);// 目标数
				subResult.put("notcompleted", 0);// 未完成数
				subResult.put("completed", 0);// 完成项目数
				subResult.put("zj_completed", 0);// 自建项目数
				subResult.put("wb_completed", 0);// 投资项目数

				subResult.put("rate", 0); // 完成率
				subResult.put("wb_rate", 0);// 已完成项目占比： 外部完成率
				subResult.put("zj_rate", 0);// 已完成项目占比： 自建完成率

				subResult.put("department_name", dep.getName());// 部门名称
				subResult.put("department_id", dep.getId());// 部门id

				mmm.put(dep.getId(), subResult);
			}
		} else {
			return resultList;
		}

		// 事业线-投资经理人数统计
		Map<String, Object> userQuery = new HashMap<String, Object>();
		userQuery.put("roleId", 4);
		userQuery.put("departmentIds", departmentIdList);
		List<User> userlist = userService.querytTzjlSum(userQuery);
		if (userlist != null && !userlist.isEmpty()) {
			for (User au : userlist) {
				if (mmm.containsKey(au.getDepartmentId())) {
					Map<String, Object> subResult = mmm.get(au.getDepartmentId());

					String projectType = params.get("projectType").equals("-1") ? null
							: params.get("projectType").toString();
					if (projectType == null) {
						subResult.put("target", perAllProTarget * (int) (au.getUserTzjlSum()));// 目标数
					} else if (projectType.equals("projectType:1")) {
						subResult.put("target", perWbProTarget * (int) (au.getUserTzjlSum()));// 投资目标数
					} else if (projectType.equals("projectType:2")) {
						subResult.put("target", perZjProTarget * (int) (au.getUserTzjlSum()));// 自建目标数
					}
				}
			}
		}

		// 项目查询
		ProjectBo proQuery = new ProjectBo();
		proQuery.setStartTime(
				DateUtil.stringToLong(params.get("sdate").toString() + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		proQuery.setEndTime(DateUtil.stringToLong(params.get("edate").toString() + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		// proQuery.setProjectType(params.get("projectType").equals("-1") ? null
		// : params.get("projectType").toString());
		List<Project> proList = projectDao.selectProjectReportForGg(proQuery);

		for (Project apro : proList) {
			Map<String, Object> subResult = mmm.get(apro.getProjectDepartid());
			if (subResult != null) {
				if (apro.getProjectType() != null && apro.getProjectType().equals("projectType:1")) { // 投资
					subResult.put("wb_completed", apro.getCompleted());// 投资项目数
				} else if (apro.getProjectType() != null && apro.getProjectType().equals("projectType:2")) { // 内部
					subResult.put("zj_completed", apro.getCompleted());// 自建项目数
				}
			}
		}

		DecimalFormat df = new DecimalFormat("0.0000");
		Set<Entry<Long, Map<String, Object>>> set = mmm.entrySet();
		Iterator<Entry<Long, Map<String, Object>>> iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry<Long, Map<String, Object>> mapentry = iterator.next();
			Map<String, Object> subResult = mapentry.getValue();
			int target = (int) subResult.get("target");
			int wb_completed = (Integer) subResult.get("wb_completed");
			int zj_completed = (Integer) subResult.get("zj_completed");
			int completed = 0;
			double rate = 0;
			double wb_rate = 0;
			double zj_rate = 0;

			String projectType = params.get("projectType").equals("-1") ? null : params.get("projectType").toString();
			if (projectType == null) {
				if (wb_completed != 0 || zj_completed != 0) {
					if (target != 0) {
						rate = (double) (wb_completed + zj_completed) / target;
					}
					completed = wb_completed + zj_completed;
				}
			} else if (projectType.equals("projectType:1")) { // 外部
				if (wb_completed != 0) {
					if (target != 0) {
						rate = (double) wb_completed / target;
					}
					completed = wb_completed;
				}

			} else if (projectType.equals("projectType:2")) { // 内部
				if (zj_completed != 0) {
					if (target != 0) {
						rate = (double) (zj_completed) / target;
					}
					completed = zj_completed;
				}
			}

			if (wb_completed != 0 || zj_completed != 0) {
				wb_rate = (double) wb_completed / (wb_completed + zj_completed);
				zj_rate = (double) zj_completed / (wb_completed + zj_completed);
			}

			subResult.put("notcompleted", target == 0 ? 0 : target - completed);// 未完成数
			subResult.put("completed", completed);// 完成项目数
			subResult.put("rate", df.format(rate)); // 完成率
			subResult.put("wb_rate", df.format(wb_rate));// 已完成项目占比： 外部完成率
			subResult.put("zj_rate", df.format(zj_rate));// 已完成项目占比： 自建完成率

			resultList.add(subResult);
		}

		// 排序，项目数从大到小
		for (int i = 0; i < resultList.size() - 1; i++) {
			for (int j = 0; j < resultList.size() - i - 1; j++) {
				if ((int) resultList.get(j).get("completed") < (int) resultList.get(j + 1).get("completed")) {
					Map<String, Object> temp = resultList.get(j);
					resultList.set(j, resultList.get(j + 1));
					resultList.set(j + 1, temp);
				}
			}
		}

		if (model == 1 && datatype != -1) {
			int beginIndex = pageNum * pageSize;
			int endIndex = pageNum * pageSize + pageSize;
			if (resultList.size() < endIndex) {
				endIndex = resultList.size();
			}
			return resultList.subList(beginIndex, endIndex);
		}

		return resultList;
	}

	@Override
	public List<Map<String, Object>> lineChart(Map<String, Object> params) {
		return chartDao.lineChart(params);
	}

	@Override
	public List<Map<String, Object>> dataBriefChart(Map<String, Object> params) {
		return chartDao.dataBriefChart(params);
	}

	@Override
	public List<Map<String, Object>> projectList(Map<String, Object> params) {
		return chartDao.projectList(params);
	}

	@Override
	public List<Map<String, Object>> userKpi(Map<String, Object> params) {
		return chartDao.userKpi(params);
	}

	@Override
	public List<Map<String, Object>> deptKpi(Map<String, Object> params) {
		return chartDao.deptKpi(params);
	}

	@Override
	public List<Map<String, Object>> projectProgressChart(Map<String, Object> params) {
		return chartDao.projectProgressChart(params);
	}

	@Override
	public List<Map<String, Object>> meetingRate(Map<String, Object> params) {
		return chartDao.meetingRate(params);
	}

	@Override
	public List<Map<String, Object>> meetingRateUser(Map<String, Object> params) {
		return chartDao.meetingRateUser(params);
	}

	@Override
	public List<Map<String, Object>> rateRiseDChart(Map<String, Object> params) {
		return chartDao.rateRiseDChart(params);
	}

	@Override
	public List<Map<String, Object>> rateRiseD(Map<String, Object> params) {
		return chartDao.rateRiseD(params);
	}

	@Override
	public List<Map<String, Object>> rateRiseMChart(Map<String, Object> params) {
		return chartDao.rateRiseMChart(params);
	}

	@Override
	public List<Map<String, Object>> rateRiseM(Map<String, Object> params) {
		return chartDao.rateRiseM(params);
	}

	@Override
	public List<Map<String, Object>> rateRiseMonthChart(Map<String, Object> params) {
		return chartDao.rateRiseMonthChart(params);
	}

	@Override
	public List<Map<String, Object>> platformMeetingScheduling(Map<String, Object> params) {
		return chartDao.platformMeetingScheduling(params);
	}

	@Override
	public List<Map<String, Object>> meetingSchedList(Map<String, Object> params) {
		return chartDao.meetingSchedList(params);
	}

	@Override
	public List<Map<String, Object>> progressDurationList(Map<String, Object> params) {
		return chartDao.progressDurationList(params);
	}

	@Override
	public List<Map<String, Object>> departmentList(Map<String, Object> params) {
		return chartDao.departmentList(params);
	}

	@Override
	public List<Map<String, Object>> lineHhrChart(Map<String, Object> params) {
		return chartDao.lineHhrChart(params);
	}

	@Override
	public List<Map<String, Object>> appBrief(Map<String, Object> params) {
		return chartDao.appBrief(params);
	}

	@Override
	public List<Map<String, Object>> lineHhrChart1(Project ps, PageRequest pr) {
		int pageNum = pr.getPageNumber() == 0 ? 0 : pr.getPageNumber();
		int pageSize = pr.getPageSize() == 0 ? 0 : pr.getPageSize();

		/**
		 * 1,根据当前登录的事业线查询该事业线下所有的投资经理
		 * 
		 */
		List<Map<String, Object>> projectAnalysis = new ArrayList<Map<String, Object>>();
		User user = new User();
		user.setDepartmentId(ps.getProjectDepartid());
		Long nowid = ps.getCreateUid();
		ps.setCreateUid(null);
		List<User> selectList = userService.queryList(user);
		List<Project> selectPageList = projectDao.selectList(ps);
		Department selectById = departmentService.queryById(ps.getProjectDepartid());
		Map<String, Object> totalMap = new HashMap<String, Object>();
		DecimalFormat df = new DecimalFormat("0.0000");
		for (User u : selectList) {
			if (!nowid.equals(u.getId())) {
				// 自建项目数
				int zj_completed = 0;
				// 投资项目数
				int wb_completed = 0;
				// 完成项目数
				int completed = 0;
				Map<String, Object> map = new HashMap<String, Object>();
				for (Project p : selectPageList) {
					if (u.getId().longValue() == p.getCreateUid().longValue()) {
						completed = completed + 1;
						if (p.getProjectType().equals("projectType:1")) {
							wb_completed = wb_completed + 1;
						} else {
							zj_completed = zj_completed + 1;
						}
					}

				}
				map.put("rate", completed * 1.00 / 500);
				map.put("wb_rate", completed != 0 ? df.format(wb_completed * 1.00 / completed) : 0);
				map.put("notcompleted", 500 - completed);
				map.put("zj_completed", zj_completed);
				map.put("zj_rate", completed != 0 ? df.format(zj_completed * 1.0 / completed) : 0);
				map.put("real_name", u.getRealName());
				map.put("target", 500);
				map.put("user_id", u.getId());
				if (selectById != null) {
					map.put("department_name", selectById.getName());
				} else {
					map.put("department_name", "");
				}

				map.put("wb_completed", wb_completed);
				map.put("completed", completed);
				projectAnalysis.add(map);
			}
		}

		// 排序，项目数从大到小
		for (int i = 0; i < projectAnalysis.size() - 1; i++) {
			for (int j = 0; j < projectAnalysis.size() - i - 1; j++) {
				if ((int) projectAnalysis.get(j).get("completed") < (int) projectAnalysis.get(j + 1).get("completed")) {
					Map<String, Object> temp = projectAnalysis.get(j);
					projectAnalysis.set(j, projectAnalysis.get(j + 1));
					projectAnalysis.set(j + 1, temp);
				}
			}
		}

		int beginIndex = pageNum * pageSize;
		int endIndex = pageNum * pageSize + pageSize;
		if (projectAnalysis.size() < endIndex) {
			endIndex = projectAnalysis.size();
			projectAnalysis = projectAnalysis.subList(beginIndex, endIndex);
		}
		totalMap.put("total", projectAnalysis.size());
		projectAnalysis.add(totalMap);
		return projectAnalysis;
	}

}
