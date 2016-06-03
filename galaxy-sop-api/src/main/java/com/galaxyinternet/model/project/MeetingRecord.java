package com.galaxyinternet.model.project;

import java.text.ParseException;
import java.util.Date;

import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.model.common.RecordEntity;


public class MeetingRecord  extends RecordEntity{
	private static final long serialVersionUID = 1L;

	 private Long projectId;

    private Long fileId;
	private String fkey;

    private Date meetingDate;
    private String meetingDateStr;

    private String meetingType;
    private String meetingTypeStr;

    private String meetingResult;
    private String meetingResultStr;

    private String meetingNotes;
    
    private String meetingNotesText;

    private String fname;
    
    private String participant;
    
    private byte meetValid; //0表示有效，1表示无效
    
    
    public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
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

	public Date getMeetingDate() { //2016-05-27 16:00:00   19
		if(meetingDate==null && meetingDateStr!=null){
			meetingDateStr = dateStrformat(meetingDateStr);
			try {
	    		meetingDate = DateUtil.convertStringtoD(meetingDateStr);
			} catch (ParseException e) {
				meetingDate = null;
			}
		}else{
			if(meetingDateStr==null && meetingDate!=null){
				meetingDateStr = DateUtil.convertDateToStringForChina(meetingDate);
			}
		}
        return meetingDate;
    }
	
    public void setMeetingDate(Date meetingDate) {
    	if(meetingDate==null && meetingDateStr!=null){
    		meetingDateStr = dateStrformat(meetingDateStr);
			try {
	    		meetingDate = DateUtil.convertStringtoD(this.meetingDateStr);
			} catch (ParseException e) {
				meetingDate = null;
			}
		}else{
			if(meetingDateStr==null && meetingDate!=null){
				meetingDateStr = DateUtil.convertDateToStringForChina(meetingDate);
			}
		}
        this.meetingDate = meetingDate;
    }

    public String getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(String meetingType) {
    	if(meetingType!=null){
			if(meetingType.equals("meetingType:1")){
				meetingTypeStr = "内评会";
			}else if(meetingType.equals("meetingType:2")){
				meetingTypeStr = "CEO评审";
			}else if(meetingType.equals("meetingType:3")){
				this.meetingTypeStr = "立项会";
			}else if(meetingType.equals("meetingType:4")){
				meetingTypeStr = "投决会";
			}
		}
        this.meetingType = meetingType == null ? null : meetingType.trim();
    }

    
    public String getMeetingResult() {
		return meetingResult;
	}

	public void setMeetingResult(String meetingResult) {
		if(meetingResult!=null&&meetingResultStr==null){
			if(meetingResult.equals("meetingResult:1")){
				meetingResultStr = "通过";
			}else if(meetingResult.equals("meetingResult:2")){
				meetingResultStr = "待定";
			}else if(meetingResult.equals("meetingResult:3")){
				meetingResultStr = "否决";
			}
		}
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
			meetingDateStr = DateUtil.convertDateToStringForChina(meetingDate);
		}
		return meetingDateStr;
	}

	
	
	public void setMeetingDateStr(String meetingDateStr) { ////2016-05-27 16:00:00   19
		if(meetingDate==null && meetingDateStr!=null ){
			meetingDateStr = dateStrformat(meetingDateStr.trim());
			try {
	    		meetingDate = DateUtil.convertStringtoD(meetingDateStr);
			} catch (ParseException e) {
				meetingDate = null;
			}
		}else{
			if(meetingDateStr==null && meetingDate!=null){
				meetingDateStr = DateUtil.convertDateToStringForChina(meetingDate);
			}
		}
		this.meetingDateStr = meetingDateStr;
	}
	
	public String getMeetingTypeStr() {
		if(meetingType!=null&&meetingTypeStr==null){
			if(meetingType.equals("meetingType:1")){
				meetingTypeStr = "内评会";
			}else if(meetingType.equals("meetingType:2")){
				meetingTypeStr = "CEO评审";
			}else if(meetingType.equals("meetingType:3")){
				meetingTypeStr = "立项会";
			}else if(meetingType.equals("meetingType:4")){
				meetingTypeStr = "投决会";
			}
		}
		return meetingTypeStr;
	}

	public void setMeetingTypeStr(String meetingTypeStr) {
		this.meetingTypeStr = meetingTypeStr;
	}
		
	public String getMeetingResultStr() {
		if(meetingResult!=null&&meetingResultStr==null){
			if(meetingResult.equals("meetingResult:1")){
				meetingResultStr = "通过";
			}else if(meetingResult.equals("meetingResult:2")){
				meetingResultStr = "待定";
			}else if(meetingResult.equals("meetingResult:3")){
				meetingResultStr = "否决";
			}
		}
		return meetingResultStr;
	}

	public void setMeetingResultStr(String meetingResultStr) {
		this.meetingResultStr = meetingResultStr;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getFkey() {
		return fkey;
	}

	public void setFkey(String fkey) {
		this.fkey = fkey;
	}

	public byte getMeetValid() {
		return meetValid;
	}

	public void setMeetValid(byte meetValid) {
		this.meetValid = meetValid;
	}

	public String getMeetingNotesText() {
		return meetingNotesText;
	}

	public void setMeetingNotesText(String meetingNotesText) {
		this.meetingNotesText = meetingNotesText;
	}

    
	
	
	

	public static String dateStrformat(String dateStr){  //2016-05-27 16:00:00   19
		int len = dateStr.length();
		if( dateStr.indexOf("/") != -1){
			dateStr = dateStr.replaceAll("/", "-");
		}
	//	String format = "yyyy-MM-dd HH:mm:ss";
		switch (len) {
		/*case 8:
			if(dateStr.indexOf("-")==-1 || dateStr.indexOf("/")==-1 ){
				format = "yyyyMMdd";
			}
			break;*/
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
	

}