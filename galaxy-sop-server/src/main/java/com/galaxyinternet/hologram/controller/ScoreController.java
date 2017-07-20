package com.galaxyinternet.hologram.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.galaxyinternet.framework.core.model.ResponseData;
import com.galaxyinternet.hologram.model.ItemParam;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/galaxy/score")
public class ScoreController
{
	@ApiOperation("计算题目分数")
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="calculateScore", method=RequestMethod.POST)
	public ResponseData calculateScore(@RequestBody ItemParam param)
	{
		ResponseData data = new ResponseData();
		
		return data;
	}
}
