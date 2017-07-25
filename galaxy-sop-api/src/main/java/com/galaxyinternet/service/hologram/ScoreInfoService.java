package com.galaxyinternet.service.hologram;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.ScoreInfo;
import com.galaxyinternet.model.hologram.ReportParam;

public interface ScoreInfoService extends BaseService<ScoreInfo>
{
	public Map<Long, BigDecimal> calculateSingleReport(ReportParam param);
	public Map<Long,BigDecimal> calculateMutipleReport(List<Long> relateIds, Long projectId) throws Exception;
}
