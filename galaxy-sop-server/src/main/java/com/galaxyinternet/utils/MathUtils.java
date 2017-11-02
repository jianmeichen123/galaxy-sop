package com.galaxyinternet.utils;

import java.text.NumberFormat;

public class MathUtils {
	
	private static NumberFormat numberFormat = NumberFormat.getInstance();
	
	
	public static String calculate(Long a, Long b,String compute, int reservation) {
		numberFormat.setMaximumFractionDigits(reservation);
		numberFormat.setGroupingUsed(false); 
		String result = null;
		switch (compute) {
		case "+":
			result = numberFormat.format((float) a + (float) b);
			break;
		case "-":
			result = numberFormat.format((float) a - (float) b);
			break;
		case "*":
			result = numberFormat.format((float) a * (float) b);
			break;
		case "/":
			result = numberFormat.format((float) a / (float) b);
			break;
		default:
			break;
		}
		return result;
	}
	
	public static String calculate(Float a, Float b,String compute, int reservation) {
		numberFormat.setMaximumFractionDigits(reservation);
		numberFormat.setGroupingUsed(false); 
		String result = null;
		switch (compute) {
		case "+":
			result = numberFormat.format((float) a + (float) b);
			break;
		case "-":
			result = numberFormat.format((float) a - (float) b);
			break;
		case "*":
			result = numberFormat.format((float) a * (float) b);
			break;
		case "/":
			result = numberFormat.format((float) a / (float) b);
			break;
		default:
			break;
		}
		return result;
	}
	
	public static String calculate(Long a, Float b,String compute, int reservation) {
		numberFormat.setMaximumFractionDigits(reservation);
		numberFormat.setGroupingUsed(false); 
		String result = null;
		switch (compute) {
		case "+":
			result = numberFormat.format((float) a + (float) b);
			break;
		case "-":
			result = numberFormat.format((float) a - (float) b);
			break;
		case "*":
			result = numberFormat.format((float) a * (float) b);
			break;
		case "/":
			result = numberFormat.format((float) a / (float) b);
			break;
		default:
			break;
		}
		return result;
	}
	
	public static String calculate(Float a, Long b,String compute, int reservation) {
		numberFormat.setMaximumFractionDigits(reservation);
		numberFormat.setGroupingUsed(false); 
		String result = null;
		switch (compute) {
		case "+":
			result = numberFormat.format((float) a + (float) b);
			break;
		case "-":
			result = numberFormat.format((float) a - (float) b);
			break;
		case "*":
			result = numberFormat.format((float) a * (float) b);
			break;
		case "/":
			result = numberFormat.format((float) a / (float) b);
			break;
		default:
			break;
		}
		return result;
	}
	
	public static String calculate(Double a, Double b,String compute, int reservation) {
		numberFormat.setMaximumFractionDigits(reservation);
		numberFormat.setGroupingUsed(false); 
		String result = null;
		switch (compute) {
		case "+":
			result = numberFormat.format((double) a + (double) b);
			break;
		case "-":
			result = numberFormat.format((double) a - (double) b);
			break;
		case "*":
			result = numberFormat.format((double) a * (double) b);
			break;
		case "/":
			result = numberFormat.format((double) a / (double) b);
			break;
		default:
			break;
		}
		return result;
	}
	
	public static void main(String [] args){
		String author="星河互联集团有限公司";
		if(author.length()>4){
			String str1=author.substring(0,2);
			String str2=author.substring(author.length()-2);
			String str3=author.substring(2,author.length()-2);
			String str4=str3.replaceAll("[^x00-xff]|\\w", "*");
			System.out.println(str1+str4+str2);
			
		}
	}
	

	
	
	
}
