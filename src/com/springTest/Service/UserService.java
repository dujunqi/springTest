package com.springTest.Service;

import org.springframework.stereotype.Service;

/**
 * @desciption:用途
 * @author: 杜俊圻
 * @date: 2020/10/22 17:04
 */
@Service(value = "userService")//类似于<bean id="userService" class=".."> 该value属性不写也不影响使用
public class UserService {
    private String name;
    public void out(){
         System.out.println("找到你了");
    }
}

