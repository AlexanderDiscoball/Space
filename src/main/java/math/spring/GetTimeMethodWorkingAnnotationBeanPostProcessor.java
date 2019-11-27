package math.spring;

import math.entity.linesegments.Algorithms;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class GetTimeMethodWorkingAnnotationBeanPostProcessor implements BeanPostProcessor {
    private Map<String, Class> map = new HashMap<>();
    static long start = 0;
    static long end = 0;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        Method[] methods = Algorithms.class.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(math.annotation.GetMethodTime.class)) {
                map.put(beanName, beanClass);
            }
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = map.get(beanName);
        if (beanClass != null) {
            Method[] methods = Algorithms.class.getMethods();
            for (Method methodf : methods) {
                if (methodf.isAnnotationPresent(math.annotation.GetMethodTime.class)) {
                    return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            start = System.currentTimeMillis();
                            Object retVal = method.invoke(bean, args);
                            end = System.currentTimeMillis();
                            System.out.println("Время на " + method.getName() + "  " + (end - start) + " Миллисекунд");
                            return retVal;
                        }
                    });
                }
            }
        }
        return bean;
    }
}


