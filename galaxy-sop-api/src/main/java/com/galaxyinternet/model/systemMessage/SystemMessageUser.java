package com.galaxyinternet.model.systemMessage;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class SystemMessageUser extends PagableEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long messageId;

    private String messageOs;

    private Long userId;

    private int isRead;

    private int isDel;

    private Long createId;

    private Long createTime;

    private Long updateId;

    private Long updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getMessageOs() {
        return messageOs;
    }

    public void setMessageOs(String messageOs) {
        this.messageOs = messageOs == null ? null : messageOs.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
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
}