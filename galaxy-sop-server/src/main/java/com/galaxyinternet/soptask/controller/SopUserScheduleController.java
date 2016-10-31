package com.galaxyinternet.soptask.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.SheduleCommon;
import com.galaxyinternet.bo.SopUserScheduleBo;
import com.galaxyinternet.bo.project.MeetingSchedulingBo;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.dictEnum.DictUtil;
import com.galaxyinternet.exception.PlatformException;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.model.project.PersonPool;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.soptask.SopUserSchedule;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.MeetingSchedulingService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopUserScheduleService;

@Controller
@RequestMapping("/galaxy/sopUserSchedule")
public class SopUserScheduleController extends
		BaseControllerImpl<SopUserSchedule, SopUserScheduleBo> {
	final Logger logger = LoggerFactory
			.getLogger(SopUserScheduleController.class);

	@Autowired
	private SopUserScheduleService sopUserScheduleService;
	
	@Autowired
	private MeetingSchedulingService meetingSchedulingService;
	
	@Autowired
	private DepartmentService departmentService;

	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	@Autowired
	private ProjectService projectService;

	@Override
	protected BaseService<SopUserSchedule> getBaseService() {
		// TODO Auto-generated method stub
		return this.sopUserScheduleService;
	}
	/***
	 * 添加|修改我的日程 status 1:添加,2:修改
	 * 
	 * @param sopUserSchedule
	 * @return*/
	
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateSopUserSchedule", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopUserScheduleBo> addProjectPerson(@RequestBody SopUserScheduleBo sopUserSchedule, HttpServletRequest request) {
		ResponseData<SopUserScheduleBo> responseBody = new ResponseData<SopUserScheduleBo>();
		User user = (User) getUserFromSession(request);
		sopUserSchedule.setUserId(user.getId());
		String date=sopUserSchedule.getItemDateStr()+" 00:00:00";
		try {
			if(sopUserSchedule.getId() == null){
				sopUserSchedule.setItemDate(Timestamp.valueOf(date));
				sopUserScheduleService.insert(sopUserSchedule);
				responseBody.setResult(new Result(Status.OK,"添加日程成功!"));
			}else{
				sopUserSchedule.setItemDate(Timestamp.valueOf(date));
				sopUserScheduleService.updateById(sopUserSchedule);
				responseBody.setResult(new Result(Status.OK,"修改日程成功!"));
			}
		} catch (Exception e) {
			logger.error("操作日程失败!",e);
			responseBody.setResult(new Result(Status.ERROR,"操作日程失败!"));
		}
		return responseBody;
	}
	/***
	 * 获取我的日程前三条信息| type: 1:前三条数据?更多
	 * 
	 * @return
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequestMapping(value = "/selectSopUserSchedule/{type}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public ResponseData<SopUserScheduleBo> selectUserScheduleByTime(
			@PathVariable Integer type, HttpServletRequest request) throws ParseException {

		ResponseData<SopUserScheduleBo> responseBody = new ResponseData<SopUserScheduleBo>();
		User user = (User) getUserFromSession(request);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		Long currentTime = sdf.parse(date).getTime();
		List<SopUserScheduleBo> list = sopUserScheduleService
				.selectSopUserScheduleByTime(user.getId(), currentTime, type);
		Page<SopUserScheduleBo> page = new Page<SopUserScheduleBo>(list, null,
				null);
		responseBody.setPageList(page);
		
		return responseBody;
	}
	
	
	/***
	 * 获取日程列表
	 */
	@RequestMapping(value = "/scheduleList",method = RequestMethod.GET)
	public String scheduleList(HttpServletRequest request) {
		User user = (User) getUserFromSession(request);
		List<SheduleCommon> sop=sopUserScheduleService.scheduleListByDate(user.getId());
		request.setAttribute("sheduleList", sop);
		return "shedule/sheduleList";
	}
	
	
	/***
	 * 获取日程信息
	 */
	@ResponseBody
	@RequestMapping(value = "/getSchedule/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public ResponseData<SopUserScheduleBo> getSchedule(
			@PathVariable Long id, HttpServletRequest request) throws ParseException {

		ResponseData<SopUserScheduleBo> responseBody = new ResponseData<SopUserScheduleBo>();
		try{
			SopUserScheduleBo sop= (SopUserScheduleBo) sopUserScheduleService.queryById(id);
			String str[] = sop.getItemDate().toString().split(" ");
			sop.setTimeTask(str[0]);
			responseBody.setEntity(sop);
			responseBody.setResult(new Result(Status.OK,"成功!"));
		}catch(Exception e){
			responseBody.setResult(new Result(Status.ERROR,"失败!"));
		}
		return responseBody;
	}

	/**
	 * 供app端的接口
	 * 获取日程代分页模糊查询
	 * 
	 * 当前用户的 
	 */
	@ResponseBody
	@RequestMapping(value = "/queryscheduleList",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopUserSchedule> queryscheduleList(HttpServletRequest request,@RequestBody SopUserScheduleBo sopUserScheduleBo) {
		ResponseData<SopUserSchedule> responseBody = new ResponseData<SopUserSchedule>();
		User user = (User) getUserFromSession(request);		
		sopUserScheduleBo.setUserId(user.getId());
		try {
			
			Page<SopUserSchedule> pageList = sopUserScheduleService.queryPageList(sopUserScheduleBo, new PageRequest(sopUserScheduleBo.getPageNum(), sopUserScheduleBo.getPageSize()));
			responseBody.setPageList(pageList);
			return responseBody;
		} catch (PlatformException e) {
			responseBody.setResult(new Result(Status.ERROR, "queryscheduleList faild"));
			if (logger.isErrorEnabled()) {
				logger.error("queryscheduleList ", e);
			}
		}
		return responseBody;
		
		
	}
	
	/**
	 * 日程跳转页面
	 * @param request
	 * @date 2016-06-23
	 * @return
	 */
	@RequestMapping(value = "/popupMeetingList/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String popupMeetingList(@PathVariable("type") String type,HttpServletRequest request) {
		request.setAttribute("type", type);
		return "shedule/sheduleMeeting";
	}
	
	/**
	 * 日程表格
	 * @param request
	 * @param query
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sh", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingSchedulingBo> shedulingMeeting(HttpServletRequest request,@RequestBody MeetingScheduling query){
		ResponseData<MeetingSchedulingBo> responseBody = new ResponseData<MeetingSchedulingBo>();
		User user = (User) getUserFromSession(request);
		try{
			ProjectBo pb = new ProjectBo();
			pb.setCreateUid(user.getId());
			List<Project> projectList = projectService.queryList(pb);
			//组装项目的投资经理uid
			List<Long> proids = new ArrayList<Long>();
			List<String> departmentids = new ArrayList<String>();
			Map<Long,Project> projectProMap = new HashMap<Long,Project>();
			for(Project pr:projectList){
				proids.add(pr.getId());
				projectProMap.put(pr.getId(), pr);
				departmentids.add(String.valueOf(pr.getProjectDepartid()));
			}
			//若此人没有创建的项目则返回
			if(proids != null && proids.size() > 0){
				query.setProjectIdList(proids);
			}else{
				return responseBody;
			}
			//若前端传来排期类型为空
			if(StringUtils.isEmpty(query.getMeetingType())){
				return responseBody;
			}
			//获取排期数据
			List<MeetingSchedulingBo> mslist=meetingSchedulingService.meetingListByCondition(query);
			if(mslist.size() == 0){
				return responseBody;
			}
			
			//获取事业线数据
			Map<Long, Department> careerlineMap = new HashMap<Long, Department>();
			List<Department> careerlineList = departmentService.queryListById(departmentids);
			for(Department department : careerlineList){
				careerlineMap.put(department.getId(), department);
			}
			
			List<MeetingSchedulingBo> mbsList = new ArrayList<MeetingSchedulingBo>();
			//组装数据
			for(MeetingSchedulingBo ms : mslist){
				    Project p = projectProMap.get(ms.getProjectId());
				    if(p != null){
				    	ms.setProjectCode(p.getProjectCode());
						ms.setProjectName(p.getProjectName());
						ms.setProjectCareerline(careerlineMap.get(p.getProjectDepartid()).getName());
						ms.setCreateUname(p.getCreateUname());
						ms.setMeetingType(DictUtil.getMeetingType(ms.getMeetingType()));
						ms.setStart(DateUtil.convertDateToStringForChina(ms.getReserveTimeStart()));
						ms.setEnd(DateUtil.convertDateToStringForChina(ms.getReserveTimeEnd()));
		                ms.setTitle(p.getProjectName()+ms.getMeetingType());
		                mbsList.add(ms);
				    }
			}
			responseBody.setEntityList(mbsList);
		}catch(Exception e){
			logger.error("排期时间出错:"+e.getMessage());
		}
		return responseBody;
	}
	
	
	/**
	 * 高管-日程表格
	 * @param request
	 * @param query
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ceosh", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingSchedulingBo> shedulingCeoshMeeting(HttpServletRequest request,@RequestBody MeetingScheduling query){
		ResponseData<MeetingSchedulingBo> responseBody = new ResponseData<MeetingSchedulingBo>();
		
		if(query.getMeetingType().contains("meetingType:3") && query.getMeetingType().contains("meetingType:4")){
			query.setMeetingType(null);
		}
		
		List<MeetingSchedulingBo> mslist=meetingSchedulingService.meetingListByCondition(query);
		if(mslist.size() == 0){
			return responseBody;
		}
		List<String> ids = new ArrayList<String>();
		for(MeetingSchedulingBo ms : mslist){
			ids.add(String.valueOf(ms.getProjectId()));
		}
	
		ProjectBo pb = new ProjectBo();
		pb.setIds(ids);
		List<Project> projectList = projectService.queryList(pb);
		//组装项目的投资经理uid
		List<String> uids = new ArrayList<String>();
		List<String> departmentids = new ArrayList<String>();
		for(Project pr:projectList){
			uids.add(String.valueOf(pr.getCreateUid()));
			departmentids.add(String.valueOf(pr.getProjectDepartid()));
		}
		
		Map<Long, Department> careerlineMap = new HashMap<Long, Department>();
		List<Department> careerlineList = departmentService.queryListById(departmentids);
		for(Department department : careerlineList){
			careerlineMap.put(department.getId(), department);
		}
		
		//组装数据
		for(MeetingSchedulingBo ms : mslist){
			for(Project p : projectList){
				if(ms.getProjectId().longValue() == p.getId().longValue()){
					ms.setProjectCode(p.getProjectCode());
					ms.setProjectName(p.getProjectName());
					ms.setProjectCareerline(careerlineMap.get(p.getProjectDepartid()).getName());
					ms.setCreateUname(p.getCreateUname());
					ms.setMeetingType(DictUtil.getMeetingType(ms.getMeetingType()));
					ms.setStart(DateUtil.convertDateToStringForChina(ms.getReserveTimeStart()));
					ms.setEnd(DateUtil.convertDateToStringForChina(ms.getReserveTimeEnd()));
                    ms.setTitle(p.getProjectName()+ms.getMeetingType());
					
				}
				
			}
		}
		responseBody.setEntityList(mslist);
		return responseBody;
	}

	/**
	 * 获取会议总数
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/shedulingList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingScheduling> shedulingList(HttpServletRequest request,@RequestBody MeetingSchedulingBo query){
		
		ResponseData<MeetingScheduling> responseBody = new ResponseData<MeetingScheduling>();
		PageRequest pageable = new PageRequest(0, 10, Direction.DESC,"apply_time");
		Page<MeetingScheduling> pageEntity = new Page<MeetingScheduling>(null,pageable , null);
		List<MeetingScheduling> sl = new ArrayList<MeetingScheduling>();
		
		try {	
			User user = (User) getUserFromSession(request);
			/**
			 * 查询出所有的事业线
			 */
			
			String currentTime = DateUtil.refFormatNowDate();
			String start = currentTime + " 00:00:00";
			String end = currentTime + " 23:59:59";
			
			String meetingType=query.getMeetingType();
			String type = query.getType();
			
			if(!StringUtils.isEmpty(type) && "0".equals(type)){
				query.setReserveTimeStart(Timestamp.valueOf(start));
				query.setReserveTimeEnd(Timestamp.valueOf(end));
			}
			if(meetingType.contains("meetingType:3") && meetingType.contains("meetingType:4")){
				query.setMeetingType(null);
			}
			Page<MeetingScheduling> list=meetingSchedulingService.getMeetingList(query,new PageRequest(query.getPageNum(), query.getPageSize()));
			
			List<MeetingScheduling> schedulingList = list.getContent();
			if(schedulingList.size() == 0){
				return responseBody;
			}
			List<String> ids = new ArrayList<String>();
			for(MeetingScheduling ms : schedulingList){
				ids.add(String.valueOf(ms.getProjectId()));
			}
			/**
			 * 查询出相关的所有项目
			 */
			ProjectBo pb = new ProjectBo();
			pb.setIds(ids);
			List<Project> projectList = projectService.queryList(pb);
			//组装项目的投资经理uid
			List<String> uids = new ArrayList<String>();
			List<String> departmentids = new ArrayList<String>();
			for(Project pr:projectList){
				uids.add(String.valueOf(pr.getCreateUid()));
				departmentids.add(String.valueOf(pr.getProjectDepartid()));
			}
			
			Map<Long, Department> careerlineMap = new HashMap<Long, Department>();
			List<Department> careerlineList = departmentService.queryListById(departmentids);
			for(Department department : careerlineList){
				careerlineMap.put(department.getId(), department);
			}
			
			//组装数据
			for(MeetingScheduling ms : schedulingList){
				for(Project p : projectList){
					if(ms.getProjectId().longValue() == p.getId().longValue()){
						ms.setProjectCode(p.getProjectCode());
						ms.setProjectName(p.getProjectName());
						ms.setProjectCareerline(careerlineMap.get(p.getProjectDepartid()).getName());
						ms.setCreateUname(p.getCreateUname());
						ms.setStatus(DictUtil.getMeetingResult(ms.getScheduleStatus()));
						ms.setMeetingType(DictUtil.getMeetingType(ms.getMeetingType()));
					}
					
				}
			}
			pageEntity.setTotal(list.getTotal());
			pageEntity.setContent(list.getContent());
			responseBody.setPageList(pageEntity);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
		
		} catch (PlatformException e) {
			responseBody.setResult(new Result(Status.ERROR, null, "queryUserList faild"));
			
		}
		return null;
	}
	
	/**
	 * 公用弹出层页面，只提供路由功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/popupList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String popupList(HttpServletRequest request) {
	
		return "common/popupList";
	}
}
