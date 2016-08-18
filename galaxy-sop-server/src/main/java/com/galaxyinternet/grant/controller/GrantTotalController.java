package com.galaxyinternet.grant.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.form.Token;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.GrantPart;
import com.galaxyinternet.model.GrantTotal;
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
	@RequestMapping(value = "/toApprActualAll", method = RequestMethod.GET)
	public String toApprActualAll(HttpServletRequest request) {
		String pid=request.getParameter("pid");
		request.setAttribute("projectId", pid);
		return "project/tanchuan/appr_actual_all";
	}
	/**
	 * 新建总拨款计划
	 */
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
		try {
			User user = (User) getUserFromSession(request);
			grantTotal.setCreateUid(user.getId());
			grantTotal.setCreateUname(user.getRealName());
			long id = grantTotalService.insert(grantTotal);
			responseBody.setId(id);
			responseBody.setResult(new Result(Status.OK, "success", "添加总拨款计划成功!"));
			_common_logger_.info("添加总拨款计划成功"+grantTotal.toString());
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "error", "添加总拨款计划失败!"));
			_common_logger_.error("添加总拨款计划失败！", e);
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
		try {
			User user = (User) getUserFromSession(request);
			total.setCreateUid(user.getId());
			Page<GrantTotal> totalPage = grantTotalService.queryPageList(total,
					new PageRequest(total.getPageNum(), //1 
							total.getPageSize(), //3
							Direction.fromString(total.getDirection()), //desc
							total.getProperty()));
			GrantPart part = new GrantPart();
			//封装分期拨款
			List<GrantTotal> tList = new ArrayList<GrantTotal>();
			for(GrantTotal t : totalPage.getContent()){
				part.setTotalGrantId(t.getId());
				List<GrantPart> partList = grantPartService.selectHasActualMoney(part);
				t.setPartList(partList);
				tList.add(t);
			}
			totalPage.setContent(tList);
			responseBody.setPageList(totalPage);
			responseBody.setResult(new Result(Status.OK, "success", "查询总拨款计划列表成功!"));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "error", "查询总拨款计划列表失败!"));
			_common_logger_.error("查询总拨款计划列表失败！", e);
		}
		return responseBody;
	}
	
}
