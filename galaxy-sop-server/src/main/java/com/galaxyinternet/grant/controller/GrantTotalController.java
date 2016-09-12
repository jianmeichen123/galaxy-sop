package com.galaxyinternet.grant.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.data.domain.Sort.Direction;

import com.galaxyinternet.bo.GrantTotalBo;
import com.galaxyinternet.common.annotation.LogType;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.GrantPart;
import com.galaxyinternet.model.GrantTotal;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.GrantPartService;
import com.galaxyinternet.service.GrantTotalService;
import com.galaxyinternet.service.ProjectService;



@Controller
@RequestMapping("/galaxy/grant/total")
public class GrantTotalController extends BaseControllerImpl<GrantTotal, GrantTotalBo> {
	
	private final static Logger _common_logger_ = LoggerFactory.getLogger(GrantTotalController.class);
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private GrantTotalService grantTotalService;
	@Autowired
	private GrantPartService grantPartService;
	
	@Override
	protected BaseService<GrantTotal> getBaseService() {
		return this.grantTotalService;
	}
	
	
	/**
	 * sop tab页面  日志 详情    /galaxy/project/proview/
	 */
	@RequestMapping(value = "/toApprActualAging/{pid}", method = RequestMethod.GET)
	public String toApprActualAging(@PathVariable("pid") Long pid, HttpServletRequest request) {
		Project proinfo = new Project();
		proinfo = projectService.queryById(pid);
		request.setAttribute("pid", pid);
		request.setAttribute("prograss", proinfo.getProjectProgress());
		request.setAttribute("pname", proinfo.getProjectName());
		request.setAttribute("projectId", pid);
		return "project/tanchuan/appr_actual_aging";
	}
	/**
	 * sop tab页面  日志 详情    /galaxy/project/proview/
	 */
	@RequestMapping(value = "/toApprAllAdd", method = RequestMethod.GET)
	public String toApprActualAll(HttpServletRequest request) {
		String pid=request.getParameter("pid");
		request.setAttribute("projectId", pid);
		return "project/tanchuan/appr_actual_all";
	}
	/**
	 * sop tab页面  日志 详情    /galaxy/project/proview/
	 */
	@RequestMapping(value = "/tipsDelete", method = RequestMethod.GET)
	public String tipsDelete(HttpServletRequest request) {
		return "project/tanchuan/tipsDelete";
	}
	
