package com.galaxyinternet.model.systemMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.galaxyinternet.framework.core.model.PagableEntity;
import com.galaxyinternet.framework.core.utils.DateUtil;

public class SystemMessage extends PagableEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	com.galaxyinternet.framework.cache.Cache cache;
	private Long id;

	private String osType;

	private Byte isNowSend;

	private Long sendTime;

	private String sendStatus;

	private Byte isDel;

	private Long createId;

	private Long createTime;

	private Long updateId;

	private Long updateTime;

	private Long upgradeTime;

	private String messageContent;

	private String startTime;

	private String endTime;

	private String userStr;

	private String sendTimeStr;

	private String createTimeStr;

	private String messageStatusStr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType == null ? null : osType.trim();
	}

	public Byte getIsNowSend() {
		return isNowSend;
	}

	public void setIsNowSend(Byte isNowSend) {
		this.isNowSend = isNowSend;
	}

	public Long getSendTime() {
		return sendTime;
	}

	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
		if ( sendTime != null) {
			sendTimeStr = DateUtil.transferLongToDate("yyyy-MM-dd", sendTime);
		}
		
	}

	public String getSendStatus() {

		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus == null ? null : sendStatus.trim();
		if (sendStatus != null && messageStatusStr == null) {
			if (sendStatus.equals("messageStatus:1")) {
				messageStatusStr = "未发送";
			} else if (sendStatus.equals("messageStatus:2")) {
				messageStatusStr = "已发送";
			} else if (sendStatus.equals("messageStatus:3")) {
				this.messageStatusStr = "已关闭";
			}
		}
	}

	public Byte getIsDel() {
		return isDel;
	}

	public void setIsDel(Byte isDel) {
		this.isDel = isDel;
	}

	public Long getCreateId() {
		return createId;
		
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	/*	if (createId != null) {
			Object obj= cache.hget("galaxy_user:"+createId, "realName");
			userStr =null==obj?"":(String)obj;
		}
		*/
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpgradeTime() {
		return upgradeTime;
	}

	public void setUpgradeTime(Long upgradeTime) {
		this.upgradeTime = upgradeTime;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent == null ? null : messageContent.trim();
	}

	public String getUserStr() {
		if (userStr == null && createId != null) {
			userStr = (String) cache.hget("galaxy_user:"+createId, "realName");
		}

		return userStr;
	}

	public void setUserStr(String userStr) {
		this.userStr = userStr;
	}

	public String getSendTimeStr() {
		if (sendTimeStr == null && sendTime != null) {
			sendTimeStr = DateUtil.transferLongToDate("yyyy-MM-dd HH:mm", sendTime);
		}
		return sendTimeStr;
	}

	public void setSendTimeStr(String sendTimeStr) {
		if (sendTimeStr == null && sendTime != null) {
			sendTimeStr = DateUtil.transferLongToDate("yyyy-MM-dd HH:mm", sendTime);
		}
		this.sendTimeStr = sendTimeStr;
	}

	public String getCreateTimeStr() {
		if (createTimeStr == null && createdTime != null) {
			createTimeStr = DateUtil.transferLongToDate("yyyy-MM-dd HH:mm", createdTime);
		}
		return createTimeStr;
	}

	public void setCreateTimeStr(String sendTimeStr) {
		if (sendTimeStr == null && createdTime != null) {
			sendTimeStr = DateUtil.transferLongToDate("yyyy-MM-dd HH:mm", createdTime);
		}
		this.sendTimeStr = sendTimeStr;
	}

	public String getMessageStatusStr() {
		if (sendStatus != null && messageStatusStr == null) {
			if (sendStatus.equals("messageStatus:1")) {
				messageStatusStr = "未发送";
			} else if (sendStatus.equals("messageStatus:2")) {
				messageStatusStr = "已发送";
			} else if (sendStatus.equals("messageStatus:3")) {
				this.messageStatusStr = "已关闭";
			}
		}
		return messageStatusStr;
	}

	public void setMessageStatusStr(String messageStatusStr) {
		this.messageStatusStr = messageStatusStr;
	}
	
    @Override
    public void setCreatedTime(Long createdTime) {
    	this.createdTime = createdTime;
    	if(createdTime != null){
    		this.createTimeStr = DateUtil.transferLongToDate("yyyy-MM-dd HH:mm", createdTime); 
    	}
    }

	public String getStartTime() {
		if (startTime != null && startTime.length() == 10) {
			startTime = startTime + " 00:00:00";
		}
		return startTime;
	}

	public void setStartTime(String startTime) {
		if (startTime != null && startTime.length() == 10) {
			startTime = startTime + " 00:00:00";
		}
		this.startTime = startTime;
	}

	public String getEndTime() {
		if (endTime != null && endTime.length() == 10) {
			endTime = endTime + " 23:59:59";
		}
		return endTime;
	}

	public void setEndTime(String endTime) {
		if (endTime != null && endTime.length() == 10) {
			endTime = endTime + " 23:59:59";
		}
		this.endTime = endTime;
	}
}