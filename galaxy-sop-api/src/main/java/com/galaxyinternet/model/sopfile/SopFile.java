package com.galaxyinternet.model.sopfile;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.framework.core.utils.DateUtil;
import com.galaxyinternet.model.operationLog.UrlNumber;

public class SopFile extends SopParentFile implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/*public static final String INIT_FILE_MAEK_INIT = "init"; // 文档状态， init(有初始记录)：记录   update；    
	public static final String INIT_FILE_MAEK_HAS = "has";   // 文档状态，  has(有完整记录)：  记录  dead，insert；
	public static final String INIT_FILE_MAEK_NO = "no";     // 文档状态，  no（没有记录）：      insert；
*/	
	
	/**
     * 文档  操作
     */
	private UrlNumber number;
	//private String initMark;   // 文档状态， init(有初始记录)：记录update；     has(有完整记录)：记录dead，insert； no（没有记录）：insert；
	private Boolean canOpt;    // 文档可操作： 上传、编辑
	private Boolean canDown;   // 文档可操作： 下载
	
	
	public UrlNumber getNumber() {
		return number;
	}
	public void setNumber(UrlNumber number) {
		this.number = number;
	}
	public Boolean getCanOpt() {
		return canOpt;
	}
	public void setCanOpt(Boolean canOpt) {
		this.canOpt = canOpt;
	}
	public Boolean getCanDown() {
		return canDown;
	}
	public void setCanDown(Boolean canDown) {
		this.canDown = canDown;
	}



	/**
     * 任务
     */
	private String taskStatusStr;   // 关联任务状态
	private Long taskUid;           // 认领人id
	private String taskUname;       // 认领人name
	
    public String getTaskStatusStr() {
		return taskStatusStr;
	}
	public void setTaskStatusStr(String taskStatusStr) {
		this.taskStatusStr = taskStatusStr;
	}
	public Long getTaskUid() {
		return taskUid;
	}
	public void setTaskUid(Long taskUid) {
		this.taskUid = taskUid;
	}
	public String getTaskUname() {
		return taskUname;
	}
	public void setTaskUname(String taskUname) {
		this.taskUname = taskUname;
	}
	
	
	

	/**
     * 任务所属人
     */
    private Long belongUid;
    
    /**
     * 文档是否有效
     */
    private Integer fileValid;

    
    /**
     * 项目进程
     * 1、接触访谈; 2、内部评审;3、立项会; 4、投资意向书; 5、尽职调查;6、投资决策会;7、投资协议; 8、投后运营
     */
    private String projectProgress;

    


    
    /**
     * 所属事业线
     */
    private Long careerLine;

    
    /**
     * 档案来源 
     * 1:内部 2:外部
     */
    private String fileSource;

    
    /**
     * 存储类型 
     * 1:文档 2:图片 3:音视频
     */
    private String fileType;

    
    /**
     * 档案摘要
     */
    private String remark;
    /**
     * 签署凭证Id
     */
    private Long voucherId;
    
    /**
     * 档案状态
     * 缺失，已上传，已审核
     */
    private String fileStatus;
  
    /**
     * 上传人/起草者
     */
    private Long fileUid;
    
    /**
     * 存储地址
     */
    private String filUri;
    
    /**
     * 会议记录ID
     */
    private Long meetingId;
    
    /**
     * 临时地址
     */
    private String tempPath;
    
    
    
    //详情数据转换
    //数据库转换
  	private String fileUName;
  	private String projectName;
  	private String careerLineName;
  	
  	private String voucherFileKey;
  	
  	private String Vstatus;
  	
  	//枚举转换
  	private String fType = "";
  	private String progress;
  	private String fileStatusDesc;
  	private String updatedDate;
  	private String createDate;
  	private String fSource;
  	private Map<String,String> params;
  	private String voucherFileName;
  	
  	//参数
  	private List<Long> projectIdList;
  	private List<Long> projectLikeIdList;
  	private List<Long> fileULikeidList;
  	private List<String> fileStatusList;
  	private List<String> fileworktypeList;
  	private List<Long> ids;
  	
  	private String startTime;
  	private String endTime;
  	
  	private String isEdit;
  	private String isChangeTask;
  	private String isProveEdit;
  	
  	
  	private String isProve;
  	
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

	public String getfType() {
		return fType == null ? "" : fType;
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
    	super.setUpdatedTime(updatedTime);
    	if(updatedTime != null){
     		this.updatedDate = DateUtil.longToString(updatedTime);
     	}
    }
     
 

 	public String getProgress() {
 		return progress;
 	}
    

    public Long getBelongUid() {
		return belongUid;
	}

	public void setBelongUid(Long belongUid) {
		this.belongUid = belongUid;
	}

	public Integer getFileValid() {
		return fileValid;
	}

	public void setFileValid(Integer fileValid) {
		this.fileValid = fileValid;
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

    
   

    
    public Long getCareerLine() {
        return careerLine;
    }

    
    public void setCareerLine(Long careerLine) {
        this.careerLine = careerLine;
    }

    
    
    public String getFileSource() {
		return fileSource;
	}


	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
		if(fileSource != null){
			fSource = DictEnum.fileSource.getNameByCode(fileSource);
		}
		
	}


	public String getFileType() {
		return fileType;
	}


	public void setFileType(String fileType) {
		 this.fileType = fileType == null ? null : fileType.trim();
         if(fileType != null){
 			this.fType = DictEnum.fileType.getNameByCode(fileType);
         }
	}


	public String getRemark() {
        return remark;
    }

    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    
    public Long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(Long voucherId) {
		this.voucherId = voucherId;
	}

	public String getFileStatus() {
        return fileStatus;
    }

    
    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
        if(fileStatus != null)
        {
        	fileStatusDesc = DictEnum.fileStatus.getNameByCode(fileStatus);
        }
    }

    
    public Long getFileUid() {
        return fileUid;
    }

    
    public void setFileUid(Long fileUid) {
        this.fileUid = fileUid;
    }

    
    public String getFilUri() {
        return filUri;
    }

    
    public void setFilUri(String filUri) {
        this.filUri = filUri;
    }

    



	

	public Long getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
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

	public List<Long> getProjectIdList() {
		return projectIdList;
	}

	public void setProjectIdList(List<Long> projectIdList) {
		this.projectIdList = projectIdList;
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

	public List<Long> getFileULikeidList() {
		return fileULikeidList;
	}

	public void setFileULikeidList(List<Long> fileULikeidList) {
		this.fileULikeidList = fileULikeidList;
	}

	public List<Long> getProjectLikeIdList() {
		return projectLikeIdList;
	}

	public void setProjectLikeIdList(List<Long> projectLikeIdList) {
		this.projectLikeIdList = projectLikeIdList;
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

	public List<String> getFileStatusList() {
		return fileStatusList;
	}

	public void setFileStatusList(List<String> fileStatusList) {
		this.fileStatusList = fileStatusList;
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

	public List<String> getFileworktypeList() {
		return fileworktypeList;
	}

	public void setFileworktypeList(List<String> fileworktypeList) {
		this.fileworktypeList = fileworktypeList;
	}

	public String getIsProve() {
		return isProve;
	}

	public void setIsProve(String isProve) {
		this.isProve = isProve;
	}

	public String getTempPath() {
		return tempPath;
	}

	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
    
	
	
	
}