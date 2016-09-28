package com.galaxyinternet.chart.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.galaxyinternet.dao.chart.SopDataBriefingDao;
import com.galaxyinternet.dao.chart.SopProjectAnalysisDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.model.chart.SopCharts;
import com.galaxyinternet.model.department.Department;
import com.galaxyinternet.model.dict.Dict;
import com.galaxyinternet.model.project.Project;
import com.galaxyinternet.model.user.User;
import com.galaxyinternet.service.DepartmentService;
import com.galaxyinternet.service.DictService;
import com.galaxyinternet.service.UserService;
import com.galaxyinternet.service.chart.SopProjectAnalysisService;
import com.galaxyinternet.utils.MathUtils;

@Service("analysisService")
public class SopProjectAnalysisServiceImpl implements SopProjectAnalysisService {

	@Autowired
	private DictService dictService;
	@Autowired
	private SopProjectAnalysisDao analysisDao;
	@Autowired
	private SopDataBriefingDao dataBriefingDao;
	@Autowired
	private UserService userService;
	@Autowired
	private DepartmentService departmentService;
	
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
		Long totalCount = analysisDao.selectCount(query);
		//获取字典
		List<Dict> dictList = dictService.selectByParentCode("projectProgress");	
		List<SopCharts> overViewList = analysisDao.selectOverView(query);
		
		
		
		
		for(SopCharts overView : overViewList){
			if(overView.getProjectCount()==null){
				overView.setProjectCount(0L);
			}
			//计算阶段项目比率
			String projectRate;
			if(totalCount != 0){
				projectRate = MathUtils.calculate(overView.getProjectCount(),totalCount, "/", 4);
				projectRate = String.valueOf(Float.parseFloat(projectRate) * 100);
			}else{
				projectRate = "0.00";
			}
			
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



	/**
	 * 项目日期，事业部，类型，投资经理分组环比增长率
	 * @param query
	 * @return
	 */
	@Override
	public Page<SopCharts> queryRiseRateGroup(SopCharts query,Pageable pageable) {
		// TODO Auto-generated method stub
		Project pQuery = new Project();
		pQuery.setProjectType(query.getProjectType());
		pQuery.setProjectDepartid(query.getDepartmentId());
		pQuery.setStartTime(query.getStartTime());
		pQuery.setEndTime(query.getEndTime());
		Page<SopCharts> chartsPage = analysisDao.selectCountGroupAll(query,pageable);
		List<User> userList = getUser(chartsPage.getContent());
		List<Department> departmentList = departmentService.queryAll();
		//上个数据对比集合
		Map<String,SopCharts> preMap = new HashMap<String,SopCharts>();
		for(SopCharts sopCharts : chartsPage.getContent()){
			
			//计算环比增长率
			String projectType = sopCharts.getProjectType();
			Long departmentId = sopCharts.getDepartmentId();
			String projectDate = sopCharts.getProjectDate();
			Long createUid = sopCharts.getCreateUid();
			//通过 （projectType_departmentId_projectDate_createUid）标记preMap的key
			String key = projectType+"_"+departmentId.toString()+"_"+createUid.toString();
			SopCharts preCharts = preMap.get(key);
			if(preCharts == null){
				//写这句代码是因为环比增长在没有比较数据的情况下会有其他显示形式.
				sopCharts.setRiseRate(null);
			}else{
				//计算环比
				Long fall  = Long.parseLong(MathUtils.calculate(sopCharts.getProjectCount(), preCharts.getProjectCount(), "-", 2));
				float rate = Float.parseFloat(MathUtils.calculate(fall, preCharts.getProjectCount(), "/", 4)) * 100;
				sopCharts.setRiseRate(String.valueOf(rate));
			}
			//计算完成后
			preMap.put(key, sopCharts);
			//设置createUname,
			for(User user : userList){
				if(sopCharts.getCreateUid()!=null){
					if(user.getId().intValue() == sopCharts.getCreateUid().intValue()){
						sopCharts.setCreateUname(user.getRealName());
						break;
					}
				}	
			}
			//设置departmentName
			for(Department department : departmentList){
				if(sopCharts.getDepartmentId()!=null){
					if(department.getId().intValue() == sopCharts.getDepartmentId().intValue()){
						sopCharts.setDepartmentName(department.getName());
						break;
					}
				}
			}
		}
		return chartsPage;
	}
	
	private List<User> getUser(List<SopCharts> chartsList){
		User user = new User();
		List<Long> ids = new ArrayList<Long>();	
		for(SopCharts sopCharts : chartsList){
			if(sopCharts.getCreateUid()!=null && !ids.contains(sopCharts.getCreateUid())){
				ids.add(sopCharts.getCreateUid());
			}	
		}
		user.setIds(ids);
		if(ids.size() > 0){
			return userService.queryList(user);
		}
		return null;
	}




	@Override
	public List<SopCharts> queryInvestmentGroupDate(SopCharts query) {
		// TODO Auto-generated method stub
		return analysisDao.selectInvestmentGroupDate(query);
	}




	@Override
	public List<SopCharts> queryPostAnalysis(SopCharts query) {
		// TODO Auto-generated method stub
		List<SopCharts> retList;
		if(query.getDepartmentId()!=null){
			retList = analysisDao.searchPostAnalysisByHhr(query);
		}else{
			retList = analysisDao.searchPostAnalysis(query);
		} 
		return retList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
