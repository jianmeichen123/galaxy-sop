package com.galaxyinternet.chart.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.galaxyinternet.bo.chart.ChartBo;
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
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.MeetingSchedulingService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.soptask.controller.SopUserScheduleController;

@Controller
@RequestMapping("/galaxy/meetingShe")
public class MeetingSheduleController extends BaseControllerImpl<MeetingScheduling, MeetingSchedulingBo>{

	final Logger logger = LoggerFactory
			.getLogger(MeetingSheduleController.class);
	
	@Autowired
	private MeetingSchedulingService meetingSchedulingService;
	
    @Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private ProjectService projectService;
	
	
	@Override
	protected BaseService<MeetingScheduling> getBaseService() {
		// TODO Auto-generated method stub
		return this.meetingSchedulingService;
	}
	
	/**
	 * 获取会议总数
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sheduling", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingSchedulingBo> sheduling(HttpServletRequest request,@RequestBody ChartBo chartBo){
		ResponseData<MeetingSchedulingBo> responseBody = new ResponseData<MeetingSchedulingBo>();
		MeetingSchedulingBo bo=new MeetingSchedulingBo();
		try{
			MeetingSchedulingBo mbo = meetingSchedulingService.getMeetingScheduling(bo);
			responseBody.setEntity(mbo);
		}catch(Exception e){
			responseBody.setResult(new Result(Status.ERROR, null, "失败!"));
		}
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
			Page<MeetingScheduling> list=meetingSchedulingService.getMeetingListForReport(query,new PageRequest(query.getPageNum(), query.getPageSize()));
			
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
	 * 日程表格
	 * @param request
	 * @param query
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sh", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingSchedulingBo> shedulingMeeting(HttpServletRequest request,@RequestBody MeetingScheduling query){
		ResponseData<MeetingSchedulingBo> responseBody = new ResponseData<MeetingSchedulingBo>();
		
		if(query.getMeetingType().contains("meetingType:3") && query.getMeetingType().contains("meetingType:4")){
			query.setMeetingType(null);
		}
		try{
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
		}catch(Exception e){
			logger.error("ceo获取排期日程错误:"+e.getMessage());
		}
		return responseBody;
	}

}
