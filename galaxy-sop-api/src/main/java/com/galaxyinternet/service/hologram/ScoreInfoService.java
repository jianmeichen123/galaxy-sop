package com.galaxyinternet.service.hologram;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.galaxyinternet.framework.core.service.BaseService;
import com.galaxyinternet.model.hologram.ScoreInfo;
import com.galaxyinternet.model.hologram.ReportParam;

public interface ScoreInfoService extends BaseService<ScoreInfo>
{
	public Map<String, BigDecimal> calculateSingleReport(ReportParam param);
	public Map<String,BigDecimal> calculateMutipleReport(List<Long> relateIds, Long projectId) throws Exception;
	public BigDecimal getWeight(Long relateId, Long projectId);
	/**
	 * 查询各tab项的比重
	 * @param preCode EN CN
	 * @return W1001项目、W1002团队、运营、、的比重
	 */
	public Map<String,String> getTabWeight(String preCode, Long projectId);
}
