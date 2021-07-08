package com.springTest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @desciption:用途
 * @author: 杜俊圻
 * @date: 2020/10/21 16:28
 */
public class TestUser {
    public static void main(String[] args) {
        //查询类路径 加载配置文件 ClassPathXmlApplicationContext 读取src下文件夹的相对路径
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");
        //读取配置文件所在盘符的绝对路径
        ApplicationContext fileSystemXmlApplicationContext = new FileSystemXmlApplicationContext("D:/resource/springTest/src/spring-config.xml");
        //根据id获取bean
        //Spring就是一个大工厂（容器）专门生成bean bean就是对象
        User user = (User) applicationContext.getBean("user");
        User user2 = (User) fileSystemXmlApplicationContext.getBean("user");
        //输出获取到的对象
        System.out.println("user = " + user+"user2 = " + user2);
    }
}
