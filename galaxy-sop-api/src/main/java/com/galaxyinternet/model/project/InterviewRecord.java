package com.galaxyinternet.model.project;

import java.text.ParseException;
import java.util.Date;

import com.galaxyinternet.framework.core.model.BaseEntity;
import com.galaxyinternet.framework.core.utils.DateUtil;

public class InterviewRecord extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
    private Long projectId;
    
    private Long fileId;

    private Date viewDate;
    private String viewDateStr;
    
    private String viewTarget;

    private String viewNotes;

    private String fname;
    
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

	public Date getViewDate() {
		if(viewDate==null && viewDateStr!=null && viewDateStr.trim().length() == 10 ){
			try {
				if( viewDateStr.indexOf("/") != -1){
					viewDate = DateUtil.convertStringToDate(this.viewDateStr.replaceAll("/", "-"));
	    		}else{
	    			viewDate = DateUtil.convertStringToDate(this.viewDateStr);
	    		}
			} catch (ParseException e) {
				viewDate = null;
			}
		}else{
			if(viewDateStr==null && viewDate!=null){
				viewDateStr = DateUtil.convertDateToString(viewDate);
			}
		}
        return viewDate;
    }

    public void setViewDate(Date viewDate) {
    	if(viewDate==null && viewDateStr!=null && viewDateStr.trim().length() == 10 ){
			try {
				if( viewDateStr.indexOf("/") != -1){
					viewDate = DateUtil.convertStringToDate(viewDateStr.replaceAll("/", "-"));
	    		}else{
	    			viewDate = DateUtil.convertStringToDate(viewDateStr);
	    		}
			} catch (ParseException e) {
				viewDate = null;
			}
		}else{
			if(viewDateStr==null && viewDate!=null){
				viewDateStr = DateUtil.convertDateToString(viewDate);
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
			viewDateStr = DateUtil.convertDateToString(viewDate);
		}
		return viewDateStr;
	}

	public void setViewDateStr(String viewDateStr){
		if(viewDateStr==null && viewDate!=null){
			viewDateStr = DateUtil.convertDateToString(viewDate);
		}
		this.viewDateStr = viewDateStr;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}
	
	
	
}