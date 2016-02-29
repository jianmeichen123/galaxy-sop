package com.galaxyinternet.soptask.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.bo.SopUserScheduleBo;
import com.galaxyinternet.dao.soptask.SopUserScheduleDao;
import com.galaxyinternet.framework.core.dao.BaseDao;
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
	 */
	@Override
	public List<SopUserScheduleBo> selectSopUserScheduleByTime(Long userId,
			Long currentTime, Integer type) {
		// TODO Auto-generated method stub
		// 获取大于当前系统时间的数据信息
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("createdTime", currentTime);
		params.put("type", type);
		List<SopUserSchedule> list = sopUserScheduleDao.selectSopUserScheduleByTime(params);
		// 取数据的前三条信息并进行时间判断
		List<SopUserScheduleBo> sopUserScheduleBoList = new ArrayList<SopUserScheduleBo>();
		for (SopUserSchedule sopUser:list) {
			SopUserScheduleBo sopbo = new SopUserScheduleBo();
			String message = "";
			if (type == 1) {
				long l = sopUser.getCreatedTime() - currentTime;
				long day = l / (24 * 60 * 60 * 1000);
				long hour = (l / (60 * 60 * 1000) - day * 24);
				long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
				long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
				String messageContent = hour + "小时" + min + "分" + s + "秒后";
				message += String.valueOf(day) + "天";
				if (day < 1) {
					message = "";
				}
				if (day == 1) {
					message = "明天";
				}
				if (day == 2) {
					message = "后天";
				}
				sopbo.setTimeTask(message + messageContent);
			}
			sopbo.setItemType(sopUser.getItemType());
			sopbo.setItemOrder(sopUser.getItemOrder());
			sopbo.setUserId(sopUser.getUserId());
			sopbo.setCreatedTime(sopUser.getCreatedTime());
			sopUserScheduleBoList.add(sopbo);
		}
		return sopUserScheduleBoList;

	}

}
