package com.galaxyinternet.common.constants;

import java.util.regex.Pattern;

public interface SopConstant {
	public static final Pattern _meeting_type_pattern_ = Pattern.compile("^meetingType:[0-9]");
	public static final Pattern _progress_pattern_ = Pattern.compile("^projectProgress:[0-9]");
	public static final Pattern _meeting_result_pattern_ = Pattern.compile("^meetingResult:[0-9]");
	public static final Pattern _file_type_pattern_ = Pattern.compile("^fileType:[0-9]");
	public static final Pattern _file_worktype_pattern_ = Pattern.compile("^fileWorktype:[0-9]");
	public static final Pattern _task_status_pattern_ = Pattern.compile("^taskStatus:[0-9]");
	//部门ID
	public static final long DEPARTMENT_RS_ID = 23L;
	public static final long DEPARTMENT_CW_ID = 21L;
	public static final long DEPARTMENT_FW_ID = 20L;
	
	
	
	//任务状态
	public static final int NORMAL_STATUS = 0;
	public static final int N_OVER_STATUS = 1;
	public static final int OVER_STATUS = 2;
	
	//任务名称
	public static final String TASK_NAME_WSJL = "完善简历";
	public static final int TASK_FLAG_WSJL = 0;
	public static final String TASK_NAME_SCTZYXS = "上传投资意向书";
	public static final int TASK_FLAG_SCTZYXS = 1;
	public static final String TASK_NAME_RSJD = "上传人事尽职调查报告";
	public static final int TASK_FLAG_RSJD = 2;
	public static final String TASK_NAME_FWJD = "上传法务尽职调查报告";
	public static final int TASK_FLAG_FWJD = 3;
	public static final String TASK_NAME_CWJD = "上传财务尽职调查报告";
	public static final int TASK_FLAG_CWJD = 4;
	public static final String TASK_NAME_YWJD = "上传业务尽职调查报告";
	public static final int TASK_FLAG_YWJD = 5;
	public static final String TASK_NAME_TZXY = "上传投资协议";
	public static final int TASK_FLAG_TZXY = 6;
	public static final String TASK_NAME_GQZR = "上传股权转让协议";
	public static final int TASK_FLAG_GQZR = 7;
	public static final String TASK_NAME_ZJBF = "上传资金拨付凭证";
	public static final int TASK_FLAG_ZJBF = 8;
	public static final String TASK_NAME_GSBG = "上传工商转让凭证";
	public static final int TASK_FLAG_GSBG = 9;
	
	//ceo评审
	static final String CEOPS_MEETING ="meetingType:2";
	//立项会类型
	static final String PROJECT_MEETING ="meetingType:3";
	//投诀会类型
	static final String VOTE_MEETING ="meetingType:4";
	
	//待办任务
	static final String TASK_MISSION_STATUS ="taskStatus:2";
	//紧急任务
	static final Integer TASK_URGENT_STATUS =1;
	
	
	public static final String CONFIG_KEY_IDEA_CODE = "idea_code";
	public static final String CONFIG_KEY_ZIXUN_CODE = "zixun_code";
	
	/**创意阶段 - 待认领**/
	public static final String IDEA_PROGRESS_DRL 			= "ideaProgress:1";
	/**创意阶段 - 调研**/
	public static final String IDEA_PROGRESS_DY 			= "ideaProgress:2";
	/**创意阶段 - 创建立项会**/
	public static final String IDEA_PROGRESS_CJLXH 			= "ideaProgress:3";
	/**创意阶段 - 搁置**/
	public static final String IDEA_PROGRESS_GZ				= "ideaProgress:4";
	/**创意阶段 - 创建项目**/
	public static final String IDEA_PROGRESS_CJXM 			= "ideaProgress:5";
	/**创意阶段 - 项目立项会**/
	public static final String IDEA_PROGRESS_LXH 			= "ideaProgress:6";
	/**创意阶段 - 项目投决会**/
	public static final String IDEA_PROGRESS_TJH 			= "ideaProgress:7";
	
	public static final String REQUEST_SCOPE_ATTR_IS_MENU 	= "_is_menu_";
	public static final String SESSION_SCOPE_ATTR_CUR_MENU 	= "_curr_menu_";
	
	/**投资**/
	public static final String BUSINESS_TYPE_TZ = "TZ"; 
	/**闪投**/
	public static final String BUSINESS_TYPE_ST = "ST"; 
	
	
	
	
}
