package com.galaxyinternet.touhou.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.OperationalDataBo;
import com.galaxyinternet.common.annotation.LogType;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.touhou.OperationalData;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.OperationalDataService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.UserService;

@Controller
@RequestMapping("/galaxy/operationalData")
public class OperationalDataController extends BaseControllerImpl<OperationalData, OperationalDataBo> {
 
	private final static Logger logger = LoggerFactory.getLogger(OperationalDataController.class);

	@Autowired
	private OperationalDataService operationalDataService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;
	
	@Override
	protected BaseService<OperationalData> getBaseService() {
		// TODO Auto-generated method stub
		return operationalDataService;
	}
	
	/**
	 * 运营记录入口
	 * @return
	 */
	@RequestMapping(value="/toOperationalDataList/{projectId}",method = RequestMethod.GET)
	public String toOperationalDataList(@PathVariable("projectId") Long projectId,HttpServletRequest request,HttpServletResponse response){
		if(projectId !=null){
			Project pro = projectService.queryById(projectId);
			request.setAttribute("projectName", pro.getProjectName());
			request.setAttribute("projectId", projectId);
		}
		return "project/operationalDataList";
	}
	
	/**
	 * 添加运营记录入口
	 * @return
	 */
	@RequestMapping(value="/addOperationalDataList/{projectId}",method = RequestMethod.GET)
	public String addOperationalDataList(@PathVariable("projectId") Long projectId,HttpServletRequest request){
		if(projectId !=null ){
			Project pro = projectService.queryById(projectId);
			request.setAttribute("projectName", pro.getProjectName());
			request.setAttribute("projectId", pro.getId());
		}
		return "project/addOperationalDataList";
	}
	
	
	/**
	 * 编辑运营记录入口
	 * @return
	 */
	@RequestMapping(value="/editOperationalDataList/{operationId}",method = RequestMethod.GET)
	public String editOperationalDataList(@PathVariable("operationId") Long operationId,HttpServletRequest request){
		if(operationId !=null ){
			OperationalData operationalData = operationalDataService.selectOperationalDataById(operationId);
			request.setAttribute("projectId", operationalData.getProjectId());
			Project pro = projectService.queryById(operationalData.getProjectId());
			request.setAttribute("projectName", pro.getProjectName());
			request.setAttribute("operationalData", operationalData);
		}
		return "project/addOperationalDataList";
	}
	
	
	/**
	 * 查看运营记录入口
	 * @return
	 */
	@RequestMapping(value="/infoOperationalDataList/{operationId}",method = RequestMethod.GET)
	public String infoOperationalDataList(@PathVariable("operationId") Long operationId,HttpServletRequest request){
		if(operationId !=null ){
			OperationalData operationalData = operationalDataService.selectOperationalDataById(operationId);
			Project pro = projectService.queryById(operationalData.getProjectId());
			request.setAttribute("projectName", pro.getProjectName());
			request.setAttribute("operationalData", operationalData);
		}
		return "project/infoOperationalDataList";
	}
	
