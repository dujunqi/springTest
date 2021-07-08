package com.springTest;

/**
 * @desciption:用途
 * @author: 杜俊圻
 * @date: 2020/10/21 16:24
 */
public class User {
    private String name;        //姓名
    private int age;            //年龄

    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
