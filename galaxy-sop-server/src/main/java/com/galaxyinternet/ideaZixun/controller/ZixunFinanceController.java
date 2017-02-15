package com.galaxyinternet.ideaZixun.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.IdeaZixunBo;
import com.galaxyinternet.bo.ZixunFinanceBo;
import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.bo.project.MeetingRecordBo;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.common.annotation.LogType;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.file.UploadFileResult;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.oss.OSSFactory;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.framework.core.utils.JSONUtils;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.idea.IdeaZixun;
import com.galaxyinternet.model.idea.ZixunFinance;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.IdeaZixunService;
import com.galaxyinternet.service.ZixunFinanceService;

@Controller
@RequestMapping("/galaxy/zixunFinance")
public class ZixunFinanceController extends BaseControllerImpl<ZixunFinance, ZixunFinanceBo> {
	 
	final Logger logger = LoggerFactory.getLogger(ZixunFinanceController.class);
	
	@Autowired
	private ZixunFinanceService zixunFinanceService;
	
	@Autowired
	private IdeaZixunService ideaZixunService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	@Override
	protected BaseService<ZixunFinance> getBaseService() {
		return this.zixunFinanceService;
	}
	
	
	
	/**
	 * 资讯融资  添加   页面
	 */
	@RequestMapping(value = "/add")
	public String rzAdd() {
		return "idea/zixun/rz_add";
	}
	
	/**
	 * 资讯融资  编辑   页面
	 */
	@RequestMapping(value = "/edit")
	public String rzEdit() {
		return "idea/zixun/rz_edit";
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/addRz",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@com.galaxyinternet.common.annotation.Logger(operationScope = {LogType.MESSAGE},recordType=com.galaxyinternet.common.annotation.RecordType.IDEAZIXUN)
	public ResponseData<ZixunFinance> addRz(HttpServletRequest request,@RequestBody ZixunFinance rz){
		ResponseData<ZixunFinance> responseBody = new ResponseData<ZixunFinance>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if (user == null) {
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}
		try{
			rz.setId(null);
			rz.setCreateUid(user.getId());
			rz.setCreatedTime(System.currentTimeMillis());
			rz.setUpdatedUid(user.getId());
			rz.setUpdatedTime(System.currentTimeMillis());
			Long id = zixunFinanceService.insert(rz);
			
			responseBody.setId(id);
			responseBody.setResult(new Result(Status.OK,null,"添加成功"));
			
			IdeaZixun zxQ = ideaZixunService.queryById(rz.getZixunId());
			ControllerUtils.setRequestParamsForMessageTip(request, zxQ.getCode(),zxQ.getId(),"18");
		}catch(Exception e){
			responseBody.setResult(new Result(Status.ERROR,null,"添加失败!"));
			logger.error("addRz 添加失败",e);
		}
		
		return responseBody;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/editRz",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@com.galaxyinternet.common.annotation.Logger(operationScope = {LogType.MESSAGE},recordType=com.galaxyinternet.common.annotation.RecordType.IDEAZIXUN)
	public ResponseData<ZixunFinance> editRz(HttpServletRequest request,@RequestBody ZixunFinance rz){
		ResponseData<ZixunFinance> responseBody = new ResponseData<ZixunFinance>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if (user == null) {
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}
		try{
			rz.setUpdatedUid(user.getId());
			rz.setUpdatedTime(System.currentTimeMillis());
			zixunFinanceService.updateById(rz);
			
			responseBody.setResult(new Result(Status.OK,null,"修改成功"));
			
			IdeaZixun zxQ = ideaZixunService.queryById(rz.getZixunId());
			ControllerUtils.setRequestParamsForMessageTip(request, zxQ.getCode(),zxQ.getId(),"18");
		}catch(Exception e){
			responseBody.setResult(new Result(Status.ERROR,null,"修改失败!"));
			logger.error("editRz 修改失败",e);
		}
		
		return responseBody;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/delRz/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	@com.galaxyinternet.common.annotation.Logger(operationScope = {LogType.MESSAGE},recordType=com.galaxyinternet.common.annotation.RecordType.IDEAZIXUN)
	public ResponseData<ZixunFinance> delRz(@PathVariable("id") Long id,HttpServletRequest request){
		ResponseData<ZixunFinance> responseBody = new ResponseData<ZixunFinance>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if (user == null) {
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}
		try{
			
			ZixunFinance rz = new ZixunFinance();
			rz.setId(id);
			rz.setStatus((byte) 1);
			zixunFinanceService.updateById(rz);
			
			//zixunFinanceService.deleteById(id);
			responseBody.setResult(new Result(Status.OK,null,"删除成功"));
			
			
			ZixunFinance rzq = zixunFinanceService.queryById(id);
			IdeaZixun zxQ = ideaZixunService.queryById(rzq.getZixunId());
			ControllerUtils.setRequestParamsForMessageTip(request, zxQ.getCode(),zxQ.getId(),"18");
		}catch(Exception e){
			responseBody.setResult(new Result(Status.ERROR,null,"删除失败!"));
			logger.error("delRz 删除失败",e);
		}
		
		return responseBody;
	}
	
	
	
	
	
}
