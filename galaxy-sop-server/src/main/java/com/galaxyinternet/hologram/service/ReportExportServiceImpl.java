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
import com.galaxyinternet.model.hologram.ScoreInfo;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.FileUtilModel;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.platform.constant.PlatformConst;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.UserService;
import com.galaxyinternet.service.hologram.ReportExportService;
import com.galaxyinternet.service.hologram.ScoreInfoService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    private ScoreInfoService scoreService;
    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;



    /*
    <br/> <br>

    replace(/\<br\>/g,'\n')  <w:br />
    replace(/&nbsp;/g," ")
    replace(/&amp;/g,"&")
    replace(/&gt;/g,">")
    replace(/&lt;/g,"<")
    replace(/<.*?>/g,"").
    */
    public String textConversion(String text){
        if(StringUtils.isBlank(text)){
            return null;
        }else{
        	String check ="";
            if(text.contains("sitg")){
            	check = text.replace("<sitg>","（").replace("</sitg>","）");
            }else{
            	check = text;
            }
            check=  text.replace("<br/>","")
            .replace("<br>","")
            .replace("&nbsp;","");
            if(StringUtils.isBlank(check)){
                return null;
            }
        }

        String result = text.replace("<br/>","<w:br />")
                .replace("<br>","<w:br />")
                .replace("&nbsp;"," ")
                .replace("&","&amp;")
                .replace("&amp;gt;","&gt;")
                .replace("&amp;lt;","&lt;");

        result = result.replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("&lt;w:", "<w:")
                .replaceAll("w:br /&gt;", "w:br />");

        try{
            Double.parseDouble(result);
        }catch(Exception ex){
            return result;
        }

        if(result.indexOf(".") > 0){
            result = result.replaceAll("0+?$", "");//去掉多余的0
            result = result.replaceAll("[.]$", "");//如最后一位是.则去掉
        }

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
     * @param imageDir currentMark_tempName
     */
    public Map<String, Object> titleAnswerConversionTask(Long uid, Project project, String preCode, String imageDir,String tempfilePath){
        Long btime = System.currentTimeMillis();

        Map<String, Object> map = new HashMap<>();
        try {
            Map<Long, String> valueIdNameMap = (Map<Long, String>) cache.get(CacheOperationServiceImpl.CACHE_KEY_VALUE_ID_NAME);

            ForkJoinPool pool = GalaxyThreadPool.getForkJoinPool();
            ExportDataConversionTask task = new ExportDataConversionTask(preCode,project.getId(),null,null,valueIdNameMap,imageDir,tempfilePath);
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

        Map<String, Object> map1 = projectTitleResult(project_ids,proId,valueIdNameMap);
        Map<String, Object> map2 = resultTitleResult(result_ids,proId,valueIdNameMap,preCode);
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
    public Map<String,Object> projectTitleResult(Set<Long> ids,Long projectId, Map<Long, String> valueIdNameMap){
        Map<String, Object> map = new HashMap<>();

        /* NO1_1_9
        if(ids == null || ids.isEmpty()){
            return  map;
        }*/
        String[] mustTitleCode = {"NO1_1_9"};

        try{
            Map<String, Object> params = new HashMap<String,Object>();
            params.put("codes",mustTitleCode);
            params.put("projectId",projectId);
            params.put("notAllNUll",true);
            List<InformationTitle> titleList = informationTitleDao.selectTitleOfResults(params);

            if(titleList != null &&  !titleList.isEmpty()){
                Object value = null;

                for(InformationTitle tempTitle : titleList)
                {
                    value = null;
                    List<InformationResult> resultList = tempTitle.getResultList();
                    if(resultList!=null && !resultList.isEmpty())
                    {
                        value = resultTypeConver(tempTitle,valueIdNameMap);
                        if(value != null){
                            map.put(tempTitle.getCode(), value);
                        }
                    }
                }
            }



            Project project = projectDao.selectById(projectId);
            map.put("NO1_1_1", project.getProjectCode());
            map.put("NO1_1_1_n", textConversion(project.getProjectName()));

            //项目来源
            /*if(StringUtils.isNotBlank(project.getFaFlag())){
                if( "projectSource:1".equals(project.getFaFlag())){
                    map.put("NO1_flag", DictEnum.projectSource.getNameByCode(project.getFaFlag()) +"-"+project.getFaName());
                }else{
                    map.put("NO1_flag", DictEnum.projectSource.getNameByCode(project.getFaFlag()));
                }
            }*/

            User createUser = userService.queryById(project.getCreateUid());
            project.setCreateUname(createUser.getRealName());
            map.put("NO1_1_2_z", createUser.getRealName());

            Department department = departmentService.queryById(project.getProjectDepartid());
            //project.setProjectCareerline(department.getName());
            if(department!=null) map.put("NO1_1_4", department.getName());

            //事业部总经理
            if(department.getManagerId()!=null){
                User managerUser = userService.queryById(department.getManagerId());
                //project.setHhrName(managerUser.getRealName());
                if(managerUser!=null) map.put("NO1_1_3", managerUser.getRealName());
            }


            //项目承做人 1103 NO1_1_2   field_1:uid   field_3:deptId   field_5:标识主副
            //NO1_1_2_s
            Map<String, Object> query = new HashMap<>();
            query.put("projectId", projectId);
            List<InformationListdata> queryCZRList = listDataDao.searchFor1103(query);


            List<Map<String, Object>> resultC = new ArrayList<>();
            Set<String> checkHasDept = new HashSet<>();
            Map<String,Object> datac = null;

            if(queryCZRList == null || queryCZRList.size()<2){
                datac = new HashMap<>();
                datac.put("field1", map.get("NO1_1_2_z") +"（100%）");
                datac.put("field2", map.get("NO1_1_4") );
                datac.put("field3", map.get("NO1_1_3")  );
                resultC.add(datac);
            }else{
                map.put("NO1_1_2_s", true);

                List<InformationListdata> copyCZRList = new ArrayList<>(queryCZRList);
                for(InformationListdata temp : queryCZRList)
                {
                    if(checkHasDept.contains(temp.getProperty())){
                        continue;
                    }else{
                        checkHasDept.add(temp.getProperty());
                    }

                    datac = new HashMap<>();
                    datac.put("field1", (cache.hget(PlatformConst.CACHE_PREFIX_USER+temp.getField1(), "realName")).toString()+"（"+ temp.getField2() +"%）");
                    datac.put("field2", cache.hget(PlatformConst.CACHE_PREFIX_DEP+temp.getProperty(), "name"));

                    Object managerId = cache.hget(PlatformConst.CACHE_PREFIX_DEP+temp.getProperty(),"manager");
                    if(managerId != null){
                        datac.put("field3", cache.hget(PlatformConst.CACHE_PREFIX_USER+managerId, "realName"));
                    }

                    for(InformationListdata tempC : copyCZRList){
                        if(tempC.getId().longValue() == temp.getId().longValue()){
                            continue;
                        }else if(tempC.getProperty().equals(temp.getProperty())){
                            datac.put("field1", datac.get("field1")+"<w:br />"+
                                    (cache.hget(PlatformConst.CACHE_PREFIX_USER+tempC.getField1(), "realName")).toString()+"（"+ tempC.getField2() +"%）");
                        }
                    }
                    resultC.add(datac);
                }
            }
            map.put("NO1_1_2", resultC);

        }catch (Exception e){
            logger.error("projectTitleResult ",e);
            //throw new RuntimeException("ReportDataConversion projectTitleResult 项目基础信息查询失败",e);
        }

        return map;
    }


    /**
     * result 结果表 结果查询封装
     * title-type :  ,1,2,3,4,5,6,8,12,13,14,15,16,18,19,20, 21
     *
     * 【6 18 无】
     *
     */
    public Map<String,Object> resultTitleResult(Set<Long> ids,Long projectId, Map<Long, String> valueIdNameMap,String precode){
        Map<String, Object> map = new HashMap<>();

        if(ids == null || ids.isEmpty()){
            return  map;
        }

        List<InformationTitle> titleList = null;
        if(null != precode && precode.equals("NO")){
            Map<String, Object> params = new HashMap<String,Object>();
            params.put("titleIds",ids);
            params.put("projectId",projectId);
            params.put("notAllNUll",true);
            titleList = informationTitleDao.selectTitleOfResults(params);
        }else{
            Map<String, Object> params = new HashMap<String,Object>();
            params.put("relateIds",ids);
            params.put("projectId",projectId);
            params.put("notAllNUll",true);
            titleList = informationTitleDao.selectTitleOfResultsForRelate(params);
        }

        if(titleList == null || titleList.isEmpty()){
            return  map;
        }

        Object value = null;

        for(InformationTitle tempTitle : titleList)
        {
            value = null;

            List<InformationResult> resultList = tempTitle.getResultList();

            //项目来源 title NO1_1_17—1120 CLR承揽人关系 写死
            //定义是否显示承揽人  information_dictionary id_2257:FA推荐 -> 不显示承揽人
            if(tempTitle.getCode().equals("NO1_1_17"))
            {
                if(!("2257".equals(resultList.get(0).getContentChoose()))){
                    map.put("CLR_tos", "y");
                }
            }

            if(resultList!=null && !resultList.isEmpty())
            {
                value = resultTypeConver(tempTitle,valueIdNameMap);
                if(value != null){
                    map.put(tempTitle.getCode(), value);
                }
            }
        }

        return map;
    }

    public Object resultTypeConver(InformationTitle tempTitle,Map<Long, String> valueIdNameMap){
        String value = null;
        String contact = "、";

        int type = 0;
        if(tempTitle.getType() != null) type = tempTitle.getType().intValue();

        List<InformationResult> resultList = tempTitle.getResultList();

        if ( type == 0 ||type == 1|| type == 18)
        {
            // 1:文本、  8:文本域(textarea)、
            //  map : code-value
            InformationResult tempResult = resultList.get(0);
            value = textConversion(tempResult.getContentDescribe1());
        } else if (type == 2 || type == 14) {
            //  2: 单选（Radio）、  14 单选（select）、
            //  map : code-value
            InformationResult tempResult = resultList.get(0);
            if (StringUtils.isNotBlank(tempResult.getContentChoose())) {
                if (StringUtils.isNumeric(tempResult.getContentChoose())) {
                    value = valueIdNameMap.get(new Long(tempResult.getContentChoose()));
                } else {
                    value = textConversion(tempResult.getContentChoose());
                }
            }
        } else if (type == 3 || type == 4) {
            // 3:复选（、 号连接）、4:级联选择（-  号连接）<br>
            // map : code-value
            contact = type == 3 ? "、" : " - ";

            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < resultList.size(); i++)
            {
                if (resultList.get(i).getContentChoose() != null && StringUtils.isNumeric(resultList.get(i).getContentChoose()))
                {
                    value = valueIdNameMap.get(new Long(resultList.get(i).getContentChoose()));

                    if (StringUtils.isNotBlank(value)) {
                        if (stringBuffer.length() > 0) {
                            stringBuffer.append(contact).append(value);
                        } else {
                            stringBuffer.append(value);
                        }
                    }
                }
            }
            if (stringBuffer.length() > 0) {
                value = stringBuffer.toString();
            }
        } else if (type == 5 || type == 6) {
            // 5:单选带备注(textarea) 、【6:复选带备注(textarea) 无该类型】
            // 仅显示 选项
            //  map : code-value
            for (int i = 0; i < resultList.size(); i++) {
                if (resultList.get(i).getContentChoose() != null && StringUtils.isNumeric(resultList.get(i).getContentChoose())) {
                    value = valueIdNameMap.get(new Long(resultList.get(i).getContentChoose()));
                    if (StringUtils.isNotBlank(value)) {
                        break;
                    }
                }
            }
        } else if (type == 12 || type == 13 || type == 21 || type == 23) {
            // 12:单选带备注(input)--有 contentDescribe1 就不要 ContentChoose 、
            // 13:复选带备注(input)--有 contentDescribe1 就不要 ContentChoose
            // 21: 下拉选择加备注
            // 23: 下拉复选加备注
            // map : code-value
            StringBuffer stringBuffer = new StringBuffer();

            for (int i = 0; i < resultList.size(); i++) {
                value = null;
                if (StringUtils.isNotBlank(resultList.get(i).getContentDescribe1())) {
                    value = resultList.get(i).getContentDescribe1();
                    value = textConversion(value);
                } else if (StringUtils.isNotBlank(resultList.get(i).getContentChoose()) && StringUtils.isNumeric(resultList.get(i).getContentChoose())) {
                    if (StringUtils.isNotBlank(valueIdNameMap.get(new Long(resultList.get(i).getContentChoose())))) {
                        value = valueIdNameMap.get(new Long(resultList.get(i).getContentChoose()));
                        if (value.contains("其他")) {
                            continue;
                        }
                    }
                }

                if (value != null) {
                    if (stringBuffer.length() > 0) {
                        stringBuffer.append(contact).append(value);
                    } else {
                        stringBuffer.append(value);
                    }
                }
            }

            if (stringBuffer.length() > 0) {
                value = stringBuffer.toString();
            }
        } else if (type == 15) {
            // 15 一个标题带两个文本域(textarea)、
            // map : code-List String
            Map<String, String> mapValue = new HashMap<>();
            for (int i = 0; i < resultList.size(); i++) {
                if (StringUtils.isNotBlank(resultList.get(i).getContentDescribe1()) && StringUtils.isBlank(mapValue.get("N1"))) {
                    mapValue.put("N1", textConversion(resultList.get(i).getContentDescribe1()));
                }

                if (StringUtils.isNotBlank(resultList.get(i).getContentDescribe2()) && StringUtils.isBlank(mapValue.get("N2"))) {
                    mapValue.put("N2", textConversion(resultList.get(i).getContentDescribe2()));
                }
            }

            if (mapValue.isEmpty()) {
                return null;
            } else {
                return mapValue;
            }
        } else if (/*type == 16*/ type == 8 ) {
            // 16 多个文本框内容--组装一条显示  str=str.replace(/<sitg>/g,'（').replace(/<\/sitg>/g,'）');
            // map : code-value
            InformationResult tempResult = resultList.get(0);
          //  value = textConversion(tempResult.getContentDescribe1());
            if (StringUtils.isNotBlank(tempResult.getContentDescribe1()))
            {
                if (tempResult.getContentDescribe1().contains("sitg"))
                {
                    String[] conStr=tempResult.getContentDescribe1().split("<sitg>");
                    int sum=0;
                    for(int i=0;i<conStr.length;i++){
                        if(conStr[i].indexOf("</sitg>")>-1){
                            int inputsValueLen=conStr[i].substring(0,conStr[i].indexOf("</sitg>")).trim().length();
                            sum +=inputsValueLen;
                        }
                    }
                    if(sum == 0){
                        return null;
                    }

                    Pattern pattern = Pattern.compile("<sitg>\\s*");
                    Matcher matcher = pattern.matcher(tempResult.getContentDescribe1());
                    value = matcher.replaceAll("（");

                    pattern = Pattern.compile("\\s*</sitg>");
                    matcher = pattern.matcher(value);
                    value = matcher.replaceAll("）");

                    //value = textConversion(tempResult.getContentDescribe1().replace("<sitg>", "（").replace("</sitg>", "）"));
                }else{
                    value = textConversion(tempResult.getContentDescribe1());
                }

                if("ENO1_1_4".equals(tempTitle.getCode()) && value.contains("，满足了（")){
                    value = value.substring(value.lastIndexOf("，满足了（")+1,value.length());
                }
            }
        } else if (type == 19 || type == 20) {
            // 19：文本框输入题目答案带单位的处理；(input)，单位在 title.content <br>
            // 20：文本框输入题目答案带单位的处理；(input)，单位在 title.content + result.ContentDescribe2
            // map : code-value
            InformationResult tempResult = resultList.get(0);
            if (StringUtils.isNotBlank(tempResult.getContentDescribe1()))
            {
                String unit = tempTitle.getContent();
                if (StringUtils.isNotBlank(tempResult.getContentDescribe2()))
                {
                    unit += tempResult.getContentDescribe2().substring(0, tempResult.getContentDescribe2().indexOf("p"));
                }
                value = textConversion(tempResult.getContentDescribe1()) + unit;
            }
        } else if(type == 22){
            // 写死  ENO4_4_3  ENO4_4_4
            //22:复选题（选项来自列表题一部分，评分每个选项单独评）
            List<Map<String, String>> results = new ArrayList<>();

            Set<Long> resultIds = new HashSet<>();
            for (int i = 0; i < resultList.size(); i++){
                resultIds.add(resultList.get(i).getId());
            }

            Map<String, Object> params = new HashMap<String,Object>();
            params.put("resultIds",resultIds);
            params.put("notAllNUll",true);
            List<Map<String,Object>> relsl = informationTitleDao.selectResultsAndGradeForType22(params);

            for(Map<String,Object> tempmap : relsl){
                Map<String, String> hmap = new HashMap<>();
                if(tempmap.get("field_1") != null)
                    hmap.put("value", textConversion(tempmap.get("field_1")+""));
                if(tempmap.get("grade1") != null)
                    hmap.put("grade", textConversion(tempmap.get("grade1")+""));
                if(tempmap.get("grade_percentage") != null)
                    hmap.put("weight", textConversion(tempmap.get("grade_percentage")+""));

                results.add(hmap);
            }

            return results;
        }


        return value;
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
        ids.remove(1103l);  //项目承做人 移到必选特殊处理  title_tableids.add("1103");
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
        boolean toAddd = false;

        for(InformationListdata item : listdataList)
        {
            toAddd = false;
            listDataResult = null;

            if(StringUtils.isNotBlank(item.getCode())){
                remarkCode = item.getCode();
            }else{
                remarkCode = CacheOperationServiceImpl.table_tid_remarkCode.get(item.getTitleId()+"");
            }
            if(StringUtils.isNotBlank(remarkCode))
                codeReplace = remarkCode.replace("-","_");

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

            if(logger.isDebugEnabled()){
                if("investor_situation".equals(codeReplace)){
                    System.out.println();
                }
            }

            /*if("1103".equals(item.getTitleId())){ //team_person  项目承做人 ,必选, 特殊处理
                if(map.containsKey(codeReplace)){
                    ((List<InformationListdata>)  map.get(codeReplace)).add(item);
                    //map.put(codeReplace,listDataResult);
                }else{
                    listDataResult = new ArrayList<>();
                    listDataResult.add(item);
                    map.put(codeReplace,listDataResult);
                }
                continue;
            }*/

            for(int i=1; i<17;i++){
                try {
                    String tid = CacheOperationServiceImpl.table_remarkCode_field_tid.get(remarkCode).get(preField+i);
                    Integer type = CacheOperationServiceImpl.table_fieldTid_type.get(tid);

                    String newStr=preField.substring(0, 1).toUpperCase()+preField.replaceFirst("\\w","");
                    String methodStr="get"+newStr+i;
                    Method getMethod = InformationListdata.class.getMethod(methodStr);
                    String fieldValue = (String) getMethod.invoke(item);
                    if(null != fieldValue) //对 isBlank 的数据设置为null
                    {
                        String value = tableFieldValueCon(fieldValue, type==null?0:type, valueIdNameMap);
                        if(StringUtils.isNotBlank(value)){
                            toAddd = true;
                        }
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

            if(toAddd){
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

        }


        /*
         * 排序需求 写死
         * equity_structure    field2
         * investor_situation
         *  && map.get("equity_structure")!=null && ((List<InformationListdata>)  map.get("equity_structure")).size() > 1
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
            if(StringUtils.isNotBlank(fieldValue) && StringUtils.isNumeric(fieldValue)){
                value = valueIdNameMap.get(new Long(fieldValue));
            }
            if(StringUtils.isBlank(value)){
                value = textConversion(fieldValue);
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
        }else if(type == 8 )
        {
            // 16 多个文本框内容--组装一条显示  str=str.replace(/<sitg>/g,'（').replace(/<\/sitg>/g,'）');
            if(fieldValue.contains("sitg")){
                value = fieldValue.replace("<sitg>","（").replace("</sitg>","）");
            }else{
                value = textConversion(fieldValue);
            }
        }else if(type == 19 || type == 20 )
        {
            // 19：文本框输入题目答案带单位的处理；(input)，单位在 title.content <br>
            // 20：文本框输入题目答案带单位的处理；(input)，单位在 title.content + result.ContentDescribe2
            value = textConversion(fieldValue);
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
     * map code - list<FileUtilModel>   rels_no : rid 100+
     *@param currentMark  imageDir 图片存储路径 currentMark_tempName
    NO4_1_2     rId101 2345 image101.png image105.png
    NO4_2_2     rId201 2345 image201.png image10.png
    NO4_2_5_3   rId301 2345 image301.png image15.png
    NO4_2_6_3   rId401 2345 image401.png image20.png
    NO9_3_7     rId501 2345 image501.png image25.png
     */
    public Map<String,Object> fileTitleResult(Set<Long> ids,Long projectId,String currentMark,String tempfilePath,String precode)
            throws Exception
    {
        Map<String, Object> map = new HashMap<>();

        if(ids == null || ids.isEmpty()){
            return  map;
        }

        Map<String, Object> params = new HashMap<String,Object>();
        params.put("titleIds",ids);
        params.put("projectId",projectId);
        params.put("notAllNUll",true);
        params.put("property","result.file_key ASC");
        List<InformationTitle> titleList = informationTitleDao.selectTitleOfFileResults(params);

        if(titleList == null || titleList.isEmpty()){
            return  map;
        }

        //word 图片 长宽设置
        int hight = 2510000;
        int width = 3318000;
        if("EN".equals(precode)){
            hight = 2160000;
            width = 2810000;
        }


        //String tempfilePath = SpringContextManager.getBean(SopFileController.class).getTempfilePath() ;
        List<FileUtilModel> resultTemp = null;
        int beSum = 0;
        String ridMark = "";

        InputStream fis = null;
        OutputStream out = null;

        for (InformationTitle temp : titleList)
        {
            List<InformationFile> resultList = temp.getFileList();

            if(resultList!=null && !resultList.isEmpty() && StringUtils.isNotBlank(temp.getCode()))
            {
                resultTemp = new ArrayList<FileUtilModel>();
                switch (temp.getCode()){
                    case "NO4_1_2":
                        beSum = 0;
                        ridMark = "10";
                        break;
                    case "NO4_2_2":
                        beSum = 5;
                        ridMark = "20";
                        break;
                    case "NO4_2_5_3":
                        beSum = 10;
                        ridMark = "30";
                        break;
                    case "NO4_2_6_3":
                        beSum = 15;
                        ridMark = "40";
                        break;
                    case "NO9_3_7":
                        beSum = 20;
                        ridMark = "50";
                        break;
                    default:
                        continue;
                }

                for (int i = 1; i < resultList.size()+1; i++)
                {
                    FileUtilModel am = new FileUtilModel();
                    try {
                        OSSObject ossobjcet = OSSFactory.getClientInstance().getObject(new GetObjectRequest( StringUtils.isBlank(resultList.get(i-1).getBucketName())? OSSFactory.getDefaultBucketName():resultList.get(i-1).getBucketName(), resultList.get(i-1).getFileKey()));
                        fis = ossobjcet.getObjectContent();

                        File dir = new File(tempfilePath+ File.separator +currentMark);
                        if(!dir.exists()){
                            dir.mkdirs();
                        }
                        File outFile = new File(dir,"image"+ridMark+i+".png");
                        out=new BufferedOutputStream(new FileOutputStream(outFile));

                        byte[] buffer = new byte[1024*2];
                        int len = -1;
                        while ((len = fis.read(buffer)) != -1) {
                            out.write(buffer,0,len);
                        }
                        out.flush();
                        out.close();
                        fis.close();

                        fis = new FileInputStream(outFile);
                        BufferedImage src = javax.imageio.ImageIO.read(fis);
                        am.setRid("rId"+ridMark+i);
                        am.setHigh(src.getHeight()*9525>hight?hight:src.getHeight()*9525);
                        am.setWide(src.getWidth()*9525>width?width:src.getWidth()*9525);

                        fis.close();

                        resultTemp.add(am);
                    } catch (Exception e) {
                        logger.error("file error ",e);
                        //throw new RuntimeException("aliyun photo down to tempfilepaht err", e);
                    } finally {
                        try {
                            if(out!=null){
                                out.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            if(fis!=null){
                                fis.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                map.put(temp.getCode(),resultTemp);
            }
        }
        return map;
    }


    /**
     * 图片数据 for doc
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
        params.put("property","result.file_key ASC");
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

        InputStream fis = null;
        try {
            OSSObject ossobjcet = OSSFactory.getClientInstance().getObject(new GetObjectRequest(StringUtils.isBlank(informationFile.getBucketName())? OSSFactory.getDefaultBucketName():informationFile.getBucketName(), informationFile.getFileKey()));

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
                if(fis!=null){
                    fis.close();
                }
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


    /**
     * 分数查询 for doc
     * map code - map  T + relate—id
     */
    public Map<String,Object> titleScoreResult(Long projectId,String precode){
        Map<String, Object> map = new HashMap<>();

        try {
            Map<String,BigDecimal> scores = new HashMap<>();
            ScoreInfo query = new ScoreInfo();
            query.setParentId(0l);
            query.setReportType("EN".equals(precode)?1:6);
            List<ScoreInfo> list = scoreService.queryList(query);

            List<Long> relateIds = new ArrayList<>(list.size());
            if(list != null && list.size()>0)
            {
                for(ScoreInfo item : list)
                {
                    if(item != null && item.getRelateId() != null)
                    {
                        relateIds.add(item.getRelateId());
                    }
                }
            }
            if(relateIds.size() > 0)
            {
                Map<String,BigDecimal> childrenScores = scoreService.calculateMutipleReport(relateIds, projectId);
                scores.putAll(childrenScores);
            }
            roundHalfUp(scores);

            for(Map.Entry<String, BigDecimal> item : scores.entrySet()){
                map.put("T"+item.getKey().replace("-","_"),item.getValue());
            }
        } catch (Exception e) {
            logger.error("gradeTitleResult err",e);
            //throw new RuntimeException();
        }

        return map;
    }
    private void roundHalfUp(Map<String,BigDecimal> scores)
    {
        Set<Map.Entry<String, BigDecimal>> items = scores.entrySet();
        for(Map.Entry<String, BigDecimal> item : items)
        {
            if(item.getValue() != null)
            {
                item.setValue(item.getValue().setScale(2, RoundingMode.HALF_UP));
            }
        }
    }







}
