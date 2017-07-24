package com.galaxyinternet.model.hologram;

import com.galaxyinternet.framework.core.model.PagableEntity;
import com.galaxyinternet.framework.core.utils.GSONUtil;

public class InformationTitleRelate extends PagableEntity {
	private static final long serialVersionUID = 1L;

    private Long parentId;

    private String code;

    private String name;

    private Long titleId;

    private Double indexNo;

    private Integer sign;

    private Integer reportType;

    private Integer isValid;

    private Long createdId;

    private Long updatedId;

    
    private InformationTitle informationTitle;
    

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getTitleId() {
        return titleId;
    }

    public void setTitleId(Long titleId) {
        this.titleId = titleId;
    }

    public Double getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Double indexNo) {
        this.indexNo = indexNo;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Long getCreatedId() {
        return createdId;
    }

    public void setCreatedId(Long createdId) {
        this.createdId = createdId;
    }


    public Long getUpdatedId() {
        return updatedId;
    }

    public void setUpdatedId(Long updatedId) {
        this.updatedId = updatedId;
    }

    
    

	public InformationTitle getInformationTitle() {
		return informationTitle;
	}

	public void setInformationTitle(InformationTitle informationTitle) {
		this.informationTitle = informationTitle;
	}

	@Override
	public String toString() {
		return GSONUtil.toJson(this);
	}
    
    
    

}
