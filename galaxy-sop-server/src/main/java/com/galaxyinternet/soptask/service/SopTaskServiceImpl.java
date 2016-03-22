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

import com.galaxyinternet.bo.SopTaskBo;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.common.dictEnum.DictUtil;
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
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.service.SopTaskService;

@Service("com.galaxyinternet.service.SopTaskService")
public class SopTaskServiceImpl extends BaseServiceImpl<SopTask> implements SopTaskService {
	// private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SopTaskDao sopTaskDao;

	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private SopFileDao sopFileDao;

	@Override
	protected BaseDao<SopTask, Long> getBaseDao() {
		return this.sopTaskDao;
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
		if (sopTaskBo.getNameLike() != null && !"".equals(sopTaskBo.getNameLike())) {
			projectBo.setNameLike(sopTaskBo.getNameLike());
			// 查询该项目投资经理或者项目名称查询相应的项目
			projectList = projectDao.selectProjectByMap(projectBo);
			if(!projectList.isEmpty()){
			    sopTaskBo = setProjectIdsByPList(projectList);
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
		    int diffHour=dateAdd(sopTasknew.getCreatedTime());
			sopTaskBo.setHours(diffHour);
			sopTaskBo.setTaskDeadlineformat(longToString);//
			sopTaskBo.setTaskName(sopTasknew.getTaskName()==null?"":sopTasknew.getTaskName());
			sopTaskBo.setTaskType(sopTasknew.getTaskType()==null?"":DictUtil.getTypeName(sopTasknew.getTaskType()));
			sopTaskBo.setTaskOrder(sopTasknew.getTaskOrder()==null?0:sopTasknew.getTaskOrder());
			sopTaskBo.setOrderRemark(sopTasknew.getTaskOrder()==null?"":DictUtil.getTaskOrderName(sopTasknew.getTaskOrder()));
			sopTaskBo.setDepartmentId(sopTasknew.getDepartmentId());
			sopTaskBo.setTaskStatus(sopTasknew.getTaskStatus()==null?"":sopTasknew.getTaskStatus());
			
			if(sopTasknew.getTaskStatus().equals("taskStatus:1")){
				StringBuffer caozuohtml=new StringBuffer();
				sopTaskBo.setCaozuo(DictUtil.getStatusName(sopTasknew.getTaskStatus()));
				sopTaskBo.setTaskStatus(DictUtil.getStatusName(sopTasknew.getTaskStatus()));
//				sopTaskBo.setStatusFlag("1");/.append("/galaxy/soptask/goClaimtcPage?id="+sopTaskBo.getId())
				caozuohtml.append("<a id='dai' href='javascript:void(0)' class='blue' ")
				.append("     data-btn='claim'").append(" >").append(DictUtil.getStatusName(sopTasknew.getTaskStatus())).append("<input type='hidden' id='taskid' ")
						.append(" value='"+sopTasknew.getId()+"'").append("/><input type='hidden' id='projectid' ").append(" value='"+sopTasknew.getProjectId()+"' />");
				sopTaskBo.setCaozuohtml(caozuohtml.toString());
			}
			if(sopTasknew.getTaskStatus().equals("taskStatus:2")){
				StringBuffer caozuohtml=new StringBuffer();
				sopTaskBo.setCaozuo(DictUtil.getStatusName(sopTasknew.getTaskStatus()));
				sopTaskBo.setTaskStatus(DictUtil.getStatusName(sopTasknew.getTaskStatus()));
				sopTaskBo.setStatusFlag("2");
			//	String params = Constants.SESSOPM_SID_KEY + "=" + getSessionId(request) + "&" + Constants.REQUEST_URL_USER_ID_KEY + "=" + getUserId(request);
				caozuohtml.append("<a href='javascript:void(0)' class='blue' ").append(" id='doclaim' ").append(" >").append("处理")
				.append("<input type='hidden'").append(" value='"+sopTasknew.getId()+"'/>").append("</a>");
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
			sopTaskBo.setRemark(sopTasknew.getRemark()==null?"":sopTasknew.getRemark());
			SopTaskBoList.add(sopTaskBo);
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
	public int dateAdd(Long date){
		Date convertStringToDate;
		String convertDateToString="";
		int diffHour=0;
		try {
			Date nowTime = new Date(date);
			SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String retStrFormatNowDate = sdFormatter.format(nowTime);
		    convertStringToDate = DateUtil.convertStringToDate(retStrFormatNowDate,"yyyy-MM-dd HH:mm:ss");
			Date addDate = DateUtil.addDate(convertStringToDate,3);
			convertDateToString = DateUtil.convertDateToStringForChina(addDate);
			diffHour = DateUtil.getDiffHour(convertDateToString,DateUtil.getCurrentDateTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return diffHour;
	}
	
}
