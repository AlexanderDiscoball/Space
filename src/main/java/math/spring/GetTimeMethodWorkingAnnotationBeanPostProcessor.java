package math.spring;

import math.annotation.AlgorithmsType;
import math.annotation.GetMethodTime;
import math.entity.Array.TwoDimensionalArray;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;

public class GetTimeMethodWorkingAnnotationBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getMethods();
        for (Method method :methods) {
            GetMethodTime meth = method.getAnnotation(GetMethodTime.class);
            if (meth != null) {

            }
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
