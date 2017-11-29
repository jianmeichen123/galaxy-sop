package com.galaxyinternet.mongodb.controller;

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

import com.galaxyinternet.common.annotation.LogType;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationListdata;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.mongodb.model.InformationDataMG;
import com.galaxyinternet.mongodb.model.InformationListdataMG;
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
	
	 /*
     *根据projectId 和 titleId查询表格列表
     */
    @RequestMapping("/queryRowsList/{titleId}/{projectId}")
    @ResponseBody
    public ResponseData<InformationTitle> queryList(@PathVariable("titleId") String titleId, @PathVariable("projectId") String projectId ){
        ResponseData<InformationTitle> resp = new ResponseData<>();
        if(null == projectId || null == titleId){
            resp.setResult(new Result(Result.Status.ERROR,null, "projectId或titleId缺失"));
            logger.error("queryRowsList 失败 :projectId或titleId缺失");
            return resp;
        }
        try{
            List<InformationTitle> tvList = informationMGService.searchWithData(titleId,projectId);
            List<InformationListdataMG> resultList = new ArrayList<InformationListdataMG>();
            if(tvList.size()>0){
                //获取核心成员
                InformationTitle title = null;
                for(InformationTitle t:tvList){
                    if(t.getType()==10){
                        title = t;
                    }
                }
                List<InformationListdataMG> dataList = title.getDataMGList();

                if(dataList!=null && dataList.size()>0){
                    for(InformationListdataMG data :dataList){
                        if(data.getParentId()==null){
                            //查询成员的工作学习创业经历
                            InformationListdataMG info = informationMGService.queryMemberById(data.getUuid());
                            resultList.add(info);
                        }
                    }
                }
                title.setDataMGList(resultList);
            }
            resp.setEntityList(tvList);
        }catch(Exception e){
            resp.setResult(new Result(Result.Status.ERROR,null, "查询表格列表失败"));
            logger.error("queryRowsList 失败 ",e);
        }
        return resp;
    }
    
    @ResponseBody
	@RequestMapping("/removeData/{titleId}/{projectId}")
	public ResponseData<InformationTitle> removeData(@PathVariable String titleId,@PathVariable String projectId)
	{
		ResponseData<InformationTitle> data = new ResponseData<>();
		
		try
		{
			informationMGService.removeData(titleId, projectId);
			data.setResult(new Result(Status.OK,"清空草稿箱成功"));
		} catch (Exception e)
		{
			logger.error("清空草稿箱失败，信息:titleId="+titleId,e);
			data.getResult().addError("清空草稿箱失败");
		}
		
		return data;
	}
	

	
}


