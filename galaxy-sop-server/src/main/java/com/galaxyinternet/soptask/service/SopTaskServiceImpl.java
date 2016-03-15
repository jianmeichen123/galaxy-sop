package com.galaxyinternet.soptask.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.bo.SopTaskBo;
import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.common.dictEnum.DictUtil;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.soptask.SopTaskDao;
import com.galaxyinternet.exception.PlatformException;
import com.galaxyinternet.framework.core.constants.RequestUrl;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.framework.core.utils.ExceptionMessage;
import com.galaxyinternet.framework.core.utils.StringEx;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.service.SopTaskService;

import static com.galaxyinternet.utils.ExceptUtils.*;

@Service("com.galaxyinternet.service.SopTaskService")
public class SopTaskServiceImpl extends BaseServiceImpl<SopTask> implements SopTaskService {
	// private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SopTaskDao sopTaskDao;

	@Autowired
	private ProjectDao projectDao;

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
		   formatData = formatData(selectListSopTask,projectList);
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
	public Page<SopTaskBo> formatData(Page<SopTask> sopTaskData, List<Project> projectList) {
		Page<SopTaskBo> sopTaskPage = new Page<>(null, null, null);
		List<SopTaskBo> SopTaskBoList = new ArrayList<SopTaskBo>();
		
		for (int i = 0; i < sopTaskData.getContent().size(); i++) {
			SopTask sopTasknew =  sopTaskData.getContent().get(i);
			SopTaskBo sopTaskBo=new SopTaskBo();
			for (Project project : projectList) {
				if (sopTasknew.getProjectId() == project.getId()) {
					sopTaskBo.setProjectName(project.getProjectName());
					sopTaskBo.setCreateUname(project.getCreateUname());
					break;
				}

			}
			sopTaskBo.setId(sopTasknew.getId());
		
			sopTaskBo.setTaskDeadlineformat(sopTasknew.getTaskDeadline()!=null?DateUtil.convertDateToString(sopTasknew.getTaskDeadline()):"");//
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
				caozuohtml.append("<a id='dai'")
				.append("     data-btn='claim'").append(" >").append(DictUtil.getStatusName(sopTasknew.getTaskStatus())).append("<input type='hidden' id='taskid' ")
						.append(" value='"+sopTasknew.getId()+"'").append("/><input type='hidden' id='projectid' ").append(" value='"+sopTasknew.getProjectId()+"' />");
				sopTaskBo.setCaozuohtml(caozuohtml.toString());
			}
			if(sopTasknew.getTaskStatus().equals("taskStatus:2")){
				StringBuffer caozuohtml=new StringBuffer();
				sopTaskBo.setCaozuo(DictUtil.getStatusName(sopTasknew.getTaskStatus()));
				sopTaskBo.setTaskStatus(DictUtil.getStatusName(sopTasknew.getTaskStatus()));
				sopTaskBo.setStatusFlag("2");
				caozuohtml.append("<a ").append(" id='doclaim' ").append(" >").append(DictUtil.getStatusName(sopTasknew.getTaskStatus()))
				.append("<input type='hidden' id='claimid' ").append(" value='"+sopTasknew.getId()+"'").append("</a>");
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
}
