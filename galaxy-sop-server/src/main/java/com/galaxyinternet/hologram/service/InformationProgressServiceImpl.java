package com.galaxyinternet.hologram.service;

import com.aliyun.oss.ServiceException;
import com.galaxyinternet.dao.hologram.InformationProgressDao;
import com.galaxyinternet.dao.hologram.InformationTitleDao;
import com.galaxyinternet.dao.hologram.InformationTitleRelateDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.framework.core.thread.GalaxyThreadPool;
import com.galaxyinternet.hologram.util.ProgressRecursiveTask;
import com.galaxyinternet.model.hologram.InformationProgress;
import com.galaxyinternet.service.hologram.InformationProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

@Service("com.galaxyinternet.service.hologram.InformationProgressService")
public class InformationProgressServiceImpl extends BaseServiceImpl<InformationProgress> implements InformationProgressService {


	@Autowired
	private InformationProgressDao informationProgressDao;

	@Autowired
	private InformationTitleRelateDao informationTitleRelateDao;

	@Autowired
	private InformationTitleDao informationTitleDao;


	@Override
	protected BaseDao<InformationProgress, Long> getBaseDao() {
		return this.informationProgressDao;
	}




	public void usersAllReportProgressOfPro(Long uid, final Long proId){
		Long btime = System.currentTimeMillis();
		InformationProgress informationProgress;
		try {
			ForkJoinPool pool = GalaxyThreadPool.getForkJoinPool();

			informationProgress = new InformationProgress();
			informationProgress.setUid(uid);
			informationProgress.setProjectId(proId);

			ProgressRecursiveTask task = new ProgressRecursiveTask(informationProgress,null,proId);
			ForkJoinTask<InformationProgress> result = pool.submit(task);
			result.get();
		} catch (Exception e) {
			throw new ServiceException("err usersAllReportProgressOfPro : ", e);
		}

		System.err.println( "===========  用时 ： "  +  (System.currentTimeMillis() -  btime));//444  //352
		System.err.println("=============");
	}
	public Double getProgressByReportCode(String code,final Long proId){
		double result = BigDecimal.ZERO.doubleValue();
		try {
			Map<String, Set<Long>> titletype_titleIds = CacheOperationServiceImpl.code_titletype_titleIds.get(code);

			Set<Long> project_ids = titletype_titleIds.get("project");
			Set<Long> result_ids = titletype_titleIds.get("result");
			Set<Long> listdata_ids = titletype_titleIds.get("listdata");
			Set<Long> fixedtable_ids = titletype_titleIds.get("fixedtable");
			Set<Long> file_ids = titletype_titleIds.get("file");

			int project_completed = projectCompletedNum(project_ids, proId);
			int result_completed = resultCompletedNum(result_ids, proId);
			int listdata_completed = listdataCompletedNum(listdata_ids, proId);
			int fixedtable_completed = fixedtableCompletedNum(fixedtable_ids, proId);
			int file_completed = fileCompletedNum(file_ids, proId);

			MathContext mc = new MathContext(5);
			BigDecimal b1 = new BigDecimal((project_completed + result_completed + listdata_completed + fixedtable_completed + file_completed) + "");
			BigDecimal b2 = new BigDecimal(Integer.toString(CacheOperationServiceImpl.code_titleNum.get(code)));
			result = b1.divide(b2,mc).doubleValue();
		} catch (Exception e) {
			throw new ServiceException("err getProgressByReportCode : ", e);
		}
		return result;
	}



	public Integer projectCompletedNum(Set<Long> ids,Long projectId){
		return ids==null?0:ids.size();
	}
	public Integer resultCompletedNum(Set<Long> ids,Long projectId){
		if(ids == null || ids.isEmpty()){
			return 0;
		}

		Map<String, Object> params = new HashMap<String,Object>();
		params.put("titleIds",ids);
		params.put("projectId",projectId);
		params.put("notAllNUll",true);
		//List<InformationTitle> titleList = informationTitleDao.selectTitleOfResults(params);
		Integer count = informationTitleDao.selectCountForTitleOfResults(params);

		return count==null?0:count;
	}
	public Integer listdataCompletedNum(Set<Long> ids,Long projectId){
		if(ids == null || ids.isEmpty()){
			return 0;
		}

		Map<String, Object> params = new HashMap<String,Object>();
		params.put("titleIds",ids);
		params.put("projectId",projectId);
		params.put("notAllNUll",true);
		Integer count = informationTitleDao.selectCountForTitleOfListdata(params);

		return count==null?0:count;
	}
	public Integer fixedtableCompletedNum(Set<Long> ids,Long projectId){
		if(ids == null || ids.isEmpty()){
			return 0;
		}

		Map<String, Object> params = new HashMap<String,Object>();
		params.put("titleIds",ids);
		params.put("projectId",projectId);
		params.put("notAllNUll",true);
		Integer count = informationTitleDao.selectCountForTitleOfFixedTable(params);

		return count==null?0:count;
	}
	public Integer fileCompletedNum(Set<Long> ids,Long projectId){
		if(ids == null || ids.isEmpty()){
			return 0;
		}

		Map<String, Object> params = new HashMap<String,Object>();
		params.put("titleIds",ids);
		params.put("projectId",projectId);
		params.put("notAllNUll",true);
		Integer count = informationTitleDao.selectCountForTitleOfFile(params);

		return count==null?0:count;
	}




}
