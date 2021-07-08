package com.springTest.Service;

/**
 * @desciption:用途
 * @author: 杜俊圻
 * @date: 2020/10/26 15:37
 */
public interface TransferDao {

    public void payMoney(String name, Long amount);

    /**
     * @param name 账户名称
     * @param amount 收入金额
     */
    public void collectMoney(String name, Long amount);
}
