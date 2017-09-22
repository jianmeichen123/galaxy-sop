package com.galaxyinternet.hologram.service;


import com.aliyun.oss.ServiceException;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.galaxyinternet.dao.hologram.InformationFixedTableDao;
import com.galaxyinternet.dao.hologram.InformationListdataDao;
import com.galaxyinternet.dao.hologram.InformationListdataRemarkDao;
import com.galaxyinternet.dao.hologram.InformationResultDao;
import com.galaxyinternet.dao.hologram.InformationTitleDao;
import com.galaxyinternet.dao.hologram.InformationTitleRelateDao;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.oss.OSSFactory;
import com.galaxyinternet.framework.core.thread.GalaxyThreadPool;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.hologram.util.ExportDataConversionTask;
import com.galaxyinternet.hologram.util.ListSortUtil;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.hologram.InformationFile;
import com.galaxyinternet.model.hologram.InformationFixedTable;
import com.galaxyinternet.model.hologram.InformationListdata;
import com.galaxyinternet.model.hologram.InformationResult;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.platform.constant.PlatformConst;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.UserService;
import com.galaxyinternet.service.hologram.ReportExportService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;


@Service("com.galaxyinternet.service.hologram.ReportExportService")
@Order
public class ReportExportServiceImpl implements ReportExportService {

    final Logger logger = LoggerFactory.getLogger(ReportExportServiceImpl.class);

    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private InformationTitleRelateDao informationTitleRelateDao;
    @Autowired
    private InformationResultDao resultDao;
    @Autowired
    private InformationFixedTableDao fixedTableDao;
    @Autowired
    private InformationListdataDao listDataDao;
    @Autowired
    private InformationTitleDao informationTitleDao;
    @Autowired
    private InformationListdataRemarkDao headerDao;


    @Autowired
    private Cache cache;

    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;



    /*
    replace(/\<br\>/g,'\n')  <w:br />
    replace(/&nbsp;/g," ")
    replace(/&amp;/g,"&")
    replace(/&gt;/g,">")
    replace(/&lt;/g,"<")
    replace(/<.*?>/g,"").
    */
    public String textConversion(String text){
        if(StringUtils.isBlank(text)){
            return text;
        }

        String result = text.replace("<br>","<w:br />").replace("&nbsp;"," ")
                .replace("&amp;","&").replace("&gt;",">").replace("&lt;","<");

        return result;
    }

    /**
     * 查询出题答案
     *
     * NO  全息报告
     * DN  尽调报告
     * PN  决策报告
     * GN  融资报告
     * ON  运营报告
     * EN  评测报告
     * CN  初评报告
     */
    public Map<String, Object> titleAnswerConversionTask(Long uid, Project project, String preCode){
        Long btime = System.currentTimeMillis();

        Map<String, Object> map = new HashMap<>();
        try {
            Map<Long, String> valueIdNameMap = (Map<Long, String>) cache.get(CacheOperationServiceImpl.CACHE_KEY_VALUE_ID_NAME);

            ForkJoinPool pool = GalaxyThreadPool.getForkJoinPool();
            ExportDataConversionTask task = new ExportDataConversionTask(preCode,project.getId(),null,null,valueIdNameMap);
            ForkJoinTask<Map<String,Object>> result = pool.submit(task);

            map = result.get();
        } catch (Exception e) {
            throw new ServiceException("titleAnswerConversionTask : 失败 ", e);
        }

        logger.debug("======================  titleAnswerConversionTask  用时 ： "  +  (System.currentTimeMillis() -  btime));
        return map;
    }
    public Map<String, Object> titleAnswerConversion(Long uid, Long proId, String preCode){
        Long btime = System.currentTimeMillis();

        Map<Long, String> valueIdNameMap = (Map<Long, String>) cache.get(CacheOperationServiceImpl.CACHE_KEY_VALUE_ID_NAME);

        Map<String, Set<Long>> titletype_titleIds = CacheOperationServiceImpl.code_titletype_titleIds.get(StringUtils.isBlank(preCode)?"NO":preCode);

        Set<Long> project_ids = titletype_titleIds.get("project");
        Set<Long> result_ids = titletype_titleIds.get("result");
        Set<Long> listdata_ids = titletype_titleIds.get("listdata");
        Set<Long> fixedtable_ids = titletype_titleIds.get("fixedtable");
        Set<Long> file_ids = titletype_titleIds.get("file");
        //Set<Long> resultGrage_ids = titletype_titleIds.get("resultGrage");

        Map<String, Object> map1 = projectTitleResult(project_ids,proId);
        Map<String, Object> map2 = resultTitleResult(result_ids,proId,valueIdNameMap);
        Map<String, Object> map3 = listdataTitleResult(listdata_ids,proId, valueIdNameMap);
        Map<String, Object> map4 = fixedtableTitleResult(fixedtable_ids, proId, valueIdNameMap);
        Map<String, Object> map5 = fileTitleResult(file_ids, proId);

        map2.putAll(map1);
        map2.putAll(map3);
        map2.putAll(map4);
        map2.putAll(map5);

        logger.debug("======================  titleAnswerConversion 用时 ： "  +  (System.currentTimeMillis() -  btime));
        return map2;
    }




