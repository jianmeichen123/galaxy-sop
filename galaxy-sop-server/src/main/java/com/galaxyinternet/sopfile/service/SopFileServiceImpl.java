package com.galaxyinternet.sopfile.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.galaxyinternet.bo.SopTaskBo;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.bo.sopfile.SopVoucherFileBo;
import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.utils.StrUtils;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.dao.sopfile.SopVoucherFileDao;
import com.galaxyinternet.dao.soptask.SopTaskDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.file.DownloadFileResult;
import com.galaxyinternet.framework.core.file.FileResult;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.file.UploadFileResult;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.oss.GlobalCode;
import com.galaxyinternet.framework.core.oss.OSSConstant;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopDownLoad;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.sopfile.SopVoucherFile;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.UserService;
import com.galaxyinternet.sopfile.controller.SopFileController;
import com.galaxyinternet.utils.FileUtils;




@Service("com.galaxyinternet.service.SopFileService")
public class SopFileServiceImpl extends BaseServiceImpl<SopFile> implements
		SopFileService {
	
	@Autowired
	private SopFileDao sopFileDao;
	@Autowired
	private SopVoucherFileDao voucherFileDao;
	@Autowired
	private SopTaskDao sopTaskDao;
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private UserService userService;
	
	final Logger logger = LoggerFactory.getLogger(SopFileController.class);
	
	@Override
	protected BaseDao<SopFile, Long> getBaseDao() {
		return this.sopFileDao;
	}
	
	private String tempfilePath = "/data/apps/osstemp/";
	
	public String getTempfilePath() {
		return tempfilePath;
	}

	//@Value("${sop.oss.tempfile.path}")
	public void setTempfilePath(String tempfilePath) {
		//System.err.println("tempFilePath==>>" + this.tempfilePath);
		this.tempfilePath = tempfilePath;
	}
	
	
	@Override
	public List<SopFile> selectByFileTypeList(SopFileBo sbo) {
		return sopFileDao.queryByFileTypeList(sbo);
	}
	
	/**
	 * 通过项目ID及业务分类获取唯一档案
	 * @param sf
	 * @return
	 */
	public SopFile selectByProjectAndFileWorkType(SopFile sf){
		return sopFileDao.queryByProjectAndFileWorkType(sf);
	}

	
	@Override
	public Page<SopFile> queryPageList(SopFile query, Pageable pageable) {
		Page<SopFile> pageFile = sopFileDao.selectPageFile(query, pageable);
		List<Department> departmentList = getDepartMent();
		for(SopFile temp : pageFile.getContent()){
			if(query.getBelongUid()==null){
				if (DictEnum.fileStatus.已上传.getCode().equals(temp.getFileStatus())) {
					if (temp.getFileValid() != null && temp.getFileValid().intValue() == 0) {
						temp.setFileStatus(DictEnum.fileStatus.缺失.getCode());
					}
				}
			}else{
				temp.setFileValid(1);
			}
			for(Department department : departmentList){
				if(department.getId().equals(temp.getCareerLine())){
					temp.setCareerLineName(department.getName());
				}
			}
			if(temp.getVoucherId()!=null){
				if (temp.getVstatus().equals("fileStatus:1")) {
					temp.setVstatus("false");
				}
				if (temp.getVstatus().equals("fileStatus:3")) {
					temp.setVstatus("true");
				}
			}else{
				temp.setVstatus("no");
			}
		}
		return pageFile;
	}
	
	
	private List<Department> getDepartMent(){
		return departmentService.queryAll();
	}
	
	private List<User> getUser(List<SopFile> sopFileList){
		User user = new User();
		List<Long> ids = new ArrayList<Long>();	
		for(SopFile sopFile : sopFileList){
			if(sopFile.getFileUid()!=null && !ids.contains(sopFile.getFileUid())){
				ids.add(sopFile.getFileUid());
			}	
		}
		user.setIds(ids);
		if(ids.size() > 0){
			return userService.queryList(user);
		}
		return null;
	}
	
	private List<Project> getProject(List<SopFile> sopFileList){
		ProjectBo project = new ProjectBo();
		List<String> ids = new ArrayList<String>();
		for(SopFile sopFile : sopFileList){
			if(sopFile.getProjectId()!=null && !ids.contains(sopFile.getProjectId().toString())){
				ids.add(sopFile.getProjectId().toString());
			}	
		}
		project.setIds(ids);
		if(ids.size() > 0){
			return projectDao.selectList(project);
		}
		return null;
	}
	

	
	



	@Override
	@Transactional
	public List<SopFile> queryList(SopFile query) {
		List<SopFile> list = super.queryList(query);
		if(list != null && list.size()>0)
		{
			
			List<Long> vIds = new ArrayList<Long>();
			for(SopFile file : list)
			{
				if(file.getVoucherId() != null)
				{
					vIds.add(file.getVoucherId());
				}
				if(file.getFileUid()!=null){
					User user = userService.queryById(file.getFileUid());
					if(user!=null)
					{
						file.setFileUName(user.getRealName());
					}
				}	
			}
			if(vIds != null && vIds.size()>0)
			{
				SopVoucherFileBo bo = new SopVoucherFileBo();
				bo.setIds(vIds.toArray(new Long[vIds.size()]));
				List<SopVoucherFile> voucherList = voucherFileDao.selectList(bo);
				for(SopFile file : list)
				{
					if(file.getVoucherId() != null)
					{
						for(SopVoucherFile voucher : voucherList)
						{
							if(voucher.getId().equals(file.getVoucherId()))
							{
								file.setVoucherFileName(voucher.getFileName());
							}
						}
					}
				}
				
			}
		}
		return list;
	}
	
	/**
	 * 签署凭证上传时业务逻辑处理
	 * @param sopFile
	 * @param sopTask
	 * @param project
	 * @return
	 */
	@Transactional
	public SopVoucherFile updateProve(SopVoucherFile sopVoucherFile){
		//回填签署凭证文件表
		voucherFileDao.updateById(sopVoucherFile);
		return sopVoucherFile;
	}
	
	/**
	 * 投资协议阶段，    上传  投资协议-签署证明；
	 * 				更新项目阶段；
	 * 				生成任务;
	 * @param   project 
	 * @return
	 */
	private void upInvestmentSign(Project project){
		project.setProjectProgress(DictEnum.projectProgress.股权交割.getCode());
		project.setProjectStatus(DictEnum.projectStatus.GJZ.getCode());
		projectDao.updateById(project);
		
		//财务  任务生成
		SopTask task3 = new SopTask();
		task3.setProjectId(project.getId());         //项目id
		task3.setDepartmentId(SopConstant.DEPARTMENT_CW_ID);  		 //任务分派到: 投资经理
		task3.setTaskName("上传资金拨付凭证");        //任务名称：  上传资金拨付凭证
		//0 完善简历、
		//1 投资意向书、
		//2 人事尽职调查报告、
		//3 法务尽职调查报告、
		//4 财务尽调报告、
		//5 业务尽调报告、
		//6 投资协议、
		//7 股权转让协议、
		//8 资金拨付凭证、
		//9 工商变更登记凭证
		task3.setTaskFlag(8);
		task3.setTaskStatus(DictEnum.taskStatus.待认领.getCode());				 //任务状态: 2:待认领
		task3.setTaskType(DictEnum.taskType.协同办公.getCode());					 //任务类型    协同
		sopTaskDao.insert(task3);
		
		//法务  任务生成
		SopTask task4 = new SopTask();
		task4.setProjectId(project.getId());         //项目id
		task4.setDepartmentId(SopConstant.DEPARTMENT_FW_ID); 		 //任务分派到: 投资经理
		task4.setTaskName("上传工商变更登记凭证");        //任务名称：  上传工商变更登记凭证
		//0 完善简历、
		//1 投资意向书、
		//2 人事尽职调查报告、
		//3 法务尽职调查报告、
		//4 财务尽调报告、
		//5 业务尽调报告、
		//6 投资协议、
		//7 股权转让协议、
		//8 资金拨付凭证、
		//9 工商变更登记凭证
		task4.setTaskFlag(9);
		task4.setTaskStatus(DictEnum.taskStatus.待认领.getCode());				 //任务状态: 2:待认领
		task4.setTaskType(DictEnum.taskType.协同办公.getCode());					 //任务类型    协同
		sopTaskDao.insert(task4);
		
	}
	
	/**
	 * 投资意向书阶段，    上传  投资意向书-签署证明；
	 * 				更新项目阶段；  --》  尽职调查
	 * 				生成任务;
	 * @param   project 
	 * @return
	 */
	private void upTermSheetSign(Project project,Long userid,Long departid){
		project.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
		project.setProjectStatus(DictEnum.projectStatus.GJZ.getCode());
		projectDao.updateById(project);
		
		//业务dd  任务生成
		SopTask task1 = new SopTask();
		task1.setProjectId(project.getId());         //项目id
		task1.setDepartmentId(departid);  		 //任务分派到: 投资经理
		task1.setTaskName("上传业务尽职调查报告");    //任务名称：  上传股权转让协议
		//0 完善简历、
		//1 投资意向书、
		//2 人事尽职调查报告、
		//3 法务尽职调查报告、
		//4 财务尽调报告、
		//5 业务尽调报告、
		//6 投资协议、
		//7 股权转让协议、
		//8 资金拨付凭证、
		//9 工商变更登记凭证
		task1.setTaskFlag(5);
		task1.setAssignUid(userid);             //任务认领人id 
		task1.setTaskStatus(DictEnum.taskStatus.待完工.getCode());				 //任务状态: 2:待完工
		task1.setTaskType(DictEnum.taskType.协同办公.getCode());					 //任务类型    协同
		sopTaskDao.insert(task1);
		
		//人事dd  任务生成
		SopTask task2 = new SopTask();
		task2.setProjectId(project.getId());         //项目id
		task2.setDepartmentId(SopConstant.DEPARTMENT_RS_ID);  		 //任务分派到: 投资经理
		task2.setTaskName("上传人事尽职调查报告");        //任务名称：  上传股权转让协议
		//0 完善简历、
		//1 投资意向书、
		//2 人事尽职调查报告、
		//3 法务尽职调查报告、
		//4 财务尽调报告、
		//5 业务尽调报告、
		//6 投资协议、
		//7 股权转让协议、
		//8 资金拨付凭证、
		//9 工商变更登记凭证
		task2.setTaskFlag(2);
		task2.setTaskStatus(DictEnum.taskStatus.待认领.getCode());				 //任务状态: 2:待完工
		task2.setTaskType(DictEnum.taskType.协同办公.getCode());					 //任务类型    协同
		sopTaskDao.insert(task2);
		
		
		if(project.getProjectType()!=null && project.getProjectType().equals(DictEnum.projectType.外部投资.getCode())){
			//财务dd  任务生成
			SopTask task3 = new SopTask();
			task3.setProjectId(project.getId());         //项目id
			task3.setDepartmentId(SopConstant.DEPARTMENT_CW_ID);  		 //任务分派到: 投资经理
			task3.setTaskName("上传财务尽职调查报告");     //任务名称：  上传股权转让协议
			//0 完善简历、
			//1 投资意向书、
			//2 人事尽职调查报告、
			//3 法务尽职调查报告、
			//4 财务尽调报告、
			//5 业务尽调报告、
			//6 投资协议、
			//7 股权转让协议、
			//8 资金拨付凭证、
			//9 工商变更登记凭证
			task3.setTaskFlag(4);
			task3.setTaskStatus(DictEnum.taskStatus.待认领.getCode());				 //任务状态: 2:待完工
			task3.setTaskType(DictEnum.taskType.协同办公.getCode());					 //任务类型    协同
			sopTaskDao.insert(task3);
			
			//法务dd  任务生成
			SopTask task4 = new SopTask();
			task4.setProjectId(project.getId());         //项目id
			task4.setDepartmentId(SopConstant.DEPARTMENT_FW_ID);  		 //任务分派到: 投资经理
			task4.setTaskName("上传法务尽职调查报告");        //任务名称：  上传股权转让协议
			//0 完善简历、
			//1 投资意向书、
			//2 人事尽职调查报告、
			//3 法务尽职调查报告、
			//4 财务尽调报告、
			//5 业务尽调报告、
			//6 投资协议、
			//7 股权转让协议、
			//8 资金拨付凭证、
			//9 工商变更登记凭证
			task4.setTaskFlag(3);
			task4.setTaskStatus(DictEnum.taskStatus.待认领.getCode());				 //任务状态: 2:待完工
			task4.setTaskType(DictEnum.taskType.协同办公.getCode());				 //任务类型    协同
			sopTaskDao.insert(task4);
		}
	}
	
	/**
	 * 判断所有任务是否全部完成
	 * @param sopTaskBo
	 * @return
	 */
	private boolean searchIsAllHasCompleted(SopTaskBo sopTaskBo){
		List<SopTask> sopTaskList = sopTaskDao.selectForTaskByFlag(sopTaskBo);
		for(SopTask sopTask : sopTaskList){	
			if(sopTask.getTaskStatus().equals(DictEnum.taskStatus.已完成.getCode())){
				continue;
			}else{
				return false;
			}
		}
		return true;
	}
	
	
	
	
	
	
	/**
	 * 文档更新
	 * 删除aliyun原文件
	 * 上传新文件
	 * 更新sopfile表记录
	 */
	public Result updateFile(HttpServletRequest request, Long fid) throws Exception{
		
		SopFile queryfile = sopFileDao.selectById(fid);
		if(queryfile == null){
			return new Result(Status.ERROR,null);
		}
		
		String key = queryfile.getFileKey();
		String bucktname = queryfile.getBucketName();
		
		//del aliyun
		FileResult fileResult = OSSHelper.deleteFile(bucktname, key);
		if(fileResult.getResult().getStatus().equals(Status.ERROR)){
			return new Result(Status.ERROR,null,"aliyun del old file failed");
		}
		
		//调用 上传 接口
//		File file = aLiColoudUpload(request,key,null);
		Map<String,Object> map = aLiColoudUpload(request, key);
		if(map==null){
			return new Result(Status.ERROR,null,"aliyun add file failed");
		}
		Map<String,String> nameMap = (Map<String, String>) map.get("nameMap");
		MultipartFile file = (MultipartFile) map.get("file");		
//		if(fileNameStr.length == 2){
//			queryfile.setFileName(fileNameStr[0]);  //文件名称 temp.getName()  upload4196736950003923576secondarytile.png
//			queryfile.setFileSuffix(fileNameStr[1]);
//		}else if(fileNameStr.length == 1){
//			queryfile.setFileName(fileNameStr[0]);
//		}
		
		queryfile.setFileName(nameMap.get("fileName"));
		queryfile.setFileSuffix(nameMap.get("fileSuffix"));				
		queryfile.setFileLength(file.getSize());  //文件大小
		queryfile.setFileStatus(DictEnum.fileStatus.已上传.getCode());  //档案状态
		sopFileDao.updateById(queryfile);
		//update end
		
		return new Result(Status.OK,"");
	}	
	
	@Transactional
	public boolean updateFile(SopFile sopFile) throws Exception{
		//更新
		int ret = getBaseDao().updateByIdSelective(sopFile);
//		if(ret > 0){
//			SopTask sopTask = new SopTask();
//			sopTask.setProjectId(sopFile.getProjectId());
//			int pos = sopFile.getFileWorktype().lastIndexOf(":");
//			int worktype = Integer.parseInt(sopFile.getFileWorktype().substring(pos+1));
////			int taskFlag = FileUtils.getTaskByWorktype(worktype);
//			WorktypeTask worktypeTask = FileUtils.getWorktypeEntityByTask(worktype);
//			if(worktypeTask!=null){
//				sopTask.setTaskFlag(worktypeTask.getTaskFlag());
//				sopTask = sopTaskDao.selectOne(sopTask);
//				if(sopTask!=null){
//					if(!worktypeTask.isHasProve()){
//						if(sopTask.getTaskStatus().equals(DictEnum.taskStatus.待认领.getCode()) || 
//								sopTask.getTaskStatus().equals(DictEnum.taskStatus.待完工.getCode())){
//							sopTask.setTaskStatus(DictEnum.taskStatus.已完成.getCode());
//							ret = sopTaskDao.updateById(sopTask);
//						}
//					}		
//				}	
//			}	
//		}
		
		
		return ret > 0 ;
		
	}
	
	/**
	 * 文档上传
	 * @param request 转为 MultipartFile，获取key=file
	 * @param fileKey 调用OSSHelper生成的key
	 * @return MultipartFile null=上传失败
	 * @throws IOException 
	 */	
	public Map<String,Object> aLiColoudUpload(HttpServletRequest request, String fileKey) throws IOException{
		Map<String,Object> retMap = new HashMap<String,Object>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; // 请求转换
		MultipartFile multipartFile = multipartRequest.getFile("file"); // 获取multipartFile文件
		
//		String path = request.getSession().getServletContext().getRealPath("upload");// 获取临时存储路径
		String path = tempfilePath;
		String fileName = multipartFile.getOriginalFilename();// 获取文件名称
		
		Map<String,String> nameMap = FileUtils.transFileNames(fileName);
		File tempFile = new File(path, fileKey + "_" +nameMap.get("fileName"));
		UploadFileResult upResult = null;
		if (!tempFile.exists()) {
			tempFile.mkdirs();
		}
		try {
			multipartFile.transferTo(tempFile);
			//begin 上传到aliyun
			long asize = multipartFile.getSize(); 
			if(asize>OSSConstant.UPLOAD_PART_SIZE){//大文件线程池上传
//				int result = OSSHelper.uploadSupportBreakpoint(tempFile,fileKey); // 上传至阿里云
				int result = OSSHelper.uploadSupportBreakpoint(fileName, tempFile, fileKey);
				if(result == GlobalCode.ERROR){
					return null;
				}
			}else{
//				upResult = OSSHelper.simpleUploadByOSS(tempFile,fileKey);  //上传至阿里云
				upResult = OSSHelper.simpleUploadByOSS(tempFile, fileKey, OSSHelper.setRequestHeader(fileName, multipartFile.getSize())); //上传至阿里云
				//若文件上传成功
				if(upResult.getResult().getStatus()==null || upResult.getResult().getStatus().equals(Status.ERROR)){
					return null;
				}
			}
			retMap.put("nameMap", nameMap);
			retMap.put("file", multipartFile);
			retMap.put("upResult", upResult);
		}catch (IOException e) {
			throw new IOException(e);
		}finally{
//			tempFile.delete();
		}
		return retMap;
	}
	

	
	
	
	
	
	/**
	 * 文件下载接口
	 * 
	 * @param request
	 * @param response
	 * @param tempfilePath
	 *            临时存储路径
	 * @param downloadEntity
	 *            下载实体类
	 * @throws Exception
	 */
	public void download(HttpServletRequest request,
			HttpServletResponse response, String tempfilePath,
			SopDownLoad downloadEntity) throws Exception{

		InputStream fis = null;
		OutputStream out = null;
		
				
		File tempDir = new File(tempfilePath);
		File tempFile = new File(tempfilePath, downloadEntity.getFileKey() + "_" +downloadEntity.getFileName());
		
		if (!tempDir.exists()) {
			tempDir.mkdirs();
		}
		if(!tempFile.exists() || !tempFile.isFile()){
			tempFile.createNewFile();
			if (downloadEntity.getFileSize().longValue() > OSSConstant.DOWNLOAD_PART_SIZE) {
				OSSHelper.downloadSupportBreakpoint(tempFile.getAbsolutePath(),
						downloadEntity.getFileKey());
			} else {
				DownloadFileResult result = OSSHelper.simpleDownloadByOSS(tempFile,
						downloadEntity.getFileKey());
				System.err.println(GSONUtil.toJson(result));
			}	
		}
		try{	
//			boolean ie10 = request.getHeader(SopDownLoad.USER_AGENT).toUpperCase()
//					.indexOf("MSIE") > 0;
//			boolean ie11p = request.getHeader(SopDownLoad.USER_AGENT).toUpperCase()
//					.indexOf("RV:11") > 0
//					&& request.getHeader(SopDownLoad.USER_AGENT).toUpperCase()
//							.indexOf("LIKE GECKO") > 0;
//			boolean iedge = request.getHeader(SopDownLoad.USER_AGENT).toUpperCase()
//					.indexOf("EDGE") > 0;
//			boolean ie = ie10 || ie11p || iedge;
//			if (ie) {
//				try {
//					String filename = downloadEntity.getFileName()+downloadEntity.getFileSuffix();
//					filename = new String(StrUtils.encodString(filename).getBytes("UTF-8"), "ISO8859-1"); 
//					downloadEntity.setFileName(filename);
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
//			} else {
//				downloadEntity.setFileName(new String((downloadEntity
//						.getFileName() + downloadEntity.getFileSuffix())
//						.getBytes("UTF-8"), "ISO8859-1"));
//			}
			
			String fileName = getFileNameByBrowser(request, downloadEntity.getFileName()+downloadEntity.getFileSuffix());
			downloadEntity.setFileName(fileName);
			response.reset();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-download");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ downloadEntity.getFileName());
			response.setHeader("Content-Length", "" + tempFile.length());
			out = new BufferedOutputStream(response.getOutputStream());
			fis = new BufferedInputStream(new FileInputStream(tempFile.getPath()));
			byte[] buffer = new byte[1024 * 2];
			
			while (fis.read(buffer) != -1) {
				out.write(buffer);
			}
			response.flushBuffer();
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			try {
				if(fis != null)
				{
					fis.close();
				}
				if(out != null)
				{
					out.close();
				}
//				tempFile.delete();
			} catch (IOException e) {
				logger.error("下载失败.",e);
			}
		}	
	}
	
	private String getFileNameByBrowser(HttpServletRequest request,String fileName) throws UnsupportedEncodingException{
		boolean ie10 = request.getHeader(SopDownLoad.USER_AGENT).toUpperCase()
				.indexOf("MSIE") > 0;
		boolean ie11p = request.getHeader(SopDownLoad.USER_AGENT).toUpperCase()
				.indexOf("RV:11") > 0
				&& request.getHeader(SopDownLoad.USER_AGENT).toUpperCase()
						.indexOf("LIKE GECKO") > 0;
		boolean iedge = request.getHeader(SopDownLoad.USER_AGENT).toUpperCase()
				.indexOf("EDGE") > 0;
		boolean ie = ie10 || ie11p || iedge;
		if (ie) {
			fileName = new String(StrUtils.encodString(fileName).getBytes("UTF-8"), "ISO8859-1");

		} else {
			fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");

		}
		return fileName;
	}
	public Map<String,SopVoucherFile> getVoucherId(List<SopFile> sopFile){
		Map<String,SopVoucherFile> map=new HashMap<String,SopVoucherFile>();
		List<Long> ids=new ArrayList<Long>();
		for (SopFile sopfile:sopFile) {
				if(null!=sopfile.getVoucherId()){
				ids.add(sopfile.getVoucherId());
			}
		}
		if(!ids.isEmpty()){
		    List<SopVoucherFile> selectListById = voucherFileDao.selectListById(ids);
			if(null!=selectListById && selectListById.size() > 0){
				for(SopVoucherFile vfile:selectListById){
					map.put(vfile.getId().toString(), vfile);
				}
			}
		}
		return map;
		
	}
	
	
	

	/*@Autowired
	private DictService dictService;*/
	
	/**
	 * @param query   projectid : cyid,  RecordType:1   fileuid : 登陆人id
	 */
	@Override
	public Page<SopFile> queryFileList(SopFile query, Pageable pageable) {
		Page<SopFile> filePage = null;
		List<SopFile> fileList = null;
		Long total = null;
		Map<Long,String> uIdNameMap = new HashMap<Long,String>();
		Map<Long,String> departIdNameMap = new HashMap<Long,String>();
		
		//查询所有file记录
		fileList = sopFileDao.selectList(query, pageable);
		total = sopFileDao.selectCount(query);
		
		if(fileList!=null && fileList.size()>0){
			if(query.getFileUid()!=null){  //个人下的文档
				uIdNameMap.put(query.getFileUid(), query.getFileUName()); //uid-uname
				departIdNameMap.put(query.getCareerLine(), query.getCareerLineName()); //departid-departname
			}else{
				//uid-uname
				User user = new User();
				List<Long> uids = new ArrayList<Long>();	
				for(SopFile sopFile : fileList){
					if(sopFile.getFileUid()!=null && !uids.contains(sopFile.getFileUid())){
						uids.add(sopFile.getFileUid());
					}	
				}
				user.setIds(uids);
				List<User> userList = userService.queryList(user);
				if(userList!=null && !userList.isEmpty()){
					for(User au : userList){
						uIdNameMap.put(au.getId(), au.getRealName());
					}
				}
				
				//departid-departname
				List<Department> allDepartMent = departmentService.queryAll();
				if(allDepartMent!=null){
					for(Department ad : allDepartMent){
						departIdNameMap.put(ad.getId(), ad.getName());
					}
				}
			}
			for(SopFile afile:fileList){
				afile.setFileUName(uIdNameMap.get(afile.getFileUid()));
				afile.setCareerLineName(departIdNameMap.get(afile.getCareerLine()));
				
			}
			
			filePage = new Page<SopFile>(fileList, pageable, total);
		}else{
			filePage = new Page<SopFile>(new ArrayList<SopFile>() , pageable, 0l);
		}
		
		return filePage;
	}

	/**
	 * 批量下载
	 */
	@Override
	public void downloadBatch(HttpServletRequest request,
			HttpServletResponse response, String tempfilePath,String type,
			List<SopDownLoad> downloadEntityList) throws Exception {
		String strZipName = type+".zip";
		ZipOutputStream outzip = new ZipOutputStream(new FileOutputStream(tempfilePath+strZipName));
		byte[] buffer = new byte[1024 * 4];
		try{
			for(int i =0 ; i< downloadEntityList.size(); i++){
				SopDownLoad downloadEntity = downloadEntityList.get(i);
				File tempDir = new File(tempfilePath);
				File tempFile = new File(tempfilePath, downloadEntity.getFileKey() + "_" +downloadEntity.getFileName());
				
				if (!tempDir.exists()) {
					tempDir.mkdirs();
				}
				if(!tempFile.exists() || !tempFile.isFile()){
					tempFile.createNewFile();
					if (downloadEntity.getFileSize().longValue() > OSSConstant.DOWNLOAD_PART_SIZE) {
						OSSHelper.downloadSupportBreakpoint(tempFile.getAbsolutePath(),
								downloadEntity.getFileKey());
					} else {
						DownloadFileResult result = OSSHelper.simpleDownloadByOSS(tempFile,
								downloadEntity.getFileKey());
						System.err.println(GSONUtil.toJson(result));
					}
					
				}
			     FileInputStream fiso = new FileInputStream(tempFile); 
			     outzip.putNextEntry(new ZipEntry(downloadEntity.getFileName()+downloadEntity.getFileSuffix())); 
	             //设置压缩文件内的字符编码，不然会变成乱码    
			     //outzip.setEncoding("GBK");    
	             int len;    
	             // 读入需要下载的文件的内容，打包到zip文件    
	             while ((len = fiso.read(buffer)) > 0) {    
	            	 outzip.write(buffer, 0, len);    
	             }    
	             outzip.closeEntry();    
	             fiso.close();  
			}
			outzip.close();
			this.downFile(response, strZipName,tempfilePath);    
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			try {
				if(outzip != null)
				{
					outzip.close();
				}
				
			} catch (IOException e) {
				logger.error("下载失败.",e);
			}
		}	
		
	}
	
	
	/**   
     * 文件下载   
     * @param response   
     * @param str   
     */    
    private Result downFile(HttpServletResponse response, String str,String tempFilePath) {    
    	Result result = new Result();
    	result.setStatus(Status.OK);
    	BufferedInputStream bins = null;
    	BufferedOutputStream bouts = null;
    	try {    
            String path = tempFilePath + str;    
            File file = new File(path);    
            if (file.exists()) {    
                bins = new BufferedInputStream(new FileInputStream(path));// 放到缓冲流里面    
                bouts = new BufferedOutputStream(response.getOutputStream());    
                response.setContentType("application/x-download");// 设置response内容的类型    
                response.setHeader(    
                        "Content-disposition",    
                        "attachment;filename="    
                                + URLEncoder.encode(str, "UTF-8"));// 设置头部信息    
                int bytesRead = 0;    
                byte[] buffer = new byte[1024 * 2];    
                // 开始向网络传输文件流    
                while ((bytesRead = bins.read(buffer, 0, 1024 * 2)) != -1) {    
                    bouts.write(buffer, 0, bytesRead);    
                }    
            } else {    
            	result.setStatus(Status.ERROR);
            }    
        } catch (IOException e) {    
        	logger.error("文件下载出错", e);   
        	result.setStatus(Status.ERROR);
        }finally{
        	if(bouts != null){
        		try {
					bouts.flush();// 这里一定要调用flush()方法
					bouts.close();
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					if(bins != null){
						try {
							bins.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
        	}
        }
    	return result;
    }

	@Override
	public int updateDepartmentId(SopFile f) {
		return sopFileDao.updateDepartmentId(f);
	}    
    
}
