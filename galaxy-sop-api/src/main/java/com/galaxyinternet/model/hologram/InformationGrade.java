package com.galaxyinternet.model.hologram;

import com.galaxyinternet.framework.core.model.PagableEntity;
import com.galaxyinternet.framework.core.utils.GSONUtil;

public class InformationGrade extends PagableEntity {
	private static final long serialVersionUID = 1L;

	private Long titleRelateId;

    private Long subId;

    private String scoreExplain;

    private Double scoreMax;

    private Integer isScore;

    private Integer isValid;

    private Long createId;

    private Long updateId;


    public Long getTitleRelateId() {
        return titleRelateId;
    }

    public void setTitleRelateId(Long titleRelateId) {
        this.titleRelateId = titleRelateId;
    }

    public Long getSubId() {
        return subId;
    }

    public void setSubId(Long subId) {
        this.subId = subId;
    }

    public String getScoreExplain() {
        return scoreExplain;
    }

    public void setScoreExplain(String scoreExplain) {
        this.scoreExplain = scoreExplain == null ? null : scoreExplain.trim();
    }

    public Double getScoreMax() {
        return scoreMax;
    }

    public void setScoreMax(Double scoreMax) {
        this.scoreMax = scoreMax;
    }

    public Integer getIsScore() {
        return isScore;
    }

    public void setIsScore(Integer isScore) {
        this.isScore = isScore;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

	@Override
	public String toString() {
		return GSONUtil.toJson(this);
	}
    
    
    

}
