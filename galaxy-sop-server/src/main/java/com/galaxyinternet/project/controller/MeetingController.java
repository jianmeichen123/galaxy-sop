package com.galaxyinternet.project.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.common.annotation.GalaxyResource;
import com.galaxyinternet.common.annotation.LogType;
import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.dictEnum.DictEnum.meetingType;
import com.galaxyinternet.common.dictEnum.DictEnum.projectProgress;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.file.UploadFileResult;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.framework.core.utils.JSONUtils;
import com.galaxyinternet.model.dict.Dict;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DictService;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;

@Controller
@RequestMapping("/galaxy/meeting")
public class MeetingController extends BaseControllerImpl<MeetingRecord, MeetingRecordBo>
{
	private static final Logger logger = LoggerFactory.getLogger(MeetingController.class);
	@Autowired
	private ProjectService projectService;
	@Autowired
	private DictService dictService;
	@Autowired
	private MeetingRecordService meetingRecordService;
	@Autowired
	private  SopFileService sopFileService;
	@Autowired
	private ProjectController projectController;
	@Value("${sop.oss.tempfile.path}")
	private String tempfilePath;

	@GalaxyResource(name="meetView")
	@ResponseBody
	@RequestMapping(value = "/queryMeet", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingRecordBo> queryMeet(HttpServletRequest request,@RequestBody MeetingRecordBo query ) {
		
		ResponseData<MeetingRecordBo> responseBody = new ResponseData<MeetingRecordBo>();
		
		try {
			Page<MeetingRecordBo> pageList = meetingRecordService.queryMeetPage(query, new PageRequest(query.getPageNum()==null?0:query.getPageNum(), query.getPageSize()==null?10:query.getPageSize()));
			responseBody.setPageList(pageList);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
			
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"查询失败"));
			
			if(logger.isErrorEnabled()){
				logger.error("queryMeet ",e);
			}
		}
		
