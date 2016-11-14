package com.galaxyinternet.project.controller;

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

import com.galaxyinternet.bo.project.FinanceHistoryBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.FormatterUtils;
import com.galaxyinternet.model.project.FinanceHistory;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.FinanceHistoryService;

@Controller
@RequestMapping("/galaxy/financeHistory")
public class FinanceHistoryController extends BaseControllerImpl<FinanceHistory, FinanceHistoryBo> {

	private final static Logger logger = LoggerFactory.getLogger(FinanceHistoryController.class);

	@Autowired
	private FinanceHistoryService financeHistoryService;

	@Override
	protected BaseService<FinanceHistory> getBaseService() {
		
		return this.financeHistoryService;
	}
	/**
	 * 修改融资历史信息页面跳转
	 * @version v
	 * @return
	 */
	@RequestMapping(value = "/toUpateOrSaveFH", method = RequestMethod.GET)
	public String upateFinanceHistory(HttpServletRequest request) {
		return "project/v_upateOrsaveFH";
	}
	/**
	 * 根据项目id查询项目的历史投资信息
	 */
	@ResponseBody
	@RequestMapping(value = "/searchFH/{pid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<FinanceHistory> searchFH(@PathVariable("pid") String pid, HttpServletRequest request) {
		ResponseData<FinanceHistory> responseBody = new ResponseData<FinanceHistory>();
		if(pid == null){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			FinanceHistory financeHistory=new FinanceHistory();
			financeHistory.setProjectId(Long.parseLong(pid));
			List<FinanceHistory> queryList = financeHistoryService.queryList(financeHistory);
			responseBody.setEntityList(queryList);
			responseBody.setResult(new Result(Status.OK, "ok" , "查询融资历史成功!"));
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error(user.getId() + ":" + user.getRealName() + " to search financeHistory get an exception", e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	/**
	 * 添加股权结构弹出层
	 * @version v
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveFH/{pid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<FinanceHistory> saveFinanceHistory(@PathVariable("pid") Long pid, 
			@RequestBody FinanceHistory financeHistory,
			HttpServletRequest request) {
		ResponseData<FinanceHistory> responseBody = new ResponseData<FinanceHistory>();
		if(null ==  financeHistory ||null == pid){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			financeHistory.setProjectId(pid);
			financeHistoryService.insert(financeHistory);
			logger.info(user.getId() + ":" + user.getRealName() + " to save FinanceHistory successful > " + pid);
			responseBody.setResult(new Result(Status.OK, "ok" , "添加融资历史成功!"));
		} catch (Exception e) {
			logger.error(user.getId() + ":" + user.getRealName() + " to save FinanceHistory get an exception", e);
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	/**
	 * 修改历史投资信息
	 */
	@ResponseBody
	@RequestMapping(value = "/upateFHSave/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<FinanceHistory> updateSave(@PathVariable("id") Long id,@RequestBody FinanceHistory financeHistory,
			HttpServletRequest request) {
		ResponseData<FinanceHistory> responseBody = new ResponseData<FinanceHistory>();
		if(id == null ){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		try {
			financeHistory.setId(id);
			int updateById = financeHistoryService.updateById(financeHistory);
			if(updateById>0){
				responseBody.setResult(new Result(Status.OK,"ok" , "更新融资历史成功!"));
			}else{
				responseBody.setResult(new Result(Status.ERROR,"error" , "更新融资历史失败!"));
			}
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error(FormatterUtils.formatStr(
						"{0}:{1} to updateSave financeHistory get an exception {id : {3}}", 
						user.getId(), user.getRealName(), id), e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	/**
	 * 查询历史投资信息
	 */
	@ResponseBody
	@RequestMapping(value = "/getFH/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<FinanceHistory> getFinanceHistory(@PathVariable("id") String id,
			HttpServletRequest request) {
		ResponseData<FinanceHistory> responseBody = new ResponseData<FinanceHistory>();
		if(id == null || "".equals(id.trim()) ){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		FinanceHistory fh = new FinanceHistory();
		try {
			fh=financeHistoryService.queryById(Long.parseLong(id));
			if(null!=fh&&!"".equals(fh)){
				responseBody.setEntity(fh);
				responseBody.setResult(new Result(Status.OK,"ok" , "查询融资历史成功!"));
				logger.info(user.getId() + ":" + user.getRealName() + " to search FinanceHistory by uuid successful > " + fh.getProjectId());
			}else{
				responseBody.setResult(new Result(Status.ERROR,"error" , "未找到该融资信息!"));
			}
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error(FormatterUtils.formatStr(
						"{0}:{1} to delete learning get an exception {pid : {3}, uuid : {4}}", 
						user.getId(), user.getRealName(), fh.getProjectId(), id), e);
			}
			responseBody.setResult(new Result(Status.ERROR,"error" , "出现未知异常!"));
		}
		return responseBody;
	}
	/**
	 * 删除历史投资信息
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteFH/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<FinanceHistory> deleteFinanceHistory(@PathVariable("id") String id,
			HttpServletRequest request) {
		ResponseData<FinanceHistory> responseBody = new ResponseData<FinanceHistory>();
		if (id == null || "".equals(id.trim())) {
			responseBody.setResult(new Result(Status.ERROR, "csds", "必要的参数丢失!"));
			return responseBody;
		}
		User user = (User) getUserFromSession(request);
		FinanceHistory financeHistory=new FinanceHistory();
		try {
			int result = financeHistoryService.deleteById(Long.parseLong(id));
			financeHistory=financeHistoryService.queryById(Long.parseLong(id));
			if(result>0){
					logger.info(user.getId() + ":" + user.getRealName() + " to deleteFinanceHistory financeHistory successful > "
							+ financeHistory.getProjectId());
					responseBody.setResult(new Result(Status.OK, "ok", "删除融资历史成功!"));
			}else{
				responseBody.setResult(new Result(Status.ERROR,"error" , "删除融资历史失败!"));
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(
						FormatterUtils.formatStr("{0}:{1} to delete financeHistory get an exception {id : {3},pid : {4}}",
								user.getId(), user.getRealName(), id,financeHistory.getProjectId()),
						e);
			}
			responseBody.setResult(new Result(Status.ERROR, "error", "出现未知异常!"));
		}
		return responseBody;
	}
}
