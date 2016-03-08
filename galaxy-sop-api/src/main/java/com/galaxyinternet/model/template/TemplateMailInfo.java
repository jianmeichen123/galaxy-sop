package com.galaxyinternet.model.template;

public class TemplateMailInfo
{
	private String fromAddress;
	private String toAddress;
	private String title;
	private String content;
	private Long[] templateIds;
	private String zipFlag;
	private String smFlag;
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long[] getTemplateIds() {
		return templateIds;
	}
	public void setTemplateIds(Long[] templateIds) {
		this.templateIds = templateIds;
	}
	public String getZipFlag() {
		return zipFlag;
	}
	public void setZipFlag(String zipFlag) {
		this.zipFlag = zipFlag;
	}
	public String getSmFlag() {
		return smFlag;
	}
	public void setSmFlag(String smFlag) {
		this.smFlag = smFlag;
	}
	
	
	
}