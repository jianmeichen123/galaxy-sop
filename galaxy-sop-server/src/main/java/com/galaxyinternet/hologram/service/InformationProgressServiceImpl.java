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
import com.galaxyinternet.project.controller.ProjectProgressController;
import com.galaxyinternet.service.hologram.InformationProgressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

@Service("com.galaxyinternet.service.hologram.InformationProgressService")
public class InformationProgressServiceImpl extends BaseServiceImpl<InformationProgress> implements InformationProgressService {

	final Logger logger = LoggerFactory.getLogger(ProjectProgressController.class);


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


	@Override
	public void threadForUpdate(final Long uid, final Long proId){
		GalaxyThreadPool.getExecutorService().execute(new Runnable(){
			public void run(){
				updateUsersAllReportProgressOfPro(uid,proId);
			}
		});
	}

	@Override
	public void updateUsersAllReportProgressOfPro(Long uid, final Long proId){
		int reback = 3;
		try {
            InformationProgress informationProgress = new InformationProgress();
            informationProgress.setUid(uid);
            informationProgress.setProjectId(proId);

            ForkJoinPool pool = GalaxyThreadPool.getForkJoinPool();
            ProgressRecursiveTask task = new ProgressRecursiveTask(informationProgress,null,proId);
            ForkJoinTask<InformationProgress> result = pool.submit(task);

            InformationProgress query = new InformationProgress();
            query.setProjectId(proId);
            List<InformationProgress> checkM = informationProgressDao.selectList(query);
			if(null == checkM || checkM.isEmpty() || checkM.size() > 1){
				while(reback != 0){
					checkM = informationProgressDao.selectList(query);
					if(null == checkM || checkM.isEmpty() || checkM.size() > 1){
						reback -= 1;
						Thread.currentThread().sleep(10);
					}else{
                        query = checkM.get(0);
						break;
					}
				}
			}else{
                query = checkM.get(0);
			}

            result.get();
            if(null == query.getId()){
                throw new ServiceException("err updateUsersAllReportProgressOfPro : 修改失败");
            }else{
                informationProgress.setId(query.getId());
                informationProgressDao.updateById(informationProgress);
            }
        } catch (Exception e) {
			throw new ServiceException("err updateUsersAllReportProgressOfPro : 修改失败 ", e);
		}
	}


	public InformationProgress initUsersAllReportProgressOfPro(Long uid, final Long proId){
		InformationProgress informationProgress = new InformationProgress();
		informationProgress.setUid(uid);
		informationProgress.setProjectId(proId);
		try {
			ForkJoinPool pool = GalaxyThreadPool.getForkJoinPool();

			ProgressRecursiveTask task = new ProgressRecursiveTask(informationProgress,null,proId);
			ForkJoinTask<InformationProgress> result = pool.submit(task);


			InformationProgress query = new InformationProgress();
			//query.setUid(uid);
			query.setProjectId(proId);
			List<InformationProgress> checkM = informationProgressDao.selectList(query);

            result.get();
			if(null == checkM || checkM.isEmpty()){
				informationProgressDao.insert(informationProgress);
			}else if(checkM.size() > 1){
				informationProgressDao.delete(query);
				informationProgressDao.insert(informationProgress);
			}else{
				informationProgress = checkM.get(0);
			}
		} catch (Exception e) {
			throw new ServiceException("err initUsersAllReportProgressOfPro : 初始化失败 ", e);
		}

		return informationProgress;
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
			Set<Long> resultGrage_ids = titletype_titleIds.get("resultGrage");

			int project_completed = projectCompletedNum(project_ids, proId);
			int result_completed = resultCompletedNum(result_ids, proId);
			int listdata_completed = listdataCompletedNum(listdata_ids, proId);
			int fixedtable_completed = fixedtableCompletedNum(fixedtable_ids, proId);
			int file_completed = fileCompletedNum(file_ids, proId);
			int resultGrage_completed = resultGrageNum(resultGrage_ids, proId);

			MathContext mc = new MathContext(5);
			BigDecimal b1 = new BigDecimal((project_completed + result_completed + listdata_completed + fixedtable_completed + file_completed + resultGrage_completed) + "");
			BigDecimal b2 = new BigDecimal(Integer.toString(CacheOperationServiceImpl.code_titleNum.get(code)));
			result = b1.divide(b2,mc).doubleValue();

			logger.debug(String.format("计算进度 code：%s   \n  totleNum : %d , result : %f  \n"+
					"project_completed : %d , result_completed : %d ,  listdata_completed : %d ,  "+
					"fixedtable_completed : %d ,  file_completed : %d ,  resultGrage_completed : %d" ,
					code,CacheOperationServiceImpl.code_titleNum.get(code),result,
					project_completed , result_completed , listdata_completed ,
					fixedtable_completed , file_completed , resultGrage_completed)
			);
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

		//debug used
		String code = ProgressRecursiveTask.tl.get();
		if(null != code && code.equals("NO")){
			System.err.println("============");
			logger.debug("result_ids : " + Arrays.toString(ids.toArray()));
		}//end

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
	public Integer resultGrageNum(Set<Long> ids,Long projectId){
		if(ids == null || ids.isEmpty()){
			return 0;
		}

		Map<String, Object> params = new HashMap<String,Object>();
		params.put("titleRelateIds",ids);
		params.put("projectId",projectId);
		params.put("notAllNUll",true);
		Integer count = informationTitleDao.selectCountForRelateOfGrade(params);
		Integer count2 = informationTitleDao.selectCountForRelateOfGrade2(params);

		return (count==null?0:count) + (count2==null?0:count2);
	}





}
