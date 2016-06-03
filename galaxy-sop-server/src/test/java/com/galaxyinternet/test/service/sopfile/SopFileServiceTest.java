package com.galaxyinternet.test.service.sopfile;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.common.enums.DictEnum.RecordType;
import com.galaxyinternet.framework.core.thread.GalaxyThreadPool;
import com.galaxyinternet.model.sopfile.SopFile;
import com.galaxyinternet.service.SopFileService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/server_ctx.xml")
public class SopFileServiceTest 
{
	@Autowired
	private SopFileService sopFileService;
	@Test
	public void addTest() throws InterruptedException
	{
		ExecutorService serv = GalaxyThreadPool.getExecutorService();
		for(int i = 1;i<5;i++)
		{
			final Long projectId = i*100000L;
			serv.execute(new Runnable(){
				
				@Override
				public void run() {
					int code = 1;
					Long pid = projectId;
					for (int j = 0;j<100000;j++)
					{
						SopFile f = new SopFile();
						f.setProjectId(pid);
						f.setFileStatus(DictEnum.fileStatus.缺失.getCode());
						f.setFileStatus(DictEnum.fileStatus.已上传.getCode());
						f.setCreatedTime((new Date()).getTime());
						f.setFileValid(1);
						f.setFileSource(j%2+"");
						String typecode = "fileWorktype:"+code;
						f.setFileWorktype(typecode);
						f.setRemark("test");
						sopFileService.insert(f);
						if(code == 9)
						{
							code = 1;
							pid++;
						}
						else
						{
							code++;
						}
					}
				}
			});
		}
		serv.shutdown();
		serv.awaitTermination(1, TimeUnit.HOURS);
	}
	
	@Test
	public void testQuery()
	{
		
		try {
			SopFile query = new SopFile();
			query.setRecordType(RecordType.IDEAS.getType());
			List<SopFile> list = sopFileService.queryList(query);
			if(list != null && list.size()>0)
			{
				query = list.iterator().next();
			}
			sopFileService.selectByProjectAndFileWorkType(query);
			Assert.isTrue(true);
		} catch (Exception e) {
			Assert.isTrue(false, e.getMessage());
		}
	}
	@Test
	public void testInsert()
	{
		SopFile entity = new SopFile();
		entity.setRecordType(RecordType.IDEAS.getType());
		entity.setProjectId(1L);
		entity.setFileStatus(DictEnum.fileStatus.已上传.getCode());
		entity.setCreatedTime((new Date()).getTime());
		entity.setFileValid(1);
		entity.setFileSource(DictEnum.fileSource.内部.getCode());
		entity.setFileWorktype("ideaFileType:1");
		entity.setRemark("test");
		sopFileService.insert(entity);
		Assert.notNull(entity.getId());
	}
	@Test
	public void testUpdate()
	{
		SopFile query = new SopFile();
		query.setRecordType(RecordType.IDEAS.getType());
		List<SopFile> list = sopFileService.queryList(query);
		if(list != null && list.size() > 0)
		{
			SopFile po = list.iterator().next();
			po.setRecordType(RecordType.PROJECT.getType());
			sopFileService.updateById(po);
		}
	}
}
