package com.galaxyinternet.common.controller;


import com.galaxyinternet.common.utils.StrUtils;
import com.galaxyinternet.model.sopfile.SopDownLoad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
@RequestMapping("/galaxy/manual")
public class UserManualController {
    final Logger logger = LoggerFactory.getLogger(UserManualController.class);

/*
    <!--星河投用户文档文件夹-->
    <sop.file.userdir.path>/data/apps/osstemp/xhtDir</sop.file.userdir.path>
    <!--用户手册文件名， 文件类型不固定-->
    <sop.user.manual.name>XHT_USER_MANUAL_001</sop.user.manual.name>

    public static  @Value("${sop.file.userdir.path}") String userdir;
    public static  @Value("${sop.user.manual.name}") String userManualName;
*/


    public static final String MANUAL_DIRECTORY = "/template/userManual";
  //  public static final String MANUAL_MARK_NAME = "XHT_USER_MANUAL_001";
    public static final String MANUAL_MARK_NAME = "星河投系统用户手册";
    
    public static final String MANUAL_STR_NAME = "用户手册";


    @RequestMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response)
    {
        //File dir = new File(userdir);
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            String manualDir = request.getSession().getServletContext().getRealPath("") + MANUAL_DIRECTORY;
            File dir = new File(manualDir);

            File[] files = dir.listFiles();
            if(files == null || files.length == 0)
            {
                File createFile = new File(dir,MANUAL_MARK_NAME+".txt");
                createFile.createNewFile();
            }

            files = dir.listFiles();
            for(File file : files){
                if(file.getName().contains(MANUAL_MARK_NAME))
                {
                    String suffix = files[0].getName().substring(files[0].getName().lastIndexOf("."), files[0].getName().length());

                    response.reset();
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("application/x-download"); //msword OCTET-STREAM
                    response.setHeader("Content-Disposition","attachment; filename=" + getFileNameByBrowser(request,MANUAL_STR_NAME+suffix));

                    in = new BufferedInputStream(new FileInputStream(files[0]));
                    out = new BufferedOutputStream(response.getOutputStream());

                    int dump = 0;
                    byte[] buffer = new byte[1024*5];

                    while ((dump =in.read(buffer))!=-1){
                        out.write(buffer,0,dump);
                    }

                    out.flush();
                    break;
                }
            }
        }catch (Exception e){
            logger.error("down user manual failed！",e);
        }finally {
            try {
                if(out!=null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if(in!=null) in.close();
            } catch (IOException e) {
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
