package com.galaxyinternet.bo;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.annotation.Transient;

import com.galaxyinternet.framework.core.utils.StringEx;
import com.galaxyinternet.model.idea.IdeaZixun;
import com.galaxyinternet.model.idea.ZixunFinance;

public class IdeaZixunBo extends IdeaZixun {

	private static final long serialVersionUID = 1L;

	/**
	 * 是否包含转义字符
	 */
	@Transient
	protected Boolean codeQueryEscapeChar;
	
	private String codeQuery;
	//private String companyFieldQuery; //key
	
	
	private String startTime;
	private String endTime;
	
	private Long beginTimeLong;
	private Long endTimeLong;
	
	
	private String departName;
	private String userName;
	
	private int canEdit;
	
	private List<ZixunFinance> finaceList;
	private List<Long> rzIdList;
	
	
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Long getBeginTimeLong() {
		return beginTimeLong;
	}
	public void setBeginTimeLong(Long beginTimeLong) {
		this.beginTimeLong = beginTimeLong;
	}
	public Long getEndTimeLong() {
		return endTimeLong;
	}
	public void setEndTimeLong(Long endTimeLong) {
		this.endTimeLong = endTimeLong;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public List<ZixunFinance> getFinaceList() {
		return finaceList;
	}
	public void setFinaceList(List<ZixunFinance> finaceList) {
		this.finaceList = finaceList;
	}
	public int getCanEdit() {
		return canEdit;
	}
	public void setCanEdit(int canEdit) {
		this.canEdit = canEdit;
	}
	public List<Long> getRzIdList() {
		return rzIdList;
	}
	public void setRzIdList(List<Long> rzIdList) {
		this.rzIdList = rzIdList;
	}
	public String getCodeQuery() {
		return codeQuery == null ? null : codeQuery.trim();
	}
	public void setCodeQuery(String codeQuery) {
		this.codeQuery = codeQuery == null ? null : codeQuery.trim();
	}
	
	public Boolean getCodeQueryEscapeChar() {
		this.getNewCodeQuery();
		return codeQueryEscapeChar;
	}
	public void setCodeQueryEscapeChar(Boolean codeQueryEscapeChar) {
		this.codeQueryEscapeChar = codeQueryEscapeChar;
	}
	
	private void getNewCodeQuery(){
		if(codeQueryEscapeChar == null){
			codeQueryEscapeChar = false;
		}
		if(StringUtils.isNotEmpty(codeQuery)&&!codeQueryEscapeChar){
			String newCodeQuery = StringEx.checkSql(codeQuery);
			if(!codeQuery.equals(newCodeQuery)){
				this.setEscapeChar(true);
				this.setCodeQuery(newCodeQuery);
			}
		}
	}
	
	
	
}