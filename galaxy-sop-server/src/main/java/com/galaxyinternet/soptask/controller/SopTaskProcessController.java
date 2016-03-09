package com.galaxyinternet.soptask.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.galaxyinternet.bo.SopTaskBo;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.dictEnum.DictEnum;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.SopTaskService;


@Controller
@RequestMapping("/galaxy/taskprocess")
public class SopTaskProcessController extends BaseControllerImpl<SopTask, SopTaskBo> 
{
	private final Logger logger = LoggerFactory.getLogger(SopTaskProcessController.class);
	@Autowired
	private SopTaskService sopTaskService;
	@Autowired
	private SopFileService sopFileService;

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
	
	@ResponseBody
	@RequestMapping("/uploadFile")
	public Result uploadFile(SopFile bo, HttpServletRequest request)
	{
		Result result = new Result();
		try {
			MultipartFile file = null;
			if(request instanceof MultipartHttpServletRequest)
			{
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				file = multipartRequest.getFile("file");
			}
			if(file != null)
			{
				String fileName = file.getOriginalFilename();
				int dotPos = fileName.lastIndexOf(".");
				String key = String.valueOf(IdGenerator.generateId(OSSHelper.class));
				String ext = fileName.substring(dotPos);
				File temp = File.createTempFile(key, ext);
				Long length = temp.length();
				file.transferTo(temp);
				OSSHelper.simpleUploadByOSS(temp,key);
				
				bo.setFileKey(key);
				bo.setFileLength(length);
				bo.setFileName(fileName);
				bo.setUpdatedTime(System.currentTimeMillis());
				bo.setFileStatus(DictEnum.fileStatus.已上传.getCode());
			}
			sopFileService.updateById(bo);
			result.setStatus(Status.OK);
			
		} catch (Exception e) {
			Object msg = "上传失败";
			result.addError(msg);
			logger.error(msg.toString(),e);
		}
		
		return result;
	}

}
