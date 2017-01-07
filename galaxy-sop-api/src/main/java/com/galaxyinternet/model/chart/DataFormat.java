package com.galaxyinternet.model.chart;

import java.util.List;

import com.galaxyinternet.framework.core.model.PagableEntity;
import com.galaxyinternet.model.report.DataReport;

/**
 * 项目
 * @author wangkun
 *
 */
public class DataFormat<T extends PagableEntity> extends DataReport  {
	
	private static final long serialVersionUID = 1L;
	
    private List<T>  list;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
    
    
   
	
}
