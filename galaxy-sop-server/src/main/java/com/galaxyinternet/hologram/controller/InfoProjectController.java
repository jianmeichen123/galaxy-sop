package com.galaxyinternet.hologram.controller;


import com.galaxyinternet.common.annotation.LogType;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationData;
import com.galaxyinternet.model.hologram.InformationDictionary;
import com.galaxyinternet.model.hologram.InformationListdataRemark;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.hologram.InformationDataService;
import com.galaxyinternet.service.hologram.InformationDictionaryService;
import com.galaxyinternet.service.hologram.InformationListdataRemarkService;
import com.galaxyinternet.service.hologram.InformationProgressService;
import com.galaxyinternet.service.hologram.InformationTitleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("全息图后台接口")
@Controller
@RequestMapping("/galaxy/infoProject")
public class InfoProjectController  extends BaseControllerImpl<InformationData, InformationData> {

	final Logger logger = LoggerFactory.getLogger(InfoProjectController.class);
	
	
	@Autowired
	private InformationDataService infoDataService;
	@Autowired
	private InformationTitleService titleService;
	@Autowired
	private InformationDictionaryService infoDictionaryService;
	@Autowired
	private InformationListdataRemarkService  infoListdataRemarkService;
	@Autowired
	private InformationProgressService informationProgressService;
	@Override
	protected BaseService<InformationData> getBaseService() {
		return this.infoDataService;
	}
	@Autowired
	private ProjectService projectService;
	
	
	/**
	 * 全息图-项目部分数据处理页面
	 * @version 
	 * @return
	 */
	@RequestMapping(value = "/toInfoProjectPage", method = RequestMethod.GET)
	public String toInfoProjectPage(HttpServletRequest request) {
		return "project/tanchuan/v_person_learning";
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
	public ResponseData<Project> saveOrUpdateInfo(
			@ApiParam(name = "informationData", value = "标题/值信息", required = true)
			@RequestBody 
			InformationData informationData,
			HttpServletRequest request) {
		ResponseData<Project> responseBody = new ResponseData<Project>();
		if(null==informationData.getProjectId()||"".equals(informationData.getProjectId())){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
	    Long projectId=Long.parseLong(informationData.getProjectId());
	    Project project = projectService.queryById(projectId);
		User user = (User) getUserFromSession(request);
		try{
			infoDataService.save(informationData);
		    logger.info("全息图编辑项目相关信息["+"项目名称:"+project.getProjectName()+" 创建人:"+project.getCreateUname()+" 部门："+user.getDepartmentName()+"]");
		    responseBody.setResult(new Result(Status.OK, null,"编辑项目部分成功"));

			informationProgressService.threadForUpdate(user.getId(),projectId);
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
			List<InformationTitle> list = titleService.searchWithData(titleId, projectId);
			data.setEntityList(list);
			
		} catch (Exception e)
		{
			logger.error("获取标题失败，信息:titleId="+titleId,e);
			data.getResult().addError("获取标题失败");
		}
		
		return data;
	}
	@ApiOperation("查询页面标题及结果")
	@ApiImplicitParams(
		value = {
			@ApiImplicitParam(name="reportType", value="报告类型",paramType="path",required=true),
			@ApiImplicitParam(name="realteId", value="标题relate id",paramType="path",required=true),
			@ApiImplicitParam(name="projectId", value="项目id",paramType="path",required=true)
		}	
	)
	@ResponseBody
	@RequestMapping(value="/getRelateTitleResults/{reportType}/{realteId}/{projectId}",method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InformationTitle> getRelateTitleResults(@PathVariable Integer reportType,@PathVariable Long realteId, @PathVariable Long projectId)
	{
		ResponseData<InformationTitle> data = new ResponseData<>();
		try
		{
			List<InformationTitle> list = titleService.searchRelateTitleWithData(reportType, realteId, projectId);
			data.setEntityList(list);
			
		} catch (Exception e)
		{
			logger.error("获取标题失败，信息:realteId="+realteId,e);
			data.getResult().addError("获取标题失败");
		}
		return data;
	}
	/**
	 * 数据字典加载
	 * @param parentTitleId
	 * @param subCode
	 * @param fileds
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDirectory/{tittleId}/{subCode}/{filed}", method = RequestMethod.GET)
	public ResponseData<InformationTitle> getDirectory(@PathVariable String tittleId, @PathVariable String subCode,
			@PathVariable String filed)

	{
		ResponseData<InformationTitle> data = new ResponseData<>();
		try {
			InformationListdataRemark query = new InformationListdataRemark();
			query.setCode(subCode);
			query.setTitleId(Long.parseLong(tittleId));
			Map<String, Object> map =
					new HashMap<String, Object>();
			InformationListdataRemark queryOne = infoListdataRemarkService.queryOne(query);
			if (null != queryOne) {
				{
					  List<InformationDictionary> dircList=
		  		                new ArrayList<InformationDictionary>();
					String index = filed.substring(5);
					String getSubCode = getSubCode(queryOne, index);
					dircList = infoDictionaryService
							.selectValuesByTid(Long.parseLong(getSubCode));
					map.put(filed, dircList);
				}
			}
			data.getResult().setStatus(Status.OK);
			data.setUserData(map);
		} catch (Exception e) {
			logger.error("加载数据字典失败，信息:titileId=" + tittleId, e);
			data.getResult().addError("加载数据字典失败");
		}
		return data;
	}
	
	public String getSubCode(InformationListdataRemark query,String index){
		String str="";
		switch(Integer.parseInt(index)){
		case 1:
			str=query.getSubTitleId1();
			break;
		case 2:
			str=query.getSubTitleId2();
			break;
		case 3:
			str=query.getSubTitleId3();
			break;
		case 4:
			str=query.getSubTitleId4();
			break;
		case 5:
			str=query.getSubTitleId5();
			break;
		case 6:
			str=query.getSubTitleId6();
			break;
		case 7:
			str=query.getSubTitleId7();
			break;
		case 8:
			str=query.getSubTitleId8();
			break;
		case 9:
			str=query.getSubTitleId9();
			break;
		case 10:
			str=query.getSubTitleId10();
			break;
		}
		return str;
	}
}



