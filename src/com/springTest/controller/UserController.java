package com.springTest.controller;

import com.springTest.Service.CatService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @desciption:用途
 * @author: 杜俊圻
 * @date: 2020/10/22 17:34
 */
@Controller
public class UserController {
    @Resource(name = "catService1")
    private CatService catService;

    public void test(){
        catService.add();
    }
}
