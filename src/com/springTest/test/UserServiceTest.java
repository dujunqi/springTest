package com.springTest.test;

import com.springTest.Service.UserService;
import com.springTest.config.SpringConfig;
import com.springTest.controller.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @desciption:用途
 * @author: 杜俊圻
 * @date: 2020/10/22 17:07
 */
public class UserServiceTest {
    @Test
    public  void userServiceTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
        UserController bean = context.getBean("userController", UserController.class);
        System.out.println(bean);
        bean.test();
    }

    @Test
    public  void userServiceTest2(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserController bean = context.getBean("userController", UserController.class);
        System.out.println(bean);
        bean.test();
    }
}
