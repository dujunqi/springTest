package com.springTest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @desciption:用途
 * @author: 杜俊圻
 * @date: 2020/10/22 15:33
 */
public class BeanLifePost implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("第三步：后置处理器在初始化之前，执行的方法");
        return bean;
    }
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("第五步：后置处理器在初始化之后，执行的方法");
        return bean;
    }
}
