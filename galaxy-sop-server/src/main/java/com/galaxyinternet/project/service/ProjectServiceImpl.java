package com.galaxyinternet.project.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.common.dictEnum.DictEnum.projectProgress;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.dao.hr.PersonLearnDao;
import com.galaxyinternet.dao.hr.PersonWorkDao;
import com.galaxyinternet.dao.project.JointDeliveryDao;
import com.galaxyinternet.dao.project.MeetingSchedulingDao;
import com.galaxyinternet.dao.project.PersonPoolDao;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.project.ProjectPersonDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.dao.sopfile.SopVoucherFileDao;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.hr.PersonLearn;
import com.galaxyinternet.model.hr.PersonWork;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.JointDelivery;
import com.galaxyinternet.model.project.MeetingRecord;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.model.project.PersonPool;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.project.ProjectPerson;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.sopfile.SopVoucherFile;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.project_process.event.ProgressChangeEvent;
import com.galaxyinternet.project_process.event.RejectEvent;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.InterviewRecordService;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopTaskService;
import com.galaxyinternet.service.UserRoleService;
import com.galaxyinternet.service.UserService;


@Service("com.galaxyinternet.service.ProjectService")
public class ProjectServiceImpl extends BaseServiceImpl<Project> implements ProjectService,ApplicationEventPublisherAware  {

	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private PersonLearnDao personLearnDao;
	@Autowired
	private PersonWorkDao personWorkDao;
	@Autowired
	private ProjectPersonDao projectPersonDao;
	@Autowired
	private SopFileDao sopFileDao;
	@Autowired
	private SopVoucherFileDao sopVoucherFileDao;
	@Autowired
	private MeetingSchedulingDao meetingSchedulingDao;
	@Autowired
	private UserService userService;
	@Autowired
	private SopTaskService sopTaskService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private PersonPoolDao personPoolDao;
	@Autowired
	private JointDeliveryDao jointDeliveryDao;
	
	@Autowired
	private MeetingRecordService meetingRecordService;
	@Autowired
	private InterviewRecordService interviewRecordService;

	@Override
	protected BaseDao<Project, Long> getBaseDao() {
		return this.projectDao;
	}

	public Project selectColumnById(Long id){
		return projectDao.selectColumnById(id);
	}

	@Override
	@Transactional
	public long newProject(Project project, SopFile file) throws Exception {
		project.setProgressHistory(project.getProjectProgress());
		long id = projectDao.insertProject(project);
		//存商业计划书
		if(file != null){
			file.setProjectId(id);
			file.setProjectProgress(DictEnum.projectProgress.接触访谈.getCode());
			sopFileDao.insert(file);
		}
		//通用属性
		SopFile f = new SopFile();
		f.setProjectId(id);
		f.setCareerLine(project.getProjectDepartid());
		f.setFileStatus(DictEnum.fileStatus.缺失.getCode());
		f.setCreatedTime((new Date()).getTime());
		SopVoucherFile svf = new SopVoucherFile();
		svf.setProjectId(id);
		svf.setCareerLine(project.getProjectDepartid());
		svf.setFileStatus(DictEnum.fileStatus.缺失.getCode());
		svf.setCreatedTime((new Date()).getTime());
		//投资意向书，先提交投资意向书-签署-上传签署证明
		svf.setProjectProgress(DictEnum.projectProgress.投资意向书.getCode());
		svf.setFileWorktype(DictEnum.fileWorktype.投资意向书.getCode());
	//	Long fid = sopVoucherFileDao.insert(svf);
		svf.setId(null);
		f.setFileValid(1);
		f.setProjectProgress(DictEnum.projectProgress.投资意向书.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.投资意向书.getCode());
		//f.setVoucherId(fid);
		sopFileDao.insert(f);
		f.setId(null);
		f.setVoucherId(null);
		//尽调阶段
		f.setFileValid(1);
		f.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.业务尽职调查报告.getCode());
		sopFileDao.insert(f);
		f.setId(null);
		f.setFileValid(0);
		f.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.人力资源尽职调查报告.getCode());
		sopFileDao.insert(f);
		f.setId(null);
		//投资协议文档，投资协议文档+签署证明
		svf.setProjectProgress(DictEnum.projectProgress.投资协议.getCode());
		svf.setFileWorktype(DictEnum.fileWorktype.投资协议.getCode());
		//fid = sopVoucherFileDao.insert(svf);
		svf.setId(null);
		f.setFileValid(1);
		f.setProjectProgress(DictEnum.projectProgress.投资协议.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.投资协议.getCode());
		//f.setVoucherId(fid);
		sopFileDao.insert(f);
		f.setId(null);
		f.setVoucherId(null);
		//股权交割
		f.setFileValid(0);
		f.setProjectProgress(DictEnum.projectProgress.股权交割.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.工商转让凭证.getCode());
		sopFileDao.insert(f);
		f.setId(null);
		f.setFileValid(0);
		f.setProjectProgress(DictEnum.projectProgress.股权交割.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.资金拨付凭证.getCode());
		sopFileDao.insert(f);
		f.setId(null);
		
		//投后运营
		/*f.setFileValid(1);
		f.setProjectProgress(DictEnum.projectProgress.投后运营.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.公司资料.getCode());
		sopFileDao.insert(f);
		f.setId(null);
		f.setFileValid(1);
		f.setProjectProgress(DictEnum.projectProgress.投后运营.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.财务预测报告.getCode());
		sopFileDao.insert(f);
		f.setId(null);
		f.setFileValid(1);
		f.setProjectProgress(DictEnum.projectProgress.投后运营.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.商业计划.getCode());
		sopFileDao.insert(f);
		f.setId(null);*/
		
