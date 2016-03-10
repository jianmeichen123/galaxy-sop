package com.galaxyinternet.common.dictEnum;

public class DictUtil {
	public static String getStatusName(String code){
		String name="";
		if(code.equals(DictEnum.taskStatus.待认领.getCode()))	{ 
			name=DictEnum.taskStatus.待认领.getName();
		}
		if(code.equals(DictEnum.taskStatus.待完工.getCode()))	{
			name=DictEnum.taskStatus.待完工.getName();
		}
		if(code.equals(DictEnum.taskStatus.已完成.getCode()))	{
			name=DictEnum.taskStatus.已完成.getName();
		}	
		return name;
	}
	public static String getTypeName(String code){
		String name="";
		if(code.equals(DictEnum.taskType.协同办公.getCode()))	{
			name=DictEnum.taskType.协同办公.getName();
		}
		if(code.equals(DictEnum.taskType.审批流程.getCode()))	{
			name=DictEnum.taskType.审批流程.getName();
		}
		return name;
	}
	public static String getTaskOrderName(Integer code){
		String name="";
		if(code==0)	{
			name="正常";
		}
		if(code==1)	{
			name="紧急";
		}
		if(code==2)	{
			name="特急";
		}
		return name;
	}

}
