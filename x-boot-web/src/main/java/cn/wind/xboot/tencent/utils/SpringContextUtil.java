package cn.wind.xboot.tencent.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/8/29 16:07
 * @Description:
 */
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    private void initInstance(ApplicationContext applicationContext)
    {
        if (this.applicationContext == null){
            this.applicationContext = applicationContext;
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException
    {
        initInstance(applicationContext);
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }
}
