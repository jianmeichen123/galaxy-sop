package com.galaxyinternet.test.service.InitProject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.galaxyinternet.bo.project.PersonPoolBo;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.enums.EnumUtil;
import com.galaxyinternet.core.WorkbookReader;
import com.galaxyinternet.model.common.Config;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.project.InterviewRecord;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.ConfigService;
import com.galaxyinternet.service.InterviewRecordService;
import com.galaxyinternet.service.PersonPoolService;
import com.galaxyinternet.service.ProjectService;
import com.galaxyinternet.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/server_ctx.xml" })
public class InitProject extends AbstractJUnit4SpringContextTests  {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private PersonPoolService personPoolService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private InterviewRecordService interviewRecordService;
	
	
	
	
	@Test
	public void initProject() throws Exception {
		//读取用户的excel
		List<ProjectPo> projectpoList = WorkbookReader.readIgnoreErrors("projectnew.xlsx", "project.properties", ProjectPo.class);
		for(int i = 0; i<projectpoList.size(); i++){
			ProjectPo projectPo = projectpoList.get(i);
			
			//把时间转换成毫秒 
			Long createTime = projectPo.getCreatedTime().getTime();
			
			//项目类型
			String projectType = projectPo.getProjectType().trim();
			if(projectType.equals("外部")){
				projectType = "投资";
			}
			DictEnum.projectType[] types =DictEnum.projectType.values();
			for(int t = 0; t<types.length; t++){
				if(projectType.equals(types[t].getName())){
					projectType = types[t].getCode();
				}
			}
			//项目名称
			String projectName = projectPo.getProjectName();
			//项目进度
			String projectProcess = projectPo.getProjectProgress();
			
			boolean pflag = false;
			DictEnum.projectProgress[] progress =DictEnum.projectProgress.values();
			for(int p = 0; p<progress.length; p++){
				if(projectProcess.equals(progress[p].getName())){
					projectProcess = progress[p].getCode();
					pflag = true;
				}
			}
			if(!pflag){
				System.out.println(projectName+"：项目进度没有匹配，原文："+projectProcess);
			}
			
			//状态
			String status = projectPo.getProjectStatus();
			if(StringUtils.isEmpty(status)){
				status = "待定";
			}else if(status.equals("关闭")){
				status = "否决";
			}
			DictEnum.meetingResult[] statusResult =DictEnum.meetingResult.values();
			for(int r = 0; r<statusResult.length; r++){
				if(status.equals(statusResult[r].getName())){
					status = statusResult[r].getCode();
				}
			}
			
			//投资经理
			String tzjl = projectPo.getCreateUname();
			User user = userService.queryUserByRealName(tzjl);
			Long userId = user.getId();
			String username = user.getRealName();
			Long departId ;
			Department dept = userService.getDepartmentByUserId(userId);
			if(null != dept){
				departId = dept.getId();
			}else{
				departId = 888888l;
			}
			
			//资金单位，0表示人民币，1表示美元，2表示英镑，3表示欧元
			Integer currencyUnit = null;
			String zjdw = projectPo.getCurrencyUnit();
			if(StringUtils.isNotEmpty(zjdw)){
				if(zjdw.equals("人民币")){
					currencyUnit = 0;
				}else if(zjdw.equals("美元")){
					currencyUnit = 1;
				}else if(zjdw.equals("英镑")){
					currencyUnit = 2;
				}else if(zjdw.equals("欧元")){
					currencyUnit = 3;
				}
			}
			
			//计划额度
			Double jhed = projectPo.getProjectContribution();
			//出让股份
			Double crgf = projectPo.getProjectShareRatio();
			if(null != crgf){
				crgf=crgf*100;
			}
			//初始估值
			Double csgz = null ;
			if(null != jhed && crgf != null ){
				if(crgf > 0 && jhed > 0){
					csgz =  jhed * (100/crgf);
				} 
			}
			
			//项目概述
			String desciption = projectPo.getProjectDescribe();
			String projectanaly = projectPo.getProspectAnalysis();
			//获取项目编码
			String projectCode = getProjectCode(departId);
			//保存项目并返回项目id
			Long projectId = saveProject(createTime,projectType,projectName,projectProcess,status,userId,username,departId,currencyUnit,jhed,crgf,csgz,desciption,projectanaly,projectCode);
			
			//初始化团队成员和项目-成员中间表
			String personName = projectPo.getPersonName();
			if(null != personName){
				String personTelephone = projectPo.getPersonTelephone();
				PersonPoolBo person = new PersonPoolBo();
				person.setPersonName(personName);
				person.setPersonTelephone(personTelephone);
				person.setProjectId(projectId);
				personPoolService.addProjectPerson(person);
			}
			
			
			//初始化访谈记录
			Date viewDate = projectPo.getViewDate();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(viewDate);
			Date viewDate1 = formatter.parse(dateString);
			
			String viewObj = projectPo.getViewObject();
			String viewRecord = projectPo.getViewRecord();
			InterviewRecord interview = new InterviewRecord();
			interview.setViewDate(viewDate1);
			interview.setViewNotes(viewRecord);
			interview.setViewTarget(viewObj);
			interview.setProjectId(projectId);
			interview.setCreatedTime(System.currentTimeMillis());
			interviewRecordService.insert(interview);
			
		}		
	}

	/**
	 * @author zcy
	 * @param createTime
	 * @param projectType
	 * @param projectName
	 * @param projectProcess
	 * @param status
	 * @param userId
	 * @param username
	 * @param departId
	 * @param currencyUnit
	 * @param jhed
	 * @param crgf
	 * @param csgz
	 * @param desciption
	 * @param projectanaly
	 * @throws Exception 
	 */
	private Long saveProject(Long createTime, String projectType, String projectName, String projectProcess,
			String status, Long userId, String username, Long departId, Integer currencyUnit, Double jhed, Double crgf,
			Double csgz, String desciption, String projectanaly,String projectCode) throws Exception {
		Project project = new Project();
		project.setCreatedTime(createTime);
		project.setProjectType(projectType);
		project.setProjectName(projectName);
		project.setProjectProgress(projectProcess);
		project.setProjectStatus(status);
		
		project.setCreateUid(userId);
		project.setCreateUname(username);
		project.setProjectDepartid(departId);
		
		project.setCurrencyUnit(currencyUnit);
		project.setProjectContribution(jhed);
		project.setProjectShareRatio(crgf);
		project.setProjectValuations(csgz);
		
		project.setProjectDescribe(desciption);
		project.setProspectAnalysis(projectanaly);
		
		//controller里加的字段
		project.setProjectCode(projectCode);
		project.setStockTransfer(0);
		
		projectService.newProject(project, null);
		return project.getId();
	}
	
	private String getProjectCode(Long did){
		try {
			Config config = configService.createCode();
			NumberFormat nf = NumberFormat.getInstance();
			nf.setGroupingUsed(false);
			nf.setMaximumIntegerDigits(6);
			nf.setMinimumIntegerDigits(6);
			if(did != null){
				int code = EnumUtil.getCodeByCareerline(did.longValue());
				String projectCode = String.valueOf(code) + nf.format(Integer.parseInt(config.getValue()));
				config.setPcode(projectCode);
				return projectCode;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Test
	public void getcode(){
		System.out.println(getProjectCode(1l));
	}
	
	
}