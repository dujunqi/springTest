package com.springTest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @desciption:用途
 * @author: 杜俊圻
 * @date: 2020/10/21 18:02
 */
public class BeanTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config.xml");
        User factoryBeanTest = applicationContext.getBean("factoryBeanTest",User.class);
        System.out.println(factoryBeanTest);
    }
}
