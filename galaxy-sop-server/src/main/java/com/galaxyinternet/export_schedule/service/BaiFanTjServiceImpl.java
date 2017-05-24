package com.galaxyinternet.export_schedule.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.export_schedule.dao.BaiFanTjDao;
import com.galaxyinternet.export_schedule.model.BaiFanTj;
import com.galaxyinternet.export_schedule.model.ScheduleInfo;
import com.galaxyinternet.export_schedule.util.ScheduleUtil;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;

@Service("com.galaxyinternet.export_schedule.service.BaiFanTjService")
public class BaiFanTjServiceImpl extends BaseServiceImpl<BaiFanTj> implements BaiFanTjService{

	private final static Logger logger = LoggerFactory.getLogger(BaiFanTjServiceImpl.class);
	
	@Autowired
	private BaiFanTjDao baiFanTjDao;
	
	@Autowired
	private ScheduleUtil scheduleUtil;
	
	
	@Override
	protected BaseDao<BaiFanTj, Long> getBaseDao() {
		return this.baiFanTjDao;
	}
	
	
	/**
	 * 拜访统计图  列表  排序<br>
	 * 统计                           总计划拜访数量、已完成拜访数量
	 * @param info   封装的查询条件
	 * @return 
	 */
	public List<BaiFanTj> exportBaiFanSum(ScheduleInfo info) {
		List<BaiFanTj> results = new ArrayList<BaiFanTj>();
		
		long time1 = System.currentTimeMillis();
		Map<Long,Integer> allSchedule = this.queryAllUserSchedule(info);
		
		long time2 = System.currentTimeMillis();
		List<Long> ids = new ArrayList<>(allSchedule.keySet());
		
		Map<Long, String> id_nameMap =  new HashMap<Long, String>();
		Long departmentId = info.getDepartmentId();
		if(departmentId != null){
			id_nameMap =  scheduleUtil.queryUidNameMap(ids);
		}else{
			id_nameMap = scheduleUtil.queryDeptIdNameMap(ids);
		}
		
		
		long time3 = System.currentTimeMillis();
		Map<Long,Integer> completeSchedule = this.queryCompleteUserSchedule(info);
		
		long time4 = System.currentTimeMillis();
		
		if(allSchedule != null && !allSchedule.isEmpty()){
				
			BaiFanTj abf =null;
			for(Entry<Long, Integer> idsum : allSchedule.entrySet()){
				abf = new BaiFanTj();
				
				abf.setId(idsum.getKey());
				abf.setName(id_nameMap.get(idsum.getKey()));
				abf.setAllSum(idsum.getValue());
				if(completeSchedule.containsKey(idsum.getKey())){
					abf.setCompleteSum(completeSchedule.get(idsum.getKey()));
				}else{
					abf.setCompleteSum(0);
				}
				
				results.add(abf);
			}
		}
		
		if(results!=null && results.size()>1){
			Collections.sort(results, new Comparator<BaiFanTj>() {
				public int compare(BaiFanTj arg0, BaiFanTj arg1) {
					return (arg1.getCompleteSum().intValue() - arg0.getCompleteSum().intValue());
				}
			});
		}
		long time5 = System.currentTimeMillis();
		
		if(logger.isDebugEnabled()){
			if(time5-time4 > 1000 ||time4-time3 > 1000 || time3-time2 > 1000 || time2-time1 > 1000 || time5-time1 > 1000  ){
				logger.error( (time4-time3) + ":" + (time3-time2) +":" + (time2-time1) +":" + (time4-time1) );
			}
		}
		
		return results;
	}
	
	
	
