package com.springTest.aop.anno;

import org.springframework.stereotype.Component;

/**
 * @desciption:用途
 * @author: 杜俊圻
 * @date: 2020/10/24 16:06
 */
@Component(value = "anno")
public class AspectJAnnoTest {
    public void after(){
        System.out.println("之后执行");
    }
}
