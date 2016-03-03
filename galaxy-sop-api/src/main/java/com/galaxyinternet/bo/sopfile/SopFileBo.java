package com.galaxyinternet.bo.sopfile;

import java.util.List;

import com.galaxyinternet.model.sopfile.SopFile;

public class SopFileBo extends SopFile {

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @author zfeng
	 * 根据文件业务类型查询 
	 */
	private List<String> fileworktypeList;

	public List<String> getFileworktypeList() {
		return fileworktypeList;
	}

	public void setFileworktypeList(List<String> fileworktypeList) {
		this.fileworktypeList = fileworktypeList;
	}


	
	

}
