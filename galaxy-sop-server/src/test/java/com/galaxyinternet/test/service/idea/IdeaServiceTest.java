package com.galaxyinternet.test.service.idea;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.galaxyinternet.common.constants.SopConstant;
import com.galaxyinternet.common.enums.CodeEnum;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.model.PageRequest;
import com.galaxyinternet.model.common.Config;
import com.galaxyinternet.model.idea.Idea;
import com.galaxyinternet.service.ConfigService;
import com.galaxyinternet.service.IdeaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/server_ctx.xml")
public class IdeaServiceTest 
{
	@Autowired
	private ConfigService configService;
	@Autowired
	private IdeaService ideaService;
	@Test
	@Rollback(true)
	public void addTest() throws Exception
	{
		Config config = configService.getByKey(SopConstant.CONFIG_KEY_IDEA_CODE, true);
		Idea vo = new Idea();
		vo.setIdeaName("创意042501");
		vo.setIdeaCode(config.getValue());
		vo.setDepartmentId(CodeEnum.o2oDS.getId());
		vo.setCreatedUid(111L);
		vo.setCreatedTime(new Date().getTime());
		vo.setUpdatedUid(112L);
		vo.setUpdatedTime(new Date().getTime());
		vo.setIdeaProgress("ideaProgress:1");
		vo.setIdeaDesc("描述1");
		vo.setIdeaSource("来源1");
		vo.setProjectId(1L);
		vo.setClaimantUid(113L);
		ideaService.insert(vo);
		Assert.notNull(vo.getId());
	}
	@Test
	public void testSelect()
	{
		Idea query = new Idea();
		query.setKeyword("1");
		query.setIdeaProgress("ideaProgress:1");
		Page<Idea> page = ideaService.queryPageList(query, new PageRequest(0,10));
		Assert.isTrue(page != null && page.getContent() != null && page.getContent().size()>0);
	}
	@Test
	public void testCreateProject()
	{
		Long id = 1L;
		ideaService.createProject(id, "创意生成项目");
		Idea idea = ideaService.queryById(1L);
		Assert.notNull(idea.getProjectId());
		
	}

}
