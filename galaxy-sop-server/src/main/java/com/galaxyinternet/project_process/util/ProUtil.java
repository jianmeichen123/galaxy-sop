package com.galaxyinternet.project_process.util;

import com.galaxyinternet.common.enums.DictEnum;
import com.galaxyinternet.model.project.Project;

public class ProUtil {

	
	public static String errMessage(Project project){
		if(project == null){
			return "项目检索为空";
		}else if(project.getProjectStatus().equals(DictEnum.meetingResult.否决.getCode())||project.getProjectStatus().equals(DictEnum.projectStatus.YFJ.getCode())){ //字典 项目状态 = 会议结论 关闭
			return "项目已经关闭";
		}else if(project.getProjectStatus().equals(DictEnum.projectStatus.YTC.getCode())){ //字典 项目状态 = 会议结论 关闭
			return "项目已退出";
		}
		return null;
	}
	
}
