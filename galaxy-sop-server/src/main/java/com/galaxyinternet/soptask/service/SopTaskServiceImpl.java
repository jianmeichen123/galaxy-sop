package com.galaxyinternet.soptask.service;

import static com.galaxyinternet.utils.ExceptUtils.throwSopException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.bo.SopTaskBo;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.dictEnum.DictUtil;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.taglib.FXFunctionTags;
import com.galaxyinternet.dao.project.PersonPoolDao;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.dao.soptask.SopTaskDao;
import com.galaxyinternet.exception.PlatformException;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.framework.core.utils.ExceptionMessage;
import com.galaxyinternet.framework.core.utils.StringEx;
import com.galaxyinternet.model.project.PersonPool;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.SopFileService;
import com.galaxyinternet.service.SopTaskService;
import com.galaxyinternet.service.UserService;
import com.galaxyinternet.utils.SopConstatnts;

@Service("com.galaxyinternet.service.SopTaskService")
public class SopTaskServiceImpl extends BaseServiceImpl<SopTask> implements SopTaskService {
	// private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SopTaskDao sopTaskDao;

	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private SopFileDao sopFileDao;
	@Autowired
	private PersonPoolDao personPoolDao;
	@Autowired
	private SopFileService sopFileService;
	@Autowired
	private UserService userService;

	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected BaseDao<SopTask, Long> getBaseDao() {
		return this.sopTaskDao;
	}
	
	@Transactional
	@Override
	public void toSureMsgForPerson(Long pid, List<PersonPool> list)  throws Exception {
		SopTask task = new SopTask();
		task.setProjectId(pid);
		task.setTaskName(SopConstant.TASK_NAME_WSJL);
		task.setTaskType(DictEnum.taskType.协同办公.getCode());
		task.setTaskFlag(SopConstant.TASK_FLAG_WSJL);
		task.setTaskOrder(SopConstant.NORMAL_STATUS);
		task.setDepartmentId(SopConstant.DEPARTMENT_RS_ID);
		task.setTaskStatus(DictEnum.taskStatus.待认领.getCode());
		task.setCreatedTime(System.currentTimeMillis());
		Long tid = sopTaskDao.insert(task);
		for(PersonPool p : list){
			p.setTid(tid);
			personPoolDao.updateById(p);
		}
	}
	
	
	/**
	 * @author chenjianmei
	 * @category 不同角色的人根据不同的状态获取任列表（包含根据项目名称和投资经理查询任务，包含分页）
	 * @serialData 2016-02-26
	 */

	@Override
	public Page<SopTaskBo> tasklist(PageRequest pageable, SopTaskBo sopTaskBo,HttpServletRequest request) {
		ProjectBo projectBo =new ProjectBo();
		Page<SopTaskBo> formatData=new Page<>(null, pageable, null);
		List<Project> projectList = new ArrayList<Project>();
		Page<SopTask> selectListSopTask =new Page<>(null, pageable, null);
	    // 如果查询条件部位空的时候，现根据项目名称或者投资经理去查询该项目的任务列表
		if (sopTaskBo.getKeyword() != null && !"".equals(sopTaskBo.getKeyword())) {
			projectBo.setKeyword(sopTaskBo.getKeyword());
			projectBo.setFlagkeyword("concatcode");
			// 查询该项目投资经理或者项目名称查询相应的项目
			projectList = projectDao.selectProjectByMap(projectBo);
			if(!projectList.isEmpty()){
				List<String> pids = getProjectIds(projectList);
				if(pids != null && pids.size()>0)
				{
					sopTaskBo.setIds(pids);
				}
			    selectListSopTask = sopTaskDao.selectTaskInPids(sopTaskBo, pageable);
			    if (selectListSopTask == null) {
					throwPlatformException(ExceptionMessage.QUERY_LIST_FAIL);
					return null;
				}
			}
		    
		}else{
			
			selectListSopTask = sopTaskDao.selectTaskInPids(sopTaskBo, pageable);
			if(selectListSopTask!=null && !selectListSopTask.getContent().isEmpty()){		
				projectBo = setProjectIdsByTList(selectListSopTask.getContent());	
				projectList = projectDao.selectProjectByMap(projectBo);
				if (projectList == null) {
					throwPlatformException(ExceptionMessage.QUERY_LIST_FAIL);
					return null;
				}
				
			}	
		}
			// 组装数据
		if(selectListSopTask!=null && !projectList.isEmpty()){
		   formatData = formatData(selectListSopTask,projectList,request);
		}
		return formatData;

	}

