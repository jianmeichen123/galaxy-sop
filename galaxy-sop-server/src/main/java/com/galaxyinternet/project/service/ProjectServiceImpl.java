package com.galaxyinternet.project.service;

import com.galaxyinternet.bo.project.ProjectBo;
import com.galaxyinternet.common.dictEnum.DictEnum.projectProgress;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.query.ChartKpiQuery;
import com.galaxyinternet.common.query.ProjectQuery;
import com.galaxyinternet.common.utils.WebUtils;
import com.galaxyinternet.dao.hr.PersonLearnDao;
import com.galaxyinternet.dao.hr.PersonWorkDao;
import com.galaxyinternet.dao.project.JointDeliveryDao;
import com.galaxyinternet.dao.project.MeetingSchedulingDao;
import com.galaxyinternet.dao.project.PersonPoolDao;
import com.galaxyinternet.dao.project.ProjectDao;
import com.galaxyinternet.dao.project.ProjectPersonDao;
import com.galaxyinternet.dao.sopfile.SopFileDao;
import com.galaxyinternet.dao.sopfile.SopVoucherFileDao;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.constants.UserConstant;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.file.OSSHelper;
import com.galaxyinternet.framework.core.file.UploadFileResult;
import com.galaxyinternet.framework.core.id.IdGenerator;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.framework.core.model.Result;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.dict.Dict;
import com.galaxyinternet.model.hologram.InformationData;
import com.galaxyinternet.model.hologram.InformationListdata;
import com.galaxyinternet.model.hologram.InformationModel;
import com.galaxyinternet.model.hr.PersonLearn;
import com.galaxyinternet.model.hr.PersonWork;
import com.galaxyinternet.model.operationLog.UrlNumber;
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
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.platform.constant.PlatformConst;
import com.galaxyinternet.project_process.event.ProgressChangeEvent;
import com.galaxyinternet.project_process.event.RejectEvent;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.DictService;
import com.galaxyinternet.service.InterviewRecordService;
import com.galaxyinternet.service.MeetingRecordService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.SopTaskService;
import com.galaxyinternet.service.UserService;
import com.galaxyinternet.service.hologram.InformationDataService;
import com.galaxyinternet.service.hologram.InformationListdataService;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


@Service("com.galaxyinternet.service.ProjectService")
public class ProjectServiceImpl extends BaseServiceImpl<Project> implements ProjectService,ApplicationEventPublisherAware  {

