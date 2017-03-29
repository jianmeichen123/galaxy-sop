package com.galaxyinternet.hologram.controller;

import com.galaxyinternet.bo.hologram.InformationTitleBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.utils.WebUtils;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.*;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.hologram.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;

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
    private InformationTitleService informationTitleService;

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
        return "hologram/teamInfo";
    }

    /**
     * 成员简历保存
     */
    @RequestMapping(value = "/saveorUpdate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseData<InformationListdata> saveOrupdate(@RequestBody InformationListdata data){
        ResponseData<InformationListdata> responseBody = new ResponseData<>();
        try{
            List<InformationListdata> listdataList = data.getDataList();
            //先删除之前的成员列表
            InformationListdata query = new InformationListdata();
            query.setTitleId(listdataList.get(0).getTitleId());
            query.setProjectId(listdataList.get(0).getProjectId());
            informationListdataService.delete(query);
            if(listdataList != null && !listdataList.isEmpty()){
                for (InformationListdata entity : listdataList){
                    if(null != entity.getCode() && entity.getCode().equals("team-members")){
                        entity.setCreateTime(System.currentTimeMillis());
                        informationListdataService.insert(entity);
                        Long id = entity.getId();
                        List<InformationListdata> studyList = entity.getStudyList();
                        List<InformationListdata> workList = entity.getWorkList();
                        List<InformationListdata> startupList = entity.getStartupList();
                        if(studyList!=null &&!studyList.isEmpty()){
                            for(InformationListdata study:studyList){
                                study.setParentId(id);
                            }
                            informationListdataService.insertInBatch(studyList);
                        }
                        if(workList!=null &&!workList.isEmpty()){
                            for(InformationListdata work:workList){
                                work.setParentId(id);
                            }
                            informationListdataService.insertInBatch(workList);
                        }
                        if(startupList!=null &&!startupList.isEmpty()){
                            for(InformationListdata startup:startupList){
                                startup.setParentId(id);
                            }
                            informationListdataService.insertInBatch(startupList);
                        }

                    }
                }
            }
        }catch(Exception e ){
            responseBody.setResult(new Result(Result.Status.ERROR,null, "保存失败"));
            logger.error("save 保存失败 ",e);
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
            List<InformationTitle> tvList = informationTitleService.searchWithData(titleId,projectId);
            List<InformationListdata> resultList = new ArrayList<InformationListdata>();
            if(tvList.size()>0){
                //获取核心成员
                InformationTitle title = null;
                for(InformationTitle t:tvList){
                    if(t.getType()==10){
                        title = t;
                    }
                }
                List<InformationListdata> dataList = title.getDataList();

                if(dataList!=null && dataList.size()>0){
                    for(InformationListdata data :dataList){
                        if(data.getParentId()==null){
                            //查询成员的工作学习创业经历
                            InformationListdata info = informationListdataService.queryMemberById(data.getId());
                            resultList.add(info);
                        }
                    }
                }
                title.setDataList(resultList);
            }
            resp.setEntityList(tvList);
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


    @ResponseBody
    @RequestMapping("/saveRow")
    public ResponseData<InformationListdata> saveRow(@RequestBody InformationListdata row)
    {
        ResponseData<InformationListdata> data = new ResponseData<InformationListdata>();
        User user = WebUtils.getUserFromSession();
        if(row.getProjectId() == null || row.getTitleId() == null)
        {
            data.getResult().addError("信息不完整");
            return data;
        }
        Long now = new Date().getTime();
        Long uId = user.getId();
        if(row.getId() == null)
        {
            row.setCreatedTime(now);
            row.setCreateId(uId);
            informationListdataService.insert(row);
        }
        else
        {
            row.setUpdatedTime(now);
            row.setUpdateId(uId);
            informationListdataService.updateById(row);
        }
        return data;
    }
    
    /*
     *根据projectId 和 titleId查询表格列表
     */
    /**
     * app端获取团队列表 2017/3/27
     * @param data
     * @return
     */
    @RequestMapping("/querySelectList")
    @ResponseBody
    public ResponseData<InformationListdata> querySelectList(@RequestBody InformationListdata data){
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
            data.setProperty("created_time");
            data.setDirection(Direction.DESC.toString());
            List<InformationListdata> list = informationListdataService.queryList(data);
            resp.setEntityList(list);
        }catch(Exception e){
            resp.setResult(new Result(Result.Status.ERROR,null, "查询表格列表失败"));
            logger.error("querySelectList 失败 ",e);
        }
        return resp;
    }
    /**
     * app端获取更多2017/3/28
     * @param data
     * @return
     */
    @RequestMapping("/queryResultList")
    @ResponseBody
    public ResponseData<InformationListdata> queryResultList(@RequestBody InformationListdata data){
        ResponseData<InformationListdata> resp = new ResponseData<>();
        Long projectId  = data.getProjectId();
        Long titleId  = data.getTitleId();
        if(null == projectId || null == titleId){
            resp.setResult(new Result(Result.Status.ERROR,null, "projectId或titleId缺失"));
            logger.error("queryRowsList 失败 :projectId或titleId缺失");
            return resp;
        }
        try{
           
            List<InformationListdata> list = informationListdataService.queryList(data);
            resp.setEntityList(list);
        }catch(Exception e){
            resp.setResult(new Result(Result.Status.ERROR,null, "查询表格列表失败"));
            logger.error("queryResultList 失败 ",e);
        }
        return resp;
    }

}