    /**
     * 项目基本信息查询
     * 项目编号     NO1_1_1
     * 项目负责人   NO1_1_2
     * 项目合伙人   NO1_1_3
     * 隶属事业部   NO1_1_4
     *
     * 项目名称 NO1_1_1_n
     *
     */
    public Map<String,Object> projectTitleResult(Set<Long> ids,Long projectId){
        Map<String, Object> map = new HashMap<>();

        if(ids == null || ids.isEmpty()){
            return  map;
        }

        try{
            Project project = projectDao.selectById(projectId);
            map.put("NO1_1_1", project.getProjectCode());
            map.put("NO1_1_1_n", project.getProjectName());

            User createUser = userService.queryById(project.getCreateUid());
            //project.setCreateUname(createUser.getRealName());
            map.put("NO1_1_2", createUser.getRealName());

            Department department = departmentService.queryById(project.getProjectDepartid());
            //project.setProjectCareerline(department.getName());
            map.put("NO1_1_4", department.getName());

            User managerUser = userService.queryById(department.getManagerId());
            //project.setHhrName(managerUser.getRealName());
            map.put("NO1_1_3", managerUser.getRealName());
        }catch (Exception e){
            logger.error("projectTitleResult ",e);
            //throw new RuntimeException("ReportDataConversion projectTitleResult 项目基础信息查询失败",e);
        }

        return map;
    }


