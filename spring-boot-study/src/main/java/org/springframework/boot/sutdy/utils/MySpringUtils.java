package org.springframework.boot.sutdy.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by TJS on 2019/3/11
 * FIXME: 2020/12/16 如果有多个,只会取加载顺序的最后一个
 */
@Component//必须要加入ioc容器，才会在启动的时候回调ApplicationContextAware.setApplicationContext方法
//@Lazy(false)//不加载多余的bean  默认false
public class MySpringUtils implements ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(MySpringUtils.class);
    private static ApplicationContext APPLICATION_CONTEXT;

    /**
     * 设置spring上下文  *  * @param applicationContext spring上下文  * @throws BeansException
     * todo-tjs:测试使用这种方式获取bean是否可以跨模块跨继承关系调用
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LOGGER.debug("ApplicationContext registed-->{}", applicationContext);
        APPLICATION_CONTEXT = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

    /**
     * 注意 bean name默认 = 类名(首字母小写)
     * 例如: A8sClusterDao = getBean("a8sClusterDao")
     *
     * @param beanName
     * @return
     * @throws BeansException
     */
//    public static <T> T getBeanByBeanName(String beanName) {
//        //Class classz=Class.forName(className).getClass().newInstance();
//        try {
//            return (T) MySpringUtils.getApplicationContext().getBean(beanName);
//        } catch (Exception ex) {
//            LOGGER.error(ex.getMessage());
//            return null;
//        }
//    }


    /**
     * 根据类名获取到bean
     *
     * @param <T>
     * @param clazz
     * @return
     * @throws BeansException
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBeanByClass(Class<T> clazz) throws BeansException {
        try {
            return (T) MySpringUtils.getApplicationContext().getBean(clazz);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return null;
        }
    }

//    public static <T> T getBeanByClassName(Class<T> clazz) throws BeansException {
//        try {
//            char[] cs = clazz.getSimpleName().toCharArray();
//            //首字母大写到小写
//            cs[0] += 32;
//            return (T) MySpringUtils.getApplicationContext().getBean(String.valueOf(cs));
//        } catch (Exception ex) {
//            LOGGER.error(ex.getMessage());
//            return null;
//        }
//    }

    /**
     * 创建一个bean
     *
     * @param name
     * @return
     */
    public static boolean containsBeanByBeanName(String name) {
        return MySpringUtils.getApplicationContext().containsBean(name);
    }

}