package com.galaxyinternet.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.galaxyinternet.common.dictEnum.DictEnum;
import com.galaxyinternet.framework.core.constants.UserConstant;



/**
 * 权限验证工具类
 * @author zhongliangzhang
 *
 */
public class RoleUtils {
	
	private static List<RoleWorkTypeRule> roleRuleList;
	
	

	//档案管理角色权限及范围说明：展示并操作各用户角色涉及的所有档案数据，除去接触访谈、内部评审、CEO评审阶段涉及的文档
		//合伙人:可查看本事业部内所有项目档案，
		//1).合伙人：
		//查看范围为其事业线所有已上传的项目档案（包括签署凭证和非签署凭证）。
		//上传/更新：对于已上传的文档有更新权限；对于缺失的文档，只能是项目当前阶段的文档且属于自己的项目才允许上传，自身经办的项目上传/更新会影响当前项目的SOP流程。
		//2).投资经理：
		//查1看范围为本人经办的所有已上传的项目档案（包括签署凭证和非签署凭证）
		//上传/更新：对于已上传的文档有更新权限；对于缺失的文档，只能是项目当前阶段的文档才允许上传，且会影响当前项目的SOP流程。
		//3).人/财/法部门总监：
		//查看范围为其部门下经办阶段的所有已上传的项目档案。
		//上传/更新：对于已上传的文档有更新权限【对于自己认领的任务关联的文档可更新和上传项目当前阶段的文档】。
		//4).人/财/法部门经理
		//查看范围为本人经办项目的经办阶段的档案。
		//上传/更新：对于自己认领的任务关联的文档可更新和上传当前阶段的档案。
		//5).档案管理员
		//查看范围为系统内的所有项目的档案。
		//对于已上传的所有文档可以更新。
		//2、档案管理列表中增加"签署凭证"字段：即档案业务类型为“投资意向书、投资协议、股权转让协议”时，若签署凭证为“缺失”状态，则显示“上传”按钮，点击可上传相应的文档，若签署凭证为“已上传/已签署”状态，则展示其相应的签署凭证名称，并可下载、更新相应的档案，
		//除去以上档案业务类型时，该"签署凭证"属性默认值为“无”。	
	static{
		roleRuleList = new ArrayList<RoleWorkTypeRule>();
		//档案管理员
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.DAGLY, DictEnum.fileWorktype.业务尽职调查报告.getCode(), "true","true","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.DAGLY, DictEnum.fileWorktype.人力资源尽职调查报告.getCode(), "true","true","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.DAGLY, DictEnum.fileWorktype.法务尽职调查报告.getCode(), "true","true","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.DAGLY, DictEnum.fileWorktype.财务尽职调查报告.getCode(), "true","true","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.DAGLY, DictEnum.fileWorktype.投资意向书.getCode(), "true","true","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.DAGLY, DictEnum.fileWorktype.投资协议.getCode(), "true","true","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.DAGLY, DictEnum.fileWorktype.股权转让协议.getCode(), "true","true","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.DAGLY, DictEnum.fileWorktype.工商转让凭证.getCode(), "true","true","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.DAGLY, DictEnum.fileWorktype.资金拨付凭证.getCode(), "true","true","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.DAGLY, DictEnum.fileWorktype.商业计划.getCode(), "true","true","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.DAGLY, DictEnum.fileWorktype.财务预测报告.getCode(), "true","true","false","true"));
		//HR总监
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRZJ, DictEnum.fileWorktype.业务尽职调查报告.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRZJ, DictEnum.fileWorktype.人力资源尽职调查报告.getCode(), "true","true","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRZJ, DictEnum.fileWorktype.法务尽职调查报告.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRZJ, DictEnum.fileWorktype.财务尽职调查报告.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRZJ, DictEnum.fileWorktype.投资意向书.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRZJ, DictEnum.fileWorktype.投资协议.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRZJ, DictEnum.fileWorktype.股权转让协议.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRZJ, DictEnum.fileWorktype.工商转让凭证.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRZJ, DictEnum.fileWorktype.资金拨付凭证.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRZJ, DictEnum.fileWorktype.商业计划.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRZJ, DictEnum.fileWorktype.财务预测报告.getCode(), "false","false","false"));
		//HR经理
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRJL, DictEnum.fileWorktype.业务尽职调查报告.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRJL, DictEnum.fileWorktype.人力资源尽职调查报告.getCode(), "true","true","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRJL, DictEnum.fileWorktype.法务尽职调查报告.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRJL, DictEnum.fileWorktype.财务尽职调查报告.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRJL, DictEnum.fileWorktype.投资意向书.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRJL, DictEnum.fileWorktype.投资协议.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRJL, DictEnum.fileWorktype.股权转让协议.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRJL, DictEnum.fileWorktype.工商转让凭证.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRJL, DictEnum.fileWorktype.资金拨付凭证.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRJL, DictEnum.fileWorktype.商业计划.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRJL, DictEnum.fileWorktype.财务预测报告.getCode(), "false","false","false"));
		//法务总监
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWZJ, DictEnum.fileWorktype.业务尽职调查报告.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWZJ, DictEnum.fileWorktype.人力资源尽职调查报告.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWZJ, DictEnum.fileWorktype.法务尽职调查报告.getCode(), "true","true","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWZJ, DictEnum.fileWorktype.财务尽职调查报告.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWZJ, DictEnum.fileWorktype.投资意向书.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWZJ, DictEnum.fileWorktype.投资协议.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWZJ, DictEnum.fileWorktype.股权转让协议.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWZJ, DictEnum.fileWorktype.工商转让凭证.getCode(), "true","true","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWZJ, DictEnum.fileWorktype.资金拨付凭证.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWZJ, DictEnum.fileWorktype.商业计划.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWZJ, DictEnum.fileWorktype.财务预测报告.getCode(), "false","false","false"));
		//法务经理
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWJL, DictEnum.fileWorktype.业务尽职调查报告.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWJL, DictEnum.fileWorktype.人力资源尽职调查报告.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWJL, DictEnum.fileWorktype.法务尽职调查报告.getCode(), "true","true","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWJL, DictEnum.fileWorktype.财务尽职调查报告.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWJL, DictEnum.fileWorktype.投资意向书.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWJL, DictEnum.fileWorktype.投资协议.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWJL, DictEnum.fileWorktype.股权转让协议.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWJL, DictEnum.fileWorktype.工商转让凭证.getCode(), "true","true","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWJL, DictEnum.fileWorktype.资金拨付凭证.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWJL, DictEnum.fileWorktype.商业计划.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWJL, DictEnum.fileWorktype.财务预测报告.getCode(), "false","false","false"));
		//财务总监
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWZJ, DictEnum.fileWorktype.业务尽职调查报告.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWZJ, DictEnum.fileWorktype.人力资源尽职调查报告.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWZJ, DictEnum.fileWorktype.法务尽职调查报告.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWZJ, DictEnum.fileWorktype.财务尽职调查报告.getCode(), "true","true","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWZJ, DictEnum.fileWorktype.投资意向书.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWZJ, DictEnum.fileWorktype.投资协议.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWZJ, DictEnum.fileWorktype.股权转让协议.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWZJ, DictEnum.fileWorktype.工商转让凭证.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWZJ, DictEnum.fileWorktype.资金拨付凭证.getCode(), "true","true","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWZJ, DictEnum.fileWorktype.商业计划.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWZJ, DictEnum.fileWorktype.财务预测报告.getCode(), "false","false","false"));
		//财务经理
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWJL, DictEnum.fileWorktype.业务尽职调查报告.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWJL, DictEnum.fileWorktype.人力资源尽职调查报告.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWJL, DictEnum.fileWorktype.法务尽职调查报告.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWJL, DictEnum.fileWorktype.财务尽职调查报告.getCode(), "true","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWJL, DictEnum.fileWorktype.投资意向书.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWJL, DictEnum.fileWorktype.投资协议.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWJL, DictEnum.fileWorktype.股权转让协议.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWJL, DictEnum.fileWorktype.工商转让凭证.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWJL, DictEnum.fileWorktype.资金拨付凭证.getCode(), "true","true","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWJL, DictEnum.fileWorktype.商业计划.getCode(), "false","false","false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWJL, DictEnum.fileWorktype.财务预测报告.getCode(), "false","false","false"));
		//合伙人
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HHR, DictEnum.fileWorktype.业务尽职调查报告.getCode(), "false","true","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HHR, DictEnum.fileWorktype.人力资源尽职调查报告.getCode(), "false","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HHR, DictEnum.fileWorktype.法务尽职调查报告.getCode(), "false","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HHR, DictEnum.fileWorktype.财务尽职调查报告.getCode(), "false","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HHR, DictEnum.fileWorktype.投资意向书.getCode(), "false","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HHR, DictEnum.fileWorktype.投资协议.getCode(), "false","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HHR, DictEnum.fileWorktype.股权转让协议.getCode(), "false","true","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HHR, DictEnum.fileWorktype.工商转让凭证.getCode(), "false","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HHR, DictEnum.fileWorktype.资金拨付凭证.getCode(), "false","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HHR, DictEnum.fileWorktype.商业计划.getCode(), "false","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HHR, DictEnum.fileWorktype.财务预测报告.getCode(), "false","false","true"));
		//投资经理
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.TZJL, DictEnum.fileWorktype.业务尽职调查报告.getCode(), "true","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.TZJL, DictEnum.fileWorktype.人力资源尽职调查报告.getCode(), "false","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.TZJL, DictEnum.fileWorktype.法务尽职调查报告.getCode(), "false","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.TZJL, DictEnum.fileWorktype.财务尽职调查报告.getCode(), "false","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.TZJL, DictEnum.fileWorktype.投资意向书.getCode(), "true","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.TZJL, DictEnum.fileWorktype.投资协议.getCode(), "true","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.TZJL, DictEnum.fileWorktype.股权转让协议.getCode(), "true","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.TZJL, DictEnum.fileWorktype.工商转让凭证.getCode(), "false","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.TZJL, DictEnum.fileWorktype.资金拨付凭证.getCode(), "false","false","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.TZJL, DictEnum.fileWorktype.商业计划.getCode(), "true","true","true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.TZJL, DictEnum.fileWorktype.财务预测报告.getCode(), "true","true","true"));
		
		
	}
	
	/**
	 * 判断是否为董事长
	 * @param roleList
	 * @return
	 */
	public static boolean isDSZ(List<Long> roleList){
		if(roleList.contains(UserConstant.DSZ)){
			return true;
		}
		return false;
	}
	/**
	 * 判断是否为CEO
	 * @param roleList
	 * @return
	 */
	public static boolean isCEO(List<Long> roleList){
		if(roleList.contains(UserConstant.CEO)){
			return true;
		}
		return false;
	}
	
	public static boolean isGaoGuan(List<Long> roleList){
		if(roleList.contains(UserConstant.CEO) || roleList.contains(UserConstant.DSZ)){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否为梅西
	 * @param roleList
	 * @return
	 */
	public static boolean isMs(List<Long> roleList){
		if(roleList.contains(UserConstant.CEOMS) || roleList.contains(UserConstant.DMS)){
			return true;
		}
		return false;
	}
	/**
	 * 判断是否为合伙人
	 * @param roleList
	 * @return
	 */
	public static boolean isHHR(List<Long> roleList){
		if(roleList.contains(UserConstant.HHR)){
			return true;
		}
		return false;
	}
	/**
	 * 判断是否为投资经理
	 * @param roleList
	 * @return
	 */
	public static boolean isTZJL(List<Long> roleList){
		if(roleList.contains(UserConstant.TZJL)){
			return true;
		}
		return false;
	}
	/**
	 * 判断是否为项目运营负责人
	 * @param roleList
	 * @return
	 */
	public static boolean isYYFZR(List<Long> roleList){
		if(roleList.contains(UserConstant.YYFZR)){
			return true;
		}
		return false;
	}
	/**
	 * 判断是否为项目HR总监
	 * @param roleList
	 * @return
	 */
	public static boolean isHRZJ(List<Long> roleList){
		if(roleList.contains(UserConstant.HRZJ)){
			return true;
		}
		return false;
	}
	/**
	 * 判断是否为项目HR经理
	 * @param roleList
	 * @return
	 */
	public static boolean isHRJL(List<Long> roleList){
		if(roleList.contains(UserConstant.HRJL)){
			return true;
		}
		return false;
	}
	/**
	 * 判断是否为法务总监
	 * @param roleList
	 * @return
	 */
	public static boolean isFWZJ(List<Long> roleList){
		if(roleList.contains(UserConstant.FWZJ)){
			return true;
		}
		return false;
	}
	/**
	 * 判断是否为法务经理
	 * @param roleList
	 * @return
	 */
	public static boolean isFWJL(List<Long> roleList){
		if(roleList.contains(UserConstant.FWJL)){
			return true;
		}
		return false;
	}
	/**
	 * 判断是否为财务总监
	 * @param roleList
	 * @return
	 */
	public static boolean isCWZJ(List<Long> roleList){
		if(roleList.contains(UserConstant.CWZJ)){
			return true;
		}
		return false;
	}
	/**
	 * 判断是否为财务总监
	 * @param roleList
	 * @return
	 */
	public static boolean isCWJL(List<Long> roleList){
		if(roleList.contains(UserConstant.CWJL)){
			return true;
		}
		return false;
	}
	/**
	 * 判断是否为档案管理员
	 * @param roleList
	 * @return
	 */
	public static boolean isDAGLY(List<Long> roleList){
		if(roleList.contains(UserConstant.DAGLY)){
			return true;
		}
		return false;
	}
	/**
	 * 判断是否为管理员
	 * @param roleList
	 * @return
	 */
	public static boolean isGLY(List<Long> roleList){
		if(roleList.contains(UserConstant.GLY)){
			return true;
		}
		return false;
	}
	/**
	 * 判断角色对响应业务分类是否可操作
	 * @param roleList
	 * @param workType
	 * @return true 可操作 false 不可操作 默认false
	 */
	public static String getWorkTypeEdit(List<Long> roleList,String workType){
		for(RoleWorkTypeRule roleRule : roleRuleList){
			if(roleList!=null && roleList.size() > 0 && !StringUtils.isBlank(workType)){
				if(roleList.contains(roleRule.getRoleId()) && workType.equals(roleRule.getWorkType())){
					return roleRule.getIsEdit();
				}
			}	
		}
		return "false";
	}
	
	public static String getWorktypeChangeTask(List<Long> roleList,String workType){
		for(RoleWorkTypeRule roleRule : roleRuleList){
			if(roleList!=null && roleList.size() > 0 && !StringUtils.isBlank(workType)){
				if(roleList.contains(roleRule.getRoleId()) && workType.equals(roleRule.getWorkType())){
					return roleRule.getIsChangeTask();
				}
			}	
		}
		return "false";
	}
	
	public static String getWorktypeProveEdit(List<Long> roleList,String workType){
		for(RoleWorkTypeRule roleRule : roleRuleList){
			if(roleList!=null && roleList.size() > 0 && !StringUtils.isBlank(workType)){
				if(roleList.contains(roleRule.getRoleId()) && workType.equals(roleRule.getWorkType())){
					return roleRule.getIsProveEdit();
				}
			}	
		}
		return "false";
	}
	
	/**
	 * 根据角色获取其对应 ‘是否显示’的业务分类
	 * @param roleList
	 * @param isShow true:获取可显示 false:获取不可显示 
	 * @return
	 */
	public static List<RoleWorkTypeRule> getWorktypeByShow(List<Long> roleList,String isShow){
		List<RoleWorkTypeRule> retList = new ArrayList<RoleWorkTypeRule>();
		if(roleList != null && roleList.size() > 0 && !StringUtils.isBlank(isShow)){
			for(RoleWorkTypeRule roleRule : roleRuleList){
				if(roleList.contains(roleRule.getRoleId()) && isShow.equals(roleRule.getIsShow())){
					retList.add(roleRule);
				}	
			}
		}
		return retList;
	}
	
	
	
	
	
	
	
	
}
