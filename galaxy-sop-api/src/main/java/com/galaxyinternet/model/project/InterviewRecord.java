package com.galaxyinternet.model.project;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.framework.core.model.BaseEntity;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.framework.core.utils.GSONUtil;

public class InterviewRecord extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
    private Long projectId;
    
    private Long fileId;

    private Date viewDate;
    private String viewDateStr;
    
    private String viewTarget;

    private String viewNotes;
    private String viewNotesText;
    
    private String fname;
    
    private Long createdId;
    
    private Long scheduleId;
    
    private String interviewResult;
    private String interviewResultStr;
    
    public String getInterviewResultStr() {
       return interviewResultStr;
	}

	public void setInterviewResultStr(String interviewResultStr) {
		this.interviewResultStr = interviewResultStr;
	}

	private String resultReason;
    private String reasonOther;
    
    private String resultReasonStr;
    
    
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

	
	
	public Date getViewDate() { //2016-05-27 16:00:00   19
		if(viewDate==null && viewDateStr!=null){
			viewDateStr = dateStrformat(viewDateStr);
			try {
				viewDate = DateUtil.convertStringtoD(viewDateStr);
			} catch (ParseException e) {
				viewDate = null;
			}
		}else{
			if(viewDateStr==null && viewDate!=null){
//				viewDateStr = DateUtil.convertDateToStringForChina(viewDate);
				viewDateStr = DateUtil.convertDateToString(viewDate,"yyyy-MM-dd HH:mm");
			}
		}
        return viewDate;
    }
	
    public void setViewDate(Date viewDate) {
    	if(viewDate==null && viewDateStr!=null){
    		viewDateStr = dateStrformat(viewDateStr);
			try {
				viewDate = DateUtil.convertStringtoD(this.viewDateStr);
			} catch (ParseException e) {
				viewDate = null;
			}
		}else{
			if(viewDateStr==null && viewDate!=null){
				viewDateStr = DateUtil.convertDateToString(viewDate,"yyyy-MM-dd HH:mm");
			}
		}
        this.viewDate = viewDate;
    }
    

    public String getViewTarget() {
        return viewTarget;
    }

    public void setViewTarget(String viewTarget) {
        this.viewTarget = viewTarget == null ? null : viewTarget.trim();
    }

    public String getViewNotes() {
        return viewNotes;
    }

    public void setViewNotes(String viewNotes) {
        this.viewNotes = viewNotes == null ? null : viewNotes.trim();
    }

    
    
    public String getViewDateStr() {
    	if(viewDateStr==null && viewDate!=null){
			//viewDateStr = DateUtil.convertDateToStringForChina(viewDate);
			viewDateStr = DateUtil.convertDateToString(viewDate,"yyyy-MM-dd HH:mm");
		}
		return viewDateStr;
	}

	public void setViewDateStr(String viewDateStr){
		if(viewDateStr==null && viewDate!=null){
			viewDateStr = DateUtil.convertDateToString(viewDate,"yyyy-MM-dd HH:mm");
		}
		this.viewDateStr = viewDateStr;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public Long getCreatedId() {
		return createdId;
	}

	public void setCreatedId(Long createdId) {
		this.createdId = createdId;
	}

	public String getViewNotesText() {
		return viewNotesText;
	}

	public void setViewNotesText(String viewNotesText) {
		this.viewNotesText = viewNotesText;
	}

	public static String dateStrformat(String dateStr){  //2016-05-27 16:00:00   19
		int len = dateStr.length();
		if( dateStr.indexOf("/") != -1){
			dateStr = dateStr.replaceAll("/", "-");
		}
		switch (len) {
		case 10:
			dateStr = dateStr + " 00:00:00";
			break;
		case 13:
			dateStr = dateStr + ":00:00";
			break;
		case 16:
			dateStr = dateStr + ":00";
			break;
		default:
			break;
		}
		return dateStr;
	}
	
	
	
	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}
	
	

	public String getInterviewResult() {
		return interviewResult;
	}

	public void setInterviewResult(String interviewResult) {
		this.interviewResult = interviewResult;
	}

	public String getResultReason() {
		return resultReason;
	}

	public void setResultReason(String resultReason) {
		this.resultReason = resultReason;
	}

	public String getReasonOther() {
		return reasonOther;
	}

	public void setReasonOther(String reasonOther) {
		this.reasonOther = reasonOther;
	}
	
	public String getResultReasonStr() {
		return resultReasonStr;
	}

	public void setResultReasonStr(String resultReasonStr) {
		this.resultReasonStr = resultReasonStr;
	}

	@Override
	public String toString() {
		return GSONUtil.toJson(this);
	}
	

}