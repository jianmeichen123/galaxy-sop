package com.galaxyinternet.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.idea.Idea;
import com.galaxyinternet.service.IdeaService;
@Controller
@RequestMapping("/galaxy/idea")
public class IdeaController extends BaseControllerImpl<Idea, Idea> {
	private static final Logger logger = Logger.getLogger(IdeaController.class);
	@Autowired
	private IdeaService ideaService;
	@Override
	protected BaseService<Idea> getBaseService() {
		return this.ideaService;
	}

	
	@RequestMapping(method = RequestMethod.GET)
	public String list()
	{
		return "idea/idea_list";
	}
	@ResponseBody
	@RequestMapping("/search")
	public ResponseData<Idea> search(@RequestBody Idea idea, HttpServletRequest request)
	{
		ResponseData<Idea> resp = new ResponseData<Idea>();
		
		try {
			PageRequest pageable = new PageRequest();
			Integer pageNum = idea.getPageNum() != null ? idea.getPageNum() : 0;
			Integer pageSize = idea.getPageSize() != null ? idea.getPageSize() : 10;
			pageable.setPage(pageNum);
			pageable.setSize(pageSize);
			Page<Idea> page = ideaService.queryPageList(idea, pageable);
			resp.setPageList(page);
		} catch (Exception e) {
			resp.getResult().addError("查询创意出错");
			logger.error("查询创意出错", e);
		}
		return resp;
	}
}
