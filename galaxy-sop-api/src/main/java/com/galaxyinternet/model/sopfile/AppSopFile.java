package com.galaxyinternet.model.sopfile;

public class AppSopFile extends SopFile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6799927434048104446L;
   
	//上传人姓名
	private String name;

	//上传文件的名称
	private String fileName;
	
	//上传文件的时间
	private String fileTime;
	
	//上传的文件状态
	private String fileDs;

	//上传的文件状态的code
	private String fileDsCode;
	
	//业务类型的code
	private String fileYwCode;
	
	public String getFileYwCode() {
		return fileYwCode;
	}


	public void setFileYwCode(String fileYwCode) {
		this.fileYwCode = fileYwCode;
	}


	public String getFileDsCode() {
		return fileDsCode;
	}


	public void setFileDsCode(String fileDsCode) {
		this.fileDsCode = fileDsCode;
	}


	public String getFileDs() {
		return fileDs;
	}


	public void setFileDs(String fileDs) {
		this.fileDs = fileDs;
	}


	


	public String getFileTime() {
		return fileTime;
	}


	public void setFileTime(String fileTime) {
		this.fileTime = fileTime;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
}