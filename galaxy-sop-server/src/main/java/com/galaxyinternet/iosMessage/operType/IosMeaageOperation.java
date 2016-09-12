package com.galaxyinternet.iosMessage.operType;

import com.galaxyinternet.model.operationLog.UrlNumber;
import com.galaxyinternet.platform.constant.PlatformConst;
/**
 * 
 * @Description: 消息提醒规范类型
 *
 */
public enum IosMeaageOperation {
	
	//认领任务
	CLAIM_TASK				("/galaxy/soptask/goClaimtcPage", 						PlatformConst.IOS_TITLE_TASK, "，请您关注"),
	//完成任务
	COMPLETE_TASK			("/galaxy/soptask/updateTaskStatus", 					PlatformConst.IOS_TITLE_TASK, "，请您关注"),
	//完成任务
	SUBMIT_TASK              ("/galaxy/soptask/submitTask",                 		PlatformConst.IOS_TITLE_TASK, "，请您关注"),
	
	XXXXXXXXXXXXXX			("/galaxy/project/updateReserveTime", 	PlatformConst.IOS_TITLE_MEET, "，请您准时参加，如对时间有疑问请联系相关负责人。"),
	ADD_SCHEDULING			("/galaxy/project/updateReserveTime/"+ UrlNumber.one, 	PlatformConst.IOS_TITLE_MEET, "，请您准时参加，如对时间有疑问请联系相关负责人。"),
	UPDATE_SCHEDULING		("/galaxy/project/updateReserveTime/"+ UrlNumber.two, 	PlatformConst.IOS_TITLE_MEET, "，请您准时参加，如对时间有疑问请联系相关负责人。"),
	DELETE_SCHEDULING		("/galaxy/project/updateReserveTime/"+ UrlNumber.three, PlatformConst.IOS_TITLE_MEET, "，请您准时参加，如对时间有疑问请联系相关负责人。");

	private IosMeaageOperation(String uniqueKey, String title, String content) {
		this.uniqueKey = uniqueKey;
		this.title = title;
		this.content = content;
	}


	public static IosMeaageOperation getObject(String uniqueKey) {
		IosMeaageOperation[] types = IosMeaageOperation.values();
		IosMeaageOperation result = null;
		for (IosMeaageOperation type : types) {
			if (type.getUniqueKey()!=null && type.getUniqueKey().trim().length()>0 && uniqueKey.contains(type.getUniqueKey())) {
				result = type;
				break;
			}
		}
		return result;
	}

	
	private String title;
	private String content;
	private String uniqueKey;

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getUniqueKey() {
		return uniqueKey;
	}


}
