package com.galaxyinternet.scheduling;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.galaxyinternet.common.dictEnum.DictUtil;
import com.galaxyinternet.common.dictEnum.RoleEnum;
import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.framework.cache.Cache;
import com.galaxyinternet.framework.core.constants.Constants;
import com.galaxyinternet.framework.core.exception.BusinessException;
import com.galaxyinternet.framework.core.thread.GalaxyThreadPool;
import com.galaxyinternet.model.project.MeetingScheduling;
import com.galaxyinternet.service.UserRoleService;
import com.tencent.xinge.XGPush;

@Component("pushMessageTask")
public class PushMessageTask extends BaseGalaxyTask{
	private final static Logger logger = LoggerFactory.getLogger(PushMessageTask.class);
	@Autowired
	Cache cache;
	
	@Autowired
	private UserRoleService userRoleService;
	
	//深度为50
	public static final int SEGMENT = 50;
	
	@Override
	protected void executeInteral() throws BusinessException {
		synchronized (this) {
			final long current = System.currentTimeMillis();
			//从redis获取已经排期的会议
			List<Object> pushList = cache.getRedisQuenOBJ(Constants.PUSH_MESSAGE_LIST);
			if(pushList == null){
				return;
			}
			//指定深度对从redis取的集合进行多线程处理
			int size = pushList.size();
			int pageNum = size % SEGMENT == 0 ? size / SEGMENT : size / SEGMENT + 1;
			for (int li = 0; li < pageNum; li++) {
				int startpos = li * SEGMENT;
				int endpos = startpos + SEGMENT;
				if (endpos > size) {
					endpos = size;
				}
				//分段一个线程处理一批
				final List<Object> subList = pushList.subList(startpos, endpos);
				//多线程处理任务
				GalaxyThreadPool.getExecutorService().execute(new Runnable() {
					@Override
					public void run() {
						XGPush xinge = XGPush.getInstance();
						for(Object obj:subList){
							try{
								MeetingScheduling meeting = (MeetingScheduling) obj;
								List<String> tempUsers = null;
								//项目启动时因定时加载会报空-则非空判断
								if(DictEnum.meetingType.CEO评审.getCode().equals(meeting.getMeetingType())){
									tempUsers = PushUsersCache.getCache(RoleEnum.CEOHHR.getName());
								}else{
									tempUsers = PushUsersCache.getCache(RoleEnum.DSZCEOHHR.getName());
								}
								if(StringUtils.isEmpty(tempUsers)){
									continue;
								}
								//如果当前时间已经过了过会时间不推送消息
								Timestamp startMeetingTime = meeting.getReserveTimeStart();
								Long start = startMeetingTime.getTime();
								if(current - start >= 0){
									continue;
								}
								List<String> sendUsers = new ArrayList<String>();
								sendUsers.add(meeting.getCreateId());
								sendUsers.addAll(tempUsers);
								//小于30分钟进行推送|加上1分钟左右的误差1800000+60000
								if(start - current > 1800000 - 60000 && start - current < 1800000 + 60000){
									cache.removeRedisSetOBJ(Constants.PUSH_MESSAGE_LIST, meeting);
									xinge.pushAccountList(sendUsers,"会议通知", meeting.getProjectName()+"项目的【"+DictUtil.getMeetingType(meeting.getMeetingType())+"】，30分钟后即将开始，请您安排好工作准时参加。");
									logger.info(meeting.getProjectName()+"项目的【"+DictUtil.getMeetingType(meeting.getMeetingType())+"】，30分钟后即将开始，请您安排好工作准时参加。");
								}
							}catch(Exception e){
								logger.error("推送IOS消息失败!"+e.getMessage());
							}
						}
					}
				});
			}
		}
		
	}

}
