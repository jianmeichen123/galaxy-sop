package com.galaxyinternet.hologram.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.hologram.dao.GradeInfoDaoImpl;
import com.galaxyinternet.hologram.model.SingleReportCalculator;
import com.galaxyinternet.model.hologram.GradeInfo;
import com.galaxyinternet.model.hologram.ItemParam;
import com.galaxyinternet.model.hologram.SingleReportParam;
import com.galaxyinternet.service.hologram.GradeInfoService;

@Service
public class GradeInfoServiceImpl extends BaseServiceImpl<GradeInfo> implements GradeInfoService
{
	@Autowired
	private GradeInfoDaoImpl gradeInfoDao;
	/**
	 * 计算题目分数
	 * @param param
	 * @return
	 */
	public Map<Long,Integer> calculateSingleReport(SingleReportParam param)
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
			
			ForkJoinPool pool = new ForkJoinPool();
			SingleReportCalculator task = new SingleReportCalculator(relateId,map);
			ForkJoinTask<Integer> result = pool.submit(task);
			int score = result.get();
			pool.shutdown();
			Collection<ItemParam> values = map.values();
			Map<Long,Integer> scores = new HashMap<>();
			scores.put(param.getRelateId(), score);
			for(ItemParam value : values)
			{
				scores.put(value.getRelatedId(), value.getScore());
			}
			return scores;
			
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	@Override
	protected BaseDao<GradeInfo, Long> getBaseDao()
	{
		return gradeInfoDao;
	}
}
