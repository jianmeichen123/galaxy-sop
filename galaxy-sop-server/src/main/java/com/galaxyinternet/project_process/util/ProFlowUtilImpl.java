package com.galaxyinternet.project_process.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.galaxyinternet.framework.core.constants.UserConstant;


@Service("com.galaxyinternet.project_process.util.ProFlowUtil")
public class ProFlowUtilImpl implements ProFlowUtil {

	final Logger logger = LoggerFactory.getLogger(ProFlowUtilImpl.class);
	
	public static final String fileTypePareatCode = "fileWorktype";
	
	// projectProgress  :  <fileWorktype : task_flag(other) >
	public static Map<String,Map<String,Integer>>  file_about = new LinkedHashMap<>();
	
	
	// canOpt;    // 文档可操作： 上传、编辑
	// projectProgress  :  <fileWorktype : canOpt(true\false) >
	public static Map<String,Map<String,Boolean>>  file_opt_about_tzjl = new LinkedHashMap<>();
	public static Map<String,Map<String,Boolean>>  file_opt_about_r = new LinkedHashMap<>();
	public static Map<String,Map<String,Boolean>>  file_opt_about_f = new LinkedHashMap<>();
	public static Map<String,Map<String,Boolean>>  file_opt_about_c = new LinkedHashMap<>();
	
	
	public static Map<Long,Map<String,Map<String,Boolean>>>  role_file_opt_about = new LinkedHashMap<>();
	static{
		role_file_opt_about.put(UserConstant.TZJL, file_opt_about_tzjl);
		role_file_opt_about.put(UserConstant.HRJL, file_opt_about_r);
		role_file_opt_about.put(UserConstant.FWJL, file_opt_about_f);
		role_file_opt_about.put(UserConstant.CWJL, file_opt_about_c);
	}
	
	
	static{
		/*
		 * 阶段：  
		 * 		"立项会" : "projectProgress:4"  
		 * 
		 * 文档：
		 * 		"fileWorktype:17", "立项报告 "
		 */
		Map<String,Integer> m_p4 = new LinkedHashMap<>();
		m_p4.put("fileWorktype:17", null);
		file_about.put("projectProgress:4", m_p4);
		
		/*   
		 * 阶段：  
		 * 		"投资意向书","projectProgress:5" 
		 * 文档：
		 * 		"fileWorktype:5", "投资意向书 " (签署凭证)
		 * 任务（task_flag）：
		 * 			1	投资意向书（tzjl）
		 */
		Map<String,Integer> m_p5 = new LinkedHashMap<>();
		m_p5.put("fileWorktype:5", null);   
		file_about.put("projectProgress:5", m_p5);
		
		/*   
		 * 阶段：  
		 * 		"尽职调查","projectProgress:6"  
		 * 文档：
		 * 		"fileWorktype:1",  "业务尽职调查报告 "
		 * 		"fileWorktype:2",  "人力资源尽职调查报告 "
		 * 		"fileWorktype:3",  "法务尽职调查报告 "      (仅 投资类型 有)
		 *  	"fileWorktype:4",  "财务尽职调查报告 "      (仅 投资类型 有)
		 * 		"fileWorktype:18", "尽职调查启动会报告 "
		 *		"fileWorktype:19", "尽职调查总结会报告 "
		 * 
		 * task_flag(任务标识):
		 * 			2	人事尽职调查报告、
		 * 			3	法务尽职调查报告、
		 * 			4	财务尽调报告、
		 * 			5	业务尽调报告（tzjl）
		 */
		Map<String,Integer> m_p6 = new LinkedHashMap<>();
		m_p6.put("fileWorktype:1", null);
		m_p6.put("fileWorktype:2", 2);
		m_p6.put("fileWorktype:3", 3);   
		m_p6.put("fileWorktype:4", 4);  
		m_p6.put("fileWorktype:18", null);
		m_p6.put("fileWorktype:19", null);
		file_about.put("projectProgress:6", m_p6);
  		
		/*   
		 * 阶段：  
		 * 		"投资协议","projectProgress:8"  
		 * 文档：  
		 * 		"fileWorktype:6", "投资协议 "       (签署凭证)
		 * 		"fileWorktype:7", "股权转让协议 "   (仅 投资类型  且  涉及股权转让)、 (签署凭证)
		 * 
		 * task_flag(任务标识): 
		 * 			6	投资协议               （tzjl）
		 * 			7	股权转让协议、（tzjl）
		 */
		Map<String,Integer> m_p8 = new LinkedHashMap<>();
		m_p8.put("fileWorktype:6", null);      
		m_p8.put("fileWorktype:7", null); 
		file_about.put("projectProgress:8", m_p8);
		
		/*   
		 * 阶段：  
		 * 		"股权交割","projectProgress:9"   
		 * 文档：  
		 * 		"fileWorktype:8", "工商转让凭证 "
		 * 		"fileWorktype:9", "资金拨付凭证 "
		 * 
		 * task_flag(任务标识): 
		 * 			8	资金拨付凭证、
		 * 			9	工商变更登记凭证
		 * 
		 */
		Map<String,Integer> m_p9 = new LinkedHashMap<>();
		m_p9.put("fileWorktype:8", 9);
		m_p9.put("fileWorktype:9", 8);
		file_about.put("projectProgress:9", m_p9);
	}
	
	
	
