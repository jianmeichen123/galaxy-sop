package com.galaxyinternet.common.utils;

import java.util.HashMap;
import java.util.Map;

public class StrUtils {


	/**
	 * 汉字编码，其它不编码
	 */
	public static String encodString(String str){
		StringBuffer sb = new StringBuffer(); 
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					//new Exception("encodString 将文件名中的汉字转为UTF8编码的串时错误，输入的字符串为：" + c);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}
	
	

	
	/**
	 * 文件名、后缀分离
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
	
	public static String join(String delimiter, Object... objs )
	{
		if(objs == null || objs.length==0)
		{
			return null;
		}
		StringBuilder str = new StringBuilder();
		int index = 0;
		for(Object obj : objs)
		{
			str.append(obj);
			if(index<objs.length-1)
			{
				str.append(delimiter);
			}
			index++;
		}
		
		return str.toString();
	}
	

	
}
