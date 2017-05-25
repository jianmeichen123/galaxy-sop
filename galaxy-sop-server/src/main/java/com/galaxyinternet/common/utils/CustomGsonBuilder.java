package com.galaxyinternet.common.utils;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Date;

import com.galaxyinternet.framework.core.utils.DateUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class CustomGsonBuilder {
	
	private GsonBuilder builder = new GsonBuilder();
	public CustomGsonBuilder()
	{
		builder.registerTypeAdapter(Date.class, new DateAdapter());
	}
	
	public Gson create()
	{
		return builder.create();
	}
	
	private static final class DateAdapter implements JsonDeserializer<Date>
	{

		@Override
		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			Date date = null;
			if(json != null)
			{
				
				try {
					date = DateUtil.convertStringToDateTime(json.getAsString());
				} catch (ParseException e) {
					try {
						date = DateUtil.convertStringToDateTimeForChina(json.getAsString());
					} catch (Exception e1) {
						try {
							date = DateUtil.convertStringToDate(json.getAsString());
						} catch (ParseException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
				}
				
			}
			return date;
		}

		
	}
}
