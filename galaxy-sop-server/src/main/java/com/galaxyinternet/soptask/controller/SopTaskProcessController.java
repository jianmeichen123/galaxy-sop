package com.galaxyinternet.soptask.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.galaxyinternet.bo.SopTaskBo;
import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.dictEnum.DictEnum;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.sopfile.SopVoucherFile;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.model.user.UserRole;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.SopTaskService;
import com.galaxyinternet.service.SopVoucherFileService;
import com.galaxyinternet.service.UserRoleService;
import com.galaxyinternet.service.UserService;


@Controller
@RequestMapping("/galaxy/taskprocess")
public class SopTaskProcessController extends BaseControllerImpl<SopTask, SopTaskBo> 
{
	private final Logger logger = LoggerFactory.getLogger(SopTaskProcessController.class);
	@Autowired
	private SopTaskService sopTaskService;
	@Autowired
	private SopFileService sopFileService;
	@Autowired
	private SopVoucherFileService sopVoucherFileService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private DepartmentService departmentService;
	@Override
	protected BaseService<SopTask> getBaseService() {
		return sopTaskService;
	}
	
	@RequestMapping("/showFileList")
	public ModelAndView showFileList(@RequestParam Integer taskFlag)
	{
		ModelAndView mv = new ModelAndView();
		String viewName = "";
		String btnTxt = "";
		String fileWorktype = "";
		switch(taskFlag)
		{
			case 0: //完善简历
				viewName = "/taskProcess/wsjl";
				break;
			case 1 : //表示投资意向书
				fileWorktype = "fileWorktype:5";
				viewName = "/taskProcess/tzyxs";
				break;
			case 2 : //人事尽职调查报告
				btnTxt = "上传尽调报告";
				fileWorktype = "fileWorktype:2";
				viewName = "/taskProcess/singleFileUpload";
				break;
			case 3 : //法务尽职调查报告
				btnTxt = "上传尽调报告";
				fileWorktype = "fileWorktype:3";
				viewName = "/taskProcess/singleFileUpload";
				break;
			case 4 : //财务尽调报告
				btnTxt = "上传尽调报告";
				fileWorktype = "fileWorktype:4";
				viewName = "/taskProcess/singleFileUpload";
				break;
			case 5 : //业务尽调报告
				viewName = "/taskProcess/ywjd";
				break;
			case 6 : //投资协议
			case 7 : //股权转让协议
				viewName = "/taskProcess/tzxy";
				break;
			case 8 : //资金拨付凭证
				btnTxt = "上传资金拨付凭证";
				fileWorktype = "fileWorktype:9";
				viewName = "/taskProcess/singleFileUpload";
				break;
			case 9 : //工商变更登记凭证
				btnTxt = "上传工商变更登记凭证";
				fileWorktype = "fileWorktype:8";
				viewName = "/taskProcess/singleFileUpload";
				break;
			default :
				logger.error("Error taskFlag "+ taskFlag);
		}
		mv.setViewName(viewName);
		mv.addObject("fileWorktype", fileWorktype);
		mv.addObject("btnTxt", btnTxt);
		mv.addObject("taskFlag", taskFlag);
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/uploadFile")
	public Result uploadFile(SopFile bo, HttpServletRequest request)
	{
		Result result = new Result();
		try {
			MultipartFile file = null;
			if(request instanceof MultipartHttpServletRequest)
			{
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				file = multipartRequest.getFile("file");
			}
			if(file != null)
			{
				String fileName = file.getOriginalFilename();
				int dotPos = fileName.lastIndexOf(".");
				String key = String.valueOf(IdGenerator.generateId(OSSHelper.class));
				String ext = fileName.substring(dotPos);
				File temp = File.createTempFile(key, ext);
				Long length = temp.length();
				file.transferTo(temp);
				OSSHelper.simpleUploadByOSS(temp,key);
				
				bo.setFileKey(key);
				bo.setFileLength(length);
				bo.setFileName(fileName);
				bo.setUpdatedTime(System.currentTimeMillis());
				bo.setFileStatus(DictEnum.fileStatus.已上传.getCode());
			}
			sopFileService.updateById(bo);
			result.setStatus(Status.OK);
			
		} catch (Exception e) {
			Object msg = "上传失败";
			result.addError(msg);
			logger.error(msg.toString(),e);
		}
		
		return result;
	}
	@ResponseBody
	@RequestMapping("/uploadVoucher")
	public Result uploadVoucher(SopVoucherFile bo, HttpServletRequest request)
	{
		Result result = new Result();
		try {
			MultipartFile file = null;
			if(request instanceof MultipartHttpServletRequest)
			{
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				file = multipartRequest.getFile("file");
			}
			if(file != null)
			{
				String fileName = file.getOriginalFilename();
				int dotPos = fileName.lastIndexOf(".");
				String key = String.valueOf(IdGenerator.generateId(OSSHelper.class));
				String ext = fileName.substring(dotPos);
				File temp = File.createTempFile(key, ext);
				Long length = temp.length();
				file.transferTo(temp);
				OSSHelper.simpleUploadByOSS(temp,key);
				
				bo.setFileKey(key);
				bo.setFileLength(length);
				bo.setFileName(fileName);
				bo.setUpdatedTime(System.currentTimeMillis());
				bo.setFileStatus(DictEnum.fileStatus.已上传.getCode());
			}
			sopVoucherFileService.updateById(bo);
			result.setStatus(Status.OK);
			
		} catch (Exception e) {
			Object msg = "上传失败";
			result.addError(msg);
			logger.error(msg.toString(),e);
		}
		
		return result;
	}

	/**
	 * 文档上传任务催办
	 * @param id 传入sop_file.id；
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/taskUrged", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<User> taskUrged(Long id)
	{
		ResponseData<User> resp = new ResponseData<User>();
		try {
			SopTask task = sopTaskService.getByFileInfo(id);
			if(task == null)
			{
				logger.error("No task fount. file id = "+id);
				resp.getResult().addError("请求参数错误");
				return resp;
			}
			User user = null;
			if(task.getAssignUid() != null) //已认领的任务 - 认领人
			{
				user = userService.queryById(task.getAssignUid());
			}
			else if(task.getDepartmentId() != null) //待认领的任务 - 部门总监
			{
				
				Long roleId = null;
				if(task.getDepartmentId().longValue() == SopConstant.DEPARTMENT_RS_ID)
				{
					roleId = UserConstant.HRZJ;
				}
				else if(task.getDepartmentId().longValue() == SopConstant.DEPARTMENT_CW_ID)
				{
					roleId = UserConstant.CWZJ;
				}
				else if(task.getDepartmentId().longValue() == SopConstant.DEPARTMENT_FW_ID)
				{
					roleId = UserConstant.FWZJ;
				}
				if(roleId != null)
				{
					UserRole urQuery = new UserRole();
					urQuery.setRoleId(roleId);
					List<UserRole> urList = userRoleService.queryList(urQuery);
					if(urList != null && urList.size() >0)
					{
						UserRole ur = urList.iterator().next();
						if(ur.getUserId() != null)
						{
							user = userService.queryById(ur.getUserId());
						}
						
					}
				}
			}
			if(user == null)
			{
				logger.error("No user fount. file id = "+id);
				resp.getResult().addError("请求参数错误");
				return resp;
			}
			resp.setEntity(user);
			
		} catch (Exception e) {
			String msg = "任务催办失败";
			logger.error(msg+". file id = "+id,e);
			resp.getResult().addError(e.getMessage());
		}
		
		return resp;
	}
}
