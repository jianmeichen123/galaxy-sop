package com.galaxyinternet.hologram.controller;

import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.utils.WebUtils;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.InformationListdata;
import com.galaxyinternet.model.hologram.InformationListdataRemark;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.hologram.InformationDataService;
import com.galaxyinternet.service.hologram.InformationListdataRemarkService;
import com.galaxyinternet.service.hologram.InformationListdataService;
import com.galaxyinternet.service.hologram.InformationProgressService;
import com.galaxyinternet.service.hologram.InformationResultService;
import com.galaxyinternet.service.hologram.InformationTitleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private InformationProgressService informationProgressService;

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
    public ResponseData<InformationListdata> saveOrupdate(@RequestBody InformationListdata data,HttpServletRequest request){
        ResponseData<InformationListdata> responseBody = new ResponseData<>();
        User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
        try{

            List<InformationListdata> listdataList = data.getDataList();
            
            InformationListdata query = new InformationListdata();
            query.setTitleId(data.getTitleId());
            query.setProjectId(data.getProjectId());
            List<InformationListdata> poList = informationListdataService.queryList(query);
            Set<String> ids = new HashSet<>(poList.size());
            for(InformationListdata po : poList)
            {
            	ids.add(po.getId()+"");
            }
            if(listdataList != null && !listdataList.isEmpty()){
                for (InformationListdata entity : listdataList){
                    if(null != entity.getCode() && entity.getCode().equals("team-members")){
                    	removeIfExists(ids,entity.getId()+"");
                        informationListdataService.save(entity);
                        Long id = entity.getId();
                        List<InformationListdata> studyList = entity.getStudyList();
                        List<InformationListdata> workList = entity.getWorkList();
                        List<InformationListdata> startupList = entity.getStartupList();
                        if(studyList!=null &&!studyList.isEmpty()){
                            for(InformationListdata study:studyList){
                            	removeIfExists(ids,study.getId()+"");
                                study.setParentId(id);
                            }
                            informationListdataService.saveBatch(studyList);
                        }
                        if(workList!=null &&!workList.isEmpty()){
                            for(InformationListdata work:workList){
                            	removeIfExists(ids,work.getId()+"");
                                work.setParentId(id);
                            }
                            informationListdataService.saveBatch(workList);
                        }
                        if(startupList!=null &&!startupList.isEmpty()){
                            for(InformationListdata startup:startupList){
                            	removeIfExists(ids,startup.getId()+"");
                                startup.setParentId(id);
                            }
                            informationListdataService.saveBatch(startupList);
                        }

                    }
                }
               
            }
            if(ids.size() > 0)
            {
            	query = new InformationListdata();
            	query.setIds(ids);
            	informationListdataService.delete(query);
            }

            informationProgressService.threadForUpdate(user.getId(),data.getProjectId());
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
    
    private void removeIfExists(Set<String> ids, String id)
    {
    	if(ids.size()>0)
    	{
    		if(ids.contains(id))
    		{
    			ids.remove(id);
    		}
    	}
    }

}
