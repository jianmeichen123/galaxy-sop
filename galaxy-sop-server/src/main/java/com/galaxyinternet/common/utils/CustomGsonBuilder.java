package com.galaxyinternet.common.utils;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galaxyinternet.framework.core.utils.DateUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import springfox.documentation.spring.web.json.Json;

public class CustomGsonBuilder {
	private static final Logger logger = LoggerFactory.getLogger(CustomGsonBuilder.class);
	private GsonBuilder builder = new GsonBuilder();
	public CustomGsonBuilder()
	{
		builder.registerTypeAdapter(Date.class, new DateAdapter());
		builder.registerTypeAdapter(Long.class, new LongAdapter());
		builder.registerTypeAdapter(Json.class, new SpringfoxJsonToGsonAdapter());
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
							e2.printStackTrace();
						}
					}
				}
			}
			return date;
		}
	}
	
	private static final class LongAdapter implements JsonDeserializer<Long>
	{
		@Override
		public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			Long result = null;
			try
			{
				result = json.getAsLong();
			} catch (Exception e)
			{
				logger.error("格式转化错误:"+json.toString(),e);
			}
			return result;
		}
		
	}
	
	private static final class SpringfoxJsonToGsonAdapter implements JsonSerializer<Json>
	{

		@Override
		public JsonElement serialize(Json json, Type typeOfSrc, JsonSerializationContext context)
		{
			final JsonParser parser = new JsonParser();
	        return parser.parse(json.value());
		}
	}
}
