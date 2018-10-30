package com.aboo.vbbs.base;

import org.springframework.context.ApplicationContext;

/**
 * 上下文环境持有工具
 * @author lizm
 *
 */
public class AppContextHolder {

	// Spring容器对象，环境上下文
	private static ApplicationContext applicationContext;

	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) applicationContext.getBean(name);
	}

	public static <T> T getBean(String name, Class<T> clazz) {
		return applicationContext.getBean(name, clazz);
	}
}
