package com.galaxyinternet.hologram.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.galaxyinternet.common.utils.SpringContextManager;
import com.galaxyinternet.model.hologram.ItemParam;
import com.galaxyinternet.model.hologram.ScoreAutoInfo;
import com.galaxyinternet.model.hologram.ScoreInfo;
import com.galaxyinternet.service.hologram.ScoreInfoService;
/**
 * 计算报告分数
 * @author wangsong
 *
 */
public class ReportScoreCalculator extends RecursiveTask<BigDecimal>
{
	private static final Logger logger = LoggerFactory.getLogger(ReportScoreCalculator.class);
	private static final long serialVersionUID = 1L;
	private ScoreInfoService service = SpringContextManager.getBean(ScoreInfoService.class);
	private Long relateId;
	private Long projectId;
	private Map<Long,ItemParam> items;
	

	public ReportScoreCalculator(Long relateId, Long projectId, Map<Long,ItemParam> items)
	{
		super();
		this.relateId = relateId;
		this.projectId = projectId;
		this.items = items;
	}

	@Override
	protected BigDecimal compute()
	{
		BigDecimal score = BigDecimal.ZERO;
		ScoreInfo info = service.queryById(relateId);
		if(info == null)
		{
			return score;
		}
		ItemParam item = null;
		if(items.containsKey(relateId))
		{
			item=  items.get(relateId);
		}
		else
		{
			item = new ItemParam();
			item.setRelatedId(relateId);
			item.setScore(BigDecimal.ZERO);
		}
		
		Integer scoreType = info.getScoreType();
		if(scoreType != null && (scoreType == 1 || scoreType == 2)) //手动输入或选择分数
		{
			score=  item.getScore();
			logger.debug(String.format("Relateid = %s Manual score = %s", relateId,score));
		}
		else //累加或自动打分
		{
			Integer mode = info.getProcessMode();
			String[] values = item.getValues();
			if(values == null)
			{
				values = new String[0];
			}
			logger.debug(String.format("Values = %s", Arrays.asList(values)));
			List<ScoreAutoInfo> autoList = info.getAutoList();
			if(mode == 2) //选项对应分数
			{
				Long dictId = null;
				String value = null;
				if(ArrayUtils.isEmpty(values) || CollectionUtils.isEmpty(autoList))
				{
					score = BigDecimal.ZERO;
				}
				else
				{
					value = values[0];
					for(ScoreAutoInfo auto : autoList)
					{
						if(auto.getDictId() != null && auto.getDictId().toString().equals(value))
						{
							dictId = auto.getDictId();
							score =  auto.getGrade();
							break;
						}
					}
				}
				logger.debug(String.format("RelateId=%s, Mode=2, Value=%s, DictId=%s, Score=%s", relateId,value,dictId,score));
			}
			else if(mode == 3) //n*分数
			{
				BigDecimal grade = null;
				Integer length = null;
				if(ArrayUtils.isEmpty(values) || CollectionUtils.isEmpty(autoList))
				{
					score = BigDecimal.ZERO;
				}
				else
				{
					ScoreAutoInfo autoInfo = autoList.get(0);
					score = autoInfo.getGrade().multiply(new BigDecimal(values.length));
					grade = autoInfo.getGrade();
					length = values.length;
				}
				logger.debug(String.format("RelateId=%s, Mode=3，grade*length = %s*%s = %s", relateId,grade,length,score));
			}
			else if(mode == 4) //总分-n*分数
			{
				BigDecimal grade = null;
				Integer length = null;
				if(ArrayUtils.isEmpty(values) || CollectionUtils.isEmpty(autoList))
				{
					score = info.getScoreMax();
				}
				else
				{
					ScoreAutoInfo autoInfo = autoList.get(0);
					score = info.getScoreMax().subtract(autoInfo.getGrade().multiply(new BigDecimal(values.length)));
					grade = autoInfo.getGrade();
					length = values.length;
				}
				logger.debug(String.format("RelateId=%s, Mode=4, max - (grade*length) = %s - (%s*%s) = %s", relateId,info.getScoreMax(),grade,length,score));
			}
			else if (mode == 5)//根据选择的数据四舍五入计算分数 - 胜算度，例如"阿里巴巴 10"
			{
				if(ArrayUtils.isEmpty(values) || values[0] == null || values[0].indexOf(" ") == -1)
				{
					score = BigDecimal.ZERO;
				}
				else
				{
					String value = values[0];
					String val = value.substring(value.lastIndexOf(" "));
					if(NumberUtils.isNumber(val))
					{
						BigDecimal num = new BigDecimal(val);
						num = num.setScale(0, BigDecimal.ROUND_HALF_UP);
						score = num;
					}
					logger.debug(String.format("RelateId=%s, Mode=5, Value=%s, Score=%s",relateId,value,score));
				}
			}
			ItemParam itemParam = new ItemParam();
			itemParam.setRelatedId(relateId);
			itemParam.setScore(score);
			items.put(relateId, itemParam);
			
			if(mode == 1)//权重(子项累加*权重)
			{
				BigDecimal weight = service.getWeight(relateId, projectId);
				weight = weight == null ? BigDecimal.valueOf(100L) : weight;
				ScoreInfo query = new ScoreInfo();
				query.setParentId(relateId);
				query.setReportType(info.getReportType());
				List<ScoreInfo> list = service.queryList(query);
				ReportScoreCalculator subTask = null;
				if(list != null && list.size() >0)
				{
					List<ReportScoreCalculator> subTasks = new ArrayList<>(list.size());
					for(ScoreInfo scoreInfo : list)
					{
						subTask = new ReportScoreCalculator(scoreInfo.getRelateId(),projectId,items);
						subTasks.add(subTask);
					}
					invokeAll(subTasks);
					BigDecimal sum = BigDecimal.ZERO;
					for(ReportScoreCalculator t : subTasks)
					{
						sum = sum.add(t.join());
					}
					itemParam.setScore(sum);
					items.put(relateId, itemParam);
					logger.debug(String.format("RelateId=%s, Mode=1, Sum=%s",relateId,sum,sum));
					BigDecimal num = sum.multiply(weight)
										.divide(BigDecimal.valueOf(100))
										.setScale(2, BigDecimal.ROUND_HALF_UP);
					score = num;
					logger.debug(String.format("ParentID=%s, Mode=%s, val*pec/100=%s*%s/100=%s",info.getParentId(),mode,sum,weight,score));
				}
			}
		}
		return score;
	}
}