	static{
		/*
		 * 阶段：  
		 * 		"立项会" : "projectProgress:4"  
		 * 
		 * 文档：
		 * 		"fileWorktype:17", "立项报告 "
		 */
		Map<String,Boolean> m_p4 = new LinkedHashMap<>();
		m_p4.put("fileWorktype:17", true);
		file_opt_about_tzjl.put("projectProgress:4", m_p4);
		
		/*   
		 * 阶段：  
		 * 		"投资意向书","projectProgress:5" 
		 * 文档：
		 * 		"fileWorktype:5", "投资意向书 " (签署凭证)
		 */
		Map<String,Boolean> m_p5 = new LinkedHashMap<>();
		m_p5.put("fileWorktype:5", true);   
		file_opt_about_tzjl.put("projectProgress:5", m_p5);
		
		/*   
		 * 阶段：  
		 * 		"尽职调查","projectProgress:6"  
		 * 文档：
		 * 		"fileWorktype:1",  "业务尽职调查报告 "
		 * 		"fileWorktype:2",  "人力资源尽职调查报告 "
		 * 		"fileWorktype:3",  "法务尽职调查报告 "      (仅 投资类型 有)
		 *  	"fileWorktype:4",  "财务尽职调查报告 "      (仅 投资类型 有)
		 * 		"fileWorktype:18", "尽职调查启动会报告 "
		 *		"fileWorktype:19", "尽职调查总结会报告 "
		 */
		Map<String,Boolean> m_p6 = new LinkedHashMap<>();
		m_p6.put("fileWorktype:1", true);
		m_p6.put("fileWorktype:2", false);
		m_p6.put("fileWorktype:3", false);   
		m_p6.put("fileWorktype:4", false);  
		m_p6.put("fileWorktype:18", true);
		m_p6.put("fileWorktype:19", true);
		file_opt_about_tzjl.put("projectProgress:6", m_p6);
  		
		/*   
		 * 阶段：  
		 * 		"投资协议","projectProgress:8"  
		 * 文档：  
		 * 		"fileWorktype:6", "投资协议 "       (签署凭证)
		 * 		"fileWorktype:7", "股权转让协议 "   (仅 投资类型  且  涉及股权转让)、 (签署凭证)
		 */
		Map<String,Boolean> m_p8 = new LinkedHashMap<>();
		m_p8.put("fileWorktype:6", true);      
		m_p8.put("fileWorktype:7", true); 
		file_opt_about_tzjl.put("projectProgress:8", m_p8);
		
	}

