//package com.galaxyinternet.test.service.project;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.util.Assert;
//
//import com.galaxyinternet.model.project.Project;
//import com.galaxyinternet.service.ProjectService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath*:spring/server_ctx.xml")
//public class ProjectServiceTest 
//{
//	@Autowired
//	ProjectService projectService;
//	
//	@Test
//	@Rollback(true)
//	public void testGetSummary() throws Exception
//	{
//		Map<String,Object> summary = projectService.getSummary(1L);
//		Assert.notEmpty(summary);
//	}
//	
//	@Test
//	@Rollback(true)
//	public void testNewProject() throws Exception{
//		long _pro = projectService.newProject( getProjectObject());		
//		Assert.notNull(_pro);
//	}
//	
//	@Test
//	@Rollback(true)
//	public void tesToEstablishStage() throws Exception{
//		Boolean flag ;
//		try{
//			projectService.toEstablishStage( getProjectObject());		
//		}catch(Exception e){
//			flag = false;
//		}
//		flag = true;
//		Assert.isTrue(flag);
//	}//
//	
//	@Test
//	@Rollback(true)
//	public void tesToSureMeetingStage() throws Exception{
//		Boolean flag ;
//		try{
//			projectService.toSureMeetingStage( getProjectObject());		
//		}catch(Exception e){
//			flag = false;
//		}
//		flag = true;
//		Assert.isTrue(flag);
//	}
//	
//	private Project getProjectObject()throws Exception{
//		Project pro = new Project();
//		pro.setCompanyLocation("123");
//		pro.setCount(new Long(1));
//		pro.setCreatedTime(new Long(20160324));
//		pro.setCreateUid(new Long("134234234"));
//		pro.setCreateUname("123");
//		pro.setCreateUposition("123");
//		pro.setCurrencyUnit(1);
//		List<Long> list = new ArrayList<Long>();
//		list.add(new Long(1));
//		pro.setDeptIdList(list);
//		pro.setDirection("xx");
//		pro.setEscapeChar(true);
//		pro.setHhrName("xx");
//		pro.setId(new Long("123456789"));
//		pro.setNameCodeLike("123");
//		pro.setKeyword("123");
//		pro.setPageNum(1);
//		pro.setPageSize(1);
//		pro.setProjectBusinessModel("xx");
//		pro.setProjectCareerline("产品");
//		pro.setProjectCode("fx001");
//		pro.setProjectCompany(" 窝窝团");
//		pro.setProjectCompanyCode("wwt");
//		pro.setProjectContribution(123D);
//		pro.setProjectDepartid(new Long(1));
//		pro.setProjectDescribe(" 123");
//		pro.setProjectName("test项目");
//		pro.setProjectProgress("test123");
//		pro.setProjectShareRatio(new Double(1));
//		pro.setProjectStatus("01");
//		pro.setProjectType("fx_001");
//		pro.setProjectValuations(new Double(123));
//		pro.setProperty("890");
//		pro.setProspectAnalysis("  xxx");
//		pro.setStockTransfer(345);
//		pro.setUpdatedTime(new Long("20160324193600"));
//		pro.setUserPortrait("fx_$001");
//		return pro;
//	}
//}
