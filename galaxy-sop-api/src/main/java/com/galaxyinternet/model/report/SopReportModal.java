package com.galaxyinternet.model.report;

import java.util.List;

/**
 * 报表模板
 * @author leung
 *
 */
public class SopReportModal {
	
	/**
	 * 报表sheet页
	 */
	private int sheetPage;
	/**
	 * 数据起始行
	 */
	private int dataStartRow;
	
	/**
	 * 表头
	 */
	private BasicElement tableHeader;
	
	/**
	 * 附表头
	 */
	private BasicElement secondTableHeader;
	
	/**
	 * 抬头
	 */
	private List<BasicElement> columns;
	
	/**
	 * 模板名称
	 */
	private String templateName;
	
	/**
	 * 报表下载名称
	 */
	private String downloadName;
	
	/**
	 * 报表格式
	 */
	private String fileSuffix;
	
	/**
	 * 临时下载文件名称(不能配置)
	 */
	private String tempName;
	
	/**
	 * 临时文件生成路径
	 */
	private String downloadPath;
	
	
	
	
	

	public int getSheetPage() {
		return sheetPage;
	}

	public void setSheetPage(int sheetPage) {
		this.sheetPage = sheetPage;
	}

	public int getDataStartRow() {
		return dataStartRow;
	}

	public void setDataStartRow(int dataStartRow) {
		this.dataStartRow = dataStartRow;
	}

	public BasicElement getTableHeader() {
		return tableHeader;
	}
	public void setTableHeader(BasicElement tableHeader) {
		this.tableHeader = tableHeader;
	}
	public BasicElement getSecondTableHeader() {
		return secondTableHeader;
	}

	public void setSecondTableHeader(BasicElement secondTableHeader) {
		this.secondTableHeader = secondTableHeader;
	}

	public List<BasicElement> getColumns() {
		return columns;
	}

	public void setColumns(List<BasicElement> columns) {
		this.columns = columns;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getDownloadName() {
		return downloadName;
	}
	public void setDownloadName(String downloadName) {
		this.downloadName = downloadName;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public String getTempName() {
		return tempName;
	}

	public void setTempName(String tempName) {
		this.tempName = tempName;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}



	
	
	
	
	
	
	
}
