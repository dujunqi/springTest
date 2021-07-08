package com.springTest.Service.impl;

import com.springTest.Service.IProxyTest;

/**
 * @desciption:用途
 * @author: 杜俊圻
 * @date: 2020/10/23 15:28
 */
public class ProxyTestImpl implements IProxyTest {
    @Override
    public Integer sum(int a, int b) {return a+b;}

    @Override
    public Integer get(int a) {return a;}
}
