package com.galaxyinternet.model.project;

import java.util.List;

import com.galaxyinternet.model.sopfile.AppSopFile;

/**
 * App上传文件信息的DTO对象
 * @author LZJ
 * @ClassName  : AppFileDTO
 * @Version  版本
 * @ModifiedBy LZJ
 * @Copyright  Galaxyinternet
 * @date  2016年5月26日 上午10:46:49
 */
public class AppFileDTO {
	
	/** 档案文件类型编码  */
	private String fileTypeCode;
	/** 档案文件类型名称  */
	private String fileTypeName;
	/** 上传文件的集合 */
	private List<AppSopFile> appSopFile;
	
		
	public String getFileTypeCode() {
		return fileTypeCode;
	}
	public void setFileTypeCode(String fileTypeCode) {
		this.fileTypeCode = fileTypeCode;
	}
	public String getFileTypeName() {
		return fileTypeName;
	}
	public void setFileTypeName(String fileTypeName) {
		this.fileTypeName = fileTypeName;
	}
	public List<AppSopFile> getAppSopFile() {
		return appSopFile;
	}
	public void setAppSopFile(List<AppSopFile> appSopFile) {
		this.appSopFile = appSopFile;
	}
	
}
