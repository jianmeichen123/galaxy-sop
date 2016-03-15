package com.galaxyinternet.common.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.UserBo;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.model.Header;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.user.Menus;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.UserRoleService;

@Controller
@RequestMapping("/galaxy/common")
public class CommonController extends BaseControllerImpl<User, UserBo>{
	
	final Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	/**
	 * 动态生成左边菜单项列表
	 * @author yangshuhua
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/menu/{selected}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Menus> menu(@PathVariable("selected") int selected, HttpServletRequest request){
		ResponseData<Menus> responseBody = new ResponseData<Menus>();
		Header header = new Header();
		header.setAttachment(selected);
		responseBody.setHeader(header);
		String p = request.getRequestURI();
		String url = request.getRequestURL().toString();
		String contextPath = request.getContextPath();
		String u = null;
		if(contextPath == null || "".equals(contextPath.trim())){
			//rl.substring(0, url.indexOf(p) + 1);
			u = url.substring(0, url.indexOf(p) + 1);
		}else{
			u = url.substring(0, url.indexOf(contextPath) + contextPath.length() + 1);
		}
		
		User user = (User) getUserFromSession(request);
		
		List<Menus> tabs = new ArrayList<Menus>();
		String params = Constants.SESSOPM_SID_KEY + "=" + getSessionId(request) + "&" + Constants.REQUEST_URL_USER_ID_KEY + "=" + getUserId(request);
		//通用Tab
		tabs.add(new Menus(1L, "工作界面", u + "galaxy/index?" + params));
		tabs.add(new Menus(2L, "待办任务", u + "galaxy/soptask?" + params));
		tabs.add(new Menus(3L, "消息提醒", u + ""));
		List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
		
		if(roleIdList.contains(UserConstant.HHR) || roleIdList.contains(UserConstant.TZJL)){
			tabs.add(new Menus(4L, "添加项目", u + "galaxy/app?" + params));
			tabs.add(new Menus(5L, "我的项目", u + "galaxy/mpl?" + params));
			tabs.add(new Menus(6L, "访谈跟进", u + "galaxy/project/progress/interView?" + params));
			tabs.add(new Menus(7L, "会议纪要", u + "galaxy/project/progress/meetView?" + params));
		}
		
		
		if(roleIdList.contains(UserConstant.HRZJ) || roleIdList.contains(UserConstant.HRJL)
				|| roleIdList.contains(UserConstant.CWZJ) || roleIdList.contains(UserConstant.CWJL)
				|| roleIdList.contains(UserConstant.FWZJ) || roleIdList.contains(UserConstant.FWJL)){
			tabs.add(new Menus(9L, "尽调报告", u + ""));
		}
		
		if(roleIdList.contains(UserConstant.HRZJ) || roleIdList.contains(UserConstant.HRJL)){
			tabs.add(new Menus(10L, "完善简历", u + ""));
		}
		
		if(roleIdList.contains(UserConstant.CWZJ) || roleIdList.contains(UserConstant.CWJL)){
			tabs.add(new Menus(11L, "付款凭证", u + ""));
		}
		
		if(roleIdList.contains(UserConstant.FWZJ) || roleIdList.contains(UserConstant.FWJL)){
			tabs.add(new Menus(12L, "股权交割", u + ""));
		}
		
		tabs.add(new Menus(13L, "模板管理", u + "galaxy/template?" + params));
		tabs.add(new Menus(14L, "档案管理", u + "galaxy/sopFile/toFileList?" + params));
		
		//管理员
		if(roleIdList.contains(16L)){
			tabs.clear();
			tabs.add(new Menus(15L, "用户管理", "http://fx.qa.galaxyinternet.com/platform/galaxy/user?" + params));
			tabs.add(new Menus(16L, "数据字典", "http://fx.qa.galaxyinternet.com/platform/galaxy/dict/index?" + params));
		}
		
		//高管
		if(roleIdList.contains(1L) || roleIdList.contains(2L)){
			tabs.clear();
//			tabs.add(new Menus(1L, "工作界面", "http://127.0.0.1:8777/galaxy-sop-server/galaxy/index?" + params));
//			tabs.add(new Menus(3L, "消息提醒", u + ""));
//			tabs.add(new Menus(16L, "项目查询", "http://127.0.0.1:8777/report/galaxy/report/projects?" + params));
//			tabs.add(new Menus(16L, "数据简报", "http://127.0.0.1:8080/report/galaxy/report/dataBriefing?" + params));
//			tabs.add(new Menus(16L, "项目分析", "http://127.0.0.1:8080/report/galaxy/report/projectAnalysis?" + params));
//			tabs.add(new Menus(16L, "绩效考核", "http://127.0.0.1:8080/report/galaxy/report/kpi?" + params));
//			tabs.add(new Menus(16L, "投后项目跟踪", "http://127.0.0.1:8080/report/galaxy/report/afterInvestTrack?" + params));
//			tabs.add(new Menus(16L, "投后业务运营", "http://127.0.0.1:8080/report/galaxy/report/afterInvestBusiness?" + params));
//			tabs.add(new Menus(16L, "投后企业财报", "http://127.0.0.1:8080/report/galaxy/report/afterInvestFinace?" + params));
			
			tabs.add(new Menus(1L, "工作界面", "http://fx.qa.galaxyinternet.com/sop/galaxy/index?" + params));
			tabs.add(new Menus(3L, "消息提醒", u + ""));
			tabs.add(new Menus(16L, "项目查询", "http://fx.qa.galaxyinternet.com/report/galaxy/report/projects?" + params));
			tabs.add(new Menus(16L, "数据简报", "http://fx.qa.galaxyinternet.com/report/galaxy/report/dataBriefing?" + params));
			tabs.add(new Menus(16L, "项目分析", "http://fx.qa.galaxyinternet.com/report/galaxy/report/projectAnalysis?" + params));
			tabs.add(new Menus(16L, "绩效考核", "http://fx.qa.galaxyinternet.com/report/galaxy/report/kpi?" + params));
			tabs.add(new Menus(16L, "投后项目跟踪", "http://fx.qa.galaxyinternet.com/report/galaxy/report/afterInvestTrack?" + params));
			tabs.add(new Menus(16L, "投后业务运营", "http://fx.qa.galaxyinternet.com/report/galaxy/report/afterInvestBusiness?" + params));
			tabs.add(new Menus(16L, "投后企业财报", "http://fx.qa.galaxyinternet.com/report/galaxy/report/afterInvestFinace?" + params));
		}
	    responseBody.setEntityList(tabs);
		return responseBody;
	}

	@Override
	protected BaseService<User> getBaseService() {
		return null;
	}

}
