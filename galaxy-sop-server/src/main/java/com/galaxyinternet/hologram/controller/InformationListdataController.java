package com.galaxyinternet.hologram.controller;

import com.galaxyinternet.bo.hologram.InformationTitleBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationData;
import com.galaxyinternet.model.hologram.InformationListdata;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.service.hologram.InformationDataService;
import com.galaxyinternet.service.hologram.InformationListdataService;
import com.galaxyinternet.service.hologram.InformationTitleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by zcy on 17-3-13.
 */

@Controller
@RequestMapping("/galaxy/team")
public class InformationListdataController extends BaseControllerImpl<InformationListdata,InformationListdata> {

    final Logger logger = LoggerFactory.getLogger(InfoProjectController.class);

    @Autowired
    private InformationListdataService informationListdataService;

    @Autowired
    private InformationDataService infoDataService;

    @Override
    protected BaseService<InformationListdata> getBaseService() {
        return this.informationListdataService;
    }

    /**
     * 成员简历保存
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResponseData<InformationData> save(@RequestBody InformationData data){
        ResponseData<InformationData> responseBody = new ResponseData<>();
        try{
            infoDataService.save(data);
        }catch(Exception e ){
            responseBody.setResult(new Result(Result.Status.ERROR,null, "保存失败"));
            logger.error("save 保存失败 ",e);
        }
        return responseBody;
    }

    /*
     *根据projectId 和 titleId查询成员基本信息列表
     */
    @RequestMapping("/queryMemberList")
    @ResponseBody
    public ResponseData<InformationListdata> queryList(@RequestBody InformationListdata data){
        ResponseData<InformationListdata> resp = new ResponseData<>();
        Long projectId  = data.getProjectId();
        Long titleId = data.getTitleId();
        if(null == projectId || null == titleId){
            resp.setResult(new Result(Result.Status.ERROR,null, "projectId或titleId缺失"));
            logger.error("queryMemberList 失败 :projectId或titleId缺失");
            return resp;
        }
        try{
            data.setCode("team");
            List<InformationListdata> list = informationListdataService.queryList(data);
            resp.setEntityList(list);
        }catch(Exception e){
            resp.setResult(new Result(Result.Status.ERROR,null, "查询团队成员列表失败"));
            logger.error("queryMemberList 失败 ",e);
        }
        return resp;
    }

    /**
     * 通过 projectId ,titleId ,parentId(人员id) 查询成员简历
     */
    @RequestMapping("/queryOneMember")
    @ResponseBody
    public ResponseData<InformationListdata> query(@RequestBody InformationListdata data ) {
        ResponseData<InformationListdata> resp = new ResponseData<>();
        if(null == data.getProjectId() || null == data.getTitleId() || null == data.getParentId()){
            resp.setResult(new Result(Result.Status.ERROR,null, "projectId 或 titleId缺失"));
            logger.error("queryOneMember 失败 : projectId或titleId或parentId缺失");
        }
        try {
            data = informationListdataService.queryOne(data);
            resp.setEntity(data);
        }catch (Exception e){
            resp.setResult(new Result(Result.Status.ERROR,null, "查询团队成员失败"));
            logger.error("queryOneMember 失败 ",e);
        }
        return resp;
    }

    /**
     *根据id查询学习经历,工作经历,创业经历
     */
    @RequestMapping("/queryOneRow/{id}")
    @ResponseBody
    public ResponseData<InformationListdata> queryOneRow(@PathVariable("id") Long id){
        ResponseData<InformationListdata> resp = new ResponseData<>();
        if(id != null){
            resp.setResult(new Result(Result.Status.ERROR,null, "id缺失"));
            logger.error("queryOneRow 失败 : id缺失");
        }
        try{
            InformationListdata data  = informationListdataService.queryById(id);
            resp.setEntity(data);
        }catch(Exception e){
            resp.setResult(new Result(Result.Status.ERROR,null, "queryOneRow失败"));
            logger.error("queryOneRow 失败 ",e);
        }
        return resp;
    }

    /**
     * 删除学习经历,工作经历,创业经历
     */
    @RequestMapping("/deleteOneRow/{id}")
    @ResponseBody
    public ResponseData deleteOneRow(@PathVariable("id") Long id){
        ResponseData<InformationListdata> resp = new ResponseData<>();
        if(id != null){
            resp.setResult(new Result(Result.Status.ERROR,null, "id缺失"));
            logger.error("deleteOneRow 失败 : id缺失");
        }
        try{
            informationListdataService.deleteById(id);
        }catch(Exception e){
            resp.setResult(new Result(Result.Status.ERROR,null, "deleteOneRow 失败"));
            logger.error("deleteOneRow 失败 ",e);
        }
        return resp;
    }
}
