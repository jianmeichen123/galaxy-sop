package com.galaxyinternet.ideaZixun.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.galaxyinternet.bo.IdeaZixunBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.idea.IdeaZixun;
import com.galaxyinternet.service.IdeaZixunService;

@Controller
@RequestMapping("/galaxy/zixun")
public class IdeaZixunController extends BaseControllerImpl<IdeaZixun, IdeaZixunBo> {

	private final static Logger logger = LoggerFactory.getLogger(IdeaZixunController.class);

	@Autowired
	private IdeaZixunService ideaZixunService;
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;


	@Override
	protected BaseService<IdeaZixun> getBaseService() {
		return this.ideaZixunService;
	}
	
	
}