    /**
     * result 结果表 结果查询封装
     * title-type :  ,1,2,3,4,5,6,8,12,13,14,15,16,18,19,20,
     * 【6 18 无】
     *
     */
    public Map<String,Object> resultTitleResult(Set<Long> ids,Long projectId, Map<Long, String> valueIdNameMap){
        Map<String, Object> map = new HashMap<>();

        if(ids == null || ids.isEmpty()){
            return  map;
        }

        Map<String, Object> params = new HashMap<String,Object>();
        params.put("titleIds",ids);
        params.put("projectId",projectId);
        params.put("notAllNUll",true);
        List<InformationTitle> titleList = informationTitleDao.selectTitleOfResults(params);

        if(titleList == null || titleList.isEmpty()){
            return  map;
        }

        String value = null;
        String contact = "、";

        for(InformationTitle tempTitle : titleList)
        {
            value = null;
            contact = "、";

            List<InformationResult> resultList = tempTitle.getResultList();
            if(resultList!=null && !resultList.isEmpty())
            {

                if(tempTitle.getType().intValue() == 1 || tempTitle.getType().intValue() == 8
                        || tempTitle.getType().intValue() == 2 || tempTitle.getType().intValue() == 14 )
                {
                    // 1:文本、 2: 单选（Radio）、 8:文本域(textarea)、 14 单选（select）、
                    //  map : code-value
                    InformationResult tempResult = resultList.get(0);
                    if(StringUtils.isNotBlank(tempResult.getContentChoose())){
                        value = valueIdNameMap.get(new Long(tempResult.getContentChoose()));
                    }else{
                        value = tempResult.getContentDescribe1();
                        value = textConversion(value);
                    }

                    if(StringUtils.isNotBlank(value)){
                        map.put(tempTitle.getCode(), value);
                    }
                    //singleValueCon(tempTitle, map, valueIdNameMap);
                }else if(tempTitle.getType().intValue() == 3 || tempTitle.getType().intValue() == 4 )
                {
                    // 3:复选（、 号连接）、4:级联选择（-  号连接）<br>
                    // map : code-value
                    contact = tempTitle.getType().intValue() == 3?"、":" - ";

                    StringBuffer stringBuffer = new StringBuffer();
                    for(int i = 0; i<resultList.size(); i++)
                    {
                        if(resultList.get(i).getContentChoose() != null)
                        {
                            value = valueIdNameMap.get(new Long(resultList.get(i).getContentChoose()));

                            if(StringUtils.isNotBlank(value)){
                                if(stringBuffer.length()>0){
                                    stringBuffer.append(contact).append(value);
                                }else{
                                    stringBuffer.append(value);
                                }
                            }
                        }
                    }
                    if(stringBuffer.length()>0){
                        map.put(tempTitle.getCode(), stringBuffer.toString());
                    }
                    //type34ValueCon(tempTitle, map, valueIdNameMap,tempTitle.getType().intValue() == 3?"、":" - ");
                }else if(tempTitle.getType().intValue() == 5 || tempTitle.getType().intValue() == 6 )
                {
                    // 5:单选带备注(textarea) 、【6:复选带备注(textarea) 无该类型】
                    // 仅显示 选项
                    //  map : code-value
                    for(int i = 0; i<resultList.size(); i++)
                    {
                        if(resultList.get(i).getContentChoose() != null)
                        {
                            value = valueIdNameMap.get(new Long(resultList.get(i).getContentChoose()));
                            if(StringUtils.isNotBlank(value)){
                                map.put(tempTitle.getCode(), value);
                                break;
                            }
                        }
                    }
                    //type56ValueCon(tempTitle, map, valueIdNameMap);
                }else if(tempTitle.getType().intValue() == 12 || tempTitle.getType().intValue() == 13 )
                {
                    // 12:单选带备注(input)--有 contentDescribe1 就不要 ContentChoose 、
                    // 13:复选带备注(input)--有 contentDescribe1 就不要 ContentChoose
                    // map : code-value
                    StringBuffer stringBuffer = new StringBuffer();

                    for(int i = 0; i<resultList.size(); i++)
                    {
                        value = null;
                        if(StringUtils.isNotBlank(resultList.get(i).getContentDescribe1())){
                            value = resultList.get(i).getContentDescribe1();
                        }else if(StringUtils.isNotBlank(resultList.get(i).getContentChoose()))
                        {
                            if(StringUtils.isNotBlank(valueIdNameMap.get(new Long(resultList.get(i).getContentChoose())))){
                                value = valueIdNameMap.get(new Long(resultList.get(i).getContentChoose()));
                                if(value.contains("其他")){
                                    continue;
                                }
                            }
                        }

                        if(value != null){
                            if(stringBuffer.length()>0){
                                stringBuffer.append(contact).append(value);
                            }else{
                                stringBuffer.append(value);
                            }
                        }
                    }
                    if(stringBuffer.length()>0){
                        map.put(tempTitle.getCode(), stringBuffer.toString());
                    }
                    //type1213ValueCon(tempTitle, map, valueIdNameMap);
                }else if(tempTitle.getType().intValue() == 15 )
                {
                    // 15 一个标题带两个文本域(textarea)、
                    // map : code-List String
                    List<String> mapValue = new ArrayList<>();
                    for(int i = 0; i<resultList.size(); i++)
                    {
                        if(StringUtils.isNotBlank(resultList.get(i).getContentDescribe1())){
                            mapValue.add(textConversion(resultList.get(i).getContentDescribe1()));
                        }

                        if(StringUtils.isNotBlank(resultList.get(i).getContentDescribe2())){
                            mapValue.add(textConversion(resultList.get(i).getContentDescribe2()));
                        }
                    }

                    if(!mapValue.isEmpty()){
                        map.put(tempTitle.getCode(), mapValue);
                    }
                    //type15ValueCon(tempTitle, map);
                }else if(tempTitle.getType().intValue() == 16 )
                {
                    // 16 多个文本框内容--组装一条显示  str=str.replace(/<sitg>/g,'（').replace(/<\/sitg>/g,'）');
                    // map : code-value
                    InformationResult tempResult = resultList.get(0);
                    if(StringUtils.isNotBlank(tempResult.getContentDescribe1()) && tempResult.getContentDescribe1().contains("sitg")){
                        value = tempResult.getContentDescribe1().replace("<sitg>","（").replace("</sitg>","）");
                        map.put(tempTitle.getCode(), textConversion(value));
                    }
                    //type16ValueCon(tempTitle, map);
                }else if(tempTitle.getType().intValue() == 19 || tempTitle.getType().intValue() == 20 )
                {
                    // 19：文本框输入题目答案带单位的处理；(input)，单位在 title.content <br>
                    // 20：文本框输入题目答案带单位的处理；(input)，单位在 title.content + result.ContentDescribe2
                    // map : code-value
                    InformationResult tempResult = resultList.get(0);
                    if(StringUtils.isNotBlank(tempResult.getContentDescribe1())){
                        String unit = tempTitle.getContent();
                        if(StringUtils.isNotBlank(tempResult.getContentDescribe2())){
                            unit += tempResult.getContentDescribe2().substring(0,tempResult.getContentDescribe2().indexOf("p"));
                        }
                        map.put(tempTitle.getCode(), tempResult.getContentDescribe1()+unit);
                    }
                    //type1920ValueCon(tempTitle, map);
                }
            }
        }

        return map;
    }



