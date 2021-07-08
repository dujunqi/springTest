package com.springTest.aop.anno;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @desciption:用途
 * @author: 杜俊圻
 * @date: 2020/10/24 16:08
 */
@Component
@Aspect
public class ProxyAnno {
    @Before(value = "execution(* com.springTest.aop.anno.AspectJAnnoTest.after(..))")
    public void before(){
        System.out.println("在之前执行...");
    }
}
