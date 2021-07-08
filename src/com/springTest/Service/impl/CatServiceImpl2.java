package com.springTest.Service.impl;

import com.springTest.Service.CatService;
import org.springframework.stereotype.Service;

/**
 * @desciption:用途
 * @author: 杜俊圻
 * @date: 2020/12/28 15:21
 */
@Service("catService2")
public class CatServiceImpl2 implements CatService {
    @Override
    public void add() {
        System.out.println("CatServiceImpl2");
    }
}
