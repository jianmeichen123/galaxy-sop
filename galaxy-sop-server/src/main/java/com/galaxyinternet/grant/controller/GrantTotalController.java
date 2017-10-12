package com.galaxyinternet.grant.controller;

import com.galaxyinternet.bo.GrantTotalBo;
import com.galaxyinternet.common.annotation.LogType;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.hologram.util.ListSortUtil;
import com.galaxyinternet.model.GrantPart;
import com.galaxyinternet.model.GrantTotal;
import com.galaxyinternet.model.hologram.InformationListdata;
import com.galaxyinternet.model.hologram.InformationResult;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.GrantPartService;
import com.galaxyinternet.service.GrantTotalService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.hologram.InformationListdataService;
import com.galaxyinternet.service.hologram.InformationResultService;

import org.apache.commons.lang.StringUtils;
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

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;



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
	
	@Autowired
	private InformationResultService informationResultService;
	@Autowired
	private InformationListdataService informationListdataService;
	
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
		if(StringUtils.isNotEmpty(pid)){
			//投资金额,估值安排所有值,投资方主体
			String[] titleIds = {"3004","3012","3011","3010","3020"};
			Set<String> set=new HashSet<String>();         
			set.addAll(Arrays.asList(titleIds));
			InformationResult informationResult = new InformationResult();
			informationResult.setProjectId(pid);
			informationResult.setTitleIds(set);
			informationResult.setIsValid("0");
			//获取总注资计划的金额
			List<InformationResult> list = informationResultService.queryList(informationResult);
			if(list != null && list.size() > 0){
				for(InformationResult ir : list){
					  request.setAttribute("result"+ir.getTitleId(), ir.getId());
					  request.setAttribute("value"+ir.getTitleId(), ir.getContentDescribe1());
				}
			}
			
		}
		request.setAttribute("projectId", pid);
		
		return "project/tanchuan/appr_actual_all";
	}
	
	
	/**
	 * sop tab页面  日志 详情    /galaxy/project/proview/
	 */
	@RequestMapping(value = "/toActualTotalLook/{id}", method = RequestMethod.GET)
	public String toActualTotalLook(HttpServletRequest request,@PathVariable("id") Long id) {
		if(id == null){
			return "project/tanchuan/appr_actual_total_look";
		}
		InformationResult c = informationResultService.queryById(id);
		if(c != null){
			//投资金额,估值安排所有值,投资方主体
			String[] titleIds = {"3004","3012","3011","3010","3020"};
			Set<String> set=new HashSet<String>();         
			set.addAll(Arrays.asList(titleIds));
			InformationResult informationResult = new InformationResult();
			informationResult.setProjectId(c.getProjectId());
			informationResult.setTitleIds(set);
			informationResult.setIsValid("0");
			//获取总注资计划的金额
			List<InformationResult> list = informationResultService.queryList(informationResult);
			if(list != null && list.size() > 0){
				for(InformationResult ir : list){
					if(!StringUtils.isEmpty(ir.getContentDescribe1())){
					    request.setAttribute("value"+ir.getTitleId(), ir.getContentDescribe1());
					}
				}
			}
		}
		return "project/tanchuan/appr_actual_toatl_look";
	}
	
	/**
	 * sop tab页面  日志 详情    /galaxy/project/proview/
	 */
	@RequestMapping(value = "/tipsDelete", method = RequestMethod.GET)
	public String tipsDelete(HttpServletRequest request) {
		return "project/tanchuan/tipsDelete";
	}
	
	/**
	 * 新建总注资计划
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = {  LogType.MESSAGE })
	@ResponseBody
	@RequestMapping(value = "/addGrantTotal", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<GrantTotal> addGrantTotal(@RequestBody GrantTotal grantTotal,
			HttpServletRequest request) {
		ResponseData<GrantTotal> responseBody = new ResponseData<GrantTotal>();
		if(grantTotal.getGrantName() == null || "".equals(grantTotal.getGrantName().trim())
				|| grantTotal.getGrantMoney() == null || grantTotal.getInvestors() == null || "".equals(grantTotal.getInvestors().trim()) 
				|| grantTotal.getProjectId() == null || grantTotal.getFinalShareRatio() == null 
				|| grantTotal.getFinalValuations() == null || grantTotal.getServiceCharge() == null){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		String deal="";
		try {
			UrlNumber uNum = null;
			Project project = new Project();
			project = projectService.queryById(grantTotal.getProjectId());
			String projectCompany = grantTotal.getProjectCompany();
			//更改项目基本信息表
			project.setProjectCompany(projectCompany);
			project.setFinalContribution(grantTotal.getGrantMoney());
			project.setFinalValuations(grantTotal.getFinalValuations());
			project.setFinalShareRatio(grantTotal.getFinalShareRatio());
			project.setServiceCharge(grantTotal.getServiceCharge());
			projectService.updateById(project);
			
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
			responseBody.setResult(new Result(Status.OK, "success", deal+"总注资计划成功!"));
			ControllerUtils.setRequestParamsForMessageTip(request, null, project, "14.1", uNum);
			_common_logger_.info(deal+"总注资计划成功"+grantTotal.toString());
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "error", "添加总注资计划失败!"));
			_common_logger_.error(deal+"总注资计划失败！", e);
		}
		return responseBody;
	}
	
	/**
	 * 总注资计划列表查询
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
			//封装分期注资
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
			responseBody.setResult(new Result(Status.OK, "success", "查询总注资计划列表成功!"));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "error", "查询总注资计划列表失败!"));
			_common_logger_.error("查询总注资计划列表失败！", e);
		}
		return responseBody;
	}
	
	
	/**
	 * 总注资计划列表查询
	 */
	@ResponseBody
	@RequestMapping(value = "/searchPart", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InformationListdata> searchPart(HttpServletRequest request, @RequestBody InformationListdata informationListdata) {
		ResponseData<InformationListdata> responseBody = new ResponseData<InformationListdata>();
		if(informationListdata.getProjectId() == null && "".equals(informationListdata.getProjectId())){
			responseBody.setResult(new Result(Status.ERROR,"error" , "必要的参数丢失!"));
			return responseBody;
		}
		
		InformationResult informationResult = new InformationResult();
		informationResult.setProjectId(Long.toString(informationListdata.getProjectId()));
		informationResult.setTitleId("3004");
		informationResult.setIsValid("0");
		//获取总注资计划的金额
		List<InformationListdata> gp = null;
		List<InformationResult> list = informationResultService.queryList(informationResult);
		Map<String,Object> userData = new HashMap<String,Object>();
		if(list != null && list.size() > 0){
			informationResult = list.get(0);
			userData.put("id", informationResult.getId());
			userData.put("grantMoney", informationResult.getContentDescribe1());
		    //查找分期
			InformationListdata infordata = new InformationListdata();
			infordata.setTitleId(3022l);
			infordata.setProjectId(informationListdata.getProjectId());
			gp = informationListdataService.queryList(infordata);
			//分期列表
			if(gp != null && gp.size() > 0){
				List<InformationListdata> removedatalist = new ArrayList<InformationListdata>();
				for(InformationListdata item : gp){
					item.setField4(null);
					infordata.setParentId(item.getId());
					infordata.setCode("grant-actual");
		            Double money = informationListdataService.selectActualMoney(infordata);
		            if(money != null && money.doubleValue() > 0){
		            	item.setField4(money.toString());
		            }
					List<InformationListdata> datalist = new ArrayList<InformationListdata>();
					//实际注资
					for(InformationListdata data : gp)
					{
					  if(data.getParentId() != null && data.getCode() != null && item.getId().intValue() == data.getParentId().intValue()){
						  datalist.add(data);
						  removedatalist.add(data);
					  }
					}
					ListSortUtil<InformationListdata> sortList = new ListSortUtil<InformationListdata>();  
					sortList.sort(datalist,"field2","asc");
					item.setDataList(datalist);
					
					
				}
				gp.removeAll(removedatalist);
			}
		}
		Page<InformationListdata> totalPage = new Page<InformationListdata>(gp, gp == null ? 0 : Long.parseLong(String.valueOf(gp.size())));
		responseBody.setUserData(userData);
		responseBody.setPageList(totalPage);
		return responseBody;
	}
	
	/**
	 * 根据id查询某个总注资计划
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
			Long projectId = c.getProjectId();
			if(projectId != null){
				Project pro = projectService.queryById(projectId);
				c.setProjectCompany(pro.getProjectCompany());
				/*c.setFinalShareRatio(pro.getFinalShareRatio());
				c.setFinalValuations(pro.getFinalValuations());
				c.setServiceCharge(pro.getServiceCharge());*/
			}
			
			GrantPart p=new GrantPart();
			p.setTotalGrantId(tid);
			List<GrantPart> partList = grantPartService.selectHasActualMoney(p);
			if(null!=partList&&!partList.isEmpty()){
				c.setIs_edit(false);
			}else{
				c.setIs_edit(true);
			}
			responseBody.setEntity(c);
			responseBody.setResult(new Result(Status.OK, "ok", "查询总注资计划成功!"));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "error", "查询总注资计划成功!"));
			_common_logger_.error("查询总注资计划成功！", e);
		}
		return responseBody;
	}
	
	
	/**
	 * 删除总注资计划
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = {  LogType.MESSAGE })
	@ResponseBody
	@RequestMapping(value = "/deleteGrantTotal/{tid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<GrantTotal> deleteGrantTotal(@PathVariable("tid") Long tid,
			HttpServletRequest request) {
		ResponseData<GrantTotal> responseBody = new ResponseData<GrantTotal>();
		InformationResult c = informationResultService.queryById(tid);
		if(c == null){
			responseBody.setResult(new Result(Status.ERROR, "error" , "要删除的总注资记录不存在!"));
			return responseBody;
		}
		//查找分期
		InformationListdata id = new InformationListdata();
		id.setTitleId(3022l);
		id.setProjectId(Long.valueOf(c.getProjectId()));
		List<InformationListdata> part = informationListdataService.queryList(id);
		if(part != null && part.size() > 0){
			responseBody.setResult(new Result(Status.ERROR, "cantDelete" , "存在分期注资计划，不允许进行删除操作!"));
			return responseBody;
		}
		Project project = projectService.queryById(Long.valueOf(c.getProjectId()));
		try {
			//投资金额,估值安排所有值,投资方主体
			String[] titleIds = {"3004","3012","3011","3010","3020"};
			Set<String> set=new HashSet<String>();         
			set.addAll(Arrays.asList(titleIds));
			InformationResult informationResult = new InformationResult();
			informationResult.setProjectId(c.getProjectId());
			informationResult.setTitleIds(set);
			informationResult.setIsValid("0");
			//获取总注资计划的金额
			List<InformationResult> list = informationResultService.queryList(informationResult);
			if(list != null && list.size() > 0){
				List<Long> idList = new ArrayList<Long>();
				for(InformationResult ir : list){
					idList.add(ir.getId());
				}
				informationResultService.deleteByIdInBatch(idList);
			}
			responseBody.setResult(new Result(Status.OK, "ok", "删除总注资计划成功!"));
			ControllerUtils.setRequestParamsForMessageTip(request, null, project, "14.1", UrlNumber.three);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "error", "删除总注资计划失败!"));
			_common_logger_.error("删除总注资计划失败！", e);
		}
		return responseBody;
	}
	
	
	/**
	 * 编辑总注资计划
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.MESSAGE })
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
			responseBody.setResult(new Result(Status.ERROR, "error" , "要编辑的总注资记录不存在!"));
			return responseBody;
		}
		try {
			Project project = new Project();
			project = projectService.queryById(c.getProjectId());
			//更改项目基本信息表
			project.setProjectCompany(grantTotal.getProjectCompany());
			project.setFinalContribution(grantTotal.getGrantMoney());
			project.setFinalValuations(grantTotal.getFinalValuations());
			project.setFinalShareRatio(grantTotal.getFinalShareRatio());
			project.setServiceCharge(grantTotal.getFinalShareRatio());
			projectService.updateById(project);
			
			grantTotalService.updateById(grantTotal);
			responseBody.setResult(new Result(Status.OK, "ok", "编辑总注资计划失败!"));
			ControllerUtils.setRequestParamsForMessageTip(request, null, project, "14.1", UrlNumber.two);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "error", "编辑总注资计划失败!"));
			_common_logger_.error("编辑总注资计划失败！", e);
		}
		return responseBody;
	}
	/**
	 * 编辑总注资计划
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
			InformationResult informationResult = new InformationResult();
			informationResult.setProjectId(String.valueOf(pid));
			informationResult.setTitleId("3004");
			informationResult.setIsValid("0");
			Double sumPlanMoney = null;
			//获取总注资计划的金额
			List<InformationResult> list = informationResultService.queryList(informationResult);
			if(list != null && list.size() > 0){
				sumPlanMoney = Double.valueOf(list.get(0).getContentDescribe1()==null?"0":list.get(0).getContentDescribe1());
			}
			Map<String,Object> map=new HashMap<String, Object>();
			// TODO Auto-generated method stub
			InformationListdata data = new InformationListdata();
			data.setProjectId(pid);
			data.setTitleId(3022l);
			data.setCode("grant-actual");
			Double sumProjectToActualMoney = informationListdataService.selectActualMoney(data);
			map.put("sumPlanMoney", sumPlanMoney);
			map.put("sumActualMoney", sumProjectToActualMoney);
			responseBody.setUserData(map);
			responseBody.setResult(new Result(Status.OK, "ok", "查询注资进度失败!"));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "error", "查询注资进度失败!"));
			_common_logger_.error("查询注资进度失败！", e);
		}
		return responseBody;
	}
	
}
