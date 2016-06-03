package com.galaxyinternet.soptask.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.SopTaskBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.dictEnum.DictEnum;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopTaskService;

@Controller
@RequestMapping("/galaxy/appSoptask")
public class AppSopTaskController extends BaseControllerImpl<SopTask, SopTaskBo> {

	final Logger logger = LoggerFactory.getLogger(AppSopTaskController.class);
	
	@Autowired
	private SopTaskService sopTaskService;	

	@Override
	protected BaseService<SopTask> getBaseService() {
		// TODO Auto-generated method stub
		return this.sopTaskService;
	}
	
	/**
	 * App端待办任务认领
	 */
	 @com.galaxyinternet.common.annotation.Logger
	@ResponseBody
	@RequestMapping(value = "/{id}/receiveAppTask",method = RequestMethod.GET ,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<SopTask> receiveHandlerTask(@PathVariable("id") String tid ,HttpServletRequest request) {
		//当前登录人
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);					
		ResponseData<SopTask> responseBody = new ResponseData<SopTask>();
		try {
			SopTask sopTask=new SopTask();        
			if(StringUtils.isNotBlank(tid)){
				sopTask.setId(Long.parseLong(tid));
			}
			sopTask.setTaskStatus(DictEnum.taskStatus.待完工.getCode());
		   
			SopTask queryById = sopTaskService.queryById(Long.parseLong(tid));
	
			sopTask.setAssignUid(user.getId());
			sopTask.setTaskFlag(queryById.getTaskFlag());
			sopTask.setProjectId(queryById.getProjectId());
			 sopTaskService.updateById(sopTask);
//			 request.setAttribute("taskid", tid);	
			 responseBody.setResult(new Result(Status.OK,null,"待办认领成功!"));
//			k responseBody.setResult(new Result(Status.ERROR, null, "必要的参数丢失!"));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null,"待办认领失败!"));
			logger.error("待办认领操作发生异常", e);
		}
		return responseBody;
	}
}
