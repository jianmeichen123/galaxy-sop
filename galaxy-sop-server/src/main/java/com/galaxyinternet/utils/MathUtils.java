package com.galaxyinternet.utils;

import java.text.NumberFormat;

public class MathUtils {
	
	private static NumberFormat numberFormat = NumberFormat.getInstance();
	
	
	public static String calculate(Long a, Long b,String compute, int reservation) {
		numberFormat.setMaximumFractionDigits(reservation);
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
	

	
	
	
}