	/**
	 * @author chenjianmei
	 * @category 定义异常处理方法
	 * @serialData 2016-02-26
	 * @param status
	 * @param args
	 */
	public void throwPlatformException(ExceptionMessage status, Object... args) {
		String message = null;
		if (args.length == 0) {
			message = status.getMessage();
		} else {
			message = String.format(status.getMessage(), args);
		}
		throw new PlatformException(status.getStatus(), message);
	}
	public List<String> getProjectIds(List<Project> projectList)
	{
		List<String> ids = null;
		if(projectList != null && projectList.size()>0)
		{
			ids = new ArrayList<String>();
			for(Project project : projectList)
			{
				ids.add(project.getId().toString());
			}
		}
		return ids;
	}
	/**
	 * @author chenjianmei
	 * @category 根据项目项目list拼接项目ids
	 * @param project
	 * @return
	 */
	public SopTaskBo setProjectIdsByPList(List<Project> projectList) {
		SopTaskBo sopTaskBo = new SopTaskBo();
		// 得到项目的id放到数组里面
		List<String> ids = new ArrayList<String>();
		if (!projectList.isEmpty()) {
			for (int i = 0; i < projectList.size(); i++) {
				Project pro = projectList.get(i);
				ids.add(pro.getId().toString());
			}
			sopTaskBo.setIds(ids);
		}
		return sopTaskBo;
	}
	/**
	 * @author chenjianmei
	 * @category 根据任务list拼接项目ids
	 * @param project
	 * @return
	 */
	public ProjectBo setProjectIdsByTList(List<SopTask> sopTaskList) {
		ProjectBo projectBo = new ProjectBo();
		// 得到项目的id放到数组里面
		List<String> ids = new ArrayList<String>();
		if (!sopTaskList.isEmpty()) {
			for (int i = 0; i < sopTaskList.size(); i++) {
				SopTask task =  sopTaskList.get(i);
				ids.add(task.getProjectId().toString());
			}
			projectBo.setIds(ids);
		}
		return projectBo;
	}

