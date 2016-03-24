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
	

	
	static{
		roleRuleList = new ArrayList<RoleWorkTypeRule>();
		//档案管理员
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.DAGLY, DictEnum.fileWorktype.业务尽职调查报告.getCode(), "true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.DAGLY, DictEnum.fileWorktype.人力资源尽职调查报告.getCode(), "true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.DAGLY, DictEnum.fileWorktype.法务尽职调查报告.getCode(), "true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.DAGLY, DictEnum.fileWorktype.财务尽职调查报告.getCode(), "true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.DAGLY, DictEnum.fileWorktype.投资意向书.getCode(), "true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.DAGLY, DictEnum.fileWorktype.投资协议.getCode(), "true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.DAGLY, DictEnum.fileWorktype.股权转让协议.getCode(), "true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.DAGLY, DictEnum.fileWorktype.工商转让凭证.getCode(), "true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.DAGLY, DictEnum.fileWorktype.资金拨付凭证.getCode(), "true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.DAGLY, DictEnum.fileWorktype.商业计划.getCode(), "true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.DAGLY, DictEnum.fileWorktype.财务预测报告.getCode(), "true"));
		//HR总监
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRZJ, DictEnum.fileWorktype.业务尽职调查报告.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRZJ, DictEnum.fileWorktype.人力资源尽职调查报告.getCode(), "true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRZJ, DictEnum.fileWorktype.法务尽职调查报告.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRZJ, DictEnum.fileWorktype.财务尽职调查报告.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRZJ, DictEnum.fileWorktype.投资意向书.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRZJ, DictEnum.fileWorktype.投资协议.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRZJ, DictEnum.fileWorktype.股权转让协议.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRZJ, DictEnum.fileWorktype.工商转让凭证.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRZJ, DictEnum.fileWorktype.资金拨付凭证.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRZJ, DictEnum.fileWorktype.商业计划.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRZJ, DictEnum.fileWorktype.财务预测报告.getCode(), "false"));
		//HR经理
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRJL, DictEnum.fileWorktype.业务尽职调查报告.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRJL, DictEnum.fileWorktype.人力资源尽职调查报告.getCode(), "true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRJL, DictEnum.fileWorktype.法务尽职调查报告.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRJL, DictEnum.fileWorktype.财务尽职调查报告.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRJL, DictEnum.fileWorktype.投资意向书.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRJL, DictEnum.fileWorktype.投资协议.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRJL, DictEnum.fileWorktype.股权转让协议.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRJL, DictEnum.fileWorktype.工商转让凭证.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRJL, DictEnum.fileWorktype.资金拨付凭证.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRJL, DictEnum.fileWorktype.商业计划.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.HRJL, DictEnum.fileWorktype.财务预测报告.getCode(), "false"));
		//法务总监
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWZJ, DictEnum.fileWorktype.业务尽职调查报告.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWZJ, DictEnum.fileWorktype.人力资源尽职调查报告.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWZJ, DictEnum.fileWorktype.法务尽职调查报告.getCode(), "true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWZJ, DictEnum.fileWorktype.财务尽职调查报告.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWZJ, DictEnum.fileWorktype.投资意向书.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWZJ, DictEnum.fileWorktype.投资协议.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWZJ, DictEnum.fileWorktype.股权转让协议.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWZJ, DictEnum.fileWorktype.工商转让凭证.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWZJ, DictEnum.fileWorktype.资金拨付凭证.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWZJ, DictEnum.fileWorktype.商业计划.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWZJ, DictEnum.fileWorktype.财务预测报告.getCode(), "false"));
		//法务经理
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWJL, DictEnum.fileWorktype.业务尽职调查报告.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWJL, DictEnum.fileWorktype.人力资源尽职调查报告.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWJL, DictEnum.fileWorktype.法务尽职调查报告.getCode(), "true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWJL, DictEnum.fileWorktype.财务尽职调查报告.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWJL, DictEnum.fileWorktype.投资意向书.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWJL, DictEnum.fileWorktype.投资协议.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWJL, DictEnum.fileWorktype.股权转让协议.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWJL, DictEnum.fileWorktype.工商转让凭证.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWJL, DictEnum.fileWorktype.资金拨付凭证.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWJL, DictEnum.fileWorktype.商业计划.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.FWJL, DictEnum.fileWorktype.财务预测报告.getCode(), "false"));
		//财务总监
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWZJ, DictEnum.fileWorktype.业务尽职调查报告.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWZJ, DictEnum.fileWorktype.人力资源尽职调查报告.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWZJ, DictEnum.fileWorktype.法务尽职调查报告.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWZJ, DictEnum.fileWorktype.财务尽职调查报告.getCode(), "true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWZJ, DictEnum.fileWorktype.投资意向书.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWZJ, DictEnum.fileWorktype.投资协议.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWZJ, DictEnum.fileWorktype.股权转让协议.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWZJ, DictEnum.fileWorktype.工商转让凭证.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWZJ, DictEnum.fileWorktype.资金拨付凭证.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWZJ, DictEnum.fileWorktype.商业计划.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWZJ, DictEnum.fileWorktype.财务预测报告.getCode(), "false"));
		//财务经理
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWJL, DictEnum.fileWorktype.业务尽职调查报告.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWJL, DictEnum.fileWorktype.人力资源尽职调查报告.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWJL, DictEnum.fileWorktype.法务尽职调查报告.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWJL, DictEnum.fileWorktype.财务尽职调查报告.getCode(), "true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWJL, DictEnum.fileWorktype.投资意向书.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWJL, DictEnum.fileWorktype.投资协议.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWJL, DictEnum.fileWorktype.股权转让协议.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWJL, DictEnum.fileWorktype.工商转让凭证.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWJL, DictEnum.fileWorktype.资金拨付凭证.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWJL, DictEnum.fileWorktype.商业计划.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.CWJL, DictEnum.fileWorktype.财务预测报告.getCode(), "false"));
		//投资经理
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.TZJL, DictEnum.fileWorktype.业务尽职调查报告.getCode(), "true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.TZJL, DictEnum.fileWorktype.人力资源尽职调查报告.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.TZJL, DictEnum.fileWorktype.法务尽职调查报告.getCode(), "false"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.TZJL, DictEnum.fileWorktype.财务尽职调查报告.getCode(), "true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.TZJL, DictEnum.fileWorktype.投资意向书.getCode(), "true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.TZJL, DictEnum.fileWorktype.投资协议.getCode(), "true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.TZJL, DictEnum.fileWorktype.股权转让协议.getCode(), "true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.TZJL, DictEnum.fileWorktype.工商转让凭证.getCode(), "true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.TZJL, DictEnum.fileWorktype.资金拨付凭证.getCode(), "true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.TZJL, DictEnum.fileWorktype.商业计划.getCode(), "true"));
		roleRuleList.add(new RoleWorkTypeRule(UserConstant.TZJL, DictEnum.fileWorktype.财务预测报告.getCode(), "true"));	
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
	
	
	
	
	
	
	
}