    /**
     * 动态表格数据
     * map remarkCode - list InformationListdata
     */
    public Map<String,Object> listdataTitleResult(Set<Long> ids,Long projectId, Map<Long, String> valueIdNameMap) {
        Map<String, Object> map = new HashMap<>();

        if(ids == null || ids.isEmpty()){
            return map;
        }

        Set<String> title_tableids = new TreeSet<>();
        for(Long temp : ids){
            title_tableids.add(temp+"");
        }

        //查询表格
        InformationListdata listdataQuery = new InformationListdata();
        listdataQuery.setProjectId(projectId);
        listdataQuery.setTitleIds(title_tableids);
        listdataQuery.setProperty("project_id,created_time");
        listdataQuery.setDirection(Sort.Direction.ASC.toString());
        List<InformationListdata> listdataList = listDataDao.selectList(listdataQuery);

        if(listdataList == null || listdataList.isEmpty()) {
            return map;
        }

        String remarkCode = null;
        String codeReplace = null;
        String preField = "field";
        List<InformationListdata> listDataResult = null;
        for(InformationListdata item : listdataList)
        {
            listDataResult = null;
            if(StringUtils.isNotBlank(item.getCode())){
                remarkCode = item.getCode();
            }else{
                remarkCode = CacheOperationServiceImpl.table_tid_remarkCode.get(item.getTitleId()+"");
            }
            if(StringUtils.isNotBlank(remarkCode)) codeReplace = remarkCode.replace("-","_");

            if(item.getUpdateId() != null){
                item.setUpdateUserName((String)cache.hget(PlatformConst.CACHE_PREFIX_USER+item.getUpdateId(), "realName"));
            }else if(item.getCreateId() != null){
                item.setUpdateUserName((String)cache.hget(PlatformConst.CACHE_PREFIX_USER+item.getCreateId(), "realName"));
            }

            if(item.getUpdateTime() != null){
                item.setUpdateTimeStr(DateUtil.longToString(item.getUpdateTime()));
            }else if(item.getCreateTime() != null){
                item.setUpdateTimeStr(DateUtil.longToString(item.getCreateTime()));
            }

            for(int i=1; i<11;i++){
                try {
                    String tid = CacheOperationServiceImpl.table_remarkCode_field_tid.get(remarkCode).get(preField+i);
                    Integer type = CacheOperationServiceImpl.table_fieldTid_type.get(tid);

                    String newStr=preField.substring(0, 1).toUpperCase()+preField.replaceFirst("\\w","");
                    String methodStr="get"+newStr+i;
                    Method getMethod = InformationListdata.class.getMethod(methodStr);
                    String fieldValue = (String) getMethod.invoke(item);
                    if(StringUtils.isNotBlank(fieldValue))
                    {
                        String value = tableFieldValueCon(fieldValue, type==null?0:type, valueIdNameMap);

                        String setMethodStr = "set"+newStr+i;
                        Method setMethod = InformationListdata.class.getMethod(setMethodStr,String.class);
                        setMethod.invoke(item,value);
                    }
                } catch (Exception e) {
                    logger.error("listdataTitleResult 动态表格数据 ",e);
                }
            }
            /* if(StringUtils.isNotBlank(item.getField1())){
                String tid = table_remarkCode_field_tid.get(remarkCode).get("field1");
                Integer type = table_fieldTid_type.get(tid);
                item.setField1(tableFieldValueCon(item.getField1(), type==null?0:type, valueIdNameMap));
            }*/

            if(map.containsKey(codeReplace)){
                listDataResult = (List<InformationListdata>)  map.get(codeReplace);

                listDataResult.add(item);
                //map.put(codeReplace,listDataResult);
            }else{
                listDataResult = new ArrayList<>();

                listDataResult.add(item);
                map.put(codeReplace,listDataResult);
            }

        }


        /*
         * 排序需求 写死
         * equity_structure    field2
         * investor_situation
         */
        ListSortUtil<InformationListdata> sortList = new ListSortUtil<InformationListdata>();
        if(map.containsKey("equity_structure")){
            sortList.sortNumForNull((List<InformationListdata>)  map.get("equity_structure"),"field2","desc");
        }
        if(map.containsKey("investor_situation")){
            sortList.sortNumForNull((List<InformationListdata>)  map.get("investor_situation"),"field4","desc");
        }


        /*
         * 嵌套类表格写死
         * 团队成员名单    team-members
         *      学习经历   study-experience             studyList
         *      工作经历   work-experience              workList
         *      创业经历   entrepreneurial-experience   startupList
         */
        if(map.containsKey("team_members")){
            listDataResult = (List<InformationListdata>)  map.get("team_members");

            List<InformationListdata> studylistData = new ArrayList<>();
            List<InformationListdata> worklistData = new ArrayList<>();
            List<InformationListdata> uplistData = new ArrayList<>();
            if(map.containsKey("study_experience")) studylistData = (List<InformationListdata>)  map.get("study_experience");
            if(map.containsKey("work_experience")) worklistData = (List<InformationListdata>)  map.get("work_experience");
            if(map.containsKey("entrepreneurial_experience")) uplistData = (List<InformationListdata>)  map.get("entrepreneurial_experience");

            //List<InformationListdata> listTemp = null;
            for (InformationListdata listdata : listDataResult)
            {
                for (InformationListdata dataTemp : studylistData) {
                    if(dataTemp.getParentId().longValue() == listdata.getId().longValue()){
                        if(listdata.getStudyList() == null){
                            listdata.setStudyList(new ArrayList<InformationListdata>());
                        }
                        listdata.getStudyList().add(dataTemp);
                    }
                }
                for (InformationListdata dataTemp : worklistData) {
                    if(dataTemp.getParentId().longValue() == listdata.getId().longValue()){
                        if(listdata.getWorkList() == null){
                            listdata.setWorkList(new ArrayList<InformationListdata>());
                        }
                        listdata.getWorkList().add(dataTemp);
                    }
                }
                for (InformationListdata dataTemp : uplistData) {
                    if(dataTemp.getParentId().longValue() == listdata.getId().longValue()){
                        if(listdata.getStartupList() == null){
                            listdata.setStartupList(new ArrayList<InformationListdata>());
                        }
                        listdata.getStartupList().add(dataTemp);
                    }
                }
            }
        }
        map.remove("study_experience");
        map.remove("work_experience");
        map.remove("entrepreneurial_experience");
        // == end 嵌套

        return map;
    }
    public String tableFieldValueCon(String fieldValue,int type,Map<Long, String> valueIdNameMap){
        String value = null;
        String contact = "、";

        if(type == 0 || type == 1 || type == 8 )
        {
            // 1:文本、 8:文本域(textarea)、
            value = textConversion(fieldValue);
        }else  if( type == 2 || type == 14 )
        {
            // 2: 单选（Radio）、 14 单选（select）、
            if(StringUtils.isNumeric(fieldValue)){
                value = valueIdNameMap.get(new Long(fieldValue));
            }
            if(StringUtils.isBlank(value)){
                value = fieldValue;
            }
        }else if(type == 3 || type == 4 )
        {
            // 3:复选（、 号连接）、4:级联选择（-  号连接）<br>
            value = fieldValue;
        }else if(type == 5 || type == 6 )
        {
            // 5:单选带备注(textarea) 、【6:复选带备注(textarea) 无该类型】
            // 仅显示 选项
            value = fieldValue;
        }else if(type == 12 || type == 13 )
        {
            // 12:单选带备注(input)--有 contentDescribe1 就不要 ContentChoose 、
            // 13:复选带备注(input)--有 contentDescribe1 就不要 ContentChoose
            value = fieldValue;
        }else if(type == 15 )
        {
            // 15 一个标题带两个文本域(textarea)、
            value = fieldValue;
        }else if(type == 16 )
        {
            // 16 多个文本框内容--组装一条显示  str=str.replace(/<sitg>/g,'（').replace(/<\/sitg>/g,'）');
            if(fieldValue.contains("sitg")){
                value = fieldValue.replace("<sitg>","（").replace("</sitg>","）");
            }else{
                value = fieldValue;
            }
        }else if(type == 19 || type == 20 )
        {
            // 19：文本框输入题目答案带单位的处理；(input)，单位在 title.content <br>
            // 20：文本框输入题目答案带单位的处理；(input)，单位在 title.content + result.ContentDescribe2
            value = fieldValue;
        }
        return value;
    }




