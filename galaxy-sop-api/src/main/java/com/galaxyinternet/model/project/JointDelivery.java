package com.galaxyinternet.model.project;

import java.math.BigDecimal;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class JointDelivery extends PagableEntity{
	private static final long serialVersionUID = 1L;


    private String  deliveryType;

    private String deliveryName;

    private BigDecimal deliveryAmount;

    private BigDecimal deliveryShareRatio;

    private Long projectId;

    private Long createUid;

    private Long createTime;

    private Long updateTime;
   
    public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName == null ? null : deliveryName.trim();
    }

    public BigDecimal getDeliveryAmount() {
        return deliveryAmount;
    }

    public void setDeliveryAmount(BigDecimal deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }

    public BigDecimal getDeliveryShareRatio() {
        return deliveryShareRatio;
    }

    public void setDeliveryShareRatio(BigDecimal deliveryShareRatio) {
        this.deliveryShareRatio = deliveryShareRatio;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}