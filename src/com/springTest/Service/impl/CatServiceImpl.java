package com.springTest.Service.impl;

import com.springTest.Service.CatService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @desciption:用途
 * @author: 杜俊圻
 * @date: 2020/10/22 17:31
 */
@Service(value = "catService1")
public class CatServiceImpl implements CatService {
    @Value(value = "123")
    private String name;

    @Override
    @Transactional
    public void add() {
        System.out.println("注入完成"+name);
    }
}
