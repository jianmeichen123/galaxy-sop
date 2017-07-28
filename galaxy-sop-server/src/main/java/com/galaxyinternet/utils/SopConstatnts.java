package com.galaxyinternet.utils;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxyinternet.model.hologram.InformationTitle;

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
		
		//跳过 尽调阶段 的项目id集，在redis对应的key
		public static final String _GREEN_CHANNEL_6_ = "green_channel_6";
		
		
		//所有   签署证明  并发检查 key，
		public static final String _VOCHER_CHANNEL = "voucher_channel_key";
		//投资意向书阶段   签署证明 并发检查， 在redis中的list中的值，为   "voucher_channel_5_" + 项目id
		public static final String _VOCHER_CHANNEL_5_ = "voucher_channel_5_";
		
		
		//查询全部题 缓存key; value = Map<String,InformationTitle>废弃      value = Set<String> 
		public static final String ALL_TITLE_CACHE_CODE_KEY = "allTitleCacheCode";
		public static final String ALL_TITLE_VALUE_CACHE_PRE_KEY = "allTitleValueCachePreKey_"; // value = InformationTitle
		
	}
	
	public interface TaskCode{
		//接收项目的taskFlag
		public static final int _accept_project_flag_ = 10;
	}
	
	public interface TransferStatus{
		public static final int _undo_status_ = 1;
		public static final int _receive_status_ = 2;
		public static final int _reject_status_ = 3;
	}
	
	
	/**
	 * 绿色通道阶段标识
	 * 1：接触访谈，2：内部评审，3：CEO评审，4：立项会，5：投资意向书，6：尽职调查，7：投资决策会，8：投资协议，9：股权交割'
	 * */
	public interface GreanMark{
		String JCFT = "1";
		String NBPS = "2";
		String CEOPS = "3";
		String LXH = "4";
		String TZYXS = "5";
		String JZDC = "6";
		String TZJCH = "7";
		String TZXY = "8";
		String GQJG = "9";
		
	}
	
	
}
