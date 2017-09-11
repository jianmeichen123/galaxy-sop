package com.galaxyinternet.common.utils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DocExportUtil {

    /**
     * freemark模板配置
     */
    private Configuration configuration = null;

    /**
     * freemark 模板文件位置 模板的名字
     */
    private String templatePath;
    private String templateName;

    /**
     * 生成文件路径 文件名
     */
    private String filePath;
    private String fileName;

    private HttpServletRequest request;
    private HttpServletResponse response;

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public String getTemplateName() {
        return templateName;
    }
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }


    public DocExportUtil(){
        super();
    }
    /**
     * 本地使用
     * @param templatePath 模板文件位置
     * @param templateName 模板文件名称
     * @param filePath doc保存路径
     * @param fileName doc保存名称
     */
    public DocExportUtil(String templatePath, String templateName, String filePath, String fileName) {
        configuration=new Configuration(Configuration.VERSION_2_3_23);
        configuration.setDefaultEncoding("utf-8");
        try {
            configuration.setDirectoryForTemplateLoading(new File(templatePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_23));
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);

        this.templatePath = templatePath;
        this.templateName = templateName;
        this.filePath = filePath;
        this.fileName = fileName;
    }

    /**
     * web使用
     * @param templatePath 模板文件位置
     * @param templateName 模板文件名称
     * @param filePath doc保存路径
     * @param fileName doc保存名称
     */
    public DocExportUtil(String templatePath, String templateName, HttpServletRequest request, HttpServletResponse response) {
        configuration=new Configuration(Configuration.VERSION_2_3_23);
        configuration.setDefaultEncoding("utf-8");
        configuration.setServletContextForTemplateLoading(request.getSession().getServletContext(),templatePath); // /template  是:/WebRoot/ftl

        configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_23));
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);

        this.templatePath = templatePath;
        this.templateName = templateName;
        this.request = request;
        this.response = response;
    }


    /**
     * 根据Doc模板生成word文件
     * @param dataMap 需要填入模板的数据
     */
    public void createDoc(Map<String,Object> dataMap){
        try {
            Template template = configuration.getTemplate(templateName); //.xml  .ftl
            File outFile = new File(filePath+fileName);
            Writer out = null;
            out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
            template.process(dataMap, out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
    public OutputStream createDocOut(Map<String, Object> dataMap)
    {
        try
        {
            Template template = configuration.getTemplate(templateName); //.xml  .ftl

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            response.addHeader("Content-Disposition", "attachment; filename=test.doc");
            OutputStream out = response.getOutputStream();
            template.process(dataMap,new OutputStreamWriter(out,"utf-8"));
            out.flush();
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }





    public String getImageStr(String imgFile){
        InputStream in=null;
        byte[] data=null;
        try {
            in=new FileInputStream(imgFile);
            data=new byte[in.available()];
            in.read(data);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder=new BASE64Encoder();
        return encoder.encode(data);
    }


    /**
     * map 测试
     */
    public static void mainx(String[] args) {

        //模板位置
        String templatePath = "C:\\Users\\feng\\Desktop";
        String templateName = "testDoc.xml";
        //保存路径
        String filePath = "C:\\Users\\feng\\Desktop\\";
        String fileName = "doc_"+new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(new Date())+".doc" ;

        Map<String,Object> root = new HashMap<String, Object>();

        Map<String,Object> modelMap = new HashMap<String, Object>();
        modelMap.put("name", "韩满义");
        modelMap.put("age", "qwe");
        modelMap.put("sex", "男");
        root.put("modelMap",modelMap);


        List<Map> modelMapList = new ArrayList<>();
        Map<String,Object> amodelMap = null;
        for(int i = 0; i<4; i++){
            amodelMap = new HashMap<String, Object>();
            amodelMap.put("name", i+"韩满义");
            amodelMap.put("age", i+"qwe");
            amodelMap.put("sex", i+"男");
            modelMapList.add(amodelMap);
        }
        root.put("modelMapList",modelMapList);

        DocExportUtil freemark = new DocExportUtil(templatePath,templateName,filePath,fileName);
        freemark.createDoc(root);

    }

    /**
     * 对象实体 测试
     */
    public static void main(String[] args) {

        //模板位置
        String templatePath = "C:\\Users\\feng\\Desktop";
        String templateName = "testDoc.ftl";
        //保存路径
        String filePath = "C:\\Users\\feng\\Desktop\\";
        String fileName = "doc_"+new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(new Date())+".doc" ;

        Map<String,Object> root = new HashMap<String, Object>();


        Model modelEntity = new Model();
        modelEntity.setName("111");
        modelEntity.setSex("112");
        modelEntity.setAge("113");
        root.put("modelEntity",modelEntity);


        List<Model> modelEntitys = new ArrayList<>();
        for(int i = 0; i<4; i++){
            Model amodelEntity = new Model();
            amodelEntity.setName(i+"111");
            amodelEntity.setSex(i+"112");
            amodelEntity.setAge(i+"113");
            modelEntitys.add(amodelEntity);
        }
        root.put("modelEntitys",modelEntitys);

        DocExportUtil freemark = new DocExportUtil(templatePath,templateName,filePath,fileName);
        freemark.createDoc(root);
    }


}


