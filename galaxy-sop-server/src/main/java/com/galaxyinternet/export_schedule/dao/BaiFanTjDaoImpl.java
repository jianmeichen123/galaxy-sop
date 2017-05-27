package com.galaxyinternet.export_schedule.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.galaxyinternet.export_schedule.model.BaiFanTj;
import com.galaxyinternet.export_schedule.model.ScheduleInfo;
import com.galaxyinternet.framework.core.dao.impl.BaseDaoImpl;
import com.galaxyinternet.framework.core.exception.DaoException;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.query.Query;
import com.galaxyinternet.framework.core.utils.BeanUtils;

@Repository("baiFanTjDao")
public class BaiFanTjDaoImpl extends BaseDaoImpl<BaiFanTj, Long> implements BaiFanTjDao{


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
	public List<BaiFanTj> selectAllUserSchedule(Map<String, Object> params) {
		try {
			return sqlSessionTemplate.selectList(getSqlName("selectAllUserSchedule"), params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询出错！语句：%s", getSqlName("selectAllUserSchedule")), e);
		}
	}

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
	public List<Long> selectScheduleIds(Map<String, Object> params) {
		try {
			return sqlSessionTemplate.selectList(getSqlName("selectScheduleIds"), params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询出错！语句：%s", getSqlName("selectScheduleIds")), e);
		}
	}

	/**
	 * schedule_info 表条件下的  created_id 集合
	 * @param params
	 *            <br>params.type = 2                拜访类别
	 *            <br>params.bqStartTime   拜访开始的 起始 时间
	 *            <br>params.bqEndTime     拜访开始的 截至 时间
	 *            
	 * @return 
	 * @see
	 * 			<code>
				</code>
	 *@version 1.0.0
	 */
	public List<Long> selectScheduleCuids(Map<String, Object> params) {
		try {
			return sqlSessionTemplate.selectList(getSqlName("selectScheduleCuids"), params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询出错！语句：%s", getSqlName("selectScheduleCuids")), e);
		}
	}
	
	/**
	 * sop_interview_record 表 :  用户 已完成的拜访统计数据
	 * @param params
	 *            <br>params.scheduleIds   schedule_info 表条件下的id 集合
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
					GROUP BY
						created_id , schedule_id 
					) a
				GROUP BY
					created_id
				</code>
	 *@version 1.0.0
	 */
	public List<BaiFanTj> selectCompleteUserSchedule(Map<String, Object> params) {
		try {
			return sqlSessionTemplate.selectList(getSqlName("selectCompleteUserSchedule"), params);
		} catch (Exception e) {
			throw new DaoException(String.format("查询出错！语句：%s", getSqlName("selectCompleteUserSchedule")), e);
		}
	}


	
}
