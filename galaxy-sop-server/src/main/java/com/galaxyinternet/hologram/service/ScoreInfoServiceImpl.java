package com.galaxyinternet.hologram.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.framework.core.thread.GalaxyThreadPool;
import com.galaxyinternet.hologram.dao.ScoreInfoDaoImpl;
import com.galaxyinternet.hologram.model.ReportScoreCalculator;
import com.galaxyinternet.model.hologram.ItemParam;
import com.galaxyinternet.model.hologram.ReportParam;
import com.galaxyinternet.model.hologram.ScoreInfo;
import com.galaxyinternet.model.hologram.ScoreValue;
import com.galaxyinternet.service.hologram.ScoreInfoService;

@Service
public class ScoreInfoServiceImpl extends BaseServiceImpl<ScoreInfo> implements ScoreInfoService
{
	private static final Logger logger = LoggerFactory.getLogger(ScoreInfoServiceImpl.class);
	@Autowired
	private ScoreInfoDaoImpl gradeInfoDao;
	/**
	 * 计算题目分数
	 * @param param
	 * @return
	 */
	public Map<Long,BigDecimal> calculateSingleReport(ReportParam param)
	{
		try
		{
			Long relateId = param.getRelateId();
			List<ItemParam> items = param.getItems();
			Map<Long,ItemParam> map = new ConcurrentHashMap<>();
			for(ItemParam item : items)
			{
				map.put(item.getRelatedId(), item);
			}
			
			ForkJoinPool pool = GalaxyThreadPool.getForkJoinPool();
			ReportScoreCalculator task = new ReportScoreCalculator(relateId,param.getProjectId(),map);
			ForkJoinTask<BigDecimal> result = pool.submit(task);
			BigDecimal score = result.get();
			Collection<ItemParam> values = map.values();
			Map<Long,BigDecimal> scores = new HashMap<>();
			scores.put(param.getRelateId(), score);
			for(ItemParam value : values)
			{
				scores.put(value.getRelatedId(), value.getScore());
			}
			return scores;
			
		} catch (Exception e)
		{
			logger.error(String.format("计算报表分数失败, Param = %s", param.toString()),e);
		}
		
		return null;
	}
	/**
	 * 计算题目分数
	 * @param relateIds
	 * @param projectId
	 * @return
	 * @throws Exception 
	 */
	public Map<Long,BigDecimal> calculateMutipleReport(List<Long> relateIds, final Long projectId) throws Exception
	{
		final Map<Long,BigDecimal> scores = new ConcurrentHashMap<>();
		final CountDownLatch countDownLatch = new CountDownLatch(relateIds.size());
		ExecutorService executorService = GalaxyThreadPool.getExecutorService();
		for(Long item : relateIds)
		{
			final Long relateId = item;
			executorService.submit(new Runnable(){
				@Override
				public void run()
				{
					ScoreInfo scoreInfo = gradeInfoDao.selectById(relateId);
					ScoreInfo query = new ScoreInfo();
					query.setProjectId(projectId);
					query.setCode(scoreInfo.getCode());
					List<ScoreInfo> vallueList = gradeInfoDao.selectList(query);
					
					ReportParam param = new ReportParam();
					param.setRelateId(relateId);
					param.setProjectId(projectId);
					param.setReportType(scoreInfo.getProcessMode());
					param.setItems(convert(vallueList));
					Map<Long,BigDecimal> results = calculateSingleReport(param);
					scores.putAll(results);
					countDownLatch.countDown();
				}
			});
		}
		countDownLatch.await();
		
		return scores;
	}
	private List<ItemParam> convert(List<ScoreInfo> list)
	{
		List<ItemParam> items = new ArrayList<>();
		for(ScoreInfo info : list)
		{
			
			ItemParam param = new ItemParam();
			param.setRelatedId(info.getRelateId());
			param.setScore(info.getScore());
			List<ScoreValue> valueList = info.getValueList();
			if(valueList != null && valueList.size()>0)
			{
				List<String> values = new ArrayList<>();
				for(ScoreValue value : valueList)
				{
					if(value != null && value.getValue() != null)
					{
						values.add(value.getValue());
					}
				}
				param.setValues(values.toArray(new String[values.size()]));
			}
		}
		
		return items;
	}
	@Override
	protected BaseDao<ScoreInfo, Long> getBaseDao()
	{
		return gradeInfoDao;
	}
}