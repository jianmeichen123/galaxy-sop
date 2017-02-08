package com.galaxyinternet.model.idea;

import java.util.Date;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class ZixunFinance extends PagableEntity {

	private static final long serialVersionUID = 1L;

    private Long zixunId;

    private String financeDate;

    private Long financeAmount;

    private String financeUnit;

    private Byte status;

    private Long createUid;

    private Long updatedUid;


    public Long getZixunId() {
        return zixunId;
    }

    public void setZixunId(Long zixunId) {
        this.zixunId = zixunId;
    }

    public String getFinanceDate() {
        return financeDate;
    }

    public void setFinanceDate(String financeDate) {
        this.financeDate = financeDate;
    }

    public Long getFinanceAmount() {
        return financeAmount;
    }

    public void setFinanceAmount(Long financeAmount) {
        this.financeAmount = financeAmount;
    }

    public String getFinanceUnit() {
        return financeUnit;
    }

    public void setFinanceUnit(String financeUnit) {
        this.financeUnit = financeUnit == null ? null : financeUnit.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    public Long getUpdatedUid() {
        return updatedUid;
    }

    public void setUpdatedUid(Long updatedUid) {
        this.updatedUid = updatedUid;
    }

}