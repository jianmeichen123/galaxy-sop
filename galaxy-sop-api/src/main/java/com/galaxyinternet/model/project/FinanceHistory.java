package com.galaxyinternet.model.project;

import java.text.ParseException;
import java.util.Date;

import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.framework.core.model.BaseEntity;
import com.galaxyinternet.framework.core.utils.DateUtil;

public class FinanceHistory extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String uuid;

    private Long projectId;//项目id
    
    private String financeDateStr;//融资日期格式化
    
    private String financeFrom;//融资来源
    
    private double financeAmount;//融资金额
    
    private Integer financeUnit;//融资货币
    
    private double financeProportion;//融资占比
    
    private String financeStatus;//融资轮次
    
    private Long createUid;//创建人
    
    private Long updatedUid;
    
    
    private String financeStatusDs;//融资轮次	格式化
    
    private String createDate;//创建时间
    
    private String updateDate;//更新时间
    
    private Date financeDate;//融资时间
    
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

	public String getFinanceFrom() {
		return financeFrom;
	}

	public void setFinanceFrom(String financeFrom) {
		this.financeFrom = financeFrom;
	}

	public double getFinanceAmount() {
		return financeAmount;
	}

	public void setFinanceAmount(double financeAmount) {
		this.financeAmount = financeAmount;
	}
	
	public Integer getFinanceUnit() {
		return financeUnit;
	}

	public void setFinanceUnit(Integer financeUnit) {
		this.financeUnit = financeUnit;
	}

	public double getFinanceProportion() {
		return financeProportion;
	}

	public void setFinanceProportion(double financeProportion) {
		this.financeProportion = financeProportion;
	}

	public String getFinanceStatus() {
		return financeStatus;
	}

	public void setFinanceStatus(String financeStatus) {
		this.financeStatus = financeStatus == null ? null: financeStatus.trim();
        if(financeStatus != null){
			this.financeStatusDs = DictEnum.financeStatus.getNameByCode(financeStatus);
		}else{
			this.financeStatusDs ="不明确";
		}
	}

	public Long getCreateUid() {
		return createUid;
	}

	public void setCreateUid(Long createUid) {
		this.createUid = createUid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
  
    public void setCreatedTime(Long createdTime) {
    	this.createdTime = createdTime;
    	if(createdTime != null){
    		this.createDate = DateUtil.longToString(createdTime);
    	}
    }
	public Date getFinanceDate() { //2016-05-27 16:00:00   19
		if(financeDate==null && financeDateStr!=null){
			try {
				financeDate = DateUtil.convertStringToDate(financeDateStr);
			} catch (ParseException e) {
				financeDate = null;
			}
		}else{
			if(financeDateStr==null && financeDate!=null){
				//meetingDateStr = DateUtil.convertDateToStringForChina(meetingDate);
				financeDateStr = DateUtil.convertDateToString(financeDate,"yyyy-MM-dd");
			}
		}
        return financeDate;
    }
	
    public void setFinanceDate(Date financeDate) {
    	if(financeDate==null && financeDateStr!=null){
			try {
				financeDate = DateUtil.convertStringToDate(this.financeDateStr);
			} catch (ParseException e) {
				financeDate = null;
			}
		}else{
			if(financeDateStr==null && financeDate!=null){
				financeDateStr = DateUtil.convertDateToString(financeDate,"yyyy-MM-dd");
			}
		}
        this.financeDate = financeDate;
    }
    
	public String getFinanceDateStr() {
		if(financeDateStr==null && financeDate!=null){
			financeDateStr = DateUtil.convertDateToString(financeDate,"yyyy-MM-dd");
		}
		return financeDateStr;
	}
	public void setFinanceDateStr(String financeDateStr) { ////2016-05-27 16:00:00   19
		if(financeDateStr==null && financeDate!=null){
			financeDateStr = DateUtil.convertDateToString(financeDate,"yyyy-MM-dd");
		}
		this.financeDateStr = financeDateStr;
	}
    public void setUpdatedTime(Long updatedTime) {
    	this.updatedTime = updatedTime;
    	if(updatedTime != null){
    		this.updateDate = DateUtil.longToString(updatedTime);
    	}
    }
    
	public String getFinanceStatusDs() {
		return financeStatusDs;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}
	
	
	public Long getUpdatedUid() {
		return updatedUid;
	}

	public void setUpdatedUid(Long updatedUid) {
		this.updatedUid = updatedUid;
	}

	@Override
	public boolean equals(Object obj) {
		if(uuid != null && obj != null && obj instanceof FinanceHistory && ((FinanceHistory) obj).getUuid() != null){
			return this.getUuid().equals(((FinanceHistory) obj).getUuid());
		}
		return super.equals(obj);
	}
	
	
}