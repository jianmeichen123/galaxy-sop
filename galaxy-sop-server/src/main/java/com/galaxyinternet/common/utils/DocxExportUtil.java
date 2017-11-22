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
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;


public class DocxExportUtil {

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


    public DocxExportUtil(){
        super();
    }

    /**
     * web使用
     * @param templatePath 模板文件位置
     * @param templateName  xml docx 模板文件名称
     * @param filePath 保存路径
     * @param fileName 保存名称, xml docx 模板生成的文件
     */
    public DocxExportUtil(HttpServletRequest request, String templatePath, String templateName, String filePath, String fileName) {
        configuration=new Configuration(Configuration.VERSION_2_3_23);
        configuration.setDefaultEncoding("utf-8");
        configuration.setServletContextForTemplateLoading(request.getSession().getServletContext(),templatePath); // /template  是:/WebRoot/ftl

        configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_23));
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);//IGNORE_HANDLER

        this.templatePath = templatePath;
        this.templateName = templateName;
        this.filePath = filePath;
        this.fileName = fileName;
        this.request = request;

    }

    /**
     * web使用
     * @param templatePath 模板文件位置
     //////////////* @param templateName  xml docx 模板文件名称
     * @param filePath 保存路径
     * @param fileName 保存名称, xml docx 模板生成的文件
     */
    public DocxExportUtil(HttpServletRequest request, HttpServletResponse response, String templatePath, String filePath, String fileName) {
        configuration=new Configuration(Configuration.VERSION_2_3_23);
        configuration.setDefaultEncoding("utf-8");
        configuration.setServletContextForTemplateLoading(request.getSession().getServletContext(),templatePath); // /template  是:/WebRoot/ftl

        configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_23));
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);//IGNORE_HANDLER

        this.templatePath = templatePath;
        this.filePath = filePath;
        this.fileName = fileName;
        this.request = request;
        this.response = response;

    }



    /**
     * map数据-> xml文件 ->docx文件
     * @param templatePath 模板文件位置
     * @param templateName  xml docx 模板文件名称
     * @param filePath 保存路径
     * @param fileName 保存名称, xml docx 模板生成的文件
     */
    public void creatDocxAsZip(Map<String,Object> dataMap,String currentMark,boolean hasImage) throws Exception
    {
        Writer out = null;
        ZipOutputStream zipout = null;
        try {
            //map数据-> xml文件
            Template template = configuration.getTemplate(templateName+".xml"); //.xml  .ftl
            File outFile = new File(filePath,fileName+".xml");
            out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
            template.process(dataMap, out);
            out.flush();
            out.close();

            /*template = configuration.getTemplate("pcbg_header2.xml"); //.xml  .ftl
            File outFile_head2 = new File(filePath,System.currentTimeMillis()+"pcbg_header2.xml");
            out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile_head2), "utf-8"));
            template.process(dataMap, out);
            out.flush();
            out.close();
            else if("word/header2.xml".equals(ze.toString()))
            {
                InputStream in = new FileInputStream(outFile_head2);
                while((len = in.read(buffer))!=-1)
                {
                    zipout.write(buffer,0,len);
                }
                in.close();
                zipout.closeEntry();
            }*/

            //生成 docx 文件
            zipout = new ZipOutputStream(new FileOutputStream(new File(filePath,fileName+".docx")));

            int len=-1;
            ZipEntry ze = null;
            InputStream imageIn = null;
            byte[] buffer=new byte[1024*2];

            //copy docx 文件中的其它配置文件，替换 data处理过的 document xml 文件
            ZipFile zipFile = new ZipFile(new File(request.getSession().getServletContext().getRealPath("") + templatePath,templateName+".docx"));
            Enumeration<? extends ZipEntry> zipEntrys =  zipFile.entries();

            while(zipEntrys.hasMoreElements())
            {
                ze = zipEntrys.nextElement();
                zipout.putNextEntry(new ZipEntry(ze.toString()));

                if("word/document.xml".equals(ze.toString()))
                {
                    InputStream in = new FileInputStream(outFile);
                    while((len = in.read(buffer))!=-1)
                    {
                        zipout.write(buffer,0,len);
                    }
                    in.close();
                    zipout.closeEntry();
                }else {
                    InputStream is = zipFile.getInputStream(ze);
                    while((len = is.read(buffer))!=-1)
                    {
                        zipout.write(buffer,0,len);
                    }
                    is.close();
                    zipout.closeEntry();
                }
            }

            //处理 docx 中的图片
            if(hasImage){
                File imageDir = new File(filePath+File.separator+currentMark);

                if (imageDir.exists() && imageDir.isDirectory())
                {
                    File[] pngs = imageDir.listFiles();
                    if (pngs != null)
                    {
                        for (File af : pngs)
                        {
                            imageIn = new BufferedInputStream(new FileInputStream(af));

                            ze = new ZipEntry("word/media/"+af.getName());
                            zipout.putNextEntry(ze);

                            while ((len = imageIn.read(buffer)) != -1)
                            {
                                zipout.write(buffer, 0, len);
                            }
                            imageIn.close();
                            zipout.closeEntry();
                        }
                    }
                }
            }

            zipout.flush();
            zipout.close();
        } catch (Exception e) {
            throw new Exception("down docx err" , e);
        } finally {
            try {
                if(out!=null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if(zipout!=null){
                    zipout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param zipName 保存zip 名称
     * @param fileName 要压缩的文件名，
     * @param suffixMark   要压缩的文件名 的后缀 ".doc .docx"，
     * @param tempfilePath 要压缩的文件位置
     * @param response
     */
    public static void downZip(String zipName, Map<String, String> dname_sname, String tempfilePath,
                               HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/x-download"); //msword OCTET-STREAM
        response.setHeader("Content-Disposition","attachment; filename=" + getFileNameByBrowser(request,zipName));

        ZipOutputStream zos = null;
        BufferedInputStream is = null;
        try {
            zos = new ZipOutputStream(response.getOutputStream());

            ZipEntry ze = null;
            byte[] buf = new byte[1024*2];
            for (String f : dname_sname.keySet()) {
                ze = new ZipEntry(dname_sname.get(f)); //tempfilePath + File.separator +
                zos.putNextEntry(ze);

                is = new BufferedInputStream(new FileInputStream(new File(tempfilePath,f+".docx")));
                int len = -1;
                while ((len = is.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                is.close();
                zos.closeEntry();
            }
            zos.flush();
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








    /**
     * map数据-> xml文件 ->docx文件
     * @param templatePath 模板文件位置
     * @param templateName  xml docx 模板文件名称
     * @param filePath 保存路径
     * @param fileName 保存名称, xml docx 模板生成的文件
     */
    public void downDocxAsZip(Map<String,Object> dataMap,String currentMark,String[] doctemps,String temp_docx) throws Exception
    {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/x-download"); //msword OCTET-STREAM
        response.setHeader("Content-Disposition","attachment; filename=" + getFileNameByBrowser(request,fileName));

        ZipOutputStream zipout = null;

        Map<String, File> temp_file_map = new HashMap<>();
        Writer out = null;
        Template template = null;
        File outFile = null;
        try {

            for(String temp:doctemps){
                //map数据-> xml文件
                template = configuration.getTemplate(temp); //.xml  .ftl
                outFile = new File(filePath,currentMark+temp);
                out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
                template.process(dataMap, out);
                out.flush();
                out.close();

                temp_file_map.put("word/"+temp,new File(filePath,currentMark+temp));
            }


            //生成 docx 文件
            //zipout = new ZipOutputStream(new FileOutputStream(new File(filePath,fileName)));
            zipout = new ZipOutputStream(response.getOutputStream());

            int len=-1;
            ZipEntry ze = null;
            InputStream imageIn = null;
            byte[] buffer=new byte[1024*2];

            //copy docx 文件中的其它配置文件，替换 data处理过的 document xml 文件
            ZipFile zipFile = new ZipFile(new File(request.getSession().getServletContext().getRealPath("") + templatePath,temp_docx));
            Enumeration<? extends ZipEntry> zipEntrys =  zipFile.entries();

            while(zipEntrys.hasMoreElements())
            {
                ze = zipEntrys.nextElement();
                zipout.putNextEntry(new ZipEntry(ze.toString()));

                if(temp_file_map.containsKey(ze.toString())){
                    InputStream in = new FileInputStream(temp_file_map.get(ze.toString()));
                    while((len = in.read(buffer))!=-1)
                    {
                        zipout.write(buffer,0,len);
                    }
                    in.close();
                    zipout.closeEntry();
                }else {
                    InputStream is = zipFile.getInputStream(ze);
                    while((len = is.read(buffer))!=-1)
                    {
                        zipout.write(buffer,0,len);
                    }
                    is.close();
                    zipout.closeEntry();
                }
            }

            //处理 docx 中的图片
            File imageDir = new File(filePath+File.separator+currentMark);

            if (imageDir.exists() && imageDir.isDirectory())
            {
                File[] pngs = imageDir.listFiles();
                if (pngs != null)
                {
                    for (File af : pngs)
                    {
                        imageIn = new BufferedInputStream(new FileInputStream(af));

                        ze = new ZipEntry("word/media/"+af.getName());
                        zipout.putNextEntry(ze);

                        while ((len = imageIn.read(buffer)) != -1)
                        {
                            zipout.write(buffer, 0, len);
                        }
                        imageIn.close();
                        zipout.closeEntry();
                    }
                }
            }


            zipout.flush();
            zipout.close();
        } catch (Exception e) {
            throw new Exception("down docx err" , e);
        } finally {
            try {
                if(out!=null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if(zipout!=null){
                    zipout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }








    public boolean arrayHasEnu(String[] agrs,String target){
        for(String temp:agrs){
            if(target.equals(temp)){
                return true;
            }
        }
        return false;
    }


}


