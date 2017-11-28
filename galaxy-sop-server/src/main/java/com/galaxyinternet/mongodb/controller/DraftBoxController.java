package com.galaxyinternet.mongodb.controller;

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

import com.galaxyinternet.common.annotation.LogType;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.mongodb.model.InformationDataMG;
import com.galaxyinternet.mongodb.service.InformationMGService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("全息图后台接口")
@Controller
@RequestMapping("/galaxy/draftBox/infoData")
public class DraftBoxController  extends BaseControllerImpl<InformationDataMG, InformationDataMG> {

	final Logger logger = LoggerFactory.getLogger(DraftBoxController.class);
	
	@Autowired
	private InformationMGService informationMGService;
	
	@Override
	protected BaseService<InformationDataMG> getBaseService() {
		return this.informationMGService;
	}
	/**
	 * 全息图-项目部分-字段编辑
	 * @version 2017-03-13
	 * @author jianmeichen
	 */
	@ApiOperation("保存")
	@com.galaxyinternet.common.annotation.Logger(operationScope = LogType.LOG)
	@ResponseBody
	@RequestMapping(value = "/saveOrUpdateInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InformationDataMG> saveOrUpdateInfo(
			@ApiParam(name = "informationDataMG", value = "标题/值信息", required = true)
			@RequestBody 
			InformationDataMG informationData,
			HttpServletRequest request) {
		ResponseData<InformationDataMG> responseBody = new ResponseData<InformationDataMG>();
		if(null==informationData.getProjectId()||"".equals(informationData.getProjectId())){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
	    Long projectId=Long.parseLong(informationData.getProjectId());
	   // Project project = projectService.queryById(projectId);
		User user = (User) getUserFromSession(request);
		try{
			informationMGService.save(informationData);
		   // logger.info("全息图编辑项目相关信息["+"项目名称:"+project.getProjectName()+" 创建人:"+project.getCreateUname()+" 部门："+user.getDepartmentName()+"]");
		    responseBody.setResult(new Result(Status.OK, null,"编辑项目部分成功"));

		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"编辑项目部分数据失败"));
			logger.error("异常信息:",e);
		}
		return responseBody;
	}
	
	@ResponseBody
	@RequestMapping("/getTitleResults/{titleId}/{projectId}")
	public ResponseData<InformationTitle> getTitleResults(@PathVariable String titleId,@PathVariable String projectId)
	{
		ResponseData<InformationTitle> data = new ResponseData<>();
		
		try
		{
			List<InformationTitle> list = informationMGService.searchWithData(titleId, projectId);
			data.setEntityList(list);
			
		} catch (Exception e)
		{
			logger.error("获取标题失败，信息:titleId="+titleId,e);
			data.getResult().addError("获取标题失败");
		}
		
		return data;
	}
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
			
			InformationTitle title = informationMGService.selectAreaTitleResutl(pid, tcode);
			
			responseBody.setEntity(title);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "题和保存的结果信息获取失败"));
			logger.error("queryTitleInfo 题和保存的结果信息 : "+tcode,e);
		}
		
		return responseBody;
	}
	

	
}



