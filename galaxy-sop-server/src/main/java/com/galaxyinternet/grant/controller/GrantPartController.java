package com.galaxyinternet.grant.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.galaxyinternet.bo.GrantPartBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.GrantPart;
import com.galaxyinternet.model.GrantTotal;
import com.galaxyinternet.model.sopfile.SopDownLoad;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.touhou.Delivery;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.GrantPartService;
import com.galaxyinternet.service.GrantTotalService;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.utils.BatchUploadFile;



@Controller
@RequestMapping("/galaxy/grant/part")
public class GrantPartController extends BaseControllerImpl<GrantPart, GrantPartBo> {
	
	private static final Logger _common_logger_ = LoggerFactory.getLogger(GrantPartController.class);
	
	@Autowired
	private GrantPartService grantPartService;
	@Override
	protected BaseService<GrantPart> getBaseService() {
		return this.grantPartService;
	}
	@Autowired
	private SopFileService sopFileService;
	@Autowired
	private GrantTotalService grantTotalService;
	@Autowired
	BatchUploadFile batchUpload;
	
	private String tempfilePath;

	public String getTempfilePath() {
		return tempfilePath;
	}

	@Value("${sop.oss.tempfile.path}")
	public void setTempfilePath(String tempfilePath) {
		this.tempfilePath = tempfilePath;
	}
	
	/**
	 * sop tab页面  日志 详情    /galaxy/project/proview/
	 */
	@RequestMapping(value = "/toApprPartAging/{tid}", method = RequestMethod.GET)
	public String toApprActualAging(@PathVariable("tid") Long tid, HttpServletRequest request) {
		GrantTotal total = grantTotalService.queryById(tid);
		double useMoney = grantPartService.calculateBelongToPartMoney(tid);
		request.setAttribute("totalGrantId", total.getId());
		request.setAttribute("totalMoney", total.getGrantMoney());
		request.setAttribute("remainMoney", total.getGrantMoney() - useMoney);
		return "project/tanchuan/appr_part_aging";
	}
	/**
	 *查询 事项
	 */
	@ResponseBody
	@RequestMapping(value = "/selectGrantPart/{partid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<GrantPart> selectDelivery(@PathVariable("partid") Long partid,HttpServletRequest request,HttpServletResponse response ) {
		ResponseData<GrantPart> responseBody = new ResponseData<GrantPart>();
		try {
			GrantPart grantPart = grantPartService.selectGrantPart(partid);
			responseBody.setEntity(grantPart);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "查询失败"));
			_common_logger_.error("grantPart 查询失败",e);
		}
		return responseBody;
	}
	
	/**
	 * 新建分期拨款计划
	 */
	@ResponseBody
	@RequestMapping(value = "/addGrantPart", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<GrantPart> addGrantPart(@RequestBody GrantPart grantPart,
			HttpServletRequest request) {
		ResponseData<GrantPart> responseBody = new ResponseData<GrantPart>();
		if(grantPart.getGrantDetail() == null || "".equals(grantPart.getGrantDetail().trim())
				|| grantPart.getGrantMoney() == null || grantPart.getTotalGrantId() == null){
			responseBody.setResult(new Result(Status.ERROR,"csds" , "必要的参数丢失!"));
			return responseBody;
		}
		
		try {
			GrantTotal total = grantTotalService.queryById(grantPart.getTotalGrantId());
			if(total == null){
				responseBody.setResult(new Result(Status.ERROR,"csds" , "未找到总拨款计划!"));
				return responseBody;
			}
			GrantPart part = new GrantPart();
			List<GrantPart> partList = grantPartService.queryList(part);
			if(partList != null && partList.size() > 0){
				GrantPart p = partList.get(partList.size()-1);
				String grantName = p.getGrantName().trim();
				int currentNum = Integer.parseInt(grantName.substring(grantName.length() - 1));
				grantPart.setGrantName("分拨" + (currentNum + 1));
			}else{
				grantPart.setGrantName("分拨1");
			}
			User user = (User) getUserFromSession(request);
			grantPart.setCreateUid(user.getId());
			grantPart.setCreateUname(user.getRealName());
			grantPart.setGrantTotal(total);
			if(grantPart.getFileReidsKey() != null){
				ResponseData<SopFile> result = batchUpload.batchUpload(user.getId()+grantPart.getFileReidsKey());
				if(Status.OK.equals(result.getResult().getStatus())){
					List<SopFile> fileList = result.getEntityList();
					grantPart.setFiles(fileList);
				}
			}
			if(grantPart.getId() == null){
				grantPartService.insertGrantPart(grantPart);
			}else{
				grantPartService.upateGrantPart(grantPart);
			}
			responseBody.setResult(new Result(Status.OK, "success", "操作分期拨款计划成功!"));
			_common_logger_.info("操作总拨款计划成功"+grantPart.getGrantName());
		} catch (Exception e) {
			e.printStackTrace();
			responseBody.setResult(new Result(Status.ERROR, "error", "操作总拨款计划失败!"));
			_common_logger_.error("操作总拨款计划失败！", e);
		}
		return responseBody;
	}
	
	/**
	 *删除
	 */
	@ResponseBody
	@RequestMapping(value = "/delGrantPart/{grantPartid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseData<Delivery> delGrantPart(@PathVariable("grantPartid") Long grantPartid,HttpServletRequest request,HttpServletResponse response ) {
		ResponseData<Delivery> responseBody = new ResponseData<Delivery>();
		try {
			grantPartService.deleteGrantPart(grantPartid);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (Exception e) {
			responseBody.setResult(new Result(Status.ERROR,null, "删除失败"));
			_common_logger_.error("delGrantPart 删除失败",e);
		}
		return responseBody;
	}
	
	/**
	 * 批量下载
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/downloadBatchFile/{id}")
	public void downloadBatchFile(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response)
	{
		if(id != null){
			
			GrantPart grantPart = grantPartService.queryById(id);
			
			List<Long> fileIdList = grantPartService.grantPartFileList(id);
			SopFile sopfile = new SopFile();
			if(fileIdList != null && fileIdList.size() > 0){
				sopfile.setIds(fileIdList);
			}
			List<SopFile> sopFileList = sopFileService.queryList(sopfile);
			List<SopDownLoad> sopDownLoadList = new ArrayList<SopDownLoad>();
			try {
				if(sopFileList != null && sopFileList.size() > 0){
					for(SopFile file:sopFileList){
						SopDownLoad downloadEntity = new SopDownLoad();
						downloadEntity.setFileName(file.getFileName());
						downloadEntity.setFileSuffix("." + file.getFileSuffix());
						downloadEntity.setFileSize(file.getFileLength());
						downloadEntity.setFileKey(file.getFileKey());
						sopDownLoadList.add(downloadEntity);
					}
			
				}
				sopFileService.downloadBatch(request, response, tempfilePath,grantPart.getGrantName(),sopDownLoadList);
			} catch (Exception e) {
				_common_logger_.error("下载失败.",e);
			}
		}
	}
	
}
