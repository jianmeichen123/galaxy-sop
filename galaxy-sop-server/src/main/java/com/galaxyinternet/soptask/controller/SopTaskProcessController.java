package com.galaxyinternet.soptask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.galaxyinternet.bo.SopTaskBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.service.SopTaskService;
@Controller
@RequestMapping("/galaxy/taskprocess")
public class SopTaskProcessController extends BaseControllerImpl<SopTask, SopTaskBo> 
{
	@Autowired
	private SopTaskService sopTaskService;

	@Override
	protected BaseService<SopTask> getBaseService() {
		return sopTaskService;
	}
	
	@RequestMapping("/showTaskInfo")
	public ModelAndView showFileInfo(@RequestParam Long projectId,@RequestParam String pageName)
	{
		ModelAndView mv = new ModelAndView("/taskProcess/task_info");
		mv.addObject("projectId", projectId);
		mv.addObject("pageName", pageName);
		return mv;
	}
	@RequestMapping("/showFileList")
	public String showFileList(@RequestParam String pageName)
	{
		return "/taskProcess/"+pageName;
	}

}
