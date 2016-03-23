package com.galaxyinternet.sopfile.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.galaxyinternet.bo.SopTaskBo;
import com.galaxyinternet.bo.sopfile.SopFileBo;
import com.galaxyinternet.bo.sopfile.SopVoucherFileBo;
import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.dictEnum.DictEnum;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.dao.sopfile.SopVoucherFileDao;
import com.galaxyinternet.dao.soptask.SopTaskDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.file.BucketName;
import com.galaxyinternet.framework.core.file.FileResult;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.file.UploadFileResult;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.model.Result.Status;
import com.galaxyinternet.framework.core.oss.GlobalCode;
import com.galaxyinternet.framework.core.oss.OSSConstant;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.sopfile.SopVoucherFile;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.UserService;
import com.galaxyinternet.sopfile.controller.SopFileController;

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
		// TODO Auto-generated method stub
		return this.sopFileDao;
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


	/**
	 * 分页查询获取project名称
	 */
	public Page<SopFile> queryPageList(SopFile query, Pageable pageable) {
		// TODO Auto-generated method stub
		Page<SopFile> pageEntity = super.queryPageList(query, pageable);
		//获取Project名称
		for(SopFile sopFile : pageEntity.getContent()){
			
			if(sopFile.getProjectId()!=null){
				Project project = projectDao.selectById(sopFile.getProjectId());
				//当为空时证明关联数据已被删除
				if(project!=null){
					sopFile.setProjectName(project.getProjectName());
				}
			}
			
			if(sopFile.getCareerLine()!=null){
				Department department = departmentService.queryById(sopFile.getCareerLine());
				if(department!=null){
					sopFile.setCareerLineName(department.getName());
				}
				
			}
			if(sopFile.getFileUid()!=null){
				User user = userService.queryById(sopFile.getFileUid());
				if(user!=null){
					sopFile.setFileUName(user.getRealName());
				}
			}
			
			logger.error("!!!!!sopFileId is" + sopFile.getId() );
			logger.error("!!!!!sopFileUid is" + sopFile.getFileUid());
			logger.error("!!!!!sopFileUName is" + sopFile.getFileUName());
			logger.error("!!!!!projectId is" + sopFile.getProjectId() );
			logger.error("!!!!!projectName is" + sopFile.getProjectName() );
			
//			
		}
		return pageEntity;
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
	public SopVoucherFile updateProve(SopVoucherFile sopVoucherFile,SopTask sopTask,Project project,Long userId,Long departmentId){
		//回填签署凭证文件表
		voucherFileDao.updateById(sopVoucherFile);
		//修改任务状态完成
		sopTask.setTaskStatus(DictEnum.taskStatus.已完成.getCode());
		sopTaskDao.updateById(sopTask);
		SopTaskBo sopTaskBo = new SopTaskBo();
		sopTaskBo.setProjectId(sopTask.getProjectId());
		List<String> taskFlagList = new ArrayList<String>();
		if(project.getProgress().equals(DictEnum.projectProgress.投资意向书.getCode())){
			//投资意向书
			taskFlagList.add("1");
		}else if(project.getProgress().equals(DictEnum.projectProgress.投资协议.getCode())){
			//投资协议
			taskFlagList.add("6");
			//股权转让
			taskFlagList.add("7");
		}else if(project.getProgress().equals(DictEnum.projectProgress.股权交割.getCode())){
			//工商签署
			taskFlagList.add("9");
		}
		sopTaskBo.setTaskFlagList(taskFlagList);
		//判断所有任务状态均为完成
		if(searchIsAllHasCompleted(sopTaskBo)){
			//更新当前项目阶段并插入任务
			if(project.getProgress().equals(DictEnum.projectProgress.投资意向书.getCode())){
				upTermSheetSign(project, userId, departmentId);
			}else if(project.getProgress().equals(DictEnum.projectProgress.投资协议.getCode())){
				upInvestmentSign(project);
			}else if(project.getProgress().equals(DictEnum.projectProgress.股权交割.getCode())){

			}	
		}

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
		project.setProjectStatus(DictEnum.meetingResult.待定.getCode());
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
		project.setProjectStatus(DictEnum.meetingResult.待定.getCode());
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
		
		
		if(project.getProjectType()!=null && project.getProjectType().equals(DictEnum.projectType.外部项目.getCode())){
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
			if(sopTask.getTaskStatus().equals(DictEnum.taskStatus.已完成)){
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
		Map<String,Object> map = aLiColoudUpload(request, key, null);
		if(map==null){
			return new Result(Status.ERROR,null,"aliyun add file failed");
		}
		Map<String,String> nameMap = (Map<String, String>) map.get("nameMap");
		File file = (File) map.get("file");		
//		if(fileNameStr.length == 2){
//			queryfile.setFileName(fileNameStr[0]);  //文件名称 temp.getName()  upload4196736950003923576secondarytile.png
//			queryfile.setFileSuffix(fileNameStr[1]);
//		}else if(fileNameStr.length == 1){
//			queryfile.setFileName(fileNameStr[0]);
//		}
		
		queryfile.setFileName(nameMap.get("fileName"));
		queryfile.setFileSuffix(nameMap.get("fileSuffix"));				
		queryfile.setFileLength(file.length());  //文件大小
		queryfile.setFileStatus(DictEnum.fileStatus.已上传.getCode());  //档案状态
		sopFileDao.updateById(queryfile);
		//update end
		
		return new Result(Status.OK,"");
	}
		

	private Map<String, String> transFileNames(String fileName) {
		Map<String, String> retMap = new HashMap<String, String>();
		int dotPos = fileName.lastIndexOf(".");
		if(dotPos == -1){
			retMap.put("fileName", fileName);
			retMap.put("fileSuffix", "");
		}else{
			retMap.put("fileName", fileName.substring(0, dotPos));
			retMap.put("fileSuffix", fileName.substring(dotPos+1));
		}
		return retMap;
	}
	
	
	
	/**
	 * 文档上传
	 * @param request 转为 MultipartFile，获取key=file
	 * @param fileKey 调用OSSHelper生成的key
	 * @param bucketName  默认传入 BucketName.DEV.getName()
	 * @return MultipartFile null=上传失败
	 */	
	public Map<String,Object> aLiColoudUpload(HttpServletRequest request, String fileKey,String bucketName) throws Exception {
		Map<String,Object> retMap = new HashMap<String,Object>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; // 请求转换
		MultipartFile multipartFile = multipartRequest.getFile("file"); // 获取multipartFile文件
		
		String path = request.getSession().getServletContext().getRealPath("upload");// 获取临时存储路径
		String fileName = multipartFile.getOriginalFilename();// 获取文件名称
		
		Map<String,String> nameMap = transFileNames(fileName);
		File tempFile = new File(path, nameMap.get("fileName"));
		if (!tempFile.exists()) {
			tempFile.mkdirs();
		}
		multipartFile.transferTo(tempFile); // 存储临时文件
		
		//begin 上传到aliyun
		long asize = multipartFile.getSize(); 
//		long bsize = tempFile.length();
		if(bucketName == null){
			bucketName = BucketName.DEV.getName();
		}
		if(asize>OSSConstant.UPLOAD_PART_SIZE){//大文件线程池上传
			int result = OSSHelper.uploadSupportBreakpoint(tempFile, bucketName, fileKey); // 上传至阿里云
			if(result == GlobalCode.ERROR){
				return null;
			}
		}else{
			UploadFileResult upResult = OSSHelper.simpleUploadByOSS(tempFile, bucketName, fileKey);  //上传至阿里云
			
			//若文件上传成功
			if(upResult.getResult().getStatus()==null || upResult.getResult().getStatus().equals(Status.ERROR)){
				return null;
			}
		}
		retMap.put("nameMap", nameMap);
		retMap.put("file", tempFile);

		return retMap;
	}
	
	
	
	
	

}
