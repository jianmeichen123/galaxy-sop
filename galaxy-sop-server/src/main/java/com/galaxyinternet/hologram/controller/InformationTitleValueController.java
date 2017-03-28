package com.galaxyinternet.hologram.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.hologram.InformationTitleBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.exception.PlatformException;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationDictionary;
import com.galaxyinternet.model.hologram.InformationTitle;

import com.galaxyinternet.project.controller.ProjectProgressController;
import com.galaxyinternet.service.hologram.CacheOperationService;
import com.galaxyinternet.service.hologram.InformationDictionaryService;
import com.galaxyinternet.service.hologram.InformationTitleService;


@Controller
@RequestMapping("/galaxy/tvalue")
public class InformationTitleValueController  extends BaseControllerImpl<InformationTitle, InformationTitleBo> {

	final Logger logger = LoggerFactory.getLogger(ProjectProgressController.class);
	
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
	
	
	@ResponseBody
	@RequestMapping(value = "/refersh", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InformationTitle> refersh() {
		ResponseData<InformationTitle> responseBody = new ResponseData<InformationTitle>();
		Result result = new Result();
		try {
			cacheOperationService.refreshCache();
			result.setStatus(Status.OK);
		} catch (PlatformException e){
			result.addError(e.getMessage(), e.getCode()+"");
		} catch (Exception e) {
			result.addError("系统错误");
			logger.error("CacheOperationService.refresh error",e);
		}
		responseBody.setResult(result);
		return responseBody;
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
			//User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
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
	 * 传入题 id 或 code， 返回该题 及其下一级的 题 信息
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTsTitles/{pinfoKey}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InformationTitle> queryTsTitles(HttpServletRequest request,@PathVariable("pinfoKey") String pinfoKey ) {
		ResponseData<InformationTitle> responseBody = new ResponseData<InformationTitle>();
		
		try{
			InformationTitle titles = informationTitleService.selectTChildsByPinfo(pinfoKey);
			responseBody.setEntity(titles);
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
	 * 传入value的 id ， 返回 该value下级的 values
	 */
	@ResponseBody
	@RequestMapping(value = "/queryValuesByVpid/{pid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InformationDictionary> queryValuesByVpid(HttpServletRequest request,@PathVariable("pid") Long pid ) {
		ResponseData<InformationDictionary> responseBody = new ResponseData<InformationDictionary>();
		try{
			List<InformationDictionary> values = informationDictionaryService.selectValuesByVpid(pid);
			responseBody.setEntityList(values);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "字典获取失败"));
			logger.error("queryValuesByVpid 的下级 values信息获取失败： "+pid,e);
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
	 * 传入题 id 或  code， 返回该题信息，及该题的下一级的 题及value 信息
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTitleAndTsTvalues/{pinfoKey}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InformationTitle> queryTitleAndTsTvalues(HttpServletRequest request,@PathVariable("pinfoKey") String pinfoKey ) {
		ResponseData<InformationTitle> responseBody = new ResponseData<InformationTitle>();
		
		try{
			InformationTitle title = informationDictionaryService.selectTitleAndTsTvaluesByCache(pinfoKey);
			responseBody.setEntity(title);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "信息获取失败"));
			logger.error("queryTsTvalues 题及该题的下一级题value信息获取失败 ："+pinfoKey,e);
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
	
	
	
	
	// ===  TODO 页面功能
	
	
	/**
	 * 页面查看功能
	 * 查看题和保存的结果信息
	 * 传入项目 id， 区域  code， 返回 该区域下 题和保存的结果信息
	 */
	@ResponseBody
	@RequestMapping(value = "/queryProjectAreaInfo/{pid}/{tcode}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InformationTitle> queryProjectAreaInfo(HttpServletRequest request,@PathVariable("pid") String pid,@PathVariable("tcode") String tcode ) {
		ResponseData<InformationTitle> responseBody = new ResponseData<InformationTitle>();
		try{

			InformationTitle title = informationTitleService.selectAreaTitleResutl(pid, tcode);
			
			responseBody.setEntity(title);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "题和保存的结果信息获取失败"));
			logger.error("queryTitleInfo 题和保存的结果信息 : "+tcode,e);
		}
		
		return responseBody;
	}
	
	
	
	
	/**
	 * 页面编辑功能
	 * 查看题和values 及 保存的结果信息
	 * 传入项目 id， 区域  code， 返回 该区域下 题和values 及 保存的结果信息
	 */
	@ResponseBody
	@RequestMapping(value = "/editProjectAreaInfo/{pid}/{tcode}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InformationTitle> editProjectAreaInfo(HttpServletRequest request,@PathVariable("pid") String pid,@PathVariable("tcode") String tcode ) {
		ResponseData<InformationTitle> responseBody = new ResponseData<InformationTitle>();
		try{

			InformationTitle title = informationTitleService.editAreaTitleResutl(pid, tcode);
			
			responseBody.setEntity(title);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "题和保存的结果信息获取失败"));
			logger.error("queryTitleInfo 题和保存的结果信息 : "+tcode,e);
		}
		
		return responseBody;
	}
	
	
	
	
	
	/**
	 * 页面级联功能
	 * 传入项目 id，    title id，    级联 value的 pid，  
	 * 返回 value 的 pid 下的 values
	 */
	@ResponseBody
	@RequestMapping(value = "/queryProNvaluesInfo/{pid}/{tid}/{vpid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InformationDictionary> queryProNvaluesInfo(HttpServletRequest request,
			@PathVariable("pid") String pid,@PathVariable("tid") String tid,@PathVariable("vpid") String vpid  ) {
		ResponseData<InformationDictionary> responseBody = new ResponseData<InformationDictionary>();
		try{

			List<InformationDictionary> vs = informationTitleService.selectProNvaluesInfo(pid, tid, vpid);
			
			responseBody.setEntityList(vs);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "题和保存的结果信息获取失败"));
			logger.error("queryTitleInfo 题和保存的结果信息 : ",e);
		}
		
		return responseBody;
	}
	
	
	
	
}
