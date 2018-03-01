package com.galaxyinternet.model.systemMessage;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class SystemMessage extends PagableEntity  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus == null ? null : sendStatus.trim();
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
}