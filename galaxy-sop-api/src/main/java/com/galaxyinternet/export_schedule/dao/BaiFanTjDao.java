package com.galaxyinternet.export_schedule.dao;

import java.util.List;
import java.util.Map;

import com.galaxyinternet.export_schedule.model.BaiFanTj;
import com.galaxyinternet.framework.core.dao.BaseDao;


public interface BaiFanTjDao extends BaseDao<BaiFanTj, Long>{


	/**
	 * schedule_info 表所有用户的拜访统计数据
	 * @param params
	 *            <br>params.type = 2                拜访类别
	 *            <br>params.bqStartTime   拜访开始的 起始 时间
	 *            <br>params.bqEndTime     拜访开始的 截至 时间
	 *            
	 * @return 
	 * 
	 * @see
	 * 			<code>
	            SELECT
					created_id uid, COUNT(id) usum
				FROM
					schedule_info
				WHERE
					parem.type = 2
					parem.bqStartTime
					parem.bqEndTime
				GROUP BY
					created_id
				</code>
	 *@version 1.0.0
	 */
	List<BaiFanTj> selectAllUserSchedule(Map<String,Object> params);
	
	
	/**
	 * schedule_info 表条件下的id 集合
	 * @param params
	 *            <br>params.type = 2                拜访类别
	 *            <br>params.bqStartTime   拜访开始的 起始 时间
	 *            <br>params.bqEndTime     拜访开始的 截至 时间
	 *            
	 * @return 
	 * @see
	 * 			<code>
	            SELECT
					id
				FROM
					schedule_info
				WHERE
					parem.type = 2
					parem.bqStartTime
					parem.bqEndTime
				</code>
	 *@version 1.0.0
	 */
	List<Long> selectScheduleIds(Map<String,Object> params);
	
	
	/**
	 * sop_interview_record 表 :  用户 已完成的拜访统计数据
	 * @param params
	 *            <br>params.scheduleIds   schedule_info 表条件下的id 集合
	 *            <br>params.bqStartTime   拜访开始的 起始 时间
	 *            <br>params.bqEndTime     拜访开始的 截至 时间
	 *            
	 * @return 
	 * 
	 * @see
	 * 			<code>
	            SELECT created_id uid , COUNT(schedule_id) usum
				FROM
					(
					SELECT 
						created_id, schedule_id
					FROM
						sop_interview_record
					WHERE
						parem.scheduleIds
						parem.bqStartTime
					    parem.bqEndTime
					GROUP BY
						created_id , schedule_id 
					) a
				GROUP BY
					created_id
				</code>
	 *@version 1.0.0
	 */
	List<BaiFanTj> selectCompleteUserSchedule(Map<String,Object> params);
	
	
	
	
	
	
}
