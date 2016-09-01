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
	
	
	

	
	
	
}
