package com.galaxyinternet.project_process.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.common.annotation.LogType;
import com.galaxyinternet.common.controller.BaseControllerImpl;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.utils.ControllerUtils;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.oss.OSSFactory;
import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.dict.Dict;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.project_process.service.ProFlowAboutFileService;
import com.galaxyinternet.project_process.util.ProFlowUtilImpl;
import com.galaxyinternet.project_process.util.ProUtil;
import com.galaxyinternet.service.DictService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.UserRoleService;


/**
 * 访谈、
 * 内部评审、 
 * CEO评审 、
 * 立项会、
 * 商务谈判、
 * 投资意向书、
 * 尽职调查、
 * 投决会 、
 * 投资协议 、
 * 股权交割、
 * 投后运营
 */
@Controller
@RequestMapping("/galaxy/progressT")
public class ProjectProController extends BaseControllerImpl<Project, ProjectBo> {
	
	final Logger logger = LoggerFactory.getLogger(ProjectProController.class);
	
	private String tempfilePath;
	public String getTempfilePath() { return tempfilePath; }
	@Value("${sop.oss.tempfile.path}")
	public void setTempfilePath(String tempfilePath) { this.tempfilePath = tempfilePath; }
	
	@Autowired
	private SopFileService sopFileService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private DictService dictService;
	
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	
	@Override
	protected BaseService<Project> getBaseService() {
		return this.projectService;
	}
	
