package com.galaxyinternet.hologram.controller;


import com.galaxyinternet.bo.hologram.InformationTitleBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.utils.DocExportUtil;
import com.galaxyinternet.common.utils.DocxExportUtil;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.project.controller.ProjectProgressController;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.hologram.CacheOperationService;
import com.galaxyinternet.service.hologram.InformationDictionaryService;
import com.galaxyinternet.service.hologram.InformationResultService;
import com.galaxyinternet.service.hologram.InformationTitleService;
import com.galaxyinternet.service.hologram.ReportExportService;
import com.galaxyinternet.service.hologram.ScoreInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/galaxy/binfo")
public class BaseInfoController  extends BaseControllerImpl<InformationTitle, InformationTitleBo> {

	final Logger logger = LoggerFactory.getLogger(ProjectProgressController.class);

	@Autowired
	private Cache cache;

	@Autowired
	private InformationTitleService informationTitleService;

	@Autowired
	private InformationDictionaryService informationDictionaryService;

	@Autowired
	private CacheOperationService cacheOperationService;

	@Autowired
	private ReportExportService reportExportService;

	@Autowired
	private InformationResultService informationResultService;

	@Autowired
	private ProjectService projectService;
	@Autowired
	private ScoreInfoService scoreInfoService;


	@Value("${sop.oss.tempfile.path}")
	private String tempfilePath;


	@Override
	protected BaseService<InformationTitle> getBaseService() {
		return this.informationTitleService;
	}




	/**
	 * 全息报告 ： docx 下载
	 */
	public static final String tempath = "/template/qxbg";    //  模板地址
	public static final String[] temp_xml = new String[]{"document.xml","header1.xml","header2.xml"};
	public static final String temp_docx = "qxbg_lr_temp.docx";   //内容 .xml .docx

	@RequestMapping("/downxNO/{pid}")
	public void downNOdocx(@PathVariable("pid") Long pid, HttpServletRequest request, HttpServletResponse response)
	{
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);

		String currTime = System.currentTimeMillis()+"";
		String ymd = DateUtil.convertDateToStringForTeamCode(new Date());

		Project project = projectService.queryById(pid);
		Map<String,Object> map = reportExportService.titleAnswerConversionTask(user.getId(),project,"NO",currTime,tempfilePath);

		String docxName = project.getProjectName() + "全息报告"+ymd+".docx";

