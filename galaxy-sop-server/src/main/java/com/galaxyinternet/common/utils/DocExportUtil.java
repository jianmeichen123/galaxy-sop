package com.galaxyinternet.common.utils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
     * web使用
     * @param templatePath 模板文件位置
     * @param templateName 模板文件名称
     * @param fileName doc保存名称
     */
    public DocExportUtil(String templatePath, String templateName, HttpServletRequest request, HttpServletResponse response, String fileName) {
        configuration=new Configuration(Configuration.VERSION_2_3_23);
        configuration.setDefaultEncoding("utf-8");
        configuration.setServletContextForTemplateLoading(request.getSession().getServletContext(),templatePath); // /template  是:/WebRoot/ftl

        configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_23));
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);

        this.templatePath = templatePath;
        this.templateName = templateName;
        this.request = request;
        this.response = response;
        this.fileName = fileName;
    }


    public OutputStream createDocOut(Map<String, Object> dataMap)
    {
        try
        {
            Template template = configuration.getTemplate(templateName); //.xml  .ftl

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            response.addHeader("Content-Disposition", "attachment; filename="+fileName);
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






}


