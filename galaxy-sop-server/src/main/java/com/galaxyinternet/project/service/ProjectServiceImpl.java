package com.galaxyinternet.project.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.enums.EnumUtil;
import com.galaxyinternet.dao.project.MeetingSchedulingDao;
import com.galaxyinternet.dao.project.PersonPoolDao;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.dao.sopfile.SopVoucherFileDao;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.model.common.Config;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.hr.PersonLearn;
import com.galaxyinternet.model.project.FinanceHistory;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.model.project.PersonPool;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.project.ProjectPerson;
import com.galaxyinternet.model.project.ProjectShares;
import com.galaxyinternet.model.role.Role;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.model.sopfile.SopVoucherFile;
import com.galaxyinternet.model.soptask.SopTask;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.model.user.UserRole;
import com.galaxyinternet.service.ConfigService;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopTaskService;
import com.galaxyinternet.service.UserRoleService;
import com.galaxyinternet.service.UserService;


@Service("com.galaxyinternet.service.ProjectService")
public class ProjectServiceImpl extends BaseServiceImpl<Project> implements ProjectService {

	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private SopFileDao sopFileDao;
	@Autowired
	private SopVoucherFileDao sopVoucherFileDao;
	@Autowired
	private MeetingSchedulingDao meetingSchedulingDao;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private SopTaskService sopTaskService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private PersonPoolDao personPoolDao;
	@Autowired
	private ConfigService configService;
	
	@Override
	protected BaseDao<Project, Long> getBaseDao() {
		return this.projectDao;
	}

	@Override
	@Transactional
	public long newProject(Project project, SopFile file) throws Exception {
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
	}