	@Autowired
	private Cache cache;
	@Autowired
	private DictService dictService;
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
	@Autowired
	private InformationDataService infoDataService;
	@Autowired
	private InformationListdataService informationListdataService;

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
	public long insertProject(Project project,UploadFileResult result,HttpServletRequest request) throws Exception  {
		project.setProgressHistory(project.getProjectProgress());
		long id = projectDao.insertProject(project);
		User user = WebUtils.getUserFromSession();
		Long userId = user != null ? user.getId() : null;
		Long time=new Date().getTime();
		//存商业计划书
		if(null!=project.getBusinessPlanFile()){
			SopFile file=project.getBusinessPlanFile();
			file.setProjectId(id);
			file.setProjectProgress(DictEnum.projectProgress.接触访谈.getCode());
			sopFileDao.insert(file);
		}
		//需要保存到报告里面的字段/团队成员保存处理
		 InformationData informationData = project.getInformationData();
		 informationData.setProjectId(id+"");
		 infoDataService.save(informationData);
         //接触访谈信息处理
         Project p= new Project();
         p.setCreateUid(userId);
         //该接口调用之前项目流程里面的添加访谈记录的处理方式
         /**
          * p    该对象为了传值  p.setCreateUid(userId);   
          * project.getProjectQuery()    改对象是将访谈记录从前台传到后台
          * result 控制层上传商业计划书到阿里云的结果
          * request  对象
          */
         if(null!=project.getProjectQuery()){
        	 interviewRecordService.operateInterview(p, project.getProjectQuery(), result, request);
         }
         //通用属性
		SopFile f = new SopFile();
		f.setProjectId(id);
		f.setCareerLine(project.getProjectDepartid());
		f.setFileStatus(DictEnum.fileStatus.缺失.getCode());
		f.setCreatedTime(time);
		SopVoucherFile svf = new SopVoucherFile();
		svf.setProjectId(id);
		svf.setCareerLine(project.getProjectDepartid());
		svf.setFileStatus(DictEnum.fileStatus.缺失.getCode());
		svf.setCreatedTime(time);
		//投资意向书，先提交投资意向书-签署-上传签署证明
		svf.setProjectProgress(DictEnum.projectProgress.投资意向书.getCode());
		svf.setFileWorktype(DictEnum.fileWorktype.投资意向书.getCode());
		svf.setId(null);
		f.setFileValid(1);
		f.setProjectProgress(DictEnum.projectProgress.投资意向书.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.投资意向书.getCode());
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
		svf.setId(null);
		f.setFileValid(1);
		f.setProjectProgress(DictEnum.projectProgress.投资协议.getCode());
		f.setFileWorktype(DictEnum.fileWorktype.投资协议.getCode());
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
			svf.setId(null);
			f.setFileValid(1);
			f.setProjectProgress(DictEnum.projectProgress.投资协议.getCode());
			f.setFileWorktype(DictEnum.fileWorktype.股权转让协议.getCode());
			sopFileDao.insert(f);
			f.setId(null);
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
			//删除排期
			MeetingScheduling meetingScheduling=new MeetingScheduling();
			meetingScheduling.setProjectId(project.getId());
			meetingScheduling.setIsDelete(1);
			meetingSchedulingDao.updateBySelective(meetingScheduling);
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



	/**
	 * 项目分析 - 项目总览 - 项目进度分布图
	 * 项目分析 - 项目总览 - 项目数统计top10
	 *
	 * select - dict - parentCode : projectProgress 、 order by : dictSort
	 *
	 * 真实的项目数，没有协作混淆
	 * @param query
	 * @return
	 */
	public Map<String,Object> queryProjOverViewForComp(ChartKpiQuery query){
		Map<String, Object> result = new HashMap<>();

		String progressMark = "projectProgress";
		List<Project> proListTable = null;
		List<Project> proListListdata =null;

		ProjectBo proQuery = new ProjectBo();
		//过滤已否决
		proQuery.setResultCloseFilter(DictEnum.projectStatus.YFJ.getCode());

		proQuery.setStartTime(query.getStartTime());
		proQuery.setEndTime(query.getEndTime());
		//proQuery.setProjectDepartid(query.getDeptid());

		proListTable = projectDao.searchProjOverViewByProject(proQuery);
		//if(query.getDeptid() != null) proListListdata = projectDao.searchProjOverViewByListdata(proQuery);

		int totalPro = 0;
		Map<String, Object> data1 = new HashMap<>();
		Map<String, Object> data2 = new HashMap<>();
		// for progress—totle
		//1、获取字典 各阶段并排序  projectProgress:4
		if(proListTable != null && !proListTable.isEmpty())
		{
			List<Dict> dictList = dictService.selectByParentCode(progressMark);

			Map<String, Integer> progressTotle = new HashMap<>();
			for(Project temp:proListTable){
				totalPro += temp.getCompleted();
				if(progressTotle.containsKey(temp.getProjectProgress())){
					progressTotle.put(temp.getProjectProgress(), progressTotle.get(temp.getProjectProgress())+temp.getCompleted());
				}else{
					progressTotle.put(temp.getProjectProgress(), temp.getCompleted());
				}
			}


			//MathContext mc = new MathContext(1);
			int mc = 1;
			BigDecimal b1 = new BigDecimal(totalPro + "");
			BigDecimal b2 = null;

			//Map<String, Integer> progressTotleSort = new LinkedHashMap<>();
			List<String> xValues = new ArrayList<>();
			List<Integer> values = new ArrayList<>();
			List<String> rate = new ArrayList<>();
			Integer tempNum = null;
			for(Dict temp : dictList){
				tempNum = progressTotle.get(temp.getCode())==null?0:progressTotle.get(temp.getCode());
				xValues.add(temp.getName());
				values.add(tempNum);

				if(totalPro != 0){
					b2 = new BigDecimal(Integer.toString(tempNum*100));
					rate.add( b2.divide(b1,mc,java.math.RoundingMode.HALF_EVEN).toString()+"%");
				}else{
					rate.add("0%");
				}
			}

			List<Map<String, Object>> valueData = new ArrayList<>();
			Map<String, Object> valuesMap = new HashMap<>();
			valuesMap.put("name",  "项目数");
			valuesMap.put("data",  values);
			valuesMap.put("rate",  rate);
			valueData.add(valuesMap);

			data1.put("xValue",xValues);
			data1.put("dataValue",valueData);

			result.put("data1", data1);


			// top 10
			Map<Long, Integer> topData = new HashMap<>();

			for(Project temp:proListTable)
			{
				if(topData.containsKey(temp.getProjectDepartid())){
					topData.put(temp.getProjectDepartid(), topData.get(temp.getProjectDepartid())+temp.getCompleted());
				}else{
					topData.put(temp.getProjectDepartid(), temp.getCompleted());
				}
			}

			List<Map.Entry<Long, Integer>> entryList = new ArrayList<Map.Entry<Long, Integer>>(topData.entrySet());
			Collections.sort(entryList, new Comparator<Map.Entry<Long, Integer>>() {
				public int compare(Map.Entry<Long, Integer> me1, Map.Entry<Long, Integer> me2) {
					return me2.getValue() - me1.getValue();
				}
			});

			Iterator<Map.Entry<Long, Integer>> iter = entryList.iterator();
			Map.Entry<Long, Integer> tmpEntry = null;
			List<String> xTopValues = new ArrayList<>();
			List<Integer> topValues = new ArrayList<>();
			while (iter.hasNext()) {
				tmpEntry = iter.next();
				xTopValues.add((String)cache.hget(PlatformConst.CACHE_PREFIX_DEP+tmpEntry.getKey(), "name"));
				topValues.add( tmpEntry.getValue());
			}
			List<Map<String, Object>> topvalueData = new ArrayList<>();
			Map<String, Object> topvaluesMap = new HashMap<>();
			topvaluesMap.put("name",  "项目数");
			topvaluesMap.put("data",  topValues);
			topvalueData.add(topvaluesMap);

			data2.put("xValue",xTopValues);
			data2.put("dataValue",topvalueData);

			result.put("data2", data2);
		}
		return result;
	}
	public Map<String,Object> queryProjOverViewForDept(ChartKpiQuery query){
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> data1 = new HashMap<>();
		Map<String, Object> data2 = new HashMap<>();

		String progressMark = "projectProgress";
		List<Project> proListTable = null;
		List<Project> proListListdata =null;

		ProjectBo proQuery = new ProjectBo();
		//过滤已否决
		proQuery.setResultCloseFilter(DictEnum.projectStatus.YFJ.getCode());
		proQuery.setStartTime(query.getStartTime());
		proQuery.setEndTime(query.getEndTime());
		proQuery.setProjectDepartid(query.getDeptid());

		proListTable = projectDao.searchProjOverViewByProject(proQuery);
		proQuery.setForProgress(true);
		proListListdata = projectDao.searchProjOverViewByListdata(proQuery);

		int totalPro_0 = 0;
		int totalPro_1 = 0;
		int totalPro_2 = 0;
		Map<String, Integer> progressProTotle = new HashMap<>();
		for(Project temp:proListTable)
		{
			totalPro_1 += temp.getCompleted();

			if(progressProTotle.containsKey(temp.getProjectProgress())){
				progressProTotle.put(temp.getProjectProgress(), progressProTotle.get(temp.getProjectProgress())+temp.getCompleted());
			}else{
				progressProTotle.put(temp.getProjectProgress(), temp.getCompleted());
			}
		}
		Map<String, Integer> progressListTotle = new HashMap<>();
		for(Project temp:proListListdata)
		{
			totalPro_2 += temp.getCompleted();

			if(progressListTotle.containsKey(temp.getProjectProgress())){
				progressListTotle.put(temp.getProjectProgress(), progressListTotle.get(temp.getProjectProgress())+temp.getCompleted());
			}else{
				progressListTotle.put(temp.getProjectProgress(), temp.getCompleted());
			}
		}

		totalPro_0 = totalPro_1 + totalPro_2;

		List<String> xValues = new ArrayList<>();
		List<Integer> allvalues = new ArrayList<>();
		List<Integer> provalues = new ArrayList<>();
		List<Integer> listvalues = new ArrayList<>();

		List<String> rate0 = new ArrayList<>();
		List<String> rate1 = new ArrayList<>();
		List<String> rate2 = new ArrayList<>();

		//MathContext mc = new MathContext(1);
		int mc = 1;
		BigDecimal all1_b0 = new BigDecimal(totalPro_0 + "");
		BigDecimal all1_b1 = new BigDecimal(totalPro_1 + "");
		BigDecimal all1_b2 = new BigDecimal(totalPro_2 + "");
		BigDecimal b2 = null;


		List<Dict> dictList = dictService.selectByParentCode(progressMark);
		Integer int0 = null;
		Integer int1 = null;
		Integer int2 = null;
		for(Dict temp : dictList)
		{
			int1 = progressProTotle.get(temp.getCode())==null?0:progressProTotle.get(temp.getCode());
			int2 = progressListTotle.get(temp.getCode())==null?0:progressListTotle.get(temp.getCode());
			int0 = int1 + int2;

			xValues.add(temp.getName());

            allvalues.add(int0);
			provalues.add(int1);
			listvalues.add(int2);

			if(totalPro_0 != 0){
				b2 = new BigDecimal(Integer.toString(int0*100));
				rate0.add(b2.divide(all1_b0,mc,java.math.RoundingMode.HALF_UP).toString()+"%");
			}else{
				rate0.add("0%");
			}
			if(totalPro_1 != 0){
				b2 = new BigDecimal(Integer.toString(int1*100));
				rate1.add(b2.divide(all1_b1,mc,java.math.RoundingMode.HALF_UP).toString()+"%");
			}else{
				rate1.add("0%");
			}
			if(totalPro_2 != 0){
				b2 = new BigDecimal(Integer.toString(int2*100));
				rate2.add(b2.divide(all1_b2,mc,java.math.RoundingMode.HALF_UP).toString()+"%");
			}else{
				rate2.add("0%");
			}
		}

		List<Map<String, Object>> valueData = new ArrayList<>();
		Map<String, Object> valuesMap0 = new HashMap<>();
		valuesMap0.put("name",  "项目总数");
		valuesMap0.put("data",  allvalues);
		valuesMap0.put("rate",  rate0);

		Map<String, Object> valuesMap1 = new HashMap<>();
		valuesMap1.put("name",  "负责项目数");
		valuesMap1.put("data",  provalues);
		valuesMap1.put("rate",  rate1);

		Map<String, Object> valuesMap2 = new HashMap<>();
		valuesMap2.put("name",  "协作项目数");
		valuesMap2.put("data",  listvalues);
		valuesMap2.put("rate",  rate2);


		valueData.add(valuesMap0);
		valueData.add(valuesMap1);
		valueData.add(valuesMap2);

		data1.put("xValue",xValues);
		data1.put("dataValue",valueData);

		result.put("data1", data1);


		// top 10
		proQuery.setForProgress(null);
		proListListdata = projectDao.searchProjOverViewByListdata(proQuery);
		Map<Long, Integer> usertotalAll = new HashMap<>();
		Map<Long, Map<String, Integer>> userTypeTotle = new HashMap<>();

		for(Project temp:proListTable)
		{
			if(userTypeTotle.containsKey(temp.getCreateUid())){
				userTypeTotle.get(temp.getCreateUid()).put("pro",userTypeTotle.get(temp.getCreateUid()).get("pro") + temp.getCompleted());
			}else{
				Map<String, Integer> protopData = new HashMap<>();
				protopData.put("pro",temp.getCompleted());
				userTypeTotle.put(temp.getCreateUid(),protopData);
			}
		}
		for(Project temp:proListListdata)
		{
			if(userTypeTotle.containsKey(temp.getCreateUid())){
				if(userTypeTotle.get(temp.getCreateUid()).containsKey("list")){
					userTypeTotle.get(temp.getCreateUid()).put("list",userTypeTotle.get(temp.getCreateUid()).get("list") + temp.getCompleted());
				}else{
					userTypeTotle.get(temp.getCreateUid()).put("list", temp.getCompleted());
				}
			}else{
				Map<String, Integer> protopData = new HashMap<>();
				protopData.put("list",temp.getCompleted());
				userTypeTotle.put(temp.getCreateUid(),protopData);
			}
		}
		int valueT = 0;
		for(Map.Entry<Long, Map<String, Integer>> temp : userTypeTotle.entrySet()){
			valueT = 0;
			if(temp.getValue().containsKey("pro")){
				valueT += temp.getValue().get("pro");
			}
			if(temp.getValue().containsKey("list")){
				valueT += temp.getValue().get("list");
			}
			usertotalAll.put(temp.getKey(), valueT);
		}

		List<Map.Entry<Long, Integer>> entryList = new ArrayList<Map.Entry<Long, Integer>>(usertotalAll.entrySet());
		Collections.sort(entryList, new Comparator<Map.Entry<Long, Integer>>() {
			public int compare(Map.Entry<Long, Integer> me1, Map.Entry<Long, Integer> me2) {
				return me2.getValue() - me1.getValue();
			}
		});


		Iterator<Map.Entry<Long, Integer>> iter = entryList.iterator();
		Map.Entry<Long, Integer> tmpEntry = null;
		List<String> xTopValues = new ArrayList<>();
		List<Integer> protopValues = new ArrayList<>();
		List<Integer> listtopValues = new ArrayList<>();
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			//topDataSort.put(tmpEntry.getKey(), tmpEntry.getValue());
			xTopValues.add((String)cache.hget(PlatformConst.CACHE_PREFIX_USER+tmpEntry.getKey(), "realName"));
			protopValues.add(userTypeTotle.get(tmpEntry.getKey()).get("pro")==null?0:userTypeTotle.get(tmpEntry.getKey()).get("pro"));
			listtopValues.add(userTypeTotle.get(tmpEntry.getKey()).get("list")==null?0:userTypeTotle.get(tmpEntry.getKey()).get("list"));
		}

		List<Map<String, Object>> topvalueData = new ArrayList<>();
		Map<String, Object> topvaluesMap1 = new HashMap<>();
		topvaluesMap1.put("name",  "负责项目数");
		topvaluesMap1.put("data",  protopValues);
		Map<String, Object> topvaluesMap2 = new HashMap<>();
		topvaluesMap2.put("name",  "协作项目数");
		topvaluesMap2.put("data",  listtopValues);
		topvalueData.add(topvaluesMap1);
		topvalueData.add(topvaluesMap2);

		data2.put("xValue",xTopValues);
		data2.put("dataValue",topvalueData);

		result.put("data2", data2);


		return result;
	}