	static{
		/*   
		 * 阶段：  
		 * 		"尽职调查","projectProgress:6"  
		 * 文档：
		 * 		"fileWorktype:1",  "业务尽职调查报告 "
		 * 		"fileWorktype:2",  "人力资源尽职调查报告 "
		 * 		"fileWorktype:3",  "法务尽职调查报告 "      (仅 投资类型 有)
		 *  	"fileWorktype:4",  "财务尽职调查报告 "      (仅 投资类型 有)
		 * 		"fileWorktype:18", "尽职调查启动会报告 "
		 *		"fileWorktype:19", "尽职调查总结会报告 "
		 */
		Map<String,Boolean> m_p6 = new LinkedHashMap<>();
		m_p6.put("fileWorktype:1", false);
		m_p6.put("fileWorktype:2", true);
		m_p6.put("fileWorktype:3", false);   
		m_p6.put("fileWorktype:4", false);  
		m_p6.put("fileWorktype:18", false);
		m_p6.put("fileWorktype:19", false);
		file_opt_about_r.put("projectProgress:6", m_p6);
	}
	
	static{
		/*   
		 * 阶段：  
		 * 		"尽职调查","projectProgress:6"  
		 * 文档：
		 * 		"fileWorktype:1",  "业务尽职调查报告 "
		 * 		"fileWorktype:2",  "人力资源尽职调查报告 "
		 * 		"fileWorktype:3",  "法务尽职调查报告 "      (仅 投资类型 有)
		 *  	"fileWorktype:4",  "财务尽职调查报告 "      (仅 投资类型 有)
		 * 		"fileWorktype:18", "尽职调查启动会报告 "
		 *		"fileWorktype:19", "尽职调查总结会报告 "
		 */
		Map<String,Boolean> m_p6 = new LinkedHashMap<>();
		m_p6.put("fileWorktype:1", false);
		m_p6.put("fileWorktype:2", false);
		m_p6.put("fileWorktype:3", true);   
		m_p6.put("fileWorktype:4", false);  
		m_p6.put("fileWorktype:18", false);
		m_p6.put("fileWorktype:19", false);
		file_opt_about_f.put("projectProgress:6", m_p6);
  		
		/*   
		 * 阶段：  
		 * 		"股权交割","projectProgress:9"   
		 * 文档：  
		 * 		"fileWorktype:8", "工商转让凭证 "
		 * 		"fileWorktype:9", "资金拨付凭证 "
		 */
		Map<String,Boolean> m_p9 = new LinkedHashMap<>();
		m_p9.put("fileWorktype:8", true);
		m_p9.put("fileWorktype:9", false);
		file_opt_about_f.put("projectProgress:9", m_p9);
	}
	
	static{
		/*   
		 * 阶段：  
		 * 		"尽职调查","projectProgress:6"  
		 * 文档：
		 * 		"fileWorktype:1",  "业务尽职调查报告 "
		 * 		"fileWorktype:2",  "人力资源尽职调查报告 "
		 * 		"fileWorktype:3",  "法务尽职调查报告 "      (仅 投资类型 有)
		 *  	"fileWorktype:4",  "财务尽职调查报告 "      (仅 投资类型 有)
		 * 		"fileWorktype:18", "尽职调查启动会报告 "
		 *		"fileWorktype:19", "尽职调查总结会报告 "
		 */
		Map<String,Boolean> m_p6 = new LinkedHashMap<>();
		m_p6.put("fileWorktype:1", false);
		m_p6.put("fileWorktype:2", false);
		m_p6.put("fileWorktype:3", false);   
		m_p6.put("fileWorktype:4", true);  
		m_p6.put("fileWorktype:18", false);
		m_p6.put("fileWorktype:19", false);
		file_opt_about_c.put("projectProgress:6", m_p6);
  		
		
		/*   
		 * 阶段：  
		 * 		"股权交割","projectProgress:9"   
		 * 文档：  
		 * 		"fileWorktype:8", "工商转让凭证 "
		 * 		"fileWorktype:9", "资金拨付凭证 "
		 */
		Map<String,Boolean> m_p9 = new LinkedHashMap<>();
		m_p9.put("fileWorktype:8", false);
		m_p9.put("fileWorktype:9", true);
		file_opt_about_c.put("projectProgress:9", m_p9);
	}
	
	
	
	
	
	
}