	public Map<String, Object> getSummary(Long userId)
	{
		Map<String, Object> summary = new HashMap<String, Object>();
		User currUser = userService.queryById(userId);
		Role role = userService.getRoleByUserId(userId);
		/**项目总数**/
		Long count = 0L;
		/**项目总估值**/
		Double projectValuations = null;
		/**尽调阶段项目**/
		Long countOfJd = 0L;
		/**投后运营阶段项目**/
		Long countOfYy = 0L;
		/**立项会过会数量**/
		Long ghsl = 0L;
		/**立项会过会率**/
		Double ghl = 0.00;
		/**本年度实看项目**/
		Long countOfndskxm = 0L;
		/**本年度应看项目**/
		Long countOfndykxm = 0L;
		/**投资经理数量**/
		Integer countOftzjl = 0;
		
		UserRole ur = new UserRole();
		ur.setRoleId(UserConstant.TZJL);
		List<UserRole> tzjlList = userRoleService.queryList(ur);
		
		
		if(role != null )
		{
			ProjectBo query = null;
			if(role.getId().longValue() == UserConstant.DSZ 
					|| role.getId().longValue() == UserConstant.CEO
					|| role.getId().longValue() == UserConstant.YYFZR
					)
			{
				query = new ProjectBo();
				countOftzjl = tzjlList.size();
				
			}
			else if(role.getId().longValue() == UserConstant.HHR)
			{
				query = new ProjectBo();
				query.setProjectDepartid(currUser.getDepartmentId());
				
				if(tzjlList != null && tzjlList.size() >0)
				{
					for(UserRole userRole : tzjlList)
					{
						User u = userService.queryById(userRole.getUserId());
						if(u != null && u.getDepartmentId() != null && u.getDepartmentId().longValue() == currUser.getDepartmentId().longValue())
						{
							countOftzjl = countOftzjl + 1;
						}
					}
				}
				
			}
			else if(role.getId().longValue() == UserConstant.TZJL)
			{
				query = new ProjectBo();
				query.setCreateUid(userId);
				
				countOftzjl = 1;
			}
			if(query != null)
			{
				Project po = projectDao.selectTotalSummary(query);
				count = po.getCount();
				projectValuations = po.getProjectValuations();
				
				List<Project> pjList = projectDao.selectStageSummary(query);
				if(pjList != null && pjList.size()>0)
				{
					for(Project pj : pjList)
					{
						//尽职调查
						if("projectProgress:6".equals(pj.getProjectProgress()))
						{
							countOfJd = pj.getCount();
						}
						//投后运营
						else if("projectProgress:10".equals(pj.getProjectProgress()))
						{
							countOfYy = pj.getCount();
						}
						
						//过会数量
						if(pj.getProjectProgress() != null)
						{
							String[] arr = pj.getProjectProgress().split(":");
							if(arr.length == 2 && NumberUtils.isDigits(arr[1]))
							{
								int progressIndex = Integer.valueOf(arr[1]);
								if(progressIndex>4)
								{
									ghsl = ghsl + pj.getCount();
								}
							}
						}
					}
				}
				Calendar cal = Calendar.getInstance();
				Date createDateFrom = DateUtil.getSearchYearFromDate(cal.get(Calendar.YEAR));
				query.setCreateDateFrom(createDateFrom.getTime());
				po = projectDao.selectTotalSummary(query);
				countOfndskxm = po.getCount();
				
				countOfndykxm = countOftzjl*500L;
				
				if(count>0 && ghsl>0)
				{
					ghl = BigDecimal.valueOf(ghsl).divide(BigDecimal.valueOf(count), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
			}
			BigDecimal val = BigDecimal.ZERO;
			if(projectValuations != null)
			{
				val = new BigDecimal(projectValuations,MathContext.UNLIMITED);
			}
			val = val.setScale(4, BigDecimal.ROUND_HALF_UP);
			summary.put("count", count);
			summary.put("projectValuations", val.toString());
			summary.put("countOfJd", countOfJd);
			summary.put("countOfYy", countOfYy);
			summary.put("ghl", ghl);
			summary.put("countOfndskxm", countOfndskxm);
			summary.put("countOfndykxm", countOfndykxm);
		}
		
		return summary;
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
		// TODO Auto-generated method stub
		//条件查询项目成员
		List<String> ids = new ArrayList<String>();
		if(!StringUtils.isBlank(query.getProjectPerson())){
			PersonPool pQuery = new PersonPool();
			pQuery.setPersonName(query.getProjectPerson());
			List<PersonPool> personPoolList = personPoolDao.selectProjectIdByPersonName(pQuery);
			for(PersonPool personPool : personPoolList){
				if(!ids.contains(personPool.getProjectId().toString())){
					ids.add(personPool.getProjectId().toString());
				}	
			}
			if(ids.size() > 0 ){
				query.setIds(ids);
			}else{
				query.setStopSearch("true");
			}
		}
		Page<Project> pageProject = super.queryPageList(query, pageable);
		return pageProject;
	}
	
	
	@Override
	public List<Long> getProIdsForPrivilege(Map<String,Object> params) {
		return projectDao.selectProIdsForPrivilege(params);
	}

	@Override
	@Transactional
	public void newProByPreInfo(Long userId,Long deptId, String userName, com.galaxyinternet.mongodb.model.Project project) throws Exception {
		//project
		Project newPro = new Project();
		BeanUtils.copyProperties(project, newPro);
		
		//创建项目编码
		Config config = configService.createCode();
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		nf.setMaximumIntegerDigits(6);
		nf.setMinimumIntegerDigits(6);
		int code = EnumUtil.getCodeByCareerline(deptId.longValue());
		String projectCode = String.valueOf(code) + nf.format(Integer.parseInt(config.getValue()));
		newPro.setProjectCode(String.valueOf(projectCode));
				
		if (newPro.getProjectValuations() == null) {
			if (newPro.getProjectShareRatio() != null
					&& newPro.getProjectShareRatio() > 0
					&& newPro.getProjectContribution() != null
					&& newPro.getProjectContribution() > 0) {
				newPro.setProjectValuations(newPro.getProjectContribution() * 100 / newPro.getProjectShareRatio());
			}
		}
		newPro.setCurrencyUnit(0);
		
		//默认不涉及股权转让
		newPro.setStockTransfer(0);
		newPro.setCreateUid(userId);
		newPro.setCreateUname(userName);
		newPro.setProjectDepartid(deptId);
		newPro.setProjectProgress(DictEnum.projectProgress.接触访谈.getCode());
		newPro.setProjectStatus(DictEnum.projectStatus.GJZ.getCode());
		newPro.setUpdatedTime(new Date().getTime());
		newPro.setCreatedTime(DateUtil.convertStringToDate(project.getCreateDate().trim(), "yyyy-MM-dd").getTime());
		
		//商业计划书
		SopFile businessPlan = project.getSopFile();
		businessPlan.setFileStatus(DictEnum.fileStatus.已上传.getCode());
		businessPlan.setFileUid(userId);
		businessPlan.setRecordType((byte)0);
		businessPlan.setCareerLine(deptId);
		
		Long newProId = newProject(newPro, businessPlan);
		
		
		
		//融资历史
		List<FinanceHistory> pFinanceHistory= project.getFh();
		
		
		
		
		
		
		//股权结构
		List<ProjectShares> pShares= project.getPsc();
		
		
		
		
		
	}
	
	
	
	
	
}