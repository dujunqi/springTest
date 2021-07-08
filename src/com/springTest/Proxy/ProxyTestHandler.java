package com.springTest.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @desciption:用途
 * @author: 杜俊圻
 * @date: 2020/10/23 15:38
 */
    public class ProxyTestHandler implements InvocationHandler {

    //由于Proxy.newProxyInstance方法中获得的是接口class对象，所以需要指定该接口的实现类，并传入该类
    //通过有参构造进行传递
    private Object o;
    public ProxyTestHandler(Object o){this.o = o;}
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //方法之前
        System.out.println("方法之前执行："+ method.getName()+"    传递的参数："+ Arrays.toString(args));
        //被增强的方法执行
        Object invoke = method.invoke(o,args);
        //方法之后
        System.out.println("方法之后执行："+o);
        return invoke;
    }
}
