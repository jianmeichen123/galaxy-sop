package com.galaxyinternet.service.hologram;

import java.util.Map;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.GradeInfo;
import com.galaxyinternet.model.hologram.SingleReportParam;

public interface GradeInfoService extends BaseService<GradeInfo>
{
	public Map<Long,Integer> calculateSingleReport(SingleReportParam param);
}