	/**
	 * @author chenjianmei
	 * @category组装数据
	 * @param sopTaskData
	 * @param projectList
	 * @return
	 */
	public Page<SopTaskBo> formatData(Page<SopTask> sopTaskData, List<Project> projectList,HttpServletRequest  request) {
		Page<SopTaskBo> sopTaskPage = new Page<>(null, null, null);
		List<SopTaskBo> SopTaskBoList = new ArrayList<SopTaskBo>();
		
		for (int i = 0; i < sopTaskData.getContent().size(); i++) {
			SopTask sopTasknew =  sopTaskData.getContent().get(i);
			SopTaskBo sopTaskBo=new SopTaskBo();
			for (Project project : projectList) {
				if (sopTasknew.getProjectId().equals(project.getId())) {
					sopTaskBo.setProjectName(project.getProjectName());
					sopTaskBo.setCreateUname(project.getCreateUname());
					break;
				}

			}
			sopTaskBo.setId(sopTasknew.getId());
			String longToString = DateUtil.longToString(sopTasknew.getCreatedTime());
			String diffHour="";
			diffHour=dateAdd(sopTasknew.getCreatedTime(),sopTasknew.getTaskDeadline());
			sopTaskBo.setHours(diffHour);
			sopTaskBo.setTaskDeadlineformat(longToString);//
			sopTaskBo.setTaskName(sopTasknew.getTaskName()==null?"":sopTasknew.getTaskName());
			sopTaskBo.setTaskType(sopTasknew.getTaskType()==null?"":DictUtil.getTypeName(sopTasknew.getTaskType()));
			sopTaskBo.setTaskOrder(sopTasknew.getTaskOrder()==null?0:sopTasknew.getTaskOrder());
			sopTaskBo.setOrderRemark(sopTasknew.getTaskOrder()==null?"":DictUtil.getTaskOrderName(sopTasknew.getTaskOrder()));
			sopTaskBo.setDepartmentId(sopTasknew.getDepartmentId());
			sopTaskBo.setTaskStatus(sopTasknew.getTaskStatus()==null?"":sopTasknew.getTaskStatus());
			String mark=ResourceMark(sopTasknew);
			sopTaskBo.setMark(mark);
			if(sopTasknew.getTaskStatus().equals("taskStatus:1")){
				StringBuffer caozuohtml=new StringBuffer();
				sopTaskBo.setCaozuo(DictUtil.getStatusName(sopTasknew.getTaskStatus()));
				sopTaskBo.setTaskStatus(DictUtil.getStatusName(sopTasknew.getTaskStatus()));	
//				sopTaskBo.setStatusFlag("1");/.append("/galaxy/soptask/goClaimtcPage?id="+sopTaskBo.getId())
				
				if(FXFunctionTags.isTransfering(sopTasknew.getProjectId()) && sopTasknew.getTaskFlag().intValue() != SopConstatnts.TaskCode._accept_project_flag_){
					caozuohtml.append("<a ").append(" >").append("认领").append("</a>");
				}else{
					caozuohtml.append("<a id='dai' href='javascript:void(0)' name='markResource' resource-mark='"+mark+"' class='blue' ")
					.append("     data-btn='claim'").append(" >").append("认领").append("<input type='hidden' id='taskid' ")
							.append(" value='"+sopTasknew.getId()+"'").append("/><input type='hidden' id='projectid' ").append(" value='"+sopTasknew.getProjectId()+"' />");
				}
				sopTaskBo.setCaozuohtml(caozuohtml.toString());
			}
			if(sopTasknew.getTaskStatus().equals("taskStatus:2")){
				StringBuffer caozuohtml=new StringBuffer();
				sopTaskBo.setCaozuo(DictUtil.getStatusName(sopTasknew.getTaskStatus()));
				sopTaskBo.setTaskStatus(DictUtil.getStatusName(sopTasknew.getTaskStatus()));
				sopTaskBo.setStatusFlag("2");
			//	String params = Constants.SESSOPM_SID_KEY + "=" + getSessionId(request) + "&" + Constants.REQUEST_URL_USER_ID_KEY + "=" + getUserId(request);
				if(FXFunctionTags.isTransfering(sopTasknew.getProjectId()) && sopTasknew.getTaskFlag().intValue() != SopConstatnts.TaskCode._accept_project_flag_){
					caozuohtml.append("<a ").append(" >").append("处理").append("</a>");
				}else{
					caozuohtml.append("<a href='javascript:void(0)' name='markResource' resource-mark='"+mark+"' class='blue' ").append(" id='doclaim' ").append(" >").append("处理")
					.append("<input type='hidden'").append(" value='"+sopTasknew.getId()+"'/>").append("</a>");
					
				}
				sopTaskBo.setCaozuohtml(caozuohtml.toString());
		
			}
			if(sopTasknew.getTaskStatus().equals("taskStatus:3")){
				StringBuffer caozuohtml=new StringBuffer();
				sopTaskBo.setCaozuo(DictUtil.getStatusName(sopTasknew.getTaskStatus()));
				sopTaskBo.setTaskStatus(DictUtil.getStatusName(sopTasknew.getTaskStatus()));
				sopTaskBo.setStatusFlag("3");
				caozuohtml.append("<a ").append(" >").append(DictUtil.getStatusName(sopTasknew.getTaskStatus())).append("</a>");
				sopTaskBo.setCaozuohtml(caozuohtml.toString());
			}
			sopTaskBo.setProjectId(sopTasknew.getProjectId());
			sopTaskBo.setRemark(sopTasknew.getRemark()==null?"":sopTasknew.getRemark());
			List<String> qlist = new ArrayList<String>();
			List<User> userList=new ArrayList<User>();
			if(null!=sopTasknew.getAssignUid()){
				qlist.add(sopTasknew.getAssignUid().toString());
				userList = userService.queryListById(qlist);
			}
			//===========================================
			
			if(userList!=null && userList.size()>0){			
				sopTaskBo.setAssignUidName(userList.get(0).getRealName());			
			}
			sopTaskBo.setAssignUid(sopTasknew.getAssignUid());
			SopTaskBoList.add(sopTaskBo);
			//===========================================
		}
		sopTaskPage.setContent(SopTaskBoList);
		sopTaskPage.setPageable(sopTaskData.getPageable());
		sopTaskPage.setTotal(sopTaskData.getTotal());
		if(sopTaskPage.getContent().isEmpty()){
			sopTaskPage.setTotal((long)0);
		}
		return sopTaskPage;
	}
	@Override
	public int updateById(SopTask entity) {
		if (!StringEx.isNullOrEmpty(entity.getTaskStatus()) && entity.getTaskStatus().equals("1")) {
			entity.setTaskStatus("2");
		}
		if (!StringEx.isNullOrEmpty(entity.getTaskStatus()) && entity.getTaskStatus().equals("2")) {
			entity.setTaskStatus("3");
		}
	//	String viewName = "";
	//	String btnTxt = "";
		String fileWorktype = "";
	    boolean flag=true;
		switch(entity.getTaskFlag())
		{
			case 0: //完善简历
				break;
			case 1 : //表示投资意向书
				fileWorktype = "fileWorktype:5";
				break;
			case 2 : //人事尽职调查报告
			//	btnTxt = "上传尽调报告";
				fileWorktype = "fileWorktype:2";
				break;
			case 3 : //法务尽职调查报告
				//btnTxt = "上传尽调报告";
				fileWorktype = "fileWorktype:3";
				break;
			case 4 : //财务尽调报告
			//	btnTxt = "上传尽调报告";
				fileWorktype = "fileWorktype:4";
				break;
			case 5 : //业务尽调报告	
				fileWorktype = "fileWorktype:1";
				break;
			case 6 : //投资协议
				fileWorktype = "fileWorktype:6";
				break;
			case 7 : //股权转让协议
				fileWorktype = "fileWorktype:7";
				break;
			case 8 : //资金拨付凭证
				//btnTxt = "上传资金拨付凭证";
				fileWorktype = "fileWorktype:9";
				break;
			case 9 : //工商变更登记凭证
			//	btnTxt = "上传工商变更登记凭证";
				fileWorktype = "fileWorktype:8";
				break;
			default :
				flag=false;
				//logger.error("Error taskFlag "+ entity.getTaskFlag());
		}
		SopFile sopfile=new SopFile();
		if(flag==true &&!"".equals(fileWorktype)){
			sopfile.setProjectId(entity.getProjectId());
			sopfile.setBelongUid(entity.getAssignUid());
			sopfile.setFileWorktype(fileWorktype);	
			sopFileService.updateByIdSelective(sopfile);
		}
		int result = sopTaskDao.updateById(entity);
		if(result<=0){
			throwSopException(ExceptionMessage.UPDATE_TASK_STATUS);
			return result ;
		}
		return result;
	
	}
	@Override
	public Long insertsopTask(SopTask entity) {
		Long result = sopTaskDao.insert(entity);
		if(result<=0){
			throwSopException(ExceptionMessage.UPDATE_TASK_STATUS);
			return result ;
		}
		return result;
	}
	
	
	@Override
	public List<SopTask> selectForTaskOverList(SopTaskBo query){
		return sopTaskDao.selectForTaskOverList(query);
	}

