package com.galaxyinternet.chart.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.chart.SopDataBriefingDao;
import com.galaxyinternet.dao.chart.SopProjectAnalysisDao;
import com.galaxyinternet.model.chart.SopCharts;
import com.galaxyinternet.model.dict.Dict;
import com.galaxyinternet.service.DictService;
import com.galaxyinternet.service.chart.SopProjectAnalysisService;
import com.galaxyinternet.utils.MathUtils;

@Service("analysisService")
public class SopProjectAnalysisServiceImpl implements SopProjectAnalysisService {

	@Autowired
	private DictService dictService;
	@Autowired
	private SopProjectAnalysisDao overViewDao;
	@Autowired
	private SopDataBriefingDao dataBriefingDao;
	
	/**
	 * 项目总览图表查询
	 * @param query
	 * @return
	 */
	@Override
	public List<SopCharts> queryProjectOverView(
			SopCharts query) {
		// TODO Auto-generated method stub
		//获取总数
		Long totalCount = overViewDao.selectCount(query);
		//获取字典
		List<Dict> dictList = dictService.selectByParentCode("projectProgress");	
		List<SopCharts> overViewList = overViewDao.selectOverView(query);
		for(SopCharts overView : overViewList){
			if(overView.getProjectCount()==null){
				overView.setProjectCount(0L);
			}
			//计算阶段项目比率
			String projectRate = MathUtils.calculate(overView.getProjectCount(),totalCount, "/", 4);
			overView.setProjectRate(projectRate);
			overView.setTotalCount(totalCount);
			for(Dict dict : dictList){
				if(overView.getProjectProgress().equals(dict.getCode())){
					overView.setProjectProgressName(dict.getName());
					overView.setSort(dict.getSort());
					break;
				}
			}
		}
		overViewList = sort(overViewList);
		return overViewList;
	}
	
	
	
	
	private List<SopCharts> sort(List<SopCharts> sortList){
		//排序
        Collections.sort(sortList, new Comparator<SopCharts>(){
                @Override
                public int compare(SopCharts s1, SopCharts s2) {
                    // TODO Auto-generated method stub
                    int int_o1 = s1.getSort();
                    int int_o2 = s2.getSort();
                    if(int_o1==int_o2)
                        return 0;
                    return int_o1-int_o2;
                }
            });
        return sortList;
	}



	/**
	 * 项目日期环比增长折线图表
	 * @param query
	 * @return
	 */
	@Override
	public List<SopCharts> queryRiseRate(SopCharts query) {
		// TODO Auto-generated method stub
		List<SopCharts> chartsList = dataBriefingDao.selectCountGroupDate(query);
		//计算环比
		SopCharts preCharts = null;
		for(SopCharts sopCharts : chartsList){
			if(preCharts == null){
				sopCharts.setRiseRate("0");
			}else{
				Long fall  = Long.parseLong(MathUtils.calculate(sopCharts.getProjectCount(), preCharts.getProjectCount(), "-", 2));
				float rate = Float.parseFloat(MathUtils.calculate(fall, preCharts.getProjectCount(), "/", 4)) * 100;
				sopCharts.setRiseRate(String.valueOf(rate));
			}
			preCharts = sopCharts;
		}
		return chartsList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
