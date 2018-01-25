package com.galaxyinternet.hologram.controller;

import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.hologram.CacheOperationService;
import com.galaxyinternet.service.hologram.ReportExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/galaxy/test")
public class TestController{
	final Logger logger = LoggerFactory.getLogger(TestController.class);


	@Autowired
	private CacheOperationService cacheOperationService;

	@Autowired
	private ReportExportService reportExportService;

	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = "/laobanTest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String platform(HttpServletRequest request) {
		return "seven_report/hologram/test/laoban_test";
	}

	
	 // /galaxy/test/jtym1
	@RequestMapping(value = "/jtym1")
	public String platformTest(HttpServletRequest request) {
		return "project/infoenter/infoList";
	}
	@RequestMapping(value = "/jtym2")
	public String platformTest2(HttpServletRequest request) {
		return "project/infoenter/info";
	}
	@RequestMapping(value = "/infoJsp")
	public String platformTest5(HttpServletRequest request) {
		return "project/infoenter/infoJsp";
	}

	@RequestMapping(value = "/infoDJsp")
	public String platformTestD(HttpServletRequest request) {
		return "project/infoenter/infoDJsp";
	}
		
	@RequestMapping(value = "/searchResult")
	public String platformTest4(HttpServletRequest request) {
		return "project/search";
	}
	
	@RequestMapping(value = "/writeStand")
	public String platformTest123(HttpServletRequest request) {
		return "project/sopinfo/tab_writeStand";
	}
	@RequestMapping(value = "/writePage")
	public String platformTest12(HttpServletRequest request) {
		return "write/writePage";
	}
	
/*

	@Value("${sop.oss.tempfile.path}")
	private String tempfilePath;

	public static final String temp1 = "qxbg-hb-temp.xml"; //横板
	public static final String temp2 = "qxbg-zh-temp.xml"; //综合
	public static final String tempath = "/template";  //  模板地址

	@RequestMapping("/down/{pid}")
	public void downDoc(@PathVariable("pid") Long pid, HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);

		String currTime = System.currentTimeMillis()+"";

		Project project = projectService.queryById(pid);
		Map<String,Object> map = reportExportService.titleAnswerConversionTask(user.getId(),project,"NO");

		String fn1 = currTime + project.getProjectName() + "全息报告概览.docx";
		String fn2 = currTime + project.getProjectName() + "全息报告内容.docx";
		try {
			DocExportUtil docExportUtil1 = new DocExportUtil(request,tempath, temp1, tempfilePath, fn1);
			DocExportUtil docExportUtil2 = new DocExportUtil(request,tempath, temp2, tempfilePath, fn2);
			docExportUtil1.createDoc(map);
			docExportUtil2.createDoc(map);

			String zipName = project.getProjectName() + "全息报告.zip";
			List<String> fnlist = new ArrayList<>();
			fnlist.add(fn1);
			fnlist.add(fn2);
			DocExportUtil.downZip(zipName,fnlist,currTime,tempfilePath,request,response);
		} catch (Exception e) {
			logger.error("downDoc ",e);
		}
	}

*/



}