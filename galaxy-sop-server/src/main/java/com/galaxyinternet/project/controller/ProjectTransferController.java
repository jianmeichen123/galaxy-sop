package com.galaxyinternet.project.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.project.ProjectTransferBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.project.ProjectTransfer;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.ProjectTransferService;
import com.galaxyinternet.utils.SopConstatnts;

@Controller
@RequestMapping("/galaxy/projectTransfer")
public class ProjectTransferController extends BaseControllerImpl<ProjectTransfer, ProjectTransferBo> {
	private final static Logger _common_logger_ = LoggerFactory.getLogger(ProjectTransferController.class);
	
    @Autowired
	com.galaxyinternet.framework.cache.Cache cache;

	@Autowired
	private ProjectTransferService projectTransferService;
	
	@Autowired
	private ProjectService projectService;
	
	@Override
	protected BaseService<ProjectTransfer> getBaseService() {
		return this.projectTransferService;
	}
	
	
	/**
	 * 页面跳转至移交项目
	 */
	@RequestMapping(value = "/toProjectTransfer", method = RequestMethod.GET)
	public String toProjectTransfer() {
		return "project/projectTransfer/project_transfer";
	}
	
	/**
	 * 页面跳转至撤销项目页面
	 */
	@RequestMapping(value = "/toRevokeProTransfer", method = RequestMethod.GET)
	public String toRevokeProTransfer() {
		return "project/projectTransfer/revoke_transfer";
	}
	/**
	 * 页面跳转至撤销项目页面
	 */
	@RequestMapping(value = "/toReciviceTransfer", method = RequestMethod.GET)
	public String toReciviceTransfer() {
		return "project/projectTransfer/receiveTask";
	}
	
	
	/**
	 * 项目移交
	 */
	@ResponseBody
	@RequestMapping(value = "/applyTransfer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectTransfer> applyTransfer(@RequestBody ProjectTransfer projectTransfer,
			HttpServletRequest request) {
		ResponseData<ProjectTransfer> responseBody = new ResponseData<ProjectTransfer>();
		if(projectTransfer.getProjectId() == null 
				|| projectTransfer.getAfterDepartmentId() == null 
				|| projectTransfer.getAfterUid() == null){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		try {
			Project project = projectService.queryById(projectTransfer.getProjectId());
			if(project == null){
				responseBody.setResult(new Result(Status.ERROR,"csds" , "未找到被移交的项目!"));
				return responseBody;
			}
			User user = (User) getUserFromSession(request);
			projectTransfer.setBeforeUid(user.getId());
			projectTransfer.setBeforeDepartmentId(user.getDepartmentId());
		    Long result=	projectTransferService.applyProjectTransfer(projectTransfer);
			projectTransferService.setTransferProjectInRedis(cache, project.getId());
			
			List<Long> ids = (List<Long>) cache.get(SopConstatnts.Redis._transfer_projects_key_);
			for(Long id : ids){
				System.out.println(">>>>>>" + id);
			}
			responseBody.setId(result);
			responseBody.setResult(new Result(Status.OK,"200" , "项目移交成功!"));
			_common_logger_.info(user.getRealName() + "移交项目成功[json]-" + projectTransfer);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,"err" , "项目移交失败!"));
			if(_common_logger_.isErrorEnabled()){
				_common_logger_.error("移交项目失败[josn]-" + projectTransfer);
			}
		}
		return responseBody;
	}
	
	
	/**
	 * 撤销项目移交
	 */
	@ResponseBody
	@RequestMapping(value = "/undoTransfer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectTransfer> undoTransfer(@RequestBody ProjectTransfer projectTransfer,
			HttpServletRequest request) {
		ResponseData<ProjectTransfer> responseBody = new ResponseData<ProjectTransfer>();
		if(projectTransfer.getProjectId() == null || projectTransfer.getUndoReason() == null){
			responseBody.setResult(new Result(Status.ERROR,"err" , "缺少必需参数!"));
			return responseBody;
		}
		try {
			User user = (User) getUserFromSession(request);
			List<ProjectTransfer> datas = projectTransferService.applyTransferData(projectTransfer.getProjectId());
			if(datas == null || datas.isEmpty()){
				responseBody.setResult(new Result(Status.ERROR,"err" , "没有找到相关的移交申请记录!"));
				return responseBody;
			}
			ProjectTransfer transfer = datas.get(0);
			transfer.setRecordStatus(SopConstatnts.TransferStatus._undo_status_);
			transfer.setUndoReason(projectTransfer.getUndoReason());
			projectTransferService.undoProjectTransfer(transfer);
			projectTransferService.removeTransferProjectFromRedis(cache, transfer.getProjectId());
			
			List<Long> ids = (List<Long>) cache.get(SopConstatnts.Redis._transfer_projects_key_);
			for(Long id : ids){
				System.out.println(">>>>>>" + id);
			}
			
			responseBody.setResult(new Result(Status.ERROR,"200" , "移交申请撤销成功!"));
			_common_logger_.info(user.getRealName() + "撤销移交申请成功[json]-" + transfer);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,"err" , "撤销移交申请失败!"));
			if(_common_logger_.isErrorEnabled()){
				_common_logger_.error("撤销移交申请失败[josn]-" + projectTransfer);
			}
		}
		return responseBody;
	}
	
	
	/**
	 * 拒接项目移交
	 */
	@ResponseBody
	@RequestMapping(value = "/rejectTransfer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectTransfer> rejectTransfer(@RequestBody ProjectTransfer projectTransfer,
			HttpServletRequest request) {
		ResponseData<ProjectTransfer> responseBody = new ResponseData<ProjectTransfer>();
		if(projectTransfer.getProjectId() == null || projectTransfer.getRefuseReason() == null){
			responseBody.setResult(new Result(Status.ERROR,"err" , "缺少必需参数!"));
			return responseBody;
		}
		try {
			User user = (User) getUserFromSession(request);
			List<ProjectTransfer> datas = projectTransferService.applyTransferData(projectTransfer.getProjectId());
			if(datas == null || datas.isEmpty()){
				responseBody.setResult(new Result(Status.ERROR,"err" , "没有找到相关的移交申请记录!"));
				return responseBody;
			}
			ProjectTransfer transfer = datas.get(0);
			transfer.setRecordStatus(SopConstatnts.TransferStatus._reject_status_);
			transfer.setRefuseReason(projectTransfer.getRefuseReason());
			projectTransferService.rejectProjectTransfer(transfer);
			projectTransferService.removeTransferProjectFromRedis(cache, transfer.getProjectId());
			
			List<Long> ids = (List<Long>) cache.get(SopConstatnts.Redis._transfer_projects_key_);
			for(Long id : ids){
				System.out.println(">>>>>>" + id);
			}
			
			responseBody.setResult(new Result(Status.ERROR,"200" , "拒接项目成功!"));
			_common_logger_.info(user.getRealName() + "拒接项目成功[json]-" + transfer);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,"err" , "拒接项目失败!"));
			if(_common_logger_.isErrorEnabled()){
				_common_logger_.error("拒接项目失败[josn]-" + projectTransfer);
			}
		}
		return responseBody;
	}
	
	
	
	/**
	 * 接收项目移交
	 */
	@ResponseBody
	@RequestMapping(value = "/receiveTransfer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<ProjectTransfer> receiveTransfer(@RequestBody ProjectTransfer projectTransfer,
			HttpServletRequest request) {
		ResponseData<ProjectTransfer> responseBody = new ResponseData<ProjectTransfer>();
		if(projectTransfer.getProjectId() == null){
			responseBody.setResult(new Result(Status.ERROR,"err" , "缺少必需参数!"));
			return responseBody;
		}
		try {
			User user = (User) getUserFromSession(request);
			List<ProjectTransfer> datas = projectTransferService.applyTransferData(projectTransfer.getProjectId());
			if(datas == null || datas.isEmpty()){
				responseBody.setResult(new Result(Status.ERROR,"err" , "没有找到相关的移交申请记录!"));
				return responseBody;
			}
			ProjectTransfer transfer = datas.get(0);
			transfer.setRecordStatus(SopConstatnts.TransferStatus._receive_status_);
			projectTransferService.receiveProjectTransfer(transfer, transfer.getAfterUid(), user.getRealName(), transfer.getAfterDepartmentId());
			projectTransferService.removeTransferProjectFromRedis(cache, transfer.getProjectId());
			
			List<Long> ids = (List<Long>) cache.get(SopConstatnts.Redis._transfer_projects_key_);
			for(Long id : ids){
				System.out.println(">>>>>>" + id);
			}
			
			responseBody.setResult(new Result(Status.ERROR,"200" , "接收项目成功!"));
			_common_logger_.info(user.getRealName() + "接收项目成功[json]-" + transfer);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,"err" , "接收项目失败!"));
			if(_common_logger_.isErrorEnabled()){
				_common_logger_.error("接收项目失败[josn]-" + projectTransfer);
			}
		}
		return responseBody;
	}
	
	
	
}
