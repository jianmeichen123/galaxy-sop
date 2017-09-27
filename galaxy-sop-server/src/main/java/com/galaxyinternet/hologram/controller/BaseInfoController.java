package com.galaxyinternet.hologram.controller;


import com.galaxyinternet.bo.hologram.InformationTitleBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.utils.DocExportUtil;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.service.BaseService;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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


	@Value("${sop.oss.tempfile.path}")
	private String tempfilePath;

	public static final String temp1 = "qxbg-hb-temp.xml"; //横板
	public static final String temp2 = "qxbg-zh-temp-xia.xml"; //综合
	public static final String tempath = "/template";  //  模板地址


	@Override
	protected BaseService<InformationTitle> getBaseService() {
		return this.informationTitleService;
	}


	/**
	 * 全息报告 ： doc 下载
	 */
	@RequestMapping("/downNO/{pid}")
	public void downNOdoc(@PathVariable("pid") Long pid, HttpServletRequest request, HttpServletResponse response)
	{
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);

		String currTime = System.currentTimeMillis()+"";

		Project project = projectService.queryById(pid);
		Map<String,Object> map = reportExportService.titleAnswerConversionTask(user.getId(),project,"NO");

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




}



