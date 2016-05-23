package com.galaxyinternet.test.service.idea;

import java.util.Date;
import java.util.List;

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
	@Rollback
	public void addTest() throws Exception
	{
		Config config = configService.getByKey(SopConstant.CONFIG_KEY_IDEA_CODE, true);
		Idea vo = new Idea();
		vo.setIdeaName("创意050501");
		vo.setIdeaCode(config.getValue());
		vo.setDepartmentId(CodeEnum.artificialIntelligence.getId());
		vo.setCreatedUid(3L);
		vo.setCreatedTime(new Date().getTime());
		vo.setUpdatedUid(112L);
		vo.setUpdatedTime(new Date().getTime());
		vo.setIdeaProgress("ideaProgress:1");
		vo.setIdeaDesc("描述1");
		vo.setIdeaSource("来源1");
		vo.setProjectId(1L);
		vo.setClaimantUid(4L);
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
		Long id = 2L;
		ideaService.createProject(id, "创意生成项目");
		Idea idea = ideaService.queryById(id);
		Assert.notNull(idea.getProjectId());
		
	}
	@Test
	public void testUpdate()
	{
		List<Idea> list = ideaService.queryAll();
		Assert.notEmpty(list);
		Idea idea = list.iterator().next();
		try {
			ideaService.updateById(idea);
			Assert.isTrue(true);
		} catch (Exception e) {
			Assert.isTrue(false,e.getMessage());
		}
	}

}