	//index - 已投项目分析
	public Map<String,Object> queryProjOverViewForXMFX(ChartKpiQuery query){
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> data1 = new HashMap<>();
		Map<String, Object> data2 = new HashMap<>();

		List<Project> proListTable = null;
		List<Project> proListListdata =null;

		ProjectBo proQuery = new ProjectBo();
		//过滤已否决
		proQuery.setResultCloseFilter(DictEnum.projectStatus.YFJ.getCode());
		proQuery.setStartTime(null); //query.getStartTime()
		proQuery.setEndTime(null); //query.getEndTime()
		proQuery.setProjectDepartid(query.getDeptid());
		proQuery.setProjectProgress(DictEnum.projectProgress.投后运营.getCode());


		Map<Long, Integer> usertotalAll = new HashMap<>();
		Map<Long, Map<String, Integer>> userTypeTotle = new HashMap<Long, Map<String, Integer>>();

		List<Map.Entry<Long, Integer>> entryList = null;
		Iterator<Map.Entry<Long, Integer>> iter = null;
		Map.Entry<Long, Integer> tmpEntry = null;
		List<String> xTopValues = new ArrayList<>();
		List<Integer> protopValues = new ArrayList<>();

		List<Map<String, Object>> topvalueData = new ArrayList<>();
		Map<String, Object> topvaluesMap1 = new HashMap<>();
		/*topvaluesMap1.put("name",  "项目数");
		topvaluesMap1.put("data",  protopValues);
		topvalueData.add(topvaluesMap1);*/

		switch(query.getBelongType())
		{
			case 1:
				proListTable = projectDao.searchProjOverViewByProject(proQuery);
				proListListdata = projectDao.searchProjOverViewByListdata(proQuery);

				// top 10

				if(query.getDeptid() != null)
				{
					for(Project temp:proListTable)
					{
						if(userTypeTotle.containsKey(temp.getCreateUid())){
							userTypeTotle.get(temp.getCreateUid()).put("pro",userTypeTotle.get(temp.getCreateUid()).get("pro") + temp.getCompleted());
						}else{
							Map<String, Integer> protopData = new HashMap<>();
							protopData.put("pro",temp.getCompleted());
							userTypeTotle.put(temp.getCreateUid(),protopData);
						}
					}
					for(Project temp:proListListdata)
					{
						if(userTypeTotle.containsKey(temp.getCreateUid())){
							if(userTypeTotle.get(temp.getCreateUid()).containsKey("list")){
								userTypeTotle.get(temp.getCreateUid()).put("list",userTypeTotle.get(temp.getCreateUid()).get("list") + temp.getCompleted());
							}else{
								userTypeTotle.get(temp.getCreateUid()).put("list", temp.getCompleted());
							}
						}else{
							Map<String, Integer> protopData = new HashMap<>();
							protopData.put("list",temp.getCompleted());
							userTypeTotle.put(temp.getCreateUid(),protopData);
						}
					}
				}else{
					for(Project temp:proListTable)
					{
						if(userTypeTotle.containsKey(temp.getProjectDepartid())){
							userTypeTotle.get(temp.getProjectDepartid()).put("pro",userTypeTotle.get(temp.getProjectDepartid()).get("pro") + temp.getCompleted());
						}else{
							Map<String, Integer> protopData = new HashMap<>();
							protopData.put("pro",temp.getCompleted());
							userTypeTotle.put(temp.getProjectDepartid(),protopData);
						}
					}
					for(Project temp:proListListdata)
					{
						if(userTypeTotle.containsKey(temp.getProjectDepartid())){
							if(userTypeTotle.get(temp.getProjectDepartid()).containsKey("list")){
								userTypeTotle.get(temp.getProjectDepartid()).put("list",userTypeTotle.get(temp.getProjectDepartid()).get("list") + temp.getCompleted());
							}else{
								userTypeTotle.get(temp.getProjectDepartid()).put("list", temp.getCompleted());
							}
						}else{
							Map<String, Integer> protopData = new HashMap<>();
							protopData.put("list",temp.getCompleted());
							userTypeTotle.put(temp.getProjectDepartid(),protopData);
						}
					}
				}

				int valueT = 0;
				for(Map.Entry<Long, Map<String, Integer>> temp : userTypeTotle.entrySet()){
					valueT = 0;
					if(temp.getValue().containsKey("pro")){
						valueT += temp.getValue().get("pro");
					}
					if(temp.getValue().containsKey("list")){
						valueT += temp.getValue().get("list");
					}
					usertotalAll.put(temp.getKey(), valueT);
				}

				entryList = new ArrayList<Map.Entry<Long, Integer>>(usertotalAll.entrySet());
				Collections.sort(entryList, new Comparator<Map.Entry<Long, Integer>>() {
					public int compare(Map.Entry<Long, Integer> me1, Map.Entry<Long, Integer> me2) {
						return me2.getValue() - me1.getValue();
					}
				});

				iter = entryList.iterator();
				while (iter.hasNext())
				{
					tmpEntry = iter.next();
					//topDataSort.put(tmpEntry.getKey(), tmpEntry.getValue());

					if(query.getDeptid() != null)
					{
						xTopValues.add((String)cache.hget(PlatformConst.CACHE_PREFIX_USER+tmpEntry.getKey(), "realName"));
					}else{
						xTopValues.add((String)cache.hget(PlatformConst.CACHE_PREFIX_DEP+tmpEntry.getKey(), "name"));
					}

					protopValues.add(tmpEntry.getValue());
				}

				topvaluesMap1.put("name",  "项目数");
				topvaluesMap1.put("data",  protopValues);
				topvalueData.add(topvaluesMap1);

				data2.put("xValue",xTopValues);
				data2.put("dataValue",topvalueData);

				result.put("data2", data2);

				break;
			case 2:  //负责的项目 pro
				proListTable = projectDao.searchProjOverViewByProject(proQuery);

				// top 10

				if(query.getDeptid() != null)
				{
					for(Project temp:proListTable)
					{
						if(usertotalAll.containsKey(temp.getCreateUid())){
							usertotalAll.put(temp.getCreateUid(), usertotalAll.get(temp.getCreateUid())+temp.getCompleted());
						}else{
							usertotalAll.put(temp.getCreateUid(), temp.getCompleted());
						}
					}
				}else{
					for(Project temp:proListTable)
					{
						if(usertotalAll.containsKey(temp.getProjectDepartid())){
							usertotalAll.put(temp.getProjectDepartid(), usertotalAll.get(temp.getProjectDepartid())+temp.getCompleted());
						}else{
							usertotalAll.put(temp.getProjectDepartid(), temp.getCompleted());
						}
					}
				}


				entryList = new ArrayList<Map.Entry<Long, Integer>>(usertotalAll.entrySet());
				Collections.sort(entryList, new Comparator<Map.Entry<Long, Integer>>() {
					public int compare(Map.Entry<Long, Integer> me1, Map.Entry<Long, Integer> me2) {
						return me2.getValue() - me1.getValue();
					}
				});

				iter = entryList.iterator();
				while (iter.hasNext())
				{
					tmpEntry = iter.next();
					//topDataSort.put(tmpEntry.getKey(), tmpEntry.getValue());

					if(query.getDeptid() != null)
					{
						xTopValues.add((String)cache.hget(PlatformConst.CACHE_PREFIX_USER+tmpEntry.getKey(), "realName"));
					}else{
						xTopValues.add((String)cache.hget(PlatformConst.CACHE_PREFIX_DEP+tmpEntry.getKey(), "name"));
					}

					protopValues.add(tmpEntry.getValue());
				}

				topvaluesMap1.put("name",  "项目数");
				topvaluesMap1.put("data",  protopValues);
				topvalueData.add(topvaluesMap1);

				data2.put("xValue",xTopValues);
				data2.put("dataValue",topvalueData);

				result.put("data2", data2);

				break;
			case 3:  //协作的项目，listdata - table
				proListListdata = projectDao.searchProjOverViewByListdata(proQuery);

				// top 10

				if(query.getDeptid() != null)
				{
					for(Project temp:proListListdata)
					{
						if(usertotalAll.containsKey(temp.getCreateUid())){
							usertotalAll.put(temp.getCreateUid(), usertotalAll.get(temp.getCreateUid())+temp.getCompleted());
						}else{
							usertotalAll.put(temp.getCreateUid(), temp.getCompleted());
						}
					}
				}else{
					for(Project temp:proListListdata)
					{
						if(usertotalAll.containsKey(temp.getProjectDepartid())){
							usertotalAll.put(temp.getProjectDepartid(), usertotalAll.get(temp.getProjectDepartid())+temp.getCompleted());
						}else{
							usertotalAll.put(temp.getProjectDepartid(), temp.getCompleted());
						}
					}
				}

				entryList = new ArrayList<Map.Entry<Long, Integer>>(usertotalAll.entrySet());
				Collections.sort(entryList, new Comparator<Map.Entry<Long, Integer>>() {
					public int compare(Map.Entry<Long, Integer> me1, Map.Entry<Long, Integer> me2) {
						return me2.getValue() - me1.getValue();
					}
				});

				iter = entryList.iterator();
				while (iter.hasNext())
				{
					tmpEntry = iter.next();
					//topDataSort.put(tmpEntry.getKey(), tmpEntry.getValue());

					if(query.getDeptid() != null)
					{
						xTopValues.add((String)cache.hget(PlatformConst.CACHE_PREFIX_USER+tmpEntry.getKey(), "realName"));
					}else{
						xTopValues.add((String)cache.hget(PlatformConst.CACHE_PREFIX_DEP+tmpEntry.getKey(), "name"));
					}

					protopValues.add(tmpEntry.getValue());
				}

				topvaluesMap1.put("name",  "项目数");
				topvaluesMap1.put("data",  protopValues);
				topvalueData.add(topvaluesMap1);

				data2.put("xValue",xTopValues);
				data2.put("dataValue",topvalueData);

				result.put("data2", data2);

				break;
			default:
				break;
		}

		return result;
	}





}