package com.galaxyinternet.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtils {
	private static List<WorktypeTask> worktypeTaskList;
	
	static{
		worktypeTaskList = new ArrayList<WorktypeTask>();
		worktypeTaskList.add(new WorktypeTask(1,5,true));
		worktypeTaskList.add(new WorktypeTask(2,2,false));
		worktypeTaskList.add(new WorktypeTask(3,3,false));
		worktypeTaskList.add(new WorktypeTask(4,4,false));
		worktypeTaskList.add(new WorktypeTask(5,1,false));
		worktypeTaskList.add(new WorktypeTask(6,6,true));
		worktypeTaskList.add(new WorktypeTask(7,7,true));
		worktypeTaskList.add(new WorktypeTask(8,8,false));
		worktypeTaskList.add(new WorktypeTask(9,9,false));
	}
	
	/**
	 * 通过业务分类查找任务类型
	 * @param worktype
	 * @return
	 */
	public static int getTaskByWorktype(int worktype){
		for(WorktypeTask worktypeTask : worktypeTaskList){
			if(worktype == worktypeTask.getWorktype()){
				return worktypeTask.getTaskFlag();
			}
		}
		return -1;
	}

	
	/**
	 * 通过任务类型查找业务分类
	 * @param worktype
	 * @return
	 */
	public static int getWorktypeByTask(int taskFlag){
		for(WorktypeTask worktypeTask : worktypeTaskList){
			if(taskFlag == worktypeTask.getTaskFlag()){
				return worktypeTask.getWorktype();
			}
		}
		return -1;
	}
	
	/**
	 * 通过任务类型查找业务分类
	 * @param worktype
	 * @return
	 */
	public static WorktypeTask getWorktypeEntityByTask(int worktype){
		for(WorktypeTask worktypeTask : worktypeTaskList){
			if(worktype == worktypeTask.getWorktype()){
				return worktypeTask;
			}
		}
		return null;
	}
	
	
	
	/**
	 * 获取文件名称和后缀
	 * @param fileName
	 * @return map <String,String> 
	 * fileName:文件名称          
	 * fileSuffix:文件后缀
	 */
	public static Map<String, String> transFileNames(String fileName) {
		Map<String, String> retMap = new HashMap<String, String>();
		int dotPos = fileName.lastIndexOf(".");
		if(dotPos == -1){
			retMap.put("fileName", fileName);
			retMap.put("fileSuffix", "");
		}else{
			retMap.put("fileName", fileName.substring(0, dotPos));
			retMap.put("fileSuffix", fileName.substring(dotPos+1));
		}
		return retMap;
	}
}
