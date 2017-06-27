package com.galaxyinternet.common.query;

import java.text.ParseException;
import java.util.Date;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.galaxyinternet.common.ViewQuery;
import com.galaxyinternet.framework.core.utils.DateUtil;


public class ProjectQuery extends ViewQuery{
	private static final long serialVersionUID = 1L;
	
	//项目ID
	private Long pid;
	//本次选择的项目阶段，并以一定和项目当前阶段一致
	private String stage;
	//针对会议类型
	private String meetingType;
	//针对会议结论
	private String result;
	//存储类型
	private String fileType;
	//文档的业务类型
	private String fileWorktype;
	//是否为签署证明,0/null--非签署证明,1--签署证明
	private Integer voucherType;
	//是否涉及股权转让
	private Integer hasStockTransfer;
	//档案来源
	private Integer type;
	//针对访谈对象
	private String target;
	//访谈结果
	private String interviewResult;
	
	private Long recordId;
	public String getInterviewResult() {
		return interviewResult;
	}
	public void setInterviewResult(String interviewResult) {
		this.interviewResult = interviewResult;
	}
	//结果原因
	private String resultReason;
	//其他原因
	private String reasonOther;
	
	
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
	//附加内容
	private String content;
	//时间
	private String createDate;
	
	//文件属性
	private String fileName;
	private String suffix;
	private String bucketName;
	private String fileKey;
	private long fileSize;
	
	private CommonsMultipartFile file;
	
	
	//操作人属性
	private long createdUid;
	private long departmentId;
	

	private Double finalValuations;//实际估值
  	private Double finalContribution;//实际投资
  	private Double finalShareRatio;//实际所占股份百分比
  	private Double serviceCharge;
  	

	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getMeetingType() {
		return meetingType;
	}
	public void setMeetingType(String meetingType) {
		this.meetingType = meetingType;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileWorktype() {
		return fileWorktype;
	}
	public void setFileWorktype(String fileWorktype) {
		this.fileWorktype = fileWorktype;
	}
	public Integer getVoucherType() {
		return voucherType;
	}
	public void setVoucherType(Integer voucherType) {
		this.voucherType = voucherType;
	}
	public Integer getHasStockTransfer() {
		return hasStockTransfer;
	}
	public void setHasStockTransfer(Integer hasStockTransfer) {
		this.hasStockTransfer = hasStockTransfer;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Date getParseDate() {
		if(this.createDate != null){
			try {
				return DateUtil.convertStringToDate(this.createDate, "yyyy-MM-dd hh:mm");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return new Date();
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public String getFileKey() {
		return fileKey;
	}
	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public long getCreatedUid() {
		return createdUid;
	}
	public void setCreatedUid(long createdUid) {
		this.createdUid = createdUid;
	}
	public long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
	}
	public CommonsMultipartFile getFile() {
		return file;
	}
	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}
	public Double getFinalValuations() {
		return finalValuations;
	}
	public void setFinalValuations(Double finalValuations) {
		this.finalValuations = finalValuations;
	}
	public Double getFinalContribution() {
		return finalContribution;
	}
	public void setFinalContribution(Double finalContribution) {
		this.finalContribution = finalContribution;
	}
	public Double getFinalShareRatio() {
		return finalShareRatio;
	}
	public void setFinalShareRatio(Double finalShareRatio) {
		this.finalShareRatio = finalShareRatio;
	}
	public Double getServiceCharge() {
		return serviceCharge;
	}
	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	public Long getRecordId() {
		return recordId;
	}
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	
	
	
	
}
