package com.galaxyinternet.model.sopfile;

public class SopDownLoad {
	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 文件后缀
	 */
	private String fileSuffix;
	/**
	 * 文件阿里云KEY
	 */
	private String fileKey;
	
	public static final String USER_AGENT = "User-Agent";
	/**
	 * 文件大小
	 */
	private Long fileSize;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileSuffix() {
		return fileSuffix;
	}
	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}
	public String getFileKey() {
		return fileKey;
	}
	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}


	
}
