package com.galaxyinternet.test.service.hologram;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.galaxyinternet.model.hologram.InformationTitle;
import com.galaxyinternet.service.hologram.InformationTitleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/server_ctx.xml")
public class TitleServiceTest {
	
	@Autowired
	InformationTitleService service;
	@Test
	public void searchWithDataTest()
	{
		String id = "1";
		List<InformationTitle> list = service.searchWithData(id,"1");
		Assert.notEmpty(list,"No title found");
		InformationTitle title = list.iterator().next();
		Assert.notEmpty(title.getResultList(),"No results found");
		Assert.notEmpty(title.getFixedTableList(),"No fixedtable found");
		Assert.notEmpty(title.getDataList(),"No listdata found");
	}

}
