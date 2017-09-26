package com.galaxyinternet.common.utils;

import com.galaxyinternet.model.sopfile.SopDownLoad;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


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
     * @param filePath 保存路径
     * @param fileName 保存名称
     */
    public DocExportUtil(HttpServletRequest request,String templatePath, String templateName, String filePath, String fileName) {
        configuration=new Configuration(Configuration.VERSION_2_3_23);
        configuration.setDefaultEncoding("utf-8");
        configuration.setServletContextForTemplateLoading(request.getSession().getServletContext(),templatePath); // /template  是:/WebRoot/ftl

        configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_23));
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);//IGNORE_HANDLER

        this.templatePath = templatePath;
        this.templateName = templateName;
        this.filePath = filePath;
        this.fileName = fileName;

    }

    public void createDoc(Map<String,Object> dataMap)  throws Exception {
        Writer out = null;
        try {
            Template template = configuration.getTemplate(templateName); //.xml  .ftl

            File outFile = new File(filePath,fileName);

            out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));

            template.process(dataMap, out);
        } catch (Exception e) {
            throw new Exception("down doc err" , e);
        } finally {
            try {
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param zipName 保存zip民称
     * @param fileName 要压缩的文件名，
     * @param fnMark   要压缩的文件名 要替换掉的标识，
     * @param tempfilePath 要压缩的文件位置
     * @param response
     */
    public static void downZip(String zipName, List<String> fileName,String fnMark, String tempfilePath,
                               HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/x-download"); //msword
        response.setHeader("Content-Disposition","attachment; filename=" + getFileNameByBrowser(request,zipName));

        ZipOutputStream zos = null;
        BufferedInputStream is = null;
        try {
            zos = new ZipOutputStream(response.getOutputStream());

            ZipEntry ze = null;
            byte[] buf = new byte[1024*2];
            for (String f : fileName) {
                ze = new ZipEntry(f.replace(fnMark,"")); //tempfilePath + File.separator +
                zos.putNextEntry(ze);

                is = new BufferedInputStream(new FileInputStream(new File(tempfilePath,f)));
                int len = -1;
                while ((len = is.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.flush();
                is.close();
                zos.closeEntry();
            }
        } catch (Exception e) {
            throw new Exception("down zip err" , e);
        } finally {
            try {
            	zos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private static String getFileNameByBrowser(HttpServletRequest request,String fileName) {
        try {
            boolean ie10 = request.getHeader(SopDownLoad.USER_AGENT).toUpperCase().indexOf("MSIE") > 0;
            boolean ie11p = request.getHeader(SopDownLoad.USER_AGENT).toUpperCase().indexOf("RV:11") > 0
                    && request.getHeader(SopDownLoad.USER_AGENT).toUpperCase().indexOf("LIKE GECKO") > 0;
            boolean iedge = request.getHeader(SopDownLoad.USER_AGENT).toUpperCase().indexOf("EDGE") > 0;
            boolean ie = ie10 || ie11p || iedge;
            if (ie) {
                fileName = new String(StrUtils.encodString(fileName).getBytes("UTF-8"), "ISO8859-1");
            } else {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            }
        } catch (Exception e) {
            throw new RuntimeException("getFileNameByBrowser err" , e);
        }

        //response.setHeader("Content-disposition","attachment;filename=" + URLEncoder.encode(str, "UTF-8"));// 设置头部信息
        return fileName;
    }


}