	/**
	 * 新建总拨款计划
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG, LogType.MESSAGE })
	@ResponseBody
	@RequestMapping(value = "/addGrantTotal", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<GrantTotal> addGrantTotal(@RequestBody GrantTotal grantTotal,
			HttpServletRequest request) {
		ResponseData<GrantTotal> responseBody = new ResponseData<GrantTotal>();
		if(grantTotal.getGrantName() == null || "".equals(grantTotal.getGrantName().trim())
				|| grantTotal.getGrantMoney() == null || grantTotal.getProjectId() == null){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		String deal="";
		try {
			UrlNumber uNum = null;
			Project project = new Project();
			project = projectService.queryById(grantTotal.getProjectId());
			
			User user = (User) getUserFromSession(request);
			grantTotal.setUpdatedUname(user.getRealName());
			grantTotal.setUpdatedTime(System.currentTimeMillis());
			long id=0;
			if(null==grantTotal.getId()||grantTotal.getId()==0){
				grantTotal.setCreateUid(user.getId());
				grantTotal.setCreateUname(user.getRealName());
				uNum = UrlNumber.one;
				 grantTotal.setId(null);
				 id = grantTotalService.insert(grantTotal);
				 deal="添加";
			}else{
				uNum = UrlNumber.two;
				 id = grantTotalService.updateById(grantTotal);
				 deal="修改";
			}
			
			responseBody.setId(id);
			responseBody.setResult(new Result(Status.OK, "success", deal+"总拨款计划成功!"));
			ControllerUtils.setRequestParamsForMessageTip(request, null, project, "14.1", uNum);
			_common_logger_.info(deal+"总拨款计划成功"+grantTotal.toString());
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "error", "添加总拨款计划失败!"));
			_common_logger_.error(deal+"总拨款计划失败！", e);
		}
		return responseBody;
	}
	
	/**
	 * 总拨款计划列表查询
	 */
	@ResponseBody
	@RequestMapping(value = "/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<GrantTotal> searchProject(HttpServletRequest request, @RequestBody GrantTotal total) {
		ResponseData<GrantTotal> responseBody = new ResponseData<GrantTotal>();
		if(total.getProjectId() == null && "".equals(total.getProjectId())){
			responseBody.setResult(new Result(Status.ERROR,"error" , "必要的参数丢失!"));
			return responseBody;
		}
		try {
			total.setProperty("created_time");
			total.setDirection("desc");
			List<GrantTotal> totalList = grantTotalService.queryList(total);
			GrantPart part = new GrantPart();
			//封装分期拨款
			List<GrantTotal> tList = new ArrayList<GrantTotal>();
			for(GrantTotal t : totalList){
				boolean isSearch = false;
				part.setTotalGrantId(t.getId());
				List<GrantPart> partList = grantPartService.selectHasActualMoney(part);
				if(total.getSearchPartMoney() != null){
					for(GrantPart p : partList){
						if(p.getGrantMoney().doubleValue() == total.getSearchPartMoney().doubleValue()){
							isSearch = true;
							break;
						}
					}
				}else{
					isSearch = true;
				}
				if(isSearch){
					t.setPartList(partList);
					tList.add(t);
				}
			}
			Page<GrantTotal> totalPage = new Page<GrantTotal>(tList, Long.parseLong(String.valueOf(tList.size())));
			responseBody.setPageList(totalPage);
			responseBody.setResult(new Result(Status.OK, "success", "查询总拨款计划列表成功!"));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "error", "查询总拨款计划列表失败!"));
			_common_logger_.error("查询总拨款计划列表失败！", e);
		}
		return responseBody;
	}
	
	/**
	 * 根据id查询某个总拨款计划
	 */
	@ResponseBody
	@RequestMapping(value = "/getGrantTotal/{tid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<GrantTotal> getGrantTotal(@PathVariable("tid") Long tid,
			HttpServletRequest request) {
		ResponseData<GrantTotal> responseBody = new ResponseData<GrantTotal>();
			if(tid == null){
			responseBody.setResult(new Result(Status.ERROR, "error" , "重要的参数丢失!"));
			return responseBody;
		}

		try {
			GrantTotal c = grantTotalService.queryById(tid);
			GrantPart p=new GrantPart();
			p.setTotalGrantId(tid);
			List<GrantPart> partList = grantPartService.selectHasActualMoney(p);
			if(null!=partList&&!partList.isEmpty()){
				c.setIs_edit(false);
			}else{
				c.setIs_edit(true);
			}
			responseBody.setEntity(c);
			responseBody.setResult(new Result(Status.OK, "ok", "查询总拨款计划成功!"));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "error", "查询总拨款计划成功!"));
			_common_logger_.error("查询总拨款计划成功！", e);
		}
		return responseBody;
	}
	
	
	/**
	 * 删除总拨款计划
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG, LogType.MESSAGE })
	@ResponseBody
	@RequestMapping(value = "/deleteGrantTotal/{tid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<GrantTotal> deleteGrantTotal(@PathVariable("tid") Long tid,
			HttpServletRequest request) {
		ResponseData<GrantTotal> responseBody = new ResponseData<GrantTotal>();
		
		GrantTotal c = grantTotalService.queryById(tid);
		if(c == null){
			responseBody.setResult(new Result(Status.ERROR, "error" , "要删除的总拨款记录不存在!"));
			return responseBody;
		}
		
		GrantPart part = new GrantPart();
		part.setTotalGrantId(c.getId());
		Long count = grantPartService.queryCount(part);
		if(count > 0){
			responseBody.setResult(new Result(Status.ERROR, "cantDelete" , "存在分期拨款计划，不允许进行删除操作!"));
			return responseBody;
		}
		
		try {
			Project project = new Project();
			project = projectService.queryById(c.getProjectId());
			
			grantTotalService.deleteById(c.getId());
			responseBody.setResult(new Result(Status.OK, "ok", "删除总拨款计划失败!"));
			ControllerUtils.setRequestParamsForMessageTip(request, null, project, "14.1", UrlNumber.three);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "error", "删除总拨款计划失败!"));
			_common_logger_.error("删除总拨款计划失败！", e);
		}
		return responseBody;
	}
	
	
	/**
	 * 编辑总拨款计划
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG, LogType.MESSAGE })
	@ResponseBody
	@RequestMapping(value = "/resetGrantTotal", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<GrantTotal> resetGrantTotal(@RequestBody GrantTotal grantTotal,
			HttpServletRequest request) {
		ResponseData<GrantTotal> responseBody = new ResponseData<GrantTotal>();
		if(grantTotal.getId() == null){
			responseBody.setResult(new Result(Status.ERROR, "error" , "缺少必要的参数!"));
			return responseBody;
		}
		GrantTotal c = grantTotalService.queryById(grantTotal.getId());
		if(c == null){
			responseBody.setResult(new Result(Status.ERROR, "error" , "要编辑的总拨款记录不存在!"));
			return responseBody;
		}
		try {
			Project project = new Project();
			project = projectService.queryById(c.getProjectId());
			
			grantTotalService.updateById(grantTotal);
			responseBody.setResult(new Result(Status.OK, "ok", "编辑总拨款计划失败!"));
			ControllerUtils.setRequestParamsForMessageTip(request, null, project, "14.1", UrlNumber.two);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "error", "编辑总拨款计划失败!"));
			_common_logger_.error("编辑总拨款计划失败！", e);
		}
		return responseBody;
	}
	/**
	 * 编辑总拨款计划
	 */
	@ResponseBody
	@RequestMapping(value = "/getApprProcess/{pid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<GrantTotal> getAppropriationProcess(@PathVariable("pid")  Long pid,
			HttpServletRequest request) {
		ResponseData<GrantTotal> responseBody = new ResponseData<GrantTotal>();
		if(pid == null){
			responseBody.setResult(new Result(Status.ERROR, "error" , "缺少必要的参数!"));
			return responseBody;
		}
		try {
			Map<String,Object> setApprProcess = grantTotalService.setApprProcess(pid);
			responseBody.setUserData(setApprProcess);
			responseBody.setResult(new Result(Status.OK, "ok", "查询拨款进度失败!"));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "error", "查询拨款进度失败!"));
			_common_logger_.error("查询拨款进度失败！", e);
		}
		return responseBody;
	}
	
}
