package com.galaxyinternet.grant.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.GrantTotalBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.GrantTotal;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.GrantTotalService;



@Controller
@RequestMapping("/galaxy/grant/total")
public class GrantTotalController extends BaseControllerImpl<GrantTotal, GrantTotalBo> {
	
	private final static Logger _common_logger_ = LoggerFactory.getLogger(GrantTotalController.class);
	
	private GrantTotalService grantTotalService;
	
	@Override
	protected BaseService<GrantTotal> getBaseService() {
		return this.grantTotalService;
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
			_common_logger_.error("添加总拨款计划失败！", e);
		}
		return responseBody;
	}
	
}
