package com.galaxyinternet.hologram.util;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListSortUtil<T> {
	 /** 
     * @param targetList 目标排序List 
     * @param sortField 排序字段(实体类属性名) 
     * @param sortMode 排序方式（asc or  desc） 
     */  
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void sort(List<T> targetList, final String sortField, final String sortMode) {

        Collections.sort(targetList, new Comparator() {
            public int compare(Object obj1, Object obj2) {
                int retVal = 0;
                try {
                    //首字母转大写
                    String newStr=sortField.substring(0, 1).toUpperCase()+sortField.replaceFirst("\\w","");
                    String methodStr="get"+newStr;

                    Method method1 = ((T)obj1).getClass().getMethod(methodStr, null);
                    Method method2 = ((T)obj2).getClass().getMethod(methodStr, null);
                    if (sortMode != null && "desc".equals(sortMode)) {
                        retVal = method2.invoke(((T) obj2), null).toString().compareTo(method1.invoke(((T) obj1), null).toString()); // 倒序
                    } else {
                        retVal = method1.invoke(((T) obj1), null).toString().compareTo(method2.invoke(((T) obj2), null).toString()); // 正序
                    }
                } catch (Exception e) {
                    throw new RuntimeException();
                }
                return retVal;
            }
        });
    }

    public void sortNumForNull(List<T> targetList, final String sortField, final String sortMode) {
        Collections.sort(targetList, new Comparator() {
            public int compare(Object obj1, Object obj2) {
                int retVal = 0;
                try {
                    //首字母转大写
                    String newStr=sortField.substring(0, 1).toUpperCase()+sortField.replaceFirst("\\w","");
                    String methodStr="get"+newStr;

                    Method method1 = ((T)obj1).getClass().getMethod(methodStr);
                    Method method2 = ((T)obj2).getClass().getMethod(methodStr);

                    String v1 = (String) method1.invoke(((T) obj1))==null?0+"":(String) method1.invoke(((T) obj1));
                    String v2 = (String) method2.invoke(((T) obj2))==null?0+"":(String) method2.invoke(((T) obj2));

                    Double d1 = Double.valueOf(v1);
                    Double d2 = Double.valueOf(v2);

                    if (sortMode != null && "desc".equals(sortMode)) {

                        retVal = (d2-d1) >= 0 ? 1 : -1; // 倒序
                    } else {
                        retVal = (d1-d2) > 0 ? 1 : -1; // 正序
                    }
                } catch (Exception e) {
                    throw new RuntimeException();
                }
                return retVal;
            }
        });
    }
}
