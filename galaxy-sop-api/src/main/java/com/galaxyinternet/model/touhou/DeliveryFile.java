package com.galaxyinternet.model.touhou;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class DeliveryFile extends PagableEntity {
	private static final long serialVersionUID = 1L;

    private Long deliveryId;

    private Long fileId;

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
}