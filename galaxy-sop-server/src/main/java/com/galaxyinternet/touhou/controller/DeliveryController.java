package com.galaxyinternet.touhou.controller;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

import com.galaxyinternet.bo.touhou.DeliveryBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.exception.PlatformException;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.model.hologram.InformationFile;
import com.galaxyinternet.model.hologram.InformationListdata;
import com.galaxyinternet.model.sopfile.SopDownLoad;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.touhou.Delivery;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.platform.constant.PlatformConst;
import com.galaxyinternet.service.DeliveryService;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.hologram.InformationFileService;
import com.galaxyinternet.service.hologram.InformationListdataService;
import com.galaxyinternet.utils.BatchUploadFile;


/**
 * 投后阶段    交割前事项
 */

@Controller
@RequestMapping("/galaxy/delivery")
public class DeliveryController extends BaseControllerImpl<Delivery, DeliveryBo> {

	final Logger logger = LoggerFactory.getLogger(DeliveryController.class);

	@Autowired
	private DeliveryService deliveryService;
	@Autowired
	private SopFileService sopFileService;
	@Autowired
	BatchUploadFile batchUpload;
	@Autowired
	private InformationListdataService informationListdataService;
	@Autowired
	InformationFileService informationFileService;
	@Autowired
	Cache cache;

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
		request.setAttribute("projectId", pid);
		return "project/sopinfo/tab_delivery";
	}
	
	
	/**
	 * 弹窗   添加事项
	 */
	@RequestMapping(value = "/toadddeliver", method = RequestMethod.GET)
	public String toAddDeliver(HttpServletRequest request) {
		return "project/tanchuan/delivery";
	}
	
	
	@RequestMapping(value = "/tomatterdeliver", method = RequestMethod.GET)
	public String toMatterDeliver(HttpServletRequest request) {
		return "project/tanchuan/delivery";
	}
	@RequestMapping(value = "/todeliverinfo", method = RequestMethod.GET)
	public String toDeliverInfo(HttpServletRequest request) {
		return "project/tanchuan/seeinfortc";
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
	@RequestMapping(value = "/operdelivery", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Delivery> operDelivery(@RequestBody Delivery delivery,HttpServletRequest request) {
		
		ResponseData<Delivery> responseBody = new ResponseData<Delivery>();
		
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		
		if(delivery == null || delivery.getProjectId() == null || delivery.getDelDescribe() == null || StringUtils.isBlank(delivery.getDelDescribe())){
			responseBody.setResult(new Result(Status.ERROR,null, "请完善信息"));
			return responseBody;
		}
		
		try {
			if(delivery.getFileReidsKey() != null){
				ResponseData<SopFile> result = batchUpload.batchUpload(user.getId()+delivery.getFileReidsKey());
				if(Status.OK.equals(result.getResult().getStatus())){
					List<SopFile> fileList = result.getEntityList();
					delivery.setFiles(fileList);
				}
			}
			
			Long id = null;
			boolean isIn = delivery.getId()==null;
			if(isIn){ 
				delivery.setCreatedUid(user.getId()); 
				id = deliveryService.insertDelivery(delivery);
			}else{ 
				delivery.setUpdatedUid(user.getId()); 
				id = deliveryService.updateDelivery(delivery);
			}
			responseBody.setResult(new Result(Status.OK,null));
			responseBody.setId(id);
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "操作失败"));
			logger.error("operDelivery 操作失败",e);
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
			informationListdataService.deleteDataRelateFile(deliverid);
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
	public ResponseData<InformationListdata> selectDelivery(@PathVariable("deliverid") Long deliverid,HttpServletRequest request,HttpServletResponse response ) {
		ResponseData<InformationListdata> responseBody = new ResponseData<InformationListdata>();
		try {
			
			if(deliverid != null){
				InformationListdata data = informationListdataService.queryById(deliverid);
				if(!StringUtils.isEmpty(data.getRelateFileId())){
					InformationFile file = new InformationFile();
					file.setTitleId(data.getTitleId());
					file.setProjectId(data.getProjectId());
					file.setFileIds(Arrays.asList(data.getRelateFileId().split(",")));
					List<InformationFile> fileList = informationFileService.queryList(file);
					if(fileList != null && fileList.size() > 0){
						data.setFileList(fileList);
					}
				}
				
				responseBody.setEntity(data);
				responseBody.setResult(new Result(Status.OK, ""));
			}
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "查询失败"));
			logger.error("delDelivery 查询失败",e);
		}
		return responseBody;
	}
	
	/**
	 *查询 事项列表
	@ResponseBody
	@RequestMapping(value = "/queryprodeliverypage", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<DeliveryBo> queryProDeliveryPage(HttpServletRequest request, @RequestBody DeliveryBo query) {
		
		ResponseData<DeliveryBo> responseBody = new ResponseData<DeliveryBo>();
		
		try {
			PageRequest pageRequest = new PageRequest();
			Integer pageNum = query.getPageNum() != null ? query.getPageNum() : 0;
			Integer pageSize = query.getPageSize() != null ? query.getPageSize() : 10;
			Direction direction = Direction.DESC;
			String property = "created_time"; //updated_time
			if(StringUtils.isNotEmpty(query.getProperty())){
				if(StringUtils.isNotEmpty(query.getDirection())){
					try {
						direction = Direction.fromString(query.getDirection());
					} catch (Exception e) {
						direction = Direction.ASC;
					}
				}
				property = query.getProperty();
				pageRequest = new PageRequest(pageNum,pageSize, direction,property);
			}else {
				pageRequest = new PageRequest(pageNum,pageSize,direction,new String[]{"CASE  WHEN IFNULL(updated_time,'')='' THEN created_time ELSE updated_time END ","created_time"});
			}
			
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
	 */
	/**
	 * 实际注资记录列表查询
	 */
	@ResponseBody
	@RequestMapping(value = "/queryprodeliverypage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<InformationListdata> queryprodeliverypage(@RequestBody InformationListdata delivery,
			HttpServletRequest request) {
		ResponseData<InformationListdata> responseBody = new ResponseData<InformationListdata>();
		if(delivery.getTitleId()== null || delivery.getProjectId() == null){
			responseBody.setResult(new Result(Status.ERROR,"error" , "必要的参数丢失!"));
			return responseBody;
		}
		try {
			delivery.setProperty(null);
			delivery.setDirection(null);
			Page<InformationListdata> actualPage = informationListdataService.queryPageList(delivery,
					new PageRequest(delivery.getPageNum(), 
							delivery.getPageSize(), 
							Direction.fromString("desc"), 
							"created_time"));
			List<InformationListdata> content = actualPage.getContent();
			if(content != null && content.size() > 0){
				for(InformationListdata c : content){
					c.setUpdateUserName((String)cache.hget(PlatformConst.CACHE_PREFIX_USER+c.getUpdateId(), "realName"));
					c.setUpdateTimeStr(c.getUpdatedTime() == null ? DateUtil.longToString(c.getCreatedTime()) : DateUtil.longToString(c.getUpdatedTime()));
				}
				actualPage.setContent(content);
			}
			responseBody.setPageList(actualPage);
		} catch (Exception e) {
			logger.error("查询交割前事项列表失败！查询条件：" + delivery, e);
		}
		return responseBody;
	}
	

	/**
	 * 文件下载
	 */
	@RequestMapping("/downloadBatchFile/{id}")
	public void downloadBatchFile(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response)
	{
		
		if(id != null){
			try {
				InformationListdata data = informationListdataService.queryById(id);
				if(!StringUtils.isEmpty(data.getRelateFileId())){
					InformationFile file = new InformationFile();
					file.setTitleId(data.getTitleId());
					file.setProjectId(data.getProjectId());
					file.setFileIds(Arrays.asList(data.getRelateFileId().split(",")));
					List<InformationFile> fileList = informationFileService.queryList(file);
					List<SopDownLoad> sopDownLoadList = new ArrayList<SopDownLoad>();
					if(fileList != null && fileList.size() > 0){
						for(InformationFile f:fileList){
							SopDownLoad downloadEntity = new SopDownLoad();
							downloadEntity.setFileName(f.getFileName());
							downloadEntity.setFileSuffix("." + f.getFileSuffix());
							downloadEntity.setFileSize(Long.valueOf(f.getFileLength()));
							downloadEntity.setFileKey(f.getFileKey());
							sopDownLoadList.add(downloadEntity);
						}
					}
					sopFileService.downloadBatch(request, response, tempfilePath,data.getField1(),sopDownLoadList);
				}
			} catch (Exception e) {
				logger.error("下载失败.",e);
			}
		}
	}
	
	
}
