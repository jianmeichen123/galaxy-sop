package com.galaxyinternet.project_process.util;

import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.model.project.Project;

public class ProUtil {

	
	public static String errMessage(Project project,String prograss){
		if(project == null){
			return "项目检索为空";
		}else if(project.getProjectStatus().equals(DictEnum.meetingResult.否决.getCode())||project.getProjectStatus().equals(DictEnum.projectStatus.YFJ.getCode())){ //字典 项目状态 = 会议结论 关闭
			return "项目已经关闭";
		}else if(project.getProjectStatus().equals(DictEnum.projectStatus.YTC.getCode())){ //字典 项目状态 = 会议结论 关闭
			return "项目已退出";
		}
		if(prograss != null){
			if(project.getProjectProgress()!=null){
				try {
					int operationPro = Integer.parseInt(prograss.substring(prograss.length()-1)) ;
					int projectPro = Integer.parseInt(project.getProjectProgress().substring(project.getProjectProgress().length()-1)) ;
					if(projectPro != operationPro){
						return "项目当前阶段不允许进行该操作";
					}
				} catch (Exception e) {
					return "项目阶段不和规范";
				}
			}else{
				return "项目阶段出错";
			}
		}
		return null;
	}
	
}
