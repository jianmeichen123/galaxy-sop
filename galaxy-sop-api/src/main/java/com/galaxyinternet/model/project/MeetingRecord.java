package com.galaxyinternet.model.project;

import java.text.ParseException;
import java.util.Date;

import com.galaxyinternet.framework.core.model.BaseEntity;
import com.galaxyinternet.framework.core.utils.DateUtil;


public class MeetingRecord  extends BaseEntity{
	private static final long serialVersionUID = 1L;

	 private Long projectId;

    private Long fileId;

    private Date meetingDate;
    private String meetingDateStr;

    private String meetingType;
    private String meetingTypeStr;

    private String meetingResult;
    private String meetingResultStr;

    private String meetingNotes;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public Date getMeetingDate() {
		if(meetingDate==null && meetingDateStr!=null && meetingDateStr.trim().length() == 10 ){
			try {
				if( meetingDateStr.indexOf("/") != -1){
					meetingDate = DateUtil.convertStringToDate(this.meetingDateStr.replaceAll("/", "-"));
	    		}else{
	    			meetingDate = DateUtil.convertStringToDate(this.meetingDateStr);
	    		}
				
			} catch (ParseException e) {
				meetingDate = null;
			}
		}else{
			if(meetingDateStr==null && meetingDate!=null){
				meetingDateStr = DateUtil.convertDateToString(meetingDate);
			}
		}
        return meetingDate;
    }
	
    public void setMeetingDate(Date meetingDate) {
    	if(meetingDate==null && meetingDateStr!=null && meetingDateStr.trim().length() == 10 ){
			try {
				if( meetingDateStr.indexOf("/") != -1){
					meetingDate = DateUtil.convertStringToDate(this.meetingDateStr.replaceAll("/", "-"));
	    		}else{
	    			meetingDate = DateUtil.convertStringToDate(this.meetingDateStr);
	    		}
				
			} catch (ParseException e) {
				meetingDate = null;
			}
		}else{
			if(meetingDateStr==null && meetingDate!=null){
				meetingDateStr = DateUtil.convertDateToString(meetingDate);
			}
		}
        this.meetingDate = meetingDate;
    }

    public String getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(String meetingType) {
        this.meetingType = meetingType == null ? null : meetingType.trim();
    }

    
    public String getMeetingResult() {
		return meetingResult;
	}

	public void setMeetingResult(String meetingResult) {
		this.meetingResult = meetingResult == null ? null : meetingResult.trim();
	}

	public String getMeetingNotes() {
        return meetingNotes;
    }

    public void setMeetingNotes(String meetingNotes) {
        this.meetingNotes = meetingNotes == null ? null : meetingNotes.trim();
    }

    
	public String getMeetingDateStr() {
		if(meetingDateStr==null && meetingDate!=null){
			meetingDateStr = DateUtil.convertDateToString(meetingDate);
		}
		return meetingDateStr;
	}

	public void setMeetingDateStr(String meetingDateStr) {
		if(meetingDate==null && meetingDateStr!=null && meetingDateStr.trim().length() == 10 ){
			try {
				if( meetingDateStr.indexOf("/") != -1){
					meetingDate = DateUtil.convertStringToDate(meetingDateStr.replaceAll("/", "-"));
	    		}else{
	    			meetingDate = DateUtil.convertStringToDate(meetingDateStr);
	    		}
				
			} catch (ParseException e) {
				meetingDate = null;
			}
		}else{
			if(meetingDateStr==null && meetingDate!=null){
				meetingDateStr = DateUtil.convertDateToString(meetingDate);
			}
		}
		
		this.meetingDateStr = meetingDateStr;
	}
	
	public String getMeetingTypeStr() {
		if(this.meetingType!=null){
			if(this.meetingType.equals("meetingType:1")){
				this.meetingTypeStr = "内评会";
			}else if(this.meetingType.equals("meetingType:2")){
				this.meetingTypeStr = "CEO评审";
			}else if(this.meetingType.equals("meetingType:3")){
				this.meetingTypeStr = "立项会";
			}else if(this.meetingType.equals("meetingType:4")){
				this.meetingTypeStr = "投决会";
			}
		}
		return meetingTypeStr;
	}

	public void setMeetingTypeStr(String meetingTypeStr) {
		if(this.meetingType!=null){
			if(this.meetingType.equals("meetingType:1")){
				this.meetingTypeStr = "内评会";
			}else if(this.meetingType.equals("meetingType:2")){
				this.meetingTypeStr = "CEO评审";
			}else if(this.meetingType.equals("meetingType:3")){
				this.meetingTypeStr = "立项会";
			}else if(this.meetingType.equals("meetingType:4")){
				this.meetingTypeStr = "投决会";
			}
		}
		this.meetingTypeStr = meetingTypeStr;
	}
		
	public String getMeetingResultStr() {
		if(this.meetingResult!=null){
			if(this.meetingResult.equals("meetingResult:1")){
				this.meetingResultStr = "通过";
			}else if(this.meetingResult.equals("meetingResult:2")){
				this.meetingResultStr = "待定";
			}else if(this.meetingResult.equals("meetingResult:3")){
				this.meetingResultStr = "否决";
			}
		}
		return meetingResultStr;
	}

	public void setMeetingResultStr(String meetingResultStr) {
		if(this.meetingResult!=null){
			if(this.meetingResult.equals("meetingResult:1")){
				this.meetingResultStr = "通过";
			}else if(this.meetingResult.equals("meetingResult:2")){
				this.meetingResultStr = "待定";
			}else if(this.meetingResult.equals("meetingResult:3")){
				this.meetingResultStr = "否决";
			}
		}
		this.meetingResultStr = meetingResultStr;
	}


}