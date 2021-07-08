package com.springTest.test;

import com.springTest.Proxy.ProxyTestHandler;
import com.springTest.Service.IProxyTest;
import com.springTest.Service.impl.ProxyTestImpl;
import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * @desciption:用途
 * @author: 杜俊圻
 * @date: 2020/10/23 15:31
 */
public class ProxyTestDemo {
    @Test
    public void proxyTest(){
        //待加强的接口数组
        Class[] i = {IProxyTest.class};
        //创建接口实现类的代理对象
        IProxyTest o = (IProxyTest)Proxy.newProxyInstance(ProxyTestDemo.class.getClassLoader(), i, new ProxyTestHandler(new ProxyTestImpl()));
        o.get(1);
    }
}