	/**
	 * 运营记录列表
	 * @param request
	 * @param operationalData
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/operationalDataList",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<OperationalData> searchOperationalDataList(
			@RequestBody OperationalData operationalData,HttpServletRequest request) {
		
        ResponseData<OperationalData> responseBody = new ResponseData<OperationalData>();
		try {
			if(operationalData != null){
				operationalData.setProperty("operation_interval_date");
				operationalData.setDirection("DESC");
			}
			List<User> userList = userService.queryAll();
			Map<String,String> mapUser = new HashMap<String,String>();
			for(User user : userList){
				mapUser.put(user.getId()+"", user.getRealName());
			}
			List<Long> dataTypeList = new ArrayList<Long>();
			if(!StringUtils.isEmpty(operationalData.getDataTypeMonth())){
				dataTypeList.add(Long.valueOf(operationalData.getDataTypeMonth()));
			}
			if(!StringUtils.isEmpty(operationalData.getDataTypeQuarter())){
				dataTypeList.add(Long.valueOf(operationalData.getDataTypeQuarter()));
			}
			if(dataTypeList != null && dataTypeList.size() > 0){
				operationalData.setDataTypeList(dataTypeList);
			}else{
				dataTypeList.add(3l);
				operationalData.setDataTypeList(dataTypeList);
			}	
		    Page<OperationalData> pageList = operationalDataService.queryOperationalDataPageList(operationalData, new PageRequest(operationalData.getPageNum(),operationalData.getPageSize()));
			List<OperationalData> contentList = pageList.getContent();
			for(OperationalData op: contentList){
				String name = mapUser.get(op.getUpdatedUid()+"");
				op.setUpdateUserName(name);
			}
			pageList.setContent(contentList);
			responseBody.setPageList(pageList);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"查询运营记录失败"));
			if(logger.isErrorEnabled()){
				logger.error("查询运营记录失败  ",e);
			}
			return responseBody;
		}
		
	}
	
	
	/**
	 * 查看运营记录
	 * @param operationalDataId
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/selectOperationalData/{operationalDataId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<OperationalData> selectOperationalData(@PathVariable("operationalDataId") Long operationalDataId,HttpServletRequest request,HttpServletResponse response ) {
		ResponseData<OperationalData> responseBody = new ResponseData<OperationalData>();
		try {
			OperationalData operationalData = operationalDataService.selectOperationalDataById(operationalDataId);
			responseBody.setEntity(operationalData);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "查询失败"));
			logger.error("operationalData 查询失败",e);
		}
		return responseBody;
	}
	
	/***
	 * 新增|修改运营记录
	 * @param operationalData
	 * @param request
	 * @return
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG, LogType.MESSAGE })
	@RequestMapping(value = "/formAddOperationalData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseData<OperationalDataBo> addOperationalData(@RequestBody OperationalDataBo operationalData,HttpServletRequest request) {
		ResponseData<OperationalDataBo> responseBody = new ResponseData<OperationalDataBo>();
		User user = (User) getUserFromSession(request);
		String messageType = "19.1";
		UrlNumber number = UrlNumber.one;
		try {
			if(operationalData == null){
				responseBody.setResult(new Result(Status.ERROR, "error", "添加运营记录失败!"));
			}
			Project project = projectService.queryById(operationalData.getProjectId());
			if(operationalData.getOperationalDataId() != null){
				OperationalData op = operationalDataService.selectOperationalDataById(Long.valueOf(operationalData.getOperationalDataId()));
				operationalData.setId(Long.valueOf(operationalData.getOperationalDataId()));
				operationalData.setUpdatedUid(user.getId());
				operationalData.setUpdatedTime(System.currentTimeMillis());
				operationalData.setCreateUid(op.getCreateUid());
				operationalData.setCreateTime(op.getCreateTime());
				operationalDataService.updateByIdSelective(operationalData);
				messageType = "19.2";
				number = UrlNumber.two;
			}else{
				operationalData.setCreateUid(user.getId());
				operationalData.setCreateTime(System.currentTimeMillis());
				operationalData.setUpdatedUid(user.getId());
				operationalData.setUpdatedTime(System.currentTimeMillis());
				operationalDataService.insert(operationalData);
			}
			responseBody.setResult(new Result(Status.OK, "success", "添加运营记录成功!"));
			ControllerUtils.setRequestParamsForMessageTip(request,project.getProjectName(), project.getId(),messageType,number);
			logger.info("添加运营记录成功!");
		} catch (Exception e) {
			e.printStackTrace();
			responseBody.setResult(new Result(Status.ERROR, "error", "添加运营记录失败!"));
			logger.error("添加运营记录失败！", e);
		}
		return responseBody;
	}
	
	/**
	 *删除
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG, LogType.MESSAGE })
	@ResponseBody
	@RequestMapping(value = "/delOperationalData/{operationalDataId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<OperationalData> delOperationalData(@PathVariable("operationalDataId") Long operationalDataId,HttpServletRequest request,HttpServletResponse response ) {
		ResponseData<OperationalData> responseBody = new ResponseData<OperationalData>();
		if(StringUtils.isEmpty(operationalDataId)){
			responseBody.setResult(new Result(Status.ERROR,null, "参数丢失!"));
			return responseBody;
		}
		try {
			OperationalData od = operationalDataService.selectOperationalDataById(operationalDataId);
			Project project = projectService.queryById(od.getProjectId());
			operationalDataService.deleteById(operationalDataId);
			responseBody.setResult(new Result(Status.OK, ""));
			ControllerUtils.setRequestParamsForMessageTip(request,project.getProjectName(), project.getId(),"19.3");
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "删除失败"));
			logger.error("delGrantPart 删除失败",e);
		}
		return responseBody;
	}
	

}
