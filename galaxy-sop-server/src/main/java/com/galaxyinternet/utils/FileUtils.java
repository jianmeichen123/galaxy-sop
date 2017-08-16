package com.galaxyinternet.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.galaxyinternet.common.dictEnum.DictEnum;

public class FileUtils {
	private static List<WorktypeTask> worktypeTaskList;
	private static String[] fileTypeCodes = {
			DictEnum.fileType.文档.getCode(),
			DictEnum.fileType.音频文件.getCode(),
			DictEnum.fileType.视频文件.getCode(),
			DictEnum.fileType.图片.getCode()
	};
	private static String[][] fileTypes = {
			{"doc","docx","ppt","pptx","pps","xls","xlsx","pdf","txt","pages","key","numbers","DOC","DOCX","PPT","PPTX","PPS","XLS","XLSX","PDF","TXT","PAGES","KEY","NUMBER"},
			{"mp3","mp4","avi","wav","wma","aac","m4a","m4r","MP3","MP4","AVI","WAV","WMA","AAC","M4A","M4R"},
			{"avi,AVI"},
			{"bmp","jpg","gif","png","jpeg","BMP","JPG","GIF","PNG","JPEG"}
	};
	
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
	
	public static String getFileType(String suffix)
	{
		if(suffix == null) return null;
		for(int i =0; i<fileTypes.length;i++)
		{
			for(int j = 0; j<fileTypes[i].length;j++)
			{
				if(suffix.equals(fileTypes[i][j]))
				{
					return fileTypeCodes[i];
				}
			}
		}
		return null;
	}
	
	public static void base64ToFile(String data, File file)
	{
		if(data == null)
		{
			return;
		}
		OutputStream out = null;
		try
		{
			byte[] bytes = Base64.decodeBase64(data);
			out = new FileOutputStream(file);  
			out.write(bytes);  
			out.flush();  
			out.close(); 
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if(out != null)
			{
				try
				{
					out.close();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
		}
	}
	
	public static String file2Base64(File file)
	{
		InputStream in = null;  
		try
		{
			in = new FileInputStream(file);  
			byte[] data = new byte[in.available()];  
			in.read(data);  
			byte[] b = Base64.encodeBase64(data);
			return new String(b, "UTF-8");
			
		} catch (Exception e)
		{
			e.printStackTrace();
		} 
		finally
		{
			if(in != null)
			{
				try
				{
					in.close();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		String data = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEASABIAAD";
		String suffix = data.substring(data.indexOf("data:image/")+11,data.indexOf(";base64,"));
		System.out.println(suffix);
		data = data.substring(data.indexOf(";base64,")+8, data.length());
		System.out.println(data);
		
	}
}
