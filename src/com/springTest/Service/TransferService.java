package com.springTest.Service;

/**
 * @desciption:用途
 * @author: 杜俊圻
 * @date: 2020/10/26 15:35
 */
public interface TransferService {
    public void transferMoney(String source, String destination, Long amount);
}
