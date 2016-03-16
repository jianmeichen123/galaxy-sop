package com.galaxyinternet.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 用于记录用户的操作 <br>
 *              {@link com.galaxyinternet.common.annotation.LogType}
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Logger {
	/**
	 * @return 记录sop操作日志的范围，默认生成“消息提醒”中的日志
	 */
	LogType writeOperationScope() default LogType.MESSAGE;
}