	@Autowired
	private  ProFlowAboutFileService proFlowAboutFileService;

	
	/**
	 * 根据  项目id、项目 progress ，封装当前文件的信息;  
	 * @param   pro.getId()  pro.getProjectProgress()
	 * @return
	 */
	@RequestMapping(value = "/showProFlowFiles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseData<SopFile> showProFlowFiles(HttpServletRequest request,@RequestBody Project query ) {
		ResponseData<SopFile> responseBody = new ResponseData<SopFile>();
		
		if(!ProFlowUtilImpl.file_about.containsKey(query.getProjectProgress())){
			return responseBody;
		}
		
		Map<String,Object> resultMap = new LinkedHashMap<>();
		try {
			User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			//Long uDept = user.getDepartmentId();
			
			Project pro = projectService.queryById(query.getId());
			//Long proDept = pro.getProjectDepartid();
			
			//登录人的角色
			List<Long> roleIdList = userRoleService.selectRoleIdByUserId(user.getId());
			
			//封装结果
			List<SopFile> files =  proFlowAboutFileService.getFileListForProFlow(query);
			
			if(!files.isEmpty()){
				
				//处理垃圾数据
				for(SopFile temp : files){
					if(temp.getFileKey() != null && temp.getFilUri() == null){
						String url = OSSHelper.getUrl(OSSFactory.getDefaultBucketName(),temp.getFileKey());
						temp.setFilUri(url);
						//temp.setBucketName(OSSFactory.getDefaultBucketName());
						sopFileService.updateById(temp);
					}
				}
				
				
				Map<String,String> filetype_name = new HashMap<>();
				List<Dict> dictList = dictService.selectByParentCode(ProFlowUtilImpl.fileTypePareatCode);
				for(Dict tem : dictList){
					filetype_name.put(tem.getCode(), tem.getName());
				}
				
				//封装权限
				Boolean checkOpt = false;
				Boolean optAuth = false;
				Map<String,Boolean> fwType = null;   // projectProgress : <fileWorktype : canOpt(true\false) >
				
				if(DictEnum.projectStatus.YFJ.getCode().equals(pro.getProjectStatus()) 
						|| DictEnum.projectStatus.YTC.getCode().equals(pro.getProjectStatus())){ //以否决的项目
					checkOpt = true;
					optAuth = false;
				}else if(!pro.getProjectProgress().equals(query.getProjectProgress())){ // 不在项目当前阶段
					checkOpt = true;
					optAuth = false;
				}else if(roleIdList.contains(UserConstant.TZJL)){   //投资经理
					fwType = ProFlowUtilImpl.role_file_opt_about.get(UserConstant.TZJL).get(query.getProjectProgress());
					if(user.getId().longValue() != pro.getCreateUid().longValue() || fwType == null){
						checkOpt = true;
						optAuth = false;
					}else{
						checkOpt = false;
						optAuth = false;
					}
				}else{   //其他角色
					checkOpt = true;
					optAuth = false;
				}
		
				if(checkOpt){
					for(SopFile temp : files){
						temp.setCanOpt(optAuth);
						temp.setCanDown(true);
						temp.setfWorktype(filetype_name.get(temp.getFileWorktype()));
						resultMap.put(temp.getFileWorktype(), temp);
					}
				}else{
					for(SopFile temp : files){
						temp.setCanOpt(fwType.get(temp.getFileWorktype()));
						temp.setCanDown(true);
						temp.setfWorktype(filetype_name.get(temp.getFileWorktype()));
						resultMap.put(temp.getFileWorktype(), temp);
					}
				}
				
			}
			
			//responseBody.setEntityList(files);
			responseBody.setUserData(resultMap);
			responseBody.setResult(new Result(Status.OK, ""));
		} catch (Exception e) {
			logger.error("showFilesProFlow 异常",e);
			responseBody.setResult(new Result(Status.ERROR, null,"系统异常"));
		}
		
		return responseBody;
	}
	
	
	
	
	
	
	
	
	/**
	 * 用户上传文档
	 * 根据  文档id、fileKey ，判断 insert update 
	 * @param   
	 * 	file.getId() 、 file.FileWorktype 、 file.getFileKey()
	 * 	file.projectId、file.projectProgress
	 * 
	 * 	file.FileType 、 file.FileSource
	 * 
	 * 	file.fileUid   file.CareerLine  
	 * @return 
	 */
	@com.galaxyinternet.common.annotation.Logger(operationScope = { LogType.LOG })
	@RequestMapping(value = "/optProFlowFiles", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseData<SopFile> showFilesProFlow(HttpServletRequest request, SopFile fileTemp ) {
		ResponseData<SopFile> responseBody = new ResponseData<SopFile>();
		
		if(fileTemp.getProjectId() == null){
			responseBody.setResult(new Result(Status.ERROR, null,"参数缺失"));
			return responseBody;
		}
		try {
			//String json = JSONUtils.getBodyString(request);
			//fileTemp = GSONUtil.fromJson(json, SopFile.class);
			
			User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
			
			Project project = projectService.queryById(fileTemp.getProjectId());
			//判断项目状态
			String err = ProUtil.errMessage(project,fileTemp.getProjectProgress()); //字典  项目进度  接触访谈 
			if(err!=null && err.length()>0){
				responseBody.setResult(new Result(Status.ERROR,"REFRESH", err));
				return responseBody;
			}
			//封装结果
			fileTemp.setFileUid(user.getId());
			fileTemp.setCareerLine(user.getDepartmentId());
			fileTemp.setProjectProgress(project.getProjectProgress());
			SopFile result = proFlowAboutFileService.optFileAboutProgress(request,this, fileTemp, tempfilePath);
			
			if(result == null){
				logger.error("showFilesProFlow 异常");
				responseBody.setResult(new Result(Status.ERROR, null,"系统异常"));
			}else{
				Dict dict = dictService.selectByCode(result.getFileWorktype());
				result.setfWorktype(dict.getName());
				
				result.setCanOpt(true);
				result.setCanDown(true);
				
				responseBody.setEntity(result);
				responseBody.setResult(new Result(Status.OK, ""));
				
				// 记录操作日志
				if(result.getNumber() != null){
					ControllerUtils.setRequestParamsForMessageTip(request, project.getProjectName(), project.getId(), null, result.getNumber());
				}
			}
		} catch (Exception e) {
			logger.error("showFilesProFlow 异常",e);
			responseBody.setResult(new Result(Status.ERROR, null,"系统异常"));
		}
		
		return responseBody;
	}
	
	
	
	
	
	
	

	@RequestMapping("showFile/{id}")
	public void showFile(HttpServletRequest request,HttpServletResponse response, @PathVariable("id") Long id ){
		SopFile file = sopFileService.queryById(id);
		if(file != null)
		{
			String fileMimeType = request.getSession().getServletContext().getMimeType(file.getFileName() + "." + file.getFileSuffix());
			response.setContentType(fileMimeType);
			response.setHeader("If-Modified-Since", "0");
			response.setHeader("Cache-Control", "no-cache");
			
			OSSObject ossobjcet = OSSFactory.getClientInstance().getObject(new GetObjectRequest(OSSFactory.getDefaultBucketName(), file.getFileKey()));
			InputStream fis = null;
			OutputStream os = null;
			try{
				fis = ossobjcet.getObjectContent();
				os = response.getOutputStream();
				int count = 0;
				byte[] buffer = new byte[1024 * 1024];
				while ((count = fis.read(buffer)) != -1){
					os.write(buffer, 0, count);
				}
				os.flush();
			}
			catch (Exception e){
				logger.error("显示文件失败",e);
			}
			finally{
				try{
					if (os != null) os.close();
					if (fis != null) fis.close();
				}
				catch (IOException e){
					logger.error("显示文件失败",e);
				}
			}
		}
	}
	
	
	
	
	
	
}


