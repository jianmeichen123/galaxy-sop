package com.galaxyinternet.model.project;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.model.common.PagableRecordEntity;
import com.galaxyinternet.model.sopfile.AppSopFile;

public class AppProgress extends PagableRecordEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   
    /**
     * 项目ID
     */
    private Long projectId;
    /**
     * 访谈日志
     */
    private String interviewNotes;
    /**
     * 访谈对象
     */
    private String interviewTarget;
    /**
     * 访谈时间
     */
    private String interviewTime;
    /**
     * 会议结果类型
     */
    private String meetResult;
    /**
     * 会议纪要
     * 
     */
    private String meetNotes;
    /**
     * 会议时间
     */
    private String meetTime;
    /**
     * 会议类型
     */
    private String meetCode;
    /**
     * 会议结果
     * @return
     */
    private String MeetingResultStr;
    /**
     * 项目进程编码
     * 1、接触访谈; 2、内部评审;3、立项会; 4、投资意向书; 5、尽职调查;6、投资决策会;7、投资协议; 8、投后运营
     */
    private String projectProgress;    
    /**
     * 项目进程名称
     */
    private String projectProgressName ;
    
  	private String fWorktype;
  	private String progress;
  	private String fileStatusDesc;
  	private String updatedDate;
  	private String createDate;
  	private String fSource;
  	private Map<String,String> params;
  	private String voucherFileName;
  	
   //详情数据转换
    //数据库转换
  	private String fileUName;
  	private String projectName;
  	private String careerLineName;
  	
  	private String voucherFileKey;
  	
  	private String Vstatus;
  	  	
	//上传文件list集合
  	private List<AppSopFile> appSopFile;
  	
  	/** 新增App上传文件信息的DTO对象  */
  	private List<AppFileDTO> appFileDtoList;
  	/** 排期结果  */
  	private Integer schedulingFlag  ;
  	
  	
    public String getMeetCode() {
		return meetCode;
	}

	public void setMeetCode(String meetCode) {
		this.meetCode = meetCode;
	}

    public String getMeetingResultStr() {
		return MeetingResultStr;
	}

	public void setMeetingResultStr(String meetingResultStr) {
		MeetingResultStr = meetingResultStr;
	}

	public String getInterviewNotes() {
		return interviewNotes;
	}

	public void setInterviewNotes(String interviewNotes) {
		this.interviewNotes = interviewNotes;
	}

	public String getInterviewTarget() {
		return interviewTarget;
	}

	public void setInterviewTarget(String interviewTarget) {
		this.interviewTarget = interviewTarget;
	}


	public String getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(String interviewTime) {
		this.interviewTime = interviewTime;
	}

	public String getMeetResult() {
		return meetResult;
	}

	public void setMeetResult(String meetResult) {
		this.meetResult = meetResult;
	}

	public String getMeetNotes() {
		return meetNotes;
	}

	public void setMeetNotes(String meetNotes) {
		this.meetNotes = meetNotes;
	}

	public String getMeetTime() {
		return meetTime;
	}

	public void setMeetTime(String meetTime) {
		this.meetTime = meetTime;
	}

    public String getProjectProgressName() {
		return projectProgressName;
	}

	public void setProjectProgressName(String projectProgressName) {
		this.projectProgressName = projectProgressName;
	}


	
  	
  

	public List<AppSopFile> getAppSopFile() {
		return appSopFile;
	}

	public void setAppSopFile(List<AppSopFile> appSopFile) {
		this.appSopFile = appSopFile;
	}

	private String startTime;
  	private String endTime;
  	
  	private String isEdit;
  	private String isChangeTask;
  	private String isProveEdit;
  	
  	/**
  	 *  请求页面
  	 */
  	private String pageType;
  	private String fileWorktypeNullFilter;
  	
  	private MultipartFile multipartFile;

  
	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
     		this.updatedDate = updatedDate;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

/*	public String getfType() {
		return fType == null ? "" : fType;
	}*/

	public String getfWorktype() {
		return fWorktype;
	}

	public String getCreateDate() {
 		return createDate;
 	}
     
	@Override
	public void setCreatedTime(Long createdTime) {
		super.setCreatedTime(createdTime);
		if (createdTime != null) {
			this.createDate = DateUtil.longToString(createdTime);
		}
	}
     @Override
    public void setUpdatedTime(Long updatedTime) {
    	// TODO Auto-generated method stub
    	super.setUpdatedTime(updatedTime);
    	if(updatedTime != null){
     		this.updatedDate = DateUtil.longToString(updatedTime);
     	}
    }
     
 

 	public String getProgress() {
 		return progress;
 	}
    
    public Long getProjectId() {
        return projectId;
    }

    
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }



	public String getProjectProgress() {
        return projectProgress;
    }

    public void setProjectProgress(String projectProgress) {
        this.projectProgress = projectProgress == null ? null : projectProgress.trim();
        if(projectProgress != null){
			this.progress = DictEnum.projectProgress.getNameByCode(projectProgress);
		}
    }

	public String getFileStatusDesc() {
		return fileStatusDesc;
	}

	public String getVoucherFileName() {
		return voucherFileName;
	}

	public void setVoucherFileName(String voucherFileName) {
		this.voucherFileName = voucherFileName;
	}

	public String getProjectName() {
		return projectName == null ? null : projectName.trim();
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName == null ? null : projectName.trim();
	}



	public String getFileUName() {
		return fileUName;
	}

	public void setFileUName(String fileUName) {
		this.fileUName = fileUName;
	}

	public String getCareerLineName() {
		return careerLineName;
	}

	public void setCareerLineName(String careerLineName) {
		this.careerLineName = careerLineName;
	}


	public String getVoucherFileKey() {
		return voucherFileKey;
	}

	public void setVoucherFileKey(String voucherFileKey) {
		this.voucherFileKey = voucherFileKey;
	}

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public String getFileWorktypeNullFilter() {
		return fileWorktypeNullFilter;
	}

	public void setFileWorktypeNullFilter(String fileWorktypeNullFilter) {
		this.fileWorktypeNullFilter = fileWorktypeNullFilter;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}
	
	public String getIsChangeTask() {
		return isChangeTask;
	}

	public void setIsChangeTask(String isChangeTask) {
		this.isChangeTask = isChangeTask;
	}
	
	public String getIsProveEdit() {
		return isProveEdit;
	}

	public void setIsProveEdit(String isProveEdit) {
		this.isProveEdit = isProveEdit;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getfSource() {
		return fSource;
	}

	public String getVstatus() {
		return Vstatus;
	}

	public void setVstatus(String vstatus) {
		Vstatus = vstatus;
	}

	public List<AppFileDTO> getAppFileDtoList() {
		return appFileDtoList;
	}

	public void setAppFileDtoList(List<AppFileDTO> appFileDtoList) {
		this.appFileDtoList = appFileDtoList;
	}

	public Integer getSchedulingFlag() {
		return schedulingFlag;
	}

	public void setSchedulingFlag(Integer schedulingFlag) {
		this.schedulingFlag = schedulingFlag;
	}
	
}