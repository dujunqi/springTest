package com.springTest;

/**
 * @desciption:用途
 * @author: 杜俊圻
 * @date: 2020/10/22 15:08
 */
public class BeanLife {
    private String name;

    BeanLife() {System.out.println("第一步：调用无参的构造方法创建Bean");}

    public void setName(String name) {this.name = name;
        System.out.println("第二步：调用set方法向bean中的属性注值");}

    public void initMethod() {System.out.println("第三步：使用初始化方法");}

    public void destroyMethod() {System.out.println("第五步：销毁Bean");}
}
