package com.galaxyinternet.project.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.galaxyinternet.bo.touhou.ProjectHealthBo;
import com.galaxyinternet.common.annotation.RecordType;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.dictEnum.DictEnum;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.model.dict.Dict;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopDownLoad;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.touhou.Delivery;
import com.galaxyinternet.model.touhou.ProjectHealth;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DeliveryService;
import com.galaxyinternet.service.DictService;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.ProjectHealthService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.UserRoleService;
import com.galaxyinternet.touhou.controller.DeliveryController;
import com.galaxyinternet.utils.BatchUploadFile;

@Controller
@RequestMapping(value="/galaxy/project/postOperation")
public class PostOperatationController extends BaseControllerImpl<MeetingRecord, MeetingRecord> {

	final Logger logger = LoggerFactory.getLogger(PostOperatationController.class);
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
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private SopFileService sopFileService;
	@Autowired
	BatchUploadFile batchUpload;
	
	private String tempfilePath;

	public String getTempfilePath() {
		return tempfilePath;
	}

	@Value("${sop.oss.tempfile.path}")
	public void setTempfilePath(String tempfilePath) {
		this.tempfilePath = tempfilePath;
	}
	
	public static final String ERROR_DAO_EXCEPTION = "系统出现异常";
	
	@Override
	protected BaseService<MeetingRecord> getBaseService() {
		// TODO Auto-generated method stub
		return meetingService;
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
	@RequestMapping(value="queryPostMeeting", method=RequestMethod.POST ,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingRecord> queryPostMeeting(@RequestBody MeetingRecordBo meetingRecord){
		
		ResponseData<MeetingRecord> responseBody = new ResponseData<MeetingRecord>();
		try {
			PageRequest pageRequest = new PageRequest(meetingRecord.getPageNum(),
					meetingRecord.getPageSize());
			//运营分析 类型投后运营会议
			meetingRecord.setRecordType(RecordType.OPERATION_MEETING.getType());
			if(meetingRecord.getMeetingTypeList() == null || meetingRecord.getMeetingTypeList().size() <= 0){
				meetingRecord.setMeetingTypeNull("true");
			}
			Page<MeetingRecord> pageList = meetingService.queryPageList(meetingRecord, pageRequest);
			responseBody.setPageList(pageList);
		} catch (DaoException e) {
			// TODO: handle exception
			responseBody.setResult(new Result(Status.ERROR, ERROR_DAO_EXCEPTION));
		}
		return responseBody;	
	}
	
	
	/**
	 * 跳转编辑投后运营会议弹窗
	 * @return
	 */
	@RequestMapping(value="toEditPostMeeting", method=RequestMethod.GET)
	public String toEditPostMeeting(){
		return "project/dialog/editPostMeetingDialog";
	}
	
	/**
	 * 获取投后运营会议名称序号(存入result message中)
	 * @param meetingType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getMeetNumberByType/{projectId}/{meetingType}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingRecord> getMeetNumberByType(@PathVariable Long projectId,@PathVariable String meetingType){
		ResponseData<MeetingRecord> responseBody = new ResponseData<MeetingRecord>();
		if(projectId==null && projectId.equals(0) ){
			responseBody.setResult(new Result(Status.ERROR,"参数错误"));
			return responseBody;
		}
		if(StringUtils.isBlank(meetingType)){
			responseBody.setResult(new Result(Status.ERROR,"参数错误"));
			return responseBody;
		}
		try {
			MeetingRecord meetingRecord = new MeetingRecord();
			meetingRecord.setProjectId(projectId);
			meetingRecord.setMeetingType(meetingType);
			Long number = meetingService.queryMeetNumberByType(meetingRecord);
			Result result = new Result(Status.OK, "");
			result.setMessage(number);
					
			responseBody.setResult(result);
		} catch (Exception e) {
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
	@com.galaxyinternet.common.annotation.Logger()
	public ResponseData<MeetingRecord> saveMeeting(HttpServletRequest request, @RequestBody @Valid MeetingRecord meetingRecord){
		ResponseData<MeetingRecord> responseBody = getErrorResponse(meetingRecord);
		if(responseBody != null){
			return responseBody;
		}
		responseBody = new ResponseData<MeetingRecord>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if (user == null) {
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}
		try {
			Long projectId = meetingRecord.getProjectId();
			Project project = projectService.queryById(projectId);
			String projectName = project != null ? project.getProjectName() : null;
			String messageType = null;
			UrlNumber urlNumber = UrlNumber.one;
			if("postMeetingType:3".endsWith(meetingRecord.getMeetingType()))
			{
				messageType = "12.3";
			}
			else if("postMeetingType:3".endsWith(meetingRecord.getMeetingType()))
			{
				messageType = "12.2";
			}
			else
			{
				messageType = "12.1";
			}
			
			if(meetingRecord.getId() != null)
			{
				urlNumber = UrlNumber.two;
			}
			
			if(meetingRecord.getFileReidsKey() != null){
				ResponseData<SopFile> result = batchUpload.batchUpload(user.getId()+meetingRecord.getFileReidsKey());
				if(Status.OK.equals(result.getResult().getStatus())){
					List<SopFile> fileList = result.getEntityList();
					meetingRecord.setFiles(fileList);
				}
			}
			
					
			//设置meetingName
			if(meetingRecord.getMeetingName() == null || meetingRecord.getMeetingName().intValue()==0){
				Long meetNumber = meetingService.queryMeetNumberByType(meetingRecord);
				if(meetNumber==null){
					meetNumber = 0l;
				}
				meetingRecord.setMeetingName(meetNumber + 1);
			}
			//设置meetingValid
			meetingRecord.setMeetValid((byte) 0);
			//设置会议类型
			meetingRecord.setRecordType(RecordType.OPERATION_MEETING.getType());
			
			meetingService.saveMeeting(meetingRecord, user.getId());
			responseBody.setResult(new Result(Status.OK,"")); 
			responseBody.setId(meetingRecord.getId());
			ControllerUtils.setRequestParamsForMessageTip(request, user, projectName, projectId, messageType, urlNumber);	
		} catch (Exception e) {
			// TODO: handle exception
			responseBody.setResult(new Result(Status.ERROR, ERROR_DAO_EXCEPTION));
		}
		return responseBody;
	}
	
	@ResponseBody
	@RequestMapping(value="deletePostMeeting/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingRecord> deletePostMeeting(@PathVariable Long id){
		ResponseData<MeetingRecord> responseBody = new ResponseData<MeetingRecord>();
		if(id == null || id.intValue()==0){
			responseBody.setResult(new Result(Status.ERROR, "参数错误"));
			return responseBody;
		}
		try {
			meetingService.deletePostMeetingById(id);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (Exception e) {
			// TODO: handle exception
			responseBody.setResult(new Result(Status.ERROR, ERROR_DAO_EXCEPTION));
		}
		return responseBody;
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
	
	
	/**
	 * 获取文件列表
	 * @param id
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="/selectFile/{id}",method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingRecord> selectFile(@PathVariable("id") Long id, HttpServletRequest request)
	{
		ResponseData<MeetingRecord> data = new ResponseData<MeetingRecord>();
		MeetingRecord meeting = new MeetingRecord();
		try{
			if(id != null){
				SopFile sopfile = new SopFile();
			    sopfile.setMeetingId(id);
				List<SopFile> sopFileList = sopFileService.queryList(sopfile);
				meeting.setFiles(sopFileList);
				data.setEntity(meeting);
				data.setResult(new Result(Status.OK, ""));
			}else{
				data.setResult(new Result(Status.ERROR, "无操作数据!"));
			}
		}catch(Exception e){
			data.setResult(new Result(Status.ERROR, ERROR_DAO_EXCEPTION));
		}
		return data;
	}
	
	/**
	 * 批量下载
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/downloadBatchFile/{id}")
	public void downloadBatchFile(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response)
	{
		if(id != null){
			MeetingRecord meeting = meetingService.queryById(id);
			SopFile sopfile = new SopFile();
		    sopfile.setMeetingId(id);
			List<SopFile> sopFileList = sopFileService.queryList(sopfile);
			List<SopDownLoad> sopDownLoadList = new ArrayList<SopDownLoad>();
			try {
				if(sopFileList != null && sopFileList.size() > 0){
					for(SopFile file:sopFileList){
						SopDownLoad downloadEntity = new SopDownLoad();
						downloadEntity.setFileName(file.getFileName());
						downloadEntity.setFileSuffix("." + file.getFileSuffix());
						downloadEntity.setFileSize(file.getFileLength());
						downloadEntity.setFileKey(file.getFileKey());
						sopDownLoadList.add(downloadEntity);
					}
			
				}
				String meetingTypeStr = meeting.getMeetingTypeStr();
				sopFileService.downloadBatch(request, response, tempfilePath,meetingTypeStr+"纪要"+meeting.getMeetingName(),sopDownLoadList);
			} catch (Exception e) {
				logger.error("下载失败.",e);
			}
		}
	}
	

}
