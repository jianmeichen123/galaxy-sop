package com.galaxyinternet.model.sopfile;

import java.io.Serializable;

public class FileUtilModel implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String rid;
	private int wide;
	private int high;

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public int getWide() {
		return wide;
	}

	public void setWide(int wide) {
		this.wide = wide;
	}

	public int getHigh() {
		return high;
	}

	public void setHigh(int high) {
		this.high = high;
	}

}