		try {
			/*
			 * request
			 * @param templatePath 模板文件位置
			 * @param filePath 保存路径
			 * @param fileName 保存名称,  生成的docx文件
			 */
			DocxExportUtil docExportUtil1 = new DocxExportUtil(request,response,tempath, tempfilePath, docxName);
			docExportUtil1.downDocxAsZip(map,currTime,temp_xml,temp_docx);

			/*String zipName = project.getProjectName() + "全息报告.zip";
			Map<String, String> dname_sname = new HashMap<>();
			dname_sname.put(dfn1,fn1);
			dname_sname.put(dfn2,fn2);

			DocxExportUtil.downZip(zipName,dname_sname,tempfilePath,request,response);*/
		} catch (Exception e) {
			logger.error("downDoc ",e);
		}
	}
	/*
	public static final String temp1x = "qxbg_gl_temp";   //概览 .xml .docx
	public static final String temp2x = "qxbg_lr_temp";   //内容 .xml .docx
	@RequestMapping("/downxNO/{pid}")
	public void downNOdocx(@PathVariable("pid") Long pid, HttpServletRequest request, HttpServletResponse response)
	{
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);

		String currTime = System.currentTimeMillis()+"";

		Project project = projectService.queryById(pid);
		Map<String,Object> map = reportExportService.titleAnswerConversionTask(user.getId(),project,"NO",currTime,tempfilePath);

		String fn1 = project.getProjectName() + "全息报告概览.docx";
		String fn2 = project.getProjectName() + "全息报告内容.docx";

		String dfn1 = currTime + "qxgl";
		String dfn2 = currTime + "qxlr";

		try {
			*//*
			 * request
			 * @param templatePath 模板文件位置
			 * @param templateName  xml docx 模板文件名称
			 * @param filePath 保存路径
			 * @param fileName 保存名称, xml docx 模板生成的文件
			 *//*
			DocxExportUtil docExportUtil1 = new DocxExportUtil(request,tempath, temp1x, tempfilePath, dfn1);
			DocxExportUtil docExportUtil2 = new DocxExportUtil(request,tempath, temp2x, tempfilePath, dfn2);
			docExportUtil1.creatDocxAsZip(map,currTime,false);
			docExportUtil2.creatDocxAsZip(map,currTime,true);

			String zipName = project.getProjectName() + "全息报告.zip";
			Map<String, String> dname_sname = new HashMap<>();
			dname_sname.put(dfn1,fn1);
			dname_sname.put(dfn2,fn2);

			DocxExportUtil.downZip(zipName,dname_sname,tempfilePath,request,response);
		} catch (Exception e) {
			logger.error("downDoc ",e);
		}
	}*/
	/**
	 * 全息报告 ： doc 下载

	public static final String temp1 = "qxbg-hb-temp.xml"; //横板
	public static final String temp2 = "qxbg-zh-temp-xia.xml"; //综合
	@RequestMapping("/downNO/{pid}")
	public void downNOdoc(@PathVariable("pid") Long pid, HttpServletRequest request, HttpServletResponse response)
	{
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);

		String currTime = System.currentTimeMillis()+"";

		Project project = projectService.queryById(pid);
		Map<String,Object> map = reportExportService.titleAnswerConversionTask(user.getId(),project,"NO",currTime,tempfilePath);

		String fn1 = project.getProjectName() + "全息报告概览.doc";
		String fn2 = project.getProjectName() + "全息报告内容.doc";

		String dfn1 = currTime + "qxgl.doc";
		String dfn2 = currTime + "qxlr.doc";

		try {
			DocExportUtil docExportUtil1 = new DocExportUtil(request,tempath, temp1, tempfilePath, dfn1);
			DocExportUtil docExportUtil2 = new DocExportUtil(request,tempath, temp2, tempfilePath, dfn2);
			docExportUtil1.createDoc(map);
			docExportUtil2.createDoc(map);

			String zipName = project.getProjectName() + "全息报告.zip";
			Map<String, String> dname_sname = new HashMap<>();
			dname_sname.put(dfn1,fn1);
			dname_sname.put(dfn2,fn2);

			DocExportUtil.downZip(zipName,dname_sname,currTime,tempfilePath,request,response);
		} catch (Exception e) {
			logger.error("downDoc ",e);
		}
	}
	 */



	/**
	 * 评测报告 ： docx 下载
	 */
	public static final String pc_tempath = "/template/pcbg";    //  模板地址
	public static final String[] pc_temp_xml = new String[]{"document.xml","header2.xml"};
	public static final String pc_temp_docx = "pcbg_temp.docx";   //内容 .xml .docx

	@RequestMapping("/downxEN/{pid}")
	public void downENdocx(@PathVariable("pid") Long pid, HttpServletRequest request, HttpServletResponse response)
	{
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);

		String currTime = System.currentTimeMillis()+"";
		String ymd = DateUtil.convertDateToStringForTeamCode(new Date());

		Project project = projectService.queryById(pid);
		Map<String,Object> map = reportExportService.titleAnswerConversionTask(user.getId(),project,"EN",currTime,tempfilePath);

		map.putAll(reportExportService.titleScoreResult(pid,"EN"));

		Map<String,String>  weight = scoreInfoService.getTabWeight("EN",pid);
		map.putAll(weight);

		String docxName = project.getProjectName() + "评测报告"+ymd+".docx";

		try {
			/*
			 * request
			 * @param templatePath 模板文件位置
			 * @param filePath 保存路径
			 * @param fileName 保存名称,  生成的docx文件
			 */
			DocxExportUtil docExportUtil1 = new DocxExportUtil(request,response,pc_tempath, tempfilePath, docxName);
			docExportUtil1.downDocxAsZip(map,currTime,pc_temp_xml,pc_temp_docx);

			/*String zipName = project.getProjectName() + "全息报告.zip";
			Map<String, String> dname_sname = new HashMap<>();
			dname_sname.put(dfn1,fn1);
			dname_sname.put(dfn2,fn2);

			DocxExportUtil.downZip(zipName,dname_sname,tempfilePath,request,response);*/
		} catch (Exception e) {
			logger.error("downDoc ",e);
		}
	}
	/**
	 * 评测报告 ： docx 下载

	public static final String temp1x_pc = "pcbg_gl_temp";   //概览 .xml .docx
	public static final String temp2x_pc = "pcbg_lr_temp";   //内容 .xml .docx
	public static final String tempath_pc = "/template/pcbg";    //  模板地址
	@RequestMapping("/downxEN/{pid}")
	public void downENdocx(@PathVariable("pid") Long pid, HttpServletRequest request, HttpServletResponse response)
	{
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);

		String currTime = System.currentTimeMillis()+"";
		String ymd = DateUtil.convertDateToStringForTeamCode(new Date());

		Project project = projectService.queryById(pid);
		Map<String,Object> map = reportExportService.titleAnswerConversionTask(user.getId(),project,"EN",currTime,tempfilePath);
		map.putAll(reportExportService.titleScoreResult(pid,"EN"));

		Map<String,String>  weight = scoreInfoService.getTabWeight("EN",pid);
		map.putAll(weight);

		String fn1 = project.getProjectName() + "评测报告概览"+ymd+".docx";
		String fn2 = project.getProjectName() + "评测报告内容"+ymd+".docx";

		String dfn1 = currTime + "pcgl";
		String dfn2 = currTime + "pclr";

		try {
			DocxExportUtil docExportUtil1 = new DocxExportUtil(request,tempath_pc, temp1x_pc, tempfilePath, dfn1);
			DocxExportUtil docExportUtil2 = new DocxExportUtil(request,tempath_pc, temp2x_pc, tempfilePath, dfn2);
			docExportUtil1.creatDocxAsZip(map,currTime,false);
			docExportUtil2.creatDocxAsZip(map,currTime,true);

			String zipName = project.getProjectName() + "评测报告"+ymd+".zip";
			Map<String, String> dname_sname = new HashMap<>();
			dname_sname.put(dfn1,fn1);
			dname_sname.put(dfn2,fn2);

			DocxExportUtil.downZip(zipName,dname_sname,tempfilePath,request,response);
		} catch (Exception e) {
			logger.error("downDoc ",e);
		}
	}
		*/


}



