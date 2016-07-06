package com.galaxyinternet.project.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.bo.touhou.ProjectHealthBo;
import com.galaxyinternet.common.annotation.RecordType;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.model.dict.Dict;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.touhou.Delivery;
import com.galaxyinternet.model.touhou.ProjectHealth;
import com.galaxyinternet.service.DeliveryService;
import com.galaxyinternet.service.DictService;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.ProjectHealthService;
import com.galaxyinternet.service.ProjectService;

@Controller
@RequestMapping(value="/galaxy/project/postOperation")
public class PostOperatationController extends BaseControllerImpl<Project, ProjectBo> {

	@Autowired
	private ProjectService projectService;
	@Autowired
	private MeetingRecordService meetingService;
	@Autowired
	private ProjectHealthService projectHealthService;
	@Autowired
	private DeliveryService deliveryService;
	@Autowired
	private DictService dictService;
	
	public static final String ERROR_DAO_EXCEPTION = "系统出现异常";
	
	@Override
	protected BaseService<Project> getBaseService() {
		// TODO Auto-generated method stub
		return projectService;
	}
	
	
	/**
	 * 根据parentCode获取字典信息
	 * @param parentCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getDictByParent/{parentCode}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Dict> getDictByParent(@PathVariable String parentCode){
		ResponseData<Dict> responseBody = new ResponseData<Dict>();
		if(StringUtils.isBlank(parentCode)){
			responseBody.setResult(new Result(Status.ERROR,"参数错误"));
		}
		try {
			List<Dict> dictList = dictService.selectByParentCode(parentCode);
			responseBody.setEntityList(dictList);
			responseBody.setResult(new Result(Status.OK, ""));
//			response
		} catch (DaoException e) {
			// TODO: handle exception
			responseBody.setResult(new Result(Status.ERROR, ERROR_DAO_EXCEPTION));
		}
		return responseBody;
	}
	
	/**
	 * 运营分析跳转
	 * @param request
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="toPostMeeting/{pid}",method=RequestMethod.GET)
	public String toPostMeeting(HttpServletRequest request,@PathVariable Long pid){
		Project project = new Project();
		project = projectService.queryById(pid);
		request.setAttribute("proinfo", GSONUtil.toJson(project));
		request.setAttribute("projectId", pid);
		request.setAttribute("pid", pid);
		request.setAttribute("prograss", project.getProjectProgress());
		request.setAttribute("projectName", project.getProjectName());
		return "project/sopinfo/tab_postMeetingAnlysis";
	}
	/**
	 * 分页查询透后运营会议记录
	 * @param meetingRecord
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="queryMeeting", method=RequestMethod.POST ,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingRecord> queryMeeting(@RequestBody MeetingRecordBo meetingRecord){
		
		ResponseData<MeetingRecord> responseBody = new ResponseData<MeetingRecord>();
		try {
			PageRequest pageRequest = new PageRequest(meetingRecord.getPageNum(),
					meetingRecord.getPageSize());
			//运营分析 类型投后运营会议
			meetingRecord.setRecordType(RecordType.OPERATION_MEETING.getType());
			Page<MeetingRecord> pageList = meetingService.queryPageList(meetingRecord, pageRequest);
			responseBody.setPageList(pageList);
		} catch (DaoException e) {
			// TODO: handle exception
			responseBody.setResult(new Result(Status.ERROR, ERROR_DAO_EXCEPTION));
		}
		return responseBody;	
	}
	
	/**
	 * 新建编辑投后会议记录
	 * @param request
	 * @param meetingRecord
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="saveMeeting", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingRecord> saveMeeting(HttpServletRequest request, @RequestBody MeetingRecord meetingRecord){
		return null;
	}
	/**
	 * 投后运营信息
	 * @param request
	 * @param meetingRecord
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getThyyInfo")
	public ResponseData<MeetingRecord> getThyyInfo(Long projectId)
	{
		
		ResponseData<MeetingRecord> data = new ResponseData<MeetingRecord>();
		
		//运营状态-默认为正常(2)
		Byte healthState = (byte)2;
		ProjectHealthBo healthQuery = new ProjectHealthBo();
		healthQuery.setProjectId(projectId);
		PageRequest healthPageable = new PageRequest(0,1, new Sort(Direction.DESC,"created_time"));
		List<ProjectHealth> healthList = projectHealthService.queryList(healthQuery, healthPageable);
		if(healthList != null && healthList.size() >0)
		{
			ProjectHealth health = healthList.iterator().next();
			if(health != null && health.getHealthState() != null)
			{
				healthState = health.getHealthState();
			}
		}
		data.putAttachmentItem("healthState", healthState);
		
		//交割前事项
		Delivery deliveryQuery = new Delivery();
		deliveryQuery.setProjectId(projectId);
		Long total = deliveryService.queryCount(deliveryQuery);
		deliveryQuery.setDelStatus((byte) 1);
		Long complete = deliveryService.queryCount(deliveryQuery);
		
		data.putAttachmentItem("total",total);
		data.putAttachmentItem("complete",complete);
		
		//运营会议
		PageRequest pageable = new PageRequest();
		pageable = new PageRequest(0, 3);
		MeetingRecord meetQuery = new MeetingRecord();
		meetQuery.setProjectId(projectId);
		meetQuery.setRecordType(RecordType.OPERATION_MEETING.getType());
		List<MeetingRecord> list = meetingService.queryList(meetQuery,pageable);
		data.setEntityList(list);
		
		return data;
	}

}
