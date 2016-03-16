package com.galaxyinternet.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 用于记录用户的操作 <br>
 * 注意：beanName和beanClass的配置是有优先级的，
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Logger {
	/**
	 * @return 是否记录sop的操作日志，默认“否”
	 */
	boolean writeSopOperationLog() default false;
}
