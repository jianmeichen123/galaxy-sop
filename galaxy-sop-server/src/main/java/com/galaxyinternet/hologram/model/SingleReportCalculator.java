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
import com.galaxyinternet.model.hologram.GradeAutoInfo;
import com.galaxyinternet.model.hologram.GradeInfo;
import com.galaxyinternet.model.hologram.ItemParam;
import com.galaxyinternet.service.hologram.GradeInfoService;
/**
 * 计算当前编辑报告(tab)分数
 * @author wangsong
 *
 */
public class SingleReportCalculator extends RecursiveTask<Integer>
{
	private static final Logger logger = LoggerFactory.getLogger(SingleReportCalculator.class);
	private static final long serialVersionUID = 1L;
	private GradeInfoService service = SpringContextManager.getBean(GradeInfoService.class);
	private Long relateId;
	private Map<Long,ItemParam> items;
	

	public SingleReportCalculator(Long relateId, Map<Long,ItemParam> items)
	{
		super();
		this.relateId = relateId;
		this.items = items;
	}

	@Override
	protected Integer compute()
	{
		int score = 0;
		GradeInfo info = service.queryById(relateId);
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
			item.setScore(0);
		}
		
		Integer scoreType = info.getScoreType();
		if(scoreType != null && (scoreType == 1 || scoreType == 2)) //手动输入或选择分数
		{
			score=  item.getScore();
			logger.debug(String.format("Relateid = %s Manual score = %s", relateId,score));
		}
		else
		{
			Integer mode = info.getProcessMode();
			String[] values = item.getValues();
			if(values == null)
			{
				values = new String[0];
			}
			logger.debug(String.format("Values = %s", Arrays.asList(values)));
			List<GradeAutoInfo> autoList = info.getAutoList();
			if(mode == 2) //选项对应分数
			{
				Long dictId = null;
				String value = null;
				if(ArrayUtils.isEmpty(values) || CollectionUtils.isEmpty(autoList))
				{
					score = 0;
				}
				else
				{
					value = values[0];
					for(GradeAutoInfo auto : autoList)
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
				Integer grade = null;
				Integer length = null;
				if(ArrayUtils.isEmpty(values) || CollectionUtils.isEmpty(autoList))
				{
					score = 0;
				}
				else
				{
					GradeAutoInfo autoInfo = autoList.get(0);
					score = autoInfo.getGrade()*values.length;
					grade = autoInfo.getGrade();
					length = values.length;
				}
				logger.debug(String.format("RelateId=%s, Mode=3，grade*length = %s*%s = %s", relateId,grade,length,score));
			}
			else if(mode == 4) //总分-n*分数
			{
				Integer grade = null;
				Integer length = null;
				if(ArrayUtils.isEmpty(values) || CollectionUtils.isEmpty(autoList))
				{
					score = info.getScoreMax();
				}
				else
				{
					GradeAutoInfo autoInfo = autoList.get(0);
					score = info.getScoreMax() - (autoInfo.getGrade()*values.length);
					grade = autoInfo.getGrade();
					length = values.length;
				}
				logger.debug(String.format("RelateId=%s, Mode=4, max - (grade*length) = %s - (%s*%s) = %s", info.getScoreMax(),grade,length,score));
			}
			else if (mode == 5)//根据选择的数据四舍五入计算分数 - 胜算度，例如"阿里巴巴 10"
			{
				if(ArrayUtils.isEmpty(values))
				{
					score = 0;
				}
				String value = values[0];
				if(value != null && value.indexOf(" ")>-1)
				{
					String val = value.substring(value.lastIndexOf(" "));
					if(NumberUtils.isNumber(val))
					{
						BigDecimal num = new BigDecimal(val);
						num = num.setScale(0, BigDecimal.ROUND_HALF_UP);
						score = num.intValue();
					}
					
				}
				logger.debug(String.format("RelateId=%s, Mode=5, Value=%s, Score=%s",relateId,value,score));
			}
			ItemParam itemParam = new ItemParam();
			itemParam.setRelatedId(relateId);
			itemParam.setScore(score);
			items.put(relateId, itemParam);
			
			if(mode == 1)//权重-子项累加*权重
			{
				GradeAutoInfo autoInfo = autoList.get(0);
				GradeInfo query = new GradeInfo();
				query.setParentId(relateId);
				List<GradeInfo> list = service.queryList(query);
				SingleReportCalculator subTask = null;
				if(list != null && list.size() >0)
				{
					List<SingleReportCalculator> subTasks = new ArrayList<>(list.size());
					for(GradeInfo gradeInfo : list)
					{
						subTask = new SingleReportCalculator(gradeInfo.getRelateId(),items);
						subTasks.add(subTask);
					}
					invokeAll(subTasks);
					int sum = 0;
					for(SingleReportCalculator gradeInfo : subTasks)
					{
						sum += gradeInfo.join();
					}
					itemParam.setScore(sum);
					items.put(relateId, itemParam);
					logger.debug(String.format("RelateId=%s, Mode=1, Sum=%s",relateId,sum,sum));
					BigDecimal num = BigDecimal.valueOf(sum)
												.multiply(BigDecimal.valueOf(autoInfo.getGrade()))
												.divide(BigDecimal.valueOf(100))
												.setScale(0, BigDecimal.ROUND_HALF_UP);
					score = num.intValue();
					logger.debug(String.format("ParentID=%s, Mode=1, val*pec/100=%s*%s/100=%s",info.getParentId(),sum,autoInfo.getGrade(),score));
				}
			}
		}
		return score;
	}

}
