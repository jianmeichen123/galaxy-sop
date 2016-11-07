package com.galaxyinternet.model.project;

import java.text.ParseException;
import java.util.Date;

import com.galaxyinternet.framework.core.model.BaseEntity;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.framework.core.utils.GSONUtil;
import com.galaxyinternet.model.sopfile.SopFile;

public class InterviewRecord extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/*唯一编码*/
	private String uuid;
	private String pid;
	
    private Long projectId;
    
    private Long fileId;

    private Date viewDate;
    private String viewDateStr;
    
    private String viewTarget;

    private String viewNotes;
    private String viewNotesText;
    
    private Long createdId;
    
    private String fname;
	
    private SopFile sopFile;
    
    
    
    
    public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public SopFile getSopFile() {
		return sopFile;
	}

	public void setSopFile(SopFile sopFile) {
		this.sopFile = sopFile;
	}

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

	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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
	
	@Override
	public String toString() {
		return GSONUtil.toJson(this);
	}

}