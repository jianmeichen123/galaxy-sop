package com.galaxyinternet.model.report;

/**
 * 报表基本配置元素
 * @author leung
 *
 */
public class BasicElement{
	public static final String VALUE_DATE = "date";
	public static final String GETTERMETHOD_NUM = "num";
	/**
	 * 行索引
	 */
	private int row;
	/**
	 * 列索引
	 */
	private int column;
	/**
	 * 内容
	 */
	private String value;	
	/**
	 * 数据源get方法
	 */
	private String getterMethod;
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getGetterMethod() {
		return getterMethod;
	}
	public void setGetterMethod(String getterMethod) {
		this.getterMethod = getterMethod;
	}
}