package com.galaxyinternet.soptask.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.galaxyinternet.bo.SheduleCommon;
import com.galaxyinternet.bo.SopUserScheduleBo;
import com.galaxyinternet.dao.soptask.SopUserScheduleDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
import com.galaxyinternet.framework.core.model.Page;
import com.galaxyinternet.framework.core.service.impl.BaseServiceImpl;
import com.galaxyinternet.model.soptask.SopUserSchedule;
import com.galaxyinternet.service.SopUserScheduleService;

@Service("com.galaxyinternet.service.SopUserScheduleService")
public class SopUserScheduleServiceImpl extends
		BaseServiceImpl<SopUserSchedule> implements SopUserScheduleService {

	@Autowired
	private SopUserScheduleDao sopUserScheduleDao;

	@Override
	protected BaseDao<SopUserSchedule, Long> getBaseDao() {
		// TODO Auto-generated method stub
		return this.sopUserScheduleDao;
	}

	/***
	 * 获取我的日程前三条信息 type: 1:前三条数据?更多 currentTime 当前系统时间
	 * @throws ParseException 
	 */
	@Override
	public List<SopUserScheduleBo> selectSopUserScheduleByTime(Long userId,
			Long currentTime, Integer type) throws ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 获取大于当前系统时间的数据信息
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("itemDate", new Timestamp(currentTime));
		params.put("type", type);
		List<SopUserSchedule> list = sopUserScheduleDao.selectSopUserScheduleByTime(params);
		// 取数据的前三条信息并进行时间判断
		List<SopUserScheduleBo> sopUserScheduleBoList = new ArrayList<SopUserScheduleBo>();
		for (SopUserSchedule sopUser:list) {
			SopUserScheduleBo sopbo = new SopUserScheduleBo();
			String message = "";
			String str[] = sopUser.getItemDate().toString().split(" ");
	        Date date = sdf.parse(str[0]);
			if (type == 1) {
				long l = date.getTime() - currentTime;
				long day = l / (24 * 60 * 60 * 1000);
				/**
				long hour = (l / (60 * 60 * 1000) - day * 24);
				long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
				long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
				String messageContent = hour + "小时" + min + "分" + s + "秒后";
				message += String.valueOf(day) + "天";
				if (day < 1) {
					message = "";
				}**/
				if (day == 0) {
					message = "今天,";
				}
				if (day == 1) {
					message = "明天,";
				}
				if (day == 2) {
					message = "后天,";
				}
				if (day > 2) {
					message = str[0]+",";
				}
				sopbo.setTimeTask(message+ sopUser.getContent());
			}
			sopbo.setItemType(sopUser.getItemType());
			sopbo.setItemOrder(sopUser.getItemOrder());
			sopbo.setUserId(sopUser.getUserId());
			sopbo.setCreatedTime(sopUser.getCreatedTime());
			sopUserScheduleBoList.add(sopbo);
		}
		return sopUserScheduleBoList;

	}

	@Override
	public List<SheduleCommon> scheduleListByDate(Long userId) {
		// TODO Auto-generated method stub
		// 获取大于当前系统时间的数据信息
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		List<SheduleCommon> list = sopUserScheduleDao.selectScheduleListByDate(params);
		for(SheduleCommon common:list){
			String month=common.getMonths();
			params.put("itemDate", month);
			List<SopUserSchedule> soplist=sopUserScheduleDao.selectSopUserScheduleDesc(params);
			common.setList(soplist);
		}
		return list;
	}

	/**
	 * 分页模糊查询
	 */
	@Override
	public Page<SopUserSchedule> scheduleListByName(SopUserScheduleBo query, Pageable pageable) {
		Page<SopUserSchedule> pageList = sopUserScheduleDao.scheduleListByName(query,pageable);
		
		return pageList;
	}



}
