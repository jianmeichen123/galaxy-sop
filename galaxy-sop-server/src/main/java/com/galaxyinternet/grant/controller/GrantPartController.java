package com.galaxyinternet.grant.controller;

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
import org.springframework.web.servlet.ModelAndView;

import com.galaxyinternet.bo.GrantPartBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
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



@Controller
@RequestMapping("/galaxy/grant/part")
public class GrantPartController extends BaseControllerImpl<GrantPart, GrantPartBo> {
	
	private static final Logger _common_logger_ = LoggerFactory.getLogger(GrantPartController.class);
	
	@Autowired
	private GrantPartService grantPartService;
	@Override
	protected BaseService<GrantPart> getBaseService() {
		return this.grantPartService;
	}
	
	@Autowired
	private GrantTotalService grantTotalService;
	
	
	@RequestMapping(value = "/toAddGrantPart/{tid}", method = RequestMethod.GET)
	public ModelAndView toApprActualAging(@PathVariable("tid") Long tid, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("project/tanchuan/appr_actual_aging");
		GrantTotal total = grantTotalService.queryById(tid);
		mv.addObject("total", total);
		double useMoney = grantPartService.calculateBelongToActualMoney(tid);
		mv.addObject("remainMoney", total.getGrantMoney() - useMoney);
		return mv;
	}
	
	
	/**
	 * 新建分期拨款计划
	 */
	@ResponseBody
	@RequestMapping(value = "/addGrantPart", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<GrantPart> addGrantPart(@RequestBody GrantPart grantPart,
			HttpServletRequest request) {
		ResponseData<GrantPart> responseBody = new ResponseData<GrantPart>();
		if(grantPart.getGrantDetail() == null || "".equals(grantPart.getGrantDetail().trim())
				|| grantPart.getGrantMoney() == null || grantPart.getTotalGrantId() == null){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		
		try {
			Long count = grantPartService.queryCount(grantPart);
			/**
			 * 分期拨款记录删除时，做逻辑删除
			 */
			if(count > 0){
				grantPart.setGrantName("分拨" + (count + 1));
			}else{
				grantPart.setGrantName("分拨" + (count + 1));
			}
			User user = (User) getUserFromSession(request);
			grantPart.setCreateUid(user.getId());
			grantPart.setCreateUname(user.getRealName());
			long id = grantPartService.insert(grantPart);
			responseBody.setId(id);
			responseBody.setResult(new Result(Status.OK, "success", "添加分期拨款计划成功!"));
			_common_logger_.info("添加总拨款计划成功"+grantPart.toString());
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, "error", "添加总拨款计划失败!"));
			_common_logger_.error("添加总拨款计划失败！", e);
		}
		return responseBody;
	}
}
