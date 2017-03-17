package com.galaxyinternet.hologram.controller;

import com.galaxyinternet.bo.hologram.InformationTitleBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationData;
import com.galaxyinternet.model.hologram.InformationListdata;
import com.galaxyinternet.model.hologram.InformationListdataRemark;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.hologram.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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


    @Autowired
    private InformationResultService informationResultService;

    @Autowired
    private InformationListdataRemarkService informationListdataRemarkService;


    @Override
    protected BaseService<InformationListdata> getBaseService() {
        return this.informationListdataService;
    }




    /**
     * 团队成员页面跳转
     */
    @RequestMapping(value = "/toTeamPage", method = RequestMethod.GET)
    public String toTeamPage(HttpServletRequest request) {
        return "project/tanchuan/v_person_learning";
    }

    /**
     * 成员简历保存
     */
    @RequestMapping(value = "/saveorUpdate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseData<InformationData> saveOrupdate(@RequestBody InformationData data){
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
     *根据projectId 和 titleId查询表格列表
     */
    @RequestMapping("/queryRowsList")
    @ResponseBody
    public ResponseData<InformationListdata> queryList(@RequestBody InformationListdata data){
        ResponseData<InformationListdata> resp = new ResponseData<>();
        Long projectId  = data.getProjectId();
        Long titleId  = data.getTitleId();
        if(null == projectId || null == titleId){
            resp.setResult(new Result(Result.Status.ERROR,null, "projectId或titleId缺失"));
            logger.error("queryRowsList 失败 :projectId或titleId缺失");
            return resp;
        }
        try{
            InformationListdataRemark remark = informationListdataRemarkService.queryByTitleId(data.getTitleId());
            if(remark == null){
                resp.setResult(new Result(Result.Status.ERROR,null, "titleId错误"));
                logger.error("queryRowsList 失败 :titleId错误");
                return resp;
            }
            String code = remark.getCode();
            data.setCode(code);
            data.setProjectId(projectId);
            List<InformationListdata> list = informationListdataService.queryList(data);
            resp.setEntityList(list);
        }catch(Exception e){
            resp.setResult(new Result(Result.Status.ERROR,null, "查询表格列表失败"));
            logger.error("queryRowsList 失败 ",e);
        }
        return resp;
    }

    /**
     * 根据id
     * 查看表格中的一条记录
     */
    @RequestMapping("/queryOneRow/{id}")
    @ResponseBody
    public ResponseData<InformationListdata> query(@PathVariable("id") Long id) {
        ResponseData<InformationListdata> resp = new ResponseData<>();
        if(null == id){
            resp.setResult(new Result(Result.Status.ERROR,null, "成员id缺失"));
            logger.error("queryOneRow 失败 : 成员id缺失");
            return resp;
        }
        try {
            InformationListdata data = informationListdataService.queryMemberById(id);
            if(data ==null){
                resp.setResult(new Result(Result.Status.ERROR,null, "id错误"));
                logger.error("queryOneRow 失败 : id错误");
                return resp;
            }
            resp.setEntity(data);
        }catch (Exception e){
            resp.setResult(new Result(Result.Status.ERROR,null, "查询团队成员失败"));
            logger.error("queryOneRow 失败 ",e);
        }
        return resp;
    }

    /**
     *  通过 id 删除一条记录
     */
    @RequestMapping("/deleteOneRow/{id}")
    @ResponseBody
    public ResponseData deleteOneMember(@PathVariable("id") Long id){
        ResponseData<InformationListdata> resp = new ResponseData<>();
        if(null == id){
            resp.setResult(new Result(Result.Status.ERROR,null, "projectId 或 titleId缺失"));
            logger.error("deleteOneMember 失败 : projectId或titleId或parentId缺失");
        }
        try{
            informationListdataService.deleteOneMember(id);
        }catch(Exception e){
            resp.setResult(new Result(Result.Status.ERROR,null, "deleteOneMember 失败"));
            logger.error("deleteOneMember 失败 ",e);
        }
        return resp;
    }

    /**
    *根据id查询一条工作经历或创业经历等
     */
  /*  @RequestMapping("/queryOneInnerRow/{id}")
    @ResponseBody
    public ResponseData<InformationListdata> queryOneRow(@PathVariable("id") Long id){
        ResponseData<InformationListdata> resp = new ResponseData<>();
        if(id == null){
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
    }*/
}