	/**
	 * schedule_info 表所有用户的拜访统计数据
	 * @param 
	 * 		info        :封装的查询条件
	 * 		id_nameMap  :返回数据
	 * @return 
	 */
	public Map<Long,Integer> queryAllUserSchedule(ScheduleInfo info) {
		Map<Long,Integer> id_sum_map = new HashMap<Long,Integer>();
		
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("type", ScheduleUtil.BAI_FAN_TYPE);
		params.put("isProject", info.getIsProject());
		/*params.put("bqStartTime", info.getStartTime());
		params.put("bqEndTime", info.getEndTime());*/
		params.put("bqStartTime", info.getStartTimeFrom());
		params.put("bqEndTime", info.getStartTimeThrough());
		
		List<BaiFanTj> bflist = null;
		
		Long departmentId = info.getDepartmentId();
		if(departmentId != null){
			Map<Long, String> id_nameMap =  scheduleUtil.queryUidNameByDept(departmentId);
			if(id_nameMap!=null && !id_nameMap.isEmpty()){
				params.put("createtUids", id_nameMap.keySet());
				
				bflist = baiFanTjDao.selectAllUserSchedule(params);
				
				if(bflist!=null && !bflist.isEmpty()){
					for(BaiFanTj bf :bflist){
						id_sum_map.put(bf.getUid(), bf.getUsum());
					}
				}
			}
		}else{
			bflist = baiFanTjDao.selectAllUserSchedule(params);
			
			if(bflist !=null && !bflist.isEmpty()){
				List<Long> uids = new ArrayList<Long>();
				for(BaiFanTj bf : bflist){ // uid, usum
					uids.add(bf.getUid());
				}
				
				//获取投资经理   uid-deptid map 
				Map<Long, Long> uId_deptidMap = scheduleUtil.queryAllTzjlDeptMap(uids);
				
				Long dptId = null;
				for(BaiFanTj bf : bflist){ // uid, usum
					dptId = uId_deptidMap.get(bf.getUid());
					if(id_sum_map.containsKey(dptId)){
						id_sum_map.put(dptId, id_sum_map.get(dptId)+bf.getUsum());
					}else{
						id_sum_map.put(dptId, bf.getUsum());
					}
				}
				
			}
		}

		return id_sum_map;
	}

	
	
	/**
	 * schedule_info、sop_interview_record 表 :  用户 已完成的拜访统计数据
	 * @param info   封装的查询条件
	 * @return 
	 */
	public Map<Long,Integer> queryCompleteUserSchedule(ScheduleInfo info) {
		Map<Long,Integer> id_sum_map = new HashMap<Long,Integer>();
		
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("type", ScheduleUtil.BAI_FAN_TYPE);
		params.put("isProject", info.getIsProject());
		params.put("bqStartTime", info.getStartTimeFrom());
		params.put("bqEndTime", info.getStartTimeThrough());
		
		List<BaiFanTj> bflist = null;
		
		Long departmentId = info.getDepartmentId();
		if(departmentId != null){
			Map<Long, String> uid_nameMap =  scheduleUtil.queryUidNameByDept(departmentId);
			if(uid_nameMap!=null && !uid_nameMap.isEmpty()){
				params.put("createtUids", uid_nameMap.keySet());
				List<Long> ids = baiFanTjDao.selectScheduleIds(params);
				
				if(ids!=null && !ids.isEmpty()){
					params = new HashMap<String,Object>();
					params.put("scheduleIds", ids);
					bflist = baiFanTjDao.selectCompleteUserSchedule(params);
					
					if(bflist!=null && !bflist.isEmpty()){
						for(BaiFanTj bf :bflist){
							id_sum_map.put(bf.getUid(), bf.getUsum());
						}
					}
				}
			}
		}else{
			List<Long> ids = baiFanTjDao.selectScheduleIds(params);
			
			if(ids!=null && !ids.isEmpty()){
				params = new HashMap<String,Object>();
				params.put("scheduleIds", ids);
				bflist = baiFanTjDao.selectCompleteUserSchedule(params);
				
				if(bflist!=null && !bflist.isEmpty()){
					
					List<Long> uids = new ArrayList<Long>();
					for(BaiFanTj bf : bflist){ // uid, usum
						uids.add(bf.getUid());
					}
					
					//获取投资经理   uid-deptid map 
					Map<Long, Long> uId_deptidMap = scheduleUtil.queryAllTzjlDeptMap(uids);
					
					Long dptId = null;
					for(BaiFanTj bf : bflist){ // uid, usum
						dptId = uId_deptidMap.get(bf.getUid());
						if(id_sum_map.containsKey(dptId)){
							id_sum_map.put(dptId, id_sum_map.get(dptId)+bf.getUsum());
						}else{
							id_sum_map.put(dptId, bf.getUsum());
						}
					}
				}
				
			}
			
		}	
		
		return id_sum_map;
	}






	
	
	

	
}
