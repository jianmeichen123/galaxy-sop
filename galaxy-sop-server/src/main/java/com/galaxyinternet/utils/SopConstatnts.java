package com.galaxyinternet.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface SopConstatnts {
	
	public interface Log{
		/**
		 * 告警日志记录{比如：删除数据等一些危险操作}
		 * eg:
		 * SopConstatnts.Log._alarm_logger_.info("alarm");
		 */
		public static final Logger _alarm_logger_ = LoggerFactory.getLogger("ALARM_LOG");
	}
	
	public interface Redis{
		//正在移交中的项目集，在redis对应的key
		public static final String _transfer_projects_key_ = "transfer_projects_key";
	}
	
	public interface TaskCode{
		//接收项目的taskFlag
		public static final int _accept_project_flag_ = 10;
	}
	
}
