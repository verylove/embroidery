package cn.wind.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * spring获取bean工具类
 * @author xukk
 */
@Component
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtil.applicationContext = applicationContext;
	}
	private static Map<Class<?>, Object> beans = new HashMap<Class<?>, Object>();

	public static <T> T getBean(Class<T> requiredType) {
		if (beans.get(requiredType) != null) {
			return (T) beans.get(requiredType);
		} else {
			Object instance = SpringUtil.applicationContext .getBean(requiredType);
			beans.put(requiredType, instance);
			return (T) instance;
		}
	}


	public static Object getBean(String beanName) {
		return SpringUtil.applicationContext .getBean(beanName);
	}

	public static boolean containsBean(String beanName) {
		return SpringUtil.applicationContext .containsBean(beanName);
	}

	public static <T> Map<String, T> getBeansOfType(Class<T> requiredType) {
		return SpringUtil.applicationContext .getBeansOfType(requiredType);
	}

	public static void cleanHolder() {
		applicationContext = null;
	}

	public static String getProperty(String key) {
		return applicationContext.getBean(Environment.class).getProperty(key);
	}
}