    /**
     * 固定表格数据
     * 只有一题 写死  NO2_4_5
     * map code - map
     */
    public Map<String,Object> fixedtableTitleResult(Set<Long> ids,Long projectId, Map<Long, String> valueIdNameMap){
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> mapChild = new HashMap<>();

        if(ids == null || ids.isEmpty()){
            return  map;
        }

        Set<String> title_fixtableids = new TreeSet<>();
        for(Long temp : ids){
            title_fixtableids.add(temp+"");
        }
        InformationFixedTable fixedTableQuery = new InformationFixedTable();
        fixedTableQuery.setProjectId(projectId+"");
        fixedTableQuery.setTitleIds(title_fixtableids);
        List<InformationFixedTable> fixedTableList = fixedTableDao.selectList(fixedTableQuery);

        if(fixedTableList != null && fixedTableList.size()> 0) {
            for (InformationFixedTable fixedTable : fixedTableList) {
                if(fixedTable.getContent() != null){
                    mapChild.put(fixedTable.getRowNo() + "" + fixedTable.getColNo(), valueIdNameMap.get(new Long(fixedTable.getContent())));
                }
            }

            map.put("NO2_4_5",mapChild);
        }

        return map;
    }


    /**
     * 图片数据
     * map code - map N1\N2\N3\N4
     */
    public Map<String,Object> fileTitleResult(Set<Long> ids,Long projectId){
        Map<String, Object> map = new HashMap<>();

        if(ids == null || ids.isEmpty()){
            return  map;
        }


        Map<String, Object> params = new HashMap<String,Object>();
        params.put("titleIds",ids);
        params.put("projectId",projectId);
        params.put("notAllNUll",true);
        List<InformationTitle> titleList = informationTitleDao.selectTitleOfFileResults(params);

        if(titleList == null || titleList.isEmpty()){
            return  map;
        }

        /*List<String> resultTemp = new ArrayList<>();
        for (InformationTitle temp : titleList)
        {
            resultTemp = new ArrayList<>();
            List<InformationFile> resultList = temp.getFileList();

            if(resultList!=null && !resultList.isEmpty())
            {
                for (InformationFile informationFile : resultList) {
                    resultTemp.add(getImageStr(informationFile));
                }
                map.put(temp.getCode(),resultTemp);
            }
        }*/
        Map<String,String> resultTemp = null;
        for (InformationTitle temp : titleList)
        {
            resultTemp = new HashMap<>();
            List<InformationFile> resultList = temp.getFileList();

            if(resultList!=null && !resultList.isEmpty())
            {
                for (int i = 0; i < resultList.size(); i++) {
                    resultTemp.put("N" + i, getImageStr(resultList.get(i)));
                }
                map.put(temp.getCode(),resultTemp);
            }
        }
        return map;
    }
    public String getImageStr(InformationFile informationFile){
        byte[] data=null;

        OSSObject ossobjcet = OSSFactory.getClientInstance().getObject(new GetObjectRequest(OSSFactory.getDefaultBucketName(), informationFile.getFileKey()));
        InputStream fis = null;
        try {
            fis = ossobjcet.getObjectContent();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                if(data == null){
                    data = Arrays.copyOf(buffer, bytesRead);
                }else{
                    data = ArrayUtils.addAll(data, Arrays.copyOf(buffer, bytesRead));
                }
            }
        } catch (Exception e) {
            logger.error("getImageStr err",e);
        } finally {
            try {
                fis.close();
            } catch (IOException e1) {
                logger.error("getImageStr err",e1);
            }
        }

        if(data == null){
            return null;
        }else{
            //==ceshi
           /*OutputStream os = null;
            try {
                File outFile = new File("C:\\Users\\feng\\Desktop\\photo2.png");
                os = new FileOutputStream(outFile);
                os.write(data);
                os.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
            //== end ceshi
            return Base64.encodeBase64String(data);
            /*BASE64Encoder encoder=new BASE64Encoder();
            return encoder.encode(data);*/
        }
    }




    //暂无
    public Map<String,Object> resultGrageTitleResult(Set<Long> ids,Long projectId){
        Map<String, Object> map = new HashMap<>();

        if(ids == null || ids.isEmpty()){
            return  map;
        }


        return map;
    }










}