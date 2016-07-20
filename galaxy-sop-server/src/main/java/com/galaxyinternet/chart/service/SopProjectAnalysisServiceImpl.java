package com.galaxyinternet.chart.service;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.chart.SopProjectAnalysisDao;
import com.galaxyinternet.model.chart.SopProjectAnalysis;
import com.galaxyinternet.model.dict.Dict;
import com.galaxyinternet.service.DictService;
import com.galaxyinternet.service.chart.SopProjectAnalysisService;

@Service("analysisService")
public class SopProjectAnalysisServiceImpl implements SopProjectAnalysisService {

	@Autowired
	private DictService dictService;
	@Autowired
	private SopProjectAnalysisDao overViewDao;
	
	/**
	 * 项目总览图表查询
	 * @param query
	 * @return
	 */
	@Override
	public List<SopProjectAnalysis> queryProjectOverView(
			SopProjectAnalysis query) {
		// TODO Auto-generated method stub
		//获取总数
		Long totalCount = overViewDao.selectCount(query);
		//获取字典
		List<Dict> dictList = dictService.selectByParentCode("projectProgress");	
		List<SopProjectAnalysis> overViewList = overViewDao.selectOverView(query);
		for(SopProjectAnalysis overView : overViewList){
			if(overView.getProjectCount()==null){
				overView.setProjectCount(0L);
			}
			//计算阶段项目比率
			String projectRate = divide(overView.getProjectCount(),totalCount);
			overView.setProjectRate(projectRate);
			overView.setTotalCount(totalCount);
			for(Dict dict : dictList){
				if(overView.getProjectProgress().equals(dict.getCode())){
					overView.setProjectProgressName(dict.getName());
					overView.setSort(dict.getSort());
				}
			}
		}
		overViewList = sort(overViewList);
		return overViewList;
	}
	
	
	private String divide(Long divide,Long divided){
		 NumberFormat numberFormat = NumberFormat.getInstance();    
		 numberFormat.setMaximumFractionDigits(4);  	  
		 String result = numberFormat.format((float) divide / (float) divided);  
		 return result;
	}
	
	private List<SopProjectAnalysis> sort(List<SopProjectAnalysis> sortList){
		//排序
        Collections.sort(sortList, new Comparator<SopProjectAnalysis>(){
                @Override
                public int compare(SopProjectAnalysis s1, SopProjectAnalysis s2) {
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
