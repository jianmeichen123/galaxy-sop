package com.galaxyinternet.hologram.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.model.hologram.ReportParam;
import com.galaxyinternet.model.hologram.ScoreInfo;
import com.galaxyinternet.service.hologram.ScoreInfoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RequestMapping("/galaxy/score")
public class ScoreController
{
	private static final Logger logger = LoggerFactory.getLogger(ScoreController.class);
	@Autowired
	private ScoreInfoService scoreService;
	
	@SuppressWarnings({"rawtypes","unchecked"})
	@RequestMapping(value="calculateScore", method=RequestMethod.POST)
	@ApiOperation("计算题目分数")
	@ApiResponses(
		value={
				@ApiResponse(code = 200, message = "返回分数userData(key=题目realteId, value=分数)", response=ResponseData.class)
		}
	)
	public ResponseData calculateScore(
			@ApiParam(name = "param", value = "标题/值信息", required = true) 
			@RequestBody 
			ReportParam param)
	{
		ResponseData data = new ResponseData();
		try
		{
			Long relateId = param.getRelateId();
			Long projectId = param.getProjectId();
			Integer reportType = param.getReportType();
			if(relateId == null || projectId == null || reportType == null)
			{
				throw new BusinessException("参数不完整");
			}
			ScoreInfo info = scoreService.queryById(relateId);
			if(info == null)
			{
				throw new BusinessException("参数错误");
			}
			Long parentId = info.getParentId();
			ScoreInfo query = new ScoreInfo();
			query.setParentId(info.getParentId());
			query.setReportType(reportType);
			List<ScoreInfo> list = scoreService.queryList(query);
			List<Long> otherIds = new ArrayList<>();
			if(list != null && list.size()>0)
			{
				for(ScoreInfo item : list)
				{
					if(item != null && item.getRelateId() != null && item.getRelateId().intValue() == relateId.intValue())
					{
						otherIds.add(item.getRelateId());
					}
				}
			}
			Map<Long,BigDecimal> scores = scoreService.calculateSingleReport(param);
			BigDecimal total = scores.containsKey(parentId) ? scores.get(parentId) : BigDecimal.ZERO;
			if(otherIds.size() > 0)
			{
				Map<Long,BigDecimal> otherScores = scoreService.calculateMutipleReport(otherIds, projectId);
				scores.putAll(otherScores);
				for(Long item : otherIds)
				{
					if(scores.containsKey(item))
					{
						total = total.add(scores.get(item));
					}
				}
			}
			scores.put(parentId, total);
			data.getUserData().putAll(scores);
		} catch (Exception e)
		{
			logger.error("计算分数错误,Param = "+param,e);
			String msg = StringUtils.isEmpty(e.getMessage()) ? e.getMessage() : "计算分数错误";
			data.getResult().addError(msg);
		}
		
		return data;
	}
}