		return responseBody;
	}
	/**
	 * 会议添加页面
	 */
	@RequestMapping(value = "/preAdd", method = RequestMethod.GET)
	public ModelAndView meetPreAdd()
	{
		ModelAndView mv = new ModelAndView("meeting/preAdd");
		List<Dict> dictList = dictService.selectByParentCode("meetingType");
		mv.addObject("meetingType", dictList);
		return mv;
	}
	/**
	 * 添加会议页面 - 搜索项目
	 * @param request
	 * @param project
	 * @return
	 */
	@GalaxyResource(name="meetingRecord_add")
	@ResponseBody
	@RequestMapping(value = "/searchProject", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Project> searchProject(HttpServletRequest request, @RequestBody ProjectBo project)
	{
		return projectController.searchProject(request, project);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView meetAdd(Long projectId, String type)
	{
		ModelAndView mv = new ModelAndView("meeting/error");
		Project project = projectService.queryById(projectId);
		String progress = project.getProjectProgress();
		String businessTypeCode = project.getBusinessTypeCode();
		if (!validMeetingType(progress, type, businessTypeCode))
		{
			mv.addObject("msg", "项目未到此阶段");
			return mv;
		}
		mv = new ModelAndView("meeting/add");
		mv.addObject("projectId", projectId);
		mv.addObject("meetingType", type);
		mv.addObject("meetingTypeDesc", meetingType.getNameByCode(type));

		String resultParentCode = "meetingResult";
		if (meetingType.会后商务谈判.getCode().equals(type))
		{
			resultParentCode = "meeting5Result";
		} else if (meetingType.投决会.getCode().equals(type))
		{
			resultParentCode = "meeting4Result";
		} else if (meetingType.立项会.getCode().equals(type))
		{
			resultParentCode = "meeting3Result";
		} else if (meetingType.内评会.getCode().equals(type))
		{
			resultParentCode = "meeting1Result";
		}
		// 会议结论
		List<Dict> meetingResultList = dictService.selectByParentCode(resultParentCode);
		mv.addObject("meetingResultList", meetingResultList);
		// 否决原因
		List<Dict> meetingVetoReason = dictService.selectByParentCode("meetingVetoReason");
		mv.addObject("meetingVetoReason", meetingVetoReason);
		// 待定原因
		List<Dict> meetingUndeterminedReason = dictService.selectByParentCode("meetingUndeterminedReason");
		mv.addObject("meetingUndeterminedReason", meetingUndeterminedReason);
		// 跟进中原因
		List<Dict> meetingFollowingReason = dictService.selectByParentCode("meetingFollowingReason");
		mv.addObject("meetingFollowingReason", meetingFollowingReason);
		return mv;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(Long id)
	{
		ModelAndView mv = new ModelAndView("meeting/error");
		MeetingRecord entity = meetingRecordService.queryById(id);
		if(entity == null)
		{
			mv.addObject("msg", "数据错误");
			return mv;
		}
		
		mv = meetAdd(entity.getProjectId(), entity.getMeetingType());
		mv.addObject("entity", entity);
		if(entity.getFileId() != null)
		{
			SopFile file  = sopFileService.queryById(entity.getFileId());
			mv.addObject("file", file);
		}
		return mv;
	}

	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG })
	@ResponseBody
	@RequestMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingRecord> save(MeetingRecordBo meetingRecord, HttpServletRequest request, HttpServletResponse response)
	{
		ResponseData<MeetingRecord> data = new ResponseData<>();
		try
		{
			User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			UrlNumber uNum = null;
			boolean isInsert = true;
			boolean isMultPart = ServletFileUpload.isMultipartContent(request);
			if(!isMultPart)
			{
				String json = JSONUtils.getBodyString(request);
				meetingRecord = GSONUtil.fromJson(json, MeetingRecordBo.class);
			}
			
			Project project = projectService.queryById(meetingRecord.getProjectId());
			
			if(meetingRecord.getId() == null)
			{
				meetingRecord.setCreateUid(user.getId());
				meetingRecordService.insert(meetingRecord);
			}
			else
			{
				isInsert = false;
				meetingRecordService.updateById(meetingRecord);
			}
			switch(meetingRecord.getMeetingType())
			{
		       case "meetingType:1":
		    	    if(isInsert)
		    	    	uNum = UrlNumber.one;
		    	    else 
		    	    	uNum = UrlNumber.two;
		    	    break;
		       case "meetingType:2":
		    	    if(isInsert)
		    	    	uNum = UrlNumber.three;
		    	    else 
		    	    	uNum = UrlNumber.four;
		    	    break;
		       case "meetingType:3":
		    	    if(isInsert)
		    	    	uNum = UrlNumber.five;
		    	    else 
		    	    	uNum = UrlNumber.six;
		    	    break;
		       case "meetingType:4":
		    	    if(isInsert)
		    	    	uNum = UrlNumber.nine;
		            else 
		    	    	uNum = UrlNumber.ten;
		    	    break;
		       case "meetingType:5":
		    	    if(isInsert)
		    	    	uNum = UrlNumber.seven;
		            else 
		    	    	uNum = UrlNumber.eight;
		    	    break;
		    	default :
		    		 break;
			}
			
			if (isMultPart)
			{
				String fileKey = String.valueOf(IdGenerator.generateId(OSSHelper.class));
				UploadFileResult result = uploadFileToOSS(request, fileKey, tempfilePath);
				if (result != null && result.getResult().getStatus().equals(Result.Status.OK))
				{
					SopFile sopFile = new SopFile();
					sopFile.setBucketName(result.getBucketName());
					sopFile.setFileKey(fileKey);
					sopFile.setFileLength(result.getContentLength());
					sopFile.setFileName(result.getFileName());
					sopFile.setFileSuffix(result.getFileSuffix());
					sopFile.setProjectId(meetingRecord.getProjectId());
					sopFile.setProjectProgress(project.getProjectProgress());
					sopFile.setFileUid(user.getId());	 //上传人
					sopFile.setCareerLine(user.getDepartmentId());
					sopFile.setFileType(DictEnum.fileType.音频文件.getCode());   //存储类型
					sopFile.setFileSource(DictEnum.fileSource.内部.getCode());  //档案来源
					sopFile.setFileStatus(DictEnum.fileStatus.已上传.getCode());  //档案状态
					sopFile.setCreatedTime(new Date().getTime());
					meetingRecordService.operateFlowMeeting(sopFile,meetingRecord);
				}
			}
			ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId(),uNum);
		} catch (Exception e)
		{
			data.setResult(new Result(Status.ERROR,null, "会议添加失败"));
			if(logger.isErrorEnabled()){
				logger.error("addfilemeet 会议添加失败 ",e);
			}
		}

		return data;
	}
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG })
	@ResponseBody
	@RequestMapping(value = "/del", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingRecord>del(HttpServletRequest request, Long id)
	{
		ResponseData<MeetingRecord> data = new ResponseData<>();
		try
		{
			MeetingRecord po = meetingRecordService.queryById(id);
			if(po != null)
			{
				UrlNumber uNum = null;
				switch(po.getMeetingType())
				{
			       case "meetingType:1":
		    	    	uNum = UrlNumber.one;
			    	    break;
			       case "meetingType:2":
		    	    	uNum = UrlNumber.two;
			    	    break;
			       case "meetingType:3":
			    	    	uNum = UrlNumber.three;
			    	    break;
			       case "meetingType:4":
		    	    	uNum = UrlNumber.four;
			    	    break;
			       case "meetingType:5":
		    	    	uNum = UrlNumber.five;
			    	    break;
			    	default :
			    		 break;
				}
				Project project = projectService.queryById(po.getProjectId());
				meetingRecordService.deleteById(id);
				ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId(),uNum);
			}
		} catch (Exception e)
		{
			logger.error("删除失败，id="+id,e);
			data.getResult().addError("删除失败");
		}
		return data;
	}
	/**
	 * 根据项目阶段验证是否能添加会议到项目 接触访谈 -> 内部评审 -> CEO评审 -> 立项会 -> 会后商务谈判 -> 投资意向书（投资） ->
	 * 尽职调查 -> 投决会 -> 投资协议 -> 股权交割 接触访谈 -> 内部评审 -> CEO评审 -> 立项会 -> 会后商务谈判 ->
	 * 投资协议（闪投） -> 尽职调查 -> 投决会 -> 股权交割
	 * 
	 * @return
	 */
	private boolean validMeetingType(String progress, String type, String businessTypeCode)
	{
		if (projectProgress.投后运营.getCode().equals(progress) || projectProgress.股权交割.getCode().equals(progress) || projectProgress.投资决策会.getCode().equals(progress))
		{
			return true;
		}
		if (meetingType.投决会.getCode().equals(type))
		{
			if (SopConstant.BUSINESS_TYPE_TZ.equals(businessTypeCode) && projectProgress.投资协议.getCode().equals(progress))
			{
				return true;
			}
		} else if (meetingType.会后商务谈判.getCode().equals(type))
		{
			if (projectProgress.会后商务谈判.getCode().equals(progress) || projectProgress.投资意向书.getCode().equals(progress) || projectProgress.尽职调查.getCode().equals(progress)
					|| projectProgress.尽职调查.getCode().equals(progress) || projectProgress.投资协议.getCode().equals(progress))
			{
				return true;
			}
		} else if (meetingType.立项会.getCode().equals(type))
		{
			if (!projectProgress.接触访谈.getCode().equals(progress) && !projectProgress.内部评审.getCode().equals(progress) && !projectProgress.CEO评审.getCode().equals(progress))
			{
				return true;
			}
		} else if (meetingType.CEO评审.getCode().equals(type))
		{
			if (!projectProgress.接触访谈.getCode().equals(progress) && !projectProgress.内部评审.getCode().equals(progress))
			{
				return true;
			}
		} else if (meetingType.内评会.getCode().equals(type))
		{
			if (!projectProgress.接触访谈.getCode().equals(progress))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	protected BaseService<MeetingRecord> getBaseService()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