/*		if(project.getProjectType() != null && 
				DictEnum.projectType.投资.getCode().equals(project.getProjectType())){*/
			//投资项目必须四个尽调、创建必须两个尽调
			f.setFileValid(0);
			f.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
			f.setFileWorktype(DictEnum.fileWorktype.法务尽职调查报告.getCode());
			sopFileDao.insert(f);
			f.setId(null);
			f.setFileValid(0);
			f.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
			f.setFileWorktype(DictEnum.fileWorktype.财务尽职调查报告.getCode());
			sopFileDao.insert(f);
			f.setId(null);
			//投资转让协议文档，投资转让协议文档+签署证明
			svf.setProjectProgress(DictEnum.projectProgress.投资协议.getCode());
			svf.setFileWorktype(DictEnum.fileWorktype.股权转让协议.getCode());
			//sfid = sopVoucherFileDao.insert(svf);
			svf.setId(null);
			f.setFileValid(1);
			f.setProjectProgress(DictEnum.projectProgress.投资协议.getCode());
			f.setFileWorktype(DictEnum.fileWorktype.股权转让协议.getCode());
			//f.setVoucherId(fid);
			sopFileDao.insert(f);
			f.setId(null);
		/*}*/
		return id;
	}

	@Override
	@Transactional
	public void toEstablishStage(Project project) throws Exception {
		project.setProjectProgress(DictEnum.projectProgress.立项会.getCode());
		projectDao.updateById(project);
		MeetingScheduling ms = new MeetingScheduling();
		ms.setProjectId(project.getId());
		ms.setMeetingType(DictEnum.meetingType.立项会.getCode());
		ms.setMeetingCount(0);
		ms.setStatus(DictEnum.meetingResult.待定.getCode());
		ms.setScheduleStatus(DictEnum.meetingSheduleResult.待排期.getCode());
		ms.setReserveTimeStartStr(null);
		ms.setReserveTimeEndStr(null);
		ms.setReserveTimeEnd(null);
		ms.setReserveTimeStart(null);
		ms.setApplyTime(new Timestamp(new Date().getTime()));
		ms.setCreatedTime((new Date()).getTime());
		meetingSchedulingDao.insert(ms);
		
		//修改CEO评审排期记录为完成
		MeetingScheduling m = new MeetingScheduling();
		m.setProjectId(project.getId());
		m.setMeetingType(DictEnum.meetingType.CEO评审.getCode());
		m.setStatus(DictEnum.meetingResult.通过.getCode());
		m.setUpdatedTime((new Date()).getTime());
		m.setScheduleStatus(DictEnum.meetingSheduleResult.已通过.getCode());
		meetingSchedulingDao.updateBySelective(m);
	}

	@Override
	@Transactional
	public void toSureMeetingStage(Project project) throws Exception {
		project.setProjectProgress(DictEnum.projectProgress.投资决策会.getCode());
		projectDao.updateById(project);
		MeetingScheduling ms = new MeetingScheduling();
		ms.setProjectId(project.getId());
		ms.setMeetingType(DictEnum.meetingType.投决会.getCode());
		ms.setMeetingCount(0);
		ms.setStatus(DictEnum.meetingResult.待定.getCode());
		ms.setScheduleStatus(DictEnum.meetingSheduleResult.待排期.getCode());
		ms.setReserveTimeStartStr(null);
		ms.setReserveTimeEndStr(null);
		ms.setReserveTimeEnd(null);
		ms.setReserveTimeStart(null);
		ms.setCreatedTime((new Date()).getTime());
		ms.setApplyTime(new Timestamp(new Date().getTime()));
		meetingSchedulingDao.insert(ms);
		
		//修改CEO评审排期记录为完成
		MeetingScheduling m = new MeetingScheduling();
		m.setProjectId(project.getId());
		m.setMeetingType(DictEnum.meetingType.立项会.getCode());
		m.setStatus(DictEnum.meetingResult.通过.getCode());
		m.setUpdatedTime((new Date()).getTime());
		m.setScheduleStatus(DictEnum.meetingSheduleResult.已通过.getCode());
		meetingSchedulingDao.updateBySelective(m);
	}


	@Override
	public List<Project> queryListById(List<Long> idList) {
		return projectDao.selectListById(idList);
	}
	@Transactional
	@Override
	public int closeProject(Project project) {
		int updateById = projectDao.updateById(project);
		if(updateById > 0){
			SopTask sopTask=new SopTask();
			sopTask.setProjectId(project.getId());
			sopTask.setTaskStatus("taskStatus3");
			sopTaskService.delete(sopTask);
		}
		return updateById;
	}
	@Transactional
	@Override
	public int deleteProject(Project project) {
		int updateById = projectDao.updateById(project);
		if(updateById > 0){
		   //项目删除，删除会议记录
			MeetingRecord meetingRecord=new MeetingRecord();
			meetingRecord.setProjectId(project.getId());
			meetingRecord.setMeetValid((byte)1);
			meetingRecordService.updateByIdProjectId(meetingRecord);
			//删除访谈
			InterviewRecord rc=new InterviewRecord();
			rc.setInterviewvalid(1L);
			rc.setProjectId(project.getId());
			interviewRecordService.updateByIdProjectId(rc);
			//删除任务
			SopTask soptask=new SopTask();
			soptask.setIsDelete(1);
			soptask.setProjectId(project.getId());
			sopTaskService.updateTask(soptask);
			//删除相关文件
			SopFile sopfile=new SopFile();
			sopfile.setFileValid(3);	
			sopfile.setProjectId(project.getId());
			sopFileDao.updateByIdSelective(sopfile);
		}
		return updateById;
	}
	/**
	 * id必须存在
	 * project_departId项目所属部门必须存在
	 * project_type项目类型必须存在
	 */
	@Transactional
	@Override
	public long perfectFilesForProject(Project project) throws Exception {
		Long id = project.getId();
		if(id == null || project.getProjectDepartid() == null || project.getProjectType() == null){
			return 0L;
		}
		//通用属性
		SopFile f = new SopFile();
		f.setProjectId(id);
		f.setCareerLine(project.getProjectDepartid());
		f.setFileStatus(DictEnum.fileStatus.缺失.getCode());
		f.setCreatedTime((new Date()).getTime());
		SopVoucherFile svf = new SopVoucherFile();
		svf.setProjectId(id);
		svf.setCareerLine(project.getProjectDepartid());
		svf.setFileStatus(DictEnum.fileStatus.缺失.getCode());
		svf.setCreatedTime((new Date()).getTime());
		//投资意向书，先提交投资意向书-签署-上传签署证明
		svf.setProjectProgress(DictEnum.projectProgress.投资意向书.getCode());
		svf.setFileWorktype(DictEnum.fileWorktype.投资意向书.getCode());
		Long fid = sopVoucherFileDao.insert(svf);
		svf.setId(null);
		f.setFileValid(1);
		f.setProjectProgress(DictEnum.projectProgress.投资意向书.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.投资意向书.getCode());
		f.setVoucherId(fid);
		sopFileDao.insert(f);
		f.setId(null);
		f.setVoucherId(null);
		//尽调阶段
		f.setFileValid(1);
		f.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.业务尽职调查报告.getCode());
		sopFileDao.insert(f);
		f.setId(null);
		f.setFileValid(0);
		f.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.人力资源尽职调查报告.getCode());
		sopFileDao.insert(f);
		f.setId(null);
		//投资协议文档，投资协议文档+签署证明
		svf.setProjectProgress(DictEnum.projectProgress.投资协议.getCode());
		svf.setFileWorktype(DictEnum.fileWorktype.投资协议.getCode());
		fid = sopVoucherFileDao.insert(svf);
		svf.setId(null);
		f.setFileValid(1);
		f.setProjectProgress(DictEnum.projectProgress.投资协议.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.投资协议.getCode());
		f.setVoucherId(fid);
		sopFileDao.insert(f);
		f.setId(null);
		f.setVoucherId(null);
		//股权交割
		f.setFileValid(0);
		f.setProjectProgress(DictEnum.projectProgress.股权交割.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.工商转让凭证.getCode());
		sopFileDao.insert(f);
		f.setId(null);
		f.setFileValid(0);
		f.setProjectProgress(DictEnum.projectProgress.股权交割.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.资金拨付凭证.getCode());
		sopFileDao.insert(f);
		f.setId(null);
		
		//投后运营
		/*f.setFileValid(1);
		f.setProjectProgress(DictEnum.projectProgress.投后运营.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.公司资料.getCode());
		sopFileDao.insert(f);
		f.setId(null);
		f.setFileValid(1);
		f.setProjectProgress(DictEnum.projectProgress.投后运营.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.财务预测报告.getCode());
		sopFileDao.insert(f);
		f.setId(null);
		f.setFileValid(1);
		f.setProjectProgress(DictEnum.projectProgress.投后运营.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.商业计划.getCode());
		sopFileDao.insert(f);
		f.setId(null);*/
		
		if(project.getProjectType() != null && 
				DictEnum.projectType.投资.getCode().equals(project.getProjectType())){
			//投资项目必须四个尽调、创建必须两个尽调
			f.setFileValid(0);
			f.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
			f.setFileWorktype(DictEnum.fileWorktype.法务尽职调查报告.getCode());
			sopFileDao.insert(f);
			f.setId(null);
			f.setFileValid(0);
			f.setProjectProgress(DictEnum.projectProgress.尽职调查.getCode());
			f.setFileWorktype(DictEnum.fileWorktype.财务尽职调查报告.getCode());
			sopFileDao.insert(f);
			f.setId(null);
			//投资转让协议文档，投资转让协议文档+签署证明
			svf.setProjectProgress(DictEnum.projectProgress.投资协议.getCode());
			svf.setFileWorktype(DictEnum.fileWorktype.股权转让协议.getCode());
			fid = sopVoucherFileDao.insert(svf);
			svf.setId(null);
			f.setFileValid(1);
			f.setProjectProgress(DictEnum.projectProgress.投资协议.getCode());
			f.setFileWorktype(DictEnum.fileWorktype.股权转让协议.getCode());
			f.setVoucherId(fid);
			sopFileDao.insert(f);
			f.setId(null);
		}
		return id;
	}
	
	public Page<Project> queryPageListByChart(Project query,PageRequest pageRequest){
		Page<Project> projectPage = super.queryPageList(query, pageRequest);
		//查找合伙人
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("roleId", UserConstant.HHR);
		List<Map<String,Object>> userList = userService.queryUserDetail(params);
		//查找事业线
		List<Department> departmentList = departmentService.queryAll();
		

		for(Project project : projectPage.getContent()){
			for(Map<String,Object> tempUser : userList){
				Long departmentId = (Long) tempUser.get("departmentId");
				if(departmentId!=null){
					if(departmentId.equals(project.getProjectDepartid())){
						project.setHhrName((String)tempUser.get("userName"));
					}
				}
			}
			//所属事业线
			for(Department department : departmentList){
				if(project.getProjectDepartid().intValue() == department.getId().intValue()){
					project.setDepartmentName(department.getName());
					break;
				}
			}
			
		}
		return projectPage;
		
	}
	
	@Override
	public Page<Project> queryPageList(ProjectBo query, Pageable pageable) {
		Page<Project> pageProject = super.queryPageList(query, pageable);
		return pageProject;
	}
	
	
	@Override
	public List<Long> getProIdsForPrivilege(Map<String,Object> params) {
		return projectDao.selectProIdsForPrivilege(params);
	}

	/**
	 * 添加团队成员
	 * 1、personPool
	 * 2、personLearn
	 * 3、personWork
	 * 4、projectPerson
	 */
	@Transactional
	@Override
	public Long addProPersonAndPerInfo( PersonPool personPool) throws Exception {
		Boolean isNew = false;
		Long personId = personPool.getId();
		
		if(personPool.getPersonBirthdayStr() != null){
			try {
				Date date = DateUtil.convertStringToDate(personPool.getPersonBirthdayStr()+"-01-01 00:00:00");
				personPool.setPersonBirthday(date);
			} catch (ParseException e) {
				throw new Exception(personPool.getPersonBirthdayStr() +" 转  Date 失败" + e);
			}
		}
		if(personId!=null){
			personPoolDao.updateById(personPool);
		}else{
			isNew = true;
			personId = personPoolDao.insert(personPool);
		}
		List<PersonLearn> personLearns = personPool.getPlc();
		if(personLearns != null&& personLearns.size() >0){
			for (PersonLearn personLearn : personLearns) {
				if(personLearn.getBeginDateStr()!= null){
					try {
						Date date = DateUtil.convertStringToDate(personLearn.getBeginDateStr()+"-01 00:00:00");
						personLearn.setBeginDate(date);
					} catch (ParseException e) {
						throw new Exception(personLearn.getOverDateStr() +" 转  Date 失败" + e);
					}
				}
				
				try {
					if(personLearn.getOverDate()!= null){
						personLearn.setOverDate(personLearn.getOverDate());
					}else if(personLearn.getOverDateStr() != null){
						Date date = DateUtil.convertStringToDate(personLearn.getOverDateStr()+"-01 00:00:00");
						personLearn.setOverDate(date);
					}else{
						Date date = DateUtil.convertStringToDate("0000-00-00 00:00:00");
						personLearn.setOverDate(date);
					}
				} catch (ParseException e) {
					throw new Exception(personLearn.getOverDateStr() +" 转  Date 失败" + e);
				}
				
				if(personLearn.getId() == null){
					personLearn.setPersonId(personId);
					personLearnDao.insert(personLearn);
				}else {
					if(personLearn.getIsEditOrCreate()!=null && personLearn.getIsEditOrCreate().intValue()==2 ){
						personLearnDao.deleteById(personLearn.getId());
					}else{
						personLearnDao.updateById(personLearn);
					}
				}
			}
		}
		//person work
		List<PersonWork> personWorks = personPool.getPwc();
		if(personWorks != null && personWorks.size() >0){
			for (PersonWork personWork : personWorks) {
				if(personWork.getBeginWorkStr() != null){
					try {
						Date date = DateUtil.convertStringToDate(personWork.getBeginWorkStr()+"-01 00:00:00");
						personWork.setBeginWork(date);
					} catch (ParseException e) {
						throw new Exception(personWork.getBeginWorkStr() +" 转  Date 失败" + e);
					}
				}
				
				try {
					if(personWork.getOverWork() != null){
						personWork.setOverWork(personWork.getOverWork());
					}else if(personWork.getOverWorkStr() != null){
						Date date = DateUtil.convertStringToDate(personWork.getOverWorkStr()+"-01 00:00:00");
						personWork.setOverWork(date);
					}else{
						Date date = DateUtil.convertStringToDate("0000-00-00 00:00:00");
						personWork.setOverWork(date);
					}
					
				} catch (ParseException e) {
					throw new Exception(personWork.getOverWorkStr() +" 转  Date 失败" + e);
				}
				
				
				if(personWork.getId() == null){
					personWork.setPersonId(personId);
					personWorkDao.insert(personWork);
				}else {
					if(personWork.getIsEditOrCreate()!=null && personWork.getIsEditOrCreate().intValue()==2 ){
						personWorkDao.deleteById(personWork.getId());
					}else{
						personWorkDao.updateById(personWork);
					}
				}
			}
		}
		
		//project --- person
		if(isNew){
			ProjectPerson projectPerson = new ProjectPerson();
			projectPerson.setProjectId(personPool.getProjectId());
			projectPerson.setPersonId(personId);
			projectPersonDao.insert(projectPerson);
		}
		
		return personId;
	}

	@Override
	public Page<Project> selectDeptProject(Project query, Pageable pageable) {
		// TODO Auto-generated method stub
		return projectDao.selectDeptProject(query, pageable);
	}

	@Override
	public Page<Project> selectProjectTotalTime(Project query, Pageable pageable) {
		// TODO Auto-generated method stub
		return projectDao.selectProjectTotalTime(query, pageable);
	}

	@Override
	public List<Project> selectProjectForPushMessage() {
		// TODO Auto-generated method stub
		return projectDao.selectProjectForPushMessage();
	}


	ApplicationEventPublisher eventPublisher;
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher)
	{
		this.eventPublisher = applicationEventPublisher;
		
	}

	@Override
	public void reject(Long id)
	{
		Project project = queryById(id);
		RejectEvent event = new RejectEvent(project);
		eventPublisher.publishEvent(event);
	}

	@Override
	public void updateProgress(Long id, String next)
	{
		Project project = queryById(id);
		ProgressChangeEvent event = new ProgressChangeEvent(project,projectProgress.getByCode(next));
		eventPublisher.publishEvent(event);
	}
	
	
	/**
	 * @author chenjianmei
	 * 修改项目基本信息，包括投资形式
	 * @serialData 2017-06-12
	 * @param project
	 */
	@Override
	@Transactional
	public int updateBaseById(Project project) {
		JointDelivery jointNull=new JointDelivery();
		if(null!=project.getFinanceMode()&&project.getFinanceMode().equals("financeMode:0")){
			jointNull.setProjectId(project.getId());
			jointDeliveryDao.delete(jointNull);
		}else{
			if(null!=project.getIsDelete()&&!project.getIsDelete().isEmpty()){
				for( Long Id:project.getIsDelete()){
					jointNull.setId(Id);
					jointDeliveryDao.delete(jointNull);
				}
			}
			if(null!=project.getJointDeliveryList()&&!project.getJointDeliveryList().isEmpty()){
				List<JointDelivery> jointDeliverylist=project.getJointDeliveryList();
				for(int i=0;i<jointDeliverylist.size();i++){
					JointDelivery jointDelivery=jointDeliverylist.get(i);
					jointDelivery.setProjectId(project.getId());
					jointDelivery.setDeliveryType(project.getFinanceMode());
					jointDelivery.setCreateUid(project.getUpdateUid());
					jointDelivery.setUpdatedTime(System.currentTimeMillis());
					if(null!=jointDelivery.getId()){
						jointDeliveryDao.updateById(jointDelivery);
					}else{
						jointDeliveryDao.insert(jointDelivery);
					}
				}
			 }
		}
		int result = projectDao.updateById(project);
	  return result;
	}

	@Override
	public List<Long> selectIds(Project project)
	{
		return projectDao.selectIds(project);
	}
	

}