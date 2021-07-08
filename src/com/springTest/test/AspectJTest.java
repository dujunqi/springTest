package com.springTest.test;

import com.springTest.aop.anno.AspectJAnnoTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @desciption:用途
 * @author: 杜俊圻
 * @date: 2020/10/24 16:15
 */
public class AspectJTest {
    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("annoConfig.xml");
        AspectJAnnoTest anno = context.getBean("anno", AspectJAnnoTest.class);
        anno.after();
    }
}