	@Override
	public Long selectTotalMission(SopTaskBo query) {
		return sopTaskDao.selectTotalMission(query);
	}

	@Override
	public Long selectTotalUrgent(SopTaskBo query) {
		return sopTaskDao.selectTotalUrgent(query);
	}

	@Override
	public int updateTask(SopTask task) {
		return sopTaskDao.updateTask(task);
	}

	@Override
	public SopTask getByFileInfo(Long id) {
		SopTask task = null;
		SopFile po = sopFileDao.selectById(id);
		if(po != null)
		{
			Integer taskFlag = this.getTaskFlagByWorktype(po.getFileWorktype());
			if(taskFlag != null)
			{
				SopTask taskQuery = new SopTask();
				taskQuery.setProjectId(po.getProjectId());
				taskQuery.setTaskFlag(taskFlag);;
				task = queryOne(taskQuery);
			}
		}
		return task;
	}
	
	private Integer getTaskFlagByWorktype(String fileWorktype)
	{
		Integer taskFlag = null;
		
		switch(fileWorktype)
		{
		
			case "fileWorktype:2": // 人事尽调
				taskFlag = 2;
				break;
			case "fileWorktype:3": //法务尽调
				taskFlag = 3;
				break;
			case "fileWorktype:4": //财务尽调
				taskFlag = 4;
				break;
			case "fileWorktype:8": //工商转让凭证
				taskFlag = 9;
				break;
			case "fileWorktype:9": //资金拨付凭证
				taskFlag = 8;
				break;
			default : 
				taskFlag = null;
				break;
		}
		return taskFlag;
	}
	public String dateAdd(Long date,Date endTime){
		Date convertStringToDate;
		String convertDateToString="";
		int diffHour=0;
		String result="";
		String hours="";
		try {
			Date nowTime = new Date(date);
			SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String retStrFormatNowDate = sdFormatter.format(nowTime);
		    convertStringToDate = DateUtil.convertStringToDate(retStrFormatNowDate,"yyyy-MM-dd HH:mm:ss");
			Date addDate = DateUtil.addDate(convertStringToDate,3);
			convertDateToString = DateUtil.convertDateToStringForChina(addDate);
            if(null!=endTime){
        		diffHour = DateUtil.getDiffHour(convertDateToString,DateUtil.convertDateToStringForChina(endTime));
            }else{
            	diffHour = DateUtil.getDiffHour(convertDateToString,DateUtil.getCurrentDateTime());			
			}
			result=Integer.toString(diffHour);
			if(diffHour<0){	
				hours=result.replace("-", "超时")+"(h)";
			}else{
				hours=result;
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return hours;
	}
	
	@Transactional
	@Override
	public int submitTask(SopTask task) throws Exception {
		boolean giveUp=task.getGiveUp();
		int result = 0;
		task = sopTaskDao.selectById(task.getId());
		task.setTaskStatus(DictEnum.taskStatus.已完成.getCode());
		task.setTaskDeadline(new Date());
		sopTaskDao.updateById(task);
		SopTask t = null;
		if(task.getTaskFlag() != null){
			SopFile file = new SopFile();
			file.setProjectId(task.getProjectId());
			if(task.getTaskFlag().intValue() == 5){
				file.setFileWorktype(DictEnum.fileWorktype.业务尽职调查报告.getCode());
			}else if(task.getTaskFlag().intValue() == 2){
				file.setFileWorktype(DictEnum.fileWorktype.人力资源尽职调查报告.getCode());
			}else if(task.getTaskFlag().intValue() == 3){
				file.setFileWorktype(DictEnum.fileWorktype.法务尽职调查报告.getCode());
			}else if(task.getTaskFlag().intValue() == 4){
				file.setFileWorktype(DictEnum.fileWorktype.财务尽职调查报告.getCode());
			}else if(task.getTaskFlag().intValue() == 8){
				file.setFileWorktype(DictEnum.fileWorktype.资金拨付凭证.getCode());
				t = new SopTask();
				t.setProjectId(task.getProjectId());
				t.setTaskFlag(9);
			}else if(task.getTaskFlag().intValue() == 9){
				file.setFileWorktype(DictEnum.fileWorktype.工商转让凭证.getCode());
				t = new SopTask();
				t.setProjectId(task.getProjectId());
				t.setTaskFlag(8);
			}
			SopFile f = sopFileDao.selectOne(file);
			if(giveUp==true){
				f.setFileUid(task.getAssignUid());
                f.setFileStatus("fileStatus:4");
			}
			f.setFileValid(1);
			sopFileDao.updateById(f);
			if(t != null){
				t.setTaskStatus(DictEnum.taskStatus.已完成.getCode());
				SopTask ut = sopTaskDao.selectOne(t);
		    	if(ut != null){
		    		/*Project project = new Project();
		    		project.setId(task.getProjectId());
		    		project.setProjectProgress(DictEnum.projectProgress.投后运营.getCode());
		    		project.setProjectStatus(DictEnum.projectStatus.THYY.getCode());
		    		projectDao.updateById(project);*/
		    		result = 1;
		    	}
			}
		}
		return result;
	}
	
	@Override
	public List<SopTask> getSopTaskByProjectId(SopTaskBo query) {
		return sopTaskDao.getSopTaskByProjectId(query);
	}
	
	public String ResourceMark(SopTask soptask){
		String result="";
		switch(soptask.getTaskFlag())
		{
			case 0: //完善简历	
				result="resume_produce_task";
				break;
			case 2 : //人事尽职调查报告
				result="person_produce_task";
				break;
			case 3 : //法务尽职调查报告
				result="law_handle_task";
				break;
			case 4 : //财务尽调报告
				result="finance_handle_task";
				break;
			case 8 : //资金拨付凭证
				result="payment_handle_task";
				break;
			case 9 : //工商变更登记凭证
				result="delivery_handle_task";
				break;
			default :
				result="";
		}
		return result;
	}

	@Override
	public int updateAtProjectTranfer(SopTask task) {
		return sopTaskDao.updateAtProjectTranfer(task);
	}
	
}
