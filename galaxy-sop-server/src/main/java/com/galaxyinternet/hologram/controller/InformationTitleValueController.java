package com.galaxyinternet.hologram.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.hologram.InformationTitleBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.hologram.service.CacheOperationServiceImpl;
import com.galaxyinternet.model.hologram.InformationDictionary;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.project.controller.ProjectProgressController;
import com.galaxyinternet.service.hologram.CacheOperationService;
import com.galaxyinternet.service.hologram.InformationDictionaryService;
import com.galaxyinternet.service.hologram.InformationTitleService;


@Controller
@RequestMapping("/galaxy/tvalue")
public class InformationTitleValueController  extends BaseControllerImpl<InformationTitle, InformationTitleBo> {

	final Logger logger = LoggerFactory.getLogger(ProjectProgressController.class);
	
	@Autowired
	private Cache cache;
	
	@Autowired
	private CacheOperationService cacheOperationService;
	
	@Autowired
	private InformationTitleService informationTitleService;
	
	@Autowired
	private InformationDictionaryService informationDictionaryService;
	
	@Override
	protected BaseService<InformationTitle> getBaseService() {
		return this.informationTitleService;
	}
	
	

	
	//=============== TODO  title 
	
	
	/**
	 * 传入题 id 或 code， 返回 题 信息
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTitleInfo/{pinfoKey}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InformationTitle> queryTitleInfo(HttpServletRequest request,@PathVariable("pinfoKey") String pinfoKey ) {
		ResponseData<InformationTitle> responseBody = new ResponseData<InformationTitle>();
		try{
			User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			InformationTitle title = informationTitleService.selectTitleByPinfo(pinfoKey);
			responseBody.setEntity(title);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "题信息获取失败"));
			logger.error("queryTitleInfo 题信息获取失败 : "+pinfoKey,e);
		}
		
		return responseBody;
	}
	
	
	/**
	 * 传入题 id 或 code， 返回该题 的下一级所有 题 信息
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTsTitles/{pinfoKey}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InformationTitle> queryTsTitles(HttpServletRequest request,@PathVariable("pinfoKey") String pinfoKey ) {
		ResponseData<InformationTitle> responseBody = new ResponseData<InformationTitle>();
		
		try{
			List<InformationTitle> titles = informationTitleService.selectChildsByPinfo(pinfoKey);
			responseBody.setEntityList(titles);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "题信息获取失败"));
			logger.error("queryTsTitles 题的子集信息获取失败: "+pinfoKey,e);
		}
		
		return responseBody;
	}
	
	
	
	/**
	 * 传入题 id 或 code， 返回该题信息及其下的所有子级的 题 信息
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAllTitle/{pinfoKey}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InformationTitle> queryAllTitle(HttpServletRequest request,@PathVariable("pinfoKey") String pinfoKey ) {
		ResponseData<InformationTitle> responseBody = new ResponseData<InformationTitle>();
		
		try{
			InformationTitle title = informationTitleService.selectPchildsByPinfo(pinfoKey);
			responseBody.setEntity(title);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "题信息获取失败"));
			logger.error("queryAllTitle 题及其所有子集信息获取失败 ："+pinfoKey,e);
		}
		
		return responseBody;
	}
	
	

	
	
	//=============== TODO  title + value
	
	/**
	 * 传入题 id ， 返回 题对应的 value 信息
	 */
	@ResponseBody
	@RequestMapping(value = "/queryValuesByTid/{tid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InformationDictionary> queryValuesByTid(HttpServletRequest request,@PathVariable("tid") Long tid ) {
		ResponseData<InformationDictionary> responseBody = new ResponseData<InformationDictionary>();
		try{
			List<InformationDictionary> values = informationDictionaryService.selectValuesByTid(tid);
			responseBody.setEntityList(values);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "字典获取失败"));
			logger.error("queryValuesByTid 题的values信息获取失败： "+tid,e);
		}
		
		return responseBody;
	}
	
	
	
	/**
	 * 传入题 id 或 code， 返回 题信息 及 其对应的 value 信息
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTitleAndValues/{pinfoKey}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InformationTitle> queryTitleAndValues(HttpServletRequest request,@PathVariable("pinfoKey") String pinfoKey ) {
		ResponseData<InformationTitle> responseBody = new ResponseData<InformationTitle>();
		
		try{
			InformationTitle title = informationDictionaryService.selectValuesByTinfo(pinfoKey);
			responseBody.setEntity(title);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "题字典获取失败"));
			logger.error("queryTitleAndValues 题及其values信息获取失败： "+pinfoKey,e);
		}
		
		return responseBody;
	}
	
	
	
	
	/**
	 * 传入题 id 或  code， 返回该题 的下一级的 题及value 信息
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTsTvalues/{pinfoKey}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InformationTitle> queryTsTvalues(HttpServletRequest request,@PathVariable("pinfoKey") String pinfoKey ) {
		ResponseData<InformationTitle> responseBody = new ResponseData<InformationTitle>();
		
		try{
			List<InformationTitle> titles = informationDictionaryService.selectTsTvalueInfoByCache(pinfoKey);
			responseBody.setEntityList(titles);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "信息获取失败"));
			logger.error("queryTsTvalues 题下的子集题value信息获取失败 ："+pinfoKey,e);
		}
		
		return responseBody;
	}
	
	
	
	/**
	 * 传入题 id 或 code， 返回该题信息及其下的所有级的 题和value信息
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAllTitleValues/{pinfoKey}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InformationTitle> queryAllTitleValues(HttpServletRequest request,@PathVariable("pinfoKey") String pinfoKey ) {
		ResponseData<InformationTitle> responseBody = new ResponseData<InformationTitle>();
		
		try{
			InformationTitle title = informationDictionaryService.selectTitlesValues(pinfoKey);
			responseBody.setEntity(title);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "题信息获取失败"));
			logger.error("queryAllTitleValues 题及所有子集及value信息获取失败："+pinfoKey,e);
		}
		
		return responseBody;
	}
	
	
	
	
}



