package com.springTest;
import org.springframework.beans.factory.FactoryBean;

/**
 * @desciption:用途
 * @author: 杜俊圻
 * @date: 2020/10/21 17:58
 */
public class FactoryBeanTest implements FactoryBean<User> {
    @Override
    public User getObject() throws Exception {
        User user = new User();
        return user;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
