package com.galaxyinternet.touhou.controller;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
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

import com.galaxyinternet.bo.project.InterviewRecordBo;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.bo.touhou.DeliveryBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.exception.PlatformException;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.file.UploadFileResult;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.oss.OSSFactory;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.framework.core.utils.JSONUtils;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.model.project.FormatData;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.touhou.Delivery;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DeliveryService;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.UserRoleService;
import com.galaxyinternet.service.UserService;

@Controller
@RequestMapping("/galaxy/delivery")
public class DeliveryController extends BaseControllerImpl<Delivery, DeliveryBo> {

	final Logger logger = LoggerFactory.getLogger(DeliveryController.class);

	@Autowired
	private DeliveryService deliveryService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private SopFileService sopFileService;
	@Autowired
	private DepartmentService departmentService;

	private String tempfilePath;

	public String getTempfilePath() {
		return tempfilePath;
	}

	@Value("${sop.oss.tempfile.path}")
	public void setTempfilePath(String tempfilePath) {
		this.tempfilePath = tempfilePath;
	}

	@Override
	protected BaseService<Delivery> getBaseService() {
		return this.deliveryService;
	}
	
	
	
	/**
	 * sop tab页面  项目交割前事项
	 */
	@RequestMapping(value = "/toprodeliver/{pid}", method = RequestMethod.GET)
	public String toprolog(@PathVariable("pid") Long pid, HttpServletRequest request) {
		request.setAttribute("pid", pid);
		return "project/sopinfo/tab_delivery";
	}
	
	
	/**
	 * 弹窗   添加事项
	 */
	@RequestMapping(value = "/toadddeliver", method = RequestMethod.GET)
	public String toAddDeliver(HttpServletRequest request) {
		return "project/tanchuan/delivery";
	}
	
	/**
	 * 弹窗   编辑事项 / 查看
	 */
/*	@RequestMapping(value = "/tomatterdeliver/{deliverid}", method = RequestMethod.GET)
	public ModelAndView toMatterDeliver(@PathVariable("deliverid") Long deliverid,HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("project/tanchuan/delivery_matter");
		Delivery deliver = deliveryService.selectDelivery(deliverid);
		mv.addObject("deliverInfo", GSONUtil.toJson(deliver));
		return mv;
	}
	@RequestMapping(value = "/tomatterdeliver/{deliverid}", method = RequestMethod.GET)
	public String toMatterDeliver(@PathVariable("deliverid") Long deliverid,HttpServletRequest request) {
		Delivery deliver = deliveryService.selectDelivery(deliverid);
		request.setAttribute("deliverInfo", GSONUtil.toJson(deliver));
		return "project/tanchuan/delivery_matter";
	}
	*/
	@RequestMapping(value = "/tomatterdeliver", method = RequestMethod.GET)
	public String toMatterDeliver(HttpServletRequest request) {
		return "project/tanchuan/delivery_matter";
	}
	
	/**
	 * 弹窗 删除事项
	 */
	@RequestMapping(value = "/todeldeliver", method = RequestMethod.GET)
	public String toDelDeliver(HttpServletRequest request) {
		return "project/tanchuan/1tips";
	}
	
	
	/**
	 * 添加/编辑事项
	 */
	@ResponseBody
	@RequestMapping(value = "/operdelivery", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Delivery> operDelivery(@RequestBody Delivery delivery,HttpServletRequest request,HttpServletResponse response ) {
		ResponseData<Delivery> responseBody = new ResponseData<Delivery>();
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
		if(delivery == null || delivery.getProjectId() == null || delivery.getDelDescribe() == null ){
			responseBody.setResult(new Result(Status.ERROR,null, "请完善信息"));
			return responseBody;
		}
		
		try {
			Long id = null;
			boolean isIn = delivery.getId()==null;
			if(isIn){ 
				delivery.setCreatedUid(user.getId()); 
				id = deliveryService.insertDelivery(delivery);
			}else{ 
				delivery.setUpdatedUid(user.getId()); 
				id = deliveryService.updateDelivery(delivery);
			}
			responseBody.setResult(new Result(Status.OK, ""));
			responseBody.setId(id);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "操作失败"));
			
			if(logger.isErrorEnabled()){
				logger.error("operDelivery 操作失败",e);
			}
		}
		return responseBody;
	}
	
	
	
	/**
	 *删除 事项
	 */
	@ResponseBody
	@RequestMapping(value = "/deldelivery/{deliverid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Delivery> delDelivery(@PathVariable("deliverid") Long deliverid,HttpServletRequest request,HttpServletResponse response ) {
		ResponseData<Delivery> responseBody = new ResponseData<Delivery>();
		try {
			deliveryService.delDeliveryById(deliverid);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "删除失败"));
			logger.error("delDelivery 删除失败",e);
		}
		return responseBody;
	}
	
	/**
	 *查询 事项
	 */
	@ResponseBody
	@RequestMapping(value = "/selectdelivery/{deliverid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Delivery> selectDelivery(@PathVariable("deliverid") Long deliverid,HttpServletRequest request,HttpServletResponse response ) {
		ResponseData<Delivery> responseBody = new ResponseData<Delivery>();
		try {
			Delivery deliver = deliveryService.selectDelivery(deliverid);
			responseBody.setEntity(deliver);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "查询失败"));
			logger.error("delDelivery 查询失败",e);
		}
		return responseBody;
	}
	
	/**
	 *查询 事项列表
	 */
	@ResponseBody
	@RequestMapping(value = "/queryprodeliverypage", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<DeliveryBo> queryProDeliveryPage(HttpServletRequest request, @RequestBody DeliveryBo query) {
		
		ResponseData<DeliveryBo> responseBody = new ResponseData<DeliveryBo>();
		
		try {
			Direction direction = Direction.DESC;
			String property = "updated_time";
			if(!StringUtils.isEmpty(query.getProperty())){
				if("desc".equals(query.getDirection())){
					direction = Direction.DESC;
				}else{
					direction = Direction.ASC;
				}
				property = "created_time";
			}
			PageRequest pageRequest = new PageRequest(query.getPageNum(), query.getPageSize(),direction,property);
			
			Page<DeliveryBo> deliverypage =  deliveryService.queryDeliveryPageList(query, pageRequest);
			
			responseBody.setPageList(deliverypage);
			responseBody.setResult(new Result(Status.OK, ""));
			return responseBody;
		} catch (PlatformException e) {
			responseBody.setResult(new Result(Status.ERROR, null,"列表查询失败"));
			logger.error("queryProDeliveryPage ", e);
		}
		return responseBody;
	}
	
	
}
