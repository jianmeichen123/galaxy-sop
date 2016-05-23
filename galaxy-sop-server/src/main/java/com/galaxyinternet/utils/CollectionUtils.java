package com.galaxyinternet.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

public class CollectionUtils {
	
	public static <T> T getItem(Collection<T> items, String propName, Object propVal)
	{
		if(propVal != null && items != null && items.size() > 0)
		{
			for(T item : items)
			{
				BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(item);
				Object val = wrapper.getPropertyValue(propName);
				if(propVal.equals(val))
				{
					return item;
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getItemProp(Collection<?> items, String propName, Object propVal, String targetPropName)
	{
		if(propVal != null && items != null && items.size() > 0)
		{
			for(Object item : items)
			{
				BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(item);
				Object val = wrapper.getPropertyValue(propName);
				Object targetVal = wrapper.getPropertyValue(targetPropName);
				if(propVal.equals(val))
				{
					return (T) targetVal;
				}
			}
		}
		return null;
	}
	
	public static List<String> string2List(String src, String delimiter)
	{
		if(src != null && delimiter != null)
		{
			return Arrays.asList(src.split(delimiter));
		}
		return null;
	}

}
