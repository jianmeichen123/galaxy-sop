package com.galaxyinternet.project.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.project.MeetingSchedulingBo;
import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.service.MeetingSchedulingService;

/**
 * 首页查询相关
 */
@Controller
@RequestMapping("/galaxy/home")
public class HomePageSearchController
		extends
			BaseControllerImpl<MeetingScheduling, MeetingSchedulingBo> {

	final Logger logger = LoggerFactory
			.getLogger(HomePageSearchController.class);


	@Autowired
	private MeetingSchedulingService meetingSchedulingService;
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;

	@Override
	protected BaseService<MeetingScheduling> getBaseService() {
		return this.meetingSchedulingService;
	}

	/**
	 * 首页top5立项会排期
	 * 
	 * @author zhaoying
	 * @param request
	 * @param query
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/top5ProjectMeeting",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingSchedulingBo> top5ProjectMeeting(
			HttpServletRequest request) {

		ResponseData<MeetingSchedulingBo> responseBody = new ResponseData<MeetingSchedulingBo>();
		/*Object obj = request.getSession()
				.getAttribute(Constants.SESSION_USER_KEY);
		if (obj == null) {
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}*/
		// User user = (User)
		// request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		try {

			List<MeetingSchedulingBo> list = meetingSchedulingService
					.selectTop5ProjectMeetingByType(SopConstant.PROJECT_MEETING);
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setEntityList(list);
			return responseBody;

		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,
					"selectTop5ProjectMeeting faild"));

			if (logger.isErrorEnabled()) {
				logger.error("selectTop5ProjectMeeting ", e);
			}
		}

		return responseBody;
	}

	/**
	 * 立项会排期more
	 * 
	 * @author zhaoying
	 * @param request
	 * @param query
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/moreProjectMeeting",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingSchedulingBo> moreProjectMeeting(
			HttpServletRequest request) {

		ResponseData<MeetingSchedulingBo> responseBody = new ResponseData<MeetingSchedulingBo>();
	/*	Object obj = request.getSession()
				.getAttribute(Constants.SESSION_USER_KEY);
		if (obj == null) {
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}
*/		// User user = (User)
		// request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		try {

			List<MeetingSchedulingBo> list = meetingSchedulingService
					.selectProjectMeetingByType(SopConstant.PROJECT_MEETING);
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setEntityList(list);
			return responseBody;

		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,
					"selectMoreProjectMeeting faild"));

			if (logger.isErrorEnabled()) {
				logger.error("selectMoreProjectMeeting ", e);
			}
		}

		return responseBody;
	}
	
	/**
	 * 首页投决会排期前5
	 * gxc
	 */
	
	@ResponseBody
	@RequestMapping(value = "/ProjectVoteWill",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingSchedulingBo> ProjectVoteWill(
			HttpServletRequest request) {

		ResponseData<MeetingSchedulingBo> responseBody = new ResponseData<MeetingSchedulingBo>();
		/*Object obj = request.getSession()
				.getAttribute(Constants.SESSION_USER_KEY);
		if (obj == null) {
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}*/

		try {

			List<MeetingSchedulingBo> list = meetingSchedulingService
					.selectTop5ProjectMeetingByType(SopConstant.VOTE_MEETING);
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setEntityList(list);
			return responseBody;

		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,
					"selectTop5ProjectMeeting faild"));

			if (logger.isErrorEnabled()) {
				logger.error("selectTop5ProjectMeeting ", e);
			}
		}

		return responseBody;
	}
	@ResponseBody
	@RequestMapping(value = "/moreProjectVoteWill",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingSchedulingBo> moreProjectVoteWill(
			HttpServletRequest request) {

		ResponseData<MeetingSchedulingBo> responseBody = new ResponseData<MeetingSchedulingBo>();
		/*Object obj = request.getSession()
				.getAttribute(Constants.SESSION_USER_KEY);
		if (obj == null) {
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}*/
		// User user = (User)
		// request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		try {

			List<MeetingSchedulingBo> list = meetingSchedulingService
					.selectProjectMeetingByType(SopConstant.VOTE_MEETING);
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setEntityList(list);
			return responseBody;

		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,
					"selectMoreProjectVoteWill faild"));

			if (logger.isErrorEnabled()) {
				logger.error("selectMoreProjectVoteWill ", e);
			}
		}

		return responseBody;
	}
	/**
	 * 首页top5CEO评审排期
	 * 
	 * @author wangkun
	 * @param request
	 * @param query
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/top5CeoPsMeeting",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingSchedulingBo> top5CeoPsMeeting(
			HttpServletRequest request) {

		ResponseData<MeetingSchedulingBo> responseBody = new ResponseData<MeetingSchedulingBo>();
		/*Object obj = request.getSession()
				.getAttribute(Constants.SESSION_USER_KEY);
		if (obj == null) {
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}*/
		// User user = (User)
		// request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		try {

			List<MeetingSchedulingBo> list = meetingSchedulingService
					.selectTop5ProjectMeetingByType(SopConstant.CEOPS_MEETING);
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setEntityList(list);
			return responseBody;

		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,
					"selectTop5CeoPsMeeting faild"));

			if (logger.isErrorEnabled()) {
				logger.error("selectTop5CeoPsMeeting ", e);
			}
		}

		return responseBody;
	}

	/**
	 * CEO评审排期more
	 * 
	 * @author wangkun
	 * @param request
	 * @param query
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/moreCeoPsMeeting",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingSchedulingBo> moreCeoPsMeeting(
			HttpServletRequest request) {

		ResponseData<MeetingSchedulingBo> responseBody = new ResponseData<MeetingSchedulingBo>();
	/*	Object obj = request.getSession()
				.getAttribute(Constants.SESSION_USER_KEY);
		if (obj == null) {
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}
*/		// User user = (User)
		// request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		try {

			List<MeetingSchedulingBo> list = meetingSchedulingService
					.selectProjectMeetingByType(SopConstant.CEOPS_MEETING);
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setEntityList(list);
			return responseBody;

		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,
					"selectMoreCeoPsMeeting faild"));

			if (logger.isErrorEnabled()) {
				logger.error("selectMoreCeoPsMeeting ", e);
			}
		}

		return responseBody;
	}
	
	/**
	 * 供app使用的立项排期,投诀排期
	 * 用户id,类型必填
	 * @author zhaoying
	 * @param request
	 * @param query
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pageProjectMeeting",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<MeetingScheduling> pageProjectMeeting(@RequestBody MeetingScheduling meeting,
			HttpServletRequest request) {

		ResponseData<MeetingScheduling> responseBody = new ResponseData<MeetingScheduling>();
	/*	Object obj = request.getSession()
				.getAttribute(Constants.SESSION_USER_KEY);
		if (obj == null) {
			responseBody.setResult(new Result(Status.ERROR, "未登录!"));
			return responseBody;
		}
*/		// User user = (User)
		// request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		try {
			Sort sort = null;
			if(meeting != null && meeting.getSortName() != null) {
				if (meeting.getSortDirection().equals("0")) {
					sort = new Sort(Direction.ASC,meeting.getSortName());
				} else {
					sort = new Sort(Direction.DESC,meeting.getSortName());
				}
			}
			Page<MeetingScheduling> pageList = meetingSchedulingService.queryMeetingPageList(meeting,new PageRequest(meeting.getPageNum(), meeting.getPageSize(),sort));
			responseBody.setPageList(pageList);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
			
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR, null,
					"pageProjectMeeting faild"));

			if (logger.isErrorEnabled()) {
				logger.error("pageProjectMeeting ", e);
			}
		}

		return responseBody;
	}
	
	/**
	 * 到立项会排期,投诀会
	 * @return
	 */
	@RequestMapping(value = "/projectMeeting", method = RequestMethod.GET)
	public String projectMeeting() {
		return "meeting/projectMeeting";
	}
	
	
}
