package com.galaxyinternet.common.converter;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.http.converter.json.GsonHttpMessageConverter;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class CustomGsonHttpMessageConverter extends GsonHttpMessageConverter {

	public CustomGsonHttpMessageConverter() {
        // Creates the json object which will manage the information received
        GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        /*builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });*/
        builder.registerTypeAdapter(Timestamp.class, new JsonDeserializer<Timestamp>() {
        	public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        		return new Timestamp(json.getAsJsonPrimitive().getAsLong());
        	}
        });

        setGson(builder.create());
    }
}
