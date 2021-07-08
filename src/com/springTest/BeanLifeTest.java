package com.springTest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @desciption:用途
 * @author: 杜俊圻
 * @date: 2020/10/22 15:14
 */
public class BeanLifeTest {
    public static void main(String[] args) {
        ApplicationContext beanLife = new ClassPathXmlApplicationContext("config.xml");
        BeanLife beanLift = beanLife.getBean("beanLife", BeanLife.class);
        System.out.println("第四步：Bean进入了IOC容器");
        System.out.println(beanLife);
        ((ClassPathXmlApplicationContext) beanLife).close();//关闭容器
    }

}
