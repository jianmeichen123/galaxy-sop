package com.galaxyinternet.model.idea;

import java.util.Date;

import com.galaxyinternet.framework.core.model.PagableEntity;

public class IdeaZixun extends PagableEntity {

	private static final long serialVersionUID = 1L;

    private String code;

    private String companyName;

    private String companyBtime;

    private String companyField;

    private String companyCuser;

    private String companyAddress;

    private String companyUrl;

    private Long departmentId;

    private String remark;

    private String detailInfo;

    private Byte status;

    private Long updatedUid;

    private Long createUid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getCompanyBtime() {
        return companyBtime;
    }

    public void setCompanyBtime(String companyBtime) {
        this.companyBtime = companyBtime;
    }

    public String getCompanyField() {
        return companyField;
    }

    public void setCompanyField(String companyField) {
        this.companyField = companyField == null ? null : companyField.trim();
    }

    public String getCompanyCuser() {
        return companyCuser;
    }

    public void setCompanyCuser(String companyCuser) {
        this.companyCuser = companyCuser == null ? null : companyCuser.trim();
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress == null ? null : companyAddress.trim();
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl == null ? null : companyUrl.trim();
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo == null ? null : detailInfo.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }


    public Long getUpdatedUid() {
        return updatedUid;
    }

    public void setUpdatedUid(Long updatedUid) {
        this.updatedUid = updatedUid;
    }


    public Long getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }
}