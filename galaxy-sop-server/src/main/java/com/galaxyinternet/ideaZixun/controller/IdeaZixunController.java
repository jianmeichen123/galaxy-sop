package com.galaxyinternet.ideaZixun.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
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
import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.model.idea.Idea;
import com.galaxyinternet.model.idea.IdeaZixun;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.IdeaZixunService;
import com.galaxyinternet.service.UserRoleService;

@Controller
@RequestMapping("/galaxy/zixun")
public class IdeaZixunController extends BaseControllerImpl<IdeaZixun, IdeaZixunBo> {

	private final static Logger logger = LoggerFactory.getLogger(IdeaZixunController.class);

	@Autowired
	private IdeaZixunService ideaZixunService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;


	@Override
	protected BaseService<IdeaZixun> getBaseService() {
		return this.ideaZixunService;
	}
	
	
	
	/**
	 * 资讯首页 
	 */
	@RequestMapping(value = "/index")
	public String zixunIndex() {
		return "idea/";
	}
	
	
	/**
	 * 资讯  添加 编辑  页面
	 */
	@RequestMapping(value = "/operat")
	public String zixunOperat() {
		return "idea/zixun/";
	}
	
	
	/**
	 * 资讯  查看  页面
	 */
	@RequestMapping(value = "/info")
	public String zixunInfo() {
		return "idea/zixun/";
	}
	
	

	
	/**
	 * 资讯  列表
	 */
	@ResponseBody
	@RequestMapping(value = "/showlist", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<IdeaZixunBo> showPageList(HttpServletRequest request,@RequestBody IdeaZixunBo query ) {
		
		ResponseData<IdeaZixunBo> responseBody = new ResponseData<IdeaZixunBo>();
		
		try {
			//User user = (User)getUserFromSession(request);
			User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());  //UserConstant.DSZ
			
			
			
			PageRequest pageable = new PageRequest();

			Integer pageNum = query.getPageNum() != null ? query.getPageNum() : 0;
			Integer pageSize = query.getPageSize() != null ? query.getPageSize() : 10;
			
			Direction direction = Direction.DESC;
			if(StringUtils.isNotEmpty(query.getDirection())){
				direction = Direction.fromString(query.getDirection());
			}
			pageable = new PageRequest(pageNum, pageSize, new Sort(direction,query.getProperty()));
			pageable = new PageRequest(pageNum, pageSize,direction,"created_time");
			pageable = new PageRequest(pageNum,pageSize);
			
			
			//时间
			if(query.getBeginTime() != null){
				Date date = DateUtil.convertStringToDate(query.getBeginTime());
				query.setBeginTimeLong(DateUtil.getSearchFromDate(date).getTime());
			}
			if(query.getEndTime() != null){
				Date date = DateUtil.convertStringToDate(query.getEndTime());
				query.setEndTimeLong(DateUtil.getSearchToDate(date).getTime());
			}
			
			
			Page<IdeaZixunBo> pageList = ideaZixunService.queryZixunPage(query, pageable );
			
			
			
			
			
			responseBody.setPageList(pageList);
			responseBody.setResult(new Result(Status.OK, ""));
			
			return responseBody;
			
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,"查询失败"));
			
			if(logger.isErrorEnabled()){
				logger.error("queryInterview 查询失败",e);
			}
		}
		
		return responseBody;
	}
	
	
	
	
	
	
	
}
