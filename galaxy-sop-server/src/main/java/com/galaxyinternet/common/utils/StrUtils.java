package com.galaxyinternet.common.utils;


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
					new Exception("encodString 将文件名中的汉字转为UTF8编码的串时错误，输入的字符串为：" + c);
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
	

	
}
